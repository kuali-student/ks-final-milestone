package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.umd.ks.poc.lum.util.adapter.JaxbAttributeListMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluCreateInfo implements Serializable {

	private static final long serialVersionUID = 7372017067791199847L;

	@XmlElement
	private String cluLongName;
	@XmlElement
	private String cluShortName;
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
	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeListMapListAdapter.class)
	private Map<String, List<String>> attributes;
	@XmlElement
	private String status;
	@XmlElement
	private String adminDepartment;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the cluLongName
	 */
	public String getCluLongName() {
		return cluLongName;
	}

	/**
	 * @param cluLongName
	 *            the cluLongName to set
	 */
	public void setCluLongName(String cluLongName) {
		this.cluLongName = cluLongName;
	}

	/**
	 * @return the cluShortName
	 */
	public String getCluShortName() {
		return cluShortName;
	}

	/**
	 * @param cluShortName
	 *            the cluShortName to set
	 */
	public void setCluShortName(String cluShortName) {
		this.cluShortName = cluShortName;
	}

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

	/**
	 * @return the attributes
	 */
	public Map<String, List<String>> getAttributes() {
		if (attributes == null) {
			attributes = new HashMap<String, List<String>>();
		}
		return attributes;
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
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result
				+ ((cluLongName == null) ? 0 : cluLongName.hashCode());
		result = prime * result
				+ ((cluShortName == null) ? 0 : cluShortName.hashCode());
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
		final CluCreateInfo other = (CluCreateInfo) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (cluLongName == null) {
			if (other.cluLongName != null)
				return false;
		} else if (!cluLongName.equals(other.cluLongName))
			return false;
		if (cluShortName == null) {
			if (other.cluShortName != null)
				return false;
		} else if (!cluShortName.equals(other.cluShortName))
			return false;
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

	/**
	 * @return the adminDepartment
	 */
	public String getAdminDepartment() {
		return adminDepartment;
	}

	/**
	 * @param adminDepartment the adminDepartment to set
	 */
	public void setAdminDepartment(String adminDepartment) {
		this.adminDepartment = adminDepartment;
	}

}
