package org.kuali.student.poc.personidentity.person.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name = "Person_Attribute_T")
public class PersonAttribute {

	@Id
	private String id;

	@ManyToOne
	@JoinColumn(name = "Person_ID", nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "Person_Attribute_Type_ID", nullable = false)
	private PersonAttributeType personAttributeType;

	private String value;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID();
	}

	public PersonAttribute() {
		id = null;
	}

	public PersonAttribute(String value) {
		super();
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PersonAttributeType getPersonAttributeType() {
		return personAttributeType;
	}

	public void setPersonAttributeType(PersonAttributeType personAttributeType) {
		this.personAttributeType = personAttributeType;
	}

}
