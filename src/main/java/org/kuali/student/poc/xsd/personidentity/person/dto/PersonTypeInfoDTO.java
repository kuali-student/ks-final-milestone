package org.kuali.student.poc.xsd.personidentity.person.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class PersonTypeInfoDTO {
    @XmlAttribute
    private Long id;
    @XmlAttribute
    private String name;
	/**
	 * 
	 */
	public PersonTypeInfoDTO() {
		super();
	}
	/**
	 * @param id
	 * @param name
	 */
	public PersonTypeInfoDTO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
