/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.population.service.decorators;


import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import java.util.List;


public class PopulationServiceValidationDecorator extends PopulationServiceDecorator
{
    // validator property w/getter & setter
    private DataDictionaryValidator validator;
    public DataDictionaryValidator getValidator() {
        return validator;
    }
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(String validationTypeKey, PopulationInfo populationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, populationInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validatePopulation(validationTypeKey, populationInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public PopulationInfo createPopulation(PopulationInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), populationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createPopulation(populationInfo, contextInfo);
    }

    @Override
    public PopulationInfo updatePopulation(String populationId, PopulationInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), /* populationId, */ populationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updatePopulation(populationId, populationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(String validationTypeKey, PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, populationRuleInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validatePopulationRule(validationTypeKey, populationRuleInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public PopulationRuleInfo createPopulationRule(PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationRule(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), populationRuleInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createPopulationRule(populationRuleInfo, contextInfo);
    }

    @Override
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationRule(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), /* populationRuleId, */ populationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updatePopulationRule(populationRuleId, populationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePopulationCategory(String validationTypeKey, String populationCategoryTypeKey, PopulationCategoryInfo populationCategoryInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, populationCategoryInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validatePopulationCategory(validationTypeKey, populationCategoryTypeKey, populationCategoryInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public PopulationCategoryInfo createPopulationCategory(String populationCategoryTypeKey, PopulationCategoryInfo populationCategoryInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), populationCategoryTypeKey, populationCategoryInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createPopulationCategory(populationCategoryTypeKey, populationCategoryInfo, contextInfo);
    }

    @Override
    public PopulationCategoryInfo updatePopulationCategory(String populationCategoryId, PopulationCategoryInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), populationCategoryId, populationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updatePopulationCategory(populationCategoryId, populationInfo, contextInfo);
    }
}

