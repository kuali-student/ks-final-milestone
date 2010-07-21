/*
 * MemorySessionMonitorCreator.java created on Mar 14, 2005
 *
 */
package org.kuali.student.deployment.monitor.http.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.kuali.student.deployment.monitor.http.HttpRequestStatisticsCollector;


/**
 *
 * Factory class for HttpRequestMonitor
 *
 * @author <a href="mailto:randy@sberkeley.edu">Randy Ballew</a>
 *
 * @version $Revision: 1.1 $ $Date: 2005/03/18 01:04:25 $ $Author: randy $
 */
public class RequestMonitorFactory extends AbstractContextServiceFactory {

    //~ Static fields/initializers -------------------------------------------------------

    /** DOCUMENT ME! */
    public static final String CTX_TESTING_PARAM = "TestMode";

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
        Boolean testing = Boolean.valueOf(ctx.getInitParameter(CTX_TESTING_PARAM));
        logger.info("creating request statistics collector, test mode is" + testing);

        HttpRequestStatisticsCollector c = new HttpRequestStatisticsCollector(testing);
        ctx.setAttribute(HttpRequestStatisticsCollector.CTX_ATTR_NAME, c);
    }
}
