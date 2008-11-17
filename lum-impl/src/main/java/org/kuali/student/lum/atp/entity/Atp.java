package org.kuali.student.lum.atp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.dto.MetaInfo;
@Entity
public class Atp {
	@Id
	@Column(name="ATP_KEY")
	private String key;
	private String name;
	@Column(name="ATP_DESC")
	private String desc;
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	@OneToMany
	private List<AtpAttribute> attributes;
	@Embedded
	private MetaInfo metaInfo;
	private String type;
	private String state;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public List<AtpAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AtpAttribute> attributes) {
		this.attributes = attributes;
	}
	public MetaInfo getMetaInfo() {
		return metaInfo;
	}
	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
