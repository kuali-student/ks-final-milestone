package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;



@XmlAccessorType(XmlAccessType.FIELD)
public class ConstraintDescriptor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute(required = true)
    protected String key;
    
    @XmlAttribute
    protected String id;
    
    @XmlElement(required = true) 
    protected List<ConstraintSelector> constraint;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the constraint
	 */
	public List<ConstraintSelector> getConstraint() {
		return constraint;
	}

	/**
	 * @param constraint the constraint to set
	 */
	public void setConstraint(List<ConstraintSelector> constraint) {
		this.constraint = constraint;
	}
}
