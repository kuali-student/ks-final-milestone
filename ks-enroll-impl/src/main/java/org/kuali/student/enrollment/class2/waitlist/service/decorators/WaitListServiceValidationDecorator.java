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
package org.kuali.student.enrollment.class2.waitlist.service.decorators;


import java.util.List;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.waitlist.service.WaitListServiceDecorator;
import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
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
import org.kuali.student.r2.common.util.constants.LprRosterServiceConstants;
import org.kuali.student.r2.common.util.constants.WaitListServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;


public class WaitListServiceValidationDecorator extends WaitListServiceDecorator
{
    private TypeService typeService = null;
	// validator property w/getter & setter
	private DataDictionaryValidator validator;

    public TypeService getTypeService() {
        if(typeService == null){
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

	public DataDictionaryValidator getValidator() {
	    return validator;
	}
	public void setValidator(DataDictionaryValidator validator) {
	    this.validator = validator;        
	}
	
	@Override
	public List<ValidationResultInfo> validateWaitList(String validationTypeKey, String waitListTypeKey, WaitListInfo waitListInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// validate
		List<ValidationResultInfo> errors;
		try {
            errors = ValidationUtils.validateTypeKey(waitListTypeKey, WaitListServiceConstants.REF_OBJECT_URI_WAIT_LIST, getTypeService(), contextInfo);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, waitListInfo, contextInfo));
            errors.addAll(getNextDecorator().validateWaitList(validationTypeKey, waitListTypeKey, waitListInfo, contextInfo));
		} catch (DoesNotExistException ex) {
		  throw new OperationFailedException("Error validating", ex);
		}
		return errors;
	}
	
	@Override
	public WaitListInfo createWaitList(String waitListTypeKey, WaitListInfo waitListInfo, ContextInfo contextInfo)
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
		      this.validateWaitList(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), waitListTypeKey, waitListInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().createWaitList(waitListTypeKey, waitListInfo, contextInfo);
	}
	
	@Override
	public WaitListInfo updateWaitList(String waitListId, WaitListInfo waitListInfo, ContextInfo contextInfo)
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
		      this.validateWaitList(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), waitListInfo.getTypeKey(), waitListInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().updateWaitList(waitListId, waitListInfo, contextInfo);
	}
	
	@Override
	public List<ValidationResultInfo> validateWaitListEntry(String validationTypeKey, String waitListId, String studentId, String waitListEntryTypeKey, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// validate
		List<ValidationResultInfo> errors;
		try {
            errors = ValidationUtils.validateTypeKey(waitListEntryTypeKey, WaitListServiceConstants.REF_OBJECT_URI_WAIT_LIST_ENTRY, getTypeService(), contextInfo);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, waitListEntryInfo, contextInfo));
            errors.addAll(getNextDecorator().validateWaitListEntry(validationTypeKey, waitListId, studentId, waitListEntryTypeKey, waitListEntryInfo, contextInfo));
		} catch (DoesNotExistException ex) {
		  throw new OperationFailedException("Error validating", ex);
		}
		return errors;
	}
	
	@Override
	public WaitListEntryInfo createWaitListEntry(String waitListId, String studentId, String waitListEntryTypeKey, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo)
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
		      this.validateWaitListEntry(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), waitListId, studentId, waitListEntryTypeKey, waitListEntryInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().createWaitListEntry(waitListId, studentId, waitListEntryTypeKey, waitListEntryInfo, contextInfo);
	}
	
	@Override
	public WaitListEntryInfo updateWaitListEntry(String waitListEntryId, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo)
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
		      this.validateWaitListEntry(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), waitListEntryInfo.getWaitListId(),
                      waitListEntryInfo.getStudentId(), waitListEntryInfo.getTypeKey(), waitListEntryInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().updateWaitListEntry(waitListEntryId, waitListEntryInfo, contextInfo);
	}
}

