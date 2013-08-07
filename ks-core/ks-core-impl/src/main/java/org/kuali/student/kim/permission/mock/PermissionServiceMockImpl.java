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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.kim.api.common.assignee.Assignee;
import org.kuali.rice.kim.api.common.template.Template;
import org.kuali.rice.kim.api.common.template.TemplateQueryResults;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionQueryResults;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.student.common.mock.MockService;

import javax.jws.WebParam;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author nwright
 */
public class PermissionServiceMockImpl implements PermissionService, MockService {

    private transient Map<String, Template> permissionTemplateCache = new HashMap<String, Template>();
    private transient Map<String, Permission> permissionCache = new HashMap<String, Permission>();
    private transient Map<String, Role> roleCache = new HashMap<String, Role>();
    private transient Map<String, RoleMembership> roleMembershipCache = new HashMap<String, RoleMembership>();

    
    @Override
	public void clear() {
    	
    	this.permissionCache.clear();
    	this.permissionTemplateCache.clear();
    	this.roleCache.clear();
    	this.roleMembershipCache.clear();
		
	}

	@Override
    public List<Template> getAllTemplates() {
        return new ArrayList<Template>(this.permissionTemplateCache.values());
    }

    @Override
    public List<Permission> getAuthorizedPermissions(String principalId,
            String namespaceCode,
            String permissionName,
            Map<String, String> qualification) {
        List<Permission> list = new ArrayList<Permission>();
        for (Permission permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (matchesQualification(permission, qualification)) {
                        if (matchesPrincipalId(permission, principalId)) {
                            list.add(permission);
                        }
                    }
                }
            }
        }
        return list;
    }

    private boolean matchesPermissionDetails(Permission permission, Map<String, String> permissionDetails) {
//        TODO: implement this check
        return true;
    }

    private boolean matchesQualification(Permission permission, Map<String, String> qualification) {
//        TODO: implement this check
        return true;
    }

    private boolean matchesPrincipalId(Permission permission, String principalId) {
//        TODO: implement this check
        return true;
    }

    @Override
    public List<Permission> getAuthorizedPermissionsByTemplate(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails,
            Map<String, String> qualification) {
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
    public List<Assignee> getPermissionAssignees(String namespaceCode,
            String permissionName,
            Map<String, String> qualification) {
        List<Assignee> list = new ArrayList<Assignee>();
        for (Permission permission : this.permissionCache.values()) {
            if (namespaceCode.equals(permission.getNamespaceCode())) {
                if (permissionName.equals(permission.getName())) {
                    if (matchesQualification(permission, qualification)) {
                        list.addAll(getPermissionAssignees(permission));
                    }
                }
            }
        }
        return list;
    }

    private List<Assignee> getPermissionAssignees(Permission permission) {
        List<Assignee> list = new ArrayList<Assignee>();
//        TODO: Implement this
        return list;
    }

    @Override
    public List<Assignee> getPermissionAssigneesByTemplate(String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails,
            Map<String, String> qualification) {
        List<Assignee> list = new ArrayList<Assignee>();
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
    public Template getPermissionTemplate(String permissionTemplateId) {
        return this.permissionTemplateCache.get(permissionTemplateId);
    }

    @Override
    public Template findPermTemplateByNamespaceCodeAndName(String namespaceCode,
            String permissionTemplateName) {
        for (Template template : this.permissionTemplateCache.values()) {
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
            String permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(String principalId, String namespaceCode,
            String permissionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermissionByTemplate(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isAuthorized(String principalId, String namespaceCode,
            String permissionName,
            Map<String, String> qualification) {
        if (principalId != null) {
            if (principalId.equals("123")) {
                return true;
            } else if (principalId.endsWith("TestDataLoader")) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAuthorizedByTemplate(String principalId,
            String namespaceCode,
            String permissionTemplateName,
            Map<String, String> permissionDetails,
            Map<String, String> qualification) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPermissionDefined(String namespaceCode, String permissionName) {
        for (Permission permission : this.permissionCache.values()) {
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
    public PermissionQueryResults findPermissions(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Permission createPermission(Permission permission) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Permission updatePermission(Permission permission) throws RiceIllegalArgumentException, RiceIllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TemplateQueryResults findPermissionTemplates(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

