/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.w3c.dom.Element;

/**
 * Detailed information about an amount of currency including both the type of units and the quantity.
 *
 * @Author Kamal
 * @Since Mon Jan 11 15:20:51 PST 2010
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyAmountInfo", propOrder = {"id", "currencyTypeKey", "currencyQuantity",
        "meta", "attributes", "_futureElements"})
public class CurrencyAmountInfo extends HasAttributesAndMetaInfo implements CurrencyAmount, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;
    
    @XmlElement
    private String currencyTypeKey;

    @XmlElement
    private Integer currencyQuantity;
    
    @XmlAnyElement
    private List<Element> _futureElements;
        
    public CurrencyAmountInfo() {
        this.id = null;
        this.currencyTypeKey = null;
        this.currencyQuantity = null;
        this._futureElements = null;
    }
    
    public CurrencyAmountInfo(CurrencyAmount currency) {
        super(currency);
        
        if(null == currency) return;
        
        this.id = currency.getId();
        this.currencyQuantity = (null != currency.getCurrencyQuantity()) ? currency.getCurrencyQuantity() : null;
        this.currencyTypeKey = currency.getCurrencyTypeKey();
        this._futureElements = null;
    }
    
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

	/**
	 * 	Identifier for the currency amount record.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
