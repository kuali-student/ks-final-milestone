package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KS_CLU_REL_T")
public class CluCluRelation {
	
	@Id
	@Column(name="ID")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="CLU_ID")
	private Clu clu;
	
	@ManyToOne
	@JoinColumn(name="RELATED_CLU_ID")
	private Clu relatedClu;
	
	@ManyToOne
	@JoinColumn(name="LU_REL_TYPE_ID")
	private LuLuRelationType luLuRelationType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}

	public Clu getRelatedClu() {
		return relatedClu;
	}

	public void setRelatedClu(Clu relatedClu) {
		this.relatedClu = relatedClu;
	}

	public LuLuRelationType getLuLuRelationType() {
		return luLuRelationType;
	}

	public void setLuLuRelationType(LuLuRelationType luLuRelationType) {
		this.luLuRelationType = luLuRelationType;
	}
	
}
