package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_REQ_COM_TYPE_NL_TMPL")
public class ReqComponentTypeNLTemplate extends Attribute<ReqComponentType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private ReqComponentType owner;

	@Column(name="NL_USUAGE_TYPE_KEY")
	private String nlUsageTypeKey;

	@Column(name="TEMPLATE", length=2000)
	private String template;

	@Column(name="LANGUAGE", length=2)
	private String language;

	@Override
	public ReqComponentType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(ReqComponentType owner) {
		this.owner = owner;
	}

	public String getNlUsageTypeKey() {
		return nlUsageTypeKey;
	}

	public void setNlUsageTypeKey(String nlUsageTypeKey) {
		this.nlUsageTypeKey = nlUsageTypeKey;
	}

	public void setTemplate(String value) {
		this.template = value;
	}

	public String getTemplate() {
		return template;
	}

	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String locale) {
		this.language = locale;
	}
}
