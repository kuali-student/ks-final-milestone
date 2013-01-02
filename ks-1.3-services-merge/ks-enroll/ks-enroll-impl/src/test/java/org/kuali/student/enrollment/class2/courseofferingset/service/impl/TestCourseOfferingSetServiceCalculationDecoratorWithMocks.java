/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.AcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseR1TestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the calculation decorator using mock impls for persistence
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soc-calc-decorator-with-mocks-test-context.xml"})
public class TestCourseOfferingSetServiceCalculationDecoratorWithMocks {

    @Resource(name = "socCalcDecorator")
    private CourseOfferingSetService socService;
    @Resource(name = "coCalcDecorator")
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
            DependentObjectsExistException, AlreadyExistsException, InterruptedException {
        assertNotNull(callContext);
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

        TermInfo sourceTerm = acalService.getTerm("2012FA", callContext);
        // create co from course
        List<String> optionKeys = new ArrayList<String>();
        CourseInfo course1;
        try {
            course1 = courseService.getCourse("COURSE1");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        CourseOfferingInfo sourceCo1 = new CourseOfferingInfo();
        sourceCo1.setCourseId(course1.getId());
        sourceCo1.setTermId(sourceTerm.getId());
        sourceCo1.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        sourceCo1.setStateKey(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        sourceCo1 = coService.createCourseOffering(sourceCo1.getCourseId(), sourceCo1.getTermId(),
                sourceCo1.getTypeKey(), sourceCo1, optionKeys, callContext);

        FormatInfo format1 = course1.getFormats().get(0);
        FormatOfferingInfo sourceFo1 = new FormatOfferingInfo();
        sourceFo1.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        sourceFo1.setStateKey(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        sourceFo1.setCourseOfferingId(sourceCo1.getId());
        sourceFo1.setDescr(new RichTextHelper().fromPlain("test format offering"));
        sourceFo1.setFormatId(format1.getId());
        sourceFo1.setName("format offering 1");
        sourceFo1.setTermId(sourceCo1.getTermId());
        sourceFo1 = coService.createFormatOffering(sourceFo1.getCourseOfferingId(), sourceFo1.getFormatId(),
                sourceFo1.getTypeKey(), sourceFo1, callContext);

        ActivityInfo activity1 = format1.getActivities().get(0);
        ActivityOfferingInfo sourceAo1A = new ActivityOfferingInfo();
        sourceAo1A.setFormatOfferingId(sourceFo1.getId());
        sourceAo1A.setActivityId(activity1.getId());
        sourceAo1A.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        sourceAo1A.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        sourceAo1A.setActivityCode("A");
        sourceAo1A.setDescr(new RichTextHelper().fromPlain("test activity"));
        sourceAo1A.setIsHonorsOffering(Boolean.TRUE);
        sourceAo1A.setMaximumEnrollment(100);
        sourceAo1A.setMinimumEnrollment(90);
        sourceAo1A.setName("my activity offering");
        sourceAo1A = coService.createActivityOffering(sourceAo1A.getFormatOfferingId(), sourceAo1A.getActivityId(),
                sourceAo1A.getTypeKey(), sourceAo1A, callContext);

        ActivityOfferingInfo sourceAo1B = new ActivityOfferingInfo();
        sourceAo1B.setFormatOfferingId(sourceFo1.getId());
        sourceAo1B.setActivityId(activity1.getId());
        sourceAo1B.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        sourceAo1B.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        sourceAo1B.setActivityCode("B");
        sourceAo1B.setDescr(new RichTextHelper().fromPlain("test activity B"));
        sourceAo1B.setIsHonorsOffering(Boolean.TRUE);
        sourceAo1B.setMaximumEnrollment(100);
        sourceAo1B.setMinimumEnrollment(90);
        sourceAo1B.setName("my B activity offering");
        sourceAo1B = coService.createActivityOffering(sourceAo1B.getFormatOfferingId(), sourceAo1B.getActivityId(),
                sourceAo1B.getTypeKey(), sourceAo1B, callContext);

        // now create data for the 2nd course
        CourseInfo course2;
        try {
            course2 = courseService.getCourse("COURSE2");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        CourseOfferingInfo sourceCo2 = new CourseOfferingInfo();
        sourceCo2.setCourseId(course2.getId());
        sourceCo2.setTermId(sourceTerm.getId());
        sourceCo2.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        sourceCo2.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        sourceCo2 = coService.createCourseOffering(sourceCo2.getCourseId(), sourceCo2.getTermId(),
                sourceCo2.getTypeKey(), sourceCo2, optionKeys, callContext);

        FormatInfo format2 = course2.getFormats().get(0);
        FormatOfferingInfo sourceFo2 = new FormatOfferingInfo();
        sourceFo2.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        sourceFo2.setStateKey(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        sourceFo2.setCourseOfferingId(sourceCo2.getId());
        sourceFo2.setDescr(new RichTextHelper().fromPlain("test format offering"));
        sourceFo2.setFormatId(format2.getId());
        sourceFo2.setName("format offering 1");
        sourceFo2.setTermId(sourceCo2.getTermId());
        sourceFo2 = coService.createFormatOffering(sourceFo2.getCourseOfferingId(), sourceFo2.getFormatId(),
                sourceFo2.getTypeKey(), sourceFo2, callContext);

        ActivityInfo activity2 = format2.getActivities().get(0);
        ActivityOfferingInfo sourceAo2A = new ActivityOfferingInfo();
        sourceAo2A.setFormatOfferingId(sourceFo2.getId());
        sourceAo2A.setActivityId(activity2.getId());
        sourceAo2A.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        sourceAo2A.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        sourceAo2A.setActivityCode("A");
        sourceAo2A.setDescr(new RichTextHelper().fromPlain("test activity"));
        sourceAo2A.setIsHonorsOffering(Boolean.TRUE);
        sourceAo2A.setMaximumEnrollment(100);
        sourceAo2A.setMinimumEnrollment(90);
        sourceAo2A.setName("my activity offering");
        sourceAo2A = coService.createActivityOffering(sourceAo2A.getFormatOfferingId(), sourceAo2A.getActivityId(),
                sourceAo2A.getTypeKey(), sourceAo2A, callContext);

        ActivityOfferingInfo sourceAo2B = new ActivityOfferingInfo();
        sourceAo2B.setFormatOfferingId(sourceFo2.getId());
        sourceAo2B.setActivityId(activity2.getId());
        sourceAo2B.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        sourceAo2B.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        sourceAo2B.setActivityCode("B");
        sourceAo2B.setDescr(new RichTextHelper().fromPlain("test activity B"));
        sourceAo2B.setIsHonorsOffering(Boolean.TRUE);
        sourceAo2B.setMaximumEnrollment(100);
        sourceAo2B.setMinimumEnrollment(90);
        sourceAo2B.setName("my B activity offering");
        sourceAo2B = coService.createActivityOffering(sourceAo2B.getFormatOfferingId(), sourceAo2B.getActivityId(),
                sourceAo2B.getTypeKey(), sourceAo2B, callContext);

        // create the soc
        SocInfo sourceSoc = new SocInfo();
        sourceSoc.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        sourceSoc.setStateKey(CourseOfferingSetServiceConstants.ACTIVE_SOC_STATE_KEY);
        sourceSoc.setTermId(sourceTerm.getId());
        sourceSoc = socService.createSoc(sourceSoc.getTermId(), sourceSoc.getTypeKey(), sourceSoc, callContext);

        // now try to rollover to new term
        TermInfo targetTerm = acalService.getTerm("2013SP", callContext);
        optionKeys = new ArrayList<String>();
        optionKeys.add(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY);
        optionKeys.add(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY);
        SocInfo targetSoc = socService.rolloverSoc(sourceSoc.getId(), targetTerm.getId(), optionKeys, callContext);
        assertNotNull(targetSoc);
        assertEquals(targetTerm.getId(), targetSoc.getTermId());
        assertEquals(sourceSoc.getStateKey(), targetSoc.getStateKey());
        assertEquals(sourceSoc.getTypeKey(), targetSoc.getTypeKey());

        List<String> resultIds = socService.getSocRolloverResultIdsByTargetSoc(targetSoc.getId(), callContext);
        assertEquals(1, resultIds.size());
        SocRolloverResultInfo result = socService.getSocRolloverResult(resultIds.get(0), callContext);
        assertEquals(sourceSoc.getId(), result.getSourceSocId());
        assertEquals(targetSoc.getId(), result.getTargetSocId());
        assertEquals(targetTerm.getId(), result.getTargetTermId());
        compareStringList(optionKeys, result.getOptionKeys());
        assertNull(result.getMessage());
        for (int i = 0; i < 1000; i++) {
            if (result.getStateKey().equals(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY)) {
                break;
            }
            if (result.getStateKey().equals(CourseOfferingSetServiceConstants.ABORTED_RESULT_STATE_KEY)) {
                System.out.println("Batch job aborted");
                if (result.getMessage() != null) {
                    fail(result.getMessage().getPlain());
                    return;
                }
                fail("aborted no message");
                return;
            }
            System.out.println("Batch job has not completed " + result.getStateKey() + " items processed=" +
                    result.getItemsProcessed() +
                    ", expected=" + result.getItemsExpected() + " sleeping for 1ms and trying again");
            Thread.sleep(1);
            result = socService.getSocRolloverResult(result.getId(), callContext);
        }
        assertEquals(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY, result.getStateKey());
        assertEquals (sourceTerm.getId(), result.getSourceTermId());
        assertEquals (sourceSoc.getId(), result.getSourceSocId());
        assertEquals(new Integer(2), result.getItemsExpected());
        assertEquals(new Integer(2), result.getItemsProcessed());
        assertEquals (new Integer (2), result.getCourseOfferingsCreated());
        assertEquals (new Integer (0), result.getCourseOfferingsSkipped());
        assertEquals (targetTerm.getId(), result.getTargetTermId());
        assertEquals (targetSoc.getId(), result.getTargetSocId());
        assertNull(result.getMessage());
        List<SocRolloverResultItemInfo> items = socService.getSocRolloverResultItemsByResultId(result.getId(), callContext);
        assertEquals(2, items.size());

        // check the first item
        SocRolloverResultItemInfo item1 = findBySourceCourseOffering(items, sourceCo1.getId());
        assertEquals(result.getId(), item1.getSocRolloverResultId());
        assertEquals(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY, item1.getTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY, item1.getStateKey());
        assertEquals(sourceCo1.getId(), item1.getSourceCourseOfferingId());

        // now check the resulting co
        CourseOfferingInfo targetCo1 = coService.getCourseOffering(item1.getTargetCourseOfferingId(), callContext);
        assertNotNull(targetCo1);
        assertEquals(sourceCo1.getCourseId(), targetCo1.getCourseId());
        assertEquals(targetTerm.getId(), targetCo1.getTermId());
        assertEquals(sourceCo1.getStateKey(), targetCo1.getStateKey());
        assertEquals(sourceCo1.getTypeKey(), targetCo1.getTypeKey());
        assertEquals(sourceCo1.getCourseOfferingTitle(), targetCo1.getCourseOfferingTitle());

        List<FormatOfferingInfo> targetFo1s = coService.getFormatOfferingsByCourseOffering(targetCo1.getId(), callContext);
        assertEquals(1, targetFo1s.size());
        FormatOfferingInfo targetFo1 = targetFo1s.get(0);
        assertEquals(sourceFo1.getFormatId(), targetFo1.getFormatId());
        assertEquals(targetTerm.getId(), targetFo1.getTermId());
        assertEquals(sourceFo1.getStateKey(), targetFo1.getStateKey());
        assertEquals(sourceFo1.getTypeKey(), targetFo1.getTypeKey());
        assertEquals(sourceFo1.getName(), targetFo1.getName());

        List<ActivityOfferingInfo> targetAo1s = coService.getActivityOfferingsByFormatOffering(targetFo1.getId(), callContext);
        assertEquals(2, targetAo1s.size());
        ActivityOfferingInfo targetAo1A = findByCode(targetAo1s, "A");
        assertNotNull(targetAo1A);
        assertEquals(sourceAo1A.getActivityId(), targetAo1A.getActivityId());
        assertEquals(targetTerm.getId(), targetAo1A.getTermId());
        assertEquals(sourceAo1A.getStateKey(), targetAo1A.getStateKey());
        assertEquals(sourceAo1A.getTypeKey(), targetAo1A.getTypeKey());
        assertEquals(sourceAo1A.getName(), targetAo1A.getName());
        assertEquals(sourceAo1A.getActivityCode(), targetAo1A.getActivityCode());

        ActivityOfferingInfo targetAo1B = findByCode(targetAo1s, "B");
        assertNotNull(targetAo1B);
        assertEquals(sourceAo1B.getActivityId(), targetAo1B.getActivityId());
        assertEquals(targetTerm.getId(), targetAo1B.getTermId());
        assertEquals(sourceAo1B.getStateKey(), targetAo1B.getStateKey());
        assertEquals(sourceAo1B.getTypeKey(), targetAo1B.getTypeKey());
        assertEquals(sourceAo1B.getName(), targetAo1B.getName());
        assertEquals(sourceAo1B.getActivityCode(), targetAo1B.getActivityCode());

        // chec the 2nd item
        SocRolloverResultItemInfo item2 = findBySourceCourseOffering(items, sourceCo2.getId());
        assertEquals(result.getId(), item2.getSocRolloverResultId());
        assertEquals(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY, item2.getTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY, item2.getStateKey());
        assertEquals(sourceCo2.getId(), item2.getSourceCourseOfferingId());

        // now check the resulting co
        CourseOfferingInfo targetCo2 = coService.getCourseOffering(item2.getTargetCourseOfferingId(), callContext);
        assertNotNull(targetCo2);
        assertEquals(sourceCo2.getCourseId(), targetCo2.getCourseId());
        assertEquals(targetTerm.getId(), targetCo2.getTermId());
        assertEquals(sourceCo2.getStateKey(), targetCo2.getStateKey());
        assertEquals(sourceCo2.getTypeKey(), targetCo2.getTypeKey());
        assertEquals(sourceCo2.getCourseOfferingTitle(), targetCo2.getCourseOfferingTitle());

        List<FormatOfferingInfo> targetFo2s = coService.getFormatOfferingsByCourseOffering(targetCo2.getId(), callContext);
        assertEquals(1, targetFo2s.size());
        FormatOfferingInfo targetFo2 = targetFo2s.get(0);
        assertEquals(sourceFo2.getFormatId(), targetFo2.getFormatId());
        assertEquals(targetTerm.getId(), targetFo2.getTermId());
        assertEquals(sourceFo2.getStateKey(), targetFo2.getStateKey());
        assertEquals(sourceFo2.getTypeKey(), targetFo2.getTypeKey());
        assertEquals(sourceFo2.getName(), targetFo2.getName());

        List<ActivityOfferingInfo> targetAo2s = coService.getActivityOfferingsByFormatOffering(targetFo2.getId(), callContext);
        assertEquals(2, targetAo2s.size());
        ActivityOfferingInfo targetAo2A = findByCode(targetAo2s, "A");
        assertNotNull(targetAo2A);
        assertEquals(sourceAo2A.getActivityId(), targetAo2A.getActivityId());
        assertEquals(targetTerm.getId(), targetAo2A.getTermId());
        assertEquals(sourceAo2A.getStateKey(), targetAo2A.getStateKey());
        assertEquals(sourceAo2A.getTypeKey(), targetAo2A.getTypeKey());
        assertEquals(sourceAo2A.getName(), targetAo2A.getName());
        assertEquals(sourceAo2A.getActivityCode(), targetAo2A.getActivityCode());

        ActivityOfferingInfo targetAo2B = findByCode(targetAo2s, "B");
        assertNotNull(targetAo2B);
        assertEquals(sourceAo2B.getActivityId(), targetAo2B.getActivityId());
        assertEquals(targetTerm.getId(), targetAo2B.getTermId());
        assertEquals(sourceAo2B.getStateKey(), targetAo2B.getStateKey());
        assertEquals(sourceAo2B.getTypeKey(), targetAo2B.getTypeKey());
        assertEquals(sourceAo2B.getName(), targetAo2B.getName());
        assertEquals(sourceAo2B.getActivityCode(), targetAo2B.getActivityCode());
    }

    private ActivityOfferingInfo findByCode(List<ActivityOfferingInfo> list, String code) {
        for (ActivityOfferingInfo info : list) {
            if (code.equals(info.getActivityCode())) {
                return info;
            }
        }
        return null;
    }

    private SocRolloverResultItemInfo findBySourceCourseOffering(List<SocRolloverResultItemInfo> list, String sourceCourseOfferingId) {
        for (SocRolloverResultItemInfo info : list) {
            if (sourceCourseOfferingId.equals(info.getSourceCourseOfferingId())) {
                return info;
            }
        }
        return null;
    }

    private void compareStringList(List<String> list1, List<String> list2) {
        assertEquals(list1.size(), list2.size());
        List lst1 = new ArrayList(list1);
        Collections.sort(lst1);
        List lst2 = new ArrayList(list2);
        Collections.sort(lst2);
        for (int i = 0; i < lst1.size(); i++) {
            assertEquals(i + "", lst1.get(i), lst2.get(i));
        }
    }
}
