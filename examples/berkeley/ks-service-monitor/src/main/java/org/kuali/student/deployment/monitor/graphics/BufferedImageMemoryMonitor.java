package org.kuali.student.deployment.monitor.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import org.kuali.student.deployment.monitor.graphics.MemoryMonitorModel.Snapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * BufferedImageMemoryMonitor.java
 * Memory monitor utility produces a BufferedImage with snapshot
 * of current memory state.  In a j2ee container,
 *  an <code>HttpSessionBindingListener</code> can
 * invoke the <code>update</code> method on bind events
 * to produce a history of memory usage.
 * As of servlet 2.3, A <code>HttpSessionListener</code>
 * may also be used.
 * <b>This class is not thread-safe</b>.
 * Client classes must <tt>synchronize</tt> calls to <tt>update</tt>.
 * Derived from java2d demo.  Sun copyright at
 * end of this file.
 *
 * Created: Sun Mar 10 13:54:22 2002
 * @author Sun Java2 Demo Team
 * @author <a href="mailto:randy@socrates.berkeley.edu">Randy Ballew</a>
 * @version $Id: BufferedImageMemoryMonitor.java,v 1.9 2006/03/21 23:02:41 randy Exp $
 */
public class BufferedImageMemoryMonitor {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(
            BufferedImageMemoryMonitor.class);
    private static final int PREF_WIDTH = 540;
    private static final int PREF_HEIGHT = 320;
    private static final int GRAPH_XPOS = 30;
    private static final int GRAPH_MARGIN = 5;

    //~ Instance fields --------------------------------------------------------

    private Dimension size;
    private Rectangle graphOutlineRect = new Rectangle();
    private Rectangle2D mfRect = new Rectangle2D.Float();
    private Rectangle2D muRect = new Rectangle2D.Float();
    private Line2D graphLine = new Line2D.Float();
    private Color graphColor = new Color(46, 139, 87);
    private Color mfColor = new Color(0, 100, 0);
    private Color bgColor = Color.black;
    private Font font = new Font("Helvetica", Font.PLAIN, 20);
    private int columnInc;

    private int ascent;
    private int descent;
    private int leading;
    BufferedImage theImage;
    Graphics2D big;

    private MemoryMonitorModel model;

    //~ Constructors -----------------------------------------------------------

    public BufferedImageMemoryMonitor() {
        size = new Dimension(PREF_WIDTH, PREF_HEIGHT);
        createImage();
        model = new MemoryMonitorModel(calculateGraphWidth());
        model.setSampleCount(calculateGraphWidth());
    }

    //~ Methods ----------------------------------------------------------------

    private void createImage() {
        theImage = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_INT_RGB);
        big = (Graphics2D) theImage.createGraphics();
        big.setFont(font);
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        big.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);

        FontMetrics fm = big.getFontMetrics(font);
        ascent = (int) fm.getAscent();
        descent = (int) fm.getDescent();
        leading = (int) fm.getLeading();
    }

    public void setPreferredSize(Dimension d) {
        size = d;
        createImage();
        update();
    }

    public Dimension getPreferredSize() {
        return size;
    }

    public synchronized BufferedImage getMemorySnapshot() {
        long start = System.currentTimeMillis();
        render();

        if (logger.isDebugEnabled())
            logger.debug(" " + theImage.hashCode());


        long elapsed = System.currentTimeMillis() - start;

        if (logger.isDebugEnabled())
            logger.debug("render image in " + elapsed + " ms.");

        return theImage;
    }

    public void update() {
        model.update();
    }

    private void render() {
        int w = size.width;
        int h = size.height;
        big.setBackground(bgColor);
        big.clearRect(0, 0, w, h);


        // .. Draw allocated and used strings ..
        big.setColor(Color.green);


        big.drawString(model.reportAllocation(), 4.0f, (float) ascent + 0.5f);

        big.drawString(model.reportCurrentUsage(), 4.0f,
            (float) (h - (ascent + descent + leading)) - 0.5f);

        big.setColor(Color.yellow);
        big.drawString(model.reportPeakUsage(), 4, h - descent);

        // Calculate remaining size
        float ssH = ascent + descent + leading;
        float remainingHeight = (float) (h - (ssH * 3) - 0.5f);
        float blockHeight = remainingHeight / 10;
        float blockWidth = 20.0f;
       // float remainingWidth = (float) (w - blockWidth - 10);

        // .. Memory Free ..
        big.setColor(mfColor);

        int MemUsage = (int) (model.getCurrentUsagePercentage() * 10);
        int i = 0;

        for (; i < MemUsage; i++) {
            mfRect.setRect(5, (float) ssH + (i * blockHeight), blockWidth,
                (float) blockHeight - 1);
            big.fill(mfRect);
        }

        // .. Memory Used ..
        big.setColor(Color.green);

        for (; i < 10; i++) {
            muRect.setRect(5, (float) ssH + (i * blockHeight), blockWidth,
                (float) blockHeight - 1);
            big.fill(muRect);
        }

        // .. Draw History Graph ..
        big.setColor(graphColor);

        int graphX = GRAPH_XPOS;
        int graphY = (int) ssH;
        int graphW = calculateGraphWidth();
        int graphH = (int) remainingHeight;

        MemoryMonitorModel.Snapshot snapshot = model.renderableSnapshot(graphY,
                graphH);

        graphOutlineRect.setRect(graphX, graphY, graphW, graphH);
        big.draw(graphOutlineRect);

        int graphRow = graphH / 10;

        // .. Draw row ..
        for (int j = graphY; j <= (graphH + graphY); j += graphRow) {
            graphLine.setLine(graphX, j, graphX + graphW, j);
            big.draw(graphLine);
        }

        // .. Draw animated column movement ..
        int graphColumn = graphW / 15;

        if (columnInc == 0) {
            columnInc = graphColumn;
        }

        for (int j = graphX + columnInc; j < (graphW + graphX);
                j += graphColumn) {
            graphLine.setLine(j, graphY, j, graphY + graphH);
            big.draw(graphLine);
        }

        --columnInc;

        big.setColor(Color.yellow);

        int[] pts = snapshot.samples;
        int ptNum = snapshot.count;

        // .. Draw memory history ..
        for (int j = (graphX + graphW) - ptNum, k = 0; k < ptNum; k++, j++) {

            if (k != 0) {
                // System.out.println(k + ": " + pts[k] + " " + pts[k - 1]);

                if (pts[k] != pts[k - 1]) {
                    big.drawLine(j - 1, pts[k - 1], j, pts[k]);
                } else {
                    big.fillRect(j, pts[k], 1, 1);
                }
            }
        }

    }

    private int calculateGraphWidth() {
        return size.width - GRAPH_MARGIN - GRAPH_XPOS;
    }

    public static void main(String[] argv) {
        BufferedImageMemoryMonitor memoryMonitor;
        ArrayList<Integer> refs;

        memoryMonitor = new BufferedImageMemoryMonitor();
        refs = new ArrayList<Integer>();

        for (int i = 0; i < 200; i++) {
            memoryMonitor.update();

            for (int k = 0; k < 500; k++)
                refs.add(k);
        }

        try {
            BufferedImage img = memoryMonitor.getMemorySnapshot();
            final String path = "/var/tmp/model-memory-monitor.jpg";
            File f = new File(path);
            FileOutputStream os = new FileOutputStream(f);
            JPEGEncodeParam jpgParam = JPEGCodec.getDefaultJPEGEncodeParam(img);
            jpgParam.setQuality(80, true);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os,
                    jpgParam);
            encoder.encode(img);
        } catch (Exception e) {
            logger.error(e.toString());
            // fail(e.getMessage());
        }
    }
}
// BufferedImageMemoryMonitor
/*
 * @(#)MemoryMonitor.java        1.26 99/04/23
 *
 * Copyright (c) 1998, 1999 by Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */
