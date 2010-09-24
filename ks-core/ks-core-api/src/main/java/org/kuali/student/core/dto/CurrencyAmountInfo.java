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

package org.kuali.student.core.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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

    @XmlAttribute
    private String id;
    
    @XmlElement
    private String currencyTypeKey;

    @XmlElement
    private Integer currencyQuantity;
    
    @XmlElement
    private MetaInfo metaInfo;

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

	/**
	 * Create and last update info for the structure. This is optional and 
	 * treated as read only since the data is set by the internals of the
	 * service during maintenance operations.
	 */
	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}
}
