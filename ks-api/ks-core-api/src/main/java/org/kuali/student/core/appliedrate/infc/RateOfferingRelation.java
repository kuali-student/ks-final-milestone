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

package org.kuali.student.core.appliedrate.infc;

import org.kuali.student.r2.common.infc.Relationship;

import java.util.List;

/**
 * This relationship applies a Rate to a Course Offering. The
 * Relationship is defined between the FormatOffering and the
 * Rate but may be qualified to a specific set of
 * ActivityOfferings within the FormatOffering.
 *
 * @author Kuali Student Services
 */

public interface RateOfferingRelation
    extends Relationship {

    /**
     * The Id for the rate.
     * 
     * @return the Rate Id
     * @name Rate Id
     * @required
     * @readOnly
     */
    public String getRateId();

    /**
     * The Id for the FormatOffering.
     * 
     * @return the FormatOffering Id
     * @name FormatOffering Id
     * @required
     * @readOnly
     */
    public String getFormatOfferingId();

    /**
     * The list of ActivityOfferings within the FormatOffering to
     * which this Rate applies. This is an optional qualifier
     * where if this list is empty, the Rate applies to all
     * Activities in the FormatOffering.
     * 
     * @return a list of ActivityOffering Ids
     * @name ActivityOffering Ids
     */
    public List<String> getActivityOfferingIds();
}
