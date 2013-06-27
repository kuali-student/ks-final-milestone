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
import org.kuali.student.r2.core.room.model.RoomResponsibleOrgEntity;

import java.util.List;

/**
 * This class queries the database for RoomResponsibleOrg information on behalf of RoomService
 *
 * @author Kuali Student Team
 */
public class RoomResponsibleOrgDao extends GenericEntityDao<RoomResponsibleOrgEntity> {

    public List<String> findRoomResponsibleOrgIdsByRoom(String roomId) {
        return em.createNamedQuery("RoomRespOrg.findIdsByRoom", String.class).setParameter("roomId", roomId).getResultList();
    }
}

