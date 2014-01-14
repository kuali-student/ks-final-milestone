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
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.common.infc.TimeOfDay;
import org.kuali.student.r2.common.util.date.DateFormatters;
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
        "parameterKey","priority","atpTypeKeys","populationId","ruleId", "value", "meta", "attributes", "_futureElements" })
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
    private String value;
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
            this.value = value.getStringValue();
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
        if(value != null) {
            return Boolean.parseBoolean(value);
        }
        return null;
    }

    public void setBooleanValue(Boolean booleanValue) {
        if(booleanValue != null) {
            value = booleanValue.toString();
        } else {
            value = null;
        }
    }

    @Override
    public Date getDateValue() {
        if(value != null) {
            return DateFormatters.SERVER_DATE_PARSER_FORMATTER.parse(value);
        }
        return null;
    }

    public void setDateValue(Date dateValue) {
        if(dateValue != null) {
            value = DateFormatters.SERVER_DATE_PARSER_FORMATTER.format(dateValue);
        } else {
            value = null;
        }
    }

    @Override
    public Long getNumericValue() {
        if(value != null) {
            return Long.parseLong(value);
        }
        return null;
    }

    public void setNumericValue(Long numericValue) {
        if(numericValue != null) {
            value = numericValue.toString();
        }
        else {
            value = null;
        }
    }

    @Override
    public KualiDecimal getDecimalValue() {
        if(value != null) {
            return new KualiDecimal(value);
        }
        return null;
    }

    public void setDecimalValue(KualiDecimal decimalValue) {
        if(decimalValue != null) {
            value = decimalValue.toString();
        } else {
            value = null;
        }
    }

    @Override
    public String getStringValue() {
        return value;
    }

    public void setStringValue(String stringValue) {
        value = stringValue;
    }

    @Override
    public Amount getAmountValue() {
        if(value != null) {
            AmountInfo amount = new AmountInfo();
            String[] parts = value.split(":");
            amount.setUnitQuantity(parts[0]);
            amount.setUnitTypeKey(parts[1]);

            return amount;
        }
        return null;
    }

    public void setAmountValue(Amount amountValue) {
        if(amountValue != null) {
            value = amountValue.getUnitQuantity() + ":" + amountValue.getUnitTypeKey();
        } else {
            value = null;
        }
    }

    @Override
    public CurrencyAmount getCurrencyAmountValue() {
        if(value != null) {
            CurrencyAmountInfo currencyAmountInfo = new CurrencyAmountInfo();
            String[] parts = value.split(":");
            currencyAmountInfo.setCurrencyQuantity(Integer.parseInt(parts[0]));
            currencyAmountInfo.setCurrencyTypeKey(parts[1]);
            return currencyAmountInfo;
        }
        return null;
    }

    public void setCurrencyAmountValue(CurrencyAmount currencyAmountValue) {
        if(currencyAmountValue != null) {
           value = currencyAmountValue.getCurrencyQuantity() + ":" + currencyAmountValue.getCurrencyTypeKey();
        } else {
            value = null;
        }
    }

    @Override
    public TimeAmount getTimeAmountValue() {
        if(value != null) {
            TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
            String[] parts = value.split(":");
            timeAmountInfo.setTimeQuantity(Integer.parseInt(parts[0]));
            timeAmountInfo.setAtpDurationTypeKey(parts[1]);
            return timeAmountInfo;
        }
        return null;
    }

    public void setTimeAmountValue(TimeAmount timeAmountValue) {
        if(timeAmountValue != null) {
            value = timeAmountValue.getTimeQuantity() + ":" + timeAmountValue.getAtpDurationTypeKey();
        } else {
            value = null;
        }
    }

    @Override
    public TimeOfDay getTimeOfDayValue() {
        if(value != null) {
            String[] parts = value.split(":");
            return new TimeOfDayInfo(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        }
        return null;
    }

    public void setTimeOfDayValue(TimeOfDay timeOfDayValue) {
        if(timeOfDayValue != null) {
            value = pullSafeValue(timeOfDayValue.getHour()) + ":" +
                    pullSafeValue(timeOfDayValue.getMinute()) + ":" +
                    pullSafeValue(timeOfDayValue.getSecond());
        } else {
            value = null;
        }
    }

    public List<Object> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Object> _futureElements) {
        this._futureElements = _futureElements;
    }

    private String pullSafeValue(Integer input) {
        if(input == null) {
            return "0";
        }
        return input.toString();
    }
}
