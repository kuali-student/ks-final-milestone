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
import javax.xml.bind.annotation.XmlElement;

/**
 * Detailed information about an amount including both the type of units and the quantity.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Mon Jan 11 15:20:30 PST 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/amountInfo+Structure+v1.0-rc1">AmountInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AmountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String unitType;

    @XmlElement
    private String unitQuantity;

    /**
     * The kind of units associated with the quantity, such as hours/week. It is expected that in usage in other structures, this value will always be enumerated based on that context.
     */
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    /**
     * The amount of units. Allowed values consist of numeric values as well as the string "unbounded".
     */
    public String getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(String unitQuantity) {
        this.unitQuantity = unitQuantity;
    }
}
