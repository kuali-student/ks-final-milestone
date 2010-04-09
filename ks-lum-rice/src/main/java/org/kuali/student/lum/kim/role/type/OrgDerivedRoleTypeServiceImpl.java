/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.kim.role.type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;

public class OrgDerivedRoleTypeServiceImpl extends KimDerivedRoleTypeServiceBase {
	
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(OrgDerivedRoleTypeServiceImpl.class);
	
	private OrganizationService orgService;
	private List<String> includedOrgPersonRelationTypes = null;
	private List<String> excludedOrgPersonRelationTypes = null;
	
	
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
		if (null == orgService) {
		   	orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
		}
		
		validateRequiredAttributesAgainstReceived(qualification);
		List<RoleMembershipInfo> members = new ArrayList<RoleMembershipInfo>();
		
		String orgId = qualification.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		String org = qualification.get(KualiStudentKimAttributes.QUALIFICATION_ORG);
		
		//Put the org name into the attribute set
		AttributeSet attributes = new AttributeSet();
		attributes.put(KualiStudentKimAttributes.QUALIFICATION_ORG, org);

		try {
			//If the includedOrgPersonRelationType is set, restrict members to that relationship type
			if(includedOrgPersonRelationTypes!=null){
				for(String orgPersonRelationType:includedOrgPersonRelationTypes){
					List<String> principalIds = orgService.getPersonIdsForOrgByRelationType(orgId, orgPersonRelationType);
					for(String principalId:principalIds){
						RoleMembershipInfo member = new RoleMembershipInfo(null/*roleId*/, null, principalId, Role.PRINCIPAL_MEMBER_TYPE, attributes);
						members.add(member);
					}
				}
			//Otherwise get all members of the organization except for the excluded
			}else{
			    //getCurrent Date
			    Date now = new Date();
				List<OrgPersonRelationInfo> relations = orgService.getAllOrgPersonRelationsByOrg(orgId);
				for(OrgPersonRelationInfo relation:relations){
					if(excludedOrgPersonRelationTypes==null||!excludedOrgPersonRelationTypes.contains(relation.getType())){
					    //Add role membership only for memberships that are valid meaning expiration date is greater than or equal to current date.
					    if(relation.getExpirationDate().compareTo(now)>=0){
						RoleMembershipInfo member = new RoleMembershipInfo(null/*roleId*/, null, relation.getPersonId(), Role.PRINCIPAL_MEMBER_TYPE, attributes);
						members.add(member);
					    }
					}
				}
			}
		} catch (Exception e) {
			LOG.warn("Error getting relations from Org Service for Org:"+orgId+". ",e);
		} 
	
		return members;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

	public List<String> getIncludedOrgPersonRelationTypes() {
		return includedOrgPersonRelationTypes;
	}

	public void setIncludedOrgPersonRelationTypes(
			List<String> includedOrgPersonRelationTypes) {
		this.includedOrgPersonRelationTypes = includedOrgPersonRelationTypes;
	}

	public List<String> getExcludedOrgPersonRelationTypes() {
		return excludedOrgPersonRelationTypes;
	}

	public void setExcludedOrgPersonRelationTypes(
			List<String> excludedOrgPersonRelationTypes) {
		this.excludedOrgPersonRelationTypes = excludedOrgPersonRelationTypes;
	}



}
