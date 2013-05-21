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
import org.kuali.student.r2.common.entity.BaseEntity;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dao.ScheduleDao;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestDao;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestSetDao;
import org.kuali.student.r2.core.scheduling.dao.TimeSlotDao;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleTransactionInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.model.ScheduleEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestAttributeEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestComponentEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestSetEntity;
import org.kuali.student.r2.core.scheduling.model.TimeSlotEntity;
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

    private ScheduleRequestSetDao scheduleRequestSetDao;
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

    public ScheduleRequestSetDao getScheduleRequestSetDao() {
        return scheduleRequestSetDao;
    }

    public void setScheduleRequestSetDao(ScheduleRequestSetDao scheduleRequestSetDao) {
        this.scheduleRequestSetDao = scheduleRequestSetDao;
    }

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

    public ScheduleDao getScheduleDao() {
        return scheduleDao;
    }

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleInfo getSchedule(String scheduleId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<String> getScheduleIdsByType(String scheduleTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getIdList(scheduleDao.getSchedulesByType(scheduleTypeKey));
    }

    @Override
    public List<String> searchForScheduleIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleInfo> searchForSchedules(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateSchedule( String validationTypeKey, String scheduleTypeKey,  ScheduleInfo scheduleInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public ScheduleInfo updateSchedule(String scheduleId,  ScheduleInfo scheduleInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
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
    public StatusInfo deleteSchedule(String scheduleId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public ScheduleBatchInfo getScheduleBatch(String scheduleBatchId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleBatchInfo> getScheduleBatchesByIds(List<String> scheduleBatchIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getScheduleBatchIdsByType(String scheduleBatchTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForScheduleBatchIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleBatchInfo> searchForScheduleBatches(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateScheduleBatch( String validationTypeKey,  String scheduleBatchTypeKey, ScheduleBatchInfo scheduleBatchInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleBatchInfo createScheduleBatch( String scheduleBatchTypeKey, ScheduleBatchInfo scheduleBatchInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleBatchInfo updateScheduleBatch( String scheduleBatchId, ScheduleBatchInfo scheduleBatchInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteScheduleBatch(String scheduleBatchId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleRequestInfo getScheduleRequest(String scheduleRequestId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestEntity scheduleRequestEntity = scheduleRequestDao.find(scheduleRequestId);
        if (null == scheduleRequestEntity) {
            throw new DoesNotExistException(scheduleRequestId);
        }

        return scheduleRequestEntity.toDto();

    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByIds(List<String> scheduleRequestIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.findByIds(scheduleRequestIds);

        return getScheduleRequestInfoList(entityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getScheduleRequestIdsByType(String scheduleRequestTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByType(scheduleRequestTypeKey);

        return getIdList(entityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getScheduleRequestIdsByRefObject(String refObjectType, String refObjectId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByRefObject(refObjectType, refObjectId);
        return getIdList(entityList);
    }

    
    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObjects(
            String refObjectType,
            List<String> refObjectIds,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByRefObjects(refObjectType, refObjectIds);
        return getScheduleRequestInfoList(entityList);
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByScheduleRequestSet(@WebParam(name = "scheduleRequestSetId") String scheduleRequestSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObject(String refObjectType, String refObjectId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestEntity> entityList = scheduleRequestDao.getScheduleRequestsByRefObject(refObjectType, refObjectId);
        return getScheduleRequestInfoList(entityList);
    }



    @Override
    public List<String> searchForScheduleRequestIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<ScheduleRequestEntity> entities = criteriaLookupService.lookup(ScheduleRequestEntity.class, criteria);
        if (null != entities && entities.getResults().size() > 0) {
            for (ScheduleRequestEntity entity : entities.getResults()) {
                results.add(entity.getId());
            }
        }
        return results;
    }

    @Override
    public List<ScheduleRequestInfo> searchForScheduleRequests(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestInfo> results = new ArrayList<ScheduleRequestInfo>();
        GenericQueryResults<ScheduleRequestEntity> entities = criteriaLookupService.lookup(ScheduleRequestEntity.class, criteria);
        if (null != entities && entities.getResults().size() > 0) {
            for (ScheduleRequestEntity entity : entities.getResults()) {
                results.add(entity.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateScheduleRequest(String validationTypeKey, String scheduleRequestTypeKey,  ScheduleRequestInfo scheduleRequestInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleRequestInfo createScheduleRequest(String scheduleRequestTypeKey, ScheduleRequestInfo scheduleRequestInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
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
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleRequestInfo updateScheduleRequest(String scheduleRequestId,  ScheduleRequestInfo scheduleRequestInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
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
    public StatusInfo deleteScheduleRequest(String scheduleRequestId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public TimeSlotInfo getTimeSlot(String timeSlotId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        TimeSlotEntity retrieved = timeSlotDao.find(timeSlotId);

        if(retrieved == null) {
            throw new DoesNotExistException(timeSlotId);
        }

        return retrieved.toDto();
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByIds(List<String> timeSlotIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<String> getTimeSlotIdsByType(String timeSlotTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TimeSlotEntity> entities = timeSlotDao.getByTimeSlotType(timeSlotTypeKey);

        List<String> ids = new ArrayList<String>(entities.size());

        for(TimeSlotEntity e : entities) {
            ids.add(e.getId());
        }

        return ids;
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTime( String timeSlotTypeKey, List<Integer> daysOfWeek, TimeOfDayInfo startTime,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

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
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTimeAndEndTime( String timeSlotTypeKey,  List<Integer> daysOfWeek,  TimeOfDayInfo startTime,  TimeOfDayInfo endTime,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<String> searchForTimeSlotIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TimeSlotInfo> searchForTimeSlots(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateTimeSlot( String validationTypeKey, String timeSlotTypeKey,  TimeSlotInfo timeSlotInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public TimeSlotInfo createTimeSlot(String timeSlotTypeKey,  TimeSlotInfo timeSlotInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        TimeSlotEntity entity = new TimeSlotEntity(timeSlotInfo);

        entity.setTimeSlotType(timeSlotTypeKey);
        entity.setEntityCreated(contextInfo);

        timeSlotDao.persist(entity);

        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public TimeSlotInfo updateTimeSlot(String timeSlotId,  TimeSlotInfo timeSlotInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if(!timeSlotInfo.getId().equals(timeSlotId)){
            throw new InvalidParameterException(timeSlotId +  "does not match the id in the object " + timeSlotInfo.getId());
        }

        TimeSlotEntity entity = timeSlotDao.find(timeSlotId);
        if (null == entity) {
            throw new DoesNotExistException(timeSlotId);
        }

        // Copy data from the DTO into the entity
        entity.fromDto(timeSlotInfo);

        //Update any Meta information
        entity.setEntityUpdated(contextInfo);

        timeSlotDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteTimeSlot(String timeSlotId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TimeSlotEntity entity = timeSlotDao.find(timeSlotId);
        if (null == entity) {
            throw new DoesNotExistException(timeSlotId);
        }

        timeSlotDao.remove(entity);

        return new StatusInfo();
    }

    @Override
    public List<Integer> getValidDaysOfWeekByTimeSlotType(String timeSlotTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<ScheduleBatchInfo> getScheduleBatchesForScheduleTransaction(String scheduleTransactionId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduleTransactionInfo getScheduleTransaction(String scheduleTransactionId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByIds(List<String> scheduleTransactionIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getScheduleTransactionIdsByType(String scheduleTransactionTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getScheduleTransactionIdsByRefObject(String refObjectType, String refObjectId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByRefObject(String refObjectType, String refObjectId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsForScheduleBatch( String scheduleBatchId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForScheduleTransactionIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScheduleTransactionInfo> searchForScheduleTransactions(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateScheduleTransaction( String validationTypeKey,  String scheduleBatchId, String scheduleRequestTypeKey,  ScheduleRequestInfo scheduleTransactionInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleTransactionInfo createScheduleTransaction( String scheduleBatchId, String scheduleTransactionTypeKey,  ScheduleTransactionInfo scheduleTransactionInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleTransactionInfo updateScheduleTransaction( String scheduleTransactionId,  ScheduleTransactionInfo scheduleTransactionInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteScheduleTransaction(String scheduleTransactionId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo submitScheduleBatch( String scheduleBatchId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo commitSchedules( String scheduleBatchId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean areTimeSlotsInConflict( String timeSlot1Id, String timeSlot2Id,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<ScheduleDisplayInfo> searchForScheduleDisplays(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public ScheduleRequestDisplayInfo getScheduleRequestDisplay(String scheduleRequestId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestInfo scheduleInfo = getScheduleRequest(scheduleRequestId, contextInfo);
        ScheduleRequestDisplayInfo scheduleDisplayInfo =
                ScheduleDisplayTransformer.scheduleRequestInfo2SceduleRequestDisplayInfo(scheduleInfo, getTypeService(), getRoomService(), this, contextInfo);
        return scheduleDisplayInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRequestDisplayInfo> getScheduleRequestDisplaysByIds(List<String> scheduleRequestIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<ScheduleRequestDisplayInfo> searchForScheduleRequestDisplays(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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

    @Override
    public ScheduleRequestSetInfo getScheduleRequestSet(String scheduleRequestSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestSetEntity scheduleRequestSetEntity = scheduleRequestSetDao.find(scheduleRequestSetId);

        if (null == scheduleRequestSetEntity) {
            throw new DoesNotExistException(scheduleRequestSetId);
        }

        return scheduleRequestSetEntity.toDto();
    }

    @Override
    public List<ScheduleRequestSetInfo> getScheduleRequestSetsByIds(List<String> scheduleRequestSetIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if(scheduleRequestSetIds.size() == 0) {
            return getScheduleRequestSetInfoList(null);
        }
        return getScheduleRequestSetInfoList(scheduleRequestSetDao.findByIds(scheduleRequestSetIds));
    }

    @Override
    public List<String> getScheduleRequestSetIdsByType(String scheduleRequestSetTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getIdList(scheduleRequestSetDao.getScheduleRequestSetsByType(scheduleRequestSetTypeKey));
    }

    @Override
    public List<String> getScheduleRequestSetIdsByRefObjType(String refObjectTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return scheduleRequestSetDao.getScheduleRequestSetIdsByRefObjType(refObjectTypeKey);
    }

    @Override
    public List<String> searchForScheduleRequestSetIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<ScheduleRequestSetEntity> entities = criteriaLookupService.lookup(ScheduleRequestSetEntity.class, criteria);
        if (null != entities && entities.getResults().size() > 0) {
            for (ScheduleRequestSetEntity entity : entities.getResults()) {
                results.add(entity.getId());
            }
        }
        return results;
    }

    @Override
    public List<ScheduleRequestSetInfo> searchForScheduleRequestSets(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestSetInfo> results = new ArrayList<ScheduleRequestSetInfo>();
        GenericQueryResults<ScheduleRequestSetEntity> entities = criteriaLookupService.lookup(ScheduleRequestSetEntity.class, criteria);
        if (null != entities && entities.getResults().size() > 0) {
            for (ScheduleRequestSetEntity entity : entities.getResults()) {
                results.add(entity.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateScheduleRequestSet(String validationTypeKey, String scheduleRequestSetTypeKey, String refObjectTypeKey, ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleRequestSetInfo createScheduleRequestSet(String scheduleRequestSetTypeKey, String refObjectTypeKey, ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!scheduleRequestSetTypeKey.equals(scheduleRequestSetInfo.getTypeKey())) {
            throw new InvalidParameterException (scheduleRequestSetTypeKey + " does not match the type on the info object " + scheduleRequestSetInfo.getTypeKey());
        }
        if (!refObjectTypeKey.equals(scheduleRequestSetInfo.getRefObjectTypeKey())) {
            throw new InvalidParameterException (refObjectTypeKey + " does not match the ref object type on the info object " + scheduleRequestSetInfo.getRefObjectTypeKey());
        }

        ScheduleRequestSetEntity entity = new ScheduleRequestSetEntity(scheduleRequestSetInfo);
        entity.setEntityCreated(contextInfo);

        scheduleRequestSetDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ScheduleRequestSetInfo updateScheduleRequestSet(String scheduleRequestSetId, ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if(!scheduleRequestSetInfo.getId().equals(scheduleRequestSetId)){
            throw new InvalidParameterException(scheduleRequestSetId +  "does not match the id in the object " + scheduleRequestSetInfo.getId());
        }

        ScheduleRequestSetEntity entity = scheduleRequestSetDao.find(scheduleRequestSetId);
        if (null == entity) {
            throw new DoesNotExistException(scheduleRequestSetId);
        }

        // Copy data from the DTO into the entity
        entity.fromDto(scheduleRequestSetInfo);

        //Update any Meta information
        entity.setEntityUpdated(contextInfo);

        scheduleRequestSetDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteScheduleRequestSet(String scheduleRequestSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestSetEntity entity = scheduleRequestSetDao.find(scheduleRequestSetId);
        if (null == entity) {
            throw new DoesNotExistException(scheduleRequestSetId);
        }

        scheduleRequestSetDao.remove(entity);

        return new StatusInfo();
    }

    @Override
    public List<ScheduleRequestSetInfo> getScheduleRequestSetsByRefObject(String refObjectType, String refObjectId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getScheduleRequestSetInfoList(scheduleRequestSetDao.getScheduleRequestSetsByRefObject(refObjectType, refObjectId));
    }

    private <T extends BaseEntity> List<String> getIdList(List<T> entityList){
        List<String> idList = new ArrayList<String>();
        if(entityList != null) {
            for(T entity : entityList){
                idList.add(entity.getId());
            }
        }

        return idList;
    }

    private List<ScheduleRequestSetInfo> getScheduleRequestSetInfoList(List<ScheduleRequestSetEntity> entityList){
        List<ScheduleRequestSetInfo> infoList = new ArrayList<ScheduleRequestSetInfo>();
        if(entityList != null) {
            for(ScheduleRequestSetEntity entity : entityList){
                infoList.add(entity.toDto());
            }
        }

        return infoList;
    }

    private List<ScheduleRequestInfo> getScheduleRequestInfoList(List<ScheduleRequestEntity> entityList){
        List<ScheduleRequestInfo> infoList = new ArrayList<ScheduleRequestInfo>();
        if(entityList != null) {
            for(ScheduleRequestEntity scheduleRequestEntity : entityList){
                infoList.add(scheduleRequestEntity.toDto());
            }
        }

        return infoList;
    }

}
