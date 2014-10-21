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

package org.kuali.student.core.rate.infc;

import org.kuali.student.r2.common.infc.CurrencyAmount;
import java.io.Serializable;

/**
 * This structure is used to capture flexible unit rate amounts by
 * grouping amounts with a number of units.
 *
 * @author Kuali Student Services
 */

public interface FlexibleUnitAmount
    extends Serializable {

    /**
     * The number of units as a decimal number.
     * 
     * @return the number of units
     * @name Units
     * @required
     */
    public String getUnits();

    /**
     * The amount.
     *
     * @return the amount
     * @name Amount
     * @required
     */
    public CurrencyAmount getAmount();

    /**
     * Gets another override transaction code. This can be the same
     * transaction code defined in the Rate. Or, the transaction code
     * can differ on a unit by unit basis (USC) if not final.
     *
     * @return the transaction code
     * @name Transaction Code
     */
    public String getTransactionCode();
}
