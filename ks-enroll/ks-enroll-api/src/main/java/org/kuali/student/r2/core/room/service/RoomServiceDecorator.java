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
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;

import javax.jws.WebParam;
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
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedRefObjectURI") String relatedRefObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relationTypeKey") String relationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }

    @Override
    public RoomInfo getRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoom(roomId, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByIds(@WebParam(name = "roomIds") List<String> roomIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByIds(roomIds, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByBuilding(buildingId, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByBuildingAndFloor(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "floorKey") String floorKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByBuildingAndFloor(buildingId, floorKey, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByRoomUsagesType(@WebParam(name = "roomUsageTypeKeys") List<String> roomUsageTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByRoomUsagesType(roomUsageTypeKeys, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByRoomType(@WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByRoomType(roomTypeKey, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByBuildingAndRoomType(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByBuildingAndRoomType(buildingId, roomTypeKey, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByBuildingAndRoomUsagesType(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomUsageTypeKeys") List<String> roomUsageTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByBuildingAndRoomUsagesType(buildingId, roomUsageTypeKeys, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByResourceTypes(@WebParam(name = "resourceTypeKeys") List<String> resourceTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByResourceTypes(resourceTypeKeys, contextInfo);
    }

    @Override
    public List<RoomInfo> getRoomsByBuildingAndResourceTypes(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "resourceTypeKeys") List<String> resourceTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomsByBuildingAndResourceTypes(buildingId, resourceTypeKeys, contextInfo);
    }

    @Override
    public List<String> searchForRoomIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRoomIds(criteria, contextInfo);
    }

    @Override
    public List<RoomInfo> searchForRooms(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRooms(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateRoom(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.validateRoom(validationTypeKey, roomInfo, contextInfo);
    }

    @Override
    public RoomInfo createRoom(@WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRoom(roomInfo, contextInfo);
    }

    @Override
    public RoomInfo updateRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateRoom(roomId, roomInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRoom(roomId, contextInfo);
    }

    @Override
    public BuildingInfo getBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuilding(buildingId, contextInfo);
    }

    @Override
    public List<BuildingInfo> getBuildingByIds(@WebParam(name = "buildingIds") List<String> buildingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingByIds(buildingIds, contextInfo);
    }

    @Override
    public List<BuildingInfo> getBuildingByCampus(@WebParam(name = "campusId") String campusId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingByCampus(campusId, contextInfo);
    }

    @Override
    public BuildingInfo getBuildingByRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getBuildingByRoom(roomId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateBuilding(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.validateBuilding(validationTypeKey, buildingInfo, contextInfo);
    }

    @Override
    public BuildingInfo createBuilding(@WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createBuilding(buildingInfo, contextInfo);
    }

    @Override
    public BuildingInfo updateBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateBuilding(buildingId, buildingInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteBuilding(buildingId, contextInfo);
    }

    @Override
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgKey") String roomResponsibleOrgKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrg(roomResponsibleOrgKey, contextInfo);
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgByRoomIds(@WebParam(name = "roomIds") List<String> roomIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgByRoomIds(roomIds, contextInfo);
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByType(@WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgsByType(roomResponsibleOrgTypeKey, contextInfo);
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgsByRoom(roomId, contextInfo);
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRoomResponsibleOrgsByBuilding(buildingId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateRoomResponsibleOrg(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.validateRoomResponsibleOrg(validationTypeKey, roomResponsibleOrgInfo, contextInfo);
    }

    @Override
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(@WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return this.createRoomResponsibleOrg(roomId, orgId, roomResponsibleOrgTypeKey, roomResponsibleOrgInfo, contextInfo);
    }

    @Override
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgKey") String roomResponsibleOrgKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateRoomResponsibleOrg(roomResponsibleOrgKey, roomResponsibleOrgInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgKey") String roomResponsibleOrgKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRoomResponsibleOrg(roomResponsibleOrgKey, contextInfo);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(@WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return getNextDecorator().getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(@WebParam(name = "entryKey") String entryKey, @WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return getNextDecorator().getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public StateProcessInfo getProcessByKey(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByKey(processKey, context);
    }

    @Override
    public List<String> getProcessByObjectType(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByObjectType(refObjectUri, context);
    }

    @Override
    public StateInfo getState(@WebParam(name = "processKey") String processKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getState(processKey, stateKey, context);
    }

    @Override
    public List<StateInfo> getStatesByProcess(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getStatesByProcess(processKey, context);
    }

    @Override
    public List<StateInfo> getInitialValidStates(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(@WebParam(name = "processKey") String processKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getNextHappyState(processKey, currentStateKey, context);
    }
}
