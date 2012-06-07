package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.infc.SocRolloverResult;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public SocRolloverResultEntity() {
    }

    public SocRolloverResultEntity(SocRolloverResult socRolloverResult) {
        super(socRolloverResult);
        this.setId(socRolloverResult.getId());
        this.setSocRorType(socRolloverResult.getTypeKey());
        this.fromDTO(socRolloverResult);
    }

    public void fromDTO(SocRolloverResult socRolloverResult) {
        this.setSocRorState(socRolloverResult.getStateKey());
        this.setSourceSocId(socRolloverResult.getSourceSocId());
        this.setTargetSocId(socRolloverResult.getTargetSocId());
        this.setTargetTermId(socRolloverResult.getTargetTermId());
        // TODO: store the option keys
        this.setItemsProcessed(socRolloverResult.getItemsProcessed());
        this.setItemsExpected(socRolloverResult.getItemsExpected());
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
        this.setAttributes(new HashSet<SocRolloverResultAttributeEntity>());
        for (Attribute att : socRolloverResult.getAttributes()) {
            this.getAttributes().add(new SocRolloverResultAttributeEntity(att, this));
        }
    }

    private boolean alreadyExists(String optionKey) {
        for (SocRolloverResultOptionEntity optionEntity : this.getOptions()) {
            if (optionEntity.getOptionId().equals(optionKey)) {
                return true;
            }
        }
        return false;
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
            for (SocRolloverResultAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                socRolloverResult.getAttributes().add(attInfo);
            }
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
