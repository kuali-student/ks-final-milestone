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
import java.util.Arrays;
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
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.LeftHandSide;
import org.kuali.student.rules.internal.common.entity.Operator;
import org.kuali.student.rules.internal.common.entity.RightHandSide;
import org.kuali.student.rules.internal.common.entity.RuleProposition;
import org.kuali.student.rules.internal.common.entity.YieldValueFunction;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.facts.CourseEnrollmentRequest;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.translators.drools.GenerateRuleSet;
import org.kuali.student.rules.util.FactContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateRuleSetPerfTest {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(GenerateRuleSetPerfTest.class);
    
    private final GenerateRuleSet generateRuleSet = GenerateRuleSet.getInstance();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private RuleProposition getRuleProposition(String criteria) {
        return getRuleProposition(criteria, YieldValueFunctionType.INTERSECTION, "1");
    }

    private RuleProposition getRuleProposition(String criteria, YieldValueFunctionType functionType) {
        return getRuleProposition(criteria, functionType, "1");
    }

    private RuleProposition getRuleProposition(String criteria, YieldValueFunctionType functionType, String expectedValue) {
        // E.g. 1 of CPR 101
        //YieldValueFunction yieldValueFunction = new YieldValueFunction("1", YieldValueFunctionType.INTERSECTION);
        YieldValueFunction yieldValueFunction = new YieldValueFunction("1", functionType);
        LeftHandSide leftSide = new LeftHandSide(criteria, yieldValueFunction);
        Operator operator = new Operator(ComparisonOperator.EQUAL_TO);
        //RightHandSide rightSide = new RightHandSide("1");
        RightHandSide rightSide = new RightHandSide(expectedValue);
        RuleProposition ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);

        return ruleProp;
    }

    private CourseEnrollmentRequest getCourseEnrollmentRequest(String anchorId, String luiIds) {
        CourseEnrollmentRequest request = new CourseEnrollmentRequest(anchorId);
        Set<String> luiIdSet = new HashSet<String>(Arrays.asList(luiIds.split(",")));
        request.setLuiIds(luiIdSet);
        return request;
    }

    private RuleSet createRuleSet(int propositionCount, String ruleName) {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        String functionString = "";
        int start = 65;
        int count = start + propositionCount;
        for(int i=start; i<=count; i++) {
            String id = String.valueOf((char) i);
            // 1 of xxx
            propositionMap.put(id, getRuleProposition(""+(i-start)));
//            for(YieldValueFunctionType type : YieldValueFunctionType.values()) {
//                propositionMap.put(id, getRuleProposition(""+(i-start), type));
//            }
            functionString += (i==start ? "" : "*") + id; 
        }
        RuleSet ruleSet = generateRuleSet.createRuleSet("TestPackageName", "A package", ruleName,
                functionString, propositionMap);
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
    
    private String createLuiIds(int count) {
        String luiIds = "0";
        for(int i=1; i<=count; i++) {
            luiIds += "," + i;
        }
        return luiIds;
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

    private void executeStatefulSession(RuleBase ruleBase, FactContainer facts) throws Exception {
        StatefulSession session = null;
        try {
            session = ruleBase.newStatefulSession();
            session.insert(facts);
            session.fireAllRules();
        } finally {
            if(session != null) {
                session.dispose();
            }
        }
    }

    private void executeStatelessSession(RuleBase ruleBase, FactContainer facts) throws Exception {
        StatelessSession session = ruleBase.newStatelessSession();
        session.execute(facts);
    }

    @Test
    public void testParseAndExecuteRuleSets() throws Exception {
        int[] iterations = new int[] {1, 2, 3};
        //int[] iterations = new int[] {1, 5, 10, 20, 50, 100, 150, 200, 250};
        int c = 0;
        for(int ruleSetCount : iterations) {
            c++;
            int ruleCount = 100;
            int droolsRuleCount = ruleCount * ruleSetCount * 2;
            List<RuleSet> ruleSetList = createRuleSetList(ruleSetCount, ruleCount);
            logger.info(c+": 1-Creating ruleset. RuleSet count=" + ruleSetList.size() +", Rule count=" + droolsRuleCount);
    
            String luiIds = createLuiIds(100);

            // Get facts
            String id = "rulename_1";
            CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, luiIds);
            FactContainer facts = new FactContainer(id, request);

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
        }
    }
}
