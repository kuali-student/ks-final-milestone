/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_CLU_DOC_RELTN", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"CLU_ID", "DOC_ID" }) })
@NamedQueries( {
		@NamedQuery(name = "LuDocumentRelation.getLuDocRelationsByClu", query = "SELECT ldr FROM LuDocumentRelation ldr WHERE ldr.clu.id = :cluId"),
		@NamedQuery(name = "LuDocumentRelation.getLuDocRelationsByDocument", query = "SELECT ldr FROM LuDocumentRelation ldr WHERE ldr.documentId = :documentId"),
		@NamedQuery(name = "LuDocumentRelation.getLuDocRelationsByIdList", query = "SELECT ldr FROM LuDocumentRelation ldr WHERE ldr.id IN(:luDocRelationIds)"),
		@NamedQuery(name = "LuDocumentRelation.getLuDocRelationsByType", query = "SELECT ldr FROM LuDocumentRelation ldr WHERE ldr.luDocumentRelationType.id = :luDocRelationTypeId")
})	
public class LuDocumentRelation extends MetaEntity implements
		AttributeOwner<LuDocumentRelationAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "LU_DOC_RELTN_TYPE_ID")
	private LuDocumentRelationType luDocumentRelationType;

	@ManyToOne
	@JoinColumn(name = "CLU_ID")
	private Clu clu;

	@Column(name = "DOC_ID")
	private String documentId; // FOREIGN Service Relation

	@Column(name = "TITLE")
	private String title;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DESCR")
	private LuRichText descr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuDocumentRelationAttribute> attributes;

	@Column(name = "ST")
	private String state;

	@Override
    public void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
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

	public LuRichText getDescr() {
		return descr;
	}

	public void setDescr(LuRichText descr) {
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

	public void setClu(Clu clu) {
		this.clu = clu;
	}

	public Clu getClu() {
		return clu;
	}

}
