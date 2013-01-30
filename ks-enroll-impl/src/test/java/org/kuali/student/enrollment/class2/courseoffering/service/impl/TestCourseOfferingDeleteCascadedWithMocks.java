/**
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
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.class2.acal.util.AcalTestDataLoader;
import org.kuali.student.enrollment.class2.acal.util.MockAcalTestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
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
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * This class tests the deleteCourseOfferingCascaded functionality in CourseOfferingService
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-mocks-context.xml"})
public class TestCourseOfferingDeleteCascadedWithMocks {
        @Resource(name = "coService")
    protected CourseOfferingService courseOfferingService;
    public static String principalId = "123";
    public ContextInfo callContext = null;
    @Resource(name = "courseService")
    protected CourseService courseService;
    @Resource(name = "luiService")
    protected LuiService luiService;
    @Resource(name = "acalService")
    protected AcademicCalendarService acalService;
    @Resource(name = "atpService")
    protected AtpService atpService;
    @Resource(name = "LrcService")
    protected LRCService lrcService;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new CourseR1TestDataLoader(this.courseService).loadData();
            new LuiServiceDataLoader(this.luiService).loadData();
            new MockAcalTestDataLoader(this.acalService).loadData();
            new AcalTestDataLoader(this.atpService).loadData();
            new MockLrcTestDataLoader(this.lrcService).loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Test
    public void testDeleteCourseOfferingCascaded() throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException,
            DependentObjectsExistException {
        CourseOfferingInfo co = this.testCreateCourseOffering();
        FormatOfferingInfo fo = this.testCreateFormatOffering(co);
        ActivityOfferingInfo ao = this.testCreateActivityOffering(fo);

        StatusInfo status = courseOfferingService.deleteCourseOfferingCascaded(co.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            ao = courseOfferingService.getActivityOffering(ao.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }

        try {
            fo = courseOfferingService.getFormatOffering(fo.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }

        try {
            co = courseOfferingService.getCourseOffering(co.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }

    }

    private CourseOfferingInfo testCreateCourseOffering() throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // get course
        CourseInfo course;
        try {
            course = courseService.getCourse("COURSE1", ContextUtils.getContextInfo());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // create co from course
        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo orig = new CourseOfferingInfo();
        orig.setCourseId(course.getId());
        orig.setTermId("testAtpId1");
        orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        orig.setCourseOfferingTitle("my name");
        orig.setWaitlistLevelTypeKey("waitlist key");
        orig.setHasWaitlist(true);
        orig.setFinalExamType(FinalExam.STANDARD.toString());
        orig.setIsEvaluated(true);
        orig.setIsFeeAtActivityOffering(false);
        orig.setFundingSource("funding source");
        orig.setCourseOfferingCode("CODE");
        orig.setCourseOfferingTitle("Title");
        orig.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        orig.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE);

        CourseOfferingInfo info = courseOfferingService.createCourseOffering(orig.getCourseId(), orig.getTermId(),
                orig.getTypeKey(), orig, optionKeys, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getWaitlistLevelTypeKey(), info.getWaitlistLevelTypeKey());
        assertEquals(orig.getHasWaitlist(), info.getHasWaitlist());
        assertEquals(orig.getFinalExamType(), info.getFinalExamType());
        assertEquals(orig.getIsFeeAtActivityOffering(), info.getIsFeeAtActivityOffering());
        assertEquals(orig.getFundingSource(), info.getFundingSource());
        assertEquals(course.getCode(), info.getCourseOfferingCode());
        assertEquals(course.getSubjectArea(), info.getSubjectArea());

        // refetch co
        orig = info;
        info = courseOfferingService.getCourseOffering(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());

        return info;
    }

     private FormatOfferingInfo testCreateFormatOffering(CourseOfferingInfo co)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException,
                   VersionMismatchException, ReadOnlyException, DependentObjectsExistException {
        FormatOfferingInfo orig = new FormatOfferingInfo();
        orig.setCourseOfferingId(co.getId());
        orig.setFormatId("COURSE1-FORMAT1");
        orig.setActivityOfferingTypeKeys(Arrays.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        FormatOfferingInfo info = courseOfferingService.createFormatOffering(orig.getCourseOfferingId(), orig.getFormatId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());

         orig = info;
         info = courseOfferingService.getFormatOffering(orig.getId(), callContext);
         assertNotNull(info);
         assertEquals(orig.getId(), info.getId());
         assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
         assertEquals(orig.getStateKey(), info.getStateKey());
         assertEquals(orig.getTypeKey(), info.getTypeKey());
         assertEquals(orig.getFormatId(), info.getFormatId());

         return info;
     }

    private ActivityOfferingInfo testCreateActivityOffering(FormatOfferingInfo fo)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException,
            ReadOnlyException, VersionMismatchException {

        ActivityOfferingInfo orig = new ActivityOfferingInfo();
        orig.setFormatOfferingId(fo.getId());
        orig.setActivityId(fo.getId() + "." + LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);
        orig.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        orig.setMinimumEnrollment(100);
        orig.setMaximumEnrollment(150);
        orig.setIsEvaluated(true);
        orig.setIsMaxEnrollmentEstimate(false);
        orig.setIsHonorsOffering(true);
        ActivityOfferingInfo info = courseOfferingService.createActivityOffering(orig.getFormatOfferingId(), orig.getActivityId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getIsEvaluated(), info.getIsEvaluated());
        assertEquals(orig.getIsMaxEnrollmentEstimate(), info.getIsMaxEnrollmentEstimate());
        assertEquals(orig.getIsHonorsOffering(), info.getIsHonorsOffering());

        orig = info;
        info = courseOfferingService.getActivityOffering(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());

        return info;
        }
}
