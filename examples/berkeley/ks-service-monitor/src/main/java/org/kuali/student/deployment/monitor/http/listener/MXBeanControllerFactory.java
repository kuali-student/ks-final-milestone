package org.kuali.student.deployment.monitor.http.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.kuali.student.deployment.monitor.jmx.MXBeanController;


public class MXBeanControllerFactory extends AbstractContextServiceFactory {

    //~ Methods --------------------------------------------------------------------------

    /**
     * Instantiates the controller and places a reference in the
     * servlet context
     *
     * @param ctx the servlet context
     *
     * @throws ServletException DOCUMENT ME!
     */
    @Override public void createService(ServletContext ctx) throws ServletException {
    	logger.info("creating MXBeanController...");
        MXBeanController controller = new MXBeanController();
        logger.info("MXBeanController created:  " + controller.hashCode());
        ctx.setAttribute(MXBeanController.CTX_ATTR_NAME, controller);
        logger.info("MXBean controller in servlet context with handle: " 
        	+ MXBeanController.CTX_ATTR_NAME);
    }
    
}
