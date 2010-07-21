package org.kuali.student.deployment.monitor.http;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import org.kuali.student.deployment.monitor.http.HttpSessionRequestHistory.RequestInfo;
import org.kuali.student.deployment.monitor.jmx.SessionRequestHistoryLogger;


public class HttpSessionRequestHistoryTest {

    //~ Instance fields ------------------------------------------------------------------

    HttpSessionRequestHistory ri;
    Random rnd;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new HttpSessionRequestHistoryTest object.
     */
    public HttpSessionRequestHistoryTest() {
        // TODO Auto-generated constructor stub
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    @Before public void setUp() throws Exception {
        ri = new HttpSessionRequestHistory();
        rnd = new Random();
    }

    /**
     * @todo DOCUMENT ME!
     */
    @Test public void testAdd() {
        mockAdd();
        System.out.println(ri.toString());
    }

    /**
     * @todo DOCUMENT ME!
     */
    @Test public void testLog() {
        SessionRequestHistoryLogger logger = new SessionRequestHistoryLogger();
        mockAdd();

        Map<String, List<RequestInfo>> history = ri.getHistory();

        for (String sessionId : history.keySet()) {
            logSessionHistory(logger, history, sessionId);
        }
    }

    /**
     * @param logger
     * @param history
     * @param sessionId
     */
    private void logSessionHistory(
        SessionRequestHistoryLogger logger, Map<String, List<RequestInfo>> history,
        String sessionId) {
        List<RequestInfo> sessionHistory = history.get(sessionId);
        logger.log(sessionId, sessionHistory);
    }

    /**
     *
     */
    private void mockAdd() {
        for (int i = 0; i < 10; i++) {
            mockRequest("session-1", i);
            mockRequest("session-2", i);
        }
    }

    private void mockRequest(String sessionId, int counter) {
        long elapsed = (long) rnd.nextInt(2000);
        long start = System.currentTimeMillis();
        System.out.println(
            "mockRequest" + sessionId + "start: " + start + " elapsed: " + elapsed);
        ri.add(sessionId, "someURI-" + counter, System.currentTimeMillis(), elapsed);

        try {
            Thread.sleep(elapsed);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
