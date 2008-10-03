package org.kuali.student.rules.ruleexecution.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ValueDTO {
	@XmlAttribute
    private String valueId;
	@XmlElement
	private String valueDataType;
	@XmlElement
	private String val;

	public ValueDTO() {}

	public ValueDTO(String id, String dataType, String value) {
		this.valueId = id;
		this.valueDataType = dataType;
		this.val = value;
	}

	public String getId() { return valueId; }
	public void setId(String id) { this.valueId = id; }

	public String getDataType() { return valueDataType; }
	public void setDataType(String dataType) { this.valueDataType = dataType; }

	public String getValue() { return val; }
	public void setValue(String value) { this.val = value; }

}

