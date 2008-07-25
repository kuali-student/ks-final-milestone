package org.kuali.student.rules.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.LeftHandSide;
import org.kuali.student.rules.internal.common.entity.Operator;
import org.kuali.student.rules.internal.common.entity.RightHandSide;
import org.kuali.student.rules.internal.common.entity.RuleProposition;
import org.kuali.student.rules.internal.common.entity.YieldValueFunction;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.facts.CourseEnrollmentRequest;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.translators.drools.GenerateRuleSet;

public class RuleSetUtil {
    private final GenerateRuleSet generateRuleSet = GenerateRuleSet.getInstance();

    /**
     * Private constructor.
     */
    private RuleSetUtil() {
    }

    /**
     * Returns a new instance of <code>RuleSetUtil</code>
     * 
     * @return A new instance of <code>RuleSetUtil</code>
     */
    public static RuleSetUtil getInstance() {
        return new RuleSetUtil();
    }
    
    /**
     * Asserts whether <code>ruleSet1</code> equals <code>ruleSet2</code>. 
     * 
     * @param ruleSet1 Expected rule set
     * @param ruleSet2 Actual rule set
     * @throws Exception
     */
    public void assertRuleSetEquals( RuleSet ruleSet1 , RuleSet ruleSet2) {
        assertEquals( ruleSet1.getName(), ruleSet2.getName() );
        assertEquals( ruleSet1.getDescription(), ruleSet2.getDescription() );
        assertEquals( ruleSet1.getHeader(), ruleSet2.getHeader() );
    }

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
    
    public RuleSet createRuleSet(int RuleSetCount) {
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
    
    public List<RuleSet> createRuleSetList(int ruleSetCount, int ruleCount) {
        List<RuleSet> ruleSetList = new ArrayList<RuleSet>();
        for(int j=0; j<ruleSetCount; j++) {
            RuleSet ruleSet = createRuleSet(ruleCount);
            ruleSetList.add(ruleSet);
        }
        return ruleSetList;
    }
}
