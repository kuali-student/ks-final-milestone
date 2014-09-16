/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.ges.service.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.infc.GesValueTypeEnum;
import org.kuali.student.core.ges.infc.Value;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.date.DateFormatters;


@Entity
@Table(name = "KSEN_GES_VALUE")
@NamedQueries({
        @NamedQuery(name = ValueEntity.GES_VALUE_GET_IDS_BY_TYPE, query = "select id from ValueEntity where typeKey = :type"),
        @NamedQuery(name = ValueEntity.GES_VALUE_GET_BY_PARAMETER, query = "select a from ValueEntity a where parameterKey = :parameterKey"),
        @NamedQuery(name = ValueEntity.GES_VALUE_GET_VALUES_BY_PARAMETERS, query = "select v from ValueEntity v where v.parameterKey in (:parameterKeys) and (v.stateKey = :stateKey)"),
        @NamedQuery(name = ValueEntity.GES_VALUE_GET_VALUES_BY_PARAMETERS_WITH_CRITERIA,
                query = "select v from ValueEntity v where v.parameterKey in (:parameterKeys) and (v.atpId = :atpId or v.atpTypeKey = :atpTypeKey or (v.atpId IS NULL and v.atpTypeKey IS NULL)) and (v.stateKey = :stateKey) order by v.parameterKey, v.priority, v.atpId, v.atpTypeKey")
})
public class ValueEntity extends MetaEntity
        implements AttributeOwner<ValueAttributeEntity> {

    public static final String GES_VALUE_GET_IDS_BY_TYPE = "ValueEntity.getIdsByType";
    public static final String GES_VALUE_GET_BY_PARAMETER = "ValueEntity.getByParameter";
    public static final String GES_VALUE_GET_VALUES_BY_PARAMETERS = "ValueEntity.getValuesByParameters";
    public static final String GES_VALUE_GET_VALUES_BY_PARAMETERS_WITH_CRITERIA = "ValueEntity.getValuesByParametersWithAtpCriteria";


    @Column(name = "GES_VALUE_TYPE", nullable = false)
    private String typeKey;

    @Column(name = "GES_VALUE_STATE", nullable = false)
    private String stateKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT", nullable = true)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT", nullable = true)
    private Date expirationDate;

    @Column(name = "GES_PARM_ID", nullable = false)
    private String parameterKey;

    @Column(name = "PRIORITY", nullable = true)
    private Integer priority;

    @Column(name = "POPULATION_ID", nullable = true)
    private String populationId;

    @Column(name = "RULE_ID", nullable = true)
    private String ruleId;

    @Column(name = "GES_VALUE_TYPE_ID", nullable = false)
    private String valueTypeId;

    @Column(name = "ATP_ID", nullable = true)
    private String atpId;

    @Column(name = "ORG_ID", nullable = true)
    private String orgId;

    @Column(name = "SOC_ID", nullable = true)
    private String socId;

    @Column(name = "SUBJECT_CODE", nullable = true)
    private String subjectCode;

    @Column(name = "ATP_TYPE_KEY", nullable = true)
    private String atpTypeKey;

    @Column(name = "CLU_ID", nullable = true)
    private String cluId;

    @Column(name = "GES_VALUE", nullable = true)
    private String value;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<ValueAttributeEntity> attributes = new HashSet<ValueAttributeEntity>();


    public ValueEntity() {
        super();
    }

    public ValueEntity(Value dto) {
        super(dto);
        this.setId(dto.getId());
        this.setTypeKey(dto.getTypeKey());
        this.setParameterKey(dto.getParameterKey());
        this.fromDto(dto);
    }

    public void fromDto(Value dto) {
        super.fromDTO(dto);
        setStateKey(dto.getStateKey());
        setEffectiveDate(dto.getEffectiveDate());
        setExpirationDate(dto.getExpirationDate());
        setPriority(dto.getPriority());
        setPopulationId(dto.getPopulationId());
        setRuleId(dto.getRuleId());

        setAtpId(dto.getAtpId());
        setOrgId(dto.getOrgId());
        setSocId(dto.getSocId());
        setSubjectCode(dto.getSubjectCode());
        setAtpTypeKey(dto.getAtpTypeKey());
        setCluId(dto.getCluId());

        parseGesValue(dto);



        this.getValueTypeId();


        // dynamic attributes
        this.getAttributes().clear();
        for (Attribute att : dto.getAttributes()) {
            ValueAttributeEntity attEntity = new ValueAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public ValueInfo toDto() {
        ValueInfo info = new ValueInfo();
        info.setId(getId());
        info.setTypeKey(this.getTypeKey());
        info.setStateKey(this.getStateKey());
        info.setEffectiveDate(this.getEffectiveDate());
        info.setExpirationDate(this.getExpirationDate());
        info.setParameterKey(this.getParameterKey());
        info.setPriority(this.getPriority());

        info.setPopulationId(this.getPopulationId());
        info.setRuleId(this.getRuleId());
        info.setAtpId(this.getAtpId());
        info.setOrgId(this.getOrgId());
        info.setSocId(this.getSocId());
        info.setSubjectCode(this.getSubjectCode());
        info.setAtpTypeKey(this.getAtpTypeKey());
        info.setCluId(this.getCluId());

        getStringValue(info);



        setDtoValue(info);

        info.setMeta(super.toDTO());

        // dynamic attributes
        for (ValueAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }
        return info;
    }

    private void parseGesValue(Value value) {

        if (value.getStringValue() != null){
            setValue(value.getStringValue());
            setValueTypeId(GesValueTypeEnum.STRING.name());

        }else if (value.getNumericValue() != null){
            setValue(String.valueOf(value.getNumericValue()));
            setValueTypeId(GesValueTypeEnum.NUMERIC.name());

        }else if (value.getDateValue() != null){
            setValue(String.valueOf(value.getDateValue()));
            setValueTypeId(GesValueTypeEnum.DATE.name());

        }else if (value.getBooleanValue() != null){
            setValue(String.valueOf(value.getBooleanValue()));
            setValueTypeId(GesValueTypeEnum.BOOLEAN.name());

        }else if (value.getDecimalValue() != null){
            setValue(String.valueOf(value.getDecimalValue()));
            setValueTypeId(GesValueTypeEnum.KUALI_DECIMAL.name());

        }else if (value.getAmountValue() != null){
            setValue(String.valueOf(value.getAmountValue()));
            setValueTypeId(GesValueTypeEnum.AMOUNT.name());

        }else if (value.getCurrencyAmountValue() != null){
            setValue(String.valueOf(value.getCurrencyAmountValue()));
            setValueTypeId(GesValueTypeEnum.CURRENCY_AMOUNT.name());

        }else if (value.getTimeAmountValue() != null){
            setValue(String.valueOf(value.getTimeAmountValue()));
            setValueTypeId(GesValueTypeEnum.TIME_AMOUNT.name());

        }else if (value.getTimeOfDayValue() != null){
            setValue(String.valueOf(value.getTimeOfDayValue()));
            setValueTypeId(GesValueTypeEnum.TIME_OF_DAY.name());
        } else if (value.getCustomValue() != null){
           throw new UnsupportedOperationException("Custom values are not supported at this time.");
        }
    }


    private void getStringValue(ValueInfo valueInfo) {

        if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.STRING.name())){
            valueInfo.setStringValue(this.value);

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.BOOLEAN.name())){

            valueInfo.setBooleanValue(Boolean.parseBoolean(this.value));

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.NUMERIC.name())){

            valueInfo.setNumericValue(Long.parseLong(this.value));

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.KUALI_DECIMAL.name())){

            valueInfo.setDecimalValue(new KualiDecimal(this.value));

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.CURRENCY_AMOUNT.name())){

            CurrencyAmountInfo currencyAmountInfo = new CurrencyAmountInfo();
            String[] parts = this.value.split(":");
            currencyAmountInfo.setCurrencyQuantity(Integer.parseInt(parts[0]));
            currencyAmountInfo.setCurrencyTypeKey(parts[1]);

            valueInfo.setCurrencyAmountValue(currencyAmountInfo);

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.TIME_AMOUNT.name())){

            TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
            String[] parts = value.split(":");
            timeAmountInfo.setTimeQuantity(Integer.parseInt(parts[0]));
            timeAmountInfo.setAtpDurationTypeKey(parts[1]);

            valueInfo.setTimeAmountValue(timeAmountInfo);

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.TIME_OF_DAY.name())){

            String[] parts = value.split(":");
            valueInfo.setTimeOfDayValue(new TimeOfDayInfo(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.DATE.name())){
            valueInfo.setDateValue(DateFormatters.SERVER_DATE_PARSER_FORMATTER.parse(this.value));

        }else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.AMOUNT.name())){

            AmountInfo amount = new AmountInfo();
            String[] parts = value.split(":");
            amount.setUnitQuantity(parts[0]);
            amount.setUnitTypeKey(parts[1]);

            valueInfo.setAmountValue(amount);
        } else if (this.getValueTypeId().equalsIgnoreCase(GesValueTypeEnum.GES_CUSTOM_VALUE.name())){

        }





    }



    private void setDtoValue(ValueInfo value) {

    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getStateKey() {
        return this.stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getParameterKey() {
        return this.parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public String getPopulationId() {
        return this.populationId;
    }

    public void setPopulationId(String populationId) {
        this.populationId = populationId;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueTypeId() {
        return valueTypeId;
    }

    public void setValueTypeId(String valueTypeId) {
        this.valueTypeId = valueTypeId;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getAtpTypeKey() {
        return atpTypeKey;
    }

    public void setAtpTypeKey(String atpTypeKey) {
        this.atpTypeKey = atpTypeKey;
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    @Override
    public void setAttributes(Set<ValueAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    @Override
    public Set<ValueAttributeEntity> getAttributes() {
        return attributes;
    }

}

