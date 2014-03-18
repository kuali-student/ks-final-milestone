package org.kuali.student.r2.common.messenger.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.messenger.Messenger;
import org.kuali.student.r2.common.messenger.util.MessengerConstants;
import org.kuali.student.r2.common.util.constants.MessageServiceConstants;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * This class is a JMS implementation of the Messenger interface that sends messages to the user that will
 * be displayed as growl messages in the browser.
 *
 * @author Kuali Student Team
 */
public class JMSMessengerImpl implements Messenger {

    private MessageService messageService;
    private JmsTemplate jmsTemplate;  // needed to call ActiveMQ based Messenger Service

    public void sendWarningMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if (user != null) {
            sendMessage(user, key, MessengerConstants.WARNING_MESSAGE, parameters);
        }

    }

    public void sendInfoMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if (user != null) {
            sendMessage(user, key, MessengerConstants.INFO_MESSAGE, parameters);
        }

    }

    public void sendErrorMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if (user != null) {
            sendMessage(user, key, MessengerConstants.ERROR_MESSAGE, parameters);
        }

    }

    public void sendSuccessMessage(String key, String[] parameters, ContextInfo contextInfo) {

        final String user = getProcessId(contextInfo);
        if (user != null) {
            sendMessage(user, key, MessengerConstants.SUCCESS_MESSAGE, parameters);
        }

    }

    private String getProcessId(ContextInfo contextInfo) {
        for (AttributeInfo attr : contextInfo.getAttributes()) {
            if (attr.getKey().equals(MessengerConstants.USER_MESSAGE_PROCESS_ID)) {
                return attr.getValue();
            }
        }
        return null;
    }

    private void sendMessage(final String processId, final String key, final String theme, final String[] parameters) {

        if(this.messageService == null){
            messageService = GlobalResourceLoader.getService("messageService");
        }
        final String messageText = messageService.getMessageText(null, null,
                key);

        //Comment out for now to make sure this is not the cause for the memory leaks.
        jmsTemplate.send(MessengerConstants.USER_MESSAGE_DESTINATION, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                TextMessage message = session.createTextMessage();
                message.setText(theme + ":" + messageText);
                message.setJMSCorrelationID(processId);
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
