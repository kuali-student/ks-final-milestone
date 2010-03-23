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
