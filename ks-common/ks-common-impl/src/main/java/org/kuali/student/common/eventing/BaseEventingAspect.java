package org.kuali.student.common.eventing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

/**
 * We are using AOP to broadcast events in the system. This is a base class for event sending aspects
 */
public class BaseEventingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(BaseEventingAspect.class);

    private String jmsDestination;  // where messages should be sent
    private JmsTemplate jmsTemplate;  // you pass in the jms template. This is where you determine queue or topic via config


    /**
     * Creates an EventMessage based on luiInfo and contextInfo parameters. Then sends it to the jms broker
     * @param eventMessage
     */
    protected  void sendEventMessage(EventMessage eventMessage){

        String jmsDestination = getJmsDestination();

        if(jmsDestination == null || jmsDestination.isEmpty()){
            throw new IllegalArgumentException("jmsDestination cannot be null or empty.");
        }

        if(isValidEvent(eventMessage)) {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Sending Event Message" + eventMessage.toString());
            }

            // broadcast event message
            getJmsTemplate().convertAndSend(getJmsDestination(), eventMessage);
        } else {
            throw new IllegalArgumentException("EventMessage object invalid. " + eventMessage.toString());
        }
    }

    /**
     * and event message is valid if none of it's fields are empty or null
     * @param eventMessage
     * @return
     */
    protected boolean isValidEvent(EventMessage eventMessage){
        if(eventMessage != null
                && !StringUtils.isEmpty(eventMessage.getId())
                && !StringUtils.isEmpty(eventMessage.getAction())
                && !StringUtils.isEmpty(eventMessage.getType())
                && eventMessage.getContextInfo() != null){
            return true;
        }

        return false;

    }

    public String getJmsDestination() {
        return jmsDestination;
    }

    public void setJmsDestination(String jmsDestination) {
        this.jmsDestination = jmsDestination;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
