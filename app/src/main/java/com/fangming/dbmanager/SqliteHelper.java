package com.fangming.dbmanager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper {
	/**
	 * SQLiteOpenHelper类
	 */
	private SQLiteOpenHelper sqliteOpenHelper;

	/**
	 * SQLiteDatabase类
	 */
	private SQLiteDatabase sqliteDatabase;

	/**
	 * 构造函数
	 * 
	 * @param sqliteOpenHelper
	 */
	public SqliteHelper() {
	}

	/**
	 * 构造函数
	 * 
	 * @param sqliteOpenHelper
	 */
	public SqliteHelper(Context context, String dataBaseName,
			int dataBaseVersion, List<Class<?>> tableClassNameList) {
		this.sqliteOpenHelper = new DataBaseHelper(context, dataBaseName,
				dataBaseVersion, tableClassNameList);
	}

	/**
	 * 打开数据库
	 */
	public void open() {
		try {

			if (!sqliteDatabase.isOpen()) {
				sqliteDatabase = sqliteOpenHelper.getWritableDatabase();
			}
			Log.d("openDataBase", "open DataBase success!");

		} catch (Exception e) {
			sqliteDatabase = sqliteOpenHelper.getReadableDatabase();
			Log.d("openDataBase", "open DataBase failed!");

		}
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		try {
			if (sqliteDatabase.isOpen()) {
				sqliteDatabase.close();
			}

		} catch (Exception e) {
			Log.d("closeDataBase", "close DataBase failed!");
		}
	}

	/**
	 * 删除原数据表，重建数据表
	 */
	public void upgradeDataBase(int olderVersion, int newVersion) {
		open();
		sqliteOpenHelper.onUpgrade(sqliteDatabase, olderVersion, newVersion);
		close();
	}

	/**
	 * 开始事务
	 */
	public void beginTransaction() {
		open();
		Log.d(this.getClass().getName(), "开始事务");
		sqliteDatabase.beginTransaction();
	}

	/**
	 * 成功完成事务
	 */
	public void setTransactionSuccessful() {
		Log.d(this.getClass().getName(), "成功完成事务");
		sqliteDatabase.setTransactionSuccessful();
	}

	/**
	 * 结束事务
	 */
	public void endTransaction() {
		Log.d(this.getClass().getName(), "结束事务");
		sqliteDatabase.endTransaction();
	}

	/**
	 * 直接转换为Cursor对象
	 * 
	 * @param sql
	 * @return
	 */
	public Cursor toCursor(String sql) {
		Cursor cursor = sqliteDatabase.rawQuery(sql, null);
		return cursor;
	}

	/**
	 * 操作Sql文
	 * 
	 * @param sql
	 *            sql文
	 */
	public void execSql(String sql) {
		sqliteDatabase.execSQL(sql);
	}

	/**
	 * sql语句查询
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @param clazz
	 * @return Arrlist<T> 集合
	 */
	public <T> ArrayList<T> queryForListBySql(String sql,
			String[] selectionArgs, Class<T> clazz) {
		Cursor cursor = sqliteDatabase.rawQuery(sql, selectionArgs);
		return cursorToList(cursor, clazz);
	}

	/**
	 * 通过反射将cursor转化成List
	 * 
	 * @param <T>
	 *            返回类
	 * @param cursor
	 *            Cursor类 返回对象类型
	 * @return 对象列表
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> cursorToList(Cursor cursor, Class<T> clazz) {

		// 初始化结果LIST
		ArrayList<T> r_list = new ArrayList<T>();
		try {
			if (null != cursor && null != clazz) {

				// 要封装的对象
				T obj = null;

				// 属性对象
				Object fieldValue = null;

				// 获得列名
				String t_columnName = null;

				// 遍历结果集cursor
				while (cursor.moveToNext()) {

					// 初始化封装对象
					obj = clazz.newInstance();

					HashMap<String, ArrayList<String>> mapString = new HashMap<String, ArrayList<String>>();

					HashMap<String, Integer> cursorNameIndexMap = new HashMap<String, Integer>();

					// 遍历-列
					for (int i = 0; i < cursor.getColumnCount(); i++) {

						// 获得列名
						t_columnName = cursor.getColumnName(i);

						if (t_columnName == null) {
							continue;
						} else // 用于控制状态位的不需要查询出值出来。
						if (t_columnName.equals("_mobileState")) {
							continue;
						} else if (t_columnName.contains("$")) {
							int intIndex = t_columnName.indexOf("$");

							String str1 = t_columnName.substring(0, intIndex);
							String str2 = t_columnName.substring(intIndex + 1,
									t_columnName.length());

							if (mapString.containsKey(str1)) {
								ArrayList<String> temp1 = mapString.get(str1);
								temp1.add(str2);
								mapString.put(str1, temp1);
							} else {
								ArrayList<String> temp2 = new ArrayList<String>();
								temp2.add(str2);
								mapString.put(str1, temp2);
							}
							cursorNameIndexMap.put(str2,
									cursor.getColumnIndex(t_columnName));
						} else {
							Field t_field = clazz
									.getDeclaredField(t_columnName);

							// 设为可操作
							t_field.setAccessible(true);

							// 属性对象清空
							fieldValue = null;

							Type type = t_field.getType();

							// String的场合
							if (type.equals(String.class)) {
								fieldValue = cursor.getString(i);

								// 整数的场合
							} else if (type.equals(Integer.TYPE)
									|| type.equals(Integer.class)) {
								fieldValue = Integer.valueOf(cursor.getInt(i));

								// Double的场合
							} else if (type.equals(Double.TYPE)
									|| type.equals(Double.class)) {
								fieldValue = Double
										.valueOf(cursor.getDouble(i));

								// Boolean的场合
							} else if (type.equals(Boolean.TYPE)
									|| type.equals(Boolean.class)) {
								String strTemp = cursor.getString(i);

								if (null != strTemp) {

									fieldValue = "1".equals(strTemp) ? true
											: false;
								}

								// Long 的场合
							} else if (type.equals(Long.TYPE)
									|| type.equals(Long.class)) {
								fieldValue = cursor.getLong(i);

								// float的场合
							} else if (type.equals(Float.TYPE)
									|| type.equals(Float.class)) {
								fieldValue = Float.valueOf(cursor.getFloat(i));
							}
							// fieldValue不为空的场合
							if (null != fieldValue) {
								t_field.set(obj, fieldValue);
							}
						}
					}

					// 循环mapString
					for (String fieldNameKey : mapString.keySet()) {
						List<String> reffieldNames = mapString
								.get(fieldNameKey);

						Field refentityKey = obj.getClass().getDeclaredField(
								"oneKeytoName");
						Map<String, String> ddd = (Map<String, String>) refentityKey
								.get(null);
						Field fieldTemp = clazz.getDeclaredField(ddd
								.get(fieldNameKey));

						Class<?> refClass = (Class<?>) fieldTemp
								.getGenericType();

						Object objectTemp = refClass.newInstance();

						String reffield = null;
						for (int k = 0; k < reffieldNames.size(); k++) {
							reffield = reffieldNames.get(k);

							Field reffiledName = objectTemp.getClass()
									.getDeclaredField(reffield);
							reffiledName.setAccessible(true);
							// 属性对象清空
							fieldValue = null;

							Type type = reffiledName.getType();

							// String的场合
							if (type.equals(String.class)) {
								fieldValue = cursor
										.getString(cursorNameIndexMap
												.get(reffield));

								// 整数的场合
							} else if (type.equals(Integer.TYPE)
									|| type.equals(Integer.class)) {
								fieldValue = Integer.valueOf(cursor
										.getInt(cursorNameIndexMap
												.get(reffield)));

								// Double的场合
							} else if (type.equals(Double.TYPE)
									|| type.equals(Double.class)) {
								fieldValue = Double.valueOf(cursor
										.getDouble(cursorNameIndexMap
												.get(reffield)));

								// Boolean的场合
							} else if (type.equals(Boolean.TYPE)
									|| type.equals(Boolean.class)) {
								if (cursorNameIndexMap != null
										&& cursorNameIndexMap
												.containsKey(reffield)) {

									String tempString = cursor
											.getString(cursorNameIndexMap
													.get(reffield));
									if (null != tempString) {

										fieldValue = "1".equals(cursor
												.getString(cursorNameIndexMap
														.get(reffield))) ? true
												: false;
									}

								}

								// Long 的场合
							} else if (type.equals(Long.TYPE)
									|| type.equals(Long.class)) {
								fieldValue = cursor.getLong(cursorNameIndexMap
										.get(reffield));

								// float的场合
							} else if (type.equals(Float.TYPE)
									|| type.equals(Float.class)) {
								fieldValue = Float.valueOf(cursor
										.getFloat(cursorNameIndexMap
												.get(reffield)));
							}
							// fieldValue不为空的场合
							if (null != fieldValue) {
								reffiledName.set(objectTemp, fieldValue);
							}
						}

						fieldTemp.setAccessible(true);

						fieldTemp.set(obj, objectTemp);

						fieldTemp.setAccessible(false);
					}

					r_list.add(obj);
				}
				if (r_list.size() < 1) {
					r_list = null;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			cursor.close();
		}
		return r_list;
	}

	/**
	 * 把需要插入的字段放到hashmap中插入
	 * 
	 * @param tableName
	 * @param contentValues
	 * @return true is row id & false is -1
	 */
	public long insert(String tableName, ContentValues contentValues) {
		return sqliteDatabase.insert(tableName, null, contentValues);
	}

	/**
	 * 获取表记录总数
	 * 
	 * @return
	 */
	public long getCount(String tableName) {
		if (null == tableName) {
			return 0;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ");
		sb.append(tableName);
		Cursor cur = sqliteDatabase.rawQuery(sb.toString(), null);
		cur.moveToFirst();
		long count = cur.getLong(0);
		cur.close();
		return count;
	}

	/**
	 * 删除记录
	 * 
	 * @param tableName
	 *            表名
	 * @param whereClause
	 *            where条件
	 * @param whereArgs
	 *            where条件字段的值
	 * @return 受影响的行数
	 */
	public int delete(String tableName, String whereClause, String[] whereArgs) {
		return sqliteDatabase.delete(tableName, whereClause, whereArgs);
	}

	/**
	 * 更新记录
	 * @param tableName 表名
	 * @param obj 更新的对象(set 后面语句)
	 * @param whereClause where条件
	 * @param whereArgs where条件字段值
	 * @return
	 */
	public int update(String tableName, Object obj, String whereClause,
			String[] whereArgs) {
		if (obj == null) {
			return -1;
		}
		ContentValues contentValue = jsonToContentValues(convertObjtoJson(obj));
		return sqliteDatabase.update(tableName, contentValue, whereClause, whereArgs);
	}

	public String convertObjtoJson(Object src) {
		Gson gson = new Gson();
		String requestBody = null;
		try {
			if (src == null) {
				// skip
			} else if (src.getClass().equals(String.class)) {
				requestBody = src.toString();
			} else {
				requestBody = gson.toJson(src);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return requestBody;
	}

	/**
	 * json转Contentvalues
	 * 
	 * @param strJson
	 * @return
	 */
	public ContentValues jsonToContentValues(String strJson) {
		ContentValues cvs = new ContentValues();
		try {
			JSONObject jsonObject = new JSONObject(strJson);

			Iterator<?> iterator = jsonObject.keys();

			String key = null;
			Object objValue = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next().toString();
				objValue = jsonObject.get(key);

				if (objValue instanceof String) {
					cvs.put(key, (String) objValue);
				} else if (objValue instanceof Integer) {
					cvs.put(key, (Integer) objValue);
				} else if (objValue instanceof Double) {
					cvs.put(key, (Double) objValue);
				} else if (objValue instanceof Long) {
					cvs.put(key, (Long) objValue);
				} else if (objValue instanceof Boolean) {
					cvs.put(key, ("true".equals(objValue.toString()) ? 1 : 0));
				} else if (objValue instanceof Float) {
					cvs.put(key, (Float) objValue);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cvs;

	}
	
	/**
	 * <p>
	 * 插入数据
	 * </p>
	 * 
	 * 插入基于表注解的类
	 * 
	 * @param obj
	 *            基于Table注解的数据对象
	 * @return 成功的场合返回<code>1</code>
	 * @see #insert(String, Object)
	 * 
	 */
	public long insert(Object obj) {
		if (obj == null) {
			return -1;
		}
		String tableName = null;
		Class<?> clazz = obj.getClass();
		/**
		 * 注解为表的场合
		 */
		if (clazz.isAnnotationPresent(AnnotationTables.class)) {
			AnnotationTables table = (AnnotationTables) clazz
					.getAnnotation(AnnotationTables.class);
			if ("".equals(table.name())) {
				tableName = clazz.getSimpleName();
			} else {
				tableName = table.name();
			}
			return insert(tableName, obj);
		} else {
			/**
			 * 该类没有被注解为表，不能插入
			 */
			return -1;
		}
	}
	
	/**
	 * <p>
	 * 插入数据
	 * </p>
	 * 
	 * 指定一张表，插入基本表注解的类
	 * 
	 * @param tableName
	 *            表名
	 * @param obj
	 *            基于Table注解的数据对象
	 * @return 成功的场合返回<code>1</code>
	 * @see #cursorToList(Cursor, Class)
	 */
	public long insert(String tableName, Object obj) {
		ContentValues contentValues = objectToContentValues(obj);
		return sqliteDatabase.insert(tableName, null, contentValues);
	}
	
	/**
	 * 通过反射将Object转换为ContentValues
	 * 
	 * @param obj
	 *            转化对象
	 * @return ContentValues
	 */
	private ContentValues objectToContentValues(Object obj) {
		/**
		 * 空的场合
		 */
		if (obj == null) {
			return null;
		}
		ContentValues contentValue = new ContentValues();

		/**
		 * 获取该对象所有的属性
		 */
		Field[] fields = obj.getClass().getDeclaredFields();

		Field tempField = null;
		for (int i = 0; i < fields.length; i++) {
			tempField = fields[i];

			if (tempField.isAnnotationPresent(AnnotationColumns.class)) {
				AnnotationColumns columns = tempField
						.getAnnotation(AnnotationColumns.class);

				Type type = tempField.getType();

				/**
				 * 表字段
				 */
				String columnName = columns.name();

				/** 表字段名的建立 */
				if (!"".equals(columnName)) {
					// columnName=columns.name();
				} else {
					columnName = tempField.getName();
				}
				if (columnName.equals("_id")) {
					continue;
				}

				boolean isNotNull = columns.notNull();

				String fieldName = tempField.getName();

				String methodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);

				try {
					Method method = obj.getClass().getMethod(methodName);

					Object tempObject = method.invoke(obj);

					if (isNotNull && null == tempObject) {
						throw new RuntimeException(fieldName
								+ "该字段为非空值，请输入相应值！");
					}

					if (type.equals(String.class)) {
						contentValue.put(columnName, (String) tempObject);
					} else if (type.equals(Integer.TYPE)
							|| type.equals(Integer.class)) {
						contentValue.put(columnName, (Integer) tempObject);
					} else if (type.equals(Double.TYPE)
							|| type.equals(Double.class)) {
						contentValue.put(columnName, (Double) tempObject);

					} else if (type.equals(Boolean.TYPE)
							|| type.equals(Boolean.class)) {
						if (null != tempObject) {

							contentValue.put(columnName, ("true"
									.equals(tempObject.toString()) ? 1 : 0));
						}
					} else if (type.equals(Long.class)
							|| type.equals(Long.TYPE)) {
						contentValue.put(columnName, (Long) tempObject);
					} else if (type.equals(Float.class)
							|| type.equals(Float.TYPE)) {
						contentValue.put(columnName, (Float) tempObject);
					}
				} catch (NoSuchMethodException e) {

					try {
						String methodName1 = "is"
								+ fieldName.substring(0, 1).toUpperCase()
								+ fieldName.substring(1);
						Method method = obj.getClass().getMethod(methodName1);

						Object tempObject = method.invoke(obj);
						if (isNotNull && null == tempObject) {
							throw new RuntimeException(fieldName
									+ "该字段为非空值，请输入相应值！");
						}
						contentValue
								.put(columnName, String.valueOf(tempObject));

					} catch (Exception e1) {
						e1.printStackTrace();;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return contentValue;
	}

}
