package org.kuali.student.enrollment.registration.engine.node.filter;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.listener.SimplePerformanceListener;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNodeFilter;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.MapMessage;
import java.util.List;

/**
 * This class is designed to be the Last listener in the registration engine.
 * <p/>
 * It performs specific last node operations.
 */
public class PerformanceEndNodeFilter extends AbstractCourseRegistrationNodeFilter<RegistrationRequestItemEngineMessage, RegistrationRequestItemEngineMessage> {

    /**
     * This implementation makes a final call to the performance monitoring queue
     *
     * @param inMessage    input message
     * @param outMessage   output message
     * @param jmsTemplate  template for JMS access
     * @param destinations list of destinations for the current node
     * @return passes through the input message
     */
    @Override
    public RegistrationRequestItemEngineMessage afterProcess(RegistrationRequestItemEngineMessage inMessage, RegistrationRequestItemEngineMessage outMessage, JmsTemplate jmsTemplate, List<String> destinations) {
        try {
            MapMessage perfMap = new ActiveMQMapMessage();

            String regReqId = inMessage.getRequestItem().getRegistrationRequestId();

            perfMap.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regReqId);
            perfMap.setLong("endTime", System.currentTimeMillis()); // get the end time

            // notify perf queue of end time.
            jmsTemplate.convertAndSend(SimplePerformanceListener.QUEUE_NAME, perfMap);
            return outMessage;
        } catch (Exception e) {
            throw new RuntimeException("Error starting performance processing");
        }
    }
}
