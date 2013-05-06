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
 * Created by Mezba Mahtab on 8/21/12
 */
package org.kuali.student.r2.core.scheduling.service.impl;

/**
 * This class represents a mock implementation of Scheduling Service.
 *
 * @author Mezba Mahtab
 */

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.service.transformer.ScheduleDisplayTransformer;
import org.kuali.student.r2.core.scheduling.util.CriteriaMatcherInMemory;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class SchedulingServiceMockImpl implements SchedulingService, MockService {

    ////////////////////////////////
    // DATA VARIABLES
    ////////////////////////////////

    // The LinkedHashMap is just so the values come back in a predictable order
    // The Maps to hold the mock impl data
    private Map<String, ScheduleInfo> scheduleMap = new LinkedHashMap<String, ScheduleInfo>();
    private Map<String, ScheduleBatchInfo> scheduleBatchMap = new LinkedHashMap<String, ScheduleBatchInfo>();
    private Map<String, ScheduleRequestSetInfo> scheduleRequestSetMap = new LinkedHashMap<String, ScheduleRequestSetInfo>();
    private Map<String, ScheduleRequestInfo> scheduleRequestMap = new LinkedHashMap<String, ScheduleRequestInfo>();
    private Map<String, TimeSlotInfo> timeSlotMap = new LinkedHashMap<String, TimeSlotInfo>();
    private Map<String, ScheduleTransactionInfo> scheduleTransactionMap = new LinkedHashMap<String, ScheduleTransactionInfo>();

    // other services as needed
    @Resource
    private AtpService atpService;
    @Resource
    private RoomService roomService;
    @Resource
    private TypeService typeService;
    private OrganizationService organizationService;

    ////////////////////////////////
    // ACCESSORS AND MODIFIERS
    ////////////////////////////////

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    ////////////////////////////////
    // IMPLEMENTING METHODS
    ////////////////////////////////

    @Override
    public void clear() {
        this.scheduleBatchMap.clear();
        this.scheduleMap.clear();
        this.scheduleRequestSetMap.clear();
        this.scheduleRequestMap.clear();
        this.scheduleTransactionMap.clear();
        this.timeSlotMap.clear();
    }

    @Override
    public ScheduleInfo getSchedule(String scheduleId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.scheduleMap.containsKey(scheduleId)) {
            throw new DoesNotExistException(scheduleId);
        }
        return new ScheduleInfo(this.scheduleMap.get (scheduleId));
    }

	@Override
    public List<ScheduleInfo> getSchedulesByIds(List<String> scheduleIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ScheduleInfo> list = new ArrayList<ScheduleInfo>();
        for (String id: scheduleIds) {
            list.add (this.getSchedule(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getScheduleIdsByType(String scheduleTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (ScheduleInfo info: scheduleMap.values ()) {
            if (scheduleTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForScheduleIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        return searchForInfoIds(criteria, scheduleMap.values());
    }

    @Override
    public List<ScheduleInfo> searchForSchedules(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        Collection<ScheduleInfo> matches = searchForInfoObjects(criteria, scheduleMap.values());

        List<ScheduleInfo> matchList = new ArrayList<ScheduleInfo>();
        for(ScheduleInfo info : matches) {
            matchList.add(new ScheduleInfo(info));
        }

        return matchList;
    }

    @Override
    public List<ValidationResultInfo> validateSchedule(String validationTypeKey, String scheduleTypeKey, ScheduleInfo scheduleInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
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
        if (!scheduleTypeKey.equals (scheduleInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        ScheduleInfo copy = new ScheduleInfo(scheduleInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        scheduleMap.put(copy.getId(), copy);
        return new ScheduleInfo(copy);
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
        if (!scheduleId.equals (scheduleInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ScheduleInfo copy = new ScheduleInfo(scheduleInfo);
        ScheduleInfo old = this.getSchedule(scheduleInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.scheduleMap .put(scheduleInfo.getId(), copy);
        return new ScheduleInfo(copy);
    }

    @Override
    public StatusInfo deleteSchedule(String scheduleId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.scheduleMap.remove(scheduleId) == null) {
            throw new DoesNotExistException(scheduleId);
        }
        return newStatus();
    }

    @Override
    public ScheduleBatchInfo getScheduleBatch(String scheduleBatchId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.scheduleBatchMap.containsKey(scheduleBatchId)) {
            throw new DoesNotExistException(scheduleBatchId);
        }
        return new ScheduleBatchInfo(this.scheduleBatchMap.get (scheduleBatchId));
    }

    @Override
    public List<ScheduleBatchInfo> getScheduleBatchesByIds(List<String> scheduleBatchIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ScheduleBatchInfo> list = new ArrayList<ScheduleBatchInfo> ();
        for (String id: scheduleBatchIds) {
            list.add (this.getScheduleBatch(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getScheduleBatchIdsByType(String scheduleBatchTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (ScheduleBatchInfo info: scheduleBatchMap.values ()) {
            if (scheduleBatchTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForScheduleBatchIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        return searchForInfoIds(criteria, scheduleBatchMap.values());
    }

    @Override
    public List<ScheduleBatchInfo> searchForScheduleBatches(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        Collection<ScheduleBatchInfo> matches = searchForInfoObjects(criteria, scheduleBatchMap.values());

        List<ScheduleBatchInfo> matchList = new ArrayList<ScheduleBatchInfo>();
        for(ScheduleBatchInfo info : matches) {
            matchList.add(new ScheduleBatchInfo(info));
        }

        return matchList;
    }

    @Override
    public List<ValidationResultInfo> validateScheduleBatch(String validationTypeKey, String scheduleBatchTypeKey, ScheduleBatchInfo scheduleBatchInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
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
        if (!scheduleBatchTypeKey.equals (scheduleBatchInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        ScheduleBatchInfo copy = new ScheduleBatchInfo(scheduleBatchInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        scheduleBatchMap.put(copy.getId(), copy);
        return new ScheduleBatchInfo(copy);
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
        if (!scheduleBatchId.equals (scheduleBatchInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ScheduleBatchInfo copy = new ScheduleBatchInfo(scheduleBatchInfo);
        ScheduleBatchInfo old = this.getScheduleBatch(scheduleBatchInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.scheduleBatchMap .put(scheduleBatchInfo.getId(), copy);
        return new ScheduleBatchInfo(copy);
    }

    @Override
    public StatusInfo deleteScheduleBatch(String scheduleBatchId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.scheduleBatchMap.remove(scheduleBatchId) == null) {
            throw new DoesNotExistException(scheduleBatchId);
        }
        return newStatus();
    }

    @Override
    public ScheduleRequestInfo getScheduleRequest(String scheduleRequestId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.scheduleRequestMap.containsKey(scheduleRequestId)) {
            throw new DoesNotExistException(scheduleRequestId);
        }
        return new ScheduleRequestInfo(this.scheduleRequestMap.get (scheduleRequestId));
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByIds(List<String> scheduleRequestIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ScheduleRequestInfo> list = new ArrayList<ScheduleRequestInfo> ();
        for (String id: scheduleRequestIds) {
            list.add (this.getScheduleRequest(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getScheduleRequestIdsByType(String scheduleRequestTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (ScheduleRequestInfo info: scheduleRequestMap.values ()) {
            if (scheduleRequestTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> getScheduleRequestIdsByRefObject(String refObjectType, String refObjectId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        // go through the list of schedule requests and add those to list that
        // have the specified refObjectType and refObjectId
        for (ScheduleRequestInfo info: scheduleRequestMap.values()) {
// TODOSSR           if (refObjectType.equals(info.getRefObjectTypeKey())
//                && refObjectId.equals(info.getRefObjectId())) {
//                list.add(info.getId());
//            }
        }
        return list;
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObject(String refObjectType, String refObjectId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        try { return getScheduleRequestsByIds(getScheduleRequestIdsByRefObject(refObjectType, refObjectId, contextInfo), contextInfo); }
        catch (DoesNotExistException e) {
            return new ArrayList<ScheduleRequestInfo> ();
        }
    }
    
    

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObjects(
            String refObjectType,
            List<String> refObjectIds,
            ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        List<ScheduleRequestInfo>requests = new ArrayList<ScheduleRequestInfo>();
        
        for (String refObjectId : refObjectIds) {
            
            requests.addAll(getScheduleRequestsByRefObject(refObjectType, refObjectId, contextInfo));
        }
        
        return requests;
        
    }

    @Override
    public List<String> searchForScheduleRequestIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        return searchForInfoIds(criteria, scheduleRequestMap.values());
    }

    @Override
    public List<ScheduleRequestInfo> searchForScheduleRequests(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        Collection<ScheduleRequestInfo> matches = searchForInfoObjects(criteria, scheduleRequestMap.values());

        List<ScheduleRequestInfo> matchList = new ArrayList<ScheduleRequestInfo>();
        for(ScheduleRequestInfo info : matches) {
            matchList.add(new ScheduleRequestInfo(info));
        }

        return matchList;
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
        return new ArrayList<ValidationResultInfo> ();
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
        if (!scheduleRequestTypeKey.equals (scheduleRequestInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        if(scheduleRequestInfo.getScheduleRequestSetId() == null || !scheduleRequestSetMap.containsKey(scheduleRequestInfo.getScheduleRequestSetId())) {
            throw new InvalidParameterException ("The schedule request set id given is not valid");
        }
        ScheduleRequestInfo copy = new ScheduleRequestInfo(scheduleRequestInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        scheduleRequestMap.put(copy.getId(), copy);
        return new ScheduleRequestInfo(copy);
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
        if (!scheduleRequestId.equals (scheduleRequestInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ScheduleRequestInfo copy = new ScheduleRequestInfo(scheduleRequestInfo);
        ScheduleRequestInfo old = this.getScheduleRequest(scheduleRequestInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.scheduleRequestMap .put(scheduleRequestInfo.getId(), copy);
        return new ScheduleRequestInfo(copy);
    }

    @Override
    public StatusInfo deleteScheduleRequest(String scheduleRequestId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.scheduleRequestMap.remove(scheduleRequestId) == null) {
            throw new DoesNotExistException(scheduleRequestId);
        }
        return newStatus();
    }

    @Override
    public TimeSlotInfo getTimeSlot(String timeSlotId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.timeSlotMap.containsKey(timeSlotId)) {
            throw new DoesNotExistException(timeSlotId);
        }
        return new TimeSlotInfo(this.timeSlotMap.get (timeSlotId));
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByIds(List<String> timeSlotIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<TimeSlotInfo> list = new ArrayList<TimeSlotInfo> ();
        for (String id: timeSlotIds) {
            list.add (this.getTimeSlot(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getTimeSlotIdsByType(String timeSlotTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (TimeSlotInfo info: timeSlotMap.values ()) {
            if (timeSlotTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTime(String timeSlotTypeKey, List<Integer> daysOfWeek, TimeOfDayInfo startTime, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<TimeSlotInfo> list = new ArrayList<TimeSlotInfo> ();
        for (TimeSlotInfo info: timeSlotMap.values ()) {
            if (timeSlotTypeKey.equals(info.getTypeKey())) {
                if (daysOfWeek.equals(info.getWeekdays())) {
                    if (startTime.equals(info.getStartTime())) {
                        list.add (info);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByDaysAndStartTimeAndEndTime(String timeSlotTypeKey, List<Integer> daysOfWeek, TimeOfDayInfo startTime, TimeOfDayInfo endTime, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<TimeSlotInfo> list = new ArrayList<TimeSlotInfo> ();
        for (TimeSlotInfo info: timeSlotMap.values ()) {
            if (timeSlotTypeKey.equals(info.getTypeKey())) {
                if (daysOfWeek.equals(info.getWeekdays())) {
                    if (startTime.equals(info.getStartTime())) {
                        if (endTime.equals(info.getEndTime())) {
                            list.add (info);
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForTimeSlotIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        return searchForInfoIds(criteria, timeSlotMap.values());
    }

    @Override
    public List<TimeSlotInfo> searchForTimeSlots(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        Collection<TimeSlotInfo> matches = searchForInfoObjects(criteria, timeSlotMap.values());

        List<TimeSlotInfo> matchList = new ArrayList<TimeSlotInfo>();
        for(TimeSlotInfo info : matches) {
            matchList.add(new TimeSlotInfo(info));
        }

        return matchList;
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
        return new ArrayList<ValidationResultInfo> ();
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
        if (!timeSlotTypeKey.equals (timeSlotInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        TimeSlotInfo copy = new TimeSlotInfo(timeSlotInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        timeSlotMap.put(copy.getId(), copy);
        return new TimeSlotInfo(copy);
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
        if (!timeSlotId.equals (timeSlotInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        TimeSlotInfo copy = new TimeSlotInfo(timeSlotInfo);
        TimeSlotInfo old = this.getTimeSlot(timeSlotInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.timeSlotMap .put(timeSlotInfo.getId(), copy);
        return new TimeSlotInfo(copy);
    }

    @Override
    public StatusInfo deleteTimeSlot(String timeSlotId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.timeSlotMap.remove(timeSlotId) == null) {
            throw new DoesNotExistException(timeSlotId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo submitScheduleBatch(String scheduleBatchId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("submitScheduleBatch has not been implemented");
    }

    @Override
    public StatusInfo commitSchedules(String scheduleBatchId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("commitSchedules has not been implemented");
    }

    @Override
    public List<Integer> getValidDaysOfWeekByTimeSlotType(String timeSlotTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> tsIds = getTimeSlotIdsByType(timeSlotTypeKey, contextInfo);
        List<TimeSlotInfo> ts = new ArrayList<TimeSlotInfo>();
        for (String id: tsIds) {
            try { ts.add (this.getTimeSlot(id, contextInfo)); } catch (DoesNotExistException e) { throw new OperationFailedException(); }
        }
        List<Integer> days = new ArrayList<Integer> ();
        for (TimeSlotInfo tsI: ts) {
            for (Integer d: tsI.getWeekdays()) {
                if (!days.contains(d)) {
                    days.add(d);
                }
            }
        }
        return days;
    }

    @Override
    public List<ScheduleBatchInfo> getScheduleBatchesForScheduleTransaction(String scheduleTransactionId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getScheduleBatchesForScheduleTransaction has not been implemented");
    }

    @Override
    public ScheduleTransactionInfo getScheduleTransaction(String scheduleTransactionId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.scheduleTransactionMap.containsKey(scheduleTransactionId)) {
            throw new DoesNotExistException(scheduleTransactionId);
        }
        return new ScheduleTransactionInfo(this.scheduleTransactionMap.get (scheduleTransactionId));
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByIds(List<String> scheduleTransactionIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ScheduleTransactionInfo> list = new ArrayList<ScheduleTransactionInfo> ();
        for (String id: scheduleTransactionIds) {
            list.add (this.getScheduleTransaction(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getScheduleTransactionIdsByType(String scheduleTransactionTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (ScheduleTransactionInfo info: scheduleTransactionMap.values ()) {
            if (scheduleTransactionTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> getScheduleTransactionIdsByRefObject(String refObjectType, String refObjectId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        // go through the list of schedule transactions and add those to list that
        // have the specified refObjectType and refObjectId
        for (ScheduleTransactionInfo info: scheduleTransactionMap.values()) {
// TODOSSR           if (refObjectType.equals(info.getRefObjectTypeKey())
//                    && refObjectId.equals(info.getRefObjectId())) {
//                list.add(info.getId());
//            }
        }
        return list;
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsByRefObject(String refObjectType, String refObjectId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        try { return getScheduleTransactionsByIds(getScheduleTransactionIdsByRefObject(refObjectType, refObjectId, contextInfo), contextInfo); }
        catch (DoesNotExistException e) {
            return new ArrayList<ScheduleTransactionInfo> ();
        }
    }

    @Override
    public List<ScheduleTransactionInfo> getScheduleTransactionsForScheduleBatch(String scheduleBatchId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getScheduleTransactionsForScheduleBatch has not been implemented");
    }

    @Override
    public List<String> searchForScheduleTransactionIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        return searchForInfoIds(criteria, scheduleTransactionMap.values());
    }

    @Override
    public List<ScheduleTransactionInfo> searchForScheduleTransactions(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        Collection<ScheduleTransactionInfo> matches = searchForInfoObjects(criteria, scheduleTransactionMap.values());

        List<ScheduleTransactionInfo> matchList = new ArrayList<ScheduleTransactionInfo>();
        for(ScheduleTransactionInfo info : matches) {
            matchList.add(new ScheduleTransactionInfo(info));
        }

        return matchList;
    }

    @Override
    public List<ValidationResultInfo> validateScheduleTransaction(String validationTypeKey, String scheduleBatchId, String scheduleTransactionTypeKey, ScheduleRequestInfo scheduleTransactionInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public ScheduleTransactionInfo createScheduleTransaction(String scheduleBatchId, String scheduleTransactionTypeKey, ScheduleTransactionInfo scheduleTransactionInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!scheduleTransactionTypeKey.equals (scheduleTransactionInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        ScheduleTransactionInfo copy = new ScheduleTransactionInfo(scheduleTransactionInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        scheduleTransactionMap.put(copy.getId(), copy);
        return new ScheduleTransactionInfo(copy);
    }

    @Override
    public ScheduleTransactionInfo updateScheduleTransaction(String scheduleTransactionId, ScheduleTransactionInfo scheduleTransactionInfo, ContextInfo contextInfo)
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
        if (!scheduleTransactionId.equals (scheduleTransactionInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ScheduleTransactionInfo copy = new ScheduleTransactionInfo(scheduleTransactionInfo);
        ScheduleTransactionInfo old = this.getScheduleTransaction(scheduleTransactionInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.scheduleTransactionMap .put(scheduleTransactionInfo.getId(), copy);
        return new ScheduleTransactionInfo(copy);
    }

    @Override
    public StatusInfo deleteScheduleTransaction(String scheduleTransactionId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.scheduleTransactionMap.remove(scheduleTransactionId) == null) {
            throw new DoesNotExistException(scheduleTransactionId);
        }
        return newStatus();
    }

    @Override
    public Boolean areTimeSlotsInConflict(String timeSlot1Id, String timeSlot2Id, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TimeSlotInfo timeSlotInfo1 = getTimeSlot(timeSlot1Id, contextInfo);
        TimeSlotInfo timeSlotInfo2 = getTimeSlot(timeSlot2Id, contextInfo);
        return SchedulingServiceUtil.areTimeSlotsInConflict(timeSlotInfo1, timeSlotInfo2);
    }

    @Override
    public ScheduleDisplayInfo getScheduleDisplay(String scheduleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleInfo scheduleInfo = getSchedule(scheduleId, contextInfo);
        ScheduleDisplayInfo scheduleDisplayInfo  = new ScheduleDisplayInfo();
        copyScheduleInfoIntoScheduleDisplayInfo(scheduleInfo, scheduleDisplayInfo, contextInfo);
        return scheduleDisplayInfo;
    }

    @Override
    public List<ScheduleDisplayInfo> getScheduleDisplaysByIds(List<String> scheduleIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleInfo> scheduleInfos = getSchedulesByIds(scheduleIds, contextInfo);
        List<ScheduleDisplayInfo> scheduleDisplayInfos = new ArrayList<ScheduleDisplayInfo>();
        for (ScheduleInfo scheduleInfo: scheduleInfos) {
            ScheduleDisplayInfo scheduleDisplayInfo = new ScheduleDisplayInfo();
            copyScheduleInfoIntoScheduleDisplayInfo(scheduleInfo, scheduleDisplayInfo, contextInfo);
            scheduleDisplayInfos.add(scheduleDisplayInfo);
        }
        return scheduleDisplayInfos;
    }

    @Override
    public List<ScheduleDisplayInfo> searchForScheduleDisplays(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> ids = searchForInfoIds(criteria, scheduleMap.values());
        try {
            return getScheduleDisplaysByIds(ids, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Failed to search for schedule displays", e);
        }
    }

    @Override
    public ScheduleRequestDisplayInfo getScheduleRequestDisplay(String scheduleRequestId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestInfo scheduleInfo = getScheduleRequest(scheduleRequestId, contextInfo);
        ScheduleRequestDisplayInfo scheduleDisplayInfo =
                ScheduleDisplayTransformer.scheduleRequestInfo2SceduleRequestDisplayInfo(scheduleInfo, getTypeService(), getRoomService(), this, contextInfo);
        return scheduleDisplayInfo;
    }

    @Override
    public List<ScheduleRequestDisplayInfo> getScheduleRequestDisplaysByIds(List<String> scheduleRequestIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<ScheduleRequestDisplayInfo> searchForScheduleRequestDisplays(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> ids = searchForInfoIds(criteria, scheduleRequestMap.values());
        try {
            return getScheduleRequestDisplaysByIds(ids, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Failed to search for schedule request displays", e);
        }
    }

    @Override
    public ScheduleRequestSetInfo getScheduleRequestSet(String scheduleRequestSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!scheduleRequestSetMap.containsKey(scheduleRequestSetId)) {
            throw new DoesNotExistException(scheduleRequestSetId);
        }
        return new ScheduleRequestSetInfo(scheduleRequestSetMap.get(scheduleRequestSetId));
    }

    @Override
    public List<ScheduleRequestSetInfo> getScheduleRequestSetsByIds(List<String> scheduleRequestSetIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestSetInfo> list = new ArrayList<ScheduleRequestSetInfo>();
        for(String id : scheduleRequestSetIds) {
            list.add(getScheduleRequestSet(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getScheduleRequestSetIdsByType(String scheduleRequestSetTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String> ();
        for (ScheduleRequestSetInfo info: scheduleRequestSetMap.values ()) {
            if (scheduleRequestSetTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getScheduleRequestSetIdsByRefObjType(String refObjectTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String> ();
        for (ScheduleRequestSetInfo info: scheduleRequestSetMap.values ()) {
            if (refObjectTypeKey.equals(info.getRefObjectTypeKey())) {
                list.add (info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForScheduleRequestSetIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return searchForInfoIds(criteria, scheduleRequestSetMap.values());
    }

    @Override
    public List<ScheduleRequestSetInfo> searchForScheduleRequestSets(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Collection<ScheduleRequestSetInfo> matches = searchForInfoObjects(criteria, scheduleRequestSetMap.values());

        List<ScheduleRequestSetInfo> matchList = new ArrayList<ScheduleRequestSetInfo>();
        for(ScheduleRequestSetInfo info : matches) {
            matchList.add(new ScheduleRequestSetInfo(info));
        }

        return matchList;
    }

    @Override
    public List<ValidationResultInfo> validateScheduleRequestSet(String validationTypeKey, String scheduleRequestSetTypeKey, String refObjectTypeKey, ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ScheduleRequestSetInfo createScheduleRequestSet(String scheduleRequestSetTypeKey, 
                                                           String refObjectTypeKey, ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo contextInfo) 
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        if (!scheduleRequestSetTypeKey.equals(scheduleRequestSetInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        if (!refObjectTypeKey.equals(scheduleRequestSetInfo.getRefObjectTypeKey())) {
            throw new InvalidParameterException ("The ref object type parameter does not match the ref object type on the info object");
        }
        ScheduleRequestSetInfo copy = new ScheduleRequestSetInfo(scheduleRequestSetInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        scheduleRequestSetMap.put(copy.getId(), copy);
        return new ScheduleRequestSetInfo(copy);
    }

    @Override
    public ScheduleRequestSetInfo updateScheduleRequestSet(String scheduleRequestSetId, ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if (!scheduleRequestSetId.equals (scheduleRequestSetInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ScheduleRequestSetInfo copy = new ScheduleRequestSetInfo(scheduleRequestSetInfo);
        ScheduleRequestSetInfo old = getScheduleRequestSet(scheduleRequestSetInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.scheduleRequestSetMap.put(scheduleRequestSetInfo.getId(), copy);
        return new ScheduleRequestSetInfo(copy);
    }

    @Override
    public StatusInfo deleteScheduleRequestSet(String scheduleRequestSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.scheduleRequestSetMap.remove(scheduleRequestSetId) == null) {
            throw new DoesNotExistException(scheduleRequestSetId);
        }
        return newStatus();
    }

    @Override
    public List<ScheduleRequestSetInfo> getScheduleRequestSetsByRefObject(String refObjectType, String refObjectId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestSetInfo> list = new ArrayList<ScheduleRequestSetInfo>();

        for(ScheduleRequestSetInfo info : scheduleRequestSetMap.values()) {
            if(info.getRefObjectTypeKey().equals(refObjectType) && info.getRefObjectIds().contains(refObjectId)) {
                list.add(new ScheduleRequestSetInfo(info));
            }
        }


        return list;
    }

    private void copyScheduleInfoIntoScheduleDisplayInfo (ScheduleInfo scheduleInfo, ScheduleDisplayInfo scheduleDisplayInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        scheduleDisplayInfo.setId(scheduleInfo.getId());
        scheduleDisplayInfo.setDescr(scheduleInfo.getDescr());
        scheduleDisplayInfo.setAttributes(scheduleInfo.getAttributes());
        scheduleDisplayInfo.setMeta(scheduleInfo.getMeta());
        scheduleDisplayInfo.setName(scheduleInfo.getName());
        scheduleDisplayInfo.setStateKey(scheduleInfo.getStateKey());
        scheduleDisplayInfo.setTypeKey(scheduleInfo.getTypeKey());
        scheduleDisplayInfo.setAtp(atpService.getAtp(scheduleInfo.getAtpId(), contextInfo));
        List<ScheduleComponentDisplayInfo> scheduleComponentDisplays = new ArrayList<ScheduleComponentDisplayInfo>();
        for (ScheduleComponentInfo scheduleComponentInfo: scheduleInfo.getScheduleComponents()) {
            ScheduleComponentDisplayInfo scheduleComponentDisplayInfo = new ScheduleComponentDisplayInfo();
            copyScheduleComponentInfoIntoScheduleComponentDisplayInfo(scheduleComponentInfo, scheduleComponentDisplayInfo, contextInfo);
            scheduleComponentDisplays.add(scheduleComponentDisplayInfo);
        }
        scheduleDisplayInfo.setScheduleComponentDisplays(scheduleComponentDisplays);
    }

    private void copyScheduleComponentInfoIntoScheduleComponentDisplayInfo(ScheduleComponentInfo scheduleComponentInfo, ScheduleComponentDisplayInfo scheduleComponentDisplayInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        scheduleComponentDisplayInfo.setId(scheduleComponentInfo.getId());
        RoomInfo roomInfo = roomService.getRoom(scheduleComponentInfo.getRoomId(), contextInfo);
        scheduleComponentDisplayInfo.setRoom(roomInfo);
        scheduleComponentDisplayInfo.setBuilding(roomService.getBuilding(roomInfo.getBuildingId(), contextInfo));
        List<TimeSlotInfo> timeSlotInfos = new ArrayList<TimeSlotInfo>();
        for (String timeSlotId : scheduleComponentInfo.getTimeSlotIds()) {
            timeSlotInfos.add(getTimeSlot(timeSlotId, contextInfo));
        }
        scheduleComponentDisplayInfo.setTimeSlots(timeSlotInfos);
    }

    /*
    private void copyScheduleRequestIntoScheduleRequestInfo (ScheduleRequestInfo scheduleRequestInfo, ScheduleRequestDisplayInfo scheduleRequestDisplayInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        scheduleRequestDisplayInfo.setId(scheduleRequestInfo.getId());
        scheduleRequestDisplayInfo.setDescr(scheduleRequestInfo.getDescr());
        scheduleRequestDisplayInfo.setAttributes(scheduleRequestInfo.getAttributes());
        scheduleRequestDisplayInfo.setMeta(scheduleRequestInfo.getMeta());
        scheduleRequestDisplayInfo.setName(scheduleRequestInfo.getName());
        scheduleRequestDisplayInfo.setStateKey(scheduleRequestInfo.getStateKey());
        scheduleRequestDisplayInfo.setTypeKey(scheduleRequestInfo.getTypeKey());
// TODOSSR       scheduleRequestDisplayInfo.setRefObjectId(scheduleRequestInfo.getRefObjectId());
//        scheduleRequestDisplayInfo.setRefObjectTypeKey(scheduleRequestInfo.getRefObjectTypeKey());
        List<ScheduleRequestComponentDisplayInfo> scheduleRequestComponentDisplayInfos = new ArrayList<ScheduleRequestComponentDisplayInfo>();
        for (ScheduleRequestComponentInfo scheduleRequestComponentInfo: scheduleRequestInfo.getScheduleRequestComponents()) {
            ScheduleRequestComponentDisplayInfo scheduleRequestComponentDisplayInfo = new ScheduleRequestComponentDisplayInfo();
            copyScheduleRequestComponentInfoIntoScheduleRequestComponentDisplayInfo(scheduleRequestComponentInfo, scheduleRequestComponentDisplayInfo, contextInfo);
            scheduleRequestComponentDisplayInfos.add(scheduleRequestComponentDisplayInfo);
        }
        scheduleRequestDisplayInfo.setScheduleRequestComponentDisplays(scheduleRequestComponentDisplayInfos);
    }

    private void copyScheduleRequestComponentInfoIntoScheduleRequestComponentDisplayInfo(ScheduleRequestComponentInfo scheduleRequestComponentInfo, ScheduleRequestComponentDisplayInfo scheduleRequestComponentDisplayInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // id and is tba
        scheduleRequestComponentDisplayInfo.setId(scheduleRequestComponentInfo.getId());
        scheduleRequestComponentDisplayInfo.setIsTBA(scheduleRequestComponentInfo.getIsTBA());
        // resource types
        List<TypeInfo> resourceTypes = new ArrayList<TypeInfo>();
        for (String resourceTypeKey: scheduleRequestComponentInfo.getResourceTypeKeys()) {
             resourceTypes.add(typeService.getType(resourceTypeKey, contextInfo));
        }
        scheduleRequestComponentDisplayInfo.setResourceTypes(resourceTypes);
        // rooms
        List<RoomInfo> rooms = new ArrayList<RoomInfo>();
        for (String roomId : scheduleRequestComponentInfo.getRoomIds()) {
            rooms.add(roomService.getRoom(roomId, contextInfo));
        }
        scheduleRequestComponentDisplayInfo.setRooms(rooms);
        // buildings
        List<BuildingInfo> buildings = new ArrayList<BuildingInfo>();
        for (String buildingId : scheduleRequestComponentInfo.getBuildingIds()) {
            buildings.add(roomService.getBuilding(buildingId, contextInfo));
        }
        scheduleRequestComponentDisplayInfo.setBuildings(buildings);
        // timeslots
        List<TimeSlotInfo> timeSlotInfos = new ArrayList<TimeSlotInfo>();
        for (String timeSlotId : scheduleRequestComponentInfo.getTimeSlotIds()) {
            timeSlotInfos.add(getTimeSlot(timeSlotId, contextInfo));
        }
        scheduleRequestComponentDisplayInfo.setTimeSlots(timeSlotInfos);
        // orgs
        List<OrgInfo> orgs = new ArrayList<OrgInfo>();
        for (String orgId : scheduleRequestComponentInfo.getOrgIds()) {
            orgs.add(organizationService.getOrg(orgId, contextInfo));
        }
        scheduleRequestComponentDisplayInfo.setOrgs(orgs);
    }
    */

    protected StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    private <T extends HasId> List<String> searchForInfoIds(QueryByCriteria criteria, Collection<T> collection)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        Collection<T> matches = searchForInfoObjects(criteria, collection);
        List<String> matchingIds = new ArrayList<String>();

        for(T info : matches) {
            matchingIds.add(info.getId());
        }
        return matchingIds;
    }

    public <T> Collection<T> searchForInfoObjects(QueryByCriteria criteria, Collection<T> collection)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        CriteriaMatcherInMemory<T> matcher = new CriteriaMatcherInMemory<T>();
        matcher.setCriteria(criteria);
        return matcher.findMatching(collection);
    }

}
