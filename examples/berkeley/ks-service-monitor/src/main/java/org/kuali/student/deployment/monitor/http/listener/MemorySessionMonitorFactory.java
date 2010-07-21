/*
 * MemorySessionMonitorCreator.java created on Mar 14, 2005
 *
 */
package org.kuali.student.deployment.monitor.http.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 *
 * Factory class for MemorySessionMonitor
 *
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 *
 * @version $Revision: 1.1 $ $Date: 2005/03/18 01:04:25 $ $Author: randy $
 */
public class MemorySessionMonitorFactory extends AbstractContextServiceFactory {

    //~ Methods --------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.kuali.student.deployment.http_monitor.listener.AbstractContextServiceFactory#createService(javax.servlet.ServletContext)
     */
    /**
     * @todo DOCUMENT ME!
     *
     * @param ctx DOCUMENT ME!
     *
     * @throws ServletException DOCUMENT ME!
     */
    public void createService(ServletContext ctx) throws ServletException {
        // TODO Auto-generated method stub
        logger.info("creating memory session monitor");

        MemorySessionMonitor m = new MemorySessionMonitor(ctx.getServletContextName());
        ctx.setAttribute(MemorySessionMonitor.CTX_ATTR_NAME, m);
    }
}
