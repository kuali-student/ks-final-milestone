package org.kuali.student.enrollment.lpr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.dto.AttributeInfo;
import org.kuali.student.common.infc.Attribute;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelation;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelationEntity extends MetaEntity implements AttributeOwner<LuiPersonRelationAttributeEntity> {

    private String personId;

    private String luiId;

    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private LuiPersonRelationTypeEntity personRelationType;

    @ManyToOne(cascade = CascadeType.ALL)
    private LuiPersonRelationStateEntity personRelationState;

    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "LPR_ATTR_ID")
//    @JoinTable(name="LPR_ATTR_JOIN",
//    			joinColumns=@JoinColumn(name="OWNER_ID", referencedColumnName="ID"),
//    			inverseJoinColumns=@JoinColumn(name="ATTRIB_ID", referencedColumnName="ID"))
    private List<LuiPersonRelationAttributeEntity> attributes;

	public LuiPersonRelationEntity() {
	}
    
    public LuiPersonRelationEntity(LuiPersonRelation dto) {
    	
    	this.setEffectiveDate(dto.getEffectiveDate());
    	this.setExpirationDate(dto.getExpirationDate());
    	this.setId(dto.getId());
    	this.setLuiId(dto.getLuiId());
    	this.setPersonId(dto.getPersonId());
    	// TODO - need to retrieve the LuiPersonRelationState based on the return of dto.getState()
    	// lpr.setPersonRelationState(new LuiPersonRelationState(dto.getState());
    	// TODO - need to retrieve the LuiPersonRelationType based on the return of dto.getType()
    	// lpr.setPersonRelationType(new LuiPersonRelationType(dto.getType());
    	this.setAttributes(new ArrayList<LuiPersonRelationAttributeEntity>());
    	for (Attribute att : dto.getAttributes()) {
    		this.getAttributes().add(new LuiPersonRelationAttributeEntity(att));
    	}
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
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

    public LuiPersonRelationTypeEntity getPersonRelationType() {
        return personRelationType;
    }

    public void setPersonRelationType(LuiPersonRelationTypeEntity personRelationType) {
        this.personRelationType = personRelationType;
    }

    public LuiPersonRelationStateEntity getPersonRelationState() {
        return personRelationState;
    }

    public void setPersonRelationState(LuiPersonRelationStateEntity personRelationState) {
        this.personRelationState = personRelationState;
    }

    @Override
    public List<LuiPersonRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<LuiPersonRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }
    
    public LuiPersonRelationInfo toDto() {
    	LuiPersonRelationInfo.Builder builder = new LuiPersonRelationInfo.Builder();
    	builder.setId(getId());
    	builder.setLuiId(luiId);
    	builder.setPersonId(personId);
    	builder.setEffectiveDate(effectiveDate);
    	builder.setExpirationDate(expirationDate);
    	builder.setType(personRelationType.getId());
    	builder.setState(personRelationState.getId());
    	builder.setMetaInfo(super.toDTO());
    	List<Attribute> atts = new ArrayList<Attribute>();
    	for (LuiPersonRelationAttributeEntity att : getAttributes()) {
    		Attribute attInfo = att.toDto();
    		atts.add(attInfo);
    	}
		builder.setAttributes(atts);
    	
    	LuiPersonRelationInfo info = builder.build();
    	return info;
    }
}
