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
 * Created by chongzhu on 8/13/12
 */
package org.kuali.student.r2.core.room.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.dto.RoomUsageInfo;
import org.kuali.student.r2.core.room.service.RoomService;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is the mockup  of the RoomService
 *
 * @author Kuali Student Team
 */

public class RoomServiceMockImpl implements RoomService {
    private static Logger log = Logger.getLogger(RoomServiceMockImpl.class);

    private RoomInfo room1;
    private RoomInfo room2;
    private RoomInfo room3;
    private BuildingInfo building1;
    private BuildingInfo building2;
    private BuildingInfo building3;
    private RoomResponsibleOrgInfo responsibleInfo1;
    private RoomResponsibleOrgInfo responsibleInfo2;
    private RoomResponsibleOrgInfo responsibleInfo3;
    private List<RoomInfo> roomList;
    private List<BuildingInfo> buildingList;
    private List<RoomResponsibleOrgInfo>  responsibleInfoList;


    public RoomServiceMockImpl() {
        roomList = new ArrayList();
        buildingList = new ArrayList();
        createRooms();
        createBuildings();
        createRespOrgs();
    }


    @Override
    public RoomInfo getRoom(String roomId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        for(RoomInfo info : roomList) {
            if(info.getId().equalsIgnoreCase(roomId)) {
                return info;
            }
        }
        throw new DoesNotExistException("No room info found for ID: " + roomId);
    }

    @Override
    public List<RoomInfo> getRoomsByIds(List<String> roomIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomInfo> rooms = new ArrayList<RoomInfo>();
        for(String id : roomIds) {
            boolean found = false;
            for(RoomInfo info : roomList)  {
                if(info.getId().equalsIgnoreCase(id)) {
                    found = true;
                    rooms.add(info);
                }
            }
            if(!found) {
                throw new DoesNotExistException("No room info found for ID: " + id);
            }
        }
        return rooms;
    }

    @Override
    public List<String> getRoomIdsByBuilding(String buildingId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        boolean found = false;
        for(RoomInfo info : roomList) {
            if(info.getBuildingId().equalsIgnoreCase(buildingId)) {
                found = true;
                roomIdList.add(info.getId());
            }
        }
        if(!found) {
            throw new DoesNotExistException("No room info found for building ID: " + buildingId);
        }
        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndFloor( String buildingId, String floor,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        boolean found = false;
        for(RoomInfo info : roomList) {
            if(info.getBuildingId().equalsIgnoreCase(buildingId) && info.getFloor().equalsIgnoreCase(floor)) {
                found = true;
                roomIdList.add(info.getId());
            }
        }
        if(!found) {
            throw new DoesNotExistException("No room info found for building ID: " + buildingId + " and floor: "+ floor);
        }
        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByType(String roomTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(RoomInfo info : roomList) {
            if(info.getTypeKey().equalsIgnoreCase(roomTypeKey) ) {
                roomIdList.add(info.getId());
            }
        }
        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomType( String buildingId, String roomTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        boolean found = false;
        for(RoomInfo info : roomList) {
            if(info.getBuildingId().equalsIgnoreCase(buildingId) && info.getTypeKey().equalsIgnoreCase(roomTypeKey)) {
                found = true;
                roomIdList.add(info.getId());
            }
        }
        if(!found) {
            throw new DoesNotExistException("No room info found for building ID: " + buildingId + " and type: "+ roomTypeKey);
        }
        return roomIdList;
    }

    @Override
    public List<String> getRoomsByBuildingAndRoomUsageTypes( String buildingId, List<String> roomUsageTypeKeys,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> roomIdList = new ArrayList<String>();
        boolean found = false;
        for (RoomInfo info : roomList) {
            if (info.getBuildingId().equalsIgnoreCase(buildingId)) {
                for (String usageKey : roomUsageTypeKeys) {
                    List<RoomUsageInfo> usageList = info.getRoomUsages();
                    for (RoomUsageInfo usage : usageList) {
                        if (usage.getUsageTypeKey().equalsIgnoreCase(usageKey)) {
                            found = true;
                            roomIdList.add(info.getId());
                        }
                    }
                }
            }
        }
        if (!found) {
            throw new DoesNotExistException("No room info found for building ID: " + buildingId + " roomTypeKey");
        }
        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomTypes( String buildingId, List<String> roomTypeKeys,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> roomIdList = new ArrayList<String>();
        boolean found = false;
        for (RoomInfo info : roomList) {
            if (info.getBuildingId().equalsIgnoreCase(buildingId)) {
                for (String type : roomTypeKeys) {
                    if (info.getTypeKey().equalsIgnoreCase(type)) {
                        found = true;
                        roomIdList.add(info.getId());
                    }
                }
            }
        }
        if (!found) {
            throw new DoesNotExistException("No room info found for building ID: " + buildingId + " and roomTypeKeys");
        }
        return roomIdList;
    }

    @Override
    public List<String> searchForRoomIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 5; i++) {
            String id = "searchForRoomIds"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<RoomInfo> searchForRooms(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomInfo> roomList = new ArrayList<RoomInfo>();
        for(int i = 1; i < 3; i++) {
            RoomInfo info = null;
            roomList.add(info);
        }
        return roomList;
    }

    public List<ValidationResultInfo>  validateRoom(String validationTypeKey, String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo) {
        List<ValidationResultInfo> infoList = new ArrayList<ValidationResultInfo>();
        ValidationResultInfo info = new ValidationResultInfo();
        if (roomInfo.getStateKey().equals(validationTypeKey)) {
            info.setElement(validationTypeKey);
            info.setMessage("validationTypeKey");
        }  else {
            info.setError("Invalid validationTypeKey");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);

        info = new ValidationResultInfo();

        if(roomInfo.getBuildingId().equals(buildingId))  {
            info.setElement(buildingId);
            info.setMessage("buildingId");
        } else {
            info.setError("Invalid buildingId");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);

        info = new ValidationResultInfo();

        if(roomInfo.getTypeKey().equals(roomTypeKey))  {
            info.setElement(roomTypeKey);
            info.setMessage("roomTypeKey");
        } else {
            info.setError("Invalid roomTypeKey");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);


        return infoList;
    }

    @Override
    public RoomInfo createRoom( String buildingId, String roomTypeKey,  RoomInfo roomInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        String typeKey = "kuali.room.type.classroom.general";
        String stateKey = "kuali.room.room.state.active";
        String roomId = roomInfo.getId();
        if (StringUtils.isBlank(roomId)){
            roomId = "createRoomId1";
        }
        RoomInfo info = createRoomInfo(roomId, buildingId, "testFloor1", "createRoomCd","createRoomTest", typeKey, stateKey);
        info.setTypeKey(roomTypeKey);
        return info;
    }

    @Override
    public RoomInfo updateRoom(String roomId,  RoomInfo roomInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        RoomInfo info = new RoomInfo();
        info.setId(roomId);
        info.setTypeKey(roomInfo.getTypeKey());
        info.setAccessibilityTypeKeys(roomInfo.getAccessibilityTypeKeys());
        info.setBuildingId(roomInfo.getBuildingId());
        info.setFloor(roomInfo.getFloor());
        info.setRoomCode(roomInfo.getRoomCode());
        info.setRoomUsages(roomInfo.getRoomUsages());
        info.setAttributes(roomInfo.getAttributes());
        info.setRoomFixedResources(roomInfo.getRoomFixedResources());
        info.setStateKey(roomInfo.getStateKey());
        info.setName(roomInfo.getName());
        info.setDescr(roomInfo.getDescr());
        info.setMeta(roomInfo.getMeta());
        return info;
    }

    @Override
    public StatusInfo deleteRoom(String roomId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        RoomInfo room = getRoom(roomId, contextInfo);
        if(room == null)  {
            throw new DoesNotExistException("Room does not exist for ID: "+roomId);
        }
        StatusInfo info = new StatusInfo();
        info.setMessage("room id: " + roomId + " deleted");
        info.setSuccess(true);

        return info;
    }

    @Override
    public BuildingInfo getBuilding(String buildingId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        for(BuildingInfo info : buildingList) {
            if(info.getId().equalsIgnoreCase(buildingId)) {
                return info;
            }
        }

        throw new DoesNotExistException("No building info found for building ID: " + buildingId);
    }

    @Override
    public List<BuildingInfo> getBuildingsByIds(List<String> buildingIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BuildingInfo> infoList = new ArrayList<BuildingInfo>();

        for(String id : buildingIds)   {
            boolean found = false;
            for(BuildingInfo info : buildingList) {
                if(info.getId().equalsIgnoreCase(id)) {
                    found = true;
                    infoList.add(info);
                }
            }
            if(!found) {
                throw new DoesNotExistException("No building exists for ID: " + id);
            }
        }
        return infoList;
    }

    @Override
    public List<String> getBuildingIdsByCampus(String campusKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> idList = new ArrayList<String>();
        boolean found = false;
        for(BuildingInfo info : buildingList) {
            if(info.getCampusKey().equalsIgnoreCase(campusKey)) {
                found = true;
                idList.add(info.getId());
            }
        }
        if(!found) {
            throw new DoesNotExistException("No building exists for campusKey: " + campusKey);
        }

        return idList;
    }

    @Override
    public List<String> searchForBuildingIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> idList = new ArrayList<String>();
        idList.add("testSearchBuilding1");
        idList.add("testSearchBuilding2");
        idList.add("testSearchBuilding3");
        idList.add("testSearchBuilding4");

        return idList;
    }

    @Override
    public List<BuildingInfo> searchForBuildings(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        BuildingInfo info;

        info = createBuildingInfo("searchForBuildings1","testCode1", "testCampus", "testName1", "This is building 1 for method searchForBuildings ");
        info = createBuildingInfo("searchForBuildings2","testCode2", "testCampus", "testName2", "This is building 2 for method searchForBuildings ");

        return buildingList;
    }

    @Override
    public List<ValidationResultInfo> validateBuilding(String buildingTypeKey, String validationTypeKey,BuildingInfo buildingInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> infoList = new ArrayList<ValidationResultInfo>();
        ValidationResultInfo info = new ValidationResultInfo();
        if (buildingInfo.getTypeKey().equals(buildingTypeKey)) {
            info.setElement(buildingTypeKey);
            info.setMessage("validationTypeKey");
        }  else {
            info.setError("Invalid buildingTypeKey");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);

        info = new ValidationResultInfo();

        if(buildingInfo.getStateKey().equals(validationTypeKey))  {
            info.setElement(validationTypeKey);
            info.setMessage("validationTypeKey");
        } else {
            info.setError("Invalid validationTypeKey");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);
        return infoList;
    }

    @Override
    public BuildingInfo createBuilding(String buildingTypeKey,  BuildingInfo buildingInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        String buildingId = buildingInfo.getId();
        if (StringUtils.isBlank(buildingId)){
            buildingId = "createBuildingID1";
        }
        BuildingInfo  info = createBuildingInfo(buildingId, "testCode", "testCampus","testBuilding", "createBuilding with buildingTypeKey");
        info.setTypeKey(buildingTypeKey);
        buildingList.add(info);
        return info;
    }

    @Override
    public BuildingInfo updateBuilding( String buildingId,  BuildingInfo buildingInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        BuildingInfo  info = new BuildingInfo();
        info.setId(buildingId);
        info.setBuildingCode(buildingInfo.getBuildingCode());
        info.setCampusKey(buildingInfo.getCampusKey());
        info.setAttributes(buildingInfo.getAttributes());
        info.setDescr(buildingInfo.getDescr());
        info.setMeta(buildingInfo.getMeta());
        info.setName(buildingInfo.getName());
        info.setStateKey(buildingInfo.getStateKey());
        info.setTypeKey(buildingInfo.getTypeKey());
        return info;
    }

    @Override
    public StatusInfo deleteBuilding( String buildingId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo info = new StatusInfo();
        BuildingInfo building = getBuilding(buildingId, contextInfo);
        if(building == null)  {
            throw new DoesNotExistException("building does not exist for ID: "+buildingId);
        }

        info.setMessage("test deleteBuilding with buildingId" + buildingId);
        info.setSuccess(true);

        return info;
    }

    @Override
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(String roomResponsibleOrgId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        for(RoomResponsibleOrgInfo info : responsibleInfoList) {
            if(info.getId().equalsIgnoreCase(roomResponsibleOrgId)) {
                return info;
            }
        }
        throw new DoesNotExistException("RoomResponsibleOrgInfo does not exist for OrgID: "+roomResponsibleOrgId);
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(List<String> roomResponsibleOrgIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomResponsibleOrgInfo>  infoList = new ArrayList<RoomResponsibleOrgInfo>();

        for(String id : roomResponsibleOrgIds)  {
            boolean found = false;
            for(RoomResponsibleOrgInfo info : responsibleInfoList) {
                if(info.getId().equalsIgnoreCase(id)) {
                    found = true;
                    infoList.add(info);
                }
            }
            if(!found) {
                throw new DoesNotExistException("RoomResponsibleOrgInfo does not exist for OrgID: "+id);
            }
        }
        return infoList;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByType(String roomResponsibleOrgTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();

        for(RoomResponsibleOrgInfo info : responsibleInfoList) {
            if(info.getTypeKey().equalsIgnoreCase(roomResponsibleOrgTypeKey)) {
                orgIdList.add(info.getOrgId());
            }
        }

        return orgIdList;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByRoom(String roomId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();
        boolean found = false;
        for(RoomResponsibleOrgInfo info : responsibleInfoList) {
            if(info.getRoomId().equalsIgnoreCase(roomId)) {
                found = true;
                orgIdList.add(info.getId());
            }
        }
        if(!found) {
            throw new DoesNotExistException("RoomResponsibleOrgInfo does not exist for roomId: "+roomId);
        }

        return orgIdList;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsForBuilding( String buildingId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();
        boolean found = false;
        for(RoomResponsibleOrgInfo info : responsibleInfoList) {
            String bId = getRoom(info.getRoomId(), contextInfo).getBuildingId();
            if(bId.equalsIgnoreCase(buildingId)) {
                found = true;
                orgIdList.add(info.getId());
            }
        }
        if(!found) {
            throw new DoesNotExistException("RoomResponsibleOrgInfo does not exist for buildingId: "+buildingId);
        }
        return orgIdList;
    }

    @Override
    public List<String> searchForRoomResponsibleOrgIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();
        orgIdList.add("testSearchOrgId1");
        orgIdList.add("testSearchOrgId2");
        orgIdList.add("testSearchOrgId3");
        return orgIdList;
    }

    @Override
    public List<RoomResponsibleOrgInfo> searchForRoomResponsibleOrgs(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomResponsibleOrgInfo>  infoList = new ArrayList<RoomResponsibleOrgInfo>();

        Date effectiveDate = new Date(1344398400000L);
        Date expireDate = new Date(1345176000000L);
        for( int i = 10; i < 12; i++)  {
            RoomResponsibleOrgInfo info = createResponsibleInfo("responseID1", "testRoomID", "testOrg"+i, effectiveDate, expireDate);
            infoList.add(info);
        }
        return infoList;
    }

    @Override
    public List<ValidationResultInfo> validateRoomResponsibleOrg(String validationTypeKey,  String roomId,  String orgId,  String roomResponsibleOrgTypeKey,  RoomResponsibleOrgInfo roomResponsibleOrgInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> infoList = new ArrayList<ValidationResultInfo>();
        ValidationResultInfo info = new ValidationResultInfo();
        if (roomResponsibleOrgInfo.getTypeKey().equals(validationTypeKey)) {
            info.setElement(validationTypeKey);
            info.setMessage("validationTypeKey");
        }  else {
            info.setError("Invalid validationTypeKey");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);

        info = new ValidationResultInfo();

        if(roomResponsibleOrgInfo.getRoomId().equals(roomId))  {
            info.setElement(roomId);
            info.setMessage("roomId");
        } else {
            info.setError("Invalid roomId");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);

        info = new ValidationResultInfo();
        if(roomResponsibleOrgInfo.getOrgId().equals(orgId))  {
            info.setElement(orgId);
            info.setMessage("orgId");
        } else {
            info.setError("Invalid orgId");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);

        info = new ValidationResultInfo();
        if(roomResponsibleOrgInfo.getStateKey().equals(roomResponsibleOrgTypeKey))  {
            info.setElement(roomResponsibleOrgTypeKey);
            info.setMessage("roomResponsibleOrgTypeKey");
        } else {
            info.setError("Invalid roomResponsibleOrgTypeKey");
            info.setErrorLevel(ValidationResult.ErrorLevel.ERROR);
        }
        infoList.add(info);
        return infoList;
    }

    @Override
    public RoomResponsibleOrgInfo createRoomResponsibleOrg( String roomId,  String orgId,  String roomResponsibleOrgTypeKey,  RoomResponsibleOrgInfo roomResponsibleOrgInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        Date effectiveDate = new Date(1344398400000L);
        Date expireDate = new Date(1345176000000L);
        return createResponsibleInfo("responseID1", roomId, orgId, effectiveDate, expireDate);
     }

    @Override
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(String roomResponsibleOrgId,  RoomResponsibleOrgInfo roomResponsibleOrgInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        RoomResponsibleOrgInfo info = new RoomResponsibleOrgInfo();
        info.setOrgId(roomResponsibleOrgId);
        info.setRoomId(roomResponsibleOrgInfo.getRoomId());
        info.setAttributes(roomResponsibleOrgInfo.getAttributes());
        info.setEffectiveDate(roomResponsibleOrgInfo.getEffectiveDate());
        info.setExpirationDate(roomResponsibleOrgInfo.getExpirationDate());
        info.setId(roomResponsibleOrgInfo.getId());
        info.setMeta(roomResponsibleOrgInfo.getMeta());
        info.setTypeKey(roomResponsibleOrgInfo.getTypeKey());
        info.setStateKey(roomResponsibleOrgInfo.getStateKey());
        return info;
    }

    @Override
    public StatusInfo deleteRoomResponsibleOrg(String roomResponsibleOrgId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        RoomResponsibleOrgInfo info = getRoomResponsibleOrg(roomResponsibleOrgId, contextInfo);
        if(info == null) {
            throw new DoesNotExistException("RoomResponsibleOrgInfo does not exist for Id: "+roomResponsibleOrgId);
        }
        status.setMessage("test delete RoomResponsibleOrg with roomResponsibleOrgId: " + roomResponsibleOrgId);
        status.setSuccess(true);

        return status;
    }

    private RoomUsageInfo craeteRoomUsageInfo(String id, Integer hardCapacity, Integer preferCapacity) {
        RoomUsageInfo usageInfo = new RoomUsageInfo();

        usageInfo.setId(id);
        usageInfo.setHardCapacity(hardCapacity);
        usageInfo.setLayoutTypeKey("testLayoutTypeKey");
        usageInfo.setUsageTypeKey("usageType");
        usageInfo.setPreferredCapacity(preferCapacity);

        return usageInfo;
    }

    private RoomInfo createRoomInfo(String roomId, String buildingId, String floor,  String roomCode, String roomName, String typeKey, String StateKey) {
        RoomInfo info = new RoomInfo();
        List<String> accessKeys = new ArrayList<String>();
        accessKeys.add("kuali.room.type.classroom.general");
        accessKeys.add("kuali.room.type.classroom.general");

        info.setAccessibilityTypeKeys(accessKeys);
        info.setBuildingId(buildingId);
        info.setFloor(floor);
        info.setRoomCode(roomCode);
        info.setId(roomId);
        info.setName(roomName);
        info.setStateKey(StateKey);
        info.setTypeKey(typeKey);
        List<RoomUsageInfo> usageInfoList = new ArrayList<RoomUsageInfo>();
        RoomUsageInfo usageInfo = craeteRoomUsageInfo("test1", 100, 80);
        usageInfoList.add(usageInfo);
        usageInfo = craeteRoomUsageInfo("test2", 222, 200);
        usageInfoList.add(usageInfo);
        info.setRoomUsages(usageInfoList);

        roomList.add(info);
        return info;
    }

    private BuildingInfo createBuildingInfo (String buildingId, String buildingCode, String compusKey, String buildingName, String desc) {
        BuildingInfo info = new BuildingInfo();
        info.setBuildingCode(buildingCode);
        info.setCampusKey(compusKey);
        info.setId(buildingId);
        info.setName(buildingName);
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain(desc);
        info.setDescr(descr);
        info.setStateKey("kuali.room.building.state.active");
        info.setTypeKey("kuali.room.building.type.test");

        return info;
    }

    private RoomResponsibleOrgInfo createResponsibleInfo(String id, String roomId, String orgId, Date effectiveDate, Date expirationDate) {
        RoomResponsibleOrgInfo info = new RoomResponsibleOrgInfo();

        info.setOrgId(orgId);
        info.setRoomId(roomId);
        info.setId(id);
        info.setStateKey("kuali.room.org.responsibility.state.active");
        info.setTypeKey("kuali.room.org.responsibility.scheduling");
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expirationDate);

        return info;
    }

    private void createRooms() {
        String roomId = "1115097";
        String buildingId = "097";
        String floor = "1";
        String roomCode = "1115";
        String roomName = "CCC 1115";
        String typeKey = "kuali.room.type.classroom.general";
        String stateKey = "kuali.room.room.state.active";
        room1 = createRoomInfo(roomId, buildingId, floor, roomCode, roomName, typeKey, stateKey);
        roomId = "2118406";
        buildingId = "406";
        floor = "2";
        roomCode = "2118";
        roomName = "CSI 2118";
        room2 = createRoomInfo(roomId, buildingId, floor, roomCode, roomName, typeKey, stateKey);
        roomId = "1505039";
        buildingId = "039";
        floor = "1";
        roomCode = "1505";
        roomName = "VMH 1505";
        room3 = createRoomInfo(roomId, buildingId, floor, roomCode, roomName, typeKey, stateKey);
    }

    private void createBuildings() {
        String buildingId = "097";
        String buildingCode = "CCC";
        String campusKey = "MAIN";
        String buildingName = "CAMBRIDGE COMMUNITY CENTER";
        String desc = "CCC - CAMBRIDGE COMMUNITY CENTER";
        building1 = createBuildingInfo(buildingId, buildingCode, campusKey, buildingName, desc);
        buildingId = "406";
        buildingCode = "CSI";
        campusKey = "MAIN";
        buildingName = "COMPUTER SCIENCE INSTRUCTIONAL";
        desc = "CSI - COMPUTER SCIENCE INSTRUCTIONAL";
        building2 = createBuildingInfo(buildingId, buildingCode, campusKey, buildingName, desc);
        buildingId = "039";
        buildingCode = "VMH";
        campusKey = "MAIN";
        buildingName = "VAN MUNCHING HALL";
        desc = "VMH - VAN MUNCHING HALL";
        building3 = createBuildingInfo(buildingId, buildingCode, campusKey, buildingName, desc);
        buildingList = new ArrayList<BuildingInfo>();
        buildingList.add(building1);
        buildingList.add(building2);
        buildingList.add(building3);
    }

    private void createRespOrgs() {
        String id = "1001";
        String roomId = "1115097";
        String orgId = "102";
        Date effectiveDate = new Date(1344398400000L);
        Date expirationDate = new Date(1345176000000L);
        responsibleInfo1 = createResponsibleInfo(id, roomId, orgId, effectiveDate, expirationDate);
        id = "1010";
        roomId = "2118406";
        orgId = "102";
        effectiveDate = new Date(1344398400000L);
        expirationDate = new Date(1345176000000L);
        responsibleInfo2 = createResponsibleInfo(id, roomId, orgId, effectiveDate, expirationDate);
        id = "1168";
        roomId = "1505039";
        orgId = "102";
        effectiveDate = new Date(1344398400000L);
        expirationDate = new Date(1345176000000L);
        responsibleInfo3 = createResponsibleInfo(id, roomId, orgId, effectiveDate, expirationDate);
        responsibleInfoList = new ArrayList<RoomResponsibleOrgInfo>();
        responsibleInfo1.setStateKey("kuali.room.room.state.active");
        responsibleInfo1.setTypeKey("kuali.room.type.classroom.general");
        responsibleInfo2.setStateKey("kuali.room.room.state.active");
        responsibleInfo2.setTypeKey("kuali.room.type.classroom.general");
        responsibleInfo3.setStateKey("kuali.room.room.state.active");
        responsibleInfo3.setTypeKey("kuali.room.type.classroom.general");

        responsibleInfoList.add(responsibleInfo1);
        responsibleInfoList.add(responsibleInfo2);
        responsibleInfoList.add(responsibleInfo3);
    }

    @Override
    public List<BuildingInfo> getBuildingsByBuildingCode(String buildingCode,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("getBuildingsByBuildingCode has not been implemented");
    }

    @Override
    public List<RoomInfo> getRoomsByBuildingAndRoomCode(String buildingCode, String roomCode,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("getRoomsByBuildingAndRoomCode has not been implemented");
    }

}
