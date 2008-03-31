package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeSetDTO {
	@XmlAttribute
	Long id;
	@XmlAttribute
	String name;
	@XmlElement(name="attributeDefinition")
	List<AttributeDefinitionDTO> attributeDefinitions;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	/**
	 * @return the attributeDefinitions
	 */
	public List<AttributeDefinitionDTO> getAttributeDefinitions() {
		if (attributeDefinitions == null) {
			attributeDefinitions = new ArrayList<AttributeDefinitionDTO>();
		}
		return attributeDefinitions;
	}

}
