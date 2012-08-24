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

package org.kuali.student.r2.core.scheduling.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestDao;
import org.kuali.student.r2.core.scheduling.dao.TimeSlotDao;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleTransactionInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestAttributeEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestComponentEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestEntity;
import org.kuali.student.r2.core.scheduling.model.TimeSlotEntity;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Version 1.0
 * @Author Mezba Mahtab
 *
 * Changes as of 23/8/2012 by Mezba - contract changes to SchedulingService (ScheduleTransaction crud operations,
 * returning infos and ids for ScheduleBatch-Transaction/Request) and checking for TimeSlot conflicts.
 */

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class SchedulingServiceImpl implements SchedulingService {
    private ScheduleRequestDao scheduleRequestDao;
    private TimeSlotDao timeSlotDao;

    public ScheduleRequestDao getScheduleRequestDao() {
        return scheduleRequestDao;
    }

    public void setScheduleRequestDao(ScheduleRequestDao scheduleRequestDao) {
        this.scheduleRequestDao = scheduleRequestDao;
    }

    public TimeSlotDao getTimeSlotDao() {
        return timeSlotDao;
    }

    public void setTimeSlotDao(TimeSlotDao timeSlotDao) {
        this.timeSlotDao = timeSlotDao;
    }

    @Override
    public ScheduleInfo getSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleInfo> getSchedulesByIds(@WebParam(name = "scheduleIds") List<String> scheduleIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getScheduleIdsByType(@WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForScheduleIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleInfo> searchForSchedules(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateSchedule(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleInfo createSchedule(@WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleInfo updateSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo deleteSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleBatchInfo getScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleBatchInfo> getScheduleBatchesByIds(@WebParam(name = "scheduleBatchIds") List<String> scheduleBatchIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getScheduleBatchIdsByType(@WebParam(name = "scheduleBatchTypeKey") String scheduleBatchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForScheduleBatchIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleBatchInfo> searchForScheduleBatches(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateScheduleBatch(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleBatchTypeKey") String scheduleBatchTypeKey, @WebParam(name = "scheduleBatchInfo") ScheduleBatchInfo scheduleBatchInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleBatchInfo createScheduleBatch(@WebParam(name = "scheduleBatchTypeKey") String scheduleBatchTypeKey, @WebParam(name = "scheduleBatchInfo") ScheduleBatchInfo scheduleBatchInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleBatchInfo updateScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "scheduleBatchInfo") ScheduleBatchInfo scheduleBatchInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo deleteScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleRequestInfo getScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestEntity scheduleRequestEntity = scheduleRequestDao.find(scheduleRequestId);
        if (null == scheduleRequestEntity) {
            throw new DoesNotExistException(scheduleRequestId);
        }

        return scheduleRequestEntity.toDto();

    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByIds(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.findByIds(scheduleRequestIds);

        return getScheduleRequestsInfoList(entityList);
    }

    @Override
    public List<String> getScheduleRequestIdsByType(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByType(scheduleRequestTypeKey);

        return getScheduleRequestsIdList(entityList);
    }

    @Override
    public List<String> getScheduleRequestIdsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByRefObject(refObjectType, refObjectId);
        return getScheduleRequestsIdList(entityList);
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByRefObject(refObjectType, refObjectId);
        return getScheduleRequestsInfoList(entityList);
    }

    private List<String> getScheduleRequestsIdList(List<ScheduleRequestEntity> entityList){
        List<String> idList = new ArrayList<String>();
        for(ScheduleRequestEntity scheduleRequestEntity : entityList){
            idList.add(scheduleRequestEntity.getId());
        }

        return idList;
    }

    private List<ScheduleRequestInfo> getScheduleRequestsInfoList(List<ScheduleRequestEntity> entityList){
        List<ScheduleRequestInfo> infoList = new ArrayList<ScheduleRequestInfo>();
        for(ScheduleRequestEntity scheduleRequestEntity : entityList){
            infoList.add(scheduleRequestEntity.toDto());
        }

        return infoList;
    }

    @Override
    public List<String> searchForScheduleRequestIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleRequestInfo> searchForScheduleRequests(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateScheduleRequest(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false)
    public ScheduleRequestInfo createScheduleRequest(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!scheduleRequestInfo.getTypeKey().equals(scheduleRequestTypeKey)) {
            throw new InvalidParameterException(scheduleRequestTypeKey + " does not match the type in the info object " + scheduleRequestInfo.getTypeKey());
        }

        ScheduleRequestEntity scheduleRequestEntity = new ScheduleRequestEntity(scheduleRequestInfo);
        scheduleRequestEntity.setSchedReqType(scheduleRequestTypeKey);
        scheduleRequestEntity.setEntityCreated(contextInfo);

        if(scheduleRequestEntity.getScheduleRequestComponents() != null){
            for(ScheduleRequestComponentEntity srComp : scheduleRequestEntity.getScheduleRequestComponents()){
                srComp.setCreateId(contextInfo.getPrincipalId());
                srComp.setCreateTime(contextInfo.getCurrentDate());
                srComp.setUpdateId(contextInfo.getPrincipalId());
                srComp.setUpdateTime(contextInfo.getCurrentDate());
            }
        }

        scheduleRequestDao.persist(scheduleRequestEntity);
        return scheduleRequestEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false)
    public ScheduleRequestInfo updateScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if(!scheduleRequestInfo.getId().equals(scheduleRequestId)){
            throw new InvalidParameterException(scheduleRequestId +  "does not match the id in the object " + scheduleRequestInfo.getId());
        }

        ScheduleRequestEntity scheduleRequestEntity = scheduleRequestDao.find(scheduleRequestId);
        if (null == scheduleRequestEntity) {
            throw new DoesNotExistException(scheduleRequestId);
        }

        //Transform the DTO to the entity
        List<Object> orphans = scheduleRequestEntity.fromDto(scheduleRequestInfo);

        //Delete any orphaned children
        for(Object orphan : orphans){
            scheduleRequestDao.getEm().remove(orphan);
        }

        //Update any Meta information
        scheduleRequestEntity.setEntityUpdated(contextInfo);

        if(scheduleRequestEntity.getScheduleRequestComponents() != null){
            for(ScheduleRequestComponentEntity srComp : scheduleRequestEntity.getScheduleRequestComponents()){
                if(srComp.getCreateId() == null){
                    srComp.setCreateId(contextInfo.getPrincipalId());
                }
                if(srComp.getCreateTime() == null){
                    srComp.setCreateTime(contextInfo.getCurrentDate());
                }
                srComp.setUpdateId(contextInfo.getPrincipalId());
                srComp.setUpdateTime(contextInfo.getCurrentDate());
            }
        }
        scheduleRequestDao.merge(scheduleRequestEntity);
        return scheduleRequestEntity.toDto();

    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        ScheduleRequestEntity scheduleRequestEntity = scheduleRequestDao.find(scheduleRequestId);
        if (null == scheduleRequestEntity) {
            throw new DoesNotExistException(scheduleRequestId);
        }

        //Delete attributes
        if(scheduleRequestEntity.getAttributes() != null){
            for(ScheduleRequestAttributeEntity attr : scheduleRequestEntity.getAttributes()){
                scheduleRequestDao.getEm().remove(attr);
            }
        }

        //Delete scheduleRequestComponents
        if(scheduleRequestEntity.getScheduleRequestComponents() != null){
            for(ScheduleRequestComponentEntity srComp : scheduleRequestEntity.getScheduleRequestComponents()){
                scheduleRequestDao.getEm().remove(srComp);
            }
        }

        scheduleRequestDao.remove(scheduleRequestEntity);

        return status;
    }

    @Override
    public TimeSlotInfo getTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        TimeSlotEntity retrieved = timeSlotDao.find(timeSlotId);

        if(retrieved == null) {
            throw new DoesNotExistException(timeSlotId);
        }

        return retrieved.toDto();
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByIds(@WebParam(name = "timeSlotIds") List<String> timeSlotIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TimeSlotEntity> entities = timeSlotDao.findByIds(timeSlotIds);

        if(entities == null) {
            throw new DoesNotExistException();
        }

        List<TimeSlotInfo> results = new ArrayList<TimeSlotInfo>(entities.size());

        for (TimeSlotEntity entity : entities) {
            if(entity == null) {
                // if one of the entities from "findByIds" is returned as null,
                // then one of the keys in the list was not found
                throw new DoesNotExistException();
            }

            results.add(entity.toDto());
        }

        return results;
    }

    @Override
    public List<String> getTimeSlotIdsByType(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TimeSlotEntity> entities = timeSlotDao.getByTimeSlotType(timeSlotTypeKey);

        List<String> ids = new ArrayList<String>(entities.size());

        for(TimeSlotEntity e : entities) {
            ids.add(e.getId());
        }

        return ids;
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTime(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "daysOfWeek") List<Integer> daysOfWeek, @WebParam(name = "startTime") TimeOfDayInfo startTime, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // Check that no invalid days of week are passed in
        List<Integer> temp = new ArrayList<Integer>(daysOfWeek);

        temp.removeAll(getValidDaysOfWeekByTimeSlotType(timeSlotTypeKey, contextInfo));

        // if there are any integers left after removing all the valid ones for the type, then there are invalid entries
        if(!temp.isEmpty()) {
            throw new InvalidParameterException("Invalid entries found in daysOfWeek parameter for type " + timeSlotTypeKey + " - " + temp.toString());
        }

        // Build the string version of the days
        String weekdays = SchedulingServiceUtil.weekdaysList2WeekdaysString(daysOfWeek);

        List<TimeSlotEntity> entities = timeSlotDao.getByTimeSlotTypeWeekdaysAndStartTime(timeSlotTypeKey, weekdays, startTime.getMilliSeconds());

        List<TimeSlotInfo> results = new ArrayList<TimeSlotInfo>(entities.size());

        for (TimeSlotEntity entity : entities) {
            results.add(entity.toDto());
        }

        return results;
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTimeAndEndTime(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "daysOfWeek") List<Integer> daysOfWeek, @WebParam(name = "startTime") TimeOfDayInfo startTime, @WebParam(name = "endTime") TimeOfDayInfo endTime, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Check that no invalid days of week are passed in
        List<Integer> temp = new ArrayList<Integer>(daysOfWeek);

        temp.removeAll(getValidDaysOfWeekByTimeSlotType(timeSlotTypeKey, contextInfo));

        // if there are any integers left after removing all the valid ones for the type, then there are invalid entries
        if(!temp.isEmpty()) {
            throw new InvalidParameterException("Invalid entries found in daysOfWeek parameter for type " + timeSlotTypeKey + " - " + temp.toString());
        }

        // Build the string version of the days
        String weekdays = SchedulingServiceUtil.weekdaysList2WeekdaysString(daysOfWeek);

        List<TimeSlotEntity> entities = timeSlotDao.getByTimeSlotTypeWeekdaysStartTimeAndEndTime(timeSlotTypeKey, weekdays, startTime.getMilliSeconds(), endTime.getMilliSeconds());

        List<TimeSlotInfo> results = new ArrayList<TimeSlotInfo>(entities.size());

        for (TimeSlotEntity entity : entities) {
            results.add(entity.toDto());
        }

        return results;
    }

    @Override
    public List<String> searchForTimeSlotIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TimeSlotInfo> searchForTimeSlots(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateTimeSlot(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false)
    public TimeSlotInfo createTimeSlot(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        TimeSlotEntity entity = new TimeSlotEntity(timeSlotInfo);

        entity.setTimeSlotType(timeSlotTypeKey);
        entity.setEntityCreated(contextInfo);

        timeSlotDao.persist(entity);

        return entity.toDto();
    }

    @Override
    public TimeSlotInfo updateTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo deleteTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Integer> getValidDaysOfWeekByTimeSlotType(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if(timeSlotTypeKey.equals(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY)) {
            return SchedulingServiceConstants.TIME_SLOT_DAYS_OF_WEEK_ACTIVITY_OFFERING_TYPE;
        }
        else if (timeSlotTypeKey.equals(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM_KEY)) {
            return Collections.emptyList();
        }
        else {
            throw new InvalidParameterException("No defined valid days of week for type: " + timeSlotTypeKey);
        }
    }

    @Override
    public List<ScheduleBatchInfo> getScheduleBatchesForScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleTransactionInfo getScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByIds(@WebParam(name = "scheduleTransactionIds") List<String> scheduleTransactionIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getScheduleTransactionIdsByType(@WebParam(name = "scheduleTransactionTypeKey") String scheduleTransactionTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getScheduleTransactionIdsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsForScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForScheduleTransactionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> searchForScheduleTransactions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateScheduleTransaction(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleTransactionTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleTransactionInfo") ScheduleRequestInfo scheduleTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleTransactionInfo createScheduleTransaction(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "scheduleTransactionTypeKey") String scheduleTransactionTypeKey, @WebParam(name = "scheduleTransactionInfo") ScheduleTransactionInfo scheduleTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleTransactionInfo updateScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "scheduleTransactionInfo") ScheduleTransactionInfo scheduleTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo deleteScheduleTransaction(@WebParam(name = "scheduleTransactionId") String scheduleTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo submitScheduleBatch(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo commitSchedules(@WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean areTimeSlotsInConflict(@WebParam(name = "timeSlot1Id") String timeSlot1Id, @WebParam(name = "timeSlot2Id") String timeSlot2Id, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TimeSlotInfo timeSlotInfo1 = getTimeSlot(timeSlot1Id, contextInfo);
        TimeSlotInfo timeSlotInfo2 = getTimeSlot(timeSlot2Id, contextInfo);
        return SchedulingServiceUtil.areTimeSlotsInConflict(timeSlotInfo1, timeSlotInfo2);
    }
}
