package org.kuali.student.core.organization.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.atp.dto.TimeAmountInfo;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
public class OrgPositionRestriction extends MetaEntity implements AttributeOwner<OrgPositionRestrictionAttribute>{
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "ORG")
	private Org org; 

	@ManyToOne
	@JoinColumn(name = "PERSON_RELATION_TYPE")
	private OrgPersonRelationType personRelationType; 

	@Column(name="ORG_POS_RSTRC_DESC")
	private String desc; 
	
	@Column(name="ORG_POS_RSTRC_TITLE")
	private String title; 
	
	@Embedded
	private TimeAmountInfo stdDuration; 

	@Column(name="MIN_NUM_RSTNS")
	private Integer minNumRelations; 

	@Column(name="MAX_NUM_RSTNS")
	private String maxNumRelations; 

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgPositionRestrictionAttribute> attributes;
	
	
	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TimeAmountInfo getStdDuration() {
		return stdDuration;
	}
	public void setStdDuration(TimeAmountInfo stdDuration) {
		this.stdDuration = stdDuration;
	}
	public Integer getMinNumRelations() {
		return minNumRelations;
	}
	public void setMinNumRelations(Integer minNumRelations) {
		this.minNumRelations = minNumRelations;
	}
	public String getMaxNumRelations() {
		return maxNumRelations;
	}
	public void setMaxNumRelations(String maxNumRelations) {
		this.maxNumRelations = maxNumRelations;
	}
	@Override
	public List<OrgPositionRestrictionAttribute> getAttributes() {
		if(attributes==null){
			attributes = new ArrayList<OrgPositionRestrictionAttribute>();
		}
		return attributes;
	}
	@Override
	public void setAttributes(List<OrgPositionRestrictionAttribute> attributes) {
		this.attributes = attributes;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public OrgPersonRelationType getPersonRelationType() {
		return personRelationType;
	}
	public void setPersonRelationType(OrgPersonRelationType personRelationType) {
		this.personRelationType = personRelationType;
	}

	
	
}
