package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="KS_LU_DOC_REL_T")
public class LuDocumentRelation {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "LU_DOC_REL_TYPE_ID")
	private LuDocumentRelationType luDocumentRelationType;

	@ManyToMany
	@JoinTable(name = "KS_CLU_LU_DOC_REL_T", joinColumns = @JoinColumn(name = "LU_DOC_REL_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_ID"))
	private List<Clu> clus;

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

	public List<Clu> getClus() {
		if(clus==null){
			clus=new ArrayList<Clu>();
		}
		return clus;
	}

	public void setClu(List<Clu> clus) {
		this.clus = clus;
	}



}
