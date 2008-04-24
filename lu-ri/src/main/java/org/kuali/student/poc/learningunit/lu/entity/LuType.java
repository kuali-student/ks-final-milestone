package org.kuali.student.poc.learningunit.lu.entity;

import java.util.Date;
import java.util.HashSet;
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
	private Set<LuAttributeType> luAttributeTypes;

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
	public Set<LuAttributeType> getLuAttributeTypes() {
		if (luAttributeTypes == null) {
			luAttributeTypes = new HashSet<LuAttributeType>();
		}
		return luAttributeTypes;
	}

	/**
	 * @param luAttributeTypes
	 *            the luAttributeTypes to set
	 */
	public void setLuAttributeTypes(Set<LuAttributeType> luAttributeTypes) {
		this.luAttributeTypes = luAttributeTypes;
	}
}
