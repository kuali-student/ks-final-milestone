package org.kuali.student.enrollment.class2.courseoffering.aop;

import org.aspectj.lang.JoinPoint;
import org.kuali.student.common.eventing.BaseEventingAspect;
import org.kuali.student.common.eventing.EventMessage;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This AOP aspect broadcasts course offering method calls to a JMS broker.
 */
public class CourseOfferingBroadcastAspect extends BaseEventingAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(CourseOfferingBroadcastAspect.class);


    /**
     * Fires an event when a registration group is deleted
     * @param joinPoint
     * @throws Throwable
     */
    public void deleteRegistrationGroupAdvise(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String luiId = (String)args[0];
        ContextInfo contextInfo = (ContextInfo)args[1];


        // build an event message to broadcast
        EventMessage eventMessage = new EventMessage();
        eventMessage.setAction(EventMessage.Action.DELETE.toString());
        eventMessage.setId(luiId);
        eventMessage.setType(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        eventMessage.setContextInfo(contextInfo);

        super.sendEventMessage(eventMessage);

    }
}
