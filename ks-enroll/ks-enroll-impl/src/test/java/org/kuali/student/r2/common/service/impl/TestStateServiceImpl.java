package org.kuali.student.r2.common.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestStateServiceImpl {

    @Autowired
    private StateService stateService;

    public static String principalId = "123";

    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testClient() {
        assertNotNull(stateService);
    }

    @Test
    public void testGetState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StateInfo stateInfo = stateService.getState(AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
        assertNotNull(stateInfo);
        assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);

        try {
            StateInfo invalid = stateService.getState("invalid.state.key", callContext);
            assertNull(invalid);
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    @Test
    public void testGetStatesByLifecycle() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateInfo> stateInfo = stateService.getStatesByLifecycle(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
        assertNotNull(stateInfo);
        assertEquals(stateInfo.size(), 2);
    }

    @Test
    public void testGetLifecycleByKey() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LifecycleInfo spInfo = stateService.getLifecycle(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
        assertNotNull(spInfo);
        assertEquals(spInfo.getKey(), AtpServiceConstants.ATP_PROCESS_KEY);
    }

    @Test(expected = AlreadyExistsException.class)
    public void testCreateDuplicateState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException {
        StateInfo newState = new StateInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> duplicate for testing purposes");
        rti.setPlain("duplicate for testing purposes");
        newState.setDescr(rti);
        newState.setKey("kuali.atp.state.Draft");
        newState.setName("Draft");
        StateInfo createdState = stateService.createState(AtpServiceConstants.ATP_PROCESS_KEY, "kuali.atp.state.Draft", newState, callContext);
        assertNull(createdState);
    }

    @Test
    public void testCRUDState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        // create
        StateInfo newState = new StateInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> state for testing purposes");
        rti.setPlain("Plain state for testing purposes");
        newState.setDescr(rti);
        newState.setKey("kuali.atp.state.Testing");
        newState.setName("Testing");
        Date effDate = new Date();
        newState.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2012, 8, 23);
        newState.setExpirationDate(cal.getTime());
        StateInfo createdState = stateService.createState(AtpServiceConstants.ATP_PROCESS_KEY, "kuali.atp.state.Testing", newState, callContext);
        // read
        assertNotNull(createdState);
        assertEquals("kuali.atp.state.Testing", createdState.getKey());
        assertEquals("Testing", createdState.getName());
        assertEquals(effDate, createdState.getEffectiveDate());
        assertEquals(cal.getTime(), createdState.getExpirationDate());
        // update
        createdState.setName("DifferentName");
        StateInfo updatedState = stateService.updateState("kuali.atp.state.Testing", createdState, callContext);
        assertEquals("DifferentName", updatedState.getName());
        // delete
        StatusInfo result = stateService.deleteState("kuali.atp.state.Testing", callContext);
        assertEquals(true, result.getIsSuccess());
    }

    @Test(expected = PermissionDeniedException.class)
    public void testShouldNotDeleteStateWithRelationships() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        StatusInfo result = stateService.deleteState("kuali.atp.state.Draft", callContext);// should not allow delete because of existing relationships
        assertEquals(false, result.getIsSuccess());
    }

    @Test
    public void testGetStatesByKeys() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> stateKeys = new ArrayList<String>();
        stateKeys.add("kuali.milestone.state.Official");
        stateKeys.add("kuali.atp.atp.relation.state.inactive");
        stateKeys.add("kuali.atp.state.Official");
        List<StateInfo> list = stateService.getStatesByKeys(stateKeys, callContext);
        assertEquals(3, list.size());
    }

    @Test
    public void testCRUDLifecycle() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        // create
        LifecycleInfo newLifecycle = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> state for testing purposes");
        rti.setPlain("Plain state for testing purposes");
        newLifecycle.setDescr(rti);
        newLifecycle.setKey("kuali.test.lifecycle");
        newLifecycle.setName("Testing");
        newLifecycle.setRefObjectUri("refTest");
        LifecycleInfo createdLifecycle = stateService.createLifecycle("kuali.test.lifecycle", newLifecycle, callContext);
        // read
        assertNotNull(createdLifecycle);
        assertEquals("kuali.test.lifecycle", createdLifecycle.getKey());
        assertEquals("Testing", createdLifecycle.getName());
        assertEquals("refTest", createdLifecycle.getRefObjectUri());
        // update
        createdLifecycle.setName("Different Lifecycle");
        LifecycleInfo updatedLifecycle = stateService.updateLifecycle("kuali.test.lifecycle", createdLifecycle, callContext);
        assertEquals("Different Lifecycle", updatedLifecycle.getName());
        // delete
        StatusInfo result = stateService.deleteLifecycle("kuali.test.lifecycle", callContext);
        assertEquals(true, result.getIsSuccess());
    }

    @Test(expected = PermissionDeniedException.class)
    public void testShouldNotDeleteLifecycleWithRelationships() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        StatusInfo result = stateService.deleteLifecycle("kuali.atp.process", callContext);// should not allow delete because of existing relationships
        assertEquals(false, result.getIsSuccess());
    }

    @Test
    public void testGetLifecyclesByKeys() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> lifecycleKeys = new ArrayList<String>();
        lifecycleKeys.add("kuali.atp.process");
        lifecycleKeys.add("kuali.atp.atp.relation.process");
        lifecycleKeys.add("kuali.atp.milestone.relation.process");
        List<LifecycleInfo> list = stateService.getLifecyclesByKeys(lifecycleKeys, callContext);
        assertEquals(3, list.size());
    }

    // @Test
    // public void testGetInitialValidStates()throws DoesNotExistException, InvalidParameterException,
    // MissingParameterException, OperationFailedException, PermissionDeniedException{
    // List<StateInfo> stateInfo = stateService.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    // assertNotNull(stateInfo);
    // assertEquals(stateInfo.size(), 1);
    // }
    //
    // @Test
    // public void testGetNextHappyState()throws DoesNotExistException, InvalidParameterException, MissingParameterException,
    // OperationFailedException, PermissionDeniedException{
    // StateInfo stateInfo = stateService.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY,
    // AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
    // assertNotNull(stateInfo);
    // assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
    // }
}
