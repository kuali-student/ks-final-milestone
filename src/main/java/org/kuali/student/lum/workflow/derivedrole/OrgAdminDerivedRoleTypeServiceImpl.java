package org.kuali.student.lum.workflow.derivedrole;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;
import org.kuali.student.core.organization.service.OrganizationService;

public class OrgAdminDerivedRoleTypeServiceImpl extends KimDerivedRoleTypeServiceBase {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(OrgAdminDerivedRoleTypeServiceImpl.class);
	
	private OrganizationService orgService;
	/**
	 * This method should grab the orgId from the qualification 
	 * use the org service to find Admin person-org relations (getPersonIdsForOrgByRelationType) 'kuali.org.PersonRelation.AdministrativeOfficer'
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
		validateRequiredAttributesAgainstReceived(qualification);
		List<RoleMembershipInfo> members = new ArrayList<RoleMembershipInfo>();
		
		String orgId = qualification.get("orgId");
		try {
			List<String> principalIds = orgService.getPersonIdsForOrgByRelationType(orgId, "kuali.org.PersonRelation.AdministrativeOfficer");
			for(String principalId:principalIds){
				RoleMembershipInfo member = new RoleMembershipInfo(null/*roleId*/, null, principalId, Role.PRINCIPAL_MEMBER_TYPE, null);
				members.add(member);
			}
		} catch (Exception e) {
			LOG.warn("Error getting Administrators from Org Service for Org:"+orgId+". "+e.getMessage());
		} 
	
		return members;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

}
