package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;



@XmlAccessorType(XmlAccessType.FIELD)
public class CaseConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "when")
    protected List<WhenConstraint> whenConstraint;

    @XmlAttribute
    protected String field;
    
    @XmlAttribute
    protected String operator;
    

	/**
	 * @return the whenConstraint
	 */
	public List<WhenConstraint> getWhenConstraint() {
		return whenConstraint;
	}

	/**
	 * @param whenConstraint the whenConstraint to set
	 */
	public void setWhenConstraint(List<WhenConstraint> whenConstraint) {
		this.whenConstraint = whenConstraint;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}	
}
