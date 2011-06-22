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

import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.service.HoldServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;

public class HoldServiceValidationDecorator extends HoldServiceDecorator implements HoldsDataDictionaryService, HoldsValidator {

    private DataDictionaryValidator validator;
    
    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;        
    }

    
    private DataDictionaryService dataDictionaryService;

    @Override
    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    @Override
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    private List<ValidationResultInfo> validate(String validationType, Object info, ContextInfo context) throws OperationFailedException, MissingParameterException, InvalidParameterException {
        List<ValidationResultInfo> errors;
        try {
            errors = this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), info, context);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Validation failed due to permission exception", ex);
        }
        return errors;
    }

    private void validateHold(HoldInfo holdInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateHold(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), holdInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Errors", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("erorr trying to validate", ex);
        }
    }
    
    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return validate(validationTypeKey, holdInfo, context);
    }

    @Override
    public HoldInfo createHold(HoldInfo holdInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	validateHold(holdInfo, context);
        return getHoldService().createHold(holdInfo, context);
    }

    @Override
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	validateHold(holdInfo, context);
        return getHoldService().updateHold(holdId, holdInfo, context);
    }

}
