package org.kuali.student.lum.kim.role.type;


public class KSAdhocActionRequestDerivedRoleTypeServiceImpl extends	KSActionRequestDerivedRoleTypeServiceImpl {

	@Override
	protected REQUESTS_TYPES_TO_CHECK getRequestTypesToCheck() {
		return REQUESTS_TYPES_TO_CHECK.ADHOC_ONLY;
	}

}
