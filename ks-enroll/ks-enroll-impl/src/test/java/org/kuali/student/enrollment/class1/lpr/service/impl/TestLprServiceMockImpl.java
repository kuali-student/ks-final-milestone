package org.kuali.student.enrollment.class1.lpr.service.impl;

import org.kuali.student.r2.common.dto.BulkStatusInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionItemEntity;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import static org.junit.Assert.*;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.enrollment.test.util.RelationshipTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import java.util.Date;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.enrollment.test.util.RelationshipTester;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lpr-mock-service-test-context.xml"})
public class TestLprServiceMockImpl {

    public LprService getLprService() {
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }
    @Resource
    private LprService lprService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
//        try {
//            new LprTestDataLoader(lprDao).loadData();
//        } catch (Exception ex) {
//            throw new RuntimeException (ex);
//        }
    }

    @Test
    public void testCrudLpr() throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // test create
        LprInfo expected = new LprInfo();
        expected.setPersonId("person1");
        expected.setLuiId("lui1");
        expected.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        expected.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setCommitmentPercent("100.00");
        expected.getResultValuesGroupKeys().add("rvg1");
        expected.getResultValuesGroupKeys().add("rvg2");
        new AttributeTester().add2ForCreate(expected.getAttributes());
        LprInfo actual = lprService.createLpr(expected.getPersonId(), expected.getLuiId(), expected.getTypeKey(), expected,
                callContext);
        assertNotNull(actual.getId());
        new RelationshipTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        new ListOfStringTester().check(expected.getResultValuesGroupKeys(), actual.getResultValuesGroupKeys());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getLuiId(), actual.getLuiId());
        assertEquals(expected.getCommitmentPercent(), actual.getCommitmentPercent());

        // test read
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
			
        	// clear out any id's set during the persistence
        	// to let the checks work properly
        	itemInfo.setId(null);
		}
        actual = lprService.getLpr(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
        new ListOfStringTester().check(expected.getResultValuesGroupKeys(), actual.getResultValuesGroupKeys());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getLuiId(), actual.getLuiId());
        assertEquals(expected.getCommitmentPercent(), actual.getCommitmentPercent());

        // test update
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
			
        	// clear out any id's set during the persistence
        	// to let the checks work properly
        	itemInfo.setId(null);
		}
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setCommitmentPercent("33.33");
        expected.getResultValuesGroupKeys().remove(0);
        expected.getResultValuesGroupKeys().add("rvg3");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = lprService.updateLpr(expected.getId(), expected, callContext);
        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
        new ListOfStringTester().check(expected.getResultValuesGroupKeys(), actual.getResultValuesGroupKeys());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getLuiId(), actual.getLuiId());
        assertEquals(expected.getCommitmentPercent(), actual.getCommitmentPercent());

        // test read
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
			
        	// clear out any id's set during the persistence
        	// to let the checks work properly
        	itemInfo.setId(null);
		}
        actual = lprService.getLpr(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        new ListOfStringTester().check(expected.getResultValuesGroupKeys(), actual.getResultValuesGroupKeys());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getLuiId(), actual.getLuiId());
        assertEquals(expected.getCommitmentPercent(), actual.getCommitmentPercent());

        // test delete
        StatusInfo status = lprService.deleteLpr(expected.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lprService.getLpr(expected.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted LprEntity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

    @Test
    public void testCrudLprTransaction() throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // test create
        LprTransactionInfo expected = new LprTransactionInfo();
        
        expected.setRequestingPersonId("person1");
        expected.setAtpId("atp1");
        expected.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        expected.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        expected.setLprTransactionItems(new java.util.LinkedList<LprTransactionItemInfo>());
        new AttributeTester().add2ForCreate(expected.getAttributes());
        new LprTransactionItemTester().add2ForCreate(expected.getLprTransactionItems());
        LprTransactionInfo actual = lprService.createLprTransaction(expected.getTypeKey(), expected, callContext);
        new LprTransactionItemTester().add2ForCreate(expected.getLprTransactionItems());
        actual = lprService.createLprTransaction(expected.getTypeKey(), expected, callContext);
        assertNotNull(actual.getId());
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals(expected.getAtpId(), actual.getAtpId());
        new LprTransactionItemTester().check(expected.getLprTransactionItems(), actual.getLprTransactionItems());

        

        // test read
        expected = actual;
        
        for (LprTransactionItemInfo itemInfo : expected.getLprTransactionItems()) {
			
        	// clear out any id's set during the persistence
        	// to let the checks work properly
        	itemInfo.setId(null);
		}
        
        actual = lprService.getLprTransaction(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals(expected.getAtpId(), actual.getAtpId());
        new LprTransactionItemTester().check(expected.getLprTransactionItems(), actual.getLprTransactionItems()); 

        // test update
        expected = actual;
        
        for (LprTransactionItemInfo itemInfo : expected.getLprTransactionItems()) {
			
        	// clear out any id's set during the persistence
        	// to let the checks work properly
        	itemInfo.setId(null);
		}
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = lprService.updateLprTransaction(expected.getId(), expected, callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals(expected.getAtpId(), actual.getAtpId());
        new LprTransactionItemTester().check(expected.getLprTransactionItems(), actual.getLprTransactionItems());

        // test read
        expected = actual;
        
        for (LprTransactionItemInfo itemInfo : expected.getLprTransactionItems()) {
			
        	// clear out any id's set during the persistence
        	// to let the checks work properly
        	itemInfo.setId(null);
		}
        actual = lprService.getLprTransaction(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals(expected.getAtpId(), actual.getAtpId());
        new LprTransactionItemTester().check(expected.getLprTransactionItems(), actual.getLprTransactionItems());

        // test delete
        StatusInfo status = lprService.deleteLprTransaction(expected.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lprService.getLprTransaction(expected.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted LprTransactionEntity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

    @Test
    public void testBulkCreateLprsForPerson() throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // test create
        List<LprInfo> expectedList = new ArrayList();
        LprInfo expected1 = new LprInfo();
        expected1.setPersonId("person1");
        expected1.setLuiId("lui1");
        expected1.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        expected1.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        expected1.setEffectiveDate(new Date());
        expected1.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected1.setCommitmentPercent("100.00");
        expected1.getResultValuesGroupKeys().add("rvg1");
        expected1.getResultValuesGroupKeys().add("rvg2");
        new AttributeTester().add2ForCreate(expected1.getAttributes());
        expectedList.add(expected1);

        LprInfo expected2 = new LprInfo();
        expected2.setPersonId("person2");
        expected2.setLuiId("lui1");
        expected2.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        expected2.setStateKey(LprServiceConstants.CONFIRMED_STATE_KEY);
        expected2.setEffectiveDate(new Date(new Date().getTime() + 1000));
        expected2.setExpirationDate(new Date(new Date().getTime() + 2000));
        expected2.setCommitmentPercent("75.00");
        expected2.getResultValuesGroupKeys().add("rvg3");
        expected2.getResultValuesGroupKeys().add("rvg4");
        new AttributeTester().add2ForCreate(expected2.getAttributes());
        expectedList.add(expected2);

        List<BulkStatusInfo> actualBulkList = lprService.createLprsForLui(expected1.getLuiId(), expected1.getTypeKey(),
                expectedList, callContext);
        assertNotNull(actualBulkList);
        assertEquals(2, actualBulkList.size());
        List<String> ids = new ArrayList<String>();
        for (BulkStatusInfo bsi : actualBulkList) {
            assertEquals(Boolean.TRUE, bsi.getIsSuccess());
            assertNotNull(bsi.getId());
            ids.add(bsi.getId());
        }
        List<LprInfo> lprs = this.lprService.getLprsByIds(ids, callContext);
        boolean found1 = false;
        boolean found2 = false;
        for (LprInfo actual : lprs) {
            LprInfo expected = null;
            if (actual.getPersonId().equals(expected1.getPersonId())) {
                expected = expected1;
                found1 = true;
            } else {
                expected = expected2;
                found2 = true;
            }
            new RelationshipTester().check(expected, actual);
            new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
            new MetaTester().checkAfterCreate(actual.getMeta());
            new ListOfStringTester().check(expected.getResultValuesGroupKeys(), actual.getResultValuesGroupKeys()); 
            assertEquals(expected.getPersonId(), actual.getPersonId());
            assertEquals(expected.getLuiId(), actual.getLuiId());
            assertEquals(expected.getCommitmentPercent(), actual.getCommitmentPercent());
        }
        if (!found1) {
            fail("first one never found");
        }
        if (!found2) {
            fail("second one never found");
        }

        // TODO: test that it handles validation failures
    }

    @Test
    public void testBulkCreateLprsForLui() throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // test create
        List<LprInfo> expectedList = new ArrayList<LprInfo>();
        LprInfo expected1 = new LprInfo();
        expected1.setPersonId("person1");
        expected1.setLuiId("lui1");
        expected1.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        expected1.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        expected1.setEffectiveDate(new Date());
        expected1.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected1.setCommitmentPercent("100.00");
        expected1.getResultValuesGroupKeys().add("rvg1");
        expected1.getResultValuesGroupKeys().add("rvg2");
        new AttributeTester().add2ForCreate(expected1.getAttributes());
        expectedList.add(expected1);

        LprInfo expected2 = new LprInfo();
        expected2.setPersonId("person2");
        expected2.setLuiId("lui1");
        expected2.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        expected2.setStateKey(LprServiceConstants.CONFIRMED_STATE_KEY);
        expected2.setEffectiveDate(new Date(new Date().getTime() + 1000));
        expected2.setExpirationDate(new Date(new Date().getTime() + 2000));
        expected2.setCommitmentPercent("75.00");
        expected2.getResultValuesGroupKeys().add("rvg3");
        expected2.getResultValuesGroupKeys().add("rvg4");
        new AttributeTester().add2ForCreate(expected2.getAttributes());
        expectedList.add(expected2);

        List<BulkStatusInfo> actualBulkList = lprService.createLprsForLui(expected1.getLuiId(), expected1.getTypeKey(),
                expectedList, callContext);
        assertNotNull(actualBulkList);
        assertEquals(2, actualBulkList.size());
        List<String> ids = new ArrayList<String>();
        for (BulkStatusInfo bsi : actualBulkList) {
            assertEquals(Boolean.TRUE, bsi.getIsSuccess());
            assertNotNull(bsi.getId());
            ids.add(bsi.getId());
        }
        List<LprInfo> lprs = this.lprService.getLprsByIds(ids, callContext);
        boolean found1 = false;
        boolean found2 = false;
        for (LprInfo actual : lprs) {
            LprInfo expected = null;
            if (actual.getPersonId().equals(expected1.getPersonId())) {
                expected = expected1;
                found1 = true;
            } else {
                expected = expected2;
                found2 = true;
            }
            new RelationshipTester().check(expected, actual);
            new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
            new MetaTester().checkAfterCreate(actual.getMeta());
            new ListOfStringTester().check(expected.getResultValuesGroupKeys(), actual.getResultValuesGroupKeys());
            assertEquals(expected.getPersonId(), actual.getPersonId());
            assertEquals(expected.getLuiId(), actual.getLuiId());
            assertEquals(expected.getCommitmentPercent(), actual.getCommitmentPercent());
        }
        if (!found1) {
            fail("first one never found");
        }
        if (!found2) {
            fail("second one never found");
        }

        // TODO: test that it handles validation failures
    }
}
