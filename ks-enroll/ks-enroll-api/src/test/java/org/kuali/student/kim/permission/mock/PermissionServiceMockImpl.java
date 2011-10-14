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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMembership;

import org.kuali.rice.kim.bo.role.dto.KimPermissionTemplateInfo;
import org.kuali.rice.kim.bo.role.dto.PermissionAssigneeInfo;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.service.PermissionUpdateService;

/**
 * @author nwright
 */
public class PermissionServiceMockImpl implements PermissionService,
        PermissionUpdateService
{

    private transient Map<String, KimPermissionTemplateInfo> permissionTemplateCache = new HashMap<String, KimPermissionTemplateInfo>();
    private transient Map<String, Permission> permissionCache = new HashMap<String, Permission>();
    private transient Map<String, Role> roleCache = new HashMap<String, Role>();
    private transient Map<String, RoleMembership> roleMembershipCache = new HashMap<String, RoleMembership>();

    @Override
    public List<KimPermissionTemplateInfo> getAllTemplates() {
        return new ArrayList(this.permissionTemplateCache.values());
    }

    @Override
    public List<Permission> getAuthorizedPermissions(String principalId,
            String namespaceCode,
            String permissionName,
            Map<String,String> permissionDetails,
            Map<String,String> qualification) {
        List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionCache.values()) {
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

    private boolean matchesPermissionDetails(Permission permission, Map<String,String> permissionDetails) {
//        TODO: implement this check
        return true;
    }

    private boolean matchesQualification(Permission permission, Map<String,String> qualification) {
//        TODO: implement this check
        return true;
    }

    private boolean matchesPrincipalId(Permission permission, String principalId) {
//        TODO: implement this check
        return true;
    }

    @Override
    public List<Permission> getAuthorizedPermissionsByTemplateName(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String,String> permissionDetails,
            Map<String,String> qualification) {
        List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionCache.values()) {
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
    public Permission getPermission(String permissionId) {
        return this.permissionCache.get(permissionId);
    }

    @Override
    public List<PermissionAssigneeInfo> getPermissionAssignees(String namespaceCode,
            String permissionName,
            Map<String,String> permissionDetails,
            Map<String,String> qualification) {
        List<PermissionAssigneeInfo> list = new ArrayList<PermissionAssigneeInfo>();
        for (Permission permission : this.permissionCache.values()) {
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

    private List<PermissionAssigneeInfo> getPermissionAssignees(Permission permission) {
        List<PermissionAssigneeInfo> list = new ArrayList<PermissionAssigneeInfo>();
//        TODO: Implement this
        return list;
    }

    @Override
    public List<PermissionAssigneeInfo> getPermissionAssigneesForTemplateName(String namespaceCode,
            String permissionTemplateName,
            Map<String,String> permissionDetails,
            Map<String,String> qualification) {
        List<PermissionAssigneeInfo> list = new ArrayList<PermissionAssigneeInfo>();
        for (Permission permission : this.permissionCache.values()) {
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
    public Permission getPermissionsByName(String namespaceCode, String permissionName) {
         List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (permission.isActive()) {
                        list.add(permission);
                    }
                }
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
   

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

    
    @Override
    public List<Permission> getPermissionsByTemplateName(String namespaceCode,
            String permissionTemplateName) {
        List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionCache.values()) {
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
            Map<String,String> permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRoleIdsForPermissionId(String permissionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRoleIdsForPermissions(List<Permission> permissions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(String principalId, String namespaceCode,
            String permissionName,
            Map<String,String> permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermissionByTemplateName(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String,String> permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isAuthorized(String principalId, String namespaceCode,
            String permissionName,
            Map<String,String> permissionDetails,
            Map<String,String> qualification) {
    	if(principalId != null){
	    	if(principalId.equals("123"))
	    		return true;
	    	else
	    		return false;
    	}
    	else
    		return false;
    }

    @Override
    public boolean isAuthorizedByTemplateName(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String,String> permissionDetails,
            Map<String,String> qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPermissionDefined(String namespaceCode, String permissionName,
            Map<String,String> permissionDetails) {
        for (Permission permission : this.permissionCache.values()) {
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
            Map<String,String> permissionDetails) {
        for (Permission permission : this.permissionCache.values()) {
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
    public List<Permission> lookupPermissions(Map<String, String> searchCriteria,
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
            Map<String,String> permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

