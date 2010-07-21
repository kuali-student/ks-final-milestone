/*
 * AbstractContextServiceFactory.java created on Mar 14, 2005
 *
 */
package org.kuali.student.deployment.monitor.http.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;


/**
 *
 * Superclass for webapp-wide service creation.
 *
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 *
 * @version $Revision: 1.5 $ $Date: 2006/03/22 00:03:04 $ $Author: randy $
 */
public abstract class AbstractContextServiceFactory
    implements ServletContextListener {

    //~ Static fields/initializers ---------------------------------------------

    protected final static Logger logger = LoggerFactory.getLogger(
            AbstractContextServiceFactory.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * Factory method to create a service.
     * Implementation classes will place the service implementation
     * in the servlet context's attribute list.
     * @param ctx the servlet context
     *
     */
    public abstract void createService(ServletContext ctx)
        throws ServletException;

    /**
     * Hook for resource deallocation.  Subclasses may override as
     * needed.
     * @param ctx  the servletcontext
     */
    public void destroyService(ServletContext ctx) throws ServletException {
        // default to noop
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent evt) {

        try {
            destroyService(evt.getServletContext());
        } catch (ServletException e) {
            logger.error(e.toString());
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent evt) {
        ServletContext ctx = evt.getServletContext();
        logger.info(" initalizing for context: " + ctx.getServletContextName() +
            " hash code: " + hashCode());

        try {
            createService(evt.getServletContext());
        } catch (ServletException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream(ServletContext ctx,
        String resourceName) throws ServletException {
        InputStream rval;

        rval = ctx.getResourceAsStream(resourceName);

        if (rval == null) {
            logger.error("unable to getResourceAsStream named: " +
                resourceName);
            throw new ServletException("unable to getResourceAsStream " +
                " named " + resourceName);
        }

        return (rval);
    }

    protected String getFileDataAsString(ServletContext ctx,
        String resourceName) throws ServletException {
        String rval;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
             is = getInputStream(ctx, resourceName);
             isr = new InputStreamReader(is);
            char[] c = new char[is.available()];
            isr.read(c);
            rval = new String(c);
            isr.close();
            is.close();
        } catch (FileNotFoundException fe) {
            logger.error("file not found: " + resourceName);
            throw (new ServletException(fe));
        } catch (IOException ioe) {
            logger.error("IO exception in file: " + resourceName
            		+  " " + ioe.getLocalizedMessage());
            
            	try {
					isr.close();
					is.close();
				} catch (IOException e) {
					logger.error(e.getLocalizedMessage());
				}
           
            throw (new ServletException(ioe));
        }

        return rval;
    }
}
