/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.util.*;

public class OrgDerivedRoleTypeServiceImpl extends DerivedRoleTypeServiceBase {

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
	 * See DerivedRoleTypeServiceBase
	 */
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase#getRoleMembersFromDerivedRole(java.lang.String, java.lang.String, java.util.Map<String,String>)
	 */
    @Override
    public List<RoleMembership> getRoleMembersFromDerivedRole(
			String namespaceCode, String roleName, Map<String,String> qualification) {
		if (null == orgService) {
		   	orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
		}

		validateRequiredAttributesAgainstReceived(qualification);
		List<RoleMembership> members = new ArrayList<RoleMembership>();

		String orgId = qualification.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
//		String org = qualification.get(KualiStudentKimAttributes.QUALIFICATION_ORG);
		if (LOG.isDebugEnabled()) {
    		LOG.debug("Using Org Values:");
    		LOG.debug("------ Org ID: " + orgId);
//    		LOG.debug("------ Org Short Name: " + org);
    	}
		if (StringUtils.isEmpty(orgId)) {
		    throw new RuntimeException("No valid qualifier value found for key: " + KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		//Put the org name into the attribute set
		Map<String,String> attributes = new LinkedHashMap<String,String>();
//		attributes.put(KualiStudentKimAttributes.QUALIFICATION_ORG, org);

		try {
			//If the includedOrgPersonRelationType is set, restrict members to that relationship type
			if(includedOrgPersonRelationTypes!=null){
				for(String orgPersonRelationType:includedOrgPersonRelationTypes){
					List<String> principalIds = new ArrayList<String>();
					List<OrgPersonRelationInfo> orgPersonRelationInfos = orgService.getOrgPersonRelationsByTypeAndOrg(orgPersonRelationType, orgId, ContextUtils.getContextInfo());
					for (OrgPersonRelationInfo orgPersonRelationInfo : orgPersonRelationInfos) {
					    principalIds.add(orgPersonRelationInfo.getPersonId());
                    }
					for(String principalId:principalIds){
						RoleMembership member = RoleMembership.Builder.create(null/*roleId*/, null, principalId, KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, attributes).build();
						members.add(member);
					}
				}
			//Otherwise get all members of the organization except for the excluded
			}else{
			    //getCurrent Date
			    Date now = new Date();
				List<OrgPersonRelationInfo> relations = null;
				relations = orgService.getOrgPersonRelationsByOrg(orgId, ContextUtils.getContextInfo());
				for(OrgPersonRelationInfo relation:relations){
					if(excludedOrgPersonRelationTypes==null||!excludedOrgPersonRelationTypes.contains(relation.getTypeKey())){
					    //Add role membership only for memberships that are valid meaning expiration date is greater than or equal to current date.
					    if(relation.getExpirationDate()!=null){
					        if(relation.getExpirationDate().compareTo(now)>=0){
					            RoleMembership member = RoleMembership.Builder.create(null/*roleId*/, null, relation.getPersonId(), KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, attributes).build();
					            members.add(member);
					        }
					    }
					    else{
                            RoleMembership member = RoleMembership.Builder.create(null/*roleId*/, null, relation.getPersonId(), KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, attributes).build();
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
