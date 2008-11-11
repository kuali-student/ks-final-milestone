package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluSetCreateInfo implements Serializable {

	private static final long serialVersionUID = -3584826854621419393L;

	@XmlElement
	private String description;
	@XmlElement
	private Date effectiveStartDate;
	@XmlElement
	private Date effectiveEndDate;
	@XmlElement
	private String effectiveStartCycle;
	@XmlElement
	private String effectiveEndCycle;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the effectiveStartDate
	 */
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	/**
	 * @param effectiveStartDate
	 *            the effectiveStartDate to set
	 */
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	/**
	 * @return the effectiveEndDate
	 */
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	/**
	 * @param effectiveEndDate
	 *            the effectiveEndDate to set
	 */
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	/**
	 * @return the effectiveStartCycle
	 */
	public String getEffectiveStartCycle() {
		return effectiveStartCycle;
	}

	/**
	 * @param effectiveStartCycle
	 *            the effectiveStartCycle to set
	 */
	public void setEffectiveStartCycle(String effectiveStartCycle) {
		this.effectiveStartCycle = effectiveStartCycle;
	}

	/**
	 * @return the effectiveEndCycle
	 */
	public String getEffectiveEndCycle() {
		return effectiveEndCycle;
	}

	/**
	 * @param effectiveEndCycle
	 *            the effectiveEndCycle to set
	 */
	public void setEffectiveEndCycle(String effectiveEndCycle) {
		this.effectiveEndCycle = effectiveEndCycle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((effectiveEndCycle == null) ? 0 : effectiveEndCycle
						.hashCode());
		result = prime
				* result
				+ ((effectiveEndDate == null) ? 0 : effectiveEndDate.hashCode());
		result = prime
				* result
				+ ((effectiveStartCycle == null) ? 0 : effectiveStartCycle
						.hashCode());
		result = prime
				* result
				+ ((effectiveStartDate == null) ? 0 : effectiveStartDate
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CluSetCreateInfo other = (CluSetCreateInfo) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (effectiveEndCycle == null) {
			if (other.effectiveEndCycle != null)
				return false;
		} else if (!effectiveEndCycle.equals(other.effectiveEndCycle))
			return false;
		if (effectiveEndDate == null) {
			if (other.effectiveEndDate != null)
				return false;
		} else if (!effectiveEndDate.equals(other.effectiveEndDate))
			return false;
		if (effectiveStartCycle == null) {
			if (other.effectiveStartCycle != null)
				return false;
		} else if (!effectiveStartCycle.equals(other.effectiveStartCycle))
			return false;
		if (effectiveStartDate == null) {
			if (other.effectiveStartDate != null)
				return false;
		} else if (!effectiveStartDate.equals(other.effectiveStartDate))
			return false;
		return true;
	}

}
