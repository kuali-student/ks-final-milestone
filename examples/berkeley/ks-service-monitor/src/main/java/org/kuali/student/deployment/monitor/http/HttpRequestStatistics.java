/**
 * Dec 3, 2008
 */
package org.kuali.student.deployment.monitor.http;

import java.util.Date;


/**
 * Collects statistics on HTTP requests.
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 * @author Kuali Student
 */
public final class HttpRequestStatistics {

    //~ Static fields/initializers ---------------------------------------------

    private static final String format =
        "Total Requests:  %d\nAverage Request Time: %.2f ms.\n" +
        "Max Request Time: %d\n Start Time:  %s\n";

    //~ Instance fields --------------------------------------------------------

    private long totalRequests;
    private long totalRequestTime;
    private double averageRequestTime;
    private long maxRequestTime;
    private long startTimeMillis;

    //~ Constructors -----------------------------------------------------------

    /**
     * Create a new HttpRequestStatistics
     */
    public HttpRequestStatistics() {
        super();
        startTimeMillis = System.currentTimeMillis();
    }


    /**
     * Constructor for reporting.
     * @param averageRequestTime
     * @param maxRequestTime
     * @param startTimeMillis
     * @param totalRequests
     */
    public HttpRequestStatistics(double averageRequestTime, long maxRequestTime,
        long startTimeMillis, long totalRequests) {
        super();
        this.averageRequestTime = averageRequestTime;
        this.maxRequestTime = maxRequestTime;
        this.startTimeMillis = startTimeMillis;
        this.totalRequests = totalRequests;
    }

    //~ Methods ----------------------------------------------------------------

    public synchronized void collect(long elapsedTime) {
        ++totalRequests;
        totalRequestTime += elapsedTime;
        averageRequestTime = (double) totalRequestTime / totalRequests;
        maxRequestTime = Math.max(maxRequestTime, elapsedTime);

    }

    /**
     * @return the totalRequests
     */
    public long getTotalRequests() {
        return totalRequests;
    }

    /**
     * @return the averageRequestTime
     */
    public double getAverageRequestTime() {
        return averageRequestTime;
    }

    /**
     * @return the maxRequestTime
     */
    public long getMaxRequestTime() {
        return maxRequestTime;
    }

    /**
     * @return the startTimeMillis
     */
    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override public String toString() {

        return String.format(format, totalRequests, averageRequestTime,
                maxRequestTime, new Date(startTimeMillis).toString());

    }


}
