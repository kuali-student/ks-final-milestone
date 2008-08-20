package org.kuali.student.poc.personidentity.person.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name = "PersonReferenceID_T")
public class PersonReferenceId {

	@Id
	private String id;

	@ManyToOne
	@JoinColumn(name = "Person_ID", nullable = false)
	private Person person;

	private String referenceId;

	@Column(nullable = false)
	private String organizationReferenceId;
	private boolean restrictedAccess;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String updateUserId;

	private String updateUserComment;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getOrganizationReferenceId() {
		return organizationReferenceId;
	}

	public void setOrganizationReferenceId(String organizationReferenceId) {
		this.organizationReferenceId = organizationReferenceId;
	}

	public boolean isRestrictedAccess() {
		return restrictedAccess;
	}

	public void setRestrictedAccess(boolean restrictedAccess) {
		this.restrictedAccess = restrictedAccess;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserComment() {
		return updateUserComment;
	}

	public void setUpdateUserComment(String updateUserComment) {
		this.updateUserComment = updateUserComment;
	}

}
