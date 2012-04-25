/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores log messages, can be sent to log service.
 */
@Deprecated
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
