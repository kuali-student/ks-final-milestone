/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.room.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
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
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.PartitionInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 *
 * Room Service Description and Assumptions
 *
 * If a room is e62-221, the Building code is "e62" and the Room code is "221".
 * The Room will always be referenced by the internal Room Id and not the code
 * when used by other KS services.
 *
 * @version 0.0.7
 * @Author Sri komandur@uw.edu
 * @Author Mezba mezba.mahtab@utoronto.ca
 */
@WebService(name = "RoomService", targetNamespace = RoomServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface RoomService {

    ////////////////////
    // ROOM
    ////////////////////

    // basic lookup methods
    // --------------------------

    /**
     * Retrieves a Room
     *
     * @param roomId      a unique Id of a Room
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Room
     * @throws DoesNotExistException     roomId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RoomInfo getRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given list
     * of Room Ids.
     *
     * @param roomIds     list of Rooms to be retrieved
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws DoesNotExistException     a roomId in list not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByIds(@WebParam(name = "roomIds") List<String> roomIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the Room Type key
     *
     * @param roomTypeKey a Room Type key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomIdsByType(@WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // other lookup methods
    // --------------------------

    /**
     * Retrieves a list of Rooms corresponding to the given Building id
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomIdsByBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and Floor
     *
     * @param buildingId  a unique Id of a Building
     * @param floor    floor
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid floor, contextInfo
     * @throws MissingParameterException missing buildingId, floor or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomIdsByBuildingAndFloor(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "floor") String floor, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and Room Type key
     *
     * @param buildingId  a unique Id of a Building
     * @param roomTypeKey a Room Type key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId, roomTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomIdsByBuildingAndRoomType(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and a list of Room Usage Type keys
     *
     * @param buildingId        a unique Id of a Building
     * @param roomUsageTypeKeys a list of Room Usage Type keys
     * @param contextInfo       Context information containing the
     *                          principalId and locale information about the caller of
     *                          service operation
     * @return a list of Room Ids
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId, roomUsageTypeKeys or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomsByBuildingAndRoomUsageTypes(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomUsageTypeKeys") List<String> roomUsageTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and a list of Resource Type keys
     *
     * @param buildingId       a unique Id of a Building
     * @param roomTypeKeys     a list of Room Type keys
     * @param contextInfo      Context information containing the
     *                         principalId and locale information about the caller of
     *                         service operation
     * @return a list of Room Keys
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId, roomTypeKeys or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomIdsByBuildingAndRoomTypes(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKeys") List<String> roomTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms by a building and room code.
     *
     * @param buildingCode the building code of the rooms to be retrieved.
     * @param roomCode the room code of the rooms to be retrieved.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service operation.
     * @return a list of rooms that have the building code as the given building code and the given room code
     *          as the room code. The multiplicity is due to the fact that the service doesn't describe unique
     *          constraints on data fields and the codes could possibly be duplicated, although we don't want them
     *          to be in real life. Room Codes are only unique within a building so its lookup should include the
     *          building.
     * @throws DoesNotExistException the building code, room code combination is not found for any room.
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingCode, roomCode or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByBuildingAndRoomCode (@WebParam(name = "buildingCode") String buildingCode, @WebParam(name = "roomCode") String roomCode, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // basic search methods
    // --------------------------

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
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForRoomIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

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
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> searchForRooms(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    // CRUD methods
    // --------------------------

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
     * @param buildingId Id of the building the room is in
     * @param roomTypeKey a Room Type key
     * @param roomInfo the Room information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey, buildingId not found
     * @throws InvalidParameterException invalid roomInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         roomInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateRoom(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Room
     *
     * @param buildingId Id of the building the room is in
     * @param roomTypeKey a Room Type key
     * @param roomInfo    the details of Room to be created
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return the Room just created
     * @throws AlreadyExistsException       the Room being created already exists
     * @throws DataValidationErrorException one or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        buildingId does not exist
     * @throws InvalidParameterException    invalid roomInfo or contextInfo
     * @throws MissingParameterException    missing roomInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public RoomInfo createRoom(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Room
     *
     * @param roomId      the Id of Room to be updated
     * @param roomInfo    the details of updates to Room being updated
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return the details of Room just updated
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        roomId not found
     * @throws InvalidParameterException    invalid roomInfo or contextInfo
     * @throws MissingParameterException    missing roomId,
     *                                      roomInfo, or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public RoomInfo updateRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Room
     *
     * @param roomId      the Id of the Room to be deleted
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     roomId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    ////////////////////
    // BUILDING
    ////////////////////

    // basic lookup methods
    // --------------------------

    /**
     * Retrieves a Building
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Building
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public BuildingInfo getBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Buildings corresponding to the given list
     * of Building Ids.
     *
     * @param buildingIds list of Buildings to be retrieved
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Buildings
     * @throws DoesNotExistException     a buildingId in list not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BuildingInfo> getBuildingsByIds(@WebParam(name = "buildingIds") List<String> buildingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Buildings corresponding to the Building Type key
     *
     * @param buildingTypeKey a Building Type key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Building Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBuildingIdsByType(@WebParam(name = "buildingTypeKey") String buildingTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // other lookup methods
    // --------------------------

    /**
     * Retrieves a list of Buildings corresponding to the Campus id
     *
     * @param campusKey    a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Building Ids
     * @throws DoesNotExistException     campusKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing campusKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBuildingIdsByCampus(@WebParam(name = "campusKey") String campusKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Buildings by a building code.
     *
     * @param buildingCode the building code of the buildings to be retrieved.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service operation.
     * @return a list of Buildings that have the building code as the given building code. The multiplicity
     *          is due to the fact that the service doesn't describe unique constraints on data fields and
     *          the codes could possibly be duplicated, although we don't want them to be in real life.
     * @throws DoesNotExistException the building code is not found for any building.
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingCode or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BuildingInfo> getBuildingsByBuildingCode (@WebParam(name = "buildingCode") String buildingCode, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // basic search methods
    // --------------------------

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
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForBuildingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

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
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BuildingInfo> searchForBuildings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // crud methods
    // --------------------------

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
     * @param buildingTypeKey Building Type key
     * @param validationTypeKey the identifier of the extent of validation
     * @param buildingInfo the Building information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException buildingTypeKey, validationTypeKey not found
     * @throws InvalidParameterException invalid buildingInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         buildingInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateBuilding(@WebParam(name = "buildingTypeKey") String buildingTypeKey, @WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Building
     *
     * @param buildingTypeKey Building Type key
     * @param buildingInfo the details of Building to be created
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return the Building just created
     * @throws AlreadyExistsException       the Building being created already exists
     * @throws DataValidationErrorException one or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        buildingTypeKey does not exist or is not supported
     * @throws InvalidParameterException    invalid buildingInfo or contextInfo
     * @throws MissingParameterException    missing buildingInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public BuildingInfo createBuilding(@WebParam(name = "buildingTypeKey") String buildingTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Building
     *
     * @param buildingId   the Id of Building to be updated
     * @param buildingInfo the details of updates to Building being updated
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return the details of Building just updated
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        buildingId not found
     * @throws InvalidParameterException    invalid buildingInfo, or contextInfo
     * @throws MissingParameterException    missing buildingId,
     *                                      buildingInfo, or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public BuildingInfo updateBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Building
     *
     * @param buildingId  the Id of the Building to be deleted
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////////////////////////////////
    // ROOM RESPONSIBLE ORG
    ////////////////////////////////

    // basic lookup methods
    // --------------------------

    /**
     * Retrieves a Room Responsible Org
     *
     * @param roomResponsibleOrgId a unique Id of a Room Responsible Org
     * @param contextInfo    Context information containing the
     *                       principalId and locale information about the caller of
     *                       service operation
     * @return a Room Responsible Org
     * @throws DoesNotExistException     roomResponsibleOrgId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomResponsibleOrgId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the given list
     * of Room Ids.
     *
     * @param roomResponsibleOrgIds     list of Room Responsible Ids
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Orgs
     * @throws DoesNotExistException     a roomResponsibleOrgId in list not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomResponsibleOrgIds or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(@WebParam(name = "roomResponsibleOrgIds") List<String> roomResponsibleOrgIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the Room Responsible Org Type key
     *
     * @param roomResponsibleOrgTypeKey a Room Responsible Org Type key
     * @param contextInfo        Context information containing the
     *                           principalId and locale information about the caller of
     *                           service operation
     * @return a list of Room Responsible Org Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomResponsibleOrgTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomResponsibleOrgIdsByType(@WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // other lookup methods
    // --------------------------

    /**
     * Retrieves a list of Room Responsible Org Ids corresponding to the Room id
     *
     * @param roomId      a unique Id of a Room
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Org Ids
     * @throws DoesNotExistException     roomId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomResponsibleOrgIdsByRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the Building id
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Org Ids
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRoomResponsibleOrgIdsForBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // basic search methods
    // --------------------------

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
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForRoomResponsibleOrgIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

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
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomResponsibleOrgInfo> searchForRoomResponsibleOrgs(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // crud methods
    // --------------------------

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
     * @param validationTypeKey the identifier of the extent of validation
     * @param roomId   room Id
     * @param orgId    org Id
     * @param roomResponsibleOrgTypeKey Type of Room Responsible Org
     * @param roomResponsibleOrgInfo the Room Responsible Org information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException roomId, orgId not found
     * @throws InvalidParameterException invalid roomResponsibleOrgInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey, roomId, orgId,
     *         roomResponsibleOrgInfo or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateRoomResponsibleOrg(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Room Responsible Org
     *
     * @param roomId room identifier
     * @param orgId  org identifier
     * @param roomResponsibleOrgTypeKey Type of Room Responsible Org
     * @param roomResponsibleOrgInfo the details of Room Responsible Org to be created
     * @param contextInfo     Context information containing the
     *                        principalId and locale information about the caller of
     *                        service operation
     * @return the Room Responsible Org just created
     * @throws AlreadyExistsException       the Room Responsible Org being created already exists
     * @throws DataValidationErrorException one or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        roomId or orgId not found
     * @throws InvalidParameterException    invalid roomResponsibleOrgInfo or contextInfo
     * @throws MissingParameterException    missing roomId, orgId, roomResponsibleOrgTypeKey, roomResponsibleOrgInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(@WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Room Responsible Org
     *
     * @param roomResponsibleOrgId  the Id of Room Responsible Org to be updated
     * @param roomResponsibleOrgInfo the details of updates to Room Responsible Org being updated
     * @param contextInfo     Context information containing the
     *                        principalId and locale information about the caller of
     *                        service operation
     * @return the details of Room Responsible Org just updated
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        roomResponsibleOrgId not found
     * @throws InvalidParameterException    invalid roomResponsibleOrgInfo or contextInfo
     * @throws MissingParameterException    missing roomResponsibleOrgId,
     *                                      roomResponsibleOrgInfo, or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Room Responsible Org
     *
     * @param roomResponsibleOrgId the Id of the Room Responsible Org to be deleted
     * @param contextInfo    Context information containing the
     *                       principalId and locale information about the caller of
     *                       service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     roomResponsibleOrgId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing roomResponsibleOrgId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////////////////////
    // PARTITION
    ////////////////////

    // basic lookup methods
    // --------------------------

    /**
     * Retrieves a Partition
     *
     * @param partitionId a unique Id of a Partition
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Partition
     * @throws DoesNotExistException     partitionId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing partitionId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public PartitionInfo getPartition(@WebParam(name = "partitionId") String partitionId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Partitions corresponding to the given list
     * of Partition Ids.
     *
     * @param partitionIds list of Partitions to be retrieved
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Partitions
     * @throws DoesNotExistException     a PartitionId in list not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing partitionId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<PartitionInfo> getPartitionsByIds(@WebParam(name = "partitionIds") List<String> partitionIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Partitions of the given Type.
     *
     * @param partitionTypeKey a type of a Partition
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Partition Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException partitionTypeKey or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getPartitionIdsByType(@WebParam(name = "partitionTypeKey") String partitionTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // other lookup methods
    // --------------------------

    /**
     * Retrieves a list of Building Ids of Buildings that are in the given Partition.
     *
     * @param partitionId the Id of the Partition whose buildings are to be obtained
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Building Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException partitionId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<String> getBuildingIdsByPartition (String partitionId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Buildings that are in the given Partition.
     *
     * @param partitionId the Id of the Partition whose buildings are to be obtained
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Buildings
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException partitionId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<BuildingInfo> getBuildingsByPartition (String partitionId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Partition Ids of Partitions that contain the given Building.
     *
     * @param buildingId the Id of the Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Partition Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException buildingId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<String> getPartitionIdsByBuilding (String buildingId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Partitions that contain the given Building.
     *
     * @param buildingId the Id of the Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Partitions
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException buildingId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<PartitionInfo> getPartitionsByBuilding (String buildingId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Room Ids of Rooms that are in the given Partition.
     *
     * @param partitionId the Id of the Partition whose rooms are to be obtained
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException partitionId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<String> getRoomIdsByPartition (String partitionId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rooms that are in the given Partition.
     *
     * @param partitionId the Id of the Partition whose rooms are to be obtained
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException partitionId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<RoomInfo> getRoomsByPartition (String partitionId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Partition Ids of Partitions that contain the given Room.
     *
     * @param roomId the Id of the Room.
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Partition Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException roomId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<String> getPartitionIdsByRoom (String roomId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Partitions that contain the given Room.
     *
     * @param roomId the Id of the Room.
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Partitions
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException roomId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<PartitionInfo> getPartitionsByRoom (String roomId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // basic search methods
    // --------------------------

    /**
     * Searches for Partitions based on the criteria and
     * returns a list of Partition Ids which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Partition Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForPartitionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Partitions based on the criteria and
     * returns a list of Partitions which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return list of Partitions
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<PartitionInfo> searchForPartitions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // crud methods
    // --------------------------

    /**
     * Validates a Partition. Depending on the value of validationType,
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
     * @param partitionTypeKey Partition Type key
     * @param validationTypeKey the identifier of the extent of validation
     * @param partitionInfo the Partition information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException partitionTypeKey, validationTypeKey not found
     * @throws InvalidParameterException invalid partitionInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         partitionInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validatePartition(@WebParam(name = "partitionTypeKey") String partitionTypeKey,
                                                        @WebParam(name = "validationTypeKey") String validationTypeKey,
                                                        @WebParam(name = "partitionInfo") PartitionInfo partitionInfo,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Partition
     *
     * @param partitionTypeKey Partition Type key
     * @param partitionInfo the details of Partition to be created
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return the Partition just created
     * @throws DataValidationErrorException one or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        partitionTypeKey does not exist or is not supported
     * @throws InvalidParameterException    invalid partitionInfo or contextInfo
     * @throws MissingParameterException    missing partitionInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public PartitionInfo createPartition(@WebParam(name = "partitionTypeKey") String partitionTypeKey, @WebParam(name = "partitionInfo") PartitionInfo partitionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Partition
     *
     * @param partitionId   the Id of Partition to be updated
     * @param partitionInfo the details of updates to Partition being updated
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return the details of Partition just updated
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        partitionId not found
     * @throws InvalidParameterException    invalid partitionInfo, or contextInfo
     * @throws MissingParameterException    missing partitionId,
     *                                      partitionInfo, or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public PartitionInfo updatePartition(@WebParam(name = "partitionId") String partitionId, @WebParam(name = "partitionInfo") PartitionInfo partitionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Partition
     *
     * @param partitionId  the Id of the Partition to be deleted
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     partitionId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing partitionId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deletePartition(@WebParam(name = "partitionId") String partitionId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    // other methods
    // --------------------------

    /**
     * Adds a Building to a Partition.
     *
     * @param partitionId       an identifier for a Partition
     * @param buildingId        an identifier for a Building
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the status of the operation. This must always be true.
     * @throws AlreadyExistsException    The Building is already mapped to the Partition
     * @throws DoesNotExistException     partitionId or buildingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException partitionId, buildingId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl A RoomService implementation can be done several ways, one of which is that
     * all data (rooms, buildings, partitions, scheduling) remains on an external scheduler
     * or facility management system, and KS just pulls data out or pushes data in via the
     * RoomService implementation calling an API published by this external system. In that
     * case this method would not be updating any KS data but data across on the other system.
     * In such a situation, this method may not be needed if all maintenance is done away from
     * the KS side. It could also be that the implementation DOES store some data on the KS
     * side (and somehow the two are kept in sync with some rules); in that case this method would
     * touch KS data as well.
     */
    public StatusInfo addBuildingToPartition(@WebParam(name = "partitionId") String partitionId,
                                             @WebParam(name = "buildingId") String buildingId,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Removes a Building from a Partition.
     *
     * @param partitionId       an identifier for a Partition
     * @param buildingId        an identifier for a Building
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     partitionId or buildingId is not
     *                                   found or buildingId is not mapped to
     *                                   partitionId
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException partitionId, buildingId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl A RoomService implementation can be done several ways, one of which is that
     * all data (rooms, buildings, partitions, scheduling) remains on an external scheduler
     * or facility management system, and KS just pulls data out or pushes data in via the
     * RoomService implementation calling an API published by this external system. In that
     * case this method would not be updating any KS data but data across on the other system.
     * In such a situation, this method may not be needed if all maintenance is done away from
     * the KS side. It could also be that the implementation DOES store some data on the KS
     * side (and somehow the two are kept in sync with some rules); in that case this method would
     * touch KS data as well.
     */
    public StatusInfo removeBuildingFromPartition(@WebParam(name = "partitionId") String partitionId,
                                                  @WebParam(name = "buildingId") String buildingId,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Adds a Room to a Partition.
     *
     * @param partitionId       an identifier for a Partition
     * @param roomId            an identifier for a Room
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the status of the operation. This must always be true.
     * @throws AlreadyExistsException    The Room is already mapped to the Partition
     * @throws DoesNotExistException     partitionId or roomId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException partitionId, roomId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl A RoomService implementation can be done several ways, one of which is that
     * all data (rooms, buildings, partitions, scheduling) remains on an external scheduler
     * or facility management system, and KS just pulls data out or pushes data in via the
     * RoomService implementation calling an API published by this external system. In that
     * case this method would not be updating any KS data but data across on the other system.
     * In such a situation, this method may not be needed if all maintenance is done away from
     * the KS side. It could also be that the implementation DOES store some data on the KS
     * side (and somehow the two are kept in sync with some rules); in that case this method would
     * touch KS data as well.
     */
    public StatusInfo addRoomToPartition(@WebParam(name = "partitionId") String partitionId,
                                         @WebParam(name = "roomId") String roomId,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Removes a Room from a Partition.
     *
     * @param partitionId       an identifier for a Partition
     * @param roomId            an identifier for a Room
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     partitionId or roomId is not
     *                                   found or roomId is not mapped to
     *                                   partitionId
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException partitionId, roomId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl A RoomService implementation can be done several ways, one of which is that
     * all data (rooms, buildings, partitions, scheduling) remains on an external scheduler
     * or facility management system, and KS just pulls data out or pushes data in via the
     * RoomService implementation calling an API published by this external system. In that
     * case this method would not be updating any KS data but data across on the other system.
     * In such a situation, this method may not be needed if all maintenance is done away from
     * the KS side. It could also be that the implementation DOES store some data on the KS
     * side (and somehow the two are kept in sync with some rules); in that case this method would
     * touch KS data as well.
     */
    public StatusInfo removeRoomFromPartition(@WebParam(name = "partitionId") String partitionId,
                                              @WebParam(name = "roomId") String roomId,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}
