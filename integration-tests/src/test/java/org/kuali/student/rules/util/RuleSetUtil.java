package org.kuali.student.rules.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;

public class RuleSetUtil {
    private final RuleSetTranslatorDroolsImpl generateRuleSet = new RuleSetTranslatorDroolsImpl();

    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();
    
    private final static String PROPOSITION_NAME = "co-requisites";
    private final static String ANCHOR_ID = "TestRuleAnchor";
    private final static String FACT_ID_1 = "fact1";

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

        RuleSet ruleSet = generateRuleSet.createRuleSet(ANCHOR_ID, "TestPackageName", "A package", ruleName,
                functionString, propositionMap, effectiveStartTime, effectiveEndTime);
        return ruleSet;
    }
    
    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
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
