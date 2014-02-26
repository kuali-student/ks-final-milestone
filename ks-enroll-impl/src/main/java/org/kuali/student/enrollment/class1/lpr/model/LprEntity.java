package org.kuali.student.enrollment.class1.lpr.model;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.infc.Lpr;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
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
@NamedQueries({
        @NamedQuery(name = "Lpr.getLprsByLuis", query = "Select lpr from LprEntity lpr where lpr.luiId in (:luiIds)")
})
public class LprEntity extends MetaEntity implements AttributeOwner<LprAttributeEntity> {

    @Column(name = "PERS_ID")
    private String personId;

    @Column(name = "LUI_ID")
    private String luiId;

    @Column(name = "COMMIT_PERCT")
    private BigDecimal commitmentPercent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @Column(name = "LPR_TYPE")
    private String personRelationTypeId;

    @Column(name = "LPR_STATE")
    private String personRelationStateId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.EAGER)
    private final Set<LprAttributeEntity> attributes = new HashSet<LprAttributeEntity>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "KSEN_LPR_RESULT_VAL_GRP", joinColumns = @JoinColumn(name = "LPR_ID"))
    @Column(name = "RESULT_VAL_GRP_ID")
    private final Set<String> resultValueGroups = new HashSet<String>();

    @Column(name = "ATP_ID")
    private String atpId;

    @Column(name = "MASTER_LPR_ID")
    private String masterLprId;

    @Column(name = "CREDITS")
    private String credits;

    @Column(name = "GRADING_OPT_ID")
    private String gradingOptionId;

    public LprEntity() {
    }

    public LprEntity(Lpr dto) {
        super(dto);
        // These are the read-only fields
        this.setId(dto.getId());
        this.setLuiId(dto.getLuiId());
        this.setPersonId(dto.getPersonId());
        this.setPersonRelationTypeId(dto.getTypeKey());
        this.setAtpId(dto.getAtpId());
        this.setMasterLprId(dto.getMasterLprId());
        fromDto(dto);
    }

    @Override
    protected void onPrePersist() {
        super.onPrePersist();
        //This makes it so if no masterLPR id is set, it always defaults to the ID to avoid an additional call to update
        if (masterLprId == null) {
            setMasterLprId(this.getId());
        }
    }

    public void fromDto(Lpr dto) {

        super.fromDTO(dto);

        if (dto.getCommitmentPercent() != null) {
            this.setCommitmentPercent(dto.getCommitmentPercent().bigDecimalValue());
        }
        this.setExpirationDate(dto.getExpirationDate());
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setPersonRelationStateId(dto.getStateKey());
        this.setMasterLprId(dto.getMasterLprId());

        //Set these fields on the LPR (makes access easier).
        for(String rvgKey:dto.getResultValuesGroupKeys()){
            if(rvgKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_BASE)){
                this.setGradingOptionId(rvgKey);
            }else if(rvgKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE)){
                this.setCredits(rvgKey.substring(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE.length()+1));
            }
        }

        this.attributes.clear();

        for (Attribute attr : dto.getAttributes()) {

            this.attributes.add(new LprAttributeEntity(attr, this));

        }

        this.resultValueGroups.clear();

        this.resultValueGroups.addAll(dto.getResultValuesGroupKeys());

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

    public LprInfo toDto() {
        LprInfo lprInfo = new LprInfo();
        lprInfo.setId(getId());
        lprInfo.setLuiId(luiId);
        if (commitmentPercent != null) {
            lprInfo.setCommitmentPercent(new KualiDecimal(commitmentPercent));
        }
        lprInfo.setPersonId(personId);
        lprInfo.setEffectiveDate(effectiveDate);
        lprInfo.setExpirationDate(expirationDate);
        lprInfo.setTypeKey(personRelationTypeId);
        lprInfo.setStateKey(personRelationStateId);
        lprInfo.setMasterLprId(masterLprId);

        // instead need to create a new JPA entity to hold the lpr to rvg
        // mapping
        List<String> rvGroupIds = new ArrayList<String>();
        if (null != getResultValueGroups()) {
            for (String rvGroupId : getResultValueGroups()) {
                rvGroupIds.add(rvGroupId);
            }
        }

        lprInfo.setResultValuesGroupKeys(rvGroupIds);

        lprInfo.setMeta(super.toDTO());

        List<AttributeInfo> atts = lprInfo.getAttributes();

        if (getAttributes() != null) {

            for (LprAttributeEntity lprAttr : getAttributes()) {

                atts.add(lprAttr.toDto());
            }
        }

        return lprInfo;
    }


    public Set<LprAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LprAttributeEntity> attributes) {
        this.attributes.clear();

        if (attributes != null)
            this.attributes.addAll(attributes);
    }

    public Set<String> getResultValueGroups() {
        return resultValueGroups;
    }

    public void setResultValueGroups(Set<String> resultValueGroups) {
        this.resultValueGroups.clear();

        if (resultValueGroups != null)
            this.resultValueGroups.addAll(resultValueGroups);
    }

    public BigDecimal getCommitmentPercent() {
        return commitmentPercent;
    }

    public void setCommitmentPercent(BigDecimal commitmentPercent) {
        this.commitmentPercent = commitmentPercent;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getGradingOptionId() {
        return gradingOptionId;
    }

    public void setGradingOptionId(String gradingOptionId) {
        this.gradingOptionId = gradingOptionId;
    }

    public String getMasterLprId() {
        return masterLprId;
    }

    public void setMasterLprId(String masterLprId) {
        this.masterLprId = masterLprId;
    }
}
