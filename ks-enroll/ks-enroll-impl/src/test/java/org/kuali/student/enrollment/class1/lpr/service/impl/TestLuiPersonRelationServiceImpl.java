/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * @Author sambit
 */
@Ignore
@Daos({@Dao(value = "org.kuali.student.enrollment.class1.lpr.dao.LprDao", testSqlFile = "classpath:ks-lpr.sql"),
        @Dao(value = "org.kuali.student.enrollment.class1.lpr.dao.LprStateDao"),
        @Dao(value = "org.kuali.student.enrollment.class1.lpr.dao.LprTypeDao")})
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestLuiPersonRelationServiceImpl extends AbstractServiceTest {

    @Client(value = "org.kuali.student.enrollment.class1.lpr.service.impl.LuiPersonRelationServiceImpl")
    private LuiPersonRelationService lprService;

    private static String principalId = "123";
    private static String LPRID1 = "testLprId1";
    private static String LUIID1 = "testLuiId1";
    private static String PERSONID1 = "testPersonId1";
    private static String LUIID2 = "testLuiId2";
    private static String PERSONID2 = "testPersonId2";

    private ContextInfo callContext = ContextInfo.newInstance();

    @Before
    public void setUp() {
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testGetLuiPersonRelation() {
        try {
            LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
            assertNotNull(lpr);
            assertEquals(LUIID1, lpr.getLuiId());
            assertEquals(PERSONID1, lpr.getPersonId());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    // TODO implement @Test
    public void testCreateLuiPersonRelation() {
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey("kuali.lpr.type.registrant");
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;
        LuiPersonRelationInfo lpr2 = null;
        try {
            lprId = lprService.createLpr(PERSONID2, LUIID2, "kuali.lpr.type.registrant", lprInfo, callContext);
            assertNotNull(lprId);
            lpr2 = lprService.getLpr(lprId, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(lpr2);
        assertEquals(LUIID2, lpr2.getLuiId());
        assertEquals(PERSONID2, lpr2.getPersonId());
    }

    @Test
    public void testGetLuiPersonRelationsForLui() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprInfoList = lprService.getLprsByLui(LUIID1, ContextInfo.newInstance());
        assertNotNull(lprInfoList);
        assertEquals(1, lprInfoList.size());

        LuiPersonRelationInfo personRelationInfo = lprInfoList.get(0);
        assertNotNull(personRelationInfo);
        assertEquals(LUIID1, personRelationInfo.getLuiId());
        assertEquals(PERSONID1, personRelationInfo.getPersonId());
        // TODO - add attributes to ks-lpr.sql:
        // assertEquals(2, personRelationInfo.getAttributes().size());
    }

    // TODO implement @Test
    public void testCreateBulkRelationshipsForPerson() {
        try {
            List<String> createResults = lprService.createBulkRelationshipsForPerson(principalId,
                    new ArrayList<String>(), "", "", new LuiPersonRelationInfo(), callContext);
            assertNotNull(createResults);
            assertEquals(1, createResults.size());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    // TODO implement @Test
    public void testCreateBulkRelationshipsForPersonExceptions() {
        try {
            lprService.createBulkRelationshipsForPerson("", new ArrayList<String>(), "", "",
                    new LuiPersonRelationInfo(), callContext);
        } catch (Exception ex) {
            // ex.printStackTrace();
            assertTrue(ex instanceof OperationFailedException);
        }
    }

    @Test
    public void testDeleteLuiPersonRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist; cannot delete", lpr);

        try {
            lprService.deleteLpr(LPRID1, callContext);
        } catch (Exception ex) {
            fail("Exception from service call: " + ex.getMessage());
        }

        lpr = lprService.getLpr(LPRID1, callContext);
        assertNull("LPR entity '" + LPRID1 + "' was not deleted", lpr);
    }

    // TODO implement @Test
    public void testGetLuiPersonRelations() throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprList = lprService.getLprsByLuiAndPerson(PERSONID1, LUIID1, callContext);
        assertNotNull("Method LuiPersonRelationServiceImpl.getLuiPersonRelations() is not implemented yet", lprList);
        assertEquals(1, lprList.size());
        // add asserts
        fail("Test method not implemented yet");
    }

    // TODO implement @Test
    public void testGetLuiPersonRelationsForPersonAndAtp() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        fail("Test method not implemented yet");
    }

    // TODO implement @Test
    public void testGetLuiPersonRelationByState() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        fail("Test method not implemented yet");
    }

    // TODO implement @Test
    public void testUpdateLuiPersonRelation() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist; cannot update", lpr);
        Float commitmentPercent = lpr.getCommitmentPercent();
        Date expirationDate = lpr.getExpirationDate();

        // lpr.setCommitmentPercent(commitmentPercent + .05F);
        lpr.setExpirationDate(new Date());
        try {
            lprService.updateLpr(LPRID1, lpr, callContext);
        } catch (Exception ex) {
            fail("Exception from service call: " + ex.getMessage());
        }

        lpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist after being updated", lpr);
        assertFalse("'commitmentPercent' property was not updated", commitmentPercent == lpr.getCommitmentPercent());
        assertFalse("'expirationDate' property was not updated", expirationDate == lpr.getExpirationDate());
        fail("Test method not implemented yet");
    }

    // TODO implement @Test
    public void testValidateLuiPersonRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        fail("Test method not implemented yet");
    }

}