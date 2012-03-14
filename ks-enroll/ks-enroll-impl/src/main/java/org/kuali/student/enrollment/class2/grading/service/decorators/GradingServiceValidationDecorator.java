/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.grading.service.decorators;


import java.util.List;

import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

public class GradingServiceValidationDecorator extends GradingServiceDecorator  implements HoldsValidator, HoldsDataDictionaryService{
	private DataDictionaryValidator validator;
	private DataDictionaryService dataDictionaryService;
	
    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;        
    }

	@Override
	public DataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	@Override
	public void setDataDictionaryService(
			DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;		
	}

    private void gradingFullValidation(GradeRosterInfo gradeRoster, ContextInfo context)
		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
		    List<ValidationResultInfo> errors =
		            this.validateGradeRoster(gradeRoster, context);
		    if (!errors.isEmpty()) {
		        throw new DataValidationErrorException("Error(s) validating graderoster", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating graderoster", ex);
		}
}
    
	@Override
	public List<ValidationResultInfo> validateGradeRoster(
			GradeRosterInfo gradeRoster, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), gradeRoster, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateGradeRoster(gradeRoster, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error trying to validate course offering", ex);
    	}
    	return errors;
	}
	
    @Override
    public GradeRosterInfo updateInterimGradeRoster(GradeRosterInfo gradeRoster, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	gradingFullValidation(gradeRoster, context);
        return getNextDecorator().updateInterimGradeRoster(gradeRoster, context);
    }
}
