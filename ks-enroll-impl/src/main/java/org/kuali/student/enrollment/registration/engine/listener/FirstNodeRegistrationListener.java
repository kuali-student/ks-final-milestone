package org.kuali.student.enrollment.registration.engine.listener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * This class is designed to be the First listener in the registration engine.
 * <p/>
 * It performs specific first node operations.
 */
public class FirstNodeRegistrationListener extends BaseRegistrationListener {

    /**
     * This implementation makes a start call to the performance monitoring queue
     *
     * @param regReqId
     * @throws JMSException
     */
    @Override
    protected void beforeProcessHook(String regReqId) throws JMSException {
        MapMessage perfMap = new ActiveMQMapMessage();

        perfMap.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regReqId);
        perfMap.setLong("startTime", System.currentTimeMillis());  // only need to set start time.

        getJmsTemplate().convertAndSend(SimplePerformanceListener.QUEUE_NAME, perfMap);
    }
}
