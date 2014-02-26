package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
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
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.SchedulingServiceDataLoader;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-mocks-context.xml"})
public class TestCourseOfferingServiceImplWithClass1Mocks {

    public static String principalId = "123";
    public ContextInfo callContext = new ContextInfo();

    @Resource(name = "coService")
    protected CourseOfferingService courseOfferingService;
    @Resource(name = "courseService")
    protected CourseService courseService;
    @Resource(name = "stateService")
    protected StateService stateService;
    @Resource(name = "typeService")
    protected TypeService typeService;
    @Resource(name = "luiService")
    protected LuiService luiService;
    @Resource(name = "acalService")
    protected AcademicCalendarService acalService;
    @Resource(name = "atpService")
    protected AtpService atpService;
    @Resource(name = "LrcService")
    protected LRCService lrcService;
    @Resource(name = "schedulingService")
    protected SchedulingService schedulingService;
    @Resource(name = "schedulingServiceDataLoader")
    private SchedulingServiceDataLoader schedulingServiceDataLoader;

    @Before
    public void setUp() throws Exception {
        callContext.setPrincipalId(principalId);
        new CourseR1TestDataLoader(this.courseService).loadData();
        new LuiServiceDataLoader(this.luiService).loadData();

        // due to KSENROLL-4185, data must be loaded into the mock Atp and mock Acal services
        AcalTestDataLoader acalLoader = new AcalTestDataLoader(this.atpService);
        acalLoader.loadTerm("testAtpId1", "test1", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "description 1");
        new MockAcalTestDataLoader(this.acalService).loadData();
        new MockLrcTestDataLoader(this.lrcService).loadData();

        createStateTestData();
        createSchedulingServiceData();
    }

    protected void createSchedulingServiceData() throws Exception {
        schedulingServiceDataLoader.loadData();
    }

    protected void createStateTestData() throws Exception {

        // ActivityOffering state
        LifecycleInfo aoLifecycle = addLifecycle( LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY );
        addState( aoLifecycle, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, true );

        // FormatOffering state
        LifecycleInfo foLifecycle = addLifecycle( LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY );
        addState( foLifecycle, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, true );

        // CourseOffering state
        LifecycleInfo coLifecycle = addLifecycle( LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY );
        addState( coLifecycle, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY, true );
    }

    private LifecycleInfo addLifecycle( String name ) throws Exception {

        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> lifecycle for testing purposes");
        rti.setPlain("Plain lifecycle for testing purposes");
        origLife.setDescr(rti);
        origLife.setKey( name );
        origLife.setName( "TEST_NAME" );
        origLife.setRefObjectUri( "TEST_URI" );
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origLife.getAttributes().add(attr);

        return stateService.createLifecycle(origLife.getKey(), origLife, callContext);
    }

    private StateInfo addState( LifecycleInfo lifecycleInfo, String state, boolean isInitialState ) throws Exception {

        StateInfo orig = new StateInfo();
        orig.setKey(state);
        orig.setLifecycleKey(lifecycleInfo.getKey());
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> state for testing purposes");
        rti.setPlain("Plain state again for testing purposes");
        orig.setDescr(rti);
        orig.setName("Testing state");
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        orig.setIsInitialState(isInitialState);

        return stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
    }

    @Test
    public void testCRUD() throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException,
            DependentObjectsExistException {
        CourseOfferingInfo co = this.testCRUDCourseOffering();
        FormatOfferingInfo fo = this.testCRUDFormatOffering(co);
        ActivityOfferingInfo ao = this.testCRUDActivityOffering(fo);

        searchForTimeSlots(ao);

        this.testDeletes(co, fo, ao);
    }

    public void searchForTimeSlots(ActivityOfferingInfo ao) throws PermissionDeniedException, MissingParameterException,
            InvalidParameterException, OperationFailedException, DoesNotExistException {
        /*
         *  Search for time slots for an AO in a Fall term, with days == Tues, Thurs, and start time == 8am
         */
        String aoId = ao.getId();
        List<Integer> days = new ArrayList<Integer>();
        days.add(3);
        days.add(5);
        Collections.sort(days);  //  Make the order predictable.

        TimeOfDayInfo startTime = new TimeOfDayInfo(8, 0); // 8 AM (military)
        //  Define some end times (in military)
        TimeOfDayInfo endTime850 =  new TimeOfDayInfo(8, 50);
        TimeOfDayInfo endTime910 =  new TimeOfDayInfo(9, 10);
        TimeOfDayInfo endTime950 =  new TimeOfDayInfo(9, 50);
        TimeOfDayInfo endTime1050 =  new TimeOfDayInfo(10, 50);
        TimeOfDayInfo endTime1110 =  new TimeOfDayInfo(11, 10);

        //  Search
        List<TimeSlotInfo> timeSlots = courseOfferingService
                .getAllowedTimeSlotsByDaysAndStartTimeForActivityOffering(aoId, days, startTime, ContextUtils.createDefaultContextInfo());

        assertEquals(3, timeSlots.size());

        //  Make the time slot order predictable.
        Collections.sort(timeSlots, SchedulingServiceUtil.makeTimeSlotComparator());

        //  Type, days, start time should be the same
        for (TimeSlotInfo ts : timeSlots) {
            assertEquals(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL, ts.getTypeKey());
            List<Integer> tsDays = new ArrayList<Integer>();
            tsDays.addAll(ts.getWeekdays());
            Collections.sort(tsDays);
            assertEquals(days.toString(), tsDays.toString());
            assertEquals(startTime, ts.getStartTime());
        }

        //  Check end times.
        TimeSlotInfo endTime = timeSlots.get(0);
        assertEquals(endTime850, endTime.getEndTime());
        endTime = timeSlots.get(1);
        assertEquals(endTime910, endTime.getEndTime());
        endTime = timeSlots.get(2);
        assertEquals(endTime950, endTime.getEndTime());

        /*
         *  Search for time slots for an AO in a Fall term, with days == MWF, and start time == 10am
         */
        startTime = new TimeOfDayInfo(10, 0); // 10 AM

        days = new ArrayList<Integer>();
        days.add(2);
        days.add(4);
        days.add(6);
        Collections.sort(days);

        timeSlots = courseOfferingService
                .getAllowedTimeSlotsByDaysAndStartTimeForActivityOffering(aoId, days, startTime, ContextUtils.createDefaultContextInfo());

        assertEquals(2, timeSlots.size());

        //  Type, days, start time should be the same
        for (TimeSlotInfo ts : timeSlots) {
            assertEquals(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL, ts.getTypeKey());
            List<Integer> tsDays = new ArrayList<Integer>();
            tsDays.addAll(ts.getWeekdays());
            Collections.sort(tsDays);
            assertEquals(days.toString(), tsDays.toString());
            assertEquals(startTime, ts.getStartTime());
        }

        //  Check end times.
        endTime = timeSlots.get(0);
        assertEquals(endTime1050, endTime.getEndTime());
        endTime = timeSlots.get(1);
        assertEquals(endTime1110, endTime.getEndTime());
    }

    private CourseOfferingInfo testCRUDCourseOffering() throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // get course
        CourseInfo course;
        course = courseService.getCourse("COURSE1", ContextUtils.getContextInfo());
        // create co from course
        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo orig = new CourseOfferingInfo();
        orig.setCourseId(course.getId());
        orig.setTermId("testAtpId1");
        orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        orig.setCourseOfferingTitle("my name");
        orig.setWaitlistLevelTypeKey("waitlist key");
        orig.setHasWaitlist(true);
        orig.setFinalExamType(FinalExam.STANDARD.toString());
        orig.setIsEvaluated(true);
        orig.setIsFeeAtActivityOffering(false);
        orig.setFundingSource("funding source");
        orig.setCourseOfferingCode("CODE");
        orig.setCourseOfferingTitle("Title");

        orig.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
        orig.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        orig.setGradingOptionId(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);

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
//        assertEquals(course.getCourseNumberSuffix(), info.getCourseNumberSuffix());
        assertEquals(course.getSubjectArea(), info.getSubjectArea());
        if (course.getDescr() != null) {
            assertEquals(course.getDescr().getPlain(), info.getDescr().getPlain());
            assertEquals(course.getDescr().getFormatted(), info.getDescr().getFormatted());
        }
//        assertEquals(2,info.getStudentRegistrationOptionIds().size());
//        assertTrue(info.getStudentRegistrationOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT));
//        assertTrue(info.getStudentRegistrationOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL));
//
//        assertEquals(2,info.getGradingOptionIds().size());
//        assertTrue(info.getGradingOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER));
//        assertTrue(info.getGradingOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE));

        // TODO: test for these things 
//        assertEquals(course.getUnitsContentOwnerOrgIds(), info.getUnitsContentOwnerOrgIds());
//        assertEquals(course.getUnitsDeploymentOrgIds(), info.getUnitsDeploymentOrgIds());


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
//        instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
//        instructor.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
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
            courseOfferingService.getActivityOffering(ao.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertEquals(ao.getId(), ex.getMessage());
        }

        // delete fo
        status = this.courseOfferingService.deleteFormatOffering(fo.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            courseOfferingService.getFormatOffering(fo.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertEquals(fo.getId(), ex.getMessage());
        }

        // delete co
        status = this.courseOfferingService.deleteCourseOffering(co.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            courseOfferingService.getCourseOffering(co.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertEquals(co.getId(), ex.getMessage());
        }
    }

    private FormatOfferingInfo testCRUDFormatOffering(CourseOfferingInfo co)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException,
                   VersionMismatchException, ReadOnlyException, DependentObjectsExistException {
        FormatOfferingInfo orig = new FormatOfferingInfo();
        orig.setCourseOfferingId(co.getId());
        orig.setFormatId("COURSE1-FORMAT1");
        orig.setActivityOfferingTypeKeys(Arrays.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
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

        List<FormatOfferingInfo> formats = courseOfferingService.getFormatOfferingsByCourseOffering(co.getId(), callContext);
        info = formats.get(0);
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
        orig.setScheduleIds(generateScheduleIdList("testScheduleId1", "testScheduleId2", "testScheduleId3"));


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
        compareList(orig.getScheduleIds(), 3, info.getScheduleIds());

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
        compareList(orig.getScheduleIds(), 3, info.getScheduleIds());

        orig = info;
        orig.setMinimumEnrollment(100);
        orig.getScheduleIds().add("testScheduleId4");
        info = courseOfferingService.updateActivityOffering(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        compareList(orig.getScheduleIds(), 4, info.getScheduleIds());
        return info;
    }

    private void compareList(List<String> expectedList, int expectedSize, List<String> actualList) {
        assertEquals(expectedSize, actualList.size());
        for(String expected : expectedList) {
            assertTrue(actualList.contains(expected));
        }
    }

    private List<String> generateScheduleIdList(String... ids) {
        List<String> scheduleIds = new ArrayList<String>();
        for(String id : ids) {
            scheduleIds.add(id);
        }
        return scheduleIds;
    }
}
