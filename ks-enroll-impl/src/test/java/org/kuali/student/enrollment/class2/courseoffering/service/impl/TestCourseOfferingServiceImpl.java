package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ColocatedOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.mortbay.component.LifeCycle;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    protected StateService stateService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    @Resource(name = "LrcService")
    protected LRCService lrcService;
    
    public static String principalId = "123";
    public ContextInfo callContext = null;

    private final List<CourseOfferingCrossListingInfo> crossListings = new ArrayList<CourseOfferingCrossListingInfo>();

    @Before
    public void setup() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);

        // load in custom dates for use in the courses
        TermInfo fall2012 = dataLoader.createTerm("2012FA", "Fall 2012", AtpServiceConstants.ATP_FALL_TYPE_KEY, new DateTime().withDate(2012, 9, 1).toDate(), new DateTime().withDate(2012, 12, 31).toDate(), callContext);
        createCourseCHEM123(fall2012, callContext);
        new MockLrcTestDataLoader(this.lrcService).loadData();

        createStateTestData();
    }

    @After
    public void teardown() throws Exception {
        destroyStateTestData();
    }

    private void createStateTestData() throws Exception {

        // ActivityOffering state
        LifecycleInfo aoLifecycle = addLifecycle( LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY );
        addState( aoLifecycle, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, true );

        // FormatOffering state
        LifecycleInfo foLifecycle = addLifecycle( LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY );
        addState( foLifecycle, LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, true );

        // CourseOffering state
        LifecycleInfo coLifecycle = addLifecycle( LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY );
        addState( coLifecycle, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY, true );
    }

    private void destroyStateTestData() throws Exception {

        // ActivityOffering state
        cleanupStateTestData( LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY );

        // FormatOffering state
        cleanupStateTestData( LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY );

        // CourseOffering state
        cleanupStateTestData( LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY );
    }

    private LifecycleInfo addLifecycle( String name ) throws Exception {

        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> lifecycle for testing purposes");
        rti.setPlain("Plain lifecycle for testing purposes");
        origLife.setDescr(rti);
        origLife.setKey( name );
        origLife.setName( "TEST_NAME" );
        origLife.setRefObjectUri( "TEST_URI" );
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origLife.getAttributes().add(attr);

        return stateService.createLifecycle(origLife.getKey(), origLife, callContext);
    }

    private StateInfo addState( LifecycleInfo lifecycleInfo, String state, boolean isInitialState ) throws Exception {

        StateInfo orig = new StateInfo();
        orig.setKey(state);
        orig.setLifecycleKey(lifecycleInfo.getKey());
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> state for testing purposes");
        rti.setPlain("Plain state again for testing purposes");
        orig.setDescr(rti);
        orig.setName("Testing state");
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        orig.setIsInitialState(isInitialState);

        return stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
    }

    private void cleanupStateTestData( String state ) {
        try {
            stateService.deleteState( state, callContext );
        } catch( Exception e ) { }
    }

    private void cleanupLifecycleTestData( String name ) {
        try {
            stateService.deleteLifecycle(name, callContext);
        } catch( Exception e ) { }
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

    private CourseOfferingInfo createCourseOffering() throws Exception {
        List<String> optionKeys = new ArrayList<String>();
        CourseInfo canonicalCourse = courseService
                .getCourse("CLU-1", callContext);
        CourseOfferingInfo coInfo = CourseOfferingServiceTestDataUtils
                .createCourseOffering(canonicalCourse, "2012FA");

        buildAndAttachCrossListings(coInfo);

        // gets around the unique course code constraint
        // this is ok for testing.
        coInfo.setCourseCode(coInfo.getCourseOfferingCode() + "TESTING CREATE");

        CourseOfferingInfo created = coService.createCourseOffering("CLU-1",
                "2012FA", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo,
                optionKeys, callContext);


        return created;
    }

    private void buildAndAttachCrossListings(CourseOfferingInfo coInfo) {

        CourseOfferingCrossListingInfo info = null;
        Date createTime = new Date();
        for( int i = 0, j = 3 ; i < j ; i++ ) {
            info = new CourseOfferingCrossListingInfo();

            info.setId("MY-ID_" + i);
            info.setTypeKey("MY-TYPE_" + i);
            info.setStateKey("MY-STATE_" + i);
            info.setCode("MY-CODE_" + i);
            info.setSubjectArea("MY-SUBJECT_" + i);
            //info.setDepartmentOrgId(coInfo.getd);     // not sure how this maps
            info.setCourseNumberSuffix("MY-SUFFIX_" + i);

            MetaInfo meta = new MetaInfo();
            meta.setCreateId("META-CREATE-ID_" + i);
            meta.setCreateTime(createTime);
            meta.setVersionInd(Integer.toString(i));
            info.setMeta(meta);

            List<AttributeInfo> attrs = new ArrayList<AttributeInfo>();
            AttributeInfo attr = new AttributeInfo();
            attr.setId("ATTRS-ID_" + i);
            attr.setKey("ATTRS-KEY_" + i);
            attr.setValue("ATTRS-VALUE_" + i);
            attrs.add(attr);
            info.setAttributes(attrs);

            crossListings.add(info);
        }

        coInfo.setCrossListings(crossListings);
    }


    private void testUpdateCourseOffering(String coId) throws Exception {
        try {
            CourseOfferingInfo coi = coService.getCourseOffering(coId,
                    callContext);
            assertNotNull(coi);
            coi.setIsHonorsOffering(true);
            coi.setMaximumEnrollment(40);
            coi.setMinimumEnrollment(10);

            //skipping instructors test because we can't config kim personservice at test context

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

            validateCrossListings(coInfo.getCrossListings());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    private void validateCrossListings(List<CourseOfferingCrossListingInfo> crossListings) {

        assertTrue( this.crossListings.size() == crossListings.size() );
        Collections.sort(this.crossListings);
        Collections.sort(crossListings);

        for( int i = 0, j = this.crossListings.size() ; i < j ; i++ ) {
            CourseOfferingCrossListingInfo expected = this.crossListings.get(i);
            CourseOfferingCrossListingInfo actual = crossListings.get(i);

            // basic props
            assertEquals( expected.getId(), actual.getId() );
            assertEquals( expected.getTypeKey(), actual.getTypeKey() );
            assertEquals( expected.getStateKey(), actual.getStateKey() );
            assertEquals( expected.getCode() + expected.getCourseNumberSuffix(),    // business-rules append the suffix to the code when object is persisted to DB
                            actual.getCode() );
            assertEquals( expected.getSubjectArea(), actual.getSubjectArea() );
            assertEquals( expected.getSubjectOrgId(), actual.getSubjectOrgId() );
            assertEquals( expected.getCourseNumberSuffix(), actual.getCourseNumberSuffix() );
        }

    }

    private CourseOfferingCrossListingInfo getCrosslistingWithMatchingIdFromList(CourseOfferingCrossListingInfo target, List<CourseOfferingCrossListingInfo> list) {
        for( CourseOfferingCrossListingInfo item : list ) {
            if( item.getId().equals(target.getId())) return target;
        }
        return null;
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

            ActivityOffering createdAo = coService.getActivityOffering(retrieved.getId(), callContext);
            assertFalse(createdAo.getIsPartOfColocatedOfferingSet());

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

    private ColocatedOfferingSetInfo createColocatedOfferingSet() throws Exception {

        CourseOfferingInfo courseOffering = createCourseOffering();
        FormatOfferingInfo fo = createFormatOffering(courseOffering.getId(), courseOffering.getTermId());

        ActivityOfferingInfo activityOfferingInfo = createActivityOffering(courseOffering, fo.getId());

        ColocatedOfferingSetInfo info = new ColocatedOfferingSetInfo();

        info.setTypeKey("test.type.key");
        info.setStateKey("test.state.key");
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("test");
        info.setDescr(rt);
        info.setName("test");
        List<String> aos = new ArrayList<String>();
        aos.add(activityOfferingInfo.getId());
        info.setActivityOfferingIds(aos);
        info.setMaximumEnrollment(10);
        info.setIsMaxEnrollmentShared(true);

        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setKey("test.key");
        attributeInfo.setValue("test.value");
        info.getAttributes().add(attributeInfo);

        return info;
    }

    @Test
    public void testCreateColocatedOfferingSet() throws Exception {

        ColocatedOfferingSetInfo info = createColocatedOfferingSet();

        ColocatedOfferingSetInfo created = coService.createColocatedOfferingSet("test.type.key",info,callContext);

        assertNotNull(created);
        assertEquals("test.type.key",created.getTypeKey());
        assertEquals("test.state.key",created.getStateKey());
        assertEquals(10,created.getMaximumEnrollment().intValue());
        assertEquals(true,created.getIsMaxEnrollmentShared());
        assertEquals("test",created.getName());
        assertEquals(1,created.getActivityOfferingIds().size());
        assertEquals(1,created.getAttributes().size());

        ActivityOffering createdAo = coService.getActivityOffering(created.getActivityOfferingIds().get(0), callContext);
        assertNotNull(createdAo);
        assertTrue(createdAo.getIsPartOfColocatedOfferingSet());
    }

    @Test
    public void testUpdateColocatedOfferingSet() throws Exception {

        ColocatedOfferingSetInfo info = createColocatedOfferingSet();

        ColocatedOfferingSetInfo created = coService.createColocatedOfferingSet("test.type.key",info,callContext);

        created.getActivityOfferingIds().clear();
        created.setIsMaxEnrollmentShared(false);
        created.setMaximumEnrollment(20);

        ColocatedOfferingSetInfo updated = coService.updateColocatedOfferingSet(created.getId(), created, callContext);
        assertNotNull(updated);
        assertEquals(20,updated.getMaximumEnrollment().intValue());
        assertEquals(false,updated.getIsMaxEnrollmentShared());
        assertEquals(0,updated.getActivityOfferingIds().size());
    }

    @Test
    public void testDeleteColocatedOfferingSet() throws Exception {

        ColocatedOfferingSetInfo info = createColocatedOfferingSet();

        ColocatedOfferingSetInfo created = coService.createColocatedOfferingSet("test.type.key",info,callContext);

        assertNotNull(created);

        StatusInfo statusInfo = coService.deleteColocatedOfferingSet(created.getId(), callContext);
        assertTrue(statusInfo.getIsSuccess());
    }

    @Test
    public void testGetColocatedOfferingSet() throws Exception {

        ColocatedOfferingSetInfo info = createColocatedOfferingSet();
        ColocatedOfferingSetInfo created = coService.createColocatedOfferingSet("test.type.key",info,callContext);

        List<ColocatedOfferingSetInfo> colos = coService.getColocatedOfferingSetsByActivityOffering(info.getActivityOfferingIds().get(0),callContext);
        assertNotNull(colos);
        assertEquals(1,colos.size());
        ColocatedOfferingSetInfo pulledColo = colos.get(0);
        assertEquals(info.getMaximumEnrollment(), pulledColo.getMaximumEnrollment());
        assertEquals(info.getIsMaxEnrollmentShared(), pulledColo.getIsMaxEnrollmentShared());
        assertEquals(info.getName(), pulledColo.getName());

        List<String> coloIds = coService.getColocatedOfferingSetIdsByType("test.type.key",callContext);
        assertNotNull(coloIds);
        assertEquals(1,coloIds.size());

        coloIds = new ArrayList<String>();
        coloIds.add(created.getId());

        List<ColocatedOfferingSetInfo> coloInfos = coService.getColocatedOfferingSetsByIds(coloIds,callContext);
        assertNotNull(coloInfos);
        assertEquals(1,coloInfos.size());
    }

    @Test
    public void testGetActivityOfferingsForSeatPoolDefinition() throws Exception {
        // Create  Seatpool
        SeatPoolDefinitionInfo seatPoolDefinitionInfo = new SeatPoolDefinitionInfo();
        seatPoolDefinitionInfo.setName("TestSeatPoolDefinitionInfo-Id");
        seatPoolDefinitionInfo.setStateKey("TestSeatPoolDefinitionInfo-StateKey1");
        seatPoolDefinitionInfo.setTypeKey("TestSeatPoolDefinitionInfo-TypeKey1");
        seatPoolDefinitionInfo.setExpirationMilestoneTypeKey("TestSeatPoolDefinitionInfo-MilestoneKey1");
        seatPoolDefinitionInfo.setIsPercentage(false);
        seatPoolDefinitionInfo.setSeatLimit(50);
        seatPoolDefinitionInfo.setProcessingPriority(3);
        seatPoolDefinitionInfo.setId(null);
        SeatPoolDefinitionInfo seatPoolCreated = coService.createSeatPoolDefinition(seatPoolDefinitionInfo, callContext);

        // Create AO
        CourseOfferingInfo courseOffering = createCourseOffering();
        FormatOfferingInfo fo = createFormatOffering(courseOffering.getId(), courseOffering.getTermId());
        ActivityOfferingInfo activityOfferingCreated = createActivityOffering(courseOffering, fo.getId());

        // Add Seatpool to AO
        coService.addSeatPoolDefinitionToActivityOffering(seatPoolCreated.getId(), activityOfferingCreated.getId(), callContext);

        //  Actual test
        List<ActivityOfferingInfo> activityOfferingInfos = coService.getActivityOfferingsForSeatPoolDefinition(seatPoolCreated.getId(), callContext);

        assertEquals(1, activityOfferingInfos.size());
        assertEquals(activityOfferingCreated.getId(), activityOfferingInfos.get(0).getId());
    }

}