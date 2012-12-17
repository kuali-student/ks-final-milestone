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
package org.kuali.student.r2.core.class1.state.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.infc.StateConstraintOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestStateChangeServiceImpl extends TestStateServiceMockImpl{

    @Before
    public void setUp() throws Exception {
        super.setUp();
        loadStateData();
    }

    private void loadStateData()throws Exception{
        String lifecycleKey = createLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, "kuali.soc.lifecycle", "Set of Courses State Lifecycle", CourseOfferingSetServiceConstants.REF_OBJECT_URI_SOC);
        createState(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, lifecycleKey, "Open", "Open");
        createState(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, lifecycleKey, "Published", "Published");
        createState(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, lifecycleKey, "Locked", "Locked");
        createState(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, lifecycleKey, "Final Edits", "Final Edits");
    }

    private String createLifecycle(String key, String name, String descr, String uri) throws Exception{
        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b>" + descr);
        rti.setPlain(descr);
        origLife.setDescr(rti);
        origLife.setKey(key);
        origLife.setName(name);
        origLife.setRefObjectUri(uri);
        LifecycleInfo infoLife = stateService.createLifecycle(origLife.getKey(), origLife, callContext);
        return infoLife.getKey();
    }

    private void createState(String key, String lifecycleKey, String name, String descr)throws Exception{
        StateInfo orig = new StateInfo();
        orig.setKey(key);
        orig.setLifecycleKey(lifecycleKey);
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> " + descr);
        rti.setPlain(descr);
        orig.setDescr(rti);
        orig.setName(name);
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
    }

    @Test
    public void testUpdateStateChange() throws Exception {
        //TODO: implement StateServiceImpl.updateStateChange
    }

    @Test
    public void testDeleteStateChange() throws Exception {
        //TODO: implement StateServiceImpl.deleteStateChange
    }

    @Test
    public void testUpdateStateConstraint() throws Exception {
        StateConstraintInfo orig = new StateConstraintInfo();
        orig.setId("testConstraintId");
        orig.setAgendaId("test-agendaId");
        StateConstraintOperator operator =  StateConstraintOperator.ALL;
        orig.setStateConstraintOperator(operator);
        orig.setStateKey("testConstraintStateKey");
        orig.setTypeKey("testTypeKey");
        List<StateInfo> stateInfoList = stateService.getStatesByLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, callContext);
        assertTrue(stateInfoList.size() > 0);
        List<String>  relatedStateKeys = new ArrayList<String>();
        for(StateInfo info : stateInfoList) {
            relatedStateKeys.add(info.getKey());
        }
        orig.setRelatedObjectStateKeys(relatedStateKeys);

        //Create
        StateConstraintInfo constraintInfo = stateService.createStateConstraint(orig.getId(), orig, callContext);
        assertNotNull(constraintInfo);
        // Read
        StateConstraintInfo readInfo = stateService.getStateConstraint(orig.getId(), callContext);
        assertNotNull(readInfo);
        assertEquals(orig.getId(), readInfo.getId());
        assertEquals(orig.getAgendaId(), readInfo.getAgendaId());
        assertEquals(orig.getStateKey(), readInfo.getStateKey());
        assertEquals(orig.getTypeKey(), readInfo.getTypeKey());
        assertEquals(orig.getStateConstraintOperator().toString(), readInfo.getStateConstraintOperator().toString());
        assertEquals(orig.getRelatedObjectStateKeys(), readInfo.getRelatedObjectStateKeys());
        //update
        readInfo.setStateKey("testUpdateConstraintStateKey");
        readInfo.setTypeKey("testUpdateTypeKey");
        StateConstraintInfo updated = stateService.updateStateConstraint(readInfo.getId(), readInfo, callContext);
        assertNotNull(updated);
        assertEquals(orig.getId(), readInfo.getId());
        assertEquals(orig.getAgendaId(), readInfo.getAgendaId());
        assertEquals("testUpdateConstraintStateKey", readInfo.getStateKey());
        assertEquals("testUpdateTypeKey", readInfo.getTypeKey());
        assertEquals(orig.getStateConstraintOperator().toString(), readInfo.getStateConstraintOperator().toString());
        assertEquals(orig.getRelatedObjectStateKeys(), readInfo.getRelatedObjectStateKeys());

        // delete
        StatusInfo statusInfo =  stateService.deleteStateConstraint(readInfo.getId(), callContext);
        assertNotNull(statusInfo);
        assertTrue(statusInfo.getIsSuccess());

    }

    @Test
    public void testDeleteStateConstraint() throws Exception {
        StateConstraintInfo orig = new StateConstraintInfo();
        orig.setId("testConstraintId");
        orig.setAgendaId("test-agendaId");
        StateConstraintOperator operator =  StateConstraintOperator.ALL;
        orig.setStateConstraintOperator(operator);
        orig.setStateKey("testConstraintStateKey");
        orig.setTypeKey("testTypeKey");
        List<StateInfo> stateInfoList = stateService.getStatesByLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, callContext);
        assertTrue(stateInfoList.size() > 0);
        List<String>  relatedStateKeys = new ArrayList<String>();
        for(StateInfo info : stateInfoList) {
            relatedStateKeys.add(info.getKey());
        }
        orig.setRelatedObjectStateKeys(relatedStateKeys);

        //Create
        StateConstraintInfo constraintInfo = stateService.createStateConstraint(orig.getId(), orig, callContext);
        assertNotNull(constraintInfo);
        // delete
        StatusInfo statusInfo =  stateService.deleteStateConstraint(constraintInfo.getId(), callContext);
        assertNotNull(statusInfo);
        assertTrue(statusInfo.getIsSuccess());

    }

    @Test
    public void testCRStateConstraint() throws Exception {
        StateConstraintInfo orig = new StateConstraintInfo();
        orig.setId("testConstraintId");
        orig.setAgendaId("test-agendaId");
        StateConstraintOperator operator =  StateConstraintOperator.ALL;
        orig.setStateConstraintOperator(operator);
        orig.setStateKey("testConstraintStateKey");
        orig.setTypeKey("testTypeKey");
        List<StateInfo> stateInfoList = stateService.getStatesByLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, callContext);
        assertTrue(stateInfoList.size() > 0);
        List<String>  relatedStateKeys = new ArrayList<String>();
        for(StateInfo info : stateInfoList) {
            relatedStateKeys.add(info.getKey());
        }
        orig.setRelatedObjectStateKeys(relatedStateKeys);

        //Create
        StateConstraintInfo constraintInfo = stateService.createStateConstraint(orig.getId(), orig, callContext);
        assertNotNull(constraintInfo);
        // Read
        StateConstraintInfo readInfo = stateService.getStateConstraint(orig.getId(), callContext);
        assertNotNull(readInfo);
        assertEquals(orig.getId(), readInfo.getId());
        assertEquals(orig.getAgendaId(), readInfo.getAgendaId());
        assertEquals(orig.getStateKey(), readInfo.getStateKey());
        assertEquals(orig.getTypeKey(), readInfo.getTypeKey());
        assertEquals(orig.getStateConstraintOperator().toString(), readInfo.getStateConstraintOperator().toString());
        assertEquals(orig.getRelatedObjectStateKeys(), readInfo.getRelatedObjectStateKeys());
    }

    @Test
    public void testUpdateStatePropagation() throws Exception {
        //TODO: implement StateServiceImpl.updateStateChange
    }


    @Test
    public void testDeleteStatePropagation() throws Exception {
        //TODO: remove this
    }

    @Test
    public void testCRStatePropagation() throws Exception {
      //TODO: remove this
    }

    @Test
    public void testCRUDState() throws Exception {
        //TODO: remove this
    }

    @Test
    public void testCRStateChange() throws Exception {
        // create
        StateChangeInfo orig = new StateChangeInfo();
        orig.setStateKey("Active");
        orig.setTypeKey("statechange");
        orig.setFromStateKey(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY);
        orig.setToStateKey(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY);
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        orig.getStateConstraintIds().add("cnstrnt-1");
        orig.getStatePropagationIds().add("propagt-1");
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StateChangeInfo created = stateService.createStateChange(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, "statechange", orig, callContext);
        assertNotNull(created);
        assertEquals(orig.getStateKey(), created.getStateKey());
        assertEquals(orig.getTypeKey(), created.getTypeKey());
        assertEquals(orig.getFromStateKey(), created.getFromStateKey());
        assertEquals(orig.getToStateKey(), created.getToStateKey());
        assertEquals(orig.getEffectiveDate(), created.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), created.getExpirationDate());
        assertEquals(orig.getStateConstraintIds().size(), created.getStateConstraintIds().size());
        assertTrue(created.getStateConstraintIds().contains("cnstrnt-1"));
        assertEquals(orig.getStatePropagationIds().size(), created.getStatePropagationIds().size());
        assertTrue(created.getStatePropagationIds().contains("propagt-1"));
        assertEquals(orig.getAttributes().size(), created.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getKey(), created.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), created.getAttributes().get(0).getValue());
        assertNotNull(created.getMeta());
        assertNotNull(created.getMeta().getCreateId());
        assertNotNull(created.getMeta().getCreateTime());
        assertNotNull(created.getMeta().getUpdateId());
        assertNotNull(created.getMeta().getUpdateTime());
        assertNotNull(created.getMeta().getVersionInd());

        //get
        StateChangeInfo retrieved = stateService.getStateChange(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(retrieved.getStateKey(), created.getStateKey());
        assertEquals(retrieved.getTypeKey(), created.getTypeKey());
        assertEquals(retrieved.getFromStateKey(), created.getFromStateKey());
        assertEquals(retrieved.getToStateKey(), created.getToStateKey());
        assertEquals(retrieved.getEffectiveDate(), created.getEffectiveDate());
        assertEquals(retrieved.getExpirationDate(), created.getExpirationDate());
        assertEquals(retrieved.getStateConstraintIds().size(), created.getStateConstraintIds().size());
        assertTrue(retrieved.getStateConstraintIds().contains("cnstrnt-1"));
        assertEquals(retrieved.getStatePropagationIds().size(), created.getStatePropagationIds().size());
        assertTrue(retrieved.getStatePropagationIds().contains("propagt-1"));
        assertEquals(retrieved.getAttributes().size(), created.getAttributes().size());
        assertEquals(retrieved.getAttributes().get(0).getKey(), created.getAttributes().get(0).getKey());
        assertEquals(retrieved.getAttributes().get(0).getValue(), created.getAttributes().get(0).getValue());
        assertNotNull(retrieved.getMeta());
        assertNotNull(retrieved.getMeta().getCreateId());
        assertNotNull(retrieved.getMeta().getCreateTime());
        assertNotNull(retrieved.getMeta().getUpdateId());
        assertNotNull(retrieved.getMeta().getUpdateTime());
        assertNotNull(retrieved.getMeta().getVersionInd());

        //getbyFrom&ToStates
        List<StateChangeInfo> stateChangeInfoList = stateService.getStateChangesByFromStateAndToState(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, callContext);
        assertNotNull(stateChangeInfoList);
        StateChangeInfo info = stateChangeInfoList.get(0);
        assertEquals(info.getStateKey(), created.getStateKey());
        assertEquals(info.getTypeKey(), created.getTypeKey());
        assertEquals(info.getFromStateKey(), created.getFromStateKey());
        assertEquals(info.getToStateKey(), created.getToStateKey());
        assertEquals(info.getEffectiveDate(), created.getEffectiveDate());
        assertEquals(info.getExpirationDate(), created.getExpirationDate());
        assertEquals(info.getStateConstraintIds().size(), created.getStateConstraintIds().size());
        assertTrue(info.getStateConstraintIds().contains("cnstrnt-1"));
        assertEquals(info.getStatePropagationIds().size(), created.getStatePropagationIds().size());
        assertTrue(info.getStatePropagationIds().contains("propagt-1"));
    }
}
