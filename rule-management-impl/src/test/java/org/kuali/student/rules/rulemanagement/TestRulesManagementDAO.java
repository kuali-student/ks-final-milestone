/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
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

    public static final String ruleId_1 = "1";

    @Test
    public void testCreateRule() {

        RuleMetaData metaInfo = new RuleMetaData();
        metaInfo.setCreateDate( new Date() );
        metaInfo.setCreatedBy( "Kamal" );
        metaInfo.setUpdateBy("Len");
        metaInfo.setUpdateDate( new Date() );
        metaInfo.setStatus("ACTIVE");
        metaInfo.setEffectiveDateEnd( new Date() );
        metaInfo.setEffectiveDateStart( new Date() );

        BusinessRule rule = new BusinessRule();
        rule.setRuleId("2");
        rule.setName("CHEM 100 course prerequisites");
        rule.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule.setSuccessMessage("Test success message");
        rule.setFailureMessage("Test failure message");
        rule.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule.setAnchor("CHEM 100");
        rule.setMetaData(metaInfo);

        
        List<RuleElement> elements = new ArrayList<RuleElement>();
        
        YieldValueFunction yvf = new YieldValueFunction();
        yvf.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION);
        
        LeftHandSide lhs = new LeftHandSide();
        lhs.setYieldValueFunction(yvf);
        
        RightHandSide rhs = new RightHandSide();
        rhs.setExpectedValue("12.0");
        
        RuleProposition ruleProposition = new RuleProposition();
        ruleProposition.setComparisonDataType("kual.number");
        ruleProposition.setName("P1");
        ruleProposition.setOperator(ComparisonOperator.LESS_THAN);
        ruleProposition.setRightHandSide(rhs);
        ruleProposition.setLeftHandSide(lhs);
        
        RuleElement re1 = new RuleElement();
        re1.setDescription("Credit Intersection Change");
        re1.setName("Credit Check");
        re1.setOperation(RuleElementType.PROPOSITION);
        re1.setOrdinalPosition(1);
        re1.setRuleProposition(ruleProposition);
        
        elements.add(re1);
        
        rule.setRuleElements(elements);
        
        
        rulesManagementDAO.createBusinessRule(rule);
        
        BusinessRule newRule = rulesManagementDAO.lookupBusinessRuleUsingRuleId("2");

        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getRuleId(), rule.getRuleId());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
    }

    @Test
    public void testUpdateRule() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingRuleId(ruleId_1);

        rule.setName("New Rule Name");

        rulesManagementDAO.updateBusinessRule(rule);

        BusinessRule updatedRule = em.find(BusinessRule.class, rule.getId());
        assertEquals(updatedRule.getName(), rule.getName());
    }

    @Test
    public void testDeleteRule() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingRuleId(ruleId_1);
        assertTrue(rulesManagementDAO.deleteBusinessRule(rule));

        assertNull(em.find(BusinessRule.class, rule.getId()));
        
    }

//    @Test
//    public void testLookupBusinessRuleUsingRuleId() {
//        FunctionalBusinessRule rule = functionalBusinessRuleDAO.lookupBusinessRuleUsingRuleId("3");
//        assertEquals("course.co.req", rule.getBusinessRuleType());
//        assertEquals("EMS Certificate Program", rule.getName());
//        assertEquals(7, rule.getElements().size());
//    }
//
//    @Test
//    public void testLookupCompiledRuleID() {
//        FunctionalBusinessRule rule = new FunctionalBusinessRule("PR CHEM 2000",
//                "enrollment co-requisites for Chemistry 2000", "Success Message1", "Failure Message1", "5", null, null,
//                "Student Enrolls in Course", "course.co.req", "course", "PR CHEM 1001");
//        functionalBusinessRuleDAO.createBusinessRule(rule);
//
//        Collection<FunctionalBusinessRule> newRules = functionalBusinessRuleDAO
//                .lookupCompiledIDs("Student Enrolls in Course", "course.co.req", "course", "PR CHEM 1001");
//
//        assertEquals(newRules.size(), 1);
//
//        for (FunctionalBusinessRule newRule : newRules) {
//            assertEquals(rule.getAgendaType(), newRule.getAgendaType());
//            assertEquals(rule.getAnchor(), newRule.getAnchor());
//            assertEquals(rule.getAnchorType(), newRule.getAnchorType());
//            assertEquals(rule.getBusinessRuleType(), newRule.getBusinessRuleType());
//        }
//    }
}
