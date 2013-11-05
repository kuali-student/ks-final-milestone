/*
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

package org.kuali.student.r2.core.room.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.PartitionInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;

import java.util.List;

/**
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class RoomServiceDecorator implements RoomService {

    private RoomService nextDecorator;

    public RoomService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(RoomService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public RoomInfo getRoom(String roomId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoom(roomId, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByIds(List<String> roomIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByIds(roomIds, contextInfo);
    }

    @Override
    public List<String> getRoomIdsByBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomIdsByBuilding(buildingId, contextInfo);
    }

    @Override
    public List<String> getRoomIdsByBuildingAndFloor(String buildingId, String floor, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomIdsByBuildingAndFloor(buildingId, floor, contextInfo);
    }

    @Override
    public List<String> getRoomIdsByType(String roomTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomIdsByType(roomTypeKey, contextInfo);
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomType(String buildingId, String roomTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomIdsByBuildingAndRoomType(buildingId, roomTypeKey, contextInfo);
    }

    @Override
    public List<String> getRoomsByBuildingAndRoomUsageTypes(String buildingId, List<String> roomUsageTypeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByBuildingAndRoomUsageTypes(buildingId, roomUsageTypeKeys, contextInfo);
    }

    @Override
    public List<String> getRoomIdsByBuildingAndRoomTypes(String buildingId, List<String> roomTypeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomIdsByBuildingAndRoomTypes(buildingId, roomTypeKeys, contextInfo);
    }

    @Override
    public List<String> searchForRoomIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRoomIds(criteria, contextInfo);
    }

    @Override
    public List<RoomInfo> searchForRooms(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRooms(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateRoom(String validationTypeKey, String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateRoom(validationTypeKey, buildingId, roomTypeKey, roomInfo, contextInfo);
    }

    @Override
    public RoomInfo createRoom(String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRoom(buildingId, roomTypeKey, roomInfo, contextInfo);
    }

    @Override
    public RoomInfo updateRoom(String roomId, RoomInfo roomInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateRoom(roomId, roomInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteRoom(String roomId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRoom(roomId, contextInfo);
    }

    @Override
    public BuildingInfo getBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuilding(buildingId, contextInfo);
    }

    @Override
    public List<BuildingInfo> getBuildingsByIds(List<String> buildingIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingsByIds(buildingIds, contextInfo);
    }

    @Override
    public List<String> getBuildingIdsByCampus(String campusKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingIdsByCampus(campusKey, contextInfo);
    }

    @Override
    public List<String> searchForBuildingIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForBuildingIds(criteria, contextInfo);
    }

    @Override
    public List<BuildingInfo> searchForBuildings(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForBuildings(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateBuilding(String buildingTypeKey, String validationTypeKey, BuildingInfo buildingInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateBuilding(buildingTypeKey, validationTypeKey, buildingInfo, contextInfo);
    }

    @Override
    public BuildingInfo createBuilding(String buildingTypeKey, BuildingInfo buildingInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createBuilding(buildingTypeKey, buildingInfo, contextInfo);
    }

    @Override
    public BuildingInfo updateBuilding(String buildingId, BuildingInfo buildingInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateBuilding(buildingId, buildingInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteBuilding(buildingId, contextInfo);
    }

    @Override
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(String roomResponsibleOrgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrg(roomResponsibleOrgId, contextInfo);
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(List<String> roomResponsibleOrgIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgsByIds(roomResponsibleOrgIds, contextInfo);
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByType(String roomResponsibleOrgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgIdsByType(roomResponsibleOrgTypeKey, contextInfo);
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsByRoom(String roomId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgIdsByRoom(roomId, contextInfo);
    }

    @Override
    public List<String> getRoomResponsibleOrgIdsForBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgIdsForBuilding(buildingId, contextInfo);
    }

    @Override
    public List<String> searchForRoomResponsibleOrgIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRoomResponsibleOrgIds(criteria, contextInfo);
    }

    @Override
    public List<RoomResponsibleOrgInfo> searchForRoomResponsibleOrgs(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRoomResponsibleOrgs(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateRoomResponsibleOrg(String validationTypeKey, String roomId, String orgId, String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateRoomResponsibleOrg(validationTypeKey, roomId, orgId, roomResponsibleOrgTypeKey, roomResponsibleOrgInfo, contextInfo);
    }

    @Override
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(String roomId, String orgId, String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRoomResponsibleOrg(roomId, orgId, roomResponsibleOrgTypeKey, roomResponsibleOrgInfo, contextInfo);
    }

    @Override
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(String roomResponsibleOrgId, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateRoomResponsibleOrg(roomResponsibleOrgId, roomResponsibleOrgInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteRoomResponsibleOrg(String roomResponsibleOrgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRoomResponsibleOrg(roomResponsibleOrgId, contextInfo);
    }

    @Override
    public List<BuildingInfo> getBuildingsByBuildingCode(String buildingCode, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingsByBuildingCode(buildingCode, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByBuildingAndRoomCode(String buildingCode, String roomCode, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByBuildingAndRoomCode(buildingCode, roomCode, contextInfo);
    }

    @Override
    public StatusInfo removeRoomFromPartition(String partitionId, String roomId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeRoomFromPartition(partitionId, roomId, contextInfo);
    }

    @Override
    public StatusInfo addRoomToPartition(String partitionId, String roomId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addRoomToPartition(partitionId, roomId, contextInfo);
    }

    @Override
    public StatusInfo removeBuildingFromPartition(String partitionId, String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeBuildingFromPartition(partitionId, buildingId, contextInfo);
    }

    @Override
    public StatusInfo addBuildingToPartition(String partitionId, String buildingId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addBuildingToPartition(partitionId, buildingId, contextInfo);
    }

    @Override
    public List<PartitionInfo> getPartitionsByRoom(String roomId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPartitionsByRoom(roomId, contextInfo);
    }

    @Override
    public List<String> getPartitionIdsByRoom(String roomId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPartitionIdsByRoom(roomId, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByPartition(String partitionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByPartition(partitionId, contextInfo);
    }

    @Override
    public List<String> getRoomIdsByPartition(String partitionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomIdsByPartition(partitionId, contextInfo);
    }

    @Override
    public List<PartitionInfo> getPartitionsByBuilding(String buildingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPartitionsByBuilding(buildingId, contextInfo);
    }

    @Override
    public List<String> getPartitionIdsByBuilding(String buildingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPartitionIdsByBuilding(buildingId, contextInfo);
    }

    @Override
    public List<BuildingInfo> getBuildingsByPartition(String partitionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingsByPartition(partitionId, contextInfo);
    }

    @Override
    public List<String> getBuildingIdsByPartition(String partitionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingIdsByPartition(partitionId, contextInfo);
    }

    @Override
    public StatusInfo deletePartition(String partitionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deletePartition(partitionId, contextInfo);
    }

    @Override
    public PartitionInfo updatePartition(String partitionId, PartitionInfo partitionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updatePartition(partitionId, partitionInfo, contextInfo);
    }

    @Override
    public PartitionInfo createPartition(String partitionTypeKey, PartitionInfo partitionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createPartition(partitionTypeKey, partitionInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePartition(String partitionTypeKey, String validationTypeKey, PartitionInfo partitionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validatePartition(partitionTypeKey, validationTypeKey, partitionInfo, contextInfo);
    }

    @Override
    public List<PartitionInfo> searchForPartitions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForPartitions(criteria, contextInfo);
    }

    @Override
    public List<String> searchForPartitionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForPartitionIds(criteria, contextInfo);
    }

    @Override
    public List<String> getPartitionIdsByType(String partitionTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPartitionIdsByType(partitionTypeKey, contextInfo);
    }

    @Override
    public List<PartitionInfo> getPartitionsByIds(List<String> partitionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPartitionsByIds(partitionIds, contextInfo);
    }

    @Override
    public PartitionInfo getPartition(String partitionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPartition(partitionId, contextInfo);
    }

    @Override
    public List<String> getBuildingIdsByType(String buildingTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingIdsByType(buildingTypeKey, contextInfo);
    }
}
