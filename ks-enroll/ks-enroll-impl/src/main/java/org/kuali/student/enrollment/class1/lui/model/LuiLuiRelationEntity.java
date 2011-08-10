package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.infc.LuiLuiRelation;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;

@Entity
@Table(name = "KSEN_LUILUI_RELTN")
public class LuiLuiRelationEntity extends MetaEntity implements AttributeOwner<LuiLuiRelationAttributeEntity>{
    @Column(name = "NAME")
    private String name;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuiRichTextEntity descr;   
    
	@ManyToOne
	@JoinColumn(name = "LUI_ID")
	private LuiEntity lui;

	@ManyToOne
	@JoinColumn(name = "RELATED_LUI_ID")
	private LuiEntity relatedLui;

    @ManyToOne(optional=false)
    @JoinColumn(name = "TYPE_ID")
    private LuiTypeEntity luiLuiRelationType;

    @ManyToOne(optional=false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity luiLuiRelationState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXP_DT")
	private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiLuiRelationAttributeEntity> attributes;
    
    public LuiLuiRelationEntity(){}
    
    public LuiLuiRelationEntity(LuiLuiRelation luiLuiRelation){
        this.setId(luiLuiRelation.getId());
        this.setEffectiveDate(luiLuiRelation.getEffectiveDate());
        this.setExpirationDate(luiLuiRelation.getExpirationDate());
        
        this.setAttributes(new ArrayList<LuiLuiRelationAttributeEntity>());
        if (null != luiLuiRelation.getAttributes()) {
            for (Attribute att : luiLuiRelation.getAttributes()) {
                this.getAttributes().add(new LuiLuiRelationAttributeEntity(att));
            }
        }    	
    }
    
    public LuiLuiRelationInfo toDto() {
    	LuiLuiRelationInfo obj = new LuiLuiRelationInfo();
    	obj.setId(getId());
    	obj.setLuiId(lui.getId());
    	obj.setRelatedLuiId(relatedLui.getId());
        obj.setEffectiveDate(effectiveDate);
        obj.setExpirationDate(expirationDate);
        obj.setStateKey(luiLuiRelationState.getId());
        obj.setTypeKey(luiLuiRelationType.getId());
        obj.setMeta(super.toDTO());
        
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiLuiRelationAttributeEntity att : getAttributes()) {
        	AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        return obj;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LuiRichTextEntity getDescr() {
		return descr;
	}

	public void setDescr(LuiRichTextEntity descr) {
		this.descr = descr;
	}

	public LuiEntity getLui() {
		return lui;
	}

	public void setLui(LuiEntity lui) {
		this.lui = lui;
	}

	public LuiEntity getRelatedLui() {
		return relatedLui;
	}

	public void setRelatedLui(LuiEntity relatedLui) {
		this.relatedLui = relatedLui;
	}

	public LuiTypeEntity getLuiLuiRelationType() {
		return luiLuiRelationType;
	}

	public void setLuiLuiRelationType(LuiTypeEntity luiLuiRelationType) {
		this.luiLuiRelationType = luiLuiRelationType;
	}

	public StateEntity getLuiLuiRelationState() {
		return luiLuiRelationState;
	}

	public void setLuiLuiRelationState(StateEntity luiLuiRelationState) {
		this.luiLuiRelationState = luiLuiRelationState;
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

	@Override
	public void setAttributes(List<LuiLuiRelationAttributeEntity> attributes) {
		this.attributes = attributes;
		
	}

	@Override
	public List<LuiLuiRelationAttributeEntity> getAttributes() {
		return attributes;
	}

	
}
