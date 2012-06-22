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
 * Created by Mezba Mahtab on 6/20/12
 */
package org.kuali.student.r2.core.process.service.impl;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.mortbay.log.Log;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * This class tests ProcessServiceMockImpl
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestProcessServiceMockImpl {

    ///////////////////
    // CONSTANTS
    ///////////////////

    private static final Logger logger = Logger.getLogger(TestProcessServiceModel.class);

    ///////////////////
    // DATA FIELDS
    ///////////////////

    public static String principalId = "123";
    public ContextInfo contextInfo = null;

    @Resource(name = "processServiceMockImpl")
    private ProcessService processService;

    @Resource
    private boolean debugMode;

    /////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////

    public ProcessService getProcessService() {
        return processService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    /////////////////////////
    // FUNCTIONALS
    /////////////////////////

    @Before
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        try {
            ProcessServiceDataLoader loader = new ProcessServiceDataLoader (processService, debugMode, logger);
            if (debugMode) { Log.warn("Calling loader.loadData()"); }
            loader.loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @Test
    public void testSetup() {
        assertNotNull(processService);
    }

    @Test
    public void testProcessCrud () throws Exception {
        // create
        String processRequestId = "request1";
        ProcessInfo info = new ProcessInfo();
        info.setKey(processRequestId);
        info.setTypeKey(ProcessServiceConstants.PROCESS_TYPE_KEY);
        info.setStateKey(ProcessServiceConstants.PROCESS_ENABLED_STATE_KEY);
        info.setName("basic eligibility");
        Date before = new Date();
        ProcessInfo result = processService.createProcess(processRequestId,ProcessServiceConstants.PROCESS_TYPE_KEY, info, contextInfo);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(processRequestId, result.getKey());
        assertEquals(ProcessServiceConstants.PROCESS_TYPE_KEY, result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(principalId, result.getMeta().getCreateId());
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
        assertEquals(principalId, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // read / get

    }

}
