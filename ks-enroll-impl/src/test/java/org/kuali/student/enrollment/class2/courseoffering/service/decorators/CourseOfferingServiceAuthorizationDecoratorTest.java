/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.common.template.Template;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.AcademicCalendarServiceMockImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.AcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceBusinessLogicImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceMockImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseR1TestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseServiceR1MockImpl;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.kim.permission.mock.GroupServiceMockImpl;
import org.kuali.student.kim.permission.mock.KimPermissionConstants;
import org.kuali.student.kim.permission.mock.RoleAndPermissionServiceMockImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.atp.service.impl.AtpServiceMockImpl;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

/**
 *
 * @author nwright
 */
public class CourseOfferingServiceAuthorizationDecoratorTest {

    private RoleService roleService;
    private PermissionService permissionService;
    private AcademicCalendarService acalService;
    private AtpService atpService;
    private CourseService courseService;
    private CourseOfferingServiceTestDataLoader dataLoader;
    private CourseOfferingService coService;
    private static final String CENTRAL_ADMIN1 = "CENTRAL_ADMIN1";
    private static final String DEPT_ADMIN1 = "DEPT_ADMIN1";
    private static final String DEPT_ADMIN2 = "DEPT_ADMIN2";
    private static final String DEPT_ADMIN3 = "DEPT_ADMIN3";

    
    @Test
    public void testBasicOperations()
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DependentObjectsExistException,
            VersionMismatchException {
        CourseOfferingInfo co = null;
        String principalId = null;
        CourseInfo course = null;
        
        principalId = CENTRAL_ADMIN1;
        course = ENGL101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        
        principalId = DEPT_ADMIN1;
        course = ENGL101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        course = PHYS101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, CENTRAL_ADMIN1);
        course = MATH101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, CENTRAL_ADMIN1);
        
        principalId = DEPT_ADMIN2;
        course = ENGL101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, CENTRAL_ADMIN1);
        course = PHYS101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        course = MATH101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, CENTRAL_ADMIN1);
        
        principalId = DEPT_ADMIN3;
        course = ENGL101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        course = PHYS101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        course = MATH101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, CENTRAL_ADMIN1);
        
    }

    
    
    private ContextInfo getContext(String userId) {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId(userId);
        return context;
    }

    public CourseOfferingServiceAuthorizationDecoratorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        RoleAndPermissionServiceMockImpl impl = new RoleAndPermissionServiceMockImpl();
        impl.setGroupService(new GroupServiceMockImpl());
        this.roleService = impl;
        this.permissionService = impl;
        this.loadNeededTemplates();
        this.loadRolesAndPermissions();
        this.coService = this.getInstance();
    }

    @After
    public void tearDown() {
    }

    private CourseOfferingService getInstance() {
        CourseOfferingServiceAuthorizationDecorator decorator = new CourseOfferingServiceAuthorizationDecorator();
        decorator.setNextDecorator(this.getCourseOfferingServicePersistence());
        decorator.setPermissionService(this.permissionService);
        decorator.setCourseService(courseService);
        return decorator;
    }

    private CourseOfferingService getCourseOfferingServicePersistence() {
        AtpServiceMockImpl atp = new AtpServiceMockImpl();
        this.atpService = atp;
        AcademicCalendarServiceMockImpl acal = new AcademicCalendarServiceMockImpl();
        this.acalService = acal;
        loadNeededTerms();
        CourseOfferingServiceMockImpl co = new CourseOfferingServiceMockImpl();
        co.setAcalService(acal);
        CourseServiceR1MockImpl course = new CourseServiceR1MockImpl();
        this.courseService = course;
        this.loadNeededCourses();
        co.setCourseService(course);
        CourseOfferingServiceBusinessLogicImpl bl = new CourseOfferingServiceBusinessLogicImpl();
        bl.setAcalService(acalService);
        bl.setCoService(co);
        bl.setCourseService(course);
        co.setBusinessLogic(bl);
        return co;
    }
    private AtpInfo FA2012_ATP;
    private AtpInfo FA2011_ATP;
    private TermInfo SP2011_TERM;
    private TermInfo FA2011_TERM;
    private TermInfo SP2012_TERM;
    private TermInfo FA2012_TERM;
    private TermInfo SP2013_TERM;
    private TermInfo FA2013_TERM;

    private void loadNeededTerms() {
        AtpInfo atp = new AtpInfo();
        atp.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        atp.setId("2011FA");
        try {
            FA2011_ATP = atpService.createAtp(atp.getTypeKey(), atp, getContext(CENTRAL_ADMIN1));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        atp = new AtpInfo();
        atp.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        atp.setId("2012FA");
        try {
            FA2012_ATP = atpService.createAtp(atp.getTypeKey(), atp, getContext(CENTRAL_ADMIN1));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        AcalTestDataLoader acalLoader = new AcalTestDataLoader(this.acalService);
        SP2011_TERM = acalLoader.loadTerm("2011SP", "Spring 2011", "Spring Term 2011", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2011-03-01 00:00:00.0", "2011-05-31 00:00:00.0");
        FA2011_TERM = acalLoader.loadTerm("2011FA", "Fall 2011", "Fall Term 2011", AtpServiceConstants.ATP_FALL_TYPE_KEY,
                "2011-09-01 00:00:00.0", "2011-12-31 00:00:00.0");
        SP2012_TERM = acalLoader.loadTerm("2012SP", "Spring 2012", "Spring Term 2012", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2012-03-01 00:00:00.0", "2012-05-31 00:00:00.0");
        FA2012_TERM = acalLoader.loadTerm("2012FA", "Fall 2012", "Fall Term 2012", AtpServiceConstants.ATP_FALL_TYPE_KEY,
                "2012-09-01 00:00:00.0", "2012-12-31 00:00:00.0");
        SP2013_TERM = acalLoader.loadTerm("2013SP", "Spring 2013", "Spring Term 2013", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2013-03-01 00:00:00.0", "2013-05-31 00:00:00.0");
        FA2013_TERM = acalLoader.loadTerm("2013FA", "Fall 2013", "Fall Term 2013", AtpServiceConstants.ATP_FALL_TYPE_KEY,
                "2013-09-01 00:00:00.0", "2013-12-31 00:00:00.0");
    }
    private CourseInfo MATH101;
    private CourseInfo ENGL101;
    private CourseInfo PHYS101;

    private void loadNeededCourses() {
        String code = "MATH101";
        CourseR1TestDataLoader courseLoader = new CourseR1TestDataLoader(this.courseService);
        MATH101 = courseLoader.loadCourse(code, "2012FA", code.substring(0, 4), code, "Math 101", "description 1", code + "-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY);
        code = "ENGL101";
        ENGL101 = courseLoader.loadCourse(code, "2012FA", code.substring(0, 4), code, "English 101", "description 1", code + "-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY);
        code = "PHYS101";
        PHYS101 = courseLoader.loadCourse(code, "2012FA", code.substring(0, 4), code, "Physics 101", "description 1", code + "-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY);
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

    private void loadRolesAndPermissions() {
        System.out.println("test basic operations");
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

    private CourseOfferingInfo createCourseOffering(boolean shouldThrowPermissionDenied, CourseInfo course, String principalId)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DependentObjectsExistException {
        ContextInfo context = this.getContext(principalId);
        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo sourceCo = new CourseOfferingInfo();
        sourceCo.setCourseId(course.getId());
        sourceCo.setTermId(FA2011_TERM.getId());
        sourceCo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        sourceCo.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        try {
            sourceCo = coService.createCourseOffering(sourceCo.getCourseId(), sourceCo.getTermId(),
                    sourceCo.getTypeKey(), sourceCo, optionKeys, context);
        } catch (PermissionDeniedException ex) {
            if (!shouldThrowPermissionDenied) {
                fail("Create should not have thrown permission denied but did " + course.getCode () + " " + principalId);
                return null;
            }
            return null;
        }
        if (shouldThrowPermissionDenied) {
            fail("Create should have thrown permission denied but did not " + course.getCode () + " " + principalId);
            return null;
        }
        return sourceCo;
    }

    private void updateCourseOffering(boolean shouldThrowPermissionDenied, CourseOfferingInfo co, String principalId)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DependentObjectsExistException,
            VersionMismatchException {
        ContextInfo context = this.getContext(principalId);
        co.setCourseOfferingTitle(co.getCourseOfferingTitle() + " Updated");
        try {
            CourseOfferingInfo info = this.coService.updateCourseOffering(co.getId(), co, context);
        } catch (PermissionDeniedException ex) {
            if (!shouldThrowPermissionDenied) {
                fail("Delete should not have thrown permission denied but did " + co.getCourseCode() + " " + principalId);
                return;
            }
            return;
        }
        if (shouldThrowPermissionDenied) {
            fail("Delete should have thrown permission denied but did not " + co.getCourseCode() + " " + principalId);
            return;
        }
        return;
    }
    private void deleteCourseOffering(boolean shouldThrowPermissionDenied, CourseOfferingInfo co, String principalId)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DependentObjectsExistException {
        ContextInfo context = this.getContext(principalId);
        try {
            StatusInfo status = this.coService.deleteCourseOffering(co.getId(), context);
        } catch (PermissionDeniedException ex) {
            if (!shouldThrowPermissionDenied) {
                fail("Delete should not have thrown permission denied but did " + co.getCourseCode() + " " + principalId);
                return;
            }
            return;
        }
        if (shouldThrowPermissionDenied) {
            fail("Delete should have thrown permission denied but did not " + co.getCourseCode() + " " + principalId);
            return;
        }
        return;
    }
    
//
//        FormatInfo format = course.getFormats().get(0);
//        FormatOfferingInfo sourceFo = new FormatOfferingInfo();
//        sourceFo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
//        sourceFo.setStateKey(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
//        sourceFo.setCourseOfferingId(sourceCo.getId());
//        sourceFo.setDescr(new RichTextHelper().fromPlain("test format offering"));
//        sourceFo.setFormatId(format.getId());
//        sourceFo.setName("format offering 1");
//        sourceFo.setTermId(sourceCo.getTermId());
//        sourceFo = coService.createFormatOffering(sourceFo.getCourseOfferingId(), sourceFo.getFormatId(),
//                sourceFo.getTypeKey(), sourceFo, this.getContext(DEPT_ADMIN1));
//
//        ActivityInfo activity = format.getActivities().get(0);
//        ActivityOfferingInfo sourceAo = new ActivityOfferingInfo();
//        sourceAo.setFormatOfferingId(sourceFo.getId());
//        sourceAo.setActivityId(activity.getId());
//        sourceAo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
//        sourceAo.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
//        sourceAo.setActivityCode("A");
//        sourceAo.setDescr(new RichTextHelper().fromPlain("test activity"));
//        sourceAo.setIsHonorsOffering(Boolean.TRUE);
//        sourceAo.setMaximumEnrollment(100);
//        sourceAo.setMinimumEnrollment(90);
//        sourceAo.setName("my activity offering");
//        ScheduleInfo scheduleInfo = new ScheduleInfo();
//        scheduleInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
//        scheduleInfo.setAtpId(sourceCo.getTermId());
//        scheduleInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(), scheduleInfo, callContext);
//        sourceAo.setScheduleId(scheduleInfo.getId());
//        sourceAo = coService.createActivityOffering(sourceAo.getFormatOfferingId(), sourceAo.getActivityId(),
//                sourceAo.getTypeKey(), sourceAo, callContext);
//    }
}
