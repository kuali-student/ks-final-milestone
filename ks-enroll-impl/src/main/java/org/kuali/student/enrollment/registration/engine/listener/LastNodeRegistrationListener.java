package org.kuali.student.enrollment.registration.engine.listener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.springframework.jms.JmsException;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * This class is designed to be the Last listener in the registration engine.
 * <p/>
 * It performs specific last node operations.
 */
public class LastNodeRegistrationListener extends BaseRegistrationListener {

    /**
     * This implementation makes a final call to the performance monitoring queue
     *
     * @param regReqId
     * @throws JMSException
     */
    @Override
    protected void afterProcessHook(String regReqId) throws JMSException {
        MapMessage perfMap = new ActiveMQMapMessage();

        perfMap.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regReqId);
        perfMap.setLong("endTime", System.currentTimeMillis()); // get the end time

        // notify perf queue of end time.
        getJmsTemplate().convertAndSend(SimplePerformanceListener.QUEUE_NAME, perfMap);
    }
}
