/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.mock;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.common.template.Template;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.*;

/**
 *
 * @author nwright
 */
public class TestRoleAndPermissionServiceMockImpl {

    public TestRoleAndPermissionServiceMockImpl() {
    }
    private RoleService roleService;
    private PermissionService permissionService;
    private static final String CENTRAL_ADMIN1 = "CENTRAL_ADMIN1";
    private static final String DEPT_ADMIN1 = "DEPT_ADMIN1";
    private static final String DEPT_ADMIN2 = "DEPT_ADMIN2";
    private static final String DEPT_ADMIN3 = "DEPT_ADMIN3";
    private static final String CO_CREATE_MAINTENANCE_VIEW = "KS-CourseOffering-Create-MaintenanceView";
    private static final String ROLLOVER_MANAGEMENT_VIEW = "KS-CourseOfferingRolloverManagement-View";

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        RoleAndPermissionServiceMockImpl impl = new RoleAndPermissionServiceMockImpl();
        impl.setGroupService(new GroupServiceMockImpl());
        this.roleService = impl;
        this.permissionService = impl;
        this.loadNeededTemplates();
    }

    protected void loadNeededTemplates() {
        this.createTemplate(KimPermissionConstants.KS_ENRL_NAMESPACE, KimPermissionConstants.CAN_INVOKE_SERVICE_METHOD_TEMPLATE_NAME, KimPermissionConstants.DEFAULT_KIM_TYPE_ID);
        this.createTemplate(KimPermissionConstants.KR_KRAD_NAMESPACE, KimPermissionConstants.OPEN_VIEW_TEMPLATE_NAME, KimPermissionConstants.KRAD_VIEW_KIM_TYPE_ID);
        this.createTemplate(KimPermissionConstants.KR_KRAD_NAMESPACE, KimPermissionConstants.EDIT_VIEW_TEMPLATE_NAME, KimPermissionConstants.KRAD_VIEW_KIM_TYPE_ID);
    }

    protected Template createTemplate(String namespaceCode, String name, String kimTypeId) {
        return this.createTemplate(namespaceCode, name, name, kimTypeId);
    }

    protected Template createTemplate(String namespaceCode, String name, String description, String kimTypeId) {
        // TODO: refactor if RICE adds Create/update methods for templates
        RoleAndPermissionServiceMockImpl impl = (RoleAndPermissionServiceMockImpl) this.roleService;
        Template.Builder bldr = Template.Builder.create(namespaceCode, name, kimTypeId);
        bldr.setDescription(description);
        bldr.setActive(true);
        Template template = impl.createTemplate(bldr.build());
        return template;
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of clear method, of class RoleAndPermissionServiceMockImpl.
     */
    @Test
    public void testBasicOperations() {
        System.out.println("test basic operations");
        // Make sure we have a handle on all the templates we need
        Template CAN_INVOKE_SERVICE_METHOD_TEMPLATE =
                this.permissionService.findPermTemplateByNamespaceCodeAndName(KimPermissionConstants.KS_ENRL_NAMESPACE,
                KimPermissionConstants.CAN_INVOKE_SERVICE_METHOD_TEMPLATE_NAME);
        assertNotNull(CAN_INVOKE_SERVICE_METHOD_TEMPLATE);

        Template OPEN_VIEW_TEMPLATE =
                this.permissionService.findPermTemplateByNamespaceCodeAndName(KimPermissionConstants.KR_KRAD_NAMESPACE,
                KimPermissionConstants.OPEN_VIEW_TEMPLATE_NAME);
        assertNotNull(OPEN_VIEW_TEMPLATE);

        Template EDIT_VIEW_TEMPLATE =
                this.permissionService.findPermTemplateByNamespaceCodeAndName(KimPermissionConstants.KR_KRAD_NAMESPACE,
                KimPermissionConstants.EDIT_VIEW_TEMPLATE_NAME);
        assertNotNull(EDIT_VIEW_TEMPLATE);

        // create permissions
        Permission CREATE_ACTIVITY_OFFERING_PERMISSION = 
                this.createPermission(KimPermissionConstants.CREATE_ACTIVITYOFFERING_PERMISSION, 
                CAN_INVOKE_SERVICE_METHOD_TEMPLATE, 
                null);

        Permission CREATE_SOC_PERMISSION = 
                this.createPermission(KimPermissionConstants.CREATE_SOC_PERMISSION, 
                CAN_INVOKE_SERVICE_METHOD_TEMPLATE, 
                null);

        Permission OPEN_VIEWS_FOR_COURSE_OFFERING_PERMISSION = 
                this.createPermission(KimPermissionConstants.OPEN_VIEWS_FOR_COURSE_OFFERING_PERMISSION, 
                OPEN_VIEW_TEMPLATE, 
                CO_CREATE_MAINTENANCE_VIEW);
        
        Permission EDIT_VIEWS_FOR_COURSE_OFFERING_PERMISSION = 
                this.createPermission(KimPermissionConstants.EDIT_VIEWS_FOR_COURSE_OFFERING_PERMISSION, 
                EDIT_VIEW_TEMPLATE, 
                CO_CREATE_MAINTENANCE_VIEW);

        Permission OPEN_VIEWS_FOR_SOC_PERMISSION = 
                this.createPermission(KimPermissionConstants.OPEN_VIEWS_FOR_SOC_PERMISSION, 
                OPEN_VIEW_TEMPLATE, 
                ROLLOVER_MANAGEMENT_VIEW);

        Permission EDIT_VIEWS_FOR_SOC_PERMISSION = 
                this.createPermission(KimPermissionConstants.EDIT_VIEWS_FOR_SOC_PERMISSION, 
                EDIT_VIEW_TEMPLATE, 
                ROLLOVER_MANAGEMENT_VIEW);

        // create some roles
        Role CENTRAL_ADMIN_ROLE = createRole(KimPermissionConstants.KS_ENRL_NAMESPACE,
                KimPermissionConstants.KUALI_STUDENT_COURSE_OFFERING_CENTRAL_ADMIN_ROLE_NAME,
                KimPermissionConstants.DEFAULT_KIM_TYPE_ID);
        Role DEPT_ADMIN_ROLE = createRole(KimPermissionConstants.KS_ENRL_NAMESPACE,
                KimPermissionConstants.KUALI_STUDENT_COURSE_OFFERING_DEPARTMENTAL_ADMIN_ROLE_NAME,
                KimPermissionConstants.DEFAULT_KIM_TYPE_ID);

        // Assign some permissions to some roles
        this.roleService.assignPermissionToRole(CREATE_ACTIVITY_OFFERING_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
        this.roleService.assignPermissionToRole(CREATE_SOC_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
        this.roleService.assignPermissionToRole(CREATE_ACTIVITY_OFFERING_PERMISSION.getId(), DEPT_ADMIN_ROLE.getId());

        this.roleService.assignPermissionToRole(OPEN_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
        this.roleService.assignPermissionToRole(EDIT_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
        this.roleService.assignPermissionToRole(OPEN_VIEWS_FOR_SOC_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());
        this.roleService.assignPermissionToRole(EDIT_VIEWS_FOR_SOC_PERMISSION.getId(), CENTRAL_ADMIN_ROLE.getId());

        this.roleService.assignPermissionToRole(OPEN_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getId(), DEPT_ADMIN_ROLE.getId());
        this.roleService.assignPermissionToRole(EDIT_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getId(), DEPT_ADMIN_ROLE.getId());
        this.roleService.assignPermissionToRole(OPEN_VIEWS_FOR_SOC_PERMISSION.getId(), DEPT_ADMIN_ROLE.getId());

        List<String> roleIds = this.permissionService.getRoleIdsForPermission(CREATE_ACTIVITY_OFFERING_PERMISSION.getNamespaceCode(),
                CREATE_ACTIVITY_OFFERING_PERMISSION.getName());
        assertEquals(2, roleIds.size());
        assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));
        assertTrue(roleIds.contains(DEPT_ADMIN_ROLE.getId()));

        roleIds = this.permissionService.getRoleIdsForPermission(CREATE_SOC_PERMISSION.getNamespaceCode(),
                CREATE_SOC_PERMISSION.getName());
        assertEquals(1, roleIds.size());
        assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));
        
        roleIds = this.permissionService.getRoleIdsForPermission(OPEN_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getNamespaceCode(),
                OPEN_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getName());
        assertEquals(2, roleIds.size());
        assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));
        assertTrue(roleIds.contains(DEPT_ADMIN_ROLE.getId()));
        
        roleIds = this.permissionService.getRoleIdsForPermission(EDIT_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getNamespaceCode(),
                EDIT_VIEWS_FOR_COURSE_OFFERING_PERMISSION.getName());
        assertEquals(2, roleIds.size());
        assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));
        assertTrue(roleIds.contains(DEPT_ADMIN_ROLE.getId()));

        
        roleIds = this.permissionService.getRoleIdsForPermission(OPEN_VIEWS_FOR_SOC_PERMISSION.getNamespaceCode(),
                OPEN_VIEWS_FOR_SOC_PERMISSION.getName());
        assertEquals(2, roleIds.size());
        assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));
        assertTrue(roleIds.contains(DEPT_ADMIN_ROLE.getId()));

        roleIds = this.permissionService.getRoleIdsForPermission(EDIT_VIEWS_FOR_SOC_PERMISSION.getNamespaceCode(),
                EDIT_VIEWS_FOR_SOC_PERMISSION.getName());
        assertEquals(1, roleIds.size());
        assertTrue(roleIds.contains(CENTRAL_ADMIN_ROLE.getId()));
//        assertTrue(roleIds.contains(DEPT_ADMIN_ROLE.getId()));
        
        this.assignPrincipal2Role(CENTRAL_ADMIN1, CENTRAL_ADMIN_ROLE, null);
        this.assignPrincipal2Role(DEPT_ADMIN1, DEPT_ADMIN_ROLE, "ENGL");
        this.assignPrincipal2Role(DEPT_ADMIN2, DEPT_ADMIN_ROLE, "PHYS");
        this.assignPrincipal2Role(DEPT_ADMIN3, DEPT_ADMIN_ROLE, "ENGL");
        this.assignPrincipal2Role(DEPT_ADMIN3, DEPT_ADMIN_ROLE, "PHYS");

        // check is auth by template
        assertTrue(isAuthorizedByTemplate(CENTRAL_ADMIN1, KimPermissionConstants.OPEN_VIEW_TEMPLATE_NAME, CO_CREATE_MAINTENANCE_VIEW));
        assertTrue(isAuthorizedByTemplate(CENTRAL_ADMIN1, KimPermissionConstants.EDIT_VIEW_TEMPLATE_NAME, CO_CREATE_MAINTENANCE_VIEW));
        assertTrue(isAuthorizedByTemplate(CENTRAL_ADMIN1, KimPermissionConstants.OPEN_VIEW_TEMPLATE_NAME, ROLLOVER_MANAGEMENT_VIEW));
        assertTrue(isAuthorizedByTemplate(CENTRAL_ADMIN1, KimPermissionConstants.EDIT_VIEW_TEMPLATE_NAME, ROLLOVER_MANAGEMENT_VIEW));

        assertTrue(isAuthorizedByTemplate(DEPT_ADMIN1, KimPermissionConstants.OPEN_VIEW_TEMPLATE_NAME, CO_CREATE_MAINTENANCE_VIEW));
        assertTrue(isAuthorizedByTemplate(DEPT_ADMIN1, KimPermissionConstants.EDIT_VIEW_TEMPLATE_NAME, CO_CREATE_MAINTENANCE_VIEW));
        assertTrue(isAuthorizedByTemplate(DEPT_ADMIN1, KimPermissionConstants.OPEN_VIEW_TEMPLATE_NAME, ROLLOVER_MANAGEMENT_VIEW));
        assertFalse(isAuthorizedByTemplate(DEPT_ADMIN1, KimPermissionConstants.EDIT_VIEW_TEMPLATE_NAME, ROLLOVER_MANAGEMENT_VIEW));

        assertTrue (isAuthorized(CENTRAL_ADMIN1, CREATE_ACTIVITY_OFFERING_PERMISSION, "ENGL"));
        assertTrue (isAuthorized(CENTRAL_ADMIN1, CREATE_ACTIVITY_OFFERING_PERMISSION, "PHYS"));
        assertTrue (isAuthorized(CENTRAL_ADMIN1, CREATE_ACTIVITY_OFFERING_PERMISSION, "MATH"));
        
        assertTrue (isAuthorized(DEPT_ADMIN1, CREATE_ACTIVITY_OFFERING_PERMISSION, "ENGL"));
        assertFalse(isAuthorized(DEPT_ADMIN1, CREATE_ACTIVITY_OFFERING_PERMISSION, "PHYS"));
        assertFalse(isAuthorized(DEPT_ADMIN1, CREATE_ACTIVITY_OFFERING_PERMISSION, "MATH"));
        
        assertFalse (isAuthorized(DEPT_ADMIN2, CREATE_ACTIVITY_OFFERING_PERMISSION, "ENGL"));
        assertTrue (isAuthorized(DEPT_ADMIN2, CREATE_ACTIVITY_OFFERING_PERMISSION, "PHYS"));
        assertFalse (isAuthorized(DEPT_ADMIN2, CREATE_ACTIVITY_OFFERING_PERMISSION, "MATH"));
        
        assertTrue (isAuthorized(DEPT_ADMIN3, CREATE_ACTIVITY_OFFERING_PERMISSION, "ENGL"));
        assertTrue (isAuthorized(DEPT_ADMIN3, CREATE_ACTIVITY_OFFERING_PERMISSION, "PHYS"));
        assertFalse (isAuthorized(DEPT_ADMIN3, CREATE_ACTIVITY_OFFERING_PERMISSION, "MATH"));

    }

    private boolean isAuthorized (String principalId, Permission permission, String subjArea) {
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

    private boolean isAuthorizedByTemplate(String principalId, String templateName, String viewId) {
        Map<String, String> details = new LinkedHashMap<String, String>();
        details.put(KimPermissionConstants.VIEW_ID_ATTR_DEFINITION, viewId);
        Map<String, String> qualifiers = new LinkedHashMap<String, String>();
        return this.permissionService.isAuthorizedByTemplate(principalId, KimPermissionConstants.KR_KRAD_NAMESPACE, templateName, details, qualifiers);
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
}
