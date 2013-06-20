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

package org.kuali.student.core.fee.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.CurrencyAmount;

import java.util.List;

/**
 * The CatalogFee is a list of "canonical" fees in a "catalog" that
 * can be used in establishing an actual Fee.
 *
 * The Catalog Fee has one of three flavors indicated by its Type:
 *
 *    1. Flat - A fee that doesn't vary. The Catalog Fee specifies
 *              a minimum and maxmimum amount to constrain the Fee.
 *  
 *    2. Fixed Credit - A fee per credit where the total amount is
 *                      the fee multiplied by the credits (or units) 
 *                      determined by what the fee applies to. The 
 *                      Catalog Fee specifies a minimum and maxmimum
 *                      amount for the fee per credit.
 * 
 *    3. Flexible Credit - A specific fee for each credit value. The
 *                         Catalog Fee specifies the list of
 *                         acceptable values (amount and number of
 *                         credits) allowed in a Fee.
 */

public interface CatalogFee
    extends IdEntity {

    /**
     * The code for the catalog fee.
     * 
     * @return the catalog fee code
     * @name Code
     */
    public String getCode();

    /**
     * The list of ATPs in which this catalog fee can be applied. A
     * Course Offering in an ATP not in this list cannot have a fee
     * derived from this catalog fee.
     * 
     * @return a list of applicable ATP Ids
     * @name Applicable ATP Ids
     */
    public List<String> getApplicableAtpIds();

    /**
     * The minimum amount for a flat or fixed credit Fee. This field
     * is not applicable for flexible credit fees.
     * 
     * @return the minimum amount
     * @name Minimum Amount
     */
    public CurrencyAmount getMinimumAmount();

    /**
     * The maximum amount for a flat or fixed credit Fee. This field
     * is not applicable for flexible credit fees.
     * 
     * @return the maximum amount
     * @name Maximum Amount
     */
    public CurrencyAmount getMaximumAmount();

    /**
     * Tests if a fixed amount is capped. This is only applicable to
     * fixed fees.
     *
     * @return true if the fixed amount is capped, false otherwise
     * @name Is Fixed Credit Amount Capped
     */
    public Boolean getIsFixedCreditAmountCapped();

    /**
     * The maximum amount for a fixed credit Fee. For a fixed credit
     * fee, getMinimumAmount() and getMaxmimumAmount() describe the
     * acceptable range of amounts per credit. This field specifies
     * the cap on the amount multiplied by the number of credits.
     *
     * @return the capped fixed credit amount
     * @name Capped Fixed Credit Amount
     */
    public CurrencyAmount getCappedFixedCreditAmount();

    /**
     * The allowed list of flexible credit amounts in flexible credit
     * Fees.
     *
     * @return a list of flexible credit amounts
     * @name Flexible Credit Amounts
     */
    public List<? extends FlexibleCreditAmount> getFlexibleCreditAmounts();

    /**
     * Tests if a Fee can override the transaction code in this
     * catalog.
     *
     * @return true if the transaction code can be changed, false
     *         otherwise
     * @name Can Override Transaction Code
     */
    public Boolean getCanOverrideTransactionCode();

    /**
     * The transaction code to use in the Fees.
     *
     * @return the transaction code
     * @name Transaction Code
     */
    public String getTransactionCode();

    /**
     * Tests if a Fee can override the transaction date type in this
     * catalog.
     *
     * @return true if the transaction date type can be changed, false
     *         otherwise
     * @name Can Override Transaction Date Type
     */
    public Boolean getCanOverrideTransactionDateType();

    /**
     * The transaction date type key to use in the Fees.
     *
     * @return the transaction date type key
     * @name Transaction Date Type Key
     */
    public String getTransactionDateTypeKey();
}
