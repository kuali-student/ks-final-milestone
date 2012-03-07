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
    @Before
    public void before() {
    }
    
    @Test
    public void testExists() {
        // Setup
        String id = "123";
        AppointmentWindowInfo apptWindowInfo = new AppointmentWindowInfo();
        apptWindowInfo.setId(id);
        AppointmentSlotRuleInfo rule = new AppointmentSlotRuleInfo();
        List<Integer> weekdays = new ArrayList<Integer>();
        weekdays.add(1);
        weekdays.add(3);
        weekdays.add(5);
        rule.setWeekdays(weekdays);
        Long start = 9 * 60 * 60 * 1000L; // 9 AM
        Long end = 17 * 60 * 60 * 1000L; // 5 PM
        TimeOfDayInfo startInfo = new TimeOfDayInfo();
        startInfo.setMilliSeconds(start);
        TimeOfDayInfo endInfo = new TimeOfDayInfo();
        endInfo.setMilliSeconds(end);
        rule.setStartTimeOfDay(startInfo);
        rule.setEndTimeOfDay(endInfo);

        apptWindowInfo.setSlotRule(rule);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL, 
                                                        apptWindowInfo, new ContextInfo());
            // Now try to get it back
            AppointmentWindowInfo info = appointmentService.getAppointmentWindow(id, new ContextInfo());
            assertNotNull(info);
            assertEquals(id, info.getId());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            assert(false);
        }

    }
}
