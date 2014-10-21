/*
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.CurrencyAmount;
//import org.w3c.dom.Element;

/**
 * Detailed information about an amount of currency including both the
 * type of units and the quantity.
 *
 * @author Kamal
 * @since Mon Jan 11 15:20:51 PST 2010
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyAmountInfo", propOrder = {"currencyTypeKey", "currencyQuantity", "_futureElements" })

public class CurrencyAmountInfo 
    implements CurrencyAmount, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String currencyTypeKey;

    @XmlElement
    private Integer currencyQuantity;

    @XmlAnyElement
    private List<Object> _futureElements;

    /**
     * Constructs a new CurrencyAmount.
     */
    public CurrencyAmountInfo() {
    }

    /**
     * Constructs a new CurrencyAmount from another Currency.
     *
     * @param currency the currency to copy
     */   
    public CurrencyAmountInfo(CurrencyAmount currency) {
        if (currency != null) {
            this.currencyQuantity = currency.getCurrencyQuantity();
            this.currencyTypeKey = currency.getCurrencyTypeKey();
        }
    }

    @Override
    public String getCurrencyTypeKey() {
        return currencyTypeKey;
    }

    public void setCurrencyTypeKey(String currencyTypeKey) {
        this.currencyTypeKey = currencyTypeKey;
    }

    @Override
    public Integer getCurrencyQuantity() {
        return currencyQuantity;
    }

    public void setCurrencyQuantity(Integer currencyQuantity) {
        this.currencyQuantity = currencyQuantity;
    }
}
