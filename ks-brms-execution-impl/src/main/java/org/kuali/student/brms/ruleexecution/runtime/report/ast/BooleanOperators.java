package org.kuali.student.brms.ruleexecution.runtime.report.ast;

public class BooleanOperators {
	private String andOperator = "AND";
	private String orOperator = "OR";

	public BooleanOperators() {
	}
	
	public BooleanOperators(String and, String or) {
		this.andOperator = and;
		this.orOperator = or;
	}

	public String getAndOperator() {
		return this.andOperator;
	}

	public String getOrOperator() {
		return this.orOperator;
	}
	
	public String toString() {
		return "andOperator=" + this.andOperator + ", orOperator=" + this.orOperator;
	}
}
