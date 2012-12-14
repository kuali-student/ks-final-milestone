package org.kuali.student.myplan.course;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.myplan.course.controller.CourseSearchController;
import org.kuali.student.myplan.course.dataobject.CourseSearchItem;
import org.kuali.student.myplan.course.form.CourseSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test Class for Plan Item Inquiry Service
 * User: kmuthu
 * Date: 12/20/11
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:myplan-test-context.xml"})
public class PlanItemInquiryTest {

    // TODO: Add test cases for planItemInquiryViewHelperService
    @Test
    public void testPlanItemInquiry() {
        assertTrue(true);
    }
}
