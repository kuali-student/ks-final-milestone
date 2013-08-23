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
 * This structure is used to capture flexible credit rate amounts by
 * grouping amounts with credits.
 *
 * @author Kuali Student Services
 */

public interface FlexibleCreditAmount
    extends Serializable {

    /**
     * The number of credits as a decimal number.
     * 
     * @return the number of credits
     * @name Credits
     * @required
     */
    public String getCredits();

    /**
     * The amount.
     *
     * @return the amount
     * @name Amount
     * @required
     */
    public CurrencyAmount getAmount();
}
