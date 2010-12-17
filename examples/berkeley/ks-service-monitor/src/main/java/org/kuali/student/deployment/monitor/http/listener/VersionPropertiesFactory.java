/*
 * VersionPropertiesFactory.java created on Mar 14, 2005
 *
 */
package org.kuali.student.deployment.monitor.http.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 *
 * Create a Version Properties Service
 *
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 *
 * @version $Revision: 1.3 $ $Date: 2006/03/21 23:20:19 $ $Author: randy $
 */
public class VersionPropertiesFactory extends AbstractContextServiceFactory
    {
	  /**
     * Servlet context attribute name
     */
    public static final String CTX_ATTR_NAME = "VersionProps";
    //~ Methods --------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.kuali.student.deployment.http_monitor.listener.AbstractContextServiceFactory#createService(javax.servlet.ServletContext)
     */
    public void createService(ServletContext ctx) throws ServletException {
        logger.info("loading version properties...");

        String propFile = ctx.getInitParameter(CTX_ATTR_NAME);
        if (propFile == null) {
        	throw new IllegalStateException("no context parameter:  " + CTX_ATTR_NAME);
        }
        InputStream is;
        Properties versionProps = new Properties();

        try {
            is = getInputStream(ctx, propFile);
        } catch (ServletException se) {
            logger.error("streek couldn't find " + propFile + " in context root");
            throw new IllegalStateException(
                "Couldn't find: " + propFile + " in context root");
        }

        try {
            versionProps.load(is);
            is.close();
            ctx.setAttribute(CTX_ATTR_NAME, versionProps);
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            throw new IllegalStateException(ioe.getMessage());
        }
    }
}
