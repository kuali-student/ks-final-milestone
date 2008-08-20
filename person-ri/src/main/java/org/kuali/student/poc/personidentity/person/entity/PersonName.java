package org.kuali.student.poc.personidentity.person.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name = "Person_Name_T")
@NamedQueries(
    {
        @NamedQuery( name = "PersonName.findPersonDisplayDTOByCriteria",
        	query = "SELECT NEW org.kuali.student.poc.personidentity.person.dto.PersonDisplayDTO(n.person.id, n.givenName, n.middleNames, n.surname) FROM PersonName n WHERE LOWER(n.givenName) LIKE LOWER(:firstName) AND LOWER(n.surname) LIKE LOWER(:lastName)")
    }
)

public class PersonName {

	@Id
	private String id;

	@ManyToOne
	@JoinColumn(name = "Person_ID", nullable = false)
	private Person person;

	private String title;
	private String name;
	private String surname;
	private String middleNames;
	private String givenName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;

	@Column(nullable = false)
	private String nameType;
	private String suffix;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String updateUserId;
	private String updateUserComment;

	public PersonName() {
		id = null;
	}

	public PersonName(String givenName, String surname) {
		this.givenName = givenName;
		this.surname = surname;
	}

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
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

	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
