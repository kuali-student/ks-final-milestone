package org.kuali.student.core.dictionary.newmodel.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class CaseConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "when")
    protected TypeStateWhenConstraint whenConstraint;

    @XmlAttribute
    protected String field;
    
    @XmlAttribute
    protected String operator;
    
	/**
	 * @return the whenConstraint
	 */
	public TypeStateWhenConstraint getWhenConstraint() {
		return whenConstraint;
	}

	/**
	 * @param whenConstraint the whenConstraint to set
	 */
	public void setWhenConstraint(TypeStateWhenConstraint whenConstraint) {
		this.whenConstraint = whenConstraint;
	}
}
