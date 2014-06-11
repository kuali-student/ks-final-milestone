package org.kuali.student.enrollment.registration.engine.node;

import org.springframework.jms.core.JmsTemplate;

import java.util.List;


/**
 * Adds before and after process hooks so that node processing is more configurable
 *
 * @param <T> Input Message Type
 * @param <R> Output Message Type
 */
public abstract class AbstractCourseRegistrationNodeFilter<T, R> {

    /**
     * Hook for before processing
     *
     * @param inMessage    input message
     * @param jmsTemplate  handle to JMS template
     * @param destinations list of destination queue names
     * @return a possibly modified input message
     */
    public T beforeProcess(T inMessage, JmsTemplate jmsTemplate, List<String> destinations) {
        return inMessage;
    }

    /**
     * Hook for before processing
     *
     * @param inMessage    input message
     * @param outMessage   output message
     * @param jmsTemplate  handle to JMS template
     * @param destinations list of destination queue names
     * @return a possibly modified input message
     */
    public R afterProcess(T inMessage, R outMessage, JmsTemplate jmsTemplate, List<String> destinations) {
        return outMessage;
    }
}
