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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import javax.jws.WebParam;
import java.util.List;

/**
 * This class represents a SchedulingService decorator.
 *
 * @author Mezba Mahtab
 */
public class SchedulingServiceDecorator implements SchedulingService {

    private SchedulingService nextDecorator;

    public SchedulingService getNextDecorator() throws OperationFailedException {
        if (nextDecorator == null) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(SchedulingService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public ScheduleInfo getSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSchedule(scheduleId, contextInfo);
    }

    @Override
    public List<ScheduleInfo> getSchedulesByIds(@WebParam(name = "scheduleIds") List<String> scheduleIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSchedulesByIds(scheduleIds, contextInfo);
    }

    @Override
    public List<String> getScheduleIdsByType(@WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleIdsByType(scheduleTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForScheduleIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleIds(criteria, contextInfo);
    }

    @Override
    public List<ScheduleInfo> searchForSchedules(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSchedules(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateSchedule(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateSchedule(validationTypeKey, scheduleTypeKey, scheduleInfo, contextInfo);
    }

    @Override
    public ScheduleInfo createSchedule(@WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createSchedule(scheduleTypeKey, scheduleInfo, contextInfo);
    }

    @Override
    public ScheduleInfo updateSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateSchedule(scheduleId, scheduleInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteSchedule(scheduleId, contextInfo);
    }

    @Override
    public ScheduleBatchInfo getScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatch(scheduleBatchId, contextInfo);
    }

    @Override
    public List<ScheduleBatchInfo> getScheduleBatchesByIds(@WebParam(name = "scheduleBatchIds") List<String> scheduleBatchIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchesByIds(scheduleBatchIds, contextInfo);
    }

    @Override
    public List<String> getScheduleBatchIdsByType(@WebParam(name = "scheduleBatchTypeKey") String scheduleBatchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchIdsByType(scheduleBatchTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForScheduleBatchIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleBatchIds(criteria, contextInfo);
    }

    @Override
    public List<ScheduleBatchInfo> searchForScheduleBatches(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleBatches(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateScheduleBatch(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleBatchTypeKey") String scheduleBatchTypeKey, @WebParam(name = "scheduleBatchInfo") ScheduleBatchInfo scheduleBatchInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateScheduleBatch(validationTypeKey, scheduleBatchTypeKey, scheduleBatchInfo, contextInfo);
    }

    @Override
    public ScheduleBatchInfo createScheduleBatch(@WebParam(name = "scheduleBatchTypeKey") String scheduleBatchTypeKey, @WebParam(name = "scheduleBatchInfo") ScheduleBatchInfo scheduleBatchInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createScheduleBatch(scheduleBatchTypeKey, scheduleBatchInfo, contextInfo);
    }

    @Override
    public ScheduleBatchInfo updateScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "scheduleBatchInfo") ScheduleBatchInfo scheduleBatchInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateScheduleBatch(scheduleBatchId, scheduleBatchInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteScheduleBatch(scheduleBatchId, contextInfo);
    }

    @Override
    public ScheduleRequestInfo getScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequest(scheduleRequestId, contextInfo);
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByIds(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestsByIds(scheduleRequestIds, contextInfo);
    }

    @Override
    public List<String> getScheduleRequestIdsByType(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestIdsByType(scheduleRequestTypeKey, contextInfo);
    }

    @Override
    public List<String> getScheduleRequestIdsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestIdsByRefObject(refObjectType, refObjectId, contextInfo);
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestsByRefObject(refObjectType, refObjectId, contextInfo);
    }

    @Override
    public List<String> searchForScheduleRequestIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleRequestIds(criteria, contextInfo);
    }

    @Override
    public List<ScheduleRequestInfo> searchForScheduleRequests(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleRequests(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateScheduleRequest(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateScheduleRequest(validationTypeKey, scheduleRequestTypeKey, scheduleRequestInfo, contextInfo);
    }

    @Override
    public ScheduleRequestInfo createScheduleRequest(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createScheduleRequest(scheduleRequestTypeKey, scheduleRequestInfo, contextInfo);
    }

    @Override
    public ScheduleRequestInfo updateScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateScheduleRequest(scheduleRequestId, scheduleRequestInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteScheduleRequest(scheduleRequestId, contextInfo);
    }

    @Override
    public TimeSlotInfo getTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTimeSlot(timeSlotId, contextInfo);
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByIds(@WebParam(name = "timeSlotIds") List<String> timeSlotIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTimeSlotsByIds(timeSlotIds, contextInfo);
    }

    @Override
    public List<String> getTimeSlotIdsByType(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTimeSlotIdsByType(timeSlotTypeKey, contextInfo);
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTime(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "daysOfWeek") List<Integer> daysOfWeek, @WebParam(name = "startTime") TimeOfDayInfo startTime, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTimeSlotsByDaysAndStartTime(timeSlotTypeKey, daysOfWeek, startTime, contextInfo);
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTimeAndEndTime(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "daysOfWeek") List<Integer> daysOfWeek, @WebParam(name = "startTime") TimeOfDayInfo startTime, @WebParam(name = "endTime") TimeOfDayInfo endTime, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTimeSlotsByDaysAndStartTimeAndEndTime(timeSlotTypeKey, daysOfWeek, startTime, endTime, contextInfo);
    }

    @Override
    public List<String> searchForTimeSlotIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForTimeSlotIds(criteria, contextInfo);
    }

    @Override
    public List<TimeSlotInfo> searchForTimeSlots(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForTimeSlots(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateTimeSlot(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateTimeSlot(validationTypeKey, timeSlotTypeKey, timeSlotInfo, contextInfo);
    }

    @Override
    public TimeSlotInfo createTimeSlot(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createTimeSlot(timeSlotTypeKey, timeSlotInfo, contextInfo);
    }

    @Override
    public TimeSlotInfo updateTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateTimeSlot(timeSlotId, timeSlotInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteTimeSlot(timeSlotId, contextInfo);
    }

    @Override
    public StatusInfo submitScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().submitScheduleBatch(scheduleBatchId, contextInfo);
    }

    @Override
    public StatusInfo commitSchedules(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().commitSchedules(scheduleBatchId, contextInfo);
    }

    @Override
    public List<Integer> getValidDaysOfWeekByTimeSlotType(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getValidDaysOfWeekByTimeSlotType(timeSlotTypeKey, contextInfo);
    }

    @Override
    public List<ScheduleBatchInfo> getScheduleBatchesForScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchesForScheduleTransaction(scheduleTransactionId, contextInfo);
    }

    @Override
    public ScheduleTransactionInfo getScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleTransaction(scheduleTransactionId, contextInfo);
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByIds(@WebParam(name = "scheduleTransactionIds") List<String> scheduleTransactionIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleTransactionsByIds(scheduleTransactionIds, contextInfo);
    }

    @Override
    public List<String> getScheduleTransactionIdsByType(@WebParam(name = "scheduleTransactionTypeKey") String scheduleTransactionTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleTransactionIdsByType(scheduleTransactionTypeKey, contextInfo);
    }

    @Override
    public List<String> getScheduleTransactionIdsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleTransactionIdsByRefObject(refObjectType, refObjectId, contextInfo);
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleTransactionsByRefObject(refObjectType, refObjectId, contextInfo);
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsForScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleTransactionsForScheduleBatch(scheduleBatchId, contextInfo);
    }

    @Override
    public List<String> searchForScheduleTransactionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleTransactionIds(criteria, contextInfo);
    }

    @Override
    public List<ScheduleTransactionInfo> searchForScheduleTransactions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleTransactions(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateScheduleTransaction(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "scheduleTransactionTypeKey") String scheduleTransactionTypeKey, @WebParam(name = "scheduleTransactionInfo") ScheduleRequestInfo scheduleTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateScheduleTransaction(validationTypeKey, scheduleBatchId, scheduleTransactionTypeKey, scheduleTransactionInfo, contextInfo);
    }

    @Override
    public ScheduleTransactionInfo createScheduleTransaction(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "scheduleTransactionTypeKey") String scheduleTransactionTypeKey, @WebParam(name = "scheduleTransactionInfo") ScheduleTransactionInfo scheduleTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createScheduleTransaction(scheduleBatchId, scheduleTransactionTypeKey, scheduleTransactionInfo, contextInfo);
    }

    @Override
    public ScheduleTransactionInfo updateScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "scheduleTransactionInfo") ScheduleTransactionInfo scheduleTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateScheduleTransaction(scheduleTransactionId, scheduleTransactionInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteScheduleTransaction(scheduleTransactionId, contextInfo);
    }

    @Override
    public Boolean areTimeSlotsInConflict(@WebParam(name = "timeSlot1Id") String timeSlot1Id, @WebParam(name = "timeSlot2Id") String timeSlot2Id, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().areTimeSlotsInConflict(timeSlot1Id, timeSlot2Id, contextInfo);
    }

    @Override
    public ScheduleDisplayInfo getScheduleDisplay(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleDisplay(scheduleId, contextInfo);
    }

    @Override
    public List<ScheduleDisplayInfo> getScheduleDisplaysByIds(@WebParam(name = "scheduleIds") List<String> scheduleIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleDisplaysByIds(scheduleIds, contextInfo);
    }

    @Override
    public List<ScheduleDisplayInfo> searchForScheduleDisplays(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleDisplays(criteria, contextInfo);
    }

    @Override
    public ScheduleRequestDisplayInfo getScheduleRequestDisplay(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestDisplay(scheduleRequestId, contextInfo);
    }

    @Override
    public List<ScheduleRequestDisplayInfo> getScheduleRequestDisplaysByIds(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestDisplaysByIds(scheduleRequestIds, contextInfo);
    }

    @Override
    public List<ScheduleRequestDisplayInfo> searchForScheduleRequestDisplays(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleRequestDisplays(criteria, contextInfo);
    }

    @Override
    public ScheduleRequestSetInfo getScheduleRequestSet(@WebParam(name = "scheduleRequestSetId") String scheduleRequestSetId,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestSet(scheduleRequestSetId, contextInfo);
    }

    @Override
    public List<ScheduleRequestSetInfo> getScheduleRequestSetsByIds(@WebParam(name = "scheduleRequestSetIds") List<String> scheduleRequestSetIds,
                                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestSetsByIds(scheduleRequestSetIds, contextInfo);
    }

    @Override
    public List<String> getScheduleRequestSetIdsByType(@WebParam(name = "scheduleRequestSetTypeKey") String scheduleRequestSetTypeKey,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestSetIdsByType(scheduleRequestSetTypeKey, contextInfo);
    }

    @Override
    public List<String> getScheduleRequestSetIdsByRefObjType(@WebParam(name = "refObjectTypeKey") String refObjectTypeKey,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestSetIdsByRefObjType(refObjectTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForScheduleRequestSetIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleRequestSetIds(criteria, contextInfo);
    }

    @Override
    public List<ScheduleRequestSetInfo> searchForScheduleRequestSets(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForScheduleRequestSets(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateScheduleRequestSet(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                 @WebParam(name = "scheduleRequestSetTypeKey") String scheduleRequestSetTypeKey,
                                                                 @WebParam(name = "refObjectTypeKey") String refObjectTypeKey,
                                                                 @WebParam(name = "scheduleRequestSetInfo") ScheduleRequestSetInfo scheduleRequestSetInfo,
                                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateScheduleRequestSet(validationTypeKey, scheduleRequestSetTypeKey, refObjectTypeKey, scheduleRequestSetInfo, contextInfo);
    }

    @Override
    public ScheduleRequestSetInfo createScheduleRequestSet(@WebParam(name = "scheduleRequestSetTypeKey") String scheduleRequestSetTypeKey,
                                                           @WebParam(name = "refObjectTypeKey") String refObjectTypeKey,
                                                           @WebParam(name = "scheduleRequestSetInfo") ScheduleRequestSetInfo scheduleRequestSetInfo,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createScheduleRequestSet(scheduleRequestSetTypeKey, refObjectTypeKey, scheduleRequestSetInfo, contextInfo);
    }

    @Override
    public ScheduleRequestSetInfo updateScheduleRequestSet(@WebParam(name = "scheduleRequestSetId") String scheduleRequestSetId,
                                                           @WebParam(name = "scheduleRequestSetInfo") ScheduleRequestSetInfo scheduleRequestSetInfo,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateScheduleRequestSet(scheduleRequestSetId, scheduleRequestSetInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteScheduleRequestSet(@WebParam(name = "scheduleRequestSetId") String scheduleRequestSetId,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteScheduleRequestSet(scheduleRequestSetId, contextInfo);
    }

    @Override
    public List<ScheduleRequestSetInfo> getScheduleRequestSetsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestSetsByRefObject(refObjectType, refObjectId, contextInfo);
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObjects(
            @WebParam(name = "refObjectType") String refObjectType,
            @WebParam(name = "refObjectId") List<String> refObjectIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestsByRefObjects(refObjectType, refObjectIds, contextInfo);
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByScheduleRequestSet(String scheduleRequestSetId,
                                                                             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestsByScheduleRequestSet(scheduleRequestSetId, contextInfo);
    }


}