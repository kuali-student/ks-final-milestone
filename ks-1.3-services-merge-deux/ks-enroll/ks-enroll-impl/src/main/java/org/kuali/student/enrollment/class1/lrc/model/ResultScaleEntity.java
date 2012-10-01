package org.kuali.student.enrollment.class1.lrc.model;

import java.util.ArrayList;
import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_LRC_RESULT_SCALE")
@NamedQueries({
    @NamedQuery(name = "ResultScaleEntity.getIdsByType",
    query = "select id from ResultScaleEntity where type = :type")
})
public class ResultScaleEntity extends MetaEntity implements AttributeOwner<ResultScaleAttributeEntity> {

    @Column(name = "RESULT_SCALE_TYPE")
    private String type;
    @Column(name = "RESULT_SCALE_STATE")
    private String state;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    @Column(name = "RANGE_MIN_VALUE")
    private String minValue;
    @Column(name = "RANGE_MAX_VALUE")
    private String maxValue;
    @Column(name = "RANGE_INCREMENT")
    private String increment;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    // orphan removal does not work so need to manually remove them ourselves
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ResultScaleAttributeEntity> attributes;

    public ResultScaleEntity() {
    }

    public ResultScaleEntity(ResultScaleInfo dto,
            EntityManager em) {
        super(dto);
        this.setId(dto.getKey());
        this.setType(dto.getTypeKey());
        fromDTO(dto, em);
    }

    public void fromDTO(ResultScaleInfo dto,
            EntityManager em) {

        List<Object> orphanData = new ArrayList<Object>();
        this.setName(dto.getName());
        this.setState(dto.getStateKey());
        if (dto.getDescr() != null) {
            this.setDescrFormatted(dto.getDescr().getFormatted());
            this.setDescrPlain(dto.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        if (dto.getResultValueRange() == null) {
            this.setMinValue(null);
            this.setMaxValue(null);
            this.setIncrement(null);
        } else {
            this.setMinValue(dto.getResultValueRange().getMinValue());
            this.setMaxValue(dto.getResultValueRange().getMaxValue());
            this.setIncrement(dto.getResultValueRange().getIncrement());
        }
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setExpirationDate(dto.getExpirationDate());
        // dynamic attributes
        if (this.getAttributes() == null) {
            this.setAttributes(new HashSet<ResultScaleAttributeEntity>());
        }
        Set<String> idSet = new HashSet<String>(dto.getAttributes().size());
        for (AttributeInfo attr : dto.getAttributes()) {
            if (attr.getId() != null) {
                idSet.add(attr.getId());
            }
        }
        for (ResultScaleAttributeEntity attEntity : new ArrayList<ResultScaleAttributeEntity> (this.getAttributes())) {
            if (!idSet.contains(attEntity.getId())) {
                em.remove(attEntity);
                this.getAttributes().remove(attEntity);
            }
        }
        for (Attribute att : dto.getAttributes()) {
            ResultScaleAttributeEntity attEntity = new ResultScaleAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
        return;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
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

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setAttributes(Set<ResultScaleAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<ResultScaleAttributeEntity> getAttributes() {
        return attributes;
    }

    public ResultScaleInfo toDto() {

        ResultScaleInfo info = new ResultScaleInfo();

        info.setKey(getId());
        info.setTypeKey(getType());
        info.setStateKey(getState());
        info.setName(getName());
        info.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));
        info.setEffectiveDate(getEffectiveDate());
        info.setExpirationDate(getExpirationDate());
        if (this.getMinValue() != null || this.getMaxValue() != null || this.getIncrement() != null) {
            ResultValueRangeInfo resultValueRange = new ResultValueRangeInfo();
            resultValueRange.setMaxValue(getMaxValue());
            resultValueRange.setMinValue(getMinValue());
            resultValueRange.setIncrement(getIncrement());
            info.setResultValueRange(resultValueRange);
        }
        if (this.getAttributes() != null) {
            for (ResultScaleAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                info.getAttributes().add(attInfo);
            }
        }
        info.setMeta(super.toDTO());
        return info;
    }
}
