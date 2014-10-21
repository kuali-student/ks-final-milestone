package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryByCriteria.Builder;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.ListOfStringTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.class2.acal.util.MockAcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.helper.CourseOfferingServiceScheduleHelper;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/*
 * This class was used to test the class1 backed implementation of CourseOfferingService for CourseOffering,
 * FormatOffering and ActivityOffering.
 *
 * For M4 it has been refactored.  Most of the test are now in TestCourseOfferingServiceMockImpl and only db
 * dependent tests go here.  soc-businesslogic-with-mocks-test-context.xml
 * See TestLprServiceImpl for an example.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImplM4 {
    
    private static final Logger log = LoggerFactory.getLogger(TestCourseOfferingServiceImplM4.class);
    
    @Resource
    protected CourseOfferingService coServiceImpl;
    @Resource
    protected PopulationService populationService;
    @Resource
    protected LuiService luiService;
    @Resource
    protected AcademicCalendarService acalService;
    @Resource
    protected TypeService typeService;
    @Resource
    protected StateService stateService;
    @Resource
    protected SchedulingService schedulingService;

    protected ContextInfo contextInfo;

    @Resource
    protected LuiServiceDataLoader dataLoader = new LuiServiceDataLoader();

    protected MockAcalTestDataLoader acalTestDataLoader;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setup() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);

        CourseOfferingServiceScheduleHelper.setSchedulingService(schedulingService);
        createStateTestData();
    }

    private void createStateTestData() throws Exception {

        // ActivityOffering states
        cleanupStateTestData( LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY );
        LifecycleInfo aoLifecycle = addLifecycle( LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY );
        addState( aoLifecycle, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, true );

        // FormatOffering states
        cleanupStateTestData( LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY );
        LifecycleInfo foLifecycle = addLifecycle( LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY );
        addState( foLifecycle, LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, true );

        // CourseOffering states
        cleanupStateTestData( LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY );
        LifecycleInfo coLifecycle = addLifecycle( LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY );
        addState( coLifecycle, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY, true );

        // RegistrationGroup states
        cleanupStateTestData( LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY );
        cleanupStateTestData( LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY );
        cleanupLifecycleTestData( LuiServiceConstants.REGISTRATION_GROUP_LIFECYCLE_KEY );
        LifecycleInfo rgLifecycle = addLifecycle( LuiServiceConstants.REGISTRATION_GROUP_LIFECYCLE_KEY );
        addState( rgLifecycle, LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY, true );
        addState( rgLifecycle, LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY, true );
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

    protected void before() throws Exception {
        if(contextInfo == null) {
            contextInfo = ContextUtils.createDefaultContextInfo();
            contextInfo.setPrincipalId("admin");
            contextInfo.setAuthenticatedPrincipalId("admin");

            acalTestDataLoader = new MockAcalTestDataLoader(acalService);

        }

        acalTestDataLoader.loadTerm("atpId5", "atpId5", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, "Desc 101");
        acalTestDataLoader.loadTerm("atpId8", "atpId8", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, "Desc 101");

        dataLoader.loadData();
        FormatOfferingInfo foInfo = coServiceImpl.getFormatOffering("Lui-6", contextInfo);
        if (foInfo.getActivityOfferingTypeKeys() == null |
                foInfo.getActivityOfferingTypeKeys().isEmpty()) {
            List<String> aoTypes = new ArrayList<String>();
            aoTypes.add(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
            aoTypes.add(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
            foInfo.setActivityOfferingTypeKeys(aoTypes);
            coServiceImpl.updateFormatOffering(foInfo.getId(), foInfo, contextInfo);
        }
    }

    private SeatPoolDefinitionInfo _constructSeatPoolDefinitionInfoById(Integer val) {
        String extension = "";
        if (val != null) {
            extension += val;
        }
        SeatPoolDefinitionInfo seatPoolDefinitionInfo = new SeatPoolDefinitionInfo();
        seatPoolDefinitionInfo.setName("TestSeatPoolDefinitionInfo-Id" + extension);
        seatPoolDefinitionInfo.setStateKey("TestSeatPoolDefinitionInfo-StateKey1" + extension);
        seatPoolDefinitionInfo.setTypeKey("TestSeatPoolDefinitionInfo-TypeKey1" + extension);
        seatPoolDefinitionInfo.setExpirationMilestoneTypeKey("TestSeatPoolDefinitionInfo-MilestoneKey1" + extension);
        seatPoolDefinitionInfo.setIsPercentage(false);
        seatPoolDefinitionInfo.setSeatLimit(50);
        seatPoolDefinitionInfo.setProcessingPriority(3);
        return seatPoolDefinitionInfo;
    }

    private PopulationInfo _constructPopulationInfo(Integer val) {
        String extension = "";
        if (val != null) {
            extension += val;
        }
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setName("TestPop" + extension);
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("plain" + extension);
        richTextInfo.setFormatted("formatted" + extension);
        populationInfo.setDescr(richTextInfo);
        populationInfo.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
        populationInfo.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
        return populationInfo;
    }

    private List<PopulationInfo> _constructPopulationList() {
        PopulationInfo ref = _constructPopulationInfo(2);
        PopulationInfo three = _constructPopulationInfo(3);
        PopulationInfo four = _constructPopulationInfo(4);
        PopulationInfo five = _constructPopulationInfo(5);
        List<PopulationInfo> popList = new ArrayList<PopulationInfo>();
        popList.add(ref);
        popList.add(three);
        popList.add(four);
        popList.add(five);
        return popList;
    }

    private PopulationRuleInfo _constructExclusionPopulationRuleInfo() {
        PopulationRuleInfo populationRuleInfo = new PopulationRuleInfo();
        populationRuleInfo.setName("TestPopRule");
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("rule-plain");
        richTextInfo.setFormatted("rule-formatted");
        populationRuleInfo.setDescr(richTextInfo);
        populationRuleInfo.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        populationRuleInfo.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY);
        return populationRuleInfo;
    }

    private RegistrationGroupInfo _constructRegistrationGroupInfoById(Integer val) {
        String extension = "";
        if (val != null) {
            extension += val;
        }
        RegistrationGroupInfo registrationGroupInfo = new RegistrationGroupInfo();
        registrationGroupInfo.setName("TestRegistrationGroupInfo-Id" + extension);
        registrationGroupInfo.setStateKey("TestRegistrationGroupInfo-StateKey1" + extension);
        registrationGroupInfo.setTypeKey("TestRegistrationGroupInfo-TypeKey1" + extension);
        registrationGroupInfo.setFormatOfferingId("Lui-6");
        registrationGroupInfo.setCourseOfferingId("Lui-1");
        registrationGroupInfo.setTermId("20122");
        registrationGroupInfo.setRegistrationCode("02" + extension);

        List<String> activityOfferingIds = new ArrayList<String>();
        activityOfferingIds.add("Lui-2");
        activityOfferingIds.add("Lui-5");

        registrationGroupInfo.setActivityOfferingIds(activityOfferingIds);
        registrationGroupInfo.setIsGenerated(true);
        registrationGroupInfo.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        registrationGroupInfo.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
        return registrationGroupInfo;
    }

    private RegistrationGroupInfo _constructRegistrationGroupInfo2() {
        String extension = "-foo";
        RegistrationGroupInfo registrationGroupInfo = new RegistrationGroupInfo();
        registrationGroupInfo.setName("TestRegistrationGroupInfo-Id" + extension);
        registrationGroupInfo.setStateKey("TestRegistrationGroupInfo-StateKey1" + extension);
        registrationGroupInfo.setTypeKey("TestRegistrationGroupInfo-TypeKey1" + extension);
        registrationGroupInfo.setFormatOfferingId("Lui-6");
        registrationGroupInfo.setCourseOfferingId("Lui-1");
        registrationGroupInfo.setTermId("20122");
        registrationGroupInfo.setRegistrationCode("02" + extension);

        List<String> activityOfferingIds = new ArrayList<String>();
        activityOfferingIds.add("Lui-2");
        activityOfferingIds.add("Lui-Lab2");

        registrationGroupInfo.setActivityOfferingIds(activityOfferingIds);
        registrationGroupInfo.setIsGenerated(true);
        registrationGroupInfo.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        registrationGroupInfo.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
        return registrationGroupInfo;
    }

    protected List<String> extractActivityOfferingIds(List<ActivityOfferingSetInfo> aoList) {
        List<String> idList = new ArrayList<String>();

        for (ActivityOfferingSetInfo activityOfferingSetInfo : aoList) {

            idList.addAll(activityOfferingSetInfo.getActivityOfferingIds());

        }
        return idList;
    }

    protected ActivityOfferingClusterInfo _createAOC() throws Exception {
        ActivityOfferingInfo activities[] = new ActivityOfferingInfo[]{
            coServiceImpl.getActivityOffering("Lui-5", contextInfo),
            coServiceImpl.getActivityOffering("Lui-Lab2", contextInfo),
            coServiceImpl.getActivityOffering("Lui-8", contextInfo)};

        return CourseOfferingServiceTestDataUtils.createActivityOfferingCluster("Lui-6", "Default Cluster",
                                                                                 Arrays.asList(activities));
    }

    // ============================================== TESTS ======================================================
    @Test
    public void testRegCodeGenerator() throws Exception {
        RegistrationGroupCodeGenerator generator = new FourDigitRegistrationGroupCodeGenerator();
        CourseOfferingService coService = new FakeCourseOfferingService();
        FormatOfferingInfo foInfo = coService.getFormatOffering("foo", null);
        generator.initializeGenerator(coService, foInfo, null, null);
        String prefix = "1";
        int suffixVal = 1;
        for (int i = 1; i <= 999; i++) {
            String code = generator.generateRegistrationGroupCode(foInfo, null, null);
            String suffix = "" + suffixVal;
            suffixVal++; // Increment to next suffix
            if (suffixVal % 100 == 0) {
                suffixVal++;  // Code generator skips over suffix codes that end in 00.
            }
            while (suffix.length() < 3) {
                suffix = "0" + suffix;
            }
            String expectedCode = prefix + suffix;
            assertEquals(expectedCode, code);
            if (suffixVal > 999) {
                break;
            }
        }
        // Now see if it throws an exception
        try {
            generator.generateRegistrationGroupCode(foInfo, null, null);
            fail("RuntimeException should have been thrown");
        } catch (RuntimeException ex) {
            assertNotNull(ex.getMessage());
            assertEquals("No more reg codes left to use", ex.getMessage());
        }
    }

    @Test
    public void testGetAndRemoveRegistrationGroupsByFormatOffering() throws Exception {
        before();

        RegistrationGroupInfo info = _constructRegistrationGroupInfoById(null);
        RegistrationGroupInfo info2 = _constructRegistrationGroupInfo2();

        String foId = "Lui-6";
        String aocId = "Aoc-1";
        coServiceImpl.createRegistrationGroup(foId, aocId, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, info, contextInfo);
        coServiceImpl.createRegistrationGroup(foId, aocId, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, info2, contextInfo);

        List<RegistrationGroupInfo> rgInfos = coServiceImpl.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertEquals(2, rgInfos.size());
        for (RegistrationGroupInfo rgInfo : rgInfos) {
            List<String> aoIds = rgInfo.getActivityOfferingIds();
            for (String aoId : aoIds) {
                // I would prefer to get AO via the coService, but the Lui Loader only handles LUIs
                LuiInfo luiInfo = luiService.getLui(aoId, contextInfo);
                assertNotNull(luiInfo); // Should be trivially true
            }
        }
        // Now remove the reg groups
        coServiceImpl.deleteRegistrationGroupsByFormatOffering(foId, contextInfo);
        List<RegistrationGroupInfo> rgInfos2 = coServiceImpl.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertTrue(rgInfos2.isEmpty());
        for (RegistrationGroupInfo rgInfo : rgInfos) {
            try {
                coServiceImpl.getRegistrationGroup(rgInfo.getId(), contextInfo);
                fail("DoesNotExistException should have been thrown as we should not be able to find the old registration groups");
            } catch (DoesNotExistException dnee) {
                assertNotNull(dnee.getMessage());
                assertEquals(rgInfo.getId(), dnee.getMessage());
            }
        }
    }

    @Test
    public void testCreateUpdateRegistrationGroupInfoGet() throws Exception {
        before();

        RegistrationGroupInfo info = _constructRegistrationGroupInfoById(null);

        RegistrationGroupInfo created = coServiceImpl.createRegistrationGroup("Lui-6", "Aoc-1", LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, info, contextInfo);
        RegistrationGroupInfo fetched = coServiceImpl.getRegistrationGroup(created.getId(), contextInfo);
        Assert.assertEquals(created.getName(), fetched.getName());
        Assert.assertEquals(created.getStateKey(), fetched.getStateKey());
        Assert.assertEquals(created.getTypeKey(), fetched.getTypeKey());
        Assert.assertEquals(created.getFormatOfferingId(), fetched.getFormatOfferingId());
        Assert.assertEquals(created.getRegistrationCode(), fetched.getRegistrationCode());
        Assert.assertEquals(created.getCourseOfferingId(), fetched.getCourseOfferingId());
        Assert.assertEquals(created.getId(), fetched.getId());

        List<String> activityOfferingIds = new ArrayList<String>();
        activityOfferingIds.add("Lui-2");
        activityOfferingIds.add("Lui-Lab2");
        fetched.setActivityOfferingIds(null);
        fetched.setActivityOfferingIds(activityOfferingIds);
        fetched.setFormatOfferingId(null);
        fetched.setFormatOfferingId("Lui-7");
        RegistrationGroupInfo updated = coServiceImpl.updateRegistrationGroup(fetched.getId(), fetched, contextInfo);

        coServiceImpl.deleteRegistrationGroup(updated.getId(), contextInfo);

        try {
            coServiceImpl.getRegistrationGroup(updated.getId(), contextInfo);
            fail("DoesNotExistException should have been thrown since the reg group was deleted");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(updated.getId(), dnee.getMessage());
        }
    }

    @Test
    public void testPopulation() throws Exception {
        before();
        List<PopulationInfo> popList = _constructPopulationList();

        PopulationInfo refCreated = populationService.createPopulation(popList.get(0).getTypeKey(), popList.get(0), contextInfo);
        PopulationInfo threeCreated = populationService.createPopulation(popList.get(1).getTypeKey(), popList.get(1), contextInfo);
        PopulationInfo fourCreated = populationService.createPopulation(popList.get(2).getTypeKey(), popList.get(2), contextInfo);
        // Now the pop rule
        PopulationRuleInfo ruleInfo = _constructExclusionPopulationRuleInfo();
        ruleInfo.setReferencePopulationId(refCreated.getId());
        List<String> childIds = new ArrayList<String>();
        childIds.add(threeCreated.getId());
        childIds.add(fourCreated.getId());
        ruleInfo.setChildPopulationIds(childIds);
        // Create the rule info
        PopulationRuleInfo ruleInfoCreated = populationService.createPopulationRule(ruleInfo.getTypeKey(), ruleInfo, contextInfo);
        // Fetch it
        PopulationRuleInfo ruleInfoFetched = populationService.getPopulationRule(ruleInfoCreated.getId(), contextInfo);
        PopulationInfo combined = populationService.createPopulation(popList.get(3).getTypeKey(), popList.get(3), contextInfo);
        populationService.applyPopulationRuleToPopulation(ruleInfoFetched.getId(), combined.getId(), contextInfo);
        SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);
        info.setPopulationId(combined.getId());
        SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
        PopulationInfo retrieved = populationService.getPopulation(created.getPopulationId(), contextInfo);
        assertEquals(combined.getId(), retrieved.getId());
    }

    @Test
    public void testCreateSeatPoolDefinitionGet() throws Exception {
        before();
        SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);

        SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
        SeatPoolDefinitionInfo fetched = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
        Assert.assertEquals(info.getName(), fetched.getName());
        Assert.assertEquals(info.getStateKey(), fetched.getStateKey());
        Assert.assertEquals(info.getTypeKey(), fetched.getTypeKey());
        Assert.assertEquals(info.getExpirationMilestoneTypeKey(), fetched.getExpirationMilestoneTypeKey());
        Assert.assertEquals(info.getIsPercentage(), fetched.getIsPercentage());
        Assert.assertEquals(info.getSeatLimit(), fetched.getSeatLimit());
        Assert.assertEquals(info.getProcessingPriority(), fetched.getProcessingPriority());
    }

    @Test
    public void testCreateSeatPoolDefinitionUpdateDelete() throws Exception {
        before();
        SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);

        SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
        SeatPoolDefinitionInfo fetched = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
        fetched.setSeatLimit(5);
        fetched.setExpirationMilestoneTypeKey(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY);
        coServiceImpl.updateSeatPoolDefinition(fetched.getId(), fetched, contextInfo);
        SeatPoolDefinitionInfo fetched2 = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
        assertEquals(new Integer(5), fetched2.getSeatLimit());
        assertEquals(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY, fetched2.getExpirationMilestoneTypeKey());
        coServiceImpl.deleteSeatPoolDefinition(fetched.getId(), contextInfo);
        try {
            coServiceImpl.getSeatPoolDefinition(fetched.getId(), contextInfo);
            fail("DoesNotExistException should have been thrown");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(fetched.getId(), dnee.getMessage());
        }
    }

    @Test
    public void testGenerateRegistrationGroupsSimple() throws Exception,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {

        before();
        //create AOC
        coServiceImpl.createActivityOfferingCluster("Lui-6", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, _createAOC(), contextInfo);

        //generate RG
        List<BulkStatusInfo> status = coServiceImpl.generateRegistrationGroupsForFormatOffering("Lui-6", contextInfo);
        assertNotNull(status);
        Assert.assertEquals(2, status.size());

        //test RG generation was successful
        List<RegistrationGroupInfo> rgList = coServiceImpl.getRegistrationGroupsByFormatOffering("Lui-6", contextInfo);
        Assert.assertEquals(2, rgList.size());
    }

    @Test
    public void testCreateAOCSimple() throws Exception {
        before();
        ActivityOfferingClusterInfo aocInfo = new ActivityOfferingClusterInfo();
        FormatOfferingInfo foInfo = coServiceImpl.getFormatOffering("Lui-6", contextInfo);
        // Add some AO type keys to the FO (they don't appear there)
        List<String> aoTypeKeys = new ArrayList<String>();
        aoTypeKeys.add(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        aoTypeKeys.add(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
        foInfo.setActivityOfferingTypeKeys(aoTypeKeys);
        coServiceImpl.updateFormatOffering(foInfo.getId(), foInfo, contextInfo);

        aocInfo.setFormatOfferingId(foInfo.getId());
        aocInfo.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        aocInfo.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        ActivityOfferingClusterInfo created =
                coServiceImpl.createActivityOfferingCluster(foInfo.getId(), aocInfo.getTypeKey(), aocInfo, contextInfo);
        assertNotNull(created.getActivityOfferingSets());
    }

    @Test
    public void testActivityOfferingClusterCRUDsPlus() throws Exception {

        before();

        ActivityOfferingClusterInfo expected = _createAOC();
        new AttributeTester().add2ForCreate(expected.getAttributes());

        //test createActivityOfferingCluster
        ActivityOfferingClusterInfo actual = coServiceImpl.createActivityOfferingCluster("Lui-6", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, expected, contextInfo);
        assertNotNull(actual.getId());
        
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        //test  CRUD ActivityOfferingCluster
        //test createActivityOfferingCluster
        ActivityOfferingClusterInfo copy = coServiceImpl.createActivityOfferingCluster("Lui-6", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, expected, contextInfo);
        assertEquals(copy.getName(), "Default Cluster");
        copy.setName("Updated");
        //test getActivityOfferingCluster
        coServiceImpl.updateActivityOfferingCluster("Lui-6", copy.getId(), copy, contextInfo) ;
        //test updateActivityOfferingCluster
        copy = coServiceImpl.getActivityOfferingCluster(copy.getId(), contextInfo);
        assertEquals(copy.getName(), "Updated");
        //test deleteActivityOfferingCluster
        coServiceImpl.deleteActivityOfferingCluster(copy.getId(), contextInfo);
        boolean found = true;
        try {
            coServiceImpl.getActivityOfferingCluster(copy.getId(), contextInfo);
            fail("DoesNotExistException should have been thrown");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(copy.getId(), dnee.getMessage());
        }

        // check that the union of activity id's matches what we declared
        new ListOfStringTester().checkExistsAnyOrder(Arrays.asList("Lui-5", "Lui-Lab2", "Lui-8"),
                extractActivityOfferingIds(actual.getActivityOfferingSets()), true);

        //test getRegistrationGroupsByActivityOfferingCluster
        List<RegistrationGroupInfo> rgList = coServiceImpl.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), contextInfo);
        assertTrue(rgList.isEmpty());

        //test generateRegistrationGroupsForCluster and deleteRegistrationGroupsForCluster
        coServiceImpl.generateRegistrationGroupsForCluster(actual.getId(), contextInfo);

        rgList = coServiceImpl.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), contextInfo);
        assertEquals(2, rgList.size());

        //test deleteRegistrationGroupsForCluster within generateRegistrationGroupsForCluster
        coServiceImpl.generateRegistrationGroupsForCluster(actual.getId(), contextInfo);

        // verify count stays the same even after calling the method again.
        rgList = coServiceImpl.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), contextInfo);
        assertEquals(2, rgList.size());
    }
    
    // shows the problems that need to be resolved in https://jira.kuali.org/browse/KSENROLL-6479
    // once fixed this test should work ok.
    // the other search for methods should also be exercised but this one shows the two know problem cases right now.
    @Test
    @Ignore
    public void testSearchForMethods () throws Exception {
        
        before();
        
        Builder builder = QueryByCriteria.Builder.create();
        
        // this shows the filtering that is wrong we should just get 2 co's for both but the second one returns all lui.id's
        
        // want to set typeKey since that is what is on the dto but need to specify luiType which is the name of the field in the luiEntity
        builder.setPredicates(PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY));
        
        List<String> expectedCOIds = coServiceImpl.searchForCourseOfferingIds(builder.build(), callContext);
        
        log.info (String.format("%d expected", expectedCOIds.size()));
        
        builder = QueryByCriteria.Builder.create();
        
        List<String> actualCOIds = coServiceImpl.searchForCourseOfferingIds(builder.build(), callContext);
        
        log.info (String.format("%d actual", actualCOIds.size()));
        
        assertEquals(expectedCOIds.size(), actualCOIds.size());
        
        assertTrue (CollectionUtils.isEqualCollection(expectedCOIds, actualCOIds));
        
        // this shows the paging problem
        // we should get back 2 co's
        // but only 1 co in the first 2 lui's
        
        builder = QueryByCriteria.Builder.create();
        
        // this shows the filtering that is wrong we should just get 2 co's for both but the second one returns all lui.id's
        
        // want to set typeKey since that is what is on the dto but need to specify luiType which is the name of the field in the luiEntity
        builder.setPredicates(PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY));
        
        builder.setMaxResults(2);
        builder.setStartAtIndex(0);
        
        List<CourseOfferingInfo> expectedCOs = coServiceImpl.searchForCourseOfferings(builder.build(), callContext);
        
        log.info (String.format("%d expected", expectedCOs.size()));
        
        builder = QueryByCriteria.Builder.create();
        
        builder.setMaxResults(2);
        builder.setStartAtIndex(0);
        
        List<CourseOfferingInfo> actualCOs = coServiceImpl.searchForCourseOfferings(builder.build(), callContext);
        
        log.info (String.format("%d actual", actualCOs.size()));
        
        assertEquals(expectedCOs.size(), actualCOs.size());
        
        assertTrue (CollectionUtils.isEqualCollection(expectedCOs, actualCOs));
        
    }

}
