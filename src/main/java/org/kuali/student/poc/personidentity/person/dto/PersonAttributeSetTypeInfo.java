package org.kuali.student.poc.personidentity.person.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonAttributeSetTypeInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String id;
	@XmlAttribute
	private String name;
	@XmlElement(name="attributeTypes")
	private List<PersonAttributeTypeInfo> attributeTypes;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
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
	public List<PersonAttributeTypeInfo> getAttributeTypes() {
		if (attributeTypes == null) {
			attributeTypes = new ArrayList<PersonAttributeTypeInfo>();
		}
		return attributeTypes;
	}

}
