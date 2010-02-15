package org.kuali.student.core.search.newdto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchParam {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String value;
	@XmlElement
	private List<String> listValue;
	@XmlAttribute
	private String key;

	public Object getValue() {
		if (value != null) {
			return value;
		} else {
			return listValue;
		}
	}

	public void setValue(String value) {
		this.value = value;
		listValue = null;
	}

	public void setValue(List<String> listValue) {
		this.listValue = listValue;
		value = null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
