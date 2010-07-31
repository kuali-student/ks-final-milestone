package org.kuali.student.lum.statement.config.context.lu;

public class NLHelper {

	public static String getProperGrammar(Number number, String singularText, String pluralText) {
		return (number.intValue() == 1 ? singularText : pluralText);
	}
	
    public static String getProperGrammar(Number number, String singularText) {
        return (number.intValue() == 1 ? singularText : singularText + 's');
    }	

	public static String getProperGrammar(String number, String singularText, String pluralText) {
		return getProperGrammar(Integer.valueOf(number), singularText, pluralText);
	}
	
	public static String getProperGrammar(String number, String singularText) {
		return getProperGrammar(Integer.valueOf(number), singularText);
	}
}
