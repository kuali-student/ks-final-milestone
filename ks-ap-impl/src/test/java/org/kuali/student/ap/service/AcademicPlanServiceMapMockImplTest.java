package org.kuali.student.ap.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceDecorator;
import org.kuali.student.ap.service.mock.AcademicPlanServiceMockImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Run  AcademicPlanServiceImplTests against Map based impl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-map-mock-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class AcademicPlanServiceMapMockImplTest extends AcademicPlanServiceImplTest {


    private final AcademicPlanServiceTstHelper testHelper = new AcademicPlanServiceTstHelper();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testServiceNoValidator=testService;
        while (testServiceNoValidator!=null &&
                !(testServiceNoValidator instanceof AcademicPlanServiceMockImpl)) {
            testServiceNoValidator = ((AcademicPlanServiceDecorator)testService).getNextDecorator();
        }

        //Load Test Plans/Items
        AcademicPlanDataLoader loader = new AcademicPlanDataLoader();
        loader.setContextInfo(contextInfo);
        loader.setPlanService(testServiceNoValidator);
        loader.load();

        contextInfo.setPrincipalId(principalId); //reset context principalId...after loading plans
    }
}
