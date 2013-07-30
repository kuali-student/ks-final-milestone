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
import org.kuali.student.core.rate.infc.FlexibleCreditAmount;

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
        "catalogRateId", "refObjectURI", "refObjectIds",
        "atpId", "amount", "flexibleCreditAmounts",
        "transactionCode", "transactionDate", "transactionDateTypeKey",
        "meta", "attributes", "_futureElements" })

public class RateInfo
    extends IdEntityInfo 
    implements Rate {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String catalogRateId;

    @XmlElement
    private String refObjectURI;
    
    @XmlElement
    private List<String> refObjectIds;

    @XmlElement
    private String atpId;

    @XmlElement
    private CurrencyAmountInfo amount;

    @XmlElement
    private List<FlexibleCreditAmountInfo> flexibleCreditAmounts;

    @XmlElement
    private String transactionCode;

    @XmlElement
    private Date transactionDate;

    @XmlElement
    private String transactionDateTypeKey;

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
            this.refObjectURI = rate.getRefObjectURI();
            
            if (rate.getRefObjectIds() != null) {
                this.refObjectIds = new ArrayList<String>(rate.getRefObjectIds());
            }

            this.atpId = rate.getAtpId();
            this.amount = new CurrencyAmountInfo(rate.getAmount());

            this.flexibleCreditAmounts = new ArrayList<FlexibleCreditAmountInfo>();
            if (rate.getFlexibleCreditAmounts() != null) {
                for (FlexibleCreditAmount amount : rate.getFlexibleCreditAmounts()) {
                    this.flexibleCreditAmounts.add(new FlexibleCreditAmountInfo(amount));
                }
            }

            this.transactionCode = rate.getTransactionCode();
            if (rate.getTransactionDate() != null) {
                this.transactionDate = new Date(rate.getTransactionDate().getTime());
            }

            this.transactionDateTypeKey = rate.getTransactionDateTypeKey();
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
    public String getRefObjectURI() {
        return (this.refObjectURI);
    }

    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }

    @Override
    public List<String> getRefObjectIds() {
        if (this.refObjectIds == null) {
            this.refObjectIds = new ArrayList<String>(0);
        }

        return (this.refObjectIds);
    }

    public void setRefObjectId(List<String> refObjectIds) {
        this.refObjectIds = new ArrayList<String>(refObjectIds);
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
    public List<FlexibleCreditAmountInfo> getFlexibleCreditAmounts() {
        if (this.flexibleCreditAmounts == null) {
            this.flexibleCreditAmounts = new ArrayList<FlexibleCreditAmountInfo>(0);
        }

        return (this.flexibleCreditAmounts);
    }

    public void setFlexibleCreditAmounts(List <FlexibleCreditAmountInfo> flexibleCreditAmounts) {
        this.flexibleCreditAmounts = flexibleCreditAmounts;
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
    public String getTransactionDateTypeKey() {
        return (this.transactionDateTypeKey);
    }

    public void setTransactionDateTypeKey(String transactionDateTypeKey) {
        this.transactionDateTypeKey = transactionDateTypeKey;
    }
}
