/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.impl.AttributeHelper;
import org.kuali.student.enrollment.class2.acal.service.impl.AttributeTester;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the calculation decorator using mock impls for persistence
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soc-calc-decorator-with-mocks-test-context.xml"})
public class TestCourseOfferingSetServiceCalculationDecoratorWithMocks {

    @Resource(name = "socService")
    private CourseOfferingSetService socService;
    @Resource(name = "courseService")
    private CourseService courseService;
    @Resource(name = "acalService")
    private AcademicCalendarService acalService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
//        try {
//            loadData();
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }

    @Test
    public void testCRUD() throws Exception {
     
        // create
        SocInfo orig = new SocInfo();
        orig.setName("test name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        orig.setStateKey(CourseOfferingSetServiceConstants.ACTIVE_SOC_STATE_KEY);
        orig.setTermId("myTermId");
        orig.setSubjectArea("ENG");
        orig.setUnitsContentOwnerId("myUnitId");
        orig.getAttributes().add(new AttributeHelper().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeHelper().toAttribute("key2", "value2"));
        SocInfo info = socService.createSoc(orig.getTermId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getSubjectArea(), info.getSubjectArea());
        assertEquals(orig.getUnitsContentOwnerId(), info.getUnitsContentOwnerId());
        new AttributeTester().compare(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.socService.getSoc(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getSubjectArea(), info.getSubjectArea());
        new AttributeTester().compare(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("test appt window name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated",
                "description formatted 1 updated"));
        orig.setStateKey(CourseOfferingSetServiceConstants.ACTIVE_SOC_STATE_KEY);
        new AttributeHelper().findAttributes(orig.getAttributes(), "key1").get(0).setValue(
                "value1Updated");
        info = this.socService.updateSoc(orig.getId(), orig,
                callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getSubjectArea(), info.getSubjectArea());
        new AttributeTester().compare(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.socService.getSoc(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getSubjectArea(), info.getSubjectArea());
        new AttributeTester().compare(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // delete
        orig = info;
        StatusInfo status = this.socService.deleteSoc(orig.getId(),
                callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            info = this.socService.getSoc(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    
    private void compareStringList(List<String> list1, List<String> list2) {
        assertEquals(list1.size(), list2.size());
        List lst1 = new ArrayList(list1);
        Collections.sort(lst1);
        List lst2 = new ArrayList(list2);
        Collections.sort(lst2);
        for (int i = 0; i < lst1.size(); i++) {
            assertEquals(i + "", lst1.get(i), lst2.get(i));
        }
    }
}
