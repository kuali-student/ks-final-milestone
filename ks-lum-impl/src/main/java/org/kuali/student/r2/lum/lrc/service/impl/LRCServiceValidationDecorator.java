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
 * Created by cmuller on 1/21/13
 */
package org.kuali.student.r2.lum.lrc.service.impl;


import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCServiceDecorator;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;


/**
 * This class implements the validation layer for the LRC Service
 *
 * @author Kuali Student Team
 */
public class LRCServiceValidationDecorator extends LRCServiceDecorator {

    private DataDictionaryValidator validator;
    private TypeService typeService;

    @Override
    public ResultScaleInfo createResultScale(String resultScaleTypeKey, ResultScaleInfo resultScaleInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateResultScale(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), resultScaleInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createResultScale(resultScaleTypeKey, resultScaleInfo, context);
    }

    @Override
    public ResultScaleInfo updateResultScale(String resultScaleKey, ResultScaleInfo resultScaleInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateResultScale(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), resultScaleInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateResultScale(resultScaleKey, resultScaleInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultScale(String validationType, ResultScaleInfo gradeScaleInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(gradeScaleInfo.getTypeKey(), LrcServiceConstants.REF_OBJECT_URI_RESULT_SCALE, getTypeService(),context);
            errors.addAll(ValidationUtils.validateInfo(validator, validationType, gradeScaleInfo, context));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateResultScale(validationType, gradeScaleInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ResultValueInfo createResultValue(String resultScaleTypeKey, String typeKey, ResultValueInfo resultValueInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateResultValue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), resultValueInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createResultValue(resultScaleTypeKey, typeKey, resultValueInfo, context);
    }

    @Override
    public ResultValueInfo updateResultValue(String key, ResultValueInfo resultValueInfo, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateResultValue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), resultValueInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateResultValue(key, resultValueInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType, ResultValueInfo resultValueInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(resultValueInfo.getTypeKey(), LrcServiceConstants.REF_OBJECT_URI_RESULT_VALUE, getTypeService(),context);
            errors.addAll(ValidationUtils.validateInfo(validator, validationType, resultValueInfo, context));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateResultValue(validationType, resultValueInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(String resultScaleTypeKey, String typeKey, ResultValuesGroupInfo resultValuesGroupInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateResultValuesGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), resultValuesGroupInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createResultValuesGroup(resultScaleTypeKey, typeKey, resultValuesGroupInfo, context);
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(String key, ResultValuesGroupInfo resultValuesGroupInfo, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateResultValuesGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), resultValuesGroupInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateResultValuesGroup(key, resultValuesGroupInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(String validationType, ResultValuesGroupInfo resultValuesGroupInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(resultValuesGroupInfo.getTypeKey(), LrcServiceConstants.REF_OBJECT_URI_RESULT_VALUES_GROUP, getTypeService(),context);
            errors.addAll(ValidationUtils.validateInfo(validator, validationType, resultValuesGroupInfo, context));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateResultValuesGroup(validationType, resultValuesGroupInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
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
