package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.krms.util.KSKRMSConstants;

public class KSTermResolverTypeService implements TermResolverTypeService {

	AcademicRecordService acadRecordService;
	CourseRegistrationService courseRegistrationService;
	
	@Override
	public TermResolver<?> loadTermResolver(
			TermResolverDefinition termResolverDefinition) {
		
		if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_RESOLVER_COURSE)) {
			CompletedCourseTermResolver resolver = new CompletedCourseTermResolver();
			resolver.setAcadRecordService(acadRecordService);
			return resolver;	
		} else if (termResolverDefinition.getName().equals("xyz")) {
			// TODO implement the Term resolvers by instansiating it and setting the required services...
		}
		return null;
	}
	
	public AcademicRecordService getAcadRecordService() {
		return acadRecordService;
	}
	public void setAcadRecordService(AcademicRecordService acadRecordService) {
		this.acadRecordService = acadRecordService;
	}
	
	public CourseRegistrationService getCourseRegistrationService() {
		return courseRegistrationService;
	}
	
	public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
		this.courseRegistrationService = courseRegistrationService;
	}

}
