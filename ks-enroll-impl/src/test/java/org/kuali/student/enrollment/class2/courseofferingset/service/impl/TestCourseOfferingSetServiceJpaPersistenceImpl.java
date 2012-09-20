/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tests the jpa persistence impl
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soc-jpa-persistence-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingSetServiceJpaPersistenceImpl extends TestCourseOfferingSetServiceMockImpl {

    @Test
    public void testSocSchedulingState() throws Exception {
        SocInfo orig = new SocInfo();
        orig.setName("SOC");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        orig.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        orig.setTermId("myTermId");
        orig.setSubjectArea("ENG");
        orig.setUnitsContentOwnerId("myUnitId");

        //  Verify that scheduling state changes are successfully "logged" in dynamic attributes and that the conversion between date string and date works.
        SimpleDateFormat formatter = new SimpleDateFormat(CourseOfferingSetServiceConstants.STATE_CHANGE_DATE_FORMAT);
        Date startDate = new Date();
        String startDateString = formatter.format(startDate);
        orig.getAttributes().add(new AttributeTester().toAttribute(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS, startDateString));
        SocInfo info = socService.createSoc(orig.getTermId(), orig.getTypeKey(), orig, callContext);

        assertEquals(startDateString, formatter.format(info.getLastSchedulingRunStarted()));
        assertNull(info.getLastSchedulingRunCompleted());

        //  Update SOC with scheduling state "completed".
        Date completedDate = new Date();
        String completedDateString = formatter.format(completedDate);
        info.getAttributes().add(new AttributeTester().toAttribute(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED, completedDateString));

        info.setLastSchedulingRunCompleted(completedDate);
        info = socService.updateSoc(info.getId(), info, callContext);

        assertEquals(startDateString, formatter.format(info.getLastSchedulingRunStarted()));
        assertEquals(startDateString, formatter.format(info.getLastSchedulingRunCompleted()));
    }
}
