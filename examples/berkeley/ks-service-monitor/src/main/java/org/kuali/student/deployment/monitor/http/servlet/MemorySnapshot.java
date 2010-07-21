package org.kuali.student.deployment.monitor.http.servlet;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import org.kuali.student.deployment.monitor.http.listener.MemorySessionMonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MemorySnapshot.java
 *
 *
 * Created: Mon Mar 11 14:13:40 2002
 *
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 * @version $Revision: 1.3 $ $Date: 2003/05/28 16:31:39 $ $Author: randy $
 */
public class MemorySnapshot extends HttpServlet {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(
            MemorySnapshot.class);

    //~ Methods ----------------------------------------------------------------

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {

        BufferedImage snapshot = memorySnapshot(getServletContext());

        OutputStream os = res.getOutputStream();
        JPEGEncodeParam jpgParam = JPEGCodec.getDefaultJPEGEncodeParam(
                snapshot);
        jpgParam.setQuality(80, true);

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os, jpgParam);
        encoder.encode(snapshot);
    }

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
    }

    private BufferedImage memorySnapshot(ServletContext ctx) {
        MemorySessionMonitor monitor = (MemorySessionMonitor) ctx.getAttribute(
                MemorySessionMonitor.CTX_ATTR_NAME);

        if (monitor == null) {
            String msg = MemorySessionMonitor.CTX_ATTR_NAME +
                " not in servlet context";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        if (logger.isDebugEnabled())
            logger.debug("monitor is: " + monitor.hashCode());

        return monitor.getMemorySnapshot();
    }
}
// MemorySnapshot
