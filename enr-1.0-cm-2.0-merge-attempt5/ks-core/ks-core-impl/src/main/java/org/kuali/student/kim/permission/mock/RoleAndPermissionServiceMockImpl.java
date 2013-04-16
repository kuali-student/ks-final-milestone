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
import javax.jws.WebParam;

import org.joda.time.DateTime;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.delegation.DelegationType;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.common.delegate.DelegateMember;
import org.kuali.rice.kim.api.common.delegate.DelegateType;

import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.rice.kim.api.role.*;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.common.assignee.Assignee;
import org.kuali.student.common.mock.MockService;

import org.kuali.rice.kim.api.common.template.Template;
import org.kuali.rice.kim.api.common.template.TemplateQueryResults;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionQueryResults;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.framework.permission.PermissionTypeService;
import org.kuali.rice.krad.kim.ViewPermissionTypeServiceImpl;
import org.kuali.student.common.util.UUIDHelper;

/**
 * @author nwright
 */
// Note: had to combine Role and Permission services because RICE put the role/permission 
// assignment methods on the Role Service but the Getter methods on the Permission service!
// so no one service has jurisdiction over the mapping.
public class RoleAndPermissionServiceMockImpl implements RoleService, PermissionService, MockService {

    private Map<String, Role> roleMap = new HashMap<String, Role>();
    private Map<String, RoleMembership> roleMembershipMap = new HashMap<String, RoleMembership>();
    private Map<String, Set<String>> rolePermissionMap = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> permissionRoleMap = new HashMap<String, Set<String>>();
    private Map<String, Template> templateMap = new HashMap<String, Template>();
    private Map<String, Permission> permissionMap = new HashMap<String, Permission>();
    private GroupService groupService;

    public RoleAndPermissionServiceMockImpl() {
    }

    @Override
    public void clear() {
        this.roleMap.clear();
        this.roleMembershipMap.clear();
        this.rolePermissionMap.clear();
        this.permissionRoleMap.clear();
        this.permissionMap.clear();
        this.templateMap.clear();
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public Role getRole(String id)
            throws RiceIllegalArgumentException {
        if (!this.roleMap.containsKey(id)) {
            throw new RiceIllegalArgumentException(id);
        }
        return this.roleMap.get(id);
    }

    @Override
    public List<Role> getRoles(List<String> roleIds) {
        List<Role> list = new ArrayList<Role>();
        for (String roleId : roleIds) {
            list.add(this.getRole(roleId));
        }
        return list;
    }

    @Override
    public Role getRoleByNamespaceCodeAndName(String namespaceCode, String roleName) {
        for (Role role : this.roleMap.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getName())) {
                    return role;
                }
            }
        }
        return null;
    }

    @Override
    public String getRoleIdByNamespaceCodeAndName(String namespaceCode, String roleName) {
        for (Role role : this.roleMap.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getName())) {
                    return role.getId();
                }
            }
        }
        return null;
    }

    @Override
    public boolean isRoleActive(String roleId)
            throws RiceIllegalArgumentException {
        Role role = this.getRole(roleId);
        if (role == null) {
            throw new RiceIllegalArgumentException(roleId);
        }
        return role.isActive();
    }

    @Override
    public List<RoleMembership> getRoleMembers(List<String> roleIds, Map<String, String> qualification) {
        List<RoleMembership> list = new ArrayList<RoleMembership>();
        for (RoleMembership info : this.roleMembershipMap.values()) {
            if (roleIds.contains(info.getRoleId())) {
                if (calcIfQualifiersMatch(info.getQualifier(), qualification)) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public Collection<String> getRoleMemberPrincipalIds(String namespaceCode, String roleName, Map<String, String> qualification)
            throws RiceIllegalArgumentException {
        Role roleInfo = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        if (roleInfo == null) {
            throw new RiceIllegalArgumentException("role name not found");
        }
        return this.calcAllMatchingRoleMemberPrincipalIds(roleName, qualification);
    }

    @Override
    public boolean principalHasRole(String principalId, List<String> roleIds, Map<String, String> qualification) {
        for (String roleId : roleIds) {
            if (this.calcIfPrincipalHasRoleAndMatchesQualifiers(principalId, roleId, qualification)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean principalHasRole(String principalId, List<String> roleIds, Map<String, String> qualification, boolean checkDelegations)
            throws RiceIllegalArgumentException {
        if (checkDelegations) {
            throw new RiceIllegalArgumentException("Delegations not supported Yet");
        }
        return this.principalHasRole(principalId, roleIds, qualification);
    }

    @Override
    public List<String> getPrincipalIdSubListWithRole(List<String> principalIds,
            String roleNamespaceCode, String roleName, Map<String, String> qualification) {
        List<String> subList = new ArrayList<String>();
        Role role = getRoleByNamespaceCodeAndName(roleNamespaceCode, roleName);
        for (String principalId : principalIds) {
            if (calcIfPrincipalHasRoleAndMatchesQualifiers(principalId, role.getId(), qualification)) {
                subList.add(principalId);
            }
        }
        return subList;
    }

    @Override
    public RoleQueryResults findRoles(QueryByCriteria queryByCriteria)
            throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("Finds by query is not supported Yet");
    }

    @Override
    public List<RoleMembership> getFirstLevelRoleMembers(List<String> roleIds) {
        List<RoleMembership> list = new ArrayList<RoleMembership>();
        for (RoleMembership rm : this.roleMembershipMap.values()) {
            if (roleIds.contains(rm.getRoleId())) {
                list.add(rm);
            }
        }
        return list;
    }

    @Override
    public RoleMemberQueryResults findRoleMembers(QueryByCriteria queryByCriteria) {
        throw new RiceIllegalArgumentException("find by query by criteria not supported yet.");
    }

    @Override
    public Set<String> getRoleTypeRoleMemberIds(String roleId)
            throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("Roles containing roles are not supported Yet");
    }

    @Override
    public List<String> getMemberParentRoleIds(String memberType, String memberId) {
        List<String> list = new ArrayList<String>();
        for (RoleMembership rm : this.roleMembershipMap.values()) {
            if (memberType.equals(rm.getType().getCode())) {
                if (memberId.equals(rm.getMemberId())) {
                    list.add(rm.getRoleId());
                }
            }
        }
        return list;
    }

    @Override
    public RoleMembershipQueryResults findRoleMemberships(QueryByCriteria queryByCriteria) {
        throw new RiceIllegalArgumentException("Finding by criteria is not supported yet.");
    }

    @Override
    public DelegateMemberQueryResults findDelegateMembers(QueryByCriteria queryByCriteria) {
        throw new RiceIllegalArgumentException("Finding by criteria is not supported yet.");
    }

    @Override
    public List<DelegateMember> getDelegationMembersByDelegationId(String delegationId) {
        throw new RiceIllegalArgumentException("Delgation is not supported Yet");
    }

    @Override
    public DelegateMember getDelegationMemberByDelegationAndMemberId(String delegationId, String memberId) {
        throw new RiceIllegalArgumentException("Delgation is not supported Yet");
    }

    @Override
    public DelegateMember getDelegationMemberById(String delegationMemberId) {
        throw new RiceIllegalArgumentException("Delgation is not supported Yet");
    }

    @Override
    public List<RoleResponsibility> getRoleResponsibilities(String roleId) {
        throw new RiceIllegalArgumentException("Responsibilities are not supported Yet");
    }

    @Override
    public List<RoleResponsibilityAction> getRoleMemberResponsibilityActions(String roleMemberId) {
        throw new RiceIllegalArgumentException("Responsibilities are not supported Yet");
    }

    @Override
    public void deleteRoleResponsibilityAction(String roleResponsibilityActionId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Responsibilities not supported yet.");
    }

    @Override
    public RoleResponsibilityAction updateRoleResponsibilityAction(RoleResponsibilityAction roleResponsibilityAction)
            throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Responsibilities not supported yet.");
    }

    @Override
    public DelegateType getDelegateTypeByRoleIdAndDelegateTypeCode(String roleId,
            DelegationType delegateType) throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("Delgation is not supported Yet");
    }

    @Override
    public DelegateType getDelegateTypeByDelegationId(String delegationId) throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("Delgation is not supported Yet");
    }

    @Override
    public DelegateType updateDelegateType(DelegateType delegateType) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new RiceIllegalArgumentException("Delgation is not supported Yet");
    }

    @Override
    public RoleMember assignGroupToRole(String groupId, String namespaceCode,
            String roleName, Map<String, String> qualifications)
            throws RiceIllegalArgumentException {
        Role role = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        if (role == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + roleName);
        }
        RoleMembership existing = calcExistingRoleMembership(role.getId(),
                KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE.getCode(),
                groupId);
        // if does not exist create
        if (null != existing) {
            throw new RiceIllegalArgumentException("Already exists: " + existing.getId());
        }
        String roleId = role.getId();
        String id = UUIDHelper.genStringUUID();
        String memberId = groupId;
        MemberType memberType = KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE;
        RoleMembership.Builder bldr = RoleMembership.Builder.create(roleId,
                id,
                memberId,
                memberType,
                qualifications);
        RoleMembership rm = bldr.build();
        this.roleMembershipMap.put(rm.getId(), rm);
        return calcMembership2RoleMember(rm);
    }

    @Override
    public RoleMember assignPrincipalToRole(String principalId, String namespaceCode,
            String roleName, Map<String, String> qualifications)
            throws RiceIllegalArgumentException {
        Role role = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        if (role == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + roleName);
        }
        RoleMembership existing = calcExistingRoleMembership(role.getId(),
                KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE.getCode(),
                principalId);
        // if it exists update qualifications
        if (null != existing) {
            // else copy and update qualifications
            RoleMembership.Builder bldr = RoleMembership.Builder.create(existing);
            bldr.setQualifier(qualifications);
            RoleMembership rm = bldr.build();
            this.roleMembershipMap.put(rm.getId(), rm);
            return calcMembership2RoleMember(rm);
        }
        // if does not exist create
        RoleMembership.Builder bldr = RoleMembership.Builder.create(role.getId(),
                UUIDHelper.genStringUUID(),
                principalId,
                KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE,
                qualifications);
        RoleMembership rm = bldr.build();
        this.roleMembershipMap.put(rm.getId(), rm);
        return calcMembership2RoleMember(rm);
    }

    private RoleMembership calcExistingRoleMembership(String roleId, String type, String memberId) {
        for (RoleMembership rm : roleMembershipMap.values()) {
            if (rm.getRoleId().equals(roleId)) {
                if (rm.getType().equals(type)) {
                    if (rm.getMemberId().equals(memberId)) {
                        return rm;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public RoleMember assignRoleToRole(String roleId, String namespaceCode,
            String roleName, Map<String, String> qualifications)
            throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("Roles of roles are not supported yet.");
    }

    @Override
    public void removeGroupFromRole(String groupId, String namespaceCode,
            String roleName, Map<String, String> qualifications)
            throws RiceIllegalArgumentException {
        Role role = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        if (role == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + roleName);
        }
        Group group = this.groupService.getGroup(groupId);
        if (group == null) {
            throw new RiceIllegalArgumentException(groupId);
        }
        RoleMembership existing = calcExistingRoleMembership(role.getId(),
                KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE.getCode(),
                groupId);
        if (existing == null) {
            throw new RiceIllegalArgumentException("Does not exist");
        }
        this.roleMembershipMap.remove(existing.getId());
    }

    @Override
    public void removePrincipalFromRole(String principalId, String namespaceCode,
            String roleName,
            Map<String, String> qualifications) throws RiceIllegalArgumentException {
        Role role = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        if (role == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + roleName);
        }
        RoleMembership existing = calcExistingRoleMembership(role.getId(),
                KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE.getCode(),
                principalId);
        if (existing == null) {
            throw new RiceIllegalArgumentException("Does not exist");
        }
        this.roleMembershipMap.remove(existing.getId());
    }

    @Override
    public void removeRoleFromRole(String roleId, String namespaceCode,
            String roleName, Map<String, String> qualifications)
            throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("Roles of roles not supported yet.");
    }

    @Override
    public RoleResponsibilityAction createRoleResponsibilityAction(RoleResponsibilityAction roleResponsibilityAction)
            throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("Responsibilities not supported yet.");
    }

    @Override
    public RoleMember createRoleMember(RoleMember roleMember) throws RiceIllegalArgumentException, RiceIllegalStateException {
        RoleMembership existing = calcExistingRoleMembership(roleMember.getRoleId(),
                roleMember.getType().getCode(),
                roleMember.getMemberId());
        if (existing == null) {
            throw new RiceIllegalArgumentException("Does not exist");
        }
        RoleMembership rm = this.calcRoleMember2RoleMembership(roleMember);
        this.roleMembershipMap.put(rm.getId(), rm);
        return calcMembership2RoleMember(rm);
    }

    @Override
    public RoleMember updateRoleMember(RoleMember roleMember) throws RiceIllegalArgumentException, RiceIllegalStateException {
        RoleMembership existing = this.roleMembershipMap.get(roleMember.getId());
        if (existing == null) {
            throw new RiceIllegalArgumentException(roleMember.getId());
        }
        RoleMembership rm = this.calcRoleMember2RoleMembership(roleMember);
        this.roleMembershipMap.put(rm.getId(), rm);
        return calcMembership2RoleMember(rm);
    }

    @Override
    public DelegateMember updateDelegateMember(DelegateMember delegateMember)
            throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new RiceIllegalArgumentException("Delegation not supported yet.");
    }

    @Override
    public DelegateMember createDelegateMember(DelegateMember delegateMember)
            throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new RiceIllegalArgumentException("Delegation not supported yet.");
    }

    @Override
    public void removeDelegateMembers(List<DelegateMember> delegateMembers)
            throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new RiceIllegalArgumentException("Delegation not supported yet.");
    }

    @Override
    public Role createRole(Role role) throws RiceIllegalArgumentException, RiceIllegalStateException {
        // CREATE
        Role orig = this.getRoleByNamespaceCodeAndName(role.getNamespaceCode(), role.getName());
        if (orig != null) {
            throw new RiceIllegalArgumentException(role.getNamespaceCode() + "." + role.getName() + " already exists");
        }
        Role.Builder bldr = Role.Builder.create(role);
        if (bldr.getId() == null) {
            bldr.setId(UUIDHelper.genStringUUID());
        }
        bldr.setVersionNumber(1L);
        role = bldr.build();
        roleMap.put(role.getId(), role);
        return role;
    }

    @Override
    public Role updateRole(Role role) throws RiceIllegalArgumentException, RiceIllegalStateException {// UPDATE
        Role.Builder bldr = Role.Builder.create(role);
        Role old = this.getRole(role.getId());
        if (!old.getVersionNumber().equals(bldr.getVersionNumber())) {
            throw new RiceIllegalStateException("" + old.getVersionNumber());
        }
        bldr.setVersionNumber(bldr.getVersionNumber() + 1);
        role = bldr.build();
        roleMap.put(role.getId(), role);
        return role;
    }

    @Override
    public List<Map<String, String>> getNestedRoleQualifiersForPrincipalByRoleIds(
            String principalId, List<String> roleIds, Map<String, String> qualification)
            throws RiceIllegalArgumentException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (RoleMembership rm : this.roleMembershipMap.values()) {
            if (roleIds.contains(rm.getRoleId())) {
                Role role = this.getRole(rm.getRoleId());
                if (rm.getType().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                    if (rm.getMemberId().equals(principalId)) {
                        if (this.calcIfQualifiersMatch(rm.getQualifier(), qualification)) {
                            list.add(rm.getQualifier());
                        }
                    }
                } else if (rm.getType().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                    if (groupService.isMemberOfGroup(principalId, rm.getMemberId())) {
                        if (this.calcIfQualifiersMatch(rm.getQualifier(), qualification)) {
                            list.add(rm.getQualifier());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, String>> getRoleQualifersForPrincipalByNamespaceAndRolename(
            String principalId, String namespaceCode, String roleName, Map<String, String> qualification)
            throws RiceIllegalArgumentException {
        Role role = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        return this.getRoleQualifersForPrincipalByRoleIds(principalId, Arrays.asList(role.getId()), qualification);
    }

    @Override
    public List<Map<String, String>> getRoleQualifersForPrincipalByRoleIds(String principalId,
            List<String> roleIds, Map<String, String> qualification) throws RiceIllegalArgumentException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (RoleMembership rm : this.roleMembershipMap.values()) {
            if (roleIds.contains(rm.getRoleId())) {
                Role role = this.getRole(rm.getRoleId());
                if (rm.getType().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                    if (rm.getMemberId().equals(principalId)) {
                        if (this.calcIfQualifiersMatch(rm.getQualifier(), qualification)) {
                            list.add(rm.getQualifier());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, String>> getNestedRoleQualifersForPrincipalByNamespaceAndRolename(
            String principalId, String namespaceCode,
            String roleName, Map<String, String> qualification)
            throws RiceIllegalArgumentException {
        Role role = this.getRoleByNamespaceCodeAndName(namespaceCode, roleName);
        return this.getNestedRoleQualifiersForPrincipalByRoleIds(principalId, Arrays.asList(role.getId()), qualification);
    }

    @Override
    public DelegateType createDelegateType(DelegateType delegateType) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new RiceIllegalArgumentException("Delegation not supported yet.");
    }

    @Override
    public void assignPermissionToRole(String permissionId, String roleId)
            throws RiceIllegalArgumentException {
        Role role = this.getRole(roleId);
        if (role == null) {
            throw new RiceIllegalArgumentException(roleId);
        }
        Permission permission = this.getPermission(permissionId);
        if (permission == null) {
            throw new RiceIllegalArgumentException(permissionId);
        }
        Set<String> rpSet = this.rolePermissionMap.get(roleId);
        if (rpSet == null) {
            rpSet = new LinkedHashSet<String>();
            this.rolePermissionMap.put(roleId, rpSet);
        }
        rpSet.add(permissionId);
        Set<String> prSet = this.permissionRoleMap.get(permissionId);
        if (prSet == null) {
            prSet = new LinkedHashSet<String>();
            this.permissionRoleMap.put(permissionId, prSet);
        }
        prSet.add(roleId);
    }

    @Override
    public void revokePermissionFromRole(String permissionId, String roleId) throws RiceIllegalArgumentException {
        Role role = this.getRole(roleId);
        if (role == null) {
            throw new RiceIllegalArgumentException(roleId);
        }
        Permission permission = this.getPermission(roleId);
        if (permission == null) {
            throw new RiceIllegalArgumentException(permissionId);
        }
        Set<String> rpSet = this.rolePermissionMap.get(roleId);
        if (rpSet == null) {
            throw new RiceIllegalArgumentException(roleId);
        }
        if (!rpSet.remove(permissionId)) {
            throw new RiceIllegalArgumentException(permissionId);
        }
        Set<String> prSet = this.permissionRoleMap.get(permissionId);
        if (prSet == null) {
            throw new RiceIllegalArgumentException(permissionId);
        }
        if (!prSet.remove(roleId)) {
            throw new RiceIllegalArgumentException(roleId);
        }
    }

    @Override
    public boolean isDerivedRole(String roleId) throws RiceIllegalArgumentException {
        // TODO: support derived roles
        return false;
    }

    @Override
    public boolean isDynamicRoleMembership(String roleId) throws RiceIllegalArgumentException {
        // TODO: figure out what dynamic means does it mean via membership via group membership?
        return false;
    }

    ////
    //// permission service methods
    //// 
    @Override
    public List<Template> getAllTemplates() {
        return new ArrayList<Template>(this.templateMap.values());
    }

    @Override
    public List<Permission> getAuthorizedPermissions(String principalId,
            String namespaceCode,
            String permissionName,
            Map<String, String> qualification) {
        throw new RiceIllegalArgumentException("not implemented do not understand it");
    }

    @Override
    public List<Permission> getAuthorizedPermissionsByTemplate(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails,
            Map<String, String> qualification) {
        Template template = this.findPermTemplateByNamespaceCodeAndName(namespaceCode, permissionTemplateName);
        if (template == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + permissionTemplateName);
        }
        List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionMap.values()) {
            if (permission.getTemplate() != null) {
                if (template.getId().equals(permission.getTemplate().getId())) {
                    list.add(permission);
                }
            }
        }
        if (template.getKimTypeId().equals(KimPermissionConstants.KRAD_VIEW_KIM_TYPE_ID)) {
            PermissionTypeService pts = new ViewPermissionTypeServiceImpl();
            list = pts.getMatchingPermissions(permissionDetails, list);
        }
        List<Permission> list2 = new ArrayList<Permission>();
        for (Permission permission : list) {
            if (this.isAuthorized(principalId, permission.getNamespaceCode(), permission.getName(), qualification)) {
                list.add(permission);
            }
        }
        return list2;
    }

    @Override
    public Permission getPermission(String permissionId) {
        return this.permissionMap.get(permissionId);
    }

    @Override
    public List<Assignee> getPermissionAssignees(String namespaceCode,
            String permissionName,
            Map<String, String> qualification) {
        List<Assignee> list = new ArrayList<Assignee>();
        for (Permission permission : this.permissionMap.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (calcIfQualifiersMatch(permission.getAttributes(), qualification)) {
                        list.addAll(calcPermissionAssignees(permission, qualification));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<Assignee> getPermissionAssigneesByTemplate(String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails,
            Map<String, String> qualification) {
        Template template = this.findPermTemplateByNamespaceCodeAndName(namespaceCode, permissionTemplateName);
        if (template == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + permissionTemplateName);
        }
        List<Assignee> list = new ArrayList<Assignee>();
        for (Permission permission : this.permissionMap.values()) {
            if (permission.getTemplate() != null) {
                if (permission.getTemplate().getId().equals(template.getId())) {
                    if (this.calcIfQualifiersMatch(permission.getAttributes(), permissionDetails)) {
                        list.addAll(calcPermissionAssignees(permission, qualification));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Template getPermissionTemplate(String permissionTemplateId) {
        return this.templateMap.get(permissionTemplateId);
    }

    @Override
    public Template findPermTemplateByNamespaceCodeAndName(String namespaceCode,
            String permissionTemplateName) {
        for (Template template : this.templateMap.values()) {
            if (template.getNamespaceCode().equals(namespaceCode)) {
                if (template.getName().equals(permissionTemplateName)) {
                    return template;
                }
            }
        }
        return null;
    }

    @Override
    public Permission findPermByNamespaceCodeAndName(String namespaceCode, String permissionName) {
        List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionMap.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    return permission;
                }
            }
        }
        return null;
    }

    /*
    
     // TODO Larry Symms wanted to take a look at this
     @Override
     public Permission getPermissionsByNameIncludingInactive(String namespaceCode, String permissionName) {
     List<Permission> list = new ArrayList<Permission>();
     for (Permission permission : this.permissionCache.values()) {
     if (namespaceCode.equals(permission.getNamespaceCode())) {
     if (permissionName.equals(permission.getName())) {
     list.add(permission);
     }
     }
     }
     if (list.isEmpty()) {
     return null;
     }
     return list.get(0);
     }
     */
    @Override
    public List<Permission> findPermissionsByTemplate(String namespaceCode,
            String permissionTemplateName) {
        List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionMap.values()) {
            if (permission.getTemplate() != null) {
                if (namespaceCode.equals(permission.getTemplate().getNamespaceCode())) {
                    if (permissionTemplateName.equals(permission.getTemplate().getName())) {
                        list.add(permission);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getRoleIdsForPermission(String namespaceCode,
            String permissionName)
            throws RiceIllegalArgumentException {
        Permission permission = this.findPermByNamespaceCodeAndName(namespaceCode, permissionName);
        if (permission == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + permissionName);
        }
        Set<String> roleIds = this.permissionRoleMap.get(permission.getId());
        return new ArrayList(roleIds);
    }

    @Override
    public boolean hasPermission(String principalId, String namespaceCode,
            String permissionName)
            throws RiceIllegalArgumentException {
        Permission permission = this.findPermByNamespaceCodeAndName(namespaceCode, permissionName);
        if (permission == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + permissionName);
        }
        Set<String> roleIds = this.permissionRoleMap.get(permission.getId());
        return this.principalHasRole(principalId, new ArrayList(roleIds), null);
    }

    @Override
    public boolean hasPermissionByTemplate(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails)
            throws RiceIllegalArgumentException {
        Template template = this.findPermTemplateByNamespaceCodeAndName(namespaceCode, permissionTemplateName);
        if (template == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + permissionTemplateName);
        }
        List<Permission> permissions = this.findPermissionsByTemplate(namespaceCode, permissionTemplateName);
        for (Permission permission : permissions) {
            if (this.calcIfQualifiersMatch(permission.getAttributes(), permissionDetails)) {
                if (this.hasPermission(principalId, permission.getNamespaceCode(), permission.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isAuthorized(String principalId, String namespaceCode,
            String permissionName,
            Map<String, String> qualification) {
        Permission permission = this.findPermByNamespaceCodeAndName(namespaceCode, permissionName);
        if (permission == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + permissionName);
        }
        Set<String> roleIds = this.permissionRoleMap.get(permission.getId());
        return this.principalHasRole(principalId, new ArrayList(roleIds), qualification);
    }

    @Override
    public boolean isAuthorizedByTemplate(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails,
            Map<String, String> qualification) {
        Template template = this.findPermTemplateByNamespaceCodeAndName(namespaceCode, permissionTemplateName);
        if (template == null) {
            throw new RiceIllegalArgumentException(namespaceCode + "." + permissionTemplateName);
        }
        List<Permission> permissions = this.findPermissionsByTemplate(namespaceCode, permissionTemplateName);
        for (Permission permission : permissions) {
            if (this.calcIfQualifiersMatch(permission.getAttributes(), permissionDetails)) {
                Set<String> roleIds = this.permissionRoleMap.get(permission.getId());
                if (roleIds != null) {
                    return this.principalHasRole(principalId, new ArrayList(roleIds), qualification);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPermissionDefined(String namespaceCode, String permissionName) {
        for (Permission permission : this.permissionMap.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPermissionDefinedByTemplate(String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails) {
        for (Permission permission : this.permissionMap.values()) {
            if (permission.getTemplate() != null) {
                if (namespaceCode.equals(permission.getTemplate().getNamespaceCode())) {
                    if (permissionTemplateName.equals(permission.getTemplate().getName())) {
                        if (calcIfQualifiersMatch(permission.getAttributes(), permissionDetails)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public PermissionQueryResults findPermissions(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Find by criteria not supported yet.");
    }

    @Override
    public Permission createPermission(Permission permission) throws RiceIllegalArgumentException, RiceIllegalStateException {// CREATE
        Permission orig = this.findPermByNamespaceCodeAndName(permission.getNamespaceCode(), permission.getName());
        if (orig != null) {
            throw new RiceIllegalArgumentException(permission.getNamespaceCode() + "." + permission.getName());
        }
        Permission.Builder copy = Permission.Builder.create(permission);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        permission = copy.build();
        permissionMap.put(permission.getId(), permission);
        return permission;
    }

    @Override
    public Permission updatePermission(Permission permission) throws RiceIllegalArgumentException, RiceIllegalStateException {
        // UPDATE
        Permission.Builder copy = Permission.Builder.create(permission);
        Permission old = this.getPermission(permission.getId());
        if (!old.getVersionNumber().equals(copy.getVersionNumber())) {
            throw new RiceIllegalStateException("" + old.getVersionNumber());
        }
        copy.setVersionNumber(copy.getVersionNumber() + 1);
        permission = copy.build();
        this.permissionMap.put(permission.getId(), permission);
        return permission;
    }

    @Override
    public TemplateQueryResults findPermissionTemplates(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Find by criteria not supported yet.");
    }

    ////
    //// private methods
    ////
    private RoleMember calcMembership2RoleMember(RoleMembership rm) {
        String roleId = rm.getRoleId();
        String id = rm.getId();
        String memberId = rm.getMemberId();
        MemberType memberType = rm.getType();
        DateTime activeFromDate = null;
        DateTime activeToDate = null;
        Map<String, String> attributes = rm.getQualifier();
        String memberName = null;
        String memberNamespaceCode = null;
        RoleMember.Builder bldr = RoleMember.Builder.create(roleId,
                id,
                memberId,
                memberType,
                activeFromDate,
                activeToDate,
                attributes,
                memberName,
                memberNamespaceCode);
        return bldr.build();
    }

    private RoleMembership calcRoleMember2RoleMembership(RoleMember rm) {
        String roleId = rm.getRoleId();
        String id = rm.getId();
        if (id == null) {
            id = UUIDHelper.genStringUUID();
        }
        String principalId = rm.getMemberId();
        MemberType memberType = rm.getType();
        Map<String, String> qualifications = rm.getAttributes();
        RoleMembership.Builder bldr = RoleMembership.Builder.create(roleId,
                id,
                principalId,
                memberType,
                qualifications);
        return bldr.build();
    }

    private boolean calcIfPrincipalHasRoleAndMatchesQualifiers(String principalId, String roleId, Map<String, String> qualification) {
        return this.calcAllMatchingRoleMemberPrincipalIds(roleId, qualification).contains(principalId);
    }

    private Set<String> calcAllMatchingRoleMemberPrincipalIds(String roleId, Map<String, String> qualification) {
        Set<String> principals = new LinkedHashSet<String>();
        for (RoleMembership roleMember : this.roleMembershipMap.values()) {
            if (roleId.equals(roleMember.getRoleId())) {
                if (calcIfQualifiersMatch(roleMember.getQualifier(), qualification)) {
                    if (roleMember.getType().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                        principals.add(roleMember.getMemberId());
                    } else if (roleMember.getType().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                        principals.addAll(groupService.getMemberPrincipalIds(roleMember.getMemberId()));
                        // TODO: find out if ROLE members can still be other roles or are they being phased out?
//                    } else if (info.getType().equals(KimConstants.KimGroupMemberTypes.ROLE_MEMBER_TYPE)) {
//                        principals.addAll(this.getAllRoleMemberPrincipalIds(info.getMemberId(), qualification));
                    }
                }
            }
        }
        return principals;
    }

    private boolean calcIfQualifiersMatch(Map<String, String> qual1, Map<String, String> qual2) {
        if (qual2 == null) {
            return true;
        }
        if (qual1 == null) {
            return true;
        }
        if (qual2.isEmpty()) {
            return true;
        }
        if (qual1.isEmpty()) {
            return true;
        }
        // if both have the same qualifier type then those types have to match
        for (String key1 : qual2.keySet()) {
            String val1 = qual1.get(key1);
            if (val1 == null) {
                continue;
            }
            String val2 = qual2.get(key1);
            if (!val1.equals(val2)) {
                return false;
            }
        }
        return true;
    }

    private List<Assignee> calcPermissionAssignees(Permission permission, Map<String, String> queryQualifiers) {
        Set<String> principalIds = new LinkedHashSet<String>();
        List<String> roleIds = this.getRoleIdsForPermission(permission.getNamespaceCode(), permission.getName());
        for (String roleId : roleIds) {
            principalIds.addAll(this.calcAllMatchingRoleMemberPrincipalIds(roleId, queryQualifiers));
        }
        // convert them into assignee
        List<Assignee> list = new ArrayList<Assignee>(principalIds.size());
        for (String principalId : principalIds) {
            // TODO: deal with groups and delegates
            String groupId = null;
            List<DelegateType.Builder> delegates = new ArrayList<DelegateType.Builder>();
            Assignee.Builder bldr = Assignee.Builder.create(principalId, groupId, delegates);
            list.add(bldr.build());
        }
        return list;
    }

    /**
     * This should be part of the contract but it is not There is no way to
     * create a template via the contract.
     *
     * @param template
     * @return
     * @throws RiceIllegalArgumentException
     * @throws RiceIllegalStateException
     */
    public Template createTemplate(Template template) throws RiceIllegalArgumentException, RiceIllegalStateException {// CREATE
        Template orig = this.findPermTemplateByNamespaceCodeAndName(template.getNamespaceCode(), template.getName());
        if (orig != null) {
            throw new RiceIllegalArgumentException(template.getNamespaceCode() + "." + template.getName());
        }
        Template.Builder copy = Template.Builder.create(template);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        template = copy.build();
        templateMap.put(template.getId(), template);
        return template;
    }
}
