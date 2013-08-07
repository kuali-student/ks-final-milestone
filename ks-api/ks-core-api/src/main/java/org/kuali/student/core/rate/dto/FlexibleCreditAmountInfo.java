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

import org.kuali.student.core.rate.infc.FlexibleCreditAmount;
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
@XmlType(name = "FlexibleCreditAmountInfo", propOrder = {
        "credits", "amount", "_futureElements" })

public class FlexibleCreditAmountInfo
    implements FlexibleCreditAmount {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String credits;

    @XmlElement
    private CurrencyAmountInfo amount;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new FlexibleCreditAmountInfo.
     */
    public FlexibleCreditAmountInfo() {
    }

    /**
     * Constructs a new FlexibleCreditAmountInfo from another
     * FlexibleCreditAmount.
     *
     * @param flexibleCreditAmount the FlexibleCreditAmount to copy
     */
    public FlexibleCreditAmountInfo(FlexibleCreditAmount flexibleCreditAmount) {
        if (flexibleCreditAmount != null) {
            this.credits = flexibleCreditAmount.getCredits();
            this.amount = new CurrencyAmountInfo(flexibleCreditAmount.getAmount());
        }
    }

    @Override
    public String getCredits() {
        return (this.credits);
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    @Override
    public CurrencyAmountInfo getAmount() {
        return (this.amount);
    }

    public void setAmount(CurrencyAmountInfo amount) {
        this.amount = amount;
    }
}
