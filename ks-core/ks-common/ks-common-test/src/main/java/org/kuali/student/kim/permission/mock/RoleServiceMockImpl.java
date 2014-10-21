/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.kim.permission.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.delegation.DelegationType;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.core.api.util.jaxb.MapStringStringAdapter;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.common.delegate.DelegateMember;
import org.kuali.rice.kim.api.common.delegate.DelegateType;
import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.rice.kim.api.role.*;
import org.kuali.rice.kim.framework.type.KimTypeService;
import org.kuali.student.common.mock.MockService;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

/**
 * @author nwright
 */
public class RoleServiceMockImpl implements RoleService, MockService {

    private transient Map<String, Role> roleCache = new HashMap<String, Role>();
    private transient Map<String, RoleMembership> roleMembershipCache = new HashMap<String, RoleMembership>();
    private transient Map<String, RoleMember> roleMemberCompleteInfoCache = new HashMap<String, RoleMember>();
    private GroupService groupService;
    private KimTypeService kimTypeInfoService;

    
    @Override
	public void clear() {
    	this.roleCache.clear();
    	this.roleMemberCompleteInfoCache.clear();
    	this.roleMembershipCache.clear();
		
	}

	public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public KimTypeService getKimTypeService() {
        return kimTypeInfoService;
    }

    public void setKimTypeService(KimTypeService kimTypeInfoService) {
        this.kimTypeInfoService = kimTypeInfoService;
    }


    /**
     * Get the KIM Role object with the given ID.
     *
     * If the roleId is blank, this method returns <code>null</code>.
     */
    @Override
    public Role getRole(String roleId) {
        return roleCache.get(roleId);
    }

    /**
     * Get the KIM Role objects for the role IDs in the given List.
     */
    @Override
    public List<Role> getRoles(List<String> roleIds) {
        List<Role> list = new ArrayList<Role>();
        for (String roleId : roleIds) {
            list.add(this.getRole(roleId));
        }
        return list;
    }

    /**
     * Get the KIM Role object with the unique combination of namespace, component,
     * and role name.
     *
     * If any parameter is blank, this method returns <code>null</code>.
     */
    @Override
    public Role getRoleByNamespaceCodeAndName(String namespaceCode, String roleName) {
        for (Role role : this.roleCache.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getName())) {
                    return role;
                }
            }
        }
        return null;
    }

    /**
     * Return the Role ID for the given unique combination of namespace,
     * component and role name.
     */
    @Override
    public String getRoleIdByNamespaceCodeAndName(String namespaceCode, String roleName) {
        for (Role role : this.roleCache.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getName())) {
                    return role.getId();
                }
            }
        }
        return null;
    }

    /**
     * Checks whether the role with the given role ID is active.
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean isRoleActive(String roleId) {
        return this.getRole(roleId).isActive();
    }

    // --------------------
    // Role Membership Checks
    // --------------------
    /**
     * Get all the role members (groups and principals) associated with the given list of roles
     * where their role membership/assignment matches the given qualification.
     *
     * The return object will have each membership relationship along with the delegations
     *
     */
    @Override
    public List<RoleMembership> getRoleMembers(List<String> roleIds, Map<String,String> qualification) {
        List<RoleMembership> list = new ArrayList<RoleMembership>();
        for (RoleMembership info : this.roleMembershipCache.values()) {
            if (roleIds.contains(info.getRoleId())) {
                if (matchesQualifiers(info, qualification)) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    private boolean matchesQualifiers(RoleMembership info, Map<String,String> qualification) {
        // TODO: implement check
        return true;
    }

    private Collection<String> getAllRoleMemberPrincipalIds(String roleId, Map<String,String> qualification) {
        Collection<String> principals = new ArrayList<String>();
        for (RoleMembership info : this.roleMembershipCache.values()) {
            if (roleId.equals(info.getRoleId())) {
                if (matchesQualifiers(info, qualification)) {
                    if (info.getType().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                        principals.add(info.getMemberId());
                    } else if (info.getType().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                        principals.addAll(groupService.getMemberPrincipalIds(info.getMemberId()));
//                    } else if (info.getMemberTypeCode().equals(Role.ROLE_MEMBER_TYPE)) {
//                        principals.addAll(this.getAllRoleMemberPrincipalIds(info.getMemberId(), qualification));
                    }
                }
            }
        }
        return principals;
    }

    /**
     * This method gets all the members, then traverses down into members of type role and group to obtain the nested principal ids
     *
     * @return list of member principal ids
     */
    @Override
    public Collection<String> getRoleMemberPrincipalIds(String namespaceCode, String roleName, Map<String,String> qualification) {
        Role roleInfo = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        if (roleInfo == null) {
            throw new IllegalArgumentException("role name not found");
        }
        return this.getAllRoleMemberPrincipalIds(roleName, qualification);
    }

    private boolean principalHasThisRole(String principalId, String roleId, Map<String,String> qualification) {
        return (this.getAllRoleMemberPrincipalIds(roleId, qualification).contains(principalId));
    }

    /**
     * Returns whether the given principal has any of the passed role IDs with the given qualification.
     */
    @Override
    public boolean principalHasRole(String principalId, List<String> roleIds, Map<String,String> qualification) {
        for (String roleId : roleIds) {
            if (this.principalHasThisRole(principalId, roleId, qualification)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean principalHasRole(String principalId, List<String> roleIds, @XmlJavaTypeAdapter(value = MapStringStringAdapter.class) Map<String, String> qualification, boolean checkDelegations) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Returns the subset of the given principal ID list which has the given role and qualification.
     * This is designed to be used by lookups of people by their roles.
     */
    @Override
    public List<String> getPrincipalIdSubListWithRole(List<String> principalIds,
            String roleNamespaceCode, String roleName, Map<String,String> qualification) {
        List<String> subList = new ArrayList<String>();
        Role role = getRoleByNamespaceCodeAndName(roleNamespaceCode, roleName);
        for (String principalId : principalIds) {
            if (principalHasThisRole(principalId, role.getId(), qualification)) {
                subList.add(principalId);
            }
        }
        return subList;
    }

    @Override
    public RoleQueryResults findRoles(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Gets all direct members of the roles that have ids within the given list
     * of role ids.  This method does not recurse into any nested roles.
     *
     *  <p>The resulting List of role membership will contain membership for
     *  all the roles with the specified ids.  The list is not guaranteed to be
     *  in any particular order and may have membership info for the
     *  different roles interleaved with each other.
     */
    @Override
    public List<RoleMembership> getFirstLevelRoleMembers(List<String> roleIds) {
        List<RoleMembership> list = new ArrayList<RoleMembership>();
        for (RoleMembership membership : this.roleMembershipCache.values()) {
            if (roleIds.contains(membership.getRoleId())) {
                this.roleMembershipCache.remove(membership.getMemberId());
            }
        }
        return list;
    }

    /**
     * Gets role member information based on the given search criteria.  The
     * map of criteria contains attributes of RoleMembership as it's
     * key and the values to search on as the value.
     */
    @Override
    public RoleMemberQueryResults findRoleMembers(QueryByCriteria queryByCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<String> getRoleTypeRoleMemberIds(String roleId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported Yet");
    }


    /**
     *
     * Gets a list of Roles that the given member belongs to.
     *
     */
    @Override
    public List<String> getMemberParentRoleIds(String memberType, String memberId) {
        List<String> list = new ArrayList<String>();
        for (RoleMembership membership : this.roleMembershipCache.values()) {
            if (memberType.equals(membership.getType().getCode())) {
                if (memberId.equals(membership.getMemberId())) {
                    list.add(membership.getRoleId());
                }
            }
        }
        return list;
    }



    @Override
    public RoleMembershipQueryResults findRoleMemberships(QueryByCriteria queryByCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateMemberQueryResults findDelegateMembers(QueryByCriteria queryByCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   

    /**
     * Gets delegation member information based on the given search criteria.  The
     * map of criteria contains attributes of DelegateInfo as it's
     * key and the values to search on as the value.
     */
    @Override
    public List<DelegateMember> getDelegationMembersByDelegationId(String delegationId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateMember getDelegationMemberByDelegationAndMemberId(String delegationId, String memberId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateMember getDelegationMemberById(String delegationMemberId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public List<RoleResponsibility> getRoleResponsibilities(String roleId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public List<RoleResponsibilityAction> getRoleMemberResponsibilityActions(String roleMemberId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateType getDelegateTypeByRoleIdAndDelegateTypeCode(String roleId,
            DelegationType delegateType)  throws RiceIllegalArgumentException{
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateType getDelegateTypeByDelegationId(String delegationId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateType updateDelegateType(DelegateType delegateType) throws RiceIllegalArgumentException, RiceIllegalStateException{
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public RoleMember assignGroupToRole(String groupId, String namespaceCode,
            String roleName, Map<String,String> qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignPermissionToRole(String permissionId, String roleId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleMember assignPrincipalToRole(String principalId, String namespaceCode,
            String roleName, Map<String,String> qualifications)
            throws UnsupportedOperationException {
        Role roleInfo = null;
        for (Role role : this.roleCache.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                roleInfo = role;
                break;
            }
            if (null == roleInfo) {
                String id = UUID.randomUUID().toString();
                String description = roleName + " description";
                String kimTypeId = null;
                Role.Builder bldr = Role.Builder.create(id, roleName, namespaceCode, description, kimTypeId);
                roleInfo = bldr.build();
                this.roleCache.put(roleInfo.getId(), roleInfo);
            }
            RoleMembership roleMembershipInfo = null;
            if (roleName.equals(roleInfo.getName())) {
                for (RoleMembership rmInfo : roleMembershipCache.values()) {
                    if (rmInfo.getRoleId().equals(roleInfo.getId())) {
                        roleMembershipInfo = rmInfo;
                    }
                }
            }
            //if (null == roleMembershipInfo) {
                // roleMembershipInfo = new RoleMembership(roleInfo.getRoleId(), roleMemberId, memberId, memberTypeCode, qualifier)
            //}
            
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleMember assignRoleToRole(String roleId, String namespaceCode,
            String roleName, Map<String,String> qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeGroupFromRole(String groupId, String namespaceCode,
            String roleName, Map<String,String> qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePrincipalFromRole(String principalId, String namespaceCode,
            String roleName,
            Map<String,String> qualifications) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeRoleFromRole(String roleId, String namespaceCode,
            String roleName, Map<String,String> qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleResponsibilityAction createRoleResponsibilityAction(RoleResponsibilityAction roleResponsibilityAction) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleResponsibilityAction updateRoleResponsibilityAction(RoleResponsibilityAction roleResponsibilityAction) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteRoleResponsibilityAction(String s) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleMember createRoleMember(RoleMember roleMember) throws RiceIllegalArgumentException, RiceIllegalStateException {
         throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleMember updateRoleMember(RoleMember roleMember) throws RiceIllegalArgumentException, RiceIllegalStateException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateMember updateDelegateMember(DelegateMember delegateMember) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateMember createDelegateMember(DelegateMember delegateMember) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeDelegateMembers(List<DelegateMember> delegateMembers) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Role createRole(Role role) throws RiceIllegalArgumentException, RiceIllegalStateException {
          throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Role updateRole(Role role) throws RiceIllegalArgumentException, RiceIllegalStateException {
         throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public	List<Map<String, String>> getNestedRoleQualifiersForPrincipalByRoleIds(
            String principalId, List<String> roleIds, Map<String, String> qualification)
            throws RiceIllegalArgumentException{
           throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public  List<Map<String, String>> getRoleQualifersForPrincipalByNamespaceAndRolename(
            String principalId, String namespaceCode, String roleName, Map<String, String> qualification)
            throws RiceIllegalArgumentException {
            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Map<String, String>> getRoleQualifersForPrincipalByRoleIds(String principalId,
            List<String> roleIds, Map<String, String> qualification) throws RiceIllegalArgumentException {
            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public  List<Map<String, String>> getNestedRoleQualifersForPrincipalByNamespaceAndRolename(
            String principalId, String namespaceCode,
            String roleName,  Map<String, String> qualification)
            throws RiceIllegalArgumentException {
            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateType createDelegateType(DelegateType delegateType) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void revokePermissionFromRole(String permissionId, String roleId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDerivedRole(String s) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDynamicRoleMembership(String s) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

