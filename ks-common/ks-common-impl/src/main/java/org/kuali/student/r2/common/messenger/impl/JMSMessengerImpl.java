package org.kuali.student.r2.common.messenger.impl;

import org.kuali.student.r2.common.messenger.Messenger;
import org.kuali.student.r2.common.messenger.util.MessengerConstants;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * This class is a JMS implementation of the Messenger interface that sends messages to the user that will
 * be displayed as growl messages in the browser.
 *
 * @author Kuali Student Team
 */
public class JMSMessengerImpl implements Messenger {

    private JmsTemplate jmsTemplate;  // needed to call ActiveMQ based Messenger Service

    public void sendWarningMessage(final String user, final String key, final String[] parameters) {

        jmsTemplate.send(MessengerConstants.USER_MESSAGE_DESTINATION, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                MapMessage message = session.createMapMessage();
                message.setString(MessengerConstants.USER_MESSAGE_USER, user);
                message.setString(MessengerConstants.USER_MESSAGE_THEME, MessengerConstants.WARNING_MESSAGE);
                message.setString(MessengerConstants.USER_MESSAGE_KEY, key);
                message.setString(MessengerConstants.USER_MESSAGE_PARAMETERS, StringUtils.arrayToCommaDelimitedString(parameters));

                return message;
            }
        });
    }

    public void sendInfoMessage(final String user, final String key, final String[] parameters) {

        jmsTemplate.send(MessengerConstants.USER_MESSAGE_DESTINATION, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                MapMessage message = session.createMapMessage();
                message.setString(MessengerConstants.USER_MESSAGE_USER, user);
                message.setString(MessengerConstants.USER_MESSAGE_THEME, MessengerConstants.INFO_MESSAGE);
                message.setString(MessengerConstants.USER_MESSAGE_KEY, key);
                message.setString(MessengerConstants.USER_MESSAGE_PARAMETERS, StringUtils.arrayToCommaDelimitedString(parameters));

                return message;
            }
        });
    }

    public void sendErrorMessage(final String user, final String key, final String[] parameters) {

        jmsTemplate.send(MessengerConstants.USER_MESSAGE_DESTINATION, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                MapMessage message = session.createMapMessage();
                message.setString(MessengerConstants.USER_MESSAGE_USER, user);
                message.setString(MessengerConstants.USER_MESSAGE_THEME, MessengerConstants.ERROR_MESSAGE);
                message.setString(MessengerConstants.USER_MESSAGE_KEY, key);
                message.setString(MessengerConstants.USER_MESSAGE_PARAMETERS, StringUtils.arrayToCommaDelimitedString(parameters));

                return message;
            }
        });
    }

    public void sendSuccessMessage(final String user, final String key, final String[] parameters) {

        jmsTemplate.send(MessengerConstants.USER_MESSAGE_DESTINATION, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                MapMessage message = session.createMapMessage();
                message.setString(MessengerConstants.USER_MESSAGE_USER, user);
                message.setString(MessengerConstants.USER_MESSAGE_THEME, MessengerConstants.SUCCESS_MESSAGE);
                message.setString(MessengerConstants.USER_MESSAGE_KEY, key);
                message.setString(MessengerConstants.USER_MESSAGE_PARAMETERS, StringUtils.arrayToCommaDelimitedString(parameters));

                return message;
            }
        });
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
