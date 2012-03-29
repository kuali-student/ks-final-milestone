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

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.infc.LuiLuiRelation;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_LUILUI_RELTN")
public class LuiLuiRelationEntity extends MetaEntity {

    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;
    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;
    @ManyToOne
    @JoinColumn(name = "RELATED_LUI_ID")
    private LuiEntity relatedLui;
    @Column(name = "LUILUI_RELTN_TYPE")
    private String luiLuiRelationType;
    @Column(name = "LUILUI_RELTN_STATE")
    private String luiLuiRelationState;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiLuiRelationAttributeEntity> attributes;

    public LuiLuiRelationEntity() {
    }

    public LuiLuiRelationEntity(LuiLuiRelation luiLuiRelation) {
        this.setId(luiLuiRelation.getId());
        this.setLuiLuiRelationType(luiLuiRelation.getTypeKey());
        fromDto(luiLuiRelation);
    }

    public void fromDto(LuiLuiRelation luiLuiRelation) {
        this.setEffectiveDate(luiLuiRelation.getEffectiveDate());
        this.setExpirationDate(luiLuiRelation.getExpirationDate());
        this.setLuiLuiRelationState(luiLuiRelation.getStateKey());
        if (luiLuiRelation.getDescr() == null) {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        } else {
            this.setDescrFormatted(luiLuiRelation.getDescr().getFormatted());
            this.setDescrPlain(luiLuiRelation.getDescr().getPlain());
        }
        this.setAttributes(new ArrayList<LuiLuiRelationAttributeEntity>());
        for (Attribute att : luiLuiRelation.getAttributes()) {
            this.getAttributes().add(new LuiLuiRelationAttributeEntity(att));
        }
    }

    public LuiLuiRelationInfo toDto() {
        LuiLuiRelationInfo info = new LuiLuiRelationInfo();
        info.setId(getId());
        if (lui != null) {
            info.setLuiId(lui.getId());
        }
        if (relatedLui != null) {
            info.setRelatedLuiId(relatedLui.getId());
        }
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expirationDate);
        info.setStateKey(luiLuiRelationState);
        info.setTypeKey(luiLuiRelationType);
        info.setMeta(super.toDTO());
        info.setDescr(new RichTextHelper().toRichTextInfo(plain, formatted));
        if (getAttributes() != null) {
            for (LuiLuiRelationAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                info.getAttributes().add(attInfo);
            }
        }
        return info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrFormatted() {
        return formatted;
    }

    public void setDescrFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getDescrPlain() {
        return plain;
    }

    public void setDescrPlain(String plain) {
        this.plain = plain;
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

    public String getLuiLuiRelationType() {
        return luiLuiRelationType;
    }

    public void setLuiLuiRelationType(String luiLuiRelationType) {
        this.luiLuiRelationType = luiLuiRelationType;
    }

    public String getLuiLuiRelationState() {
        return luiLuiRelationState;
    }

    public void setLuiLuiRelationState(String luiLuiRelationState) {
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

    public void setAttributes(List<LuiLuiRelationAttributeEntity> attributes) {
        this.attributes = attributes;

    }

    public List<LuiLuiRelationAttributeEntity> getAttributes() {
        return attributes;
    }
}
