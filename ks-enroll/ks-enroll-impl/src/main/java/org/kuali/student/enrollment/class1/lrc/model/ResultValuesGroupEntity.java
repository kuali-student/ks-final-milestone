package org.kuali.student.enrollment.class1.lrc.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KSEN_LRC_RES_VAL_GRP")
public class ResultValuesGroupEntity extends MetaEntity implements AttributeOwner<ResultValuesGroupAttributeEntity>{

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private ResultValuesGroupRichTextEntity descr;

    @Column(name = "RES_SCALE_ID")
    private String resultScaleId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KSEN_LRC_RVGP_RV_RELTN", joinColumns = @JoinColumn(name = "RES_VAL_GRP_ID"), inverseJoinColumns = @JoinColumn(name = "RES_VAL_ID"))
    private List<ResultValueEntity> resultValues;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private LrcTypeEntity type;

    @Column(name = "MIN_VALUE")
    private String minValue;

    @Column(name = "MAX_VALUE")
    private String maxValue;

    @Column(name = "INCR")
    private String increment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity state;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ResultValuesGroupAttributeEntity> attributes;

    public ResultValuesGroupEntity(){ }

    public ResultValuesGroupEntity(ResultValuesGroupInfo dto){
        super(dto);
        this.setName(dto.getName());
        this.setId(dto.getKey());
        if (dto.getDescr() != null) {
            ResultValuesGroupRichTextEntity entityDesc = new ResultValuesGroupRichTextEntity(dto.getDescr());
            this.setDescr(entityDesc);
        }
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setExpirationDate(dto.getExpirationDate());
        this.setResultScaleId(dto.getResultScaleKey());

        //No Entity available (not needed) for ResultValueRangeInfo as it's a 1-1 for ResultValuesGroup. But, Service contract has the ResultValueRangeInfo object
        if (dto.getResultValueRange() != null){
            this.setMinValue(dto.getResultValueRange().getMinValue());
            this.setMaxValue(dto.getResultValueRange().getMaxValue());
            this.setIncrement(dto.getResultValueRange().getIncrement());
        }

        this.setAttributes(new ArrayList<ResultValuesGroupAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                ResultValuesGroupAttributeEntity attEntity = new ResultValuesGroupAttributeEntity(att);
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

    public ResultValuesGroupRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(ResultValuesGroupRichTextEntity descr) {
        this.descr = descr;
    }

    public String getResultScaleId() {
       return resultScaleId;
    }

    public void setResultScaleId(String resultScaleId) {
       this.resultScaleId = resultScaleId;
    }

    public List<ResultValueEntity> getResultValues() {
        return resultValues;
    }

    public void setResultValues(List<ResultValueEntity> resultValues) {
        this.resultValues = resultValues;
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
    public void setAttributes(List<ResultValuesGroupAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<ResultValuesGroupAttributeEntity> getAttributes() {
        return attributes;
    }

     public ResultValuesGroupInfo toDto() {

         ResultValuesGroupInfo info = new ResultValuesGroupInfo();

         info.setKey(getId());
         info.setName(getName());

         if (getDescr() != null){
            info.setDescr(getDescr().toDto());
         }

         if (getState() != null){
            info.setStateKey(getState().getId());
         }

         if (getType() != null){
            info.setTypeKey(getType().getId());
         }

         info.setEffectiveDate(getEffectiveDate());
         info.setExpirationDate(getExpirationDate());
         info.setMeta(info.getMeta());

         info.setResultScaleKey(getResultScaleId());
         List<String> rvIDs = new ArrayList<String>();
         for(ResultValueEntity rvEntity : getResultValues()){
            rvIDs.add(rvEntity.getId());
         }
         info.setResultValueKeys(rvIDs);

         //No Entity available (not needed) for ResultValueRangeInfo as it's a 1-1 for ResultValuesGroup. But, Service contract has the ResultValueRangeInfo object
         ResultValueRangeInfo range = new ResultValueRangeInfo();
         range.setIncrement(getIncrement());
         range.setMaxValue(getMaxValue());
         range.setMinValue(getMinValue());
         info.setResultValueRange(range);

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (ResultValuesGroupAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);
        info.setMeta(super.toDTO());

        return info;
     }
}
