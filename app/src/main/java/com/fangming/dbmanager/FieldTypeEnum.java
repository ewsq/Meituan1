package com.fangming.dbmanager;

import java.util.EnumMap;

public enum FieldTypeEnum {

	TEXT, String, NOTNULL;

	public FieldTypeEnum[] getValus() {
		return FieldTypeEnum.values();
	}

	static EnumMap<FieldTypeEnum, String> enumMap = new EnumMap<FieldTypeEnum, String>(
			FieldTypeEnum.class);

	/**
	 * 默认字段类型
	 */
	static {
		enumMap.put(TEXT, "TEXT");
		enumMap.put(String, "String");
		enumMap.put(NOTNULL, "NOTNULL");
	}

	/**
	 * 获取字段类型
	 * 
	 * @param f
	 * @return 字段类型
	 */
	public static String getFieldName(FieldTypeEnum f) {
		return enumMap.get(f);
	}
}
