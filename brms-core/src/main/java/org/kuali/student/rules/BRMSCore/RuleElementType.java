package org.kuali.student.rules.BRMSCore;

public enum RuleElementType {
	AND_TYPE ("AND", "1"),
	OR_TYPE ("OR", "2"),
	XOR_TYPE ("XOR", "3"),
	NOT_TYPE ("NOT", "4"),
	LPAREN_TYPE ("LPAREN", "5"),
	RPAREN_TYPE ("RPAREN", "6"),
	PROPOSITION_TYPE ("PROPOSITION", "7");
	
	private String name;
	private String id;
	
	/**
	 * 
	 */
	private RuleElementType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the validateTypeId
	 */
	public String getRuleElementTypeId() {
		return id;
	}
}
