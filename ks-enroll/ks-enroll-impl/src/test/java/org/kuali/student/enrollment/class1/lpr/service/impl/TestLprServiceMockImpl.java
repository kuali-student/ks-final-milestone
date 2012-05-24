package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.*;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.enrollment.test.util.RelationshipTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import java.util.Date;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void testCrudLpr() throws Exception {
        // test lpr create
        LprInfo orig = new LprInfo();
        orig.setPersonId("person1");
        orig.setLuiId("lui1");
        orig.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        orig.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        orig.setEffectiveDate(new Date());
        orig.setExpirationDate(new Date(new Date().getTime() + 1000));
        orig.setCommitmentPercent("100.00");
        orig.getResultValuesGroupKeys().add("rvg1");
        orig.getResultValuesGroupKeys().add("rvg2");
        new AttributeTester().add2ForCreate(orig.getAttributes());
        LprInfo info = lprService.createLpr(orig.getPersonId(), orig.getLuiId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info.getId());
        new RelationshipTester().check(orig, info);
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        new MetaTester().checkAfterCreate(info.getMeta());
        new ListOfStringTester().check(orig.getResultValuesGroupKeys(), info.getResultValuesGroupKeys());
        assertEquals(orig.getCommitmentPercent(), info.getCommitmentPercent());

        // test lpr read
        orig = info;
        info = lprService.getLpr(info.getId(), callContext);
        assertEquals(orig.getId(), info.getId());
        new RelationshipTester().check(orig, info);
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        new MetaTester().checkAfterGet(orig.getMeta(), info.getMeta());
        new ListOfStringTester().check(orig.getResultValuesGroupKeys(), info.getResultValuesGroupKeys());
        assertEquals(orig.getCommitmentPercent(), info.getCommitmentPercent());

        // test lpr update
        orig = info;
        orig.setEffectiveDate(new Date(orig.getEffectiveDate().getTime() - 2000));
        orig.setExpirationDate(new Date(orig.getExpirationDate().getTime() + 2000));
        orig.setCommitmentPercent("33.33");
        orig.getResultValuesGroupKeys().remove(0);
        orig.getResultValuesGroupKeys().add("rvg3");
        new AttributeTester().delete1Update1Add1ForUpdate(orig.getAttributes());
        info = lprService.updateLpr(orig.getId(), orig, callContext);
        assertEquals(orig.getId(), info.getId());
        new RelationshipTester().check(orig, info);
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        new MetaTester().checkAfterUpdate(orig.getMeta(), info.getMeta());
        new ListOfStringTester().check(orig.getResultValuesGroupKeys(), info.getResultValuesGroupKeys());
        assertEquals(orig.getCommitmentPercent(), info.getCommitmentPercent());

        // test lpr read
        orig = info;
        info = lprService.getLpr(info.getId(), callContext);
        assertEquals(orig.getId(), info.getId());
        new RelationshipTester().check(orig, info);
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        new MetaTester().checkAfterCreate(info.getMeta());
        new ListOfStringTester().check(orig.getResultValuesGroupKeys(), info.getResultValuesGroupKeys());
        assertEquals(orig.getCommitmentPercent(), info.getCommitmentPercent());

        // test lpr delete
        StatusInfo status = lprService.deleteLpr(orig.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            info = lprService.getLpr(orig.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted LprEntity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }
    
    
    
}
