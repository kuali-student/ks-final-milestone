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
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRequestTestDataLoader {
    private String principalId = ScheduleRequestTestDataLoader.class.getSimpleName();

    public ScheduleRequestTestDataLoader() {
    }

    public  void loadData(SchedulingService schedulingService, ContextInfo callContext)
            throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {

        List<String> ids = new ArrayList<String>();
        ids.add("refObjId-A");
        createSchedReqSet("scheduleRequestSetId-A", "schedReqSet-A", "schedReqSetType-A",
                "schedReqSetState-A", "refObjType-N", ids, true, 1000, "formatted A", "plain A", schedulingService, callContext);
        ids.clear();
        ids.add("refObjId-U");
        createSchedReqSet("scheduleRequestSetId-U", "schedReqSet-U", "schedReqSetType-U",
                "schedReqSetState-U", "refObjType-U", ids, true, 1000, "formatted U", "plain U", schedulingService, callContext);
        ids.clear();
        ids.add("refObjId-N");
        ids.add("refObjId-O");
        ids.add("refObjId-P");
        createSchedReqSet("scheduleRequestSetId-N", "schedReqSet-N", "schedReqSetType-N",
                "schedReqSetState-N", "refObjType-N", ids, true, 1000, "formatted N", "plain U", schedulingService, callContext);

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

    public ScheduleRequestSetInfo createSchedReqSet(String id, String name, String type, String state, String refObjectType, List<String> refObjectIds, boolean maxEnrollShared,
                                                    int maxEnroll, String descrFormatted, String descrPlain, SchedulingService schedulingService, ContextInfo callContext)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException,
            ReadOnlyException, MissingParameterException, DataValidationErrorException {
        ScheduleRequestSetInfo info = generateSchedReqSet(id, name, type, state, refObjectType, refObjectIds, maxEnrollShared, maxEnroll, descrFormatted, descrPlain);
        return schedulingService.createScheduleRequestSet(info.getTypeKey(), info.getRefObjectTypeKey(), info, callContext);
    }

    public ScheduleRequestSetInfo generateSchedReqSet(String id, String name, String type, String state, String refObjectType, List<String> refObjectIds, boolean maxEnrollShared,
    int maxEnroll, String descrFormatted, String descrPlain) {
        ScheduleRequestSetInfo info = new ScheduleRequestSetInfo();
        info.setId(id);
        info.setName(name);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setRefObjectTypeKey(refObjectType);
        info.setRefObjectIds(refObjectIds);
        info.setMaxEnrollmentShared(maxEnrollShared);
        info.setMaximumEnrollment(maxEnroll);
        info.setDescr(RichTextHelper.buildRichTextInfo(descrPlain, descrFormatted));

        return info;
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
        ScheduleRequestInfo info = generateSchedReq(id, name, scheduleId, scheduleRequestSetId, type, state, descrFormatted, descrPlain, suffix);
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
                              String suffix)
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