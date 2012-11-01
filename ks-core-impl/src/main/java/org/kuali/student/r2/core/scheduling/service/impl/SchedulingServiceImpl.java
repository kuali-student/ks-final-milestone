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

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dao.ScheduleDao;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestDao;
import org.kuali.student.r2.core.scheduling.dao.TimeSlotDao;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.model.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.service.transformer.ScheduleDisplayTransformer;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Version 1.0
 *
 * Changes as of 23/8/2012 by Mezba - contract changes to SchedulingService (ScheduleTransaction crud operations,
 * returning infos and ids for ScheduleBatch-Transaction/Request) and checking for TimeSlot conflicts. The rest are
 * work from M4 by the PDTs (Venkat, Andy).
 */

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class SchedulingServiceImpl implements SchedulingService {
    private AtpService atpService;
    private RoomService roomService;
    private TypeService typeService;

    private ScheduleRequestDao scheduleRequestDao;
    private ScheduleDao scheduleDao;
    private TimeSlotDao timeSlotDao;
    private CriteriaLookupService criteriaLookupService;

    public AtpService getAtpService() {
        if(atpService == null){
            atpService = GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE,
                    AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public RoomService getRoomService() {
        if (roomService == null){
            roomService = GlobalResourceLoader.getService(new QName(RoomServiceConstants.NAMESPACE,
                    RoomServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public void setScheduleRequestDao(ScheduleRequestDao scheduleRequestDao) {
        this.scheduleRequestDao = scheduleRequestDao;
    }

    public void setTimeSlotDao(TimeSlotDao timeSlotDao) {
        this.timeSlotDao = timeSlotDao;
    }

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleInfo getSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleEntity scheduleEntity = scheduleDao.find(scheduleId);
        if (null == scheduleEntity) {
            throw new DoesNotExistException(scheduleId);
        }
        return scheduleEntity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleInfo> getSchedulesByIds(List<String> scheduleIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleEntity> scheduleEntityList = scheduleDao.findByIds(scheduleIds);
        List<ScheduleInfo> scheduleInfoList = new ArrayList<ScheduleInfo>();
        for (ScheduleEntity entity: scheduleEntityList) {
            ScheduleInfo scheduleInfo = entity.toDto();
            scheduleInfoList.add(scheduleInfo);
        }
        return scheduleInfoList;
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
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleInfo createSchedule(String scheduleTypeKey, ScheduleInfo scheduleInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!scheduleInfo.getTypeKey().equals(scheduleTypeKey)) {
            throw new InvalidParameterException(scheduleTypeKey + " does not match the type in the info object " + scheduleInfo.getTypeKey());
        }

        for(ScheduleComponentInfo sci: scheduleInfo.getScheduleComponents()){
            if(sci.getIsTBA()==null){
                sci.setIsTBA(false);
            }
        }

        ScheduleEntity scheduleEntity = new ScheduleEntity(scheduleInfo);
        scheduleEntity.setScheduleType(scheduleTypeKey); // a bit redundant (constructor handles this)
        scheduleEntity.setEntityCreated(contextInfo);

        scheduleDao.persist(scheduleEntity);
        return scheduleEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleInfo updateSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        ScheduleEntity scheduleEntity = scheduleDao.find(scheduleId);
        if (null != scheduleEntity) {
            scheduleEntity.fromDto(scheduleInfo);

            scheduleEntity.setEntityUpdated(contextInfo);

            scheduleDao.merge(scheduleEntity);
            return scheduleEntity.toDto();
        } else {
            throw new DoesNotExistException(scheduleId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        ScheduleEntity scheduleEntity = scheduleDao.find(scheduleId);
        if (null != scheduleEntity) {
            scheduleDao.remove(scheduleEntity);
        } else {
            throw new DoesNotExistException(scheduleId);
        }
        return status;
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<String> getScheduleRequestIdsByType(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByType(scheduleRequestTypeKey);

        return getScheduleRequestsIdList(entityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getScheduleRequestIdsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByRefObject(refObjectType, refObjectId);
        return getScheduleRequestsIdList(entityList);
    }

    @Override
    @Transactional(readOnly = true)
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
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleRequestInfo createScheduleRequest(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!scheduleRequestInfo.getTypeKey().equals(scheduleRequestTypeKey)) {
            throw new InvalidParameterException(scheduleRequestTypeKey + " does not match the type in the info object " + scheduleRequestInfo.getTypeKey());
        }

        ScheduleRequestEntity scheduleRequestEntity = new ScheduleRequestEntity(scheduleRequestInfo);
        scheduleRequestEntity.setSchedReqType(scheduleRequestTypeKey);
        scheduleRequestEntity.setEntityCreated(contextInfo);

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

        // Copy data from the DTO into the entity
        scheduleRequestEntity.fromDto(scheduleRequestInfo);

        //Update any Meta information
        scheduleRequestEntity.setEntityUpdated(contextInfo);

        scheduleRequestDao.merge(scheduleRequestEntity);
        return scheduleRequestEntity.toDto();

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
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

        // if the list of entities returned is not the same size as the list of given ids,
        // then at least one of the requested ids was not found, and an exception should be thrown
        if(timeSlotIds.size() != entities.size()) {
            throw new DoesNotExistException("Returned entities does not contain all requested ids");
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
        return new ArrayList<ValidationResultInfo>();
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
        if(timeSlotTypeKey.equals(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING)) {
            return SchedulingServiceConstants.TIME_SLOT_DAYS_OF_WEEK_ACTIVITY_OFFERING_TYPE;
        }
        else if (timeSlotTypeKey.equals(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM)) {
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
    public List<ValidationResultInfo> validateScheduleTransaction(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "scheduleBatchId") String scheduleBatchId, @WebParam(name = "scheduleTransactionTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleTransactionInfo") ScheduleRequestInfo scheduleTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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

    @Override
    @Transactional(readOnly = true)
    public ScheduleDisplayInfo getScheduleDisplay(String scheduleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleInfo scheduleInfo = getSchedule(scheduleId, contextInfo);
        ScheduleDisplayInfo scheduleDisplayInfo =
                ScheduleDisplayTransformer.schedule2scheduleDisplay(scheduleInfo, atpService, roomService, this, contextInfo);
        return scheduleDisplayInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDisplayInfo> getScheduleDisplaysByIds(List<String> scheduleIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        List<ScheduleInfo> scheduleInfoList = getSchedulesByIds(scheduleIds, contextInfo);
        List<ScheduleDisplayInfo> displayInfoList = new ArrayList<ScheduleDisplayInfo>();
        if (scheduleInfoList != null) {
            for (ScheduleInfo info: scheduleInfoList) {
                ScheduleDisplayInfo scheduleDisplayInfo =
                        ScheduleDisplayTransformer.schedule2scheduleDisplay(info, atpService, roomService, this, contextInfo);
                displayInfoList.add(scheduleDisplayInfo);
            }
        }
        return displayInfoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDisplayInfo> searchForScheduleDisplays(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<ScheduleEntity> results = criteriaLookupService.lookup(ScheduleEntity.class, criteria);
        List<ScheduleDisplayInfo> scheduleDisplayInfos = new ArrayList<ScheduleDisplayInfo>(results.getResults().size());
        if(results != null && results.getResults() != null){
            for(ScheduleEntity scheduleEntity : results.getResults()){
                try{
                    ScheduleDisplayInfo info = ScheduleDisplayTransformer.schedule2scheduleDisplay(scheduleEntity.toDto(), getAtpService(), getRoomService(), this, contextInfo);
                    scheduleDisplayInfos.add(info);
                }catch (Exception e){
                    throw new RuntimeException("Error Transforming Schedule to ScheduleDisplay", e);
                }
            }
        }

        return scheduleDisplayInfos;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleRequestDisplayInfo getScheduleRequestDisplay(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestInfo scheduleInfo = getScheduleRequest(scheduleRequestId, contextInfo);
        ScheduleRequestDisplayInfo scheduleDisplayInfo =
                ScheduleDisplayTransformer.scheduleRequestInfo2SceduleRequestDisplayInfo(scheduleInfo, getTypeService(), getRoomService(), this, contextInfo);
        return scheduleDisplayInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRequestDisplayInfo> getScheduleRequestDisplaysByIds(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestInfo> scheduleInfoList = getScheduleRequestsByIds(scheduleRequestIds, contextInfo);
        List<ScheduleRequestDisplayInfo> displayInfoList = new ArrayList<ScheduleRequestDisplayInfo>();
        if (scheduleInfoList != null) {
            for (ScheduleRequestInfo info: scheduleInfoList) {
                ScheduleRequestDisplayInfo scheduleDisplayInfo =
                        ScheduleDisplayTransformer.scheduleRequestInfo2SceduleRequestDisplayInfo(info, getTypeService(), getRoomService(), this, contextInfo);
                displayInfoList.add(scheduleDisplayInfo);
            }
        }
        return displayInfoList;
    }

    @Override
    public List<ScheduleRequestDisplayInfo> searchForScheduleRequestDisplays(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<ScheduleRequestEntity> results = criteriaLookupService.lookup(ScheduleRequestEntity.class, criteria);
        List<ScheduleRequestDisplayInfo> scheduleDisplayInfos = new ArrayList<ScheduleRequestDisplayInfo>(results.getResults().size());
        if(results != null && results.getResults() != null){
            ScheduleRequestInfo scheduleInfo;
            ScheduleRequestDisplayInfo scheduleDisplayInfo;
            for(ScheduleRequestEntity scheduleEntity : results.getResults()){
                try {
                    scheduleInfo = getScheduleRequest(scheduleEntity.getId(), contextInfo);
                } catch (DoesNotExistException e) {
                    continue;
                }
                try{
                    scheduleDisplayInfo = ScheduleDisplayTransformer.scheduleRequestInfo2SceduleRequestDisplayInfo(scheduleInfo, getTypeService(), getRoomService(), this, contextInfo);
                    scheduleDisplayInfos.add(scheduleDisplayInfo);
                }catch (Exception e){
                    throw new RuntimeException("Error Transforming Schedule to ScheduleDisplay", e);
                }
            }
        }

        return scheduleDisplayInfos;
    }
}
