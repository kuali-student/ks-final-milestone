package org.kuali.student.deployment.monitor.jmx;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import java.util.List;
import java.util.Map;

import javax.management.MBeanServer;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kuali.student.deployment.monitor.http.HttpSessionRequestHistory;
import org.kuali.student.deployment.monitor.http.RequestDumper;
import org.kuali.student.deployment.monitor.http.HttpSessionRequestHistory.RequestInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MXBeanController {

    //~ Static fields/initializers -------------------------------------------------------

    /** DOCUMENT ME! */
    public static final String CTX_ATTR_NAME = "MXBeanController";
    private static final Logger logger = LoggerFactory.getLogger(MXBeanController.class);

    //~ Instance fields ------------------------------------------------------------------

    private ClassLoadingMXBean classLoadingMXBean =
        ManagementFactory.getClassLoadingMXBean();
    private CompilationMXBean compilationMXBean =
        ManagementFactory.getCompilationMXBean();
    private List<GarbageCollectorMXBean> garbageCollectorMXBean =
        ManagementFactory.getGarbageCollectorMXBeans();
    private List<MemoryManagerMXBean> memoryManagerMXBeans =
        ManagementFactory.getMemoryManagerMXBeans();
    private MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    private List<MemoryPoolMXBean> memoryPoolMXBeans =
        ManagementFactory.getMemoryPoolMXBeans();
    private OperatingSystemMXBean operatingSystemMXBean =
        ManagementFactory.getOperatingSystemMXBean();
    private MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    private RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private RequestThreadInfoLogger threadLogger = new RequestThreadInfoLogger();
    private HttpSessionRequestHistory sessionRequestHistory;
    
    private boolean sessionHistoryEnabled;
    private SessionRequestHistoryLogger sessionHistoryLogger;
    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new MXBeanController object.
     */
    public MXBeanController() {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * @return the classLoadingMXBean
     */
    public final ClassLoadingMXBean getClassLoadingMXBean() {
        return classLoadingMXBean;
    }

    /**
     * @return the compilationMXBean
     */
    public final CompilationMXBean getCompilationMXBean() {
        return compilationMXBean;
    }

    /**
     * @return the garbageCollectorMXBean
     */
    public final List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
        return garbageCollectorMXBean;
    }

    /**
     * @return the memoryManagerMXBeans
     */
    public final List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
        return memoryManagerMXBeans;
    }

    /**
     * @return the memoryMXBean
     */
    public final MemoryMXBean getMemoryMXBean() {
        return memoryMXBean;
    }

    /**
     * @return the memoryPoolMXBeans
     */
    public final List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
        return memoryPoolMXBeans;
    }

    /**
     * @return the operatingSystemMXBean
     */
    public final OperatingSystemMXBean getOperatingSystemMXBean() {
        return operatingSystemMXBean;
    }

    /**
     * @return the mBeanServer
     */
    public final MBeanServer getMBeanServer() {
        return mBeanServer;
    }

    /**
     * @return the runtimeMXBean
     */
    public final RuntimeMXBean getRuntimeMXBean() {
        return runtimeMXBean;
    }

    /**
     * @return the threadMXBean
     */
    public final ThreadMXBean getThreadMXBean() {
        return threadMXBean;
    }

    /**
     * @todo DOCUMENT ME!
     *
     * @param request DOCUMENT ME!
     * @param startTime DOCUMENT ME!
     * @param elapsedTime DOCUMENT ME!
     * @param threadId DOCUMENT ME!
     */
    public void timerThresholdExceeded(
        final ServletRequest request, final long startTime, final long elapsedTime,
        final long threadId) {
        Thread logThread =
            new Thread(
                new Runnable() {
                    public void run() {
                        threadLogger.log(
                            request, startTime, elapsedTime, threadMXBean, threadId);
                    }
                });
        logThread.start();

        try {
            logThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    // for testing
    /**
     * @todo DOCUMENT ME!
     *
     * @param requestInfo DOCUMENT ME!
     * @param startTime DOCUMENT ME!
     * @param elapsedTime DOCUMENT ME!
     * @param threadId DOCUMENT ME!
     */
    public void timerThresholdExceeded(
        final String requestInfo, final long startTime, final long elapsedTime,
        final long threadId) {
        Thread logThread =
            new Thread(
                new Runnable() {
                    public void run() {
                        threadLogger.log(
                            requestInfo, startTime, elapsedTime, threadMXBean, threadId);
                    }
                });
        logThread.start();
    }

    /**
     * @todo DOCUMENT ME!
     *
     * @param request DOCUMENT ME!
     * @param startTime DOCUMENT ME!
     * @param elapsedTime DOCUMENT ME!
     * @param threadId DOCUMENT ME!
     */
    public void collectSessionHistory(
        ServletRequest request, long startTime, long elapsedTime, long threadId) {
        lazyInitSessionHistory();

        String sessionId = sessionIdForRequest(request);

        if (sessionId == null) {
            logger.warn("collectSessionHistory:  no session for request");

            return;
        }

        String requestUri = uriForRequest(request);

        if (requestUri == null) {
            logger.warn("collectSessionHistory: couldn't obtain requestURI");

            return;
        }
        logger.info("adding history for " + sessionId);
        sessionRequestHistory.add(sessionId, requestUri, startTime, elapsedTime);
    }

    private String uriForRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            return null;
        }

        HttpServletRequest hsr = (HttpServletRequest) request;

        return hsr.getRequestURI();
    }

    /**
     * @param request
     */
    private String sessionIdForRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            return null;
        }

        HttpServletRequest hsr = (HttpServletRequest) request;
        HttpSession session = hsr.getSession(true);

        if (session == null) {
            return null;
        }

        return session.getId();
    }

    private void lazyInitSessionHistory() {
    	logger.info(" #### Enabling Session History Collection");
    	sessionHistoryEnabled = true;
        if (sessionRequestHistory == null) {
            sessionRequestHistory = new HttpSessionRequestHistory();
             sessionHistoryLogger = new SessionRequestHistoryLogger();
        }
    }

	public void sessionDestroyed(String sessionId) {
		if (!sessionHistoryEnabled) 
			return;
		   Map<String, List<RequestInfo>> history 
		   	= sessionRequestHistory.getHistory();
		   List<RequestInfo> sessionHistory = history.remove(sessionId);
		   if (sessionHistory == null) {
			   logger.error("sessionDestroyed called for unknown session: " 
					   + sessionId);
			   return;
		   }
		   logger.info(" ### longing session  " + sessionId);
	       sessionHistoryLogger.log(sessionId, sessionHistory);
	       
		
	}
}
