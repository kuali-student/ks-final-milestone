package org.kuali.student.enrollment.registration.engine.camel.aggregator;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.kuali.student.common.eventing.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * This Aggregator is designed to work with the EventMessage class.
 *
 * It will return a list of EventMessage.id
 */
public class EventMessageIdAggregator implements AggregationStrategy {
    public static final Logger LOGGER = LoggerFactory.getLogger(EventMessageIdAggregator.class);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object newBody = newExchange.getIn().getBody();
        EventMessage eventMessage = (EventMessage) newBody;


        ArrayList<Object> list = null;
        if (oldExchange == null) {
            if(LOGGER.isInfoEnabled()) LOGGER.info("Aggregating First EventMessage:" + eventMessage.toString());
            list = new ArrayList<Object>();
            list.add(eventMessage.getId());
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            if(LOGGER.isInfoEnabled()) LOGGER.info("Aggregating Subsequent EventMessages:" + eventMessage.toString());
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(eventMessage.getId());
            return oldExchange;
        }
    }
}
