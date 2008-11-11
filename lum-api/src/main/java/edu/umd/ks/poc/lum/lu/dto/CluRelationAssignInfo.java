package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluRelationAssignInfo implements Serializable {

	private static final long serialVersionUID = 4764838663537447147L;

	@XmlElement
	private Date effectiveStartDate;
	@XmlElement
	private Date effectiveEndDate;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((effectiveEndDate == null) ? 0 : effectiveEndDate.hashCode());
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
		final CluRelationAssignInfo other = (CluRelationAssignInfo) obj;
		if (effectiveEndDate == null) {
			if (other.effectiveEndDate != null)
				return false;
		} else if (!effectiveEndDate.equals(other.effectiveEndDate))
			return false;
		if (effectiveStartDate == null) {
			if (other.effectiveStartDate != null)
				return false;
		} else if (!effectiveStartDate.equals(other.effectiveStartDate))
			return false;
		return true;
	}
}
