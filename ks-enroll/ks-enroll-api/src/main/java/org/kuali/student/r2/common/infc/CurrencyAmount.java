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

package org.kuali.student.r2.common.infc;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;

/**
 * Detailed information about an amount of currency including both the type of units and the quantity.
 *
 * @Author Kamal
 * @Since Mon Jan 11 15:20:51 PST 2010
 *
 */
public interface CurrencyAmount extends HasId, HasMeta {
        
    /**
     * The kind of units associated with the quantity, such as US Dollars
     * @name Currency Type Key
     */
    public String getCurrencyTypeKey();
    
    /**
     * The amount of currency
     * @name Currency Quantity
     */
    public Integer getCurrencyQuantity();
    
}
