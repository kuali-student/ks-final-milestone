/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.core.class1.scheduling.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchRespInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRespInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: andy
 * Date: 6/5/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
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
    public List<ScheduleBatchInfo> getScheduleBatchesForScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchesForScheduleRequest(scheduleRequestId, contextInfo);
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
    public StatusInfo addScheduleRequestsToScheduleBatch(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "scheduleBatchId") List<String> scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addScheduleRequestsToScheduleBatch(scheduleRequestIds, scheduleBatchId, contextInfo);
    }

    @Override
    public StatusInfo removeScheduleRequestsFromScheduleBatch(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "scheduleBatchId") List<String> scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeScheduleRequestsFromScheduleBatch(scheduleRequestIds, scheduleBatchId, contextInfo);
    }

    @Override
    public ScheduleBatchRespInfo getScheduleBatchResp(@WebParam(name = "scheduleBatchRespId") String scheduleBatchRespId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchResp(scheduleBatchRespId, contextInfo);
    }

    @Override
    public List<ScheduleBatchRespInfo> getScheduleBatchRespsByIds(@WebParam(name = "scheduleBatchRespIds") List<String> scheduleBatchRespIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchRespsByIds(scheduleBatchRespIds, contextInfo);
    }

    @Override
    public List<String> getScheduleBatchRespIdsByType(@WebParam(name = "scheduleBatchRespTypeKey") String scheduleBatchRespTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchRespIdsByType(scheduleBatchRespTypeKey, contextInfo);
    }

    @Override
    public List<ScheduleBatchRespInfo> getScheduleBatchRespsByScheduleBatchRequest(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleBatchRespsByScheduleBatchRequest(scheduleBatchId, contextInfo);
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
    public List<String> getScheduleRequestsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestsByRefObject(refObjectType, refObjectId, contextInfo);
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsForScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRequestsForScheduleBatch(scheduleBatchId, contextInfo);
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
    public ScheduleRespInfo getScheduleResp(@WebParam(name = "scheduleRespId") String scheduleRespId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleResp(scheduleRespId, contextInfo);
    }

    @Override
    public List<ScheduleRespInfo> getScheduleRespsByIds(@WebParam(name = "scheduleRespIds") List<String> scheduleRespIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRespsByIds(scheduleRespIds, contextInfo);
    }

    @Override
    public List<String> getScheduleRespIdsByType(@WebParam(name = "scheduleRespTypeKey") String scheduleRespTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRespIdsByType(scheduleRespTypeKey, contextInfo);
    }

    @Override
    public List<ScheduleRespInfo> getScheduleRespsByScheduleRequestIds(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRespsByScheduleRequestIds(scheduleRequestIds, contextInfo);
    }

    @Override
    public List<ScheduleRespInfo> getScheduleRespsByScheduleBatchResp(@WebParam(name = "scheduleBatchRespId") String scheduleBatchRespId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getScheduleRespsByScheduleBatchResp(scheduleBatchRespId, contextInfo);
    }

    @Override
    public ScheduleRespInfo submitScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().submitScheduleBatch(scheduleBatchId, contextInfo);
    }

    @Override
    public StatusInfo commitSchedules(@WebParam(name = "scheduleBatchRespId") String scheduleBatchRespId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().commitSchedules(scheduleBatchRespId, contextInfo);
    }

    @Override
    public List<Integer> getValidDaysOfWeekByTimeSlotType(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getValidDaysOfWeekByTimeSlotType(timeSlotTypeKey, contextInfo);
    }
}
