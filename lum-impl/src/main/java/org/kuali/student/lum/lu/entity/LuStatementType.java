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
@Table(name = "KS_LU_STMT_TYPE_T")
public class LuStatementType {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToMany
	@JoinTable(name = "KS_LU_STMT_TYPE_LU_TYPE_T", joinColumns = @JoinColumn(name = "LU_STMT_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "LU_TYPE_ID"))
	private List<LuType> luTypes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<LuType> getLuTypes() {
		return luTypes;
	}

	public void setLuTypes(List<LuType> luTypes) {
		this.luTypes = luTypes;
	}

}
