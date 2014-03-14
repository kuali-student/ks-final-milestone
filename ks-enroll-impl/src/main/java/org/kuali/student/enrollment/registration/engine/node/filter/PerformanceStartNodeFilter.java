package org.kuali.student.enrollment.registration.engine.node.filter;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.listener.SimplePerformanceListener;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNodeFilter;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.MapMessage;
import java.util.List;

/**
 * This class is designed to be the First listener in the registration engine.
 * <p/>
 * It performs specific first node operations.
 */
public class PerformanceStartNodeFilter extends AbstractCourseRegistrationNodeFilter<MapMessage, RegistrationRequestEngineMessage> {
    /**
     * This implementation makes a start call to the performance monitoring queue
     *
     * @param inMessage    input message
     * @param jmsTemplate  template for JMS access
     * @param destinations list of destinations for the current node
     * @return passes through the input message
     */
    @Override
    public MapMessage beforeProcess(MapMessage inMessage, JmsTemplate jmsTemplate, List destinations) {
        try {
            MapMessage perfMap = new ActiveMQMapMessage();

            String regReqId = inMessage.getString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID);

            perfMap.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regReqId);
            perfMap.setLong("startTime", System.currentTimeMillis());  // only need to set start time.

            jmsTemplate.convertAndSend(SimplePerformanceListener.QUEUE_NAME, perfMap);

            return inMessage;
        } catch (Exception e) {
            throw new RuntimeException("Error starting performance processing");
        }
    }
}
