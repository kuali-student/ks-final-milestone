/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestSetEntity;

import java.util.List;

public class ScheduleRequestSetDao  extends GenericEntityDao<ScheduleRequestSetEntity> {


    public List<ScheduleRequestSetEntity> getScheduleRequestSetsByType(String scheduleRequestSetTypeKey){
        return (List<ScheduleRequestSetEntity>)em.createNamedQuery("ScheduleRequestSet.getScheduleRequestSetByType")
                .setParameter("schedReqSetType", scheduleRequestSetTypeKey)
                .getResultList();
    }

    public List<ScheduleRequestSetEntity> getScheduleRequestSetsByRefObject(String refObjectType, String refObjectId) {
        return (List<ScheduleRequestSetEntity>)em.createNamedQuery("ScheduleRequestSet.getScheduleRequestSetByRefObjTypeAndRefObjId")
                .setParameter("refObjectTypeKey", refObjectType).setParameter("refObjectId", refObjectId).getResultList();
    }

    public List<String> getScheduleRequestSetIdsByRefObjType(String refObjectTypeKey) {
        return (List<String>)em.createNamedQuery("ScheduleRequestSet.getScheduleRequestSetIdsByRefObjType")
                .setParameter("refObjectTypeKey", refObjectTypeKey).getResultList();
    }
}
