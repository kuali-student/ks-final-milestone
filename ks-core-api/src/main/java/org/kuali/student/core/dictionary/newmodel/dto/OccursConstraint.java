package org.kuali.student.core.dictionary.newmodel.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class OccursConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<RequireConstraint> require;

    @XmlAttribute
    private Integer min;
    
    @XmlAttribute
    private Integer max;
    
	/**
	 * @return the require
	 */
	public List<RequireConstraint> getRequire() {
		return require;
	}

	/**
	 * @param require the require to set
	 */
	public void setRequire(List<RequireConstraint> require) {
		this.require = require;
	}

	/**
	 * @return the min
	 */
	public Integer getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(Integer min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}	
}
