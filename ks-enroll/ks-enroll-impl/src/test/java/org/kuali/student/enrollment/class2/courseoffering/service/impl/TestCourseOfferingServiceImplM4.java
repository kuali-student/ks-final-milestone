package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import junit.framework.Assert;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingAdminDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingAdminDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


/*
 * This class was used to test the class1 backed implementation of CourseOfferingService for CourseOffering, FormatOffering and ActivityOffering.
 *
 * For M4 it has been refactored.  Most of the test are now in TestCourseOfferingServiceMockImpl and only db dependent tests go here.
 *
 * See TestLprServiceImpl for an example.
 *
 * Once the tests can be run this should be unignored.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
//@ContextConfiguration(locations = {"co-test-with-class2-mock-context.xml"})

@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImplM4 {
    @Resource
    private CourseOfferingService coServiceImpl;
    @Resource
    private PopulationService populationService;
    @Resource
    private LuiService luiService;

    //private SeatPoolDefinitionDao seatPoolDefinitionDao;
    private ContextInfo contextInfo;

    @Resource
    protected LuiServiceDataLoader dataLoader = new LuiServiceDataLoader();

    private void before()  {
        contextInfo = ContextUtils.createDefaultContextInfo();
        contextInfo.setPrincipalId("admin");
        contextInfo.setAuthenticatedPrincipalId("admin");
        try {
            dataLoader.loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private SeatPoolDefinitionInfo _constructSeatPoolDefinitionInfoById (Integer val) {
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

    private List<SeatPoolDefinitionInfo> _constructSeatPoolDefinitionInfoByIdList() {
        SeatPoolDefinitionInfo ref = _constructSeatPoolDefinitionInfoById(2);
        SeatPoolDefinitionInfo three = _constructSeatPoolDefinitionInfoById(3);
        SeatPoolDefinitionInfo four = _constructSeatPoolDefinitionInfoById(4);
        SeatPoolDefinitionInfo five = _constructSeatPoolDefinitionInfoById(5);
        List<SeatPoolDefinitionInfo> poolList = new ArrayList<SeatPoolDefinitionInfo>();
        poolList.add(ref);
        poolList.add(three);
        poolList.add(four);
        poolList.add(five);
        return poolList;
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

    private RegistrationGroupInfo _constructRegistrationGroupInfoById (Integer val) {
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
        registrationGroupInfo.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);
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
        registrationGroupInfo.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);
        return registrationGroupInfo;
    }

    private List<RegistrationGroupInfo> _constructRegistrationGroupInfoByIdList() {
        RegistrationGroupInfo ref = _constructRegistrationGroupInfoById(2);
        RegistrationGroupInfo three = _constructRegistrationGroupInfoById(3);
        RegistrationGroupInfo four = _constructRegistrationGroupInfoById(4);
        RegistrationGroupInfo five = _constructRegistrationGroupInfoById(5);
        List<RegistrationGroupInfo> rgList = new ArrayList<RegistrationGroupInfo>();
        rgList.add(ref);
        rgList.add(three);
        rgList.add(four);
        rgList.add(five);
        return rgList;
    }

    // ============================================== TESTS ======================================================
    @Test
    public void testRegCodeGenerator() {
        RegistrationGroupCodeGenerator generator = new FourDigitRegistrationGroupCodeGenerator();
        CourseOfferingService coService = new FakeCOService();
        try {
            FormatOfferingInfo foInfo = coService.getFormatOffering("foo", null);
            generator.initializeGenerator(coService, foInfo, null, null);
            String prefix = "02";
            for (int i = 1; i <= 99; i++) {
                String code = generator.generateRegistrationGroupCode(foInfo, null, null);
                String answer = prefix;
                if (i < 10) {
                    answer += "0";
                }
                answer += i;
                assertEquals(answer, code);
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
            assert(false);
        }
    }
    @Test
    public void testGetAndRemoveRegistrationGroupsByFormatOffering() {
        before();

        RegistrationGroupInfo info = _constructRegistrationGroupInfoById(null);
        RegistrationGroupInfo info2 = _constructRegistrationGroupInfo2();
        try {
            String foId = "Lui-6";
            RegistrationGroupInfo created = coServiceImpl.createRegistrationGroup(foId, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, info, contextInfo);
            RegistrationGroupInfo created2 = coServiceImpl.createRegistrationGroup(foId, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, info2, contextInfo);

            List<RegistrationGroupInfo> rgInfos = coServiceImpl.getRegistrationGroupsByFormatOffering(foId, contextInfo);
            assertEquals(2, rgInfos.size());
            for (RegistrationGroupInfo rgInfo: rgInfos) {
                List<String> aoIds = rgInfo.getActivityOfferingIds();
                for (String aoId: aoIds) {
                    // I would prefer to get AO via the coService, but the Lui Loader only handles LUIs
                    LuiInfo luiInfo = luiService.getLui(aoId, contextInfo);
                    assertNotNull(luiInfo); // Should be trivially true
                }
            }
            // Now remove the reg groups
            coServiceImpl.deleteRegistrationGroupsByFormatOffering(foId, contextInfo);
            List<RegistrationGroupInfo> rgInfos2 = coServiceImpl.getRegistrationGroupsByFormatOffering(foId, contextInfo);
            assertEquals(0, rgInfos2.size());
            for (RegistrationGroupInfo rgInfo: rgInfos) {
                boolean found = true;
                try {
                    // Should not be able to find the old registration groups
                    coServiceImpl.getRegistrationGroup(rgInfo.getId(), contextInfo);
                } catch (DoesNotExistException e) { // Should use DoesNot
                    found = false;
                }
                if (found) {
                    assert(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }
    @Test
    public void testCreateUpdateRegistrationGroupInfoGet() {
        before();

        RegistrationGroupInfo info = _constructRegistrationGroupInfoById(null);
        try {
            RegistrationGroupInfo created = coServiceImpl.createRegistrationGroup("Lui-6", LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, info, contextInfo);
            RegistrationGroupInfo fetched = coServiceImpl.getRegistrationGroup(created.getId(), contextInfo);
            Assert.assertEquals(created.getName(), fetched.getName());
            Assert.assertEquals(created.getStateKey(), fetched.getStateKey());
            Assert.assertEquals(created.getTypeKey(), fetched.getTypeKey());
            Assert.assertEquals(created.getFormatOfferingId(), fetched.getFormatOfferingId());
            Assert.assertEquals(created.getRegistrationCode(), fetched.getRegistrationCode());
            Assert.assertEquals(created.getCourseOfferingId(), fetched.getCourseOfferingId());
            Assert.assertEquals(created.getId(), fetched.getId());

            List<LuiLuiRelationInfo> llrs = luiService.getLuiLuiRelationsByLui(fetched.getId(), contextInfo);

            List<String> activityOfferingIds = new ArrayList<String>();
            activityOfferingIds.add("Lui-2");
            activityOfferingIds.add("Lui-Lab2");
            fetched.setActivityOfferingIds(null);
            fetched.setActivityOfferingIds(activityOfferingIds);
            fetched.setFormatOfferingId(null);
            fetched.setFormatOfferingId("Lui-7");
            RegistrationGroupInfo updated = coServiceImpl.updateRegistrationGroup(fetched.getId(), fetched, contextInfo);

            List<LuiLuiRelationInfo> llrs1 = luiService.getLuiLuiRelationsByLui(updated.getId(), contextInfo);
            coServiceImpl.deleteRegistrationGroup(updated.getId(), contextInfo);

            List<LuiLuiRelationInfo> llrsAfter = luiService.getLuiLuiRelationsByLui(updated.getId(), contextInfo);
            try{
                RegistrationGroupInfo fetchedAfterDelete = coServiceImpl.getRegistrationGroup(updated.getId(), contextInfo);
                //This should throw an exception since the reg group was deleted
                assert(false);
            }catch(DoesNotExistException e){
                assert(true);
            }
            System.out.println("here");


        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
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
            assert(false);
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
            assert(false);
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
                assert(false); // Exception should have been thrown
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testGenerateRegistrationGroupsSimple() throws DoesNotExistException,
       			InvalidParameterException, MissingParameterException,
       			OperationFailedException, PermissionDeniedException, AlreadyExistsException {

        before();
        try {
            List<RegistrationGroupInfo> rgList = coServiceImpl.generateRegistrationGroupsForFormatOffering("Lui-6", contextInfo);
            Assert.assertEquals(1, rgList.size());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

}

/**
 * To test UMDRegGroupCodeGenerator
 */
class FakeCOService implements CourseOfferingService {
    Map<String, List<RegistrationGroupInfo>> foIdsToRegGroups = new HashMap<String, List<RegistrationGroupInfo>>();
    CourseOfferingInfo coInfo;
    List<FormatOfferingInfo> foInfoList = new ArrayList<FormatOfferingInfo>();

    private static RegistrationGroupInfo createRegGroup(String regGroupCode) {
        RegistrationGroupInfo info = new RegistrationGroupInfo();
        info.setName(regGroupCode);
        return info;
    }

    public FakeCOService() {
        // Create a co
        String coId = "CO";
        String foId1 = "fo0100", foId2 = "fo0300", foId3 = "newFo";
        coInfo = new CourseOfferingInfo();
        coInfo.setId(coId);
        // Create two fos
        FormatOfferingInfo fo1 = new FormatOfferingInfo();
        fo1.setCourseOfferingId(coId);
        fo1.setId(foId1);
        FormatOfferingInfo fo2 = new FormatOfferingInfo();
        fo2.setCourseOfferingId(coId);
        fo2.setId(foId2);
        FormatOfferingInfo fo3 = new FormatOfferingInfo();
        fo3.setCourseOfferingId(coId);
        fo3.setId(foId3);
        foInfoList.add(fo1);
        foInfoList.add(fo2);
        foInfoList.add(fo3);

        List<RegistrationGroupInfo> rg0100 = new ArrayList<RegistrationGroupInfo>();
        List<RegistrationGroupInfo> rg0300 = new ArrayList<RegistrationGroupInfo>();
        foIdsToRegGroups.put(foId1, rg0100);
        foIdsToRegGroups.put(foId2, rg0300);

        // Create a 01-- reg group
        rg0100.add(createRegGroup("0101"));
        rg0100.add(createRegGroup("0102"));
        rg0100.add(createRegGroup("0103"));

        // Create a 03-- reg group
        rg0300.add(createRegGroup("0301"));
        rg0300.add(createRegGroup("0302"));
    }

    @Override
    public CourseOfferingAdminDisplayInfo getCourseOfferingAdminDisplay(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseOfferingAdminDisplayInfo> getCourseOfferingAdminDisplaysByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingAdminDisplayInfo getActivityOfferingAdminDisplay(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getCourseOfferingType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getCourseOfferingTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getInstructorTypesForCourseOfferingType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CourseOfferingInfo getCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return coInfo;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getCourseOfferingIdsByTerm(@WebParam(name = "termId") String termId, @WebParam(name = "useIncludedTerm") Boolean useIncludedTerm, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(@WebParam(name = "termId") String termId, @WebParam(name = "subjectArea") String subjectArea, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(@WebParam(name = "termId") String termId, @WebParam(name = "instructorId") String instructorId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(@WebParam(name = "termId") String termId, @WebParam(name = "unitsContentOwnerId") String unitsContentOwnerId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getCourseOfferingIdsByType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getValidCanonicalCourseToCourseOfferingOptionKeys(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getValidRolloverOptionKeys(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CourseOfferingInfo createCourseOffering(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CourseOfferingInfo rolloverCourseOffering(@WebParam(name = "sourceCourseOfferingId") String sourceCourseOfferingId, @WebParam(name = "targetTermId") String targetTermId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteCourseOfferingCascaded(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FormatOfferingInfo getFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return foInfoList.get(2);
    }

    @Override
    public List<FormatOfferingInfo> getFormatOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return foInfoList;
    }

    @Override
    public FormatOfferingInfo createFormatOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "formatId") String formatId, @WebParam(name = "formatOfferingType") String formatOfferingType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteFormatOfferingCascaded(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(@WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getInstructorTypesForActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsWithoutClusterByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityId") String activityId, @WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferings(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingType") String activityOfferingType, @WebParam(name = "quantity") Integer quantity, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteActivityOfferingCascaded(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Float calculateInClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Float calculateTotalContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(@WebParam(name = "registrationGroupIds") List<String> registrationGroupIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (foIdsToRegGroups.containsKey(formatOfferingId)) {
            return foIdsToRegGroups.get(formatOfferingId);
        } else {
            return new ArrayList<RegistrationGroupInfo>();
        }
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "registrationGroupType") String registrationGroupType, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteGeneratedRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteRegistrationGroupsForCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingClusterInfo getActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingCluster(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterTypeKey") String activityOfferingClusterTypeKey, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingClusterInfo createActivityOfferingCluster(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterTypeKey") String activityOfferingClusterTypeKey, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> verifyActivityOfferingClusterForGeneration(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo addSeatPoolDefinitionToActivityOffering(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo removeSeatPoolDefinitionFromActivityOffering(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForCourseOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForActivityOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForRegistrationGroupIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForSeatpoolDefinitionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}