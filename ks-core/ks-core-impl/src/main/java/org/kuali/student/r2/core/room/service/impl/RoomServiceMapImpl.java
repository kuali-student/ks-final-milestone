/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.room.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.PartitionInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomService;


public class RoomServiceMapImpl implements MockService, RoomService
{
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, RoomInfo> roomMap = new LinkedHashMap<String, RoomInfo>();
	private Map<String, BuildingInfo> buildingMap = new LinkedHashMap<String, BuildingInfo>();
	private Map<String, RoomResponsibleOrgInfo> roomResponsibleOrgMap = new LinkedHashMap<String, RoomResponsibleOrgInfo>();
	private Map<String, PartitionInfo> partitionMap = new LinkedHashMap<String, PartitionInfo>();

	@Override
	public void clear()
	{
		this.roomMap.clear ();
		this.buildingMap.clear ();
		this.roomResponsibleOrgMap.clear ();
		this.partitionMap.clear ();
	}

	
	@Override
	public RoomInfo getRoom(String roomId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.roomMap.containsKey(roomId)) {
		   throw new DoesNotExistException(roomId);
		}
		return new RoomInfo(this.roomMap.get (roomId));
	}
	
	@Override
	public List<RoomInfo> getRoomsByIds(List<String> roomIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<RoomInfo> list = new ArrayList<RoomInfo> ();
		for (String id: roomIds) {
		    list.add (this.getRoom(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getRoomIdsByType(String roomTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (RoomInfo info: roomMap.values ()) {
			if (roomTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<String> getRoomIdsByBuilding(String buildingId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (RoomInfo info: roomMap.values ()) {
			if (buildingId.equals(info.getBuildingId())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<String> getRoomIdsByBuildingAndFloor(String buildingId, String floor, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (RoomInfo info: roomMap.values ()) {
			if (buildingId.equals(info.getBuildingId())) {
				if (floor.equals(info.getFloor())) {
				    list.add (info.getId ());
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> getRoomIdsByBuildingAndRoomType(String buildingId, String roomTypeKey, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (RoomInfo info: roomMap.values ()) {
			if (buildingId.equals(info.getBuildingId())) {
				if (roomTypeKey.equals(info.getTypeKey())) {
				    list.add (info.getId ());
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> getRoomsByBuildingAndRoomUsageTypes(String buildingId, List<String> roomUsageTypeKeys, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (RoomInfo info: roomMap.values ()) {
			if (buildingId.equals(info.getBuildingId())) {
				if (roomUsageTypeKeys.equals(info.getRoomUsages())) {
				    list.add (info.getId ());
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> getRoomIdsByBuildingAndRoomTypes(String buildingId, List<String> roomTypeKeys, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (RoomInfo info: roomMap.values ()) {
			if (buildingId.equals(info.getBuildingId())) {
				if (roomTypeKeys.equals(info.getAccessibilityTypeKeys())) {
				    list.add (info.getId ());
				}
			}
		}
		return list;
	}
	
	@Override
	public List<RoomInfo> getRoomsByBuildingAndRoomCode(String buildingCode, String roomCode, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<RoomInfo> list = new ArrayList<RoomInfo> ();
		for (RoomInfo info: roomMap.values ()) {
            BuildingInfo buildingInfo = this.getBuilding(info.getBuildingId(), contextInfo);
			if (buildingCode.equals(buildingInfo.getBuildingCode())) {
				if (roomCode.equals(info.getRoomCode())) {
				    list.add (new RoomInfo(info));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> searchForRoomIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForRoomIds has not been implemented");
	}
	
	@Override
	public List<RoomInfo> searchForRooms(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForRooms has not been implemented");
	}
	
	@Override
	public List<ValidationResultInfo> validateRoom(String validationTypeKey, String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	@Override
	public RoomInfo createRoom(String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo contextInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		if (!roomTypeKey.equals (roomInfo.getTypeKey())) {
		    throw new InvalidParameterException ("The type parameter does not match the type on the info object");
		}
		RoomInfo copy = new RoomInfo(roomInfo);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		roomMap.put(copy.getId(), copy);
		return new RoomInfo(copy);
	}
	
	@Override
	public RoomInfo updateRoom(String roomId, RoomInfo roomInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!roomId.equals (roomInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		RoomInfo copy = new RoomInfo(roomInfo);
		RoomInfo old = this.getRoom(roomInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.roomMap .put(roomInfo.getId(), copy);
		return new RoomInfo(copy);
	}
	
	@Override
	public StatusInfo deleteRoom(String roomId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.roomMap.remove(roomId) == null) {
		   throw new OperationFailedException(roomId);
		}
		return newStatus();
	}
	
	@Override
	public BuildingInfo getBuilding(String buildingId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.buildingMap.containsKey(buildingId)) {
		   throw new DoesNotExistException(buildingId);
		}
		return new BuildingInfo(this.buildingMap.get (buildingId));
	}
	
	@Override
	public List<BuildingInfo> getBuildingsByIds(List<String> buildingIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<BuildingInfo> list = new ArrayList<BuildingInfo> ();
		for (String id: buildingIds) {
		    list.add (this.getBuilding(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getBuildingIdsByType(String buildingTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (BuildingInfo info: buildingMap.values ()) {
			if (buildingTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<String> getBuildingIdsByCampus(String campusKey, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (BuildingInfo info: buildingMap.values ()) {
			if (campusKey.equals(info.getCampusId())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<BuildingInfo> getBuildingsByBuildingCode(String buildingCode, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<BuildingInfo> list = new ArrayList<BuildingInfo> ();
		for (BuildingInfo info: buildingMap.values ()) {
			if (buildingCode.equals(info.getBuildingCode())) {
			    list.add (new BuildingInfo(info));
			}
		}
		return list;
	}
	
	@Override
	public List<String> searchForBuildingIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForBuildingIds has not been implemented");
	}
	
	@Override
	public List<BuildingInfo> searchForBuildings(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForBuildings has not been implemented");
	}
	
	@Override
	public List<ValidationResultInfo> validateBuilding(String buildingTypeKey, String validationTypeKey, BuildingInfo buildingInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	@Override
	public BuildingInfo createBuilding(String buildingTypeKey, BuildingInfo buildingInfo, ContextInfo contextInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		if (!buildingTypeKey.equals (buildingInfo.getTypeKey())) {
		    throw new InvalidParameterException ("The type parameter does not match the type on the info object");
		}
		BuildingInfo copy = new BuildingInfo(buildingInfo);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		buildingMap.put(copy.getId(), copy);
		return new BuildingInfo(copy);
	}
	
	@Override
	public BuildingInfo updateBuilding(String buildingId, BuildingInfo buildingInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!buildingId.equals (buildingInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		BuildingInfo copy = new BuildingInfo(buildingInfo);
		BuildingInfo old = this.getBuilding(buildingInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.buildingMap .put(buildingInfo.getId(), copy);
		return new BuildingInfo(copy);
	}
	
	@Override
	public StatusInfo deleteBuilding(String buildingId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.buildingMap.remove(buildingId) == null) {
		   throw new OperationFailedException(buildingId);
		}
		return newStatus();
	}
	
	@Override
	public RoomResponsibleOrgInfo getRoomResponsibleOrg(String roomResponsibleOrgId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.roomResponsibleOrgMap.containsKey(roomResponsibleOrgId)) {
		   throw new DoesNotExistException(roomResponsibleOrgId);
		}
		return new RoomResponsibleOrgInfo(this.roomResponsibleOrgMap.get (roomResponsibleOrgId));
	}
	
	@Override
	public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(List<String> roomResponsibleOrgIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<RoomResponsibleOrgInfo> list = new ArrayList<RoomResponsibleOrgInfo> ();
		for (String id: roomResponsibleOrgIds) {
		    list.add (this.getRoomResponsibleOrg(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getRoomResponsibleOrgIdsByType(String roomResponsibleOrgTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (RoomResponsibleOrgInfo info: roomResponsibleOrgMap.values ()) {
			if (roomResponsibleOrgTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<String> getRoomResponsibleOrgIdsByRoom(String roomId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (RoomResponsibleOrgInfo info: roomResponsibleOrgMap.values ()) {
			if (roomId.equals(info.getRoomId())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<String> getRoomResponsibleOrgIdsForBuilding(String buildingId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("getRoomResponsibleOrgIdsForBuilding has not been implemented");
	}
	
	@Override
	public List<String> searchForRoomResponsibleOrgIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForRoomResponsibleOrgIds has not been implemented");
	}
	
	@Override
	public List<RoomResponsibleOrgInfo> searchForRoomResponsibleOrgs(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForRoomResponsibleOrgs has not been implemented");
	}
	
	@Override
	public List<ValidationResultInfo> validateRoomResponsibleOrg(String validationTypeKey, String roomId, String orgId, String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	@Override
	public RoomResponsibleOrgInfo createRoomResponsibleOrg(String roomId, String orgId, String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		if (!roomResponsibleOrgTypeKey.equals (roomResponsibleOrgInfo.getTypeKey())) {
		    throw new InvalidParameterException ("The type parameter does not match the type on the info object");
		}
		RoomResponsibleOrgInfo copy = new RoomResponsibleOrgInfo(roomResponsibleOrgInfo);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		roomResponsibleOrgMap.put(copy.getId(), copy);
		return new RoomResponsibleOrgInfo(copy);
	}
	
	@Override
	public RoomResponsibleOrgInfo updateRoomResponsibleOrg(String roomResponsibleOrgId, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!roomResponsibleOrgId.equals (roomResponsibleOrgInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		RoomResponsibleOrgInfo copy = new RoomResponsibleOrgInfo(roomResponsibleOrgInfo);
		RoomResponsibleOrgInfo old = this.getRoomResponsibleOrg(roomResponsibleOrgInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.roomResponsibleOrgMap .put(roomResponsibleOrgInfo.getId(), copy);
		return new RoomResponsibleOrgInfo(copy);
	}
	
	@Override
	public StatusInfo deleteRoomResponsibleOrg(String roomResponsibleOrgId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.roomResponsibleOrgMap.remove(roomResponsibleOrgId) == null) {
		   throw new OperationFailedException(roomResponsibleOrgId);
		}
		return newStatus();
	}
	
	@Override
	public PartitionInfo getPartition(String partitionId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.partitionMap.containsKey(partitionId)) {
		   throw new DoesNotExistException(partitionId);
		}
		return new PartitionInfo(this.partitionMap.get (partitionId));
	}
	
	@Override
	public List<PartitionInfo> getPartitionsByIds(List<String> partitionIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<PartitionInfo> list = new ArrayList<PartitionInfo> ();
		for (String id: partitionIds) {
		    list.add (this.getPartition(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getPartitionIdsByType(String partitionTypeKey, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (PartitionInfo info: partitionMap.values ()) {
			if (partitionTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<String> getBuildingIdsByPartition(String partitionId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (BuildingInfo info: buildingMap.values ()) {
			if (partitionId.equals(info.getPartitionId())) {
			    list.add (info.getId ());
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public List<BuildingInfo> getBuildingsByPartition(String partitionId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// GET_INFOS_BY_OTHER
		List<BuildingInfo> list = new ArrayList<BuildingInfo> ();
		for (BuildingInfo info: buildingMap.values ()) {
			if (partitionId.equals(info.getPartitionId())) {
			    list.add (new BuildingInfo(info));
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public List<String> getPartitionIdsByBuilding(String buildingId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (PartitionInfo info: partitionMap.values ()) {
			if (buildingId.equals(info.getBuildingId())) {
			    list.add (info.getId ());
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
    }
	
	@Override
	public List<PartitionInfo> getPartitionsByBuilding(String buildingId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// GET_INFOS_BY_OTHER
		List<PartitionInfo> list = new ArrayList<PartitionInfo> ();
		for (PartitionInfo info: partitionMap.values ()) {
			if (buildingId.equals(info.getBuildingId())) {
			    list.add (new PartitionInfo(info));
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public List<String> getRoomIdsByPartition(String partitionId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (RoomInfo info: roomMap.values ()) {
			if (partitionId.equals(info.getPartitionId())) {
			    list.add (info.getId ());
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public List<RoomInfo> getRoomsByPartition(String partitionId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// GET_INFOS_BY_OTHER
		List<RoomInfo> list = new ArrayList<RoomInfo> ();
		for (RoomInfo info: roomMap.values ()) {
			if (partitionId.equals(info.getPartitionId())) {
			    list.add (new RoomInfo(info));
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public List<String> getPartitionIdsByRoom(String roomId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// GET_IDS_BY_OTHER
		List<String> list = new ArrayList<String> ();
		for (PartitionInfo info: partitionMap.values ()) {
			if (roomId.equals(info.getRoomId())) {
			    list.add (info.getId ());
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public List<PartitionInfo> getPartitionsByRoom(String roomId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// GET_INFOS_BY_OTHER
		List<PartitionInfo> list = new ArrayList<PartitionInfo> ();
		for (PartitionInfo info: partitionMap.values ()) {
			if (roomId.equals(info.getRoomId())) {
			    list.add (new PartitionInfo(info));
			}
		}
		return list;
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public List<String> searchForPartitionIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForPartitionIds has not been implemented");
	}
	
	@Override
	public List<PartitionInfo> searchForPartitions(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForPartitions has not been implemented");
	}
	
	@Override
	public List<ValidationResultInfo> validatePartition(String partitionTypeKey, String validationTypeKey, PartitionInfo partitionInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	@Override
	public PartitionInfo createPartition(String partitionTypeKey, PartitionInfo partitionInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		if (!partitionTypeKey.equals (partitionInfo.getTypeKey())) {
		    throw new InvalidParameterException ("The type parameter does not match the type on the info object");
		}
		PartitionInfo copy = new PartitionInfo(partitionInfo);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		partitionMap.put(copy.getId(), copy);
		return new PartitionInfo(copy);
	}
	
	@Override
	public PartitionInfo updatePartition(String partitionId, PartitionInfo partitionInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!partitionId.equals (partitionInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		PartitionInfo copy = new PartitionInfo(partitionInfo);
		PartitionInfo old = this.getPartition(partitionInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.partitionMap .put(partitionInfo.getId(), copy);
		return new PartitionInfo(copy);
	}
	
	@Override
	public StatusInfo deletePartition(String partitionId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.partitionMap.remove(partitionId) == null) {
		   throw new OperationFailedException(partitionId);
		}
		return newStatus();
	}
	
	@Override
	public StatusInfo addBuildingToPartition(String partitionId, String buildingId, ContextInfo contextInfo)
		throws AlreadyExistsException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// ADD
		BuildingToPartitionInfo copy = new BuildingToPartitionInfo(partitionId);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		buildingToPartitionMap.put(copy.getId(), copy);
		return new BuildingToPartitionInfo(copy);
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
    }
	
	@Override
	public StatusInfo removeBuildingFromPartition(String partitionId, String buildingId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// REMOVE
		if (this.buildingFromPartitionMap.remove(partitionId) == null) {
		   throw new OperationFailedException(partitionId);
		}
		return newStatus();
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
    }
	
	@Override
	public StatusInfo addRoomToPartition(String partitionId, String roomId, ContextInfo contextInfo)
		throws AlreadyExistsException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// ADD
		RoomToPartitionInfo copy = new RoomToPartitionInfo(partitionId);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		roomToPartitionMap.put(copy.getId(), copy);
		return new RoomToPartitionInfo(copy);
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	@Override
	public StatusInfo removeRoomFromPartition(String partitionId, String roomId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
/*
		// REMOVE
		if (this.roomFromPartitionMap.remove(partitionId) == null) {
		   throw new OperationFailedException(partitionId);
		}
		return newStatus();
*/
        // intended to be implemented during M9
        throw new UnsupportedOperationException("not implemented.");
	}
	
	private StatusInfo newStatus() {
	     StatusInfo status = new StatusInfo();
	     status.setSuccess(Boolean.TRUE);
	     return status;
	}
	
	private MetaInfo newMeta(ContextInfo context) {
	     MetaInfo meta = new MetaInfo();
	     meta.setCreateId(context.getPrincipalId());
	     meta.setCreateTime(new Date());
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(meta.getCreateTime());
	     meta.setVersionInd("0");
	     return meta;
	}
	
	private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
	     MetaInfo meta = new MetaInfo(old);
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(new Date());
	     meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
	     return meta;
	}
	
}

