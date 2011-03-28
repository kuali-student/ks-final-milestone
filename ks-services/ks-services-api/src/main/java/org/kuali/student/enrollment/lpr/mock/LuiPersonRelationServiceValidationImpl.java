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
package org.kuali.student.enrollment.lpr.mock;

import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.common.exceptions.*;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;

import java.util.*;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.infc.HoldsDataDictionaryService;
import org.kuali.student.common.infc.HoldsValidator;
import org.kuali.student.datadictionary.DataDictionaryValidatorInfc;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.datadictionary.service.DataDictionaryService;

/**
 * @author nwright
 */
public class LuiPersonRelationServiceValidationImpl extends LuiPersonRelationServiceAdapter
        implements LuiPersonRelationService, HoldsValidator, HoldsDataDictionaryService {

    private DataDictionaryValidatorInfc validator;
    private DataDictionaryService dataDictionaryService;

    @Override
    public void setValidator(DataDictionaryValidatorInfc validator) {
        this.validator = validator;
    }

    @Override
    public DataDictionaryValidatorInfc getValidator() {
        return validator;
    }

    @Override
    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    @Override
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return dataDictionaryService.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return this.dataDictionaryService.getDataDictionaryEntryKeys(context);
    }

    @Override
    public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
            LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return this.validator.validate(DataDictionaryValidatorInfc.ValidationType.fromString(validationType), luiPersonRelationInfo, context);
    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId,
            String luiPersonRelationType,
            LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws
            AlreadyExistsException,
            DoesNotExistException,
            DisabledIdentifierException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            DataValidationErrorException,
            ReadOnlyException,
            PermissionDeniedException {
        List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidatorInfc.ValidationType.FULL_VALIDATION.name(),
                luiPersonRelationInfo, context);
        if (!vris.isEmpty()) {
            throw new DataValidationErrorException("Failed validation", vris);
        }
        return this.getLprService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context);
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId,
            LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            ReadOnlyException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidatorInfc.ValidationType.FULL_VALIDATION.name(),
                luiPersonRelationInfo, context);
        if (!vris.isEmpty()) {
            throw new DataValidationErrorException("Failed validation", vris);
        }
        return this.getLprService().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
    }
}

