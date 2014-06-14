package org.kuali.student.enrollment.batch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.batch.dto.BatchParameter;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiSetDao;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.examoffering.service.impl.ExamOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-enroll-batch-test-context.xml"})
public class TestBatchSchedulerImpl {

    @Resource(name = "batchScheduler")
    private BatchScheduler batchScheduler;

    @Resource
    private CourseOfferingServiceTestDataLoader coDataLoader;

    @Resource
    private LRCService lrcService;

    @Resource
    private ExamOfferingServiceTestDataLoader eoDataLoader;

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp() throws Exception {
        new MockLrcTestDataLoader(this.lrcService).loadData();
        coDataLoader.createStateTestData();
        coDataLoader.beforeTest(false);

        eoDataLoader.beforeTest();

        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @Test
    public void testBatchScheduler() throws Exception {
        batchScheduler.launch("kuali.batch.job.examOffering.slotting", null, new Date(), new ContextInfo());
    }
}
