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

import org.kuali.student.core.fee.infc.CatalogFee;
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
@XmlType(name = "CatalogFeeInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "code", "applicableAtpIds", "minimumAmount",
        "maximumAmount", "isFixedCreditAmountCapped",
        "cappedFixedCreditAmount", "flexibleCreditAmounts",
        "canOverrideTransactionCode", "transactionCode", 
        "canOverrideTransactionDateType", "transactionDateTypeKey", 
        "meta", "attributes", "_futureElements" })

public class CatalogFeeInfo
    extends IdEntityInfo 
    implements CatalogFee {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;

    @XmlElement
    private List<String> applicableAtpIds;
    
    @XmlElement
    private CurrencyAmountInfo minimumAmount;

    @XmlElement
    private CurrencyAmountInfo maximumAmount;

    @XmlElement
    private Boolean isFixedCreditAmountCapped;

    @XmlElement
    private CurrencyAmountInfo cappedFixedCreditAmount;

    @XmlElement
    private List<FlexibleCreditAmountInfo> flexibleCreditAmounts;
    
    @XmlElement
    private Boolean canOverrideTransactionCode;
    
    @XmlElement
    private String transactionCode;

    @XmlElement
    private Boolean canOverrideTransactionDateType;
    
    @XmlElement
    private String transactionDateTypeKey;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new CatalogCatalogFeeInfo.
     */
    public CatalogFeeInfo() {
    }

    /**
     * Constructs a new CatalogFeeInfo from another CatalogFee.
     *
     * @param catalogFee the CatalogFee to copy
     */
    public CatalogFeeInfo(CatalogFee catalogFee) {
        super(catalogFee);

        if (catalogFee != null) {
            this.code = catalogFee.getCode();

            if (catalogFee.getApplicableAtpIds() != null) {
                this.applicableAtpIds = new ArrayList<String>(catalogFee.getApplicableAtpIds());
            }

            this.minimumAmount = new CurrencyAmountInfo(catalogFee.getMinimumAmount());
            this.maximumAmount = new CurrencyAmountInfo(catalogFee.getMaximumAmount());
            this.isFixedCreditAmountCapped = catalogFee.getIsFixedCreditAmountCapped();
            this.cappedFixedCreditAmount = new CurrencyAmountInfo(catalogFee.getCappedFixedCreditAmount());
            this.flexibleCreditAmounts = new ArrayList<FlexibleCreditAmountInfo>();
            if (catalogFee.getFlexibleCreditAmounts() != null) {
                for (FlexibleCreditAmount amount : catalogFee.getFlexibleCreditAmounts()) {
                    this.flexibleCreditAmounts.add(new FlexibleCreditAmountInfo(amount));
                }
            }

            this.canOverrideTransactionCode = catalogFee.getCanOverrideTransactionCode();
            this.transactionCode = catalogFee.getTransactionCode();

            this.canOverrideTransactionDateType = catalogFee.getCanOverrideTransactionDateType();
            this.transactionDateTypeKey = catalogFee.getTransactionDateTypeKey();
        }
    }

    @Override
    public String getCode() {
        return (this.code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public List<String> getApplicableAtpIds() {
        if (this.applicableAtpIds == null) {
            this.applicableAtpIds = new ArrayList<String>(0);
        }

        return (this.applicableAtpIds);
    }

    public void setApplicableAtpIds(List <String> applicableAtpIds) {
        this.applicableAtpIds = applicableAtpIds;
    }

    @Override
    public CurrencyAmountInfo getMinimumAmount() {
        return (this.minimumAmount);
    }

    public void setMinimumAmount(CurrencyAmountInfo minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    @Override
    public CurrencyAmountInfo getMaximumAmount() {
        return (this.maximumAmount);
    }

    public void setMaximumAmount(CurrencyAmountInfo maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    @Override
    public Boolean getIsFixedCreditAmountCapped() {
        return (this.isFixedCreditAmountCapped);
    }

    public void setIsFixedCreditAmountCapped(Boolean isFixedCreditAmountCapped) {
        this.isFixedCreditAmountCapped = isFixedCreditAmountCapped;
    }

    @Override
    public CurrencyAmountInfo getCappedFixedCreditAmount() {
        return (this.cappedFixedCreditAmount);
    }

    public void setCappedFixedCreditAmount(CurrencyAmountInfo cappedFixedCreditAmount) {
        this.cappedFixedCreditAmount = cappedFixedCreditAmount;
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
    public Boolean getCanOverrideTransactionCode() {
        return (this.canOverrideTransactionCode);
    }

    public void setCanOverrideTransactionCode(Boolean canOverrideTransactionCode) {
        this.canOverrideTransactionCode = canOverrideTransactionCode;
    }

    @Override
    public String getTransactionCode() {
        return (this.transactionCode);
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Override
    public Boolean getCanOverrideTransactionDateType() {
        return (this.canOverrideTransactionDateType);
    }

    public void setCanOverrideTransactionDateType(Boolean canOverrideTransactionDateType) {
        this.canOverrideTransactionDateType = canOverrideTransactionDateType;
    }

    @Override
    public String getTransactionDateTypeKey() {
        return (this.transactionDateTypeKey);
    }

    public void setTransactionDateTypeKey(String TransactionDateTypeKey) {
        this.transactionDateTypeKey = transactionDateTypeKey;
    }
}
