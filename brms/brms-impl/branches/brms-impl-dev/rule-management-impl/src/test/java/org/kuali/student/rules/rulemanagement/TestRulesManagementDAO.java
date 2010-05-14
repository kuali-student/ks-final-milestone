/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.internal.common.entity.AgendaType;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl;
import org.kuali.student.rules.rulemanagement.entity.AgendaInfo;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.rules.rulemanagement.entity.FactStructure;
import org.kuali.student.rules.rulemanagement.entity.LeftHandSide;
import org.kuali.student.rules.rulemanagement.entity.RightHandSide;
import org.kuali.student.rules.rulemanagement.entity.RuleElement;
import org.kuali.student.rules.rulemanagement.entity.RuleMetaData;
import org.kuali.student.rules.rulemanagement.entity.RuleProposition;
import org.kuali.student.rules.rulemanagement.entity.YieldValueFunction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a <code>FunctionalBusinessRuleDAOImpl</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/rulemanagement-persistence.xml")
public class TestRulesManagementDAO extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl", testDataFile = "classpath:test-beans.xml")
    public RuleManagementDAOImpl rulesManagementDAO;

    public static final String ruleId_1 = "11223344-1122-1122-1112-100000000001";
    public static final String ruleId_2 = "11223344-1122-1122-1112-100000000011";
    
    public static final String agendaId_1 = "11223344-1122-1122-1112-100000000030";
    public static final String agendaId_2 = "11223344-1122-1122-1112-100000000031";
    
    @Test
    public void testCreateRule() {

        BusinessRuleType brType = rulesManagementDAO.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.KUALI_PRE_REQ, AnchorTypeKey.KUALI_COURSE);
        
        RuleMetaData metaInfo = new RuleMetaData();
        metaInfo.setCreateDate(new Date());
        metaInfo.setCreatedBy("Kamal");
        metaInfo.setUpdateBy("Len");
        metaInfo.setUpdateDate(new Date());

        metaInfo.setEffectiveDate(new Date());
        metaInfo.setExpirationDate(new Date());

        BusinessRule rule = new BusinessRule();
        rule.setState(BusinessRuleStatus.ACTIVE);
        rule.setName("CHEM 100 Display Name");
        rule.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule.setSuccessMessage("Test success message");
        rule.setFailureMessage("Test failure message");
        rule.setBusinessRuleType(brType);
        rule.setAnchor("CHEM 100");
        rule.setMetaData(metaInfo);

        FactStructure factStructure = new FactStructure();
        factStructure.setFactStructureId("1");
        factStructure.setStaticFact(true);
        factStructure.setStaticValue("123");
        factStructure.setStaticValueDataType(Long.class.getName());
        
        List<FactStructure> factStructureList = new ArrayList<FactStructure>();
        factStructureList.add(factStructure);
        
        YieldValueFunction yvf = new YieldValueFunction();
        yvf.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION);
        yvf.setFacts(factStructureList);

        LeftHandSide lhs = new LeftHandSide();
        lhs.setYieldValueFunction(yvf);

        RightHandSide rhs = new RightHandSide();
        rhs.setExpectedValue("12.0");

        RuleProposition ruleProposition = new RuleProposition();
        ruleProposition.setComparisonDataTypeKey("kual.number");
        ruleProposition.setName("P1");
        ruleProposition.setComparisonOperatorTypeKey(ComparisonOperator.LESS_THAN);
        ruleProposition.setRightHandSide(rhs);
        ruleProposition.setLeftHandSide(lhs);

        RuleElement re1 = new RuleElement();
        re1.setDescription("Credit Intersection Change");
        re1.setName("Credit Check");
        re1.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION);
        re1.setOrdinalPosition(1);
        re1.setRuleProposition(ruleProposition);

        List<RuleElement> elements = new ArrayList<RuleElement>();
        elements.add(re1);

        rule.setRuleElements(elements);

        rulesManagementDAO.createBusinessRule(rule);

        BusinessRule newRule = rulesManagementDAO.lookupBusinessRuleUsingId( rule.getId() );

        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
        
        assertEquals(1, newRule.getRuleElements().size());
        RuleProposition rp = newRule.getRuleElements().get(0).getRuleProposition();
        assertNotNull(rp);
        assertNotNull(rp.getLeftHandSide().getYieldValueFunction().getFacts());
        FactStructure fs = rp.getLeftHandSide().getYieldValueFunction().getFacts().get(0);
        assertEquals("1", fs.getFactStructureId());
        assertEquals("123", fs.getStaticValue());
        assertEquals(true, fs.getStaticFact());
        assertEquals(Long.class.getName(), fs.getStaticValueDataType());
    }

    @Test
    public void testUpdateRule() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingId(ruleId_1);

        FactStructure factStructure = new FactStructure();
        factStructure.setFactStructureId("1");
        factStructure.setStaticFact(true);
        factStructure.setStaticValue("123");
        factStructure.setStaticValueDataType(Double.class.getName());
        
        List<FactStructure> factStructureList = new ArrayList<FactStructure>();
        factStructureList.add(factStructure);
        
        YieldValueFunction yvf = new YieldValueFunction();
        yvf.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION);
        yvf.setFacts(factStructureList);

        LeftHandSide lhs = new LeftHandSide();
        lhs.setYieldValueFunction(yvf);

        RightHandSide rhs = new RightHandSide();
        rhs.setExpectedValue("12.0");

        RuleProposition ruleProposition1 = new RuleProposition();
        ruleProposition1.setComparisonDataTypeKey("kual.number");
        ruleProposition1.setName("Px");
        ruleProposition1.setComparisonOperatorTypeKey(ComparisonOperator.LESS_THAN);
        ruleProposition1.setRightHandSide(rhs);
        ruleProposition1.setLeftHandSide(lhs);

        RuleElement re1 = new RuleElement();
        re1.setDescription("First element");
        re1.setName("P1");
        re1.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION);
        re1.setOrdinalPosition(1);
        re1.setRuleProposition(ruleProposition1);

        RuleProposition ruleProposition2 = new RuleProposition();
        ruleProposition2.setComparisonDataTypeKey("kual.number");
        ruleProposition2.setName("Py");
        ruleProposition2.setComparisonOperatorTypeKey(ComparisonOperator.LESS_THAN);
        ruleProposition2.setRightHandSide(rhs);
        ruleProposition2.setLeftHandSide(lhs);
        
        RuleElement re2 = new RuleElement();
        re2.setDescription("Second element");
        re2.setName("P2");
        re2.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION);
        re2.setOrdinalPosition(1);
        re2.setRuleProposition(ruleProposition2);

        
        List<RuleElement> elements = new ArrayList<RuleElement>();
        elements.add(re1);
        elements.add(re2);
        
        rule.setRuleElements(elements);
        rule.setName("New Rule Name");
        
        rulesManagementDAO.updateBusinessRule(rule);

        BusinessRule updatedRule = rulesManagementDAO.lookupBusinessRuleUsingId(rule.getId());
        assertEquals(updatedRule.getName(), rule.getName());
        assertEquals(2, updatedRule.getRuleElements().size());
        assertEquals(BusinessRuleTypeKey.KUALI_CO_REQ, updatedRule.getBusinessRuleType().getBusinessRuleTypeKey());        
        
        RuleProposition rp = updatedRule.getRuleElements().get(0).getRuleProposition();
        assertNotNull(rp);
        assertNotNull(rp.getLeftHandSide().getYieldValueFunction().getFacts());
        FactStructure fs = rp.getLeftHandSide().getYieldValueFunction().getFacts().get(0);
        assertEquals("1", fs.getFactStructureId());
        assertEquals("123", fs.getStaticValue());
        assertEquals(true, fs.getStaticFact());
        assertEquals(Double.class.getName(), fs.getStaticValueDataType());
        
    }

    @Test
    public void testDeleteRuleById() {        
        org.junit.Assert.assertTrue(rulesManagementDAO.deleteBusinessRule("11223344-1122-1122-1112-100000000001"));
        org.junit.Assert.assertNull(em.find(BusinessRule.class, "11223344-1122-1122-1112-100000000001"));
    }

     @Test
     public void testDeleteRule() {
         BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingId(ruleId_2);
         org.junit.Assert.assertTrue(rulesManagementDAO.deleteBusinessRule(rule));
         org.junit.Assert.assertNull(em.find(BusinessRule.class, rule.getId()));         
     }

    @Test
    public void testDeleteRuleElemnet() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingId(ruleId_2);
        RuleElement element = rule.getRuleElements().get(0);
        
        assertTrue(rulesManagementDAO.deleteRuleElement(element));

        rule.getRuleElements().remove(0);
        rulesManagementDAO.updateBusinessRule(rule);
        
        BusinessRule newRule = rulesManagementDAO.lookupBusinessRuleUsingId(ruleId_2);
        assertEquals(2, newRule.getRuleElements().size());
    }
     
    @Test
    public void testLookupBusinessRuleUsingRuleId() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingId(ruleId_1);
        assertEquals(BusinessRuleTypeKey.KUALI_CO_REQ, rule.getBusinessRuleType().getBusinessRuleTypeKey());
        assertEquals("Intermediate CPR", rule.getName());
        assertEquals(3, rule.getRuleElements().size());
    }

    @Test
    public void testLookupBusinessRuleUsingAnchor() {
        List<BusinessRule> ruleList = rulesManagementDAO.lookupBusinessRuleUsingAnchor(BusinessRuleTypeKey.KUALI_CO_REQ, "CPR 201");

        assertEquals(1, ruleList.size());

        assertEquals("Intermediate CPR", ruleList.get(0).getName());
        assertEquals(3, ruleList.get(0).getRuleElements().size());
    }

    @Test
    public void testLookupAnchorByAnchorTypeKey() {
        List<String> anchorList = rulesManagementDAO.lookupAnchorByAnchorType(AnchorTypeKey.KUALI_COURSE);

        assertEquals(3, anchorList.size());
        assertEquals(true, anchorList.contains("CPR 201"));
        assertEquals(true, anchorList.contains("PSYC 300"));
    }

    @Test
    public void testLookupAgendaById() {
        AgendaInfo agenda = rulesManagementDAO.lookupAgendaInfoById(agendaId_1);
        assertEquals(agenda.getId(), agendaId_1);
        assertEquals(agenda.getType(), AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSE.toString());
        assertEquals(agenda.getBusinessRuleTypeInfoList().size(), 2);        
    }
    
    @Test
    public void testLookupAgendaByType() {
        List<AgendaInfo> agendaList = rulesManagementDAO.lookupAgendaInfosByType(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE);
        
        assertEquals(1, agendaList.size());
        AgendaInfo agenda = agendaList.get(0);
        
        assertEquals(agenda.getId(), agendaId_2);
        assertEquals(agenda.getType(), AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE.toString());
        assertEquals(agenda.getBusinessRuleTypeInfoList().size(), 1);        
    }
    
    @Test
    public void testLookupUniqueAgendaTypes() {
        List<AgendaType> agendaTypeList = rulesManagementDAO.lookupUniqueAgendaTypes();
        
        assertEquals(3, agendaTypeList.size());
        assertTrue(agendaTypeList.contains(AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSE));
        assertTrue(agendaTypeList.contains(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE));
        assertTrue(agendaTypeList.contains(AgendaType.KUALI_VALIDATE_LUI_PERSON_RELATION));
    }

    @Test
    public void testLookupUniqueBusinessRuleTypeKeys() {
        List<BusinessRuleTypeKey> ruleTypeList = rulesManagementDAO.lookupUniqueBusinessRuleTypeKeys();
    
        assertEquals(2, ruleTypeList.size());
        assertTrue(ruleTypeList.contains(BusinessRuleTypeKey.KUALI_CO_REQ));
        assertTrue(ruleTypeList.contains(BusinessRuleTypeKey.KUALI_PRE_REQ));
    }

    @Test
    public void testLookupBusinessRuleTypeByAgenda() {
        List<BusinessRuleTypeKey> ruleTypeList = rulesManagementDAO.lookupBusinessRuleTypeByAgenda(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE);

        assertEquals(1, ruleTypeList.size());
        assertTrue(ruleTypeList.contains(BusinessRuleTypeKey.KUALI_PRE_REQ));
    }
    
    @Test
    public void testLookupUniqueAnchorTypeKeys() {
        List<AnchorTypeKey> anchorTypeList = rulesManagementDAO.lookupUniqueAnchorTypeKeys();
        
        assertEquals(1, anchorTypeList.size());
        assertTrue(anchorTypeList.contains(AnchorTypeKey.KUALI_COURSE));
    }

    @Test
    public void testLookupRuleTypeByKeyAndAnchorType() {
        BusinessRuleType brType = rulesManagementDAO.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.KUALI_PRE_REQ, AnchorTypeKey.KUALI_COURSE);
        
        assertEquals(brType.getId(), "11223344-1122-1122-1112-100000000009");
        assertEquals(brType.getFactTypeKeyList().size(), 2);
        assertTrue(brType.getFactTypeKeyList().contains("fact.earned_credit_list"));        
    }

    
}
