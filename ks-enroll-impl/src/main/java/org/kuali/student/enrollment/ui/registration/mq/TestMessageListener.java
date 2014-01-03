package org.kuali.student.enrollment.ui.registration.mq;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 12/17/13
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestMessageListener implements MessageListener {
    private static final Logger LOG = Logger.getLogger(TestMessageListener.class);

    private JmsTemplate jmsTemplate;

    @Override
    public void onMessage(Message message) {
        String messageText="";
        //This message listener will create messages for the given user Id at various intervals.
        if(message instanceof TextMessage){
            try {
                messageText = ((TextMessage) message).getText();
            } catch (JMSException e) {
                LOG.error("Error getting message content", e);
            }
        }
        LOG.info("Received Message from user id: " + messageText);

        final String destination = "user.socket.queue." + messageText;


//        try {
            jmsTemplate.convertAndSend(destination, "Sending first Message");
            //wait(1000);
            jmsTemplate.convertAndSend(destination, "Sending second Message");
            //wait(3000);
            jmsTemplate.convertAndSend(destination, "Sending third Message");
            //wait(5000);
            jmsTemplate.convertAndSend(destination, "Sending fourth and final Message");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }


    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
