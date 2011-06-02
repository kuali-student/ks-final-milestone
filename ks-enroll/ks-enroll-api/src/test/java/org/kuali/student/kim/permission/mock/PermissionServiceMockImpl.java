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

import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.role.dto.KimPermissionTemplateInfo;

import java.util.*;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.DelegateMemberCompleteInfo;
import org.kuali.rice.kim.bo.role.dto.DelegateTypeInfo;
import org.kuali.rice.kim.bo.role.dto.KimRoleInfo;
import org.kuali.rice.kim.bo.role.dto.PermissionAssigneeInfo;
import org.kuali.rice.kim.bo.role.dto.RoleMemberCompleteInfo;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.role.dto.RoleResponsibilityActionInfo;
import org.kuali.rice.kim.bo.role.dto.RoleResponsibilityInfo;
import org.kuali.rice.core.xml.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.service.PermissionUpdateService;
import org.kuali.rice.kim.service.RoleManagementService;
import org.kuali.rice.kim.service.RoleService;
import org.kuali.rice.kim.service.RoleUpdateService;

/**
 * @author nwright
 */
public class PermissionServiceMockImpl implements PermissionService,
        PermissionUpdateService
{

    private transient Map<String, KimPermissionTemplateInfo> permissionTemplateCache = new HashMap<String, KimPermissionTemplateInfo>();
    private transient Map<String, KimPermissionInfo> permissionCache = new HashMap<String, KimPermissionInfo>();
    private transient Map<String, KimRoleInfo> roleCache = new HashMap<String, KimRoleInfo>();
    private transient Map<String, RoleMembershipInfo> roleMembershipCache = new HashMap<String, RoleMembershipInfo>();

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
}

