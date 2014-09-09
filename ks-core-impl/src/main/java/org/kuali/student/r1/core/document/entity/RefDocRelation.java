package org.kuali.student.r1.core.document.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.MetaEntity;

@Deprecated
@Entity
@Table(name="KSDO_REF_DOC_RELTN")
@NamedQueries( {
    @NamedQuery(name = "RefDocRelation.getRefDocRelationsByRef", 
    		   query = "SELECT rel FROM RefDocRelation rel WHERE rel.refObjectId = :refObjectId AND rel.refObjectType.id = :refObjectTypeKey"),
    @NamedQuery(name = "RefDocRelation.getRefDocRelationsByDoc", 
    		   query = "SELECT rel FROM RefDocRelation rel WHERE rel.document.id = :documentId")   
    })
public class RefDocRelation extends MetaEntity implements AttributeOwner<RefDocRelationAttribute>{

    @Column(name = "REF_OBJ_ID")
    private String refObjectId;

    @ManyToOne
    @JoinColumn(name = "DOC_ID")
    private Document document;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private DocumentRichText descr;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "REF_OBJ_TYPE_KEY")
    private RefObjectType refObjectType;
    
    @ManyToOne
    @JoinColumn(name = "TYPE_KEY")
    private RefDocRelationType refDocRelationType;

    @Column(name = "ST")
    private String state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<RefDocRelationAttribute> attributes;
    
	@Override
	public void setAttributes(List<RefDocRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public List<RefDocRelationAttribute> getAttributes() {
		return this.attributes;
	}

	public String getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(String refObjectId) {
		this.refObjectId = refObjectId;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DocumentRichText getDescr() {
		return descr;
	}

	public void setDescr(DocumentRichText descr) {
		this.descr = descr;
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

	public RefDocRelationType getRefDocRelationType() {
		return refDocRelationType;
	}

	public void setRefDocRelationType(RefDocRelationType refDocRelationType) {
		this.refDocRelationType = refDocRelationType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setRefObjectType(RefObjectType refObjectType) {
		this.refObjectType = refObjectType;
	}

	public RefObjectType getRefObjectType() {
		return refObjectType;
	}
}
