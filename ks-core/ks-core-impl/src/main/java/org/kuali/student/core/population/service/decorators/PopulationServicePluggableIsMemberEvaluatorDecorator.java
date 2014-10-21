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
package org.kuali.student.core.population.service.decorators;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import javax.jws.WebParam;
import java.util.Date;
import org.kuali.student.core.population.service.decorators.PopulationServiceDecorator;

/**
 * This class represents a PopulationService decorator which can apply a specific
 * IsMemberAsOfDateEvaluator when evaluation person ids and population. This is written
 * so that one can bypass the current implementation of isMemberxxx methods and write
 * their own evaluation policy, and plug that in.
 *
 * @author Mezba Mahtab
 */
public class PopulationServicePluggableIsMemberEvaluatorDecorator extends PopulationServiceDecorator {

    ////////////////////////
    // Data Variable
    ////////////////////////

    private IsMemberAsOfDateEvaluator isMemberAsOfDateEvaluator;

    /////////////////////////////
    // Getters and Setters
    /////////////////////////////

    public IsMemberAsOfDateEvaluator getMemberAsOfDateEvaluator() {
        return isMemberAsOfDateEvaluator;
    }

    public void setMemberAsOfDateEvaluator(IsMemberAsOfDateEvaluator memberAsOfDateEvaluator) {
        isMemberAsOfDateEvaluator = memberAsOfDateEvaluator;
    }

    /////////////////////////////
    // Functionals
    /////////////////////////////

    @Override
    public Boolean isMemberAsOfDate(@WebParam(name = "personId") String personId,
                                    @WebParam(name = "populationId") String populationId,
                                    @WebParam(name = "date") Date date,
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
                InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException {
        return isMemberAsOfDateEvaluator.isMemberAsOfDate(personId, populationId, date, contextInfo);
    }


}
