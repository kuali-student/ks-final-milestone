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
package org.kuali.student.enrollment.classI.lpr.mock;

import java.util.List;

import org.kuali.student.common.infc.HoldsValidator;
import org.kuali.student.datadictionary.DataDictionaryValidator;
import org.kuali.student.enrollment.classI.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.classI.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.classI.lpr.service.LuiPersonRelationServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * @author nwright
 */
public class LuiPersonRelationServiceValidationDecorator extends LuiPersonRelationServiceDecorator
        implements LuiPersonRelationService, HoldsValidator {

    private DataDictionaryValidator validator;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
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
        return this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), luiPersonRelationInfo, context);
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
        List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.name(),
                luiPersonRelationInfo, context);
        if (!vris.isEmpty()) {
            throw new DataValidationErrorException("Failed validation", vris);
        }
        return this.nextDecorator.createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context);
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
        List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.name(),
                luiPersonRelationInfo, context);
        if (!vris.isEmpty()) {
            throw new DataValidationErrorException("Failed validation", vris);
        }
        return this.nextDecorator.updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
    }
}

