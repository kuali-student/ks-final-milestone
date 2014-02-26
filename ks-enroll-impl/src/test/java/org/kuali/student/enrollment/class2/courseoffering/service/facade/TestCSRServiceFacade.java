package org.kuali.student.enrollment.class2.courseoffering.service.facade;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests CSRServiceFacadeImpl.java
 *
 * @author Kuali Student Team
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:csr-services-facade-test-class2-mock-context.xml"})
public class TestCSRServiceFacade {

   @Resource(name = "CourseOfferingService")
    private CourseOfferingService coService;

    @Resource(name="typeService")
    private TypeService typeService;


   @Resource(name="socService")
    private CourseOfferingSetService socService;

    @Resource
    private CSRServiceFacadeImpl csrServiceFacade;


    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    @Resource
    protected CourseService canonicalCourseService;

    private final boolean testAwareDataLoader;
    private ContextInfo contextInfo;

    /**
     *
     */
    public TestCSRServiceFacade() {
        this(true);
    }


    protected TestCSRServiceFacade(boolean testAwareDataLoader) {
        this.testAwareDataLoader = testAwareDataLoader;
    }

    @Before
    public void setup() throws Exception {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("123");

        if (testAwareDataLoader || !dataLoader.isInitialized()) {
            dataLoader.beforeTest();
        }
    }

    @After
    public void tearDown() throws Exception {
            dataLoader.afterTest();
    }

    private void createActivityOffering() throws Exception {

        CourseOfferingInfo courseOffering = coService.getCourseOffering("CO-1", contextInfo);

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
        instructors.add(CourseOfferingServiceTestDataUtils.createInstructor(
                "Pers-1", "Person One", 60.00F));

        String activityId = CourseOfferingServiceTestDataUtils
                .createCanonicalActivityId("CO-1:LEC-ONLY",
                        LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);

        ActivityOfferingInfo ao = CourseOfferingServiceTestDataUtils
                .createActivityOffering("2012FA", courseOffering, "CO-1:LEC-ONLY",
                        new ArrayList<String>(Collections.singletonList("SCHED-ID")), activityId, "Lecture", "A",
                        LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
                        instructors);
        ActivityOfferingInfo created = coService.createActivityOffering(
                "CO-1:LEC-ONLY", activityId,
                LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, ao,
                contextInfo);

        ActivityOfferingInfo retrieved = coService.getActivityOffering(
                created.getId(), contextInfo);

        // test getActivityOfferingsByCourseOffering
        List<ActivityOfferingInfo> activities = coService
                .getActivityOfferingsByCourseOffering("CO-1", contextInfo);
    }


    @Test
    public void testCancelActivityOffering() throws Exception {
        createActivityOffering();
        ActivityOfferingInfo activityOfferingInfo = coService.getActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
       // Making sure the activity is not in canceled state
        assertTrue(!(activityOfferingInfo.getState().contains("canceled")));

        csrServiceFacade.cancelActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
        activityOfferingInfo = coService.getActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);

        // The activity is in canceled state after cancelActivityOffering call
       assertTrue((activityOfferingInfo.getState().contains("canceled")));

    }


    @Test
    public void testSuspendActivityOffering() throws Exception {
        createActivityOffering();
        ActivityOfferingInfo activityOfferingInfo = coService.getActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
        assertTrue(!(activityOfferingInfo.getState().contains("suspend")));

        csrServiceFacade.suspendActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);

        activityOfferingInfo = coService.getActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
        assertTrue((activityOfferingInfo.getState().contains("suspend")));
    }

    @Test
    public void testReinstateActivityOffering() throws Exception{

        createActivityOffering();
        ActivityOfferingInfo ao = coService.getActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);

        csrServiceFacade.suspendActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
        assertTrue(ao.getState().contains("suspend"));

        // AOs with out ADLs (scheduleIDs) -> AO goes into draft state
        csrServiceFacade.reinstateActivityOffering(ao, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS,contextInfo);
        assertTrue(ao.getState().contains("draft"));


        csrServiceFacade.cancelActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
        assertTrue(ao.getState().contains("cancel"));

        // setting ADLs (scheduleIDs) for AO
        ao.setScheduleIds(generateScheduleIdList("testScheduleId1", "testScheduleId2", "testScheduleId3"));

        // AOs with ADLs (scheduleIDs)
        csrServiceFacade.reinstateActivityOffering(ao, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY,contextInfo);
        assertTrue(ao.getState().contains("offered"));


        csrServiceFacade.reinstateActivityOffering(ao, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY,contextInfo);
        assertTrue(ao.getState().contains("approved"));

        csrServiceFacade.suspendActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
        assertTrue(ao.getState().contains("suspend"));

        csrServiceFacade.reinstateActivityOffering(ao, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY,contextInfo);
        assertTrue(ao.getState().contains("approved"));
    }


    // Ao with ADLs -> Error reinstating Activity offering in SOC : SOC_SCHEDULING_STATE_IN_PROGRESS
    @Test(expected=RuntimeException.class)
    public void TestReinstateActivityOfferingForException()throws Exception{

        createActivityOffering();
        ActivityOfferingInfo ao = coService.getActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);

        csrServiceFacade.suspendActivityOffering("CO-1:LEC-ONLY:LEC-A",contextInfo);
        assertTrue(ao.getState().contains("suspend"));

        // setting ADLs (scheduleIDs) for AO
        ao.setScheduleIds(generateScheduleIdList("testScheduleId1", "testScheduleId2", "testScheduleId3"));

        csrServiceFacade.reinstateActivityOffering(ao, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS,contextInfo);
    }


    private List<String> generateScheduleIdList(String... ids) {
        List<String> scheduleIds = new ArrayList<String>();
        for(String id : ids) {
            scheduleIds.add(id);
        }
        return scheduleIds;
    }
}
