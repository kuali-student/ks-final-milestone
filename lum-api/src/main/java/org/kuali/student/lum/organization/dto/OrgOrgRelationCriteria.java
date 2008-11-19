package org.kuali.student.lum.organization.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.search.dto.CriteriaSet;
import org.kuali.student.core.search.dto.Criterion;
import org.kuali.student.core.search.dto.SearchIndexedType;
import org.kuali.student.core.search.dto.SearchRelationship;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrgOrgRelationCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private List<SearchIndexedType> types;
	@XmlElement
	private List<SearchRelationship> relationships;
	@XmlElement
	private CriteriaSet criteria;
	@XmlElement
	private Criterion criterion;

	public List<SearchIndexedType> getTypes() {
		if (types == null) {
			types = new ArrayList<SearchIndexedType>();
		}
		return types;
	}

	public void setTypes(List<SearchIndexedType> types) {
		this.types = types;
	}

	public List<SearchRelationship> getRelationships() {
		if (relationships == null) {
			relationships = new ArrayList<SearchRelationship>();
		}
		return relationships;
	}

	public void setRelationships(List<SearchRelationship> relationships) {
		this.relationships = relationships;
	}

	public CriteriaSet getCriteria() {
		return criteria;
	}

	public void setCriteria(CriteriaSet criteria) {
		this.criteria = criteria;
	}

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}
}