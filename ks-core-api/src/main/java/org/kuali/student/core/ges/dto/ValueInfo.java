/*
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.ges.dto;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.common.infc.TimeOfDay;
import org.kuali.student.core.ges.infc.Value;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueInfo", propOrder = {"id", "typeKey", "stateKey", "effectiveDate", "expirationDate",
        "parameterKey","priority","atpTypeKeys","populationId","ruleId","stringValue","numericValue","dateValue","booleanValue",
        "decimalValue","amountValue","currencyAmountValue","timeAmountValue","timeOfDayValue","customValue", "meta", "attributes", "_futureElements" })
public class ValueInfo extends IdNamelessEntityInfo implements Value, HasEffectiveDates {
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlElement
    private String parameterKey;
    @XmlElement
    private Integer priority;
    @XmlElement
    private List<String> atpTypeKeys;
    @XmlElement
    private String populationId;
    @XmlElement
    private String ruleId;
    @XmlElement
    private String stringValue;
    @XmlElement
    private Long numericValue;
    @XmlElement
    private Date dateValue;
    @XmlElement
    private boolean booleanValue;
    @XmlElement
    private KualiDecimal decimalValue;
    @XmlElement
    private Amount amountValue;
    @XmlElement
    private CurrencyAmount currencyAmountValue;
    @XmlElement
    private TimeAmount timeAmountValue;
    @XmlElement
    private TimeOfDay timeOfDayValue;
    @XmlElement
    private GesCustomValueInfo customValue;
    @XmlAnyElement
    private List<Object> _futureElements;

    public ValueInfo(){

    }
    public ValueInfo(Value value) throws OperationFailedException {
        super(value);
        if(value != null) {
            parameterKey = value.getParameterKey();
            priority = value.getPriority();
            if (value.getAtpTypeKeys().size() > 0){
                atpTypeKeys = new ArrayList<String>(value.getAtpTypeKeys());
            }
            populationId = value.getPopulationId();
            ruleId = value.getRuleId();
            if(value.getEffectiveDate() != null) {
                effectiveDate = new Date(value.getEffectiveDate().getTime());
            }
            if(value.getExpirationDate() != null) {
                expirationDate = new Date(value.getExpirationDate().getTime());
            }
            stringValue = value.getStringValue();

            numericValue = value.getNumericValue();

            dateValue = value.getDateValue();

            booleanValue = value.getBooleanValue();

            decimalValue = value.getDecimalValue();

            amountValue = value.getAmountValue();

            currencyAmountValue = value.getCurrencyAmountValue();

            timeAmountValue = value.getTimeAmountValue();

            timeOfDayValue = value.getTimeOfDayValue();

            if(value.getCustomValue() != null) {
                customValue = new GesCustomValueInfo(value.getCustomValue());
            }

        }
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }
    @Override
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public List<String> getAtpTypeKeys() {
        if (atpTypeKeys == null){
            atpTypeKeys = new ArrayList<String>();
        }
        return atpTypeKeys;
    }

    public void setAtpTypeKeys(List<String> atpTypeKey) {
        this.atpTypeKeys = atpTypeKey;
    }

    @Override
    public String getPopulationId() {
        return populationId;
    }

    public void setPopulationId(String populationId) {
        this.populationId = populationId;
    }

    @Override
    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Override
    public GesCustomValueInfo getCustomValue() {
        return customValue;
    }

    public void setCustomValue(GesCustomValueInfo customValue) {
        this.customValue = customValue;
    }

    @Override
    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public Long getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Long numericValue) {
        this.numericValue = numericValue;
    }

    @Override
    public KualiDecimal getDecimalValue() {
        return decimalValue;
    }

    public void setDecimalValue(KualiDecimal decimalValue) {
        this.decimalValue = decimalValue;
    }

    @Override
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public Amount getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(Amount amountValue) {
        this.amountValue = amountValue;
    }

    @Override
    public CurrencyAmount getCurrencyAmountValue() {
        return currencyAmountValue;
    }

    public void setCurrencyAmountValue(CurrencyAmount currencyAmountValue) {
        this.currencyAmountValue = currencyAmountValue;
    }

    @Override
    public TimeAmount getTimeAmountValue() {
        return timeAmountValue;
    }

    public void setTimeAmountValue(TimeAmount timeAmountValue) {
        this.timeAmountValue = timeAmountValue;
    }

    @Override
    public TimeOfDay getTimeOfDayValue() {
        return timeOfDayValue;
    }

    public void setTimeOfDayValue(TimeOfDay timeOfDayValue) {
        this.timeOfDayValue = timeOfDayValue;
    }

    public List<Object> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Object> _futureElements) {
        this._futureElements = _futureElements;
    }


}
