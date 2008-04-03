package org.kuali.student.rules.BRMSCore;

public enum ComparisonOperatorType {

	EQUAL_TO_TYPE ("EQUAL_TO", "1"),
	NOT_EQUAL_TO_TYPE ("NOT_EQUAL_TO", "2"),
	GREATER_THAN_TYPE ("GREATER_THAN", "3"),
	LESS_THAN_TYPE ("LESS_THAN", "4"),
	GREATER_THAN_OR_EQUAL_TO_TYPE ("GREATER_THAN_OR_EQUAL_TO", "5"),
	LESS_THAN_OR_EQUAL_TO_TYPE ("LESS_THAN_OR_EQUAL_TO", "6");
	
	private String name;
	private String id;
	
	/**
	 * 
	 */
	private ComparisonOperatorType(String name, String id) {
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
