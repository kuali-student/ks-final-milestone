/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.class1.scheduling.impl;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.*;


public class SchedulingServiceMockImpl implements SchedulingService
{

    ////////////////////////////
    // Data (Test Cases)
    ////////////////////////////

    private Map<String, TimeSlotInfo> timeSlotsMap = new HashMap<String, TimeSlotInfo>();
    private Map<String, ScheduleInfo> scheduleMap = new HashMap<String, ScheduleInfo>();
    private Map<String, ScheduleBatchInfo> scheduleBatchMap = new LinkedHashMap<String, ScheduleBatchInfo>();
    private Map<String, ScheduleBatchRespInfo> scheduleBatchResponseMap = new LinkedHashMap<String, ScheduleBatchRespInfo>();
    private Map<String, ScheduleRespInfo> scheduleResponseMap = new LinkedHashMap<String, ScheduleRespInfo>();

    public SchedulingServiceMockImpl () {
        // M W F 8 - 8.50
        TimeSlotInfo ts = new TimeSlotInfo();
        List<Integer> dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY); dow.add(Calendar.WEDNESDAY); dow.add(Calendar.FRIDAY);
        ts.setWeekdays(dow);
        TimeOfDayInfo tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000));
        ts.setStartTime(tod);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000 + 50 * 60 * 1000));
        timeSlotsMap.put("1", ts);

        // M W F 8 - 9.10
        ts = new TimeSlotInfo();
        dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY); dow.add(Calendar.WEDNESDAY); dow.add(Calendar.FRIDAY);
        ts.setWeekdays(dow);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000));
        ts.setStartTime(tod);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000 + 70 * 60 * 1000));
        timeSlotsMap.put("2", ts);

        // T TH 8 - 8.50
        ts = new TimeSlotInfo();
        dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY); dow.add(Calendar.THURSDAY);
        ts.setWeekdays(dow);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000));
        ts.setStartTime(tod);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000 + 50 * 60 * 1000));
        timeSlotsMap.put("3", ts);

        // T TH 8 - 9.10
        ts = new TimeSlotInfo();
        dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY); dow.add(Calendar.THURSDAY);
        ts.setWeekdays(dow);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000));
        ts.setStartTime(tod);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (8 * 60 * 60 * 1000 + 70 * 60 * 1000));
        timeSlotsMap.put("4", ts);

        // M W F 10 - 10.50
        ts = new TimeSlotInfo();
        dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY); dow.add(Calendar.WEDNESDAY); dow.add(Calendar.FRIDAY);
        ts.setWeekdays(dow);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (10 * 60 * 60 * 1000));
        ts.setStartTime(tod);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (10 * 60 * 60 * 1000 + 50 * 60 * 1000));
        timeSlotsMap.put("5", ts);

        // M W F 10 - 11.10
        ts = new TimeSlotInfo();
        dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY); dow.add(Calendar.WEDNESDAY); dow.add(Calendar.FRIDAY);
        ts.setWeekdays(dow);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (11 * 60 * 60 * 1000));
        ts.setStartTime(tod);
        tod = new TimeOfDayInfo();
        tod.setMilliSeconds((long) (11 * 60 * 60 * 1000 + 70 * 60 * 1000));
        timeSlotsMap.put("6", ts);

    }
    /////////////////////////////
    // Implementing Methods
    /////////////////////////////
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
		return this.scheduleMap.get (scheduleId);
	}
	
	@Override
	public List<ScheduleInfo> getSchedulesByIds(List<String> scheduleIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		List<ScheduleInfo> list = new ArrayList<ScheduleInfo> ();
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
		throw new OperationFailedException ("searchForScheduleIds has not been implemented");
	}
	
	@Override
	public List<ScheduleInfo> searchForSchedules(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("searchForSchedules has not been implemented");
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
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	// private Map<String, ScheduleInfo> scheduleMap = new LinkedHashMap<String, ScheduleInfo>();
	
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
		   copy.setId(scheduleMap.size() + "");
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
        throw new UnsupportedOperationException("Not supported yet.");
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
		return this.scheduleBatchMap.get (scheduleBatchId);
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
	public List<ScheduleBatchInfo> getScheduleBatchesForScheduleRequest(String scheduleRequestId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("getScheduleBatchesForScheduleRequest has not been implemented");
	}
	
	@Override
	public List<String> searchForScheduleBatchIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("searchForScheduleBatchIds has not been implemented");
	}
	
	@Override
	public List<ScheduleBatchInfo> searchForScheduleBatches(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("searchForScheduleBatches has not been implemented");
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
		   copy.setId(scheduleBatchMap.size() + "");
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
	public StatusInfo addScheduleRequestsToScheduleBatch(List<String> scheduleRequestIds, List<String> scheduleBatchId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("addScheduleRequestsToScheduleBatch has not been implemented");
	}
	
	@Override
	public StatusInfo removeScheduleRequestsFromScheduleBatch(List<String> scheduleRequestIds, List<String> scheduleBatchId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("removeScheduleRequestsFromScheduleBatch has not been implemented");
	}

	@Override
	public ScheduleBatchRespInfo getScheduleBatchResp(String scheduleBatchResponseId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		if (!this.scheduleBatchResponseMap.containsKey(scheduleBatchResponseId)) {
		   throw new DoesNotExistException(scheduleBatchResponseId);
		}
		return this.scheduleBatchResponseMap.get (scheduleBatchResponseId);
	}

	@Override
	public List<ScheduleBatchRespInfo> getScheduleBatchRespsByIds(List<String> scheduleBatchResponseIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		List<ScheduleBatchRespInfo> list = new ArrayList<ScheduleBatchRespInfo> ();
		for (String id: scheduleBatchResponseIds) {
		    list.add (this.getScheduleBatchResp(id, contextInfo));
		}
		return list;
	}

	@Override
	public List<String> getScheduleBatchRespIdsByType(String scheduleBatchResponseTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		List<String> list = new ArrayList<String> ();
		for (ScheduleBatchRespInfo info: scheduleBatchResponseMap.values ()) {
			if (scheduleBatchResponseTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}

	@Override
	public List<ScheduleBatchRespInfo> getScheduleBatchRespsByScheduleBatchRequest(String scheduleBatchId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		List<ScheduleBatchRespInfo> list = new ArrayList<ScheduleBatchRespInfo> ();
		for (ScheduleBatchRespInfo info: scheduleBatchResponseMap.values ()) {
			if (scheduleBatchId.equals(info.getId())) {
			    list.add (info);
			}
		}
		return list;
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
		return this.scheduleRequestMap.get (scheduleRequestId);
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
	public List<String> getScheduleRequestsByRefObject(String refObjectType, String refObjectId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public List<ScheduleRequestInfo> getScheduleRequestsForScheduleBatch(String scheduleBatchId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("getScheduleRequestsForScheduleBatch has not been implemented");
	}
	
	@Override
	public List<String> searchForScheduleRequestIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("searchForScheduleRequestIds has not been implemented");
	}
	
	@Override
	public List<ScheduleRequestInfo> searchForScheduleRequests(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("searchForScheduleRequests has not been implemented");
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
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, ScheduleRequestInfo> scheduleRequestMap = new LinkedHashMap<String, ScheduleRequestInfo>();
	
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
		ScheduleRequestInfo copy = new ScheduleRequestInfo(scheduleRequestInfo);
		if (copy.getId() == null) {
		   copy.setId(scheduleRequestMap.size() + "");
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
		return this.timeSlotMap.get (timeSlotId);
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
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
		throw new OperationFailedException ("searchForTimeSlotIds has not been implemented");
	}
	
	@Override
	public List<TimeSlotInfo> searchForTimeSlots(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("searchForTimeSlots has not been implemented");
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
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, TimeSlotInfo> timeSlotMap = new LinkedHashMap<String, TimeSlotInfo>();
	
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
		   copy.setId(timeSlotMap.size() + "");
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
	public ScheduleRespInfo getScheduleResp(String scheduleResponseId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		if (!this.scheduleResponseMap.containsKey(scheduleResponseId)) {
		   throw new DoesNotExistException(scheduleResponseId);
		}
		return this.scheduleResponseMap.get (scheduleResponseId);
	}
	
	@Override
	public List<ScheduleRespInfo> getScheduleRespsByIds(List<String> scheduleResponseIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		List<ScheduleRespInfo> list = new ArrayList<ScheduleRespInfo> ();
		for (String id: scheduleResponseIds) {
		    list.add (this.getScheduleResp(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getScheduleRespIdsByType(String scheduleResponseTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		List<String> list = new ArrayList<String> ();
		for (ScheduleRespInfo info: scheduleResponseMap.values ()) {
			if (scheduleResponseTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<ScheduleRespInfo> getScheduleRespsByScheduleRequestIds(List<String> scheduleRequestIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public List<ScheduleRespInfo> getScheduleRespsByScheduleBatchResp(String scheduleBatchResponseId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		List<ScheduleRespInfo> list = new ArrayList<ScheduleRespInfo> ();
		for (ScheduleRespInfo info: scheduleResponseMap.values ()) {
			if (scheduleBatchResponseId.equals(info.getId())) {
			    list.add (info);
			}
		}
		return list;
	}
	
	@Override
	public ScheduleRespInfo submitScheduleBatch(String scheduleBatchId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		throw new OperationFailedException ("submitScheduleBatch has not been implemented");
	}
	
	@Override
	public StatusInfo commitSchedules(String scheduleBatchResponseId, ContextInfo contextInfo)
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

	private MetaInfo newMeta(ContextInfo context) {
	     MetaInfo meta = new MetaInfo();
	     meta.setCreateId(context.getPrincipalId());
	     meta.setCreateTime(new Date());
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(meta.getCreateTime());
	     meta.setVersionInd("0");
	     return meta;
	}
	
	private StatusInfo newStatus() {
	     StatusInfo status = new StatusInfo();
	     status.setSuccess(Boolean.TRUE);
	     return status;
	}
	
	private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
	     MetaInfo meta = new MetaInfo(old);
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(new Date());
	     meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
	     return meta;
	}
	
}

