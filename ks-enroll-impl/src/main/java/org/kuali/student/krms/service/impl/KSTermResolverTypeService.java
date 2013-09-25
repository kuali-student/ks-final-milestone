package org.kuali.student.krms.service.impl;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.krms.termresolver.AdminOrgPermissionTermResolver;
import org.kuali.student.krms.termresolver.CreditsEarnedFromCoursesTermResolver;
import org.kuali.student.krms.termresolver.FreeFormTextTermResolver;
import org.kuali.student.krms.termresolver.NumberOfCompletedCoursesTermResolver;
import org.kuali.student.krms.termresolver.CompletedCourseTermResolver;
import org.kuali.student.krms.termresolver.GPATermResolver;
import org.kuali.student.krms.termresolver.ScoreTermResolver;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;

public class KSTermResolverTypeService implements TermResolverTypeService {

	AcademicRecordService acadRecordService;
    CourseRegistrationService courseRegistrationService;
    OrganizationService organizationService;
	
	@Override
	public TermResolver<?> loadTermResolver(
			TermResolverDefinition termResolverDefinition) {
		
		if (termResolverDefinition.getName().equals(KSKRMSServiceConstants.TERM_SPEC_COMPLETEDCOURSE)) {
			CompletedCourseTermResolver resolver = new CompletedCourseTermResolver();
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSServiceConstants.TERM_SPEC_FREEFORMTEXT)) {
			FreeFormTextTermResolver resolverForm = new FreeFormTextTermResolver();
			return resolverForm;
		}else if (termResolverDefinition.getName().equals(KSKRMSServiceConstants.TERM_SPEC_GPAFORCOURSES)) {
			GPATermResolver resolver = new GPATermResolver();
			resolver.setAcademicRecordService(acadRecordService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSServiceConstants.TERM_SPEC_NUMBEROFCREDITS)) {
			CreditsEarnedFromCoursesTermResolver resolver = new CreditsEarnedFromCoursesTermResolver();
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSServiceConstants.TERM_SPEC_SCOREONTEST)) {
			ScoreTermResolver resolver = new ScoreTermResolver();
			resolver.setAcademicRecordService(acadRecordService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSServiceConstants.TERM_SPEC_ADMINORGANIZATIONPERMISSIONREQUIRED)) {
			AdminOrgPermissionTermResolver resolver = new AdminOrgPermissionTermResolver();
			resolver.setOrganizationService(organizationService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSServiceConstants.TERM_SPEC_NUMBEROFCOMPLETEDCOURSES)) {
			NumberOfCompletedCoursesTermResolver resolver = new NumberOfCompletedCoursesTermResolver();
			return resolver;
		}
		return null;
	}
	
	public AcademicRecordService getAcadRecordService() {
		return acadRecordService;
	}
	public void setAcademicRecordService(AcademicRecordService acadRecordService) {
		this.acadRecordService = acadRecordService;
	}
	
	public CourseRegistrationService getCourseRegistrationService() {
		return courseRegistrationService;
	}
	
	public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
		this.courseRegistrationService = courseRegistrationService;
	}

}
