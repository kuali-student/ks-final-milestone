package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.MeetingSchedule;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;
import org.kuali.student.r2.lum.clu.infc.LuCode;

@Entity
@Table(name = "KSEN_LUI")
public class LuiEntity extends MetaEntity implements AttributeOwner<LuiAttributeEntity> {
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String plain;

    @Column(name = "LUI_TYPE")
    private String luiType;

    @Column(name = "LUI_STATE")
    private String luiState;

    @Column(name = "CLU_ID")
    private String cluId;

    @Column(name = "ATP_ID")
    private String atpId;

    @Column(name = "REF_URL")
    private String referenceURL;

    @Column(name = "MAX_SEATS")
    private Integer maxSeats;

    @Column(name = "MIN_SEATS")
    private Integer minSeats;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuCodeEntity> luCodes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiRevenueEntity> luiRevenues;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiExpenditureEntity> luiExpenditures;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiFeeEntity> luiFees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiIdentifierEntity> identifiers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<MeetingScheduleEntity> meetingSchedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiCluCluRelationEntity> cluCluReltns;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiUnitsDeploymentEntity> unitsDeployments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiUnitsContentOwnerEntity> unitsContentOwners;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiResultValuesGroupRelationEntity> resultValuesGroupRelationEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiAttributeEntity> attributes;

    public LuiEntity() {}

    public LuiEntity(Lui lui) {
        super(lui);
        try {
            this.setId(lui.getId());
            this.setName(lui.getName());
            this.setAtpId(lui.getAtpId());
            this.setCluId(lui.getCluId());
            this.setMaxSeats(lui.getMaximumEnrollment());
            this.setMinSeats(lui.getMinimumEnrollment());
            this.setReferenceURL(lui.getReferenceURL());
            this.setLuiState(lui.getStateKey());
            this.setLuiType(lui.getTypeKey());
            if (lui.getEffectiveDate() != null)
                this.setEffectiveDate(lui.getEffectiveDate());
            if (lui.getExpirationDate() != null)
                this.setExpirationDate(lui.getExpirationDate());

            if (lui.getDescr() != null) {
                RichText rt = lui.getDescr();
                this.setDescrFormatted(rt.getFormatted());
                this.setDescrPlain(rt.getPlain());
            }

            // Lui Identifiers
            this.setIdentifiers(new ArrayList<LuiIdentifierEntity>());
            if (lui.getOfficialIdentifier() != null)
                this.getIdentifiers().add(new LuiIdentifierEntity(lui.getOfficialIdentifier()));
            if (lui.getAlternateIdentifiers() != null) {
                for (LuiIdentifier identifier : lui.getAlternateIdentifiers()) {
                    this.getIdentifiers().add(new LuiIdentifierEntity(identifier));
                }
            }

            // Lu Codes
            this.setLuCodes(new ArrayList<LuCodeEntity>());
            if (null != lui.getLuiCodes()) {
                for (LuCode luCode : lui.getLuiCodes()) {
                    LuCodeEntity lcdEntity = new LuCodeEntity(luCode);
                    this.getLuCodes().add(lcdEntity);
                }
            }

            // Meeting Schedules
            this.setMeetingSchedules(new ArrayList<MeetingScheduleEntity>());
            if (null != lui.getMeetingSchedules()) {
                for (MeetingSchedule ms : lui.getMeetingSchedules()) {
                    MeetingScheduleEntity msEntity = new MeetingScheduleEntity(ms);
                    this.getMeetingSchedules().add(msEntity);
                }
            }

            // Lui Result Values Group Relations
            List<LuiResultValuesGroupRelationEntity> resultValuesGroupRelationList = new ArrayList<LuiResultValuesGroupRelationEntity>();
            if (lui.getResultValuesGroupKeys() != null) {
                for (String resValueGroupKey : lui.getResultValuesGroupKeys()) {
                    LuiResultValuesGroupRelationEntity resultValuesGroupRelationEntity = new LuiResultValuesGroupRelationEntity(this, resValueGroupKey);
                    resultValuesGroupRelationEntity.setId(UUIDHelper.genStringUUID());
                    resultValuesGroupRelationList.add(resultValuesGroupRelationEntity);
                }
            }
            this.setResultValuesGroupRelationEntities(resultValuesGroupRelationList);

            // Lui Attributes
            this.setAttributes(new ArrayList<LuiAttributeEntity>());
            if (null != lui.getAttributes()) {
                for (Attribute att : lui.getAttributes()) {
                    LuiAttributeEntity attEntity = new LuiAttributeEntity(att);
                    this.getAttributes().add(attEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LuiInfo toDto() {
        LuiInfo obj = new LuiInfo();
        obj.setId(getId());
        obj.setName(name);
        obj.setAtpId(atpId);
        obj.setCluId(cluId);

        List<LuCodeInfo> codes = new ArrayList<LuCodeInfo>();
        for (LuCodeEntity code : luCodes) {
            LuCodeInfo codeInfo = code.toDto();
            codes.add(codeInfo);
        }
        obj.setLuiCodes(codes);

        if (maxSeats != null)
            obj.setMaximumEnrollment(maxSeats);
        if (minSeats != null)
            obj.setMinimumEnrollment(minSeats);
        obj.setEffectiveDate(effectiveDate);
        obj.setExpirationDate(expirationDate);
        if (luiType != null)
            obj.setTypeKey(luiType);
        if (luiState != null)
            obj.setStateKey(luiState);
        obj.setMeta(super.toDTO());
        if (getDescrPlain() != null) {
            RichTextInfo rti = new RichTextInfo();
            rti.setPlain(getDescrPlain());
            rti.setFormatted(getDescrFormatted());
            obj.setDescr(rti);
        }

        // Identifiers
        List<LuiIdentifierInfo> identifierInfos = new ArrayList<LuiIdentifierInfo>();
        for (LuiIdentifierEntity identifier : identifiers) {
            if ("kuali.lui.identifier.type.official".equals(identifier.getType())) {
                obj.setOfficialIdentifier(identifier.toDto());
            } else {
                identifierInfos.add(identifier.toDto());
            }
        }
        obj.setAlternateIdentifiers(identifierInfos);

        // Meeting Schedules
        List<MeetingScheduleInfo> schedules = new ArrayList<MeetingScheduleInfo>();
        for (MeetingScheduleEntity ms : meetingSchedules) {
            MeetingScheduleInfo msInfo = ms.toDto();
            schedules.add(msInfo);
        }
        obj.setMeetingSchedules(schedules);

        // Expenditures
        ExpenditureInfo expenditureInfo = new ExpenditureInfo();
        if (null != this.getLuiExpenditures()) {
            for (LuiExpenditureEntity luiExpenditure : this.getLuiExpenditures()) {
                expenditureInfo = luiExpenditure.toDto();
                break;
            }
        }
        obj.setExpenditure(expenditureInfo);

        // Fees
        List<FeeInfo> feeInfos = new ArrayList<FeeInfo>();
        if (null != this.getLuiFees()) {
            for (LuiFeeEntity luiFee : this.getLuiFees()) {
                feeInfos.add(luiFee.toDto());
            }
        }
        obj.setFees(feeInfos);

        // Revenues
        List<RevenueInfo> revenueInfos = new ArrayList<RevenueInfo>();
        if (null != this.getLuiRevenues()) {
            for (LuiRevenueEntity luiRevenue : this.getLuiRevenues()) {
                revenueInfos.add(luiRevenue.toDto());
            }
        }
        obj.setRevenues(revenueInfos);

        // CluClu Relations
        List<String> cluCluRelationIds = new ArrayList<String>();
        if (null != this.getUnitsContOwners()) {
            for (LuiCluCluRelationEntity luCluCluRelation : this.getCluCluReltns()) {
                cluCluRelationIds.add(luCluCluRelation.getClucluRelationId());
            }
        }
        obj.setCluCluRelationIds(cluCluRelationIds);

        // Units Deployments
        List<String> unitsDeploymentIds = new ArrayList<String>();
        if (null != this.getUnitsContOwners()) {
            for (LuiUnitsDeploymentEntity unitDeployment : this.getUnitsDeployments()) {
                unitsDeploymentIds.add(unitDeployment.getOrgId());
            }
        }
        obj.setUnitsDeployment(unitsDeploymentIds);

        // Units Content Owners
        List<String> unitsContentOwnerIds = new ArrayList<String>();
        if (null != this.getUnitsContOwners()) {
            for (LuiUnitsContentOwnerEntity unitContentOwner : this.getUnitsContOwners()) {
                unitsContentOwnerIds.add(unitContentOwner.getOrgId());
            }
        }
        obj.setUnitsContentOwner(unitsContentOwnerIds);

        // Result Values Group Relations
        List<String> rvGroupIds = new ArrayList<String>();
        if (null != getResultValuesGroupRelationEntities()) {
            for (LuiResultValuesGroupRelationEntity relationEntity : getResultValuesGroupRelationEntities()) {
                rvGroupIds.add(relationEntity.getResultValuesGroupKey());
            }
        }
        obj.setResultValuesGroupKeys(rvGroupIds);

        // Attributes
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);

        return obj;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getMinSeats() {
        return minSeats;
    }

    public void setMinSeats(Integer minSeats) {
        this.minSeats = minSeats;
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

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
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

    public String getLuiType() {
        return luiType;
    }

    public void setLuiType(String luiType) {
        this.luiType = luiType;
    }

    public String getLuiState() {
        return luiState;
    }

    public void setLuiState(String luiState) {
        this.luiState = luiState;
    }

    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    // public boolean isHasWaitlist() {
    // return hasWaitlist;
    // }
    //
    // public void setHasWaitlist(boolean hasWaitlist) {
    // this.hasWaitlist = hasWaitlist;
    // }
    //
    // public boolean isWaitlistCheckinRequired() {
    // return isWaitlistCheckinRequired;
    // }
    //
    // public void setWaitlistCheckinRequired(boolean isWaitlistCheckinRequired) {
    // this.isWaitlistCheckinRequired = isWaitlistCheckinRequired;
    // }
    //
    // public Integer getWaitlistMaximum() {
    // return waitlistMaximum;
    // }
    //
    // public void setWaitlistMaximum(Integer waitlistMaximum) {
    // this.waitlistMaximum = waitlistMaximum;
    // }

    @Override
    public void setAttributes(List<LuiAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<LuCodeEntity> getLuCodes() {
        return luCodes;
    }

    public void setLuCodes(List<LuCodeEntity> luCodes) {
        this.luCodes = luCodes;
    }

    public List<LuiIdentifierEntity> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<LuiIdentifierEntity> identifiers) {
        this.identifiers = identifiers;
    }

    public List<LuiUnitsContentOwnerEntity> getUnitsContentOwners() {
        return unitsContentOwners;
    }

    public void setUnitsContentOwners(List<LuiUnitsContentOwnerEntity> unitsContentOwners) {
        this.unitsContentOwners = unitsContentOwners;
    }

    @Override
    public List<LuiAttributeEntity> getAttributes() {
        return attributes;
    }

    public List<MeetingScheduleEntity> getMeetingSchedules() {
        return meetingSchedules;
    }

    public void setMeetingSchedules(List<MeetingScheduleEntity> meetingSchedules) {
        this.meetingSchedules = meetingSchedules;
    }

    public List<LuiCluCluRelationEntity> getCluCluReltns() {
        return cluCluReltns;
    }

    public void setCluCluReltns(List<LuiCluCluRelationEntity> cluCluReltns) {
        this.cluCluReltns = cluCluReltns;
    }

    public List<LuiUnitsDeploymentEntity> getUnitsDeployments() {
        return unitsDeployments;
    }

    public void setUnitsDeployments(List<LuiUnitsDeploymentEntity> unitsDeployments) {
        this.unitsDeployments = unitsDeployments;
    }

    public List<LuiUnitsContentOwnerEntity> getUnitsContOwners() {
        return unitsContentOwners;
    }

    public void setUnitsContOwners(List<LuiUnitsContentOwnerEntity> unitsContOwners) {
        this.unitsContentOwners = unitsContOwners;
    }

    public List<LuiResultValuesGroupRelationEntity> getResultValuesGroupRelationEntities() {
        return resultValuesGroupRelationEntities;
    }

    public void setResultValuesGroupRelationEntities(List<LuiResultValuesGroupRelationEntity> resultValuesGroupRelationEntities) {
        this.resultValuesGroupRelationEntities = resultValuesGroupRelationEntities;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public List<LuiRevenueEntity> getLuiRevenues() {
        return luiRevenues;
    }

    public void setLuiRevenues(List<LuiRevenueEntity> luiRevenues) {
        this.luiRevenues = luiRevenues;
    }

    public List<LuiExpenditureEntity> getLuiExpenditures() {
        return luiExpenditures;
    }

    public void setLuiExpenditures(List<LuiExpenditureEntity> luiExpenditures) {
        this.luiExpenditures = luiExpenditures;
    }

    public List<LuiFeeEntity> getLuiFees() {
        return luiFees;
    }

    public void setLuiFees(List<LuiFeeEntity> luiFees) {
        this.luiFees = luiFees;
    }

    /*
     * public List<LuiCluRelationEntity> getCluCluRelationIds() { return cluCluRelationIds; } public void
     * setCluCluRelationIds(List<LuiCluRelationEntity> cluCluRelationIds) { this.cluCluRelationIds = cluCluRelationIds; }
     */

}
