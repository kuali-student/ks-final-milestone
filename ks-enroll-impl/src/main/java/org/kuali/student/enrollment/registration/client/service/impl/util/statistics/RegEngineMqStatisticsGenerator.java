package org.kuali.student.enrollment.registration.client.service.impl.util.statistics;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
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

public class RegEngineMqStatisticsGenerator {

    public static final String MQ_CONNECTION_BASE_URL = "vm://localhost";

    public static final String BROKER_QUEUE_NAME = "ActiveMQ.Statistics.Broker";
    public static final String DESTINATION_QUEUE_NAME_PREFIX = "ActiveMQ.Statistics.Destination";
    public static final String INITIALIZATION_QUEUE_NAME = DESTINATION_QUEUE_NAME_PREFIX + "." + "org.kuali.student.enrollment.registration.initilizationListenerQueue";
    public static final String VERIFICATION_QUEUE_NAME = DESTINATION_QUEUE_NAME_PREFIX + "." + "org.kuali.student.enrollment.registration.verificationQueue";
    public static final String SEAT_CHECK_QUEUE_NAME = DESTINATION_QUEUE_NAME_PREFIX + "." + "org.kuali.student.enrollment.registration.seatCheckQueue";

    private Map<RegistrationEngineStatsType, MapMessage> statsTypeMapMessageMap = new LinkedHashMap<RegistrationEngineStatsType, MapMessage>();

    public void initiateRequestForStats( List<RegistrationEngineStatsType> statsTypes ) throws Exception {
        statsTypeMapMessageMap.put( RegistrationEngineStatsType.BROKER, getStatsFromMqService(RegistrationEngineStatsType.BROKER ) );
        statsTypeMapMessageMap.put( RegistrationEngineStatsType.INITIALIZATION_QUEUE, getStatsFromMqService(RegistrationEngineStatsType.INITIALIZATION_QUEUE ) );
        statsTypeMapMessageMap.put( RegistrationEngineStatsType.VERIFICATION_QUEUE, getStatsFromMqService(RegistrationEngineStatsType.VERIFICATION_QUEUE ) );
        statsTypeMapMessageMap.put( RegistrationEngineStatsType.SEAT_CHECK_QUEUE, getStatsFromMqService(RegistrationEngineStatsType.SEAT_CHECK_QUEUE ) );
    }

    public Map<String, List> getStats() throws Exception {
        Map<String, List> result = new LinkedHashMap<String, List>();

        Set<RegistrationEngineStatsType> statsTypes = statsTypeMapMessageMap.keySet();
        for( RegistrationEngineStatsType statsType : statsTypes ) {
            MapMessage msg = statsTypeMapMessageMap.get(statsType);
            List<String> stats = extractMapToList(msg);
            result.put( statsType.name(), stats );
        }

        return result;
    }

    private static MapMessage getStatsFromMqService(RegistrationEngineStatsType statsType) throws Exception {
        Session session = createSession();

        // create the request components
        Queue replyTo = session.createTemporaryQueue();
        MessageConsumer consumer = session.createConsumer(replyTo);
        Message msg = createMessage(session, replyTo);


        sendRequestToProducer( statsType, session, msg );

        return (MapMessage) consumer.receive();
    }

    private static Session createSession() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( MQ_CONNECTION_BASE_URL );
        Connection connection = factory.createConnection();
        connection.start();

        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private static Message createMessage(Session session, Queue replyTo) throws Exception {

        Message msg = session.createMessage();
        msg.setJMSReplyTo( replyTo );

        return msg;
    }

    private static void sendRequestToProducer( RegistrationEngineStatsType statsType, Session session, Message msg ) throws Exception {
        Queue query = session.createQueue( statsType.queueName );
        MessageProducer producer = session.createProducer(query);
        producer.send(msg);
    }

    private static List<String> extractMapToList( MapMessage reply ) throws Exception {

        // assert not null; assert true (has mapnames and more elements ???

        List<String> result = new ArrayList<String>();
        for( Enumeration e = reply.getMapNames() ; e.hasMoreElements() ; ) {
            String name = e.nextElement().toString();
            Object o = reply.getObject(name);
            result.add(name + "=" + o);
        }

        return result;
    }

    public enum RegistrationEngineStatsType {

        BROKER( BROKER_QUEUE_NAME ),
        INITIALIZATION_QUEUE( INITIALIZATION_QUEUE_NAME ),
        VERIFICATION_QUEUE( VERIFICATION_QUEUE_NAME ),
        SEAT_CHECK_QUEUE( SEAT_CHECK_QUEUE_NAME );

        private final String queueName;

        RegistrationEngineStatsType( String queueName ) {
            this.queueName = queueName;
        }

    }

}
