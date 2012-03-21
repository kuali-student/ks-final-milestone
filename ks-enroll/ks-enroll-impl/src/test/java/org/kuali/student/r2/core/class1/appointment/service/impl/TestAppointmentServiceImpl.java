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
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
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
import java.util.Calendar;
import java.util.Date;
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
    private ContextInfo contextInfo;
    private AppointmentWindowInfo apptWindowInfo;
    private AppointmentSlotRuleInfo rule; // not a resource
    private Long startInMillis; // start of appointment slot
    private Long endInMillis; // end of appointment slot
    // For use with AppointmentSlot testing
    private AppointmentSlotInfo apptSlotInfo;
    private String slotId;
    private Date startDate;

    @Before
    public void before() {
        contextInfo = new ContextInfo();
        makeAppointmentWindowInfo();
        makeAppointmentSlotInfo();
    }

    private void makeAppointmentWindowInfo() {
        // Setup (id is not set up)
        apptWindowInfo = new AppointmentWindowInfo();
        makeSlotRule();
        // Uses rule from makeSlotRule
        apptWindowInfo.setSlotRule(rule);
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY);
        apptWindowInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ACTIVE_KEY);
    }

    private Date createDate(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();

        // Clear all fields
        cal.clear();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, dayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        // Create instance of java.util.Date
        return cal.getTime();
    }

    private void makeAppointmentSlotInfo() {
        apptSlotInfo = new AppointmentSlotInfo();
        startDate = createDate(2011, 3, 1, 9, 0);
        apptSlotInfo.setStartDate(startDate);
        Date endDate = createDate(2011, 3, 21, 5, 0);
        apptSlotInfo.setEndDate(endDate);
        slotId = UUIDHelper.genStringUUID();
        apptSlotInfo.setId(slotId);
        apptSlotInfo.setAppointmentWindowId(apptWindowInfo.getId());
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
    public void testApptWinCreate() {
        String id = UUIDHelper.genStringUUID();
        apptWindowInfo.setId(id);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL, 
                                                        apptWindowInfo, new ContextInfo());
            // Now try to get it back
            AppointmentWindowInfo info = appointmentService.getAppointmentWindow(id, contextInfo);
            assertNotNull(info);
            assertEquals(id, info.getId());
        } catch (Exception e) {
            System.err.println("Exception caught ==========================");
            e.printStackTrace();
            assert(false); // If exception is thrown, make unit test fail
        }
    }

    @Test
    public void testApptWinDelete() {
        String id = UUIDHelper.genStringUUID();
        boolean shouldExist = true;
        apptWindowInfo.setId(id);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, contextInfo);
            // Fetch it
            AppointmentWindowInfo retrieved = appointmentService.getAppointmentWindow(id, contextInfo);
            assertNotNull(retrieved);
            // Then delete it
            System.err.println("Getting ready to delete");
            appointmentService.deleteAppointmentWindow(id, contextInfo);
            shouldExist = false;
            appointmentService.getAppointmentWindow(id, contextInfo); // should throw DoesNotExistException
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
    public void testApptWinUpdate() {
        String id = UUIDHelper.genStringUUID();
        apptWindowInfo.setId(id);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, new ContextInfo());
            // Fetch it
            AppointmentWindowInfo retrieved = appointmentService.getAppointmentWindow(id, contextInfo);
            assertNotNull(retrieved);
            // First verify that we're getting the data we sent in
            Long retrievedMillis = retrieved.getSlotRule().getStartTimeOfDay().getMilliSeconds();
            assertEquals(startInMillis, retrievedMillis);
            // Then, update the startInMillis (rule is already inside apptWindowInfo
            rule.setStartTimeOfDay(makeTimeOfDayInfo(10)); // set to 10 AM
            Long newStartInMillis = computeHoursInMillis(10);
            appointmentService.updateAppointmentWindow(id, apptWindowInfo, contextInfo);
            // Now retrieve it again
            retrieved = appointmentService.getAppointmentWindow(id, contextInfo);
            assertEquals(newStartInMillis, retrieved.getSlotRule().getStartTimeOfDay().getMilliSeconds());
        } catch (Exception e) {
            System.err.println("Exception caught ==========================");
            e.printStackTrace();
            assert(false); // If exception is thrown, make unit test fail
        }
    }

    @Test
    public void testApptSlotCreate() {
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        String apptWinId = UUIDHelper.genStringUUID();
        apptWindowInfo.setId(apptWinId);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, contextInfo);
            appointmentService.createAppointmentSlot(apptWinId, AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY,
                    apptSlotInfo, contextInfo);
            // Now try to get it back
            AppointmentSlotInfo info = appointmentService.getAppointmentSlot(slotId, contextInfo);
            assertNotNull(info);
            assertEquals(slotId, info.getId());
        } catch (Exception e) {
            System.err.println("Exception");
            assert(false);
        }
    }

    @Test
    public void testApptSlotDelete() {
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        String apptWinId = UUIDHelper.genStringUUID();
        boolean shouldExist = true;
        apptWindowInfo.setId(apptWinId);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, contextInfo);
            appointmentService.createAppointmentSlot(apptWinId, AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY,
                    apptSlotInfo, contextInfo);
            // Now try to get it back
            AppointmentSlotInfo info = appointmentService.getAppointmentSlot(slotId, contextInfo);
            assertNotNull(info);
            // Now try to delete it
            System.err.println("Getting ready to delete");
            appointmentService.deleteAppointmentSlot(slotId, contextInfo);
            shouldExist = false;
            appointmentService.getAppointmentSlot(slotId, contextInfo); // should throw DoesNotExistException
        } catch  (DoesNotExistException e) {
            System.err.println("DoesNotExistException caught ==========================");
            assert(!shouldExist); // We expect this exception if shouldExist is false
        } catch (Exception e) {
            System.err.println("Exception");
            assert(false);
        }
    }
    
    @Test
    public void testAppSlotUpdate() {
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        String apptWinId = UUIDHelper.genStringUUID();
        apptWindowInfo.setId(apptWinId);
        try {
            appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, contextInfo);
            appointmentService.createAppointmentSlot(apptWinId, AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY,
                    apptSlotInfo, contextInfo);
            // Now try to get it back
            AppointmentSlotInfo info = appointmentService.getAppointmentSlot(slotId, contextInfo);
            // Check if the date in the retrieved data is the same
            assertEquals(startDate, info.getStartDate());
            Date newStartDate = createDate(2011, 3, 1, 10, 0); // Change start date to 10 AM
            // Update it
            info.setStartDate(newStartDate);
            assertEquals(apptWinId, info.getAppointmentWindowId());
            System.err.println("Getting ready to call: updateAppointmentSlot");
            appointmentService.updateAppointmentSlot(slotId, info, contextInfo);
            // Fetch it again
            AppointmentSlotInfo retrieved = appointmentService.getAppointmentSlot(slotId, contextInfo);
            // Check that date matches the new start date
            assertEquals(newStartDate, retrieved.getStartDate());
        } catch (Exception e) {
            System.err.println("Exception");
            e.printStackTrace();
            assert(false);
        }
    }
    
    @Test
    public void testGenerateSlotsByWindow() {
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        try {
            AppointmentWindowInfo window = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, contextInfo);
            List<AppointmentSlotInfo> slots =
                    appointmentService.generateAppointmentSlotsByWindow(window.getId(), contextInfo);
            assert(slots.size() == 1);
            AppointmentSlotInfo oneSlot = slots.get(0);
            assertEquals(oneSlot.getStartDate(), apptWindowInfo.getStartDate());
            assertNull(oneSlot.getEndDate());
        } catch (Exception e) {
            System.err.println("Exception");
            e.printStackTrace();
            assert(false);
        }
    }
}
