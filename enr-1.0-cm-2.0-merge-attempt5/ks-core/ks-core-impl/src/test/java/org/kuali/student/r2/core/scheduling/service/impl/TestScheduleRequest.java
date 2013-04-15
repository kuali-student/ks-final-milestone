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
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestDao;
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

    @Resource(name = "scheduleRequestDao" )
    private ScheduleRequestDao scheduleRequestDao;

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public ScheduleRequestDao getScheduleRequestDao() {
        return scheduleRequestDao;
    }

    public void setScheduleRequestDao(ScheduleRequestDao scheduleRequestDao) {
        this.scheduleRequestDao = scheduleRequestDao;
    }
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new ScheduleRequestTestDataLoader(scheduleRequestDao).loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @Test
    public void testSchedulingServiceSetup() {
        assertNotNull(schedulingService);
    }

    @Test
    public void testGetScheduleRequest() throws Exception {
        try {
            schedulingService.getScheduleRequest("ScheduleRequest-blah", callContext);
            fail("should not exist");
        } catch (DoesNotExistException ex) {
            // expected
        }

        ScheduleRequestInfo obj = schedulingService.getScheduleRequest("schedReq-G", callContext);
        assertNotNull(obj);
        assertEquals("schedReq-G", obj.getName());
        assertEquals("refObjId", obj.getRefObjectId());
        assertEquals("refObjType", obj.getRefObjectTypeKey());
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
        List<String> schedReqIds = new ArrayList<String>();
        schedReqIds.add("schedReq-G");
        schedReqIds.add("schedReq-U");

        List<ScheduleRequestInfo> schedReqs = schedulingService.getScheduleRequestsByIds(schedReqIds, callContext);
        assertEquals(2, schedReqs.size());

    }

    @Test
    public void testGetScheduleRequestIdsByType()throws Exception {
        List<String> schedReqIds = schedulingService.getScheduleRequestIdsByType("one of schedreq type", callContext);
        assertEquals(1, schedReqIds.size());
    }

    @Test
    public void testGetScheduleRequestsByRefObject()throws Exception {
        List<ScheduleRequestInfo> schedReqs = schedulingService.getScheduleRequestsByRefObject("refObjType-N", "refObjId-N", callContext);
        assertEquals(1, schedReqs.size());
    }

    @Test
    public void testDeleteScheduleRequest() throws Exception {
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
        ScheduleRequestInfo obj = schedulingService.getScheduleRequest("schedReq-U", callContext);
        assertNotNull(obj);
        assertEquals("schedReq-U", obj.getName());

        ScheduleRequestInfo modified = new  ScheduleRequestInfo(obj);
        modified.setName(obj.getName() + "-modified");
        modified.setRefObjectId(obj.getRefObjectId() + "-modified");
        modified.setRefObjectTypeKey(obj.getRefObjectTypeKey() + "-modified");
        modified.setStateKey(obj.getStateKey() + "-modified");
        int compCnt = modified.getScheduleRequestComponents().size();
        modified.getScheduleRequestComponents().add(loadSchedReqComponent("M"));

        ScheduleRequestInfo updated = schedulingService.updateScheduleRequest(modified.getId(), modified, callContext);
        assertNotNull(updated);
        assertEquals(modified.getName(), updated.getName());
        assertEquals(modified.getRefObjectId(), updated.getRefObjectId());
        assertEquals(modified.getRefObjectTypeKey(), updated.getRefObjectTypeKey());
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
        assertEquals(modified.getRefObjectId(), retrieved.getRefObjectId());
        assertEquals(modified.getRefObjectTypeKey(), retrieved.getRefObjectTypeKey());
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
        ScheduleRequestInfo orig = loadSchedReq("schedReq-C", "refObjId", "refObjType", SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED, "<p>schedreq Desc</p>", "schedreq Desc", "C");
        ScheduleRequestInfo created = schedulingService.createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, orig, callContext);
        assertNotNull(created);
        assertEquals(orig.getName(), created.getName());
        assertEquals(orig.getRefObjectId(), created.getRefObjectId());
        assertEquals(orig.getRefObjectTypeKey(), created.getRefObjectTypeKey());
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
        assertEquals(orig.getRefObjectId(), retrieved.getRefObjectId());
        assertEquals(orig.getRefObjectTypeKey(), retrieved.getRefObjectTypeKey());
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

    private ScheduleRequestInfo loadSchedReq(String name,
                                             String refObjectId,
                                             String refObjectTypeKey,
                                             String type,
                                             String state,
                                             String descrFormatted,
                                             String descrPlain,
                                             String suffix){
        ScheduleRequestInfo info = new ScheduleRequestInfo();
        info.setName(name);
        info.setRefObjectId(refObjectId);
        info.setRefObjectTypeKey(refObjectTypeKey);
        info.setTypeKey(type);
        info.setStateKey(state);
        RichTextInfo rtInfo = new RichTextInfo();
        rtInfo.setFormatted(descrFormatted);
        rtInfo.setPlain(descrPlain);
        info.setDescr(rtInfo);

        List<ScheduleRequestComponentInfo> comps = new ArrayList<ScheduleRequestComponentInfo>();
        comps.add(loadSchedReqComponent(suffix));
        info.setScheduleRequestComponents(comps);

        return info;
    }

    private ScheduleRequestComponentInfo loadSchedReqComponent(String suffix) {
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