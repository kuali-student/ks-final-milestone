package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluRelationInfo implements Serializable {

	private static final long serialVersionUID = -3192407399272685031L;

	@XmlElement
	private CluDisplay cluDisplay;
	@XmlElement
	private CluDisplay relatedCluDisplay;
	@XmlElement
	private String luRelationTypeId;
	@XmlElement
	private Date effectiveStartDate;
	@XmlElement
	private Date effectiveEndDate;
	@XmlElement
	private Date createTime;
	@XmlElement
	private String createUserId;
	@XmlElement
	private String createUserComment;
	@XmlElement
	private Date updateTime;
	@XmlElement
	private String updateUserId;
	@XmlElement
	private String updateUserComment;

	/**
	 * @return the cluDisplay
	 */
	public CluDisplay getCluDisplay() {
		return cluDisplay;
	}

	/**
	 * @param cluDisplay
	 *            the cluDisplay to set
	 */
	public void setCluDisplay(CluDisplay cluDisplay) {
		this.cluDisplay = cluDisplay;
	}

	/**
	 * @return the relatedCluDisplay
	 */
	public CluDisplay getRelatedCluDisplay() {
		return relatedCluDisplay;
	}

	/**
	 * @param relatedCluDisplay
	 *            the relatedCluDisplay to set
	 */
	public void setRelatedCluDisplay(CluDisplay relatedCluDisplay) {
		this.relatedCluDisplay = relatedCluDisplay;
	}

	/**
	 * @return the luRelationTypeId
	 */
	public String getLuRelationTypeId() {
		return luRelationTypeId;
	}

	/**
	 * @param luRelationTypeId
	 *            the luRelationTypeId to set
	 */
	public void setLuRelationTypeId(String luRelationTypeId) {
		this.luRelationTypeId = luRelationTypeId;
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUserId
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId
	 *            the createUserId to set
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the createUserComment
	 */
	public String getCreateUserComment() {
		return createUserComment;
	}

	/**
	 * @param createUserComment
	 *            the createUserComment to set
	 */
	public void setCreateUserComment(String createUserComment) {
		this.createUserComment = createUserComment;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updateUserId
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId
	 *            the updateUserId to set
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the updateUserComment
	 */
	public String getUpdateUserComment() {
		return updateUserComment;
	}

	/**
	 * @param updateUserComment
	 *            the updateUserComment to set
	 */
	public void setUpdateUserComment(String updateUserComment) {
		this.updateUserComment = updateUserComment;
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
				+ ((cluDisplay == null) ? 0 : cluDisplay.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime
				* result
				+ ((createUserComment == null) ? 0 : createUserComment
						.hashCode());
		result = prime * result
				+ ((createUserId == null) ? 0 : createUserId.hashCode());
		result = prime
				* result
				+ ((effectiveEndDate == null) ? 0 : effectiveEndDate.hashCode());
		result = prime
				* result
				+ ((effectiveStartDate == null) ? 0 : effectiveStartDate
						.hashCode());
		result = prime
				* result
				+ ((luRelationTypeId == null) ? 0 : luRelationTypeId.hashCode());
		result = prime
				* result
				+ ((relatedCluDisplay == null) ? 0 : relatedCluDisplay
						.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime
				* result
				+ ((updateUserComment == null) ? 0 : updateUserComment
						.hashCode());
		result = prime * result
				+ ((updateUserId == null) ? 0 : updateUserId.hashCode());
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
		final CluRelationInfo other = (CluRelationInfo) obj;
		if (cluDisplay == null) {
			if (other.cluDisplay != null)
				return false;
		} else if (!cluDisplay.equals(other.cluDisplay))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (createUserComment == null) {
			if (other.createUserComment != null)
				return false;
		} else if (!createUserComment.equals(other.createUserComment))
			return false;
		if (createUserId == null) {
			if (other.createUserId != null)
				return false;
		} else if (!createUserId.equals(other.createUserId))
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
		if (luRelationTypeId == null) {
			if (other.luRelationTypeId != null)
				return false;
		} else if (!luRelationTypeId.equals(other.luRelationTypeId))
			return false;
		if (relatedCluDisplay == null) {
			if (other.relatedCluDisplay != null)
				return false;
		} else if (!relatedCluDisplay.equals(other.relatedCluDisplay))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (updateUserComment == null) {
			if (other.updateUserComment != null)
				return false;
		} else if (!updateUserComment.equals(other.updateUserComment))
			return false;
		if (updateUserId == null) {
			if (other.updateUserId != null)
				return false;
		} else if (!updateUserId.equals(other.updateUserId))
			return false;
		return true;
	}
}
