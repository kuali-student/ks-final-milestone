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

import org.kuali.student.core.rate.infc.CatalogRate;
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
@XmlType(name = "CatalogRateInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "code", "applicableAtpIds", "minimumAmount",
        "maximumAmount", "flexibleUnitAmounts",
        "isTransactionCodeFinal", "transactionCode", 
        "isTransactionDateTypeFinal", "transactionDateTypeKey", 
        "isRecognitionDateDefinable", "isLimitRateFinal",
        "isLimitRate", "minimumLimitUnits", "maximumLimitUnits",
        "minimumLimitAmount", "maximumLimitAmount",
        "meta", "attributes", "_futureElements" })

public class CatalogRateInfo
    extends IdEntityInfo 
    implements CatalogRate {

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
    private List<FlexibleUnitAmountInfo> flexibleUnitAmounts;
    
    @XmlElement
    private Boolean isTransactionCodeFinal;
    
    @XmlElement
    private String transactionCode;

    @XmlElement
    private Boolean isTransactionDateTypeFinal;
    
    @XmlElement
    private String transactionDateTypeKey;

    @XmlElement
    private Boolean isRecognitionDateDefinable;

    @XmlElement
    private Boolean isLimitRateFinal;

    @XmlElement
    private Boolean isLimitRate;

    @XmlElement
    private Integer minimumLimitUnits;

    @XmlElement
    private Integer maximumLimitUnits;

    @XmlElement
    private CurrencyAmountInfo minimumLimitAmount;

    @XmlElement
    private CurrencyAmountInfo maximumLimitAmount;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new CatalogCatalogRateInfo.
     */
    public CatalogRateInfo() {
    }

    /**
     * Constructs a new CatalogRateInfo from another CatalogRate.
     *
     * @param catalogRate the CatalogRate to copy
     */
    public CatalogRateInfo(CatalogRate catalogRate) {
        super(catalogRate);

        if (catalogRate != null) {
            this.code = catalogRate.getCode();

            if (catalogRate.getApplicableAtpIds() != null) {
                this.applicableAtpIds = new ArrayList<String>(catalogRate.getApplicableAtpIds());
            }

            if (catalogRate.getMinimumAmount() != null) {
                this.minimumAmount = new CurrencyAmountInfo(catalogRate.getMinimumAmount());
            }

            if (catalogRate.getMaximumAmount() != null) {
                this.maximumAmount = new CurrencyAmountInfo(catalogRate.getMaximumAmount());
            }

            this.flexibleUnitAmounts = new ArrayList<FlexibleUnitAmountInfo>();
            if (catalogRate.getFlexibleUnitAmounts() != null) {
                for (FlexibleUnitAmount amount : catalogRate.getFlexibleUnitAmounts()) {
                    this.flexibleUnitAmounts.add(new FlexibleUnitAmountInfo(amount));
                }
            }

            this.isTransactionCodeFinal = catalogRate.getIsTransactionCodeFinal();
            this.transactionCode = catalogRate.getTransactionCode();

            this.isTransactionDateTypeFinal = catalogRate.getIsTransactionDateTypeFinal();
            this.transactionDateTypeKey = catalogRate.getTransactionDateTypeKey();
            this.isRecognitionDateDefinable = catalogRate.getIsRecognitionDateDefinable();
            this.isLimitRateFinal = catalogRate.getIsLimitRateFinal();
            this.isLimitRate = catalogRate.getIsLimitRate();
            this.minimumLimitUnits = catalogRate.getMinimumLimitUnits();
            this.maximumLimitUnits = catalogRate.getMaximumLimitUnits();

            if (catalogRate.getMinimumLimitAmount() != null) {
                this.minimumLimitAmount = new CurrencyAmountInfo(catalogRate.getMinimumLimitAmount());
            }

            if (catalogRate.getMaximumLimitAmount() != null) {
                this.maximumLimitAmount = new CurrencyAmountInfo(catalogRate.getMaximumLimitAmount());
            }
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
    public Boolean getIsTransactionCodeFinal() {
        return (this.isTransactionCodeFinal);
    }

    public void setIsTransactionCodeFinal(Boolean isTransactionCodeFinal) {
        this.isTransactionCodeFinal = isTransactionCodeFinal;
    }

    @Override
    public String getTransactionCode() {
        return (this.transactionCode);
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Override
    public Boolean getIsTransactionDateTypeFinal() {
        return (this.isTransactionDateTypeFinal);
    }

    public void setIsTransactionDateTypeFinal(Boolean isTransactionDateTypeFinal) {
        this.isTransactionDateTypeFinal = isTransactionDateTypeFinal;
    }

    @Override
    public String getTransactionDateTypeKey() {
        return (this.transactionDateTypeKey);
    }

    public void setTransactionDateTypeKey(String TransactionDateTypeKey) {
        this.transactionDateTypeKey = transactionDateTypeKey;
    }

    @Override
    public Boolean getIsRecognitionDateDefinable() {
        return (this.isRecognitionDateDefinable);
    }

    public void setIsRecognitionDateDefinable(Boolean isRecognitionDateDefinable) {
        this.isRecognitionDateDefinable = isRecognitionDateDefinable;
    }

    @Override
    public Boolean getIsLimitRateFinal() {
        return (this.isLimitRateFinal);
    }

    public void setIsLimitRateFinal(Boolean isLimitRateFinal) {
        this.isLimitRateFinal = isLimitRateFinal;
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
    public CurrencyAmountInfo getMinimumLimitAmount() {
        return (this.minimumLimitAmount);
    }

    public void setMinimumLimitAmount(CurrencyAmountInfo minimumLimitAmount) {
        this.minimumLimitAmount = minimumLimitAmount;
    }

    @Override
    public CurrencyAmountInfo getMaximumLimitAmount() {
        return (this.maximumLimitAmount);
    }

    public void setMaximumLimitAmount(CurrencyAmountInfo maximumLimitAmount) {
        this.maximumLimitAmount = maximumLimitAmount;
    }
}
