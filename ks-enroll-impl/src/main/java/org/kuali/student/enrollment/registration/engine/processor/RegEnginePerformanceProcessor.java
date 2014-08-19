package org.kuali.student.enrollment.registration.engine.processor;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.registration.engine.listener.SimplePerformanceListener;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.MapMessage;

/**
 * Created by swedev on 3/19/14.
 */
public class RegEnginePerformanceProcessor {

    JmsTemplate jmsTemplate;
    public static final Logger LOGGER = LoggerFactory.getLogger(RegEnginePerformanceProcessor.class);
    public void notifyPerfEnd(String regReqId){
        LOGGER.info("Doing End Performance Logging for regRequestId:"+regReqId);
        try {
            MapMessage perfMap = new ActiveMQMapMessage();

            perfMap.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regReqId);
            perfMap.setLong("endTime", System.currentTimeMillis()); // get the end time

            // notify perf queue of end time.
            jmsTemplate.convertAndSend(SimplePerformanceListener.QUEUE_NAME, perfMap);
        } catch (Exception e) {
            throw new RuntimeException("Error starting performance processing", e);
        }
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
