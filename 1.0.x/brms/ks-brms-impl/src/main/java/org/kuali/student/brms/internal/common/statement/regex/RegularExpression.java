package org.kuali.student.brms.internal.common.statement.regex;

public interface RegularExpression {
	/**
	 * Performs a regular expression match.
	 * 
	 * @param regex Regular expression
	 * @param s String to match the regular expression on
	 * @return True if string matches regular expression; otherwise false
	 */
	public Boolean matches(String regex, String s);
}
