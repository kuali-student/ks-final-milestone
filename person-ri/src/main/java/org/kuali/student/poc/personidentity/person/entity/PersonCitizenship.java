package org.kuali.student.poc.personidentity.person.entity;

import java.util.Date;

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
@Table(name = "Person_Citizenship_T")
public class PersonCitizenship {

	@Id
	private String id;

	@ManyToOne
	@JoinColumn(name = "Person_ID", nullable = false)
	private Person person;

	private String countryOfCitizenship;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;

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

	public String getCountryOfCitizenship() {
		return countryOfCitizenship;
	}

	public void setCountryOfCitizenship(String countryOfCitizenship) {
		this.countryOfCitizenship = countryOfCitizenship;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
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
