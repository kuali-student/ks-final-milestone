/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.atp.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.class1.atp.service.decorators.AtpServiceValidationDecorator;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class holds tests for the validation methods in AtpService that are implemented in AtpServiceValidationDecorator
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAtpServiceValidationDecorator {

    @Autowired
    private AtpServiceValidationDecorator atpService;
    //public ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"acal-test-context.xml"});
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        //atpService = appContext.getBean(AtpServiceValidationDecorator.class);
    }

    @Test
    @Ignore
    public void testValidateMilestone()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MilestoneInfo milestone = new MilestoneInfo();

        // validation should have problems with a new, incomplete milestone
        List<ValidationResultInfo> validationResults =
                atpService.validateMilestone("FULL_VALIDATION", milestone, callContext);
        for (ValidationResultInfo vri : validationResults) {
            System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(6, validationResults.size());

        // populate two of the three required fields (key, type, state) and validation
        // should now return a list with only one error, for the "stateKey" field
        milestone.setId("newId");
        milestone.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        validationResults = atpService.validateMilestone("FULL_VALIDATION", milestone, callContext);
        assertEquals(5, validationResults.size());
        assertEquals("stateKey", validationResults.get(0).getElement());

        // validation should pass once the stateKey is provided
        milestone.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        validationResults = atpService.validateMilestone("FULL_VALIDATION", milestone, callContext);
        assertNotNull("validateMilestone() should return an empty list, not null.", milestone);
        assertEquals("validateMilestone() should have returned zero errors.", 4, validationResults.size());
    }

    @Test
    @Ignore
    public void testValidateAtp() throws Exception {
        AtpInfo atpInfo = new AtpInfo();

        // validation should have problems with a new, incomplete ATP
        List<ValidationResultInfo> validationResults;
        validationResults = atpService.validateAtp("FULL_VALIDATION", atpInfo.getId(), atpInfo, callContext);
        assertEquals(4, validationResults.size());

        // populate two of the three required fields (key, type, state) and validation
        // should now return a list with only one error, for the "stateKey" field
        atpInfo.setId("newId");
        atpInfo.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        validationResults = atpService.validateAtp("FULL_VALIDATION", atpInfo.getId(), atpInfo, callContext);
        assertEquals(3, validationResults.size());
        assertEquals("stateKey", validationResults.get(0).getElement());

        // validation should pass once the stateKey is provided
        atpInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        validationResults = atpService.validateAtp("FULL_VALIDATION", atpInfo.getId(), atpInfo, callContext);
        assertNotNull("validateAtp() should return an empty list, not null.", atpInfo);
        assertEquals("validateAtp() should have returned zero errors.", 2, validationResults.size());
    }
}
