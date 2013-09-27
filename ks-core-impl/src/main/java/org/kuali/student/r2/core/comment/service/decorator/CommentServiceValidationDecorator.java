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
package org.kuali.student.r2.core.comment.service.decorator;


import java.util.List;

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
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentServiceDecorator;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;


public class CommentServiceValidationDecorator extends CommentServiceDecorator {
    private TypeService typeService;
    // validator property w/getter & setter
    private DataDictionaryValidator validator;

    public TypeService getTypeService() {
        if (typeService == null) {
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
    public CommentInfo createComment(String referenceId, String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateComment(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), referenceId, referenceTypeKey, commentTypeKey, commentInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createComment(referenceId, referenceTypeKey, commentTypeKey, commentInfo, contextInfo);
    }

    @Override
    public CommentInfo updateComment(String commentId, CommentInfo commentInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateComment(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), commentInfo.getReferenceId(), commentInfo.getReferenceTypeKey(), commentInfo.getTypeKey(), commentInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateComment(commentId, commentInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateComment(String validationTypeKey, String referenceId, String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(commentInfo.getTypeKey(), CommentServiceConstants.REF_OBJECT_URI_COMMENT, getTypeService(), contextInfo);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, commentInfo, contextInfo));
            errors.addAll(getNextDecorator().validateComment(validationTypeKey, referenceId, referenceTypeKey, commentTypeKey, commentInfo, contextInfo));
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }
}

