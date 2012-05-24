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

import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import edu.emory.mathcs.backport.java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.junit.Before;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import static org.junit.Assert.*;

/**
 * Tests the mock persistence implementation to make sure it does basic crud
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appt-mock-persistence-impl-test-context.xml"})
public class TestAppointmentServiceMockPersistenceImpl {

    @Resource(name = "appointmentService")
    private AppointmentService appointmentService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
//        try {
//            loadData();
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }

    @Test
    public void testCRUD() throws Exception {
        this.testCRUDAppointmentWindow();
    }

    private void testCRUDAppointmentWindow() throws Exception {
        // create
        AppointmentWindowInfo orig = new AppointmentWindowInfo();
        orig.setName("test appt window name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL);
        orig.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new Date(new Date().getTime() + 100000));
        orig.setAssignedOrderTypeKey("testOrder");
        orig.setAssignedPopulationId("testPopId");
        orig.setMaxAppointmentsPerSlot(100);
        orig.setPeriodMilestoneId("test.reg.period.1");
        orig.setSlotRule(new AppointmentSlotRuleInfo());
        orig.getSlotRule().setStartTimeOfDay(getTimeOfDay(1000L));
        orig.getSlotRule().setEndTimeOfDay(getTimeOfDay(2000L));
        orig.getSlotRule().setSlotDuration(getTimeAmount("hours", 1));
        orig.getSlotRule().setSlotStartInterval(getTimeAmount("minutes", 2));
        orig.getSlotRule().setWeekdays(Arrays.asList(1, 2, 3));
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        AppointmentWindowInfo info = appointmentService.createAppointmentWindow(orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAssignedOrderTypeKey(), info.getAssignedOrderTypeKey());
        assertEquals(orig.getAssignedPopulationId(), info.getAssignedPopulationId());
        assertEquals(orig.getMaxAppointmentsPerSlot(), info.getMaxAppointmentsPerSlot());
        assertEquals(orig.getPeriodMilestoneId(), info.getPeriodMilestoneId());
        this.compareSlotRules(orig.getSlotRule(), info.getSlotRule());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.appointmentService.getAppointmentWindow(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        this.compareSlotRules(orig.getSlotRule(), info.getSlotRule());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("test appt window name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated",
                "description formatted 1 updated"));
        orig.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY);
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        orig.setAssignedOrderTypeKey("testOrderUpdated");
        orig.setAssignedPopulationId("testPopIdUpdated");
        orig.setMaxAppointmentsPerSlot(150);
        orig.setPeriodMilestoneId("test.reg.period.1Updated");
        orig.setSlotRule(new AppointmentSlotRuleInfo());
        orig.getSlotRule().setStartTimeOfDay(getTimeOfDay(1500L));
        orig.getSlotRule().setEndTimeOfDay(getTimeOfDay(250L));
        orig.getSlotRule().setSlotDuration(getTimeAmount("eons", 2));
        orig.getSlotRule().setWeekdays(Arrays.asList(2, 3, 4));
        orig.getSlotRule().setSlotStartInterval(getTimeAmount("seconds", 3));
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue(
                "value1Updated");
        info = this.appointmentService.updateAppointmentWindow(orig.getId(), orig,
                callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        this.compareSlotRules(orig.getSlotRule(), info.getSlotRule());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.appointmentService.getAppointmentWindow(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        this.compareSlotRules(orig.getSlotRule(), info.getSlotRule());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        this.testCRUDAppointmentSlot(orig.getId());

        // delete
        orig = info;
        StatusInfo status = this.appointmentService.deleteAppointmentWindowCascading(orig.getId(),
                callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            info = this.appointmentService.getAppointmentWindow(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    private void compareWeekdays(List<Integer> weekdays1, List<Integer> weekdays2) {
        if (weekdays1 == null && weekdays2 == null) {
            return;
        }
        assertEquals(weekdays1.size(), weekdays2.size());
        List<Integer> sorted1 = new ArrayList<Integer>(weekdays1);
        Collections.sort(sorted1);
        List<Integer> sorted2 = new ArrayList<Integer>(weekdays2);
        Collections.sort(sorted2);
        assertEquals(sorted1, sorted2);
    }

    private void testCRUDAppointmentSlot(String appointmentWindowId) throws Exception {
        // create
        AppointmentSlotInfo orig = new AppointmentSlotInfo();
        orig.setAppointmentWindowId(appointmentWindowId);
        orig.setTypeKey(AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY);
        orig.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new Date(new Date().getTime() + 100000));
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        AppointmentSlotInfo info = appointmentService.createAppointmentSlot(orig.getAppointmentWindowId(),
                orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.appointmentService.getAppointmentSlot(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setStateKey(AppointmentServiceConstants.APPOINTMENT_SLOTS_STATE_ACTIVE_KEY);
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue(
                "value1Updated");
        info = this.appointmentService.updateAppointmentSlot(orig.getId(), orig,
                callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.appointmentService.getAppointmentSlot(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        this.testCRUDAppointment(orig.getId());

        // delete
        orig = info;
        StatusInfo status = this.appointmentService.deleteAppointmentSlotCascading(orig.getId(),
                callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            info = this.appointmentService.getAppointmentSlot(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    private void compareSlotRules(AppointmentSlotRuleInfo orig, AppointmentSlotRuleInfo info) {

        if (orig == null && info == null) {
            return;
        }
        if (orig == null) {
            fail("Expected null but found " + info);
        }

        if (info == null) {
            fail("Got a null but expected " + orig);
        }
        this.compareTimeOfDays(orig.getStartTimeOfDay(), info.getStartTimeOfDay());
        this.compareTimeOfDays(orig.getEndTimeOfDay(), info.getEndTimeOfDay());
        this.compareTimeAmountInfo(orig.getSlotDuration(), info.getSlotDuration());
        this.compareTimeAmountInfo(orig.getSlotStartInterval(), info.getSlotStartInterval());
        this.compareWeekdays(orig.getWeekdays(), info.getWeekdays());
    }

    private void compareTimeOfDays(TimeOfDayInfo orig, TimeOfDayInfo info) {
        if (orig == null && info == null) {
            return;
        }
        if (orig == null) {
            fail("Expected null but found " + info);
        }

        if (info == null) {
            fail("Got a null but expected " + orig);
        }
        assertEquals(orig.getMilliSeconds(), info.getMilliSeconds());
    }

    private void compareTimeAmountInfo(TimeAmountInfo orig, TimeAmountInfo info) {
        if (orig == null && info == null) {
            return;
        }
        if (orig == null) {
            fail("Expected null but found " + info);
        }

        if (info == null) {
            fail("Got a null but expected " + orig);
        }
        assertEquals(orig.getAtpDurationTypeKey(), info.getAtpDurationTypeKey());
        assertEquals(orig.getTimeQuantity(), info.getTimeQuantity());
    }

    private void testCRUDAppointment(String appointmentSlotId) throws Exception {
        // create
        AppointmentInfo orig = new AppointmentInfo();
        orig.setPersonId("person1");
        orig.setSlotId(appointmentSlotId);
        orig.setTypeKey(AppointmentServiceConstants.APPOINTMENT_TYPE_REGISTRATION);
        orig.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        orig.setEffectiveDate(new Date());
        orig.setExpirationDate(new Date(new Date().getTime() + 100000));
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        AppointmentInfo info = appointmentService.createAppointment(orig.getPersonId(), orig.getSlotId(), orig.getTypeKey(), orig,
                callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getPersonId(), info.getPersonId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.appointmentService.getAppointment(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getPersonId(), info.getPersonId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        orig.setEffectiveDate(new Date(orig.getEffectiveDate().getTime() - 10000));
        orig.setExpirationDate(new Date(orig.getExpirationDate().getTime() + 10000));
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue(
                "value1Updated");
        info = this.appointmentService.updateAppointment(orig.getId(), orig,
                callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getPersonId(), info.getPersonId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.appointmentService.getAppointment(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getPersonId(), info.getPersonId());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // delete
        orig = info;
        StatusInfo status = this.appointmentService.deleteAppointment(orig.getId(),
                callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            info = this.appointmentService.getAppointment(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    private TimeOfDayInfo getTimeOfDay(Long milliseconds) {
        if (milliseconds == null) {
            return null;
        }
        TimeOfDayInfo info = new TimeOfDayInfo();
        info.setMilliSeconds(milliseconds);
        return info;
    }

    private TimeAmountInfo getTimeAmount(String durationType, Integer quantity) {
        if (durationType == null && quantity == null) {
            return null;
        }
        TimeAmountInfo info = new TimeAmountInfo();
        info.setAtpDurationTypeKey(durationType);
        info.setTimeQuantity(quantity);
        return info;
    }

    @Test
    public void testBulkDeletes() throws Exception {
        AppointmentWindowInfo window = new AppointmentWindowInfo();
        window.setName("appt window name");
        window.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        window.setTypeKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_MANUAL);
        window.setStateKey(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
        window.setStartDate(new Date());
        window.setEndDate(new Date(new Date().getTime() + 100000));
        window = appointmentService.createAppointmentWindow(window.getTypeKey(), window, callContext);

        // create slot
        AppointmentSlotInfo slot = new AppointmentSlotInfo();
        slot.setAppointmentWindowId(window.getId());
        slot.setTypeKey(AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY);
        slot.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        slot.setStartDate(new Date());
        slot.setEndDate(new Date(new Date().getTime() + 100000));
        slot.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        slot.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        slot = appointmentService.createAppointmentSlot(slot.getAppointmentWindowId(), slot.getTypeKey(), slot, callContext);

        // test dependent objects exists exceptions (slot for window)
        StatusInfo status = null;
//        try {
//            status = appointmentService.deleteAppointmentWindowCascading(window.getId(), contextInfo);
//
//        } catch (DependentObjectsExistException ex) {
//            // expected
//            fail("should have thrown DependentObjectsExistException");
//        }
//
//
//        // create appointment
        AppointmentInfo appt = this.createAppointment(slot);
//
//        // test dependent objects exists exceptions appointment for slot
//        try {
//            status = appointmentService.deleteAppointmentSlot(slot.getId(), contextInfo);
//            fail("should have thrown DependentObjectsExistException");
//        } catch (DependentObjectsExistException ex) {
//            // expected
//        }
//        // test dependent objects exists exceptions appointment for slot in bulk delete method
//        try {
//            status = appointmentService.deleteAppointmentSlotsByWindow(window.getId(), contextInfo);
//            fail("should have thrown DependentObjectsExistException");
//        } catch (DependentObjectsExistException ex) {
//            // expected
//        }


        // test bulk delete by slot
        status = appointmentService.deleteAppointmentsBySlot(slot.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());
        try {
            appointmentService.getAppointment(appt.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }

        // create appointment again
        appt = this.createAppointment(slot);

        // test bulk delete appointment by window
        status = appointmentService.deleteAppointmentsByWindow(window.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());
        try {
            appointmentService.getAppointment(appt.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }
        // should not have deleted the slot
        appointmentService.getAppointmentSlot(slot.getId(), callContext);
        
        // test bulk delete slots by window
        status = appointmentService.deleteAppointmentSlotsByWindowCascading(window.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());
        try {
            appointmentService.getAppointmentSlot(appt.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            // expected
        }    
        
        // clean up
        appointmentService.deleteAppointmentWindowCascading(window.getId(), callContext);

    }

    private AppointmentInfo createAppointment(AppointmentSlotInfo slot) throws Exception {
        AppointmentInfo appt = new AppointmentInfo();
        appt.setPersonId("person1");
        appt.setSlotId(slot.getId());
        appt.setTypeKey(AppointmentServiceConstants.APPOINTMENT_TYPE_REGISTRATION);
        appt.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        appt.setEffectiveDate(new Date());
        appt.setExpirationDate(new Date(new Date().getTime() + 100000));
        appt.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        appt.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        appt = appointmentService.createAppointment(appt.getPersonId(), appt.getSlotId(), appt.getTypeKey(), appt,
                callContext);
        return appt;
    }
}
