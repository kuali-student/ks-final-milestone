package org.kuali.student.core.search.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class JoinComparisonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	public enum ComparisonType {EQUALS,NOTEQUALS,LESSTHAN,GREATERTHAN,LESSTHANEQUALS,GREATERTHANEQUALS};
	
	private ComparisonType type;
	private ComparisonParamInfo leftHandSide;
	private ComparisonParamInfo rightHandSide;
	public ComparisonType getType() {
		return type;
	}
	public void setType(ComparisonType type) {
		this.type = type;
	}
	public ComparisonParamInfo getLeftHandSide() {
		return leftHandSide;
	}
	public void setLeftHandSide(ComparisonParamInfo leftHandSide) {
		this.leftHandSide = leftHandSide;
	}
	public ComparisonParamInfo getRightHandSide() {
		return rightHandSide;
	}
	public void setRightHandSide(ComparisonParamInfo rightHandSide) {
		this.rightHandSide = rightHandSide;
	}
	
}
