/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

/**
 * Tests the mock persistence implementation to make sure it does basic crud
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soc-mock-persistence-impl-test-context.xml"})
public class TestCourseOfferingSetServiceMockImpl {

    @Resource(name = "socService")
    protected CourseOfferingSetService socService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
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
        this.testCRUDSoc();
        this.testCRUDResult();
        this.testCRUDResultItem();
    }

    private void testCRUDSoc() throws Exception {
        // create
        SocInfo orig = new SocInfo();
        orig.setName("test name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        orig.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        orig.setTermId("myTermId");
        orig.setSubjectArea("ENG");
        orig.setUnitsContentOwnerId("myUnitId");
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
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
        List<AttributeInfo> attrs = info.getAttributes();
        List<AttributeInfo> attrs2 = new ArrayList<AttributeInfo>();
        Date date = null;
        for (AttributeInfo attr : attrs) {
            if (attr.getKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
                if (date != null) {
                    fail("should only be one dynamic attribute for draft state");
                }
                date = DateFormatters.STATE_CHANGE_DATE_FORMATTER.parse(attr.getValue());
            } else {
                attrs2.add(attr);
            }
        }
        assertNotNull(date);
        new AttributeTester().check(orig.getAttributes(), attrs2);
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
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("test name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated",
                "description formatted 1 updated"));
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue(
                "value1Updated");
        info = this.socService.updateSoc(orig.getId(), orig, callContext);
        assertNotSame(orig, info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getSubjectArea(), info.getSubjectArea());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.socService.getSoc(orig.getId(), callContext);
        assertNotSame(orig, info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getSubjectArea(), info.getSubjectArea());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // update the state
        orig = info;
        orig.setStateKey(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY);
        try {
            info = this.socService.updateSoc(orig.getId(), orig, callContext);
            fail("should have gotten readonly exception");
        } catch (ReadOnlyException ex) {
            // expected
        }
        StatusInfo status = this.socService.changeSocState(orig.getId(), CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());
        info = this.socService.getSoc(orig.getId(), callContext);
        assertNotSame(orig, info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getSubjectArea(), info.getSubjectArea());
        assertEquals(4, info.getAttributes().size());
        assertNotNull(info.getAttributeValue(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY));
//        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // delete
        orig = info;
        status = this.socService.deleteSoc(orig.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            info = this.socService.getSoc(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    private void testCRUDResult() throws Exception {
        SocInfo sourceSoc = new SocInfo();
        sourceSoc.setName("source name");
        sourceSoc.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        sourceSoc.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        sourceSoc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        sourceSoc.setTermId("myTermId1");
        sourceSoc = socService.createSoc(sourceSoc.getTermId(), sourceSoc.getTypeKey(), sourceSoc, callContext);

        SocInfo targetSoc = new SocInfo();
        targetSoc.setName("target name");
        targetSoc.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        targetSoc.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        targetSoc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        targetSoc.setTermId("myTermId2");
        targetSoc = socService.createSoc(targetSoc.getTermId(), targetSoc.getTypeKey(), targetSoc, callContext);

        // create
        SocRolloverResultInfo orig = new SocRolloverResultInfo();
        orig.setMessage(new RichTextHelper().toRichTextInfo("message plain 1", "message formatted 1"));
        orig.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        orig.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        orig.setItemsExpected(100);
        orig.setItemsProcessed(1);
        orig.setSourceSocId(sourceSoc.getId());
        orig.setTargetSocId(targetSoc.getId());
        orig.setTargetTermId("my term");
        orig.getOptionKeys().add("my first option");
        orig.getOptionKeys().add("my 2nd option");
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        SocRolloverResultInfo info = socService.createSocRolloverResult(orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getItemsExpected(), info.getItemsExpected());
        assertEquals(orig.getItemsProcessed(), info.getItemsProcessed());
        assertEquals(orig.getSourceSocId(), info.getSourceSocId());
        assertEquals(orig.getTargetSocId(), info.getTargetSocId());
        assertEquals(orig.getTargetTermId(), info.getTargetTermId());
        compareStringList(orig.getOptionKeys(), info.getOptionKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.socService.getSocRolloverResult(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getItemsExpected(), info.getItemsExpected());
        assertEquals(orig.getItemsProcessed(), info.getItemsProcessed());
        assertEquals(orig.getSourceSocId(), info.getSourceSocId());
        assertEquals(orig.getTargetSocId(), info.getTargetSocId());
        assertEquals(orig.getTargetTermId(), info.getTargetTermId());
        compareStringList(orig.getOptionKeys(), info.getOptionKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setMessage(new RichTextHelper().toRichTextInfo("message plain 1 updated",
                "description formatted 1 updated"));
        orig.setStateKey(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY);
        orig.setItemsExpected(orig.getItemsExpected() + 100);
        orig.setItemsProcessed(orig.getItemsProcessed() + 100);
        orig.setTargetTermId("different term");
        orig.setSourceSocId(targetSoc.getId()); // reverse them!
        orig.setTargetSocId(sourceSoc.getId());
        orig.getOptionKeys().remove(0);
        orig.getOptionKeys().add("added a 3rd option");
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue(
                "value1Updated");
        info = this.socService.updateSocRolloverResult(orig.getId(), orig,
                callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getItemsExpected(), info.getItemsExpected());
        assertEquals(orig.getItemsProcessed(), info.getItemsProcessed());
        assertEquals(orig.getSourceSocId(), info.getSourceSocId());
        assertEquals(orig.getTargetSocId(), info.getTargetSocId());
        assertEquals(orig.getTargetTermId(), info.getTargetTermId());
        compareStringList(orig.getOptionKeys(), info.getOptionKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.socService.getSocRolloverResult(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getItemsExpected(), info.getItemsExpected());
        assertEquals(orig.getItemsProcessed(), info.getItemsProcessed());
        assertEquals(orig.getSourceSocId(), info.getSourceSocId());
        assertEquals(orig.getTargetSocId(), info.getTargetSocId());
        assertEquals(orig.getTargetTermId(), info.getTargetTermId());
        compareStringList(orig.getOptionKeys(), info.getOptionKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // delete
        orig = info;
        StatusInfo status = this.socService.deleteSocRolloverResult(orig.getId(),
                callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            info = this.socService.getSocRolloverResult(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            // expected
        }
    }

    private void testCRUDResultItem() throws Exception {
        SocInfo sourceSoc = new SocInfo();
        sourceSoc.setName("source name");
        sourceSoc.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        sourceSoc.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        sourceSoc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        sourceSoc.setTermId("myTermId1");
        sourceSoc = socService.createSoc(sourceSoc.getTermId(), sourceSoc.getTypeKey(), sourceSoc, callContext);

        SocInfo targetSoc = new SocInfo();
        targetSoc.setName("target name");
        targetSoc.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        targetSoc.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        targetSoc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        targetSoc.setTermId("myTermId2");
        targetSoc = socService.createSoc(targetSoc.getTermId(), targetSoc.getTypeKey(), targetSoc, callContext);

        SocRolloverResultInfo result = new SocRolloverResultInfo();
        result.setMessage(new RichTextHelper().toRichTextInfo("message plain 1", "message formatted 1"));
        result.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        result.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        result.setItemsExpected(100);
        result.setItemsProcessed(1);
        result.setSourceSocId(sourceSoc.getId());
        result.setTargetSocId(targetSoc.getId());
        result.setTargetTermId("my term");
        result.getOptionKeys().add("my first option");
        result.getOptionKeys().add("my 2nd option");
        result.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        result.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        result = socService.createSocRolloverResult(result.getTypeKey(), result, callContext);

        // create
        SocRolloverResultItemInfo orig = new SocRolloverResultItemInfo();
        orig.setSocRolloverResultId(result.getId());
        orig.setMessage(new RichTextHelper().toRichTextInfo("message plain 1", "message formatted 1"));
        orig.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        orig.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        orig.setSourceCourseOfferingId("co1");
        orig.setTargetCourseOfferingId("co2");
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        SocRolloverResultItemInfo info = socService.createSocRolloverResultItem(orig.getSocRolloverResultId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getSourceCourseOfferingId(), info.getSourceCourseOfferingId());
        assertEquals(orig.getTargetCourseOfferingId(), info.getTargetCourseOfferingId());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.socService.getSocRolloverResultItem(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getSourceCourseOfferingId(), info.getSourceCourseOfferingId());
        assertEquals(orig.getTargetCourseOfferingId(), info.getTargetCourseOfferingId());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setMessage(new RichTextHelper().toRichTextInfo("message plain 1 updated",
                "description formatted 1 updated"));
        orig.setStateKey(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY);
        assertEquals(orig.getSourceCourseOfferingId(), info.getSourceCourseOfferingId());
        assertEquals(orig.getTargetCourseOfferingId(), info.getTargetCourseOfferingId());
        orig.setSourceCourseOfferingId("coIdDiff1");
        orig.setTargetCourseOfferingId("coIddiff2");
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue(
                "value1Updated");
        info = this.socService.updateSocRolloverResultItem(orig.getId(), orig,
                callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getSourceCourseOfferingId(), info.getSourceCourseOfferingId());
        assertEquals(orig.getTargetCourseOfferingId(), info.getTargetCourseOfferingId());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.socService.getSocRolloverResultItem(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertNotNull(info.getMessage());
        assertEquals(orig.getMessage().getPlain(), info.getMessage().getPlain());
        assertEquals(orig.getMessage().getFormatted(), info.getMessage().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getSourceCourseOfferingId(), info.getSourceCourseOfferingId());
        assertEquals(orig.getTargetCourseOfferingId(), info.getTargetCourseOfferingId());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // delete
        orig = info;
        StatusInfo status = this.socService.deleteSocRolloverResultItem(orig.getId(),
                callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            info = this.socService.getSocRolloverResultItem(orig.getId(), callContext);
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
