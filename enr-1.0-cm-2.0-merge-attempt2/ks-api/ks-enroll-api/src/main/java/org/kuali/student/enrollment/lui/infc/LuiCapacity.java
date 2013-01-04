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

package org.kuali.student.enrollment.lui.infc;

import java.util.List;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.HasEffectiveDates;

/**
 * Detailed information about a single LUI.
 */
public interface LuiCapacity 
    extends IdEntity, HasEffectiveDates {

    /**
     * A list of LUI identifiers to which this capacity definition
     * applies.
     *
     * @ame Lui Ids
     */
    public List<String> getLuiIds();
   
    /**
     * Maximum number of "seats" that the LUI will hold for
     * registration.
     *
     * @name Maximum Seat Count
     */
    public Integer getMaximumSeatCount();

    /**
     * The order in which this capacity definition should be applied
     * when there are multiple capacity definitions for the primary
     * LUI.
     *
     * @name Processing Order
     */
    public Integer getProcessingOrder();
}

