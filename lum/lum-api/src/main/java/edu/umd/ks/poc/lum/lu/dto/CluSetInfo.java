package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluSetInfo implements Serializable {

	private static final long serialVersionUID = 2530552051039895161L;

	@XmlElement
	private String cluSetId;

	@XmlElement
	private String cluSetName;

	@XmlElement
	private String description;

	@XmlElement
	private Date effectiveStartDate;

	@XmlElement
	private Date effectiveEndDate;

	@XmlElement(name = "cluSetDisplay")
	@XmlElementWrapper(name = "cluSetDisplayList")
	private List<CluSetDisplay> cluSetDisplayList;

	@XmlElement(name = "cluDisplay")
	@XmlElementWrapper(name = "cluDisplayList")
	private List<CluDisplay> cluDisplayList;

	@XmlElement
	private CluCriteria cluCriteria;

	/**
	 * @return the cluSetId
	 */
	public String getCluSetId() {
		return cluSetId;
	}

	/**
	 * @param cluSetId
	 *            the cluSetId to set
	 */
	public void setCluSetId(String cluSetId) {
		this.cluSetId = cluSetId;
	}

	/**
	 * @return the cluSetName
	 */
	public String getCluSetName() {
		return cluSetName;
	}

	/**
	 * @param cluSetName
	 *            the cluSetName to set
	 */
	public void setCluSetName(String cluSetName) {
		this.cluSetName = cluSetName;
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
	 * @return the cluSetDisplayList
	 */
	public List<CluSetDisplay> getCluSetDisplayList() {
		if (cluSetDisplayList == null) {
			cluSetDisplayList = new ArrayList<CluSetDisplay>();
		}
		return cluSetDisplayList;
	}

	/**
	 * @return the cluDisplayList
	 */
	public List<CluDisplay> getCluDisplayList() {
		if (cluDisplayList == null) {
			cluDisplayList = new ArrayList<CluDisplay>();
		}
		return cluDisplayList;
	}

	/**
	 * @return the cluCriteria
	 */
	public CluCriteria getCluCriteria() {
		return cluCriteria;
	}

	/**
	 * @param cluCriteria
	 *            the cluCriteria to set
	 */
	public void setCluCriteria(CluCriteria cluCriteria) {
		this.cluCriteria = cluCriteria;
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
				+ ((cluCriteria == null) ? 0 : cluCriteria.hashCode());
		result = prime * result
				+ ((cluDisplayList == null) ? 0 : cluDisplayList.hashCode());
		result = prime
				* result
				+ ((cluSetDisplayList == null) ? 0 : cluSetDisplayList
						.hashCode());
		result = prime * result
				+ ((cluSetId == null) ? 0 : cluSetId.hashCode());
		result = prime * result
				+ ((cluSetName == null) ? 0 : cluSetName.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
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
		final CluSetInfo other = (CluSetInfo) obj;
		if (cluCriteria == null) {
			if (other.cluCriteria != null)
				return false;
		} else if (!cluCriteria.equals(other.cluCriteria))
			return false;
		if (cluDisplayList == null) {
			if (other.cluDisplayList != null)
				return false;
		} else if (!cluDisplayList.equals(other.cluDisplayList))
			return false;
		if (cluSetDisplayList == null) {
			if (other.cluSetDisplayList != null)
				return false;
		} else if (!cluSetDisplayList.equals(other.cluSetDisplayList))
			return false;
		if (cluSetId == null) {
			if (other.cluSetId != null)
				return false;
		} else if (!cluSetId.equals(other.cluSetId))
			return false;
		if (cluSetName == null) {
			if (other.cluSetName != null)
				return false;
		} else if (!cluSetName.equals(other.cluSetName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
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
