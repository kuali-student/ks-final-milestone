package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.acal.util.MockAcalTestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
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
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    @Resource(name = "schedulingService")
    private SchedulingService schedulingService;
    @Resource(name = "atpService")
    private AtpService atpService;

    private CourseInfo course;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);

        try {

            MockAcalTestDataLoader acalLoader = new MockAcalTestDataLoader(this.acalService);
            acalLoader.loadTerm("2011SP", "Spring 2011", "2011-03-01 00:00:00.0", "2011-05-31 00:00:00.0", AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring Term 2011"
            );
            acalLoader.loadTerm("2011FA", "Fall 2011", "2011-09-01 00:00:00.0", "2011-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall Term 2011"
            );
            acalLoader.loadTerm("2012SP", "Spring 2012", "2012-03-01 00:00:00.0", "2012-05-31 00:00:00.0", AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring Term 2012"
            );
            acalLoader.loadTerm("2012FA", "Fall 2012", "2012-09-01 00:00:00.0", "2012-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall Term 2012"
            );
            acalLoader.loadTerm("2013SP", "Spring 2013", "2013-03-01 00:00:00.0", "2013-05-31 00:00:00.0", AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring Term 2013"
            );
            acalLoader.loadTerm("2013FA", "Fall 2013", "2013-09-01 00:00:00.0", "2013-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall Term 2013"
            );

            CourseR1TestDataLoader courseLoader = new CourseR1TestDataLoader(this.courseService);
            courseLoader.loadCourse("COURSE1", "2012FA", "CHEM", "CHEM123", "Chemistry 123", "description 1", "COURSE1-FORMAT1",
                    LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY);
            courseLoader.loadCourse("COURSE2", "2012SP", "ENG", "ENG101", "Intro English", "description 2", "COURSE2-FORMAT1",
                    LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, null);

            AtpInfo atp = new AtpInfo();
            atp.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
            atp.setId("2011FA");
            atpService.createAtp(atp.getTypeKey(),atp,callContext);
            atp = new AtpInfo();
            atp.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
            atp.setId("2012FA");
            atpService.createAtp(atp.getTypeKey(),atp,callContext);

            course = courseService.getCourse("COURSE1", ContextUtils.getContextInfo());
        } catch( Exception e ) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testRollover() throws Exception {

        try {

                TermInfo sourceTerm = acalService.getTerm( "2012FA", callContext );
                CourseOfferingInfo sourceCo = createSourceCo( sourceTerm );
                FormatOfferingInfo sourceFo = createSourceFo( sourceCo );
                ActivityOfferingInfo sourceAo = createSourceAo( sourceCo, sourceFo );

                // rollover using AO that has ADLs
                TermInfo targetTerm = acalService.getTerm("2013SP", callContext);
                String idOfNewCo_1 = performRollover_withADLs( sourceCo, targetTerm );
                CourseOfferingInfo newCo_1 = validateData( sourceCo, sourceFo, sourceAo, targetTerm, idOfNewCo_1 );

                // rollover using AO that does NOT have ADLs
                targetTerm = acalService.getTerm( "2013FA", callContext );
                String idOfNewCo_2 = performRollover_withoutADLs( newCo_1, targetTerm );
                validateData( sourceCo, sourceFo, sourceAo, targetTerm, idOfNewCo_2 );

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }

    }

    private CourseOfferingInfo createSourceCo( TermInfo sourceTerm ) throws Exception {
        CourseOfferingInfo sourceCo = new CourseOfferingInfo();
        sourceCo.setCourseId(course.getId());
        sourceCo.setTermId(sourceTerm.getId());
        sourceCo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        sourceCo.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        sourceCo = coService.createCourseOffering(sourceCo.getCourseId(), sourceCo.getTermId(),
                                                  sourceCo.getTypeKey(), sourceCo,
                                                  new ArrayList<String>(),  // the "option-keys"
                                                  callContext);

        return sourceCo;
    }

    private FormatOfferingInfo createSourceFo( CourseOfferingInfo sourceCo ) throws Exception {
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

        return sourceFo;
    }

    private ActivityOfferingInfo createSourceAo( CourseOfferingInfo sourceCo, FormatOfferingInfo sourceFo ) throws Exception {
        FormatInfo format = course.getFormats().get(0);

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

        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        scheduleInfo.setAtpId(sourceCo.getTermId());
        scheduleInfo=schedulingService.createSchedule(scheduleInfo.getTypeKey(), scheduleInfo, callContext);
        List<String> sourceScheduleIds = new ArrayList<>();
        sourceScheduleIds.add(scheduleInfo.getId());
        sourceAo.setScheduleIds(sourceScheduleIds);
        sourceAo = coService.createActivityOffering(sourceAo.getFormatOfferingId(), sourceAo.getActivityId(), sourceAo.getTypeKey(), sourceAo, callContext);

        return sourceAo;
    }

    private String performRollover_withADLs( CourseOfferingInfo sourceCo, TermInfo targetTerm ) throws Exception {


        SocRolloverResultItemInfo item = coService.rolloverCourseOffering( sourceCo.getId(),
                                                                           targetTerm.getId(),
                                                                           new ArrayList<String>(),     // "option-keys"
                                                                           callContext);

        return item.getTargetCourseOfferingId();
    }

    private String performRollover_withoutADLs( CourseOfferingInfo sourceCo, TermInfo targetTerm ) throws Exception {

        SocRolloverResultItemInfo item = coService.rolloverCourseOffering( sourceCo.getId(),
                                                                           targetTerm.getId(),
                                                                           new ArrayList<String>(),     // the "option-keys"
                                                                           callContext );

        return item.getTargetCourseOfferingId();
    }

    private CourseOfferingInfo validateData( CourseOfferingInfo sourceCo, FormatOfferingInfo sourceFo, ActivityOfferingInfo sourceAo, TermInfo targetTerm, String idOfTargetCoToValidate ) throws Exception {

        CourseOfferingInfo targetCo = coService.getCourseOffering( idOfTargetCoToValidate, callContext);
        assertNotNull(targetCo);
        assertEquals(sourceCo.getCourseId(), targetCo.getCourseId());
        assertEquals(targetTerm.getId(), targetCo.getTermId());
        assertEquals(sourceCo.getTypeKey(), targetCo.getTypeKey());
        assertEquals(sourceCo.getCourseOfferingTitle(), targetCo.getCourseOfferingTitle());

        List<FormatOfferingInfo> targetFos = coService.getFormatOfferingsByCourseOffering( targetCo.getId(), callContext );
        assertEquals( 1, targetFos.size());
        FormatOfferingInfo targetFo = targetFos.get(0);
        assertEquals( sourceFo.getFormatId(), targetFo.getFormatId() );
        assertEquals( targetTerm.getId(), targetFo.getTermId() );
        assertEquals( sourceFo.getTypeKey(), targetFo.getTypeKey() );
        assertEquals( sourceFo.getName(), targetFo.getName() );

        List<ActivityOfferingInfo> targetAos = coService.getActivityOfferingsByFormatOffering( targetFo.getId(), callContext );
        assertEquals( 1, targetAos.size() );
        ActivityOfferingInfo targetAo = targetAos.get(0);
        assertEquals( sourceAo.getActivityId(), targetAo.getActivityId() );
        assertEquals( targetTerm.getId(), targetAo.getTermId() );
        assertEquals( sourceAo.getTypeKey(), targetAo.getTypeKey() );
        assertEquals( sourceAo.getName(), targetAo.getName() );
        assertTrue( targetAo.getScheduleIds().isEmpty() );      // after a rollover, target should not have ADLs

        return targetCo;
    }


}
