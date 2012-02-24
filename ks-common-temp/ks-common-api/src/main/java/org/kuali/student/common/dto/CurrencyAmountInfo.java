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

package org.kuali.student.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.CurrencyAmount;

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
@XmlType(name = "CurrencyAmountInfo", propOrder = {"id", "currencyTypeKey", "currencyQuantity",
        "meta" /*TODO KSCM-gwt-compile , "_futureElements" */ })
public class CurrencyAmountInfo implements CurrencyAmount, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;
    
    @XmlElement
    private String currencyTypeKey;

    @XmlElement
    private Integer currencyQuantity;
    
    //TODO KSCM-gwt-compile
    //@XmlAnyElement
    //private List<Element> _futureElements;

    @XmlElement
    private MetaInfo meta;
    
    public CurrencyAmountInfo() {
        this.id = null;
        this.currencyTypeKey = null;
        this.currencyQuantity = null;
        this.meta = null;
        //TODO KSCM-gwt-compile this._futureElements = null;
    }
    
    public CurrencyAmountInfo(CurrencyAmount currency) {

        if(null == currency) return;
        
        this.id = currency.getId();
        this.currencyQuantity = (null != currency.getCurrencyQuantity()) ? currency.getCurrencyQuantity() : null;
        this.currencyTypeKey = currency.getCurrencyTypeKey();
        this.meta = null != currency.getMeta() ? MetaInfo
                    .getInstance(currency.getMeta()) : null;
        //TODO KSCM-gwt-compile this._futureElements = null;
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
	
    @Override
    public MetaInfo getMeta() {
        return this.meta;
    }

    public void setMeta(MetaInfo metaInfo) {
        this.meta = metaInfo;
    }	
}
