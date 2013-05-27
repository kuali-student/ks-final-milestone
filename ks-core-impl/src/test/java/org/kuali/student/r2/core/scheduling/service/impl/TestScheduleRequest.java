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


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-impl-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestScheduleRequest {

    @Resource(name = "schedulingServiceImpl")
    private SchedulingService schedulingService;

    @Resource(name = "scheduleRequestTestDataLoader")
    private ScheduleRequestTestDataLoader scheduleRequestTestDataLoader;

    private String principalId;
    private ContextInfo callContext;


    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public ScheduleRequestTestDataLoader getScheduleRequestTestDataLoader() {
        return scheduleRequestTestDataLoader;
    }

    public void setScheduleRequestTestDataLoader(ScheduleRequestTestDataLoader scheduleRequestTestDataLoader) {
        this.scheduleRequestTestDataLoader = scheduleRequestTestDataLoader;
    }

    @Before
    public void setUp()
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testSchedulingServiceSetup() {
        assertNotNull(schedulingService);
    }

    @Test
    public void testGetScheduleRequest() throws Exception {
        scheduleRequestTestDataLoader.loadData(schedulingService, callContext);

        try {
            schedulingService.getScheduleRequest("ScheduleRequest-blah", callContext);
            fail("should not exist");
        } catch (DoesNotExistException ex) {
            // expected
        }

        ScheduleRequestInfo obj = schedulingService.getScheduleRequest("schedReq-G", callContext);
        assertNotNull(obj);
        assertEquals("schedReq-G", obj.getName());

        assertEquals("scheduleId-G", obj.getScheduleId());
        assertEquals("scheduleRequestSetId-A", obj.getScheduleRequestSetId());
        assertEquals(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, obj.getTypeKey());
        assertEquals(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED, obj.getStateKey());
        assertEquals("<p>schedreq Desc 101</p>", obj.getDescr().getFormatted());
        assertEquals("schedreq Desc 101", obj.getDescr().getPlain());
        assertNotNull(obj.getScheduleRequestComponents());
        ScheduleRequestComponentInfo srComp = obj.getScheduleRequestComponents().get(0);
        assertTrue(srComp.getBuildingIds().contains("building G1"));
        assertTrue(srComp.getCampusIds().contains("campus G1"));
        assertTrue(srComp.getOrgIds().contains("org G1"));
        assertTrue(srComp.getRoomIds().contains("room G1"));
        assertTrue(srComp.getResourceTypeKeys().contains("resource G1"));
        assertTrue(srComp.getTimeSlotIds().contains("timeslot G1"));
    }

    @Test
    public void testGetScheduleRequestsByIds()throws Exception {
        scheduleRequestTestDataLoader.loadData(schedulingService, callContext);

        List<String> schedReqIds = new ArrayList<String>();
        schedReqIds.add("schedReq-G");
        schedReqIds.add("schedReq-U");

        List<ScheduleRequestInfo> schedReqs = schedulingService.getScheduleRequestsByIds(schedReqIds, callContext);
        assertEquals(2, schedReqs.size());

    }

    @Test
    public void testGetScheduleRequestIdsByType()throws Exception {
        scheduleRequestTestDataLoader.loadData(schedulingService, callContext);

        List<String> schedReqIds = schedulingService.getScheduleRequestIdsByType("one of schedreq type", callContext);
        assertEquals(1, schedReqIds.size());
    }

    @Test
    public void testGetScheduleRequestsByRefObject()throws Exception {
        scheduleRequestTestDataLoader.loadData(schedulingService, callContext);

        List<ScheduleRequestInfo> schedReqs = schedulingService.getScheduleRequestsByRefObject("refObjType-N", "refObjId-N", callContext);
        assertEquals(1, schedReqs.size());
    }

    @Test
    public void testGetScheduleRequestsByRefObjectsAndRefType()throws Exception {
        scheduleRequestTestDataLoader.loadData(schedulingService, callContext);
        List<String> ids = new ArrayList<String>();
        ids.add("refObjId-N");
        List<ScheduleRequestInfo> schedReqs = schedulingService.getScheduleRequestsByRefObjects("refObjType-N", ids, callContext);
        assertEquals(1, schedReqs.size());

        ids.add("refObjId-A");

        schedReqs = schedulingService.getScheduleRequestsByRefObjects("refObjType-N", ids, callContext);
        assertEquals(3, schedReqs.size());
    }

    @Test
    public void testDeleteScheduleRequest() throws Exception {
        scheduleRequestTestDataLoader.loadData(schedulingService, callContext);

        ScheduleRequestInfo obj = schedulingService.getScheduleRequest("schedReq-D", callContext);
        assertNotNull(obj);

        StatusInfo status = schedulingService.deleteScheduleRequest("schedReq-D", callContext);
        assertTrue(status.getIsSuccess());

        try {
            schedulingService.getScheduleRequest("schedReq-D", callContext);
        } catch (DoesNotExistException ee) {
        }
    }

    @Test
    public void testUpdateScheduleRequest() throws Exception {
        scheduleRequestTestDataLoader.loadData(schedulingService, callContext);

        ScheduleRequestInfo obj = schedulingService.getScheduleRequest("schedReq-U", callContext);
        assertNotNull(obj);
        assertEquals("schedReq-U", obj.getName());

        ScheduleRequestInfo modified = new  ScheduleRequestInfo(obj);
        modified.setName(obj.getName() + "-modified");
        modified.setScheduleId(obj.getScheduleId() + "-modified");
        modified.setScheduleRequestSetId(obj.getScheduleRequestSetId() + "-modified");
        modified.setStateKey(obj.getStateKey() + "-modified");
        int compCnt = modified.getScheduleRequestComponents().size();
        modified.getScheduleRequestComponents().add(scheduleRequestTestDataLoader.generateSchedReqComponent("M"));

        ScheduleRequestInfo updated = schedulingService.updateScheduleRequest(modified.getId(), modified, callContext);
        assertNotNull(updated);
        assertEquals(modified.getName(), updated.getName());
        assertEquals(modified.getScheduleId(), updated.getScheduleId());
        assertEquals(modified.getScheduleRequestSetId(), updated.getScheduleRequestSetId());
        assertEquals(modified.getStateKey(), updated.getStateKey());
        assertEquals(compCnt + 1, updated.getScheduleRequestComponents().size());
        ScheduleRequestComponentInfo newComp = getScheduleRequestComponent(updated.getScheduleRequestComponents(), "M");
        assertNotNull(newComp);
        assertTrue(newComp.getBuildingIds().contains("building M1"));
        assertTrue(newComp.getCampusIds().contains("campus M1"));
        assertTrue(newComp.getOrgIds().contains("org M1"));
        assertTrue(newComp.getRoomIds().contains("room M1"));
        assertTrue(newComp.getResourceTypeKeys().contains("resource M1"));
        assertTrue(newComp.getTimeSlotIds().contains("timeslot M1"));

        ScheduleRequestInfo retrieved = schedulingService.getScheduleRequest(modified.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(modified.getName(), retrieved.getName());
        assertEquals(modified.getScheduleId(), retrieved.getScheduleId());
        assertEquals(modified.getScheduleRequestSetId(), retrieved.getScheduleRequestSetId());
        assertEquals(modified.getTypeKey(), retrieved.getTypeKey());
        assertEquals(modified.getStateKey(), retrieved.getStateKey());
        assertEquals(modified.getDescr().getFormatted(), retrieved.getDescr().getFormatted());
        assertEquals(modified.getDescr().getPlain(), retrieved.getDescr().getPlain());
        assertNotNull(retrieved.getScheduleRequestComponents());
        assertEquals(compCnt + 1, retrieved.getScheduleRequestComponents().size());

        //test orphan to delete
        retrieved.getScheduleRequestComponents().remove(0);
        ScheduleRequestInfo updated1 = schedulingService.updateScheduleRequest(retrieved.getId(), retrieved, callContext);
        assertNotNull(updated1);
        assertEquals(1, updated1.getScheduleRequestComponents().size());

        ScheduleRequestInfo retrieved1 = schedulingService.getScheduleRequest(modified.getId(), callContext);
        assertNotNull(retrieved1);
        assertEquals(1, retrieved1.getScheduleRequestComponents().size());
    }

    @Test
    public void testCreateScheduleRequest() throws Exception {
        ScheduleRequestInfo orig = scheduleRequestTestDataLoader.generateSchedReq("schedReq-C", "schedReq-C", "scheduleId-X", "scheduleRequestSetId-X",
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED, "<p>schedreq Desc</p>", "schedreq Desc", "C");
        ScheduleRequestInfo created = schedulingService.createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, orig, callContext);
        assertNotNull(created);
        assertEquals(orig.getName(), created.getName());

        assertEquals(orig.getScheduleId(), created.getScheduleId());
        assertEquals(orig.getScheduleRequestSetId(), created.getScheduleRequestSetId());
        assertEquals(orig.getTypeKey(), created.getTypeKey());
        assertEquals(orig.getStateKey(), created.getStateKey());
        assertEquals(orig.getDescr().getFormatted(), created.getDescr().getFormatted());
        assertEquals(orig.getDescr().getPlain(), created.getDescr().getPlain());
        assertNotNull(created.getScheduleRequestComponents());
        ScheduleRequestComponentInfo srComp = created.getScheduleRequestComponents().get(0);
        assertTrue(srComp.getBuildingIds().contains("building C1"));
        assertTrue(srComp.getCampusIds().contains("campus C1"));
        assertTrue(srComp.getOrgIds().contains("org C1"));
        assertTrue(srComp.getRoomIds().contains("room C1"));
        assertTrue(srComp.getResourceTypeKeys().contains("resource C1"));
        assertTrue(srComp.getTimeSlotIds().contains("timeslot C1"));

        ScheduleRequestInfo retrieved = schedulingService.getScheduleRequest(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(orig.getName(), retrieved.getName());
        assertEquals(orig.getScheduleId(), retrieved.getScheduleId());
        assertEquals(orig.getScheduleRequestSetId(), retrieved.getScheduleRequestSetId());
        assertEquals(orig.getTypeKey(), retrieved.getTypeKey());
        assertEquals(orig.getStateKey(), retrieved.getStateKey());
        assertEquals(orig.getDescr().getFormatted(), retrieved.getDescr().getFormatted());
        assertEquals(orig.getDescr().getPlain(), retrieved.getDescr().getPlain());
        assertNotNull(retrieved.getScheduleRequestComponents());
        ScheduleRequestComponentInfo srComp1 = retrieved.getScheduleRequestComponents().get(0);
        assertTrue(srComp1.getBuildingIds().contains("building C1"));
        assertTrue(srComp1.getCampusIds().contains("campus C1"));
        assertTrue(srComp1.getOrgIds().contains("org C1"));
        assertTrue(srComp1.getRoomIds().contains("room C1"));
        assertTrue(srComp1.getResourceTypeKeys().contains("resource C1"));
        assertTrue(srComp1.getTimeSlotIds().contains("timeslot C1"));
    }


    private ScheduleRequestComponentInfo getScheduleRequestComponent(List<ScheduleRequestComponentInfo> comps, String suffix){
        for(ScheduleRequestComponentInfo comp : comps) {
            List<String>  buildingIds = comp.getBuildingIds();
            if(buildingIds.contains("building " + suffix + "1")){
                return comp;
            }
        }

        return null;
    }
}