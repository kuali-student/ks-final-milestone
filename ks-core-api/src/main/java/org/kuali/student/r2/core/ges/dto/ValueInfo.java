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
package org.kuali.student.r2.core.ges.dto;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.common.infc.TimeOfDay;
import org.kuali.student.r2.core.ges.infc.Value;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueInfo", propOrder = {"id", "typeKey", "stateKey",
        "parameterId","atpTypeKey","populationId","ruleId", "onDate", "booleanValue",
        "dateValue","numericValue","decimalValue","stringValue","amountValue",
        "currencyAmountValue","timeAmountValue","timeOfDayValue", "meta", "attributes", "_futureElements" })
public class ValueInfo extends IdNamelessEntityInfo implements Value {
    @XmlElement
    private String parameterId;
    @XmlElement
    private String atpTypeKey;
    @XmlElement
    private String populationId;
    @XmlElement
    private String ruleId;
    @XmlElement
    private Date onDate;
    @XmlElement
    private Boolean booleanValue;
    @XmlElement
    private Date dateValue;
    @XmlElement
    private Long numericValue;
    @XmlElement
    private KualiDecimal decimalValue;
    @XmlElement
    private String stringValue;
    @XmlElement
    private Amount amountValue;
    @XmlElement
    private CurrencyAmount currencyAmountValue;
    @XmlElement
    private TimeAmount timeAmountValue;
    @XmlElement
    private TimeOfDay timeOfDayValue;

    @XmlAnyElement
    private List<Object> _futureElements;

    public ValueInfo(){

    }
    public ValueInfo(Value value){
        super(value);
        if(value != null) {
            parameterId = value.getParameterId();
            atpTypeKey = value.getAtpTypeKey();
            populationId = value.getPopulationId();
            ruleId = value.getRuleId();
            if(value.getOnDate() != null) {
                onDate = new Date(value.getOnDate().getTime());
            }
            booleanValue = value.getBooleanValue();
            dateValue = value.getDateValue();
            numericValue = value.getNumericValue();
            decimalValue = value.getDecimalValue();
            stringValue = value.getStringValue();
            amountValue = value.getAmountValue();
            currencyAmountValue = value.getCurrencyAmountValue();
            timeAmountValue = value.getTimeAmountValue();
            timeAmountValue = value.getTimeAmountValue();
        }

    }

    @Override
    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    @Override
    public String getAtpTypeKey() {
        return atpTypeKey;
    }

    public void setAtpTypeKey(String atpTypeKey) {
        this.atpTypeKey = atpTypeKey;
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
    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    @Override
    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
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
