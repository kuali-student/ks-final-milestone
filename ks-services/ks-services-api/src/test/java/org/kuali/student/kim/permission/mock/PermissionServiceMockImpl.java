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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.DelegateMemberCompleteInfo;
import org.kuali.rice.kim.bo.role.dto.DelegateTypeInfo;
import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.role.dto.KimPermissionTemplateInfo;
import org.kuali.rice.kim.bo.role.dto.KimRoleInfo;
import org.kuali.rice.kim.bo.role.dto.PermissionAssigneeInfo;
import org.kuali.rice.kim.bo.role.dto.RoleMemberCompleteInfo;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.role.dto.RoleResponsibilityActionInfo;
import org.kuali.rice.kim.bo.role.dto.RoleResponsibilityInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.service.PermissionUpdateService;
import org.kuali.rice.kim.service.RoleManagementService;
import org.kuali.rice.kim.service.RoleService;
import org.kuali.rice.kim.service.RoleUpdateService;

/**
 * @author nwright
 */
public class PermissionServiceMockImpl implements PermissionService,
        PermissionUpdateService,
        RoleService,
        RoleUpdateService,
        RoleManagementService {

    private transient Map<String, KimPermissionTemplateInfo> permissionTemplateCache = new HashMap<String, KimPermissionTemplateInfo>();
    private transient Map<String, KimPermissionInfo> permissionCache = new HashMap<String, KimPermissionInfo>();
    private transient Map<String, KimRoleInfo> roleCache = new HashMap<String, KimRoleInfo>();

    @Override
    public List<KimPermissionTemplateInfo> getAllTemplates() {
        return new ArrayList(this.permissionTemplateCache.values());
    }

    @Override
    public List<KimPermissionInfo> getAuthorizedPermissions(String principalId,
            String namespaceCode,
            String permissionName,
            AttributeSet permissionDetails,
            AttributeSet qualification) {
        List<KimPermissionInfo> list = new ArrayList<KimPermissionInfo>();
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (matchesPermissionDetails(permission, permissionDetails)) {
                        if (matchesQualification(permission, qualification)) {
                            if (matchesPrincipalId(permission, principalId)) {
                                list.add(permission);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private boolean matchesPermissionDetails(KimPermissionInfo permission, AttributeSet permissionDetails) {
//        TODO: implement this check
        return true;
    }

    private boolean matchesQualification(KimPermissionInfo permission, AttributeSet qualification) {
//        TODO: implement this check
        return true;
    }

    private boolean matchesPrincipalId(KimPermissionInfo permission, String principalId) {
//        TODO: implement this check
        return true;
    }

    @Override
    public List<KimPermissionInfo> getAuthorizedPermissionsByTemplateName(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            AttributeSet permissionDetails,
            AttributeSet qualification) {
        List<KimPermissionInfo> list = new ArrayList<KimPermissionInfo>();
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (permission.getTemplate() != null) {
                if (namespaceCode.equals(permission.getTemplate().getNamespaceCode())) {
                    if (permissionTemplateName.equals(permission.getTemplate().getName())) {
                        if (matchesPermissionDetails(permission, permissionDetails)) {
                            if (matchesQualification(permission, qualification)) {
                                if (matchesPrincipalId(permission, principalId)) {
                                    list.add(permission);
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public KimPermissionInfo getPermission(String permissionId) {
        return this.permissionCache.get(permissionId);
    }

    @Override
    public List<PermissionAssigneeInfo> getPermissionAssignees(String namespaceCode,
            String permissionName,
            AttributeSet permissionDetails,
            AttributeSet qualification) {
        List<PermissionAssigneeInfo> list = new ArrayList<PermissionAssigneeInfo>();
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (matchesPermissionDetails(permission, permissionDetails)) {
                        if (matchesQualification(permission, qualification)) {
                            list.addAll(getPermissionAssignees(permission));
                        }
                    }
                }
            }
        }
        return list;
    }

    private List<PermissionAssigneeInfo> getPermissionAssignees(KimPermissionInfo permission) {
        List<PermissionAssigneeInfo> list = new ArrayList<PermissionAssigneeInfo>();
//        TODO: Implement this
        return list;
    }

    @Override
    public List<PermissionAssigneeInfo> getPermissionAssigneesForTemplateName(String namespaceCode,
            String permissionTemplateName,
            AttributeSet permissionDetails,
            AttributeSet qualification) {
        List<PermissionAssigneeInfo> list = new ArrayList<PermissionAssigneeInfo>();
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (permission.getTemplate() != null) {
                if (namespaceCode.equals(permission.getTemplate().getNamespaceCode())) {
                    if (permissionTemplateName.equals(permission.getTemplate().getName())) {
                        if (matchesPermissionDetails(permission, permissionDetails)) {
                            if (matchesQualification(permission, qualification)) {
                                list.addAll(getPermissionAssignees(permission));
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public String getPermissionDetailLabel(String permissionId, String kimTypeId,
            String attributeName) {
//        TODO: figure out what this is
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimPermissionTemplateInfo getPermissionTemplate(String permissionTemplateId) {
        return this.permissionTemplateCache.get(permissionTemplateId);
    }

    @Override
    public KimPermissionTemplateInfo getPermissionTemplateByName(String namespaceCode,
            String permissionTemplateName) {
        for (KimPermissionTemplateInfo template : this.permissionTemplateCache.values()) {
            if (template.getNamespaceCode().equals(namespaceCode)) {
                if (template.getName().equals(permissionTemplateName)) {
                    return template;
                }
            }
        }
        return null;
    }

    @Override
    public List<KimPermissionInfo> getPermissionsByName(String namespaceCode,
            String permissionName) {
        List<KimPermissionInfo> list = new ArrayList<KimPermissionInfo>();
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (permission.isActive()) {
                        list.add(permission);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<KimPermissionInfo> getPermissionsByNameIncludingInactive(String namespaceCode,
            String permissionName) {
        List<KimPermissionInfo> list = new ArrayList<KimPermissionInfo>();
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    list.add(permission);
                }
            }
        }
        return list;
    }

    @Override
    public List<KimPermissionInfo> getPermissionsByTemplateName(String namespaceCode,
            String permissionTemplateName) {
        List<KimPermissionInfo> list = new ArrayList<KimPermissionInfo>();
        for (KimPermissionInfo permission : this.permissionCache.values()) {
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
            String permissionName,
            AttributeSet permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRoleIdsForPermissionId(String permissionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRoleIdsForPermissions(List<KimPermissionInfo> permissions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(String principalId, String namespaceCode,
            String permissionName,
            AttributeSet permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermissionByTemplateName(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            AttributeSet permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isAuthorized(String principalId, String namespaceCode,
            String permissionName,
            AttributeSet permissionDetails,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isAuthorizedByTemplateName(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            AttributeSet permissionDetails,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPermissionDefined(String namespaceCode, String permissionName,
            AttributeSet permissionDetails) {
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (matchesPermissionDetails(permission, permissionDetails)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isPermissionDefinedForTemplateName(String namespaceCode,
            String permissionTemplateName,
            AttributeSet permissionDetails) {
        for (KimPermissionInfo permission : this.permissionCache.values()) {
            if (permission.getTemplate() != null) {
                if (namespaceCode.equals(permission.getTemplate().getNamespaceCode())) {
                    if (permissionTemplateName.equals(permission.getTemplate().getName())) {
                        if (matchesPermissionDetails(permission, permissionDetails)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<KimPermissionInfo> lookupPermissions(Map<String, String> searchCriteria,
            boolean unbounded) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNextAvailablePermissionId() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void savePermission(String permissionId, String permissionTemplateId,
            String namespaceCode, String name,
            String description, boolean active,
            AttributeSet permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void applicationRoleMembershipChanged(String roleId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DelegateMemberCompleteInfo> findDelegateMembersCompleteInfo(Map<String, String> fieldValues) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoleMembershipInfo> findRoleMembers(Map<String, String> fieldValues) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoleMemberCompleteInfo> findRoleMembersCompleteInfo(Map<String, String> fieldValues) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushInternalDelegationCache() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushInternalDelegationMemberCache() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushInternalRoleCache() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushInternalRoleMemberCache() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateTypeInfo getDelegateTypeInfo(String roleId,
            String delegationTypeCode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateTypeInfo getDelegateTypeInfoById(String delegationId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateMemberCompleteInfo getDelegationMemberByDelegationAndMemberId(String delegationId,
            String memberId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DelegateMemberCompleteInfo getDelegationMemberById(String delegationMemberId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DelegateMemberCompleteInfo> getDelegationMembersByDelegationId(String delegationId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoleMembershipInfo> getFirstLevelRoleMembers(List<String> roleIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getMemberParentRoleIds(String memberType, String memberId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getPrincipalIdSubListWithRole(List<String> principalIds,
            String roleNamespaceCode,
            String roleName,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimRoleInfo getRole(String roleId) {
        return this.roleCache.get(roleId);
    }

    @Override
    public KimRoleInfo getRoleByName(String namespaceCode, String roleName) {
        for (KimRoleInfo role: this.roleCache.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getRoleName())) {
                    return role;
                }
            }
        }
        return null;
    }

    @Override
    public String getRoleIdByName(String namespaceCode, String roleName) {
        for (KimRoleInfo role: this.roleCache.values()) {
            if (namespaceCode.equals(role.getNamespaceCode())) {
                if (roleName.equals(role.getRoleName())) {
                    return role.getRoleId();
                }
            }
        }
        return null;
    }

    @Override
    public Collection<String> getRoleMemberPrincipalIds(String namespaceCode,
            String roleName,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoleResponsibilityActionInfo> getRoleMemberResponsibilityActionInfo(String roleMemberId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoleMembershipInfo> getRoleMembers(List<String> roleIds,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipal(String principalId,
            List<String> roleIds,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipal(String principalId,
            String namespaceCode,
            String roleName,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipalIncludingNested(String principalId,
            String namespaceCode,
            String roleName,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AttributeSet> getRoleQualifiersForPrincipalIncludingNested(String principalId,
            List<String> roleIds,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoleResponsibilityInfo> getRoleResponsibilities(String roleId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimRoleInfo> getRoles(List<String> roleIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<? extends Role> getRolesSearchResults(Map<String, String> fieldValues) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void groupInactivated(String groupId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRoleActive(String roleId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimRoleInfo> lookupRoles(Map<String, String> searchCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean principalHasRole(String principalId,
            List<String> roleIds,
            AttributeSet qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void principalInactivated(String principalId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void roleInactivated(String roleId) {
        throw new UnsupportedOperationException("Not supported yet.");
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
            String roleMemberId,
            String memberId,
            String memberTypeCode,
            String delegationTypeCode,
            String roleId,
            AttributeSet qualifications,
            java.sql.Date activeFromDate,
            java.sql.Date activeToDate)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveRole(String roleId,
            String roleName,
            String roleDescription,
            boolean active,
            String kimTypeId,
            String namespaceCode)
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

    @Override
    public void flushDelegationCaches() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushDelegationMemberCaches() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushRoleCaches() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushRoleMemberCaches() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeCacheEntries(String roleId, String principalId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

