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

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;


/**
 * Information about a Population.
 *
 * @author tom
 * @since Thu Nov 21 14:22:34 EDT 2011
 */ 

public interface Population 
    extends IdEntity {

    /**
     * Gets the valid sort order keys that can be used to sort the
     * members of the Population.
     * 
     * The valid sort order keys correspond
     * to the underlying rule sorting capabilities.
     * 
     * This may return an empty list indicating the population does
     * not support any particular ordering.
     *
     * @name Sort Order Type Keys
     * @readOnly calculation surfacing the corresponding field from the underlying rule
     */
    public List<String> getSortOrderTypeKeys();

    /**
     * Tests to see if the Population may vary by time.
     * 
     * If true then the response to isMemberAtXXXX getMembersAtXXXX
     * methods should be used to assess membership because it is
     * highly likely the population would return a different result
     * depending on the time parameter that is supplied.
     * 
     * An example of populations that vary by time include freshman,
     * sophomore, junior, senior, etc... because the answer varies
     * greatly depending on the term in question.
     * 
     * Some examples of populations that are not expected to vary with
     * time are males or students with IDs ending in an odd number or
     * US citizens.
     * 
     * Note: Saying that a population does not vary with time does not
     * mean that the population does not change over time.  Rather it
     * means that calls isMember and isMemberAtXXX methods or
     * getMembers and getMembersAtXXX should normally return the same
     * answer if invoked simultaneously.
     * 
     * @name Varies By Time
     * @readOnly calculation surfacing the corresponding field from the underlying rule
     */
    public Boolean getVariesByTime();
    
    /**
     * Tests to see if this Population supports the getting of an
     * explicit list of the members in this population.
     * 
     * Not all populations need to support this method and only
     * support the isMember method which tests.
     * 
     * If false then calls to the getMembersXXX family for this
     * population should throw an OperationFailedException exception.
     *
     * @name Supports Get Members
     * @readOnly calculation surfacing the corresponding field from the underlying rule
     */
    public Boolean getSupportsGetMembers();
    
}
