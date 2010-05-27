package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class LookupConstraintParamMapping {
	protected String fieldPath;
	protected String paramKey;
	private List<String> defaultValueList;
	private String defaultValueString;

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public List<String> getDefaultValueList() {
		return defaultValueList;
	}

	public void setDefaultValueList(List<String> defaultValueList) {
		this.defaultValueList = defaultValueList;
	}

	public String getDefaultValueString() {
		return defaultValueString;
	}

	public void setDefaultValueString(String defaultValueString) {
		this.defaultValueString = defaultValueString;
	}
}