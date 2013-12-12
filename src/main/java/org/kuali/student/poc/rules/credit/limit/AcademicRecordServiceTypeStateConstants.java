package org.kuali.student.poc.rules.credit.limit;

import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
 */
public class AcademicRecordServiceTypeStateConstants extends AcademicRecordServiceConstants {

    // types
    public static final String LOAD_TYPE_CREDITS = "kuali.academic.record.load.type.credits";
    public static final String LOAD_TYPE_COURSES = "kuali.academic.record.load.type.courses";
    public static final String LOAD_TYPE_CODE_4_TIER = "kuali.academic.record.load.type.code.4.tier";
    // states
    public static final String LOAD_STATE_PRELIMIARY = "kuali.academic.record.load.state.preliminary";
    public static final String LOAD_STATE_FINAL = "kuali.academic.record.load.state.final";
    // Calculation types "LoadLevelType" code
    public static final String LOAD_CALC_CREDITS_INTEGER = "kuali.academic.record.calculation.type.load.credit.integer";
    public static final String LOAD_CALC_CREDITS_BIG_DECIMAL = "kuali.academic.record.calculation.type.load.credit.big.decimal";
    public static final String LOAD_CALC_COURSES_SIMPLE = "kuali.academic.record.calculation.type.load.courses.simple";
    public static final String LOAD_CALC_COURSES_TIERED = "kuali.academic.record.calculation.type.load.courses.tiered";
    public static final String LOAD_CALC_CODE_4_TIERS = "kuali.academic.record.calculation.type.load.code.4.tiers";
    
    // not sure where these "types" should be stored/managed but they are the possible values from the 4 tier code
    // TODO: review these tiers to come up with a scheme that fits most schools OOB
    public static final String LOAD_CODE_4_TIER_1_NO_LOAD = "kuali.academic.record.load.code.4.tier.0.no.load";
    public static final String LOAD_CODE_4_TIER_2_LT_HT = "kuali.academic.record.load.code.4.tier.2.less.than.half.time";
    public static final String LOAD_CODE_4_TIER_3_HT = "kuali.academic.record.load.code.4.tier.3.at.least.half.time";
    public static final String LOAD_CODE_4_TIER_4_FT = "kuali.academic.record.load.code.4.tier.4.full.time";
            
}
