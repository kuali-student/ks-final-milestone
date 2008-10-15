/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.translators.drools;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;

public class RuleSetTranslatorPropositionTest {

    private final RuleSetTranslatorDroolsImpl generateRuleSet = new RuleSetTranslatorDroolsImpl();
    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();

    private final static String PROPOSITION_NAME = "co-requisites";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private RulePropositionDTO getRuleProposition(String criteria, String factId) {
    	return getRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"1", 
    			ComparisonOperator.EQUAL_TO.toString(), 
    			criteria, 
    			factId,
    			String.class.getName());
    }
    
    private RulePropositionDTO getRuleProposition(
    		String yieldValueFunctionType,
    		String expectedValue,
    		String comparisonOperator,
    		String criteria, 
    		String factId,
    		String comparisonOperatorType) {
    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, yieldValueFunctionType);
    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		PROPOSITION_NAME, comparisonOperatorType, 
        		comparisonOperator, leftSide, rightSide);
        
        FactStructureDTO factStructure = new FactStructureDTO();
        factStructure.setDataType(java.util.Set.class.getName());
        factStructure.setFactStructureId(factId);
        factStructure.setAnchorFlag(false);

        Map<String,String> definitionVariableMap = new HashMap<String,String>();
        definitionVariableMap.put("some identifier - assume only one key", criteria);
        factStructure.setDefinitionVariableList(definitionVariableMap);
        
        Map<String,String> executionVariableMap = new HashMap<String,String>();
        //executionVariableMap.put(Constants.EXE_FACT_KEY, factKey);
        factStructure.setExecutionVariableList(executionVariableMap);

        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(factStructure);
        yieldValueFunction.setFactStructureList(factStructureList);
        
        return ruleProp;
    }

    private static RuleBase getRuleBase(String source) throws Exception {
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl(new StringReader(source));

        Package pkg = builder.getPackage();

        // Add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(pkg);
        return ruleBase;
    }

    private void executeRule(String source, FactContainer fact) throws Exception {
        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase(source).newStatefulSession();
        workingMemory.insert(new CurrentDateTime());
    	workingMemory.insert(fact);
        workingMemory.fireAllRules();
    }

    private Set<String> createSet(String list) {
        Set<String> set = new HashSet<String>();
        for( String s : list.split(",") ) {
        	set.add(s.trim());
        }
        return set;
    }
    
    private List<BigDecimal> createList(String list) {
    	List<BigDecimal> set = new ArrayList<BigDecimal>();
        for( String s : list.split(",") ) {
        	set.add(new BigDecimal(s.trim()));
        }
        return set;
    }
    
    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
    }

    private RuleSet createRuleSet(String anchor, String packageName, String description, 
			 String ruleName, String functionString,
			 Map<String, RulePropositionDTO> propositionMap) {
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	RuleSet ruleSet = this.generateRuleSet.createRuleSet(anchor, 
        		"TestPackageName", "A package", "TestRuleName", functionString, propositionMap,
        		effectiveStartTime, effectiveEndTime);

    	return ruleSet;
    }
    
    @Test
    public void testParseRuleSet_OneProposition() throws Exception {
    	String factId1 = "fact1";
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101",factId1));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "P1", propositionMap);
        // Get facts
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_TwoProposition_AandB() throws Exception {
    	String factId1 = "fact1";
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
    	// 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101",factId1));
        // 1 of CPR 101
        propositionMap.put("P2", getRuleProposition("MATH102",factId1));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "P1*P2", propositionMap);
        // Get facts
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_TwoProposition_AorB() throws Exception {
    	String factId1 = "fact1";
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
    	// 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101",factId1));
        // 1 of CPR 101
        propositionMap.put("P2", getRuleProposition("MATH102",factId1));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId,  "TestPackageName", 
        		"A package", "TestRuleName", "P1+P2", propositionMap);
        // Get facts
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();
        
        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_ThreeProposition_AandBorC() throws Exception {
    	String factId1 = "fact1";
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101",factId1));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102",factId1));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101",factId1));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "(P1*P2)+P3", propositionMap);
        // Get facts
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    /**
     * <p>Due to a Drools bug, test case will fail if using version 1 of the 
     * RuleTemplate***-v1.vm templates which has the form:</p>
     * <pre>
     * when 
     *     FactContainer( id == "TestRuleName", state == FactContainer.State.DONE, prop : propositionContainer)
     *     (
     *         Proposition( propositionName == "P1" && result == true ) from prop.propositions
     *         or
     *         Proposition( propositionName == "P2" && result == true ) from prop.propositions 
     *     )
     *     and
     *         Proposition( propositionName == "P3" && result == true ) from prop.propositions
     * then
     * </pre>
     * 
     * <p>Version 2 of the template solves the problem by using the 
     * <b>exists</b> Drools keyword.</p>
     * <pre>
     * when
     *     factContainer : FactContainer( id == "TestRuleName", state == FactContainer.State.DONE, prop : propositionContainer)
     *     P1 : Proposition( propositionName == "P1-TestRuleName" )
     *     P2 : Proposition( propositionName == "P2-TestRuleName" )
     *     P3 : Proposition( propositionName == "P3-TestRuleName" )
     *     exists
     *     (
     *         (
     *             Proposition( propositionName == "P1-TestRuleName" && result == true )
     *             ||
     *             Proposition( propositionName == "P2-TestRuleName" && result == true )
     *         )
     *         && Proposition( propositionName == "P3-TestRuleName" && result == true )
     *     )
     * then
     * </pre>
     */
    @Test
    //@Ignore
    public void testParseRuleSet_ThreeProposition_AorBandC() throws Exception {
    	String factId1 = "fact1";
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101",factId1));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH100",factId1));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM100",factId1));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "(P1+P2)*P3", propositionMap);
        // Get facts
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AorBandCorD() throws Exception {
    	String factId1 = "fact1";
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101",factId1));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102",factId1));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101",factId1));
        // 1 of CHEM102
        propositionMap.put("P4", getRuleProposition("CHEM999",factId1));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", "A package", "TestRuleName", "(P1+P2)*(P3+P4)", propositionMap);
        // Get facts
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM101,CHEM102");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AandBandCorD_SubsetIntersectionSumAverage() throws Exception {
    	String factId1 = "fact1.intersection";
    	String factId2 = "fact2.sum";
    	String factId3 = "fact3.average";

        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition(
        		YieldValueFunctionType.SUBSET.toString(),
        		null,
        		null,
        		"CPR101",
        		factId1,
        		null));
        // 1 of CPR101
        propositionMap.put("P2", getRuleProposition(
        		YieldValueFunctionType.INTERSECTION.toString(),
        		"1",
        		ComparisonOperator.EQUAL_TO.toString(),
        		"CPR101",
        		factId1,
        		null));
        // Sum of credits = 12.0
        propositionMap.put("P3", getRuleProposition(
        		YieldValueFunctionType.SUM.toString(),
        		"12.0",
        		ComparisonOperator.EQUAL_TO.toString(),
        		null,
        		factId2,
        		BigDecimal.class.getName()));
        // Average >= 75.0
        propositionMap.put("P4", getRuleProposition(
        		YieldValueFunctionType.AVERAGE.toString(),
        		"75.0",
        		ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
        		null,
        		factId3,
        		BigDecimal.class.getName()));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "(P1*P2)+P3", propositionMap);
        Map<String,Object> factMap = new HashMap<String,Object>();
        
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts for intersection
        Set<String> courseSetIntersection = createSet("CPR101,MATH102,CHEM101,CHEM102");
        factMap.put(factId1, courseSetIntersection);

        factId2 = FactUtil.getFactKey(PROPOSITION_NAME, factId2, 0);
        // Get facts for sum
        List<BigDecimal> courseSetSum = createList("3.0,6.0,3.0");
        factMap.put(factId2, courseSetSum);

        factId3 = FactUtil.getFactKey(PROPOSITION_NAME, factId3, 0);
        // Get facts for average
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        factMap.put(factId3, courseSet);

        FactContainer facts =  new FactContainer(anchorId, factMap);
        
        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AandBandCandDandEandF_SubsetIntersectionSumAverage() throws Exception {
    	String factId1 = "fact1.intersection";
    	String factId2 = "fact2.sum";
    	String factId3 = "fact3.average";

    	Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition(
        		YieldValueFunctionType.SUBSET.toString(),
        		null,
        		null,
        		"CPR101",
        		factId1,
        		null));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition(
        		YieldValueFunctionType.INTERSECTION.toString(),
        		"1",
        		ComparisonOperator.EQUAL_TO.toString(),
        		"MATH102",
        		factId1,
        		null));

        // Sum of credits = 12.0
        propositionMap.put("P3", getRuleProposition(
        		YieldValueFunctionType.SUM.toString(),
        		"12.0",
        		ComparisonOperator.EQUAL_TO.toString(),
        		null,
        		factId2,
        		BigDecimal.class.getName()));
        // Sum of credits >= 12.0
        propositionMap.put("P4", getRuleProposition(
        		YieldValueFunctionType.SUM.toString(),
        		"9.0",
        		ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
        		null,
        		factId2,
        		BigDecimal.class.getName()));

        // Average > 75.0
        propositionMap.put("P5", getRuleProposition(
        		YieldValueFunctionType.AVERAGE.toString(),
        		"75.0",
        		ComparisonOperator.GREATER_THAN.toString(),
        		null,
        		factId3,
        		BigDecimal.class.getName()));
        // Average >= 80.0
        propositionMap.put("P6", getRuleProposition(
        		YieldValueFunctionType.AVERAGE.toString(),
        		"80.0",
        		ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
        		null,
        		factId3,
        		BigDecimal.class.getName()));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "P1*P2*P3*P4*P5*P6", propositionMap);
        Map<String,Object> factMap = new HashMap<String,Object>();
        
        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts for intersection
        Set<String> courseSetIntersection = createSet("CPR101,MATH102,CHEM101,CHEM102");
        factMap.put(factId1, courseSetIntersection);

        factId2 = FactUtil.getFactKey(PROPOSITION_NAME, factId2, 0);
        // Get facts for sum
        List<BigDecimal> courseSetSum = createList("3.0,6.0,3.0");
        factMap.put(factId2, courseSetSum);

        factId3 = FactUtil.getFactKey(PROPOSITION_NAME, factId3, 0);
        // Get facts for average
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        factMap.put(factId3, courseSet);

        FactContainer facts =  new FactContainer(anchorId, factMap);
        
        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

}
