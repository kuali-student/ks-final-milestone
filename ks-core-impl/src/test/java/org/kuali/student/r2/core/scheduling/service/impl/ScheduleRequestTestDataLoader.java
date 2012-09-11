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
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestComponentEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleRequestTestDataLoader {
    private ScheduleRequestDao scheduleRequestDao;

    public ScheduleRequestTestDataLoader(ScheduleRequestDao scheduleRequestDao) {
        this.scheduleRequestDao = scheduleRequestDao;
    }

    private  String principalId = ScheduleRequestTestDataLoader.class.getSimpleName();

    public  void loadData() {
        loadSchedReq("schedReq-G", "schedReq-G", "refObjId", "refObjType", SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED, "<p>schedreq Desc 101</p>", "schedreq Desc 101", "G");
        loadSchedReq("schedReq-D", "schedReq-D", "refObjId", "refObjType", SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED, "<p>schedreq Desc 102</p>", "schedreq Desc 102", "D");
        loadSchedReq("schedReq-U", "schedReq-U", "refObjId", "refObjType", SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED, "<p>schedreq Desc 103</p>", "schedreq Desc 103", "U");
        loadSchedReq("schedReq-N", "schedReq-N", "refObjId-N", "refObjType-N", "one of schedreq type", SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED, "<p>schedreq Desc 104</p>", "schedreq Desc 104", "N");
    }

    private void loadSchedReq(String id,
                              String name,
                              String refObjectId,
                              String refObjectTypeKey,
                              String type,
                              String state,
                              String descrFormatted,
                              String descrPlain,
                              String suffix){
        ScheduleRequestEntity entity = new ScheduleRequestEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setRefObjectId(refObjectId);
        entity.setRefObjectTypeKey(refObjectTypeKey);
        entity.setSchedReqType(type);
        entity.setSchedReqState(state);
        entity.setFormatted(descrFormatted);
        entity.setPlain(descrPlain);
        entity.setCreateId(principalId);
        Date time = new Date();
        entity.setCreateTime(time);
        entity.setUpdateId(principalId);
        entity.setUpdateTime(time);

        List<ScheduleRequestComponentEntity> comps = new ArrayList<ScheduleRequestComponentEntity>();
        ScheduleRequestComponentEntity comp = new ScheduleRequestComponentEntity();
        comp.setBuildingIds(getIds("building", suffix));
        comp.setCampusIds(getIds("campus", suffix));
        comp.setOrgIds(getIds("org", suffix));
        comp.setRoomIds(getIds("room", suffix));
        comp.setResourceTypeKeys(getIds("resource", suffix));
        comp.setTimeSlotIds(getIds("timeslot", suffix));
        comps.add(comp);
        entity.setScheduleRequestComponents(comps);

        scheduleRequestDao.persist(entity);
    }

    private List<String> getIds(String attr, String suffix){
        List<String> ids = new ArrayList<String>();
        ids.add(attr + " " + suffix + "1");
        ids.add(attr + " " + suffix + "2");
        return ids;
    }
}