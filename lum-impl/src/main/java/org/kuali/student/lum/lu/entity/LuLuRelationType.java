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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "KS_LU_REL_TYPE_T")
public class LuLuRelationType {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToMany
	@JoinTable(name = "KS_LU_REL_TYPE_LU_TYPE_T", joinColumns = @JoinColumn(name = "LU_REL_TYPE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "LU_TYPE_ID", referencedColumnName = "ID"))
	private List<LuType> luTypes;

	@Column(name = "LU_REL_NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String desc;

	@Column(name = "REV_NAME")
	private String revName;

	@Column(name = "REV_DESC")
	private String revDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRATION_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
	private List<LuLuRelationTypeAttribute> attributes;

	public List<LuType> getLuTypes() {
		return luTypes;
	}

	public void setLuTypes(List<LuType> luTypes) {
		this.luTypes = luTypes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRevName() {
		return revName;
	}

	public void setRevName(String revName) {
		this.revName = revName;
	}

	public String getRevDesc() {
		return revDesc;
	}

	public void setRevDesc(String revDesc) {
		this.revDesc = revDesc;
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

	public List<LuLuRelationTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LuLuRelationTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<LuLuRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}
