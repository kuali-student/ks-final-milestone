package org.kuali.student.enrollment.registration.engine.node.impl;

import org.joda.time.DateTime;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.TermResolverPerformanceUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.StaticUserDateUtil;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationEngineService;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.jms.MapMessage;

/**
 * Initializes the registration request for further processing
 */
public class CourseRegistrationInitializationNode extends AbstractCourseRegistrationNode<MapMessage, RegistrationRequestEngineMessage> {

    private CourseRegistrationEngineService courseRegistrationEngineService;

    @Override
    public RegistrationRequestEngineMessage process(MapMessage message) {
        DateTime startTime = new DateTime();

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            String userId = message.getString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_USER_ID);
            String regReqId = message.getString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID);

            contextInfo.setPrincipalId(userId);

            /*
             * If we are configured to use static dates for registration date testing, get the date for this user
             * (if it exists) and set it in the context.
             */
            DateTime staticDate = StaticUserDateUtil.getDateTimeForUser(userId);
            if (staticDate != null) {
                contextInfo.setCurrentDate(staticDate.toDate());
            }

            // Use the engine service to initialize the request
            RegistrationRequestEngineMessage registrationRequestEngineMessage =  courseRegistrationEngineService.
                    initializeRegistrationRequest(regReqId, contextInfo);

            DateTime endTime = new DateTime();
            TermResolverPerformanceUtil.putStatistics("CourseRegistrationInitializationNode", startTime, endTime);

            return registrationRequestEngineMessage;

        } catch (Exception e) {
            throw new RuntimeException("Error processing request", e);
        }
    }

    public void setCourseRegistrationEngineService(CourseRegistrationEngineService courseRegistrationEngineService) {
        this.courseRegistrationEngineService = courseRegistrationEngineService;
    }
}
