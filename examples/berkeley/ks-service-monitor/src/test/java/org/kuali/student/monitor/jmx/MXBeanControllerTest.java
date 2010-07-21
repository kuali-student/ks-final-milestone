/**
 *
 */
package org.kuali.student.monitor.jmx;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;

import org.junit.Test;
import org.kuali.student.deployment.monitor.jmx.MXBeanController;


/**
 * @author randy
 *
 */
public class MXBeanControllerTest {

    //~ Static fields/initializers -------------------------------------------------------

    static final String NL = System.getProperty("line.separator");

    //~ Instance fields ------------------------------------------------------------------

    MXBeanController controller = new MXBeanController();

    //~ Methods --------------------------------------------------------------------------
    
    @Test
    public void testThreadInfo() {
    	long start = System.currentTimeMillis();
    	long elapsed = start + 10000L;
    	controller.timerThresholdExceeded("test request", start, elapsed,
    			Thread.currentThread().getId());
    }
   

   

}
