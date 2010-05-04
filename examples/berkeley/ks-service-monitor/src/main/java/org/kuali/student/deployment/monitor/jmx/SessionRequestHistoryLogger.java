package org.kuali.student.deployment.monitor.jmx;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.kuali.student.deployment.monitor.http.HttpSessionRequestHistory;
import org.kuali.student.deployment.monitor.http.HttpSessionRequestHistory.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs session request history as a CSV file suitable for viewing 
 * in Numbers, Open Office Calc, and other spread sheet apps 
 * @author randy
 *
 */
public class SessionRequestHistoryLogger extends AbstractInstrumentationLogger {

    //~ Static fields/initializers -------------------------------------------------------

    static final Logger logger =
        LoggerFactory.getLogger(SessionRequestHistoryLogger.class);

    // same location as config overrides

    static final String LOG_DIR_BASE = "ks-session-history-log";

    static String LOG_FILE_BASE_NAME = "session";
    static final String UNDERSCORE = "________";
    static final String MIME_TYPE = "csv";
    static final String VS = ",";

    //~ Instance fields ------------------------------------------------------------------

    /**
     * Creates a new SessionRequestHistoryLogger object.
     */
    public SessionRequestHistoryLogger() {
    	super(LOG_DIR_BASE, LOG_FILE_BASE_NAME, MIME_TYPE);
    }

    //~ Methods --------------------------------------------------------------------------

	
    public void log(String sessionId, List<HttpSessionRequestHistory.RequestInfo> history) {
        createDailyLogDirIfNeeded();
        logger.info("logging history for session: " + sessionId );
        if (history == null) {
        	logger.warn("no history found for: " + sessionId);
            return;
        }
        logger.info(" session request count: " + history.size());
        File logFile = createLogFile();
        logger.info("logfile is: " + logFile.getAbsolutePath());
        doLogWrite(logFile, sessionId, history);
    }

    private void doLogWrite(File logFile, String sessionId,
			List<RequestInfo> history) {
    	PrintWriter writer = createLogFilePrintWriter(logFile);
    	writeHeader(writer, sessionId, history);
    	dumpHistory(writer, history);
    	 writer.flush();
         writer.close();
         	}

	private void dumpHistory(PrintWriter writer, List<RequestInfo> history) {
		printTitle(writer, "Request Details");
		writer.println(RequestInfo.tableHeaderCSV);
		for( RequestInfo ri : history) {
			writer.println(ri.toStringCSV());
		}
		
	}

	private void writeHeader(PrintWriter writer, String sessionId,
			List<RequestInfo> history) {
		RequestInfo first = history.get(0);
		RequestInfo last = history.get(history.size() - 1);
		String startTime = first.startTimeFormatted;
		String endTime = last.startTimeFormatted;
		printTitle(writer, "Session Info");
		
		writer.print("    Session Start " + VS);
		writer.print("    Session End: " + VS);
		writer.println("    Total Requests ");
		writer.print(startTime + VS);
		writer.print(endTime + VS);
		writer.println(history.size());
		
	}
	private void printTitle(PrintWriter writer, String sectionName) {
		writer.print(UNDERSCORE + ", "+ UNDERSCORE);
		writer.print(sectionName + VS);
		writer.println(UNDERSCORE + ", "+ UNDERSCORE);
		writer.println("");
	}
}
