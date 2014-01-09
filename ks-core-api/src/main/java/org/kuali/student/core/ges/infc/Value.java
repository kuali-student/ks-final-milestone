/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.ges.infc;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.common.infc.TimeOfDay;

import java.util.Date;

/**
 * A value associated with a specific parameter.
 *
 * The applicability of this value is restricted by specifying an ATP type key, Population Id, Rule Id, and onDate
 * This allows multiple values to be tied to a single parameter and a subset of these values to be tied to a combination of the limiting criteria given above.
 *
 * A value may store a single type of value that is determined using the values typeKey.  Following from this, for example,
 * it is not valid to attempt to set a boolean and date on a single value.
 *
 * Values are assigned a priority that may or may not be unique for all values associated with a given parameter and the highest priority is one.
 * The uniqueness of the priority is determined by the associated parameter.
 *
 */
public interface Value extends IdNamelessEntity, HasEffectiveDates {

    /**
     * The parameter associated with this value.
     * @name Parameter Id
     * @readOnly
     * @required
     */
    String getParameterId();

    /**
     *  An Integer that sets the priority of this value relative to other values associated with
     *  a specific parameter.
     * @name Priority
     */
    Integer getPriority();

    /**
     *  An optional ATP type key that restricts the applicability of this value.
     * @name ATP Type Key
     */
    String getAtpTypeKey();

    /**
     *  An optional Population Id that restricts the applicability of this value.
     * @name Population Id
     */
    String getPopulationId();

    /**
     * An optional Rule Id that restricts the applicability of this value.
     * @name Rule Id
     */
    String getRuleId();


    /**
     * The Boolean value contained within this entity
     * @name Boolean Value
     */
    Boolean getBooleanValue();

    /**
     * The Date value contained within this entity
     * @name Date Value
     */
    Date getDateValue();

    /**
     * The Numeric value contained within this entity.
     * @name Numeric Value
     */
    Long getNumericValue();

    /**
     * The KualiDecimal value contained within this entity
     * @name Decimal Value
     */
    KualiDecimal getDecimalValue();

    /**
     * The String value contained within this entity
     * @name String Value
     */
    String getStringValue();

    /**
     * The Amount value contained within this entity
     * @name Amount Value
     */
    Amount getAmountValue();

    /**
     * The CurrencyAmount value contained within this entity
     * @name Currency Amount Value
     */
    CurrencyAmount getCurrencyAmountValue();

    /**
     * The TimeAmount value contained within this entity
     * @name Time Amount Value
     */
    TimeAmount getTimeAmountValue();

    /**
     * The TimeOfDay value contained within this entity
     * @name Time of Day Value
     */
    TimeOfDay getTimeOfDayValue();
}
