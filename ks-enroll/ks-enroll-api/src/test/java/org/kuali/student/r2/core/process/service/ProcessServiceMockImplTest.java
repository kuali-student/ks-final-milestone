/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process.service;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

/**
 *
 * @author nwright
 */
public class ProcessServiceMockImplTest {
    
    public ProcessServiceMockImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    private static final String TEST_PRINCIPAL_ID1 = "testPrincipalId1";
    private static final String TEST_PRINCIPAL_ID2 = "testPrincipalId2";
    private ContextInfo getContext() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipalId1");
        return context;
    }
    private ProcessService instance = new ProcessServiceMockImpl();

    /**
     * Test of createProcess method, of class ProcessServiceMockImpl.
     */
    @Test
    public void testProcessCrud() throws Exception {
        System.out.println("createProcess");
        ContextInfo context = getContext();
        // create
        String processRequestId = "request1";
        ProcessInfo info = new ProcessInfo();
        info.setTypeKey(ProcessServiceConstants.PROCESS_TYPE_KEY);
        info.setStateKey(ProcessServiceConstants.PROCESS_ENABLED_STATE_KEY);
        info.setKey(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY);
        info.setName("basic eligiblity");
        Date before = new Date();
        ProcessInfo result = instance.createProcess(processRequestId, info, context);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(info.getKey (), result.getKey());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().before(before)) {
            fail("create time should not be before the call");
        }
        if (result.getMeta().getCreateTime().after(after)) {
            fail("create time should not be after the call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // READ/get
        info = new ProcessInfo(result);

        result = instance.getProcess(info.getKey(), context);
        assertEquals(result.getKey(), info.getKey());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new ProcessInfo(result);
        info.setName("new name");
        context.setPrincipalId(TEST_PRINCIPAL_ID2);
        before = new Date();
        result = instance.updateProcess(info.getKey(), info, context);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals (info.getKey(), result.getKey());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getName(), result.getName());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd())>= 0) {
            fail ("version ind should be lexically greater than the old version id");
        }
        
        // delete
    }

    /**
     * Test of createInstruction method, of class ProcessServiceMockImpl.
     */
    @Test
    public void testInstructionCrud() throws Exception {
        System.out.println("createInstruction");
        InstructionInfo instructionInfo = new InstructionInfo();
        instructionInfo.setTypeKey(ProcessServiceConstants.INSTRUCTION_TYPE_KEY);
        instructionInfo.setStateKey(ProcessServiceConstants.INSTRUCTION_ENABLED_STATE_KEY);
//        instructionInfo.setCheckKey(ProcessServiceConstants.);
        ContextInfo context = getContext();
        Date before = new Date();
        InstructionInfo result = instance.createInstruction(instructionInfo.getProcessKey(), instructionInfo.getCheckKey(), instructionInfo, context);
        Date after = new Date();
        if (result == instructionInfo) {
            fail("returned object should not be the same as the one passed in");
        }
        assertNotNull(result.getId());
        assertEquals(instructionInfo.getTypeKey(), result.getTypeKey());
        assertEquals(instructionInfo.getStateKey(), result.getStateKey());
//        assertEquals(instructionInfo.getPersonId(), result.getPersonId());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().before(before)) {
            fail("create time should not be before the call");
        }
        if (result.getMeta().getCreateTime().after(after)) {
            fail("create time should not be after the call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());
    }

}
