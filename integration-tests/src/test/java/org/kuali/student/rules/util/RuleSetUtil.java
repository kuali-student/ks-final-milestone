package org.kuali.student.rules.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;

public class RuleSetUtil {
    private final RuleSetTranslatorDroolsImpl generateRuleSet = new RuleSetTranslatorDroolsImpl();

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

    private RulePropositionDTO getRuleProposition(String criteria) {
        return getRuleProposition(criteria, YieldValueFunctionType.INTERSECTION, "1");
    }

    private RulePropositionDTO getRuleProposition(String criteria, YieldValueFunctionType functionType) {
        return getRuleProposition(criteria, functionType, "1");
    }

    private RulePropositionDTO getRuleProposition(String criteria, YieldValueFunctionType functionType, String expectedValue) {
        // E.g. 1 of CPR 101
        //YieldValueFunction yieldValueFunction = new YieldValueFunction("1", YieldValueFunctionType.INTERSECTION);
        YieldValueFunctionDTO yieldValueFunction = new YieldValueFunctionDTO();
        yieldValueFunction.setYieldValueFunctionType(functionType.toString());
        
        LeftHandSideDTO leftSide = new LeftHandSideDTO();
        leftSide.setYieldValueFunction(yieldValueFunction);
                
        // where did criteria go?
        //LeftHandSide leftSide = new LeftHandSide(criteria, yieldValueFunction);
        
        //Operator operator = new Operator(ComparisonOperator.EQUAL_TO);
        //RightHandSide rightSide = new RightHandSide("1");
        RightHandSideDTO rightSide = new RightHandSideDTO();
        rightSide.setExpectedValue(expectedValue);
        
        RulePropositionDTO ruleProp = new RulePropositionDTO();
        ruleProp.setComparisonDataType("kuali.number");
        ruleProp.setComparisonOperatorType(ComparisonOperator.EQUAL_TO.toString());
        ruleProp.setLeftHandSide(leftSide);
        ruleProp.setRightHandSide(rightSide);
        ruleProp.setFailureMessage("prop error message");
        ruleProp.setDescription("enumeration of required co-requisite courses");
        ruleProp.setName("co-requisites");
        
        return ruleProp;
    }

    /* No longer used
    private CourseEnrollmentRequest getCourseEnrollmentRequest(String anchorId, String luiIds) {
        CourseEnrollmentRequest request = new CourseEnrollmentRequest(anchorId);
        Set<String> luiIdSet = new HashSet<String>(Arrays.asList(luiIds.split(",")));
        request.setLuiIds(luiIdSet);
        return request;
    }*/

    private RuleSet createRuleSet(int propositionCount, String ruleName) {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
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
        RuleSet ruleSet = generateRuleSet.createRuleSet("AnchorName", "TestPackageName", "A package", ruleName, functionString, propositionMap, new Date(), new Date());
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
