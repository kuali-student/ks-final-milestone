/**
 *
 */
package org.kuali.student.deployment.monitor.http;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Timing statistics for Http Requests by session.
 * @author randy
 *
 */
public class HttpSessionRequestHistory {

    //~ Instance fields ------------------------------------------------------------------

    Map<String, List<RequestInfo>> history = new HashMap<String, List<RequestInfo>>();

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new HttpSessionRequestInfo object.
     */
    public HttpSessionRequestHistory() {
        // TODO Auto-generated constructor stub
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * @return the history
     */
    public final Map<String, List<RequestInfo>> getHistory() {
        return history;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    /**
     * @todo DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override public String toString() {
        String nl = System.getProperty("line.separator");
        StringBuilder buf = new StringBuilder("HttpSessionRequestInfo").append(nl);

        for (Map.Entry<String, List<RequestInfo>> e : history.entrySet()) {
            String sessionId = e.getKey();
            buf.append(sessionId).append(nl);

            List<RequestInfo> requestInfo = e.getValue();

            for (RequestInfo ri : requestInfo) {
                buf.append(ri).append(nl);
            }
        }

        return buf.toString();
    }

    /**
     * @todo DOCUMENT ME!
     *
     * @param sessionId DOCUMENT ME!
     * @param requestUri DOCUMENT ME!
     * @param startTime DOCUMENT ME!
     * @param elapsedTime DOCUMENT ME!
     */
    public void add(String sessionId, String requestUri, long startTime,
        long elapsedTime) {
        List<RequestInfo> sessionInfo = history.get(sessionId);

        if (sessionInfo == null) {
            RequestInfo ri = new RequestInfo(requestUri, startTime, elapsedTime, 0);
            ArrayList<RequestInfo> riList = new ArrayList<RequestInfo>();
            riList.add(ri);
            history.put(sessionId, riList);
        } else {
            addInfoToSession(sessionInfo, requestUri, startTime, elapsedTime);
        }
    }

    private void addInfoToSession(
        List<RequestInfo> sessionInfo, String requestUri, long startTime,
        long elapsedTime) {
        int lastIdx = sessionInfo.size() - 1;
        RequestInfo previousRequest = sessionInfo.get(lastIdx);
        long requestInterval = startTime - previousRequest.startTimeMillis;
        RequestInfo newInfo =
            new RequestInfo(requestUri, startTime, elapsedTime, requestInterval);
        sessionInfo.add(newInfo);
    }

    //~ Inner Classes --------------------------------------------------------------------

    /**
     * Request data struct
     * all properties public final
     * so no need for getters/setters
     *
     * @author randy
     *
     */
    public static class RequestInfo {

        //~ Static fields/initializers ---------------------------------------------------

        static final String DATE_FMT = "yyyy.MM.dd HH:mm:ss";

        // for csv emit
        static final String VS = ", ";
        public static final String tableHeaderCSV = createTableHeader();

        //~ Instance fields --------------------------------------------------------------

        public final String requestURI;
        public final long startTimeMillis;
        public final long elapsedTimeMillis;
        public final String startTimeFormatted;

        // interval between successive startTimes
        public final long timeSinceLastRequest;

        //~ Constructors -----------------------------------------------------------------

        public RequestInfo(
            String requestURI, long startTimeMillis, long elapsedTimeMillis,
            long timeSinceLastRequest) {
            super();
            this.requestURI = requestURI;
            this.startTimeMillis = startTimeMillis;
            this.elapsedTimeMillis = elapsedTimeMillis;
            this.timeSinceLastRequest = timeSinceLastRequest;
            this.startTimeFormatted = formatStartTime();
        }

        //~ Methods ----------------------------------------------------------------------
        /**
         * @return a comma-separated-value representation of this request
         */
       public String toStringCSV() {
    	   StringBuilder buf = new StringBuilder();
    	   buf.append(startTimeFormatted).append(VS)
    	   .append(requestURI).append(VS)
    	   .append(elapsedTimeMillis).append(VS)
    	   .append(timeSinceLastRequest);
    	   return buf.toString();
       }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override public String toString() {
            return "RequestInfo [elapsedTimeMillis = " + elapsedTimeMillis
                + ", requestURI=" + requestURI + ", startTimeMillis = " + startTimeMillis
                + ", elapsedTimeMillis = " + elapsedTimeMillis
                + ", timeSinceLastRequest = " + timeSinceLastRequest + "]";
        }
        
        
        private static String createTableHeader() {
            StringBuilder buf = new StringBuilder();
            buf.append("Start Time").append(VS).append("Request URI").append(VS)
               .append("Elapsed Time (ms.)").append(VS).append("Request Interval (ms.)");

            return buf.toString();
        }

        private String formatStartTime() {
            // TODO Auto-generated method stub
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(startTimeMillis);

            Date startDate = cal.getTime();

            return new SimpleDateFormat(DATE_FMT).format(startDate);
        }
    }
}
