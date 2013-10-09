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
 */
package org.kuali.student.r2.core.scheduling.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestEntity;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ScheduleRequestDao extends GenericEntityDao<ScheduleRequestEntity> {
    public List<ScheduleRequestEntity> getScheduleRequestsByRefObject(String refObjectType, String refObjectId ){
        List<ScheduleRequestEntity> results = em.createNamedQuery("ScheduleRequest.getScheduleRequestsByRefIdAndType")
                .setParameter("refObjectTypeKey", refObjectType)
                .setParameter("refObjectId", refObjectId)
                .getResultList();
        return results;
    }

    public List<ScheduleRequestEntity> getScheduleRequestsByRefObjects(String refObjectType, List<String> refObjectIds ) {
        List<ScheduleRequestEntity> results = em.createNamedQuery("ScheduleRequest.getScheduleRequestsByRefIdAndType")
                .setParameter("refObjectTypeKey", refObjectType)
                .setParameter("refObjectId", refObjectIds)
                .getResultList();
        return results;
    }

    public List<ScheduleRequestEntity> getScheduleRequestsByType(String scheduleRequestTypeKey){
        return (List<ScheduleRequestEntity>)em.createQuery(
                "from ScheduleRequestEntity sr where sr.schedReqType=:schedReqType")
                .setParameter("schedReqType", scheduleRequestTypeKey)
                .getResultList();
    }

    public List<ScheduleRequestEntity> getScheduleRequestsByScheduleRequestSet(String scheduleRequestSetId ){
        List<ScheduleRequestEntity> results = em.createNamedQuery("ScheduleRequest.getScheduleRequestsByScheduleRequestSet")
                .setParameter("scheduleRequestSetId", scheduleRequestSetId)
                .getResultList();
        return results;
    }

    public Boolean isExistsTimeSlot(String timeSlotid) {

        Query query = em.createQuery("select ts from ScheduleRequestEntity sr, IN(sr.scheduleRequestComponents) src, IN(src.timeSlotIds) ts where ts = :tsId");
        query.setParameter("tsId", timeSlotid);
        query.setMaxResults(1);
        List results = query.getResultList();
        if ((results == null) || results.isEmpty()) {
            return Boolean.FALSE;
        }
        else {
            return Boolean.TRUE;
        }
    }
}

