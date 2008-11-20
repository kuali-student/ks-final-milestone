package org.kuali.student.core.search.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CriteriaSet implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private Criterion criterion;
	@XmlElement
	private CriteriaSet criteria;
	@XmlAttribute
	private String operator;

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	public CriteriaSet getCriteria() {
		return criteria;
	}

	public void setCriteria(CriteriaSet criteria) {
		this.criteria = criteria;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}