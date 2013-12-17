/**
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
 *
 * Created by mahtabme on 12/17/13
 */
package org.kuali.student.poc.rules.population;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.infc.PopulationRule;

import java.io.Serializable;

/**
 * This class represents a PopulationRule that is an
 * explicit list of person ids.
 *
 * @author Mezba Mahtab
 */
public class ExplicitlyListedPersonsPopulationRuleInfo
        extends PopulationRuleInfo
        implements PopulationRule, Serializable {

    //////////////////////////////
    // Constructors
    //////////////////////////////

    /**
     * Constructs a new ExplicitlyListedPersonsPopulationRuleInfo.
     */
    public ExplicitlyListedPersonsPopulationRuleInfo () {
        super ();
    }

    /**
     * Constructs a new ExplicitlyListedPersonsPopulationRuleInfo
     * from another PopulationRule.
     *
     * @param populationRule the PopulationRule to copy
     */
    public ExplicitlyListedPersonsPopulationRuleInfo (PopulationRule populationRule) {
        super (populationRule);
    }

    //////////////////////////////
    // Functionals
    //////////////////////////////

    /**
     * Adds a person id to the end of the list of person ids of this PopulationRule.
     */
    public void addPersonIdToList (String personId) {
        getPersonIds().add(personId);
    }

    /**
     * Adds the person id of an entity that has it to the
     * end of the list of person ids of this PopulationRule.
     */
    public void addPersonIdOfPersonToList (hasPersonId person) {
        addPersonIdToList(person.getPersonId());
    }

    /**
     * Removes a person id from the list of person ids of this PopulationRule.
     * @throws DoesNotExistException personId is not in the list
     */
    public void removePersonIdToList (String personId) throws DoesNotExistException {
        if (getPersonIds().contains(personId)) {
            getPersonIds().remove(personId);
        } else {
            throw new DoesNotExistException("Person id " + personId + " not found in the list!");
        }
    }

    /**
     * Removes the person id of an entity with a person id from the list of person ids of this PopulationRule.
     * @throws DoesNotExistException personId is not in the list
     */
    public void removePersonIdOfPersonToList (hasPersonId person) throws DoesNotExistException {
        removePersonIdToList(person.getPersonId());
    }
}
