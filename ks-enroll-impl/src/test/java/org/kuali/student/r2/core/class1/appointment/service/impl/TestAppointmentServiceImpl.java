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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class provides unit tests for the Appointment Service
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appt-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAppointmentServiceImpl {
    
    // a key defined in ProcessPocPopulationMockImpl with only 550 elements
    private static final String ATHLETES_ONLY_STUDENTS = "kuali.population.athletes.only.student";
    
    @Resource
    private AppointmentService appointmentService;
    // ------------------------------------
    private static final int SLOT_RULE_START_OF_DAY = 9;
    private static final int SLOT_RULE_END_OF_DAY = 17;
    private ContextInfo contextInfo;
    private AppointmentWindowInfo apptWindowInfo;
    private AppointmentSlotRuleInfo rule; // not a resource
    private Long startInMillis; // start of appointment slot
    // For use with AppointmentSlot testing
    private AppointmentSlotInfo apptSlotInfo;
//    private String slotId;
    private Date startDate;

    // No longer @Before
    public void before() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("123");
        contextInfo.setCurrentDate(new Date());
        
        makeAppointmentWindowInfo();
        makeAppointmentSlotInfo();
    }

    private void makeAppointmentWindowInfo() {
        apptWindowInfo = new AppointmentWindowInfo();
        makeSlotRule();
        // Uses rule from makeSlotRule
        apptWindowInfo.setSlotRule(rule);
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY);
        apptWindowInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
        //
        Date startDate = createDate(2012, 3, 5, 12, 0);
        Date endDate = createDate(2012, 3, 16, 14, 0);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);
        apptWindowInfo.setAssignedPopulationId(ATHLETES_ONLY_STUDENTS);
    }

    private AppointmentWindowInfo createAppoinmentWindowInfo(String milestoneId, int offset) {
        AppointmentWindowInfo apptWindowInfo = new AppointmentWindowInfo();
        // Uses rule from makeSlotRule
        apptWindowInfo.setSlotRule(rule);
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY);
        apptWindowInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
        //
        Date startDate = createDate(2012, 3, 5 + offset, 12, 0);
        Date endDate = createDate(2012, 3, 16 + offset, 14, 0);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);

        apptWindowInfo.setPeriodMilestoneId(milestoneId);
        apptWindowInfo.setAssignedPopulationId(ATHLETES_ONLY_STUDENTS);

        return apptWindowInfo;
    }

    private Date createDate(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();

        // Clear all fields
        cal.clear();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // Java starts month at 0
        cal.set(Calendar.DATE, dayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        // Create instance of java.util.Date
        return cal.getTime();
    }

    private void makeAppointmentSlotInfo() {
        apptSlotInfo = new AppointmentSlotInfo();
        startDate = createDate(2012, 3, 5, 12, 0);
        apptSlotInfo.setStartDate(startDate);
        Date endDate = createDate(2012, 3, 17, 17, 0);
        apptSlotInfo.setEndDate(endDate);
        apptSlotInfo.setAppointmentWindowId(apptWindowInfo.getId());
    }

    private TimeOfDayInfo makeTimeOfDayInfo(int hour) {
        return new TimeOfDayInfo(hour);
    }
    
    private void makeSlotRule() {
        rule = new AppointmentSlotRuleInfo();
        // According to DB definition, weekdays must be non-null, so add it
        List<Integer> weekdays = new ArrayList<Integer>();
        // Sunday is 1, Monday is 2, so this is MWF.
        weekdays.add(2);
        weekdays.add(4);
        weekdays.add(6);
        rule.setWeekdays(weekdays);
        TimeOfDayInfo startInfo = makeTimeOfDayInfo(SLOT_RULE_START_OF_DAY);
        startInMillis = TimeOfDayHelper.getMillis(startInfo);
        TimeOfDayInfo endInfo = makeTimeOfDayInfo(SLOT_RULE_END_OF_DAY); // 5pm
        rule.setStartTimeOfDay(startInfo);
        rule.setEndTimeOfDay(endInfo);
        // TODO: Eventually set the type once there is a type to set to
        TimeAmountInfo tao = new TimeAmountInfo();
        tao.setTimeQuantity(15); // Every fifteen minutes
        tao.setAtpDurationTypeKey(AtpServiceConstants.DURATION_MINUTES_TYPE_KEY);
        rule.setSlotStartInterval(tao);
    }
    // ---------------------------------- IN SUPPORT OF TEST ---------------------------------------
    private int _computeMinuteOffset(TimeOfDayInfo timeOfDay) {
        int minutes = 0;
        if (timeOfDay.getHour() != null) {
            minutes += timeOfDay.getHour() * 60;
        }
        if (timeOfDay.getMinute() != null) {
            minutes += timeOfDay.getMinute();
        }
        // Seconds are truncated
        return minutes;
    }
    
    private void _checkAppointmentSlots(List<AppointmentSlotInfo> slots, Date startWindow, Date endWindow, AppointmentSlotRuleInfo slotRule) {
        int startOfDayInMinutes = _computeMinuteOffset(slotRule.getStartTimeOfDay());
        int startHour = startOfDayInMinutes / 60;
        int startMinute = startOfDayInMinutes % 60;
        int endOfDayInMinutes = _computeMinuteOffset(slotRule.getEndTimeOfDay());
        int endHour = endOfDayInMinutes / 60;
        int endMinute = endOfDayInMinutes % 60;
        
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, startHour);
        startCal.set(Calendar.MINUTE, startMinute);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.HOUR_OF_DAY, endHour);
        endCal.set(Calendar.MINUTE, endMinute);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        
        for (AppointmentSlotInfo slotInfo: slots) {
            Date slotStartDate = slotInfo.getStartDate();
            // No slot assigned before startWindow
            assert(!slotStartDate.before(startWindow));
            // No slot assigned after endWindow
            assert(!slotStartDate.after(endWindow));
            // No slot before start of day
            Calendar slotStartCal = Calendar.getInstance();
            slotStartCal.setTime(slotInfo.getStartDate());

            // Set the start of day to current date of slot
            startCal.set(Calendar.YEAR, slotStartCal.get(Calendar.YEAR));
            startCal.set(Calendar.MONTH, slotStartCal.get(Calendar.MONTH));
            startCal.set(Calendar.DAY_OF_MONTH, slotStartCal.get(Calendar.DAY_OF_MONTH));

            // Set the end of day to current date of slot
            endCal.set(Calendar.YEAR, slotStartCal.get(Calendar.YEAR));
            endCal.set(Calendar.MONTH, slotStartCal.get(Calendar.MONTH));
            endCal.set(Calendar.DAY_OF_MONTH, slotStartCal.get(Calendar.DAY_OF_MONTH));

            // Slots during business day
            assert(!slotStartCal.before(startCal));
            assert(!slotStartCal.after(endCal));
        }
    }

    // --------------------------------------------------- TESTS ------------------------------------------------------
//    @Test
//    public void testCreateAppointment() {
//        before();
//            AppointmentWindowInfo info =
//                    appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
//            List<AppointmentSlotInfo> slotInfoList =
//                    appointmentService.generateAppointmentSlotsByWindow(info.getId(), contextInfo);
//            String personId = UUIDHelper.genStringUUID();
//            AppointmentSlotInfo slotInfo = slotInfoList.get(0);
//            AppointmentInfo apptInfo = new AppointmentInfo();
//            apptInfo.setPersonId(personId);
//            apptInfo.setSlotId(slotInfo.);
//
//    }

    @Test
    public void testStatusInfoUniformSlot() throws Exception {
        // Tests to see if the status info's message has the same count as number of appointments generated
        // in uniform slot allocation
        before();
        // Test uniform slot
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY);
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        StatusInfo statusInfo =
                appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        int numAllocated = Integer.parseInt(statusInfo.getMessage());
        // Check we have assignments
        int count = 0;
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            List<AppointmentInfo> appts =
                    appointmentService.getAppointmentsBySlot(slotInfo.getId(), contextInfo);
            count += appts.size();
        }
        assertEquals(count, numAllocated);
    }
    
    @Test
    public void testStatusInfoOneSlot() throws Exception {
        // Tests to see if the status info's message has the same count as number of appointments generated
        // in one slot allocation
        before();
        // Set up the basics (window, slot, appts for the one-slot case
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        StatusInfo statusInfo =
                appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        int numAllocated = Integer.parseInt(statusInfo.getMessage());
        // Check we have assignments
        String slotId = slotInfoList.get(0).getId();
        List<AppointmentInfo> apptList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertEquals(apptList.size(), numAllocated);
    }

    @Test
    public void testInvalidDurationType() throws Exception {
        before();
        // Want to adjust to create four slots (assuming 15 minutes between slots)
        Date startDate = createDate(2012, 3, 5, 12, 0);
        Date endDate = createDate(2012, 3, 5, 12, 45);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);
        int maxPerSlot = 3000;
        apptWindowInfo.setMaxAppointmentsPerSlot(maxPerSlot); // Currently, there are 9 students.  This would fill two slot
        // in full, and 1 additional in a third slot.
        // Change slot generation type
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY);
        AppointmentSlotRuleInfo slotRuleInfo = apptWindowInfo.getSlotRule();
        slotRuleInfo.getSlotStartInterval().setAtpDurationTypeKey(AtpServiceConstants.DURATION_HOURS_TYPE_KEY);
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        try {
            // Should throw exception because duration type is hours (which is unsupported)
            appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
            fail("InvalidParameterException should have been thrown due to unsupported duration type");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("Only kuali.atp.duration.Minutes is implemented", e.getMessage());
        }
    }
    
    @Test
    public void testMaxSlotGenerationStartDateAfterEndDateError() throws Exception {
        // This tests auto-slot generation for max case without end date
        before();
        apptWindowInfo.setAssignedPopulationId(ATHLETES_ONLY_STUDENTS);
        long millis = TimeOfDayHelper.getMillis(apptWindowInfo.getSlotRule().getStartTimeOfDay());
        TimeOfDayInfo newEnd =  TimeOfDayHelper.setMillis(millis - 10000);
        apptWindowInfo.getSlotRule().setEndTimeOfDay(newEnd);
        // Want to adjust to create four slots (assuming 15 minutes between slots)
        Date startDate = createDate(2012, 3, 5, 12, 0);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(null);
        int maxPerSlot = 500;
        apptWindowInfo.setMaxAppointmentsPerSlot(maxPerSlot); // Currently, there are 9 students.  This would fill two slot
        // in full, and 1 additional in a third slot.
        // Change slot generation type
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY);
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        try {
            appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
            fail("InvalidParameterException should have been thrown");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("End time of day should be AFTER start time of day", e.getMessage());
        }
    }

    @Test
    public void testMaxSlotGenerationWithoutEndDate() throws Exception {
        // This tests auto-slot generation for max case without end date
        before();
        // Want to adjust to create four slots (assuming 15 minutes between slots)
        Date startDate = createDate(2012, 3, 5, 12, 0);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(null);
        int maxPerSlot = 500;
        apptWindowInfo.setMaxAppointmentsPerSlot(maxPerSlot); // Currently, there are 9 students.  This would fill two slot
        // in full, and 1 additional in a third slot.
        // Change slot generation type
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY);
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        // Only creates as many slots as needed (with 9 students, 4 per slot, only 3 are created)
        assertEquals(2, slotInfoList.size());
        // Now fetch the slots
        List<AppointmentSlotInfo> fetchedSlots =
                appointmentService.getAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertEquals(2, fetchedSlots.size());
        // Now assign students
        StatusInfo statusInfo =
                appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Should be fine
        assertTrue(statusInfo.getIsSuccess());
        // Now check to make sure assignments were made
        HashSet<String> studentIdSet = new HashSet<String>();
        int studentIdsCount = 0; // How many appointments we have
        int numMaxFilledSlots = 0;
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            List<AppointmentInfo> apptList = appointmentService.getAppointmentsBySlot(slotInfo.getId(), contextInfo);
            for (AppointmentInfo apptInfo: apptList) {
                studentIdSet.add(apptInfo.getPersonId());
            }
            // Make sure slots are less than or equal to maximum
            assert(apptList.size() <= maxPerSlot);
            if (apptList.size() == maxPerSlot) {
                numMaxFilledSlots++;
            }
            studentIdsCount += apptList.size();
        }
        // Check we have as many unique students as appointments
        assertEquals(studentIdsCount, studentIdSet.size());
        // Check we are filling slots to max as needed
        assertEquals(studentIdsCount / maxPerSlot, numMaxFilledSlots);
    }

    @Test
    public void testGoodMaxSlotGenerationWithEndDate() throws Exception {
        // This tests if there are too few slots for the number of students
        before();
        // Want to adjust to create four slots (assuming 15 minutes between slots)
        Date startDate = createDate(2012, 3, 5, 12, 0);
        Date endDate = createDate(2012, 3, 5, 12, 45);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);
        int maxPerSlot = 3000;
        apptWindowInfo.setMaxAppointmentsPerSlot(maxPerSlot); // Currently, there are 9 students.  This would fill two slot
                                                     // in full, and 1 additional in a third slot.
        // Change slot generation type
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY);
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        // Only creates as many slots as needed (with 9 students, 4 per slot, only 3 are created)
        assertEquals(1, slotInfoList.size());
        // Now fetch the slots
        List<AppointmentSlotInfo> fetchedSlots =
                appointmentService.getAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertEquals(1, fetchedSlots.size());
        // Now assign students
        StatusInfo statusInfo =
                appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Should be fine
        System.out.println(statusInfo.getMessage());
        assertTrue(statusInfo.getIsSuccess());
        // Now check to make sure assignments were made
        HashSet<String> studentIdSet = new HashSet<String>();
        int studentIdsCount = 0; // How many appointments we have
        int numMaxFilledSlots = 0;
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            List<AppointmentInfo> apptList = appointmentService.getAppointmentsBySlot(slotInfo.getId(), contextInfo);
            for (AppointmentInfo apptInfo: apptList) {
                studentIdSet.add(apptInfo.getPersonId());
            }
            // Make sure slots are less than or equal to maximum
            assert(apptList.size() <= maxPerSlot);
            if (apptList.size() == maxPerSlot) {
                numMaxFilledSlots++;
            }
            studentIdsCount += apptList.size();
        }
        // Check we have as many unique students as appointments
        assertEquals(studentIdsCount, studentIdSet.size());
        // Check we are filling slots to max as needed
        assertEquals(studentIdsCount / maxPerSlot, numMaxFilledSlots);
    }
    @Test
    public void testBadMaxSlotGenerationWithEndDate() throws Exception {
        // This tests if there are too few slots for the number of students
        before();
        // Want to adjust to create three slots (assuming 15 minutes between slots)
        Date startDate = createDate(2012, 3, 5, 12, 0);
        Date endDate = createDate(2012, 3, 5, 12, 30);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);
        apptWindowInfo.setMaxAppointmentsPerSlot(2); // Make it very small
        apptWindowInfo.setAssignedPopulationId(ATHLETES_ONLY_STUDENTS);
        // Change slot generation type
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY);
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertEquals(3, slotInfoList.size());
        // Now assign students
        StatusInfo statusInfo =
                appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Should fail due to not enough slots
        assertFalse(statusInfo.getIsSuccess());
        // Now check to make sure no assignments were made
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            List<AppointmentInfo> apptList = appointmentService.getAppointmentsBySlot(slotInfo.getId(), contextInfo);
            assertTrue(apptList.isEmpty());
        }
    }
    @Test
    public void testUniformSlotGeneration() throws Exception {
        // If generateAppointmentSlotsByWindow is called twice, it should delete the previous slots/assignments
        before();
        // Want to adjust to create three slots (assuming 15 minutes between slots)
        Date startDate = createDate(2012, 3, 5, 12, 0);
        Date endDate = createDate(2012, 3, 5, 12, 30);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);
        // Change slot generation type
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY);
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertEquals(3, slotInfoList.size());
        // Now assign students
        appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Go through the slots to check most of them are the same.  Also check the IDs are unique
        HashMap<Integer, Integer> slotSizeVsFrequency = new HashMap<Integer, Integer>();
        HashSet<String> studentIdSet = new HashSet<String>();
        int studentIdsCount = 0; // How many appointments we have
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            List<AppointmentInfo> apptList =
                    appointmentService.getAppointmentsBySlot(slotInfo.getId(), contextInfo);
            studentIdsCount += apptList.size();
            for (AppointmentInfo appt: apptList) {
                studentIdSet.add(appt.getPersonId());
            }
            if (!slotSizeVsFrequency.containsKey(apptList.size())) {
                slotSizeVsFrequency.put(apptList.size(), 1); // We've seen this number of students once
            } else { // Increment
                slotSizeVsFrequency.put(apptList.size(), slotSizeVsFrequency.get(apptList.size()) + 1);
            }
        }
        // Should have same number of students as appointments (thus, we didn't duplicate any students)
        assertEquals(studentIdsCount, studentIdSet.size());
        // Should either be all the same size, or all but 1 the same size (thus two distinct sizes)
        assertFalse(slotSizeVsFrequency.isEmpty());
        assertTrue(slotSizeVsFrequency.size() <= 2);
        // Make sure one of them is unique
        if (slotSizeVsFrequency.size() == 2) {
            boolean found = false;
            for (Integer key: slotSizeVsFrequency.keySet()) {
                int frequency = slotSizeVsFrequency.get(key);
                if (frequency == 1) {
                    found = true;
                }
            }
            assertEquals(true, found);
         }
    }
    @Test
    public void testGenerateTwice() throws Exception {
        // If generateAppointmentSlotsByWindow is called twice, it should delete the previous slots/assignments
        before();
        // Set up the basics (window, slot, appts for the slot)
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Check we have assignments
        String slotId = slotInfoList.get(0).getId();
        List<AppointmentInfo> apptList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be non-empty", !apptList.isEmpty());
        // Now, generate appointment slots a second time
        appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        // Then, check if we still only have one slot
        List<AppointmentSlotInfo> fetchedSlotList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertEquals(1, fetchedSlotList.size());
        // And check we have no assignments
        List<AppointmentInfo> fetchedApptList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be empty", fetchedApptList.isEmpty());
    }
    @Test
    public void testDeleteAppointmentSlotsByWindowWithAssign() throws Exception {
        // Test case for deleting appointment slots with assignments made (one slot case)
        // Test #3 
        before();
        // Set up the basics (window, slot, appts for the slot)
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Testing for state change
        AppointmentWindowInfo fetchedWindowInfo =
                appointmentService.getAppointmentWindow(windowInfo.getId(), contextInfo);
        assertEquals(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY, fetchedWindowInfo.getStateKey());
        assertEquals(1, slotInfoList.size());
        String slotId = slotInfoList.get(0).getId();
        List<AppointmentInfo> apptList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be non-empty", !apptList.isEmpty());
        appointmentService.deleteAppointmentSlotsByWindowCascading(windowInfo.getId(), contextInfo);
        // Testing we're back in DRAFT mode
        fetchedWindowInfo =
                appointmentService.getAppointmentWindow(windowInfo.getId(), contextInfo);
        assertEquals(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY, fetchedWindowInfo.getStateKey());
        List<AppointmentSlotInfo> fetchedSlotInfoList =
                appointmentService.getAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertTrue("Slot list should be empty", fetchedSlotInfoList.isEmpty());
        List<AppointmentInfo> fetchedApptList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be empty", fetchedApptList.isEmpty());
        // Make sure slot is really gone
        try {
            appointmentService.getAppointmentSlot(slotId, contextInfo);
            fail("DoesNotExistException should have been thrown");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(slotId, dnee.getMessage());
        }
    }
    
    @Test
    public void testDeleteAppointmentSlotsByWindowNoAssign() throws Exception {
        // Test case for deleting appointment slots with no assignments made (one slot case)
        // Test #3 
        before();
        // Set up the basics (window, slot, appts for the slot)
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        // Use windowInfo afterwards since it has the ID
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        // Check we're still in draft mode
        AppointmentWindowInfo fetchedWindowInfo =
                appointmentService.getAppointmentWindow(windowInfo.getId(), contextInfo);
        assertEquals(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY, fetchedWindowInfo.getStateKey());
        // Check there's only one slot (one slot case)
        assertEquals(1, slotInfoList.size());
        appointmentService.deleteAppointmentSlotsByWindowCascading(windowInfo.getId(), contextInfo);
        List<AppointmentSlotInfo> fetchedSlotInfoList =
                appointmentService.getAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertTrue(fetchedSlotInfoList.isEmpty());
    }
    @Test
    public void testDeleteAppointmentsByWindow() throws Exception {
        // One slot case--deletes only appointments, but not the slot.
        before();
        // Set up the basics (window, slot, appts for the slot)
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Now fetch the appointments by the slot
        String slotId = slotInfoList.get(0).getId();
        List<AppointmentInfo> apptList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be non-empty", !apptList.isEmpty());
        // And now delete the appointments, by window
        appointmentService.deleteAppointmentsByWindow(windowInfo.getId(), contextInfo);
        // Try fetching it again
        apptList = appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be empty", apptList.isEmpty());
    }

    @Test
    public void testDeleteAppointmentsBySlot() throws Exception {
        // One slot case--deletes only appointments, but not the slot.
        before();
        // Set up the basics (window, slot, appts for the slot)
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Now fetch the appointments by the slot
        String slotId = slotInfoList.get(0).getId();
        List<AppointmentInfo> apptList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be non-empty", !apptList.isEmpty());
        // And now delete the appointments
        appointmentService.deleteAppointmentsBySlot(slotId, contextInfo);
        // Try fetching it again
        apptList = appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assertTrue("Appointment list should be empty", apptList.isEmpty());
    }

    @Test
    public void testDeleteOneAppointmentById() throws Exception {
        before();
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        AppointmentInfo info = new AppointmentInfo();
        String slotId = slotInfoList.get(0).getId();
        info.setSlotId(slotId);
        String personId = UUIDHelper.genStringUUID();
        info.setPersonId(personId);
        info.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        info.setTypeKey(AppointmentServiceConstants.APPOINTMENT_TYPE_REGISTRATION);
        AppointmentInfo createdApptInfo =
                appointmentService.createAppointment(info.getPersonId(), info.getSlotId(), AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY, info, contextInfo);
        List<AppointmentInfo> apptList =
                appointmentService.getAppointmentsBySlot(slotInfoList.get(0).getId(), contextInfo);
        assertEquals(1, apptList.size()); // should only get one appt back
        assertEquals(slotId, apptList.get(0).getSlotId()); // check slot ID matches
        assertEquals(personId, apptList.get(0).getPersonId());
        appointmentService.deleteAppointment(createdApptInfo.getId(), contextInfo);
        // now try to get the appointments by slot again
        apptList = appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assert(apptList.isEmpty()); // should be empty
    }

    @Test
    public void testDeleteAppointmentWindowWithAppts() throws Exception {
        // One slot case
        before();
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        // Check slots/appts are being generated
        // ...first fetch slots to make sure we can fetch
        List<AppointmentSlotInfo> fetchedSlotInfoList =
                appointmentService.getAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assertEquals(1, fetchedSlotInfoList.size());
        // ...then, check for appointments
        AppointmentSlotInfo slotInfo = slotInfoList.get(0);
        String slotId = slotInfo.getId();
        List<AppointmentInfo> apptInfoList =
                appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        for (AppointmentInfo apptInfo: apptInfoList) {
            assertEquals(apptInfo.getSlotId(), slotInfo.getId());
        }
        // Now delete the window
        appointmentService.deleteAppointmentsByWindow(windowInfo.getId(), contextInfo);
        // Verify there are no slots
        List<AppointmentSlotInfo> fetchedSlots = appointmentService.getAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        assert(fetchedSlots.isEmpty());
        // Verify there are no appointments
        List<AppointmentInfo> apptList = appointmentService.getAppointmentsBySlot(slotId, contextInfo);
        assert(apptList.isEmpty());
    }

    @Test
    public void testGenerateAppointmentsByWindow() throws Exception {
        before();
        AppointmentWindowInfo windowInfo =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(windowInfo.getId(), contextInfo);
        appointmentService.generateAppointmentsByWindow(windowInfo.getId(), windowInfo.getTypeKey(), contextInfo);
        AppointmentSlotInfo slotInfo = slotInfoList.get(0);
        List<AppointmentInfo> apptInfoList =
                appointmentService.getAppointmentsBySlot(slotInfo.getId(), contextInfo);
        for (AppointmentInfo apptInfo: apptInfoList) {
            assertEquals(apptInfo.getSlotId(), slotInfo.getId());
        }
    }

    @Test
    public void testGetAppointmentWindowsByPeriod() throws Exception {
        before();
        String milestoneId = UUIDHelper.genStringUUID();
        String milestoneId2 = UUIDHelper.genStringUUID();
        List<AppointmentWindowInfo> infoList = new ArrayList<AppointmentWindowInfo>();
        for (int i = 0; i < 3; i++) {
            AppointmentWindowInfo info = createAppoinmentWindowInfo(milestoneId, i + 1);
            infoList.add(info);
        }
        for (int i = 3; i < 5; i++) {
            AppointmentWindowInfo info = createAppoinmentWindowInfo(milestoneId2, i + 1);
            infoList.add(info);
        }
        for (AppointmentWindowInfo info: infoList) {
            appointmentService.createAppointmentWindow(info.getTypeKey(), info, contextInfo);
        }
        // Now fetch the windows
        List<AppointmentWindowInfo> list = appointmentService.getAppointmentWindowsByPeriod(milestoneId, contextInfo);
        assertEquals(3, list.size());
    }

    @Test
    public void testApptWindowDeleteOneSlot() throws Exception {
        before();
        AppointmentWindowInfo info =
                appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slotInfoList =
                appointmentService.generateAppointmentSlotsByWindow(info.getId(), contextInfo);
        assertEquals(slotInfoList.size(), 1);
        appointmentService.deleteAppointmentWindowCascading(info.getId(), contextInfo);
        try {
            appointmentService.getAppointmentSlot(slotInfoList.get(0).getId(), contextInfo);
            fail("DoesNotExistException should have been thrown");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(slotInfoList.get(0).getId(), dnee.getMessage());
        }
    }
    
    @Test
    public void testGenerateMaxSlotsByWindow() throws Exception {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY);
        apptWindowInfo.setMaxAppointmentsPerSlot(100);
        AppointmentWindowInfo window = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY,
                apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slots =
                appointmentService.generateAppointmentSlotsByWindow(window.getId(), contextInfo);
        AppointmentSlotRuleInfo slotRule = window.getSlotRule();
        _checkAppointmentSlots(slots, window.getStartDate(), window.getEndDate(), slotRule);
        appointmentService.getAppointmentSlotsByWindow(window.getId(), contextInfo);
    }

    @Test
    public void testGenerateOneSlotPerWindow() throws Exception {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        AppointmentWindowInfo window = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY,
                apptWindowInfo, contextInfo);
        List<AppointmentSlotInfo> slots =
                appointmentService.generateAppointmentSlotsByWindow(window.getId(), contextInfo);
        assert(slots.size() == 1);
        AppointmentSlotInfo oneSlot = slots.get(0);
        assertEquals(oneSlot.getStartDate(), apptWindowInfo.getStartDate());
        assertNull(oneSlot.getEndDate()); // TODO: Change for
    }

    @Test
    public void testApptWinCreate() throws Exception {
        // Test for #1 in https://wiki.kuali.org/display/STUDENT/Appointment+Service+Impl+Decisions
        // createAppointmentWindow
        before();
        AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                                                    apptWindowInfo, contextInfo);
        // Now try to get it back
        String id = windowInfo.getId();
        AppointmentWindowInfo info = appointmentService.getAppointmentWindow(id, contextInfo);
        assertNotNull(info);
        // Check we're in draft state
        assertEquals(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY, info.getStateKey());
    }

    @Test
    public void testApptWinDelete() throws Exception {
        before();
        AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                apptWindowInfo, contextInfo);
        String id = windowInfo.getId();
        // Fetch it
        AppointmentWindowInfo retrieved = appointmentService.getAppointmentWindow(id, contextInfo);
        assertNotNull(retrieved);
        // Then delete it
        appointmentService.deleteAppointmentWindowCascading(id, contextInfo);
        try {
            appointmentService.getAppointmentWindow(id, contextInfo); // should throw DoesNotExistException
            fail("DoesNotExistException should have been thrown");
        } catch  (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(id, dnee.getMessage());
        }
    }

    @Test
    public void testApptWinUpdate() throws Exception {
        // Test #2 in https://wiki.kuali.org/display/STUDENT/Appointment+Service+Impl+Decisions
        before();
        AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                apptWindowInfo, contextInfo);
        String id = windowInfo.getId();
        // Fetch it
        AppointmentWindowInfo retrieved = appointmentService.getAppointmentWindow(id, contextInfo);
        assertNotNull(retrieved);
        // First verify that we're getting the data we sent in
        Long retrievedMillis = TimeOfDayHelper.getMillis(retrieved.getSlotRule().getStartTimeOfDay());
        assertEquals(startInMillis, retrievedMillis);
        // Then, update the startInMillis (rule is already inside apptWindowInfo
        rule.setStartTimeOfDay(makeTimeOfDayInfo(SLOT_RULE_START_OF_DAY+1));
        appointmentService.updateAppointmentWindow(id, apptWindowInfo, contextInfo);
        // Now retrieve it again
        retrieved = appointmentService.getAppointmentWindow(id, contextInfo);
        assertEquals(new Integer(SLOT_RULE_START_OF_DAY + 1), retrieved.getSlotRule().getStartTimeOfDay().getHour());
        // Make sure we're still in draft state
        assertEquals(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY, retrieved.getStateKey());
    }

    @Test
    public void testApptSlotCreate() throws Exception {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                apptWindowInfo, contextInfo);
        String apptWinId = windowInfo.getId();
        AppointmentSlotInfo created = appointmentService.createAppointmentSlot(apptWinId, AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY,
                apptSlotInfo, contextInfo);
        // Now try to get it back
        AppointmentSlotInfo info = appointmentService.getAppointmentSlot(created.getId(), contextInfo);
        assertNotNull(info);
        assertEquals(created.getId(), info.getId());
    }

    @Test
    public void testApptSlotDelete() throws Exception {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                apptWindowInfo, contextInfo);
        String apptWinId = windowInfo.getId();
        AppointmentSlotInfo created = appointmentService.createAppointmentSlot(apptWinId, AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY,
                apptSlotInfo, contextInfo);
        String createdId = created.getId();
        // Now try to get it back
        AppointmentSlotInfo info = appointmentService.getAppointmentSlot(createdId, contextInfo);
        assertNotNull(info);
        // Now try to delete it
        appointmentService.deleteAppointmentSlotCascading(info.getId(), contextInfo);
        appointmentService.getAppointmentSlot(createdId, contextInfo);
    }
    
    @Test
    public void testAppSlotUpdate() throws Exception {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                apptWindowInfo, contextInfo);
        String apptWinId = windowInfo.getId();
        AppointmentSlotInfo slotInfo = appointmentService.createAppointmentSlot(apptWinId, AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY,
                apptSlotInfo, contextInfo);
        String slotId = slotInfo.getId();
        // Now try to get it back
        AppointmentSlotInfo info = appointmentService.getAppointmentSlot(slotId, contextInfo);
        // Check if the date in the retrieved data is the same
        assertEquals(startDate, info.getStartDate());
        Date newStartDate = createDate(2011, 3, 1, 10, 0); // Change start date to 10 AM
        // Update it
        info.setStartDate(newStartDate);
        assertEquals(apptWinId, info.getAppointmentWindowId());
        appointmentService.updateAppointmentSlot(slotId, info, contextInfo);
        // Fetch it again
        AppointmentSlotInfo retrieved = appointmentService.getAppointmentSlot(slotId, contextInfo);
        // Check that date matches the new start date
        assertEquals(newStartDate, retrieved.getStartDate());
    }
}
