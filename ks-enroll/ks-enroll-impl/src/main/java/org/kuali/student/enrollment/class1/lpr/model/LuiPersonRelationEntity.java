package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.enrollment.class1.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelation;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSEN_LPR")
public class LuiPersonRelationEntity extends MetaEntity implements AttributeOwner<LuiPersonRelationAttributeEntity> {

    private String personId;

    private String luiId;

    private Float commitmentPercent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name = "RELATION_TYPE_ID")
    private String personRelationTypeId;

    @Column(name = "RELATION_STATE_ID")
    private String personRelationStateId;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "KSEN_LPR_RV_GRP_RELTN", joinColumns = @JoinColumn(name = "LPR_ID"), inverseJoinColumns = @JoinColumn(name = "RV_GRP_ID"))
//    private List<ResultValuesGroupEntity> resultValuesGroups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    // @JoinColumn(name = "LPR_ATTR_ID")
    // @JoinTable(name="LPR_ATTR_JOIN",
    // joinColumns=@JoinColumn(name="OWNER_ID", referencedColumnName="ID"),
    // inverseJoinColumns=@JoinColumn(name="ATTRIB_ID",
    // referencedColumnName="ID"))
    private List<LuiPersonRelationAttributeEntity> attributes;

    public LuiPersonRelationEntity() {}

    public LuiPersonRelationEntity(LuiPersonRelation dto) {
        super(dto);
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setExpirationDate(dto.getExpirationDate());
        this.setId(dto.getId());
        this.setLuiId(dto.getLuiId());
        this.setPersonId(dto.getPersonId());
        this.setCommitmentPercent(dto.getCommitmentPercent());
        this.setExpirationDate(dto.getExpirationDate());
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setPersonRelationTypeId(dto.getTypeKey());
        this.setPersonRelationStateId(dto.getStateKey());
        this.setAttributes(new ArrayList<LuiPersonRelationAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                this.getAttributes().add(new LuiPersonRelationAttributeEntity(att));
            }
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

    public Float getCommitmentPercent() {
        return commitmentPercent;
    }

    public void setCommitmentPercent(Float commitmentPercent) {
        this.commitmentPercent = commitmentPercent;
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

    public String getPersonRelationTypeId() {
        return personRelationTypeId;
    }

    public void setPersonRelationTypeId(String personRelationTypeId) {
        this.personRelationTypeId = personRelationTypeId;
    }

    public String getPersonRelationStateId() {
        return personRelationStateId;
    }

    public void setPersonRelationStateId(String personRelationStateId) {
        this.personRelationStateId = personRelationStateId;
    }

//    public List<ResultValuesGroupEntity> getResultValuesGroups() {
//        return resultValuesGroups;
//    }
//
//    public void setResultValuesGroups(List<ResultValuesGroupEntity> resultValuesGroups) {
//        this.resultValuesGroups = resultValuesGroups;
//    }

    @Override
    public List<LuiPersonRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<LuiPersonRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public LuiPersonRelationInfo toDto() {
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setId(getId());
        lprInfo.setLuiId(luiId);
        lprInfo.setCommitmentPercent(commitmentPercent);
        lprInfo.setPersonId(personId);
        lprInfo.setEffectiveDate(effectiveDate);
        lprInfo.setExpirationDate(expirationDate);
        lprInfo.setTypeKey(personRelationTypeId);
        lprInfo.setStateKey(personRelationStateId);

        // TODO: fix this it was trying to use the entity from LRC directly here!
        // instead need to create a new JPA entity to hold the lpr to rvg mapping
//        List<String> rvGroupIds = new ArrayList();
//        if (null != getResultValuesGroups()) {
//            for (ResultValuesGroupEntity rvGroup : getResultValuesGroups()) {
//                rvGroupIds.add(rvGroup.getId());
//            }
//        }
//        lprInfo.setResultValuesGroupKeys(rvGroupIds);

        lprInfo.setMeta(super.toDTO());
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiPersonRelationAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        lprInfo.setAttributes(atts);

        return lprInfo;
    }
}
