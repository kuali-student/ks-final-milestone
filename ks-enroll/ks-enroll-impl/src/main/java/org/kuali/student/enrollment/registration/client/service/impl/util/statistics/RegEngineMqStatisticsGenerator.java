package org.kuali.student.enrollment.registration.client.service.impl.util.statistics;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.kuali.student.enrollment.registration.engine.listener.SimplePerformanceListener;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This implementation depends on the fact that we are using AMQP.
 * A good read on AMQP-fundamentals can be found at http://www.rabbitmq.com/tutorials/amqp-concepts.html
 * <p/>
 * Example usage:
 * <p/>
 * // define the types of stats to collect (the broker, 1 queue, and the overall reg-engine throughput)
 * List<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType> statTypesToRequest = new LinkedList<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType>();
 * statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.BROKER );
 * statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.INITIALIZATION_QUEUE );
 * statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.REGISTRATION_ENGINE_STATS );
 * <p/>
 * // collect the stats
 * RegEngineMqStatisticsGenerator generator = new RegEngineMqStatisticsGenerator();
 * generator.initiateRequestForStats( statTypesToRequest );
 * <p/>
 * Map<String, List> stats = return generator.getStats();
 */
public class RegEngineMqStatisticsGenerator {

    public static final String MQ_CONNECTION_BASE_URL = "vm://localhost";

    public static final String BROKER_QUEUE_NAME = "ActiveMQ.Statistics.Broker";
    public static final String DESTINATION_QUEUE_NAME_PREFIX = "ActiveMQ.Statistics.Destination";
    public static final String INITIALIZATION_QUEUE_NAME = DESTINATION_QUEUE_NAME_PREFIX + "." + "org.kuali.student.enrollment.registration.initializationListenerQueue";
    public static final String VERIFICATION_QUEUE_NAME = DESTINATION_QUEUE_NAME_PREFIX + "." + "org.kuali.student.enrollment.registration.verificationQueue";
    public static final String REQ_ITEM_SPLIT_QUEUE_NAME = DESTINATION_QUEUE_NAME_PREFIX + "." + "org.kuali.student.enrollment.registration.splitByItemQueue";
    public static final String LPR_ACTION_QUEUE_NAME = DESTINATION_QUEUE_NAME_PREFIX + "." + "org.kuali.student.enrollment.registration.lprActionQueue";
    public static final String REGISTRATION_ENGINE_STATS_QUEUE_NAME = SimplePerformanceListener.QUEUE_NAME;

    private Map<RegistrationEngineStatsType, MapMessage> statsTypeMapMessageMap = null;

    /**
     * Collects stats for the types provided; does not return them.
     *
     * @param statsTypes cannot be null/empty
     */
    public void initiateRequestForStats(List<RegistrationEngineStatsType> statsTypes) throws JMSException {

        if (statsTypes == null || statsTypes.isEmpty()) {
            throw new IllegalStateException("StatsTypes was null/empty.");
        }

        statsTypeMapMessageMap = new LinkedHashMap<RegistrationEngineStatsType, MapMessage>();
        for (RegistrationEngineStatsType statsType : statsTypes) {
            statsTypeMapMessageMap.put(statsType, getStatsFromMqService(statsType));
        }
    }

    /**
     * Returns stats previously collected with {@link RegEngineMqStatisticsGenerator#initiateRequestForStats(java.util.List)}
     * <p/>
     * a JSON-map of elements (ie: entities) where each element contains
     * an array of key-value pairs.
     *
     * @return a map of entity-names (ie: "INITIALIZATION_QUEUE" ) to a list of key-value pairs for stats of each entity.
     */
    public Map<String, List> getStats() throws JMSException {

        if (statsTypeMapMessageMap == null) {
            throw new IllegalStateException("Attempted to get the stats before requesting they be collected.");
        }

        Map<String, List> result = new LinkedHashMap<String, List>();

        Set<RegistrationEngineStatsType> statsTypes = statsTypeMapMessageMap.keySet();
        for (RegistrationEngineStatsType statsType : statsTypes) {
            MapMessage msg = statsTypeMapMessageMap.get(statsType);
            List<String> stats = extractMapToList(msg);
            result.put(statsType.name(), stats);
        }

        return result;
    }

    private static MapMessage getStatsFromMqService(RegistrationEngineStatsType statsType) throws JMSException {
        Session session = createSession();

        // create the request components
        Queue replyTo = session.createTemporaryQueue();
        MessageConsumer consumer = session.createConsumer(replyTo);
        Message msg = createMessage(session, replyTo);


        sendRequestToProducer(statsType, session, msg);

        return (MapMessage) consumer.receive();
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

    private static void sendRequestToProducer(RegistrationEngineStatsType statsType, Session session, Message msg) throws JMSException {
        Queue query = session.createQueue(statsType.queueName);
        MessageProducer producer = session.createProducer(query);
        producer.send(msg);
    }

    private static List<String> extractMapToList(MapMessage reply) throws JMSException {

        if (reply == null || !reply.getMapNames().hasMoreElements()) {
            throw new IllegalStateException("Reply was null/empty.");
        }

        List<String> result = new ArrayList<String>();

        // stuff the contents from the reply into a list
        Enumeration<String> e = reply.getMapNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement().toString();
            Object o = reply.getObject(name);
            result.add(name + "=" + o);
        }

        return result;
    }

    /**
     * Various types of AMQP-entities (brokers, queues, etc.)
     */
    public enum RegistrationEngineStatsType {

        BROKER(BROKER_QUEUE_NAME),
        INITIALIZATION_QUEUE(INITIALIZATION_QUEUE_NAME),
        VERIFICATION_QUEUE(VERIFICATION_QUEUE_NAME),
        LPR_ACTION_QUEUE(LPR_ACTION_QUEUE_NAME),
        REQ_ITEM_SPLIT_QUEUE(REQ_ITEM_SPLIT_QUEUE_NAME),
        REGISTRATION_ENGINE_STATS(REGISTRATION_ENGINE_STATS_QUEUE_NAME);

        private final String queueName;

        RegistrationEngineStatsType(String queueName) {
            this.queueName = queueName;
        }

    }

}
