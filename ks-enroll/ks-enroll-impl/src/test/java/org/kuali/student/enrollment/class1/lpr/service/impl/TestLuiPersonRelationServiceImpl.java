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
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;


/**
 * @Author sambit
 */

@Daos( { @Dao(value = "org.kuali.student.enrollment.class1.lpr.dao.LprDao", testSqlFile = "classpath:ks-lpr.sql"),
         @Dao(value = "org.kuali.student.enrollment.class1.lpr.dao.LprStateDao"),
         @Dao(value = "org.kuali.student.enrollment.class1.lpr.dao.LprTypeDao") } )
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
@Ignore // LPRService is in flux
public class TestLuiPersonRelationServiceImpl extends AbstractServiceTest {

    @Client(value = "org.kuali.student.enrollment.class1.lpr.service.impl.LuiPersonRelationServiceImpl")
    public LuiPersonRelationService lprService;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
    private static String LUIID1 = "testLuiId1";
    private static String PERSONID1 = "testPersonId1";
    private static String LUIID2 = "testLuiId2";
    private static String PERSONID2 = "testPersonId2";
 
    @Before
    public void setUp() {
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testFetchLuiPersonRelation() {
        try {
            LuiPersonRelationInfo lpr = lprService.fetchLuiPersonRelation("testLprId1", callContext);
            assertNotNull(lpr);
            assertEquals("testLuiId1", lpr.getLuiId());
            assertEquals("testPersonId1", lpr.getPersonId());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
        
    }

    @Test
    public void testCreateLuiPersonRelation() {
        LuiPersonRelationInfo lprInfo =new  LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey("kuali.lpr.type.registrant");
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;
        LuiPersonRelationInfo lpr2 = null;
        try {
            lprId = lprService.createLuiPersonRelation(PERSONID2, LUIID2, "kuali.lpr.type.registrant", lprInfo, callContext);
            assertNotNull(lprId);
            lpr2 = lprService.fetchLuiPersonRelation(lprId, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(lpr2);
        assertEquals(LUIID2, lpr2.getLuiId());
        assertEquals(PERSONID2, lpr2.getPersonId());
    }

    @Test
    public void testFindLuiPersonRelationsForLui() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> personRelationInfos = lprService.findLuiPersonRelationsForLui(LUIID1, ContextInfo.newInstance());
        assertNotNull(personRelationInfos);
        assertEquals(personRelationInfos.size(), 1);

        LuiPersonRelationInfo personRelationInfo = personRelationInfos.get(0);
        assertNotNull(personRelationInfo);
        assertEquals(LUIID1, personRelationInfo.getLuiId());
        assertEquals(PERSONID1, personRelationInfo.getPersonId());
        // assertEquals(2, personRelationInfo.getAttributes().size());
    }

    @Test
    public void testCreateBulkRelationshipsForPerson() {
        try {
            List<String> createResults = lprService.createBulkRelationshipsForPerson(principalId,
                    new ArrayList<String>(),
                    "", "",
                    new LuiPersonRelationInfo(),
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
            List<String> createResults = lprService.createBulkRelationshipsForPerson("", new ArrayList<String>(), "", "",new  LuiPersonRelationInfo(), callContext);

        } catch (Throwable ex) {
            // ex.printStackTrace();
            assertTrue(ex instanceof OperationFailedException);
        }
    }
}