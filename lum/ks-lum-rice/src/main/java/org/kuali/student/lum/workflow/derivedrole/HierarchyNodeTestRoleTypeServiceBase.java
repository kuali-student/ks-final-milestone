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
package org.kuali.student.lum.workflow.derivedrole;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;

/**
 * Dummy class to test Hierarchy Node Test
 * 
 * @author delyea
 *
 */
public class HierarchyNodeTestRoleTypeServiceBase extends KimDerivedRoleTypeServiceBase {

	/* (non-Javadoc)
	 * @see org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase#getRoleMembersFromApplicationRole(java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public List<RoleMembershipInfo> getRoleMembersFromApplicationRole(String namespaceCode, String roleName, AttributeSet qualification) {
		List<RoleMembershipInfo> roleMembers = new ArrayList<RoleMembershipInfo>();
		roleMembers.add(new RoleMembershipInfo(null,null,"eric",Role.PRINCIPAL_MEMBER_TYPE,null));
		roleMembers.add(new RoleMembershipInfo(null,null,"fran",Role.PRINCIPAL_MEMBER_TYPE,null));
		roleMembers.add(new RoleMembershipInfo(null,null,"user1",Role.PRINCIPAL_MEMBER_TYPE,null));
		roleMembers.add(new RoleMembershipInfo(null,null,"user4",Role.PRINCIPAL_MEMBER_TYPE,null));
		return roleMembers;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#sortRoleMembers(java.util.List)
	 */
	@Override
	public List<RoleMembershipInfo> sortRoleMembers(List<RoleMembershipInfo> roleMembers) {
		List<RoleMembershipInfo> sortedRoleMembers = new ArrayList<RoleMembershipInfo>();
        int group = 0; // counter for the group number to add to the roleSortingCode
		for (RoleMembershipInfo roleMembershipInfo : roleMembers) {
			roleMembershipInfo.setRoleSortingCode( StringUtils.leftPad(Integer.toString(group), 3, '0') );
			sortedRoleMembers.add(roleMembershipInfo);
			group++;
		}
		return sortedRoleMembers;
	}

}
