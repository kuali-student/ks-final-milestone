package org.kuali.student.common.util.krms;

import org.kuali.rice.krms.api.engine.Term;

@SuppressWarnings("unused")
public class RulesExecutionConstants {

    private RulesExecutionConstants() {}

    private static final String personId = "kuali.term.prerequisite.personId";
    private static final String contextInfo = "kuali.term.prerequisite.contextInfo";

    /**
     * Rule execution related Term's  
     */
    public static final Term STUDENT_DECEASED_DATE_TERM = new Term("studentDeceasedDate");
    public static final Term COURSE_ID_TO_ENROLL_TERM = new Term("courseIdToEnroll");
    public static final Term COURSE_SET_TERM = new Term("courseSet");
    public static final Term GPA_FOR_COURSE_TERM = new Term("gpaForCourse");
    public static final Term GRADES_FOR_COURSE_SET_TERM = new Term("gradesForCourseSet");
    public static final Term STUDENT_COMPLETED_COURSE_IDS_TERM = new Term("studentCompletedCourseIds");
    public static final Term STUDENT_ENROLLED_COURSE_IDS_TERM = new Term("studentEnrolledCourseIds");
    public static final Term TEST_SET_SCORE_TERM = new Term("testSetScore");
    public static final Term COMPLETED_CREDITS_FOR_COURSE_SET_TERM = new Term("creditsForCourseSet");
    public static final Term REGISTRATION_TERM_TERM = new Term("registrationTerm");
    public static final Term SUMMER_ONLY_STUDENT_TERM = new Term("summerOnlyStudent");
    public static final Term MILESTONES_BY_TYPE_TERM = new Term("milestonesByType");
    public static final Term MILESTONE_TERM = new Term("milestone");
    public static final Term STUDENT_REGISTRATION_HOLDS_TERM = new Term("studentRegistrationHolds");
    public static final Term ORG_PERMISSION_TERM = new Term("orgPermission");
    public static final Term ADMIN_ORG_NUMBER_TERM = new Term("adminOrgNumber");
    public static final Term NR_OF_COMPLETED_COURSES_TERM = new Term("numberOfCompletedCourses");
    public static final Term COMPLETED_COURSE_TERM = new Term("completedCourse");
    // data
    public static final Term PROCESS_KEY_TERM = new Term("processKey");
    public static final Term CONTEXT_INFO_TERM = new Term(contextInfo);
    public static final Term PERSON_ID_TERM = new Term(personId);
    public static final Term REG_GROUP_ID_TERM = new Term("registrationGroupId");
    public static final Term REGISTRATION_REQUEST_ID_TERM = new Term("registrationRequestId");
    public static final Term SIMULATED_REGISTRATIONS_TERM = new Term("simulatedRegistrations");
    public static final Term EXISTING_REGISTRATIONS_TERM = new Term("existingRegistrations");
    public static final Term EXISTING_WAITLISTED_REGISTRATIONS_TERM = new Term("existingWaitlistedRegistrations");
    public static final Term REGISTRATION_REQUEST_ITEM_TERM = new Term("registrationRequestItem");
    public static final Term REGISTRATION_REQUEST_ITEM_ID_TERM = new Term("registrationRequestItemId");
    public static final Term REGISTRATION_REQUEST_ITEM_OK_TO_REPEAT_TERM = new Term("registrationRequestItemOkToRepeat");
    public static final Term REGISTRATION_REQUEST_TERM = new Term("registrationRequest");
    public static final Term ATP_ID_TERM = new Term("atpId");
    public static final Term ATP_TERM = new Term("atp");
    public static final Term AS_OF_DATE_TERM = new Term("asOfDate");
    public static final Term RECORD_INSTRUCTION_SUCCESSES_TERM = new Term("shouldRecordInstructionSuccesses");
    public static final Term REGISTRATION_GROUP_TERM = new Term("registrationGroup");
    public static final Term CLU_ID_TERM = new Term("cluId");
    public static final Term CLU_INFO_TERM = new Term("cluInfo");
    public static final Term CLU_VERSION_IND_ID_TERM = new Term("cluVersionIndId");
    public static final Term TOTAL_COURSE_ATTEMPTS_TERM = new Term("totalCourseAttempts");
    public static final Term MAX_REPEATABILITY_TERM = new Term("maxRepeatability");
    public static final Term WAITLISTED_ATTEMPTS_TERM = new Term("waitlistedAttempts");
    public static final Term SIMULATED_ATTEMPTS_TERM = new Term("simulatedAttempts");
    public static final Term USER_ACTION_DATE_TERM = new Term("userActionDate");
    public static final Term SCHEDULE_ADJUSTMENT_START_TERM = new Term("scheduleAdjustmentStart");
    public static final Term SCHEDULE_ADJUSTMENT_END_TERM = new Term("scheduleAdjustmentEnd");
    public static final Term DROP_PERIOD_END_TERM = new Term("dropPeriodEnd");
    public static final Term MAX_CREDITS_TERM = new Term("maxCredits");
    public static final Term COURSE_ADD_DATES_TERM = new Term("courseAddDates");
    // services
    public static final Term KRMS_EVALUATOR_TERM = new Term("krmsEvaluator");
    public static final Term ACADEMIC_RECORD_SERVICE_TERM = new Term("academicRecordService");
    public static final Term CLU_SERVICE = new Term("cluService");
    public static final Term LUI_SERVICE = new Term("luiService");
    public static final Term PROCESS_SERVICE_TERM = new Term("processService");
    public static final Term IDENTITY_SERVICE_TERM = new Term("identityService");
    public static final Term ATP_SERVICE_TERM = new Term("atpService");
    public static final Term EXEMPTION_SERVICE_TERM = new Term("exemptionService");
    public static final Term COURSE_REGISTRATION_SERVICE_TERM = new Term("courseRegistrationService");
    public static final Term COURSE_WAIT_LIST_SERVICE_TERM = new Term("courseWaitListService");
    public static final Term COURSE_OFFERING_SERVICE_TERM = new Term("courseOfferingService");
    public static final Term SCHEDULING_SERVICE_TERM = new Term("schedulingService");
    public static final Term COURSE_SERVICE_TERM = new Term("courseService");
    public static final Term POPULATION_SERVICE_TERM = new Term("populationService");
    public static final Term HOLD_SERVICE_TERM = new Term("holdService");
    public static final Term GES_SERVICE_TERM = new Term("gesService");
    public static final Term REQUISITES_SERVICE_TERM = new Term("requisitesService");
    // factories
    public static final Term RULE_FACTORY_TERM = new Term("ruleFactory");
    public static final Term PROPOSITION_FACTORY_TERM = new Term("propositionFactory");
    public static final Term LOAD_CALCULATOR_RULE_FACTORY_TERM = new Term("loadCalculatorRuleFactory");
    // logic
    public static final Term REGISTRATION_REQUEST_MERGER_TERM = new Term("registrationRequestMerger");
    /**
     * properties
     */
    public static final Term COURSE_SET_ID_TERM = new Term("courseSetIdProperty");
    public static final Term COURSE_ID_TERM = new Term("courseIdProperty");
    public static final Term TEST_SET_ID_TERM = new Term("testSetIdProperty");
    public static final Term MILESTONE_TYPE_TERM = new Term("milestoneTypeProperty");
    public static final Term MILESTONE_ATP_ID_TERM = new Term("milestoneAtpKeyProperty");
    public static final Term MILESTONE_ID_TERM = new Term("milestoneIdProperty");
    public static final Term ISSUE_KEY_TERM = new Term("issueKeyProperty");
    public static final Term REQUIRED_COURSES_TERM = new Term("requiredCourses");
    public static final Term ORGANIZATION_ID_TERM = new Term("organizationId");
    public static final Term ORG_TYPE_KEY_TERM = new Term("orgTypeKey");
    public static final Term STATEMENT_EVENT_NAME = new Term("statementEvent");
    public static final Term PROCESS_EVENT_NAME = new Term("processEvent");
    //
    // something to do with hold warnings
    public static final String REGISTRATION_HOLD_WARNINGS_ATTRIBUTE = "registrationHoldWarnings";
    // these are used as qualifiers for selecting agenda!?!? 
    public static final String DOCTYPE_CONTEXT_QUALIFIER = "docTypeName";
    public static final String STUDENT_ELIGIBILITY_DOCTYPE = "Student.Eligibility";
    // used to tag exceptions
    public static final String PROCESS_EVALUATION_EXCEPTION = "processEvaluationException";
    public static final String PROCESS_EVALUATION_RESULTS = "processEvaluationResults";
}
