/*
 * MemorySessionMonitorDeploymentProxy.java created on Mar 14, 2005
 *
 */
package org.kuali.student.deployment.monitor.http.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 *  Proxy for deployment of MemorySessionMonitor, which needs
 *  to be available in ServletContext and requires a constructor argument
 *
 *
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 *
 * @version $Revision: 1.3 $ $Date: 2005/10/24 22:39:08 $ $Author: randy $
 */
public class MemorySessionMonitorDeploymentProxy implements HttpSessionListener {
    //~ Static fields/initializers -------------------------------------------------------

    private static final Logger logger =
        LoggerFactory.getLogger(MemorySessionMonitorDeploymentProxy.class);

    //~ Methods --------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent evt) {
        if (logger.isDebugEnabled()) {
            logger.debug("session created");
        }

        MemorySessionMonitor monitor = getMonitor(evt);
        monitor.sessionCreated(evt);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent evt) {
        if (logger.isDebugEnabled()) {
            logger.debug("session destroyed");
        }
        MemorySessionMonitor monitor = getMonitor(evt);
        monitor.sessionDestroyed(evt);
    }

    /**
     * Fetch monitor from servlet context
     * @param evt
     * @return the MemorySessionMonitor
     */
    private MemorySessionMonitor getMonitor(HttpSessionEvent evt) {
        ServletContext ctx = evt.getSession().getServletContext();
        MemorySessionMonitor monitor =
            (MemorySessionMonitor) ctx.getAttribute(MemorySessionMonitor.CTX_ATTR_NAME);
        if (monitor == null) {
        	throw new IllegalStateException("Memory monitor attribute("
        			+ MemorySessionMonitor.CTX_ATTR_NAME + ") not found in context");
        }
        return monitor;
    }
}
