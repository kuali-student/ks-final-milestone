package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.enrollment.courseoffering.service.R1ToR2CopyHelper;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiTestDataLoader;
import javax.annotation.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-mocks-context.xml"})
public class TestCourseOfferingServiceImplWithMocks {

    @Resource(name = "coService")
    private CourseOfferingService courseOfferingService;
    public static String principalId = "123";
    public ContextInfo callContext = null;
    @Resource(name = "courseService")
    private CourseService courseService;
    @Resource(name = "luiService")
    private LuiService luiService;
    @Resource(name = "acalService")
    private AcademicCalendarService acalService;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new CourseR1TestDataLoader(this.courseService).loadData();
            new LuiTestDataLoader(this.luiService).loadData();
            new AcalTestDataLoader(this.acalService).loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Test
    public void testCRUD() throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException,
            DependentObjectsExistException {
        CourseOfferingInfo co = this.testCRUDCourseOffering();
        FormatOfferingInfo fo = this.testCRUDFormatOffering(co);
        ActivityOfferingInfo ao = this.testCRUDActivityOffering(fo);
        this.testDeletes(co, fo, ao);
    }

    public CourseOfferingInfo testCRUDCourseOffering() throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // get course
        CourseInfo course;
        try {
            course = courseService.getCourse("COURSE1");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // create co from course
        CourseOfferingInfo orig = new CourseOfferingInfo();
        orig.setCourseId(course.getId());
        orig.setTermId("testAtpId1");
        orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        orig.setName("my name");
        CourseOfferingInfo info = courseOfferingService.createCourseOffering(orig.getCourseId(), orig.getTermId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(course.getSubjectArea(), info.getSubjectArea());
        assertEquals(course.getCourseNumberSuffix(), info.getCourseNumberSuffix());
        assertEquals(course.getCourseTitle(), info.getCourseTitle());
        assertEquals(course.getSubjectArea(), info.getSubjectArea());
        assertEquals(course.getCode(), info.getCourseOfferingCode());
        if (course.getDescr() != null) {
            assertEquals(new R1ToR2CopyHelper().copyRichText(course.getDescr()).getPlain(), info.getDescr().getPlain());
            assertEquals(new R1ToR2CopyHelper().copyRichText(course.getDescr()).getFormatted(), info.getDescr().getFormatted());
        }
        // TODO: test for these things 
//        assertEquals(course.getUnitsContentOwner(), info.getUnitsContentOwner());
//        assertEquals(course.getUnitsDeployment(), info.getUnitsDeployment());
//        assertEquals(course.getGradingOptions(), info.getGradingOptionKeys());
//        assertEquals(course.getCreditOptions(), info.getCreditOptions());
//        assertEquals(new R1ToR2CopyHelper().copyCourseExpenditure(course.getExpenditure()), info.getExpenditure());
//        assertEquals(new R1ToR2CopyHelper().copyCourseFeeList(course.getFees()), info.getFees());
//        assertEquals(new R1ToR2CopyHelper().copyInstructors(course.getInstructors()), info.getInstructors());


        // refetch co 
        orig = info;
        info = courseOfferingService.getCourseOffering(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());

        // update co
        orig = info;
        orig.setIsHonorsOffering(true);
        orig.setMaximumEnrollment(40);
        orig.setMinimumEnrollment(10);
        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
//        OfferingInstructorInfo instructor = new OfferingInstructorInfo();
//        instructor.setPersonId("Pers-1");
//        instructor.setPercentageEffort(Float.valueOf("60"));
//        instructor.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
//        instructor.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
        // TODO: add this back in and test for it
//        instructors.add(instructor);
        orig.setInstructors(instructors);
        info = courseOfferingService.updateCourseOffering(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getIsHonorsOffering(), info.getIsHonorsOffering());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getInstructors().size(), info.getInstructors().size());
//        OfferingInstructorInfo origInst1 = orig.getInstructors().get(0);
//        OfferingInstructorInfo infoInst1 = info.getInstructors().get(0);
//        assertEquals(origInst1.getPersonId(), infoInst1.getPersonId());
//        assertEquals(origInst1.getPercentageEffort(), infoInst1.getPercentageEffort());
//        assertEquals(origInst1.getTypeKey(), infoInst1.getTypeKey());
//        assertEquals(origInst1.getStateKey(), infoInst1.getStateKey());
        return info;
    }

    private void testDeletes(CourseOfferingInfo co, FormatOfferingInfo fo, ActivityOfferingInfo ao)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DependentObjectsExistException {

        // delete activity offering
        StatusInfo status = this.courseOfferingService.deleteActivityOffering(ao.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            ao = courseOfferingService.getActivityOffering(ao.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }


        // delete fo
        status = this.courseOfferingService.deleteFormatOffering(fo.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            fo = courseOfferingService.getFormatOffering(fo.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }

        // delete co
        status = this.courseOfferingService.deleteCourseOffering(co.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            co = courseOfferingService.getCourseOffering(co.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    public FormatOfferingInfo testCRUDFormatOffering(CourseOfferingInfo co)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException,
            AlreadyExistsException, VersionMismatchException, DependentObjectsExistException {
        FormatOfferingInfo orig = new FormatOfferingInfo();
        orig.setCourseOfferingId(co.getId());
        orig.setFormatId("COURSE1-FORMAT1");
        orig.setActivityOfferingTypeKeys(Arrays.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        FormatOfferingInfo info = courseOfferingService.createFormatOffering(orig.getCourseOfferingId(), orig.getFormatId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());
        // TODO: turn these tests back on once we get the corresponding JPA entities working 
//        assertEquals(orig.getActivityOfferingTypeKeys().size(), info.getActivityOfferingTypeKeys().size());
//        assertEquals(orig.getActivityOfferingTypeKeys().get(0), info.getActivityOfferingTypeKeys().get(0));

        orig = info;
        info = courseOfferingService.getFormatOffering(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());
//        assertEquals(orig.getActivityOfferingTypeKeys().size(), info.getActivityOfferingTypeKeys().size());
//        assertEquals(orig.getActivityOfferingTypeKeys().get(0), info.getActivityOfferingTypeKeys().get(0));

        orig = info;
        orig.setActivityOfferingTypeKeys(Arrays.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        info = courseOfferingService.updateFormatOffering(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());
//        assertEquals(orig.getActivityOfferingTypeKeys().size(), info.getActivityOfferingTypeKeys().size());
//        assertEquals(orig.getActivityOfferingTypeKeys().get(0), info.getActivityOfferingTypeKeys().get(0));
        return info;
    }

    public ActivityOfferingInfo testCRUDActivityOffering(FormatOfferingInfo fo)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException,
            AlreadyExistsException, VersionMismatchException {

        ActivityOfferingInfo orig = new ActivityOfferingInfo();
        orig.setFormatOfferingId(fo.getId());
        orig.setActivityId(fo.getId() + "." + LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);
        orig.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        orig.setMinimumEnrollment(100);
        orig.setMaximumEnrollment(150);
        ActivityOfferingInfo info = courseOfferingService.createActivityOffering(orig.getFormatOfferingId(), orig.getActivityId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());

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

        orig = info;
        orig.setMinimumEnrollment(100);
        info = courseOfferingService.updateActivityOffering(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());;
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        return info;

    }
}
