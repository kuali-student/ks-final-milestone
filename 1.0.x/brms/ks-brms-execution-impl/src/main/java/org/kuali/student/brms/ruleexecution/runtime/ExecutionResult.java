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

package org.kuali.student.brms.ruleexecution.runtime;

import java.util.List;

public class ExecutionResult {
	
	private String id;
	private Boolean executionResult = Boolean.FALSE;
	private List<Object> resultList;
	private String executionLog;
	private String errorMessage;
	
	public ExecutionResult() {
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setExecutionLog(final String log) {
		this.executionLog = log;
	}

	public String getExecutionLog() {
		return this.executionLog;
	}

	public List<Object> getResults() {
		return resultList;
	}

	public void setResults(final List<Object> resultList) {
		this.resultList = resultList;
	}

	public Boolean getExecutionResult() {
		return executionResult;
	}

	public void setExecutionResult(final Boolean executionResult) {
		this.executionResult = executionResult;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String toString() {
		return "ExecutionResult[id="+this.id+
			", executionResult=" + this.executionResult + "]";
 	}
}
