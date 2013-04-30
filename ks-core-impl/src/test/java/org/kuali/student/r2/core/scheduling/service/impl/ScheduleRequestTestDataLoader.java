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

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRequestTestDataLoader {
    private String principalId = ScheduleRequestTestDataLoader.class.getSimpleName();

    public ScheduleRequestTestDataLoader() {
    }

    public  void loadData(SchedulingService schedulingService, ContextInfo callContext)
            throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {


        //TODO need valid scheduleId and scheduleSetId
        createSchedReq("schedReq-G", "schedReq-G", "scheduleId-G", "scheduleRequestSetId-A",
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED,
                "<p>schedreq Desc 101</p>", "schedreq Desc 101", "G", schedulingService, callContext);
        createSchedReq("schedReq-D", "schedReq-D", "scheduleId-D", "scheduleRequestSetId-A",
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED,
                "<p>schedreq Desc 102</p>", "schedreq Desc 102", "D", schedulingService, callContext);
        createSchedReq("schedReq-U", "schedReq-U", "scheduleId-U", "scheduleRequestSetId-U",
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED,
                "<p>schedreq Desc 103</p>", "schedreq Desc 103", "U", schedulingService, callContext);
        createSchedReq("schedReq-N", "schedReq-N", "scheduleId-N", "scheduleRequestSetId-N", "one of schedreq type",
                SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED,
                "<p>schedreq Desc 104</p>", "schedreq Desc 104", "N", schedulingService, callContext);
    }

    public ScheduleRequestInfo createSchedReq(String id,
                                            String name,
                                            String scheduleId,
                                            String scheduleRequestSetId,
                                            String type,
                                            String state,
                                            String descrFormatted,
                                            String descrPlain,
                                            String suffix,
                                            SchedulingService schedulingService,
                                            ContextInfo callContext)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        ScheduleRequestInfo info = generateSchedReq(id, name, scheduleId, scheduleRequestSetId, type, state, descrFormatted, descrPlain, suffix, schedulingService, callContext);
        return schedulingService.createScheduleRequest(info.getTypeKey(), info, callContext);
    }

    public ScheduleRequestInfo generateSchedReq(String id,
                              String name,
                              String scheduleId,
                              String scheduleRequestSetId,
                              String type,
                              String state,
                              String descrFormatted,
                              String descrPlain,
                              String suffix,
                              SchedulingService schedulingService,
                              ContextInfo callContext)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        ScheduleRequestInfo info = new ScheduleRequestInfo();
        info.setId(id);
        info.setName(name);
        info.setScheduleId(scheduleId);
        info.setScheduleRequestSetId(scheduleRequestSetId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setDescr(RichTextHelper.buildRichTextInfo(descrPlain, descrFormatted));

        List<ScheduleRequestComponentInfo> comps = new ArrayList<ScheduleRequestComponentInfo>();
        comps.add(generateSchedReqComponent(suffix));
        info.setScheduleRequestComponents(comps);

        return info;
    }

    public ScheduleRequestComponentInfo generateSchedReqComponent(String suffix) {
        ScheduleRequestComponentInfo comp = new ScheduleRequestComponentInfo();
        comp.setBuildingIds(getIds("building", suffix));
        comp.setCampusIds(getIds("campus", suffix));
        comp.setOrgIds(getIds("org", suffix));
        comp.setRoomIds(getIds("room", suffix));
        comp.setResourceTypeKeys(getIds("resource", suffix));
        comp.setTimeSlotIds(getIds("timeslot", suffix));

        return comp;
    }

    private List<String> getIds(String attr, String suffix){
        List<String> ids = new ArrayList<String>();
        ids.add(attr + " " + suffix + "1");
        ids.add(attr + " " + suffix + "2");
        return ids;
    }
}