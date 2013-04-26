package org.kuali.student.ap.course.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.kuali.student.r2.common.entity.PersistableEntity;

@Entity
@Table(name = "KSPL_CRIT")
public class ClassFinderCriteriaEntity implements PersistableEntity<String> {
	
	@Id
	@Column(name = "CRIT_KEY")
	private String key;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_SRCH_DT")
	private Date lastSearchDate;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "KSPL_CRIT_FACET", joinColumns = @JoinColumn(name = "CRIT_KEY"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"CRIT_KEY", "FACET" }))
	@Column(name = "FACET")
	private List<String> facet;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getLastSearchDate() {
		return lastSearchDate;
	}

	public void setLastSearchDate(Date lastSearchDate) {
		this.lastSearchDate = lastSearchDate;
	}

	public List<String> getFacet() {
		return facet;
	}

	public void setFacet(List<String> facet) {
		this.facet = facet;
	}

	@Override
	public String getId() {
		return key;
	}

}
