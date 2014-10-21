package org.kuali.student.r2.lum.course.service.assembler;

/**
 *
 * Catalog of Course Service Constants.
 *
 * All type keys are mapped from
 *
 * https://test.kuali.org/confluence/display/KULSTG/Kuali+Student+Types+Used+in+the+Dictionary
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CourseAssemblerConstants {

	public static final String COURSE_FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";

	public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";

	public static final String COPY_OF_CLU_RELATION_TYPE = "kuali.lu.relation.type.copyOfClu";

	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";

	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

	// what the service says, but the dictionary says: "kuali.referenceType.CLU";
	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu";

	public static final String COURSE_ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

	// Course Official Identifier
	public static final String COURSE_OFFICIAL_IDENT_TYPE  = "kuali.lu.type.CreditCourse.identifier.official";

    // Format Official Identifier
    public static final String COURSE_FORMAT_OFFICIAL_IDENT_TYPE = "kuali.lu.type.CreditCourseFormatShell.identifier";

	public static final String COURSE_CAMPUS_LOCATION_CD_NORTH = "NO";
	public static final String COURSE_CAMPUS_LOCATION_CD_SOUTH = "SO";

	// Course Variation Identifier
	public static final String COURSE_VARIATION_IDENT_TYPE  = "kuali.lu.type.CreditCourse.identifier.variation";

	// Course CrossListing Identifier
	public static final String COURSE_CROSSLISTING_IDENT_TYPE = "kuali.lu.type.CreditCourse.identifier.crosslisting";

	//Learning result Constants
	//FIXME follow Norm's Type Keys (this requires impex changes)
//	public final static String COURSE_RESULT_TYPE_GRADE   = "kuali.resultType.grades";
//	public final static String COURSE_RESULT_TYPE_CREDITS = "kuali.resultType.credits";
	public final static String COURSE_RESULT_TYPE_GRADE   = "kuali.resultType.gradeCourseResult";
	public final static String COURSE_RESULT_TYPE_CREDITS = "kuali.resultType.creditCourseResult";
	public static final String COURSE_RESULT_COMP_GRADE_PASSFAIL = "kuali.resultComponent.grade.passFail";
	public static final String COURSE_RESULT_COMP_GRADE_AUDIT = "kuali.resultComponent.grade.audit";
	public static final String COURSE_RESULT_COMP_ATTR_PASSFAIL = "passFail";
	public static final String COURSE_RESULT_COMP_ATTR_AUDIT = "audit";
	public static final String COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE = "fixedCreditValue";
	public static final String COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE = "minCreditValue";
	public static final String COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE = "maxCreditValue";
    public static final String COURSE_RESULT_COMP_ATTR_CREDIT_VALUE_INCR = "creditValueIncrement";
	public static final String COURSE_RESULT_COMP_CREDIT_PREFIX = "kuali.creditType.credit.degree.";

	//FIXME ask norm for these values
	public static final String COURSE_CODE_SPECIAL_TOPICS = "kuali.lu.code.specialTopics";
	public static final String COURSE_CODE_PILOT_COURSE = "kuali.lu.code.pilotCourse";


	//Constants for AdminOrg types in Course
    public static final String ADMIN_ORG = "kuali.adminOrg.type.Administration";
    public static final String SUBJECT_ORG = "kuali.adminOrg.type.CurriculumOversight";

	public static final String COURSE_LO_RELATION_INCLUDES = "kuali.lo.relation.type.includes";
	public static final String COURSE_LO_SEQUENCE = "sequence";
	public static final String COURSE_LO_COURSE_SPECIFIC_RELATION = "kuali.lu.lo.relation.type.includes";
	public static final String COURSE_LO_TYPE = "kuali.lo.type.singleUse";
	public static final String COURSE_LO_REPOSITORY_KEY = "kuali.loRepository.key.singleUse";
	public static final String COURSE_FINANCIALS_REVENUE_TYPE = "REVENUE";

	// FIXME ask norm about this value
	public static final String COURSE_REFERENCE_TYPE = "kuali.referenceType.CLU";

	// State
	public static final String ACTIVE = "Active";
}
