package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_STMT_TYPE_HEADER_TMPL")
public class LuStatementTypeHeaderTemplate extends Attribute<LuStatementType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuStatementType owner;
	
	@Column(name="NL_USUAGE_TYPE_KEY")
	private String nlUsageTypeKey;

	@Column(name="TEMPLATE", length=2000)
	private String template;

	@Column(name="LANGUAGE", length=2)
	private String language;

	@Override
	public LuStatementType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuStatementType owner) {
		this.owner = owner;
	}

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
		return "LuStatementTypeHeaderTemplate[language=" + language
				+ ", nlUsageTypeKey=" + nlUsageTypeKey 
				+ ", luStatementTypeId" + owner.getId() +"]";
	}
}
