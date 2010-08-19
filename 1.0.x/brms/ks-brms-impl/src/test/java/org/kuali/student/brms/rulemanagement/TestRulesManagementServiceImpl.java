/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.rulemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.internal.common.entity.AgendaType;
import org.kuali.student.brms.internal.common.entity.AnchorTypeKey;
import org.kuali.student.brms.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.brms.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.brms.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfo;
import org.kuali.student.brms.rulemanagement.dto.AgendaInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;
import org.kuali.student.brms.rulemanagement.service.RuleManagementService;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;

@Daos({@Dao(value = "org.kuali.student.brms.rulemanagement.dao.impl.RuleManagementDAOImpl", testDataFile = "classpath:test-beans.xml")})
@PersistenceFileLocation("classpath:META-INF/rulemanagement-persistence.xml")
public class TestRulesManagementServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.brms.rulemanagement.service.impl.RuleManagementServiceImpl", port = "8181", additionalContextFile="classpath:rulemanagement-mock-service-context.xml")
    public RuleManagementService client;
    
    public static final String ruleId_1 = "11223344-1122-1122-1112-100000000001";
    public static final String ruleId_2 = "11223344-1122-1122-1112-100000000011";    
    private static final String ruleId_3 = "11223344-1122-1122-1112-100000000032";
    
    private static final String create_rule_name = "Test rule create";
    
    @Test
    public void testFindAgendaTypes() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> agendaTypes = client.getAgendaTypes();
        assertTrue(agendaTypes.size() == 3);
        assertTrue(agendaTypes.contains(AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSE.toString()));
        assertTrue(agendaTypes.contains(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE.toString()));
        assertTrue(agendaTypes.contains(AgendaType.KUALI_VALIDATE_LUI_PERSON_RELATION.toString()));
    }

    @Test
    public void testFindAnchorTypes() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<String> anchorTypes = client.getAnchorTypes();

        assertTrue(anchorTypes.size() == 1);
        assertTrue(anchorTypes.contains(AnchorTypeKey.KUALI_COURSE.toString()));
    }

    @Test
    public void testFindAnchorsByAnchorType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<String> anchors = client.getAnchorsByAnchorType(AnchorTypeKey.KUALI_COURSE.toString());

        assertTrue(anchors.size() == 3);
        assertTrue(anchors.contains("CPR 201"));
        assertTrue(anchors.contains("MATH 101"));
        assertTrue(anchors.contains("PSYC 300"));
    }

    @Test
    public void testFindBusinessRuleTypeByAgenda() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> brTypeKeys = client.getBusinessRuleTypesByAgendaType("KUALI_STUDENT_STUDENT_DROPS_COURSE");

        assertTrue(brTypeKeys.size() == 1);
        assertTrue(brTypeKeys.contains(BusinessRuleTypeKey.KUALI_PRE_REQ.toString()));
    }

    @Test
    public void testFetchAgendaInfoDeterminationStructure() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        AgendaDeterminationInfo adiDTO = client.getAgendaInfoDeterminationStructure(AgendaType.KUALI_VALIDATE_LUI_PERSON_RELATION.toString());

        Map<String, String> adiDTOMap = adiDTO.getAgendaDeterminationKeyList();

        assertEquals(adiDTOMap.size(), 3);
        assertTrue(adiDTOMap.containsKey("agendaDeterminationInfo.luiType"));
        assertTrue(adiDTOMap.containsKey("agendaDeterminationInfo.luiPersonRelationType"));
        assertTrue(adiDTOMap.containsKey("agendaDeterminationInfo.relationState"));
        assertEquals("", adiDTOMap.get("agendaDeterminationInfo.luiType"));

        AgendaDeterminationInfo adiDTO1 = client.getAgendaInfoDeterminationStructure(AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSE.toString());
        Map<String, String> adiDTOMap1 = adiDTO1.getAgendaDeterminationKeyList();
        assertEquals(adiDTOMap1.size(), 0);
    }

    @Test
    public void testFetchEmptyAgendaInfoDeterminationStructure() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        AgendaDeterminationInfo adiDTO = client.getAgendaInfoDeterminationStructure(AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSE.toString());
        Map<String, String> adiDTOMap = adiDTO.getAgendaDeterminationKeyList();
        assertEquals(adiDTOMap.size(), 0);
    }
    
    @Test
    public void testFetchNonExistentAgendaInfo() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {

        try {
            AgendaDeterminationInfo adiDTO = client.getAgendaInfoDeterminationStructure(AgendaType.KUALI_EMPTY_AGENDA.toString());
        } catch (DoesNotExistException dne) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testFetchAgendaInfo() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        
        Map<String, String> agendaDeterminationMap = new HashMap<String, String>();
        agendaDeterminationMap.put("agendaDeterminationInfo.luiType", "course");
        agendaDeterminationMap.put("agendaDeterminationInfo.luiPersonRelationType", "kuali.student");
        agendaDeterminationMap.put("agendaDeterminationInfo.relationState", "enrolled");
        
        AgendaDeterminationInfo adiDTO = new AgendaDeterminationInfo();
        adiDTO.setAgendaInfoDeterminationKeyList(agendaDeterminationMap);
                
        AgendaInfo aiDTO = client.getAgendaInfo(AgendaType.KUALI_VALIDATE_LUI_PERSON_RELATION.toString(),  adiDTO);

        assertEquals(aiDTO.getAgendaTypeKey(), AgendaType.KUALI_VALIDATE_LUI_PERSON_RELATION.toString());
        assertEquals(aiDTO.getBusinessRuleTypeInfoList().size(), 1);
        assertEquals(aiDTO.getBusinessRuleTypeInfoList().get(0).getBussinessRuleTypeKey(), BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
    }


    @Test
    public void testFetchEmptyAgendaInfo() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        
        Map<String, String> agendaDeterminationMap = new HashMap<String, String>();
        AgendaDeterminationInfo adiDTO = new AgendaDeterminationInfo();
        adiDTO.setAgendaInfoDeterminationKeyList(agendaDeterminationMap);
                
        AgendaInfo aiDTO = client.getAgendaInfo(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE.toString(),  adiDTO);

        assertEquals(aiDTO.getAgendaTypeKey(), AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE.toString());
        assertEquals(aiDTO.getBusinessRuleTypeInfoList().size(), 1);
        assertEquals(aiDTO.getBusinessRuleTypeInfoList().get(0).getBussinessRuleTypeKey(), BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
    }           
    
    @Test
    public void testFetchDetailedBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleInfo brInfoDTO = client.getDetailedBusinessRuleInfo(ruleId_1);
        assertEquals(ruleId_1,  brInfoDTO.getId());
        assertEquals("CPR 201", brInfoDTO.getAnchor());
        assertEquals(3, brInfoDTO.getBusinessRuleElementList().size());
                
        RulePropositionInfo ruleProp = brInfoDTO.getBusinessRuleElementList().get(0).getBusinessRuleProposition();
        
        List<FactStructureInfo> factStructureList = ruleProp.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(2, factStructureList.size());    

        FactStructureInfo fs1 = factStructureList.get(0);
        assertEquals("CPR 101", fs1.getStaticValue());

        FactStructureInfo fs2 = factStructureList.get(1);
        assertEquals("CPR 101, MATH 101, MATH 102", fs2.getStaticValue());        
    }    

    
    @Test
    public void testFetchFactTranslationKeys() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleInfo brInfoDTO = client.getDetailedBusinessRuleInfo(ruleId_3);
        assertEquals(ruleId_3,  brInfoDTO.getId());
                
        RulePropositionInfo ruleProp = brInfoDTO.getBusinessRuleElementList().get(9).getBusinessRuleProposition();
        assertEquals(ruleProp.getName(), "P33");
        
        List<FactStructureInfo> factStructureList = ruleProp.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(1, factStructureList.size());    

        FactStructureInfo fs1 = factStructureList.get(0);
        assertEquals("331", fs1.getFactStructureId());

        Map<String, String> translationKeyMap = fs1.getResultColumnKeyTranslations();
        assertTrue(translationKeyMap.containsKey("key.proposition.column.sum"));
        assertTrue(translationKeyMap.containsValue("resultColumn.credit"));
    }  
    
    @Test
    public void testFetchBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleInfo brInfoDTO = client.getBusinessRuleInfo(ruleId_1);
        assertEquals(ruleId_1,  brInfoDTO.getId());
        assertEquals("CPR 201", brInfoDTO.getAnchor());
        assertEquals(0, brInfoDTO.getBusinessRuleElementList().size());
    }    

    @Test
    public void testFindBusinessRuleIdByType()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<String> ruleIdList = client.getBusinessRuleIdsByBusinessRuleType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
    
        assertEquals(2, ruleIdList.size());
        assertEquals(ruleId_2, ruleIdList.get(0));
        assertEquals(ruleId_3, ruleIdList.get(1));
    }
    
    @Test
    public void testInvalidFetchBusinessRule()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        try {
            // Business rule with ruleId 0 does not exists
            BusinessRuleInfo brInfo = client.getBusinessRuleInfo("0");
        } catch (DoesNotExistException ex) {
            // Right behavior
            return;
        }               
        assertTrue(false);
    }

    @Test
    public void testFetchBusinessRuleType()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleTypeInfo ruleType = client.getBusinessRuleType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString(), AnchorTypeKey.KUALI_COURSE.toString());
        
        assertEquals(BusinessRuleTypeKey.KUALI_PRE_REQ.toString(), ruleType.getBussinessRuleTypeKey());
        assertEquals(AnchorTypeKey.KUALI_COURSE.toString(),ruleType.getAnchorTypeKey());
    }
    
    @Test
    public void testFetchFactTypeKey()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleTypeInfo ruleType = client.getBusinessRuleType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString(), AnchorTypeKey.KUALI_COURSE.toString());
        
        assertEquals(2, ruleType.getFactTypeKeyList().size());
        assertEquals("fact.earned_credit_list", ruleType.getFactTypeKeyList().get(0));
        assertEquals("fact.completed_course_list", ruleType.getFactTypeKeyList().get(1));
    }
    
    
    @Test
    public void testCreateBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {        
        // Create Rule Test
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName(create_rule_name);
        BusinessRuleInfo newBrInfoDTO = client.createBusinessRule(brInfoDTO);
        
        newBrInfoDTO = client.getDetailedBusinessRuleInfo(newBrInfoDTO.getId());
        
        assertEquals(brInfoDTO.getName(), newBrInfoDTO.getName());
        assertNotNull(newBrInfoDTO.getId());
        assertNotNull(newBrInfoDTO.getCompiledId());
        assertEquals(newBrInfoDTO.getOriginalRuleId(), newBrInfoDTO.getId());
    }

    @Test
    public void testCreateActiveBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        // Create Rule Test
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName(create_rule_name + "ACTIVE");
        brInfoDTO.setState(BusinessRuleStatus.ACTIVE.toString());
        
        try {
            BusinessRuleInfo newBrInfoDTO = client.createBusinessRule(brInfoDTO);
        } catch (InvalidParameterException ipx) {
            assertTrue(true);
            return;
        }
        
        assertTrue(false);
    }    
    
    @Test
    public void testCreateRetiredBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        // Create Rule Test
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName(create_rule_name + "RETIRED");
        brInfoDTO.setState(BusinessRuleStatus.RETIRED.toString());
        
        try {
            BusinessRuleInfo newBrInfoDTO = client.createBusinessRule(brInfoDTO);
        } catch (InvalidParameterException ipx) {
            assertTrue(true);
            return;
        }
        
        assertTrue(false);
    }    

    @Test
    public void testUpdateBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException, ReadOnlyException {        

        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Test update rule");
        BusinessRuleInfo createdBrInfo = client.createBusinessRule(brInfoDTO);
        
        BusinessRuleInfo updateBrInfoDTO = generateUpdatedBusinessRule( generateNewBusinessRuleInfo() );
        updateBrInfoDTO.setName("Test update rule");
        updateBrInfoDTO.setCompiledId(createdBrInfo.getCompiledId());
        updateBrInfoDTO.setId(createdBrInfo.getId());
        
        BusinessRuleInfo updatedBrInfo = client.updateBusinessRule(createdBrInfo.getId(), updateBrInfoDTO);
                       
        assertTrue(updatedBrInfo.getBusinessRuleElementList().size() == 3);
        assertEquals(updatedBrInfo.getState(), "DRAFT_IN_PROGRESS");
    }
    
    @Test
    public void testActivateBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException, ReadOnlyException, DataValidationErrorException {
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Activate rule test");
        BusinessRuleInfo createdBrInfo = client.createBusinessRule(brInfoDTO);

        BusinessRuleInfo newBrInfoDTO1 = client.updateBusinessRuleState(createdBrInfo.getId(), BusinessRuleStatus.ACTIVE.toString());
                
        assertEquals(newBrInfoDTO1.getState(), "ACTIVE");
                
        // Check if the exception is caught when trying to change ACTIVE rule
        try {
            client.updateBusinessRuleState(createdBrInfo.getId(), BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
            fail("Updating business rule from 'ACTIVE' to 'DRAFT_IN_PROGRESS' should have failed");
        } catch (InvalidParameterException ex) {
            // This is an expected exception
        	assertTrue(true);
        }
    }   
    
    @Test
    public void testActivatingMultipleVersion() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DependentObjectsExistException, ReadOnlyException, DataValidationErrorException {
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        String origName = create_rule_name + "_ma1";
        brInfoDTO.setName(origName);
        BusinessRuleInfo rule1 = client.createBusinessRule(brInfoDTO);   

        client.updateBusinessRuleState(rule1.getId(), BusinessRuleStatus.ACTIVE.toString());
        
        BusinessRuleInfo rule2 = client.createNewVersion(rule1);
                
        assertEquals(origName , rule2.getName());
        assertEquals(rule1.getBusinessRuleElementList().size(), rule2.getBusinessRuleElementList().size());
        assertEquals(rule1.getId(), rule2.getOriginalRuleId());
        assertEquals(rule1.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey(), rule2.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey());
        
        Calendar now = new GregorianCalendar();
        now.add(Calendar.DAY_OF_YEAR, 1);
        Date dt1 = now.getTime();
        now.add(Calendar.DAY_OF_YEAR, 2);
        Date dt2 = now.getTime();
        
        rule2.setEffectiveDate(dt1);
        rule2.setExpirationDate(dt2);

        rule2 = client.updateBusinessRule(rule2.getId(), rule2);
        
        BusinessRuleInfo updatedBrInfo = client.updateBusinessRuleState(rule2.getId(), BusinessRuleStatus.ACTIVE.toString());

        assertEquals(origName , rule2.getName());
        assertEquals(BusinessRuleStatus.ACTIVE.toString(), updatedBrInfo.getState());
        assertEquals(rule1.getBusinessRuleElementList().size(), rule2.getBusinessRuleElementList().size());
        assertEquals(rule1.getId(), rule2.getOriginalRuleId());
        assertEquals(rule1.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey(), rule2.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey());
    }

    
    @Test
    public void testActivatingMultipleVersionWithDateConflicts() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DependentObjectsExistException, ReadOnlyException, DataValidationErrorException {
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        String origName = create_rule_name + "_ma2";
        brInfoDTO.setName(origName);

        Calendar now = new GregorianCalendar();
        now.add(Calendar.DAY_OF_YEAR, 1);
        Date dt1 = now.getTime();
        now.add(Calendar.DAY_OF_YEAR, 10);
        Date dt2 = now.getTime();
        brInfoDTO.setEffectiveDate(dt1);
        brInfoDTO.setExpirationDate(dt2);
        
        BusinessRuleInfo rule1 = client.createBusinessRule(brInfoDTO);   

        client.updateBusinessRuleState(rule1.getId(), BusinessRuleStatus.ACTIVE.toString());
        
        BusinessRuleInfo rule2 = client.createNewVersion(rule1);
                
        assertEquals(origName , rule2.getName());
        assertEquals(rule1.getBusinessRuleElementList().size(), rule2.getBusinessRuleElementList().size());
        assertEquals(rule1.getId(), rule2.getOriginalRuleId());
        assertEquals(rule1.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey(), rule2.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey());
        
        Calendar now1 = new GregorianCalendar();
        now1.add(Calendar.DAY_OF_YEAR, 3);
        Date dt3 = now.getTime();
        now1.add(Calendar.DAY_OF_YEAR, 15);
        Date dt4 = now.getTime();
        
        rule2.setEffectiveDate(dt3);
        rule2.setExpirationDate(dt4);

        rule2 = client.updateBusinessRule(rule2.getId(), rule2);
        
        try {
            BusinessRuleInfo updatedBrInfo = client.updateBusinessRuleState(rule2.getId(), BusinessRuleStatus.ACTIVE.toString());
        } catch (InvalidParameterException ipx) {
            assertTrue(true);
            return;
        }
        
        assertTrue(false);
    }    
        
    
    @Test
    public void testRetireBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException, ReadOnlyException, DataValidationErrorException {
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Retire rule test");
        BusinessRuleInfo createdBrInfo = client.createBusinessRule(brInfoDTO);
        
        createdBrInfo.setState("ACTIVE");     
        BusinessRuleInfo updatedBrInfo = client.updateBusinessRuleState(createdBrInfo.getId(), BusinessRuleStatus.ACTIVE.toString());

        updatedBrInfo.setState("RETIRED");
        BusinessRuleInfo retiredBrInfo = client.updateBusinessRuleState(updatedBrInfo.getId(), BusinessRuleStatus.RETIRED.toString());
         
        assertEquals(retiredBrInfo.getState(), "RETIRED");                
    }    
    
    
    @Test
    public void testFetchBusinessRuleEnglish() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        String englishValue = client.getBusinessRuleEnglish("1");
        assertEquals("Rule1", englishValue);
    }

    @Test
    public void testFindBusinessRuleType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> brTypeKeys = client.getBusinessRuleTypes();
        
        assertTrue(brTypeKeys.size() == 2);
        assertTrue(brTypeKeys.contains(BusinessRuleTypeKey.KUALI_CO_REQ.toString()));        
    }
        
    @Test
    public void testDeleteBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException {
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Test delete Rule");
        BusinessRuleInfo createdRuleInfo = client.createBusinessRule(brInfoDTO);
        String ruleId = createdRuleInfo.getId();
        client.deleteBusinessRule(ruleId);

        try {
            BusinessRuleInfo newBrInfoDTO1 = client.getDetailedBusinessRuleInfo(ruleId);
        } catch (DoesNotExistException ex) {
            assertTrue(true);
        }                        
    }

    @Test
    public void testFetchBusinessRuleByAnchor()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException, ReadOnlyException, DataValidationErrorException {
        // Activate the rule before fetching the rule
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchor("CPR 201");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_CO_REQ.toString());
        
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        brInfoDTO.setExpirationDate(cal.getTime());
        brInfoDTO.setName("Activate rule test");
        BusinessRuleInfo createdBrInfo = client.createBusinessRule(brInfoDTO);
                   
        BusinessRuleInfo newBrInfoDTO1 = client.updateBusinessRuleState(createdBrInfo.getId(), BusinessRuleStatus.ACTIVE.toString());
                                    
        BusinessRuleAnchorInfo anchorDTO = new BusinessRuleAnchorInfo();
        anchorDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_CO_REQ.toString());
        anchorDTO.setAnchorValue("CPR 201");
        anchorDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        
        List<BusinessRuleInfo> rules = client.getBusinessRuleByAnchor(anchorDTO);
                
        assertEquals(1, rules.size());
        
        BusinessRuleInfo ruleInfo = rules.get(0);
        
        assertEquals(createdBrInfo.getId(), ruleInfo.getId());        
    }

    @Test
    public void testFetchBusinessRuleByAnchorList()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException, ReadOnlyException, DataValidationErrorException {
        // Activate the rule before fetching the rule
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchor("CPR 202");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_CO_REQ.toString());
        
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        brInfoDTO.setExpirationDate(cal.getTime());
        brInfoDTO.setName("Activate rule test");
        BusinessRuleInfo createdBrInfo = client.createBusinessRule(brInfoDTO);
                   
        BusinessRuleInfo newBrInfoDTO1 = client.updateBusinessRuleState(createdBrInfo.getId(), BusinessRuleStatus.ACTIVE.toString());
                                    
        List<BusinessRuleAnchorInfo> anchorList = new ArrayList<BusinessRuleAnchorInfo>();
        BusinessRuleAnchorInfo anchorDTO = new BusinessRuleAnchorInfo();
        anchorDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_CO_REQ.toString());
        anchorDTO.setAnchorValue("CPR 202");
        anchorDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        anchorList.add(anchorDTO);
        
        List<BusinessRuleInfo> rules = client.getBusinessRuleByAnchorList(anchorList);
                
        assertEquals(1, rules.size());
        
        BusinessRuleInfo ruleInfo = rules.get(0);
        
        assertEquals(createdBrInfo.getId(), ruleInfo.getId());        
    }

    /**
     * 
     * This method tests business rule with complex rule element and fact structure created through test-beans
     * 
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DependentObjectsExistException
     * @throws PermissionDeniedException
     * @throws AlreadyExistsException
     */
    @Test
    public void testComplextBusinessRuleCreation()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException {
        BusinessRuleInfo brInfo = client.getDetailedBusinessRuleInfo(ruleId_3);
        
        assertEquals(ruleId_3, brInfo.getId());
        assertEquals("PSYC 300", brInfo.getAnchor());
        
        
        List<RuleElementInfo> ruleElements = brInfo.getBusinessRuleElementList();
        assertEquals(11, ruleElements.size());
        
        
        RulePropositionInfo proposition1 = ruleElements.get(1).getBusinessRuleProposition();
        RulePropositionInfo proposition2 = ruleElements.get(5).getBusinessRuleProposition();
        RulePropositionInfo proposition3 = ruleElements.get(9).getBusinessRuleProposition();
        
        RuleElementInfo ruleElementL = ruleElements.get(0);
        assertEquals(ruleElementL.getBusinessRuleElemnetTypeKey(), "(");
                
        assertEquals("INTERSECTION", proposition1.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
        assertEquals("INTERSECTION", proposition2.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
        assertEquals("SUM", proposition3.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
        
        List<FactStructureInfo> prop1facts = proposition1.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(2, prop1facts.size());
        FactStructureInfo fact11 = prop1facts.get(0);
        assertEquals(true, fact11.isStaticFact());
        assertEquals("fact.psyc300_prereq_criteria1", fact11.getFactTypeKey());
        
        
        List<FactStructureInfo> prop2facts = proposition2.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(2, prop2facts.size());
        
        
        List<FactStructureInfo> prop3facts = proposition3.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(1, prop3facts.size());

        FactStructureInfo fact31 = prop3facts.get(0);
        Map<String, String> paramMap = fact31.getParamValueMap();
        
        assertTrue(paramMap.containsKey("factParam.studentId"));
        assertTrue(paramMap.containsKey("factParam.clusetId"));
        assertTrue(paramMap.containsKey("factParam.excludeCluSet"));
        
        assertEquals("PSYC 205, PSYC 263",paramMap.get("factParam.excludeCluSet"));
        
    }
    
    @Test
    public void testEmptyRuleCreating()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException {

        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Empty rule");
        // Empty the rule elements
        brInfoDTO.setBusinessRuleElementList(new ArrayList<RuleElementInfo>());

        BusinessRuleInfo createdBrInfo = null;
        try {
            createdBrInfo = client.createBusinessRule(brInfoDTO);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assertTrue(false);
        }
        
        BusinessRuleInfo newBrInfoDTO = client.getBusinessRuleInfo(createdBrInfo.getId());
        assertEquals(0, newBrInfoDTO.getBusinessRuleElementList().size());
        
        assertTrue(true);
    }

  
    @Test
    public void testCreateWithNonEmptyId() throws AlreadyExistsException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
            brInfoDTO.setId("234");
            BusinessRuleInfo newBrInfoDTO = client.createBusinessRule(brInfoDTO);                        

        } catch (InvalidParameterException ex) {
            // Right behavior
            return;
        }               
        assertTrue(false);                
    }
    
    
    @Test
    public void testErrorInRepository() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
            brInfoDTO.setName(create_rule_name);
            BusinessRuleInfo newBrInfoDTO = client.createBusinessRule(brInfoDTO);   

            newBrInfoDTO.setId(null);
            newBrInfoDTO = client.createBusinessRule(brInfoDTO);   
            
            //assertTrue(true);
    }
    
    
    @Test
    public void testCreateNewRuleVersion()  throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DependentObjectsExistException, ReadOnlyException, DataValidationErrorException {
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        String origName = create_rule_name + "_version1";
        brInfoDTO.setName(origName);
        BusinessRuleInfo rule1 = client.createBusinessRule(brInfoDTO);   
        
        rule1 = client.updateBusinessRuleState(rule1.getId(), BusinessRuleStatus.ACTIVE.toString());
        
        BusinessRuleInfo rule2 = client.createNewVersion(rule1);
                
        assertEquals(origName , rule2.getName());
        assertEquals(rule1.getBusinessRuleElementList().size(), rule2.getBusinessRuleElementList().size());
        assertEquals(rule1.getId(), rule2.getOriginalRuleId());
        assertEquals(rule1.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey(), rule2.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey());
    }

 
    @Test
    public void testFirstVersionRuleId() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DependentObjectsExistException, ReadOnlyException, DataValidationErrorException {
        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        String origName = create_rule_name + "_version1";
        brInfoDTO.setName(origName);
        BusinessRuleInfo rule1 = client.createBusinessRule(brInfoDTO);   
 
        rule1 = client.updateBusinessRuleState(rule1.getId(), BusinessRuleStatus.ACTIVE.toString());
        
        BusinessRuleInfo rule2 = client.createNewVersion(rule1);
        BusinessRuleInfo updatedBrInfo1 = client.updateBusinessRuleState(rule1.getId(), BusinessRuleStatus.RETIRED.toString());
        
        BusinessRuleInfo updatedBrInfo2 = client.updateBusinessRuleState(rule2.getId(), BusinessRuleStatus.ACTIVE.toString());
                
        BusinessRuleInfo rule3 = client.createNewVersion(rule2);
                
        assertEquals(origName , rule2.getName());
        assertEquals(rule1.getBusinessRuleElementList().size(), rule2.getBusinessRuleElementList().size());
        assertEquals(rule1.getId(), rule2.getOriginalRuleId());
        assertEquals(rule1.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey(), rule2.getBusinessRuleElementList().get(0).getBusinessRuleElemnetTypeKey());
        assertEquals(rule1.getId(), rule3.getOriginalRuleId());
        
        assertEquals(origName , rule3.getName());
        assertEquals(rule1.getBusinessRuleElementList().size(), rule3.getBusinessRuleElementList().size());
    }
    
    
    @Test
    public void testCreateNewRuleVersionForInProgress()  throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DependentObjectsExistException, DataValidationErrorException {

        BusinessRuleInfo brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName(create_rule_name + "_version1");
        BusinessRuleInfo rule1 = client.createBusinessRule(brInfoDTO);   
                
        try {
            BusinessRuleInfo rule2 = client.createNewVersion(rule1);
        } catch (InvalidParameterException ipx) {
            assertTrue(true);
            return;
        }
        
        assertTrue(false);
    }
    
    
    private BusinessRuleInfo generateNewBusinessRuleInfo() {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateId("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateId("Len");
     
        FactStructureInfo fs1 = buildFactStructureForRuleCriteria();
        FactStructureInfo fs2 = buildFactStructureForIntersection();
        
        List<FactStructureInfo> factStructureList = new ArrayList<FactStructureInfo>();
        factStructureList.add(fs1);
        factStructureList.add(fs2);
        
        YieldValueFunctionInfo yieldValueFunctionDTO = new YieldValueFunctionInfo();
        yieldValueFunctionDTO.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION.toString());
        yieldValueFunctionDTO.setFactStructureList(factStructureList);

        LeftHandSideInfo leftHandSideDTO = new LeftHandSideInfo();
        leftHandSideDTO.setYieldValueFunction(yieldValueFunctionDTO);

        RightHandSideInfo rightHandSideDTO = new RightHandSideInfo();
        rightHandSideDTO.setExpectedValue("12");

        RulePropositionInfo rulePropositionDTO = new RulePropositionInfo();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDesc("Credit Intersection Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataTypeKey(Double.class.getName());
        rulePropositionDTO.setComparisonOperatorTypeKey(ComparisonOperator.LESS_THAN.toString());

        RuleElementInfo reDTO = new RuleElementInfo();
        reDTO.setName("Pre-req 1");
        reDTO.setDesc("Pre req check for Math 101");
        reDTO.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        reDTO.setBusinessRuleProposition(rulePropositionDTO);

        BusinessRuleInfo brInfoDTO = new BusinessRuleInfo();
        brInfoDTO.setName("CHEM100PRE_REQ_DISPLAY");        
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchor("CHEM 100");
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);
        brInfoDTO.setEffectiveDate(new Date());
        brInfoDTO.setExpirationDate(new Date());

        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(reDTO);

        brInfoDTO.setBusinessRuleElementList(elementList);
        
        return brInfoDTO;
    }

    
    private BusinessRuleInfo generateUpdatedBusinessRule(BusinessRuleInfo brInfoDTO) {
        
        brInfoDTO.setState("DRAFT_IN_PROGRESS");
                
        // Rule element - I
        YieldValueFunctionInfo yieldValueFunction1 = new YieldValueFunctionInfo();
        yieldValueFunction1.setYieldValueFunctionType("INTERSECTION");
        
        FactStructureInfo fs1 = buildFactStructureForRuleCriteria();
        FactStructureInfo fs2 = buildFactStructureForIntersection();

        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs1.setParamValueMap(paramVariableMap);
        
        List<FactStructureInfo> factStructureList1 = new ArrayList<FactStructureInfo>();
        factStructureList1.add(fs1);
        factStructureList1.add(fs2);
        yieldValueFunction1.setFactStructureList(factStructureList1);
        
        LeftHandSideInfo leftHandSide1 = new LeftHandSideInfo();
        leftHandSide1.setYieldValueFunction(yieldValueFunction1);
       
        RightHandSideInfo rightHandSide1 = new RightHandSideInfo();
        rightHandSide1.setExpectedValue("1");
        
        RulePropositionInfo ruleProposition1 = new RulePropositionInfo();
        ruleProposition1.setName("Must-have-1-of-CPR101");
        ruleProposition1.setDesc("Course intersection");
        ruleProposition1.setLeftHandSide(leftHandSide1);
        ruleProposition1.setRightHandSide(rightHandSide1);
        ruleProposition1.setComparisonDataTypeKey(String.class.getName());
        ruleProposition1.setComparisonOperatorTypeKey(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

        RuleElementInfo re1 = new RuleElementInfo();
        re1.setName("course.intersection.1.of.cpr101");
        re1.setDesc("Must have 1 of CPR 101");
        re1.setBusinessRuleElemnetTypeKey("PROPOSITION");
        re1.setBusinessRuleProposition(ruleProposition1);
        
        // Rule Element - II        
        RuleElementInfo re2 = new RuleElementInfo();
        re2.setName("And");
        re2.setDesc("And");
        re2.setBusinessRuleElemnetTypeKey("AND");
                
        // Rule Element - III
        YieldValueFunctionInfo yieldValueFunction2 = new YieldValueFunctionInfo();
        yieldValueFunction2.setYieldValueFunctionType("SUM");
        
        FactStructureInfo fs3 = buildFactStructureForSum();

        // Not need for summation or averages
        Map<String,String> paramVariableMap2 = new HashMap<String,String>();
        fs3.setParamValueMap(paramVariableMap2);
        
        List<FactStructureInfo> factStructureList2 = new ArrayList<FactStructureInfo>();
        factStructureList2.add(fs3);
        yieldValueFunction2.setFactStructureList(factStructureList2);
        
        LeftHandSideInfo leftHandSide2 = new LeftHandSideInfo();
        leftHandSide2.setYieldValueFunction(yieldValueFunction2);
       
        RightHandSideInfo rightHandSide2 = new RightHandSideInfo();
        rightHandSide2.setExpectedValue("10.0");
        
        RulePropositionInfo ruleProposition2 = new RulePropositionInfo();
        ruleProposition2.setName("Course-credit-summation");
        ruleProposition2.setDesc("Course credit summation");
        ruleProposition2.setLeftHandSide(leftHandSide2);
        ruleProposition2.setRightHandSide(rightHandSide2);
        ruleProposition2.setComparisonDataTypeKey(BigDecimal.class.getName());
        ruleProposition2.setComparisonOperatorTypeKey(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

        RuleElementInfo re3 = new RuleElementInfo();
        re3.setName("course.credits.sum");
        re3.setDesc("Pre req check for CPR 101");
        re3.setBusinessRuleElemnetTypeKey("PROPOSITION");
        re3.setBusinessRuleProposition(ruleProposition2);
        
        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(re1);
        elementList.add(re2);
        elementList.add(re3);

        brInfoDTO.setBusinessRuleElementList(elementList);
        
        return brInfoDTO;
    }

    /*
     * Builds the fact structure to be used as a criteria
     */
    private FactStructureInfo buildFactStructureForRuleCriteria() {
        FactStructureInfo fs = new FactStructureInfo();
        
        fs.setFactStructureId("1");
        fs.setFactTypeKey("fact.cpr_prereq_criteria");
        fs.setAnchorFlag(false);
        fs.setStaticFact(true);
        fs.setStaticValue("CPR101");
        
        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs.setParamValueMap(paramVariableMap);
        
        return fs;        
    }
    
    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureInfo buildFactStructureForIntersection() {
        FactStructureInfo fs = new FactStructureInfo();
        
        fs.setFactStructureId("2");
        fs.setFactTypeKey("fact.cpr_prereq_clulist1");
        fs.setAnchorFlag(false);
        fs.setStaticFact(true);
        fs.setStaticValue("CPR101, CPR201, CPR301");
        
        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs.setParamValueMap(paramVariableMap);
        
        return fs;                
    }

    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureInfo buildFactStructureForSum() {
        FactStructureInfo fs = new FactStructureInfo();
        
        fs.setFactStructureId("3");
        fs.setFactTypeKey("fact.cpr_prereq_gpalist");
        fs.setAnchorFlag(false);
        fs.setStaticFact(true);
        fs.setStaticValue("4.0, 2.5, 3.0");
        
        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs.setParamValueMap(paramVariableMap);
        
        return fs;                
    }

}
