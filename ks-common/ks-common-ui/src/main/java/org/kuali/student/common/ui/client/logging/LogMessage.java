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
