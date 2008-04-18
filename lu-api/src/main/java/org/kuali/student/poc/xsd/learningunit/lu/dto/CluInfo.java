package org.kuali.student.poc.xsd.learningunit.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluInfo implements Serializable {

	private static final long serialVersionUID = 8378784724612312877L;

	@XmlAttribute
	private String cluId;
	@XmlElement
	private String cluCategory;
	@XmlElement
	private String description;
	@XmlElement
	private String luTypeId;
	@XmlElement
	private String cluShortTitle;
	@XmlElement
	private String cluCode;
	@XmlElement
	private AtpDisplay atpDisplayStart;
	@XmlElement
	private AtpDisplay atpDisplayEnd;
	@XmlElement
	private Long learningResultType;
	/**
	 * @return the cluId
	 */
	public String getCluId() {
		return cluId;
	}
	/**
	 * @param cluId the cluId to set
	 */
	public void setCluId(String cluId) {
		this.cluId = cluId;
	}
	/**
	 * @return the cluCategory
	 */
	public String getCluCategory() {
		return cluCategory;
	}
	/**
	 * @param cluCategory the cluCategory to set
	 */
	public void setCluCategory(String cluCategory) {
		this.cluCategory = cluCategory;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the luTypeId
	 */
	public String getLuTypeId() {
		return luTypeId;
	}
	/**
	 * @param luTypeId the luTypeId to set
	 */
	public void setLuTypeId(String luTypeId) {
		this.luTypeId = luTypeId;
	}
	/**
	 * @return the cluShortTitle
	 */
	public String getCluShortTitle() {
		return cluShortTitle;
	}
	/**
	 * @param cluShortTitle the cluShortTitle to set
	 */
	public void setCluShortTitle(String cluShortTitle) {
		this.cluShortTitle = cluShortTitle;
	}
	/**
	 * @return the cluCode
	 */
	public String getCluCode() {
		return cluCode;
	}
	/**
	 * @param cluCode the cluCode to set
	 */
	public void setCluCode(String cluCode) {
		this.cluCode = cluCode;
	}
	/**
	 * @return the atpDisplayStart
	 */
	public AtpDisplay getAtpDisplayStart() {
		return atpDisplayStart;
	}
	/**
	 * @param atpDisplayStart the atpDisplayStart to set
	 */
	public void setAtpDisplayStart(AtpDisplay atpDisplayStart) {
		this.atpDisplayStart = atpDisplayStart;
	}
	/**
	 * @return the atpDisplayEnd
	 */
	public AtpDisplay getAtpDisplayEnd() {
		return atpDisplayEnd;
	}
	/**
	 * @param atpDisplayEnd the atpDisplayEnd to set
	 */
	public void setAtpDisplayEnd(AtpDisplay atpDisplayEnd) {
		this.atpDisplayEnd = atpDisplayEnd;
	}
	/**
	 * @return the learningResultType
	 */
	public Long getLearningResultType() {
		return learningResultType;
	}
	/**
	 * @param learningResultType the learningResultType to set
	 */
	public void setLearningResultType(Long learningResultType) {
		this.learningResultType = learningResultType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atpDisplayEnd == null) ? 0 : atpDisplayEnd.hashCode());
		result = prime * result
				+ ((atpDisplayStart == null) ? 0 : atpDisplayStart.hashCode());
		result = prime * result
				+ ((cluCategory == null) ? 0 : cluCategory.hashCode());
		result = prime * result + ((cluCode == null) ? 0 : cluCode.hashCode());
		result = prime * result + ((cluId == null) ? 0 : cluId.hashCode());
		result = prime * result
				+ ((cluShortTitle == null) ? 0 : cluShortTitle.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((learningResultType == null) ? 0 : learningResultType
						.hashCode());
		result = prime * result
				+ ((luTypeId == null) ? 0 : luTypeId.hashCode());
		return result;
	}
	/* (non-Javadoc)
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
		final CluInfo other = (CluInfo) obj;
		if (atpDisplayEnd == null) {
			if (other.atpDisplayEnd != null)
				return false;
		} else if (!atpDisplayEnd.equals(other.atpDisplayEnd))
			return false;
		if (atpDisplayStart == null) {
			if (other.atpDisplayStart != null)
				return false;
		} else if (!atpDisplayStart.equals(other.atpDisplayStart))
			return false;
		if (cluCategory == null) {
			if (other.cluCategory != null)
				return false;
		} else if (!cluCategory.equals(other.cluCategory))
			return false;
		if (cluCode == null) {
			if (other.cluCode != null)
				return false;
		} else if (!cluCode.equals(other.cluCode))
			return false;
		if (cluId == null) {
			if (other.cluId != null)
				return false;
		} else if (!cluId.equals(other.cluId))
			return false;
		if (cluShortTitle == null) {
			if (other.cluShortTitle != null)
				return false;
		} else if (!cluShortTitle.equals(other.cluShortTitle))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (learningResultType == null) {
			if (other.learningResultType != null)
				return false;
		} else if (!learningResultType.equals(other.learningResultType))
			return false;
		if (luTypeId == null) {
			if (other.luTypeId != null)
				return false;
		} else if (!luTypeId.equals(other.luTypeId))
			return false;
		return true;
	}

}
