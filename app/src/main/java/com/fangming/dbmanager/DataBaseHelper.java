package com.fangming.dbmanager;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	public List<Class<?>> tableList = new ArrayList<Class<?>>();

	public DataBaseHelper(Context context, String name, int version,
			List<Class<?>> tableList) {
		super(context, name, null, version);
		this.tableList = tableList;
	}

	/**
	 * 删除数据库
	 * 
	 * @param context
	 *            上下文环境
	 * @param dbName
	 *            数据库名
	 * @return 成功的场合返回<code>true</code>
	 */
	public boolean deleteDataBase(Context context, String dbName) {
		return context.deleteDatabase(dbName);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("createDataBase", "create start");
		List<String> sqlList = createSqlStr(tableList);
		db.beginTransaction();
		try {
			for (String str : sqlList) {
				db.execSQL(str);
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			Log.e("createDataBase", "create DataBase failed!");
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		List<String> sqlList = createSqlStr(tableList);
		List<String> tableNameList = getTableName(tableList);
		db.beginTransaction();
		try {
			for (String key : tableNameList) {
				// 删除表
				db.execSQL("DROP TABLE IF EXISTS " + key);
			}
			// 重新生成表
			for (String sql : sqlList) {
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			Log.e("updateDataBase", "upate DataBase failed!");
		}
	}

	private List<String> getTableName(List<Class<?>> tableListClass) {
		if (null == tableListClass) {
			throw new IllegalArgumentException("传入的表名列表为空");
		}
		List<String> tableNameList = new ArrayList<String>();
		String className = null;
		for (int i = 0; i < tableList.size(); i++) {
			className = tableListClass.get(i).getName();
			Class<?> test = null;
			try {
				test = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			/**
			 * 表名的取得
			 */
			String tableName = null;

			if (test.isAnnotationPresent(AnnotationTables.class)) {
				AnnotationTables table = (AnnotationTables) test
						.getAnnotation(AnnotationTables.class);
				tableName = table.name();
				if ("".equals(tableName)) {
					tableName = test.getSimpleName();
				}
				tableNameList.add(tableName);
			}
		}
		return tableNameList;
	}

	private List<String> createSqlStr(List<Class<?>> tableWithClassNameList) {

		List<String> strSqlList = new ArrayList<String>();
		if (tableWithClassNameList == null) {
			throw new IllegalArgumentException("传入的表名列表为空");
		}	

		String className = null;
		for (int i = 0; i < tableWithClassNameList.size(); i++) {
			className = tableWithClassNameList.get(i).getName();

			Class<?> test = null;
			try {
				test = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			boolean blnPrimary = false;
			/**
			 * 表名的取得
			 */
			String tableName = null;

			if (test.isAnnotationPresent(AnnotationTables.class)) {
				AnnotationTables table = (AnnotationTables) test
						.getAnnotation(AnnotationTables.class);
				tableName = table.name();
				if ("".equals(tableName)) {
					tableName = test.getSimpleName();
				}
				blnPrimary = table.isCallSelfPrimary();
			}

			List<String> sqlColumn = new ArrayList<String>();

			Field[] fields = test.getDeclaredFields();
			Field field = null;

			StringBuilder sbBuilder = null;

			/**
			 * loop fields
			 */
			for (int j = 0; j < fields.length; j++) {
				field = fields[j];

				if (field.isAnnotationPresent(AnnotationColumns.class)) {

					AnnotationColumns columns = field
							.getAnnotation(AnnotationColumns.class);

					/**
					 * 是否为主键
					 */
					boolean blnIsPrimaryKey = columns.isPrimaryKey();

					/**
					 * 表字段
					 */
					String columnName = columns.name();

					/** 表字段名的建立 */
					if (!"".equals(columnName)) {
						// columnName=columns.name();
					} else {
						columnName = field.getName();
					}

					/**
					 * 表字段在数据库中的类型
					 */
					String columnType = FieldTypeEnum.getFieldName(columns
							.enumType());

					// System.out.println("名称"+columnType);
					/**
					 * 默认类型的场合
					 */
					if (!"NOTNULL".equals(columnType)) {
						// columnType="VARCHAR(50)";
					} else {
						columnType = setColumnType(field.getType(),
								columnType);
					}

					/**
					 * 是否为NOT NULL
					 */
					boolean blnNull = columns.notNull();

					sbBuilder = new StringBuilder();

					sbBuilder.append(columnName);

					sbBuilder.append(" ");
					sbBuilder.append(columnType);

					if (blnIsPrimaryKey && columnName.equals("_id")) {
						sbBuilder.append(" ");
						sbBuilder.append(" PRIMARY KEY AUTOINCREMENT ");
					} else if (blnIsPrimaryKey) {
						sbBuilder.append(" ");
						sbBuilder.append(" PRIMARY KEY ");
					}

					if (blnNull) {
						sbBuilder.append(" ");
						sbBuilder.append("NOT NULL");
					}
					sqlColumn.add(sbBuilder.toString());
				}
			}
			strSqlList.add(createSql(tableName, blnPrimary, sqlColumn));
		}
		return strSqlList;
	}

	/**
	 * 创建Sql文
	 * 
	 * @param tableName
	 *            表明
	 * @param blnPrimary
	 *            是否需要主键
	 * @param sqlColumn
	 *            表字段
	 */
	private String createSql(String tableName, boolean blnPrimary,
			List<String> sqlColumn) {

		StringBuilder sbBuilder = new StringBuilder();
		sbBuilder.append("CREATE TABLE ");
		sbBuilder.append(tableName);
		sbBuilder.append("(");
		if (blnPrimary) {
			sbBuilder.append(BaseColumns._ID);
			sbBuilder.append(" ");
			sbBuilder.append("INTEGER PRIMARY KEY AUTOINCREMENT ");
			sbBuilder.append(",");
		}
		for (int i = 0; i < sqlColumn.size(); i++) {

			if (i == sqlColumn.size() - 1) {
				sbBuilder.append(sqlColumn.get(i));
				sbBuilder.append(")");
			} else {
				sbBuilder.append(sqlColumn.get(i));
				sbBuilder.append(",");
			}
		}
		System.out.println(sbBuilder.toString());
		return sbBuilder.toString();
	}

	/**
	 * <p>
	 * 设置表字段类型
	 * 
	 * @param type
	 *            传入的类型
	 * @param columnType
	 *            得到字符串类型
	 */
	private String setColumnType(Type type, String columnType) {

		/** String */
		if (type.equals(String.class)) {
			columnType = "VARCHAR(20)";
			/** Boolean */
		} else if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
			columnType = "BOOLEAN";

			/** Integer */
		} else if (type.equals(Integer.class) || type.equals(Integer.TYPE)
				|| type.equals(Long.class) || type.equals(Long.TYPE)) {

			columnType = "INTEGER";
		} else if (type.equals(Double.class) || type.equals(Double.TYPE)
				|| type.equals(Float.class) || type.equals(Float.TYPE)) {
			columnType = "DOUBLE(3,2)";
		}
		return columnType;
	}

}
