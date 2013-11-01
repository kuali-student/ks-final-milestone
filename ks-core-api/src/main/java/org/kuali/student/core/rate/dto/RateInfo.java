/*
 * Copyright 2013 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.rate.dto;

import org.kuali.student.core.rate.infc.Rate;
import org.kuali.student.core.rate.infc.FlexibleUnitAmount;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "catalogRateId", "atpId", "amount", "flexibleUnitAmounts",
        "transactionCode", "transactionDateTypeKey", "transactionDate", 
        "recognitionDate", "isLimitRate", "minimumLimitUnits",
        "maximumLimitUnits", "limitAmount",
        "meta", "attributes", "_futureElements" })

public class RateInfo
    extends IdEntityInfo 
    implements Rate {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String catalogRateId;

    @XmlElement
    private String atpId;

    @XmlElement
    private CurrencyAmountInfo amount;

    @XmlElement
    private List<FlexibleUnitAmountInfo> flexibleUnitAmounts;

    @XmlElement
    private String transactionCode;

    @XmlElement
    private String transactionDateTypeKey;

    @XmlElement
    private Date transactionDate;

    @XmlElement
    private Date recognitionDate;

    @XmlElement
    private Boolean isLimitRate;

    @XmlElement
    private Integer minimumLimitUnits;

    @XmlElement
    private Integer maximumLimitUnits;

    @XmlElement
    private CurrencyAmountInfo limitAmount;
    
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new RateInfo.
     */
    public RateInfo() {
    }

    /**
     * Constructs a new RateInfo from another Rate.
     *
     * @param rate the Rate to copy
     */
    public RateInfo(Rate rate) {
        super(rate);

        if (rate != null) {
            this.catalogRateId = rate.getCatalogRateId();
            this.atpId = rate.getAtpId();
            if (rate.getAmount() != null) {
                this.amount = new CurrencyAmountInfo(rate.getAmount());
            }

            this.flexibleUnitAmounts = new ArrayList<FlexibleUnitAmountInfo>();
            if (rate.getFlexibleUnitAmounts() != null) {
                for (FlexibleUnitAmount amount : rate.getFlexibleUnitAmounts()) {
                    this.flexibleUnitAmounts.add(new FlexibleUnitAmountInfo(amount));
                }
            }

            this.transactionCode = rate.getTransactionCode();
            this.transactionDateTypeKey = rate.getTransactionDateTypeKey();
            if (rate.getTransactionDate() != null) {
                this.transactionDate = new Date(rate.getTransactionDate().getTime());
            }

            if (rate.getRecognitionDate() != null) {
                this.recognitionDate = new Date(rate.getRecognitionDate().getTime());
            }
            
            this.isLimitRate = rate.getIsLimitRate();
            this.minimumLimitUnits = rate.getMinimumLimitUnits();
            this.maximumLimitUnits = rate.getMaximumLimitUnits();

            if (rate.getLimitAmount() != null) {
                this.amount = new CurrencyAmountInfo(rate.getLimitAmount());
            }
        }
    }

    
    @Override
    public String getCatalogRateId() {
        return (this.catalogRateId);
    }
    
    public void setCatalogRateId(String catalogRateId) {
        this.catalogRateId = catalogRateId;
    }

    @Override
    public String getAtpId() {
        return (this.atpId);
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    @Override
    public CurrencyAmountInfo getAmount() {
        return (this.amount);
    }

    public void setAmount(CurrencyAmountInfo amount) {
        this.amount = amount;
    }

    @Override
    public List<FlexibleUnitAmountInfo> getFlexibleUnitAmounts() {
        if (this.flexibleUnitAmounts == null) {
            this.flexibleUnitAmounts = new ArrayList<FlexibleUnitAmountInfo>(0);
        }

        return (this.flexibleUnitAmounts);
    }

    public void setFlexibleUnitAmounts(List <FlexibleUnitAmountInfo> flexibleUnitAmounts) {
        this.flexibleUnitAmounts = flexibleUnitAmounts;
    }

    @Override
    public String getTransactionDateTypeKey() {
        return (this.transactionDateTypeKey);
    }

    public void setTransactionDateTypeKey(String transactionDateTypeKey) {
        this.transactionDateTypeKey = transactionDateTypeKey;
    }

    @Override
    public String getTransactionCode() {
        return (this.transactionCode);
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Override
    public Date getTransactionDate() {
        return (this.transactionDate);
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public Date getRecognitionDate() {
        return (this.recognitionDate);
    }

    public void setRecognitionDate(Date recognitionDate) {
        this.recognitionDate = recognitionDate;
    }

    @Override
    public Boolean getIsLimitRate() {
        return (this.isLimitRate);
    }

    public void setIsLimitRate(Boolean isLimitRate) {
        this.isLimitRate = isLimitRate;
    }

    @Override
    public Integer getMinimumLimitUnits() {
        return (this.minimumLimitUnits);
    }

    public void setMinimumLimitUnits(Integer minimumLimitUnits) {
        this.minimumLimitUnits = minimumLimitUnits;
    }

    @Override
    public Integer getMaximumLimitUnits() {
        return (this.maximumLimitUnits);
    }

    public void setMaximumLimitUnits(Integer maximumLimitUnits) {
        this.maximumLimitUnits = maximumLimitUnits;
    }

    @Override
    public CurrencyAmountInfo getLimitAmount() {
        return (this.limitAmount);
    }

    public void setLimitAmount(CurrencyAmountInfo limitAmount) {
        this.limitAmount = limitAmount;
    }    
}
