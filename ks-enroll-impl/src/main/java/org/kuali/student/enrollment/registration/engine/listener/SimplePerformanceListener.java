package org.kuali.student.enrollment.registration.engine.listener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is designed to calculate performance metrics for a particular queue. In order to use
 * this class just place it at the front and end of a queue. This is designed for the Kuali Student Registration
 * Engine.
 */
public class SimplePerformanceListener implements MessageListener {

    public static final String QUEUE_NAME = "org.kuali.student.enrollment.registration.performanceStatsQueue";

    private static final Logger LOG = Logger.getLogger(BaseRegistrationListener.class);

    // Stores a KEY and a Start Timestamp
    private Map<String, Long> performanceMap = new HashMap<String, Long>();
    private long requestCount = 0;
    private long totalRequestTime = 0;
    private long averageRequestTime = 0;

    private JmsTemplate jmsTemplate;

    @Override
    public void onMessage(Message message) {

        long startTime = 0;
        long endTime = 0;
        String regReqId = "";
        MapMessage mapMessage = new ActiveMQMapMessage(); // we are using map messages so we can send multiple pieces of data

        try{
            if(message.getJMSReplyTo() != null){
               jmsTemplate.convertAndSend(message.getJMSReplyTo(), getPerformanceStats());
            } else{
                //Pull the User ID and the Registration Request ID from the message.
                if (message instanceof MapMessage) {
                    try {
                        startTime = ((MapMessage) message).getLong("startTime");
                        endTime = ((MapMessage) message).getLong("endTime");
                        regReqId = ((MapMessage) message).getString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID);
                    } catch (JMSException e) {
                        LOG.error("Error getting message content", e);
                    }
                }
                updateCounts(regReqId, startTime, endTime);
            }
        }catch (Exception ex){
            LOG.error(ex);
        }





    }

    private synchronized void updateCounts(String regReqId, long startTime, long endTime){
        if(!performanceMap.containsKey(regReqId)){
            performanceMap.put(regReqId, startTime);
        }   else{
            long storedStart = performanceMap.remove(regReqId);
            long delta = endTime - storedStart;

            requestCount++; // the request count must be incremented here, not when it's initialized
            totalRequestTime += delta;
            averageRequestTime = totalRequestTime / requestCount;
        }
    }

    private synchronized MapMessage getPerformanceStats() throws Exception{
        MapMessage perfMap = new ActiveMQMapMessage();

        perfMap.setString("requestCount", Long.toString(requestCount));
        perfMap.setString("totalRequestTimeMillis", Long.toString(totalRequestTime));
        perfMap.setString("averageRequestTimeMillis", Long.toString(averageRequestTime));

        return perfMap;

    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }




}
