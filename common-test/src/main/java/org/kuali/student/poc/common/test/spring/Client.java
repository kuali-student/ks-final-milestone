package org.kuali.student.poc.common.test.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Client {
	public String value();
	public String port() default "8181";
	public boolean secure() default false;
}
