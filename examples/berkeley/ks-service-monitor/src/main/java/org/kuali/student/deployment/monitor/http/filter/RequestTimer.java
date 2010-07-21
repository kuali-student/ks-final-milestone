/**
 * Dec 3, 2008
 */
package org.kuali.student.deployment.monitor.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.kuali.student.deployment.monitor.http.HttpRequestStatisticsCollector;
import org.kuali.student.deployment.monitor.jmx.MXBeanController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Filter that collects timing stats per request.
 * Optionally set a maximum exection time. If the thresold
 * is exceeded, request info and thread info are dumped to a file
 *
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 * @author Kuali Student
 *
 */
public class RequestTimer implements Filter {

    //~ Static fields/initializers -------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(RequestTimer.class);

    /** DOCUMENT ME! */
    public static final String CTX_ATTR_NAME = "RequestTimer";

    /** DOCUMENT ME! */
    public static final String CFG_THRESHOLD_NAME = "RequestTimerThresholdSeconds";

    /** DOCUMENT ME! */
    public static final String CFG_HISTORY_NAME = "EnableSessionHistory";

    //~ Instance fields ------------------------------------------------------------------

    private HttpRequestStatisticsCollector collector;
    private MXBeanController mxBeanController;

    private int timerThreshold;
    private long timerThresholdMillis;
    boolean thresholdEnabled;
    boolean sessionHistoryEnabled;

    //~ Methods --------------------------------------------------------------------------

    /**
     * @return the timerThreshold
     */
    public final int getTimerThreshold() {
        return timerThreshold;
    }

    /**
     * @param timerThreshold the timerThreshold to set
     */
    public final void setTimerThreshold(int timerThreshold) {
        this.timerThreshold = timerThreshold;
        timerThresholdMillis = timerThreshold * 1000L;
        thresholdEnabled = (timerThresholdMillis > 0) ? true : false;
        logger.info(
            "Timer threshold seconds: " + this.timerThreshold
            + " Timer threshold millis: " + timerThresholdMillis + " enabled: "
            + thresholdEnabled);
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    /**
     * @todo DOCUMENT ME!
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
    * Time the processing that is performed by all subsequent filters
    * in the current filter stack, including the ultimately invoked servlet.
    *
    * @param request The servlet request we are processing
    * @param result The servlet response we are creating
    * @param chain The filter chain we are processing
    * @exception IOException if an input/output error occurs
    * @exception ServletException if a servlet error occurs
    */

    public void doFilter(
        ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);

        long elapsedTime = System.currentTimeMillis() - startTime;
        collector.collect(elapsedTime);

        if (thresholdEnabled) {
            checkThreshold(request, startTime, elapsedTime);
        }

        if (sessionHistoryEnabled) {
            mxBeanController.collectSessionHistory(
                request, startTime, elapsedTime, Thread.currentThread().getId());
            ;
        }
    }

   

    private void checkThreshold(ServletRequest request, long startTime,
        long elapsedTime) {
        // TODO -- should be debug:
        logger.info(
            "checkThreshold -- elapsedTime:  " + elapsedTime + " ms."
            + " timerThresholdMillis: " + timerThresholdMillis + " ms.");

        if (elapsedTime >= timerThresholdMillis) {
            mxBeanController.timerThresholdExceeded(
                request, startTime, elapsedTime, Thread.currentThread().getId());
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    /**
     * Instantatiates dependencies and configures the filter
     *
     * @param the filter configuration
     *
     * @throws ServletException if container complains
     */
    public void init(FilterConfig cfg) throws ServletException {
        logger.info("Initializing RequestTimer filter");

        ServletContext ctx = cfg.getServletContext();
        collector =
            (HttpRequestStatisticsCollector) ctx.getAttribute(
                HttpRequestStatisticsCollector.CTX_ATTR_NAME);

        if (collector == null) {
            String msg =
                HttpRequestStatisticsCollector.CTX_ATTR_NAME + " not in servlet context";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        logger.info(
            HttpRequestStatisticsCollector.CTX_ATTR_NAME + "found in servlet context");
        mxBeanController =
            (MXBeanController) ctx.getAttribute(MXBeanController.CTX_ATTR_NAME);

        if (mxBeanController == null) {
            String msg = MXBeanController.CTX_ATTR_NAME + " not in servlet context";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        logger.info(MXBeanController.CTX_ATTR_NAME + " found in servlet context");

        String thresholdParm = cfg.getInitParameter(CFG_THRESHOLD_NAME);
        logger.info("got init param " + CFG_THRESHOLD_NAME + ": " + thresholdParm);

        Integer threshold = Integer.valueOf(thresholdParm);

        if (threshold != null) {
            setTimerThreshold(threshold.intValue());
        }

        String historyParm = cfg.getInitParameter(CFG_HISTORY_NAME);
        logger.info("got init param " + CFG_HISTORY_NAME + ": " + historyParm);
        if ((historyParm != null) && (Boolean.valueOf(historyParm) == true)) {
        	logger.info(  "#### session history enabled ###");
            sessionHistoryEnabled = true;
        }
    }
}
