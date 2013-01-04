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
import java.util.List;
import java.util.Set;

public class ScheduleRequestDao extends GenericEntityDao<ScheduleRequestEntity> {
    public List<ScheduleRequestEntity> getScheduleRequestsByRefObject(String refObjectType, String refObjectId ){
        return (List<ScheduleRequestEntity>)em.createQuery(
                "from ScheduleRequestEntity sr where sr.refObjectTypeKey=:refObjectTypeKey and sr.refObjectId = :refObjectId")
                .setParameter("refObjectTypeKey", refObjectType)
                .setParameter("refObjectId", refObjectId)
                .getResultList();

    }

    public List<ScheduleRequestEntity> getScheduleRequestsByRefObjects(String refObjectType, List<String> refObjectIds ){
        Set<String> refObjectIdSet = new HashSet<String>(refObjectIds.size());
        // remove duplicates from the key list
        refObjectIdSet.addAll(refObjectIds);

        Query query = em.createNamedQuery("ScheduleRequest.getScheduleRequestsByRefObjects");
        query.setParameter("refObjectTypeKey", refObjectType);
        query.setParameter("refObjectIds", refObjectIdSet);
        return query.getResultList();
    }

    public List<ScheduleRequestEntity> getScheduleRequestsByType(String scheduleRequestTypeKey){
        return (List<ScheduleRequestEntity>)em.createQuery(
                "from ScheduleRequestEntity sr where sr.schedReqType=:schedReqType")
                .setParameter("schedReqType", scheduleRequestTypeKey)
                .getResultList();
    }
}

