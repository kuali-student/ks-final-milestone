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
 * This class is a helping for retrieving a value from the GES
 * service.
 *
 * Created by Paul Richardson on 9/12/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.Date;

/**
 * Helper for term resolvers accessing the GES Service
 *
 * @author Kuali Student Team
 */
public abstract class GesTermResolverSupport<T> implements TermResolver<T> {

    private GesService gesService;

    /*
     * Return a value from the GES service based on:
     * -- A GES parameter
     * -- The context info
     * -- The person id
     * -- The atp id
     * -- The effective date
     */
    protected ValueInfo evaluateValueOnDate(String gesParameterKey, ContextInfo contextInfo, String personId, String atpId, Date asOfDate) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        GesCriteriaInfo criteria = new GesCriteriaInfo();
        criteria.setPersonId(personId);
        criteria.setAtpId(atpId);

        return gesService.evaluateValueOnDate(gesParameterKey,
                criteria,
                asOfDate,
                contextInfo);
    }

    protected Integer evaluateIntegerOnDate(String gesParameterKey, ContextInfo contextInfo, String personId, String atpId, Date asOfDate) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        ValueInfo value = evaluateValueOnDate(gesParameterKey, contextInfo, personId, atpId, asOfDate);

        Integer integerValue;

        if (value != null) {
            integerValue = value.getDecimalValue().intValue();
        } else {
            integerValue = null;
        }

        return integerValue;
    }

    protected Float evaluateFloatOnDate(String gesParameterKey, ContextInfo contextInfo, String personId, String atpId, Date asOfDate) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        ValueInfo value = evaluateValueOnDate(gesParameterKey, contextInfo, personId, atpId, asOfDate);

        Float floatValue;

        if (value != null) {
            floatValue = value.getDecimalValue().floatValue();
        } else {
            floatValue = null;
        }

        return floatValue;
    }

    public void setGesService(GesService gesService) {
        this.gesService = gesService;
    }
}
