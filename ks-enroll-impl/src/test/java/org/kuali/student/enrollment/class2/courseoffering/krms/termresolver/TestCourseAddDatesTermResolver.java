package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.dto.CourseAddDates;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TestCourseAddDatesTermResolver extends AbstractTermResolverTestHelper {

    private CourseAddDatesTermResolver courseAddDatesTermResolver;
    private Map<String, Object> resolvedPrereqs;
    private Map<String, String> parameters;

    private ContextInfo contextInfo;
    private MilestoneInfo mstoneEarlyReg;
    List<MilestoneInfo> nonApptMilestones;

    private List<AppointmentSlotInfo> appointmentSlots;

    @Before
    public void setUp() throws Exception {

        // create the term resolver
        courseAddDatesTermResolver = new CourseAddDatesTermResolver();

        // set up pre-requisites
        contextInfo = new ContextInfo();
        contextInfo.setCurrentDate(new DateTime(2014, 9, 15, 10, 18).toDate());
        String personId = "MOCK.PERSON";
        String atpId = "MOCK.ATP";
        resolvedPrereqs = new HashMap<>();
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), personId);
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), atpId);

        // set up parameters
        parameters = new HashMap<>();

        // set up milestones
        List<MilestoneInfo> earlyRegMilestones = new ArrayList<>();
        String mstoneEarlyRegId = "milestone-early-reg";
        mstoneEarlyReg = new MilestoneInfo();
        mstoneEarlyReg.setId(mstoneEarlyRegId);
        earlyRegMilestones.add(mstoneEarlyReg);

        nonApptMilestones = new ArrayList<>();
        String mstoneNonApptId = "milestone-non-appt";
        MilestoneInfo mstoneNonAppt = new MilestoneInfo();
        mstoneNonAppt.setId(mstoneNonApptId);
        mstoneNonAppt.setStartDate(new DateTime(2014, 9, 16, 0, 1).toDate());
        mstoneNonAppt.setEndDate(new DateTime(2014, 9, 17, 11, 59).toDate());
        nonApptMilestones.add(mstoneNonAppt);

        List<MilestoneInfo> schedAdjMilestones = new ArrayList<>();
        String mstoneSchedAdjId = "milestone-sched-adj";
        MilestoneInfo mstoneSchedAdj = new MilestoneInfo();
        mstoneSchedAdj.setId(mstoneSchedAdjId);
        mstoneSchedAdj.setStartDate(new DateTime(2014, 9, 18, 0, 1).toDate());
        mstoneSchedAdj.setEndDate(new DateTime(2014, 9, 19, 11, 59).toDate());
        schedAdjMilestones.add(mstoneSchedAdj);

        // set up atp service
        AtpService atpService = mock(AtpService.class);
        when(atpService.getMilestonesByTypeForAtp(atpId,
                AtpServiceConstants.MILESTONE_EARLY_REGISTRATION_PERIOD_TYPE_KEY, contextInfo)).
                thenReturn(earlyRegMilestones);
        when(atpService.getMilestonesByTypeForAtp(atpId,
                AtpServiceConstants.MILESTONE_EARLY_REGISTRATION_PERIOD_NONAPPT_TYPE_KEY, contextInfo)).
                thenReturn(nonApptMilestones);
        when(atpService.getMilestonesByTypeForAtp(atpId,
                AtpServiceConstants.MILESTONE_SCHEDULE_ADJUSTMENT_PERIOD_TYPE_KEY, contextInfo)).
                thenReturn(schedAdjMilestones);

        // set up appointment slots
        appointmentSlots = new ArrayList<>();

        // set up appointment service
        AppointmentService appointmentService = mock(AppointmentService.class);
        when(appointmentService.getAppointmentSlotsByPersonAndPeriod(personId, mstoneEarlyRegId, contextInfo)).thenReturn(appointmentSlots);

        // add services to the term resolver
        courseAddDatesTermResolver.setAtpService(atpService);
        courseAddDatesTermResolver.setAppointmentService(appointmentService);
    }

    @Test
    public void validateTermResolver() throws Exception {
        validateTermResolver(courseAddDatesTermResolver, resolvedPrereqs, parameters, RulesExecutionConstants.COURSE_ADD_DATES_TERM.getName());
    }

    @Test
    /*
     * If the student has an appointment slot in the future, the only field provided will be "appointmentSlot", which will
     * be the slot date/time formatted as such: March 19th, 2012, 09:00AM
     */
    public void testAppointmentSlotInFuture() throws Exception {
        AppointmentSlotInfo appointmentSlotInfo = new AppointmentSlotInfo();
        appointmentSlotInfo.setStartDate(new DateTime(2014, 9, 16, 0, 1).toDate());
        appointmentSlots.add(appointmentSlotInfo);

        CourseAddDates courseAddDates = getCourseAddDates();

        assertNotNull(courseAddDates);
        assertNotNull(courseAddDates.getAppointmentSlot());
        assertEquals("September 16, 2014, 12:01 AM", courseAddDates.getAppointmentSlot());
    }

    @Test
    /*
     * If the student is attempting to register during the early registration period, but does not have an appointment, the
     * only field provided will be "noAppointment", set as a boolean to true.
     */
    public void testNoAppointment() throws Exception {
        mstoneEarlyReg.setStartDate(new DateTime(2014, 9, 15, 0, 1).toDate());
        mstoneEarlyReg.setEndDate(new DateTime(2014, 9, 16, 11, 59).toDate());

        CourseAddDates courseAddDates = getCourseAddDates();

        assertNotNull(courseAddDates);
        assertNotNull(courseAddDates.getNoAppointment());
        assertTrue(courseAddDates.getNoAppointment());
    }

    @Test
    /*
     * If the student is attempting to register too early, the only field provided will be "startDate", which will be set as
     * the first available registration date formatted as a String M/dd/yyyy.
     *
     * Scenario:
     * -- Student is registering on 9/15
     * -- Non-appointment early registration set up 9/16-9/17
     * -- Scheduled adjustment period set up 9/18-9/19
     *
     * Expected result:
     * -- Student is given 9/16 as the registration start date
     */
    public void testTooEarlyWithNonApptPeriod() throws Exception {
        CourseAddDates courseAddDates = getCourseAddDates();

        assertNotNull(courseAddDates);
        assertNotNull(courseAddDates.getStartDate());
        assertEquals("9/16/2014", courseAddDates.getStartDate());
    }

    @Test
    /*
     * If the student is attempting to register too early, the only field provided will be "startDate", which will be set as
     * the first available registration date formatted as a String M/dd/yyyy.
     *
     * Scenario:
     * -- Student is registering on 9/15
     * -- No Non-appointment early registration period
     * -- Scheduled adjustment period set up 9/18-9/19
     *
     * Expected result:
     * -- Student is given 9/18 as the registration start date
     */
    public void testTooEarlyWithoutNonApptPeriod() throws Exception {
        nonApptMilestones.clear();

        CourseAddDates courseAddDates = getCourseAddDates();

        assertNotNull(courseAddDates);
        assertNotNull(courseAddDates.getStartDate());
        assertEquals("9/18/2014", courseAddDates.getStartDate());
    }

    @Test
    /*
     * If the student is attempting to register too late, the only field provided will be "endDate", which will be set as
     * the last available registration date formatted as a String M/dd/yyyy.
     *
     * Scenario:
     * -- Student is registering on 9/20
     * -- Non-appointment early registration set up 9/16-9/17
     * -- Scheduled adjustment period set up 9/18-9/19
     *
     * Expected result:
     * -- Student is given 9/19 as the registration end date
     */
    public void testTooLate() throws Exception {
        contextInfo.setCurrentDate(new DateTime(2014, 9, 20, 10, 18).toDate());

        CourseAddDates courseAddDates = getCourseAddDates();

        assertNotNull(courseAddDates);
        assertNotNull(courseAddDates.getEndDate());
        assertEquals("9/19/2014", courseAddDates.getEndDate());
    }

    private CourseAddDates getCourseAddDates() throws Exception {
        String courseAddDatesString = courseAddDatesTermResolver.resolve(resolvedPrereqs, parameters);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(courseAddDatesString, CourseAddDates.class);
    }

}
