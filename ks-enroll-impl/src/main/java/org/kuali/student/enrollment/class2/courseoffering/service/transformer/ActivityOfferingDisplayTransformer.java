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
 * Created by Charles on 9/10/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingDisplayTransformer {

    private static ScheduleDisplayInfo _createTempDisplayInfo() {
        // TODO: Change this when we get a real scheduling service
        // First, create a schedule component display info
        ScheduleComponentDisplayInfo scheduleComponentDisplayInfo = new ScheduleComponentDisplayInfo();
        BuildingInfo buildingInfo = new BuildingInfo();
        buildingInfo.setBuildingCode("SYM");
        buildingInfo.setName("Symons Hall");

        scheduleComponentDisplayInfo.setBuilding(buildingInfo);
        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setFloor("2");
        roomInfo.setRoomCode("202");
        scheduleComponentDisplayInfo.setRoom(roomInfo);
        // Now create ScheduleDisplayInfo
        ScheduleDisplayInfo scheduleDisplayInfo = new ScheduleDisplayInfo();
        List<ScheduleComponentDisplayInfo> scheduleComponentDisplayInfos = new ArrayList<ScheduleComponentDisplayInfo>();
        scheduleComponentDisplayInfos.add(scheduleComponentDisplayInfo);
        scheduleDisplayInfo.setScheduleComponentDisplays(scheduleComponentDisplayInfos);

        return scheduleDisplayInfo;
    }

    public static ActivityOfferingDisplayInfo ao2aoDisplay(ActivityOfferingInfo aoInfo,
                                                           SchedulingService schedulingService,
                                                           StateService stateService,
                                                           TypeService typeService,
                                                           ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {
        ActivityOfferingDisplayInfo displayInfo = new ActivityOfferingDisplayInfo();
        // Fields in ActivityOfferingDisplayInfo
        // typeName, stateName, courseOfferingTitle;
        TypeInfo aoType = typeService.getType(aoInfo.getTypeKey(), contextInfo);
        displayInfo.setTypeName(aoType.getName());
        displayInfo.setTypeKey(aoType.getKey());
        StateInfo aoState = stateService.getState(aoInfo.getStateKey(), contextInfo);
        displayInfo.setStateName(aoState.getName());
        displayInfo.setStateKey(aoState.getKey());
        displayInfo.setCourseOfferingTitle(aoInfo.getCourseOfferingTitle());
        // courseOfferingCode, formatOfferingId, formatOfferingName;
        displayInfo.setCourseOfferingCode(aoInfo.getCourseOfferingCode());
        displayInfo.setFormatOfferingId(aoInfo.getFormatOfferingId());
        displayInfo.setFormatOfferingName(aoInfo.getFormatOfferingName());
        // activityOfferingCode, instructorId, instructorName;
        displayInfo.setActivityOfferingCode(aoInfo.getActivityCode());
        List<OfferingInstructorInfo> instructorInfos = aoInfo.getInstructors();
        if (instructorInfos != null && !instructorInfos.isEmpty()) {
            // Find instructor with largest percentage effort
            displayInfo.setInstructorId(instructorInfos.get(0).getPersonId());
            displayInfo.setInstructorName(instructorInfos.get(0).getPersonName());
        }
        else  {
            displayInfo.setInstructorId(null);
            displayInfo.setInstructorName(null);
        }
        // isHonorsOffering, maximumEnrollment
        displayInfo.setIsHonorsOffering(aoInfo.getIsHonorsOffering());
        displayInfo.setMaximumEnrollment(aoInfo.getMaximumEnrollment());

        // scheduleDisplay
        if(aoInfo.getScheduleId()!=null){
            displayInfo.setScheduleDisplay(schedulingService.getScheduleDisplay(aoInfo.getScheduleId(), contextInfo));
        }

        return displayInfo;
    }

    /**
     * Transform a ActivityOfferingInfo into an ActivityOfferingDisplayInfo. It takes cached maps of keyToTypeInfoMap,
     * keyToStateInfoMap and scheduleIdToScheduleDisplayInfoMap as the params instead of doing
     * service calls inside to retrieve TypeInfo, StateInfo and ScheduleDisplayInfo
     *
     * @param aoInfo                            the ActivityOfferingInfo
     * @param keyToTypeInfoMap                  the cached map of typeKey to TypeInfo
     * @param keyToStateInfoMap                 the cached map of stateKey to StateInfo
     * @param scheduleIdToScheduleDisplayInfoMap      the cached map of scheduleId to ScheduleDisplayInfos
     * @return an ActivityOfferingDisplayInfo
     */
    public static ActivityOfferingDisplayInfo ao2aoDisplay(ActivityOfferingInfo aoInfo,
                                                           Map<String, TypeInfo> keyToTypeInfoMap,
                                                           Map<String, StateInfo> keyToStateInfoMap,
                                                           Map<String, ScheduleDisplayInfo> scheduleIdToScheduleDisplayInfoMap) {
        ActivityOfferingDisplayInfo displayInfo = new ActivityOfferingDisplayInfo();
        // Fields in ActivityOfferingDisplayInfo
        // typeName, stateName, courseOfferingTitle;
        TypeInfo aoType = keyToTypeInfoMap.get(aoInfo.getTypeKey());
        displayInfo.setTypeName(aoType.getName());
        displayInfo.setTypeKey(aoType.getKey());
        StateInfo aoState = keyToStateInfoMap.get(aoInfo.getStateKey());
        displayInfo.setStateName(aoState.getName());
        displayInfo.setStateKey(aoState.getKey());
        displayInfo.setCourseOfferingTitle(aoInfo.getCourseOfferingTitle());
        // courseOfferingCode, formatOfferingId, formatOfferingName;
        displayInfo.setCourseOfferingCode(aoInfo.getCourseOfferingCode());
        displayInfo.setFormatOfferingId(aoInfo.getFormatOfferingId());
        displayInfo.setFormatOfferingName(aoInfo.getFormatOfferingName());
        // activityOfferingCode, instructorId, instructorName;
        displayInfo.setActivityOfferingCode(aoInfo.getActivityCode());
        List<OfferingInstructorInfo> instructorInfos = aoInfo.getInstructors();
        if (instructorInfos != null && !instructorInfos.isEmpty()) {
            // Find instructor with largest percentage effort
            displayInfo.setInstructorId(instructorInfos.get(0).getPersonId());
            displayInfo.setInstructorName(instructorInfos.get(0).getPersonName());
        }
        else  {
            displayInfo.setInstructorId(null);
            displayInfo.setInstructorName(null);
        }
        // isHonorsOffering, maximumEnrollment
        displayInfo.setIsHonorsOffering(aoInfo.getIsHonorsOffering());
        displayInfo.setMaximumEnrollment(aoInfo.getMaximumEnrollment());

        // scheduleDisplay
        if(aoInfo.getScheduleId()!=null){
            displayInfo.setScheduleDisplay(scheduleIdToScheduleDisplayInfoMap.get(aoInfo.getScheduleId()));
        }

        return displayInfo;
    }

    /**
     * Transform a list of ActivityOfferingInfos into ActivityOfferingDisplayInfos. It is the bulk version of ao2aoDisplay transformer
     *
     * @param aoInfos                   the list of LuiInfos
     * @param stateService              the reference of StateService
     * @param schedulingService         the reference of SchedulingService
     * @param typeService               the reference of TypeService
     * @param contextInfo               information containing the principalId and locale
     *                                  information about the caller of service operation
     * @return a list of ActivityOfferingDisplayInfos
     * @throws DoesNotExistException     ActivityOfferingDisplayInfo is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException aoInfos, schedulingService, stateService, typeService or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public static List<ActivityOfferingDisplayInfo> aos2aoDisplays(List<ActivityOfferingInfo> aoInfos,
                                                           SchedulingService schedulingService,
                                                           StateService stateService,
                                                           TypeService typeService,
                                                           ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {

        List<ActivityOfferingDisplayInfo> displayInfos = new ArrayList<ActivityOfferingDisplayInfo>(aoInfos.size());

        if (aoInfos != null && !aoInfos.isEmpty()) {
            Map<String, TypeInfo> keyToTypeInfoMap = new HashMap<String, TypeInfo>();
            Map<String, StateInfo> keyToStateInfoMap = new HashMap<String, StateInfo>();
            Map<String, ScheduleDisplayInfo> scheduleIdToScheduleDisplayInfoMap = new HashMap<String, ScheduleDisplayInfo>();

            List<String> aoTypeKeys = new ArrayList<String>(aoInfos.size());
            List<String> aoStateKeys = new ArrayList<String>(aoInfos.size());
            List<String> aoScheduleIds = new ArrayList<String>();   /// not setting the size because the dao id search uses the size for the  ids passed in.

            for (ActivityOfferingInfo aoInfo : aoInfos) {
                aoTypeKeys.add(aoInfo.getTypeKey());
                aoStateKeys.add(aoInfo.getStateKey());
                if(aoInfo.getScheduleId() != null)     {
                    // we can't have any nulls passed into the dao search or it will break.
                    // are the schedule Ids required?
                    aoScheduleIds.add(aoInfo.getScheduleId());
                }
            }

            List<TypeInfo> typeInfos = typeService.getTypesByKeys(aoTypeKeys, contextInfo);
            for (TypeInfo typeInfo : typeInfos) {
                keyToTypeInfoMap.put(typeInfo.getKey(), typeInfo);
            }

            List<StateInfo> stateInfos = stateService.getStatesByKeys(aoStateKeys, contextInfo);
            for (StateInfo stateInfo : stateInfos) {
                keyToStateInfoMap.put(stateInfo.getKey(), stateInfo);
            }

            List<ScheduleDisplayInfo> scheduleDisplayInfos = schedulingService.getScheduleDisplaysByIds(aoScheduleIds, contextInfo);
            for (ScheduleDisplayInfo scheduleDisplayInfo : scheduleDisplayInfos) {
                scheduleIdToScheduleDisplayInfoMap.put(scheduleDisplayInfo.getId(), scheduleDisplayInfo);
            }

            for (ActivityOfferingInfo aoInfo : aoInfos) {
                displayInfos.add(ao2aoDisplay(aoInfo, keyToTypeInfoMap, keyToStateInfoMap, scheduleIdToScheduleDisplayInfoMap));
            }
        }

        return displayInfos;
    }
}
