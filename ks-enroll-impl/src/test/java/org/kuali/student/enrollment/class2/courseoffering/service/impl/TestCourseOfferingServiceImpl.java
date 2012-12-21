package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.enrollment.class1.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
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
import static org.junit.Assert.fail;


/*
 * This class was used to test the class1 backed implementation of CourseOfferingService for CourseOffering, FormatOffering and ActivityOffering.
 * 
 * For M4 it has been refactored.  Most of the test are now in TestCourseOfferingServiceMockImpl and only db dependent tests go here.
 * 
 * See TestLprServiceImpl for an example.
 *
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImpl {
    private static final Logger log = Logger
            .getLogger(TestCourseOfferingServiceImpl.class);

    @Resource
    protected CourseOfferingService coService;

    @Resource
    protected CourseService courseService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    @Resource(name = "LrcService")
    protected LRCService lrcService;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setup() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);

        // load in custom dates for use in the courses
        TermInfo fall2012 = dataLoader.createTerm("2012FA", "Fall 2012", AtpServiceConstants.ATP_FALL_TYPE_KEY, new DateTime().withDate(2012, 9, 1).toDate(), new DateTime().withDate(2012, 12, 31).toDate(), callContext);
        createCourseCHEM123(fall2012, callContext);
        new MockLrcTestDataLoader(this.lrcService).loadData();
    }

    private void createCourseCHEM123(TermInfo term, ContextInfo context) throws Exception {

        CourseInfo canonicalCourse = buildCanonicalCourse("CLU-1", term.getId(), "CHEM", "CHEM123", "Chemistry 123", "description 1");

        FormatInfo canonicalLectureOnlyFormat = buildCanonicalFormat("CHEM123:LEC-ONLY", canonicalCourse);

        buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureOnlyFormat);

        FormatInfo canonicalLectureAndLabFormat = buildCanonicalFormat("CHEM123:LEC-AND-LAB", canonicalCourse);

        buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureAndLabFormat);
        buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY, canonicalLectureAndLabFormat);

        courseService.createCourse(canonicalCourse, context);
    }

    private CourseInfo buildCanonicalCourse(String id, String startTermId, String subjectArea, String code, String title, String description) {
        CourseInfo info = new CourseInfo();
        info.setStartTerm(startTermId);
        info.setEffectiveDate(calcEffectiveDateForTerm(startTermId, id));
        info.setId(id);
        info.setSubjectArea(subjectArea);
        info.setCode(subjectArea);
        info.setCourseNumberSuffix(code.substring(subjectArea.length()));
        info.setCourseTitle(title);
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain(description);
        info.setDescr(rt);
        info.setTypeKey(LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        info.setStateKey(DtoConstants.STATE_ACTIVE);
        info.setFormats(new ArrayList<FormatInfo>());
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_2_0);
        rvg.setTypeKey(LrcServiceConstants.R1_RESULT_COMPONENT_TYPE_KEY_FIXED);
        info.getCreditOptions().add(rvg);
        return info;
    }


    private ActivityInfo buildCanonicalActivity(String activityTypeKey, FormatInfo format) {

        ActivityInfo info = new ActivityInfo();
        info.setId(CourseOfferingServiceTestDataUtils.createCanonicalActivityId(format.getId(), activityTypeKey));
        info.setTypeKey(activityTypeKey);
        info.setStateKey(DtoConstants.STATE_ACTIVE);

        format.getActivities().add(info);

        return info;

    }

    private FormatInfo buildCanonicalFormat(String formatId, CourseInfo course) {

        FormatInfo info = new FormatInfo();
        info.setId(formatId);
        info.setTypeKey(LuServiceConstants.COURSE_FORMAT_TYPE_KEY);
        info.setStateKey(DtoConstants.STATE_ACTIVE);
        info.setActivities(new ArrayList<ActivityInfo>());

        course.getFormats().add(info);

        return info;
    }

    private Date calcEffectiveDateForTerm(String termId, String context) {
        String year = termId.substring(0, 4);
        String mmdd = "09-01";
        if (termId.endsWith("FA")) {
            mmdd = "09-01";
        } else if (termId.endsWith("WI")) {
            mmdd = "01-01";
        } else if (termId.endsWith("SP")) {
            mmdd = "03-01";
        } else if (termId.endsWith("SU")) {
            mmdd = "06-01";
        }
        return str2Date(year + "-" + mmdd + " 00:00:00.0", context);
    }

    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        try {
            Date date = DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(str);
            return date;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }

    @Test
    public void testCRUDSCourseOffering() throws Exception {
        String coId = testCreateCourseOffering();
        testUpdateCourseOffering(coId);
        testSearchForCourseOfferings();
        testDeleteCourseOffering(coId);
    }

    private CourseOfferingInfo createCourseOffering() throws Exception {
        List<String> optionKeys = new ArrayList<String>();
        CourseInfo canonicalCourse = courseService
                .getCourse("CLU-1", callContext);
        CourseOfferingInfo coInfo = CourseOfferingServiceTestDataUtils
                .createCourseOffering(canonicalCourse, "2012FA");

        // gets around the unique course code constraint
        // this is ok for testing.
        coInfo.setCourseCode(coInfo.getCourseOfferingCode() + "TESTING CREATE");

        CourseOfferingInfo created = coService.createCourseOffering("CLU-1",
                "2012FA", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo,
                optionKeys, callContext);

        return created;
    }

    private String testCreateCourseOffering() throws Exception {
        CourseOfferingInfo created = createCourseOffering();
        assertNotNull(created);
        assertEquals("CLU-1", created.getCourseId());
        assertEquals("2012FA", created.getTermId());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                created.getTypeKey());
        assertEquals("CHEM123", created.getCourseOfferingCode());
        assertEquals("Chemistry 123", created.getCourseOfferingTitle());

        CourseOfferingInfo retrieved = coService.getCourseOffering(
                created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals("CLU-1", retrieved.getCourseId());
        assertEquals("2012FA", retrieved.getTermId());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                retrieved.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                retrieved.getTypeKey());

        assertEquals("CHEM123", retrieved.getCourseOfferingCode());
        assertEquals("Chemistry 123", retrieved.getCourseOfferingTitle());

        List<CourseOfferingInfo> offerings = coService.getCourseOfferingsByCourse("CLU-1", callContext);

        assertEquals(1, offerings.size());

        return created.getId();
    }

    private void testUpdateCourseOffering(String coId) throws Exception {
        try {
            CourseOfferingInfo coi = coService.getCourseOffering(coId,
                    callContext);
            assertNotNull(coi);
            coi.setIsHonorsOffering(true);
            coi.setMaximumEnrollment(40);
            coi.setMinimumEnrollment(10);

            //skiping instructors test because we can't config kim personservice at test context

            // dynamic attributes
            AttributeTester attributeTester = new AttributeTester();
            List<AttributeInfo> expectedList = new ArrayList<AttributeInfo>();
            attributeTester.add2ForCreate(expectedList);
            coi.getAttributes().addAll(expectedList);
            coi.setFundingSource("state");

            CourseOfferingInfo updated = coService.updateCourseOffering(coId,
                    coi, callContext);
            assertNotNull(updated);

            CourseOfferingInfo retrieved = coService.getCourseOffering(coId,
                    callContext);
            assertNotNull(retrieved);

            assertTrue(retrieved.getIsHonorsOffering());
            assertEquals(coi.getMaximumEnrollment(),
                    retrieved.getMaximumEnrollment());
            assertEquals(coi.getMinimumEnrollment(),
                    retrieved.getMinimumEnrollment());
            assertEquals("state", coi.getFundingSource());
            attributeTester.check(expectedList, coi.getAttributes());

        } catch (Exception ex) {
            log.error("exception due to", ex);
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    private void testSearchForCourseOfferings() throws Exception {
        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.like("courseOfferingCode", "CHEM%"),
                    PredicateFactory.equalIgnoreCase("atpId", "2012FA")));
            QueryByCriteria criteria = qbcBuilder.build();

            List<CourseOfferingInfo> coList = coService
                    .searchForCourseOfferings(criteria, callContext);
            assertNotNull(coList);
            assertEquals(1, coList.size());
            CourseOfferingInfo coInfo = coList.get(0);
            assertEquals("CHEM123", coInfo.getCourseOfferingCode());
            assertEquals("2012FA", coInfo.getTermId());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    private void testDeleteCourseOffering(String coId) throws Exception {
        try {
            // Delete the course offering and check that the status returned was
            // a success
            StatusInfo delResult = coService.deleteCourseOffering(coId, callContext);
            assertTrue(delResult.getIsSuccess());

            try {
                coService.getCourseOffering(coId, callContext);
            } catch (DoesNotExistException ex) {
                //expected
            }

        } catch (Exception ex) {
            log.error("exception due to ", ex);
            fail("Exception from service call :" + ex.getMessage());
        }

    }

    @Test
    public void testCRFormatOffering() throws Exception {
        CourseOfferingInfo co = createCourseOffering();
        FormatOfferingInfo created = createFormatOffering(co.getId(), co.getTermId());

        assertNotNull(created);
        assertEquals(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY,
                created.getStateKey());
        assertEquals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
                created.getTypeKey());
        assertEquals("TEST FORMAT OFFERING", created.getDescr().getPlain());

        FormatOfferingInfo retrieved = coService.getFormatOffering(created.getId(), callContext);
        assertEquals(retrieved.getStateKey(),
                created.getStateKey());
        assertEquals(retrieved.getTypeKey(),
                created.getTypeKey());
        assertEquals(retrieved.getDescr().getPlain(), created.getDescr().getPlain());
        assertEquals(retrieved.getTermId(), created.getTermId());
    }

    private FormatOfferingInfo createFormatOffering(String coId, String coTermId) throws Exception {
        FormatOfferingInfo fo = null;
        try {
            FormatOfferingInfo newFO = CourseOfferingServiceTestDataUtils
                    .createFormatOffering(coId, "CHEM123:LEC-ONLY",
                            coTermId, "TEST FORMAT OFFERING",
                            LuiServiceConstants.ALL_ACTIVITY_TYPES);

            fo = coService.createFormatOffering(coId,
                    "CHEM123:LEC-ONLY", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
                    newFO, callContext);
        } catch (Exception ex) {
            log.error("exception due to ", ex);
            fail(ex.getMessage());
        }

        return fo;
    }

    private ActivityOfferingInfo createActivityOffering(CourseOfferingInfo courseOffering, String foId) throws Exception {
        String activityId = CourseOfferingServiceTestDataUtils
                .createCanonicalActivityId("CHEM123:LEC-ONLY",
                        LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
        ActivityOfferingInfo ao = CourseOfferingServiceTestDataUtils
                .createActivityOffering("2012FA", courseOffering, foId,
                        null, activityId, "Lecture", "A",
                        LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
                        instructors);

        ActivityOfferingInfo created = coService.createActivityOffering(
                foId, activityId,
                LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, ao,
                callContext);

        return created;
    }

    @Test
    public void testCRActivityOffering() throws Exception {
        CourseOfferingInfo courseOffering = createCourseOffering();
        FormatOfferingInfo fo = createFormatOffering(courseOffering.getId(), courseOffering.getTermId());

        try {
            ActivityOfferingInfo created = createActivityOffering(courseOffering, fo.getId());
            assertNotNull(created);

            ActivityOfferingInfo retrieved = coService.getActivityOffering(
                    created.getId(), callContext);
            assertNotNull(retrieved);

            assertEquals(created.getActivityId(), retrieved.getActivityId());
            assertEquals(created.getTermId(), retrieved.getTermId());
            assertEquals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY,
                    retrieved.getStateKey());
            assertEquals(
                    LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
                    retrieved.getTypeKey());

            // test getActivityOfferingsByCourseOffering
            List<ActivityOfferingInfo> activities = coService
                    .getActivityOfferingsByCourseOffering(courseOffering.getId(), callContext);
            assertNotNull(activities);

            assertEquals(1, activities.size());

            boolean foundActivityId = false;

            for (ActivityOfferingInfo activityOfferingInfo : activities) {

                if (activityOfferingInfo.getActivityId().equals(
                        created.getActivityId())) {
                    foundActivityId = true;
                }
            }
            assertTrue(foundActivityId);

        } catch (Exception ex) {
            log.fatal("Exception from serviceCall", ex);

            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testDeleteCourseOfferingCascaded() throws Exception {
        CourseOfferingInfo courseOffering = createCourseOffering();
        FormatOfferingInfo fo = createFormatOffering(courseOffering.getId(), courseOffering.getTermId());
        ActivityOfferingInfo ao = createActivityOffering(courseOffering, fo.getId());
        List<FormatOfferingInfo> oformats = coService.getFormatOfferingsByCourseOffering(courseOffering.getId(), callContext);
        assertEquals(1, oformats.size());

        List<ActivityOfferingInfo> oactivities = coService.getActivityOfferingsByCourseOffering(courseOffering.getId(), callContext);
        assertEquals(1, oactivities.size());

        StatusInfo status = coService.deleteCourseOfferingCascaded(courseOffering.getId(), callContext);
        assertTrue(status.getIsSuccess());

        List<FormatOfferingInfo> formats = coService.getFormatOfferingsByCourseOffering(courseOffering.getId(), callContext);
        assertEquals(0, formats.size());

        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByCourseOffering(courseOffering.getId(), callContext);
        assertEquals(0, activities.size());

        try {
            coService.getCourseOffering(courseOffering.getId(), callContext);
        } catch (DoesNotExistException ex) {
            //expected
        }
    }
}
