package com.fangming.dbmanager;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationTables {
	/**
	 * 表名
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 是否使用默认主键
	 * 
	 * @return
	 */
	boolean isCallSelfPrimary() default false;

}
