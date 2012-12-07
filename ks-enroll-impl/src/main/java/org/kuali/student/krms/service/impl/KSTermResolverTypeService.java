package org.kuali.student.krms.service.impl;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.krms.termresolver.AdminOrgNumberTermResolver;
import org.kuali.student.krms.termresolver.CompletedCourseNumberTermResolver;
import org.kuali.student.krms.termresolver.CompletedCourseTermResolver;
import org.kuali.student.krms.termresolver.FreeTextTermResolver;
import org.kuali.student.krms.termresolver.GPATermResolver;
import org.kuali.student.krms.termresolver.NumberOfCreditsTermResolver;
import org.kuali.student.krms.termresolver.ScoreTermResolver;
import org.kuali.student.krms.util.KSKRMSConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;

public class KSTermResolverTypeService implements TermResolverTypeService {

	AcademicRecordService acadRecordService;
    CourseRegistrationService courseRegistrationService;
    OrganizationService organizationService;
	
	@Override
	public TermResolver<?> loadTermResolver(
			TermResolverDefinition termResolverDefinition) {
		
		if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_COMPLETED_COURSE)) {
			CompletedCourseTermResolver resolver = new CompletedCourseTermResolver();
			resolver.setAcademicRecordService(acadRecordService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_FREE_TEXT)) {
			FreeTextTermResolver resolver = new FreeTextTermResolver();
			//resolver.setAcademicRecordService(acadRecordService); Does not exist
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_GPA)) {
			GPATermResolver resolver = new GPATermResolver();
			resolver.setAcademicRecordService(acadRecordService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_NUMBER_OF_CREDITS)) {
			NumberOfCreditsTermResolver resolver = new NumberOfCreditsTermResolver();
			resolver.setAcademicRecordService(acadRecordService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_SCORE)) {
			ScoreTermResolver resolver = new ScoreTermResolver();
			resolver.setAcademicRecordService(acadRecordService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_ADMIN_ORG_NUMBER)) {
			AdminOrgNumberTermResolver resolver = new AdminOrgNumberTermResolver();
			resolver.setOrganizationService(organizationService);
			return resolver;
		}else if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_COMPLETED_COURSE)) {
			CompletedCourseNumberTermResolver resolver = new CompletedCourseNumberTermResolver();
			resolver.setAcademicRecordService(acadRecordService);
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
