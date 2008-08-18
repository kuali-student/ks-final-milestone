package org.kuali.student.poc.personidentity.person.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDisplay implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String personId;
	@XmlElement
	private PersonNameInfo name;

	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @return the name
	 */
	public PersonNameInfo getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(PersonNameInfo name) {
		this.name = name;
	}

}
