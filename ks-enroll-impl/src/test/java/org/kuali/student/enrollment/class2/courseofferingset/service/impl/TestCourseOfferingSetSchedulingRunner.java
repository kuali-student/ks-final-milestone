/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the SOC scheduling runner
 *
 * @author andrewlubbers
 */
@Ignore  //(fix me: ksenroll-4355)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-runner-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingSetSchedulingRunner {

    @Resource(name = "socService")
    private CourseOfferingSetService socService;

    @Resource(name = "coService")
    private CourseOfferingService coService;

    @Resource(name = "schedulingService")
    private SchedulingService schedulingService;

    @Resource
    private AcademicCalendarService acalService;

    @Resource
    private AtpService atpService;

    @Resource
    private CourseService canonicalCourseService;

    @Resource
    private CourseOfferingSetSchedulingRunnerDataLoader dataLoader;

    public static String principalId = "123";
    public ContextInfo callContext = null;
    private String socId;


    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);

        dataLoader.setContext(callContext);

        try {
            dataLoader.beforeTest();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        socId = dataLoader.getSocId();
    }



    @Test
    public void testSchedulingRunner() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        CourseOfferingSetSchedulingRunner runner = new CourseOfferingSetSchedulingRunner(socId);

        runner.setContextInfo(callContext);
        runner.setCoService(coService);
        runner.setSocService(socService);
        runner.setSchedulingService(schedulingService);

        System.out.println("RUNNER RUNNING");
        runner.run();
        System.out.println("RUNNER COMPLETED");

        // assert data was populated into the AO that should have a schedule
        ActivityOfferingInfo scheduledAo = coService.getActivityOffering(CourseOfferingSetSchedulingRunnerDataLoader.SCHEDULED_AO_ID, callContext);
        assertTrue( !scheduledAo.getScheduleIds().isEmpty() );
        assertEquals(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY, scheduledAo.getSchedulingStateKey());

        /* While integrating the SSR work, the following line was preventing compilation.  But this test has been broken
         * for many months and marked @Ignore (see top of class) with a jira noting the issue. Since this
         * doesn't exactly fall in the scope of the SSR work, I've fix this only enough to allow the code to compile.
         * This test is still probably broken and should be fixed.
         *
         * See jira KSENROLL-4355
         */
        //ScheduleInfo schedule = schedulingService.getSchedule(scheduledAo.getScheduleId(), callContext);
        ScheduleInfo schedule = schedulingService.getSchedule(scheduledAo.getScheduleIds().get(0), callContext);

        assertNotNull(schedule);
        assertEquals(2, schedule.getScheduleComponents().size());

        // should be one component that is TBA, and one that isn't
        ScheduleComponentInfo tbaComp = null, nonTbaComp = null;
        for (ScheduleComponentInfo sci : schedule.getScheduleComponents()) {
            if(sci.getIsTBA()) {
                tbaComp = sci;
            }
            else {
                nonTbaComp = sci;
            }
        }

        assertNotNull(tbaComp);
        assertNotNull(nonTbaComp);

        assertFalse(nonTbaComp.getIsTBA());
        assertEquals(1, nonTbaComp.getTimeSlotIds().size());
        assertTrue(nonTbaComp.getTimeSlotIds().contains(CourseOfferingSetSchedulingRunnerDataLoader.TIME_SLOT_1_ID));
        assertEquals(CourseOfferingSetSchedulingRunnerDataLoader.ROOM_ID, nonTbaComp.getRoomId());

        assertTrue(tbaComp.getIsTBA());
        assertTrue(tbaComp.getTimeSlotIds().contains(CourseOfferingSetSchedulingRunnerDataLoader.TIME_SLOT_2_ID));
        assertEquals(CourseOfferingSetSchedulingRunnerDataLoader.ROOM_ID, tbaComp.getRoomId());

        // assert the schedule request still exists for the scheduled AO
        List<String> requestIds = schedulingService.getScheduleRequestIdsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, scheduledAo.getId(), callContext);
        assertEquals(1, requestIds.size());

        // assert data was populated into the AO that should be exempt
        ActivityOfferingInfo exemptAo = coService.getActivityOffering(CourseOfferingSetSchedulingRunnerDataLoader.EXEMPT_AO_ID, callContext);
        assertEquals(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY, exemptAo.getSchedulingStateKey());
        assertTrue( !exemptAo.getScheduleIds().isEmpty() );

        /* While integrating the SSR work, the following line was preventing compilation.  But this test has been broken
        * for many months and marked @Ignore (see top of class) with a jira noting the issue. Since this
        * doesn't exactly fall in the scope of the SSR work, I've fix this only enough to allow the code to compile.
        * This test is still probably broken and should be fixed.
        *
        * See jira KSENROLL-4355
        */
        // ScheduleInfo exemptSchedule = schedulingService.getSchedule(exemptAo.getScheduleId(), callContext);
        ScheduleInfo exemptSchedule = schedulingService.getSchedule(exemptAo.getScheduleIds().get(0), callContext);

        assertNotNull(exemptSchedule);
        assertFalse(exemptSchedule.getScheduleComponents().isEmpty());
        ScheduleComponentInfo exemptComponent = exemptSchedule.getScheduleComponents().get(0);
        // TBA should be true for this component
        assertTrue(exemptComponent.getIsTBA());
        assertEquals(1, exemptComponent.getTimeSlotIds().size());
        assertTrue(exemptComponent.getTimeSlotIds().contains(CourseOfferingSetSchedulingRunnerDataLoader.TIME_SLOT_2_ID));
        assertEquals(CourseOfferingSetSchedulingRunnerDataLoader.ROOM_ID, exemptComponent.getRoomId());

        // assert the schedule request still exists for the exempt AO
        requestIds = schedulingService.getScheduleRequestIdsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, exemptAo.getId(), callContext);
        assertEquals(1, requestIds.size());


        // Assert that schedule data was NOT populated in the draft AO
        ActivityOfferingInfo draftAo = coService.getActivityOffering(CourseOfferingSetSchedulingRunnerDataLoader.DRAFT_AO_ID, callContext);
        assertEquals(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY, draftAo.getSchedulingStateKey());
        assertTrue( !draftAo.getScheduleIds().isEmpty() );

        // assert one schedule request still exists for the draft ao
        List<ScheduleRequestInfo> draftRequests = schedulingService.getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, draftAo.getId(), callContext);
        assertEquals(1, draftRequests.size());
        ScheduleRequestInfo draftReq = draftRequests.get(0);

        /* While integrating the SSR work, the following line was preventing compilation.  But this test has been broken
        * for many months and marked @Ignore (see top of class) with a jira noting the issue. Since this
        * doesn't exactly fall in the scope of the SSR work, I've fix this only enough to allow the code to compile.
        * This test is still probably broken and should be fixed.
        *
        * See jira KSENROLL-4355
        */
        // assertEquals(draftAo.getId(), draftReq.getRefObjectId());

        assertEquals(1, draftReq.getScheduleRequestComponents().size());
        ScheduleRequestComponentInfo draftReqComp = draftReq.getScheduleRequestComponents().get(0);
        assertEquals(false, draftReqComp.getIsTBA());
        assertTrue(draftReqComp.getTimeSlotIds().contains(CourseOfferingSetSchedulingRunnerDataLoader.TIME_SLOT_1_ID));
    }


}
