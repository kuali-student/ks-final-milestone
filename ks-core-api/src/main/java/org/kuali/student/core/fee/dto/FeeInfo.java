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

package org.kuali.student.core.fee.dto;

import org.kuali.student.core.fee.infc.Fee;
import org.kuali.student.core.fee.infc.FlexibleCreditAmount;

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
@XmlType(name = "FeeInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "catalogFeeId", "refObjectURI", "refObjectId",
        "atpId", "amount", "flexibleCreditAmounts",
        "transactionCode", "transactionDate", "transactionDateTypeKey",
        "meta", "attributes", "_futureElements" })

public class FeeInfo
    extends IdEntityInfo 
    implements Fee {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String catalogFeeId;

    @XmlElement
    private String refObjectURI;
    
    @XmlElement
    private String refObjectId;

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
     * Constructs a new FeeInfo.
     */
    public FeeInfo() {
    }

    /**
     * Constructs a new FeeInfo from another Fee.
     *
     * @param fee the Fee to copy
     */
    public FeeInfo(Fee fee) {
        super(fee);

        if (fee != null) {
            this.catalogFeeId = fee.getCatalogFeeId();
            this.refObjectURI = fee.getRefObjectURI();
            this.refObjectId = fee.getRefObjectId();
            this.atpId = fee.getAtpId();
            this.amount = new CurrencyAmountInfo(fee.getAmount());

            this.flexibleCreditAmounts = new ArrayList<FlexibleCreditAmountInfo>();
            if (fee.getFlexibleCreditAmounts() != null) {
                for (FlexibleCreditAmount amount : fee.getFlexibleCreditAmounts()) {
                    this.flexibleCreditAmounts.add(new FlexibleCreditAmountInfo(amount));
                }
            }

            this.transactionCode = fee.getTransactionCode();
            if (fee.getTransactionDate() != null) {
                this.transactionDate = new Date(fee.getTransactionDate().getTime());
            }

            this.transactionDateTypeKey = fee.getTransactionDateTypeKey();
        }
    }

    
    @Override
    public String getCatalogFeeId() {
        return (this.catalogFeeId);
    }
    
    public void setCatalogFeeId(String catalogFeeId) {
        this.catalogFeeId = catalogFeeId;
    }

    @Override
    public String getRefObjectURI() {
        return (this.refObjectURI);
    }

    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }

    @Override
    public String getRefObjectId() {
        return (this.refObjectId);
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
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
