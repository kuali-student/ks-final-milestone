/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

public interface TimeAmount {

    /**
     * The kind of units associated with the time quantity.
     * 
     * For example months, days, week, semesters, years, minutes, seconds
     *
     * @name Academic Time Period Duration Type Key
     */
    public String getAtpDurationTypeKey();

    /**
     * The amount of time.
     * 
     * The general design of this is that it should normally contain integer quantity 
     * and not hold fractions or decimals.  Instead the Duration Type should changed 
     * to a shorter duration.
     * 
     * However this is a string to allow some implementing schools, if they wish, 
     * to capture this information in fractional units which may be more natural.
     *
     * @impl the data storage for the reference implementation is an integer
     * @name Time Quantity
     */
    public String getTimeQuantity();
}

