package org.kuali.student.deployment.monitor.http.listener;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpSessionEvent;

import org.kuali.student.deployment.monitor.graphics.BufferedImageMemoryMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MemorySessionMonitor.java
 * Updates  graphic memory monitor on every
 * session create/destroy event.
 *
 * Created: Mon Mar 11 10:50:51 2002
 *
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 * @version $Revision: 1.5 $ $Date: 2005/04/11 17:44:25 $ $Author: randy $
 * @see <{BufferedImageMemoryMonitor}>
 */
public class MemorySessionMonitor extends SessionMonitor {

    //~ Static fields/initializers -------------------------------------------------------

    /**
     * Servlet context attribute name
     */
    public static final String CTX_ATTR_NAME = "MemorySessionMonitor";
    protected final static Logger logger =
        LoggerFactory.getLogger(MemorySessionMonitor.class);

    //~ Instance fields ------------------------------------------------------------------

    private BufferedImageMemoryMonitor memoryMonitor;

    //~ Constructors ---------------------------------------------------------------------

    /**
    * Creates a new <code>MemorySessionMonitor</code> instance.
    *
    * @param ctxName a <code>String</code> value
    */
    public MemorySessionMonitor(String ctxName) {
        super(ctxName);
        memoryMonitor = new BufferedImageMemoryMonitor();

        if (logger.isDebugEnabled()) {
            logger.debug("memory monitor is: " + memoryMonitor.hashCode());
        }
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * @return the current memory snapshot
     */
    public BufferedImage getMemorySnapshot() {
        return memoryMonitor.getMemorySnapshot();
    }
    // implementation of javax.servlet.http.HttpSessionListener
    // interface

    /**
     * Calls <code>super.sessionCreated()</code> and updates memory monitor.
     *
     * @param evt a <code>HttpSessionEvent</code> value
     */
    @Override public synchronized void sessionCreated(HttpSessionEvent evt) {
        super.sessionCreated(evt);
        memoryMonitor.update();
    }

    /**
     * Calls <code>super.sessionDestroyed</code>  and updates memory monitor.
     *
     * @param evt a <code>HttpSessionEvent</code> value
     */
    @Override public synchronized void sessionDestroyed(HttpSessionEvent evt) {
        super.sessionDestroyed(evt);
        memoryMonitor.update();
    }
} // MemorySessionMonitor
