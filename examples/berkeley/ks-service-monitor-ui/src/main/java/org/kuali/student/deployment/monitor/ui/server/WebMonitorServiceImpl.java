package org.kuali.student.deployment.monitor.ui.server;


import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kuali.student.deployment.monitor.http.HttpRequestStatistics;
import org.kuali.student.deployment.monitor.http.HttpRequestStatisticsCollector;
import org.kuali.student.deployment.monitor.http.listener.MemorySessionMonitor;
import org.kuali.student.deployment.monitor.http.listener.VersionPropertiesFactory;
import org.kuali.student.deployment.monitor.ui.client.RequestData;
import org.kuali.student.deployment.monitor.ui.client.ServerData;
import org.kuali.student.deployment.monitor.ui.client.SessionData;
import org.kuali.student.deployment.monitor.ui.client.VersionData;
import org.kuali.student.deployment.monitor.ui.client.WebMonitorData;
import org.kuali.student.deployment.monitor.ui.client.WebMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 * @author Kuali Student
 */
public class WebMonitorServiceImpl extends RemoteServiceServlet
    implements WebMonitorService {

    //~ Static fields/initializers ---------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -4782363015466473183L;
	private static final Logger logger = LoggerFactory.getLogger(
            WebMonitorServiceImpl.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * (non-javadoc)
     * @see org.kuali.student.deployment.monitor.ui.client.WebMonitorService#fetchWebMonitorData()
     */
    public WebMonitorData fetchWebMonitorData() {
        HttpServletRequest request = this.getThreadLocalRequest();
        getSession(request);

        WebMonitorData rval = createMonitorData();

        return rval;
    }


    /**
     * @return
     */
    private WebMonitorData createMonitorData() {
        ServletContext ctx = this.getServletContext();
        WebMonitorData rval = new WebMonitorData();
        rval.versionData = versionData(ctx);
        rval.serverData = serverData(ctx);
        rval.sessionData = sessionData(ctx);
        rval.requestData = requestData(ctx);
        logger.info("#### in fetchWebMonitorData " + rval);

        return rval;
    }


    /**
    * for testing only...
    */

    @Override protected void doGet(HttpServletRequest req,
        HttpServletResponse resp) throws ServletException, IOException {

        if (logger.isDebugEnabled())
            logger.debug("++++++++ in doGet");

        HttpSession session = getSession(req);
        String buf = versionData(getServletContext()).toString();
        resp.setContentType("text/plain");

        Writer writer = resp.getWriter();
        writer.write(buf);
        writer.flush();
        writer.close();

        if (session.isNew())
            session.invalidate();


    }

    private RequestData requestData(ServletContext ctx) {
        HttpRequestStatisticsCollector collector = requestCollector(ctx);
        HttpRequestStatistics stats = collector.reportSummary();
        RequestData rval = new RequestData();
        rval.averageRequestTime = String.format("%.2f",
                stats.getAverageRequestTime());
        rval.maxRequestTime = String.valueOf(stats.getMaxRequestTime());
        rval.totalRequests = String.valueOf(stats.getTotalRequests());
        rval.startTime = new Date(stats.getStartTimeMillis()).toString();

        return rval;
    }

    private HttpRequestStatisticsCollector requestCollector(
        ServletContext ctx) {
        HttpRequestStatisticsCollector rval = (HttpRequestStatisticsCollector)
            ctx.getAttribute(HttpRequestStatisticsCollector.CTX_ATTR_NAME);

        contextServiceGuard(rval, HttpRequestStatisticsCollector.CTX_ATTR_NAME);

        return rval;
    }


    private VersionData versionData(ServletContext ctx) {
        Properties versionProps = versionProperties(ctx);
        VersionData rval = new VersionData();

        rval.version = versionProps.getProperty("version");
        rval.buildNumber = versionProps.getProperty("build-number");
        rval.buildDate = versionProps.getProperty("build-date");

        return rval;
    }


    private ServerData serverData(ServletContext ctx) {
        MemorySessionMonitor monitor = memorySessionMonitor(ctx);

        ServerData rval = new ServerData();
        rval.hostName = getThreadLocalRequest().getServerName();
        rval.threadCount = threadCount();
        rval.availableProcessors = Runtime.getRuntime().availableProcessors();


        rval.startDate = monitor.getApplicationStartTime();

        //TODO  strategy for getting service name
        rval.serviceName = monitor.getContextName();

        return rval;
    }


    private SessionData sessionData(ServletContext ctx) {
        MemorySessionMonitor monitor = memorySessionMonitor(ctx);
        SessionData rval = new SessionData();
        rval.activeSessionCount = monitor.getActiveSessionCount();
        rval.maxConcurrentSessionCount = monitor.getMaxConcurrentSessionCount();
        rval.totalSessionCount = monitor.getTotalSessionCount();

        return rval;
    }

    /**
     * @param ctx
     * @return the version properties
     */
    private Properties versionProperties(ServletContext ctx) {
        Properties rval = (Properties) ctx.getAttribute(
                VersionPropertiesFactory.CTX_ATTR_NAME);

        contextServiceGuard(rval, VersionPropertiesFactory.CTX_ATTR_NAME);

        return rval;
    }

    /**
     * @param ctx
     * @return the memory monitor
     */
    private MemorySessionMonitor memorySessionMonitor(ServletContext ctx) {
        MemorySessionMonitor rval = (MemorySessionMonitor) ctx.getAttribute(
                MemorySessionMonitor.CTX_ATTR_NAME);
        contextServiceGuard(rval, MemorySessionMonitor.CTX_ATTR_NAME);


        return rval;
    }


    private void contextServiceGuard(Object service, String name) {

        if (service == null) {
            String msg = name + " not in servlet context";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }
    }

    private int threadCount() {
        ThreadGroup parentThread = Thread.currentThread().getThreadGroup();

        while (parentThread.getParent() != null) {
            parentThread = parentThread.getParent();
        }

        return parentThread.activeCount();
    }

    /**
     *
     */
    private HttpSession getSession(HttpServletRequest request) {


        HttpSession session = request.getSession();

        // TODO this should be debug...
        logger.info("Got Session: " + session.getId() + " is new: " +
            session.isNew());

        return session;
    }

}
