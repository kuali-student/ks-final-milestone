package org.kuali.student.r2.lum.lrc.model;

import java.util.ArrayList;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

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
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;

@Entity
@Table(name = "KSEN_LRC_RVG")
@NamedQueries({
    @NamedQuery(name = "ResultValuesGroupEntity.getIdsByType",
    query = "select id from ResultValuesGroupEntity where type = :type"),
    @NamedQuery(name = "ResultValuesGroupEntity.getByResultValue",
    query = "select RVG from ResultValuesGroupEntity RVG JOIN RVG.resultValueKeys RV where RV.id = :resultValueKey"),
    @NamedQuery(name = "ResultValuesGroupEntity.getByResultScale",
    query = "select RVG from ResultValuesGroupEntity RVG where RVG.resultScaleId = :resultScaleKey")
})
public class ResultValuesGroupEntity extends MetaEntity implements AttributeOwner<ResultValuesGroupAttributeEntity> {

    @Column(name = "RVG_TYPE")
    private String type;
    @Column(name = "RVG_STATE")
    private String state;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    @Column(name = "RESULT_SCALE_ID")
    private String resultScaleId;
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
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "RESULT_VALUE_ID")
    @CollectionTable(name = "KSEN_LRC_RVG_RESULT_VALUE", joinColumns =
    @JoinColumn(name = "RVG_ID"))
    private final Set<String> resultValueKeys = new HashSet<String>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<ResultValuesGroupAttributeEntity> attributes;

    public ResultValuesGroupEntity() {
    }

    public ResultValuesGroupEntity(ResultValuesGroupInfo dto, EntityManager em) {
        super(dto);
        this.setId(dto.getKey());
        this.setType(dto.getTypeKey());
        this.setResultScaleId(dto.getResultScaleKey());
        fromDTO(dto, em);
    }

    public void fromDTO(ResultValuesGroupInfo dto, EntityManager em) {
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
        this.resultValueKeys.addAll(dto.getResultValueKeys());

        
        // dynamic attributes
        if (this.getAttributes() == null) {
            this.setAttributes(new HashSet<ResultValuesGroupAttributeEntity>());
        }
        Set<String> idSet = new HashSet<String>(dto.getAttributes().size());
        for (AttributeInfo attr : dto.getAttributes()) {
            if (attr.getId() != null) {
                idSet.add(attr.getId());
            }
        }
        for (ResultValuesGroupAttributeEntity attEntity : new ArrayList<ResultValuesGroupAttributeEntity> (this.getAttributes())) {
            if (!idSet.contains(attEntity.getId())) {
                em.remove(attEntity);
                this.getAttributes().remove(attEntity);
            }
        }
        for (Attribute att : dto.getAttributes()) {
            ResultValuesGroupAttributeEntity attEntity = new ResultValuesGroupAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getResultScaleId() {
        return resultScaleId;
    }

    public void setResultScaleId(String resultScaleId) {
        this.resultScaleId = resultScaleId;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
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

    public Set<String> getResultValueKeys() {
        return resultValueKeys;
    }

    @Override
    public void setAttributes(Set<ResultValuesGroupAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<ResultValuesGroupAttributeEntity> getAttributes() {
        return attributes;
    }

    public ResultValuesGroupInfo toDto() {

        ResultValuesGroupInfo info = new ResultValuesGroupInfo();

        info.setKey(getId());
        info.setTypeKey(getType());
        info.setStateKey(getState());
        info.setName(getName());
        info.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));
        info.setResultScaleKey(getResultScaleId());
        if (this.getMinValue() != null || this.getMaxValue() != null || this.getIncrement() != null) {
            ResultValueRangeInfo resultValueRange = new ResultValueRangeInfo();
            resultValueRange.setMaxValue(getMaxValue());
            resultValueRange.setMinValue(getMinValue());
            resultValueRange.setIncrement(getIncrement());
            info.setResultValueRange(resultValueRange);
        }
        info.setEffectiveDate(getEffectiveDate());
        info.setExpirationDate(getExpirationDate());
        info.getResultValueKeys().addAll(getResultValueKeys());
        if (this.getAttributes() != null) {
            for (ResultValuesGroupAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                info.getAttributes().add(attInfo);
            }
        }
        info.setMeta(super.toDTO());

        return info;
    }
}
