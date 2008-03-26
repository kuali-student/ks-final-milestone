package org.kuali.student.poc.personidentity.person.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "Person_Attribute_T")
@TableGenerator(name = "idGen")
public class PersonAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Person_ID", nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "Person_Attribute_Type_ID", nullable = false)
	private PersonAttributeType personAttributeType;

	private String value;

	public PersonAttribute() {
		id = null;
	}

	public PersonAttribute(String value) {
		super();
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
