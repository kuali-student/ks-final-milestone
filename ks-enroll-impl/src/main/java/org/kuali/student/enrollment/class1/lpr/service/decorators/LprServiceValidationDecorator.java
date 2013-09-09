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
package org.kuali.student.enrollment.class1.lpr.service.decorators;


import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.service.LprServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
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
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;


public class LprServiceValidationDecorator extends LprServiceDecorator {
    // validator property w/getter & setter
    private DataDictionaryValidator validator;

    protected TypeService typeService;

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, String luiId, String personId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {

        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(lprInfo.getTypeKey(), LprServiceConstants.REF_OBJECT_URI_LUI_PERSON_RELATION, getTypeService(), contextInfo);
        errors.addAll( ValidationUtils.validateInfo(validator, DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprInfo, contextInfo));
        errors.addAll(getNextDecorator().validateLpr(validationType, luiId, personId, lprTypeKey, lprInfo, contextInfo));

        return errors;
    }

    @Override
    public LprInfo createLpr(String personId, String luiId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {

        List<ValidationResultInfo> errors = this.validateLpr(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), personId, luiId, lprTypeKey, lprInfo, contextInfo);
        if (!errors.isEmpty()) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().createLpr(personId, luiId, lprTypeKey, lprInfo, contextInfo);
    }

    public List<BulkStatusInfo> createLprsForPerson(String personId, String luiId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {

        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();
        for(LprInfo lprInfo: lprInfos)  {
            errors.addAll(validateLpr(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), personId, luiId, lprTypeKey, lprInfo, contextInfo));
        }
        if (!errors.isEmpty()) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }
        return getNextDecorator().createLprsForPerson(personId, lprTypeKey, lprInfos, contextInfo);
    }

    @Override
    public List<BulkStatusInfo> createLprsForLui(String luiId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {

        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();
        for(LprInfo lprInfo: lprInfos)  {
            errors.addAll(validateLpr(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprInfo.getPersonId(), luiId, lprTypeKey, lprInfo, contextInfo));
        }
        if (!errors.isEmpty()) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }
        return getNextDecorator().createLprsForLui(luiId, lprTypeKey, lprInfos, contextInfo);
    }

    @Override
    public LprInfo updateLpr(String lprId, LprInfo lprInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , DataValidationErrorException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {

        LprInfo oldLpr = getNextDecorator().getLpr(lprId, contextInfo);
        List<ValidationResultInfo> errors = ValidationUtils.validateTypesAreEqual(lprInfo, oldLpr);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lpr because the type errors", errors);
        }

        errors = this.validateLpr(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprInfo.getPersonId(), lprInfo.getLuiId(), lprInfo.getTypeKey(), lprInfo, contextInfo);

        return getNextDecorator().updateLpr(lprId, lprInfo, contextInfo);
    }

    @Override
    public LprTransactionInfo createLprTransaction(String lprTransactionType, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {

        List<ValidationResultInfo> errors = this.validateLprTransaction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprTransactionType, lprTransactionInfo, contextInfo);

        if (!errors.isEmpty()) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().createLprTransaction(lprTransactionType, lprTransactionInfo, contextInfo);
    }

    @Override
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {

        LprTransactionInfo oldLprTransactionInfo = getNextDecorator().getLprTransaction(lprTransactionId, contextInfo);
        LprTransactionInfo newLprTransactionInfo = getNextDecorator().createLprTransactionFromExisting(lprTransactionId, contextInfo);

        List<ValidationResultInfo> errors = ValidationUtils.validateTypesAreEqual(newLprTransactionInfo, oldLprTransactionInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        errors = this.validateLprTransaction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), newLprTransactionInfo.getTypeKey(), newLprTransactionInfo, contextInfo);

        return getNextDecorator().createLprTransactionFromExisting(lprTransactionId, contextInfo);
    }

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , VersionMismatchException {

        LprTransactionInfo oldLprTransactionInfo = getNextDecorator().getLprTransaction(lprTransactionId, contextInfo);
        List<ValidationResultInfo> errors = ValidationUtils.validateTypesAreEqual(lprTransactionInfo, oldLprTransactionInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        errors = this.validateLprTransaction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), lprTransactionInfo.getTypeKey(), lprTransactionInfo, contextInfo);

        return getNextDecorator().updateLprTransaction(lprTransactionId, lprTransactionInfo, contextInfo);
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

