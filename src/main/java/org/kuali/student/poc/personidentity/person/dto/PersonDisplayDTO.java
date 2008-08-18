package org.kuali.student.poc.personidentity.person.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDisplayDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String personId;
	@XmlElement
	private String name;

	public PersonDisplayDTO() {
	}

	public PersonDisplayDTO(final String personId, final String firstName, final String surname, final String lastName) {
		this.personId = personId;
		
		final StringBuilder name = new StringBuilder();
		if (firstName != null && firstName.trim().length() > 0) {
			name.append(firstName.trim());
		}
		if (surname != null && surname.trim().length() > 0) {
			if (name.length() > 0) {
				name.append(" ");
			}
			name.append(surname.trim());
		}
		if (lastName != null && lastName.trim().length() > 0) {
			if (name.length() > 0) {
				name.append(" ");
			}
			name.append(lastName.trim());
		}
		this.name = name.toString();
	}
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
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
