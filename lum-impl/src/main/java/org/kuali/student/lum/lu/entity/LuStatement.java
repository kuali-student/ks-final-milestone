package org.kuali.student.lum.lu.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "KS_LU_STMT_T")
public class LuStatement {
	@Id
	@Column(name = "ID")
	private String id;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PARENT_LU_STMT_ID")
	private LuStatement parent;
	
	@OneToMany(mappedBy="parent")
	private List<LuStatement> children;
	
	@ManyToMany
	@JoinTable(name="KS_LU_STMT_REQ_COMP_T",joinColumns=@JoinColumn(name="REQ_COMP_ID"),inverseJoinColumns=@JoinColumn(name="LU_STMT_ID"))
	private List<RequiredComponent> requiredComponents;
	
	@ManyToOne
	@JoinColumn(name="LU_STMT_TYPE_ID")
	private LuStatementType luStatementType;
	
	@ManyToMany
	@JoinTable(name="KS_CLU_LU_STMT_T",joinColumns=@JoinColumn(name="CLU_ID"),inverseJoinColumns=@JoinColumn(name="LU_STMT_ID"))
	private List<Clu> clus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LuStatement getParent() {
		return parent;
	}

	public void setParent(LuStatement parent) {
		this.parent = parent;
	}

	public List<LuStatement> getChildren() {
		return children;
	}

	public void setChildren(List<LuStatement> children) {
		this.children = children;
	}

	public List<RequiredComponent> getRequiredComponents() {
		return requiredComponents;
	}

	public void setRequiredComponents(List<RequiredComponent> requiredComponents) {
		this.requiredComponents = requiredComponents;
	}

	public LuStatementType getLuStatementType() {
		return luStatementType;
	}

	public void setLuStatementType(LuStatementType luStatementType) {
		this.luStatementType = luStatementType;
	}

	public List<Clu> getClus() {
		return clus;
	}

	public void setClus(List<Clu> clus) {
		this.clus = clus;
	}
	
	
	
}
