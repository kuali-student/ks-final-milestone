package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
import org.kuali.student.enrollment.registration.engine.service.WaitlistManagerService;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * Created by Daniel on 4/16/14.
 */
public class CourseRegistrationWaitlistManagerNode extends AbstractCourseRegistrationNode<List<String>, List<RegistrationRequest>> {
    private WaitlistManagerService waitlistManagerService;
    @Override
    public List<RegistrationRequest> process(List<String> message) {
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        context.setPrincipalId("SYSTEM");//Need some system user here
        context.setAuthenticatedPrincipalId("SYSTEM");//Need some system user here
        try {
            return waitlistManagerService.processPeopleOffOfWaitlist(message, context);
        } catch (Exception e) {
            throw new RuntimeException("Error processing", e);
        }
    }

    public WaitlistManagerService getWaitlistManagerService() {
        return waitlistManagerService;
    }

    public void setWaitlistManagerService(WaitlistManagerService waitlistManagerService) {
        this.waitlistManagerService = waitlistManagerService;
    }
}
