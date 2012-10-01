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
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Dummy class to test Hierarchy Node Test
 *
 * @author delyea
 *
 */
public class HierarchyNodeTestRoleTypeServiceBase extends DerivedRoleTypeServiceBase {

    /* (non-Javadoc)
     * @see org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase#getRoleMembersFromDerivedRole(java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.Map<String,String>)
     */
    @Override
    public List<RoleMembership> getRoleMembersFromDerivedRole(String namespaceCode, String roleName, Map<String, String> qualification) {
        List<RoleMembership> roleMembers = new ArrayList<RoleMembership>();
        roleMembers.add(RoleMembership.Builder.create(null, null, "eric", KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, null).build());
        roleMembers.add(RoleMembership.Builder.create(null, null, "fran", KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, null).build());
        roleMembers.add(RoleMembership.Builder.create(null, null, "user1", KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, null).build());
        roleMembers.add(RoleMembership.Builder.create(null, null, "user4", KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, null).build());
        return roleMembers;
    }

    /* (non-Javadoc)
     * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#sortRoleMembers(java.util.List)
     */
  //  @Override
    public List<RoleMembership> sortRoleMembers(List<RoleMembership> roleMembers) {
        List<RoleMembership> sortedRoleMembers = new ArrayList<RoleMembership>();
        int group = 0; // counter for the group number to add to the roleSortingCode
        for (RoleMembership roleMembership : roleMembers) {
            RoleMembership.Builder bldr = RoleMembership.Builder.create(roleMembership);
            bldr.setRoleSortingCode(StringUtils.leftPad(Integer.toString(group), 3, '0'));
            sortedRoleMembers.add(bldr.build());
            group++;
        }
        return sortedRoleMembers;
    }
}
