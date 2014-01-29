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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Collection of utility methods for the Scheduling Service
 *
 * @author andrewlubbers
 * @author Mezba Mahtab
 */
public final class SchedulingServiceUtil {

    //  Since this is a utility class (all static methods) hide the constructor.
    private SchedulingServiceUtil() {}

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
        if (StringUtils.containsIgnoreCase(testString, codeInString)) {
            result.add(integerDayCode);
        }
    }

    public static boolean isValidDays(String days){
        if (StringUtils.isNotBlank(days)){
            days = StringUtils.upperCase(days);
            String validDays = "MTWHFSU";
            return StringUtils.containsOnly(days,validDays);
        }
        return false;
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
        if (!hasCommonWeekday) {
            return false;
        }

        if (timeSlotInfo1.getStartTime() == null ||
                timeSlotInfo1.getEndTime() == null ||
                timeSlotInfo2.getStartTime() == null ||
                timeSlotInfo2.getEndTime() == null) {
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
     * Assumes that the room to be used for the actual schedule is the first room in the list from the request.
     * <p/>
     * If the request isn't TBA then fill in a value for room id as if the scheduler had looked for and found a
     * room.
     *
     * @param request The schedule request and components to process.
     * @param result The schedule for the newly created schedule components.
     * @param callContext The context.
     * @param roomService A RoomService implementation.
     * @return
     */
    public static ScheduleInfo requestToSchedule(ScheduleRequestInfo request, ScheduleInfo result, RoomService roomService, ContextInfo callContext) {
        result.setStateKey(SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        result.setTypeKey(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        result.setScheduleComponents(new ArrayList<ScheduleComponentInfo>(request.getScheduleRequestComponents().size()));

        for (ScheduleRequestComponentInfo reqComp : request.getScheduleRequestComponents()) {
            boolean isTBA =  reqComp.getIsTBA();
            boolean hasBuildingIds = ! reqComp.getBuildingIds().isEmpty();
            boolean hasRoomIds =  ! reqComp.getRoomIds().isEmpty();

            ScheduleComponentInfo compInfo = new ScheduleComponentInfo();
            compInfo.setIsTBA(isTBA);

            if (! isTBA) {
                String defaultRoomId = "d08416cf-c7e5-48c4-b6f9-8e0c9d3dddd1";
                //  No building, no room ... Just use the default room id for the schedule component.
                if ( ! hasBuildingIds && ! hasRoomIds) {
                     compInfo.setRoomId(defaultRoomId);
                } else {
                    //  Has a building but no room, look for a room in the building.
                    if (hasBuildingIds && ! hasRoomIds) {
                        String buildingId = reqComp.getBuildingIds().get(0);
                        List<String> rooms = Collections.emptyList();
                        try {
                            rooms = roomService.getRoomIdsByBuilding(buildingId, callContext);
                        } catch (Exception e) {
                            throw new RuntimeException("Query to room service failed.", e);
                        }
                        //  Grab the first room.
                        if ( ! rooms.isEmpty()) {
                            compInfo.setRoomId(rooms.get(0));
                        } else {
                            // If no rooms are defined then just use the default.
                            compInfo.setRoomId(defaultRoomId);
                        }
                    } else {
                        //  No building Ids, but room Ids exist. For RDLs that were created within the app this shouldn't
                        //  happen, but some ref data RDLs only provide a room id. Strictly speaking the data isn't correct,
                        //  but since it is sufficient to create the ADL just continue.
                        compInfo.setRoomId(reqComp.getRoomIds().get(0));
                    }
                }
            } else {
                //  If this is a TBA request then just copy the RDL params to the ADL.
                if (hasRoomIds) {
                    compInfo.setRoomId(reqComp.getRoomIds().get(0));
                }
            }
            //  Timeslot is currently required so assume present.
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

            // retrieve the room to find the building id
            if (schedComp.getRoomId() != null) {
                requestComponentInfo.getRoomIds().add(schedComp.getRoomId());
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
     * Copies the original request into a new one, but nulls out IDs, and replaces the SRS id with the
     * one passed in
     * @param origRequest The request to be copied
     * @param srsId The new srs ID to set this to
     * @return A copy of the SRI minus the IDs
     */
    public static ScheduleRequestInfo copyScheduleRequest(ScheduleRequestInfo origRequest, String srsId) {
        ScheduleRequestInfo copy = new ScheduleRequestInfo(origRequest);
        copy.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED); // Reset the state
        copy.setScheduleRequestSetId(srsId);
        copy.setId(null); // Null out the IDs
        if (copy.getScheduleRequestComponents() != null) {
            for (ScheduleRequestComponentInfo comp: copy.getScheduleRequestComponents()) {
                comp.setId(null); // Null these out too
            }
        }
        return copy;
    }

    /**
     * Makes a new Comparator<TimeSlotInfo> which can be use to sort a collection of TimeSlotInfo.
     * @return A new Comparator<TimeSlotInfo>.
     */
    public static Comparator<TimeSlotInfo> makeTimeSlotComparator() {
        return new Comparator<TimeSlotInfo>() {
            @Override
            public int compare(TimeSlotInfo o1, TimeSlotInfo o2) {
                //  Compare type key
                if ( ! StringUtils.equals(o1.getTypeKey(), o2.getTypeKey())) {
                    return o1.getTypeKey().compareTo(o2.getTypeKey());
                }

                //  Compare days. Make a copy of the List, sort it, and then just compare the strings.
                List<Integer> o1Days = new ArrayList<Integer>(o1.getWeekdays());
                Collections.sort(o1Days);
                List<Integer> o2Days = new ArrayList<Integer>(o2.getWeekdays());
                Collections.sort(o2Days);

                if (! StringUtils.equals(o1Days.toString(), o2Days.toString())) {
                    return o1Days.toString().compareTo(o2Days.toString());
                }

                //  Compare start time
                if (! o1.getStartTime().equals(o2.getStartTime())) {
                    return o1.getStartTime().compareTo(o2.getStartTime());
                }

                //  Compare end time
                if (! o1.getEndTime().equals(o2.getEndTime())) {
                    return o1.getEndTime().compareTo(o2.getEndTime());
                }
                //  They match.
                return 0;
            }
        };
    }

}
