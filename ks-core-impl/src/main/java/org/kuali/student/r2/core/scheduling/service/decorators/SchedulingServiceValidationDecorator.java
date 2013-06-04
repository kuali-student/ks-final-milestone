/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Mezba Mahtab on 8/22/12
 */
package org.kuali.student.r2.core.scheduling.service.decorators;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class represents a validation decorator for Scheduling Service.
 *
 * @author Mezba Mahtab
 */
public class SchedulingServiceValidationDecorator extends SchedulingServiceDecorator
{
    // validator property w/getter & setter
    private DataDictionaryValidator validator;
    private TypeService typeService;

    @Override
    public List<ValidationResultInfo> validateSchedule(String validationTypeKey, String scheduleTypeKey, ScheduleInfo scheduleInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {

        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(scheduleTypeKey, SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE, getTypeService(), contextInfo);
        errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, scheduleInfo, contextInfo));
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateSchedule(validationTypeKey, scheduleTypeKey, scheduleInfo, contextInfo);
        errors.addAll(nextDecoratorErrors);

        return errors;
    }

    @Override
    public ScheduleInfo createSchedule(String scheduleTypeKey, ScheduleInfo scheduleInfo, ContextInfo contextInfo)
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
                    this.validateSchedule(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), scheduleTypeKey, scheduleInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createSchedule(scheduleTypeKey, scheduleInfo, contextInfo);
    }

    @Override
    public ScheduleInfo updateSchedule(String scheduleId, ScheduleInfo scheduleInfo, ContextInfo contextInfo)
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
                    this.validateSchedule(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), scheduleInfo.getTypeKey(), scheduleInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateSchedule(scheduleId, scheduleInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateScheduleBatch(String validationTypeKey, String scheduleBatchTypeKey, ScheduleBatchInfo scheduleBatchInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(scheduleBatchTypeKey, SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_BATCH, getTypeService(), contextInfo);
        errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, scheduleBatchInfo, contextInfo));
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateScheduleBatch(validationTypeKey, scheduleBatchTypeKey, scheduleBatchInfo, contextInfo);
        errors.addAll(nextDecoratorErrors);

        return errors;
    }

    @Override
    public ScheduleBatchInfo createScheduleBatch(String scheduleBatchTypeKey, ScheduleBatchInfo scheduleBatchInfo, ContextInfo contextInfo)
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
                    this.validateScheduleBatch(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), scheduleBatchTypeKey, scheduleBatchInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createScheduleBatch(scheduleBatchTypeKey, scheduleBatchInfo, contextInfo);
    }

    @Override
    public ScheduleBatchInfo updateScheduleBatch(String scheduleBatchId, ScheduleBatchInfo scheduleBatchInfo, ContextInfo contextInfo)
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
                    this.validateScheduleBatch(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), scheduleBatchId, scheduleBatchInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateScheduleBatch(scheduleBatchId, scheduleBatchInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateScheduleRequest(String validationTypeKey, String scheduleRequestTypeKey, ScheduleRequestInfo scheduleRequestInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, scheduleRequestInfo, contextInfo);
            errors.addAll(ValidationUtils.validateTypeKey(scheduleRequestTypeKey, SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_REQUEST, getTypeService(), contextInfo));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateScheduleRequest(validationTypeKey, scheduleRequestTypeKey, scheduleRequestInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ScheduleRequestInfo createScheduleRequest(String scheduleRequestTypeKey, ScheduleRequestInfo scheduleRequestInfo, ContextInfo contextInfo)
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
                    this.validateScheduleRequest(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), scheduleRequestTypeKey, scheduleRequestInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createScheduleRequest(scheduleRequestTypeKey, scheduleRequestInfo, contextInfo);
    }

    @Override
    public ScheduleRequestInfo updateScheduleRequest(String scheduleRequestId, ScheduleRequestInfo scheduleRequestInfo, ContextInfo contextInfo)
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
                    this.validateScheduleRequest(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), scheduleRequestInfo.getTypeKey(), scheduleRequestInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateScheduleRequest(scheduleRequestId, scheduleRequestInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateTimeSlot(String validationTypeKey, String timeSlotTypeKey, TimeSlotInfo timeSlotInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, timeSlotInfo, contextInfo);
            errors.addAll(ValidationUtils.validateTypeKey(timeSlotTypeKey, SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT, getTypeService(), contextInfo));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateTimeSlot(validationTypeKey, timeSlotTypeKey, timeSlotInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public TimeSlotInfo createTimeSlot(String timeSlotTypeKey, TimeSlotInfo timeSlotInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create

        if (timeSlotInfo.getId() != null) {
            if (!CommonServiceConstants.isIdAllowedOnCreate(contextInfo)) {
                throw new ReadOnlyException("ID cannot be supplied when creating a TimeSlot.");
            }
        }

        try {
            List<ValidationResultInfo> errors =
                    this.validateTimeSlot(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), timeSlotTypeKey, timeSlotInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createTimeSlot(timeSlotTypeKey, timeSlotInfo, contextInfo);
    }

    @Override
    public TimeSlotInfo updateTimeSlot(String timeSlotId, TimeSlotInfo timeSlotInfo, ContextInfo contextInfo)
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
                    this.validateTimeSlot(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), timeSlotId, timeSlotInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateTimeSlot(timeSlotId, timeSlotInfo, contextInfo);
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