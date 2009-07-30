package org.kuali.student.lum.workflow.derivedrole;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;

public class OrgDerivedRoleTypeServiceImpl extends KimDerivedRoleTypeServiceBase {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(OrgDerivedRoleTypeServiceImpl.class);
	
	private OrganizationService orgService;
	private String orgPersonRelationType=null;
	
	/**
	 * This method should grab the orgId from the qualification 
	 * use the org service to find person-org relations (getPersonIdsForOrgByRelationType)
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
			//If the orgPersonRelationType is set, restrict members to that relationship type
			if(orgPersonRelationType!=null){
				List<String> principalIds = orgService.getPersonIdsForOrgByRelationType(orgId, orgPersonRelationType);
				for(String principalId:principalIds){
					RoleMembershipInfo member = new RoleMembershipInfo(null/*roleId*/, null, principalId, Role.PRINCIPAL_MEMBER_TYPE, null);
					members.add(member);
				}
			//Otherwise get all members of the organization
			}else{
				List<OrgPersonRelationInfo> principalIds = orgService.getAllOrgPersonRelationsByOrg(orgId);
				for(OrgPersonRelationInfo principalId:principalIds){
					RoleMembershipInfo member = new RoleMembershipInfo(null/*roleId*/, null, principalId.getPersonId(), Role.PRINCIPAL_MEMBER_TYPE, null);
					members.add(member);
				}
			}
		} catch (Exception e) {
			LOG.warn("Error getting relations from Org Service for Org:"+orgId+". "+e.getMessage());
		} 
	
		return members;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

	public String getOrgPersonRelationType() {
		return orgPersonRelationType;
	}

	public void setOrgPersonRelationType(String orgPersonRelationType) {
		this.orgPersonRelationType = orgPersonRelationType;
	}

}
