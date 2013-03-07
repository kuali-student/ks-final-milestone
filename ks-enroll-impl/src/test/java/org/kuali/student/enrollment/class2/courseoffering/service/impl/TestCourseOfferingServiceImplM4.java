package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.ListOfStringTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.class2.acal.util.MockAcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
public class TestCourseOfferingServiceImplM4 {
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
    
    protected ContextInfo contextInfo;

    @Resource
    protected LuiServiceDataLoader dataLoader = new LuiServiceDataLoader();

    protected MockAcalTestDataLoader acalTestDataLoader;

    protected void before() {
        if(contextInfo == null) {
            contextInfo = ContextUtils.createDefaultContextInfo();
            contextInfo.setPrincipalId("admin");
            contextInfo.setAuthenticatedPrincipalId("admin");

            acalTestDataLoader = new MockAcalTestDataLoader(acalService);

        }
        try {

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
//            System.out.println(updated.getStateKey());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
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

    protected ActivityOfferingClusterInfo _createAOC() {
        ActivityOfferingClusterInfo expected;
        try {
            ActivityOfferingInfo activities[] = new ActivityOfferingInfo[]{
                coServiceImpl.getActivityOffering("Lui-5", contextInfo),
                coServiceImpl.getActivityOffering("Lui-Lab2", contextInfo),
                coServiceImpl.getActivityOffering("Lui-8", contextInfo)};

            expected = CourseOfferingServiceTestDataUtils.createActivityOfferingCluster("Lui-6", "Default Cluster",
                                                                                     Arrays.asList(activities));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return expected;
    }

    // ============================================== TESTS ======================================================
    @Test
    public void testRegCodeGenerator() {
        RegistrationGroupCodeGenerator generator = new FourDigitRegistrationGroupCodeGenerator();
        CourseOfferingService coService = new FakeCourseOfferingService();
        try {
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
            boolean codeGenerated = true;
            try {
                generator.generateRegistrationGroupCode(foInfo, null, null);
            } catch (RuntimeException e) {
                codeGenerated = false;
            }
            assertFalse(codeGenerated);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void testGetAndRemoveRegistrationGroupsByFormatOffering() {
        before();

        RegistrationGroupInfo info = _constructRegistrationGroupInfoById(null);
        RegistrationGroupInfo info2 = _constructRegistrationGroupInfo2();
        try {
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
            assertEquals(0, rgInfos2.size());
            for (RegistrationGroupInfo rgInfo : rgInfos) {
                boolean found = true;
                try {
                    // Should not be able to find the old registration groups
                    coServiceImpl.getRegistrationGroup(rgInfo.getId(), contextInfo);
                } catch (DoesNotExistException e) { // Should use DoesNot
                    found = false;
                }
                if (found) {
                    assert (false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testCreateUpdateRegistrationGroupInfoGet() {
        before();

        RegistrationGroupInfo info = _constructRegistrationGroupInfoById(null);
        try {
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
                //This should throw an exception since the reg group was deleted
                assert (false);
            } catch (DoesNotExistException e) {
                assert (true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testPopulation() {
        before();
        List<PopulationInfo> popList = _constructPopulationList();
        try {
            PopulationInfo refCreated = populationService.createPopulation(popList.get(0), contextInfo);
            PopulationInfo threeCreated = populationService.createPopulation(popList.get(1), contextInfo);
            PopulationInfo fourCreated = populationService.createPopulation(popList.get(2), contextInfo);
            // Now the pop rule
            PopulationRuleInfo ruleInfo = _constructExclusionPopulationRuleInfo();
            ruleInfo.setReferencePopulationId(refCreated.getId());
            List<String> childIds = new ArrayList<String>();
            childIds.add(threeCreated.getId());
            childIds.add(fourCreated.getId());
            ruleInfo.setChildPopulationIds(childIds);
            // Create the rule info
            PopulationRuleInfo ruleInfoCreated = populationService.createPopulationRule(ruleInfo, contextInfo);
            // Fetch it
            PopulationRuleInfo ruleInfoFetched = populationService.getPopulationRule(ruleInfoCreated.getId(), contextInfo);
            PopulationInfo combined = populationService.createPopulation(popList.get(3), contextInfo);
            populationService.applyPopulationRuleToPopulation(ruleInfoFetched.getId(), combined.getId(), contextInfo);
            SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);
            info.setPopulationId(combined.getId());
            SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
            PopulationInfo retrieved = populationService.getPopulation(created.getPopulationId(), contextInfo);
            assertEquals(combined.getId(), retrieved.getId());
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testCreateSeatPoolDefinitionGet() {
        before();
        SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);
        try {
            SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
            SeatPoolDefinitionInfo fetched = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
            Assert.assertEquals(info.getName(), fetched.getName());
            Assert.assertEquals(info.getStateKey(), fetched.getStateKey());
            Assert.assertEquals(info.getTypeKey(), fetched.getTypeKey());
            Assert.assertEquals(info.getExpirationMilestoneTypeKey(), fetched.getExpirationMilestoneTypeKey());
            Assert.assertEquals(info.getIsPercentage(), fetched.getIsPercentage());
            Assert.assertEquals(info.getSeatLimit(), fetched.getSeatLimit());
            Assert.assertEquals(info.getProcessingPriority(), fetched.getProcessingPriority());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateSeatPoolDefinitionUpdateDelete() {
        before();
        SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);
        try {
            SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
            SeatPoolDefinitionInfo fetched = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
            fetched.setSeatLimit(5);
            fetched.setExpirationMilestoneTypeKey(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY);
            coServiceImpl.updateSeatPoolDefinition(fetched.getId(), fetched, contextInfo);
            SeatPoolDefinitionInfo fetched2 = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
            assertEquals(new Integer(5), fetched2.getSeatLimit());
            assertEquals(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY, fetched2.getExpirationMilestoneTypeKey());
            coServiceImpl.deleteSeatPoolDefinition(fetched.getId(), contextInfo);
            boolean found = true;
            try {
                coServiceImpl.getSeatPoolDefinition(fetched.getId(), contextInfo);
            } catch (DoesNotExistException e) {
                found = false;
            }
            if (found) {
                assert (false); // Exception should have been thrown
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void testGenerateRegistrationGroupsSimple() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {

        before();
        try {
            //create AOC
            coServiceImpl.createActivityOfferingCluster("Lui-6", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, _createAOC(), contextInfo);

            //generate RG
            List<BulkStatusInfo> status = coServiceImpl.generateRegistrationGroupsForFormatOffering("Lui-6", contextInfo);
            assertNotNull(status);
            Assert.assertEquals(2, status.size()); 

            //test RG generation was successful
            List<RegistrationGroupInfo> rgList = coServiceImpl.getRegistrationGroupsByFormatOffering("Lui-6", contextInfo);
            Assert.assertEquals(2, rgList.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateAOCSimple() {
        before();
        try {
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
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void testActivityOfferingClusterCRUDsPlus() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        before();

        ActivityOfferingClusterInfo expected = _createAOC();
        new AttributeTester().add2ForCreate(expected.getAttributes());

        //test createActivityOfferingCluster
        ActivityOfferingClusterInfo actual = coServiceImpl.createActivityOfferingCluster("Lui-6", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, expected, contextInfo);
        assertNotNull(actual.getId());
        
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        //test  CRUD ActivityOfferingCluster
        try {
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
            } catch (DoesNotExistException e) {
                found = false;
            }
            if (found) {
                assert (false); // Exception should have been thrown
            }
        } catch (Exception ex) {
            throw new RuntimeException("update failed - " + ex);
        }

        // check that the union of activity id's matches what we declared
        new ListOfStringTester().checkExistsAnyOrder(Arrays.asList("Lui-5", "Lui-Lab2", "Lui-8"),
                extractActivityOfferingIds(actual.getActivityOfferingSets()), true);

        //test getRegistrationGroupsByActivityOfferingCluster
        List<RegistrationGroupInfo> rgList = coServiceImpl.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), contextInfo);
        assertEquals(0, rgList.size());

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

}
