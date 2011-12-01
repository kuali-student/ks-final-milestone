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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.service.HoldServiceDecorator;
import org.kuali.student.r2.core.service.util.ValidationUtils;

public class HoldServiceValidationDecorator extends HoldServiceDecorator implements HoldsDataDictionaryService, HoldsValidator
{
	// validator property w/getter & setter
    private DataDictionaryValidator validator;
    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }
    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;        
    }

    // dataDictionaryService property w/getter & setter
    private DataDictionaryService dataDictionaryService;
    @Override
    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }
    @Override
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }
    

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationTypeKey, holdInfo, context);
    		List<ValidationResultInfo> nextDecorationErrors =
    				getNextDecorator().validateHold(validationTypeKey, holdInfo, context);
    		if (null != nextDecorationErrors) {
    			errors.addAll(nextDecorationErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating hold", ex);
    	}
    	return errors;
    }

    @Override
    public HoldInfo createHold(HoldInfo holdInfo, ContextInfo context)
    		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
    	_holdFullValidation(holdInfo, context);
            return getNextDecorator().createHold(holdInfo, context);
       
    }

    @Override
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
    	_holdFullValidation(holdInfo, context);
        return getNextDecorator().updateHold(holdId, holdInfo, context);
    }

    private void _holdFullValidation(HoldInfo holdInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateHold(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), holdInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating hold", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating hold", ex);
        }
    }

}
