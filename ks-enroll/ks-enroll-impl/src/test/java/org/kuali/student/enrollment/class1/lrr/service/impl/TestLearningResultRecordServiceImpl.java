package org.kuali.student.enrollment.class1.lrr.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lrr-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLearningResultRecordServiceImpl {

    @Autowired
    private LearningResultRecordService lrrService;

    public ContextInfo context = ContextInfo.newInstance();

    public LearningResultRecordService getLrrService() {
        return lrrService;
    }

    public void setLrrService(LearningResultRecordService lrrService) {
        this.lrrService = lrrService;
    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testGetLearningResultRecord() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testGetLearningResultRecordsForLpr() throws Exception {

    }

    @Test
    public void testGetLearningResultRecordsForLprIdList() throws Exception {
        List<String> lprIds = new ArrayList<String>();
        lprIds.add("student1");
        lprIds.add("student2");

        Map<String, String> lrrIdsToLprIds = new HashMap<String, String>();
        lrrIdsToLprIds.put("student1-grade-final-lecture", "student1");
        lrrIdsToLprIds.put("student2-grade-final-lecture", "student2");
        lrrIdsToLprIds.put("student1-grade-interim-lecture", "student1");
        lrrIdsToLprIds.put("student2-grade-interim-lecture", "student2");

        List<LearningResultRecordInfo> lrrs = lrrService.getLearningResultRecordsForLprIdList(lprIds, context);
        assertNotNull("Null list returned.", lrrs);
        assertEquals("Unexpected number of results.", lrrIdsToLprIds.size(), lrrs.size());
        for (LearningResultRecordInfo lrr : lrrs) {
            assertEquals("Returned LRR doesn't match correct LPR.", lrrIdsToLprIds.get(lrr.getId()), lrr.getLprId());
        }
    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testGetLearningResultRecordsBySourceId() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testCreateLearningResultRecord() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testUpdateLearningResultRecord() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testDeleteLearningResultRecord() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testValidateLearningResultRecord() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testGetResultSource() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testGetResultSourcesByIdList() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testGetResultSourcesByType() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testCreateResultSource() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testUpdateResultSource() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testDeleteResultSource() throws Exception {

    }

    @Ignore("Method not implemented.") // TODO implement method
    @Test
    public void testValidateResultSource() throws Exception {

    }
}
