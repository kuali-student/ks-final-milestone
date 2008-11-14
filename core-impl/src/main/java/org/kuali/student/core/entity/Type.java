package org.kuali.student.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Type {
	@Id
	private String key; 
	
	private String name; 

	private String desc; 

	private String durationType; 

	private String seasonalType; 

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate; 

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate; 

	private List<AttributeEntity<Type>> attributes;

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

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public String getSeasonalType() {
		return seasonalType;
	}

	public void setSeasonalType(String seasonalType) {
		this.seasonalType = seasonalType;
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

	public List<AttributeEntity<Type>> getAttributes() {
		if(attributes == null){
			attributes = new ArrayList<AttributeEntity<Type>>();
		}
		return attributes;
	}

	public void setAttributes(List<AttributeEntity<Type>> attributes) {
		this.attributes = attributes;
	} 


}
