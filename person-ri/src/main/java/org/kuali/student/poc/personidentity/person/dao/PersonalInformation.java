package org.kuali.student.poc.personidentity.person.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name = "Personal_Information_T")
public class PersonalInformation {

	@Id
	private String id;

	@OneToOne
	@JoinColumn(name = "Person_ID", nullable = false)
	private Person person;

	private char gender;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;

	private String photo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deceasedDate;

	private boolean confidential;

	private String maritalStatus;

	private String primaryLanguageCode;

	private String secondaryLanguageCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@ManyToOne
	@JoinColumn(name = "update_person_id")
	private Person updatePerson;

	private String updateComment;

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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isConfidential() {
		return confidential;
	}

	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getDeceasedDate() {
		return deceasedDate;
	}

	public void setDeceasedDate(Date deceasedDate) {
		this.deceasedDate = deceasedDate;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPrimaryLanguageCode() {
		return primaryLanguageCode;
	}

	public void setPrimaryLanguageCode(String primaryLanguageCode) {
		this.primaryLanguageCode = primaryLanguageCode;
	}

	public String getSecondaryLanguageCode() {
		return secondaryLanguageCode;
	}

	public void setSecondaryLanguageCode(String secondaryLanguageCode) {
		this.secondaryLanguageCode = secondaryLanguageCode;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Person getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(Person updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getUpdateComment() {
		return updateComment;
	}

	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

}
