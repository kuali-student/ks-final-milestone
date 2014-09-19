package org.kuali.student.enrollment.krms.termresolver;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.AbstractTermResolverTestHelper;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.ConflictCourseResult;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TestBestEffortTimeConflictTermResolver extends AbstractTermResolverTestHelper {

    private BestEffortTimeConflictTermResolver bestEffortTimeConflictTermResolver;
    private Map<String, Object> resolvedPrereqs;
    private Map<String, String> parameters;

    private ContextInfo contextInfo;
    private List<CourseRegistrationInfo> existingCourses;
    private List<CourseRegistrationInfo> existingWaitlist;
    private List<CourseRegistrationInfo> simulatedRegistrations;

    private List<String> regGroupIds;
    private List<RegistrationGroupInfo> regGroups;
    private List<String> aoIds;
    private Map<String, List<TimeSlotInfo>> timeSlotMap;

    // services
    private CourseOfferingService courseOfferingService;
    private ScheduleOfClassesService scheduleOfClassesService;

    @Before
    public void setUp() throws Exception {

        // create the term resolver
        bestEffortTimeConflictTermResolver = new BestEffortTimeConflictTermResolver();

        // mock services
        courseOfferingService = mock(CourseOfferingService.class);
        scheduleOfClassesService = mock(ScheduleOfClassesService.class);

        // add services to the term resolver
        bestEffortTimeConflictTermResolver.setCourseOfferingService(courseOfferingService);
        bestEffortTimeConflictTermResolver.setScheduleOfClassesService(scheduleOfClassesService);

        // set up collections
        regGroupIds = new ArrayList<>();
        regGroups = new ArrayList<>();
        aoIds = new ArrayList<>();
        timeSlotMap = new HashMap<>();

        // set up pre-requisites
        contextInfo = new ContextInfo();
        RegistrationRequestInfo regRequest = new RegistrationRequestInfo();
        RegistrationRequestItemInfo regRequestItem = getRegRequestItem();
        existingCourses = new ArrayList<>();
        existingWaitlist = new ArrayList<>();
        simulatedRegistrations = new ArrayList<>();

        resolvedPrereqs = new HashMap<>();
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.REGISTRATION_REQUEST_TERM.getName(), regRequest);
        resolvedPrereqs.put(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName(), regRequestItem);
        resolvedPrereqs.put(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName(), existingCourses);
        resolvedPrereqs.put(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName(), existingWaitlist);
        resolvedPrereqs.put(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName(), simulatedRegistrations);

        // set up parameters
        parameters = new HashMap<>();
    }

    @Test
    public void validateTermResolver() throws Exception {
        validateTermResolver(bestEffortTimeConflictTermResolver, resolvedPrereqs, parameters, KSKRMSServiceConstants.TERM_RESOLVER_BEST_EFFORT_TIME_CONFLICT);
    }

    /*
    Scenario: registering single course, nothing on the schedule or waitlist

    Expected result: pass (signified by an empty conflicts object)
     */
    @Test
    public void validateSingleCourse() throws Exception {
        assertEquals("{}", bestEffortTimeConflictTermResolver.resolve(resolvedPrereqs, parameters));
    }

    /*
    Scenario: registering single course, one course on the schedule but there's not a conflict

    -- Registering course with one ao on Monday from 9am-11am
    -- Course on schedule has one ao on Monday from 11am-1pm

    Expected result: pass (signified by an empty conflicts object)
     */
    @Test
    public void validateSingleCourseNoConflict() throws Exception {
        existingCourses.add(getCourse(1));
        assertEquals("{}", bestEffortTimeConflictTermResolver.resolve(resolvedPrereqs, parameters));
    }

    /*
    Scenario: registering single course, one course on the schedule with a conflict

    -- Registering course with one ao on Monday from 9am-11am
    -- Course on schedule has one ao on Monday from 8am-10am

    Expected result: fail (with the course code for the conflict course on the conflicts object)
     */
    @Test
    public void validateSingleCourseWithConflict() throws Exception {
        existingCourses.add(getCourse(2));
        List<ConflictCourseResult> timeConflicts = getTimeConflicts();

        assertNotNull(timeConflicts);
        assertEquals(1, timeConflicts.size());

        ConflictCourseResult timeConflict = KSCollectionUtils.getRequiredZeroElement(timeConflicts);

        assertEquals("MOCK201", timeConflict.getCourseCode());
    }

    /*
    Scenario: registering single course, one course waitlisted with a conflict

    -- Registering course with one ao on Monday from 9am-11am
    -- Course on waitlist has one ao on Monday from 8am-10am

    Expected result: fail (with the course code for the conflict course on the conflicts object)
     */
    @Test
    public void validateSingleCourseWithWaitlistConflict() throws Exception {
        existingWaitlist.add(getCourse(2));
        List<ConflictCourseResult> timeConflicts = getTimeConflicts();

        assertNotNull(timeConflicts);
        assertEquals(1, timeConflicts.size());

        ConflictCourseResult timeConflict = KSCollectionUtils.getRequiredZeroElement(timeConflicts);

        assertEquals("MOCK201", timeConflict.getCourseCode());
    }

    /*
    Scenario: registering single course, one "simulated" course with a conflict

    -- Registering course with one ao on Monday from 9am-11am
    -- Simulated course has one ao on Monday from 8am-10am

    Expected result: fail (with the course code for the conflict course on the conflicts object)
     */
    @Test
    public void validateSingleCourseWithSimulatedConflict() throws Exception {
        simulatedRegistrations.add(getCourse(2));
        List<ConflictCourseResult> timeConflicts = getTimeConflicts();

        assertNotNull(timeConflicts);
        assertEquals(1, timeConflicts.size());

        ConflictCourseResult timeConflict = KSCollectionUtils.getRequiredZeroElement(timeConflicts);

        assertEquals("MOCK201", timeConflict.getCourseCode());
    }

    /*
    Scenario: registering single course, two courses on the schedule, one with a conflict

    -- Registering course with one ao on Monday from 9am-11am
    -- First scheduled course has one ao on Monday from 8am-10am (conflict)
    -- Second scheduled course has one ao on Monday from 11am-1pm

    Expected result: fail (with the course code for the first scheduled course on the conflicts object)
     */
    @Test
    public void validateSingleCourseAgainstTwoRegistered() throws Exception {
        existingCourses.add(getCourse(2));
        existingCourses.add(getCourse(1));

        List<ConflictCourseResult> timeConflicts = getTimeConflicts();

        assertNotNull(timeConflicts);
        assertEquals(1, timeConflicts.size());

        ConflictCourseResult timeConflict = KSCollectionUtils.getRequiredZeroElement(timeConflicts);

        assertEquals("MOCK201", timeConflict.getCourseCode());
    }

    /*
    Scenario: registering single course, two courses on the schedule, one with a conflict

    -- Registering course with one ao on Monday from 9am-11am
    -- First scheduled course has one ao on Monday from 8am-10am (conflict)
    -- Second scheduled course has one ao on Monday from 11am-1pm
    -- Third scheduled course has one ao on Monday from 10am-11am (conflict)

    Expected result: fail (with the course code for the first scheduled course on the conflicts object)
    */
    @Test
    public void validateSingleCourseAgainstTwoConflicts() throws Exception {
        existingCourses.add(getCourse(2));
        existingCourses.add(getCourse(1));
        existingCourses.add(getCourse(3));

        List<ConflictCourseResult> timeConflicts = getTimeConflicts();

        assertNotNull(timeConflicts);
        assertEquals(2, timeConflicts.size());

        assertEquals("MOCK201", timeConflicts.get(0).getCourseCode());
        assertEquals("MOCK301", timeConflicts.get(1).getCourseCode());
    }

    private RegistrationRequestItemInfo getRegRequestItem() throws Exception {
        RegistrationRequestItemInfo regRequestItem = new RegistrationRequestItemInfo();
        regRequestItem.setId("regRequestItem1");
        regRequestItem.setRegistrationGroupId("regGroup1");
        addRegGroupToService("regGroup1", false);

        return regRequestItem;
    }

    private CourseRegistrationInfo getCourse(int courseId) throws Exception {
        CourseRegistrationInfo courseRegistrationInfo = new CourseRegistrationInfo();

        String regGroupId = null;
        String courseOfferingId = null;

        switch (courseId) {
            case 1:
                courseRegistrationInfo.setId("course1");
                regGroupId = "courseRegGroup1";
                courseOfferingId = "existingCO1";
                break;
            case 2:
                courseRegistrationInfo.setId("course2");
                courseRegistrationInfo.setCourseOfferingId("existingCO2");
                regGroupId = "courseRegGroup2";
                courseOfferingId = "existingCO2";
                break;
            case 3:
                courseRegistrationInfo.setId("course3");
                courseRegistrationInfo.setCourseOfferingId("existingCO3");
                regGroupId = "courseRegGroup3";
                courseOfferingId = "existingCO3";
                break;
        }

        courseRegistrationInfo.setRegistrationGroupId(regGroupId);
        courseRegistrationInfo.setCourseOfferingId(courseOfferingId);

        addRegGroupToService(regGroupId, true);
        addCourseOfferingToService(courseOfferingId);

        return courseRegistrationInfo;
    }

    private void addRegGroupToService(String regGroupId, boolean existing) throws Exception {
        RegistrationGroupInfo regGroup = getRegistrationGroup(regGroupId, existing);
        if (existing) {
            regGroupIds.add(regGroupId);
            regGroups.add(regGroup);
            when(courseOfferingService.getRegistrationGroupsByIds(regGroupIds, contextInfo)).thenReturn(regGroups);
        } else {
            List<String> singleRegGroupId = new ArrayList<>();
            List<RegistrationGroupInfo> singleRegGroup = new ArrayList<>();
            singleRegGroupId.add(regGroupId);
            singleRegGroup.add(regGroup);
            when(courseOfferingService.getRegistrationGroupsByIds(singleRegGroupId, contextInfo)).thenReturn(singleRegGroup);
        }
    }

    private RegistrationGroupInfo getRegistrationGroup(String regGroupId, boolean existing) throws Exception {
        RegistrationGroupInfo regGroup = new RegistrationGroupInfo();
        regGroup.setId(regGroupId);

        List<String> activityOfferingIds = new ArrayList<>();
        regGroup.setActivityOfferingIds(activityOfferingIds);

        String aoId = null;

        switch (regGroupId) {
            case "regGroup1":
                aoId = "ao1";
                break;
            case "courseRegGroup1":
                aoId = "courseAO1";
                break;
            case "courseRegGroup2":
                aoId = "courseAO2";
                break;
            case "courseRegGroup3":
                aoId = "courseAO3";
                break;
        }

        activityOfferingIds.add(aoId);
        buildTimeSlotMap(aoId, existing);

        when(courseOfferingService.getRegistrationGroup(regGroupId, contextInfo)).thenReturn(regGroup);

        return regGroup;
    }

    private void buildTimeSlotMap(String aoId, boolean existing) {
        List<TimeSlotInfo> timeSlots = new ArrayList<>();

        TimeSlotInfo timeSlot = new TimeSlotInfo();
        TimeOfDayInfo startTime = null;
        TimeOfDayInfo endTime = null;

        List<Integer> weekdays = new ArrayList<>();
        weekdays.add(1);

        switch (aoId) {
            case "ao1":
                startTime = new TimeOfDayInfo(9, 0);
                endTime = new TimeOfDayInfo(11, 0);
                break;
            case "courseAO1":
                timeSlot = new TimeSlotInfo();
                startTime = new TimeOfDayInfo(11, 0);
                endTime = new TimeOfDayInfo(13, 0);
                break;
            case "courseAO2":
                timeSlot = new TimeSlotInfo();
                startTime = new TimeOfDayInfo(8, 0);
                endTime = new TimeOfDayInfo(10, 0);
                break;
            case "courseAO3":
                timeSlot = new TimeSlotInfo();
                startTime = new TimeOfDayInfo(10, 0);
                endTime = new TimeOfDayInfo(11, 0);
                break;
        }

        timeSlot.setWeekdays(weekdays);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlots.add(timeSlot);

        if (existing) {
            timeSlotMap.put(aoId, timeSlots);
            aoIds.add(aoId);
            when(scheduleOfClassesService.getAoTimeSlotMap(aoIds, contextInfo)).thenReturn(timeSlotMap);
        } else {
            Map<String, List<TimeSlotInfo>> singleTimeSlotMap = new HashMap<>();
            List<String> singleAoIds = new ArrayList<>();
            singleTimeSlotMap.put(aoId, timeSlots);
            singleAoIds.add(aoId);
            when(scheduleOfClassesService.getAoTimeSlotMap(singleAoIds, contextInfo)).thenReturn(singleTimeSlotMap);
        }
    }

    private void addCourseOfferingToService(String courseOfferingId) throws Exception {
        CourseOfferingInfo courseofferingInfo = new CourseOfferingInfo();

        switch (courseOfferingId) {
            case "existingCO1":
                courseofferingInfo.setCourseOfferingCode("MOCK101");
                break;
            case "existingCO2":
                courseofferingInfo.setCourseOfferingCode("MOCK201");
                break;
            case "existingCO3":
                courseofferingInfo.setCourseOfferingCode("MOCK301");
                break;
        }

        when(courseOfferingService.getCourseOffering(courseOfferingId, contextInfo)).thenReturn(courseofferingInfo);
    }

    private List<ConflictCourseResult> getTimeConflicts() throws Exception {
        String timeConflictJson = bestEffortTimeConflictTermResolver.resolve(resolvedPrereqs, parameters);

        assertNotEquals("{}", timeConflictJson); // empty object indicates no conflicts

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(timeConflictJson, new TypeReference<List<ConflictCourseResult>>(){});
    }
}
