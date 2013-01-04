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
package org.kuali.student.enrollment.class2.courseofferingset.service.decorators;

import java.util.List;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

public class CourseOfferingSetServiceValidationDecorator extends CourseOfferingSetServiceDecorator {
    // validator property w/getter & setter

    private DataDictionaryValidator validator;

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public SocInfo createSoc(String termId, String socTypeKey, SocInfo socInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateSoc(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), socInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createSoc(termId, socTypeKey, socInfo, context);
    }

    @Override
    public SocInfo updateSoc(String socId, SocInfo socInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateSoc(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), socInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateSoc(socId, socInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateSoc(String validationType, SocInfo socInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, socInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateSoc(validationType, socInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public SocRolloverResultInfo createSocRolloverResult(String socRolloverResultTypeKey, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateSocRolloverResult(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    socRolloverResultInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createSocRolloverResult(socRolloverResultTypeKey, socRolloverResultInfo, context);
    }

    @Override
    public SocRolloverResultInfo updateSocRolloverResult(String socRolloverResultId, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateSocRolloverResult(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    socRolloverResultInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateSocRolloverResult(socRolloverResultId, socRolloverResultInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResult(String validationType, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, socRolloverResultInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateSocRolloverResult(validationType,
                    socRolloverResultInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public SocRolloverResultItemInfo createSocRolloverResultItem(String socRolloverResultId, String socRolloverResultItemTypeKey, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateSocRolloverResultItem(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    socRolloverResultItemInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createSocRolloverResultItem(socRolloverResultId, socRolloverResultItemTypeKey,
                socRolloverResultItemInfo, context);
    }

    @Override
    public SocRolloverResultItemInfo updateSocRolloverResultItem(String socRolloverResultItemId, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateSocRolloverResultItem(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    socRolloverResultItemInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateSocRolloverResultItem(socRolloverResultItemId, socRolloverResultItemInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResultItem(String validationType, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, socRolloverResultItemInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateSocRolloverResultItem(validationType,
                    socRolloverResultItemInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

   
}
