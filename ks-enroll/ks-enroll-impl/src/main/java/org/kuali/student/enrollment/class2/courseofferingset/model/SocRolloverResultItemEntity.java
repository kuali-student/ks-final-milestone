package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.infc.SocRolloverResultItem;

@Entity
@Table(name = "KSEN_SOC_ROR_ITEM")
public class SocRolloverResultItemEntity extends MetaEntity {

    @Column(name = "ROR_ID")
    private String socRolloverResultId;
    @Column(name = "SOC_ROR_TYPE", nullable = false)
    private String socRorType;
    @Column(name = "SOC_ROR_STATE", nullable = false)
    private String socRorState;
    @Column(name = "SOURCE_CO_ID")
    private String sourceCourseOfferingId;
    @Column(name = "TARGET_CO_ID")
    private String targetCourseOfferingId;
    @Column(name = "MESG_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String mesgFormatted;
    @Column(name = "MESG_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String mesgPlain;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<SocRolloverResultItemAttributeEntity> attributes = new ArrayList<SocRolloverResultItemAttributeEntity>();

    public SocRolloverResultItemEntity() {
    }

    public SocRolloverResultItemEntity(SocRolloverResultItem item) {
        super(item);
        this.setId(item.getId());
        this.setSocRolloverResultId(item.getSocRolloverResultId());
        this.setSocRorType(item.getTypeKey());
        this.fromDTO(item);
    }

    public void fromDTO(SocRolloverResultItem item) {
        this.setSocRorState(item.getStateKey());
        this.setSourceCourseOfferingId(item.getSourceCourseOfferingId());
        this.setTargetCourseOfferingId(item.getTargetCourseOfferingId());
        if (item.getMessage() != null) {
            this.setMesgFormatted(item.getMessage().getFormatted());
            this.setMesgPlain(item.getMessage().getPlain());
        } else {
            this.setMesgFormatted(null);
            this.setMesgPlain(null);
        }
        this.setAttributes(new ArrayList<SocRolloverResultItemAttributeEntity>());
        for (Attribute att : item.getAttributes()) {
            this.getAttributes().add(new SocRolloverResultItemAttributeEntity(att, this));
        }
    }


    public SocRolloverResultItemInfo toDto() {
        SocRolloverResultItemInfo info = new SocRolloverResultItemInfo();
        info.setId(getId());
        info.setTypeKey(socRorType);
        info.setStateKey(socRorState);
        info.setSocRolloverResultId(socRolloverResultId);
        info.setSourceCourseOfferingId(sourceCourseOfferingId);
        info.setTargetCourseOfferingId(targetCourseOfferingId);
        info.setMessage(new RichTextHelper().toRichTextInfo(getMesgPlain(), getMesgFormatted()));
        info.setMeta(super.toDTO());
        if (getAttributes() != null) {
            for (SocRolloverResultItemAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                info.getAttributes().add(attInfo);
            }
        }

        return info;
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

    public String getSocRolloverResultId() {
        return socRolloverResultId;
    }

    public void setSocRolloverResultId(String socRolloverResultId) {
        this.socRolloverResultId = socRolloverResultId;
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

    public String getSourceCourseOfferingId() {
        return sourceCourseOfferingId;
    }

    public void setSourceCourseOfferingId(String sourceCourseOfferingId) {
        this.sourceCourseOfferingId = sourceCourseOfferingId;
    }

    public String getTargetCourseOfferingId() {
        return targetCourseOfferingId;
    }

    public void setTargetCourseOfferingId(String targetCourseOfferingId) {
        this.targetCourseOfferingId = targetCourseOfferingId;
    }

    public List<SocRolloverResultItemAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<SocRolloverResultItemAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    
}
