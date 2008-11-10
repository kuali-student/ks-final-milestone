package edu.umd.ks.poc.lum.lu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class LuType {
	@Id
	private String luTypeId;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String createUserId;

	private String createUserComment;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String updateUserId;

	private String updateUserComment;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "LuTyp_LuAtTyp_J", joinColumns = @JoinColumn(name = "LuType_ID", referencedColumnName = "LUTYPEID"), inverseJoinColumns = @JoinColumn(name = "LuAtTyp_ID", referencedColumnName = "ID"))
	private List<LuAttributeType> luAttributeTypes;

	@ManyToMany
	private Set<LuRelationType> allowedLuRelationTypes;
	private String status;
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
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.luTypeId = UUIDHelper.genStringUUID(this.luTypeId);
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

	/**
	 * @return the luAttributeTypes
	 */
	public List<LuAttributeType> getLuAttributeTypes() {
		if (luAttributeTypes == null) {
			luAttributeTypes = new ArrayList<LuAttributeType>();
		}
		return luAttributeTypes;
	}

	/**
	 * @param luAttributeTypes
	 *            the luAttributeTypes to set
	 */
	public void setLuAttributeTypes(List<LuAttributeType> luAttributeTypes) {
		this.luAttributeTypes = luAttributeTypes;
	}

	/**
	 * @return the allowedLuRelationTypes
	 */
	public Set<LuRelationType> getAllowedLuRelationTypes() {
		if (allowedLuRelationTypes == null) {
			allowedLuRelationTypes = new HashSet<LuRelationType>();
		}
		return allowedLuRelationTypes;
	}

	/**
	 * @param allowedLuRelationTypes
	 *            the allowedLuRelationTypes to set
	 */
	public void setAllowedLuRelationTypes(
			Set<LuRelationType> allowedLuRelationTypes) {
		this.allowedLuRelationTypes = allowedLuRelationTypes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
		+ ((luTypeId == null) ? 0 : luTypeId.hashCode());
		if(luTypeId != null){
			return result;
		}
		result = prime
				* result
				+ ((allowedLuRelationTypes == null) ? 0
						: allowedLuRelationTypes.hashCode());
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
				+ ((luAttributeTypes == null) ? 0 : luAttributeTypes.hashCode());

		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		final LuType other = (LuType) obj;
		if (allowedLuRelationTypes == null) {
			if (other.allowedLuRelationTypes != null)
				return false;
		} else if (!allowedLuRelationTypes.equals(other.allowedLuRelationTypes))
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
		if (luAttributeTypes == null) {
			if (other.luAttributeTypes != null)
				return false;
		} else if (!luAttributeTypes.equals(other.luAttributeTypes))
			return false;
		if (luTypeId == null) {
			if (other.luTypeId != null)
				return false;
		} else if (!luTypeId.equals(other.luTypeId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
