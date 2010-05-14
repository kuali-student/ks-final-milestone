package org.kuali.student.rules.internal.common.runtime.ast;

public class BooleanFunctionResult {
	private String booleanFunction;
	private Boolean result;
	private String message;

	public BooleanFunctionResult(String booleanFunction, Boolean result, String message) {
		this.result = result;
		this.message = message;
		this.booleanFunction = booleanFunction;
	}

	public Boolean getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public String getBooleanFunction() {
		return booleanFunction;
	}
	
}
