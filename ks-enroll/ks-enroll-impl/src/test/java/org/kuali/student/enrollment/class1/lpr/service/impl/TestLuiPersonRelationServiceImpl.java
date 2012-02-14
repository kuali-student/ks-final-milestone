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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author sambit
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lpr-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLuiPersonRelationServiceImpl {

    @Resource  // look up bean via variable name, then type
    private LuiPersonRelationService lprServiceValidationDecorator;

    @Resource  // look up bean via variable name, then type; here to test package-private methods
    private LuiPersonRelationServiceImpl lprServiceImpl;

    private static String principalId = "123";
    private static String LPRID1 = "testLprId1";
    private static String LUIID1 = "testLuiId1";
    private static String PERSONID1 = "testPersonId1";
    private static String LUIID2 = "testLuiId2";
    private static String PERSONID2 = "testPersonId2";

    // LPR Roster Constants
    private final static String ATP_DURATION_KEY = "semester1";
    private final static String LPR_TRANSACTION_NAME = "NEW TRANSACTION TEST";
    private final static int TIME_QTY = 1;
    private final static String LPR_ROSTER_NAME = "LPR_ROSTER_TEST";
    private final static String LPR_ROSTER_DESC = "LPR ROSTER DESC";
    private final static String ATTRIBUTE_KEY = "Key";
    private final static String ATTRIBUTE_VALUE = "Value";
    private final static String STATE_KEY = "kuali.lpr.state.registered";
    private final static List<String> RESULT_OPTION_IDS = new ArrayList<String>();
    private final static String TYPE_KEY = LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY;
    private final static String LUI_ID = "Lui-1";
    private final static int MAX_CPTY = 10;

    public ContextInfo callContext = null;

    
    @Before
    public void setUp() {
        callContext = new ContextInfo ();
        callContext.setPrincipalId(principalId);
    }

    
    @Test
    public void testLprServiceSetup() {
        assertNotNull(lprServiceValidationDecorator);
    }

//    @Test
//    public void testGetInitialValidStates() throws InvalidParameterException, MissingParameterException,
//            DoesNotExistException, OperationFailedException {
//
//        List<StateInfo> validStates = lprServiceValidationDecorator.getInitialValidStates(
//                LuiPersonRelationServiceConstants.STUDENT_COURSE_REGISTRATION_PROCESS_KEY, callContext);
//
//        assertNotNull(validStates);
//        assertEquals(1, validStates.size());
//
//        StateInfo state = validStates.get(0);
//        assertEquals(LuiPersonRelationServiceConstants.PLANNED_STATE_KEY, state.getKey());
//
//        // assert that an invalid process throws the expected exception
//        List<StateInfo> fakeValidStates = null;
//        try {
//            fakeValidStates = lprServiceValidationDecorator.getInitialValidStates("bogusProcess", callContext);
//            fail("Did not get an expected DoesNotExistException");
//        } catch (DoesNotExistException e) {
//            assertNull(fakeValidStates);
//        }
//    }
    
    @Test
    public void testGetLpr() {
        try {
            LuiPersonRelationInfo lpr = lprServiceValidationDecorator.getLpr(LPRID1, callContext);
            assertNotNull(lpr);
            assertEquals(LUIID1, lpr.getLuiId());
            assertEquals(PERSONID1, lpr.getPersonId());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testCreateLpr() {
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        lprInfo.setResultValuesGroupKeys(new ArrayList<String>());
        String lprId = null;
        LuiPersonRelationInfo lpr2 = null;
        try {
            lprId = lprServiceValidationDecorator.createLpr(PERSONID2, LUIID2, LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, lprInfo, callContext);
            assertNotNull(lprId);
            lpr2 = lprServiceValidationDecorator.getLpr(lprId, callContext);
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
        List<LuiPersonRelationInfo> lprInfoList = lprServiceValidationDecorator.getLprsByLui(LUIID1, callContext);
        assertNotNull(lprInfoList);
        assertEquals(1, lprInfoList.size());

        LuiPersonRelationInfo personRelationInfo = lprInfoList.get(0);
        assertNotNull(personRelationInfo);
        assertEquals(LUIID1, personRelationInfo.getLuiId());
        assertEquals(PERSONID1, personRelationInfo.getPersonId());
        // TODO - add attributes to ks-lpr.sql:
        // assertEquals(2, personRelationInfo.getAttributes().size());
    }

    @Test
    public void testCreateBulkRelationshipsForPerson() {
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        lprInfo.setStateKey(LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY);

        List<String> luiIdList = Arrays.asList("Lui-1", "Lui-2", "Lui-3");

        try {
            List<String> createResults = lprServiceValidationDecorator.createBulkRelationshipsForPerson(
                    PERSONID1, luiIdList, lprInfo.getStateKey(), lprInfo.getTypeKey(), lprInfo, callContext);
            assertNotNull(createResults);
            assertEquals(3, createResults.size());

            LuiPersonRelationInfo lprInfo2;
            for (String lprId : createResults) {
                lprInfo2 = lprServiceValidationDecorator.getLpr(lprId, callContext);
                assertNotNull(lprInfo2);
                assertEquals(PERSONID1, lprInfo2.getPersonId());
                assertEquals(lprInfo.getTypeKey(), lprInfo2.getTypeKey());
                assertEquals(lprInfo.getStateKey(), lprInfo2.getStateKey());
                assertTrue(luiIdList.contains(lprInfo2.getLuiId()));
            }
        }
        catch(Exception x) {
            fail(x.getMessage());
        }
    }

    @Test
    public void testDeleteLuiPersonRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, AlreadyExistsException, DisabledIdentifierException, ReadOnlyException {
        LuiPersonRelationInfo lpr = lprServiceValidationDecorator.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist; cannot delete", lpr);

        try {
            lprServiceValidationDecorator.deleteLpr(LPRID1, callContext);
        } catch (Exception ex) {
            fail("Exception from service call: " + ex.getMessage());
        }

        LuiPersonRelationInfo deletedLpr = lprServiceValidationDecorator.getLpr(LPRID1, callContext);
        assertTrue(deletedLpr.getStateKey().equals(LuiPersonRelationServiceConstants.DROPPED_STATE_KEY));
    }

    @Test
    public void testGetLuiPersonRelations() throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprList = lprServiceValidationDecorator.getLprsByLuiAndPerson(PERSONID1, LUIID1, callContext);
        assertNotNull(lprList);
        assertEquals(1, lprList.size());
        // TODO add asserts
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

    @Test    
    public void testUpdateLuiPersonRelation() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        LuiPersonRelationInfo lpr = lprServiceValidationDecorator.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist; cannot update", lpr);

        final Float initialCommitPercent = lpr.getCommitmentPercent();
        final Float updatedCommitPercent;
        if (initialCommitPercent == null) {
            updatedCommitPercent = .5F;
        } else {
            updatedCommitPercent = initialCommitPercent + .05F;
        }
        lpr.setCommitmentPercent(updatedCommitPercent);

        Date expirationDate = lpr.getExpirationDate();
        lpr.setExpirationDate(new Date());

        LuiPersonRelationInfo updatedLpr = null;
        try {
            updatedLpr = lprServiceValidationDecorator.updateLpr(LPRID1, lpr, callContext);
        } catch (Exception ex) {
            fail("Exception from service call: " + ex.getMessage());
        }

        LuiPersonRelationInfo finalLpr = lprServiceValidationDecorator.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist after being updated", finalLpr);
        assertNotNull("'commitmentPercent' property does not exist after being updated",
                finalLpr.getCommitmentPercent());
        assertEquals("'commitmentPercent' property was not updated properly.", updatedCommitPercent,
                finalLpr.getCommitmentPercent());
        assertNotSame("'expirationDate' property was not updated", expirationDate, finalLpr.getExpirationDate());
    }

    // TODO implement @Test
    public void testValidateLuiPersonRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        fail("Test method not implemented yet");
    }

    @Test
    public void testCreateLprRoster() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        LprRosterInfo lprRosterInfo = createLprRosterInfo();

        String lprRosterId = lprServiceValidationDecorator.createLprRoster(lprRosterInfo, callContext);
        LprRosterInfo info = lprServiceValidationDecorator.getLprRoster(lprRosterId, callContext);

        assertEquals(info.getMaximumCapacity().intValue(), MAX_CPTY);
        assertTrue(info.getCheckInRequired());
        assertEquals(info.getTypeKey(), TYPE_KEY);
        assertEquals(info.getStateKey(), STATE_KEY);
        assertEquals(info.getDescr().getPlain(), LPR_ROSTER_DESC);
        assertEquals(info.getCheckInFrequency().getAtpDurationTypeKey(), ATP_DURATION_KEY);
        assertEquals(info.getCheckInFrequency().getTimeQuantity().intValue(), TIME_QTY);
        assertEquals(info.getName(), LPR_ROSTER_NAME);
        assertEquals(info.getAssociatedLuiIds().size(), 1);
        assertEquals(info.getAssociatedLuiIds().get(0), LUI_ID);

        /**
         * FIXME: Attributes not working now.
         */
        // assertEquals(info.getAttributes().size(),1);
        // assertEquals(info.getAttributes().get(0).getKey(),"Key");
        // assertEquals(info.getAttributes().get(0).getKey(),"Value");
    }

    @Test
    public void testDeleteLprRoster() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprServiceValidationDecorator.createLprRoster(lprRosterInfo, callContext);

        StatusInfo status = lprServiceValidationDecorator.deleteLprRoster(lprRosterId, callContext);
        assertTrue(status.getIsSuccess());

        // Just make sure it's really not there
        LprRosterInfo info = lprServiceValidationDecorator.getLprRoster(lprRosterId, callContext);
        assertNull(info);

    }

    @Test
    public void testGetLprRostersByLuiAndRosterType() {
        List<LprRosterInfo> infoList = null;
        try {
            LprRosterInfo lprRosterInfo = createLprRosterInfo();
            String lprRosterId = lprServiceValidationDecorator.createLprRoster(lprRosterInfo, callContext);
            infoList = lprServiceValidationDecorator.getLprRostersByLuiAndRosterType(LUI_ID, TYPE_KEY, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(infoList);
        assertEquals(1, infoList.size());
        assertEquals(infoList.get(0).getAssociatedLuiIds().size(), 1);
        assertEquals(infoList.get(0).getAssociatedLuiIds().get(0), LUI_ID);
    }

    @Test
    public void testGetLprRostersByLui() {
        List<LprRosterInfo> infoList = null;
        try {
            LprRosterInfo lprRosterInfo = createLprRosterInfo();
            String lprRosterId = lprServiceValidationDecorator.createLprRoster(lprRosterInfo, callContext);
            infoList = lprServiceValidationDecorator.getLprRostersByLui(LUI_ID, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(infoList);
        assertEquals(1, infoList.size());
        assertEquals(infoList.get(0).getAssociatedLuiIds().size(), 1);
        assertEquals(infoList.get(0).getAssociatedLuiIds().get(0), LUI_ID);
    }

    @Test
    public void testGetLprsByPersonAndTypeForAtp()  {

        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId("Lui-1");
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        lprInfo.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;
        LuiPersonRelationInfo lpr2 = null;

        try {
            lprId = lprServiceValidationDecorator.createLpr(PERSONID2, "Lui-1",
                    LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, lprInfo, callContext);
            LuiPersonRelationInfo newInfo = lprServiceValidationDecorator.getLpr(lprId,callContext);
            List<LuiPersonRelationInfo> info = lprServiceValidationDecorator.getLprsByPersonAndTypeForAtp(PERSONID2, "testTermId4",
                    LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, callContext);
            assertNotNull(info);
            assertEquals(1, info.size());
            assertEquals("Lui-1", info.get(0).getLuiId());
            assertEquals(PERSONID2, info.get(0).getPersonId());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testGetLprsByLuiPersonAndState() {
        try {
            List<LuiPersonRelationInfo> lprInfos = lprServiceImpl.getLprsByLuiPersonAndState("testPersonId1", "testLuiId1", LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, callContext);
            assertNotNull(lprInfos);
            assertEquals(1, lprInfos.size());
            assertEquals("testLprId1", lprInfos.get(0).getId());

            lprInfos = lprServiceImpl.getLprsByLuiPersonAndState("bogusPersonId", "testLuiId1", LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, callContext);
            assertNotNull(lprInfos);
            assertEquals(0, lprInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateLprTransaction() {
        LprTransactionInfo lprTransactionInfo = createLprTransaction();
        try {
            lprTransactionInfo = lprServiceValidationDecorator.createLprTransaction(lprTransactionInfo, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            assertNotNull(lprServiceValidationDecorator.getLprTransaction(lprTransactionInfo.getId(), callContext));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetLprIdsByLuiAndPerson() {
        List<String> lprIds = null;
        try {
            lprIds = lprServiceValidationDecorator.getLprIdsByLuiAndPerson("testPersonId1", "testLuiId1", callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(lprIds);
        assertEquals(1, lprIds.size());
    }

    @Test
    public void testUpdateLprTransaction() {
        String updateName = "NEW TRANSACTION TEST 1";
        LprTransactionInfo lprTransactionInfo = createLprTransaction();
        try {
            lprTransactionInfo = lprServiceValidationDecorator.createLprTransaction(lprTransactionInfo, callContext);
            lprTransactionInfo = lprServiceValidationDecorator.getLprTransaction(lprTransactionInfo.getId(), callContext);
            lprTransactionInfo.setName(updateName);
            lprTransactionInfo.setStateKey(LuiPersonRelationServiceConstants.ACTIVE_STATE_KEY);
            lprTransactionInfo = lprServiceValidationDecorator.updateLprTransaction(lprTransactionInfo.getId(), lprTransactionInfo,
                    callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(lprTransactionInfo);
        assertTrue(lprTransactionInfo.getName().equals(updateName));
        try {
            lprTransactionInfo = lprServiceValidationDecorator.getLprTransaction(lprTransactionInfo.getId(), callContext);
            assertNotNull(lprTransactionInfo);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateLprRosterEntry() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprServiceValidationDecorator.createLprRoster(lprRosterInfo, callContext);

        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;
        LuiPersonRelationInfo lpr2 = null;

        try {
            lprId = lprServiceValidationDecorator.createLpr(PERSONID2, LUIID2, LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, lprInfo, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        LprRosterEntryInfo info = new LprRosterEntryInfo();
        info.setLprId(lprId);
        info.setLprRosterId(lprRosterId);
        info.setPosition("1");

        Date effectiveDate = new Date();
        Date expiryDate = DateUtils.addYears(new Date(), 20);
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expiryDate);

        String lprEntryId = lprServiceValidationDecorator.createLprRosterEntry(info, callContext);

        List<LprRosterEntryInfo> entryInfoList = lprServiceValidationDecorator.getEntriesForLprRoster(lprRosterId, callContext);

        assertEquals(1, entryInfoList.size());
        assertEquals(entryInfoList.get(0).getLprId(), lprId);
        assertEquals(entryInfoList.get(0).getLprRosterId(), lprRosterId);
        assertEquals(entryInfoList.get(0).getPosition(), "1");

    }

    @Test
    public void testDeleteLprRosterEntry() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        // Create LprRoster
        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprServiceValidationDecorator.createLprRoster(lprRosterInfo, callContext);

        // Create LPR
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;

        try {
            lprId = lprServiceValidationDecorator.createLpr(PERSONID2, LUIID2, LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, lprInfo, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Create LPR Entry
        LprRosterEntryInfo info = new LprRosterEntryInfo();
        info.setLprId(lprId);
        info.setLprRosterId(lprRosterId);
        info.setPosition("1");

        String lprEntryId = lprServiceValidationDecorator.createLprRosterEntry(info, callContext);

        StatusInfo status = lprServiceValidationDecorator.deleteLprRosterEntry(lprEntryId, callContext);

        assertEquals(status.getIsSuccess(), true);

        // Make sure it's really deleted
        List<LprRosterEntryInfo> entries = lprServiceValidationDecorator.getEntriesForLprRoster(lprRosterId, callContext);
        assertEquals(0, entries.size());

    }

    private LprTransactionInfo createLprTransaction() {
        LprTransactionInfo lprTransactionInfo = new LprTransactionInfo();
        lprTransactionInfo.setName(LPR_TRANSACTION_NAME);
        lprTransactionInfo.setRequestingPersonId(PERSONID1);
        lprTransactionInfo.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        lprTransactionInfo.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_NEW_STATE_KEY);
        LprTransactionItemInfo lprTransactionItem = new LprTransactionItemInfo();
        lprTransactionItem.setExistingLuiId(LUIID1);
        lprTransactionItem.setNewLuiId(LUIID2);
        lprTransactionItem.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        lprTransactionItem.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);

        lprTransactionItem.setResultOptionKeys(RESULT_OPTION_IDS);
        List<LprTransactionItemInfo> lprTransItemList = new ArrayList<LprTransactionItemInfo>();
        lprTransItemList.add(lprTransactionItem);
        lprTransactionInfo.setLprTransactionItems(lprTransItemList);

        return lprTransactionInfo;
    }

    private LprRosterInfo createLprRosterInfo() {

        LprRosterInfo lprRosterInfo = new LprRosterInfo();
        lprRosterInfo.setMaximumCapacity(MAX_CPTY);
        lprRosterInfo.setCheckInRequired(true);

        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setAtpDurationTypeKey(ATP_DURATION_KEY);
        timeAmountInfo.setTimeQuantity(TIME_QTY);
        lprRosterInfo.setCheckInFrequency(timeAmountInfo);

        RichTextInfo desc = new RichTextInfo();
        desc.setPlain(LPR_ROSTER_DESC);
        lprRosterInfo.setDescr(desc);

        lprRosterInfo.setName(LPR_ROSTER_NAME);
        List<String> luiIds = new ArrayList<String>();
        luiIds.add(LUI_ID);
        lprRosterInfo.setAssociatedLuiIds(luiIds);
        lprRosterInfo.setStateKey(STATE_KEY);
        lprRosterInfo.setTypeKey(TYPE_KEY);

        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        AttributeInfo attribute = new AttributeInfo();
        attribute.setKey(ATTRIBUTE_KEY);
        attribute.setValue(ATTRIBUTE_VALUE);
        attributes.add(attribute);
        lprRosterInfo.setAttributes(attributes);

        return lprRosterInfo;
    }

}