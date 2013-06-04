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
 * Created by Gordon on 11/01/12
 */
package org.kuali.student.r2.core.room.service.impl;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.room.dao.BuildingServiceDao;
import org.kuali.student.r2.core.room.dao.RoomResponsibleOrgDao;
import org.kuali.student.r2.core.room.dao.RoomServiceDao;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.model.RoomBuildingEntity;
import org.kuali.student.r2.core.room.model.RoomEntity;
import org.kuali.student.r2.core.room.model.RoomResponsibleOrgEntity;
import org.kuali.student.r2.core.room.service.RoomService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Room Service
 *
 * @author Kuali Student Team
 */
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomServiceDao roomServiceDao;

    @Resource
    private BuildingServiceDao buildingServiceDao;

    @Resource
    private RoomResponsibleOrgDao roomResponsibleOrgDao;

    private CriteriaLookupService criteriaLookupService;

    /**
     * Retrieves a Room
     *
     * @param roomId      a unique Id of a Room
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Room
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public RoomInfo getRoom(String roomId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("id", roomId, contextInfo);

        RoomEntity e = roomServiceDao.find(roomId);
        if (e == null) {
            throw new DoesNotExistException("Missing data for : '" + roomId + "'");
        }

        return e.toDto();
    }

    /**
     * Retrieve a list of Rooms corresponding to the given list of Room Ids.
     *
     * @param roomIds     list of Rooms to be retrieved
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a roomId in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomInfo> getRoomsByIds(List<String> roomIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("id", roomIds, contextInfo);

        List<RoomEntity> rooms = roomServiceDao.findRoomsByIds(roomIds);
        List<RoomInfo> roomInfos = new ArrayList<RoomInfo>(rooms.size());
        for (RoomEntity room : rooms) {
            roomInfos.add(room.toDto());
        }

        return roomInfos;
    }

    /**
     * Retrieves a list of Rooms corresponding to the given Building id
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomIdsByBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("buildingId", buildingId, contextInfo);

        return roomServiceDao.findRoomIdsByBuilding(buildingId);
    }

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and Floor
     *
     * @param buildingId  a unique Id of a Building
     * @param floor       floor
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid floor, contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId, floor or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomIdsByBuildingAndFloor(String buildingId, String floor, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("buildingId", buildingId, "floor", floor, contextInfo);

        return roomServiceDao.findRoomIdsByBuildingAndFloor(buildingId, floor);
    }

    /**
     * Retrieves a list of Rooms corresponding to the Room Type key
     *
     * @param roomTypeKey a Room Type key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomIdsByType(String roomTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("roomType", roomTypeKey, contextInfo);

        return roomServiceDao.findRoomIdsByType(roomTypeKey);
    }

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and Room Type key
     *
     * @param buildingId  a unique Id of a Building
     * @param roomTypeKey a Room Type key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId, roomTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomIdsByBuildingAndRoomType(String buildingId, String roomTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("buildingId", buildingId, "roomType", roomTypeKey, contextInfo);

        return roomServiceDao.findRoomIdsByBuildingAndRoomType(buildingId, roomTypeKey);
    }

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and a list of Room Usage Type keys
     *
     * @param buildingId        a unique Id of a Building
     * @param roomUsageTypeKeys a list of Room Usage Type keys
     * @param contextInfo       Context information containing the
     *                          principalId and locale information about the caller of
     *                          service operation
     * @return a list of Room Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId, roomUsageTypeKeys or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomsByBuildingAndRoomUsageTypes(String buildingId, List<String> roomUsageTypeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        /*
        This method is not implemented because there is no Room Usage Type in the schema, yet
         */
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and a list of Resource Type keys
     *
     * @param buildingId   a unique Id of a Building
     * @param roomTypeKeys a list of Room Type keys
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return a list of Room Keys
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId, roomTypeKeys or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomIdsByBuildingAndRoomTypes(String buildingId, List<String> roomTypeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("buildingId", buildingId, "roomType", roomTypeKeys, contextInfo);

        return roomServiceDao.findRoomIdsByBuildingAndRoomTypes(buildingId, roomTypeKeys);
    }

    /**
     * Searches for Rooms based on the criteria and
     * returns a list of Room identifiers which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Room Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> searchForRoomIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        /*
        Not implementing QueryByCriteria, yet
         */
        throw new UnsupportedOperationException();
    }

    /**
     * Searches for Rooms based on the criteria and
     * returns a list of Rooms which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Rooms
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomInfo> searchForRooms(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        /*
        Not implementing QueryByCriteria, yet
         */
        throw new UnsupportedOperationException();
    }

    /**
     * Validates a Room. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the Process and a record is found for that
     * identifier, the validation checks if the Process can be shifted
     * to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as
     * such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the
     * standard validation as the caller provides the identifier in
     * the create statement instead of the server assigning an
     * identifier.
     *
     * @param validationTypeKey the identifier of the extent of validation
     * @param buildingId        Id of the building the room is in
     * @param roomTypeKey       a Room Type key
     * @param roomInfo          the Room information to be tested
     * @param contextInfo       Context information containing the
     *                          principalId and locale information about the caller of
     *                          service operation
     * @return Results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid roomInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey,
     *          roomInfo, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateRoom(String validationTypeKey, String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    /**
     * Creates a new Room from roomInfo, if provided, then overriding buildingId and roomType, if values are provided.
     *
     * @param buildingId  Id of the building the room is in
     * @param roomTypeKey a Room Type key
     * @param roomInfo    the details of Room to be created
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return the Room just created
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          the Room being created already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid
     *          for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid roomInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read-only
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {AlreadyExistsException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public RoomInfo createRoom(String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        checkValid(roomInfo, contextInfo);

        RoomEntity roomEntity = new RoomEntity(roomInfo); //create Entity from Info
        if (roomEntity.getId() != null && roomEntity.getId().isEmpty())
            roomEntity.setId(null); //ensure no id prior to persist
        roomEntity.setEntityCreated(contextInfo); //set MetaInfo
        //override buildingId and roomTypeKey if they're provided
        if (buildingId != null && !buildingId.isEmpty()) {
            roomEntity.setBuildingId(buildingId);
        }
        if (roomTypeKey != null && !roomTypeKey.isEmpty()) {
            roomEntity.setRoomType(roomTypeKey);
        }

        roomServiceDao.persist(roomEntity);

        return roomEntity.toDto();
    }

    /**
     * Updates an existing Room
     *
     * @param roomId      the Id of Room to be updated, not used
     * @param roomInfo    the details of updates to Room being updated
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return the details of Room just updated
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid
     *          for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid roomInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomId,
     *          roomInfo, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public RoomInfo updateRoom(String roomId, RoomInfo roomInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        checkValid(roomInfo, contextInfo);

        RoomEntity roomEntity = new RoomEntity(roomInfo);
        roomEntity.setEntityUpdated(contextInfo);
        roomServiceDao.update(roomEntity);

        return roomEntity.toDto();
    }

    /**
     * Deletes an existing Room
     *
     * @param roomId      the Id of the Room to be deleted
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteRoom(String roomId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("id", roomId, contextInfo);

        RoomEntity room = roomServiceDao.find(roomId);

        roomServiceDao.remove(room);

        return new StatusInfo(); //StatusInfo defaults to true, if we reached this point we should be successful
    }

    /**
     * Retrieves a Building
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Building
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public BuildingInfo getBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("id", buildingId, contextInfo);

        RoomBuildingEntity e = buildingServiceDao.find(buildingId);
        if (e == null) {
            throw new DoesNotExistException("Missing data for : " + buildingId);
        }

        return e.toDto();
    }

    /**
     * Retrieves a list of Buildings corresponding to the given list
     * of Building Ids.
     *
     * @param buildingIds list of Buildings to be retrieved
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Buildings
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a buildingId in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingInfo> getBuildingsByIds(List<String> buildingIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("id", buildingIds, contextInfo);

        return convertBuildingList(buildingServiceDao.findBuildingsByIds(buildingIds));
    }

    /**
     * Retrieves a list of Buildings corresponding to the Campus id
     *
     * @param campusKey   a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Building Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          campusKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing campusKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getBuildingIdsByCampus(String campusKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("campusKey", campusKey, contextInfo);

        return buildingServiceDao.findBuildingIdsByCampus(campusKey);
    }

    /**
     * Searches for Buildings based on the criteria and
     * returns a list of Building Ids which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Building Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> searchForBuildingIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches for Buildings based on the criteria and
     * returns a list of Buildings which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Buildings
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingInfo> searchForBuildings(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BuildingInfo> buildingInfos = new ArrayList<BuildingInfo>();
        GenericQueryResults<RoomBuildingEntity> results = criteriaLookupService.lookup(RoomBuildingEntity.class, criteria);
        if (null != results && results.getResults().size() > 0) {
            for (RoomBuildingEntity building : results.getResults()) {
                buildingInfos.add(building.toDto());
            }
        }
        return buildingInfos;
    }

    /**
     * Validates a Building. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the Process and a record is found for that
     * identifier, the validation checks if the Process can be shifted
     * to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as
     * such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the
     * standard validation as the caller provides the identifier in
     * the create statement instead of the server assigning an
     * identifier.
     *
     * @param buildingTypeKey   Building Type key
     * @param validationTypeKey the identifier of the extent of validation
     * @param buildingInfo      the Building information to be tested
     * @param contextInfo       Context information containing the
     *                          principalId and locale information about the caller of
     *                          service operation
     * @return Results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingTypeKey, validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid buildingInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey,
     *          buildingInfo, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateBuilding(@WebParam(name = "buildingTypeKey") String buildingTypeKey, @WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    /**
     * Creates a new Building
     *
     * @param buildingTypeKey Building Type key
     * @param buildingInfo    the details of Building to be created
     * @param contextInfo     Context information containing the
     *                        principalId and locale information about the caller of
     *                        service operation
     * @return the Building just created
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          the Building being created already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid
     *          for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingTypeKey does not exist or is not supported
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid buildingInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read-only
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {AlreadyExistsException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public BuildingInfo createBuilding(String buildingTypeKey, BuildingInfo buildingInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        checkValid(buildingInfo, contextInfo);

        RoomBuildingEntity buildingEntity = new RoomBuildingEntity(buildingInfo); //construct Entity from Info
        if (buildingEntity.getId() != null && buildingEntity.getId().isEmpty())
            buildingEntity.setId(null); //ensure no id
        //override typeKey if specified
        if (buildingTypeKey != null && !buildingTypeKey.isEmpty()) {
            buildingEntity.setBuildingType(buildingTypeKey);
        }
        buildingEntity.setEntityCreated(contextInfo);

        buildingServiceDao.persist(buildingEntity);

        return buildingEntity.toDto();
    }

    /**
     * Updates an existing Building
     *
     * @param buildingId   the Id of Building to be updated
     * @param buildingInfo the details of updates to Building being updated
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return the details of Building just updated
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid
     *          for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid buildingInfo, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId,
     *          buildingInfo, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public BuildingInfo updateBuilding(String buildingId, BuildingInfo buildingInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        checkValid(buildingInfo, contextInfo);

        RoomBuildingEntity buildingEntity = new RoomBuildingEntity(buildingInfo);
        buildingEntity.setEntityUpdated(contextInfo);
        buildingServiceDao.update(buildingEntity);

        return buildingEntity.toDto();
    }

    /**
     * Deletes an existing Building
     *
     * @param buildingId  the Id of the Building to be deleted
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("id", buildingId, contextInfo);

        RoomBuildingEntity buildingEntity = buildingServiceDao.find(buildingId);
        if (buildingEntity == null) {
            throw new DoesNotExistException("Missing data for : " + buildingId);
        }
        buildingServiceDao.remove(buildingEntity);

        return new StatusInfo();
    }

    /**
     * Retrieves a Room Responsible Org
     *
     * @param roomResponsibleOrgId a unique Id of a Room Responsible Org
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the caller of
     *                             service operation
     * @return a Room Responsible Org
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomResponsibleOrgId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomResponsibleOrgId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(String roomResponsibleOrgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("roomResponsibleOrgId", roomResponsibleOrgId, contextInfo);

        return roomResponsibleOrgDao.find(roomResponsibleOrgId).toDto();
    }

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the given list
     * of Room Ids.
     *
     * @param roomResponsibleOrgIds list of Room Responsible Ids
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the caller of
     *                              service operation
     * @return a list of Room Responsible Orgs
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a roomResponsibleOrgId in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomResponsibleOrgIds or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(List<String> roomResponsibleOrgIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the Room Responsible Org Type key
     *
     * @param roomResponsibleOrgTypeKey a Room Responsible Org Type key
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about the caller of
     *                                  service operation
     * @return a list of Room Responsible Org Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomResponsibleOrgTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomResponsibleOrgIdsByType(String roomResponsibleOrgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves a list of Room Responsible Org Ids corresponding to the Room id
     *
     * @param roomId      a unique Id of a Room
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Org Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomResponsibleOrgIdsByRoom(String roomId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("roomId", roomId, contextInfo);
        return roomResponsibleOrgDao.findRoomResponsibleOrgIdsByRoom(roomId);
    }

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the Building id
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Org Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          buildingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getRoomResponsibleOrgIdsForBuilding(String buildingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches for Room Responsible Orgs based on the criteria and
     * returns a list of Room Responsible Org identifiers which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Room Responsible Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> searchForRoomResponsibleOrgIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches for Room Responsible Orgs based on the criteria and
     * returns a list of Room Responsible Orgs which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Room Responsible Orgs
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomResponsibleOrgInfo> searchForRoomResponsibleOrgs(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    /**
     * Validates a RoomResponsibleOrg. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the Process and a record is found for that
     * identifier, the validation checks if the Process can be shifted
     * to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as
     * such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the
     * standard validation as the caller provides the identifier in
     * the create statement instead of the server assigning an
     * identifier.
     *
     * @param validationTypeKey         the identifier of the extent of validation
     * @param roomId                    room Id
     * @param orgId                     org Id
     * @param roomResponsibleOrgTypeKey Type of Room Responsible Org
     * @param roomResponsibleOrgInfo    the Room Responsible Org information to be tested
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about the caller of
     *                                  service operation
     * @return Results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomId, orgId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid roomResponsibleOrgInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, roomId, orgId,
     *          roomResponsibleOrgInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateRoomResponsibleOrg(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    /**
     * Creates a new Room Responsible Org
     *
     * @param roomId                    room identifier
     * @param orgId                     org identifier
     * @param roomResponsibleOrgTypeKey Type of Room Responsible Org
     * @param roomResponsibleOrgInfo    the details of Room Responsible Org to be created
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about the caller of
     *                                  service operation
     * @return the Room Responsible Org just created
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          the Room Responsible Org being created already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid
     *          for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomId or orgId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid roomResponsibleOrgInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomId, orgId, roomResponsibleOrgTypeKey, roomResponsibleOrgInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read-only
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {AlreadyExistsException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(String roomId,  String orgId,  String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        checkValid(roomResponsibleOrgInfo, contextInfo);
        RoomResponsibleOrgEntity e = new RoomResponsibleOrgEntity(roomResponsibleOrgInfo);

        if (roomId != null && !roomId.isEmpty()) {
            e.setRoomId( roomId );
        }

        if (orgId != null && !orgId.isEmpty()) {
            e.setOrgId( orgId );
        }

        if (roomResponsibleOrgTypeKey != null && !roomResponsibleOrgTypeKey.isEmpty()) {
            e.setRespOrgType( roomResponsibleOrgTypeKey );
        }

        e.setEntityCreated( contextInfo );

        roomResponsibleOrgDao.persist(e);

        return e.toDto();
    }

    /**
     * Updates an existing Room Responsible Org
     *
     * @param roomResponsibleOrgId   the Id of Room Responsible Org to be updated
     * @param roomResponsibleOrgInfo the details of updates to Room Responsible Org being updated
     * @param contextInfo            Context information containing the
     *                               principalId and locale information about the caller of
     *                               service operation
     * @return the details of Room Responsible Org just updated
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid
     *          for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomResponsibleOrgId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid roomResponsibleOrgInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomResponsibleOrgId,
     *          roomResponsibleOrgInfo, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(String roomResponsibleOrgId, RoomResponsibleOrgInfo roomResponsibleOrgInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        checkValid(roomResponsibleOrgInfo, contextInfo);
        RoomResponsibleOrgEntity e = new RoomResponsibleOrgEntity(roomResponsibleOrgInfo);

        if (roomResponsibleOrgId != null && !roomResponsibleOrgId.isEmpty()) {
            e.setId( roomResponsibleOrgId );
        }

        e.setEntityUpdated(contextInfo);

        roomResponsibleOrgDao.update(e);

        return e.toDto();
    }

    /**
     * Deletes an existing Room Responsible Org
     *
     * @param roomResponsibleOrgId the Id of the Room Responsible Org to be deleted
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the caller of
     *                             service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          roomResponsibleOrgId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing roomResponsibleOrgId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class, InvalidParameterException.class, MissingParameterException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteRoomResponsibleOrg(String roomResponsibleOrgId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("roomResponsibleOrgId", roomResponsibleOrgId, contextInfo);

        RoomResponsibleOrgEntity e = roomResponsibleOrgDao.find(roomResponsibleOrgId);

        roomResponsibleOrgDao.remove(e);

        return new StatusInfo();
    }

    /**
     * Retrieves a list of Buildings by a building code.
     *
     * @param buildingCode the building code of the buildings to be retrieved.
     * @param contextInfo  Context information containing the principalId and
     *                     locale information about the caller of service operation.
     * @return a list of Buildings that have the building code as the given building code. The multiplicity
     *         is due to the fact that the service doesn't describe unique constraints on data fields and
     *         the codes could possibly be duplicated, although we don't want them to be in real life.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the building code is not found for any building.
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingCode or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingInfo> getBuildingsByBuildingCode(String buildingCode, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("buildingCode", buildingCode, contextInfo);

        return convertBuildingList(buildingServiceDao.findBuildingsByBuildingCode(buildingCode));
    }

    /**
     * Retrieves a list of Rooms by a building and room code.
     *
     * @param buildingCode the building code of the rooms to be retrieved.
     * @param roomCode     the room code of the rooms to be retrieved.
     * @param contextInfo  Context information containing the principalId and
     *                     locale information about the caller of service operation.
     * @return a list of rooms that have the building code as the given building code and the given room code
     *         as the room code. The multiplicity is due to the fact that the service doesn't describe unique
     *         constraints on data fields and the codes could possibly be duplicated, although we don't want them
     *         to be in real life. Room Codes are only unique within a building so its lookup should include the
     *         building.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the building code, room code combination is not found for any room.
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing buildingCode, roomCode or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomInfo> getRoomsByBuildingAndRoomCode(String buildingCode, String roomCode, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkValid("buildingCode", buildingCode, "roomCode", roomCode, contextInfo);

        List<String> buildingIds = buildingServiceDao.findBuildingIdsByBuildingCode(buildingCode);
        if (buildingIds.isEmpty()) {
            throw new DoesNotExistException("Missing data for buildingCode: " + buildingCode);
        }

        return convertRoomList( roomServiceDao.findRoomsByCodeAndBuilding(roomCode, buildingIds) );
    }

    private void checkValid(String name0, String value0, String name1, List<String> values1, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException {
        checkContext(contextInfo);

        checkKey(name0, value0);
        checkKey(name1, values1);
    }

    private void checkValid(String keyName, String keyValue, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException {
        checkContext(contextInfo);

        checkKey(keyName, keyValue);
    }

    private void checkValid(String keyName, List<String> keys, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException {
        checkContext(contextInfo);

        checkKey(keyName, keys);
    }

    private void checkValid(Object o, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException {
        checkContext(contextInfo);

        if (o == null) {
            throw new MissingParameterException("Object is missing!");
        }
    }

    private void checkValid(String keyName0, String keyValue0, String keyName1, String keyValue1, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException {
        checkContext(contextInfo);

        checkKey(keyName0, keyValue0);
        checkKey(keyName1, keyValue1);
    }

    private void checkContext(ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException {
        if (contextInfo == null) {
            throw new MissingParameterException("contextInfo is null!");
        }

        if (contextInfo.getPrincipalId() == null || contextInfo.getPrincipalId().isEmpty()) {
            throw new InvalidParameterException("principalId is missing!");
        }

        if (contextInfo.getCurrentDate() == null) {
            throw new InvalidParameterException("currentDate is missing!");
        }
    }

    private void checkKey(String name, String value) throws MissingParameterException {
        if (value == null || value.isEmpty()) {
            throw new MissingParameterException(name + " key is missing!");
        }
    }

    private void checkKey(String name, List<String> values) throws InvalidParameterException, MissingParameterException {
        if (values == null || values.isEmpty()) {
            throw new MissingParameterException(name + " list is null!");
        }

        if (values.contains(null) || values.contains("")) {
            throw new InvalidParameterException(name + " list has invalid values! " + values);
        }
    }

    private List<RoomInfo> convertRoomList(List<RoomEntity> rooms) {
        List<RoomInfo> result = new ArrayList<RoomInfo>(rooms.size());

        for (RoomEntity room : rooms) {
            result.add(room.toDto());
        }

        return result;
    }

    private List<BuildingInfo> convertBuildingList(List<RoomBuildingEntity> buildings) {
        List<BuildingInfo> result = new ArrayList<BuildingInfo>(buildings.size());

        for (RoomBuildingEntity building : buildings) {
            result.add(building.toDto());
        }

        return result;
    }


    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    public RoomServiceDao getRoomServiceDao() {
        return roomServiceDao;
    }

    public BuildingServiceDao getBuildingServiceDao() {
        return buildingServiceDao;
    }

    public RoomResponsibleOrgDao getRoomResponsibleOrgDao() {
        return roomResponsibleOrgDao;
    }

    public void setRoomServiceDao(RoomServiceDao roomServiceDao) {
        this.roomServiceDao = roomServiceDao;
    }

    public void setBuildingServiceDao(BuildingServiceDao buildingServiceDao) {
        this.buildingServiceDao = buildingServiceDao;
    }

    public void setRoomResponsibleOrgDao(RoomResponsibleOrgDao roomResponsibleOrgDao) {
        this.roomResponsibleOrgDao = roomResponsibleOrgDao;
    }


}
