package com.fangming.dbmanager;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface AnnotationColumns {

	/**
	 * 该字段是否不为null
	 * 
	 * @return
	 */
	boolean notNull() default false;

	/**
	 * 是否是主键
	 * 
	 * @return
	 */
	boolean isPrimaryKey() default false;

	/**
	 * 字段名称
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 字段类型
	 * 
	 * @return
	 */
	String type() default "";
	
	/**
	 * 表字段类型的设定
	 * 
	 * @return
	 */
	FieldTypeEnum enumType() default FieldTypeEnum.NOTNULL;
	
	

}
