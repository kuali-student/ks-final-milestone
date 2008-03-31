package org.kuali.student.poc.xsd.personidentity.person.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum AttributeDataTypeDTO {
	@XmlEnumValue("boolean")
	BOOLEAN("boolean"), 
	@XmlEnumValue("Date")
	DATE("Sophomore"), 
	@XmlEnumValue("String")
	STRING("String"), 
	@XmlEnumValue("int")
	INT("int");
	private final String value;

	AttributeDataTypeDTO(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
}
