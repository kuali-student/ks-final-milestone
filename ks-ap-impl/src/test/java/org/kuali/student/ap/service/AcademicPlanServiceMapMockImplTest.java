package org.kuali.student.ap.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceDecorator;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceImpl;
import org.kuali.student.ap.academicplan.service.mock.AcademicPlanDataLoader;
import org.kuali.student.ap.academicplan.service.mock.AcademicPlanServiceMockImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
