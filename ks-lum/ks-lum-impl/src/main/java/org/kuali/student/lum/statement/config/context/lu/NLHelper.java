package org.kuali.student.lum.statement.config.context.lu;

public class NLHelper {

	public static String getProperGrammer(String number, String singularText, String pluralText) {
		return (Integer.valueOf(number) > 1 ? pluralText : singularText);
	}
}
