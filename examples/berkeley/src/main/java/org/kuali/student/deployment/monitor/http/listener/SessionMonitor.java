package org.kuali.student.deployment.monitor.http.listener;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.kuali.student.deployment.monitor.jmx.MXBeanController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * SessionMonitor.java
 * SessionMonitor provides some simple instrumentation for
 * testing and/or monitoring load on a web-app.
 *
 * Created: Mon Jul 30 22:13:09 2001
 *
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 * @version $Id: SessionMonitor.java,v 1.7 2005/10/24 22:39:08 randy Exp $
 */
public class SessionMonitor implements HttpSessionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(
            SessionMonitor.class);

    //~ Instance fields --------------------------------------------------------

    private int totalSessionCount;
    private int activeSessionCount;
    private String ctxName;
    private Date appStartTime;
    private int maxConcurrentSessionCount;
    private Set activeSessions;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new <code>SessionMonitor</code> instance.
     *
     * @param ctxName a <code>String</code> value
     */
    public SessionMonitor(String ctxName) {
        this.ctxName = ctxName;
        appStartTime = new Date();
        activeSessions = Collections.synchronizedSet(new HashSet());
        logger.info("SessionMonitor created");
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Return the name of the servlet context being monitored.
     *
     * @return an <code>int</code> value
     */
    public final String getContextName() {
        return ctxName;
    }

    /**
     * Return the application start time.
     *
     * @return <tt>Date<tt> started.
     */
    public Date getApplicationStartTime() {
        return appStartTime;
    }

    /**
     * Logs session creation and bumps session counter.
     *
     * @param evt a <code>HttpSessionEvent</code> value
     */
    public synchronized void sessionCreated(HttpSessionEvent evt) {
        HttpSession session = evt.getSession();
        String id = session.getId();

        if (activeSessions.contains(id)) {
            logger.warn("duplicate sessionCreated notification for " + id);

            return;
        }

        activeSessions.add(id);
        ++activeSessionCount;
        ++totalSessionCount;
        logger.info("New HTTP session: " + id + " is new: " + session.isNew() +
            " session count: " + activeSessionCount + " session set size: " +
            activeSessions.size());

        if (activeSessionCount > maxConcurrentSessionCount) {
            maxConcurrentSessionCount = activeSessionCount;
        }
    }

    /**
     * Logs session termination and decrements session counter.
     *
     * @param evt a <code>HttpSessionEvent</code> value
     */
    public synchronized void sessionDestroyed(HttpSessionEvent evt) {
        HttpSession session = evt.getSession();
        String id = session.getId();
        boolean removed = activeSessions.remove(id);

        if (!removed) {
            logger.warn(
                "received sessionDestroyed notification for unknown sesion: " +
                id);

            return;
        }

        if (activeSessionCount == 0) {
            logger.error(
                "received sessionDestroyed notification when active session count is 0 ");

            return;
        }

        --activeSessionCount;
        notifyMXBeanController(evt);
        if (logger.isDebugEnabled())
            logger.debug("removed session: " + id + " was new: " +
                session.isNew() + " session count: " + activeSessionCount +
                " session set size: " + activeSessions.size());
    }

    private void notifyMXBeanController(HttpSessionEvent evt) {
    	//@  doh!  this introduces a circular dependency 
    	// FIXME --  use java.beans PropertyChangeSupport, PropertyChangeListener
    	
		HttpSession session = evt.getSession();
		ServletContext ctx = session.getServletContext();
		MXBeanController mxbc 
			= (MXBeanController) ctx.getAttribute(MXBeanController.CTX_ATTR_NAME);
		mxbc.sessionDestroyed(session.getId());
	    
	}

	/**
     * Returns maximum number of active sessions in this context.
     *
     * @return an <code>int</code> value
     */
    public synchronized int getMaxConcurrentSessionCount() {
        return maxConcurrentSessionCount;
    }

    /**
     * Returns number of active sessions in this context.
     *
     * @return an <code>int</code> value
     */
    public synchronized int getActiveSessionCount() {
        return activeSessionCount;
    }

    /**
    * Returns number of sessions this context has serviced.
    *
    * @return an <code>int</code> value
    */
    public synchronized int getTotalSessionCount() {
        return totalSessionCount;
    }
} // SessionMonitor
