package org.kuali.student.lum.workflow.derivedrole;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;
import org.kuali.student.core.organization.service.OrganizationService;

public class OrgAdminDerivedRoleTypeServiceImpl extends KimDerivedRoleTypeServiceBase {

	private OrganizationService orgService;
	/**
	 * This method should grab the orgId from the qualification 
	 * use the org service to find Admin person-org relations (getPersonIdsForOrgByRelationType) 'kuali.org.PersonRelation.AdminMember'
	 * return the members.
	 * 
	 * See KimDerivedRoleTypeServiceBase
	 */
	/* (non-Javadoc)
	 * @see org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase#getRoleMembersFromApplicationRole(java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public List<RoleMembershipInfo> getRoleMembersFromApplicationRole(
			String namespaceCode, String roleName, AttributeSet qualification) {
		// TODO Auto-generated method stub
		

		validateRequiredAttributesAgainstReceived(qualification);
		

		List<RoleMembershipInfo> members = new ArrayList<RoleMembershipInfo>();
		
		return members;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

}
