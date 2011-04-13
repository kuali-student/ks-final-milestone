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

import java.util.*;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.DelegateMemberCompleteInfo;
import org.kuali.rice.kim.bo.role.dto.DelegateTypeInfo;
import org.kuali.rice.kim.bo.role.dto.KimRoleInfo;
import org.kuali.rice.kim.bo.role.dto.RoleMemberCompleteInfo;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.role.dto.RoleResponsibilityActionInfo;
import org.kuali.rice.kim.bo.role.dto.RoleResponsibilityInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.GroupService;
import org.kuali.rice.kim.service.GroupUpdateService;
import org.kuali.rice.kim.service.KimTypeInfoService;
import org.kuali.rice.kim.service.RoleService;
import org.kuali.rice.kim.service.RoleUpdateService;

/**
 * @author nwright
 */
public class RoleServiceMockImpl implements
        RoleService,
        RoleUpdateService {

    private transient Map<String, KimRoleInfo> roleCache = new HashMap<String, KimRoleInfo>();
    private transient Map<String, RoleMembershipInfo> roleMembershipCache = new HashMap<String, RoleMembershipInfo>();
    private transient Map<String, RoleMemberCompleteInfo> roleMemberCompleteInfoCache = new HashMap<String, RoleMemberCompleteInfo>();
    private GroupService groupService;
    private GroupUpdateService groupUpdateService;
    private KimTypeInfoService kimTypeInfoService;

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public KimTypeInfoService getKimTypeInfoService() {
        return kimTypeInfoService;
    }

    public void setKimTypeInfoService(KimTypeInfoService kimTypeInfoService) {
        this.kimTypeInfoService = kimTypeInfoService;
    }

    /**
     * Get the KIM Role object with the given ID.
     *
     * If the roleId is blank, this method returns <code>null</code>.
     */
    @Override
    public KimRoleInfo getRole(String roleId) {
        return roleCache.get(roleId);
    }

    /**
     * Get the KIM Role objects for the role IDs in the given List.
     */
    @Override
    public List<KimRoleInfo> getRoles(List<String> roleIds) {
        List<KimRoleInfo> list = new ArrayList<KimRoleInfo>();
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
    public KimRoleInfo getRoleByName(String namespaceCode, String roleName) {
        for (KimRoleInfo role : this.roleCache.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getRoleName())) {
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
    public String getRoleIdByName(String namespaceCode, String roleName) {
        for (KimRoleInfo role : this.roleCache.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getRoleName())) {
                    return role.getRoleId();
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

    /**
     * Returns a list of role qualifiers that the given principal has without taking into consideration
     * that the principal may be a member via an assigned group or role.  Use in situations where
     * you are only interested in the qualifiers that are directly assigned to the principal.
     */
    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipal(String principalId, List<String> roleIds, AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Returns a list of role qualifiers that the given principal has without taking into consideration
     * that the principal may be a member via an assigned group or role.  Use in situations where
     * you are only interested in the qualifiers that are directly assigned to the principal.
     */
    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipal(String principalId, String namespaceCode, String roleName, AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Returns a list of role qualifiers that the given principal.  If the principal's membership
     * is via a group or role, that group or role's qualifier on the given role is returned.
     */
    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipalIncludingNested(String principalId, String namespaceCode, String roleName, AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Returns a list of role qualifiers that the given principal.  If the principal's membership
     * is via a group or role, that group or role's qualifier on the given role is returned.
     */
    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipalIncludingNested(String principalId, List<String> roleIds, AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported Yet");
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
    public List<RoleMembershipInfo> getRoleMembers(List<String> roleIds, AttributeSet qualification) {
        List<RoleMembershipInfo> list = new ArrayList<RoleMembershipInfo>();
        for (RoleMembershipInfo info : this.roleMembershipCache.values()) {
            if (roleIds.contains(info.getRoleId())) {
                if (matchesQualifiers(info, qualification)) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    private boolean matchesQualifiers(RoleMembershipInfo info, AttributeSet qualification) {
        // TODO: implement check
        return true;
    }

    private Collection<String> getAllRoleMemberPrincipalIds(String roleId, AttributeSet qualification) {
        Collection<String> principals = new ArrayList<String>();
        for (RoleMembershipInfo info : this.roleMembershipCache.values()) {
            if (roleId.equals(info.getRoleId())) {
                if (matchesQualifiers(info, qualification)) {
                    if (info.getMemberTypeCode().equals(Role.PRINCIPAL_MEMBER_TYPE)) {
                        principals.add(info.getMemberId());
                    } else if (info.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
                        principals.addAll(groupService.getMemberPrincipalIds(info.getMemberId()));
                    } else if (info.getMemberTypeCode().equals(Role.ROLE_MEMBER_TYPE)) {
                        principals.addAll(this.getAllRoleMemberPrincipalIds(info.getMemberId(), qualification));
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
    public Collection<String> getRoleMemberPrincipalIds(String namespaceCode, String roleName, AttributeSet qualification) {
        KimRoleInfo roleInfo = this.getRoleByName(namespaceCode, roleName);
        if (roleInfo == null) {
            throw new IllegalArgumentException("role name not found");
        }
        return this.getAllRoleMemberPrincipalIds(roleName, qualification);
    }

    private boolean principalHasThisRole(String principalId, String roleId, AttributeSet qualification) {
        return (this.getAllRoleMemberPrincipalIds(roleId, qualification).contains(principalId));
    }

    /**
     * Returns whether the given principal has any of the passed role IDs with the given qualification.
     */
    @Override
    public boolean principalHasRole(String principalId, List<String> roleIds, AttributeSet qualification) {
        for (String roleId : roleIds) {
            if (this.principalHasThisRole(principalId, roleId, qualification)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the subset of the given principal ID list which has the given role and qualification.
     * This is designed to be used by lookups of people by their roles.
     */
    @Override
    public List<String> getPrincipalIdSubListWithRole(List<String> principalIds,
            String roleNamespaceCode, String roleName, AttributeSet qualification) {
        List<String> subList = new ArrayList<String>();
        KimRoleInfo role = getRoleByName(roleNamespaceCode, roleName);
        for (String principalId : principalIds) {
            if (principalHasThisRole(principalId, role.getRoleId(), qualification)) {
                subList.add(principalId);
            }
        }
        return subList;
    }

    /**
     *
     * This method get search results for role lookup
     */
    @Override
    public List<? extends Role> getRolesSearchResults(java.util.Map<String, String> fieldValues) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Notifies all of a principal's roles and role types that the principal has been inactivated.
     */
    @Override
    public void principalInactivated(String principalId) {
        for (RoleMembershipInfo membership : this.roleMembershipCache.values()) {
            if (membership.getMemberTypeCode().equals(Role.PRINCIPAL_MEMBER_TYPE)) {
                if (principalId.equals(membership.getMemberId())) {
                    this.roleMembershipCache.remove(membership.getRoleMemberId());
                }
            }
        }
    }

    /**
     * Notifies the role service that the role with the given id has been inactivated.
     */
    @Override
    public void roleInactivated(String roleId) {
        for (RoleMembershipInfo membership : this.roleMembershipCache.values()) {
            if (membership.getMemberTypeCode().equals(Role.ROLE_MEMBER_TYPE)) {
                if (roleId.equals(membership.getMemberId())) {
                    this.roleMembershipCache.remove(membership.getRoleMemberId());
                }
                if (roleId.equals(membership.getRoleId())) {
                    this.roleMembershipCache.remove(membership.getRoleMemberId());
                }
            }
        }
        this.roleCache.remove(roleId);
    }

    /**
     * Notifies the role service that the group with the given id has been inactivated.
     */
    @Override
    public void groupInactivated(String groupId) {
        for (RoleMembershipInfo membership : this.roleMembershipCache.values()) {
            if (membership.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
                if (groupId.equals(membership.getMemberId())) {
                    this.roleMembershipCache.remove(membership.getRoleMemberId());
                }
            }
        }
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
    public List<RoleMembershipInfo> getFirstLevelRoleMembers(List<String> roleIds) {
        List<RoleMembershipInfo> list = new ArrayList<RoleMembershipInfo>();
        for (RoleMembershipInfo membership : this.roleMembershipCache.values()) {
            if (roleIds.contains(membership.getRoleId())) {
                this.roleMembershipCache.remove(membership.getRoleMemberId());
            }
        }
        return list;
    }

    /**
     * Gets role member information based on the given search criteria.  The
     * map of criteria contains attributes of RoleMembershipInfo as it's
     * key and the values to search on as the value.
     */
    @Override
    public List<RoleMembershipInfo> findRoleMembers(java.util.Map<String, String> fieldValues) {
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
        for (RoleMembershipInfo membership : this.roleMembershipCache.values()) {
            if (memberType.equals(membership.getMemberTypeCode())) {
                if (memberId.equals(membership.getRoleMemberId())) {
                    list.add(membership.getRoleId());
                }
            }
        }
        return list;
    }

    @Override
    public List<RoleMemberCompleteInfo> findRoleMembersCompleteInfo(java.util.Map<String, String> fieldValues) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public List<DelegateMemberCompleteInfo> findDelegateMembersCompleteInfo(java.util.Map<String, String> fieldValues) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Gets delegation member information based on the given search criteria.  The
     * map of criteria contains attributes of DelegateInfo as it's
     * key and the values to search on as the value.
     */
    @Override
    public List<DelegateMemberCompleteInfo> getDelegationMembersByDelegationId(String delegationId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateMemberCompleteInfo getDelegationMemberByDelegationAndMemberId(String delegationId, String memberId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateMemberCompleteInfo getDelegationMemberById(String delegationMemberId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public List<RoleResponsibilityInfo> getRoleResponsibilities(String roleId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public List<RoleResponsibilityActionInfo> getRoleMemberResponsibilityActionInfo(String roleMemberId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateTypeInfo getDelegateTypeInfo(String roleId, String delegationTypeCode) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public DelegateTypeInfo getDelegateTypeInfoById(String delegationId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public void applicationRoleMembershipChanged(String roleId) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public List<KimRoleInfo> lookupRoles(Map<String, String> searchCriteria) {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Flushes an internal role cache used by the base implementation to prevent repeated database I/O.
     */
    @Override
    public void flushInternalRoleCache() {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Flushes an internal role member cache used by the base implementation to prevent repeated database I/O.
     */
    @Override
    public void flushInternalRoleMemberCache() {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Flushes an internal delegation cache used by the base implementation to prevent repeated database I/O.
     */
    @Override
    public void flushInternalDelegationCache() {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    /**
     * Flushes an internal delegation member cache used by the base implementation to prevent repeated database I/O.
     */
    @Override
    public void flushInternalDelegationMemberCache() {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public void assignGroupToRole(String groupId, String namespaceCode,
            String roleName, AttributeSet qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignPermissionToRole(String permissionId, String roleId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignPrincipalToRole(String principalId, String namespaceCode,
            String roleName, AttributeSet qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignRoleToRole(String roleId, String namespaceCode,
            String roleName, AttributeSet qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNextAvailableRoleId() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeGroupFromRole(String groupId, String namespaceCode,
            String roleName, AttributeSet qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePrincipalFromRole(String principalId, String namespaceCode,
            String roleName,
            AttributeSet qualifications) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeRoleFromRole(String roleId, String namespaceCode,
            String roleName, AttributeSet qualifications)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveDelegationMemberForRole(String delegationMemberId,
            String roleMemberId, String memberId,
            String memberTypeCode,
            String delegationTypeCode,
            String roleId,
            AttributeSet qualifications,
            java.sql.Date activeFromDate, java.sql.Date activeToDate)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveRole(String roleId, String roleName, String roleDescription,
            boolean active, String kimTypeId, String namespaceCode)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleMemberCompleteInfo saveRoleMemberForRole(String roleMemberId,
            String memberId,
            String memberTypeCode,
            String roleId,
            AttributeSet qualifications,
            java.sql.Date activeFromDate,
            java.sql.Date activeToDate) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveRoleRspActions(String roleResponsibilityActionId,
            String roleId, String roleResponsibilityId,
            String roleMemberId, String actionTypeCode,
            String actionPolicyCode, Integer priorityNumber,
            Boolean forceAction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

