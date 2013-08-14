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
package org.kuali.student.enrollment.class2.coursewaitlist.service.decorator;


import java.util.List;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListServiceDecorator;
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
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LprRosterServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;


public class CourseWaitListServiceValidationDecorator extends CourseWaitListServiceDecorator
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
    public List<ValidationResultInfo> validateCourseWaitList(String validationTypeKey, String courseWaitListTypeKey, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(courseWaitListTypeKey, CourseWaitListServiceConstants.REF_OBJECT_URI_WAIT_LIST, getTypeService(), contextInfo);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, courseWaitListInfo, contextInfo));
            errors.addAll(getNextDecorator().validateCourseWaitList(validationTypeKey, courseWaitListTypeKey, courseWaitListInfo, contextInfo));
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public CourseWaitListInfo createCourseWaitList(String courseWaitListTypeKey, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo)
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
                    this.validateCourseWaitList(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), courseWaitListTypeKey, courseWaitListInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createCourseWaitList(courseWaitListTypeKey, courseWaitListInfo, contextInfo);
    }

    @Override
    public CourseWaitListInfo updateCourseWaitList(String courseWaitListId, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo)
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
                    this.validateCourseWaitList(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), courseWaitListInfo.getTypeKey(), courseWaitListInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateCourseWaitList(courseWaitListId, courseWaitListInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCourseWaitListEntry(String validationTypeKey, String courseWaitListId, String studentId, String courseWaitListEntryTypeKey, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(courseWaitListEntryTypeKey, CourseWaitListServiceConstants.REF_OBJECT_URI_WAIT_LIST_ENTRY, getTypeService(), contextInfo);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, courseWaitListEntryInfo, contextInfo));
            errors.addAll(getNextDecorator().validateCourseWaitListEntry(validationTypeKey, courseWaitListId, studentId, courseWaitListEntryTypeKey, courseWaitListEntryInfo, contextInfo));
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public CourseWaitListEntryInfo createCourseWaitListEntry(String courseWaitListId, String studentId, String courseWaitListEntryTypeKey, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo)
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
                    this.validateCourseWaitListEntry(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), courseWaitListId, studentId, courseWaitListEntryTypeKey, courseWaitListEntryInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createCourseWaitListEntry(courseWaitListId, studentId, courseWaitListEntryTypeKey, courseWaitListEntryInfo, contextInfo);
    }

    @Override
    public CourseWaitListEntryInfo updateCourseWaitListEntry(String courseWaitListEntryId, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo)
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
                    this.validateCourseWaitListEntry(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), courseWaitListEntryInfo.getCourseWaitListId(), courseWaitListEntryInfo.getStudentId(),
                            courseWaitListEntryInfo.getTypeKey(), courseWaitListEntryInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateCourseWaitListEntry(courseWaitListEntryId, courseWaitListEntryInfo, contextInfo);
    }
}

