package org.kuali.student.r2.core.class1.state.service.impl;

import org.junit.Test;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.class1.state.infc.StateConstraintOperator;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;
import org.kuali.student.r2.core.class1.state.service.StateHelper;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.state.service.impl.mocks.StatefulItemRelatedObjectHelper;
import org.kuali.student.r2.core.class1.state.service.impl.mocks.StatefulItemServiceImpl;
import org.kuali.student.r2.core.class1.state.service.impl.mocks.StatefulItemStateHelper;
import org.kuali.student.r2.core.class1.state.mock.StateServiceMockImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestStateTransitionsHelperImpl {

    private ContextInfo context = new ContextInfo();

    //  This needs to be instanced in each test to reset the data.
    private StateService stateService;

    /**
     * Test "next validate state" constraints.
     */
    @Test
    public void testNextValidStateConstraints() throws Exception {
        //  Create a fresh StateService.
        stateService = new StateServiceMockImpl();

        //  Create state lifecycle and state keys
        String lifecycleKey = "kuali.Alpha.lifecycle";
        addLifecycle("kuali.Alpha.lifecycle", "Test Item A", "Test Item A", "url");
        addStateKey("kuali.Alpha.state.draft", lifecycleKey, "Draft", "Draft");
        addStateKey("kuali.Alpha.state.open", lifecycleKey, "Open", "Open");
        addStateKey("kuali.Alpha.state.closed", lifecycleKey, "Closed", "Closed");
        addStateKey("kuali.Alpha.state.canceled", lifecycleKey, "Canceled", "Canceled");

        //  Create state changes.
        addStateChange("sc1", "kuali.Alpha.state.draft", "kuali.Alpha.state.open", null, null);
        addStateChange("sc2", "kuali.Alpha.state.open", "kuali.Alpha.state.closed", null, null);
        addStateChange("sc3", "kuali.Alpha.state.closed", "kuali.Alpha.state.open", null, null);   //  Re-opening is valid.
        addStateChange("sc4", "kuali.Alpha.state.open", "kuali.Alpha.state.canceled", null, null); //  Only opens can be canceled.

        //  Instance the state transition helper
        StateTransitionsHelperImpl stateTransitionsHelper = new StateTransitionsHelperImpl();
        stateTransitionsHelper.setStateService(stateService);  //  Provide the state service.

        //  Create a StatefulItemService and initialize the item to be state changed.
        StatefulItemServiceImpl statefulItemService = new StatefulItemServiceImpl();
        statefulItemService.createItem("alpha1", "kuali.Alpha.state.draft");
        statefulItemService.setStateTransitionsHelper(stateTransitionsHelper);

        //  Create an item state helper and set the item service.
        StatefulItemStateHelper statefulItemStateHelper = new StatefulItemStateHelper();
        statefulItemStateHelper.setStatefulItemService(statefulItemService);

        //  Create the helper registry and install it in the transition helper.
        Map<String, StateHelper> stateHelperMap = new HashMap<String, StateHelper>();
        stateHelperMap.put("kuali.Alpha", statefulItemStateHelper);
        stateTransitionsHelper.setStateHelperMap(stateHelperMap);

        //  Validate state changes.
        //  draft -> open == okay
        StatusInfo si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertTrue(si.getIsSuccess());
        //  draft -> cancel != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.cancel", context);
        assertFalse(si.getIsSuccess());
        //  draft -> bad_key != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "unknown", context);
        assertFalse(si.getIsSuccess());
        //  draft -> closed != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.closed", context);
        assertFalse(si.getIsSuccess());

        //  Advance the state of alpha1
        statefulItemService.updateItem("alpha1", "kuali.Alpha.state.open");
        //  open -> closed == okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.closed", context);
        assertTrue(si.getIsSuccess());
        //  open -> canceled == okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.canceled", context);
        assertTrue(si.getIsSuccess());
        //  open -> draft != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.draft", context);
        assertFalse(si.getIsSuccess());

        //  Advance the state of alpha1
        statefulItemService.updateItem("alpha1", "kuali.Alpha.state.closed");
        //  closed -> open == okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertTrue(si.getIsSuccess());
        //  closed -> canceled != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.canceled", context);
        assertFalse(si.getIsSuccess());
        //  closed -> closed == okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.closed", context);
        assertTrue(si.getIsSuccess());
        //  closed -> draft != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.draft", context);
        assertFalse(si.getIsSuccess());

        //  Advance the state of alpha1
        statefulItemService.updateItem("alpha1", "kuali.Alpha.state.canceled");
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.draft", context);
        assertFalse(si.getIsSuccess());

         //  canceled -> open != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertFalse(si.getIsSuccess());
        //  canceled -> canceled == okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.canceled", context);
        assertTrue(si.getIsSuccess());
        //  canceled -> closed != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.closed", context);
        assertFalse(si.getIsSuccess());
        //  canceled -> draft != okay
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.draft", context);
        assertFalse(si.getIsSuccess());
    }

    /**
     *  Test a simple related object constraint.
     */
    @Test
    public void testSimpleRelatedObjectConstraint() throws Exception {
        //  Create a fresh StateService.
        stateService = new StateServiceMockImpl();

        //  Create state lifecycle and state keys
        String alphaLifecycleKey = "kuali.Alpha.lifecycle";
        addLifecycle("kuali.Alpha.lifecycle", "LA", "LA", "url");
        addStateKey("kuali.Alpha.state.draft", alphaLifecycleKey, "Draft", "Draft");
        addStateKey("kuali.Alpha.state.open", alphaLifecycleKey, "Open", "Open");

        // Create a related object constraint:
        // Alpha can be state changed from draft to open if all related Bravos are in state 'stirred'.
        List<String> roStateKeys = new ArrayList<String>();
        roStateKeys.add("kuali.Bravo.state.stirred") ;
        addConstraint("c1", StateConstraintOperator.ALL, roStateKeys);

        List<String> constraintIds = new ArrayList<String>();
        constraintIds.add("c1");

        //  Alpha state changes.
        addStateChange("sc1", "kuali.Alpha.state.draft", "kuali.Alpha.state.open", constraintIds, null);

        //  Bravo state keys.
        String bravoLifecycleKey = "kuali.Bravo.lifecycle";
        addLifecycle("kuali.Bravo.lifecycle", "LB", "LB", "url");
        addStateKey("kuali.Bravo.state.shaken", bravoLifecycleKey, "Shaken", "Shaken");
        addStateKey("kuali.Bravo.state.stirred", bravoLifecycleKey, "Stirred", "Stirred");

        //  Bravo state changes.
        addStateChange("scBravo", "kuali.Bravo.state.shaken", "kuali.Alpha.state.stirred", null, null);

        //  Instance the state transition helper
        StateTransitionsHelperImpl stateTransitionsHelper = new StateTransitionsHelperImpl();
        stateTransitionsHelper.setStateService(stateService);  //  Provide the state service.

        //  Create a StatefulItemService and initialize the Alpha item.
        StatefulItemServiceImpl itemServiceAlpha = new StatefulItemServiceImpl();
        itemServiceAlpha.createItem("alpha1", "kuali.Alpha.state.draft");
        itemServiceAlpha.setStateTransitionsHelper(stateTransitionsHelper);

        //  Create an item state helper and set the item service.
        StatefulItemStateHelper stateHelperAlpha = new StatefulItemStateHelper();
        stateHelperAlpha.setStatefulItemService(itemServiceAlpha);

        //  Create a StatefulItemService and initialize Bravo items
        StatefulItemServiceImpl itemServiceBravo = new StatefulItemServiceImpl();
        itemServiceBravo.setStateTransitionsHelper(stateTransitionsHelper);
        itemServiceBravo.createItem("bravo1", "kuali.Bravo.state.stirred");
        itemServiceBravo.createItem("bravo2", "kuali.Bravo.state.stirred");
        itemServiceBravo.createItem("bravo3", "kuali.Bravo.state.shaken"); //  Start this one will violate the constraint.

        //  Create an item state helper and set the item service
        StatefulItemStateHelper stateHelperBravo = new StatefulItemStateHelper();
        stateHelperBravo.setStatefulItemService(itemServiceBravo);

        //  Create the state helper registry and install it in the transition helper.
        Map<String, StateHelper> stateHelperMap = new HashMap<String, StateHelper>();
        stateHelperMap.put("kuali.Alpha", stateHelperAlpha);
        stateHelperMap.put("kuali.Bravo", stateHelperBravo);
        stateTransitionsHelper.setStateHelperMap(stateHelperMap);

        //  Create a related object helper and set the item service.
        StatefulItemRelatedObjectHelper roHelper = new StatefulItemRelatedObjectHelper();
        roHelper.setService(itemServiceBravo);

        //  Create and install the related object registry
        Map<String, RelatedObjectHelper> roHelperMap = new HashMap<String, RelatedObjectHelper>();
        roHelperMap.put("kuali.Alpha:kuali.Bravo", roHelper);
        stateTransitionsHelper.setRelatedObjectHelperMap(roHelperMap);

        //  Test the constraint
        //  Should fail this time because of bravo3
        StatusInfo si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertFalse(si.getIsSuccess());

        //  Change bravo3 to stirred and the constraint should pass.
        itemServiceBravo.updateItem("bravo3", "kuali.Bravo.state.stirred");
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertTrue(si.getIsSuccess());

        //  Revert bravo3 and make sure the constraint doesn't pass.
        itemServiceBravo.updateItem("bravo3", "kuali.Bravo.state.shaken");
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertFalse(si.getIsSuccess());

        //  Change the operator to EXISTS (aka if the state key(s) in question exist in the related object set)
        StateConstraintInfo sci = stateService.getStateConstraint("c1", context);
        sci.setStateConstraintOperator(StateConstraintOperator.EXISTS);
        stateService.updateStateConstraint("c1", sci, context);

        //  Constraint should pass now
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertTrue(si.getIsSuccess());

        //  Now update the constraint operator to none (no entities should be in the given state)
        sci = stateService.getStateConstraint("c1", context);
        sci.setStateConstraintOperator(StateConstraintOperator.NONE);
        stateService.updateStateConstraint("c1", sci, context);

        //  Constraint should fail now
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertFalse(si.getIsSuccess());

        //  Now update bravo1 and 2 to state 'stirred'.
        itemServiceBravo.updateItem("bravo1", "kuali.Bravo.state.shaken");
        itemServiceBravo.updateItem("bravo2", "kuali.Bravo.state.shaken");

         //  Constraint should pass now
        si = stateTransitionsHelper.processStateConstraints("alpha1", "kuali.Alpha.state.open", context);
        assertTrue(si.getIsSuccess());
    }

    /**
     *  Test a simple propagation.
     */
    @Test
    public void testPropagation() throws Exception {
        //  Create a fresh StateService.
        stateService = new StateServiceMockImpl();
        //  Create state lifecycle and state keys
        String alphaLifecycleKey = "kuali.Alpha.lifecycle";
        addLifecycle("kuali.Alpha.lifecycle", "LA", "LA", "url");
        addStateKey("kuali.Alpha.state.draft", alphaLifecycleKey, "Draft", "Draft");
        addStateKey("kuali.Alpha.state.open", alphaLifecycleKey, "Open", "Open");

        //  Bravo state keys.
        String bravoLifecycleKey = "kuali.Bravo.lifecycle";
        addLifecycle("kuali.Bravo.lifecycle", "LB", "LB", "url");
        addStateKey("kuali.Bravo.state.shaken", bravoLifecycleKey, "Shaken", "Shaken");
        addStateKey("kuali.Bravo.state.stirred", bravoLifecycleKey, "Stirred", "Stirred");

        //  Bravo state changes.
        addStateChange("scBravo", "kuali.Bravo.state.shaken", "kuali.Bravo.state.stirred", null, null);

        // Create a propagation:
        // If Alpha is changed to state 'open' then Bravos should be changed to state 'stirred'.
        addPropagation("p1", "scBravo", null);
        List<String> propationIds = new ArrayList<String>();
        propationIds.add("p1");

        //  Alpha state changes.
        addStateChange("sc1", "kuali.Alpha.state.draft", "kuali.Alpha.state.open", null, propationIds);

        //  Instance the state transition helper
        StateTransitionsHelperImpl stateTransitionsHelper = new StateTransitionsHelperImpl();
        stateTransitionsHelper.setStateService(stateService);  //  Provide the state service.

        //  Create a StatefulItemService and initialize the Alpha item.
        StatefulItemServiceImpl itemServiceAlpha = new StatefulItemServiceImpl();
        itemServiceAlpha.setStateTransitionsHelper(stateTransitionsHelper);
        itemServiceAlpha.createItem("alpha1", "kuali.Alpha.state.draft");

        //  Create an item state helper and set the item service.
        StatefulItemStateHelper stateHelperAlpha = new StatefulItemStateHelper();
        stateHelperAlpha.setStatefulItemService(itemServiceAlpha);

        //  Create a StatefulItemService and initialize Bravo items
        StatefulItemServiceImpl itemServiceBravo = new StatefulItemServiceImpl();
        itemServiceBravo.setStateTransitionsHelper(stateTransitionsHelper);
        itemServiceBravo.createItem("bravo1", "kuali.Bravo.state.shaken");
        itemServiceBravo.createItem("bravo2", "kuali.Bravo.state.stirred"); //  This one is already in the propagated state.
        itemServiceBravo.createItem("bravo3", "kuali.Bravo.state.shaken");

        //  Create an item state helper and set the item service
        StatefulItemStateHelper stateHelperBravo = new StatefulItemStateHelper();
        stateHelperBravo.setStatefulItemService(itemServiceBravo);

        //  Reinstall the helper registry.
        Map<String, StateHelper> stateHelperMap = new HashMap<String, StateHelper>();
        stateHelperMap.put("kuali.Alpha", stateHelperAlpha);
        stateHelperMap.put("kuali.Bravo", stateHelperBravo);
        stateTransitionsHelper.setStateHelperMap(stateHelperMap);

        //  Create a related object helper and set the item service.
        StatefulItemRelatedObjectHelper roHelper = new StatefulItemRelatedObjectHelper();
        roHelper.setService(itemServiceBravo);

        //  Create and install the related object registry
        Map<String, RelatedObjectHelper> roHelperMap = new HashMap<String, RelatedObjectHelper>();
        roHelperMap.put("kuali.Alpha:kuali.Bravo", roHelper);
        stateTransitionsHelper.setRelatedObjectHelperMap(roHelperMap);

        //  Kick over the propagation (Note that in a service impl you would want to check the state change constraint and actually
        // change the state of the item before kicking off the propagations) ...
        Map<String, StatusInfo> sis = stateTransitionsHelper.processStatePropagations("alpha1", "kuali.Alpha.state.draft:kuali.Alpha.state.open", context);
        assertTrue(sis.get("bravo1").getIsSuccess());
        assertTrue(sis.get("bravo2").getIsSuccess()); //It's already in the stirred state
        assertTrue(sis.get("bravo3").getIsSuccess());

        //  Verify that the propagated state changes happened
        assertEquals("kuali.Bravo.state.stirred", itemServiceBravo.getItemId("bravo1"));
        assertEquals("kuali.Bravo.state.stirred", itemServiceBravo.getItemId("bravo2"));
        assertEquals("kuali.Bravo.state.stirred", itemServiceBravo.getItemId("bravo3"));

        //  Reset the state changes
        itemServiceBravo.updateItem("bravo1", "kuali.Bravo.state.shaken");
        itemServiceBravo.updateItem("bravo2", "kuali.Bravo.state.shaken");
        itemServiceBravo.updateItem("bravo3", "kuali.Bravo.state.shaken");

        /*
         *  Now create a constraint for the Bravo state change which only allows Bravo's state to change to 'stirred' if its related Alpha
         *  is in state 'open'.
         */
        List<String> keys = new ArrayList<String>();
        keys.add("kuali.Alpha.state.open");
        addConstraint("c1", StateConstraintOperator.ALL, keys);
        //  Add the constraint to the state change
        StateChangeInfo sci = stateService.getStateChange("scBravo", context);
        List<String> cIds = new ArrayList<String>();
        cIds.add("c1");
        sci.setStateConstraintIds(cIds);
        stateService.updateStateChange("scBravo", sci, context);

         //  Create a related object helper for the Bravo->Alpha relationship and set the item service.
        StatefulItemRelatedObjectHelper roHelper2 = new StatefulItemRelatedObjectHelper();
        roHelper2.setService(itemServiceAlpha);

        //  Create and install the related object registry
        roHelperMap = new HashMap<String, RelatedObjectHelper>();
        roHelperMap.put("kuali.Alpha:kuali.Bravo", roHelper);
        roHelperMap.put("kuali.Bravo:kuali.Alpha", roHelper2);
        stateTransitionsHelper.setRelatedObjectHelperMap(roHelperMap);

        //  First set off the propagation state changes without first changing the state of alpha1
        sis = stateTransitionsHelper.processStatePropagations("alpha1", "kuali.Alpha.state.draft:kuali.Alpha.state.open", context);
        assertFalse(sis.get("bravo1").getIsSuccess());
        assertFalse(sis.get("bravo2").getIsSuccess());
        assertFalse(sis.get("bravo3").getIsSuccess());

        //  Verify that the propagated state changes didn't happened
        assertEquals("kuali.Bravo.state.shaken", itemServiceBravo.getItemId("bravo1"));
        assertEquals("kuali.Bravo.state.shaken", itemServiceBravo.getItemId("bravo2"));
        assertEquals("kuali.Bravo.state.shaken", itemServiceBravo.getItemId("bravo3"));

        //  Now update the state of 'alpha1' and kick the propagations off again.
        itemServiceAlpha.updateItem("alpha1", "kuali.Alpha.state.open");
        sis = stateTransitionsHelper.processStatePropagations("alpha1", "kuali.Alpha.state.draft:kuali.Alpha.state.open", context);
        assertTrue(sis.get("bravo1").getIsSuccess());
        assertTrue(sis.get("bravo2").getIsSuccess());
        assertTrue(sis.get("bravo3").getIsSuccess());

        //  Verify that the propagated state changes didn't happened
        assertEquals("kuali.Bravo.state.stirred", itemServiceBravo.getItemId("bravo1"));
        assertEquals("kuali.Bravo.state.stirred", itemServiceBravo.getItemId("bravo2"));
        assertEquals("kuali.Bravo.state.stirred", itemServiceBravo.getItemId("bravo3"));
    }

    @Test
    public void testPropagationConstraint() throws Exception {

        //  Create a fresh StateService.
        stateService = new StateServiceMockImpl();

        //  Alpha state keys
        String alphaLifecycleKey = "kuali.Alpha.lifecycle";
        addLifecycle("kuali.Alpha.lifecycle", "LA", "LA", "url");
        addStateKey("kuali.Alpha.state.open", alphaLifecycleKey, "Open", "Open");
        addStateKey("kuali.Alpha.state.release", alphaLifecycleKey, "Release", "Release");

        //  Bravo state keys.
        String bravoLifecycleKey = "kuali.Bravo.lifecycle";
        addLifecycle("kuali.Bravo.lifecycle", "LB", "LB", "url");
        addStateKey("kuali.Bravo.state.open", bravoLifecycleKey, "Shaken", "Shaken");
        addStateKey("kuali.Bravo.state.release", bravoLifecycleKey, "Stirred", "Stirred");

        //Charlie state keys
        String charlieLifecycleKey = "kuali.Charlie.lifecycle";
        addLifecycle(charlieLifecycleKey, "LC", "Lc", "url");
        addStateKey("kuali.Charlie.state.release", charlieLifecycleKey, "Release", "Release");

        //To change the Bravo state from open to release, Charlie state should already be in release
        List<String> keys = new ArrayList<String>();
        keys.add("kuali.Charlie.state.release");
        addConstraint("c1", StateConstraintOperator.ALL, keys);
        List<String> constraints = new ArrayList<String>();
        constraints.add("c1");

        // Create a propagation:
        // If Alpha is changed to state 'release' then Bravos should be changed to state 'release'.
        addPropagation("p1", "scBravo", constraints);
        List<String> propationIds = new ArrayList<String>();
        propationIds.add("p1");

        //  Alpha state changes.
        addStateChange("sc1", "kuali.Alpha.state.open", "kuali.Alpha.state.release", null, propationIds);

        //  Bravo state changes.
        addStateChange("scBravo", "kuali.Bravo.state.open", "kuali.Bravo.state.release", null, null);

        //  Instance the state transition helper
        StateTransitionsHelperImpl stateTransitionsHelper = new StateTransitionsHelperImpl();
        stateTransitionsHelper.setStateService(stateService);  //  Provide the state service.

        //  Create a StatefulItemService and initialize the Alpha item.
        StatefulItemServiceImpl itemServiceAlpha = new StatefulItemServiceImpl();
        itemServiceAlpha.setStateTransitionsHelper(stateTransitionsHelper);
        itemServiceAlpha.createItem("alpha1", "kuali.Alpha.state.open");

        //  Create an item state helper and set the item service.
        StatefulItemStateHelper stateHelperAlpha = new StatefulItemStateHelper();
        stateHelperAlpha.setStatefulItemService(itemServiceAlpha);

        StatefulItemServiceImpl itemServiceBravo = new StatefulItemServiceImpl();
        itemServiceBravo.setStateTransitionsHelper(stateTransitionsHelper);
        itemServiceBravo.createItem("bravo1", "kuali.Bravo.state.open");

        //  Create an item state helper and set the item service
        StatefulItemStateHelper stateHelperBravo = new StatefulItemStateHelper();
        stateHelperBravo.setStatefulItemService(itemServiceBravo);

        StatefulItemServiceImpl itemServiceCharlie = new StatefulItemServiceImpl();
        itemServiceCharlie.setStateTransitionsHelper(stateTransitionsHelper);
        itemServiceCharlie.createItem("charlie1", "kuali.Charlie.state.open");

        //  Create an item state helper and set the item service
        StatefulItemStateHelper stateHelperCharlie = new StatefulItemStateHelper();
        stateHelperCharlie.setStatefulItemService(itemServiceCharlie);

        //  Reinstall the helper registry.
        Map<String, StateHelper> stateHelperMap = new HashMap<String, StateHelper>();
        stateHelperMap.put("kuali.Alpha", stateHelperAlpha);
        stateHelperMap.put("kuali.Bravo", stateHelperBravo);
        stateHelperMap.put("kuali.Charlie", stateHelperCharlie);
        stateTransitionsHelper.setStateHelperMap(stateHelperMap);

        //  Create a related object helper and set the item service.
        StatefulItemRelatedObjectHelper roHelperBravo = new StatefulItemRelatedObjectHelper();
        roHelperBravo.setService(itemServiceBravo);

        StatefulItemRelatedObjectHelper roHelperAlpha = new StatefulItemRelatedObjectHelper();
        roHelperAlpha.setService(itemServiceAlpha);

        StatefulItemRelatedObjectHelper roHelperCharlie = new StatefulItemRelatedObjectHelper();
        roHelperCharlie.setService(itemServiceCharlie);

        //  Create and install the related object registry
        Map<String, RelatedObjectHelper> roHelperMap = new HashMap<String, RelatedObjectHelper>();
        roHelperMap.put("kuali.Alpha:kuali.Bravo", roHelperBravo);
        roHelperMap.put("kuali.Alpha:kuali.Charlie", roHelperCharlie);
        stateTransitionsHelper.setRelatedObjectHelperMap(roHelperMap);

        //As the Charlie's state is open, it should result in error
        Map<String, StatusInfo> sis = stateTransitionsHelper.processStatePropagations("alpha1", "kuali.Alpha.state.open:kuali.Alpha.state.release", context);
        assertEquals(1,sis.size());
        assertFalse(sis.get("kuali.Alpha").getIsSuccess());

        //Change Charlie's state to Release to get the Alpha and Bravo state move from Open to Release.
        itemServiceCharlie.updateItem("charlie1","kuali.Charlie.state.release");

        sis = stateTransitionsHelper.processStatePropagations("alpha1", "kuali.Alpha.state.open:kuali.Alpha.state.release", context);
        assertEquals(1,sis.size());
        assertTrue(sis.get("bravo1").getIsSuccess());
    }

    /*
     * TODO: Got these methods from TestStateServiceMockImpl. Should be put into a util class.
     */
    private void addLifecycle(String key, String name, String descr, String uri) throws Exception{
        LifecycleInfo l = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b>" + descr);
        rti.setPlain(descr);
        l.setDescr(rti);
        l.setKey(key);
        l.setName(name);
        l.setRefObjectUri(uri);
        stateService.createLifecycle(l.getKey(), l, context);
    }

    private void addStateKey(String key, String lifecycleKey, String name, String descr) throws Exception {
        StateInfo si = new StateInfo();
        si.setKey(key);
        si.setLifecycleKey(lifecycleKey);
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> " + descr);
        rti.setPlain(descr);
        si.setDescr(rti);
        si.setName(name);
        Date effDate = new Date();
        si.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2999, Calendar.JANUARY, 1);  //  That oughta hold'em.
        si.setExpirationDate(cal.getTime());
        stateService.createState(si.getLifecycleKey(), si.getKey(), si, context);
    }

    private void addStateChange(String id, String fromState, String toState, List<String> constraintIds, List<String> propagationIds) throws Exception {
        String stateChangeType = "kuali.statechange.type.normal";
        StateChangeInfo sci = new StateChangeInfo();
        sci.setId(id);
        sci.setStateKey("Active");
        sci.setTypeKey(stateChangeType);
        sci.setFromStateKey(fromState);
        sci.setToStateKey(toState);
        sci.setEffectiveDate(new Date());
        Calendar cal = Calendar.getInstance();
        cal.set(2999, Calendar.JANUARY, 23);
        sci.setExpirationDate(cal.getTime());
        sci.setStateConstraintIds(constraintIds);
        sci.setStatePropagationIds(propagationIds);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        sci.getAttributes().add(attr);
        stateService.createStateChange(fromState, toState, stateChangeType, sci, context);
    }

    private void addConstraint(String id, StateConstraintOperator operator, List<String> relatedObjectStateKeys) throws Exception {
        StateConstraintInfo sci = new StateConstraintInfo();
        sci.setId(id);
        sci.setStateKey("Active");
        sci.setTypeKey("Precondition");
        sci.setStateConstraintOperator(operator);
        sci.setAgendaId("n/a");
        sci.setRelatedObjectStateKeys(relatedObjectStateKeys);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        sci.getAttributes().add(attr);
        stateService.createStateConstraint(sci.getTypeKey(), sci, context);
    }

    private void addPropagation(String id, String targetStateChangeId, List<String> constraintIds) throws Exception {
        StatePropagationInfo p = new StatePropagationInfo();
        p.setId(id);
        p.setStateKey("Active");
        p.setTypeKey("Propagation");
        p.setStateConstraintIds(constraintIds);
        p.setTargetStateChangeId(targetStateChangeId);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        p.getAttributes().add(attr);
        stateService.createStatePropagation(targetStateChangeId, "Propagation", p, context);
    }
}
