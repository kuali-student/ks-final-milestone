/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.kuali.student.core.constants;

import org.kuali.student.core.ges.service.GesServiceNamespace;

/**
 * This class holds the constants used by the Scheduling service
 *
 * @Version 1.0
 */
public class GesServiceConstants
        extends GesServiceNamespace {

    //Value Types
    public static final String GES_VALUE_TYPE_KEY_BOOLEAN = "kuali.ges.value.type.boolean";
    public static final String GES_VALUE_TYPE_KEY_DATE = "kuali.ges.value.type.date";
    public static final String GES_VALUE_TYPE_KEY_LONG = "kuali.ges.value.type.long";
    public static final String GES_VALUE_TYPE_KEY_KUALIDECIMAL = "kuali.ges.value.type.kualidecimal";
    public static final String GES_VALUE_TYPE_KEY_STRING = "kuali.ges.value.type.string";
    public static final String GES_VALUE_TYPE_KEY_AMOUNT = "kuali.ges.value.type.amount";
    public static final String GES_VALUE_TYPE_KEY_CURRENCYAMOUNT = "kuali.ges.value.type.currencyamount";
    public static final String GES_VALUE_TYPE_KEY_TIMEAMOUNT = "kuali.ges.value.type.timeamount";
    public static final String GES_VALUE_TYPE_KEY_TIMEOFDAY = "kuali.ges.value.type.timeofday";
    //Parameter Types
    public static final String GES_PARAMETER_TYPE_KEY = "kuali.ges.parameter.type";
    //Value State
    public static final String GES_VALUE_ACTIVE_STATE_KEY = "kuali.ges.value.state.active";
    public static final String GES_VALUE_INACTIVE_STATE_KEY = "kuali.ges.value.state.inactive";
    //Parameter State
    public static final String GES_PARAMETER_ACTIVE_STATE_KEY = "kuali.ges.parameter.state.active";
    public static final String GES_PARAMETER_INACTIVE_STATE_KEY = "kuali.ges.parameter.state.inactive";
    /**
     * known parameters
     */
    public static final String PARAMETER_KEY_CREDIT_MINIMUM = "kuali.ges.credit.minimum";
    public static final String PARAMETER_KEY_CREDIT_LIMIT = "kuali.ges.credit.limit";
    public static final String PARAMETER_KEY_LOAD_CALCULATION_FOR_CREDIT_CHECKS = "kuali.ges.load.calculation.for.credit.checks";
}
