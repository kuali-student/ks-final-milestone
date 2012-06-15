package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.student.krms.util.KSKRMSConstants;

public class KSTermResolverTypeService implements TermResolverTypeService {

	@Override
	public TermResolver<?> loadTermResolver(
			TermResolverDefinition termResolverDefinition) {
		
		if (termResolverDefinition.getName().equals(KSKRMSConstants.TERM_SPEC_COURSE)) {
			CompletedCourseTermResolver resolver = new CompletedCourseTermResolver();
//			resolver.setAcadRecordService(acadRecordService);
			return new CompletedCourseTermResolver();	
		}
		// TODO Auto-generated method stub
		return null;
	}

}
