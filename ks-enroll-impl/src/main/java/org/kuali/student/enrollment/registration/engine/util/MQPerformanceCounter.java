package org.kuali.student.enrollment.registration.engine.util;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;

import javax.jms.MapMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton counter class.
 */
public class MQPerformanceCounter {

    private static final Logger LOG = Logger.getLogger(MQPerformanceCounter.class);

    // this is the more performent way to access a thread safe singleton
    public static final MQPerformanceCounter INSTANCE = new MQPerformanceCounter();

    // Stores a KEY and a Start Timestamp
    private Map<String, Long> performanceMap = new HashMap<String, Long>();
    private long requestCount = 0;
    private long totalRequestTime = 0;   // running total of all request times
    private long averageRequestTime = 0;

    /**
     * This is a singleton so it must not be public
     */
    protected MQPerformanceCounter() {
    }

    /**
     * on the first pass through, the reqId and start time are added to the map
     * on the second time through the reqId is used to remove the start time from the map, and calculate the averages
     * with the passed in endTime.
     *
     * needs to be synchronized for thread safety
     *
     * @param regReqId  always required. used as map key
     * @param startTime required on first pass
     * @param endTime   requred on second pass
     */
    public synchronized void updateCounts(String regReqId, long startTime, long endTime){
        if(!performanceMap.containsKey(regReqId)){ // first pass
            LOG.debug(String.format("START [%s]:[%s]", regReqId, startTime));
            performanceMap.put(regReqId, startTime);
        }   else{ // second pass
            LOG.debug(String.format("END   [%s]:[%s]", regReqId, endTime));
            long storedStart = performanceMap.remove(regReqId);
            long delta = endTime - storedStart;   // calculation

            requestCount++; // the request count must be incremented here, not when it's initialized
            totalRequestTime += delta;
            averageRequestTime = totalRequestTime / requestCount;
        }
    }

    /**
     * this method returns the current counts and stats
     *
     * needs to be synchronized for thread safety
     *
     * @return Returns a MQ MapMessage.
     * @throws Exception
     */
    public synchronized MapMessage getPerformanceStats() throws Exception{
        MapMessage perfMap = new ActiveMQMapMessage();

        perfMap.setString("requestCount", Long.toString(requestCount));
        perfMap.setString("totalRequestTimeMillis", Long.toString(totalRequestTime));
        perfMap.setString("averageRequestTimeMillis", Long.toString(averageRequestTime));

        return perfMap;

    }

}
