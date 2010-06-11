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

package org.kuali.student.brms.util;

public class LoggingStringBuilder implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    private int counter = 0;
	private StringBuilder stringBuilder = new StringBuilder();
	private String format = "%1$6d: %2$s";
	
	public LoggingStringBuilder() {
	}
	
	public LoggingStringBuilder(String format) {
		this.format = format;
	}

	public StringBuilder append(String s) {
		String f = String.format(format, ++counter, s);
		this.stringBuilder.append(f);
		this.stringBuilder.append("\n");
		return stringBuilder;
	}
	
	public void trimToSize() {
		this.stringBuilder.trimToSize();
	}

	public StringBuilder getStringBuilder() {
		return this.stringBuilder;
	}
	
	public String toString() {
		return this.stringBuilder.toString();
	}
}
