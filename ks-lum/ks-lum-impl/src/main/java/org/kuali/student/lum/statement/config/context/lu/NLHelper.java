package org.kuali.student.lum.statement.config.context.lu;

public class NLHelper {

	public static String getProperGrammer(Number number, String singularText, String pluralText) {
		return (number.intValue() == 1 ? singularText : pluralText);
	}
	
	public static String getProperGrammer(String number, String singularText, String pluralText) {
		return getProperGrammer(Integer.valueOf(number), singularText, pluralText);
	}
}
