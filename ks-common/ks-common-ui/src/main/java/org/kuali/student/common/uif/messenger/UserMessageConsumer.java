package org.kuali.student.common.uif.messenger;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.GrowlMessage;
import org.kuali.student.r2.common.messenger.UserMessage;
import org.kuali.student.r2.common.messenger.util.MessengerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * This class ...
 *
 * @author Kuali Student Team
 */
public class UserMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserMessageConsumer.class);

    public static final String MQ_CONNECTION_BASE_URL = "vm://localhost";

    public void publish() {

        logger.info("Publishing messages for user.");
        UserSession userSession = GlobalVariables.getUserSession();
        if (userSession == null) {
            return;
        }

        // create the request components
        try {
            Session session = createSession();
            sendReceive(session, userSession.getPrincipalId());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    private static void sendReceive(Session session, final String user) throws JMSException {

        Queue replyTo = session.createTemporaryQueue();

        MessageConsumer consumer = session.createConsumer(replyTo);
        Message msg = createMessage(session, replyTo);
        msg.setStringProperty(MessengerConstants.USER_MESSAGE_USER, user);

        Queue query = session.createQueue(MessengerConstants.USER_MESSAGE_DESTINATION);
        MessageProducer producer = session.createProducer(query);
        producer.send(msg);

        Message returnMsg = consumer.receive();
        if (returnMsg instanceof ObjectMessage) {

            UserMessage userMessage = (UserMessage) ((ObjectMessage) returnMsg).getObject();

            GrowlMessage growlMessage = new GrowlMessage();
            growlMessage.setTitle("");
            growlMessage.setMessageKey(userMessage.getKey());
            growlMessage.setMessageParameters(StringUtils.commaDelimitedListToStringArray(userMessage.getParameters()));
            growlMessage.setTheme(userMessage.getTheme());
            GlobalVariables.getMessageMap().addGrowlMessage(growlMessage);

            logger.info("Messages added to global variables.");
            if(userMessage.hasMore()){
                sendReceive(session, user);
            }

        }
    }

    private static Session createSession() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_CONNECTION_BASE_URL);
        Connection connection = factory.createConnection();
        connection.start();

        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private static Message createMessage(Session session, Queue replyTo) throws JMSException {
        Message msg = session.createMessage();
        msg.setJMSReplyTo(replyTo);

        return msg;
    }

}
