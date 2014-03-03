package org.kuali.student.r2.common.messenger;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class UserMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(UserMessageListener.class);

    private JmsTemplate jmsTemplate;

    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {

            logger.info(message.toString());

            if (message.getJMSReplyTo() != null) { // this is used for getting current stats
                // send mq message. use singlton perf counter
                UserMessage msg = UserMessageMap.INSTANCE.getMessagesForKey("Ekke");

                if(msg != null){
                    ObjectMessage objectMessage = new ActiveMQObjectMessage();
                    objectMessage.setObject(msg);

                    jmsTemplate.convertAndSend(message.getJMSReplyTo(), objectMessage);
                } else {
                    TextMessage textMessage = new ActiveMQTextMessage();
                    textMessage.setText("No message for user");
                    jmsTemplate.convertAndSend(message.getJMSReplyTo(), textMessage);
                }

            } else {
                if (message instanceof MapMessage) {

                    String userKey = ((MapMessage) message).getString("userKey");
                    String messageKey = ((MapMessage) message).getString("messageKey");
                    String theme = ((MapMessage) message).getString("theme");
                    String messageParameters = ((MapMessage) message).getString("parameters");

                    UserMessage msg = new UserMessage();
                    msg.setKey(messageKey);
                    msg.setTheme(theme);
                    msg.setParameters(messageParameters);

                    UserMessageMap.INSTANCE.addMessageForKey(userKey, msg);

                }
            }

        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
