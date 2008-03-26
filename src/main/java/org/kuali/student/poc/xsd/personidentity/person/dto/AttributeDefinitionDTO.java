package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeDefinitionDTO {
	@XmlAttribute
	private Long id;
	@XmlAttribute
	private String name;
	@XmlElement
	private AttributeDataTypeDTO type;
	@XmlElement
	private String label;
	@XmlElement(name="validator")
	@XmlElementWrapper(name="validatorList")
	private List<String> validators;
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
	/**
	 * @return the type
	 */
	public AttributeDataTypeDTO getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(AttributeDataTypeDTO type) {
		this.type = type;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the validators
	 */
	public List<String> getValidators() {
		if(validators==null){
			validators=new ArrayList<String>();
		}
		return validators;
	} 
	
}
