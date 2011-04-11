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

import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.infc.HoldsValidator;
import org.kuali.student.datadictionary.DataDictionaryValidatorInfc;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationServiceAdapter;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;

/**
 * @author nwright
 */
public class LuiPersonRelationServiceValidationImpl extends LuiPersonRelationServiceAdapter
        implements LuiPersonRelationService, HoldsValidator {

    private DataDictionaryValidatorInfc validator;

    @Override
    public DataDictionaryValidatorInfc getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidatorInfc validator) {
        this.validator = validator;
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

