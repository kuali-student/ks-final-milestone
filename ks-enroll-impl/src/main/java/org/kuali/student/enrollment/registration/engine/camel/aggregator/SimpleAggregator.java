package org.kuali.student.enrollment.registration.engine.camel.aggregator;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.processor.aggregate.CompletionAwareAggregationStrategy;
import org.apache.camel.processor.aggregate.TimeoutAwareAggregationStrategy;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by swedev on 3/19/14.
 */
public class SimpleAggregator implements CompletionAwareAggregationStrategy {
    public static final Logger LOGGER = LoggerFactory.getLogger(SimpleAggregator.class);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {


        Object newBody = newExchange.getIn().getBody();
        RegistrationRequestItemEngineMessage message = (RegistrationRequestItemEngineMessage) newBody;

        ArrayList<Object> list = null;
        if (oldExchange == null) {
            LOGGER.info("Aggregating RequestItem:" + message.getRequestItem().getId() + " RequestItem State:" + message.getRequestItem().getStateKey() + " RequestId:" + message.getRequestItem().getRegistrationRequestId());
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            LOGGER.info("Aggregating RequestItem:" + message.getRequestItem().getId() + " RequestItem State:" + message.getRequestItem().getStateKey() + " RequestId:" + message.getRequestItem().getRegistrationRequestId());
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
    }

    @Override
    public void onCompletion(Exchange exchange) {
        LOGGER.info("Aggregation completed: (size)" + exchange.getProperty(Exchange.AGGREGATED_SIZE) + " (id)" +
                exchange.getProperty(Exchange.AGGREGATED_CORRELATION_KEY));
    }

}
