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
 * Created by Gordon on 11/01/2012
 */
package org.kuali.student.r2.core.room.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.room.model.RoomEntity;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class queries the database for RoomEntity information on behalf of Room Service
 *
 * @author Kuali Student Team
 */
public class RoomServiceDao extends GenericEntityDao<RoomEntity> {

    /**
     * Returns a list of RoomEntity based on the list of roomIds provided
     *
     * @param roomIds a list of room ids
     * @return a list of room entities
     * @throws DoesNotExistException
     *          one or more of the ids listed was not found
     */
    public List<RoomEntity> findRoomsByIds(List<String> roomIds) throws DoesNotExistException {
        Set<String> idSet = new HashSet<String>(roomIds.size());

        idSet.addAll(roomIds); // remove duplicates from the key list
        List<RoomEntity> rooms = em.createNamedQuery("Room.findRoomsByIds", RoomEntity.class).setParameter("ids", idSet).getResultList();
        verifyResults(rooms, idSet);

        return rooms;
    }

    public List<String> findRoomIdsByBuilding(String buildingId) {
        return em.createNamedQuery("Room.findRoomIdsByBuilding", String.class).setParameter("id", buildingId).getResultList();
    }

    public List<String> findRoomIdsByBuildingAndFloor(String buildingId, String floor) {
        return em.createNamedQuery("Room.findRoomIdsByBuildingAndFloor", String.class).setParameter("buildingId", buildingId).setParameter("floor", floor).getResultList();
    }

    public List<String> findRoomIdsByType(String roomType) {
        return em.createNamedQuery("Room.findRoomIdsByType", String.class).setParameter("roomType", roomType).getResultList();
    }

    public List<String> findRoomIdsByBuildingAndRoomType(String buildingId, String roomType) {
        return em.createNamedQuery("Room.findRoomIdsByBuildingAndRoomType", String.class).setParameter("buildingId", buildingId).setParameter("roomType", roomType).getResultList();
    }

    public List<String> findRoomIdsByBuildingAndRoomTypes(String buildingId, List<String> roomTypes) {
        HashSet<String> types = new HashSet<String>(roomTypes.size());
        types.addAll(roomTypes);

        return em.createNamedQuery("Room.findRoomIdsByBuildingAndRoomTypes", String.class).setParameter("buildingId", buildingId).setParameter("roomTypes", types).getResultList();
    }

    public List<RoomEntity> findRoomsByCodeAndBuilding(String roomCode, List<String> buildingIds) {
        Set<String> ids = new HashSet<String>(buildingIds.size());
        ids.addAll(buildingIds);

        return em.createNamedQuery("Room.findRoomsByCodeAndBuilding", RoomEntity.class).setParameter("roomCode", roomCode).setParameter("buildingIds", ids).getResultList();
    }

}
