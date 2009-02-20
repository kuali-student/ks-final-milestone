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

import org.kuali.student.core.dto.RichText;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KS_LU_DOC_REL_T")
public class LuDocumentRelation extends MetaEntity implements
		AttributeOwner<LuDocumentRelationAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "LU_DOC_REL_TYPE_ID")
	private LuDocumentRelationType luDocumentRelationType;

	@ManyToMany
	@JoinTable(name = "KS_CLU_LU_DOC_REL_T", joinColumns = @JoinColumn(name = "LU_DOC_REL_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_ID"))
	private List<Clu> clus;

	@Column(name = "DOC_ID")
	private String documentId; // FOREIGN Service Relation

	@Column(name = "TITLE")
	private String title;

	@ManyToOne
	@JoinColumn(name = "DESCRIPTION")
	private RichText desc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRATION_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuDocumentRelationAttribute> attributes;

	@Column(name = "STATE")
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LuDocumentRelationType getLuDocumentRelationType() {
		return luDocumentRelationType;
	}

	public void setLuDocumentRelationType(
			LuDocumentRelationType luDocumentRelationType) {
		this.luDocumentRelationType = luDocumentRelationType;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<LuDocumentRelationAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LuDocumentRelationAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<LuDocumentRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setClus(List<Clu> clus) {
		this.clus = clus;
	}

	public List<Clu> getClus() {
		if (clus == null) {
			clus = new ArrayList<Clu>();
		}
		return clus;
	}

}
