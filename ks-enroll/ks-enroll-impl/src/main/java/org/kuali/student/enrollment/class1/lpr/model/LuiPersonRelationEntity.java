package org.kuali.student.enrollment.class1.lpr.model;

import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelation;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSEN_LPR")
public class LuiPersonRelationEntity extends MetaEntity  {

    @Column(name = "PERS_ID")
    private String personId;

    @Column(name = "LUI_ID")
    private String luiId;

    @Column(name = "COMMIT_PERCT")
    private Float commitmentPercent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIR_DT")
    private Date expirationDate;

    @Column(name = "LPR_TYPE")
    private String personRelationTypeId;

    @Column(name = "LPR_STATE")
    private String personRelationStateId;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<LuiPersonRelationAttributeEntity> attributes;

    public LuiPersonRelationEntity() {}

    public LuiPersonRelationEntity(Lpr dto) {
        super(dto);
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setExpirationDate(dto.getExpirationDate());
        this.setId(dto.getId());
        this.setLuiId(dto.getLuiId());
        this.setPersonId(dto.getPersonId());
        this.setPersonRelationTypeId(dto.getTypeKey());
        fromDto(dto);
    }

    public void fromDto(Lpr dto){
        this.setCommitmentPercent(Float.parseFloat(dto.getCommitmentPercent()));
        this.setExpirationDate(dto.getExpirationDate());
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setPersonRelationStateId(dto.getStateKey());
        this.setAttributes(new HashSet<LuiPersonRelationAttributeEntity>());
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

    public Set<LuiPersonRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LuiPersonRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public LprInfo toDto() {
        LprInfo lprInfo = new LprInfo();
        lprInfo.setId(getId());
        lprInfo.setLuiId(luiId);
        lprInfo.setCommitmentPercent("" + commitmentPercent);
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
        if (getAttributes() != null) {
            for (LuiPersonRelationAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }
        }
        lprInfo.setAttributes(atts);

        return lprInfo;
    }
}
