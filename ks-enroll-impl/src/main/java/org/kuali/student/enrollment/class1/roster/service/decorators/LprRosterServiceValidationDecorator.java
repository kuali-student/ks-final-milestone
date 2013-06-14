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
package org.kuali.student.enrollment.class1.roster.service.decorators;


import java.util.List;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.enrollment.roster.service.LprRosterServiceDecorator;
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

import javax.jws.WebParam;


public class LprRosterServiceValidationDecorator extends LprRosterServiceDecorator
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
	public List<ValidationResultInfo> validateLprRoster(String validationTypeKey, String lprRosterTypeKey, LprRosterInfo lprRosterInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// validate
		List<ValidationResultInfo> errors;
		try {
		    errors = ValidationUtils.validateInfo(validator, validationTypeKey, lprRosterInfo, contextInfo);
		    List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateLprRoster(validationTypeKey, lprRosterTypeKey, lprRosterInfo, contextInfo);
		   errors.addAll(nextDecoratorErrors);
		} catch (DoesNotExistException ex) {
		  throw new OperationFailedException("Error validating", ex);
		}
		return errors;
	}
	
	@Override
	public LprRosterInfo createLprRoster(String lprRosterTypeKey, LprRosterInfo lprRosterInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// create 
		try {
		    List<ValidationResultInfo> errors = 
		      this.validateLprRoster(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprRosterTypeKey, lprRosterInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().createLprRoster(lprRosterTypeKey, lprRosterInfo, contextInfo);
	}
	
	@Override
	public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo contextInfo)
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
		      this.validateLprRoster(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprRosterInfo.getTypeKey(), lprRosterInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().updateLprRoster(lprRosterId, lprRosterInfo, contextInfo);
	}
	
	@Override
    public List<ValidationResultInfo> validateLprRosterEntry(String validationTypeKey, String lprRosterId, String lprId, String lprRosterEntryTypeKey, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo)
        throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException
	{
		// validate
		List<ValidationResultInfo> errors;
		try {
		    errors = ValidationUtils.validateInfo(validator, validationTypeKey, lprRosterEntryInfo, contextInfo);
		    List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateLprRosterEntry(validationTypeKey, lprRosterId, lprId, lprRosterEntryTypeKey, lprRosterEntryInfo, contextInfo);
		   errors.addAll(nextDecoratorErrors);
		} catch (DoesNotExistException ex) {
		  throw new OperationFailedException("Error validating", ex);
		}
		return errors;
	}
	
	@Override
	public LprRosterEntryInfo createLprRosterEntry(String lprRosterId, String lprId, String lprRosterEntryTypeKey, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// create 
		try {
		    List<ValidationResultInfo> errors = 
		      this.validateLprRosterEntry(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprRosterId, lprId, lprRosterEntryTypeKey, lprRosterEntryInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().createLprRosterEntry(lprRosterId, lprId, lprRosterEntryTypeKey, lprRosterEntryInfo, contextInfo);
	}
	
	@Override
	public LprRosterEntryInfo updateLprRosterEntry(String lprRosterEntryId, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo)
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
		      this.validateLprRosterEntry(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprRosterEntryInfo.getLprRosterId(), lprRosterEntryInfo.getLprId(), lprRosterEntryInfo.getTypeKey(), lprRosterEntryInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().updateLprRosterEntry(lprRosterEntryId, lprRosterEntryInfo, contextInfo);
	}
}

