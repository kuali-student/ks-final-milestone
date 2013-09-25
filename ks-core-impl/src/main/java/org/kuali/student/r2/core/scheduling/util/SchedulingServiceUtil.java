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

package org.kuali.student.r2.core.scheduling.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Collection of utility methods for the Scheduling Service
 *
 * @author andrewlubbers
 * @author Mezba Mahtab
 */
public class SchedulingServiceUtil {

    /**
     * Converts a list of Calendar constants (i.e. Calendar.MONDAY, Calendar.FRIDAY) into a string of characters representing those days
     * Used in DTO/Entity translations for TimeSlot
     *
     * @param weekdaysList
     * @return
     */
    public static String weekdaysList2WeekdaysString(List<Integer> weekdaysList) {
        StringBuilder result = new StringBuilder();

        for(Integer day : weekdaysList) {
            switch (day) {
                case Calendar.MONDAY: {
                    result.append(SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.TUESDAY: {
                    result.append(SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.WEDNESDAY: {
                    result.append(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.THURSDAY: {
                    result.append(SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.FRIDAY: {
                    result.append(SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.SATURDAY: {
                    result.append(SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.SUNDAY: {
                    result.append(SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE);
                    break;
                }
            }
        }

        return result.toString();
    }

    public static List<Integer> weekdaysString2WeekdaysList(String weekdaysString) {
        List<Integer> result = new ArrayList<Integer>();
        if (StringUtils.isNotBlank(weekdaysString)) {
            checkStringForDayCode(SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE, Calendar.MONDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE, Calendar.TUESDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE, Calendar.WEDNESDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE, Calendar.THURSDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE, Calendar.FRIDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE, Calendar.SATURDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE, Calendar.SUNDAY, result, weekdaysString);
        }
        return result;
    }

    private static void checkStringForDayCode(String codeInString, Integer integerDayCode, List<Integer> result, String testString) {
        if (testString.contains(codeInString)) {
            result.add(integerDayCode);
        }
    }

    /**
     * Compares two time slots and sees if they 'overlap'. Will return true even if there is a millisecond overlap.
     * @author Mezba Mahtab
     * @param timeSlotInfo1 the first time slot
     * @param timeSlotInfo2 the second time slot
     * @return true if the timeslots share at least one common weekday and on that day the start and and end times of one time slot is within
     * the start and end times of the other timeslot.
     */
    public static boolean areTimeSlotsInConflict(TimeSlotInfo timeSlotInfo1, TimeSlotInfo timeSlotInfo2) {

        // Check if any of weekdays is common. If the two TimeSlots are on different weekdays,
        // no need to check for start/end times.
        boolean hasCommonWeekday = false;
        for (Integer dayOfWeekTs1: timeSlotInfo1.getWeekdays()) {
            if (timeSlotInfo2.getWeekdays().contains(dayOfWeekTs1)) {
                hasCommonWeekday = true;
                break;
            }
        }
        if (!hasCommonWeekday) return false;

        if (timeSlotInfo1.getStartTime().getMilliSeconds() == null ||
                timeSlotInfo1.getEndTime().getMilliSeconds() == null ||
                timeSlotInfo2.getStartTime().getMilliSeconds() == null ||
                timeSlotInfo2.getEndTime().getMilliSeconds() == null ) {
            // If there are null values in any of these spots, assume no conflict can occur.
            return false;
        }

        // there is a common weekday, so now check if there is an overlap of time.
        // If the end time of one time slot is before the start time of the other, there is
        // no overlap (alternate implementation--if you use semi-closed intervals where start
        // time is in the interval but end time is not, then change isBefore to ! isAfter).
        if (timeSlotInfo1.getEndTime().isBefore(timeSlotInfo2.getStartTime()) ||
                timeSlotInfo2.getEndTime().isBefore(timeSlotInfo1.getStartTime())) {
            return false;
        }
        return true;
    }

    /**
     * Convenience method for the short-cut process of translating a ScheduleRequest directly into a Schedule
     * Assumes that the room to be used for the actual schedule is the first room in the list from the request
     *
     * @param request
     * @return
     */
    public static ScheduleInfo requestToSchedule(ScheduleRequestInfo request,ScheduleInfo result) {
        result.setStateKey(SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        result.setTypeKey(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        result.setScheduleComponents(new ArrayList<ScheduleComponentInfo>(request.getScheduleRequestComponents().size()));

        for (ScheduleRequestComponentInfo reqComp : request.getScheduleRequestComponents()) {
            ScheduleComponentInfo compInfo = new ScheduleComponentInfo();
            compInfo.setIsTBA(reqComp.getIsTBA());

            // grabbing the first room in the list
            if(!reqComp.getRoomIds().isEmpty()){
                compInfo.setRoomId(reqComp.getRoomIds().get(0));
            }
            compInfo.setTimeSlotIds(reqComp.getTimeSlotIds());

            result.getScheduleComponents().add(compInfo);
        }

        return result;
    }

    /**
     * Convenience method to translate an actual Schedule into a ScheduleReqeust
     * Used during rollover and CO/AO copy to make requests in the target that are copies from the actual schedule of the source
     * Uses the RoomService to get the building information from the room id
     *
     * @param scheduleInfo
     * @param roomService
     * @param callContext
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public static ScheduleRequestInfo scheduleToRequest(ScheduleInfo scheduleInfo, RoomService roomService, ContextInfo callContext) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        ScheduleRequestInfo result = new ScheduleRequestInfo();
        result.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        result.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);

        for (ScheduleComponentInfo schedComp : scheduleInfo.getScheduleComponents()) {
            ScheduleRequestComponentInfo requestComponentInfo = new ScheduleRequestComponentInfo();
            requestComponentInfo.setIsTBA(schedComp.getIsTBA());
            requestComponentInfo.setTimeSlotIds(schedComp.getTimeSlotIds());
            requestComponentInfo.getRoomIds().add(schedComp.getRoomId());

            // retrieve the room to find the building id
            if(schedComp.getRoomId() != null) {
                RoomInfo room = roomService.getRoom(schedComp.getRoomId(), callContext);
                requestComponentInfo.getBuildingIds().add(room.getBuildingId());

                BuildingInfo building = roomService.getBuilding(room.getBuildingId(), callContext);
                requestComponentInfo.getCampusIds().add(building.getCampusKey());

                List<String> responsibleOrgIdList = roomService.getRoomResponsibleOrgIdsByRoom(schedComp.getRoomId(), callContext);
                if(responsibleOrgIdList != null) {
                    for(String id : responsibleOrgIdList) {
                        requestComponentInfo.getOrgIds().add(id);
                    }
                }
            }

            result.getScheduleRequestComponents().add(requestComponentInfo);
        }

        return result;
    }

    /**
     * Convenience method to translate an request Schedule into a ScheduleReqeust
     * Used during CO/AO copy to make requests in the target that are copies from the request schedule of the source
     *
     * @param sourceRequest
     * @param callContext
     * @return
     */
    public static ScheduleRequestInfo scheduleRequestToScheduleRequest(ScheduleRequestInfo sourceRequest, ContextInfo callContext)  {
        ScheduleRequestInfo result = new ScheduleRequestInfo();
        result.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        result.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);

        for (ScheduleRequestComponentInfo  sourceSchedComp : sourceRequest.getScheduleRequestComponents()) {
            ScheduleRequestComponentInfo requestComponentInfo = new ScheduleRequestComponentInfo();
            requestComponentInfo.setIsTBA(sourceSchedComp.getIsTBA());
            requestComponentInfo.setTimeSlotIds(sourceSchedComp.getTimeSlotIds());

            for(String id : sourceSchedComp.getRoomIds()) {
                requestComponentInfo.getRoomIds().add(id);
            }
            // retrieve the room to find the building id
            for(String id : sourceSchedComp.getBuildingIds()) {
                requestComponentInfo.getBuildingIds().add(id);
            }

            for(String id : sourceSchedComp.getCampusIds()) {
                requestComponentInfo.getCampusIds().add(id);
            }

            for(String id : sourceSchedComp.getOrgIds()) {
                requestComponentInfo.getOrgIds().add(id);
            }

            result.getScheduleRequestComponents().add(requestComponentInfo);
        }

        return result;
    }
}
