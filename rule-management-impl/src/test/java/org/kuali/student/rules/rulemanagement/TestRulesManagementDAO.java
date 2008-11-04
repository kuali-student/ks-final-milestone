/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleType;
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
    public static final String ruleId_2 = "2";

    @Test
    public void testCreateRule() {

        BusinessRuleType brType = rulesManagementDAO.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.KUALI_PRE_REQ, AnchorTypeKey.KUALI_COURSE);
        
        RuleMetaData metaInfo = new RuleMetaData();
        metaInfo.setCreateDate(new Date());
        metaInfo.setCreatedBy("Kamal");
        metaInfo.setUpdateBy("Len");
        metaInfo.setUpdateDate(new Date());
        metaInfo.setStatus("ACTIVE");
        metaInfo.setEffectiveDateEnd(new Date());
        metaInfo.setEffectiveDateStart(new Date());

        BusinessRule rule = new BusinessRule();
        rule.setRuleId("3");
        rule.setName("CHEM 100 course prerequisites");
        rule.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule.setSuccessMessage("Test success message");
        rule.setFailureMessage("Test failure message");
        rule.setBusinessRuleType(brType);
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

        BusinessRule newRule = rulesManagementDAO.lookupBusinessRuleUsingRuleId("3");

        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getRuleId(), rule.getRuleId());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
    }

    @Test
    public void testUpdateRule() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingRuleId(ruleId_1);

        YieldValueFunction yvf = new YieldValueFunction();
        yvf.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION);

        LeftHandSide lhs = new LeftHandSide();
        lhs.setYieldValueFunction(yvf);

        RightHandSide rhs = new RightHandSide();
        rhs.setExpectedValue("12.0");

        RuleProposition ruleProposition1 = new RuleProposition();
        ruleProposition1.setComparisonDataType("kual.number");
        ruleProposition1.setName("Px");
        ruleProposition1.setOperator(ComparisonOperator.LESS_THAN);
        ruleProposition1.setRightHandSide(rhs);
        ruleProposition1.setLeftHandSide(lhs);

        RuleElement re1 = new RuleElement();
        re1.setDescription("First element");
        re1.setName("P1");
        re1.setOperation(RuleElementType.PROPOSITION);
        re1.setOrdinalPosition(1);
        re1.setRuleProposition(ruleProposition1);

        
        RuleProposition ruleProposition2 = new RuleProposition();
        ruleProposition2.setComparisonDataType("kual.number");
        ruleProposition2.setName("Py");
        ruleProposition2.setOperator(ComparisonOperator.LESS_THAN);
        ruleProposition2.setRightHandSide(rhs);
        ruleProposition2.setLeftHandSide(lhs);
        
        RuleElement re2 = new RuleElement();
        re2.setDescription("Second element");
        re2.setName("P2");
        re2.setOperation(RuleElementType.PROPOSITION);
        re2.setOrdinalPosition(1);
        re2.setRuleProposition(ruleProposition2);

        
        List<RuleElement> elements = new ArrayList<RuleElement>();
        elements.add(re1);
        elements.add(re2);
        
        rule.setRuleElements(elements);
        rule.setName("New Rule Name");
        
        rulesManagementDAO.updateBusinessRule(rule);

        BusinessRule updatedRule = rulesManagementDAO.lookupBusinessRuleUsingRuleId(rule.getRuleId());
        assertEquals(updatedRule.getName(), rule.getName());
        assertEquals(2, rule.getRuleElements().size());
    }

    @Test
    public void testLookupBusinessRuleUsingRuleId() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingRuleId(ruleId_1);
        assertEquals(BusinessRuleTypeKey.KUALI_CO_REQ.toString(), rule.getBusinessRuleType().getBusinessRuleTypeKey());
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

        assertEquals(2, anchorList.size());
        assertEquals(true, anchorList.contains("CPR 201"));
    }

    @Test
    public void testDeleteRule() {
        BusinessRule rule = rulesManagementDAO.lookupBusinessRuleUsingRuleId(ruleId_2);
        org.junit.Assert.assertTrue(rulesManagementDAO.deleteBusinessRule(rule));
        org.junit.Assert.assertNull(em.find(BusinessRule.class, rule.getId()));
    }
}
