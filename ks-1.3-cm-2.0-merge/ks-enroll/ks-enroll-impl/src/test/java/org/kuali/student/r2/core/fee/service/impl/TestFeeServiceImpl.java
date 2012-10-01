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
 * Created by Charles on 4/27/12
 */
package org.kuali.student.r2.core.fee.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.FeeServiceConstants;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeAmountInfo;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;
import org.kuali.student.r2.core.fee.infc.EnrollmentFee;
import org.kuali.student.r2.core.fee.service.FeeService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:fee-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestFeeServiceImpl {
    @Resource
    private FeeService feeService;
    private ContextInfo contextInfo;

    private String principalId = "123";

    private void before() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new Date());
    }
    
    private EnrollmentFeeInfo createFeeInfo() {
        EnrollmentFeeInfo feeInfo = new EnrollmentFeeInfo();
        EnrollmentFeeAmountInfo amt = new EnrollmentFeeAmountInfo();
        amt.setCurrencyQuantity(10);
        amt.setCurrencyTypeKey("dollars");
        feeInfo.setTypeKey(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY);
        feeInfo.setStateKey(FeeServiceConstants.FEE_ACTIVE_STATE_KEY);
        feeInfo.setAmount(amt);
        feeInfo.setOrgId("MATH");
        feeInfo.setRefObjectURI("http://kuali.org/sample");
        feeInfo.setRefObjectId("Kuali");
        return feeInfo;
    }

    private EnrollmentFeeInfo createFeeInfo(int n) {
        EnrollmentFeeInfo feeInfo = new EnrollmentFeeInfo();
        EnrollmentFeeAmountInfo amt = new EnrollmentFeeAmountInfo();
        amt.setCurrencyQuantity(10);
        amt.setCurrencyTypeKey("dollars");
        feeInfo.setTypeKey(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY);
        feeInfo.setStateKey(FeeServiceConstants.FEE_ACTIVE_STATE_KEY);
        feeInfo.setAmount(amt);
        feeInfo.setOrgId("MATH");
        feeInfo.setRefObjectURI("http://kuali.org/sample" + n);
        feeInfo.setRefObjectId("Kuali" + n);
        return feeInfo;
    }

    @Test
    public void testGetFeesByReference() {
        before();
        try {
            // Create three fees, two with the same ref URI/ID, and one different
            EnrollmentFeeInfo same = createFeeInfo(2);
            feeService.createFee(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY, same, contextInfo);
            same = createFeeInfo(2);
            feeService.createFee(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY, same, contextInfo);
            // Now a different one
            EnrollmentFeeInfo different = createFeeInfo(3);
            feeService.createFee(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY, different, contextInfo);
            String sameUri = "http://kuali.org/sample2";
            String sameRefId = "Kuali2";
            String diffUri = "http://kuali.org/sample3";
            String diffRefId = "Kuali3";
            List<EnrollmentFeeInfo> fetchSame = feeService.getFeesByReference(sameUri, sameRefId, contextInfo);
            assertEquals(2, fetchSame.size());
            List<EnrollmentFeeInfo> fetchDiff = feeService.getFeesByReference(diffUri, diffRefId, contextInfo);
            assertEquals(1, fetchDiff.size());
            List<EnrollmentFeeInfo> fetchZero = feeService.getFeesByReference(sameUri, diffRefId, contextInfo);
            assertEquals(0, fetchZero.size());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }
    @Test
    public void testCrud() {
        before();
        EnrollmentFeeInfo feeInfo = createFeeInfo();
        try {
            EnrollmentFeeInfo returned = feeService.createFee(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY, feeInfo, contextInfo);
            String id = returned.getId();
            EnrollmentFeeInfo fetched = feeService.getFee(id, contextInfo);
            // Change the amount
            fetched.getAmount().setCurrencyQuantity(20);
            // Update
            feeService.updateFee(id, fetched, contextInfo);
            // Fetch it back again
            EnrollmentFee fetchedAgain = feeService.getFee(id, contextInfo);
            assertEquals(feeInfo.getOrgId(), fetchedAgain.getOrgId());
            // Verify new value
            assertEquals(new Integer(20), fetchedAgain.getAmount().getCurrencyQuantity());
            // Now delete
            StatusInfo info = feeService.deleteFee(id, contextInfo);
            assertEquals(new Boolean(true), info.getIsSuccess());
            try {
                feeService.getFee(id, contextInfo);
            } catch (DoesNotExistException e) {
                System.err.println("DoesNotExistException");
                assert(true);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert(false); // Shouldn't get here
    }
    @Test
    public void testUpdate() {
        before();
        EnrollmentFeeInfo feeInfo = createFeeInfo();
        try {
            EnrollmentFeeInfo returned = feeService.createFee(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY, feeInfo, contextInfo);
            String id = returned.getId();
            EnrollmentFeeInfo fetched = feeService.getFee(id, contextInfo);
            // Change the amount
            fetched.getAmount().setCurrencyQuantity(20);
            // Update
            feeService.updateFee(id, fetched, contextInfo);
            // Fetch it back again
            EnrollmentFee fetchedAgain = feeService.getFee(id, contextInfo);
            assertEquals(feeInfo.getOrgId(), fetchedAgain.getOrgId());
            // Verify new value
            assertEquals(new Integer(20), fetchedAgain.getAmount().getCurrencyQuantity());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false); // Shouldn't get here
        }
    }

    @Test
    public void testCreateAndRead() {
        before();
        EnrollmentFeeInfo feeInfo = createFeeInfo();
        try {
            EnrollmentFeeInfo returned = feeService.createFee(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY, feeInfo, contextInfo);
            String id = returned.getId();
            EnrollmentFeeInfo fetched = feeService.getFee(id, contextInfo);
            assertEquals(feeInfo.getOrgId(), fetched.getOrgId());
            assertEquals(feeInfo.getAmount().getCurrencyQuantity(), fetched.getAmount().getCurrencyQuantity());
            assertEquals(feeInfo.getAmount().getCurrencyTypeKey(), fetched.getAmount().getCurrencyTypeKey());
        } catch (Exception e) {
            e.printStackTrace();  
            assert(false); // Shouldn't get here
        }
    }
}
