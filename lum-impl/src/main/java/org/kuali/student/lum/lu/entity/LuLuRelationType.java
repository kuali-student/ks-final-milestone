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
@Table(name = "KS_LU_REL_TYPE_T")
public class LuLuRelationType {
	@Id
	@Column(name = "ID")
	private String luLuRelationTypeKey;

	@ManyToMany
	@JoinTable(name = "KS_LU_REL_TYPE_LU_TYPE_T", joinColumns = @JoinColumn(name = "LU_REL_TYPE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "LU_TYPE_ID", referencedColumnName = "ID"))
	private List<LuType> luTypes;

	public String getLuLuRelationTypeKey() {
		return luLuRelationTypeKey;
	}

	public void setLuLuRelationTypeKey(String luLuRelationTypeKey) {
		this.luLuRelationTypeKey = luLuRelationTypeKey;
	}

	public List<LuType> getLuTypes() {
		return luTypes;
	}

	public void setLuTypes(List<LuType> luTypes) {
		this.luTypes = luTypes;
	}
}
