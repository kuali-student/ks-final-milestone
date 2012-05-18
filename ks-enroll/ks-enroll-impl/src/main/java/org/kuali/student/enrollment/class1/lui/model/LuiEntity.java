package org.kuali.student.enrollment.class1.lui.model;

import java.util.*;

import javax.persistence.*;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.class1.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.entity.BaseEntity;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.infc.LuCode;

@Entity
@Table(name = "KSEN_LUI")
public class LuiEntity extends MetaEntity {

    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
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
    @Column(name = "SCHEDULE_ID")
    private String scheduleId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ManyToMany
    @JoinTable(name="KSEN_LUI_RESULT_VAL_GRP",
            joinColumns=
            @JoinColumn(name="LUI_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="RESULT_VAL_GRP_ID", referencedColumnName="ID")
    )
    private List<ResultValuesGroupEntity> resultValuesGroups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiIdentifierEntity> identifiers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiUnitsContentOwnerEntity> luiContentOwner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiUnitsDeploymentEntity> luiUnitsDeployment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuCodeEntity> luiCodes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiAttributeEntity> attributes;

    public LuiEntity() {
    }

    public LuiEntity(Lui lui) {
        super(lui);
        this.setId(lui.getId());
        this.setLuiType(lui.getTypeKey());
        this.setAtpId(lui.getAtpId());
        this.setCluId(lui.getCluId());

        fromDto(lui);
    }

    public List<Object> fromDto(Lui lui) {

        List<Object> orphansToDelete = new ArrayList<Object>();

        this.setName(lui.getName());
        this.setMaxSeats(lui.getMaximumEnrollment());
        this.setMinSeats(lui.getMinimumEnrollment());
        this.setReferenceURL(lui.getReferenceURL());
        this.setLuiState(lui.getStateKey());
        this.setEffectiveDate(lui.getEffectiveDate());
        this.setExpirationDate(lui.getExpirationDate());
        if (lui.getDescr() == null) {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        } else {
            this.setDescrFormatted(lui.getDescr().getFormatted());
            this.setDescrPlain(lui.getDescr().getPlain());
        }

        this.setLuiCodes(new ArrayList<LuCodeEntity>());
        for (LuCode luCode : lui.getLuiCodes()) {
            this.getLuiCodes().add(new LuCodeEntity(luCode));
        }

        // Lui Identifiers

        //Map the exisiting idents by their id
        Map<String,LuiIdentifierEntity> existingIdents = new HashMap<String,LuiIdentifierEntity>();
        if(this.getIdentifiers() != null){
            for(LuiIdentifierEntity ident : this.getIdentifiers()){
                existingIdents.put(ident.getId(),ident);
            }
        }
        //Clear the list of current idents
        this.setIdentifiers(new ArrayList<LuiIdentifierEntity>());

        //Add official identifiers
        if (lui.getOfficialIdentifier() != null) {
            LuiIdentifierEntity identEntity;
            //See if this exists already
            if(existingIdents.containsKey(lui.getOfficialIdentifier().getId())){
                //Pull the existing one out of the map
                identEntity = existingIdents.remove(lui.getOfficialIdentifier().getId());
                identEntity.fromDto(lui.getOfficialIdentifier()); //Make sure this copies all fields
            }else{
                //This is new so create a new identifier
                identEntity = new LuiIdentifierEntity(lui.getOfficialIdentifier());
            }

            identEntity.setLui(this);
            this.getIdentifiers().add(identEntity);
        }

        //Add alternate identifiers
        for (LuiIdentifier identifier : lui.getAlternateIdentifiers()) {
            LuiIdentifierEntity identEntity;
            //See if this exists already
            if(existingIdents.containsKey(identifier.getId())){
                //Pull the existing one out of the map
                identEntity = existingIdents.remove(identifier.getId());
                identEntity.fromDto(identifier); //Make sure this copies all fields
            }else{
                //This is new so create a new identifier
                identEntity = new LuiIdentifierEntity(identifier);
                identEntity.setLui(this);
            }

            this.getIdentifiers().add(identEntity);
        }

        //Now we need to delete the leftovers (orphaned idents)
        orphansToDelete.addAll(existingIdents.values());

        //lui-org relations

        //Map the exisiting orgrelations by their id
        Map<String,LuiUnitsContentOwnerEntity> existinguUnitsContentOwnerEntities = new HashMap<String,LuiUnitsContentOwnerEntity>();
        if(this.getLuiContentOwner() != null){
            for(LuiUnitsContentOwnerEntity unitEntity : this.getLuiContentOwner()){
                existinguUnitsContentOwnerEntities.put(unitEntity.getOrgId(), unitEntity);
            }
        }

        //Clear out the current list
        this.setLuiContentOwner(new ArrayList<LuiUnitsContentOwnerEntity>());

        if(lui.getUnitsContentOwner()!=null){
            for(String unitContentOrgId : lui.getUnitsContentOwner() ){
                LuiUnitsContentOwnerEntity luiUnitContentOwner;
                if(existinguUnitsContentOwnerEntities.containsKey(unitContentOrgId)){
                    luiUnitContentOwner = existinguUnitsContentOwnerEntities.remove(unitContentOrgId);
                }else{
                    luiUnitContentOwner = new LuiUnitsContentOwnerEntity(this, unitContentOrgId);
                }
                this.getLuiContentOwner().add(luiUnitContentOwner);
            }
        }

        //Now we need to delete the leftovers (orphaned entities)
        orphansToDelete.addAll(existinguUnitsContentOwnerEntities.values());

        //Map the exisiting orgrelations by their id
        Map<String,LuiUnitsDeploymentEntity> existinguLuiUnitsDeploymentEntities = new HashMap<String,LuiUnitsDeploymentEntity>();
        if(this.getLuiUnitsDeployment() != null){
            for(LuiUnitsDeploymentEntity unitEntity : this.getLuiUnitsDeployment()){
                existinguLuiUnitsDeploymentEntities.put(unitEntity.getOrgId(),unitEntity);
            }
        }

        //Clear out the current list
        this.setLuiUnitsDeployment(new ArrayList<LuiUnitsDeploymentEntity>());

        if(lui.getUnitsDeployment() != null){
            for(String unitDeploymentOrgId : lui.getUnitsDeployment()){
                LuiUnitsDeploymentEntity luiUnitDeployment;
                if(existinguLuiUnitsDeploymentEntities.containsKey(unitDeploymentOrgId)){
                    luiUnitDeployment = existinguLuiUnitsDeploymentEntities.remove(unitDeploymentOrgId);
                }else{
                    luiUnitDeployment = new LuiUnitsDeploymentEntity(this, unitDeploymentOrgId);
                }
                this.getLuiUnitsDeployment().add(luiUnitDeployment);
            }
        }

        //Now we need to delete the leftovers (orphaned entities)
        orphansToDelete.addAll(existinguLuiUnitsDeploymentEntities.values());

        // Lui Attributes
        //TODO map the attributes (merge and delete orphans, don't do delete all and add all)
        this.setAttributes(new ArrayList<LuiAttributeEntity>());
        for (Attribute att : lui.getAttributes()) {
            this.getAttributes().add(new LuiAttributeEntity(att));
        }

        return orphansToDelete;
    }

    public LuiInfo toDto() {
        LuiInfo info = new LuiInfo();
        info.setId(getId());
        info.setName(name);
        info.setAtpId(atpId);
        info.setCluId(cluId);
        info.setMaximumEnrollment(maxSeats);
        info.setMinimumEnrollment(minSeats);
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expirationDate);
        info.setReferenceURL(referenceURL);
        info.setTypeKey(luiType);
        info.setStateKey(luiState);
        info.setMeta(super.toDTO());
        info.setDescr(new RichTextHelper().toRichTextInfo(plain, formatted));

        // lucCodes
        if (luiCodes != null) {
            for (LuCodeEntity luCode : luiCodes) {
                info.getLuiCodes().add(luCode.toDto());
            }
        }

        // Identifiers
        if (identifiers != null) {
            for (LuiIdentifierEntity identifier : identifiers) {
                if (LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY.equals(identifier.getType())) {
                    info.setOfficialIdentifier(identifier.toDto());
                } else {
                    info.getAlternateIdentifiers().add(identifier.toDto());
                }
            }
        }

        // Attributes
        if (getAttributes() != null) {
            for (LuiAttributeEntity att : getAttributes()) {
                info.getAttributes().add(att.toDto());
            }
        }
        List<String> unitsDeploymentOrgIds = new ArrayList<String>();
        if( this.luiUnitsDeployment!= null)   {
            for(LuiUnitsDeploymentEntity unitsDep : this.luiUnitsDeployment){

              unitsDeploymentOrgIds.add(unitsDep.getOrgId());
            }
        }
        info.setUnitsContentOwner(unitsDeploymentOrgIds);

        List<String> unitsContentOrgIds = new ArrayList<String>();

        if(this.luiContentOwner!=null){
            for(LuiUnitsContentOwnerEntity unitsContent : this.luiContentOwner){

                unitsContentOrgIds.add(unitsContent.getOrgId());
            }
        }


        info.setUnitsContentOwner(unitsContentOrgIds);
        return info;
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
    public void setAttributes(List<LuiAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<LuiIdentifierEntity> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<LuiIdentifierEntity> identifiers) {
        this.identifiers = identifiers;
    }

    public List<LuiAttributeEntity> getAttributes() {
        return attributes;
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

    /*
     * public List<LuiCluRelationEntity> getCluCluRelationIds() { return cluCluRelationIds; } public void
     * setCluCluRelationIds(List<LuiCluRelationEntity> cluCluRelationIds) { this.cluCluRelationIds = cluCluRelationIds; }
     */
    public List<LuCodeEntity> getLuiCodes() {
        return luiCodes;
    }

    public void setLuiCodes(List<LuCodeEntity> luiCodes) {
        this.luiCodes = luiCodes;
    }

    public List<LuiUnitsContentOwnerEntity> getLuiContentOwner() {
        return luiContentOwner;
    }

    public void setLuiContentOwner(List<LuiUnitsContentOwnerEntity> luiContentOwner) {
        this.luiContentOwner = luiContentOwner;
    }

    public List<LuiUnitsDeploymentEntity> getLuiUnitsDeployment() {
        return luiUnitsDeployment;
    }

    public void setLuiUnitsDeployment(List<LuiUnitsDeploymentEntity> luiUnitsDeployment) {
        this.luiUnitsDeployment = luiUnitsDeployment;
    }
}
