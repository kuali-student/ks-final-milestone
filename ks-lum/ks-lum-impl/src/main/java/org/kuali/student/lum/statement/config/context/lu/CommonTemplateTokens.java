package org.kuali.student.lum.statement.config.context.lu;

/**
 * This class contains common template tokens that can be used in any 
 * context class such as <code>DurationContextImpl.java</code>.
 */
public class CommonTemplateTokens {
	/**
	 * <code>NLHelper</code> token (key) references a static natural language
	 * helper class used in templates.
	 * e.g. '$NLHelper.getProperGrammer($expectedValue, "course", "courses")'
	 */
	public final static String NL_HELPER_TOKEN = "NLHelper";

	/**
	 * Common template tokens (keys).
	 */
	public final static String EXPECTED_VALUE_TOKEN = "expectedValue";
	public final static String OPERATOR_TOKEN = "relationalOperator";
}
