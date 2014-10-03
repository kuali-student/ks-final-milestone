package org.kuali.student.enrollment.registration.engine.processor;

import org.joda.time.DateTime;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.registration.engine.service.WaitlistManagerService;
import org.kuali.student.enrollment.registration.engine.util.RegEnginePerformanceUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.KimIdentityServiceConstants;

import java.util.List;

/**
 * Node that processes people off of the waitlist. This uses the waitlist manager service
 * to get a list of registration requests and sends them to the registration engine for processing off of the waitlist.
 */
public class CourseRegistrationWaitlistManagerProcessor {
    private WaitlistManagerService waitlistManagerService;

    public List<RegistrationRequest> process(List<String> message) {
        DateTime startTime = new DateTime();

        //Set system user in context since this is a system task
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        context.setPrincipalId(KimIdentityServiceConstants.SYSTEM_ENTITY_TYPE_KEY);
        context.setAuthenticatedPrincipalId(KimIdentityServiceConstants.SYSTEM_ENTITY_TYPE_KEY);
        try {
            List<RegistrationRequest> registrationRequestList = waitlistManagerService.
                    processPeopleOffOfWaitlist(message, context);

            DateTime endTime = new DateTime();
            RegEnginePerformanceUtil.putStatistics(RegEnginePerformanceUtil.NODES,
                    "CourseRegistrationWaitlistManagerProcessor", startTime, endTime);

            return registrationRequestList;
        } catch (Exception e) {
            throw new RuntimeException("Error processing", e);
        }
    }

    public void setWaitlistManagerService(WaitlistManagerService waitlistManagerService) {
        this.waitlistManagerService = waitlistManagerService;
    }

}
