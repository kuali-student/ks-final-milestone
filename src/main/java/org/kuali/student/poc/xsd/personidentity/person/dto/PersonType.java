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
public class PersonType extends PersonTypeInfoDTO {
	@XmlElement(name = "set")
	@XmlElementWrapper(name = "setList")
	private List<AttributeSetDefinition> attributeSets;

	/**
	 * @return the attributeSets
	 */
	public List<AttributeSetDefinition> getAttributeSets() {
		if (attributeSets == null) {
			attributeSets = new ArrayList<AttributeSetDefinition>();
		}
		return attributeSets;
	}
}
