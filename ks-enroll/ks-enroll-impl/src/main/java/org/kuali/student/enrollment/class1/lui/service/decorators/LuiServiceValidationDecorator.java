/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.class1.lui.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiServiceDecorator;

import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
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
import org.kuali.student.r2.core.class1.util.ValidationUtils;

public class LuiServiceValidationDecorator 
    extends LuiServiceDecorator 
    implements HoldsValidator, HoldsDataDictionaryService {

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
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }


    @Override
    public List<ValidationResultInfo> validateLui(String validationTypeKey, 
                                                  String cluId, 
                                                  String atpId,
                                                  String luiTypeKey, 
                                                  LuiInfo luiInfo, 
                                                  ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, luiInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateLui(validationTypeKey, cluId, atpId, luiTypeKey, luiInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate lui", ex);
        }

        return errors;
    }
	
    private void _luiFullValidation(String cluId, 
                                    String atpId, 
                                    String luiTypeKey, 
                                    LuiInfo luiInfo, 
                                    ContextInfo context)
        throws DataValidationErrorException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {
        
        try {
            List<ValidationResultInfo> errors = this.validateLui(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), cluId, atpId, luiTypeKey, luiInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating lui", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating lui", ex);
        }
    }
	
    @Override
    public LuiInfo createLui(String cluId, 
                             String atpId, 
                             String luiTypeKey, 
                             LuiInfo luiInfo, 
                             ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException {
        
        _luiFullValidation(cluId, atpId, luiTypeKey, luiInfo, context);
        return getNextDecorator().createLui(cluId, atpId, luiTypeKey, luiInfo, context);
    }

    @Override
    public LuiInfo updateLui(String luiId, 
                             LuiInfo luiInfo, 
                             ContextInfo context)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException, VersionMismatchException {
        
        _luiFullValidation(luiInfo.getCluId(), luiInfo.getAtpId(), luiInfo.getTypeKey(), luiInfo, context);
        return getNextDecorator().updateLui(luiId,luiInfo, context);
    }
	
    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationTypeKey, 
                                                             String luiId,
                                                             String relatedLuiId,
                                                             String luiLuiRelationTypeKey, 
                                                             LuiLuiRelationInfo luiLuiRelationInfo, 
                                                             ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {
        
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, luiLuiRelationInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateLuiLuiRelation(validationTypeKey, luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate lui-lui relation", ex);
        }

        return errors;
    }

    private void _luiLuiRelationFullValidation(String luiId, 
                                               String relatedLuiId, 
                                               String luiLuiRelationTypeKey, 
                                               LuiLuiRelationInfo luiLuiRelationInfo, 
                                               ContextInfo context)
        throws DataValidationErrorException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {
        
        try {
            List<ValidationResultInfo> errors = this.validateLuiLuiRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating lui-lui relation", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating lui-lui relation", ex);
        }
    }
    
    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, 
                                                   String relatedLuiId, 
                                                   String luiLuiRelationTypeKey,
                                                   LuiLuiRelationInfo luiLuiRelationInfo, 
                                                   ContextInfo context)
        throws CircularRelationshipException,
               DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException {
        
        _luiLuiRelationFullValidation(luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, context);
        return getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, context);
    }
    
    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
                                                   LuiLuiRelationInfo luiLuiRelationInfo, 
                                                   ContextInfo context)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException, VersionMismatchException {

        _luiLuiRelationFullValidation(luiLuiRelationInfo.getLuiId(), luiLuiRelationInfo.getRelatedLuiId(), luiLuiRelationInfo.getTypeKey(), luiLuiRelationInfo, context);
        return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
    }
}
