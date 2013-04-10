package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.infc.LuCode;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "KSEN_LUI")
@NamedQueries({
    @NamedQuery(name="Lui.getLuisByType", query="Select lui from LuiEntity lui where lui.luiType =:typeId"),
    @NamedQuery(name="Lui.getLuisByClu", query="Select lui from LuiEntity lui where lui.cluId=:cluId"),
    @NamedQuery(name="Lui.getLuisByAtpAndType", query="Select lui from LuiEntity lui where lui.atpId=:atpId and lui.luiType = :typeKey"),
    @NamedQuery(name="Lui.getLuiIdsByAtpAndType", query="Select id from LuiEntity lui where lui.atpId=:atpId and lui.luiType = :typeKey"),
    @NamedQuery(name="Lui.getLuisByAtpAndClu", query="Select lui from LuiEntity lui where lui.atpId=:atpId and lui.cluId = :cluId"),
    @NamedQuery(name="Lui.getLuisByLuiId", query="SELECT lui FROM LuiEntity lui WHERE lui.id = :aoId")
})
public class LuiEntity extends MetaEntity implements AttributeOwner<LuiAttributeEntity> {

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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;


    @ElementCollection
    @CollectionTable(name ="KSEN_LUI_SCHEDULE",joinColumns = @JoinColumn(name = "LUI_ID"))
    @Column(name="SCHED_ID")
    private List<String> scheduleIds;

    @ElementCollection
    @CollectionTable(name ="KSEN_LUI_RESULT_VAL_GRP",joinColumns = @JoinColumn(name = "LUI_ID"))
    @Column(name="RESULT_VAL_GRP_ID")
    private List<String> resultValuesGroupKeys;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiIdentifierEntity> identifiers;

    @ElementCollection
    @CollectionTable(name ="KSEN_LUI_UNITS_CONT_OWNER",joinColumns = @JoinColumn(name = "LUI_ID"))
    @Column(name="ORG_ID")
    private List<String> luiContentOwner;

    @ElementCollection
    @CollectionTable(name ="KSEN_LUI_RELATED_LUI_TYPES",joinColumns = @JoinColumn(name = "LUI_ID"))
    @Column(name="RELATED_LUI_TYPE")
    private List<String> relatedLuiTypes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiUnitsDeploymentEntity> luiUnitsDeployment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuCodeEntity> luiCodes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<LuiAttributeEntity> attributes = new HashSet<LuiAttributeEntity>();

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
        this.scheduleIds = new ArrayList<String>(lui.getScheduleIds());

        if (lui.getDescr() == null) {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        } else {
            this.setDescrFormatted(lui.getDescr().getFormatted());
            this.setDescrPlain(lui.getDescr().getPlain());
        }

        // LuiCodes
        Map<String, LuCodeEntity> existingLuiCodes = new HashMap<String, LuCodeEntity>();
        if (luiCodes != null) {
            for (LuCodeEntity luiCode: luiCodes) {
                existingLuiCodes.put(luiCode.getId(), luiCode);
            }
        }

        //Clear the list
        luiCodes = new ArrayList<LuCodeEntity>();

        //Update existing or create new
        for (LuCode luCode:lui.getLuiCodes()) {
            LuCodeEntity luCodeEntity;
            if (existingLuiCodes.containsKey(luCode.getId())) {
                luCodeEntity = existingLuiCodes.remove(luCode.getId());
                orphansToDelete.addAll(luCodeEntity.fromDto(luCode));
            } else {
                luCodeEntity = new LuCodeEntity(luCode);
                luCodeEntity.setLui(this);
            }
            luiCodes.add(luCodeEntity);
        }

        // Delete the orphans (add to list of objects to delete)
        orphansToDelete.addAll(existingLuiCodes.values());

        // Map the existing result group keys by their id
        if (lui.getResultValuesGroupKeys() != null){
            resultValuesGroupKeys = new ArrayList<String>(lui.getResultValuesGroupKeys());
        } else {
            resultValuesGroupKeys = null;
        }


        // Lui Identifiers

        // Map the existing idents by their id
        Map<String,LuiIdentifierEntity> existingIdents = new HashMap<String,LuiIdentifierEntity>();
        if (identifiers != null) {
            for (LuiIdentifierEntity ident : identifiers) {
                existingIdents.put(ident.getId(), ident);
            }
        }
        // Clear the list of current idents
        identifiers = new ArrayList<LuiIdentifierEntity>();

        // Add official identifiers
        if (lui.getOfficialIdentifier() != null) {
            LuiIdentifierEntity identEntity;
            // See if this exists already
            if (existingIdents.containsKey(lui.getOfficialIdentifier().getId())) {
                // Pull the existing one out of the map
                identEntity = existingIdents.remove(lui.getOfficialIdentifier().getId());
                orphansToDelete.addAll(identEntity.fromDto(lui.getOfficialIdentifier())); //Make sure this copies all fields
            } else {
                // This is new so create a new identifier
                identEntity = new LuiIdentifierEntity(lui.getOfficialIdentifier());
                identEntity.setLui(this);
            }

            identifiers.add(identEntity);
        }

        // Add alternate identifiers
        for (LuiIdentifier identifier : lui.getAlternateIdentifiers()) {
            LuiIdentifierEntity identEntity;
            // See if this exists already
            if (existingIdents.containsKey(identifier.getId())) {
                // Pull the existing one out of the map
                identEntity = existingIdents.remove(identifier.getId());
                orphansToDelete.addAll(identEntity.fromDto(identifier)); //Make sure this copies all fields
            } else {
                // This is new so create a new identifier
                identEntity = new LuiIdentifierEntity(identifier);
                identEntity.setLui(this);
            }

            identifiers.add(identEntity);
        }

        // Now we need to delete the leftovers (orphaned idents)
        orphansToDelete.addAll(existingIdents.values());

        //lui-org relations

        // Map the existing org relations by their id
        if (lui.getUnitsContentOwner() != null) {
           luiContentOwner = new ArrayList<String>(lui.getUnitsContentOwner());
        } else {
           luiContentOwner = null;
        }

        // Map the related types id
        if (lui.getRelatedLuiTypes() != null) {
            relatedLuiTypes = new ArrayList<String>(lui.getRelatedLuiTypes());
        } else {
            relatedLuiTypes = null;
        }

        // Map the existing org relations by their id
        Map<String,LuiUnitsDeploymentEntity> existinguLuiUnitsDeploymentEntities = new HashMap<String,LuiUnitsDeploymentEntity>();
        if (luiUnitsDeployment != null) {
            for (LuiUnitsDeploymentEntity unitEntity : luiUnitsDeployment) {
                existinguLuiUnitsDeploymentEntities.put(unitEntity.getOrgId(), unitEntity);
            }
        }

        // Clear out the current list
        luiUnitsDeployment = new ArrayList<LuiUnitsDeploymentEntity>();

        if (lui.getUnitsDeployment() != null){
            for (String unitDeploymentOrgId : lui.getUnitsDeployment()){
                LuiUnitsDeploymentEntity luiUnitDeployment;
                if (existinguLuiUnitsDeploymentEntities.containsKey(unitDeploymentOrgId)){
                    luiUnitDeployment = existinguLuiUnitsDeploymentEntities.remove(unitDeploymentOrgId);
                } else {
                    luiUnitDeployment = new LuiUnitsDeploymentEntity(this, unitDeploymentOrgId);
                }
                luiUnitsDeployment.add(luiUnitDeployment);
            }
        }

        // Now we need to delete the leftovers (orphaned entities)
        orphansToDelete.addAll(existinguLuiUnitsDeploymentEntities.values());

        // Merge attributes into entity and add leftovers to be deleted
        orphansToDelete.addAll(TransformUtility.mergeToEntityAttributes(LuiAttributeEntity.class, lui, this));

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
        if(scheduleIds != null) {
            info.getScheduleIds().addAll(scheduleIds);
        }

        info.setMeta(super.toDTO());
        info.setDescr(new RichTextHelper().toRichTextInfo(plain, formatted));

        // lucCodes
        if (luiCodes != null) {
            for (LuCodeEntity luCode : luiCodes) {
                info.getLuiCodes().add(luCode.toDto());
            }
        }

        // Result Value Group Keys
        info.getResultValuesGroupKeys().clear();
        if (resultValuesGroupKeys != null){
            info.getResultValuesGroupKeys().addAll(resultValuesGroupKeys);
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
        info.setAttributes(TransformUtility.toAttributeInfoList(this));

        List<String> unitsDeploymentOrgIds = new ArrayList<String>();
        if (this.luiUnitsDeployment!= null) {
            for (LuiUnitsDeploymentEntity unitsDep : this.luiUnitsDeployment) {
                unitsDeploymentOrgIds.add(unitsDep.getOrgId());
            }
        }
        info.setUnitsDeployment(unitsDeploymentOrgIds);

        info.getUnitsContentOwner().clear();
        if (luiContentOwner != null) {
            info.getUnitsContentOwner().addAll(luiContentOwner);
        }

        info.getRelatedLuiTypes().clear();
        if (relatedLuiTypes != null){
            info.getRelatedLuiTypes().addAll(relatedLuiTypes);
        }

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

    public void setAttributes(Set<LuiAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<LuiIdentifierEntity> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<LuiIdentifierEntity> identifiers) {
        this.identifiers = identifiers;
    }

    public Set<LuiAttributeEntity> getAttributes() {
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

    public List<LuCodeEntity> getLuiCodes() {
        return luiCodes;
    }

    public void setLuiCodes(List<LuCodeEntity> luiCodes) {
        this.luiCodes = luiCodes;
    }

    public List<String> getLuiContentOwner() {
        return luiContentOwner;
    }

    public void setLuiContentOwner(List<String> luiContentOwner) {
        this.luiContentOwner = luiContentOwner;
    }

    public List<String> getRelatedLuiTypes() {
        return relatedLuiTypes;
    }

    public void setRelatedLuiTypes(List<String> relatedLuiTypes) {
        this.relatedLuiTypes = relatedLuiTypes;
    }

    public List<LuiUnitsDeploymentEntity> getLuiUnitsDeployment() {
        return luiUnitsDeployment;
    }

    public void setLuiUnitsDeployment(List<LuiUnitsDeploymentEntity> luiUnitsDeployment) {
        this.luiUnitsDeployment = luiUnitsDeployment;
    }

    public List<String> getResultValuesGroupKeys() {
        return resultValuesGroupKeys;
    }

    public void setResultValuesGroupKeys(List<String> resultValuesGroupKeys) {
        this.resultValuesGroupKeys = resultValuesGroupKeys;
    }

    public List<String> getScheduleIds() {
        return scheduleIds;
    }

    public void setScheduleIds(List<String> scheduleIds) {
        this.scheduleIds = scheduleIds;
    }
}
