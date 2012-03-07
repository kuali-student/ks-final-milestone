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
 * Created by Charles on 2/28/12
 */
package org.kuali.student.r2.core.class1.appointment.service.impl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.infc.AppointmentSlotRule;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:em-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAppointmentServiceImpl {
    @Resource
    private AppointmentService appointmentService;
    // ------------------------------------
    private AppointmentWindowInfo apptWindowInfo;
    private AppointmentSlotRuleInfo rule; // not a resource
    private Long startInMillis; // start of appointment slot
    private Long endInMillis; // end of appointment slot
    @Before
    public void before() {
        makeAppointmentWindowInfo();
    }

    private void makeAppointmentWindowInfo() {
        // Setup (id is not set up)
        apptWindowInfo = new AppointmentWindowInfo();
        makeSlotRule();
        // Uses rule from makeSlotRule
        apptWindowInfo.setSlotRule(rule);
    }

    private Long computeHoursInMillis(int hour) {
        Long timeInMillis = hour * 60 * 60 * 1000L; // In milliseconds
        return timeInMillis;
    }

    private TimeOfDayInfo makeTimeOfDayInfo(int hour) {
        TimeOfDayInfo info = new TimeOfDayInfo();
        info.setMilliSeconds(computeHoursInMillis(hour));
        return info;
    }
    
    private void makeSlotRule() {
        rule = new AppointmentSlotRuleInfo();
        // According to DB definition, weekdays must be non-null, so add it
        List<Integer> weekdays = new ArrayList<Integer>();
        weekdays.add(1);
        weekdays.add(3);
        weekdays.add(5);
        rule.setWeekdays(weekdays);
        TimeOfDayInfo startInfo = makeTimeOfDayInfo(9);
        startInMillis = startInfo.getMilliSeconds();
        TimeOfDayInfo endInfo = makeTimeOfDayInfo(17); // 5pm
        endInMillis = endInfo.getMilliSeconds();
        rule.setStartTimeOfDay(startInfo);
        rule.setEndTimeOfDay(endInfo);
    }

    @Test
    public void testExists() {
        String id = "123";
        apptWindowInfo.setId(id);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL, 
                                                        apptWindowInfo, new ContextInfo());
            // Now try to get it back
            AppointmentWindowInfo info = appointmentService.getAppointmentWindow(id, new ContextInfo());
            assertNotNull(info);
            assertEquals(id, info.getId());
        } catch (Exception e) {
            System.err.println("Exception caught ==========================");
            e.printStackTrace();
            assert(false); // If exception is thrown, make unit test fail
        }
    }

    @Test
    public void testDelete() {
        String id = "123";
        boolean shouldExist = true;
        apptWindowInfo.setId(id);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, new ContextInfo());
            // Fetch it
            ContextInfo info = new ContextInfo();
            AppointmentWindowInfo retrieved = appointmentService.getAppointmentWindow(id, info);
            assertNotNull(retrieved);
            // Then delete it
            System.err.println("Getting ready to delete");
            appointmentService.deleteAppointmentWindow(id, info);
            shouldExist = false;
            appointmentService.getAppointmentWindow(id, info); // should throw DoesNotExistException
        } catch  (DoesNotExistException e) {
            System.err.println("DoesNotExistException caught ==========================");
            assert(!shouldExist); // We expect this exception if shouldExist is false
        } catch (Exception e) {
            System.err.println("Exception caught ==========================");
            e.printStackTrace();
            assert(false); // If exception is thrown, make unit test fail
        }
    }

    @Test
    public void testUpdate() {
        String id = "123";
        apptWindowInfo.setId(id);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, new ContextInfo());
            // Fetch it
            ContextInfo info = new ContextInfo();
            AppointmentWindowInfo retrieved = appointmentService.getAppointmentWindow(id, info);
            assertNotNull(retrieved);
            // First verify that we're getting the data we sent in
            Long retrievedMillis = retrieved.getSlotRule().getStartTimeOfDay().getMilliSeconds();
            assertEquals(startInMillis, retrievedMillis);
            // Then, update the startInMillis (rule is already inside apptWindowInfo
            rule.setStartTimeOfDay(makeTimeOfDayInfo(10)); // set to 10 AM
            Long newStartInMillis = computeHoursInMillis(10);
            appointmentService.updateAppointmentWindow(id, apptWindowInfo, info);
            // Now retrieve it again
            retrieved = appointmentService.getAppointmentWindow(id, info);
            assertEquals(newStartInMillis, retrieved.getSlotRule().getStartTimeOfDay().getMilliSeconds());
        } catch (Exception e) {
            System.err.println("Exception caught ==========================");
            e.printStackTrace();
            assert(false); // If exception is thrown, make unit test fail
        }
    }
}
