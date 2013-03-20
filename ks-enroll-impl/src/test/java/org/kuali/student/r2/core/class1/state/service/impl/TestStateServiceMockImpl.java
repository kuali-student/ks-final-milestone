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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.class1.state.infc.StateConstraintOperator;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-mock-service-test-context.xml"})
public class TestStateServiceMockImpl {
   @Resource(name = "stateService")
   protected StateService stateService;

   public static String principalId = "123";
   public ContextInfo callContext = null;

   @Before
   public void setUp() throws Exception {
       callContext = new ContextInfo();
       callContext.setPrincipalId(principalId);
   }

   @Test
   public void testCRUDState() throws Exception {
        // create
        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> lifecycle for testing purposes");
        rti.setPlain("Plain lifecycle for testing purposes");
        origLife.setDescr(rti);
        origLife.setKey(AtpServiceConstants.ATP_LIFECYCLE_KEY);
        origLife.setName("Testing");
        origLife.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origLife.getAttributes().add(attr);
        LifecycleInfo infoLife = stateService.createLifecycle(origLife.getKey(), origLife, callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotNull(infoLife.getMeta().getVersionInd());

        // get
        origLife = infoLife;
        infoLife = stateService.getLifecycle(origLife.getKey(), callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotNull(infoLife.getMeta().getVersionInd());

        // update
        origLife = infoLife;
        origLife.setName("Different Lifecycle");
        infoLife = stateService.updateLifecycle(origLife.getKey(), origLife, callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotSame(origLife.getMeta().getVersionInd(), infoLife.getMeta().getVersionInd());

        LifecycleInfo atpLife = infoLife;

        // create state
        StateInfo orig = new StateInfo();
        orig.setKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setLifecycleKey(atpLife.getKey());
        rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> state for testing purposes");
        rti.setPlain("Plain state again for testing purposes");
        orig.setDescr(rti);
        orig.setName("Testing state");
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StateInfo created = stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
        assertNotNull(created);
        assertEquals(orig.getKey(), created.getKey());
        assertEquals(orig.getLifecycleKey(), created.getLifecycleKey());
        assertEquals(orig.getName(), created.getName());
        assertEquals(orig.getEffectiveDate(), created.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), created.getExpirationDate());
        assertEquals(orig.getAttributes().size(), created.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getKey(), created.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), created.getAttributes().get(0).getValue());
        assertNotNull(created.getMeta());
        assertNotNull(created.getMeta().getCreateId());
        assertNotNull(created.getMeta().getCreateTime());
        assertNotNull(created.getMeta().getUpdateId());
        assertNotNull(created.getMeta().getUpdateTime());
        assertNotNull(created.getMeta().getVersionInd());

        // get
        StateInfo retrived = stateService.getState(orig.getKey(), callContext);
        assertNotNull(retrived);
        assertEquals(created.getKey(), retrived.getKey());
        assertEquals(created.getLifecycleKey(), retrived.getLifecycleKey());
        assertEquals(created.getName(), retrived.getName());
        assertEquals(created.getDescr().getPlain(), retrived.getDescr().getPlain());
        assertEquals(created.getEffectiveDate(), retrived.getEffectiveDate());
        assertEquals(created.getExpirationDate(), retrived.getExpirationDate());
        assertEquals(created.getAttributes().size(), retrived.getAttributes().size());
        assertEquals(created.getAttributes().get(0).getKey(), retrived.getAttributes().get(0).getKey());
        assertEquals(created.getAttributes().get(0).getValue(), retrived.getAttributes().get(0).getValue());
        assertNotNull(retrived.getMeta().getCreateId());
        assertNotNull(retrived.getMeta().getCreateTime());
        assertNotNull(retrived.getMeta().getUpdateId());
        assertNotNull(retrived.getMeta().getUpdateTime());

        // update
        retrived.setName("DifferentName");
        StateInfo updated = stateService.updateState(orig.getKey(), retrived, callContext);
        assertNotNull(updated);
        assertEquals(retrived.getKey(), updated.getKey());
        assertEquals(retrived.getLifecycleKey(), updated.getLifecycleKey());
        assertEquals(retrived.getName(), updated.getName());
        assertEquals(retrived.getEffectiveDate(), updated.getEffectiveDate());
        assertEquals(retrived.getExpirationDate(), updated.getExpirationDate());
        assertEquals(retrived.getAttributes().size(), updated.getAttributes().size());
        assertEquals(retrived.getAttributes().get(0).getKey(), updated.getAttributes().get(0).getKey());
        assertEquals(retrived.getAttributes().get(0).getValue(), updated.getAttributes().get(0).getValue());
        assertNotNull(updated.getMeta());
        assertNotNull(updated.getMeta().getCreateId());
        assertNotNull(updated.getMeta().getCreateTime());
        assertNotNull(updated.getMeta().getUpdateId());
        assertNotNull(updated.getMeta().getUpdateTime());
        assertNotNull(updated.getMeta().getVersionInd());
        assertNotSame(retrived.getMeta().getVersionInd(), updated.getMeta().getVersionInd());

        StateInfo atpDraftState = updated;

        // delete state
        StatusInfo result = stateService.deleteState(atpDraftState.getKey(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            stateService.getState(atpDraftState.getKey(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
        // delete life
        result = stateService.deleteLifecycle(atpLife.getKey(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            infoLife = stateService.getLifecycle(atpLife.getKey(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
    }

    @Test
    public void testGetInitialStatesByLifecycle() throws Exception {

        // create a life-cycle with 3 states, 2 being initial-states (this is the one we are testing for)
        LifecycleInfo lifecycle_1 = addLifecycle( "lifecycle-1" );
        addState( lifecycle_1, "l1_state_1_initial" );
        addState( lifecycle_1, "l1_state_2_initial" );
        addState( lifecycle_1, "l1_state_3" );
        this.stateService.addInitialStateToLifecycle( "l1_state_1_initial", "lifecycle-1", callContext );
        this.stateService.addInitialStateToLifecycle( "l1_state_2_initial", "lifecycle-1", callContext );

        // create life-cycle 2 with 1 initial-state (this is the one we don't want to get back from our test-call)
        LifecycleInfo lifecycle_2 = addLifecycle( "lifecycle-2" );
        addState( lifecycle_2, "l2_state_1_initial" );
        this.stateService.addInitialStateToLifecycle("l2_state_1_initial", "lifecycle-2", callContext);

        // validate
        List<String> initialStatesForLifecycle1 = stateService.getInitialStatesByLifecycle( lifecycle_1.getKey(), callContext );
        assertEquals( 2, initialStatesForLifecycle1.size() );
        assertTrue(initialStatesForLifecycle1.contains("l1_state_1_initial"));
        assertTrue(initialStatesForLifecycle1.contains("l1_state_2_initial"));
    }

    private LifecycleInfo addLifecycle( String name ) throws Exception {

        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> lifecycle for testing purposes");
        rti.setPlain("Plain lifecycle for testing purposes");
        origLife.setDescr(rti);
        origLife.setKey( name );
        origLife.setName( "TEST_NAME" );
        origLife.setRefObjectUri( "TEST_URI" );
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origLife.getAttributes().add(attr);
        return stateService.createLifecycle(origLife.getKey(), origLife, callContext);
    }

    private StateInfo addState( LifecycleInfo lifecycleInfo, String state ) throws Exception {

        StateInfo orig = new StateInfo();
        orig.setKey(state);
        orig.setLifecycleKey(lifecycleInfo.getKey());
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> state for testing purposes");
        rti.setPlain("Plain state again for testing purposes");
        orig.setDescr(rti);
        orig.setName("Testing state");
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        return stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
    }

    private StateChangeInfo createStateChange() throws Exception {
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

        return created;
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

    @Test
    public void testUpdateStateChange() throws Exception {
        StateChangeInfo retrieved = createStateChange();

       // update
        retrieved.getStateConstraintIds().add("cnstrnt-2");
        retrieved.getStatePropagationIds().add("propagt-2");
        StateChangeInfo updated = stateService.updateStateChange(retrieved.getId(), retrieved, callContext);
        assertNotNull(retrieved);
        assertEquals(retrieved.getStateKey(), updated.getStateKey());
        assertEquals(retrieved.getTypeKey(), updated.getTypeKey());
        assertEquals(retrieved.getFromStateKey(), updated.getFromStateKey());
        assertEquals(retrieved.getToStateKey(), updated.getToStateKey());
        assertEquals(retrieved.getEffectiveDate(), updated.getEffectiveDate());
        assertEquals(retrieved.getExpirationDate(), updated.getExpirationDate());
        assertEquals(retrieved.getStateConstraintIds().size(), updated.getStateConstraintIds().size());
        assertTrue(updated.getStateConstraintIds().contains("cnstrnt-2"));
        assertEquals(retrieved.getStatePropagationIds().size(), updated.getStatePropagationIds().size());
        assertTrue(updated.getStatePropagationIds().contains("propagt-2"));
        assertEquals(retrieved.getAttributes().size(), updated.getAttributes().size());
        assertEquals(retrieved.getAttributes().get(0).getKey(), updated.getAttributes().get(0).getKey());
        assertEquals(retrieved.getAttributes().get(0).getValue(), updated.getAttributes().get(0).getValue());
        assertNotNull(updated.getMeta());
        assertNotNull(updated.getMeta().getCreateId());
        assertNotNull(updated.getMeta().getCreateTime());
        assertNotNull(updated.getMeta().getUpdateId());
        assertNotNull(updated.getMeta().getUpdateTime());
        assertNotNull(updated.getMeta().getVersionInd());
        assertNotSame(retrieved.getMeta().getVersionInd(), updated.getMeta().getVersionInd());
    }

    @Test
    public void testDeleteStateChange() throws Exception {
       StateChangeInfo retrieved = createStateChange();
      // delete
        StatusInfo result = stateService.deleteStateChange(retrieved.getId(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            stateService.getStateChange(retrieved.getId(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
    }

    private StateConstraintInfo createStateConstraint() throws Exception {
        StateConstraintInfo orig = new StateConstraintInfo();
        orig.setStateKey("Active");
        orig.setTypeKey("Precondition");
        orig.setStateConstraintOperator(StateConstraintOperator.ALL);
        orig.setAgendaId("agendaId");
        orig.getRelatedObjectStateKeys().add(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StateConstraintInfo created = stateService.createStateConstraint(orig.getTypeKey(), orig, callContext);

        return created;
    }

    @Test
    public void testCRStateConstraint() throws Exception {
        // create
        StateConstraintInfo orig = new StateConstraintInfo();
        orig.setStateKey("Active");
        orig.setTypeKey("Precondition");
        orig.setStateConstraintOperator(StateConstraintOperator.ALL);
        orig.setAgendaId("agendaId");
        orig.getRelatedObjectStateKeys().add(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StateConstraintInfo created = stateService.createStateConstraint(orig.getTypeKey(), orig, callContext);
        assertNotNull(created);
        assertEquals(orig.getStateKey(), created.getStateKey());
        assertEquals(orig.getTypeKey(), created.getTypeKey());
        assertEquals(orig.getStateConstraintOperator(), created.getStateConstraintOperator());
        assertTrue(created.getRelatedObjectStateKeys().contains(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY));
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
        StateConstraintInfo retrieved = stateService.getStateConstraint(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(retrieved.getStateKey(), created.getStateKey());
        assertEquals(retrieved.getTypeKey(), created.getTypeKey());
        assertEquals(retrieved.getStateConstraintOperator(), created.getStateConstraintOperator());
        assertTrue(retrieved.getRelatedObjectStateKeys().contains(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY));
        assertEquals(retrieved.getAttributes().size(), created.getAttributes().size());
        assertEquals(retrieved.getAttributes().get(0).getKey(), created.getAttributes().get(0).getKey());
        assertEquals(retrieved.getAttributes().get(0).getValue(), created.getAttributes().get(0).getValue());
        assertNotNull(retrieved.getMeta());
        assertNotNull(retrieved.getMeta().getCreateId());
        assertNotNull(retrieved.getMeta().getCreateTime());
        assertNotNull(retrieved.getMeta().getUpdateId());
        assertNotNull(retrieved.getMeta().getUpdateTime());
        assertNotNull(retrieved.getMeta().getVersionInd());
    }

    @Test
    public void testUpdateStateConstraint() throws Exception {
        StateConstraintInfo orig = createStateConstraint();
        //update
        orig.getRelatedObjectStateKeys().add(CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY);
        StateConstraintInfo updated = stateService.updateStateConstraint(orig.getId(), orig, callContext);
        assertNotNull(updated);
        assertEquals(updated.getStateKey(), orig.getStateKey());
        assertEquals(updated.getTypeKey(), orig.getTypeKey());
        assertEquals(updated.getStateConstraintOperator(), orig.getStateConstraintOperator());
        assertTrue(updated.getRelatedObjectStateKeys().contains(CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY));
        assertEquals(updated.getAttributes().size(), orig.getAttributes().size());
        assertEquals(updated.getAttributes().get(0).getKey(), orig.getAttributes().get(0).getKey());
        assertEquals(updated.getAttributes().get(0).getValue(), orig.getAttributes().get(0).getValue());
        assertNotNull(updated.getMeta());
        assertNotNull(updated.getMeta().getCreateId());
        assertNotNull(updated.getMeta().getCreateTime());
        assertNotNull(updated.getMeta().getUpdateId());
        assertNotNull(updated.getMeta().getUpdateTime());
        assertNotNull(updated.getMeta().getVersionInd());
        assertNotSame(updated.getMeta().getVersionInd(), orig.getMeta().getVersionInd());
    }

    @Test
    public void testDeleteStateConstraint() throws Exception {
        StateConstraintInfo orig = createStateConstraint();
        // delete
        StatusInfo result = stateService.deleteStateConstraint(orig.getId(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            stateService.getStateConstraint(orig.getId(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
    }

    private StatePropagationInfo createStatePropagation()  throws Exception {
        // create
        StatePropagationInfo orig = new StatePropagationInfo();
        orig.setStateKey("Active");
        orig.setTypeKey("statepropagation");
        orig.setTargetStateChangeId("TargetStateChangeId");
        orig.getStateConstraintIds().add("cnstrnt-1");
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StatePropagationInfo created = stateService.createStatePropagation(orig.getTargetStateChangeId(), orig.getTypeKey(), orig, callContext);

        return created;
    }

    @Test
    public void testCRStatePropagation() throws Exception {
        // create
        StatePropagationInfo orig = new StatePropagationInfo();
        orig.setStateKey("Active");
        orig.setTypeKey("statepropagation");
        orig.setTargetStateChangeId("TargetStateChangeId");
        orig.getStateConstraintIds().add("cnstrnt-1");
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StatePropagationInfo created = stateService.createStatePropagation(orig.getTargetStateChangeId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(created);
        assertEquals(orig.getStateKey(), created.getStateKey());
        assertEquals(orig.getTypeKey(), created.getTypeKey());
        assertEquals(orig.getTargetStateChangeId(), created.getTargetStateChangeId());
        assertEquals(orig.getStateConstraintIds().size(), created.getStateConstraintIds().size());
        assertTrue(created.getStateConstraintIds().contains("cnstrnt-1"));
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
        StatePropagationInfo retrieved = stateService.getStatePropagation(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(retrieved.getStateKey(), created.getStateKey());
        assertEquals(retrieved.getTypeKey(), created.getTypeKey());
        assertEquals(orig.getTargetStateChangeId(), created.getTargetStateChangeId());
        assertEquals(retrieved.getStateConstraintIds().size(), created.getStateConstraintIds().size());
        assertTrue(retrieved.getStateConstraintIds().contains("cnstrnt-1"));
        assertEquals(retrieved.getAttributes().size(), created.getAttributes().size());
        assertEquals(retrieved.getAttributes().get(0).getKey(), created.getAttributes().get(0).getKey());
        assertEquals(retrieved.getAttributes().get(0).getValue(), created.getAttributes().get(0).getValue());
        assertNotNull(retrieved.getMeta());
        assertNotNull(retrieved.getMeta().getCreateId());
        assertNotNull(retrieved.getMeta().getCreateTime());
        assertNotNull(retrieved.getMeta().getUpdateId());
        assertNotNull(retrieved.getMeta().getUpdateTime());
        assertNotNull(retrieved.getMeta().getVersionInd());
    }

    @Test
    public void testUpdateStatePropagation() throws Exception {
       StatePropagationInfo orig = createStatePropagation();
        //update
        orig.getStateConstraintIds().add("cnstrnt-2");
        StatePropagationInfo updated = stateService.updateStatePropagation(orig.getId(),orig, callContext);
        assertNotNull(updated);
        assertEquals(orig.getStateKey(), updated.getStateKey());
        assertEquals(orig.getTypeKey(), updated.getTypeKey());
        assertEquals(orig.getTargetStateChangeId(), updated.getTargetStateChangeId());
        assertEquals(orig.getStateConstraintIds().size(), updated.getStateConstraintIds().size());
        assertTrue(updated.getStateConstraintIds().contains("cnstrnt-2"));
        assertEquals(orig.getAttributes().size(), updated.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getKey(), updated.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), updated.getAttributes().get(0).getValue());
        assertNotNull(updated.getMeta());
        assertNotNull(updated.getMeta().getCreateId());
        assertNotNull(updated.getMeta().getCreateTime());
        assertNotNull(updated.getMeta().getUpdateId());
        assertNotNull(updated.getMeta().getUpdateTime());
        assertNotNull(updated.getMeta().getVersionInd());
        assertNotSame(orig.getMeta().getVersionInd(), updated.getMeta().getVersionInd());
    }

    @Test
    public void testDeleteStatePropagation() throws Exception {
        StatePropagationInfo orig = createStatePropagation();
        // delete
        StatusInfo result = stateService.deleteStatePropagation(orig.getId(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            stateService.getStatePropagation(orig.getId(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
    }
}
