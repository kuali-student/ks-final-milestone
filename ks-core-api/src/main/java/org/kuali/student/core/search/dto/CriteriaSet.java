package org.kuali.student.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CriteriaSet implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private List<Criterion> criterion;
	@XmlElement
	private List<CriteriaSet> criteria;
	@XmlAttribute
	private String operator;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<Criterion> getCriterion() {
		if (criterion == null) {
			criterion = new ArrayList<Criterion>();
		}
		return criterion;
	}

	public void setCriterion(List<Criterion> criterion) {
		this.criterion = criterion;
	}

	public List<CriteriaSet> getCriteria() {
		if (criteria == null) {
			criteria = new ArrayList<CriteriaSet>();
		}
		return criteria;
	}

	public void setCriteria(List<CriteriaSet> criteria) {
		this.criteria = criteria;
	}
}