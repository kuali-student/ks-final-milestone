package org.kuali.student.deployment.monitor.jmx;

import java.io.File;
import java.io.PrintWriter;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import java.sql.Timestamp;


import javax.servlet.ServletRequest;

import org.kuali.student.deployment.monitor.http.RequestDumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestThreadInfoLogger extends AbstractInstrumentationLogger {

    //~ Static fields/initializers -------------------------------------------------------

    static final Logger logger =
        LoggerFactory.getLogger(RequestThreadInfoLogger.class);

    // same location as config overrides

    static final String LOG_DIR_BASE = "ks-request-timer-log";

    static final String LOG_FILE_BASE_NAME = "request";
    
    static final String MIME_TYPE = "txt";

    //~ Instance fields ------------------------------------------------------------------

    /**
     * Creates a new RequestThreadInfoLogger object.
     */
    public RequestThreadInfoLogger() {
    	super(LOG_DIR_BASE, LOG_FILE_BASE_NAME, MIME_TYPE);
    }

    //~ Methods --------------------------------------------------------------------------

	public void log(ServletRequest request, long startTime, long elapsedTime,
			ThreadMXBean threadBean, long threadId) {
		File logFile = createLogFile();
        doLogWrite(logFile, request, startTime, elapsedTime, threadBean, threadId);
		
	}
   

	// for testing
    public void log(String requestInfo, String threadInfo) {
        createDailyLogDirIfNeeded();

        File logFile = createLogFile();
        doLogWrite(logFile, requestInfo, threadInfo);
    }

    // for testing
    public void log(
        final String  requestInfo, final long startTime, final long elapsedTime, 
        final ThreadMXBean threadBean,
        final long threadId) {
        File logFile = createLogFile();
        doLogWrite(logFile, requestInfo, startTime, elapsedTime, threadBean, threadId);
    }
    private void doLogWrite(File logFile, ServletRequest request,
			long startTime, long elapsedTime, ThreadMXBean threadBean,
			long threadId) {
    	logger.info("do log write:  " + logFile.getName());
    	PrintWriter writer = createLogFilePrintWriter(logFile);
    	 writer.println("====== Request Time Threshold Exceeded ====");
         writer.println("Request received at:  " + new Timestamp(startTime));
         writer.println("Execution time: " + elapsedTime + " ms.");
         writer.println("====== Request Info ======");
         RequestDumper.dumpToFile(request, writer, false);
         logThreadInfo(writer, threadBean, threadId);
         writer.flush();
         writer.close();
		
	}
    // for testing
    private void doLogWrite(
        File logFile, String requestInfo, long startTime, long elapsedTime,
        ThreadMXBean threadBean, long threadId) {
        PrintWriter writer = createLogFilePrintWriter(logFile);
        writer.println("====== Request Time Threshold Exceeded ====");
        writer.println("Request received at:  " + new Timestamp(startTime));
        writer.println("Execution time: " + elapsedTime + " ms.");
        writer.println("====== Request Info ======");
        writer.println(requestInfo);
        logThreadInfo(writer, threadBean, threadId);
        writer.flush();
        writer.close();
    }

    private void logThreadInfo(
        PrintWriter writer, ThreadMXBean threadBean, long threadId) {
       
        // Returns the total CPU time for a thread of the specified ID in nanoseconds.
        long cpuTime = threadBean.getThreadCpuTime(threadId);

        // Returns the CPU time that a thread of the specified ID has executed in user mode in nanoseconds.
        long userTime = threadBean.getThreadUserTime(threadId);
        writer.println("====== Thread Info ======");
        
        writer.println("CPU Time: " + cpuTime + " nanos");
        writer.println("User Mode Time:  " + userTime + " nanos");

        ThreadInfo threadInfo = threadBean.getThreadInfo(threadId, Integer.MAX_VALUE);
        logThreadInfo(writer, threadInfo);
    }

    private void logThreadInfo(PrintWriter writer, ThreadInfo threadInfo) {
       
        //Returns the total number of times that the thread associated with
        // this ThreadInfo blocked to enter or reenter a monitor.
        long blockedCount = threadInfo.getBlockedCount();

        //  Returns the approximate accumulated elapsed time (in milliseconds)
        // that the thread associated with this ThreadInfo has blocked to enter
        // or reenter a monitor since thread contention monitoring is enabled.
        long blockedTime = threadInfo.getBlockedTime();
        writer.println(" Monitor Blocks:  " + blockedCount);
        writer.println(" Block Time: " + blockedTime + " ms.");

        // Returns the stack trace of the thread associated with this ThreadInfo.
        StackTraceElement[] stackTrace = threadInfo.getStackTrace();
        logStackTrace(writer, stackTrace);
    }

    private void logStackTrace(PrintWriter writer, StackTraceElement[] stackTrace) {
    	for (StackTraceElement e : stackTrace) {
    		logStackTraceElement(writer, e);
    		
    	}
    }

    private void logStackTraceElement(PrintWriter writer, StackTraceElement e) {
		boolean isNative = e.isNativeMethod();
		String className = e.getClassName();
		String methodName = e.getMethodName();
		String fileName = e.getFileName();
		int lineNumber = e.getLineNumber();
		String nativity = isNative ? " - NATIVE - " : "";
		String rpt = String.format(" %s.%s [%s: line %d] %s", className,
				methodName, fileName, lineNumber, nativity);
		writer.println(rpt);
	}

	private void doLogWrite(File logFile, String requestInfo, String threadInfo) {
        PrintWriter writer = createLogFilePrintWriter(logFile);
        writer.println("====== Request Info ======");
        writer.println(requestInfo);
        writer.println("====== Thread Info =======");
        writer.println(threadInfo);
        writer.flush();
        writer.close();
    }

}
