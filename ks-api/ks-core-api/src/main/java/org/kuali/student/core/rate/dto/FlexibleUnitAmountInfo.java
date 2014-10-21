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

import org.kuali.student.core.rate.infc.FlexibleUnitAmount;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlexibleUnitAmountInfo", propOrder = {
        "units", "amount", "transactionCode", "_futureElements" })

public class FlexibleUnitAmountInfo
    implements FlexibleUnitAmount {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String units;

    @XmlElement
    private CurrencyAmountInfo amount;

    @XmlElement
    private String transactionCode;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new FlexibleUnitAmountInfo.
     */
    public FlexibleUnitAmountInfo() {
    }

    /**
     * Constructs a new FlexibleUnitAmountInfo from another
     * FlexibleUnitAmount.
     *
     * @param flexibleUnitAmount the FlexibleUnitAmount to copy
     */
    public FlexibleUnitAmountInfo(FlexibleUnitAmount flexibleUnitAmount) {
        if (flexibleUnitAmount != null) {
            this.units = flexibleUnitAmount.getUnits();
            this.amount = new CurrencyAmountInfo(flexibleUnitAmount.getAmount());
            this.transactionCode = flexibleUnitAmount.getTransactionCode();
        }
    }

    @Override
    public String getUnits() {
        return (this.units);
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public CurrencyAmountInfo getAmount() {
        return (this.amount);
    }

    public void setAmount(CurrencyAmountInfo amount) {
        this.amount = amount;
    }

    @Override
    public String getTransactionCode() {
        return (this.transactionCode);
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}
