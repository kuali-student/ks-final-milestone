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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ScheduleRequestDao extends GenericEntityDao<ScheduleRequestEntity> {
    public List<ScheduleRequestEntity> getScheduleRequestsByRefObject(String refObjectType, String refObjectId ){
        List<ScheduleRequestEntity> results = em.createNamedQuery("ScheduleRequest.getScheduleRequestsByRefObjectAndRefObjectType")
                .setParameter("refObjectTypeKey", refObjectType)
                .setParameter("refObjectId", refObjectId)
                .getResultList();
        return results;
    }

    public List<ScheduleRequestEntity> getScheduleRequestsByRefObjects(String refObjectType, List<String> refObjectIds ) {
        List<ScheduleRequestEntity> result = null;
        if(refObjectIds.size() > 0) {
            Set<String> refObjectIdSet = new HashSet<String>(refObjectIds.size());
            // remove duplicates from the key list
            refObjectIdSet.addAll(refObjectIds);

            StringBuilder builder = new StringBuilder("SELECT sr" +
                                " FROM ScheduleRequestEntity sr" +
                                " WHERE sr.scheduleRequestSetId in (SELECT reqSet.id" +
                                                                  " FROM ScheduleRequestSetEntity reqSet where reqSet.refObjectTypeKey = :refObjectTypeKey and (");


            Iterator<String> iter = refObjectIdSet.iterator();
            int i = 0;
            while(iter.hasNext()) {
                iter.next();
                builder.append(" :refObjectId").append(i++).append(" in elements(reqSet.refObjectIds)");
                if(iter.hasNext()) {
                    builder.append(" or");
                }
            }
            builder.append("))");

            System.out.println(builder.toString());

            Query query = em.createQuery(builder.toString());
            query.setParameter("refObjectTypeKey", refObjectType);

            i = 0;
            for(String id : refObjectIdSet) {
                query.setParameter("refObjectId" + i++, id);
            }
            result = query.getResultList();
        }
        return result;
    }

    public List<ScheduleRequestEntity> getScheduleRequestsByType(String scheduleRequestTypeKey){
        return (List<ScheduleRequestEntity>)em.createQuery(
                "from ScheduleRequestEntity sr where sr.schedReqType=:schedReqType")
                .setParameter("schedReqType", scheduleRequestTypeKey)
                .getResultList();
    }
}

