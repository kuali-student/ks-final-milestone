package org.kuali.student.deployment.monitor.jmx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractInstrumentationLogger {


    static final Logger logger =
        LoggerFactory.getLogger(RequestThreadInfoLogger.class);
    
	protected static final String PARENT_PATH = System.getProperty("user.home") + "/" + "kuali";
	int dayOfYear = 0;
	String dailyLogPath;
	int requestNumber = 0;
	String logDirBase;
	String logFileBaseName;
	String logDirPath;
	String mimeType;
	

	public AbstractInstrumentationLogger(String logDirBase,
			String logFileBaseName, String mimeType) {
		super();
		this.logDirBase = logDirBase;
		this.logFileBaseName = logFileBaseName;
		this.mimeType = mimeType;
		logDirPath = PARENT_PATH + "/" + this.logDirBase;
		init();
	}


	/**
	 * @return the log file
	 */
	protected File createLogFile() {
	    String fileName = logFileName();
	    File logFile = new File(dailyLogPath, fileName);
	
	    try {
	        logFile.createNewFile();
	    } catch (IOException e) {
	        logger.error(e.getLocalizedMessage());
	        throw new IllegalStateException(e.getLocalizedMessage());
	    }
	
	    return logFile;
	}

	/**
	 * @param logFile
	 * @return
	 */
	protected PrintWriter createLogFilePrintWriter(File logFile) {
	    PrintWriter writer = null;
	
	    try {
	        writer = new PrintWriter(logFile);
	    } catch (FileNotFoundException e) {
	        logger.error(e.getLocalizedMessage());
	        throw new IllegalStateException(e.getLocalizedMessage());
	    }
	
	    return writer;
	}

	/**
	 * for testing purposes
	 * @return the dayOfYear
	 */
	public final int getDayOfYear() {
	    return dayOfYear;
	}

	/**
	 * For testing purpose
	 * @param dayOfYear the dayOfYear to set
	 */
	public final void setDayOfYear(int dayOfYear) {
	    this.dayOfYear = dayOfYear;
	}

	/**
	 * Creates a new daily log directory as required
	 */
	public void createDailyLogDirIfNeeded() {
	    Calendar cal = Calendar.getInstance();
	    boolean newDirNeeded = checkDayRollover(cal);
	
	    if (newDirNeeded) {
	        createDailyLogDir(cal);
	    }
	}

	protected void init() {
	    File baseDir = new File(PARENT_PATH, logDirBase);
	
	    try {
	        createDirectory(baseDir);
	    } catch (IOException e) {
	        logger.error(e.getLocalizedMessage());
	    }
	    // create daily log dir
	
	    createDailyLogDirIfNeeded();
	}

	/**
	 * @param cal
	 */
	private void createDailyLogDir(Calendar cal) {
	    String dailyLogDirName = dailyLogDirName(cal);
	    File baseDir = new File(logDirPath, dailyLogDirName);
	
	    try {
	        createDirectory(baseDir);
	    } catch (IOException e) {
	        logger.error(e.getLocalizedMessage());
	    }
	
	    dailyLogPath = baseDir.getAbsolutePath();
	    logger.info("new daily log dir:  " + dailyLogPath);
	}

	/**
	 * @return true if day rolled over
	 */
	private boolean checkDayRollover(Calendar cal) {
	    int doy = cal.get(Calendar.DAY_OF_YEAR);
	
	    if (doy == dayOfYear) {
	        return false;
	    }
	
	    dayOfYear = doy;
	    requestNumber = 0;
	
	    return true;
	}

	private void createDirectory(File baseDir) throws IOException {
	    boolean dirOK = checkDirectory(baseDir);
	
	    if (dirOK) {
	        return;
	    }
	
	    try {
			baseDir.mkdir();
			baseDir.createNewFile();
			dirOK = checkDirectory(baseDir);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
			throw new IllegalStateException(e.getLocalizedMessage());
		}
	}

	/**
	 * @todo DOCUMENT ME!
	 *
	 * @param baseDir DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public boolean checkDirectory(File baseDir) throws IOException {
	    boolean exists = baseDir.exists();
	
	    if (exists && !baseDir.isDirectory()) {
	        throw new IOException(baseDir + "exists but is not a directory");
	    }
	
	    if (exists && !baseDir.canWrite()) {
	        throw new IOException(baseDir + "  not writeable");
	    }
	
	    return exists;
	}

	private String dailyLogDirName(Calendar cal) {
	    String rval = null;
	    int year = cal.get(java.util.Calendar.YEAR);
	
	    // This is a calendar-specific value. The first month of the year in
	    // the Gregorian and Julian calendars is JANUARY which is 0;
	    // the last depends on the number of months in a year.
	    int month = 1 + cal.get(java.util.Calendar.MONTH);
	    int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
	
	    rval = String.format("%d-%02d-%02d", year, month, day);
	
	    return rval;
	}

	private String logFileName() {
	    return String.format( "%s-%05d.%s", logFileBaseName, ++requestNumber,
	    		mimeType);
	}

}