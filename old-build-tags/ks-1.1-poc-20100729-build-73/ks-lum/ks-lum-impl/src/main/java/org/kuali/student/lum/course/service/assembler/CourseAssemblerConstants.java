package org.kuali.student.lum.course.service.assembler;

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

	public static final String COURSE_TYPE = "kuali.lu.type.CreditCourse";
		
	public static final String COURSE_FORMAT_TYPE = "kuali.lu.type.CreditCourseFormatShell";
	
	public static final String COURSE_FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
		
	public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";

	public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";

	public static final String COPY_OF_CLU_RELATION_TYPE = "kuali.lu.relation.type.copyOfClu";

	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
	
	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

	// what the service says, but the dictionary says: "kuali.referenceType.CLU";
	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; 
	
	public static final String COURSE_ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

	public static final String COURSE_ACTIVITY_LAB_TYPE 	   = "kuali.lu.type.activity.Lab";
	public static final String COURSE_ACTIVITY_DISCUSSION_TYPE = "kuali.lu.type.activity.Discussion";
	public static final String COURSE_ACTIVITY_TUTORIAL_TYPE   = "kuali.lu.type.activity.Tutorial";
	public static final String COURSE_ACTIVITY_LECTURE_TYPE    = "kuali.lu.type.activity.Lecture";
	public static final String COURSE_ACTIVITY_WEBLECTURE_TYPE = "kuali.lu.type.activity.WebLecture";
	public static final String COURSE_ACTIVITY_WEBDISCUSS_TYPE = "kuali.lu.type.activity.WebDiscussion";
	public static final String COURSE_ACTIVITY_DIRECTED_TYPE   = "kuali.lu.type.activity.Directed";	
	
	// Course Official Identifier
	public static final String COURSE_OFFICIAL_IDENT_TYPE  = "kuali.lu.type.CreditCourse.identifier.official";
	public static final String COURSE_OFFICIAL_IDENT_STATE = "state.field.cluInfo.officialIdentifier.CreditCourse.Official.active";

	public static final String COURSE_CAMPUS_LOCATION_CD_NORTH = "NORTH";
	public static final String COURSE_CAMPUS_LOCATION_CD_SOUTH = "SOUTH";
	
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

	//FIXME ask norm for these values
	public static final String COURSE_CODE_SPECIAL_TOPICS = "kuali.lu.code.specialTopics";
	public static final String COURSE_CODE_PILOT_COURSE = "kuali.lu.code.pilotCourse";

	public static final String COURSE_LO_RELATION_INCLUDES = "kuali.lo.relation.type.includes";
	public static final String COURSE_LO_SEQUENCE = "sequence";
	public static final String COURSE_LO_COURSE_SPECIFIC_RELATION = "cluLuType.default";
	public static final String COURSE_LO_TYPE = "kuali.lo.type.singleUse";
	public static final String COURSE_LO_REPOSITORY_KEY = "kuali.loRepository.key.singleUse";

	public static final String COURSE_FINANCIALS_REVENUE_TYPE = "REVENUE";

}
