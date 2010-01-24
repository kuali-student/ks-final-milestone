package org.kuali.student.brms.internal.common.statement;

public class MessageContextConstants {
	// Abstract Rule Proposition
	public final static String PROPOSITION_STATIC_FACT_COLUMN = "static.column";
	
	// _str is a String object
	public final static String PROPOSITION_MESSAGE_CTX_KEY_EXPECTEDVALUE = "prop_expected_value_str";
    public final static String PROPOSITION_MESSAGE_CTX_KEY_OPERATOR = "prop_comparison_operator_str";
    public final static String PROPOSITION_MESSAGE_CTX_KEY_OPERATOR_DATATYPE = "prop_comp_oper_datatype_str";
	// _fs is a org.kuali.student.brms.factfinder.dto.FactResultInfo object
    public final static String PROPOSITION_MESSAGE_CTX_KEY_FACT = "prop_fact_fr";
	public final static String PROPOSITION_MESSAGE_CTX_KEY_RESULT = "prop_result_fr";

	// Average Rule Proposition
	public final static String PROPOSITION_AVERAGE_COLUMN_KEY = "key.proposition.column.average";
    public final static String PROPOSITION_AVERAGE_MESSAGE_CTX_KEY = "proposition_average";
    public final static String PROPOSITION_AVERAGE_MESSAGE_CTX_KEY_DIFF = "proposition_average_diff";
	public final static String PROPOSITION_AVERAGE_SUCCESS_MESSAGE = "Average constraint fulfilled";
    public final static String PROPOSITION_AVERAGE_FAILURE_MESSAGE = "Average of $proposition_average is short by $proposition_average_diff. Expected an average of $prop_expected_value_str.";
    
	// Intersection Rule Proposition
	public final static String PROPOSITION_INTERSECTION_COLUMN_KEY = "key.proposition.column.intersection";
    // _set is a java.util.Set object
	public final static String PROPOSITION_INTERSECT_MESSAGE_CTX_KEY_DIFF = "prop_intersection_diff_set";
    public final static String PROPOSITION_INTERSECT_MESSAGE_CTX_KEY_MET = "prop_intersection_met_set";
    public final static String PROPOSITION_INTERSECT_MESSAGE_CTX_KEY_UNMET = "prop_intersection_unmet_set";
	public final static String PROPOSITION_INTERSECTION_SUCCESS_MESSAGE = "Intersection constraint fulfilled";
    public final static String PROPOSITION_INTERSECTION_FAILURE_MESSAGE = "$prop_intersection_diff of $prop_intersection_unmet_set is still required";

	// Max Rule Proposition
	public final static String PROPOSITION_MAX_COLUMN_KEY = "key.proposition.column.min";
    public final static String PROPOSITION_MAX_MESSAGE_CTX_KEY_MAX = "prop_max";
	public final static String PROPOSITION_MAX_SUCCESS_MESSAGE = "Maximum constraint fulfilled";
    public final static String PROPOSITION_MAX_FAILURE_MESSAGE = "Maximum of $prop_expected_value_str not met. Maximum found: $prop_max.";

	// Min Rule Proposition
	public final static String PROPOSITION_MIN_COLUMN_KEY = "key.proposition.column.min";
    public final static String PROPOSITION_MIN_MESSAGE_CTX_KEY_MIN = "prop_min";
	public final static String PROPOSITION_MIN_SUCCESS_MESSAGE = "Minimum constraint fulfilled";
    public final static String PROPOSITION_MIN_FAILURE_MESSAGE = "Minimum of $prop_expected_value_str not met. Minimum found: $prop_min.";

	// Simple Comparable Rule Proposition
	public final static String PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY = "key.proposition.column.simplecomparable";
    public final static String PROPOSITION_SIMPLE_COMPARABLE_MESSAGE_CTX_KEY_FACT = "prop_simple_comp_fact";
	public final static String PROPOSITION_SIMPLE_COMPARABLE_SUCCESS_MESSAGE = "Comparison constraint fulfilled";
    public final static String PROPOSITION_SIMPLE_COMPARABLE_FAILURE_MESSAGE = "$prop_simple_comp_fact not $prop_comparison_operator_str $prop_expected_value_str";

	// Subset Rule Proposition
	public final static String PROPOSITION_SUBSET_COLUMN_KEY = "key.proposition.column.subset";
	public final static String PROPOSITION_SUBSET_SUCCESS_MESSAGE = "Subset constraint fulfilled";
    public final static String PROPOSITION_SUBSET_FAILURE_MESSAGE = "$prop_subset_diff of $prop_subset_unmetset is still required"; 

	// Sum Rule Proposition
	public final static String PROPOSITION_SUM_COLUMN_KEY = "key.proposition.column.sum";
	public final static String PROPOSITION_SUM_MESSAGE_CTX_KEY_SUM = "prop_sum";
    public final static String PROPOSITION_SUM_MESSAGE_CTX_KEY_SUM_DIFF = "prop_sum_diff";
	public final static String PROPOSITION_SUM_SUCCESS_MESSAGE = "Sum constraint fulfilled";
    public final static String PROPOSITION_SUM_FAILURE_MESSAGE = "Sum constraint failed. "
		+ "Sum is short by $prop_sum_diff. Expected=$prop_expected_value_str, Sum=$prop_sum, Difference=$prop_sum_diff";
}
