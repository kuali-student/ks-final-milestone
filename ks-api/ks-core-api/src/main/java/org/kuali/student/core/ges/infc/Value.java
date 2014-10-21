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
import java.util.List;

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
     * @name Parameter Key
     * @readOnly
     * @required
     */
    public String getParameterKey();

    /**
     *  An Integer that sets the priority of this value relative to other values associated with
     *  a specific parameter.
     * @name Priority
     */
    public Integer getPriority();

    /**
     * An optional id for an ATP if the value is restricted for a specific term.
     */
    public String getAtpId();

    /**
     *  An ATP type key that restricts the applicability of this value.
     * @name ATP Type Key
     */
    public String getAtpTypeKey();

    /**
     * An optional Rule Id that restricts the applicability of this value.
     * @name Rule Id
     */
    public String getRuleId();

    /**
     * An optional Organization Id that restricts the applicability of this value.
     * @name Org Id
     */
    public String getOrgId();

    //////////////////////////////////
    // Person Specific Quantifiers
    //////////////////////////////////

    /**
     *  An optional Population Id that restricts the applicability of this value.
     * @name Population Id
     */
    public String getPopulationId();

    //////////////////////////////////
    // Course Specific Quantifiers
    //////////////////////////////////

    /**
     * An optional CLU (Canonical Learning Unit) Id that restricts the applicability of this value.
     * @name CLU ID
     */
    public String getCluId();

    /**
     * An optional SOC (Set of Courses) Id that restricts the applicability of this value.
     * @name SOC ID
     */
    public String getSocId();

    /**
     * An optional Subject Code that restricts the applicability of this value.
     * @name Subject Code
     */
    public String getSubjectCode();

    //////////////////////////////////
    // Value Variables
    //////////////////////////////////

    /**
     * The Boolean value contained within this entity
     * @name Boolean Value
     */
    public Boolean getBooleanValue();

    /**
     * The Date value contained within this entity
     * @name Date Value
     */
    public Date getDateValue();

    /**
     * The Numeric value contained within this entity.
     * @name Numeric Value
     */
    public Long getNumericValue();

    /**
     * The KualiDecimal value contained within this entity
     * @name Decimal Value
     */
    public KualiDecimal getDecimalValue();

    /**
     * The String value contained within this entity
     * @name String Value
     */
    public String getStringValue();

    /**
     * The Amount value contained within this entity
     * @name Amount Value
     */
    public Amount getAmountValue();

    /**
     * The CurrencyAmount value contained within this entity
     * @name Currency Amount Value
     */
    public CurrencyAmount getCurrencyAmountValue();

    /**
     * The TimeAmount value contained within this entity
     * @name Time Amount Value
     */
    public TimeAmount getTimeAmountValue();

    /**
     * The TimeOfDay value contained within this entity
     * @name Time of Day Value
     */
    public TimeOfDay getTimeOfDayValue();

    /**
     * The custom value contained within this entity
     * @name Custom Value
     */
    public GesCustomValue getCustomValue();
}
