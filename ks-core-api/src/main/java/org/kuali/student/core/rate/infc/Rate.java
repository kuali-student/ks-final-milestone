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

import java.util.Date;
import java.util.List;


/**
 * The Rate is drawn from the CatalogRate. The CatalogRate constrains
 * the Rate.
 *
 * The Rate has one of three flavors indicated by its Type:
 *
 * <dl> <dt>Flat</dt> <dd>A rate that doesn't vary. The amount is
 *                    constrained by the minimum and maximum amount
 *                    range in the Rate Catalog.</dd>
 *  
 *    <dt>Fixed Unit</dt> <dd>A rate per unit where the total amount
 *                          is the rate multiplied by the units
 *                          determined by what this Rate applies
 *                          to. The amount is constrained by the
 *                          minimum and maximum amount range in the
 *                          Rate Catalog.</dd>
 * 
 *    <dt>Flexible Unit</dt> <dd>A specific rate for each unit
 *                             value. The list of flexible unit
 *                             amounts is constrained by the list of
 *                             acceptable units amounts in the Rate
 *                             Catalog.</dd> </dl>
 *
 * @author Kuali Student Services
 */

public interface Rate
    extends IdEntity {

    /**
     * The CatalogRate identifier to which this Rate belongs.
     * 
     * @return the catalog rate Id
     * @name Catalog Rate Id
     * @required
     * @readOnly
     */
    public String getCatalogRateId();

    /**
     * The ATP for which this Rate is in effect. The ATP should be
     * constrained by the list of applicable ATP Ids in the Rate
     * Catalog.
     *
     * In the case of a Course Offering, this ATP is the same as (or a
     * parent of) the Course Offering ATP.
     * 
     * @return the ATP Id
     * @name ATP Id
     */
    public String getAtpId();

    /**
     * The amount for a flat or fixed unit rate. This amount should
     * be constrained by the minimum and maxmimum range in the Rate
     * Catalog for flat and fixed unit Rates.
     * 
     * @return the amount
     * @name Amount
     */
    public CurrencyAmount getAmount();

    /**
     * The list of flexible unit amounts. This list should be
     * constrained by the list of flexible unit amounts in the Rate
     * Catalog.
     * 
     * @return the list of flexible unit amounts
     * @name Flexible Unit Amounts
     */
    public List<? extends FlexibleUnitAmount> getFlexibleUnitAmounts();

    /**
     * The transaction code. The transaction code can differ from the
     * default type in the Rate Catalog of
     * CatlogRate.canOverrideTransactionCode is true.
     *
     * @return the transaction code
     * @name Transaction Code
     */
    public String getTransactionCode();

    /**
     * The transaction date type key. The transaction date type can
     * differ from the default type in the Rate Catalog of
     * CatlogRate.canOverrideTransactionDateTypeKey is true.
     *
     * @return the transaction date type key
     * @name Transaction Date Type Key
     */
    public String getTransactionDateTypeKey();

    /**
     * The transaction date used for rate processing.
     *
     * @return the transaction date
     * @name Transaction Date
     */
    public Date getTransactionDate();

    /**
     * The recognition date used for rate processing.
     *
     * @return the recognition date
     * @name Recognition Date
     */
    public Date getRecognitionDate();

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
     * Gets the amount for the limit when the number of units false
     * in between the minimum and maximum limit range inclusive.
     *
     * @return the limit amount
     * @name Limit Amount
     */
    public CurrencyAmount getLimitAmount();
}
