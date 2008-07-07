package org.kuali.student.commons.ui.logging.client;

import java.io.Serializable;


/**
 * Bean representing a logged message.
 * Used internally by the LogService and LogBufer.
 *
 */
public class LogMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	LogLevel logLevel;
	String message;
	Throwable error;
	
	public LogMessage() {
		
	}
	public LogMessage(LogLevel level, String message, Throwable error) {
		this.logLevel = level;
		this.message = message;
		this.error = error;
	}
	
	public LogLevel getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Throwable getError() {
		return error;
	}
	public void setError(Throwable error) {
		this.error = error;
	}
	
}
