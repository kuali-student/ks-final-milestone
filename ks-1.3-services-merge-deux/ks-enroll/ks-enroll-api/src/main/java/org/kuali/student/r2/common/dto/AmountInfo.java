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

import org.w3c.dom.Element;

import org.kuali.student.r2.common.infc.Amount;

/**
 * Detailed information about an amount including both the type of
 * units and the quantity.
 *
 * @author Kamal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountInfo", propOrder = {
                "unitTypeKey", "unitQuantity", "_futureElements" })

public class AmountInfo 
    implements Amount, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String unitTypeKey;

    @XmlElement
    private String unitQuantity;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public AmountInfo() {
    }
    
    public AmountInfo(Amount amount) {
        if (amount != null) {
            this.unitQuantity = amount.getUnitQuantity();
            this.unitTypeKey = amount.getUnitTypeKey();
        }
    }

    @Override
    public String getUnitTypeKey() {
        return unitTypeKey;
    }

    public void setUnitTypeKey(String unitTypeKey) {
        this.unitTypeKey = unitTypeKey;
    }

    @Override
    public String getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(String unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    // Compatbility
    @Override
    @Deprecated
    public String getUnitType() {
        return getUnitTypeKey();
    }

    @Deprecated
    public void setUnitType(String unitTypeKey) {
        setUnitTypeKey(unitTypeKey);
    }
}
