/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.ges.service;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;

import java.util.Date;
import java.util.List;

/**
 * The base decorator for the {@link org.kuali.student.core.ges.service.GesService}- Other sub
 * classes of this decorator only have to override the methods to which we want
 * to add additional functionality
 *
 */
public class GesServiceDecorator implements GesService {

    private GesService nextDecorator;

    public GesService getNextDecorator() throws OperationFailedException  {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(GesService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public ParameterInfo getParameter(String parameterKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getParameter(parameterKey,contextInfo);
    }

    @Override
    public List<ParameterInfo> getParametersByKeys(List<String> parameterKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getParametersByKeys(parameterKeys,contextInfo);
    }

    @Override
    public List<String> getParameterKeysByType(String parameterTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getParameterKeysByType(parameterTypeKey,contextInfo);
    }

    @Override
    public List<String> searchForParameterKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForParameterKeys(criteria,contextInfo);
    }

    @Override
    public List<ParameterInfo> searchForParameters(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForParameters(criteria,contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateParameter(String validationTypeKey, String valueTypeKey, String parameterTypeKey,ParameterInfo parameterInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateParameter(validationTypeKey,valueTypeKey,parameterTypeKey,parameterInfo,contextInfo);
    }

    @Override
    public ParameterInfo createParameter(String valueTypeKey, String parameterKey, String parameterTypeKey, ParameterInfo parameterInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createParameter(valueTypeKey,parameterKey,parameterTypeKey,parameterInfo,contextInfo);
    }

    @Override
    public ParameterInfo updateParameter(String parameterKey, ParameterInfo parameterInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateParameter(parameterKey,parameterInfo,contextInfo);
    }

    @Override
    public StatusInfo deleteParameter(String parameterKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteParameter(parameterKey,contextInfo);
    }

    @Override
    public ValueInfo getValue(String valueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getValue(valueId,contextInfo);
    }

    @Override
    public List<ValueInfo> getValuesByIds(List<String> valueIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getValuesByIds(valueIds,contextInfo);
    }

    @Override
    public List<String> getValueIdsByType(String valueTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getValueIdsByType(valueTypeKey,contextInfo);
    }

    @Override
    public List<String> searchForValueIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForValueIds(criteria,contextInfo);
    }

    @Override
    public List<ValueInfo> searchForValues(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForValues(criteria,contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateValue(String validationTypeKey,String valueTypeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateValue(validationTypeKey,valueTypeKey,parameterKey,valueInfo,contextInfo);
    }

    @Override
    public ValueInfo createValue(String valueTypeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createValue(valueTypeKey,parameterKey,valueInfo,contextInfo);
    }

    @Override
    public ValueInfo updateValue(String valueId, ValueInfo valueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateValue(valueId,valueInfo,contextInfo);
    }

    @Override
    public StatusInfo deleteValue(String valueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteValue(valueId,contextInfo);
    }

    @Override
    public List<ValueInfo> getValuesByParameter(String parameterKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getValuesByParameter(parameterKey, contextInfo);
    }

    @Override
    public List<ValueInfo> evaluateValues(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().evaluateValues(parameterKey,criteria, contextInfo);
    }

    @Override
    public List<ValueInfo> evaluateValuesOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().evaluateValuesOnDate(parameterKey, criteria, onDate, contextInfo);
    }
    @Override
    public ValueInfo evaluateValue(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().evaluateValue(parameterKey,criteria, contextInfo);
    }

    @Override
    public ValueInfo evaluateValueOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().evaluateValueOnDate(parameterKey, criteria, onDate, contextInfo);
    }

}
