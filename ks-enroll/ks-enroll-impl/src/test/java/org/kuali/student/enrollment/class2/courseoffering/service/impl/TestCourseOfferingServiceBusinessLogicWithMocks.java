package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.Arrays;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import javax.annotation.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-businesslogic-test-with-mocks-context.xml"})
public class TestCourseOfferingServiceBusinessLogicWithMocks {

    @Resource(name = "coService")
    private CourseOfferingService coService;
    public static String principalId = "123";
    public ContextInfo callContext = null;
    @Resource(name = "courseService")
    private CourseService courseService;
    @Resource(name = "acalService")
    private AcademicCalendarService acalService;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
//        try {
//            new CourseR1TestDataLoader(this.courseService).loadData();
//            new AcalTestDataLoader(this.acalService).loadData();
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }

    }

    @Test
    public void testRollover() throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException,
            DependentObjectsExistException, AlreadyExistsException {
        AcalTestDataLoader acalLoader = new AcalTestDataLoader(this.acalService);
        acalLoader.loadTerm("2011SP", "Spring 2011", "Spring Term 2011", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2011-03-01 00:00:00.0", "2011-05-31 00:00:00.0");
        acalLoader.loadTerm("2011FA", "Fall 2011", "Fall Term 2011", AtpServiceConstants.ATP_FALL_TYPE_KEY,
                "2011-09-01 00:00:00.0", "2011-12-31 00:00:00.0");
        acalLoader.loadTerm("2012SP", "Spring 2012", "Spring Term 2012", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2012-03-01 00:00:00.0", "2012-05-31 00:00:00.0");
        acalLoader.loadTerm("2012FA", "Fall 2012", "Fall Term 2012", AtpServiceConstants.ATP_FALL_TYPE_KEY,
                "2012-09-01 00:00:00.0", "2012-12-31 00:00:00.0");
        acalLoader.loadTerm("2013SP", "Spring 2013", "Spring Term 2013", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2013-03-01 00:00:00.0", "2013-05-31 00:00:00.0");

        CourseR1TestDataLoader courseLoader = new CourseR1TestDataLoader(this.courseService);
        courseLoader.loadCourse("COURSE1", "2012FA", "CHEM", "CHEM123", "Chemistry 123", "description 1", "COURSE1-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY);
        courseLoader.loadCourse("COURSE2", "2012SP", "ENG", "ENG101", "Intro English", "description 2", "COURSE2-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, null);
        // get course
        CourseInfo course;
        try {
            course = courseService.getCourse("COURSE1");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        TermInfo sourceTerm = acalService.getTerm("2012FA", callContext);
        // create co from course
        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo sourceCo = new CourseOfferingInfo();
        sourceCo.setCourseId(course.getId());
        sourceCo.setTermId(sourceTerm.getId());
        sourceCo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        sourceCo.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        sourceCo = coService.createCourseOffering(sourceCo.getCourseId(), sourceCo.getTermId(),
                sourceCo.getTypeKey(), sourceCo, optionKeys, callContext);
        
        FormatInfo format = course.getFormats().get(0);
        FormatOfferingInfo sourceFo = new FormatOfferingInfo ();
        sourceFo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        sourceFo.setStateKey(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        sourceFo.setCourseOfferingId(sourceCo.getId());
        sourceFo.setDescr(new RichTextHelper ().fromPlain ("test format offering"));
        sourceFo.setFormatId(format.getId());
        sourceFo.setName("format offering 1");
        sourceFo.setTermId(sourceCo.getTermId());
        sourceFo = coService.createFormatOffering(sourceFo.getCourseOfferingId(), sourceFo.getFormatId(),
                sourceFo.getTypeKey(), sourceFo, callContext);
        
        ActivityInfo activity = format.getActivities().get(0);
        ActivityOfferingInfo sourceAo = new ActivityOfferingInfo ();
        sourceAo.setFormatOfferingId(sourceFo.getId());
        sourceAo.setActivityId(activity.getId());
        sourceAo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        sourceAo.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        sourceAo.setActivityCode("A");
        sourceAo.setDescr(new RichTextHelper ().fromPlain("test activity"));
        sourceAo.setIsHonorsOffering(Boolean.TRUE);
        sourceAo.setMaximumEnrollment(100);
        sourceAo.setMinimumEnrollment(90);
        sourceAo.setName("my activity offering");
        sourceAo = coService.createActivityOffering(sourceAo.getFormatOfferingId(), sourceAo.getActivityId(),
                sourceAo.getTypeKey(), sourceAo, callContext);
        
        // now try to rollover to new term
        TermInfo targetTerm = acalService.getTerm("2013SP", callContext);
        CourseOfferingInfo targetCo = coService.rolloverCourseOffering(sourceCo.getId(), targetTerm.getId(), optionKeys,
                callContext);
        assertNotNull(targetCo);
        assertEquals(sourceCo.getCourseId(), targetCo.getCourseId());
        assertEquals(targetTerm.getId(), targetCo.getTermId());
// This test not relevant since state actually changes in rollover
//        assertEquals(sourceCo.getStateKey(), targetCo.getStateKey());
        assertEquals(sourceCo.getTypeKey(), targetCo.getTypeKey());
        assertEquals(sourceCo.getCourseOfferingTitle(), targetCo.getCourseOfferingTitle());
        
        List<FormatOfferingInfo> targetFos = coService.getFormatOfferingsByCourseOffering(targetCo.getId(), callContext);
        assertEquals (1, targetFos.size());
        FormatOfferingInfo targetFo = targetFos.get(0);
        assertEquals(sourceFo.getFormatId(), targetFo.getFormatId());
        assertEquals(targetTerm.getId(), targetFo.getTermId());
// This test not relevant since state actually changes in rollover
//        assertEquals(sourceFo.getStateKey(), targetFo.getStateKey());
        assertEquals(sourceFo.getTypeKey(), targetFo.getTypeKey());
        assertEquals(sourceFo.getName(), targetFo.getName());        
        
        List<ActivityOfferingInfo> targetAos = coService.getActivityOfferingsByFormatOffering(targetFo.getId(), callContext);
        assertEquals (1, targetAos.size());
        ActivityOfferingInfo targetAo = targetAos.get(0);
        assertEquals(sourceAo.getActivityId(), targetAo.getActivityId());
        assertEquals(targetTerm.getId(), targetAo.getTermId());
// This test not relevant since state actually changes in rollover
//        assertEquals(sourceAo.getStateKey(), targetAo.getStateKey());
        assertEquals(sourceAo.getTypeKey(), targetAo.getTypeKey());
        assertEquals(sourceAo.getName(), targetAo.getName());

    }
}
