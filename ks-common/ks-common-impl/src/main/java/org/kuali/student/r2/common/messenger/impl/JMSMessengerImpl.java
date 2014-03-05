package org.kuali.student.r2.common.messenger.impl;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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

    public void sendWarningMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if(user != null){
            sendMessage(user, key, MessengerConstants.WARNING_MESSAGE, parameters);
        }

    }

    public void sendInfoMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if(user != null){
            sendMessage(user, key, MessengerConstants.INFO_MESSAGE, parameters);
        }

    }

    public void sendErrorMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if(user != null){
            sendMessage(user, key, MessengerConstants.ERROR_MESSAGE, parameters);
        }

    }

    public void sendSuccessMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if(user != null){
            sendMessage(user, key, MessengerConstants.SUCCESS_MESSAGE, parameters);
        }

    }

    private String getProcessId(ContextInfo contextInfo) {
        for(AttributeInfo attr : contextInfo.getAttributes()){
            if(attr.getKey().equals(MessengerConstants.USER_MESSAGE_PROCESS_ID)){
                return attr.getValue();
            }
        }
        return null;
    }

    private void sendMessage(final String user, final String key, final String theme, final String[] parameters) {
        jmsTemplate.send(MessengerConstants.USER_MESSAGE_DESTINATION, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                MapMessage message = session.createMapMessage();
                message.setString(MessengerConstants.USER_MESSAGE_USER, user);
                message.setString(MessengerConstants.USER_MESSAGE_THEME, theme);
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
