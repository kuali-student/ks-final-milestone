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
package org.kuali.student.enrollment.class2.room.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
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


    public RoomServiceMockImpl() {
    }


    @Override
    public RoomInfo getRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return createRoomInfo(roomId, "testB1", "testF1", "testCd", "testRoom");
    }

    @Override
    public List<RoomInfo> getRoomsByIds(@WebParam(name = "roomIds") List<String> roomIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomInfo> roomList = new ArrayList<RoomInfo>();
        for(String id : roomIds) {
            RoomInfo info = createRoomInfo(id, "testB1", "testF1", "testCd", "testRoom");
            roomList.add(info);
        }
        return roomList;
    }

    @Override
    public List<String> getRoomIdsByBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 6; i++) {
            String id = buildingId+"Room"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndFloor(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "floor") String floor, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 3; i++) {
            String id = buildingId+floor+"Room"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByType(@WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 2; i++) {
            String id = "roomTypeKeyRoom"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomType(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 3; i++) {
            String id = buildingId+"roomTypeKeyRoom"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<String> getRoomsByBuildingAndRoomUsageTypes(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomUsageTypeKeys") List<String> roomUsageTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 3; i++) {
            String id = buildingId+"roomUsageTypeKeys"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomTypes(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKeys") List<String> roomTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 5; i++) {
            String id = buildingId+"roomTypeKeys"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<String> searchForRoomIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String>  roomIdList = new ArrayList<String>();
        for(int i=1; i < 5; i++) {
            String id = "searchForRoomIds"+i;
            roomIdList.add(id);
        }

        return roomIdList;
    }

    @Override
    public List<RoomInfo> searchForRooms(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomInfo> roomList = new ArrayList<RoomInfo>();
        for(int i = 1; i < 3; i++) {
            RoomInfo info = createRoomInfo("room"+i, "testB1", "testF1", "testCd", "testRoom");
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
    public RoomInfo createRoom(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        RoomInfo info = createRoomInfo("createRoomId1", buildingId, "testFloor1", "createRoomCd","createRoomTest");
        info.setTypeKey(roomTypeKey);
        return info;
    }

    @Override
    public RoomInfo updateRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
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
    public StatusInfo deleteRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo info = new StatusInfo();
        info.setMessage("test room deleted");
        info.setSuccess(true);

        return info;
    }

    @Override
    public BuildingInfo getBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return  createBuildingInfo(buildingId, "testGetBuildingCd","testCompus", "testName1", "Mocked BuildingInfo for the getBuilding method");
    }

    @Override
    public List<BuildingInfo> getBuildingsByIds(@WebParam(name = "buildingIds") List<String> buildingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BuildingInfo> infoList = new ArrayList<BuildingInfo>();
        BuildingInfo info;
        for(String id : buildingIds)   {
            info = createBuildingInfo(id, "testGetBuildingCd","testCompus", "testName1", "Mocked BuildingInfo for the getBuilding method");
            infoList.add(info);
        }
        return infoList;
    }

    @Override
    public List<String> getBuildingIdsByCampus(@WebParam(name = "campusKey") String campusKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> idList = new ArrayList<String>();
        idList.add(campusKey+"Building1");
        idList.add(campusKey+"Building2");
        idList.add(campusKey+"Building3");
        idList.add(campusKey+"Building4");

        return idList;
    }

    @Override
    public List<String> searchForBuildingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> idList = new ArrayList<String>();
        idList.add("testSearchBuilding1");
        idList.add("testSearchBuilding2");
        idList.add("testSearchBuilding3");
        idList.add("testSearchBuilding4");

        return idList;
    }

    @Override
    public List<BuildingInfo> searchForBuildings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BuildingInfo> infoList =  new ArrayList<BuildingInfo>();
        BuildingInfo info;

        info = createBuildingInfo("searchForBuildings1","testCode1", "testCampus", "testName1", "This is building 1 for method searchForBuildings ");
        infoList.add(info);
        info = createBuildingInfo("searchForBuildings2","testCode2", "testCampus", "testName2", "This is building 2 for method searchForBuildings ");
        infoList.add(info);

        return infoList;
    }

    @Override
    public List<ValidationResultInfo> validateBuilding(@WebParam(name = "buildingTypeKey") String buildingTypeKey, @WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public BuildingInfo createBuilding(@WebParam(name = "buildingTypeKey") String buildingTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        BuildingInfo  info = createBuildingInfo("createBuildingID1", "testCode", "testCampus","testBuilding", "createBuilding with buildingTypeKey");
        info.setTypeKey(buildingTypeKey);
        return info;
    }

    @Override
    public BuildingInfo updateBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
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
    public StatusInfo deleteBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo info = new StatusInfo();
        info.setMessage("test deleteBuilding with buildingId");
        info.setSuccess(true);

        return info;
    }

    @Override
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Date effectiveDate = new Date(1344398400000L);
        Date expireDate = new Date(1345176000000L);
        return  createResponsibleInfo("responseID1", "testRoomID", roomResponsibleOrgId, effectiveDate, expireDate);
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(@WebParam(name = "roomResponsibleOrgIds") List<String> roomResponsibleOrgIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomResponsibleOrgInfo>  infoList = new ArrayList<RoomResponsibleOrgInfo>();

        Date effectiveDate = new Date(1344398400000L);
        Date expireDate = new Date(1345176000000L);
        for( String orgId : roomResponsibleOrgIds)  {
            RoomResponsibleOrgInfo info = createResponsibleInfo("responseID1", "testRoomID", orgId, effectiveDate, expireDate);
            infoList.add(info);
        }
        return infoList;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByType(@WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();
        orgIdList.add("testOrgId1");
        orgIdList.add("testOrgId2");
        orgIdList.add("testOrgId3");
        orgIdList.add("testOrgId4");

        return orgIdList;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();
        orgIdList.add(roomId+"testOrgId1");
        orgIdList.add(roomId+"testOrgId2");
        orgIdList.add(roomId+"testOrgId3");
        return orgIdList;
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsForBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();
        orgIdList.add(buildingId+"testOrgId1");
        orgIdList.add(buildingId+"testOrgId2");
        orgIdList.add(buildingId+"testOrgId3");
        return orgIdList;
    }

    @Override
    public List<String> searchForRoomResponsibleOrgIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIdList = new ArrayList<String>();
        orgIdList.add("testSearchOrgId1");
        orgIdList.add("testSearchOrgId2");
        orgIdList.add("testSearchOrgId3");
        return orgIdList;
    }

    @Override
    public List<RoomResponsibleOrgInfo> searchForRoomResponsibleOrgs(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<ValidationResultInfo> validateRoomResponsibleOrg(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(@WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        Date effectiveDate = new Date(1344398400000L);
        Date expireDate = new Date(1345176000000L);
        return createResponsibleInfo("responseID1", roomId, orgId, effectiveDate, expireDate);
     }

    @Override
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
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
    public StatusInfo deleteRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo info = new StatusInfo();
        info.setMessage("test delete RoomResponsibleOrg with roomResponsibleOrgId");
        info.setSuccess(true);

        return info;
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

    private RoomInfo createRoomInfo(String roomId, String buildingId, String floor,  String roomCode, String roomName) {
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
        info.setStateKey("kuali.room.room.state.active");
        info.setTypeKey("kuali.room.type.classroom.general");
        List<RoomUsageInfo> usageInfoList = new ArrayList<RoomUsageInfo>();
        RoomUsageInfo usageInfo = craeteRoomUsageInfo("test1", 100, 80);
        usageInfoList.add(usageInfo);
        usageInfo = craeteRoomUsageInfo("test2", 222, 200);
        usageInfoList.add(usageInfo);
        info.setRoomUsages(usageInfoList);

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
}
