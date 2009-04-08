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
}
