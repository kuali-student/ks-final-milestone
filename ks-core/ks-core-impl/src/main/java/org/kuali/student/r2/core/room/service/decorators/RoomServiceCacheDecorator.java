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
package org.kuali.student.r2.core.room.service.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomServiceDecorator;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceCacheDecorator extends RoomServiceDecorator {
    private static String roomCacheName = "roomCache";
    private static String buildingCacheName = "buildingCache";
    private static String roomRespOrgCacheName = "roomRespOrgCache";
    private static String buildingRoomCodeCache = "buildingRoomCodeCache";
    private static String buildingCodeCacheName = "buildingCodeCache";

    private static String ROOM_CACHE_PREFIX = "roomCachePrefix";
    private static String BUILDING_CACHE_PREFIX = "buildingCachePrefix";
    private static String ROOM_RESP_ORG_CACHE_PREFIX = "roomRespOrgCachePrefix";
    private static String BUILDING_ROOM_CODE_CACHE_PREFIX = "roomCodeCachePrefix";
    private static String BUILDING_CODE_CACHE_PREFIX = "buildingCodeCachePrefix";

    private CacheManager cacheManager;

    public CacheManager getCacheManager() {
        if(cacheManager == null){
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public RoomInfo getRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(ROOM_CACHE_PREFIX, roomId);

        Element cachedResult = cacheManager.getCache(roomCacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getRoom(roomId, contextInfo);
            cacheManager.getCache(roomCacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (RoomInfo)result;
    }

    @Override
    public List<RoomInfo> getRoomsByIds(@WebParam(name = "roomIds") List<String> roomIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomInfo> result = new ArrayList<RoomInfo>(roomIds.size());

        for (String id : roomIds) {
            result.add( this.getRoom(id, contextInfo));
        }

        return result;
    }

    @Override
    public RoomInfo createRoom(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "roomTypeKey") String roomTypeKey, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        RoomInfo room = getNextDecorator().createRoom(buildingId, roomTypeKey, roomInfo, contextInfo);

        MultiKey cacheKey = new MultiKey(ROOM_CACHE_PREFIX, room.getId());
        cacheManager.getCache(roomCacheName).put( new Element(cacheKey, room) );
        cacheManager.getCache(buildingRoomCodeCache).removeAll();

        return room;
    }

    @Override
    public RoomInfo updateRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "roomInfo") RoomInfo roomInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        RoomInfo room = getNextDecorator().updateRoom(roomId, roomInfo, contextInfo);

        MultiKey cacheKey = new MultiKey(ROOM_CACHE_PREFIX, room.getId());
        cacheManager.getCache(roomCacheName).put(new Element(cacheKey, room));
        cacheManager.getCache(buildingRoomCodeCache).removeAll();

        return room;
    }

    @Override
    public StatusInfo deleteRoom(@WebParam(name = "roomId") String roomId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo statusInfo = getNextDecorator().deleteRoom(roomId, contextInfo);

        if (statusInfo.getIsSuccess()) {
            MultiKey cacheKey = new MultiKey(ROOM_CACHE_PREFIX, roomId);
            cacheManager.getCache(roomCacheName).remove(cacheKey);
            cacheManager.getCache(buildingRoomCodeCache).removeAll();
        }

        return statusInfo;
    }

    @Override
    public BuildingInfo getBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(BUILDING_CACHE_PREFIX, buildingId);

        Element cachedResult = cacheManager.getCache(buildingCacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getBuilding(buildingId, contextInfo);
            cacheManager.getCache(buildingCacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (BuildingInfo)result;
    }

    @Override
    public List<BuildingInfo> getBuildingsByIds(@WebParam(name = "buildingIds") List<String> buildingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BuildingInfo> result = new ArrayList<BuildingInfo>(buildingIds.size());

        for (String id : buildingIds) {
            result.add( this.getBuilding(id, contextInfo) );
        }

        return result;
    }

    @Override
    public BuildingInfo createBuilding(@WebParam(name = "buildingTypeKey") String buildingTypeKey, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        BuildingInfo building = getNextDecorator().createBuilding(buildingTypeKey, buildingInfo, contextInfo);

        MultiKey cacheKey = new MultiKey(BUILDING_CACHE_PREFIX, building.getId());
        cacheManager.getCache(buildingCacheName).put( new Element(cacheKey, building) );
        cacheManager.getCache(buildingRoomCodeCache).removeAll();

        return building;
    }

    @Override
    public BuildingInfo updateBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "buildingInfo") BuildingInfo buildingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        BuildingInfo building = getNextDecorator().updateBuilding(buildingId, buildingInfo, contextInfo);

        MultiKey cacheIdKey = new MultiKey(BUILDING_CACHE_PREFIX, building.getId());
        cacheManager.getCache(buildingCacheName).put( new Element(cacheIdKey, building) );
        cacheManager.getCache(buildingCodeCacheName).removeAll();
        cacheManager.getCache(buildingRoomCodeCache).removeAll();

        return building;
    }

    @Override
    public StatusInfo deleteBuilding(@WebParam(name = "buildingId") String buildingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteBuilding(buildingId, contextInfo);

        if (result.getIsSuccess()) {
            MultiKey cacheKey = new MultiKey(BUILDING_CACHE_PREFIX, buildingId);
            cacheManager.getCache(buildingCacheName).remove(cacheKey);
            cacheManager.getCache(buildingCodeCacheName).removeAll();
            cacheManager.getCache(buildingRoomCodeCache).removeAll();
        }

        return result;
    }

    @Override
    public RoomResponsibleOrgInfo getRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(ROOM_RESP_ORG_CACHE_PREFIX, roomResponsibleOrgId);

        Element cachedResult = cacheManager.getCache(roomRespOrgCacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getRoomResponsibleOrg(roomResponsibleOrgId, contextInfo);
            cacheManager.getCache(roomRespOrgCacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (RoomResponsibleOrgInfo)result;
    }

    @Override
    public List<RoomResponsibleOrgInfo> getRoomResponsibleOrgsByIds(@WebParam(name = "roomResponsibleOrgIds") List<String> roomResponsibleOrgIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RoomResponsibleOrgInfo> result = new ArrayList<RoomResponsibleOrgInfo>(roomResponsibleOrgIds.size());

        for (String id : roomResponsibleOrgIds) {
            result.add( this.getRoomResponsibleOrg(id, contextInfo));
        }

        return result;
    }

    @Override
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(@WebParam(name = "roomId") String roomId, @WebParam(name = "orgId") String orgId, @WebParam(name = "roomResponsibleOrgTypeKey") String roomResponsibleOrgTypeKey, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        RoomResponsibleOrgInfo roomResponsibleOrg = getNextDecorator().createRoomResponsibleOrg(roomId, orgId, roomResponsibleOrgTypeKey, roomResponsibleOrgInfo, contextInfo);

        MultiKey cacheKey = new MultiKey(ROOM_RESP_ORG_CACHE_PREFIX, roomResponsibleOrg.getId());
        cacheManager.getCache(roomRespOrgCacheName).put( new Element(cacheKey, roomResponsibleOrg) );

        return roomResponsibleOrg;
    }

    @Override
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "roomResponsibleOrgInfo") RoomResponsibleOrgInfo roomResponsibleOrgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        RoomResponsibleOrgInfo roomResponsibleOrg = getNextDecorator().updateRoomResponsibleOrg(roomResponsibleOrgId, roomResponsibleOrgInfo, contextInfo);

        MultiKey cacheKey = new MultiKey(ROOM_RESP_ORG_CACHE_PREFIX, roomResponsibleOrg.getId());
        cacheManager.getCache(roomRespOrgCacheName).put( new Element(cacheKey, roomResponsibleOrg) );

        return roomResponsibleOrg;
    }

    @Override
    public StatusInfo deleteRoomResponsibleOrg(@WebParam(name = "roomResponsibleOrgId") String roomResponsibleOrgId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteRoomResponsibleOrg(roomResponsibleOrgId, contextInfo);

        MultiKey cacheKey = new MultiKey(ROOM_RESP_ORG_CACHE_PREFIX, roomResponsibleOrgId);
        cacheManager.getCache(roomRespOrgCacheName).remove(cacheKey);

        return result;
    }

    @Override
    public List<BuildingInfo> getBuildingsByBuildingCode(@WebParam(name = "buildingCode") String buildingCode, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(BUILDING_CODE_CACHE_PREFIX, buildingCode);

        Element cachedResult = cacheManager.getCache(buildingCodeCacheName).get(cacheKey);
        List<BuildingInfo> result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getBuildingsByBuildingCode(buildingCode, contextInfo);
            cacheManager.getCache(buildingCodeCacheName).put( new Element(cacheKey, result) );
        } else {
            result = (List<BuildingInfo>)cachedResult.getValue();
        }

        return result;
    }

    @Override
    public List<RoomInfo> getRoomsByBuildingAndRoomCode(@WebParam(name = "buildingCode") String buildingCode, @WebParam(name = "roomCode") String roomCode, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(BUILDING_ROOM_CODE_CACHE_PREFIX, buildingCode, roomCode);

        Element cachedResult = cacheManager.getCache(buildingRoomCodeCache).get(cacheKey);
        List<RoomInfo> result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getRoomsByBuildingAndRoomCode(buildingCode, roomCode, contextInfo);
            cacheManager.getCache(buildingRoomCodeCache).put( new Element(cacheKey, result) );
        } else {
            result = (List<RoomInfo>)cachedResult.getValue();
        }

        return result;
    }

}
