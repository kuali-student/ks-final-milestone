package org.kuali.student.enrollment.class1.lpr.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lpr.dao.LprDao;
import org.kuali.student.enrollment.class1.lpr.service.impl.mock.LprTestDataLoader;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lpr-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLuiPersonRelationServiceImpl {
    public LprService getLprService() {
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    @Resource
    private LprService lprService;

    @Resource
    private LprDao lprDao;


    public static String principalId = "123";
    public ContextInfo callContext = null;

    public LprDao getLprDao() {
        return lprDao;
    }

    public void setLprDao(LprDao lprDao) {
        this.lprDao = lprDao;
    }

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new LprTestDataLoader(lprDao).loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @Test
    public void testLuiServiceSetup() {
        assertNotNull(lprService);
    }

    @Test
      public void testCreateLPR()throws  Exception{

        LuiPersonRelationInfo lpr = new LuiPersonRelationInfo();
        lpr.setId("lpr-4");
        lpr.setLuiId("lui-4");
        lpr.setPersonId("person-4");
        lpr.setCommitmentPercent(20.0F);
        lpr.setStateKey("kuali.courseoffering.");
        String lprId = lprService.createLpr( "lui-4","person-4","kuali.lpr.type.courseoffering.instructor.main",lpr,  callContext);
        assertNotNull(lprId);
        assertEquals(lpr.getId(), lprId);

  }

    @Test
    public void testGetLPR()throws  Exception{

        LuiPersonRelationInfo lpr = lprService.getLpr("Lpr-1", callContext);
        assertNotNull(lpr);
        assertNotNull(lpr.getStateKey());
        assertNotNull(lpr.getTypeKey());

    }

}
