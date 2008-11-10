package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuTypeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String luTypeKey;
	@XmlElement
	private String description;
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
	private List<String> luAttributeTypeIds;
	
	@XmlElement
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
	 * @return the luTypeId
	 */
	public String getLuTypeKey() {
		return luTypeKey;
	}

	/**
	 * @param luTypeKey
	 *            the luTypeId to set
	 */
	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
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
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime
				* result
				+ ((createUserComment == null) ? 0 : createUserComment
						.hashCode());
		result = prime * result
				+ ((createUserId == null) ? 0 : createUserId.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((luTypeKey == null) ? 0 : luTypeKey.hashCode());
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
		final LuTypeInfo other = (LuTypeInfo) obj;
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
		if (luTypeKey == null) {
			if (other.luTypeKey != null)
				return false;
		} else if (!luTypeKey.equals(other.luTypeKey))
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
	 * @return the luAttributeTypeIds
	 */
	public List<String> getLuAttributeTypeIds() {
		if(luAttributeTypeIds==null){
			luAttributeTypeIds = new ArrayList<String>();
		}
		return luAttributeTypeIds;
	}

	/**
	 * @param luAttributeTypeIds the luAttributeTypeIds to set
	 */
	public void setLuAttributeTypeIds(List<String> luAttributeTypeIds) {
		this.luAttributeTypeIds = luAttributeTypeIds;
	}

}
