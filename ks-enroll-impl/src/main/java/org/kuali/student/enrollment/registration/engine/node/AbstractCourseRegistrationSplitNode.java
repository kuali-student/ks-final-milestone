package org.kuali.student.enrollment.registration.engine.node;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.List;

/**
 * This class will split an input message into one or more output messages
 *
 * @param <T> Input Message Type
 * @param <R> Output Message Type For each item in List
 */
public abstract class AbstractCourseRegistrationSplitNode<T, R> extends AbstractCourseRegistrationNode<R, R> {

    /**
     * Splits a single input message into multiple input messages
     *
     * @param inputMessage
     * @return list of output messages
     */
    protected abstract List<R> split(T inputMessage);

    /**
     * Just like the superclass, except it loops over the results of split and does no call to process()
     *
     * @param message the input message
     */
    @Override
    public void onMessage(Message message) {
        try {
            T inMessageObject;
            R outMessageObject;
            if (message instanceof ObjectMessage) {
                inMessageObject = (T) ((ObjectMessage) message).getObject();
            } else {
                inMessageObject = (T) message;
            }

            for (R messageItem : split(inMessageObject)) {
                R inMessageItem = messageItem;
                for (AbstractCourseRegistrationNodeFilter<R, R> filter : getFilters()) {
                    inMessageItem = filter.beforeProcess(inMessageItem, getJmsTemplate(), getDestinations());
                }

                outMessageObject = inMessageItem;

                for (AbstractCourseRegistrationNodeFilter<R, R> filter : getFilters()) {
                    outMessageObject = filter.afterProcess(inMessageItem, outMessageObject, getJmsTemplate(), getDestinations());
                }

                notifyDestinations(outMessageObject, resolveGroupId(inMessageItem, outMessageObject));
            }
        } catch (JMSException e) {
            throw new RuntimeException("Error with Processing Message", e);
        }
    }

    /**
     * This method is final because split nodes should only split and do no processing
     *
     * @param message input message
     * @return pass through the input message
     */
    @Override
    public final R process(R message) {
        return message;
    }
}
