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
package org.kuali.student.r2.core.class1.atp.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator.ValidationType;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.atp.service.decorators.AtpServiceValidationDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class holds tests for the validation methods in AtpService that are implemented in AtpServiceValidationDecorator
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class TestAtpServiceValidationDecorator {

    public AtpService atpService;
    public ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"acal-test-context.xml"});
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
    
    @Before
    public void setUp() {
        principalId = "123";
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
        
        atpService = appContext.getBean(AtpServiceValidationDecorator.class);
    }
    
    @Test
    public void testValidateAtpMilestoneRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpMilestoneRelationInfo rel = new AtpMilestoneRelationInfo();
        
        rel.setAtpKey("testAtpId1");
        rel.setMilestoneKey("testId");
        rel.setEffectiveDate(new Date());
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2100);
        rel.setExpirationDate(cal.getTime());
        
        rel.setId("newRelId");
        rel.setStateKey(AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        rel.setTypeKey("kuali.atp.milestone.relation.owns");
        
        List<ValidationResultInfo> existingResults = atpService.validateAtpMilestoneRelation(ValidationType.FULL_VALIDATION.toString(), rel, callContext);
        
        assertTrue(existingResults == null || existingResults.isEmpty());
        
        AtpMilestoneRelationInfo invalid = new AtpMilestoneRelationInfo();
        
        List<ValidationResultInfo> invalidResults = atpService.validateAtpMilestoneRelation("FULL_VALIDATION", invalid, callContext);
        
        assertTrue(!invalidResults.isEmpty());
    }
    
    @Test
    public void testValidateMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        // validating a fully populated milestone should return an empty list
        MilestoneInfo existingMilestone = new MilestoneInfo();
        existingMilestone.setKey("newId");
        existingMilestone.setName("testCreate");
        existingMilestone.setStartDate(new Date());
        existingMilestone.setDateRange(false);
        existingMilestone.setAllDay(true);
        existingMilestone.setStateKey("kuali.atp.state.Draft");
        existingMilestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        existingMilestone.setDescr(descr);
        
        List<ValidationResultInfo> existingResults = atpService.validateMilestone("FULL_VALIDATION", existingMilestone, callContext);
        
        assertTrue(existingResults == null || existingResults.isEmpty());
        
        MilestoneInfo invalid = new MilestoneInfo();
        
        List<ValidationResultInfo> invalidResults = atpService.validateMilestone("FULL_VALIDATION", invalid, callContext);
        
        assertTrue(!invalidResults.isEmpty());        
    }
    
    @Test
    public void testValidateAtp()throws DoesNotExistException, InvalidParameterException, MissingParameterException,OperationFailedException {
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setKey("newId");
        atpInfo.setName("newId");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        try{
        	List<ValidationResultInfo> vri= atpService.validateAtp("FULL_VALIDATION", atpInfo, callContext);
        	assertTrue(vri.isEmpty());
        } catch (Exception ex) {
            //fail("exception from service call :" + ex.getMessage());
        	//TODO: test exception aspect
        } 
    }
}
