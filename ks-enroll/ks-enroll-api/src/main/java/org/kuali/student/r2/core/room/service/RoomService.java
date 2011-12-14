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
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
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
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.util.constants.RoomServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
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
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@WebService(name = "RoomService", targetNamespace = RoomServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface RoomService extends DataDictionaryService, TypeService, StateService {

    /**
     * Retrieves a Room
     *
     * @param roomId      a unique Id of a Room
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Room
     * @throws DoesNotExistException     roomId not found
     * @throws InvalidParameterException invalid roomId or contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RoomInfo getRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given list
     * of Room ids.
     *
     * @param roomIds     list of Rooms to be retrieved
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws DoesNotExistException     a roomId in list not found
     * @throws InvalidParameterException invalid roomId or contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByIds(@WebParam(name = "roomIds") List<String> roomIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid buildingId or contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and Floor key
     *
     * @param buildingId  a unique Id of a Building
     * @param floorKey    floor key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws DoesNotExistException     buildingId or floorKey  not found
     * @throws InvalidParameterException invalid buildingId, floorKey or contextInfo
     * @throws MissingParameterException missing buildingId, floorKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByBuildingAndFloor(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "floorKey") String floorKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the list of Room Usage type
     *
     * @param roomUsageTypeKeys a list of Room Usage type keys
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return a list of Rooms
     * @throws InvalidParameterException invalid roomTypeKey or
     *                                   contextInfo
     * @throws MissingParameterException missing roomTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByRoomUsagesType(@WebParam(name = "roomUsageTypeKeys") List<String> roomUsageTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the Room Type key
     *
     * @param roomTypeKey a Room Type key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws InvalidParameterException invalid roomTypeKey or
     *                                   contextInfo
     * @throws MissingParameterException missing roomTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByRoomType(@WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and Room Type key
     *
     * @param buildingId  a unique Id of a Building
     * @param roomTypeKey a Room Type key
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Rooms
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid buildingId, roomTypeKey or contextInfo
     * @throws MissingParameterException missing buildingId, roomTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByBuildingAndRoomType(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and a list of Room Usage Type keys
     *
     * @param buildingId        a unique Id of a Building
     * @param roomUsageTypeKeys a list of Room Usage Type keys
     * @param contextInfo       Context information containing the
     *                          principalId and locale information about the caller of
     *                          service operation
     * @return a list of Rooms
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid buildingId, roomTypeKey or contextInfo
     * @throws MissingParameterException missing buildingId, roomTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByBuildingAndRoomUsagesType(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomUsageTypeKeys") List<String> roomUsageTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to a list of Resource Type keys
     *
     * @param resourceTypeKeys a list of Resource type keys
     * @param contextInfo      Context information containing the
     *                         principalId and locale information about the caller of
     *                         service operation
     * @return a list of Rooms
     * @throws InvalidParameterException invalid resourceTypeKey or
     *                                   contextInfo
     * @throws MissingParameterException missing resourceTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByResourceTypes(@WebParam(name = "resourceTypeKeys") List<String> resourceTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Rooms corresponding to the given Building id and a list of Resource Type keys
     *
     * @param buildingId       a unique Id of a Building
     * @param resourceTypeKeys a list of Resource Type keys
     * @param contextInfo      Context information containing the
     *                         principalId and locale information about the caller of
     *                         service operation
     * @return a list of Rooms
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid buildingId, resourceTypeKey or contextInfo
     * @throws MissingParameterException missing buildingId, resourceTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomInfo> getRoomsByBuildingAndResourceTypes(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "resourceTypeKeys") List<String> resourceTypeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Rooms based on the criteria and
     * returns a list of Room identifiers which match the search
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
    public List<String> searchForRoomIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<RoomInfo> searchForRooms(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


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
     * @param roomInfo the Room information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid roomInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         roomInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateRoom(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Room
     *
     * @param roomInfo    the details of Room to be created
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return the Room just created
     * @throws AlreadyExistsException       the Room being created already exists
     * @throws DataValidationErrorException one or more values invalid
     *                                      for this operation
     * @throws InvalidParameterException    invalid roomInfo or contextInfo
     * @throws MissingParameterException    missing roomInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public RoomInfo createRoom(@WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

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
     * @throws InvalidParameterException    invalid roomId,
     *                                      roomInfo, or contextInfo
     * @throws MissingParameterException    missing roomId,
     *                                      roomInfo, or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public RoomInfo updateRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Room
     *
     * @param roomId      the Id of the Room to be deleted
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     roomId not found
     * @throws InvalidParameterException invalid roomId or contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a Building
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Building
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid buildingId or contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public BuildingInfo getBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Buildings corresponding to the given list
     * of Building ids.
     *
     * @param buildingIds list of Buildings to be retrieved
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Buildings
     * @throws DoesNotExistException     a buildingId in list not found
     * @throws InvalidParameterException invalid buildingId or contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BuildingInfo> getBuildingByIds(@WebParam(name = "buildingIds") List<String> buildingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Buildings corresponding to the Campus id
     *
     * @param campusKey    a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Buildings
     * @throws DoesNotExistException     campusKey not found
     * @throws InvalidParameterException invalid campusKey or contextInfo
     * @throws MissingParameterException missing campusKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BuildingInfo> getBuildingByCampus(@WebParam(name = "campusKey") String campusKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a Building to which Room belongs
     *
     * @param roomId      a unique Id of a Room
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a Room
     * @throws DoesNotExistException     roomId not found
     * @throws InvalidParameterException invalid roomId or contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public BuildingInfo getBuildingByRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param validationTypeKey the identifier of the extent of validation
     * @param buildingInfo the Building information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid buildingInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         buildingInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateBuilding(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Building
     *
     * @param buildingInfo the details of Building to be created
     * @param contextInfo  Context information containing the
     *                     principalId and locale information about the caller of
     *                     service operation
     * @return the Building just created
     * @throws AlreadyExistsException       the Building being created already exists
     * @throws DataValidationErrorException one or more values invalid
     *                                      for this operation
     * @throws InvalidParameterException    invalid buildingInfo or contextInfo
     * @throws MissingParameterException    missing buildingInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public BuildingInfo createBuilding(@WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

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
     * @throws InvalidParameterException    invalid buildingId,
     *                                      buildingInfo, or contextInfo
     * @throws MissingParameterException    missing buildingId,
     *                                      buildingInfo, or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public BuildingInfo updateBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Building
     *
     * @param buildingId  the Id of the Building to be deleted
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid buildingId or contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a Room Responsible Org
     *
     * @param roomResponsibleOrgKey a unique key of a Room Responsible Org
     * @param contextInfo    Context information containing the
     *                       principalId and locale information about the caller of
     *                       service operation
     * @return a Building
     * @throws DoesNotExistException     roomResponsibleOrgKey not found
     * @throws InvalidParameterException invalid roomResponsibleOrgKey or contextInfo
     * @throws MissingParameterException missing roomResponsibleOrgKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgKey") String roomResponsibleOrgKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the given list
     * of Room ids.
     *
     * @param roomIds     list of Rooms
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Orgs
     * @throws DoesNotExistException     a roomId in list not found
     * @throws InvalidParameterException invalid roomId or contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgByRoomIds(@WebParam(name = "roomIds") List<String> roomIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the Room Responsible Org Type key
     *
     * @param roomResponsibleOrgTypeKey a Room Responsible Org Type key
     * @param contextInfo        Context information containing the
     *                           principalId and locale information about the caller of
     *                           service operation
     * @return a list of Rooms
     * @throws InvalidParameterException invalid roomResponsibleOrgTypeKey or
     *                                   contextInfo
     * @throws MissingParameterException missing roomResponsibleOrgTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByType(@WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the Room id
     *
     * @param roomId      a unique Id of a Room
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Orgs
     * @throws DoesNotExistException     roomId not found
     * @throws InvalidParameterException invalid roomId or contextInfo
     * @throws MissingParameterException missing roomId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Room Responsible Orgs corresponding to the Building id
     *
     * @param buildingId  a unique Id of a Building
     * @param contextInfo Context information containing the
     *                    principalId and locale information about the caller of
     *                    service operation
     * @return a list of Room Responsible Orgs
     * @throws DoesNotExistException     buildingId not found
     * @throws InvalidParameterException invalid buildingId or contextInfo
     * @throws MissingParameterException missing buildingId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param roomResponsibleOrgInfo the Room Responsible Org information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid roomResponsibleOrgInfo, or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         roomResponsibleOrgInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateRoomResponsibleOrg(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @throws MissingParameterException    missing roomId, orgId, roomResponsibleOrgInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(@WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Room Responsible Org
     *
     * @param roomResponsibleOrgKey  the Id of Room Responsible Org to be updated
     * @param roomResponsibleOrgInfo the details of updates to Room Responsible Org being updated
     * @param contextInfo     Context information containing the
     *                        principalId and locale information about the caller of
     *                        service operation
     * @return the details of Room Responsible Org just updated
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        roomResponsibleOrgKey not found
     * @throws InvalidParameterException    invalid roomResponsibleOrgKey,
     *                                      roomResponsibleOrgInfo, or contextInfo
     * @throws MissingParameterException    missing roomResponsibleOrgKey,
     *                                      roomResponsibleOrgInfo, or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgKey") String roomResponsibleOrgKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Room Responsible Org
     *
     * @param roomResponsibleOrgKey the Id of the Room Responsible Org to be deleted
     * @param contextInfo    Context information containing the
     *                       principalId and locale information about the caller of
     *                       service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     roomResponsibleOrgKey not found
     * @throws InvalidParameterException invalid roomResponsibleOrgKey or contextInfo
     * @throws MissingParameterException missing roomResponsibleOrgKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgKey") String roomResponsibleOrgKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
