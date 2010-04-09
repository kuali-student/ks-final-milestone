package org.kuali.student.lum.kim.role.type;


public class KSNonAdhocActionRequestDerivedRoleTypeServiceImpl extends KSActionRequestDerivedRoleTypeServiceImpl {

	@Override
	protected REQUESTS_TYPES_TO_CHECK getRequestTypesToCheck() {
		return REQUESTS_TYPES_TO_CHECK.NON_ADHOC_ONLY;
	}

}
