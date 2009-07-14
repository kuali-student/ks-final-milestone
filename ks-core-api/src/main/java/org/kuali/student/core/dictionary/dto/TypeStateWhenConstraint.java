package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class TypeStateWhenConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    protected String type;
    
    @XmlAttribute
    protected String state;
    
    @XmlElement
    protected Integer minOccurs;
    
    @XmlElement
    protected String maxOccurs;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the minOccurs
	 */
	public Integer getMinOccurs() {
		return minOccurs;
	}

	/**
	 * @param minOccurs the minOccurs to set
	 */
	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}

	/**
	 * @return the maxOccurs
	 */
	public String getMaxOccurs() {
		return maxOccurs;
	}

	/**
	 * @param maxOccurs the maxOccurs to set
	 */
	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}        
}
