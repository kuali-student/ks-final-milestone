package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.infc.Soc;
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
@Table(name = "KSEN_SOC")
public class SocEntity extends MetaEntity implements AttributeOwner<SocAttributeEntity> {

    @Column(name = "SOC_TYPE", nullable = false)
    private String socType;
    @Column(name = "SOC_STATE", nullable = false)
    private String socState;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;
    @Column(name = "TERM_ID")
    private String termId;
    @Column(name = "SUBJECT_AREA")
    private String subjectArea;
    @Column(name = "UNITS_CONTENT_OWNER_ID")
    private String unitsContentOwnerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<SocAttributeEntity> attributes = new HashSet<SocAttributeEntity>();

    public SocEntity() {
    }

    public SocEntity(Soc soc) {
        super(soc);
        this.setId(soc.getId());
        this.setSocType(soc.getTypeKey());
        this.setTermId(soc.getTermId());
        this.fromDTO(soc);
    }

    public void fromDTO(Soc soc) {
        this.setSocState(soc.getStateKey());
        this.setName(soc.getName());
        if (soc.getDescr() != null) {
            this.setDescrFormatted(soc.getDescr().getFormatted());
            this.setDescrPlain(soc.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        this.setSubjectArea(soc.getSubjectArea());
        this.setUnitsContentOwnerId(soc.getUnitsContentOwnerId());
        this.setAttributes(new HashSet<SocAttributeEntity>());
        for (Attribute att : soc.getAttributes()) {
            this.getAttributes().add(new SocAttributeEntity(att, this));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    public String getSocType() {
        return socType;
    }

    public void setSocType(String socType) {
        this.socType = socType;
    }

    public String getSocState() {
        return socState;
    }

    public void setSocState(String socState) {
        this.socState = socState;
    }

    public void setAttributes(Set<SocAttributeEntity> attributes) {
        this.attributes = attributes;

    }

    public Set<SocAttributeEntity> getAttributes() {
        return attributes;
    }

    public SocInfo toDto() {
        SocInfo soc = new SocInfo();
        soc.setId(getId());
        soc.setTypeKey(socType);
        soc.setStateKey(socState);
        soc.setName(name);
        soc.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));
        soc.setTermId(termId);
        soc.setSubjectArea(subjectArea);
        soc.setUnitsContentOwnerId(unitsContentOwnerId);
        soc.setMeta(super.toDTO());
        if (getAttributes() != null) {
            for (SocAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                soc.getAttributes().add(attInfo);
            }
        }

        return soc;
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

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getUnitsContentOwnerId() {
        return unitsContentOwnerId;
    }

    public void setUnitsContentOwnerId(String unitsContentOwnerId) {
        this.unitsContentOwnerId = unitsContentOwnerId;
    }

    
}
