package org.kuali.student.rules.ruleexecution.runtime.drools.util;

public class Message {
	private String message = null;

	public Message() {}
	
	public Message(String message) { this.message = message; }

	public String getMessage() { return this.message; }
	public void setMessage(String message) { this.message = message; }
}
