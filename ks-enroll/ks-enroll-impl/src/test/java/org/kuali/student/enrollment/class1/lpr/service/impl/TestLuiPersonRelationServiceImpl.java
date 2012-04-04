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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiTestDataLoader;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
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

    @Resource(name = "lprServiceValidationDecorator")
    private LuiPersonRelationService lprService;
    @Resource(name = "luiServiceImpl")
    private LuiService luiService;
    //
    private static String principalId = "123";
    private static String LPRID1 = "testLprId1";
    private static String LUIID1 = "testLuiId1";
    private static String PERSONID1 = "testPersonId1";
    private static String LUIID2 = "testLuiId2";
    private static String PERSONID2 = "testPersonId2";
    // LPR Roster Constants
    private final static String ATP_DURATION_KEY = "semester1";
    private final static String LPR_TRANSACTION_NAME = "NEW TRANSACTION TEST";
    private final static Integer TIME_QTY = new Integer(1);
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
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new LuiTestDataLoader(this.luiService).loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void testLprServiceSetup() {
        assertNotNull(lprService);
    }

    @Test
    public void testGetLpr() throws Exception {
        LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull(lpr);
        assertEquals(LUIID1, lpr.getLuiId());
        assertEquals(PERSONID1, lpr.getPersonId());
    }

    @Test
    public void testCreateLpr() throws Exception {
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
        lprId = lprService.createLpr(PERSONID2, LUIID2, LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, lprInfo, callContext);
        assertNotNull(lprId);
        lpr2 = lprService.getLpr(lprId, callContext);
        assertNotNull(lpr2);
        assertEquals(LUIID2, lpr2.getLuiId());
        assertEquals(PERSONID2, lpr2.getPersonId());
    }

    @Test
    public void testGetLuiPersonRelationsForLui() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprInfoList = lprService.getLprsByLui(LUIID1, callContext);
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
    public void testCreateBulkRelationshipsForPerson() throws Exception {
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        lprInfo.setStateKey(LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY);

        List<String> luiIds = Arrays.asList("Lui-1", "Lui-2", "Lui-3");


        List<String> createResults = lprService.createBulkRelationshipsForPerson(
                PERSONID1, luiIds, lprInfo.getStateKey(), lprInfo.getTypeKey(), lprInfo, callContext);
        assertNotNull(createResults);
        assertEquals(3, createResults.size());

        LuiPersonRelationInfo lprInfo2;
        for (String lprId : createResults) {
            lprInfo2 = lprService.getLpr(lprId, callContext);
            assertNotNull(lprInfo2);
            assertEquals(PERSONID1, lprInfo2.getPersonId());
            assertEquals(lprInfo.getTypeKey(), lprInfo2.getTypeKey());
            assertEquals(lprInfo.getStateKey(), lprInfo2.getStateKey());
            assertTrue(luiIds.contains(lprInfo2.getLuiId()));
        }
    }

    @Test
    public void testDeleteLuiPersonRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, AlreadyExistsException, DisabledIdentifierException, ReadOnlyException {
        LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist; cannot delete", lpr);

        lprService.deleteLpr(LPRID1, callContext);

        LuiPersonRelationInfo deletedLpr = lprService.getLpr(LPRID1, callContext);
        assertTrue(deletedLpr.getStateKey().equals(LuiPersonRelationServiceConstants.DROPPED_STATE_KEY));
    }

    @Test
    public void testGetLuiPersonRelations() throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprList = lprService.getLprsByPersonAndLui(PERSONID1, LUIID1, callContext);
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
        LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
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
        updatedLpr = lprService.updateLpr(LPRID1, lpr, callContext);

        LuiPersonRelationInfo finalLpr = lprService.getLpr(LPRID1, callContext);
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

        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);
        LprRosterInfo info = lprService.getLprRoster(lprRosterId, callContext);

        assertEquals(info.getMaximumCapacity().intValue(), MAX_CPTY);
        assertTrue(info.getCheckInRequired());
        assertEquals(info.getTypeKey(), TYPE_KEY);
        assertEquals(info.getStateKey(), STATE_KEY);
        assertEquals(info.getDescr().getPlain(), LPR_ROSTER_DESC);
        assertEquals(info.getCheckInFrequency().getAtpDurationTypeKey(), ATP_DURATION_KEY);
        assertEquals(info.getCheckInFrequency().getTimeQuantity(), TIME_QTY);
        assertEquals(info.getName(), LPR_ROSTER_NAME);
//        assertEquals(info.getAssociatedLuiIds().size(), 1);
//        assertEquals(info.getAssociatedLuiIds().get(0), LUI_ID);

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
        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);

        StatusInfo status = lprService.deleteLprRoster(lprRosterId, callContext);
        assertTrue(status.getIsSuccess());

        // Just make sure it's really not there
        LprRosterInfo info = lprService.getLprRoster(lprRosterId, callContext);
        assertNull(info);

    }

    // ignore for now until have time to rework associated luis
    @Test
    @Ignore
    public void testGetLprRostersByLuiAndRosterType() throws Exception {
        List<LprRosterInfo> infoList = null;

        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);
        infoList = lprService.getLprRostersByLuiAndType(LUI_ID, TYPE_KEY, callContext);
        assertNotNull(infoList);
        assertEquals(1, infoList.size());
        assertEquals(infoList.get(0).getAssociatedLuiIds().size(), 1);
        assertEquals(infoList.get(0).getAssociatedLuiIds().get(0), LUI_ID);
    }

    // ignore for now until have time to rework associated luis
    @Test
    @Ignore
    public void testGetLprRostersByLui() throws Exception {
        List<LprRosterInfo> infoList = null;
        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);
        infoList = lprService.getLprRostersByLui(LUI_ID, callContext);
        assertNotNull(infoList);
        assertEquals(1, infoList.size());
        assertEquals(infoList.get(0).getAssociatedLuiIds().size(), 1);
        assertEquals(infoList.get(0).getAssociatedLuiIds().get(0), LUI_ID);
    }

    @Test
    public void testGetLprsByPersonAndTypeForAtp() throws Exception {

        LuiPersonRelationInfo original = new LuiPersonRelationInfo();
        original.setLuiId("Lui-1");
        original.setPersonId(PERSONID2);
        original.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        original.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
        original.setEffectiveDate(new Date());
        original.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;

        LuiInfo lui = luiService.getLui(original.getLuiId(), callContext);
        System.out.println("lui atp=" + lui.getAtpId());

        lprId = lprService.createLpr(original.getPersonId(),
                original.getLuiId(),
                original.getTypeKey(),
                original, callContext);
        LuiPersonRelationInfo info = lprService.getLpr(lprId, callContext);
        System.out.println (info.getId() + " person=" + info.getPersonId() + " lui=" + info.getLuiId() + " type=" + info.getTypeKey());
        List<LuiPersonRelationInfo> lprs = lprService.getLprsByPersonAndTypeForAtp(original.getPersonId(),
                lui.getAtpId(),
                original.getTypeKey(),
                callContext);
        assertNotNull(lprs);
        assertEquals(1, lprs.size());
        assertEquals(original.getLuiId(), lprs.get(0).getLuiId());
        assertEquals(original.getPersonId(), lprs.get(0).getPersonId());

    }

    @Test
    public void testCreateLprTransaction() throws Exception {
        LprTransactionInfo lprTransactionInfo = createLprTransaction();
        lprTransactionInfo = lprService.createLprTransaction(lprTransactionInfo.getTypeKey(),
                lprTransactionInfo, callContext);
        assertNotNull(lprService.getLprTransaction(lprTransactionInfo.getId(), callContext));
    }

    @Test
    public void testGetLprIdsByLuiAndPerson() throws Exception {
        List<String> lprIds = null;
        lprIds = lprService.getLprIdsByPersonAndLui("testPersonId1", "testLuiId1", callContext);
        assertNotNull(lprIds);
        assertEquals(1, lprIds.size());
    }

    @Test
    public void testUpdateLprTransaction() throws Exception {
        String updateName = "NEW TRANSACTION TEST 1";
        LprTransactionInfo lprTransactionInfo = createLprTransaction();
        lprTransactionInfo = lprService.createLprTransaction(lprTransactionInfo.getTypeKey(),
                lprTransactionInfo, callContext);
        lprTransactionInfo = lprService.getLprTransaction(lprTransactionInfo.getId(), callContext);
        lprTransactionInfo.setName(updateName);
        lprTransactionInfo.setStateKey(LuiPersonRelationServiceConstants.ACTIVE_STATE_KEY);
        lprTransactionInfo = lprService.updateLprTransaction(lprTransactionInfo.getId(), lprTransactionInfo,
                callContext);
        assertNotNull(lprTransactionInfo);
        assertTrue(lprTransactionInfo.getName().equals(updateName));
        lprTransactionInfo = lprService.getLprTransaction(lprTransactionInfo.getId(), callContext);
        assertNotNull(lprTransactionInfo);
    }

    @Test
    public void testCreateLprRosterEntry() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);

        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;
        LuiPersonRelationInfo lpr2 = null;

        lprId = lprService.createLpr(PERSONID2, LUIID2, LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, lprInfo, callContext);
        LprRosterEntryInfo info = new LprRosterEntryInfo();
        info.setLprId(lprId);
        info.setLprRosterId(lprRosterId);
        info.setPosition(1);

        Date effectiveDate = new Date();
        Date expiryDate = DateUtils.addYears(new Date(), 20);
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expiryDate);

        String lprEntryId = lprService.createLprRosterEntry(info, callContext);

        List<LprRosterEntryInfo> entryInfoList = lprService.getLprRosterEntriesForRoster(lprRosterId, callContext);

        assertEquals(1, entryInfoList.size());
        assertEquals(entryInfoList.get(0).getLprId(), lprId);
        assertEquals(entryInfoList.get(0).getLprRosterId(), lprRosterId);
        assertEquals(entryInfoList.get(0).getPosition(), new Integer(1));

    }

    @Test
    public void testDeleteLprRosterEntry() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        // Create LprRoster
        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);

        // Create LPR
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;

        lprId = lprService.createLpr(PERSONID2, LUIID2, LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, lprInfo, callContext);

        // Create LPR Entry
        LprRosterEntryInfo info = new LprRosterEntryInfo();
        info.setLprId(lprId);
        info.setLprRosterId(lprRosterId);
        info.setPosition(1);

        String lprEntryId = lprService.createLprRosterEntry(info, callContext);

        StatusInfo status = lprService.deleteLprRosterEntry(lprEntryId, callContext);

        assertEquals(status.getIsSuccess(), true);

        // Make sure it's really deleted
        List<LprRosterEntryInfo> entries = lprService.getLprRosterEntriesForRoster(lprRosterId, callContext);
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

        lprTransactionItem.setResultValuesGroupKeys(RESULT_OPTION_IDS);
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