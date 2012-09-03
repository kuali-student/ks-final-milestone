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
 * Created by Mezba Mahtab on 9/3/12
 */
package org.kuali.student.r2.core.scheduling.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.test.util.CrudInfoTester;
import org.kuali.student.enrollment.test.util.ListOfObjectTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class tests Scheduling Service
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-mock-impl-test-context.xml"})
public class TestSchedulingServiceMockImpl {

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
    @Resource
    private SchedulingService schedulingService;
    public static String principalId1 = "123";
    public static String principalId2 = "321";
    public ContextInfo callContext = null;
    public CrudInfoTester crudInfoTester = null;

    @Before
    public void setUp() {
        principalId1 = "123";
        principalId1 = "321";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId1);
        crudInfoTester = new CrudInfoTester(principalId1, principalId2, callContext);
    }

    // test crud Schedule
    @Test
    public void testCrudSchedule () throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,Exception {
        // test create
        ScheduleInfo expected = new ScheduleInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        expected.setAtpId("124");
        List<String> timeSlotIds1 = new ArrayList<String>();
        timeSlotIds1.add("t10");
        timeSlotIds1.add("t11");
        List<String> timeSlotIds2 = new ArrayList<String>();
        timeSlotIds1.add("t21");
        timeSlotIds1.add("t22");
        ScheduleComponentInfo sci1 = new ScheduleComponentInfo();
        sci1.setId("1");
        sci1.setRoomId("r1");
        sci1.setTimeSlotIds(timeSlotIds1);
        ScheduleComponentInfo sci2 = new ScheduleComponentInfo();
        sci2.setId("2");
        sci2.setRoomId("r2");
        sci2.setTimeSlotIds(timeSlotIds2);
        List<ScheduleComponentInfo> scheduleComponentInfos = new ArrayList<ScheduleComponentInfo>();
        scheduleComponentInfos.add(sci1);
        scheduleComponentInfos.add(sci2);
        expected.setScheduleComponents(scheduleComponentInfos);
        ScheduleInfo actual = schedulingService.createSchedule(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getAtpId(), actual.getAtpId());
        assertEquals(actual.getAtpId(), "124");
        // new ListOfObjectTester().dump(expected.getScheduleComponents(), actual.getScheduleComponents());
        new ListOfObjectTester().check(expected.getScheduleComponents(), actual.getScheduleComponents());
        new ListOfObjectTester().check(scheduleComponentInfos, actual.getScheduleComponents());





    }


}
