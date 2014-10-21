package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.class2.acal.util.AcalTestDataLoader;
import org.kuali.student.enrollment.class2.acal.util.MockAcalTestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-mocks-context.xml"})
public class TestCourseOfferingServiceImplWithMocks {

    @Resource(name = "coService")
    protected CourseOfferingService courseOfferingService;
    public static String principalId = "123";
    public ContextInfo callContext = null;
    @Resource(name = "courseService")
    protected CourseService courseService;
    @Resource(name="stateService")
    protected StateService stateService;
    @Resource(name = "luiService")
    protected LuiService luiService;
    @Resource(name = "acalService")
    protected AcademicCalendarService acalService;
    @Resource(name = "atpService")
    protected AtpService atpService;
    @Resource(name = "LrcService")
    protected LRCService lrcService;

    @Before
    public void setUp() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        new CourseR1TestDataLoader(this.courseService).loadData();
        new LuiServiceDataLoader(this.luiService).loadData();

        // due to KSENROLL-4185, data must be loaded into the mock Atp and mock Acal services
        AcalTestDataLoader acalLoader = new AcalTestDataLoader(this.atpService);
        acalLoader.loadTerm("testAtpId1", "test1", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "description 1");
        new MockAcalTestDataLoader(this.acalService).loadData();
        new MockLrcTestDataLoader(this.lrcService).loadData();

        createStateTestData();
    }

    private void createStateTestData() throws Exception {

        // ActivityOffering state
        cleanupStateTestData( LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY );
        LifecycleInfo aoLifecycle = addLifecycle( LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY );
        addState( aoLifecycle, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, true );

        // FormatOffering state
        cleanupStateTestData( LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY );
        LifecycleInfo foLifecycle = addLifecycle( LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY );
        addState( foLifecycle, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, true );

        // CourseOffering state
        cleanupStateTestData( LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY );
        LifecycleInfo coLifecycle = addLifecycle( LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY );
        addState( coLifecycle, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY, true );
    }

    // TODO: temporary stop-gap because SS throws an error about duplicate-data; this cleans state from previous runs
    private void cleanupStateTestData( String state ) {
        try {
            stateService.deleteState( state, callContext );
        } catch( Exception e ) { }
    }

    // TODO: temporary stop-gap because SS throws an error about duplicate-data; this cleans state from previous runs
    private void cleanupLifecycleTestData( String name ) {
        try {
            stateService.deleteLifecycle( name, callContext );
        } catch( Exception e ) { }
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

    @Test
    public void testCRUD() throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException,
            DependentObjectsExistException {
        CourseOfferingInfo co = this.testCRUDCourseOffering();
        FormatOfferingInfo fo = this.testCRUDFormatOffering(co);
        ActivityOfferingInfo ao = this.testCRUDActivityOffering(fo);
        this.testDeletes(co, fo, ao);
    }
    public CourseOfferingInfo testCRUDCourseOffering() throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // get course
        CourseInfo course;
        course = courseService.getCourse("COURSE1", ContextUtils.getContextInfo());
        // create co from course
        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo orig = new CourseOfferingInfo();
        orig.setCourseId(course.getId());
        orig.setTermId("testAtpId1");
        orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        orig.setCourseOfferingTitle("my name");
        orig.setWaitlistLevelTypeKey("waitlist key");
        orig.setHasWaitlist(true);
        orig.setFinalExamType(FinalExam.STANDARD.toString());
        orig.setIsEvaluated(true);
        orig.setIsFeeAtActivityOffering(false);
        orig.setFundingSource("funding source");
        orig.setCourseOfferingCode("CODE");
        orig.setCourseNumberSuffix("");
        orig.setCourseOfferingTitle("Title");
        orig.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
        orig.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        orig.setGradingOptionId(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);


        CourseOfferingInfo info = courseOfferingService.createCourseOffering(orig.getCourseId(), orig.getTermId(), 
                orig.getTypeKey(), orig, optionKeys, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getWaitlistLevelTypeKey(), info.getWaitlistLevelTypeKey());
        assertEquals(orig.getHasWaitlist(), info.getHasWaitlist());
        assertEquals(orig.getFinalExamType(), info.getFinalExamType());
        assertEquals(orig.getIsFeeAtActivityOffering(), info.getIsFeeAtActivityOffering());
        assertEquals(orig.getFundingSource(), info.getFundingSource());
        assertEquals(course.getCode() + info.getCourseNumberSuffix(), info.getCourseOfferingCode());
        assertEquals(orig.getCourseNumberSuffix(), info.getCourseNumberSuffix());
        assertEquals(course.getSubjectArea(), info.getSubjectArea());
        if (course.getDescr() != null) {
            assertEquals(course.getDescr().getPlain(), info.getDescr().getPlain());
            assertEquals(course.getDescr().getFormatted(), info.getDescr().getFormatted());
        }
//        assertEquals(2,info.getStudentRegistrationOptionIds().size());
//        assertTrue(info.getStudentRegistrationOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT));
//        assertTrue(info.getStudentRegistrationOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL));
//
//        assertEquals(2,info.getGradingOptionIds().size());
//        assertTrue(info.getGradingOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER));
//        assertTrue(info.getGradingOptionIds().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE));

        // TODO: test for these things 
//        assertEquals(course.getUnitsContentOwnerOrgIds(), info.getUnitsContentOwnerOrgIds());
//        assertEquals(course.getUnitsDeploymentOrgIds(), info.getUnitsDeploymentOrgIds());
//        assertEquals(course.getGradingOptions(), info.getGradingOptionIds());
//        assertEquals(course.getCreditOptionIds(), info.getCreditOptionIds());


        // refetch co 
        orig = info;
        info = courseOfferingService.getCourseOffering(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());

        // update co
        orig = info;
        orig.setIsHonorsOffering(true);
        orig.setMaximumEnrollment(40);
        orig.setMinimumEnrollment(10);
        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
//        OfferingInstructorInfo instructor = new OfferingInstructorInfo();
//        instructor.setPersonId("Pers-1");
//        instructor.setPercentageEffort(Float.valueOf("60"));
//        instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
//        instructor.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        // TODO: add this back in and test for it
//        instructors.add(instructor);
        orig.setInstructors(instructors);
        info = courseOfferingService.updateCourseOffering(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseId(), info.getCourseId());
        assertEquals(orig.getTermId(), info.getTermId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getIsHonorsOffering(), info.getIsHonorsOffering());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getInstructors().size(), info.getInstructors().size());
//        OfferingInstructorInfo origInst1 = orig.getInstructors().get(0);
//        OfferingInstructorInfo infoInst1 = info.getInstructors().get(0);
//        assertEquals(origInst1.getPersonId(), infoInst1.getPersonId());
//        assertEquals(origInst1.getPercentageEffort(), infoInst1.getPercentageEffort());
//        assertEquals(origInst1.getTypeKey(), infoInst1.getTypeKey());
//        assertEquals(origInst1.getStateKey(), infoInst1.getStateKey());
        return info;
    }

    private void testDeletes(CourseOfferingInfo co, FormatOfferingInfo fo, ActivityOfferingInfo ao)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DependentObjectsExistException {

        // delete activity offering
        StatusInfo status = this.courseOfferingService.deleteActivityOffering(ao.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            courseOfferingService.getActivityOffering(ao.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertEquals(ao.getId(), ex.getMessage());
        }


        // delete fo
        status = this.courseOfferingService.deleteFormatOffering(fo.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            courseOfferingService.getFormatOffering(fo.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertEquals(fo.getId(), ex.getMessage());
        }

        // delete co
        status = this.courseOfferingService.deleteCourseOffering(co.getId(), callContext);
        assertNotNull(status);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        try {
            courseOfferingService.getCourseOffering(co.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertEquals(co.getId(), ex.getMessage());
        }
    }

    public FormatOfferingInfo testCRUDFormatOffering(CourseOfferingInfo co)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException,
                   VersionMismatchException, ReadOnlyException, DependentObjectsExistException {
        FormatOfferingInfo orig = new FormatOfferingInfo();
        orig.setCourseOfferingId(co.getId());
        orig.setFormatId("COURSE1-FORMAT1");
        orig.setActivityOfferingTypeKeys(Arrays.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        FormatOfferingInfo info = courseOfferingService.createFormatOffering(orig.getCourseOfferingId(), orig.getFormatId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());
        // TODO: turn these tests back on once we get the corresponding JPA entities working 
//        assertEquals(orig.getActivityOfferingTypeKeys().size(), info.getActivityOfferingTypeKeys().size());
//        assertEquals(orig.getActivityOfferingTypeKeys().get(0), info.getActivityOfferingTypeKeys().get(0));

        List<FormatOfferingInfo> formats = courseOfferingService.getFormatOfferingsByCourseOffering(co.getId(), callContext);
        info = formats.get(0);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());

        orig = info;
        info = courseOfferingService.getFormatOffering(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());
//        assertEquals(orig.getActivityOfferingTypeKeys().size(), info.getActivityOfferingTypeKeys().size());
//        assertEquals(orig.getActivityOfferingTypeKeys().get(0), info.getActivityOfferingTypeKeys().get(0));

        orig = info;
        orig.setActivityOfferingTypeKeys(Arrays.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        info = courseOfferingService.updateFormatOffering(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getCourseOfferingId(), info.getCourseOfferingId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatId(), info.getFormatId());
//        assertEquals(orig.getActivityOfferingTypeKeys().size(), info.getActivityOfferingTypeKeys().size());
//        assertEquals(orig.getActivityOfferingTypeKeys().get(0), info.getActivityOfferingTypeKeys().get(0));
        return info;
    }


    public ActivityOfferingInfo testCRUDActivityOffering(FormatOfferingInfo fo)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException,
            ReadOnlyException, VersionMismatchException {

        ActivityOfferingInfo orig = new ActivityOfferingInfo();
        orig.setFormatOfferingId(fo.getId());
        orig.setActivityId(fo.getId() + "." + LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);
        orig.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        orig.setMinimumEnrollment(100);
        orig.setMaximumEnrollment(150);
        orig.setIsEvaluated(true);
        orig.setIsMaxEnrollmentEstimate(false);
        orig.setIsHonorsOffering(true);
        ActivityOfferingInfo info = courseOfferingService.createActivityOffering(orig.getFormatOfferingId(), orig.getActivityId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getIsEvaluated(), info.getIsEvaluated());
        assertEquals(orig.getIsMaxEnrollmentEstimate(), info.getIsMaxEnrollmentEstimate());
        assertEquals(orig.getIsHonorsOffering(), info.getIsHonorsOffering());

        orig = info;
        info = courseOfferingService.getActivityOffering(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());

        orig = info;
        orig.setMinimumEnrollment(100);
        info = courseOfferingService.updateActivityOffering(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getFormatOfferingId(), info.getFormatOfferingId());
        assertEquals(orig.getActivityId(), info.getActivityId());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        return info;

    }


    @Test
    public void testLuiServiceLuiSetMethods() throws Throwable {
        System.out.println("starting tests...");

        String luiSetTypeKey = "test.lui.set.type.key.test";
        LuiSetInfo luiSetInfo = new LuiSetInfo();
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain( "descr" );
        luiSetInfo.setDescr( descr );
        luiSetInfo.setStateKey( "test.lui.set.state.key.test" );
        luiSetInfo.setTypeKey( luiSetTypeKey );
        luiSetInfo.setName( "name" );
        luiSetInfo.setEffectiveDate( new Date() );
        luiSetInfo.setExpirationDate( new Date() );
        luiSetInfo.setMeta( new MetaInfo() );
        luiSetInfo.setAttributes( new ArrayList<AttributeInfo>() );

        // create
        LuiSetInfo created = luiService.createLuiSet( luiSetTypeKey, luiSetInfo, callContext );
        assertNotNull(created);
        assertTrue(UUIDHelper.isUUID(created.getId()));

        // read
        LuiSetInfo retrieved = luiService.getLuiSet( created.getId(), callContext );
        assertNotNull( retrieved );
        assertEquals( created.getId(), retrieved.getId() );

        // update
        retrieved.setName( "updated_name");
        LuiSetInfo replaced = luiService.updateLuiSet( created.getId(), retrieved, callContext );
        LuiSetInfo updated = luiService.getLuiSet( created.getId(), callContext );
        assertNotNull(replaced);
        assertNotNull(updated);
        assertEquals(created.getId(), replaced.getId());
        assertEquals( created.getId(), updated.getId() );
        assertEquals( created.getName(), replaced.getName() );
        assertEquals( retrieved.getName(), updated.getName() );

        // delete
        StatusInfo deleteStatus = luiService.deleteLuiSet( created.getId(), callContext );
        assertNotNull(deleteStatus);
        assertTrue(deleteStatus.getIsSuccess());
        try {
            luiService.getLuiSet(created.getId(), callContext);
            fail("should have thrown DoesNotExistException");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertEquals("luiSetId not found: " + retrieved.getId(), ex.getMessage());
        }

        // bulk operation -- get a bunch of luis using a list of their ids
        List<String> createdLuiSetIds = new ArrayList<String>();
        createdLuiSetIds.add(luiService.createLuiSet(luiSetTypeKey, luiSetInfo, callContext).getId());
        createdLuiSetIds.add( luiService.createLuiSet( luiSetTypeKey, luiSetInfo, callContext ).getId() );
        createdLuiSetIds.add( luiService.createLuiSet( luiSetTypeKey, luiSetInfo, callContext ).getId() );
        List<LuiSetInfo> bulkRetrieved_luiSets = luiService.getLuiSetsByIds( createdLuiSetIds, callContext );
        assertNotNull( bulkRetrieved_luiSets );
        assertTrue( bulkRetrieved_luiSets.size() == 3 );
        for( LuiSetInfo r : bulkRetrieved_luiSets ) {
            assertTrue( createdLuiSetIds.contains(r.getId()) );
        }

        // bulk operation -- get the list of lui-ids of a lui-set
        List<String> luiIds = new ArrayList<String>();
        luiIds.add( UUIDHelper.genStringUUID() );
        luiIds.add( UUIDHelper.genStringUUID() );
        luiSetInfo.setLuiIds( luiIds );
        created = luiService.createLuiSet( luiSetTypeKey, luiSetInfo, callContext );
        List<String> bulkRetrieved_luiIds = luiService.getLuiIdsFromLuiSet( created.getId(), callContext );
        assertNotNull(bulkRetrieved_luiIds);
        assertEquals(luiIds.size(), bulkRetrieved_luiIds.size());
        assertTrue( bulkRetrieved_luiIds.containsAll(luiIds) );

        // bulk operation -- get a list of lui-ids that contain a specific lui-id
        String targetLuiId = UUIDHelper.genStringUUID();
        List<String> targetLuiIdsList = new ArrayList<String>();
        targetLuiIdsList.add(targetLuiId);
        luiSetInfo.setLuiIds( targetLuiIdsList );
        // create a bunch of lui-sets containing references to that lui-id
        List<LuiSetInfo> bulkCreated_luiSets = new ArrayList<LuiSetInfo>();
        bulkCreated_luiSets.add( luiService.createLuiSet( luiSetTypeKey, luiSetInfo, callContext ) );
        bulkCreated_luiSets.add( luiService.createLuiSet( luiSetTypeKey, luiSetInfo, callContext ) );
        bulkCreated_luiSets.add( luiService.createLuiSet( luiSetTypeKey, luiSetInfo, callContext ) );
        bulkRetrieved_luiSets = luiService.getLuiSetsByLui( targetLuiId, callContext );
        assertNotNull( bulkRetrieved_luiSets );
        assertEquals( 3, bulkRetrieved_luiSets.size() );
        for( LuiSetInfo luiSet : bulkRetrieved_luiSets ) {
            assertTrue( luiSet.getLuiIds().contains( targetLuiId ) );
        }

        // bulk operation -- get a list of lui-set ids by lui-set type
        luiService.createLuiSet( "test.alternate.lui.set.type.key.test", luiSetInfo, callContext );
        bulkRetrieved_luiIds = luiService.getLuiSetIdsByType( luiSetTypeKey, callContext );
        assertEquals( 7, bulkRetrieved_luiIds.size() );
        for( String id : bulkRetrieved_luiIds ) {
            assertEquals( luiSetTypeKey, luiService.getLuiSet( id, callContext ).getTypeKey() );
        }
        assertEquals( 1, luiService.getLuiSetIdsByType( "test.alternate.lui.set.type.key.test", callContext ).size() );
    }

}
