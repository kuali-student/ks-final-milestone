package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.apache.log4j.Logger;
import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.infc.SocRolloverResult;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import javax.persistence.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "KSEN_SOC_ROR")
public class SocRolloverResultEntity extends MetaEntity implements AttributeOwner<SocRolloverResultAttributeEntity> {

    @Column(name = "SOC_ROR_TYPE", nullable = false)
    private String socRorType;
    @Column(name = "SOC_ROR_STATE", nullable = false)
    private String socRorState;
    @Column(name = "TARGET_TERM_ID")
    private String targetTermId;
    @Column(name = "ITEMS_PROCESSED")
    private Integer itemsProcessed;
    @Column(name = "ITEMS_EXPECTED")
    private Integer itemsExpected;
    @Column(name = "SOURCE_SOC_ID")
    private String sourceSocId;
    @Column(name = "TARGET_SOC_ID")
    private String targetSocId;
    @Column(name = "MESG_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String mesgFormatted;
    @Column(name = "MESG_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String mesgPlain;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "socRolloverResult")
    private List<SocRolloverResultOptionEntity> options = new ArrayList<SocRolloverResultOptionEntity>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<SocRolloverResultAttributeEntity> attributes = new HashSet<SocRolloverResultAttributeEntity>();

    private static Logger LOGGER = Logger.getLogger(SocRolloverResultEntity.class);

    public SocRolloverResultEntity() {
    }

    public SocRolloverResultEntity(SocRolloverResult socRolloverResult) {
        super(socRolloverResult);
        this.setId(socRolloverResult.getId());
        this.setSocRorType(socRolloverResult.getTypeKey());
        this.fromDTO(socRolloverResult);
    }

    private Map<String, SocRolloverResultAttributeEntity> _computeKeyAttributeMap(Set<SocRolloverResultAttributeEntity> attributes) {
        Map<String, SocRolloverResultAttributeEntity> keyAttributeMap =
                new HashMap<String, SocRolloverResultAttributeEntity>();
        for (SocRolloverResultAttributeEntity attr: attributes) {
            keyAttributeMap.put(attr.getKey(), attr);
        }
        return keyAttributeMap;
    }

    private Set<SocRolloverResultAttributeEntity> _filterForDynAttrs(SocRolloverResultEntity entity) {
        Set<SocRolloverResultAttributeEntity> filtered = new HashSet<SocRolloverResultAttributeEntity>();
        for (SocRolloverResultAttributeEntity attrEntity: this.getAttributes()) {
            if (CourseOfferingSetServiceConstants.ALL_RESULT_DYNATTR_KEYS.contains(attrEntity.getKey())) {
                filtered.add(attrEntity);
            }
        }
        // Remove the dynamic attributes from this
        this.getAttributes().removeAll(filtered);
        return filtered;
    }

    public void fromDTO(SocRolloverResult socRolloverResult) {
        this.setSocRorState(socRolloverResult.getStateKey());
        this.setSourceSocId(socRolloverResult.getSourceSocId());
        this.setTargetSocId(socRolloverResult.getTargetSocId());
        this.setTargetTermId(socRolloverResult.getTargetTermId());
        // TODO: store the option keys
        this.setItemsProcessed(socRolloverResult.getItemsProcessed());  // Basically ignored
        this.setItemsExpected(socRolloverResult.getItemsExpected()); // Basically ignored
        if (socRolloverResult.getMessage() != null) {
            this.setMesgFormatted(socRolloverResult.getMessage().getFormatted());
            this.setMesgPlain(socRolloverResult.getMessage().getPlain());
        } else {
            this.setMesgFormatted(null);
            this.setMesgPlain(null);
        }
        // Add any new options to the list of options
        // Deletes of options has to occur in the updateRolloverResult method because it needs access to the entity
        for (String optionKey : socRolloverResult.getOptionKeys()) {
            if (!alreadyExists(optionKey)) {
                this.getOptions().add(new SocRolloverResultOptionEntity(optionKey, this));
            }
        }
        // Handle copying SocRolloverResult attributes to SocRolloverResultEntity attributes
        // TODO: Could be made more generic
        Set<SocRolloverResultAttributeEntity> dynamicAttrSet = _filterForDynAttrs(this);
        // Merge the non-dynamic attributes
        List<Object> toDelete =
                TransformUtility.mergeToEntityAttributes(SocRolloverResultAttributeEntity.class, socRolloverResult, this);
        if (!toDelete.isEmpty()) {
            LOGGER.warn("Unexpected attributes--should handle by deleting orphans");
        }
        // Then, copy the dynamic attributes in
        this.getAttributes().addAll(dynamicAttrSet);
        // Then update the entity attribute values
        SocRolloverResultDynAttrConverter.copyDtoDynAttrsToEntity(socRolloverResult, this);
    }

    private boolean alreadyExists(String optionKey) {
        for (SocRolloverResultOptionEntity optionEntity : this.getOptions()) {
            if (optionEntity.getOptionId().equals(optionKey)) {
                return true;
            }
        }
        return false;
    }

    private Integer _parseInt(String intStr) {
        if (intStr == null) {
            return null;
        }
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            // Final return if exception thrown
        }
        return null;
    }

    public SocRolloverResultInfo toDto() {
        SocRolloverResultInfo socRolloverResult = new SocRolloverResultInfo();
        socRolloverResult.setId(getId());
        socRolloverResult.setTypeKey(socRorType);
        socRolloverResult.setStateKey(socRorState);
        socRolloverResult.setSourceSocId(sourceSocId);
        socRolloverResult.setTargetTermId(targetTermId);
        // TODO: load the option keys
        socRolloverResult.setTargetSocId(targetSocId);
        socRolloverResult.setItemsExpected(itemsExpected);
        socRolloverResult.setItemsProcessed(itemsProcessed);
        socRolloverResult.setMessage(new RichTextHelper().toRichTextInfo(getMesgPlain(), getMesgFormatted()));
        if (getOptions() != null) {
            for (SocRolloverResultOptionEntity optionEntity : getOptions()) {
                String optionKey = optionEntity.getOptionId();
                socRolloverResult.getOptionKeys().add(optionKey);
            }
        }
        socRolloverResult.setMeta(super.toDTO());
        if (getAttributes() != null) {
            // Handle standard attributes
            for (SocRolloverResultAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                if (!CourseOfferingSetServiceConstants.ALL_RESULT_DYNATTR_KEYS.contains(attInfo.getKey())) {
                    socRolloverResult.getAttributes().add(attInfo);
                }
            }
            // Handle dynamic attributes
            SocRolloverResultDynAttrConverter.copyEntityAttrsToDtoDynAttrs(this, socRolloverResult);
        }
        return socRolloverResult;
    }

    public Set<SocRolloverResultAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<SocRolloverResultAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public Integer getItemsExpected() {
        return itemsExpected;
    }

    public void setItemsExpected(Integer itemsExpected) {
        this.itemsExpected = itemsExpected;
    }

    public Integer getItemsProcessed() {
        return itemsProcessed;
    }

    public void setItemsProcessed(Integer itemsProcessed) {
        this.itemsProcessed = itemsProcessed;
    }

    public String getMesgFormatted() {
        return mesgFormatted;
    }

    public void setMesgFormatted(String mesgFormatted) {
        this.mesgFormatted = mesgFormatted;
    }

    public String getMesgPlain() {
        return mesgPlain;
    }

    public void setMesgPlain(String mesgPlain) {
        this.mesgPlain = mesgPlain;
    }

    public String getSocRorState() {
        return socRorState;
    }

    public void setSocRorState(String socRorState) {
        this.socRorState = socRorState;
    }

    public String getSocRorType() {
        return socRorType;
    }

    public void setSocRorType(String socRorType) {
        this.socRorType = socRorType;
    }

    public String getSourceSocId() {
        return sourceSocId;
    }

    public void setSourceSocId(String sourceSocId) {
        this.sourceSocId = sourceSocId;
    }

    public String getTargetSocId() {
        return targetSocId;
    }

    public void setTargetSocId(String targetSocId) {
        this.targetSocId = targetSocId;
    }

    public String getTargetTermId() {
        return targetTermId;
    }

    public void setTargetTermId(String targetTermId) {
        this.targetTermId = targetTermId;
    }

    public List<SocRolloverResultOptionEntity> getOptions() {
        if (this.options == null) {
            this.options = new ArrayList<SocRolloverResultOptionEntity>();
        }
        return options;
    }

    public void setOptions(List<SocRolloverResultOptionEntity> options) {
        this.options = options;
    }
}
