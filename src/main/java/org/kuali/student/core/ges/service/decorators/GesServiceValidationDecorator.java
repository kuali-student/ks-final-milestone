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
package org.kuali.student.core.ges.service.decorators;


import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
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
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.common.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesServiceDecorator;
import org.kuali.student.core.ges.service.GesServiceNamespace;

import javax.xml.namespace.QName;
import java.util.List;


public class GesServiceValidationDecorator extends GesServiceDecorator
{
    private TypeService typeService;
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
    public List<ValidationResultInfo> validateParameter(String validationTypeKey, String parameterTypeKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// validate
		List<ValidationResultInfo> errors;
		try {
            errors = ValidationUtils.validateTypeKey(parameterTypeKey, GesServiceNamespace.REF_OBJECT_URI_PARAMETER, getTypeService(), contextInfo);
		    errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, parameterInfo, contextInfo));
		    errors.addAll(getNextDecorator().validateParameter(validationTypeKey, parameterTypeKey, parameterInfo, contextInfo));
		} catch (DoesNotExistException ex) {
		  throw new OperationFailedException("Error validating", ex);
		}
		return errors;
	}
	
	@Override
    public ParameterInfo createParameter(String parameterKey, String parameterTypeKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// create 
		try {
		    List<ValidationResultInfo> errors =
		      this.validateParameter(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), parameterTypeKey, parameterInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().createParameter(parameterKey, parameterTypeKey, parameterInfo, contextInfo);
	}
	
	@Override
	public ParameterInfo updateParameter(String parameterKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
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
		      this.validateParameter(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), parameterInfo.getTypeKey(), parameterInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().updateParameter(parameterKey, parameterInfo, contextInfo);
	}
	
	@Override
    public List<ValidationResultInfo> validateValue(String validationTypeKey, String valueTypeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// validate
		List<ValidationResultInfo> errors;
		try {
            errors = ValidationUtils.validateTypeKey(valueTypeKey, GesServiceNamespace.REF_OBJECT_URI_VALUE, getTypeService(), contextInfo);
		    errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, valueInfo, contextInfo));
		    errors.addAll(getNextDecorator().validateValue(validationTypeKey, valueTypeKey, parameterKey, valueInfo, contextInfo));
		} catch (DoesNotExistException ex) {
		  throw new OperationFailedException("Error validating", ex);
		}
		return errors;
	}
	
	@Override
    public ValueInfo createValue(String valueTypeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo)
        throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException
	{
		// create 
		try {
		    List<ValidationResultInfo> errors =
		      this.validateValue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), valueTypeKey, parameterKey, valueInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().createValue(valueTypeKey, parameterKey, valueInfo, contextInfo);
	}
	
	@Override
    public ValueInfo updateValue(String valueId, ValueInfo valueInfo, ContextInfo contextInfo)
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
		      this.validateValue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), valueInfo.getTypeKey(), valueInfo.getParameterKey(), valueInfo, contextInfo);
		    if (!errors.isEmpty()) {
		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating", ex);
		}
		return getNextDecorator().updateValue(valueId, valueInfo, contextInfo);
	}
}

