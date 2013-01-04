/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.hold.service.decorators;

import java.util.List;
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
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldServiceDecorator;

public class HoldServiceValidationDecorator
        extends HoldServiceDecorator {
    // validator property w/getter & setter

    private DataDictionaryValidator validator;

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validateAppliedHold(String validationTypeKey,
            AppliedHoldInfo holdInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, holdInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateAppliedHold(validationTypeKey, holdInfo,
                    contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public AppliedHoldInfo createAppliedHold(String personId,
            String issueId,
            String holdTypeKey,
            AppliedHoldInfo holdInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        AppliedHoldInfo info = new AppliedHoldInfo(holdInfo);
        info.setPersonId(personId);
        info.setHoldIssueId(issueId);
        info.setTypeKey(holdTypeKey);
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateAppliedHold(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), info, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createAppliedHold(personId, issueId, holdTypeKey, holdInfo, contextInfo);
    }

    @Override
    public AppliedHoldInfo updateAppliedHold(String holdId,
            AppliedHoldInfo holdInfo,
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
                    this.validateAppliedHold(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), holdInfo,
                    contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateAppliedHold(holdId, holdInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateHoldIssue(String validationTypeKey,
            HoldIssueInfo issueInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, issueInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateHoldIssue(validationTypeKey, issueInfo,
                    contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public HoldIssueInfo createHoldIssue(String issueTypeKey,
            HoldIssueInfo issueInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        HoldIssueInfo info = new HoldIssueInfo(issueInfo);
        info.setTypeKey(issueTypeKey);
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateHoldIssue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), info,
                    contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createHoldIssue(issueTypeKey, issueInfo, contextInfo);
    }

    @Override
    public HoldIssueInfo updateHoldIssue(String issueId,
            HoldIssueInfo issueInfo,
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
                    this.validateHoldIssue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), issueInfo,
                    contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateHoldIssue(issueId, issueInfo, contextInfo);
    }
}
