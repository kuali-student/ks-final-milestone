package org.kuali.student.lum.lu.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "KS_CLU_SET_T")
public class CluSet {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToMany
	@JoinTable(name = "KS_CLU_SET_CLU_T", joinColumns = @JoinColumn(name = "CLU_SET_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_ID"))
	private List<Clu> clus;

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

}
