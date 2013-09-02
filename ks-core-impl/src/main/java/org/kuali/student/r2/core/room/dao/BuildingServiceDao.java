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
package org.kuali.student.r2.core.room.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.room.model.RoomBuildingEntity;
import org.kuali.student.r2.core.room.model.RoomEntity;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;


/**
 * This class queries the database for RoomBuildingEntity information on behalf of Room Service
 *
 * @author Kuali Student Team
 */
public class BuildingServiceDao extends GenericEntityDao<RoomBuildingEntity> {

    public List<RoomBuildingEntity> findBuildingsByIds(List<String> buildingIds) throws DoesNotExistException {
        HashSet<String> ids = new HashSet<String>(buildingIds.size());
        ids.addAll(buildingIds);

        List<RoomBuildingEntity> buildings = em.createNamedQuery("Building.findBuildingsByIds").setParameter("ids", ids).getResultList();
        verifyResults(buildings, ids);

        return buildings;
    }

    public List<String> findBuildingIdsByCampus(String campusKey) {
        return em.createNamedQuery("Building.findBuildingIdsByCampus").setParameter("campusKey", campusKey).getResultList();
    }

    public List<RoomBuildingEntity> findBuildingsByBuildingCode(String buildingCode) {
        return em.createNamedQuery("Building.findBuildingsByBuildingCode").setParameter("buildingCode", buildingCode).getResultList();
    }

    public List<String> findBuildingIdsByBuildingCode(String buildingCode) {
        return em.createNamedQuery("Building.findBuildingIdsByBuildingCode").setParameter("buildingCode", buildingCode).getResultList();
    }

}
