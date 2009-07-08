package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;



@XmlAccessorType(XmlAccessType.FIELD)
public class WhenConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    protected String value;
    
    @XmlElement
    protected ConstraintSelector constraint;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the constraint
	 */
	public ConstraintSelector getConstraint() {
		return constraint;
	}

	/**
	 * @param constraint the constraint to set
	 */
	public void setConstraint(ConstraintSelector constraint) {
		this.constraint = constraint;
	}    
}
