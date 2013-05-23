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
 * Created by Charles on 9/11/12
 */
package org.kuali.student.r2.core.scheduling.service.transformer;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforms scheduleInfo to scheduleDisplayInfo and scheduleComponentInfo to scheduleComponentDisplayInfo
 *
 * @author Kuali Student Team (charles)
 */
public class ScheduleDisplayTransformer {
    public static ScheduleDisplayInfo schedule2scheduleDisplay(ScheduleInfo scheduleInfo,
                                                               AtpService atpService,
                                                               RoomService roomService,
                                                               SchedulingService schedulingService,
                                                               ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException,
                   PermissionDeniedException {
        ScheduleDisplayInfo displayInfo = new ScheduleDisplayInfo();
        // Schedule Display fields:
        // atp;
        String atpId = scheduleInfo.getAtpId();
        if (atpId != null) {
            AtpInfo atpInfo = atpService.getAtp(atpId, contextInfo);
            displayInfo.setAtp(atpInfo);
        } else {
            displayInfo.setAtp(null);
        }
        // scheduleComponentDisplays;
        List<ScheduleComponentInfo> scheduleComponentInfos = scheduleInfo.getScheduleComponents();
        List<ScheduleComponentDisplayInfo> compDisplayInfos = new ArrayList<ScheduleComponentDisplayInfo>();
        if (scheduleComponentInfos != null) {
            for (ScheduleComponentInfo info: scheduleComponentInfos) {
                ScheduleComponentDisplayInfo convert =
                        schedComp2schedCompDisplay(info, roomService, schedulingService, contextInfo);
                compDisplayInfos.add(convert);
            }
        }
        displayInfo.setScheduleComponentDisplays(compDisplayInfos);
        // Other fields via inheritance--for completeness (may not be used)
        displayInfo.setId(scheduleInfo.getId());
        displayInfo.setDescr(scheduleInfo.getDescr());
        displayInfo.setAttributes(scheduleInfo.getAttributes());
        displayInfo.setName(scheduleInfo.getName());
        displayInfo.setStateKey(scheduleInfo.getStateKey());
        displayInfo.setTypeKey(scheduleInfo.getTypeKey());
        displayInfo.setMeta(scheduleInfo.getMeta());

        return displayInfo;
    }

    public static ScheduleComponentDisplayInfo schedComp2schedCompDisplay(ScheduleComponentInfo compInfo,
                                                                          RoomService roomService,
                                                                          SchedulingService schedulingService,
                                                                          ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        ScheduleComponentDisplayInfo cdInfo = new ScheduleComponentDisplayInfo();
        // Fields: room, building,
        String roomId = compInfo.getRoomId();
        if (roomId != null) {
            RoomInfo roomInfo = roomService.getRoom(roomId, contextInfo);
            BuildingInfo buildingInfo = roomService.getBuilding(roomInfo.getBuildingId(), contextInfo);
            cdInfo.setBuilding(buildingInfo);
            cdInfo.setRoom(roomInfo);
        } else {
            cdInfo.setRoom(null);
            cdInfo.setBuilding(null);
        }
        // Fields: timeSlots;
        List<String> timeSlotIds = compInfo.getTimeSlotIds();
        List<TimeSlotInfo> timeSlotInfoList = new ArrayList<TimeSlotInfo>();
        if (timeSlotIds != null) {
           timeSlotInfoList = schedulingService.getTimeSlotsByIds(timeSlotIds, contextInfo);
        }
        cdInfo.setTimeSlots(timeSlotInfoList);
        // Some basic fields
        cdInfo.setId(compInfo.getId());
        return cdInfo;
    }

    public static ScheduleRequestDisplayInfo scheduleRequestInfo2SceduleRequestDisplayInfo(ScheduleRequestInfo scheduleInfo,
                                                               TypeService typeService,
                                                               RoomService roomService,
                                                               SchedulingService schedulingService,
                                                               ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException,
            PermissionDeniedException {
        ScheduleRequestDisplayInfo displayInfo = new ScheduleRequestDisplayInfo();

        // scheduleComponentDisplays;
        List<ScheduleRequestComponentInfo> scheduleComponentInfos = scheduleInfo.getScheduleRequestComponents();
        List<ScheduleRequestComponentDisplayInfo> compDisplayInfos = new ArrayList<ScheduleRequestComponentDisplayInfo>();
        if (scheduleComponentInfos != null) {
            for (ScheduleRequestComponentInfo info: scheduleComponentInfos) {
                ScheduleRequestComponentDisplayInfo convert =
                        schedRequestComp2schedRequestCompDisplay(info, typeService, roomService, schedulingService, contextInfo);
                compDisplayInfos.add(convert);
            }
        }
        displayInfo.setScheduleRequestComponentDisplays(compDisplayInfos);
        // Other fields via inheritance--for completeness (may not be used)
        displayInfo.setId(scheduleInfo.getId());
        displayInfo.setDescr(scheduleInfo.getDescr());
        displayInfo.setAttributes(scheduleInfo.getAttributes());
        displayInfo.setName(scheduleInfo.getName());
        displayInfo.setStateKey(scheduleInfo.getStateKey());
        displayInfo.setTypeKey(scheduleInfo.getTypeKey());
        displayInfo.setMeta(scheduleInfo.getMeta());
        ScheduleRequestSetInfo setInfo = schedulingService.getScheduleRequestSet(scheduleInfo.getScheduleRequestSetId(), contextInfo);

        displayInfo.setRefObjectId(setInfo.getId());
        displayInfo.setRefObjectTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);

        return displayInfo;
    }

    public static ScheduleRequestComponentDisplayInfo schedRequestComp2schedRequestCompDisplay(ScheduleRequestComponentInfo componentInfo,
                        TypeService typeService, RoomService roomService, SchedulingService schedulingService,
                        ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {

        ScheduleRequestComponentDisplayInfo componentDisplayInfo = new ScheduleRequestComponentDisplayInfo();
        // construct the building info
        componentDisplayInfo.setBuildings(roomService.getBuildingsByIds(componentInfo.getBuildingIds(), contextInfo));
        componentDisplayInfo.setRooms(roomService.getRoomsByIds(componentInfo.getRoomIds(), contextInfo));
        componentDisplayInfo.setId(componentInfo.getId());
        componentDisplayInfo.setIsTBA(componentInfo.getIsTBA());
        List<RoomResponsibleOrgInfo> responsibleOrgInfos = roomService.getRoomResponsibleOrgsByIds(componentInfo.getOrgIds(), contextInfo);
        RoomResponsibleOrgInfo orgInfo = responsibleOrgInfos.get(0);
        OrgInfo info = new OrgInfo();
        info.setEffectiveDate(orgInfo.getEffectiveDate());

        componentDisplayInfo.setOrgs(getOrgInsFromRoomResponsibleOrgInfos(responsibleOrgInfos));

        List<String> typeKeys = componentInfo.getResourceTypeKeys();
        List<TypeInfo> typeInfos = typeService.getTypesByKeys(typeKeys, contextInfo);

        componentDisplayInfo.setResourceTypes(typeInfos);
        componentDisplayInfo.setTimeSlots(schedulingService.getTimeSlotsByIds(componentInfo.getTimeSlotIds(), contextInfo));

        return componentDisplayInfo;
    }

    public static List<OrgInfo>  getOrgInsFromRoomResponsibleOrgInfos(List<RoomResponsibleOrgInfo> responsibleOrgInfos) {
        if(null == responsibleOrgInfos) return null;

        List<OrgInfo> orgInfoList = new ArrayList<OrgInfo>();
        for(RoomResponsibleOrgInfo info : responsibleOrgInfos) {
            OrgInfo orgInfo = new OrgInfo();
            if(info != null) {
                orgInfo.setEffectiveDate(info.getEffectiveDate());
                orgInfoList.add(orgInfo);
            }
        }
        return orgInfoList;
    }
}
