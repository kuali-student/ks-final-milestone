package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonAttributeSetTypeDisplay implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String id;
	@XmlAttribute
	private String name;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
