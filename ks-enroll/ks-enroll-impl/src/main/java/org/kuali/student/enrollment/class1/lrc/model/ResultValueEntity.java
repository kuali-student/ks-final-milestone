package org.kuali.student.enrollment.class1.lrc.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KSEN_LRC_RES_VALUE")
public class ResultValueEntity extends MetaEntity implements AttributeOwner<ResultValueAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private ResultValueRichTextEntity descr;

    @Column(name = "RES_SCALE_ID")
    private String resultScaleId;

    @Column(name = "NUMERIC_VALUE")
    private String numericValue;

    @Column(name = "VALUE")
    private String value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private LrcTypeEntity type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity state;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ResultValueAttributeEntity> attributes;

    public ResultValueEntity(){
    }

    public ResultValueEntity(ResultValueInfo dto){
        super(dto);
        setName(dto.getName());
        if (dto.getDescr() != null){
            ResultValueRichTextEntity entityDesc = new ResultValueRichTextEntity(dto.getDescr());
            this.setDescr(entityDesc);
        }
        setId(dto.getKey());
        setEffectiveDate(dto.getEffectiveDate());
        setExpirationDate(dto.getExpirationDate());
        setNumericValue(dto.getNumericValue());
        setResultScaleId(dto.getResultScaleKey());
        setValue(dto.getValue());

        this.setAttributes(new ArrayList<ResultValueAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                ResultValueAttributeEntity attEntity = new ResultValueAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }

    }

    public String getResultScaleId() {
        return resultScaleId;
    }

    public void setResultScaleId(String resultScaleId) {
        this.resultScaleId = resultScaleId;
    }

    public String getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(String numericValue) {
        this.numericValue = numericValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

     public String getName() {
        return name;
     }

     public void setName(String name) {
        this.name = name;
     }

     public ResultValueRichTextEntity getDescr() {
        return descr;
     }

     public void setDescr(ResultValueRichTextEntity descr) {
        this.descr = descr;
     }

    public LrcTypeEntity getType() {
        return type;
    }

    public void setType(LrcTypeEntity type) {
        this.type = type;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    @Override
    public void setAttributes(List<ResultValueAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<ResultValueAttributeEntity> getAttributes() {
        return attributes;
    }

    public ResultValueInfo toDto() {
        ResultValueInfo info = new ResultValueInfo();
        info.setKey(getId());
        info.setEffectiveDate(getEffectiveDate());
        info.setExpirationDate(getExpirationDate());
        info.setMeta(super.toDTO());

        if (descr != null) {
            info.setDescr(descr.toDto());
        }

        info.setName(getName());
        info.setNumericValue(getNumericValue());
        info.setValue(getValue());
        info.setScaleKey(getResultScaleId());

        if (getState() != null){
            info.setStateKey(getState().getId());
        }
        if (getType() != null){
            info.setTypeKey(getType().getId());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (ResultValueAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);
        info.setMeta(super.toDTO());
        return info;
    }
}
