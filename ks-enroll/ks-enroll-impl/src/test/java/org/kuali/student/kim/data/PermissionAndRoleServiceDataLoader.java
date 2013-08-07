/*
 * Copyright 2012 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.kim.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.common.template.Template;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.kim.permission.mock.KimPermissionConstants;
import org.kuali.student.kim.permission.mock.RoleAndPermissionServiceMockImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Loads an in memory configuration of the permissions and roles suitable for validating user authorization.
 * 
 * @author Kuali Student Team 
 *
 */
public class PermissionAndRoleServiceDataLoader extends AbstractMockServicesAwareDataLoader {
    
    private static final Logger log = LoggerFactory
            .getLogger(PermissionAndRoleServiceDataLoader.class);

    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RoleService roleService;
    
    public static final String CENTRAL_ADMIN1 = "CENTRAL_ADMIN1";
    public static final String DEPT_ADMIN1 = "DEPT_ADMIN1";
    public static final String DEPT_ADMIN2 = "DEPT_ADMIN2";
    public static final String DEPT_ADMIN3 = "DEPT_ADMIN3";
    
    /**
     * 
     */
    public PermissionAndRoleServiceDataLoader() {
        
    }

    private void loadNeededTemplates() {
        this.createTemplate(KimPermissionConstants.KS_ENRL_NAMESPACE, KimPermissionConstants.CAN_INVOKE_SERVICE_METHOD_TEMPLATE_NAME, KimPermissionConstants.DEFAULT_KIM_TYPE_ID);
    }

    private Template createTemplate(String namespaceCode, String name, String kimTypeId) {
        return this.createTemplate(namespaceCode, name, name, kimTypeId);
    }

    private Template createTemplate(String namespaceCode, String name, String description, String kimTypeId) {
        // TODO: refactor if RICE adds Create/update methods for templates
        RoleAndPermissionServiceMockImpl impl = (RoleAndPermissionServiceMockImpl) this.roleService;
        Template.Builder bldr = Template.Builder.create(namespaceCode, name, kimTypeId);
        bldr.setDescription(description);
        bldr.setActive(true);
        Template template = impl.createTemplate(bldr.build());
        return template;
    }

   

    private boolean isAuthorized(String principalId, Permission permission, String subjArea) {
        Map<String, String> details = new LinkedHashMap<String, String>();
        if (subjArea != null) {
            details.put(KimPermissionConstants.SUBJECT_AREA_ATTR_DEFINITION, subjArea);
        }
        return this.permissionService.isAuthorized(principalId, permission.getNamespaceCode(), permission.getName(), details);
    }
    
    private Permission createPermission(String permName, Template template, String viewId) {
        Map<String, String> details = new LinkedHashMap<String, String>();
        if (viewId != null) {
            details.put(KimPermissionConstants.VIEW_ID_ATTR_DEFINITION, viewId);
        }
        Permission permission = createPermission(template,
                KimPermissionConstants.KS_ENRL_NAMESPACE,
                permName, details);
        return permission;
    }

    private RoleMember assignPrincipal2Role(String principalId, Role role, String subjArea) {
        Map<String, String> qualifiers = new LinkedHashMap<String, String>();
        if (subjArea != null) {
            qualifiers.put(KimPermissionConstants.SUBJECT_AREA_ATTR_DEFINITION, subjArea);
        }
        RoleMember roleMember = this.roleService.assignPrincipalToRole(principalId, role.getNamespaceCode(), role.getName(), qualifiers);
        assertEquals(principalId, roleMember.getMemberId());
        assertEquals(role.getId(), roleMember.getRoleId());
        assertEquals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, roleMember.getType());
        assertEquals(qualifiers, roleMember.getAttributes());
        List<String> roleIds = this.roleService.getMemberParentRoleIds(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE.getCode(), principalId);
//        assertEquals(1, roleIds.size());
        assertTrue(roleIds.contains(role.getId()));
        return roleMember;

    }

    private Permission createPermission(Template template, String namespaceCode, String name, Map<String, String> details) {
        Permission.Builder bldr = Permission.Builder.create(namespaceCode, name);
        bldr.setTemplate(Template.Builder.create(template));
        bldr.setActive(true);
        bldr.setAttributes(details);

        Permission fromCreate = this.permissionService.createPermission(bldr.build());
        assertEquals(namespaceCode, fromCreate.getNamespaceCode());
        assertEquals(name, fromCreate.getName());
        assertNotNull(fromCreate.getId());
        assertNotNull(fromCreate.getVersionNumber());
        assertTrue(fromCreate.isActive());
        assertEquals(details, fromCreate.getAttributes());
        assertNull(fromCreate.getDescription());

        bldr = Permission.Builder.create(fromCreate);
        bldr.setDescription(namespaceCode + " " + name);
        Permission fromUpdate = this.permissionService.updatePermission(bldr.build());
        assertEquals(fromCreate.getNamespaceCode(), fromUpdate.getNamespaceCode());
        assertEquals(fromCreate.getName(), fromUpdate.getName());
        assertEquals(fromCreate.getId(), fromUpdate.getId());
        assertNotSame(fromCreate.getVersionNumber(), fromUpdate.getVersionNumber());
        assertEquals(fromCreate.isActive(), fromUpdate.isActive());
        assertEquals(fromCreate.getAttributes(), fromUpdate.getAttributes());
        assertEquals(bldr.getDescription(), fromUpdate.getDescription());

        Permission fromGet = this.permissionService.getPermission(fromUpdate.getId());
        assertEquals(fromUpdate.getNamespaceCode(), fromGet.getNamespaceCode());
        assertEquals(fromUpdate.getName(), fromGet.getName());
        assertEquals(fromUpdate.getId(), fromGet.getId());
        assertEquals(fromUpdate.getVersionNumber(), fromGet.getVersionNumber());
        assertEquals(fromUpdate.isActive(), fromGet.isActive());
        assertEquals(fromUpdate.getAttributes(), fromGet.getAttributes());
        assertEquals(fromUpdate.getDescription(), fromGet.getDescription());

        Permission fromFind = this.permissionService.findPermByNamespaceCodeAndName(namespaceCode, name);
        assertEquals(fromUpdate.getNamespaceCode(), fromFind.getNamespaceCode());
        assertEquals(fromUpdate.getName(), fromFind.getName());
        assertEquals(fromUpdate.getId(), fromFind.getId());
        assertEquals(fromUpdate.getVersionNumber(), fromFind.getVersionNumber());
        assertEquals(fromUpdate.isActive(), fromFind.isActive());
        assertEquals(fromUpdate.getAttributes(), fromFind.getAttributes());
        assertEquals(fromUpdate.getDescription(), fromFind.getDescription());

        return fromFind;
    }

    private Role createRole(String namespaceCode, String name, String kimTypeId) {
        Role.Builder bldr = Role.Builder.create();
        bldr.setNamespaceCode(namespaceCode);
        bldr.setName(name);
        bldr.setKimTypeId(kimTypeId);
        bldr.setActive(true);

        Role fromCreate = this.roleService.createRole(bldr.build());
        assertEquals(namespaceCode, fromCreate.getNamespaceCode());
        assertEquals(name, fromCreate.getName());
        assertEquals(kimTypeId, fromCreate.getKimTypeId());
        assertNotNull(fromCreate.getId());
        assertNotNull(fromCreate.getVersionNumber());
        assertTrue(fromCreate.isActive());
        assertNull(fromCreate.getDescription());


        bldr = Role.Builder.create(fromCreate);
        bldr.setDescription(namespaceCode + " " + name);
        Role fromUpdate = this.roleService.updateRole(bldr.build());
        assertEquals(fromCreate.getNamespaceCode(), fromUpdate.getNamespaceCode());
        assertEquals(fromCreate.getName(), fromUpdate.getName());
        assertEquals(fromCreate.getId(), fromUpdate.getId());
        assertNotSame(fromCreate.getVersionNumber(), fromUpdate.getVersionNumber());
        assertEquals(fromCreate.isActive(), fromUpdate.isActive());
        assertEquals(bldr.getDescription(), fromUpdate.getDescription());

        Role fromGet = this.roleService.getRole(fromUpdate.getId());
        assertEquals(fromUpdate.getNamespaceCode(), fromGet.getNamespaceCode());
        assertEquals(fromUpdate.getName(), fromGet.getName());
        assertEquals(fromUpdate.getKimTypeId(), fromGet.getKimTypeId());
        assertEquals(fromUpdate.getId(), fromGet.getId());
        assertEquals(fromUpdate.getVersionNumber(), fromGet.getVersionNumber());
        assertEquals(fromUpdate.isActive(), fromGet.isActive());
        assertEquals(fromUpdate.getDescription(), fromGet.getDescription());

        Role fromFind = this.roleService.getRoleByNamespaceCodeAndName(namespaceCode, name);
        assertEquals(fromUpdate.getNamespaceCode(), fromFind.getNamespaceCode());
        assertEquals(fromUpdate.getName(), fromFind.getName());
        assertEquals(fromUpdate.getKimTypeId(), fromFind.getKimTypeId());
        assertEquals(fromUpdate.getId(), fromFind.getId());
        assertEquals(fromUpdate.getVersionNumber(), fromFind.getVersionNumber());
        assertEquals(fromUpdate.isActive(), fromFind.isActive());
        assertEquals(fromUpdate.getDescription(), fromFind.getDescription());

        // TODO: test update

        return fromFind;
    }
    /* (non-Javadoc)
     * @see org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader#initializeData()
     */
    @Override
    protected void initializeData() throws Exception {
            log.info("test basic operations");
            
            loadNeededTemplates();
            
            // Make sure we have a handle on all the templates we need
            Template CAN_INVOKE_SERVICE_METHOD_TEMPLATE =
                    this.permissionService.findPermTemplateByNamespaceCodeAndName(KimPermissionConstants.KS_ENRL_NAMESPACE,
                    KimPermissionConstants.CAN_INVOKE_SERVICE_METHOD_TEMPLATE_NAME);
            assertNotNull(CAN_INVOKE_SERVICE_METHOD_TEMPLATE);

            // create permissions
            Permission CREATE_COURSE_OFFERING_PERMISSION =
                    this.createPermission(KimPermissionConstants.CREATE_COURSEOFFERING_PERMISSION,
                    CAN_INVOKE_SERVICE_METHOD_TEMPLATE,
                    null);

            Permission UPDATE_COURSE_OFFERING_PERMISSION =
                    this.createPermission(KimPermissionConstants.UPDATE_COURSEOFFERING_PERMISSION,
                    CAN_INVOKE_SERVICE_METHOD_TEMPLATE,
                    null);
            Permission DELETE_COURSE_OFFERING_PERMISSION =
                    this.createPermission(KimPermissionConstants.DELETE_COURSEOFFERING_PERMISSION,
                    CAN_INVOKE_SERVICE_METHOD_TEMPLATE,
                    null);

            Permission CREATE_SOC_PERMISSION =
                    this.createPermission(KimPermissionConstants.CREATE_SOC_PERMISSION,
                    CAN_INVOKE_SERVICE_METHOD_TEMPLATE,
                    null);

            // create some roles
            Role CENTRAL_ADMIN_ROLE = createRole(KimPermissionConstants.KS_ENRL_NAMESPACE,
                    KimPermissionConstants.KUALI_STUDENT_COURSE_OFFERING_CENTRAL_ADMIN_ROLE_NAME,
                    KimPermissionConstants.DEFAULT_KIM_TYPE_ID);
            Role DEPT_ADMIN_ROLE = createRole(KimPermissionConstants.KS_ENRL_NAMESPACE,
                    KimPermissionConstants.KUALI_STUDENT_COURSE_OFFERING_DEPARTMENTAL_ADMIN_ROLE_NAME,
                    KimPermissionConstants.DEFAULT_KIM_TYPE_ID);

            // Assign some permissions to some roles
            this.roleService.assignPermissionToRole(CREATE_COURSE_OFFERING_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
            this.roleService.assignPermissionToRole(UPDATE_COURSE_OFFERING_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
            this.roleService.assignPermissionToRole(DELETE_COURSE_OFFERING_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
            this.roleService.assignPermissionToRole(CREATE_SOC_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
            
            this.roleService.assignPermissionToRole(CREATE_COURSE_OFFERING_PERMISSION.getId(), DEPT_ADMIN_ROLE.getId());
            this.roleService.assignPermissionToRole(UPDATE_COURSE_OFFERING_PERMISSION.getId(), DEPT_ADMIN_ROLE.getId());
            this.roleService.assignPermissionToRole(DELETE_COURSE_OFFERING_PERMISSION.getId(), DEPT_ADMIN_ROLE.getId());

            List<String> roleIds = this.permissionService.getRoleIdsForPermission(CREATE_COURSE_OFFERING_PERMISSION.getNamespaceCode(),
                    CREATE_COURSE_OFFERING_PERMISSION.getName());
            assertEquals(2, roleIds.size());
            assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));
            assertTrue(roleIds.contains(DEPT_ADMIN_ROLE.getId()));

            roleIds = this.permissionService.getRoleIdsForPermission(CREATE_SOC_PERMISSION.getNamespaceCode(),
                    CREATE_SOC_PERMISSION.getName());
            assertEquals(1, roleIds.size());
            assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));


            this.assignPrincipal2Role(CENTRAL_ADMIN1, CENTRAL_ADMIN_ROLE, null);
            this.assignPrincipal2Role(DEPT_ADMIN1, DEPT_ADMIN_ROLE, "ENGL");
            this.assignPrincipal2Role(DEPT_ADMIN2, DEPT_ADMIN_ROLE, "PHYS");
            this.assignPrincipal2Role(DEPT_ADMIN3, DEPT_ADMIN_ROLE, "ENGL");
            this.assignPrincipal2Role(DEPT_ADMIN3, DEPT_ADMIN_ROLE, "PHYS");

            assertTrue(isAuthorized(CENTRAL_ADMIN1, CREATE_COURSE_OFFERING_PERMISSION, "ENGL"));
            assertTrue(isAuthorized(CENTRAL_ADMIN1, CREATE_COURSE_OFFERING_PERMISSION, "PHYS"));
            assertTrue(isAuthorized(CENTRAL_ADMIN1, CREATE_COURSE_OFFERING_PERMISSION, "MATH"));

            assertTrue(isAuthorized(DEPT_ADMIN1, CREATE_COURSE_OFFERING_PERMISSION, "ENGL"));
            assertFalse(isAuthorized(DEPT_ADMIN1, CREATE_COURSE_OFFERING_PERMISSION, "PHYS"));
            assertFalse(isAuthorized(DEPT_ADMIN1, CREATE_COURSE_OFFERING_PERMISSION, "MATH"));

            assertFalse(isAuthorized(DEPT_ADMIN2, CREATE_COURSE_OFFERING_PERMISSION, "ENGL"));
            assertTrue(isAuthorized(DEPT_ADMIN2, CREATE_COURSE_OFFERING_PERMISSION, "PHYS"));
            assertFalse(isAuthorized(DEPT_ADMIN2, CREATE_COURSE_OFFERING_PERMISSION, "MATH"));

            assertTrue(isAuthorized(DEPT_ADMIN3, CREATE_COURSE_OFFERING_PERMISSION, "ENGL"));
            assertTrue(isAuthorized(DEPT_ADMIN3, CREATE_COURSE_OFFERING_PERMISSION, "PHYS"));
            assertFalse(isAuthorized(DEPT_ADMIN3, CREATE_COURSE_OFFERING_PERMISSION, "MATH"));

    }

    
    
}
