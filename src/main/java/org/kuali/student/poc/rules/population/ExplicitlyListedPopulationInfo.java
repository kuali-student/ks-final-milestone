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
 * Created by mahtabme on 12/13/13
 */
package org.kuali.student.poc.rules.population;

import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.infc.Population;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Population that supports the getting of an
 * explicit list of the members in this population.
 *
 * @author Mezba Mahtab
 */
public class ExplicitlyListedPopulationInfo extends PopulationInfo implements Population, Serializable {

    ////////////////////
    // Data Variable
    ////////////////////

    private List<String> personIds;

    ///////////////////
    // Constructor
    ////////////////////

    public ExplicitlyListedPopulationInfo() {
        super ();
        this.personIds = new ArrayList<String>();
    }


    public ExplicitlyListedPopulationInfo(List<String> personIds) {
        super ();
        this.personIds = personIds;
    }

    public ExplicitlyListedPopulationInfo(Population population) {
        super ();
        this.personIds = new ArrayList<String>();
    }

    public ExplicitlyListedPopulationInfo(Population population, List<String> personIds) {
        super(population);
        this.personIds = personIds;
    }

    ///////////////////
    // Functionals
    ////////////////////

    public Boolean getSupportsGetMembers() { return true; }

    /**
     * Returns a list of person Ids in this population.
     */
    public List<String> getPersonIds() {
        return personIds;
    }

    /**
     * Sets a list of person Ids in this population. The older is list is replaced with this list.
     * @param personIds
     */
    public void setPersonIds(List<String> personIds) {
        this.personIds = personIds;
    }

    /**
     * Adds a person id to the end of the list of person ids of this population.
     */
    public void addPersonIdToList (String personId) {
        this.personIds.add(personId);
    }

    /**
     * Removes a person id from the list of person ids of this population.
     * @throws DoesNotExistException personId is not in the list
     */
    public void removePersonIdToList (String personId) throws DoesNotExistException {
        if (this.personIds.contains(personId)) {
            this.personIds.remove(personId);
        } else {
            throw new DoesNotExistException("Person id " + personId + " not found in the list!");
        }
    }
}
