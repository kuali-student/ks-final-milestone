package org.kuali.student.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuStatementTypeHeaderTemplateInfo implements Serializable {

	private static final long serialVersionUID = 1L;

    @XmlAttribute
	private String nlUsageTypeKey;

    @XmlElement
	private String template;

    @XmlAttribute
	private String language;

	public String getNlUsageTypeKey() {
		return nlUsageTypeKey;
	}

	public void setNlUsageTypeKey(String nlUsageTypeKey) {
		this.nlUsageTypeKey = nlUsageTypeKey;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String locale) {
		this.language = locale;
	}

	@Override
	public String toString() {
		return "LuStatementTypeHeaderTemplateInfo[language=" + language
				+ ", nlUsageTypeKey=" + nlUsageTypeKey + "]";
	}
}
