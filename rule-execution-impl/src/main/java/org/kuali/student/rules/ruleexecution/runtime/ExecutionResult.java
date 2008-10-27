package org.kuali.student.rules.ruleexecution.runtime;

import java.util.List;

public class ExecutionResult {
	
	private List<Object> resultList;
	private String log;
	
	public ExecutionResult(final List<Object> resultList) {
		this.resultList = resultList;
	}

	public ExecutionResult(final List<Object> resultList, final String log) {
		this.resultList = resultList;
		this.log = log;
	}

	public void setExecutionLog(final String log) {
		this.log = log;
	}
	
	public String getLog() {
		return this.log;
	}

	public List<Object> getResults() {
		return resultList;
	}

	public void setResults(final List<Object> resultList) {
		this.resultList = resultList;
	}
	
	
}
