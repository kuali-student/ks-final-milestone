package org.kuali.student.enrollment.registration.engine.listener;


import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.kuali.student.enrollment.registration.engine.service.RegistrationProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;

/**
 * Generic listener that will be used for most nodes in our registration system.
 * <p/>
 * Each listener will be passed a RegistrationProcessService an during the onMessage() call
 * the   RegistrationProcessService.process() method will be called.
 * <p/>
 * Any messages from the process() call will be sent to the user queue.
 * <p/>
 * Finally, we pass the standard MapMessage object to the optional list of destinations.
 */
public class BaseRegistrationListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(BaseRegistrationListener.class);


    /**
     * MQ Destinations that will be called after the registrationProcessService.process is called
     */
    protected List<String> destinations;

    /**
     * The RegistrationProcessService has one method, process and a standard result. This is where we expect the
     * "work" to be done.
     */
    protected RegistrationProcessService registrationProcessService;
    protected JmsTemplate jmsTemplate;

    @Override
    public void onMessage(Message message) {

        String userId = "";
        String regReqId = "";


        //Pull the User ID and the Registration Request ID from the message.
        if (message instanceof MapMessage) {
            try {
                userId = ((MapMessage) message).getString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_USER_ID);
                regReqId = ((MapMessage) message).getString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID);
            } catch (JMSException e) {
                throw new RuntimeException("Error getting message content", e);
            }
        }

        LOG.debug("Received Message from user id: {}", userId);
        try {

            // hook point before the process call
            beforeProcessHook(regReqId);
            // Process whatever the registrationProcessService was designed to process.
            RegistrationResponseInfo regInfo = registrationProcessService.process(regReqId);

            // hook point after the process call
            afterProcessHook(regInfo.getRegistrationRequestId());

            // notify user queue
            notifyUserQueue(userId, regInfo);

            // We can configure standard destinations via spring.
            notifyDestinations(buildResponseMessage(userId, regInfo.getRegistrationRequestId()));


        } catch (JMSException jmsEx) {
            throw new RuntimeException("Error with Processing Message", jmsEx);
        }

    }

    protected void beforeProcessHook(String regReqId) throws JMSException {
        LOG.debug("EMPTY BEFORE PROCESS HOOK");
    }



    protected void afterProcessHook(String regReqId) throws JMSException {
        LOG.debug("EMPTY AFTER PROCESS HOOK");
    }



    protected MapMessage buildResponseMessage(String userId, String regReqId) throws JMSException {
        MapMessage mapMessage = new ActiveMQMapMessage(); // we are using map messages so we can send multiple pieces of data
        mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_USER_ID, userId); // put the user id into the map
        mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regReqId); // put the reg request id into map
        return mapMessage;
    }

    protected void notifyUserQueue(String userId, RegistrationResponseInfo regInfo) {
        // Processing can generate n messages. iterate through the messages, sending them to the user's queue
        for (String regMessage : regInfo.getMessages()) {
            final String destination = CourseRegistrationConstants.USER_MESSAGE_QUEUE_PREFIX + userId;
            LOG.debug("Reg Request Message: {}", regMessage);  // debugging message
            getJmsTemplate().convertAndSend(destination, regMessage);   // sends message to user queue
        }
    }

    /**
     * Internal call that takes a single message and passes to the list destinations. Often the destinations
     * will be configured in spring.
     *
     * @param message This can be any object, but for we will generally pass the MapMessage for most communication.
     */
    protected void notifyDestinations(Object message) {

        if (destinations != null) {
            for (String destination : getDestinations()) {
                getJmsTemplate().convertAndSend(destination, message);
            }
        }
    }


    public List<String> getDestinations() {
        return destinations;
    }

    /**
     * @param destinations List of destinations that will be triggered after listener has processed.
     */
    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    protected RegistrationProcessService getRegistrationProcessService() {
        return registrationProcessService;
    }

    /**
     * @param registrationProcessService
     */
    public void setRegistrationProcessService(RegistrationProcessService registrationProcessService) {
        this.registrationProcessService = registrationProcessService;
    }

    protected JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
