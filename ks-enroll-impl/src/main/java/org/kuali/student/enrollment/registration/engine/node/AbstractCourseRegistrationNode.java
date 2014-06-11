package org.kuali.student.enrollment.registration.engine.node;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic listener that will be used for most nodes in our registration system.
 * <p/>
 * This processor has hooks for before and after filters, calls an abstract process() method and notifies the queues
 * on the optional list of destinations.
 *
 * @param <T> Input Message Type
 * @param <R> Output Message Type
 */
public abstract class AbstractCourseRegistrationNode<T, R> implements MessageListener {
    protected JmsTemplate jmsTemplate;
    /**
     * MQ Destinations that will be called after the registrationProcessService.process is called
     */
    protected List<String> destinations;
    /**
     * List of filters that process before and after the main processing so that behaviour can be modified in
     * configuration
     */
    protected List<AbstractCourseRegistrationNodeFilter<T, R>> filters;

    /**
     * Handles consuming messages using filters and notifying destinations
     *
     * @param message the input message
     */
    @Override
    @Transactional
    public void onMessage(Message message) {
        try {
            T inMessageObject;
            R outMessageObject;
            if (message instanceof ObjectMessage) {
                inMessageObject = (T) ((ObjectMessage) message).getObject();
            } else {
                inMessageObject = (T) message;
            }

            //Filter input
            for (AbstractCourseRegistrationNodeFilter<T, R> filter : getFilters()) {
                inMessageObject = filter.beforeProcess(inMessageObject, getJmsTemplate(), getDestinations());
            }

            //Process
            outMessageObject = process(inMessageObject);

            //Filter output
            for (AbstractCourseRegistrationNodeFilter<T, R> filter : getFilters()) {
                outMessageObject = filter.afterProcess(inMessageObject, outMessageObject, getJmsTemplate(), getDestinations());
            }

            //Notify
            notifyDestinations(outMessageObject, resolveGroupId(inMessageObject, outMessageObject));

        } catch (JMSException e) {
            throw new RuntimeException("Error with Processing Message", e);
        }
    }

    /**
     * Sends the output message to each destination queue
     *
     * @param outputMessage message to send
     * @param groupId       optional grouping id
     */
    protected void notifyDestinations(final Object outputMessage, final String groupId) {
        for (String destination : getDestinations()) {
            jmsTemplate.send(destination, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    Message message = session.createObjectMessage((Serializable) outputMessage);
                    if (groupId != null) {
                        message.setStringProperty("JMSXGroupID", groupId);
                    }
                    return message;
                }
            });
        }
    }

    /**
     * Optionally resolves group ids from the input and output message.
     *
     * @param inputMessage  input message
     * @param outputMessage output message
     * @return string of resolved group id or null if no grouping is desired
     */
    protected String resolveGroupId(T inputMessage, R outputMessage) {
        return null;
    }

    /**
     * This is the main method that subclasses must implement, which performs all the node processing
     *
     * @param message input message
     * @return output message
     */
    public abstract R process(T message);

    public List<String> getDestinations() {
        if (destinations == null) {
            destinations = new ArrayList<String>();
        }
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public List<AbstractCourseRegistrationNodeFilter<T, R>> getFilters() {
        if (filters == null) {
            filters = new ArrayList<AbstractCourseRegistrationNodeFilter<T, R>>();
        }
        return filters;
    }

    public void setFilters(List<AbstractCourseRegistrationNodeFilter<T, R>> filters) {
        this.filters = filters;
    }
}
