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
package org.kuali.student.r2.core.scheduling.service.impl;

import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestDao;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestEntity;

import java.util.Date;

public class ScheduleRequestTestDataLoader {
    private ScheduleRequestDao scheduleRequestDao;

    public ScheduleRequestTestDataLoader(ScheduleRequestDao scheduleRequestDao) {
        this.scheduleRequestDao = scheduleRequestDao;
    }

    private  String principalId = ScheduleRequestTestDataLoader.class.getSimpleName();

    public  void loadData() {
        loadSchedReq("schedReq-1", "schedReq-1", "refObjId", "refObjType", SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE, SchedulingServiceConstants.SCHEDULE_REQUEST_CREATED_STATE, "<p>schedreq Desc 101</p>", "schedreq Desc 101");
    }

    private void loadSchedReq(String id,
                              String name,
                              String refObjectId,
                              String refObjectTypeKey,
                              String type,
                              String state,
                              String descrFormatted,
                              String descrPlain){
        ScheduleRequestEntity entity = new ScheduleRequestEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setRefObjectId(refObjectId);
        entity.setRefObjectTypeKey(refObjectTypeKey);
        entity.setSchedReqType(type);
        entity.setSchedReqState(state);
        entity.setFormatted(descrFormatted);
        entity.setPlain(descrFormatted);
        entity.setCreateId(principalId);
        Date time = new Date();
		entity.setCreateTime(time);
        entity.setUpdateId(principalId);
        entity.setUpdateTime(time);
        scheduleRequestDao.persist(entity);
    }
}
