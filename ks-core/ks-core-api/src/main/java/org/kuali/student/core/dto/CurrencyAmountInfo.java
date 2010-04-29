/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Detailed information about an amount of currency including both the type of units and the quantity.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Mon Jan 11 15:20:51 PST 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/currencyAmountInfo+Structure+v1.0-rc1">CurrencyAmountInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyAmountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String currencyTypeKey;

    @XmlElement
    private Integer currencyQuantity;

    /**
     * The kind of units associated with the quantity, such as US Dollars
     */
    public String getCurrencyTypeKey() {
        return currencyTypeKey;
    }

    public void setCurrencyTypeKey(String currencyTypeKey) {
        this.currencyTypeKey = currencyTypeKey;
    }

    /**
     * The amount of currency
     */
    public Integer getCurrencyQuantity() {
        return currencyQuantity;
    }

    public void setCurrencyQuantity(Integer currencyQuantity) {
        this.currencyQuantity = currencyQuantity;
    }
}