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
package org.kuali.student.rules.performance.translators.drools;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
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
import org.drools.StatefulSession;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;
import org.kuali.student.rules.util.RuleManagementDtoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateRuleSetPerfTest {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(GenerateRuleSetPerfTest.class);
    
    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();

    private final RuleSetTranslatorDroolsImpl generateRuleSet = new RuleSetTranslatorDroolsImpl();

    private final static String PROPOSITION_NAME = "co-requisites";
    private final static String ANCHOR_ID = "TestRuleAnchor";
    private final static String ANCHOR_TYPE_KEY = "KUALI_COURSE";
    private final static String FACT_ID_1 = "fact1";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private RulePropositionDTO getRuleProposition(String criteria, String factId) {
        return getRuleProposition(criteria, YieldValueFunctionType.INTERSECTION.toString(), "1", factId);
    }

    private RulePropositionDTO getRuleProposition(
    		String criteria, 
    		String yieldValueFunctionType,
    		String expectedValue,
    		String factId) {
    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, yieldValueFunctionType);
    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		PROPOSITION_NAME, java.lang.Integer.class.getName(), 
        		ComparisonOperator.EQUAL_TO.toString(), leftSide, rightSide);
        
        FactStructureDTO factStructure = new FactStructureDTO();
        //factStructure.setDataType(java.util.Set.class.getName());
        factStructure.setFactStructureId(factId);
        factStructure.setAnchorFlag(false);

        Map<String,String> definitionVariableMap = new HashMap<String,String>();
        //definitionVariableMap.put(Constants.DEF_CRITERIA_KEY, criteria);
        definitionVariableMap.put("some key", criteria);
        //factStructure.setDefinitionVariableList(definitionVariableMap);
        
        Map<String,String> executionVariableMap = new HashMap<String,String>();
        //executionVariableMap.put(Constants.EXE_FACT_KEY, factKey);
        //factStructure.setExecutionVariableList(executionVariableMap);

        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(factStructure);
        yieldValueFunction.setFactStructureList(factStructureList);
        
        return ruleProp;
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
    }

    private RuleSet createRuleSet(int propositionCount, String ruleName) {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        String functionString = "";
        int start = 65;
        int count = start + propositionCount;
        for(int i=start; i<=count; i++) {
            String id = String.valueOf((char) i);
            // 1 of xxx
            propositionMap.put(id, getRuleProposition(""+(i-start+1),FACT_ID_1));
            functionString += (i==start ? "" : "*") + id; 
        }

        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);

        RuleSet ruleSet = generateRuleSet.createRuleSet(ANCHOR_ID, ANCHOR_TYPE_KEY, "TestPackageName", "A package", ruleName,
                functionString, propositionMap, effectiveStartTime, effectiveEndTime);
        return ruleSet;
    }
    
    private RuleSet createRuleSet(int RuleSetCount) {
        List<RuleSet> ruleSetList = new ArrayList<RuleSet>();
        for(int i=0; i<RuleSetCount; i++) {
            String ruleName = "rulename_"+i;
            RuleSet ruleSet = createRuleSet(5, ruleName);
            ruleSetList.add(ruleSet);
        }

        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet("FullRuleSet", "A description");
        for(RuleSet rs : ruleSetList) {
            for(String header : rs.getHeaderList()) {
                ruleSet.addHeader(header);
            }
            for(Rule rule : rs.getRules()) {
                ruleSet.addRule(rule);
            }
        }
        return ruleSet;
    }
    
    private List<RuleSet> createRuleSetList(int ruleSetCount, int ruleCount) {
        List<RuleSet> ruleSetList = new ArrayList<RuleSet>();
        for(int j=0; j<ruleSetCount; j++) {
            RuleSet ruleSet = createRuleSet(ruleCount);
            ruleSetList.add(ruleSet);
        }
        return ruleSetList;
    }
    
    private Set<String> createFacts(int count) {
        Set<String> set = new HashSet<String>();
        for(int i=1; i<=count; i++) {
        	set.add(String.valueOf(i));
        }
        return set;
    }
    
    private static RuleBase createRuleBase(List<RuleSet> ruleSetList) throws Exception {
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        for(RuleSet ruleSet : ruleSetList) {
            PackageBuilder builder = new PackageBuilder();
            builder.addPackageFromDrl(new StringReader(ruleSet.getContent()));
            Package pkg = builder.getPackage();
            ruleBase.addPackage(pkg);
        }
        return ruleBase;
    }
    
    private static RuleBase createRuleBase(String drl) throws Exception {
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        PackageBuilder builder = new PackageBuilder();
        builder.addPackageFromDrl(new StringReader(drl));
        Package pkg = builder.getPackage();
        ruleBase.addPackage(pkg);
        return ruleBase;
    }

    private void executeStatefulSession(RuleBase ruleBase, FactContainer facts) throws Exception {
        StatefulSession session = null;
        try {
            session = ruleBase.newStatefulSession();
            session.insert(new CurrentDateTime());
            session.insert(facts);
            session.fireAllRules();
        } finally {
            if(session != null) {
                session.dispose();
            }
        }
    }

    private void executeStatelessSession(RuleBase ruleBase, FactContainer facts) throws Exception {
        List<Object> list = new ArrayList<Object>();
        list.add(new CurrentDateTime());
        list.add(facts);
        StatelessSession session = ruleBase.newStatelessSession();
        session.execute(list);
    }

    @Ignore
    @Test
    public void testParseAndExecuteRuleSets_StatelessSession() throws Exception {
        //int[] iterations = new int[] {1, 2, 3};
        // This causes a JVM stack overflow when generating more than 600 rules 
        //int[] iterations = new int[] {1, 5, 10, 20, 50, 100, 150, 200, 250};
        int[] iterations = new int[] {1, 5, 10, 20, 50, 100};
        int c = 0;
        for(int ruleSetCount : iterations) {
            c++;
            int ruleCount = 100;
            int droolsRuleCount = ruleCount * ruleSetCount * 2;
            List<RuleSet> ruleSetList = createRuleSetList(ruleSetCount, ruleCount);
            logger.info(c+": 1-Creating ruleset. RuleSet count=" + ruleSetList.size() +", Rule count=" + droolsRuleCount);

            // Get facts
            String factId1 = "FactId1"; //FactUtil.getFactKey(PROPOSITION_NAME, FACT_ID_1, 0);

            Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
            factMap.put(factId1, createFacts(100));
            FactContainer facts = new FactContainer(""+System.nanoTime(), ANCHOR_ID, ANCHOR_TYPE_KEY, null, factMap);

            long start = System.currentTimeMillis();
            RuleBase ruleBase = createRuleBase(ruleSetList);
            long now = System.currentTimeMillis();
            logger.info(c+": 2-Creating rulebase time: " + ((now - start) / 1000d) + " secs");

            start = System.currentTimeMillis();
            executeStatelessSession(ruleBase, facts);
            now = System.currentTimeMillis();
            logger.info(c+": 3-Executing rules time: " + ((now - start) / 1000d) + " secs");
    
            // Collection of Propositions
            PropositionContainer prop = facts.getPropositionContainer();

            assertTrue(prop.getRuleResult());
            System.gc();
        }
    }

    @Ignore
    @Test
    public void testParseAndExecuteRuleSets_StatefulSession() throws Exception {
        //int[] iterations = new int[] {1, 2, 3};
        // This causes a JVM stack overflow
        // This causes a JVM stack overflow when generating more than 600 rules 
        //int[] iterations = new int[] {1, 5, 10, 20, 50, 100, 150, 200, 250};
        int[] iterations = new int[] {1, 5, 10, 20, 50, 100};
        int c = 0;
        for(int ruleSetCount : iterations) {
            c++;
            int ruleCount = 100;
            int droolsRuleCount = ruleCount * ruleSetCount * 2;
            List<RuleSet> ruleSetList = createRuleSetList(ruleSetCount, ruleCount);
            logger.info(c+": 1-Creating ruleset. RuleSet count=" + ruleSetList.size() +", Rule count=" + droolsRuleCount);

            // Get facts
            String factId1 = "FactId1"; //FactUtil.getFactKey(PROPOSITION_NAME, FACT_ID_1, 0);

            Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
            factMap.put(factId1, createFacts(100));
            FactContainer facts = new FactContainer(""+System.nanoTime(), ANCHOR_ID, ANCHOR_TYPE_KEY, null, factMap);

            long start = System.currentTimeMillis();
            RuleBase ruleBase = createRuleBase(ruleSetList);
            long now = System.currentTimeMillis();
            logger.info(c+": 2-Creating rulebase time: " + ((now - start) / 1000d) + " secs");

            start = System.currentTimeMillis();
            executeStatefulSession(ruleBase, facts);
            now = System.currentTimeMillis();
            logger.info(c+": 3-Executing rules time: " + ((now - start) / 1000d) + " secs");
    
            // Collection of Propositions
            PropositionContainer prop = facts.getPropositionContainer();

            assertTrue(prop.getRuleResult());
            System.gc();
        }
    }
    
    @Ignore
    @Test
    public void testStatelessSession() throws Exception {
    	StringBuilder sb = new StringBuilder();
		sb.append("package org.kuali.Test \n");
    	// Create 1000 rules
		for(int i=0; i<1000; i++) {
    		sb.append("\n");
    		sb.append("rule \"Rule_" + i +"\" \n");
			sb.append("    when \n");
			sb.append("    then \n");
			sb.append("        Thread.sleep(1); \n");
			sb.append("end");
    	}
    			
		long startTime1 = System.nanoTime();
    	RuleBase ruleBase1 = createRuleBase(sb.toString());
		long endTime1 = System.nanoTime();
		double executionTime1 = (endTime1-startTime1)/1000000000d;
		System.out.println("RuleBase Creation Time: "+executionTime1);

    	for(int i=0; i<10; i++) {
    		System.out.println("Creating session...");
    		long startTime2 = System.nanoTime();
    		StatelessSession session = ruleBase1.newStatelessSession();
    		long endTime2 = System.nanoTime();
    		double executionTime2 = (endTime2-startTime2)/1000000000d;
    		System.out.println("Session Creation Time: "+executionTime2);
    		assertTrue(executionTime2<0.01d);
    		//session.execute(new Object());
    	}
    }
}
