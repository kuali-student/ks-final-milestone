package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;

@Entity
@Table(name = "KS_CLU_SET_T")
public class CluSet extends MetaEntity implements
		AttributeOwner<CluSetAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "CLU_SET_NAME")
	private String name;

	@ManyToOne
	@JoinColumn(name = "DESCRIPTION")
	private RichText desc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRATION_DT")
	private Date expirationDate;

	// private CluCriteria cluCriteria;//TODO Criteria

	@ManyToMany
	@JoinTable(name = "KS_CLU_SET_JOIN_T", joinColumns = @JoinColumn(name = "CLU_SET_PARENT_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_SET_CHILD_ID"))
	private List<CluSet> cluSets;

	@ManyToMany
	@JoinTable(name = "KS_CLU_SET_CLU_T", joinColumns = @JoinColumn(name = "CLU_SET_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_ID"))
	private List<Clu> clus;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
	private List<CluSetAttribute> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Clu> getClus() {
		return clus;
	}

	public void setClus(List<Clu> clus) {
		this.clus = clus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RichText getDesc() {
		return desc;
	}

	public void setDesc(RichText desc) {
		this.desc = desc;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public List<CluSet> getCluSets() {
		if (cluSets == null) {
			cluSets = new ArrayList<CluSet>();
		}
		return cluSets;
	}

	public void setCluSets(List<CluSet> cluSets) {
		this.cluSets = cluSets;
	}

	public List<CluSetAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<CluSetAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<CluSetAttribute> attributes) {
		this.attributes = attributes;
	}

}
