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
 * Created by Charles on 9/11/12
 */
package org.kuali.student.r2.core.scheduling.service.impl;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dao.ScheduleDao;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestDao;
import org.kuali.student.r2.core.scheduling.dto.MeetingTimeInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-impl-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestScheduleServiceScheduleMethods {
    @Resource(name = "schedulingServiceImpl")
    private SchedulingService schedulingService;

    @Resource(name = "scheduleDao" )
    private ScheduleDao scheduleDao;

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public ScheduleDao getScheduleDao() {
        return scheduleDao;
    }

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    // ================================== TESTS =========================================
    @Test
    public void testCreateReadSchedule() {

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

            ScheduleInfo info = new ScheduleInfo();
            info.setStateKey(SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
            info.setTypeKey(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
            info.setAtpId("FakeAtpId");

            MeetingTimeInfo meetingTimeInfo1 = new MeetingTimeInfo();
            meetingTimeInfo1.setRoomId("Room 1");
            meetingTimeInfo1.setStartDate(df.parse("20120113"));
            TimeAmountInfo timeAmountInfo1 = new TimeAmountInfo();
            timeAmountInfo1.setAtpDurationTypeKey(AtpServiceConstants.DURATION_HOURS_TYPE_KEY);
            timeAmountInfo1.setTimeQuantity(23);
            meetingTimeInfo1.setDuration(timeAmountInfo1);
            info.getAdditionalMeetingTimes().add(meetingTimeInfo1);

            MeetingTimeInfo meetingTimeInfo2 = new MeetingTimeInfo();
            meetingTimeInfo2.setRoomId("Room 2");
            meetingTimeInfo2.setStartDate(df.parse("20120114"));
            TimeAmountInfo timeAmountInfo2 = new TimeAmountInfo();
            timeAmountInfo2.setAtpDurationTypeKey(AtpServiceConstants.DURATION_MINUTES_TYPE_KEY);
            timeAmountInfo2.setTimeQuantity(44);
            meetingTimeInfo2.setDuration(timeAmountInfo2);
            info.getAdditionalMeetingTimes().add(meetingTimeInfo2);



            ScheduleInfo returned = schedulingService.createSchedule(info.getTypeKey(), info, callContext);
            String id = returned.getId();
            ScheduleInfo fetched = schedulingService.getSchedule(id, callContext);
            assertEquals(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, fetched.getTypeKey());
            assertEquals(SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE, fetched.getStateKey());
        } catch (Exception e) {
            assert(false);
        }
    }
}
