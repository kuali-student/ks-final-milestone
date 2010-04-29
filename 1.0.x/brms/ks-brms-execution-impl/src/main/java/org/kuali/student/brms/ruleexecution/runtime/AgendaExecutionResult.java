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

import java.util.ArrayList;
import java.util.List;

public class AgendaExecutionResult {
	private String id;
	private Boolean executionResult = Boolean.FALSE;
	private List<ExecutionResult> executionResultList = new ArrayList<ExecutionResult>();

	public AgendaExecutionResult() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Boolean getExecutionResult() {
		return executionResult;
	}

	public void setExecutionResult(Boolean executionResult) {
		this.executionResult = executionResult;
	}

	public void addExecutionResult(ExecutionResult executionResult) {
		this.executionResultList.add(executionResult);
	}
	
	public List<ExecutionResult> getExecutionResultList() {
		return this.executionResultList;
	}

	public void setExecutionResultList(List<ExecutionResult> executionResultList) {
		this.executionResultList = executionResultList;
	}

}
