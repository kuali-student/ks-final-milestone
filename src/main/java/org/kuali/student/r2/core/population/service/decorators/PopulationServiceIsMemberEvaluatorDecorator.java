/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 1/17/14
 */
package org.kuali.student.r2.core.population.service.decorators;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.decorators.PopulationServiceDecorator;

import java.util.Date;
import java.util.List;

/**
 * This class is a decorator that implements the isMember in a particular manner. To have one's
 * own custom implementation, one can use PopulationServicePluggableIsMemberEvaluatorDecorator.
 *
 * @author Mezba Mahtab
 */
public class PopulationServiceIsMemberEvaluatorDecorator extends PopulationServiceDecorator {

    @Override
    public Boolean isMemberAsOfDate(String personId, String populationId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationRuleInfo populationRuleInfo = getPopulationRuleForPopulation(populationId, contextInfo);
        if (populationRuleInfo==null) throw new OperationFailedException("Population rule for population id " + populationId + " is null");

        // list of person ids
        if (PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY.equals(populationRuleInfo.getTypeKey())) {
            if (populationRuleInfo.getPersonIds().contains(personId)) return Boolean.TRUE; // found a match
            else return Boolean.FALSE;
        }
        // everyone
        if (PopulationServiceConstants.POPULATION_RULE_TYPE_EVERYONE_KEY.equals(populationRuleInfo.getTypeKey())) {
            return Boolean.TRUE;
        }

        // an intersection
        else if (PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY.equals(populationRuleInfo.getTypeKey())) {
            for (String childPopulationId : populationRuleInfo.getChildPopulationIds()) {
                if (!isMemberAsOfDate(personId, childPopulationId, date, contextInfo)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }

        // an union
        else if (PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY.equals(populationRuleInfo.getTypeKey())) {
            for (String childPopulationId : populationRuleInfo.getChildPopulationIds()) {
                if (isMemberAsOfDate(personId, childPopulationId, date, contextInfo)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        // an exclusion
        else if (PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY.equals(populationRuleInfo.getTypeKey())) {
            // the person has to be in the reference population
            if (!isMemberAsOfDate(personId, populationRuleInfo.getReferencePopulationId(), date, contextInfo)) {
                return Boolean.FALSE;
            }

            // the person cannot be in the child population
            for (String childPopulationId : populationRuleInfo.getChildPopulationIds()) {
                if (isMemberAsOfDate(personId, childPopulationId, date, contextInfo)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }

        // unimplemented type
        else {
            throw new OperationFailedException ("population rule type " + populationRuleInfo.getTypeKey() +  " has not been implemented");
        }
    }

    @Override
    public List<String> getMembersAsOfDate(String populationId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationInfo populationInfo = getPopulation(populationId, contextInfo);
        if (populationInfo.getSupportsGetMembers()) {
            return (getPopulationRuleForPopulation(populationId, contextInfo)).getPersonIds();
        } else {
            throw new OperationFailedException("This population does not support getMembers");
        }
    }
}
