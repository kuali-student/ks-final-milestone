package edu.umd.ks.poc.lum.lu.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Clu {

	@Id
	private String cluId;
	@ManyToOne
	private LuType luType;
	private String cluLongName;
	private String cluShortName;
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;
	@ManyToOne
	private Atp effectiveStartCycle;
	@ManyToOne
	private Atp effectiveEndCycle;
	private String approvalStatus;
	private String approvalStatusTime;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "clu")
	private Set<CluAttribute> attributes;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String createUserId;
	private String createUserComment;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	private String updateUserId;
	private String updateUserComment;

	private String status;
	
	private String adminDepartment;
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

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.cluId = UUIDHelper.genStringUUID(this.cluId);
	}
	
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
	 * @return the luType
	 */
	public LuType getLuType() {
		return luType;
	}

	/**
	 * @param luType
	 *            the luType to set
	 */
	public void setLuType(LuType luType) {
		this.luType = luType;
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
	public Atp getEffectiveStartCycle() {
		return effectiveStartCycle;
	}

	/**
	 * @param effectiveStartCycle
	 *            the effectiveStartCycle to set
	 */
	public void setEffectiveStartCycle(Atp effectiveStartCycle) {
		this.effectiveStartCycle = effectiveStartCycle;
	}

	/**
	 * @return the effectiveEndCycle
	 */
	public Atp getEffectiveEndCycle() {
		return effectiveEndCycle;
	}

	/**
	 * @param effectiveEndCycle
	 *            the effectiveEndCycle to set
	 */
	public void setEffectiveEndCycle(Atp effectiveEndCycle) {
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
	 * @return the attributes
	 */
	public Set<CluAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new HashSet<CluAttribute>();
		}
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Set<CluAttribute> attributes) {
		this.attributes = attributes;
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
}
