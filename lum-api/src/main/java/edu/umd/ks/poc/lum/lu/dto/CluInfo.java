package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.umd.ks.poc.lum.util.adapter.JaxbAttributeListMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluInfo implements Serializable {

	private static final long serialVersionUID = 8378784724612312877L;
	@XmlAttribute
	private String cluId;
	@XmlElement
	private String luTypeId;
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
	private String approvalStatus;
	@XmlElement
	private String approvalStatusTime;
	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeListMapListAdapter.class)
	private Map<String, List<String>> attributes;
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

	@XmlElement
	private String status;

	@XmlElement
	private String adminDepartment;
	/**
	 * @return the cluId
	 */
	public String getCluId() {
		return cluId;
	}

	/**
	 * @param cluId
	 *            the cluId to set
	 */
	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	/**
	 * @return the luTypeId
	 */
	public String getLuTypeId() {
		return luTypeId;
	}

	/**
	 * @param luTypeId
	 *            the luTypeId to set
	 */
	public void setLuTypeId(String luTypeId) {
		this.luTypeId = luTypeId;
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
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus
	 *            the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the approvalStatusTime
	 */
	public String getApprovalStatusTime() {
		return approvalStatusTime;
	}

	/**
	 * @param approvalStatusTime
	 *            the approvalStatusTime to set
	 */
	public void setApprovalStatusTime(String approvalStatusTime) {
		this.approvalStatusTime = approvalStatusTime;
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

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, List<String>> attributes) {
		this.attributes = attributes;
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
				+ ((approvalStatus == null) ? 0 : approvalStatus.hashCode());
		result = prime
				* result
				+ ((approvalStatusTime == null) ? 0 : approvalStatusTime
						.hashCode());
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((cluId == null) ? 0 : cluId.hashCode());
		result = prime * result
				+ ((cluLongName == null) ? 0 : cluLongName.hashCode());
		result = prime * result
				+ ((cluShortName == null) ? 0 : cluShortName.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime
				* result
				+ ((createUserComment == null) ? 0 : createUserComment
						.hashCode());
		result = prime * result
				+ ((createUserId == null) ? 0 : createUserId.hashCode());
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
		result = prime * result
				+ ((luTypeId == null) ? 0 : luTypeId.hashCode());
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
		final CluInfo other = (CluInfo) obj;
		if (approvalStatus == null) {
			if (other.approvalStatus != null)
				return false;
		} else if (!approvalStatus.equals(other.approvalStatus))
			return false;
		if (approvalStatusTime == null) {
			if (other.approvalStatusTime != null)
				return false;
		} else if (!approvalStatusTime.equals(other.approvalStatusTime))
			return false;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (cluId == null) {
			if (other.cluId != null)
				return false;
		} else if (!cluId.equals(other.cluId))
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
		if (luTypeId == null) {
			if (other.luTypeId != null)
				return false;
		} else if (!luTypeId.equals(other.luTypeId))
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
