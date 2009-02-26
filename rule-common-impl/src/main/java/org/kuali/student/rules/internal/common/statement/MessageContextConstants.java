package org.kuali.student.rules.internal.common.statement;

public class MessageContextConstants {
	// AbstractRuleProposition
	public final static String PROPOSITION_STATIC_FACT_COLUMN = "static.column";
	
	public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_EXPECTEDVALUE = "prop_expectedValue";
    public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_OPERATOR = "prop_comparisonOperator";
    public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_OPERATOR_DATATYPE = "prop_compOperDataType";
    public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_RESULT_VALUE = "prop_resultValues";
	public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_FACT = "prop_fact";
	public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_RESULT = "prop_result";

	// Average Rule Proposition
	public final static String PROPOSITION_AVERAGE_COLUMN_KEY = "key.proposition.column.average";
    
	public final static String PROPOSITION_AVERAGE_SUCCESS_MESSAGE = "Average constraint fulfilled";
    public final static String PROPOSITION_AVERAGE_FAILURE_MESSAGE = "Average of $proposition_average is short by $proposition_average_diff. Expected an average of $prop_expectedValue.";
    
	// Intersection Rule Proposition
	public final static String PROPOSITION_INTERSECTION_COLUMN_KEY = "key.proposition.column.intersection";
    
	public final static String PROPOSITION_INTERSECTION_SUCCESS_MESSAGE = "Intersection constraint fulfilled";
    public final static String PROPOSITION_INTERSECTION_FAILURE_MESSAGE = "$prop_intersection_diff of $prop_intersection_unmetset is still required";

	// Max Rule Proposition
	public final static String PROPOSITION_MAX_COLUMN_KEY = "key.proposition.column.min";
    
	public final static String PROPOSITION_MAX_SUCCESS_MESSAGE = "Maximum constraint fulfilled";
    public final static String PROPOSITION_MAX_FAILURE_MESSAGE = "Maximum of $prop_expectedValue not met. Maximum found: $prop_max.";

	// Min Rule Proposition
	public final static String PROPOSITION_MIN_COLUMN_KEY = "key.proposition.column.min";
    
	public final static String PROPOSITION_MIN_SUCCESS_MESSAGE = "Minimum constraint fulfilled";
    public final static String PROPOSITION_MIN_FAILURE_MESSAGE = "Minimum of $prop_expectedValue not met. Minimum found: $prop_min.";

	// Simple Comparable Rule Proposition
	public final static String PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY = "key.proposition.column.simplecomparable";
    
	public final static String PROPOSITION_SIMPLE_COMPARABLE_SUCCESS_MESSAGE = "Comparison constraint fulfilled";
    public final static String PROPOSITION_SIMPLE_COMPARABLE_FAILURE_MESSAGE = "$prop_simple_comp_fact not $prop_comparisonOperator $prop_expectedValue";

	// Subset Rule Proposition
	public final static String PROPOSITION_SUBSET_COLUMN_KEY = "key.proposition.column.subset";
    
	public final static String PROPOSITION_SUBSET_SUCCESS_MESSAGE = "Subset constraint fulfilled";
    public final static String PROPOSITION_SUBSET_FAILURE_MESSAGE = "$prop_subset_diff of $prop_subset_unmetset is still required"; 

	// Sum Rule Proposition
	public final static String PROPOSITION_SUM_COLUMN_KEY = "key.proposition.column.sum";
    
	public final static String PROPOSITION_SUM_SUCCESS_MESSAGE = "Sum constraint fulfilled";
    public final static String PROPOSITION_SUM_FAILURE_MESSAGE = "Sum constraint failed. "
		+ "Sum is short by $prop_sum_diff. Expected=$prop_expectedValue, Sum=$prop_sum, Difference=$prop_sum_diff";

}
