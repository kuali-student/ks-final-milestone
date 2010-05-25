package org.kuali.student.lum.course.service.assembler;

public class CourseAssemblerConstants {

	public static final String COURSE_FORMAT_RELATION_TYPE = "kuali.lu.type.CreditCourseFormatShell";

	public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";

	public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";

	public static final String COPY_OF_CLU_RELATION_TYPE = "kuali.lu.relation.type.copyOfClu";

	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
	
	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
	
	public static final String COURSE_ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";	
}
