/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 3/11/14
 */
package org.kuali.student.enrollment.class2.courseoffering.helper.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.helper.ExamOfferingScheduleHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * This class defines methods to load and save schedules in Exam Offering schedule logic
 *
 * @author Kuali Student Team
 */
public class ExamOfferingScheduleHelperImpl implements ExamOfferingScheduleHelper {
    public ExamOfferingScheduleHelperImpl() {
    }

    @Transactional
    // If it's already a part of transaction, it's ok.. Otherwise, create a new transaction boundary for all the changes.
    public void saveSchedules(ExamOfferingWrapper eoWrapper, ContextInfo defaultContextInfo){
        if (defaultContextInfo == null) {
            defaultContextInfo = createContextInfo();
        }

    }

    public void loadSchedules(ExamOfferingWrapper eoWrapper, CourseOfferingManagementForm theForm, ContextInfo defaultContextInfo){
        loadScheduleRequests(eoWrapper, theForm, defaultContextInfo);
    }

    /**
     * This method loads the schedule requests/components.
     * Support multiple schedule requests
     *
     * @param eoWrapper  ExamOfferingWrapper
     */
    public void loadScheduleRequests(ExamOfferingWrapper eoWrapper, CourseOfferingManagementForm theForm, ContextInfo defaultContextInfo){
        try {
            List<ScheduleRequestInfo> scheduleRequestInfos = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestsByRefObject(
                    ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, eoWrapper.getEoInfo().getId(), defaultContextInfo);

            //For Full colo, there must be only one ScheduleRequestSet.
            if (!scheduleRequestInfos.isEmpty()){
                int firstScheduleRequest = 0;
                ScheduleRequestSetInfo scheduleRequestSet = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSet(scheduleRequestInfos.get(firstScheduleRequest).getScheduleRequestSetId(),defaultContextInfo);
                eoWrapper.setScheduleRequestSetInfo(scheduleRequestSet);

                for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfos){
                    for (ScheduleRequestComponentInfo componentInfo : scheduleRequestInfo.getScheduleRequestComponents()) {
                        ScheduleWrapper scheduleWrapper = new ScheduleWrapper(scheduleRequestInfo,componentInfo);
                        buildScheduleWrapper(eoWrapper, scheduleWrapper, componentInfo, theForm, defaultContextInfo);

                        eoWrapper.getRequestedScheduleComponents().add(scheduleWrapper);
                    }
                }
                //set current RSI of the eo
                eoWrapper.setRequestedSchedule(eoWrapper.getRequestedScheduleComponents().get(firstScheduleRequest));
            } else {
                ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
                //set current RSI of the eo
                eoWrapper.setRequestedSchedule(scheduleWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void buildScheduleWrapper(ExamOfferingWrapper eoWrapper, ScheduleWrapper scheduleWrapper, ScheduleRequestComponentInfo componentInfo, CourseOfferingManagementForm theForm, ContextInfo defaultContextInfo){
        try {
            //There should be only one timeslot per ScheduleRequestComponentInfo
            String timeSlotId = KSCollectionUtils.getOptionalZeroElement(componentInfo.getTimeSlotIds());
            TimeSlotInfo timeSlot = CourseOfferingManagementUtil.getSchedulingService().getTimeSlot(timeSlotId, defaultContextInfo);
            scheduleWrapper.setTimeSlot(timeSlot);

            TimeOfDayInfo startTime = timeSlot.getStartTime();
            TimeOfDayInfo endTime = timeSlot.getEndTime();
            List<Integer> days = timeSlot.getWeekdays();

            if (startTime != null && startTime.getHour() != null) {
                String startTimeUI = TimeOfDayHelper.makeFormattedTimeForAOSchedules(startTime);
                scheduleWrapper.setStartTimeUI(startTimeUI);
                scheduleWrapper.setStartTime(org.apache.commons.lang.StringUtils.substringBefore(startTimeUI, " "));
                scheduleWrapper.setStartTimeAmPm(org.apache.commons.lang.StringUtils.substringAfter(startTimeUI, " "));
            }
            if (endTime != null && endTime.getHour() != null) {
                String endTimeUI = TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime);
                scheduleWrapper.setEndTimeUI(endTimeUI);
                scheduleWrapper.setEndTime(org.apache.commons.lang.StringUtils.substringBefore(endTimeUI, " "));
                scheduleWrapper.setEndTimeAmPm(org.apache.commons.lang.StringUtils.substringAfter(endTimeUI, " "));
            }

            if (days != null && days.size() > 0) {
                scheduleWrapper.setDaysUI(CourseOfferingManagementUtil.examPeriodDaysDisplay(days, theForm.getExamPeriodWrapper()));
                scheduleWrapper.setDayInExamPeriod(KSCollectionUtils.getOptionalZeroElement(days).toString());
            }

            if (!componentInfo.getRoomIds().isEmpty()){
                int firstRoomIndex = 0;
                String roomId = componentInfo.getRoomIds().get(firstRoomIndex);
                if (StringUtils.isNotBlank(roomId)) {
                    RoomInfo room = CourseOfferingManagementUtil.getRoomService().getRoom(roomId, defaultContextInfo);
                    scheduleWrapper.setRoom(room);
                    if (!room.getRoomUsages().isEmpty()){
                        int firstRoomUsagesIndex = 0;
                        scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(firstRoomUsagesIndex).getHardCapacity());
                    }

                    BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(room.getBuildingId(), defaultContextInfo);
                    scheduleWrapper.setBuilding(buildingInfo);
                    scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                    scheduleWrapper.setBuildingId(room.getBuildingId());
                }

            } else if (!componentInfo.getBuildingIds().isEmpty()){
                int firstBuildingIndex = 0;
                String buildingId = componentInfo.getBuildingIds().get(firstBuildingIndex);

                if (StringUtils.isNotBlank(buildingId)) {
                    BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(buildingId, defaultContextInfo);
                    scheduleWrapper.setBuilding(buildingInfo);
                    scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                    scheduleWrapper.setBuildingId(buildingId);
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }
}
