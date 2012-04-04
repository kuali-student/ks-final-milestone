package org.kuali.student.enrollment.class1.lrc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;

@Entity
@Table(name = "KSEN_LRC_RES_SCALE")
public class ResultScaleEntity extends MetaEntity implements AttributeOwner<ResultScaleAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private ResultScaleRichTextEntity descr;

    @Column(name = "TYPE_ID")
    private String type;

    @Column(name = "STATE_ID")
    private String state;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ResultScaleAttributeEntity> attributes;

    @Column(name = "MIN_VALUE")
    private String minValue;

    @Column(name = "MAX_VALUE")
    private String maxValue;

    @Column(name = "INCR")
    private String increment;

    public ResultScaleEntity() {

    }

    public ResultScaleEntity(ResultScaleInfo dto) {
        super(dto);
        this.setName(dto.getName());
        this.setId(dto.getKey());
        if (dto.getDescr() != null) {
            ResultScaleRichTextEntity entityDesc = new ResultScaleRichTextEntity(dto.getDescr());
            this.setDescr(entityDesc);
        }
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setExpirationDate(dto.getExpirationDate());
        this.setState(dto.getStateKey());
        //No Entity available (not needed) for ResultValueRangeInfo as it's a 1-1 for ResultValuesGroup. But, Service contract has the ResultValueRangeInfo object
        if (dto.getResultValueRange() != null) {
            this.setMinValue(dto.getResultValueRange().getMinValue());
            this.setMaxValue(dto.getResultValueRange().getMaxValue());
            this.setIncrement(dto.getResultValueRange().getIncrement());
        }

        this.setAttributes(new ArrayList<ResultScaleAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                ResultScaleAttributeEntity attEntity = new ResultScaleAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResultScaleRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(ResultScaleRichTextEntity descr) {
        this.descr = descr;
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

    @Override
    public void setAttributes(List<ResultScaleAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<ResultScaleAttributeEntity> getAttributes() {
        return attributes;
    }

    public ResultScaleInfo toDto() {

        ResultScaleInfo info = new ResultScaleInfo();

        info.setKey(getId());
        info.setName(getName());

        if (getDescr() != null) {
            info.setDescr(getDescr().toDto());
        }

        if (getState() != null) {
            info.setStateKey(getState());
        }

        if (getType() != null) {
            info.setTypeKey(getType());
        }

        info.setEffectiveDate(getEffectiveDate());
        info.setExpirationDate(getExpirationDate());

        //No Entity available (not needed) for ResultValueRangeInfo as it's a 1-1 for ResultValuesGroup. But, Service contract has the ResultValueRangeInfo object
        ResultValueRangeInfo resultValueRange = new ResultValueRangeInfo();
        resultValueRange.setMaxValue(getMaxValue());
        resultValueRange.setMinValue(getMinValue());
        resultValueRange.setIncrement(getIncrement());
        info.setResultValueRange(resultValueRange);

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (ResultScaleAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);
        info.setMeta(super.toDTO());

        return info;
    }
}
