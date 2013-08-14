/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.enrollment.class2.acal.util.MockAcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.kim.data.PermissionAndRoleServiceDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test that the authorization decorator works against CourseOfferingService
 * 
 * @author Kuali Student Team (ks.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= "classpath:co-test-svcs-authz.xml")
public class CourseOfferingServiceAuthorizationDecoratorTest  {

    @Resource
    private PermissionService permissionService;
    
    @Resource
    private AcademicCalendarService acalService;
    
    @Resource
    private AtpService atpService;
    
    @Resource
    private CourseService courseService;
    
    @Resource
    private CourseOfferingServiceTestDataLoader dataLoader;
    
    @Resource
    private PermissionAndRoleServiceDataLoader permissionAndRoleDataLoader;
    
    @Resource(name="CourseOfferingServiceAuthorizationDecorator")
    private CourseOfferingService courseOfferingService;
    

    
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
        
        principalId = PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1;
        course = ENGL101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        
        principalId = PermissionAndRoleServiceDataLoader.DEPT_ADMIN1;
        course = ENGL101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        course = PHYS101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        course = MATH101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        
        principalId = PermissionAndRoleServiceDataLoader.DEPT_ADMIN2;
        course = ENGL101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        course = PHYS101;
        co = createCourseOffering(false, course, principalId);
        updateCourseOffering (false, co, principalId);
        deleteCourseOffering (false, co, principalId);
        course = MATH101;
        co = createCourseOffering(true, course, principalId);
        co = createCourseOffering(false, course, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        
        principalId = PermissionAndRoleServiceDataLoader.DEPT_ADMIN3;
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
        co = createCourseOffering(false, course, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        updateCourseOffering (true, co, principalId);
        deleteCourseOffering (true, co, principalId);
        deleteCourseOffering (false, co, PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1);
        
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
    public void setup() throws Exception {
        
        permissionAndRoleDataLoader.beforeTest();
        
        loadNeededTerms();
        loadNeededCourses();
        
        
    }

    @After
    public void tearDown() throws Exception {
        permissionAndRoleDataLoader.afterTest();
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
            FA2011_ATP = atpService.createAtp(atp.getTypeKey(), atp, getContext(PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        atp = new AtpInfo();
        atp.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        atp.setId("2012FA");
        try {
            FA2012_ATP = atpService.createAtp(atp.getTypeKey(), atp, getContext(PermissionAndRoleServiceDataLoader.CENTRAL_ADMIN1));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        MockAcalTestDataLoader acalLoader = new MockAcalTestDataLoader(this.acalService);
        SP2011_TERM = acalLoader.loadTerm("2011SP", "Spring 2011", "2011-03-01 00:00:00.0", "2011-05-31 00:00:00.0", AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring Term 2011"
        );
        FA2011_TERM = acalLoader.loadTerm("2011FA", "Fall 2011", "2011-09-01 00:00:00.0", "2011-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall Term 2011"
        );
        SP2012_TERM = acalLoader.loadTerm("2012SP", "Spring 2012", "2012-03-01 00:00:00.0", "2012-05-31 00:00:00.0", AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring Term 2012"
        );
        FA2012_TERM = acalLoader.loadTerm("2012FA", "Fall 2012", "2012-09-01 00:00:00.0", "2012-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall Term 2012"
        );
        SP2013_TERM = acalLoader.loadTerm("2013SP", "Spring 2013", "2013-03-01 00:00:00.0", "2013-05-31 00:00:00.0", AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring Term 2013"
        );
        FA2013_TERM = acalLoader.loadTerm("2013FA", "Fall 2013", "2013-09-01 00:00:00.0", "2013-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall Term 2013"
        );
    }
    private CourseInfo MATH101;
    private CourseInfo ENGL101;
    private CourseInfo PHYS101;

    private void loadNeededCourses() throws AlreadyExistsException, VersionMismatchException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        String code = "MATH101";
        
        ContextInfo contextInfo;
        String math101Id = dataLoader.createCourse(FA2012_TERM, code.substring(0, 4), code, contextInfo = getContext("system"));
        
        MATH101 = courseService.getCourse(math101Id, contextInfo);
                
//                loadCourse(code, "2012FA", code.substring(0, 4), code, "Math 101", "description 1", code + "-FORMAT1",
//                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY);
        code = "ENGL101";
        String engl101Id =  dataLoader.createCourse(FA2012_TERM, code.substring(0, 4), code, contextInfo);
        
        ENGL101 = courseService.getCourse(engl101Id, contextInfo);
        
//                courseLoader.loadCourse(code, "2012FA", code.substring(0, 4), code, "English 101", "description 1", code + "-FORMAT1",
//                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY);
        code = "PHYS101";
        String phys101Id = dataLoader.createCourse(FA2012_TERM, code.substring(0, 4), code, contextInfo); 

        PHYS101 = courseService.getCourse(phys101Id, contextInfo);

        //                courseLoader.loadCourse(code, "2012FA", code.substring(0, 4), code, "Physics 101", "description 1", code + "-FORMAT1",
//                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY);
    
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
        CourseOfferingInfo sourceCo = CourseOfferingServiceTestDataUtils.createCourseOffering(course, FA2011_TERM.getId(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
              
        try {
            sourceCo = courseOfferingService.createCourseOffering(sourceCo.getCourseId(), sourceCo.getTermId(),
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
            CourseOfferingInfo info = this.courseOfferingService.updateCourseOffering(co.getId(), co, context);
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
            StatusInfo status = this.courseOfferingService.deleteCourseOffering(co.getId(), context);
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
    
}
