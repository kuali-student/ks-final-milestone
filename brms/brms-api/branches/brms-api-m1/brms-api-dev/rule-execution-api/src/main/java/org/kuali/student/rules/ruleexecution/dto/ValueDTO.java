package org.kuali.student.rules.ruleexecution.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ValueDTO {
	@XmlAttribute
    private String valueId;
	@XmlElement
	private Object val;

	public ValueDTO() {}

	public ValueDTO(String id, Object value) {
		this.valueId = id;
		this.val = value;
	}

	public String getId() { return valueId; }

	public Object getValue() { return val; }

	public String toString() {
		return "id=" + valueId + ", value=" + val;
	}
}

