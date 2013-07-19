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
package org.kuali.student.r2.core.process.service.decorators;

import java.util.List;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

public class ProcessServiceValidationDecorator
        extends ProcessServiceDecorator {
    // validator property w/getter & setter

    private DataDictionaryValidator validator;

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validateProcessCategory(String validationTypeKey,
            String processCategoryTypeKey,
            ProcessCategoryInfo processCategoryInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator,
                    validationTypeKey,
                    processCategoryInfo,
                    contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateProcessCategory(validationTypeKey,
                    processCategoryTypeKey,
                    processCategoryInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(String processCategoryTypeKey,
            ProcessCategoryInfo processCategoryInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateProcessCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    processCategoryTypeKey, processCategoryInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createProcessCategory(processCategoryTypeKey, processCategoryInfo, contextInfo);
    }

    @Override
    public ProcessCategoryInfo updateProcessCategory(String processCategoryId,
            ProcessCategoryInfo processInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateProcessCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    processCategoryId, processInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateProcessCategory(processCategoryId, processInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateProcess(String validationTypeKey,
            String processTypeKey,
            ProcessInfo processInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, processInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateProcess(validationTypeKey, processTypeKey,
                    processInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ProcessInfo createProcess(String processKey,
            String processTypeKey,
            ProcessInfo processInfo,
            ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create
        processInfo.setKey(processKey);
        try {
            List<ValidationResultInfo> errors =
                    this.validateProcess(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    processTypeKey,
                    processInfo,
                    contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createProcess(processKey, processTypeKey, processInfo, contextInfo);
    }

    @Override
    public ProcessInfo updateProcess(String processKey,
            ProcessInfo processInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateProcess(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processKey,
                    processInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateProcess(processKey, processInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey,
            String checkTypeKey,
            CheckInfo checkInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, checkInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateCheck(validationTypeKey, checkTypeKey,
                    checkInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public CheckInfo createCheck(String checkTypeKey,
            CheckInfo checkInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateCheck(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), checkTypeKey, checkInfo,
                    contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createCheck(checkTypeKey, checkInfo, contextInfo);
    }

    @Override
    public CheckInfo updateCheck(String checkId,
            CheckInfo checkInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateCheck(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), checkId, checkInfo,
                    contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateCheck(checkId, checkInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateInstruction(String validationTypeKey,
            String processKey,
            String checkId,
            String instructionTypeKey,
            InstructionInfo instructionInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, instructionInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateInstruction(validationTypeKey, processKey,
                    checkId, instructionTypeKey, instructionInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public InstructionInfo createInstruction(String processKey,
            String checkId,
            String instructionTypeKey,
            InstructionInfo instructionInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateInstruction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processKey,
                    checkId, instructionTypeKey, instructionInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createInstruction(processKey, checkId, instructionTypeKey, instructionInfo, contextInfo);
    }

    @Override
    public InstructionInfo updateInstruction(String instructionId,
            InstructionInfo instructionInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateInstruction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    instructionInfo.getProcessKey(),
                    instructionInfo.getCheckId(),
                    instructionInfo.getTypeKey(),
                    instructionInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateInstruction(instructionId, instructionInfo, contextInfo);
    }
}
