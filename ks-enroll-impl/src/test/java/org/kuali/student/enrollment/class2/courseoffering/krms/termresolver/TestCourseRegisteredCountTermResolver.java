package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TestCourseRegisteredCountTermResolver extends AbstractTermResolverTestHelper {

    private CourseRegisteredCountTermResolver courseRegisteredCountTermResolver;
    private Map<String, Object> resolvedPrereqs;
    private Map<String, String> parameters;
    List<CourseRegistrationInfo> existingRegistrations;

    private String co01;

    @Before
    public void setUp() throws Exception {

        // create the term resolver
        courseRegisteredCountTermResolver = new CourseRegisteredCountTermResolver();

        // constants
        String regGroupId01 = "regGroup-01";
        co01 = "courseOffering-01";

        // set up pre-requisites
        ContextInfo contextInfo = new ContextInfo();

        RegistrationRequestItemInfo requestItemInfo = new RegistrationRequestItemInfo();
        requestItemInfo.setRegistrationGroupId(regGroupId01);

        existingRegistrations = new ArrayList<>();
        List<CourseRegistrationInfo> existingWaitlist = new ArrayList<>();
        List<CourseRegistrationInfo> simulatedRegistrations = new ArrayList<>();

        RegistrationGroupInfo regGroup = new RegistrationGroupInfo();
        regGroup.setCourseOfferingId(co01);

        resolvedPrereqs = new HashMap<>();
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName(), requestItemInfo);
        resolvedPrereqs.put(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName(), existingRegistrations);
        resolvedPrereqs.put(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName(), existingWaitlist);
        resolvedPrereqs.put(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName(), simulatedRegistrations);

        // set up parameters
        parameters = new HashMap<>();

        // set up services
        CourseOfferingService courseOfferingService = mock(CourseOfferingService.class);
        when(courseOfferingService.getRegistrationGroup(regGroupId01, contextInfo)).thenReturn(regGroup);

        // add services to resolver
        courseRegisteredCountTermResolver.setCourseOfferingService(courseOfferingService);

    }

    @Test
    public void validateTermResolver() throws Exception {
        validateTermResolver(courseRegisteredCountTermResolver, resolvedPrereqs, parameters, KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REGISTERED_COUNT);
    }

    @Test
    public void testNoRepeat() throws Exception {
        assertEquals(new Integer(0), courseRegisteredCountTermResolver.resolve(resolvedPrereqs, parameters));
    }

    @Test
    public void testRepeat() throws Exception {
        CourseRegistrationInfo courseRegistrationInfo = new CourseRegistrationInfo();
        courseRegistrationInfo.setCourseOfferingId(co01);
        existingRegistrations.add(courseRegistrationInfo);

        assertEquals(new Integer(1), courseRegisteredCountTermResolver.resolve(resolvedPrereqs, parameters));
    }

}
