/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational
 * Community License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.population.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about a Population.
 *
 * @author tom
 * @since Thu Nov 21 14:22:34 EDT 2011
 */ 

public interface Population extends IdEntity {

    /**
     * Gets the valid sort order keys that can be used to sort the
     * members of the Population. The valid sort order keys correspond
     * to the underlying rule sorting capabilities and are surfaced
     * here to assist with service operations.
     *
     * @name Sort Order Type Keys
     * @readonly
     */
    public List<String> getSortOrderTypeKeys();

    /**
     * Tests to see if the Population can vary by time. Some
     * underlying rules may provide the caopability for a Population
     * to vary by time where membership may be queried for a specific
     * time in the past or future.
     *
     * @name Varies By Time
     * @readonly
     */
    public Boolean getVariesByTime();

    /**
     * Gets a list of Populations contained within this
     * Population. This is an informational field derived from the
     * corresponding PopulationRule.
     *
     * @name Sub Population Ids
     * @readonly
     */
    public List<String> getSubPopulationIds();
}
