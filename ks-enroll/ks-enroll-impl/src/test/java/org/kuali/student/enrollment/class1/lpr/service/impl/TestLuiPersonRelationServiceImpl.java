/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.enrollment.class1.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.enrollment.class1.lpr.model.LuiPersonRelationStateEntity;
import org.kuali.student.enrollment.class1.lpr.service.utilities.Constants;
import org.kuali.student.enrollment.class1.lpr.service.utilities.DataLoader;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Author sambit
 */

public class TestLuiPersonRelationServiceImpl {


    private static final String LUI_ID2 = "testLuiId2";
	private static final String PERSON_ID2 = "testPersonId2";
	public LuiPersonRelationService lprService;
    public ApplicationContext appContext;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    private DataLoader dataLoader;


    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    @Before
    public void setUp() {
        principalId = "123";
        appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml", "testContext.xml"});
        lprService = (LuiPersonRelationService) appContext.getBean("lprPersistenceService");
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
        dataLoader = (DataLoader) appContext.getBean("dataLoader");
        dataLoader.load();
    }

    @Test
    public void testCreateLuiPersonRelation() throws MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.DoesNotExistException, org.kuali.student.common.exceptions.PermissionDeniedException, OperationFailedException {
    	LuiPersonRelationEntity lpr = new LuiPersonRelationEntity();
    	lpr.setLuiId(LUI_ID2);
    	lpr.setPersonId(PERSON_ID2);
    	LuiPersonRelationStateEntity lprState = new LuiPersonRelationStateEntity();
    }

    @Test
    public void testFindLuiPersonRelationsForLui() throws MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.DoesNotExistException, org.kuali.student.common.exceptions.PermissionDeniedException, OperationFailedException {
        List<LuiPersonRelationInfo> personRelationInfos = lprService.findLuiPersonRelationsForLui(Constants.LUI_ID1, ContextInfo.newInstance());
        assertNotNull(personRelationInfos);
        assertEquals(personRelationInfos.size(), 1);

        LuiPersonRelationInfo personRelationInfo = personRelationInfos.get(0);
        assertNotNull(personRelationInfo);
        assertEquals(Constants.LUI_ID1, personRelationInfo.getLuiId());
        assertEquals(Constants.PERSON_ID1, personRelationInfo.getPersonId());
        assertEquals(2, personRelationInfo.getAttributes().size());
    }

    @Test
    public void testCreateBulkRelationshipsForPerson() {
        try {
            List<String> createResults = lprService.createBulkRelationshipsForPerson(principalId,
                    new ArrayList<String>(),
                    "", "",
                    LuiPersonRelationInfo.newInstance(),
                    callContext);
            assertNotNull(createResults);
            assertEquals(1, createResults.size());
        } catch (Throwable ex) {
            fail("exception from service call :" + ex.getMessage());
        }


    }

    @Test
    public void testCreateBulkRelationshipsForPersonExceptions() {
        try {
            List<String> createResults = lprService.createBulkRelationshipsForPerson("", new ArrayList<String>(), "", "", LuiPersonRelationInfo.newInstance(), callContext);

        } catch (Throwable ex) {
            // ex.printStackTrace();
            assertTrue(ex instanceof OperationFailedException);
        }
    }
    /*
     @Test
     public void testfindLuiPersonRelationStates() throws Throwable {

             List<LuiPersonRelationStateInfo> stateInfo = lprService.findLuiPersonRelationStates(callContext);
             assertTrue(stateInfo.size()==1);
             assertTrue(  ((LuiPersonRelationStateInfo)stateInfo.get(0)).getDescr().equals("ABC") );


     }
     */


}