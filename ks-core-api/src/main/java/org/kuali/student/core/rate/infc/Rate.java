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
 * The Rate is drawn from the CatalogRate and applied to something
 * (ref object) with a charge, like a CourseOffering. The CatalogRate
 * constrains the Rate.
 *
 * The Rate has one of three flavors indicated by its Type:
 *
 * <dl> <dt>Flat</dt> <dd>A rate that doesn't vary. The amount is
 *                    constrained by the minimum and maximum amount
 *                    range in the Rate Catalog.</dd>
 *  
 *    <dt>Fixed Credit</dt> <dd>A rate per credit where the total
 *                          amount is the rate multiplied by the
 *                          credits (or units) determined by what this
 *                          Rate applies to. The amount is constrained
 *                          by the minimum and maximum amount range in
 *                          the Rate Catalog.</dd>
 * 
 *    <dt>Flexible Credit</dt> <dd>A specific rate for each credit
 *                             value. The list of flexible credit
 *                             amounts is constrained by the list of
 *                             acceptable credits amounts in the Rate
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
     * The URI of the reference object to which this Rate applies.
     *
     * @return the URI
     * @name Reference Object URI
     */
    public String getRefObjectURI();

    /**
     * The identifier of the reference objects to which this Rate
     * applies. There may be multiple references, but all of the same
     * type as indicated by the reference object URI.
     *
     * @return the Ids of the reference objects
     * @name Reference Object Ids
     */
    public List<String> getRefObjectIds();

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
     * The amount for a flat or fixed credit rate. This amount should
     * be constrained by the minimum and maxmimum range in the Rate
     * Catalog for flat and fixed credit Rates.
     * 
     * @return the amount
     * @name Amount
     */
    public CurrencyAmount getAmount();

    /**
     * The list of flexible credit amounts. This list should be
     * constrained by the list of flexible credit amounts in the Rate
     * Catalog.
     * 
     * @return the list of flexible credit amounts
     * @name Flexible Credit Amounts
     */
    public List<? extends FlexibleCreditAmount> getFlexibleCreditAmounts();

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
     * The transaction date used for rate processing.
     *
     * @return the transaction date
     * @name Transaction Date
     */
    public Date getTransactionDate();

    /**
     * The transaction date type key. The transaction date type can
     * differ from the default type in the Rate Catalog of
     * CatlogRate.canOverrideTransactionDateTypeKey is true.
     *
     * @return the transaction date type key
     * @name Transaction Date Type Key
     */
    public String getTransactionDateTypeKey();
}
