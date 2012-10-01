package org.kuali.student.r2.lum.lrc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

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
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_LRC_RESULT_VALUE")
@NamedQueries({
    @NamedQuery(name = "ResultValueEntity.getIdsByType",
    query = "select id from ResultValueEntity where type = :type"),
    @NamedQuery(name = "ResultValueEntity.getByScale",
    query = "select RV from ResultValueEntity RV where RV.resultScaleId = :resultScaleKey"),
    @NamedQuery(name = "ResultValueEntity.getByScaleAndValue",
    query = "select RV from ResultValueEntity RV where RV.resultScaleId = :resultScaleKey and RV.value = :value")
})
public class ResultValueEntity extends MetaEntity implements AttributeOwner<ResultValueAttributeEntity> {

    @Column(name = "RESULT_VALUE_TYPE")
    private String type;
    @Column(name = "RESULT_VALUE_STATE")
    private String state;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    @Column(name = "RESULT_SCALE_ID")
    private String resultScaleId;
    @Column(name = "NUMERIC_VALUE")
    private BigDecimal numericValue;
    @Column(name = "RESULT_VALUE")
    private String value;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<ResultValueAttributeEntity> attributes;

    public ResultValueEntity() {
    }

    public ResultValueEntity(ResultValueInfo dto, EntityManager em) {
        super(dto);
        this.setId(dto.getKey());
        this.setType(dto.getTypeKey());
        setResultScaleId(dto.getResultScaleKey());
        this.fromDTO(dto, em);
    }

    public void fromDTO(ResultValueInfo dto, EntityManager em) {
        this.setName(dto.getName());
        this.setState(dto.getStateKey());
        if (dto.getDescr() != null) {
            this.setDescrFormatted(dto.getDescr().getFormatted());
            this.setDescrPlain(dto.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        setEffectiveDate(dto.getEffectiveDate());
        setExpirationDate(dto.getExpirationDate());
        if(dto.getNumericValue() != null){
            setNumericValue(new BigDecimal(dto.getNumericValue()));
        }
        else
        {
            setNumericValue (null);
        }
        setValue(dto.getValue()); 
        // dynamic attributes
        if (this.getAttributes() == null) {
            this.setAttributes(new HashSet<ResultValueAttributeEntity>());
        }
        Set<String> idSet = new HashSet<String>(dto.getAttributes().size());
        for (AttributeInfo attr : dto.getAttributes()) {
            if (attr.getId() != null) {
                idSet.add(attr.getId());
            }
        }
        for (ResultValueAttributeEntity attEntity : new ArrayList<ResultValueAttributeEntity> (this.getAttributes())) {
            if (!idSet.contains(attEntity.getId())) {
                em.remove(attEntity);
                this.getAttributes().remove(attEntity);
            }
        }
        for (Attribute att : dto.getAttributes()) {
            ResultValueAttributeEntity attEntity = new ResultValueAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public String getResultScaleId() {
        return resultScaleId;
    }

    public void setResultScaleId(String resultScaleId) {
        this.resultScaleId = resultScaleId;
    }

    public BigDecimal getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(BigDecimal numericValue) {
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

    @Override
    public void setAttributes(Set<ResultValueAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<ResultValueAttributeEntity> getAttributes() {
        return attributes;
    }

    public ResultValueInfo toDto() {
        ResultValueInfo info = new ResultValueInfo();
        info.setKey(getId());
        info.setTypeKey(getType());
        info.setStateKey(getState());
        info.setName(getName());
        info.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));
        info.setResultScaleKey(getResultScaleId());
        info.setNumericValue(String.valueOf(getNumericValue()));
        info.setValue(getValue());
        info.setEffectiveDate(getEffectiveDate());
        info.setExpirationDate(getExpirationDate());
        if (this.getAttributes() != null) {
            for (ResultValueAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                info.getAttributes().add(attInfo);
            }
        }
        info.setMeta(super.toDTO());
        return info;
    }
}
