package org.kuali.student.commons.ui.logging.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores log messages, can be sent to log service.
 */
public class LogBuffer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int maxSize = Integer.MAX_VALUE;
	List<LogMessage> buffer = new ArrayList<LogMessage>();
	
	/**
	 * Creates an empty LogBuffer with a size limit of Integer.MAX_VALUE
	 */
	public LogBuffer() {
		
	}
	
	/**
	 * Creates an empty LogBuffer with the specified size limit.
	 * When size limit is reached, older messages are removed as newer ones are added.
	 * @param maxSize
	 */
	public LogBuffer(int maxSize) {
		this.maxSize = maxSize;
	}
	
	/**
	 * Adds a message to the buffer
	 * @param message
	 */
	public void add(LogMessage message) {
		buffer.add(message);
		checkLimit();
	}
	
	/**
	 * Returns the underlying log buffer.
	 * @return the underlying log buffer as List<LogMessage> 
	 */
	public List<LogMessage> getLogMessages() {
		return buffer;
	}
	/**
	 * Removes excess messages, oldest first.
	 */
	private void checkLimit() {
		while (buffer.size() > maxSize) {
			buffer.remove(0);
		}
	}
}
