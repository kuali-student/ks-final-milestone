/**
 * Copyright 2011 The Kuali Foundation 
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
package org.kuali.student.r2.core.population.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.rice.core.api.criteria.QueryByCriteria;

/**
 * Information about a Population Rule to set up a Population. Each
 * "rule" defined in this interface adds to the population.
 *
 * The Population Rule Type determines the "operator" of how these
 * elements are combined.
 *
 * @author tom
 * @since Thu Nov 21 14:22:34 EDT 2011
 */
public interface PopulationRule
        extends IdEntity {

    /**
     * The search criteria to be used in building this
     * population. 
     * 
     * *** NOT IMPLEMENTED *****
     * TODO: Figure out how this can be persisted
     *
     * @name Search Criteria
     * @required when the rule type indicates the population is based on a search criteria
     */
    public QueryByCriteria getSearchCriteria();

    /**
     * Agenda Ids to be used in building this population.
     *
     * @name Agenda Ids
     * @required when the rule type indicates this is based on a KRMS rule
     */
    public List<String> getAgendaIds();

    /**
     * A list of Group Ids to be used in building this population.
     *
     * @name Group Ids
     * @required when the rule type indicates this is based on a group
     */
    public List<String> getGroupIds();

    /**
     * A list of Person Ids to be used in building this population.
     * 
     * Note: this does not hold the list of members unless people can be manually
     * added or removed from the population.
     * 
     * @name Person Ids
     * @required when rule type indicates students can be explicitly added/removed 
     * from the population.
     */
    public List<String> getPersonIds();

    /**
     * A list of Population Ids to be used in building this
     * population. 
     * 
     * The operation is determined by the PopuationRule
     * Type.
     *
     * @name Child Population Ids
     * @required if the rule type indicates this population is created as a union or intersection or minus 
     * other existing populations.
     */
    public List<String> getChildPopulationIds();

    /**
     * The Population Id to be used as the reference population from which 
     * the child populations are removed in the minus operation.
     * 
     * This is used only in the minus operation to help calculate "all others 
     * not caught but the any of the above" use case.
     *
     * @name Reference Population Id
     * @required if the rule type is a Minus type
     */
    public String getReferencePopulationId();

    /**
     * Gets the valid sort order keys that can be used to sort the
     * members of the Population. 
     * 
     * The valid sort order keys correspond
     * to the underlying rule sorting capabilities.
     * 
     * This may return an empty list indicating the population does not support
     * any particular ordering.
     *
     * @name Sort Order Type Keys
     */
    public List<String> getSortOrderTypeKeys();

    /**
     * Tests to see if the Population may vary by time.
     * 
     * If true then the response to isMemberAtXXXX getMembersAtXXXX methods should
     * be used to assess membership because it is highly likely the population 
     * would return a different result depending on the time parameter that is supplied.
     * 
     * An example of populations that vary by time include freshman, sophomore, 
     * junior, senior, etc... because the answer varies greatly depending on the 
     * term in question.  
     * 
     * Some examples of populations that are not expected to vary with time are 
     * males or students with IDs ending in an odd number or US citizens.
     * 
     * Note: Saying that a population does not vary with time does not mean that 
     * the population does not change over time.  Rather it means that 
     * calls isMember and isMemberAtXXX methods or getMembers and getMembersAtXXX 
     * should normally return the same answer if invoked simultaneously.
     * 
     * @name Varies By Time
     */
    public Boolean getVariesByTime();

    /**
     * Tests to see if this Population supports the getting of an explicit list 
     * of the members in this population.
     * 
     * Not all populations need to support this method and only support 
     * the isMember method which tests.
     * 
     * If false then calls to the getMembersXXX family for this population 
     * should throw an OperationFailedException exception.
     *
     * @name Supports Get Members
     */
    public Boolean getSupportsGetMembers();
}
