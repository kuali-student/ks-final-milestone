package org.kuali.student.enrollment.registration.engine.node.impl;

import org.joda.time.DateTime;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
import org.kuali.student.enrollment.registration.engine.service.WaitlistManagerService;
import org.kuali.student.enrollment.registration.engine.util.NodePerformanceUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.KimIdentityServiceConstants;

import java.util.List;

/**
 * Node that processes people off of the waitlist. This uses the waitlist manager service
 * to get a list of registration requests and sends them to the registration engine for processing off of the waitlist.
 */
public class CourseRegistrationWaitlistManagerNode extends AbstractCourseRegistrationNode<List<String>, List<RegistrationRequest>> {
    private WaitlistManagerService waitlistManagerService;

    @Override
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
            NodePerformanceUtil.putStatistics("CourseRegistrationWaitlistManagerNode", startTime, endTime);

            return registrationRequestList;
        } catch (Exception e) {
            throw new RuntimeException("Error processing", e);
        }
    }

    public void setWaitlistManagerService(WaitlistManagerService waitlistManagerService) {
        this.waitlistManagerService = waitlistManagerService;
    }
}
