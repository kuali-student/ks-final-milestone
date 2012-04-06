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
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appt-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAppointmentServiceImpl {
    @Resource
    private AppointmentService appointmentService;
    // ------------------------------------
    private static final int SLOT_RULE_START_OF_DAY = 9;
    private static final int SLOT_RULE_END_OF_DAY = 17;
    private ContextInfo contextInfo;
    private AppointmentWindowInfo apptWindowInfo;
    private AppointmentSlotRuleInfo rule; // not a resource
    private Long startInMillis; // start of appointment slot
    private Long endInMillis; // end of appointment slot
    // For use with AppointmentSlot testing
    private AppointmentSlotInfo apptSlotInfo;
//    private String slotId;
    private Date startDate;

    // No longer @Before
    public void before() {
        contextInfo = new ContextInfo();
        makeAppointmentWindowInfo();
        makeAppointmentSlotInfo();
    }

    private void makeAppointmentWindowInfo() {
        apptWindowInfo = new AppointmentWindowInfo();
        makeSlotRule();
        // Uses rule from makeSlotRule
        apptWindowInfo.setSlotRule(rule);
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY);
        apptWindowInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ACTIVE_KEY);
        //
        Date startDate = createDate(2012, 3, 5, 12, 0);
        Date endDate = createDate(2012, 3, 16, 14, 0);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);
        apptWindowInfo.setAssignedPopulationId(PopulationServiceConstants.EVERYONE_POPULATION_KEY);
    }

    private AppointmentSlotInfo createAppointmentSlotInfo(String apptWinId) {
        AppointmentSlotInfo apptSlotInfo = new AppointmentSlotInfo();
        Date startDate = createDate(2012, 3, 5, 12, 0);
        apptSlotInfo.setStartDate(startDate);
        apptSlotInfo.setAppointmentWindowId(apptWinId);
        return apptSlotInfo;
    }

    private AppointmentWindowInfo createAppoinmentWindowInfo(String milestoneId, int offset) {
        AppointmentWindowInfo apptWindowInfo = new AppointmentWindowInfo();
        // Uses rule from makeSlotRule
        apptWindowInfo.setSlotRule(rule);
        apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY);
        apptWindowInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ACTIVE_KEY);
        //
        Date startDate = createDate(2012, 3, 5 + offset, 12, 0);
        Date endDate = createDate(2012, 3, 16 + offset, 14, 0);
        apptWindowInfo.setStartDate(startDate);
        apptWindowInfo.setEndDate(endDate);

        apptWindowInfo.setPeriodMilestoneId(milestoneId);
        apptWindowInfo.setAssignedPopulationId(PopulationServiceConstants.EVERYONE_POPULATION_KEY);

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
        // Sunday is 1, Monday is 2, so this is MWF.
        weekdays.add(2);
        weekdays.add(4);
        weekdays.add(6);
        rule.setWeekdays(weekdays);
        TimeOfDayInfo startInfo = makeTimeOfDayInfo(SLOT_RULE_START_OF_DAY);
        startInMillis = startInfo.getMilliSeconds();
        TimeOfDayInfo endInfo = makeTimeOfDayInfo(SLOT_RULE_END_OF_DAY); // 5pm
        endInMillis = endInfo.getMilliSeconds();
        rule.setStartTimeOfDay(startInfo);
        rule.setEndTimeOfDay(endInfo);
        // TODO: Eventually set the type once there is a type to set to
        TimeAmountInfo tao = new TimeAmountInfo();
        tao.setTimeQuantity(15); // Every fifteen minutes
        tao.setAtpDurationTypeKey(AtpServiceConstants.DURATION_MONTH_TYPE_KEY); // TODO: Not valid--waiting for MINUTE type to be created
        rule.setSlotStartInterval(tao);
    }
    // ---------------------------------- IN SUPPORT OF TEST ---------------------------------------
    private int _computeMinuteOffset(TimeOfDayInfo timeOfDay) {
        long millis = timeOfDay.getMilliSeconds();
        return (int) millis / 60000;
    }
    
    private void _checkAppointmentSlots(List<AppointmentSlotInfo> slots, Date startWindow, Date endWindow, AppointmentSlotRuleInfo slotRule) {
        AppointmentSlotInfo prev = null;
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
//        try {
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
//        } catch (Exception e) {
//            e.printStackTrace();
//            assert(false);
//        }
//    }
    
    @Test
    public void testGenerateAppointmentsByWindow() {
        before();
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testGetAppointmentWindowsByPeriod() {
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
            try {
                appointmentService.createAppointmentWindow(info.getTypeKey(), info, contextInfo);
            } catch (Exception e) {
                e.printStackTrace();
                assert(false);
            }
        }
        // Now fetch the windows
        try {
            List<AppointmentWindowInfo> list = appointmentService.getAppointmentWindowsByPeriod(milestoneId, contextInfo);
            assertEquals(3, list.size());
            System.err.println("Got here");
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testApptWindowDeleteOneSlot() {
        before();
        try {
            AppointmentWindowInfo info =
                    appointmentService.createAppointmentWindow(apptWindowInfo.getTypeKey(), apptWindowInfo, contextInfo);
            List<AppointmentSlotInfo> slotInfoList =
                    appointmentService.generateAppointmentSlotsByWindow(info.getId(), contextInfo);
            assertEquals(slotInfoList.size(), 1);
            appointmentService.deleteAppointmentWindow(info.getId(), contextInfo);
            try {
                AppointmentSlotInfo slotInfo = appointmentService.getAppointmentSlot(slotInfoList.get(0).getId(), contextInfo);
                assert(false); // Shouldn't reach here
            } catch (DoesNotExistException e) {
                assert(true);
            } catch (Exception e) {
                assert(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }
    
    @Test
    public void testGenerateMaxSlotsByWindow() {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        try {
            apptWindowInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY);
            AppointmentWindowInfo window = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY,
                    apptWindowInfo, contextInfo);
            List<AppointmentSlotInfo> slots =
                    appointmentService.generateAppointmentSlotsByWindow(window.getId(), contextInfo);
            AppointmentSlotRuleInfo slotRule = window.getSlotRule();
            _checkAppointmentSlots(slots, window.getStartDate(), window.getEndDate(), slotRule);
            List<AppointmentSlotInfo> slotInfoList = appointmentService.getAppointmentSlotsByWindow(window.getId(), contextInfo);
        } catch (Exception e) {
            System.err.println("Exception");
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testGenerateOneSlotPerWindow() {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        try {
            AppointmentWindowInfo window = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY,
                    apptWindowInfo, contextInfo);
            List<AppointmentSlotInfo> slots =
                    appointmentService.generateAppointmentSlotsByWindow(window.getId(), contextInfo);
            assert(slots.size() == 1);
            AppointmentSlotInfo oneSlot = slots.get(0);
            assertEquals(oneSlot.getStartDate(), apptWindowInfo.getStartDate());
            assertNull(oneSlot.getEndDate()); // TODO: Change for
        } catch (Exception e) {
            System.err.println("Exception");
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testApptWinCreate() {
        before();
        try {
            AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                                                        apptWindowInfo, new ContextInfo());
            // Now try to get it back
            String id = windowInfo.getId();
            AppointmentWindowInfo info = appointmentService.getAppointmentWindow(id, contextInfo);
            assertNotNull(info);
            // TODO: Add another test
        } catch (Exception e) {
            System.err.println("Exception caught ==========================");
            e.printStackTrace();
            assert(false); // If exception is thrown, make unit test fail
        }
    }

    @Test
    public void testApptWinDelete() {
        before();
        boolean shouldExist = true;
        try {
            AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, contextInfo);
            String id = windowInfo.getId();
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
        before();
        try {
            AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, new ContextInfo());
            String id = windowInfo.getId();
            // Fetch it
            AppointmentWindowInfo retrieved = appointmentService.getAppointmentWindow(id, contextInfo);
            assertNotNull(retrieved);
            // First verify that we're getting the data we sent in
            Long retrievedMillis = retrieved.getSlotRule().getStartTimeOfDay().getMilliSeconds();
            assertEquals(startInMillis, retrievedMillis);
            // Then, update the startInMillis (rule is already inside apptWindowInfo
            rule.setStartTimeOfDay(makeTimeOfDayInfo(SLOT_RULE_START_OF_DAY+1));
            Long newStartInMillis = computeHoursInMillis(SLOT_RULE_START_OF_DAY+1);
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
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        try {
            AppointmentWindowInfo windowInfo = appointmentService.createAppointmentWindow(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL,
                    apptWindowInfo, contextInfo);
            String apptWinId = windowInfo.getId();
            AppointmentSlotInfo created = appointmentService.createAppointmentSlot(apptWinId, AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY,
                    apptSlotInfo, contextInfo);
            // Now try to get it back
            AppointmentSlotInfo info = appointmentService.getAppointmentSlot(created.getId(), contextInfo);
            assertNotNull(info);
            assertEquals(created.getId(), info.getId());
        } catch (Exception e) {
            System.err.println("Exception");
            assert(false);
        }
    }

    @Test
    public void testApptSlotDelete() {
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        boolean shouldExist = true;
        try {
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
            System.err.println("Getting ready to delete");
            appointmentService.deleteAppointmentSlot(info.getId(), contextInfo);
            shouldExist = false;
            appointmentService.getAppointmentSlot(createdId, contextInfo); // should throw DoesNotExistException
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
        before();
        // This requires AppointmentWindow to be created so AppointmentSlot can refer to it
        try {
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
}
