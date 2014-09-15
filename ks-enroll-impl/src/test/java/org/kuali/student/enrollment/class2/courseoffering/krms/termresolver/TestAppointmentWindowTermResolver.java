package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TestAppointmentWindowTermResolver extends AbstractTermResolverTestHelper {

    private AppointmentWindowTermResolver appointmentWindowTermResolver;
    private Map<String, Object> resolvedPrereqs;
    private Map<String, String> parameters;

    private List<AppointmentSlotInfo> appointmentSlots;

    @Before
    public void setUp() throws Exception {

        // create the term resolver
        appointmentWindowTermResolver = new AppointmentWindowTermResolver();

        // set up pre-requisites
        DateTime currentDateTime = new DateTime(2014, 9, 15, 10, 18);
        Date currentDate = currentDateTime.toDate();
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setCurrentDate(currentDate);
        String personId = "MOCK.PERSON";
        String atpId = "MOCK.ATP";
        resolvedPrereqs = new HashMap<>();
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), personId);
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), atpId);

        // set up parameters
        parameters = new HashMap<>();

        // set up milestones
        String milestone001Id = "milestone-001";
        List<MilestoneInfo> milestones = new ArrayList<>();
        MilestoneInfo milestone001 = new MilestoneInfo();
        milestone001.setId(milestone001Id);
        milestones.add(milestone001);

        // set up atp service
        AtpService atpService = mock(AtpService.class);
        when(atpService.getMilestonesByTypeForAtp(atpId, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, contextInfo)).thenReturn(milestones);

        // set up appointment slots
        appointmentSlots = new ArrayList<>();

        // set up appointment service
        AppointmentService appointmentService = mock(AppointmentService.class);
        when(appointmentService.getAppointmentSlotsByPersonAndPeriod(personId, milestone001Id, contextInfo)).thenReturn(appointmentSlots);

        // add services to the term resolver
        appointmentWindowTermResolver.setAtpService(atpService);
        appointmentWindowTermResolver.setAppointmentService(appointmentService);
    }

    @Test
    public void validateTermResolver() throws Exception {
        validateTermResolver(appointmentWindowTermResolver, resolvedPrereqs, parameters, KSKRMSServiceConstants.TERM_RESOLVER_APPOINTMENT_WINDOW);
    }

    @Test
    public void testNoAppointmentSlotsInPeriod() throws Exception {
        assertFalse(appointmentWindowTermResolver.resolve(resolvedPrereqs, parameters));
    }

    @Test
    public void testTooEarlyForAppointment() throws Exception {
        AppointmentSlotInfo appointmentSlotInfo = new AppointmentSlotInfo();
        DateTime startDateTime = new DateTime(2014, 9, 16, 0, 1);
        DateTime endDateTime = new DateTime(2014, 9, 22, 23, 59);
        appointmentSlotInfo.setStartDate(startDateTime.toDate());
        appointmentSlotInfo.setEndDate(endDateTime.toDate());
        appointmentSlots.add(appointmentSlotInfo);
        assertFalse(appointmentWindowTermResolver.resolve(resolvedPrereqs, parameters));
    }

    @Test
    public void testTooEarlyForAppointmentNoEndDate() throws Exception {
        AppointmentSlotInfo appointmentSlotInfo = new AppointmentSlotInfo();
        DateTime startDateTime = new DateTime(2014, 9, 16, 0, 1);
        appointmentSlotInfo.setStartDate(startDateTime.toDate());
        appointmentSlots.add(appointmentSlotInfo);
        assertFalse(appointmentWindowTermResolver.resolve(resolvedPrereqs, parameters));
    }

    @Test
    public void testTooLateForAppointment() throws Exception {
        AppointmentSlotInfo appointmentSlotInfo = new AppointmentSlotInfo();
        DateTime startDateTime = new DateTime(2014, 9, 8, 0, 1);
        DateTime endDateTime = new DateTime(2014, 9, 14, 23, 59);
        appointmentSlotInfo.setStartDate(startDateTime.toDate());
        appointmentSlotInfo.setEndDate(endDateTime.toDate());
        appointmentSlots.add(appointmentSlotInfo);
        assertFalse(appointmentWindowTermResolver.resolve(resolvedPrereqs, parameters));
    }

    @Test
    public void testAppointmentSlotAvailable() throws Exception {
        AppointmentSlotInfo appointmentSlotInfo = new AppointmentSlotInfo();
        DateTime startDateTime = new DateTime(2014, 9, 15, 0, 1);
        DateTime endDateTime = new DateTime(2014, 9, 21, 23, 59);
        appointmentSlotInfo.setStartDate(startDateTime.toDate());
        appointmentSlotInfo.setEndDate(endDateTime.toDate());
        appointmentSlots.add(appointmentSlotInfo);
        assertTrue(appointmentWindowTermResolver.resolve(resolvedPrereqs, parameters));
    }

    @Test
    public void testAppointmentSlotAvailableNoEndDate() throws Exception {
        AppointmentSlotInfo appointmentSlotInfo = new AppointmentSlotInfo();
        DateTime startDateTime = new DateTime(2014, 9, 15, 0, 1);
        appointmentSlotInfo.setStartDate(startDateTime.toDate());
        appointmentSlots.add(appointmentSlotInfo);
        assertTrue(appointmentWindowTermResolver.resolve(resolvedPrereqs, parameters));
    }

}
