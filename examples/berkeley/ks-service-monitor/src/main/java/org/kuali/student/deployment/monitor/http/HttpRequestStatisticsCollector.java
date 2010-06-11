/**
 * Dec 4, 2008
 */
package org.kuali.student.deployment.monitor.http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Manage collection of HttpRequestStatistics for
 * a ServletContext
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 * @author Kuali Student
 */
public class HttpRequestStatisticsCollector {

    //~ Static fields/initializers ---------------------------------------------

    public static final String CTX_ATTR_NAME = "HttpRequestStatisticsCollector";
    private static final Logger logger = LoggerFactory.getLogger(
            HttpRequestStatisticsCollector.class);

    // collect 8 hour chunks
    //  private static final long EIGHT_HOURS = TimeUnit.HOURS.toMillis(8L);
    private static final long EIGHT_HOURS = 28800000;

    // for testing
    private static final long FIVE_MINUTES = 5 * 60 * 1000;

    // keep one weeks worth of stats
    private static final int HISTORY_LIMIT = 3 * 7;

    //~ Instance fields --------------------------------------------------------

    private HttpRequestStatistics currentStats;
    private long currentStatsStartTime;
    private ArrayList<HttpRequestStatistics> history;
    private long expiryInterval = EIGHT_HOURS;

    //~ Constructors -----------------------------------------------------------

    /**
     * Default constructor
     */
    public HttpRequestStatisticsCollector() {
        this(false);
    }

    /**
     * Create in test mode.
     * Collection bucket expiration interval is one minute.
     *
     * @param testing
     */
    public HttpRequestStatisticsCollector(boolean testing) {

        if (testing)
            expiryInterval = FIVE_MINUTES;

        history = new ArrayList<HttpRequestStatistics>();
        createCurrentStats();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Collect stats on an HttpRequest;
     * @param elapsedTime
     */
    public synchronized void collect(long elapsedTime) {
        checkExpiration();
        currentStats.collect(elapsedTime);

        if (logger.isDebugEnabled())
            logger.debug(this.toString());

    }

    public HttpRequestStatistics reportSummary() {

        HttpRequestStatistics rval = createReport(copyHistory());

        return rval;
    }

    public List<HttpRequestStatistics> reportDetail() {
        return copyHistory();
    }

    /**
     * @return
     */
    private ArrayList<HttpRequestStatistics> copyHistory() {
        ArrayList<HttpRequestStatistics> buf =
            new ArrayList<HttpRequestStatistics>();

        synchronized (this) {
            buf.addAll(history);
        }

        return buf;
    }

    /**
     * @param buf
     * @return
     */
    private HttpRequestStatistics createReport(
        ArrayList<HttpRequestStatistics> buf) {
        long totalRequests = 0;

        double averageRequestTime = 0.0;
        long maxRequestTime = 0;
        long startTimeMillis = 0;
        int i = 0;

        for (HttpRequestStatistics stat : buf) {

            if (logger.isDebugEnabled())
                logger.debug(++i + " " + stat);

            totalRequests += stat.getTotalRequests();
            maxRequestTime = Math.max(maxRequestTime, stat.getMaxRequestTime());
            averageRequestTime += stat.getAverageRequestTime();
            startTimeMillis = stat.getStartTimeMillis();
        }

        averageRequestTime /= buf.size();

        HttpRequestStatistics rval = new HttpRequestStatistics(
                averageRequestTime, maxRequestTime, startTimeMillis,
                totalRequests);

        return rval;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override public String toString() {
        StringBuilder buf = new StringBuilder();

        for (HttpRequestStatistics stat : history) {
            buf.append(stat);
        }

        return buf.toString();
    }

    private void checkExpiration() {

        if (isExpired())
            createCurrentStats();

    }

    private void createCurrentStats() {
        currentStats = new HttpRequestStatistics();
        currentStatsStartTime = currentStats.getStartTimeMillis();
        updateHistory();
    }

    private void updateHistory() {
        history.add(0, currentStats);

        if (history.size() >= HISTORY_LIMIT) {
            history.remove(HISTORY_LIMIT);
        }
    }

    private boolean isExpired() {
        return (System.currentTimeMillis() - currentStatsStartTime) >=
            expiryInterval;
    }

}
