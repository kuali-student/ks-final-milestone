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

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.CurrencyAmount;

import java.util.List;

/**
 * The Catalog Rate is a list of "canonical" rates in a "catalog" that
 * can be used in establishing an actual Rate.
 *
 * The Catalog Rate has one of three flavors indicated by its Type:
 *
 * <dl>

 *    <dt>Flat</dt> <dd>A rate that doesn't vary. The Catalog Rate
 *                  specifies a minimum and maxmimum amount to
 *                  constrain the Rate.</dd>
 *  
 *    <dt>Fixed Unit</dt> <dd>A rate per unit where the total amount
 *                          is the rate multiplied by the units
 *                          determined by what this Rate applies
 *                          to. The Catalog Rate specifies a minimum
 *                          and axmimum amount for the rate per
 *                          unit.</dd>
 * 
 *    <dt>Flexible Unit</dt> <dd>A specific rate for each unit
 *                             value. The Catalog Rate specifies the
 *                             list of acceptable valued (amount and
 *                             number of units allowed in a
 *                             Rate.</dd> </dl> 1. Flat - A rate that
 *                             doesn't vary. The Catalog Rate
 *                             specifies a minimum and maxmimum amount
 *                             to constrain the Rate.</dd> </dl>
 *
 * The Rate Catalog is effective during its applicable ATPs.
 *
 * @author Kuali Student Services
 */

public interface CatalogRate
    extends IdEntity {

    /**
     * The code for the catalog rate.
     * 
     * @return the catalog rate code
     * @name Code
     */
    public String getCode();

    /**
     * The list of ATPs in which this catalog rate can be applied. A
     * Course Offering in an ATP not in this list cannot have a rate
     * derived from this catalog rate.
     * 
     * @return a list of applicable ATP Ids
     * @name Applicable ATP Ids
     */
    public List<String> getApplicableAtpIds();

    /**
     * The minimum amount for a flat or fixed unit Rate. This field
     * is not applicable for flexible unit rates.
     * 
     * @return the minimum amount
     * @name Minimum Amount
     */
    public CurrencyAmount getMinimumAmount();

    /**
     * The maximum amount for a flat or fixed unit Rate. This field
     * is not applicable for flexible unit rates.
     * 
     * @return the maximum amount
     * @name Maximum Amount
     */
    public CurrencyAmount getMaximumAmount();

    /**
     * The allowed list of flexible unit amounts in flexible unit
     * Rates.
     *
     * @return a list of flexible unit amounts
     * @name Flexible unit Amounts
     */
    public List<? extends FlexibleUnitAmount> getFlexibleUnitAmounts();

    /**
     * Tests if a Rate can override the transaction code in this
     * catalog.
     *
     * @return true if the transaction code cannot be changed, false
     *         if the Rate can override this code
     * @name Is Transaction Code Final
     */
    public Boolean getIsTransactionCodeFinal();

    /**
     * The transaction code to use in the Rates.
     *
     * @return the transaction code
     * @name Transaction Code
     */
    public String getTransactionCode();

    /**
     * Tests if a Rate can override the transaction date type in this
     * catalog.
     *
     * @return true if the transaction date type cannot be changed,
     *         false if the Rate can override this type
     * @name Is Transaction Date Final
     */
    public Boolean getIsTransactionDateTypeFinal();

    /**
     * The transaction date type key to use in the Rates.
     *
     * @return the transaction date type key
     * @name Transaction Date Type Key
     */
    public String getTransactionDateTypeKey();

    /**
     * Tests if a Recognition Date may be specified in a Rate.
     *
     * @return true if a recognition date can be specified in a Rate,
     *         false if a recognition date cannot be specified in a
     *         Rate
     * @name Is Recognition Date Definable
     */
    public Boolean getIsRecognitionDateDefinable();

    /**
     * Tests if a Rate can override the limit rate parameters in this
     * catalog.
     *
     * @return true if the limit data cannot be changed,
     *         false if the Rate can override this limit data
     * @name Is Limit Rate Final
     */
    public Boolean getIsLimitRateFinal();

    /**
     * Tests if this is a "limit" rate. A limit rate has a minimum and
     * maximum limit unit range at which the rate is the limitAmount.
     *
     * @return true if this is a limit rate, false otherwise
     * @name Is Limit Rate
     */
    public Boolean getIsLimitRate();

    /**
     * Gets the minimum, or low end of the unit range, for a limit
     * rate. This field is only applicable if isLimitRate() is true.
     *
     * @return the low end of the limit units range
     * @name Minimum Limit Units
     */
    public Integer getMinimumLimitUnits();

    /**
     * Gets the maximum, or high end of the unit range, for a limit
     * rate. This field is only applicable if isLimitRate() is true.
     *
     * @return the low end of the limit units range
     * @name Maximum Limit Units
     */
    public Integer getMaximumLimitUnits();

    /**
     * Gets the minimum amount for the limit. This sets the lower
     * bound for the Limit Amount in the Rate. This field is only
     * applicable if isLimitRate() is true.
     *
     * @return the minimum limit amount
     * @name Minimum Limit Amount
     */
    public CurrencyAmount getMinimumLimitAmount();

    /**
     * Gets the maximum amount for the limit. This sets the upper
     * bound for the Limit Amount in the Rate. This field is only
     * applicable if isLimitRate() is true.
     *
     * @return the maximum limit amount
     * @name Maximum Limit Amount
     */
    public CurrencyAmount getMaximumLimitAmount();
}
