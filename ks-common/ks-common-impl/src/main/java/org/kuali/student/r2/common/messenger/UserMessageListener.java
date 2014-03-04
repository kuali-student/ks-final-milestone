package org.kuali.student.r2.common.messenger;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.kuali.student.r2.common.messenger.util.MessengerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementaton of the JMS Message Listener and can add a message to the queue for a specific
 * user or retrieve all the current messages for the user from the queue.
 *
 * @author Kuali Student Team
 */
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
                String user = message.getStringProperty(MessengerConstants.USER_MESSAGE_USER);
                ArrayList<UserMessage> msgs = (ArrayList)UserMessageMap.INSTANCE.getMessagesForKey(user);

                if((msgs != null) && (!msgs.isEmpty())){
                    ObjectMessage objectMessage = new ActiveMQObjectMessage();
                    objectMessage.setObject(msgs);

                    jmsTemplate.convertAndSend(message.getJMSReplyTo(), objectMessage);
                } else {
                    TextMessage textMessage = new ActiveMQTextMessage();
                    textMessage.setText("No message for user");
                    jmsTemplate.convertAndSend(message.getJMSReplyTo(), textMessage);
                }

            } else {
                if (message instanceof MapMessage) {

                    String userKey = ((MapMessage) message).getString(MessengerConstants.USER_MESSAGE_USER);
                    String messageKey = ((MapMessage) message).getString(MessengerConstants.USER_MESSAGE_KEY);
                    String theme = ((MapMessage) message).getString(MessengerConstants.USER_MESSAGE_THEME);
                    String messageParameters = ((MapMessage) message).getString(MessengerConstants.USER_MESSAGE_PARAMETERS);

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
