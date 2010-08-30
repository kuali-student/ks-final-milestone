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
public class MXBeanControllerConfigTest {

    //~ Static fields/initializers -------------------------------------------------------

    static final String NL = System.getProperty("line.separator");

    //~ Instance fields ------------------------------------------------------------------

    MXBeanController controller = new MXBeanController();

    //~ Methods --------------------------------------------------------------------------

    /**
     * @todo DOCUMENT ME!
     */
    @Test public void testThreadInfo() {
        long start = System.currentTimeMillis();
        long elapsed = start + 10000L;
        controller.timerThresholdExceeded(
            "test request", start, elapsed, Thread.currentThread().getId());
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getClassLoadingMXBean()}.
     */
    @Test public void testGetClassLoadingMXBean() {
        ClassLoadingMXBean classsLoadingBean = controller.getClassLoadingMXBean();

        if (classsLoadingBean == null) {
            System.out.println("No ClassLoadingMXBean in this jvm");

            return;
        }

        boolean verbose = classsLoadingBean.isVerbose();
        int currentLoadedClasses = classsLoadingBean.getLoadedClassCount();
        long totalLoadedClasses = classsLoadingBean.getTotalLoadedClassCount();
        long unloadedClasses = classsLoadingBean.getUnloadedClassCount();
        System.out.println("");

        StringBuilder buf = new StringBuilder("=== Class Loading ===");
        buf.append(NL).append("Total Loaded Classes:  ").append(totalLoadedClasses)
           .append(NL).append("Current Loaded Classes:  ").append(currentLoadedClasses)
           .append(NL).append("Unloaded Classes:  ").append(unloadedClasses);
        System.out.println(buf.toString());
        System.out.println("");
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getCompilationMXBean()}.
     */
    @Test public void testGetCompilationMXBean() {
        CompilationMXBean compileBean = controller.getCompilationMXBean();

        if (compileBean == null) {
            System.out.println("No CompilationMXBean in this jvm");

            return;
        }

        String jitCompilerName = compileBean.getName();
        long totalCompileTime = compileBean.getTotalCompilationTime();
        boolean monitorSupported = compileBean.isCompilationTimeMonitoringSupported();
        StringBuilder buf = new StringBuilder("=== Compilation ===");
        buf.append(NL).append("Compilation Monitoring Supported: ")
           .append(monitorSupported).append(NL).append("JIT Compiler Name: ")
           .append(jitCompilerName).append(NL).append("Total Compile Time:  ").append(
            totalCompileTime);
        System.out.println(buf.toString());
        System.out.println("");
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getGarbageCollectorMXBeans()}.
     */
    @Test public void testGetGarbageCollectorMXBean() {
        List<GarbageCollectorMXBean> gcBeans = controller.getGarbageCollectorMXBeans();

        if ((gcBeans == null) || (gcBeans.size() == 0)) {
            System.out.println("No GarbageCollectorMXBean in this jvm");

            return;
        }

        reportGarbageCollection(gcBeans);
        System.out.println("");
    }

    private void reportGarbageCollection(List<GarbageCollectorMXBean> gcBeans) {
        StringBuilder buf = new StringBuilder("=== Garbage Collection ===");

        for (GarbageCollectorMXBean gcBean : gcBeans) {
            String memoryManagerReport = memoryManagerReport(gcBean);
            buf.append(NL).append(memoryManagerReport);

            long collectionCount = gcBean.getCollectionCount();
            long collectionTime = gcBean.getCollectionTime();
            buf.append(NL).append("Collection Count: ").append(collectionCount).append(NL)
               .append("Collection Time (ms):  ").append(collectionTime);
        }

        System.out.println(buf.toString());
    }

    private String memoryManagerReport(MemoryManagerMXBean memoryBean) {
        String[] poolNames = memoryBean.getMemoryPoolNames();
        String name = memoryBean.getName();
        boolean valid = memoryBean.isValid();
        StringBuilder buf = new StringBuilder("==- MemoryManager ==");
        buf.append(NL).append("Name:  ").append(name).append(NL).append("Valid:  ")
           .append(valid).append(NL).append("Pool Names:  ");

        int counter = 1;

        for (String pn : poolNames) {
            buf.append(counter).append(":  ").append(pn).append(' ');
            counter++;
        }

        return buf.toString();
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getMemoryManagerMXBeans()}.
     */
    @Test public void testGetMemoryManagerMXBeans() {
        List<MemoryManagerMXBean> memoryManagerBeans =
            controller.getMemoryManagerMXBeans();

        if ((memoryManagerBeans == null) || (memoryManagerBeans.size() == 0)) {
            System.out.println("No MemoryManagerMXBeans in this jvm");

            return;
        }

        System.out.println("======= All Memory Managers ======");

        for (MemoryManagerMXBean memoryBean : memoryManagerBeans) {
            String rpt = memoryManagerReport(memoryBean);
            System.out.println(rpt);
        }

        System.out.println("");
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getMemoryMXBean()}.
     */
    @Test public void testGetMemoryMXBean() {
        MemoryMXBean memoryBean = controller.getMemoryMXBean();

        if (memoryBean == null) {
            System.out.println("No MemoryMXBean in this jvm");

            return;
        }

        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        int objectsPendingFinalization = memoryBean.getObjectPendingFinalizationCount();
        boolean verbose = memoryBean.isVerbose();
        StringBuilder buf = new StringBuilder("=== MemoryUsage ==");
        buf.append(NL).append("verbose: ").append(verbose).append(NL)
           .append("Objects Pending Finalization: ").append(objectsPendingFinalization)
           .append(NL).append("Heap Usage:  ").append(heapUsage.toString()).append(NL)
           .append("Non Heap Usage: ").append(nonHeapUsage.toString());
        System.out.println(buf.toString());
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getMemoryPoolMXBeans()}.
     */
    @Test public void testGetMemoryPoolMXBeans() {
        List<MemoryPoolMXBean> memoryPoolBeans = controller.getMemoryPoolMXBeans();

        if ((memoryPoolBeans == null) || (memoryPoolBeans.size() == 0)) {
            System.out.println("No MemoryPoolBeans in this jvm");

            return;
        }

        for (MemoryPoolMXBean memoryPoolBean : memoryPoolBeans) {
            reportMemoryPool(memoryPoolBean);
        }
    }

    private void reportMemoryPool(MemoryPoolMXBean memoryPoolBean) {
        String name = memoryPoolBean.getName();
        MemoryType memoryType = memoryPoolBean.getType();
        boolean valid = memoryPoolBean.isValid();
        String[] managerNames = memoryPoolBean.getMemoryManagerNames();

        MemoryUsage currentUsage = memoryPoolBean.getUsage();
        MemoryUsage peakUsage = memoryPoolBean.getPeakUsage();

        boolean usageThresholdSupported = memoryPoolBean.isUsageThresholdSupported();
        long usageThreshold = 0;
        long usageThresholdCount = 0;
        boolean usageThresholdExceeded = false;

        if (usageThresholdSupported) {
            usageThreshold = memoryPoolBean.getUsageThreshold();
            usageThresholdCount = memoryPoolBean.getUsageThresholdCount();
            usageThresholdExceeded = memoryPoolBean.isUsageThresholdExceeded();
        }

        boolean collectionThresholdSupported =
            memoryPoolBean.isCollectionUsageThresholdSupported();
        MemoryUsage collectionUsage = memoryPoolBean.getCollectionUsage();

        long collectionUsageThreshold = 0;
        boolean collectionUsageThresholdExceeded = false;

        if (collectionThresholdSupported) {
            collectionUsageThreshold = memoryPoolBean.getCollectionUsageThreshold();
            collectionUsageThresholdExceeded =
                memoryPoolBean.isCollectionUsageThresholdExceeded();
        }

        StringBuilder buf = new StringBuilder("===== Memory Pool =====");
        buf.append(NL).append("Name:  ").append(name).append(NL).append("Memory Type:  ")
           .append(memoryType).append(NL).append("Valid:  ").append(valid).append(NL)
           .append("Manaager Names:  ");

        int counter = 1;

        for (String mn : managerNames) {
            buf.append(counter).append(":  ").append(mn).append(' ');
            counter++;
        }

        buf.append(NL).append("Current Usage:  ").append(currentUsage).append(NL)
           .append("Peak Usage:  ").append(peakUsage).append(NL)
           .append("Supports Usage Threshold:  ").append(usageThresholdSupported)
           .append(NL).append("Usage Threshold:  ").append(usageThreshold).append(NL)
           .append("Usage Threshold Exceeded: ").append(usageThresholdExceeded);

        buf.append(NL).append("Current CollectionUsage:  ").append(collectionUsage);

        buf.append(NL).append("Supports Collection Usage Threshold:  ")
           .append(collectionThresholdSupported).append(NL)
           .append("Collection Usage Threshold:  ").append(collectionUsageThreshold)
           .append(NL).append("Collection Usage Threshold Exceeded: ").append(
            collectionUsageThresholdExceeded);
        System.out.println(buf.toString());
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getOperatingSystemMXBean()}.
     */
    @Test public void testGetOperatingSystemMXBean() {
        OperatingSystemMXBean osBean = controller.getOperatingSystemMXBean();

        if (osBean == null) {
            System.out.println("No OperatingSystemMXBean in this jvm");

            return;
        }

        String osName = osBean.getName();
        String version = osBean.getVersion();
        String arch = osBean.getArch();
        int processorCount = osBean.getAvailableProcessors();

        // Returns the system load average for the last minute.
        double loadAverage = osBean.getSystemLoadAverage();
        StringBuilder buf = new StringBuilder("=== Operating System");
        buf.append(NL).append("Name:  ").append(osName).append(NL).append("Version:  ")
           .append(version).append(NL).append("Architecture:  ").append(arch).append(NL)
           .append("Available Processors:  ").append(processorCount).append(NL)
           .append("Load Average (last 60 seconds):  ").append(loadAverage);
        System.out.println(buf.toString());
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getRuntimeMXBean()}.
     */
    @Test public void testGetRuntimeMXBean() {
        RuntimeMXBean runtimeBean = controller.getRuntimeMXBean();

        if (runtimeBean == null) {
            System.out.println("No RuntimeMXBean in this jvm");

            return;
        }

        StringBuilder buf = new StringBuilder("=== Runtime Info ===");

        // // Returns the version of the specification for the management interface
        // implemented by the running Java virtual machine.
        String managementSpecVersion = runtimeBean.getManagementSpecVersion();

        // Returns the name representing the running Java virtual machine.
        String jvmName = runtimeBean.getName();

        // Returns the Java virtual machine specification name.
        String jvmSpecName = runtimeBean.getSpecName();

        // Returns the Java virtual machine specification vendor.
        String jvmSpecVendor = runtimeBean.getSpecVendor();

        // Returns the Java virtual machine specification version.
        String jvmSpecVersion = runtimeBean.getSpecVersion();

        // Returns the Java virtual machine implementation name.
        String jvmImplName = runtimeBean.getVmName();

        // Returns the Java virtual machine implementation vendor.
        String jvmVendorName = runtimeBean.getVmVendor();

        // Returns the Java virtual machine implementation version.
        String jvmVersion = runtimeBean.getVmVersion();

        buf.append(NL).append("JVM Name:  ").append(jvmName).append(NL)
           .append("Specification:  ").append(jvmSpecName).append(NL)
           .append("Specification Vendor:  ").append(jvmSpecVendor).append(NL)
           .append("Specification Version:  ").append(jvmSpecVersion).append(NL)
           .append("Implementation:  ").append(jvmImplName).append(NL).append("Vendor:  ")
           .append(jvmVendorName).append(NL).append("Version:  ").append(jvmVersion)
           .append(NL).append("Management Specification Version:  ").append(
            managementSpecVersion);

        // Returns the input arguments passed to the Java virtual machine
        // which does not include the arguments to the main method.
        List<String> inputArgs = runtimeBean.getInputArguments();

        if ((inputArgs != null) && (inputArgs.size() > 0)) {
            buf.append(NL).append("Input Arguments:  ");

            int counter = 1;

            for (String arg : inputArgs) {
                buf.append(counter).append(":  ").append(arg).append(' ');
                counter++;
            }
        }

        // Returns a map of names and values of all system properties.
        Map<String, String> systemProperties = runtimeBean.getSystemProperties();
        buf.append(NL).append("System Properties:  ");

        for (Map.Entry<String, String> e : systemProperties.entrySet()) {
            buf.append(e.getKey()).append(":  ").append(e.getValue()).append(' ');
        }

        // Returns the start time of the Java virtual machine in milliseconds.
        long startTimeMillis = runtimeBean.getStartTime();
        buf.append(NL).append("Start Time:  ").append(new Timestamp(startTimeMillis));

        // Returns the uptime of the Java virtual machine in milliseconds.
        long uptime = runtimeBean.getUptime();
        renderUptime(buf, uptime);

        // Tests if the Java virtual machine supports the boot class path
        // mechanism used by the bootstrap class loader to search for class files.
        boolean bootClassPathSupported = runtimeBean.isBootClassPathSupported();

        // Returns the boot class path that is used by the bootstrap class
        // loader to search for class files.
        String bootClassPath = null;

        if (bootClassPathSupported) {
            bootClassPath = runtimeBean.getBootClassPath();
            buf.append(NL).append("Boot Class Path:  ").append(bootClassPath);
        }

        // Returns the Java class path that is used by the system class
        // loader to search for class files.
        String classPath = runtimeBean.getClassPath();
        buf.append(NL).append("Class Path:  ").append(classPath);

        // Returns the Java library path.
        String libraryPath = runtimeBean.getLibraryPath();
        buf.append(NL).append("Library Path:  ").append(libraryPath);
        System.out.println(buf.toString());
    }

    private void renderUptime(StringBuilder buf, long duration) {
        long days = TimeUnit.DAYS.toDays(duration);
        buf.append(NL).append("Uptime:  ");

        if (days > 0) {
            buf.append(days).append(" days");

            return;
        }

        long hours = TimeUnit.HOURS.toHours(duration);

        if (hours > 0) {
            buf.append(hours).append(" hours");

            return;
        }

        long minutes = TimeUnit.MINUTES.toMinutes(duration);

        if (minutes > 0) {
            buf.append(minutes).append(" minutes");

            return;
        }

        long seconds = TimeUnit.SECONDS.toSeconds(duration);

        if (seconds > 0) {
            buf.append(seconds).append(" seconds");
        }
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getThreadMXBean()}.
     */
    @Test public void testGetThreadMXBean() {
        ThreadMXBean threadBean = controller.getThreadMXBean();

        if (threadBean == null) {
            System.out.println("No ThreadMXBean in this jvm");

            return;
        }
    }

    /**
     * Test method for {@link org.kuali.student.deployment.monitor.jmx.MXBeanController#getmBeanServer()}.
     */
    @Test public void testGetMBeanServer() {
        MBeanServer server = controller.getMBeanServer();

        if (server == null) {
            System.out.println("No MBeanServer in this jvm");

            return;
        }
    }
}
