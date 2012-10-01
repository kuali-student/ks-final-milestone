package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.atp.service.impl.AtpTestDataLoader;
import org.springframework.test.context.ContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-fewer-mocks-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImplWithFewerClass1Mocks extends TestCourseOfferingServiceImplWithClass1Mocks {

    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new CourseR1TestDataLoader(this.courseService).loadData();
            new LuiServiceDataLoader(this.luiService).loadData();
            new AcalTestDataLoader(this.acalService).loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
