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
 * Created by mharmath on 1/24/13
 */
package org.kuali.student.r2.core.population.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
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
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

public class PopulationServiceValidatorDecorator
        extends PopulationServiceDecorator
        implements HoldsValidator {

    // validator property w/getter & setter
    private DataDictionaryValidator validator;

    protected TypeService typeService = null;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(String validationTypeKey, PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();
        try {
            errors.addAll(ValidationUtils.validateTypeKey(populationRuleInfo.getTypeKey(), PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE, getTypeService(), contextInfo));
            errors.addAll( ValidationUtils.validateInfo(validator, validationTypeKey, populationRuleInfo, contextInfo));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validatePopulationRule(validationTypeKey,
                    populationRuleInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(String validationTypeId, PopulationInfo populationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();
        try {
            errors.addAll(ValidationUtils.validateTypeKey(populationInfo.getTypeKey(), PopulationServiceConstants.REF_OBJECT_URI_POPULATION, getTypeService(), contextInfo));
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeId, populationInfo, contextInfo));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validatePopulation(validationTypeId,
                    populationInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // validate
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationRule(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            populationInfo, contextInfo);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updatePopulationRule(populationRuleId, populationInfo, contextInfo);
    }

    @Override
    public PopulationInfo updatePopulation(String populationKey, PopulationInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // validate
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            populationInfo, contextInfo);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updatePopulation(populationKey, populationInfo, contextInfo);
    }

    @Override
    public PopulationRuleInfo createPopulationRule(PopulationRuleInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // validate
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationRule(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            populationInfo, contextInfo);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createPopulationRule(populationInfo, contextInfo);
    }

    @Override
    public PopulationInfo createPopulation(PopulationInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // validate
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            populationInfo, contextInfo);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createPopulation(populationInfo, contextInfo);
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
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validatePopulationCategory(validationTypeKey,
                    populationCategoryTypeKey, populationCategoryInfo, contextInfo);
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
        // validate
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            populationCategoryTypeKey, populationCategoryInfo, contextInfo);
            if (checkForErrors(errors)) {
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
        // validate
        try {
            List<ValidationResultInfo> errors =
                    this.validatePopulationCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            populationCategoryId, populationInfo, contextInfo);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updatePopulationCategory(populationCategoryId, populationInfo, contextInfo);
    }

    private static boolean checkForErrors(List<ValidationResultInfo> errors) {

        if (errors != null && !errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                if (error.isError()) {
                    return true;
                }
            }
        }

        return false;
    }

    public TypeService getTypeService() {
        if(typeService == null){
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

}
