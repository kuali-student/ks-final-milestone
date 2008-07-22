package org.kuali.student.commons.ui.logging.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Logger {
	/**
	 * Default log level is LogLevel.WARN
	 */
	static LogLevel logLevel = LogLevel.WARN;
	static LogBuffer buffer = new LogBuffer();
	static Map<String, String> clientContextInfo = new HashMap<String, String>();
	static int maxBufferSize = Integer.MAX_VALUE;
	/**
	 * Sets the maximum buffer size, resizing buffer if necessary.
	 * Can be an expensive operation, avoid frequent use.
	 * @param maxSize
	 */
	public static void setMaxBufferSize(int maxSize) {
		LogBuffer tmp = new LogBuffer(maxSize);
		for (LogMessage m : buffer.getLogMessages()) {
			tmp.add(m);
		}
		buffer = tmp;
	}
	
	public static void setLogLevel(LogLevel level) {
		logLevel = level;
	}
	public static LogLevel getLogLevel() {
		return logLevel;
	}
	public static void log(LogMessage message) {
		if (message.getLogLevel().getLevel() >= logLevel.getLevel()) {
			buffer.add(message);
		}
	}
	
	public static void log(LogLevel level, String message) {
		if (level.getLevel() >= logLevel.getLevel()) {		
			buffer.add(new LogMessage(level, message, (Throwable) null));
		}
	}
	
	public static void log(LogLevel level, String message, Throwable error) {
		if (level.getLevel() >= logLevel.getLevel()) {		
			buffer.add(new LogMessage(level, message, error));
		}
	}
	
	public static void debug(String message) {
		log(LogLevel.DEBUG, message);
	}
	public static void debug(String message, Throwable error) {
		log(LogLevel.DEBUG, message, error);
	}
	
	public static void info(String message) {
		log(LogLevel.INFO, message);
	}
	public static void info(String message, Throwable error) {
		log(LogLevel.INFO, message, error);
	}
	
	public static void warn(String message) {
		log(LogLevel.WARN, message);
	}
	public static void warn(String message, Throwable error) {
		log(LogLevel.WARN, message, error);
	}
	
	public static void error(String message) {
		log(LogLevel.ERROR, message);
	}
	public static void error(String message, Throwable error) {
		log(LogLevel.ERROR, message, error);
	}
	
	public static void fatal(String message) {
		log(LogLevel.FATAL, message);
	}
	public static void fatal(String message, Throwable error) {
		log(LogLevel.FATAL, message, error);
	}
	
	public static void reset() {
		buffer = new LogBuffer(maxBufferSize);
	}
	
	
	
	public static Map<String, String> getClientContextInfo() {
        return clientContextInfo;
    }

    public static void sendLogs() {
		final List<LogMessage> messages = new ArrayList<LogMessage>(buffer.getLogMessages());
		final Map<String, String> context = new HashMap<String, String>(clientContextInfo);
		reset();
		DeferredCommand.addCommand(new Command() {
			public void execute() {
			    String log = formatLog(messages);
				LogService.Util.getInstance().sendLog(context, log, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						throw new LogFailedException(caught);
					}

					public void onSuccess(Boolean result) {
						// do nothing
					}
				});
			}
		});
	}
	
    private static String formatLog(List<LogMessage> messages) {
        StringBuilder s = new StringBuilder();
        for (LogMessage lm : messages) {
            s.append(lm.getLogLevel().toString());
            s.append(":\t");
            s.append(lm.getMessage());
            Throwable t = lm.getError();
            if (t != null) {
                appendStackTrace(t, s);
            }
        }
        return s.toString();
    }
    
    private static void appendStackTrace(Throwable t, StringBuilder s) {
        s.append(t.toString());
        s.append(": at\n");
        StackTraceElement[] stack = t.getStackTrace();
        for (StackTraceElement frame : stack) {
            s.append(frame.toString());
            s.append("\n");
        }
    }
}
