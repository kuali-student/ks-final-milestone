package org.kuali.student.lum.atp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
public class AtpType implements AttributeOwner<AtpTypeAttribute>{
	@Id
	@Column(name = "TYPE_KEY")
	private String key;

	private String name;
	@Column(name = "TYPE_DESC")
	private String desc;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<AtpTypeAttribute> attributes;
	
	@ManyToOne
	private AtpSeasonalType seasonalType;
	
	@ManyToOne
	private AtpDurationType durationType;
	public AtpSeasonalType getSeasonalType() {
		return seasonalType;
	}
	public void setSeasonalType(AtpSeasonalType seasonalType) {
		this.seasonalType = seasonalType;
	}
	public AtpDurationType getDurationType() {
		return durationType;
	}
	public void setDurationType(AtpDurationType durationType) {
		this.durationType = durationType;
	}
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
	public List<AtpTypeAttribute> getAttributes() {
		if(attributes==null){
			attributes = new ArrayList<AtpTypeAttribute>();
		}
		return attributes;
	}
	public void setAttributes(List<AtpTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}
