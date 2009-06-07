package org.kuali.student.core.messages.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "LocaleKeyList")
public class LocaleKeyList implements java.io.Serializable {
	private static final long serialVersionUID = 7277270521969339541L;

	@XmlElement(name = "locale", required = true)
	protected List<String> locales;

	public List<String> getLocales() {
		if (locales == null) {
			locales = new ArrayList<String>();
		}
		return this.locales;
	}

	public void setLocales(List<String> l) {
		locales = l;

	}
}