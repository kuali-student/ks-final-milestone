package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDTO extends PersonInfoDTO {

	@XmlElement(name = "personType")
	@XmlElementWrapper(name = "personTypes")
	List<PersonTypeInfoDTO> personTypes;

	public PersonDTO() {
		super();
		personTypes = null;
	}
	
	/**
	 * @return the personTypes
	 */
	public List<PersonTypeInfoDTO> getPersonTypes() {
		if (personTypes == null) {
			personTypes = new ArrayList<PersonTypeInfoDTO>();
		}
		return personTypes;
	}

}
