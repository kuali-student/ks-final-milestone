package org.kuali.student.rules.ruleexecution.runtime.drools.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleDTO;
import org.kuali.student.rules.repository.dto.RuleSetDTO;

public class DroolsTestUtil {

    /**
     * Gets a simple rule. 
     * First rule determines whether the minutes of the hour is even.
     * Rule takes <code>java.util.Calendar</code> as fact (package import).
     * 
     * @param ruleName Rule name
     * @return a Drools rule
     */
	private static String getSimpleRule1(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsEven" : ruleName);
        return 
			"rule \"" + ruleName + "\"\n" +
			"     when\n" +
			"          now: Calendar()\n" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )\n" +
			"          msg: Message()\n" +
			"     then\n" +
			//"          insert( \"Minute is even: \" + now.get(Calendar.MINUTE) );" +
			"          msg.setMessage(\"Minute is even: \" + now.get(Calendar.MINUTE) );\n" +
			"end\n";
	}

	/**
	 * Gets a simple rule. 
	 * Rule determines whether the minutes of the hour is odd.
	 * Rule takes <code>java.util.Calendar</code> as fact (package import).
	 *  
	 * @return a Drools rule
	 */
	private static String getSimpleRule2(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsOdd" : ruleName);
		return 
            "rule \"" + ruleName + "\"\n" +
			"     when\n" +
			"          now: Calendar()\n" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )\n" +
			"          msg: Message()\n" +
			"     then\n" +
			//"          insert( \"Minute is odd: \" + now.get(Calendar.MINUTE) );" +
			"          msg.setMessage(\"Minute is odd: \" + now.get(Calendar.MINUTE) );\n" +
			"end\n";
	}

	public static RuleSetDTO createRuleSet() {
    	RuleSetDTO ruleSet = new RuleSetDTO("TestName", "Test description", "DRL");
    	ruleSet.addHeader("import java.util.Calendar;");
    	ruleSet.addHeader("import org.kuali.student.rules.ruleexecution.runtime.drools.util.Message;");
    	
    	RuleDTO rule1 = new RuleDTO("rule1", "A rule", getSimpleRule1("rule1"), "DRL");
    	RuleDTO rule2 = new RuleDTO("rule2", "A rule", getSimpleRule2("rule2"), "DRL");
    	ruleSet.addRule(rule1);
    	ruleSet.addRule(rule2);
    	ruleSet.setContent(ruleSet.buildContent());

    	return ruleSet;
    }
	
	public static byte[] createKnowledgePackage(RuleSetDTO ruleSet) throws Exception {
    	KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    	StringReader reader = new StringReader(ruleSet.getContent());
    	Resource resource = ResourceFactory.newReaderResource(reader);
    	builder.add(resource, ResourceType.DRL);
        Collection<KnowledgePackage> pkgs = builder.getKnowledgePackages();
		return DroolsUtil.deserialize( pkgs.iterator().next() );
	}

	public static FactResultDTO createFactResult(String[] values) {
		FactResultDTO factResult = new FactResultDTO();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for (String item : values) {
			Map<String, String> row = new HashMap<String, String>();
			row.put("column1", item);
			resultList.add(row);
		}

		factResult.setResultList(resultList);
		return factResult;
	}

	public static FactResultTypeInfoDTO createColumnMetaData(String dataType) {
    	Map<String, FactResultColumnInfoDTO> columnsInfoMap = new HashMap<String, FactResultColumnInfoDTO>();
    	FactResultColumnInfoDTO columnInfo = new FactResultColumnInfoDTO();
    	columnInfo.setKey("column1");
    	columnInfo.setDataType(dataType);
    	columnsInfoMap.put(columnInfo.getKey(), columnInfo);
    	FactResultTypeInfoDTO typeInfo = new FactResultTypeInfoDTO();
    	typeInfo.setResultColumnsMap(columnsInfoMap);
    	return typeInfo;
    }

	public static RuleSetDTO getAverageIntersectionPropositionRuleSet() {
    	RuleSetDTO ruleSet = new RuleSetDTO("TestName", "Test description", "DRL");
    	// Rule set name and Drools package name must match
		String drl = 
		"package TestName \n" +
		"import java.util.*; \n" +
		"import java.math.BigDecimal; \n" +
		"import org.slf4j.Logger; \n" +
		"import org.slf4j.LoggerFactory; \n" +
		"import org.kuali.student.rules.internal.common.entity.*; \n" +
		"import org.kuali.student.rules.internal.common.statement.propositions.*; \n" +
		"import org.kuali.student.rules.internal.common.statement.propositions.rules.*; \n" +
		"import org.kuali.student.rules.rulemanagement.dto.*; \n" +
		"import org.kuali.student.rules.util.FactContainer; \n" +
		"import org.kuali.student.rules.util.FactContainer.State; \n" +
		"import org.kuali.student.rules.util.CurrentDateTime; \n" +
		"import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil; \n" +
		"\n" +
		"rule \"CPR101_INIT\" \n" +
		"    no-loop true \n" +
		"    lock-on-active true \n" +
		"\n" +
		"    when \n" + 
        "          container : FactContainer( anchor == \"CPR101\", state == FactContainer.State.INIT, propositionMap : propositionMap, prop : propositionContainer, factMap : factMap ) \n" +
		//"          //Only run rule when: currentDate >= effectiveStartTime AND currentDate < effectiveEndTime \n" +
		//"          CurrentDateTime( currentDateAsLong >= 20000101120010 && currentDateAsLong < 20010101120010 ) \n" +
		"    then \n" +
		"\n" +
		"        prop.setFunctionalRuleString(\"P1*P2\"); \n" +
		"\n" +
		"        String uuidP1 = \"P1-1fbc3af5-6b2f-445e-be85-719669e88dc3\"; \n" +
		"        RulePropositionDTO rulePropositionP1 = (RulePropositionDTO) propositionMap.get(\"P1\"); \n" +
		"\n" +
		"        AverageRuleProposition<java.math.BigDecimal> propositionP1 = new AverageRuleProposition<java.math.BigDecimal>( \n" +
		"            uuidP1, \"P1\", rulePropositionP1, factMap ); \n" +
		"        propositionP1.apply(); \n" +
		"        prop.addProposition(propositionP1); \n" +
		"        insert(propositionP1.getProposition()); \n" +
		"\n" +
		"        String uuidP2 = \"P2-a46c7596-8052-4d83-bcc0-da4744436ed3\"; \n" +
		"        RulePropositionDTO rulePropositionP2 = (RulePropositionDTO) propositionMap.get(\"P2\"); \n" +
		"\n" +
		"        IntersectionRuleProposition<java.lang.Integer> propositionP2 = new IntersectionRuleProposition<java.lang.Integer>( \n" +
		"            uuidP2, \"P2\", rulePropositionP2, factMap ); \n" +
		"        propositionP2.apply(); \n" +
		"        prop.addProposition(propositionP2); \n" +
		"        insert(propositionP2.getProposition()); \n" +
		
		"end \n" +

		"rule \"CPR101\" \n" +
		"    when \n" +
        "          container : FactContainer( anchor == \"CPR101\", state == FactContainer.State.INIT, propositionMap : propositionMap, prop : propositionContainer, factMap : factMap ) \n" +
		
		"        P1 : Proposition( id == \"P1-1fbc3af5-6b2f-445e-be85-719669e88dc3\" ) \n" +
		"        P2 : Proposition( id == \"P2-a46c7596-8052-4d83-bcc0-da4744436ed3\" ) \n" +
		"\n" +
		"        exists \n" +
		"        ( \n" +
		"            Proposition( id == \"P1-1fbc3af5-6b2f-445e-be85-719669e88dc3\" && result == true ) \n" +
		"            && \n" +
		"            Proposition( id == \"P2-a46c7596-8052-4d83-bcc0-da4744436ed3\" && result == true ) \n" +
		"        ) \n" +
		"    then \n" +
		"        prop.setRuleResult(true); \n" +
		//"        retract(container); \n" +
		"        retract(P1); \n" +
		"        retract(P2); \n" +
		"end \n";

    	ruleSet.setContent(drl);
    	return ruleSet;
	}

	public static RuleSetDTO getAveragePropositionRuleSet() {
    	RuleSetDTO ruleSet = new RuleSetDTO("AverageProposition", "A description", "DRL");
    	// Rule set name and Drools package name must match
		String drl = 
		"package AverageProposition \n" +
		"import java.util.*; \n" +
		"import java.math.BigDecimal; \n" +
		"import org.slf4j.Logger; \n" +
		"import org.slf4j.LoggerFactory; \n" +
		"import org.kuali.student.rules.internal.common.entity.*; \n" +
		"import org.kuali.student.rules.internal.common.statement.propositions.*; \n" +
		"import org.kuali.student.rules.internal.common.statement.propositions.rules.*; \n" +
		"import org.kuali.student.rules.rulemanagement.dto.*; \n" +
		"import org.kuali.student.rules.util.FactContainer; \n" +
		"import org.kuali.student.rules.util.FactContainer.State; \n" +
		"import org.kuali.student.rules.util.CurrentDateTime; \n" +
		"import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil; \n" +
		"\n" +
		"rule \"CPR201_INIT\" \n" +
		"    no-loop true \n" +
		"    lock-on-active true \n" +
		"\n" +
		"    when \n" + 
        "          container : FactContainer( anchor == \"CPR201\", state == FactContainer.State.INIT, propositionMap : propositionMap, prop : propositionContainer, factMap : factMap ) \n" +
		//"          //Only run rule when: currentDate >= effectiveStartTime AND currentDate < effectiveEndTime \n" +
		//"          CurrentDateTime( currentDateAsLong >= 20000101120010 && currentDateAsLong < 20010101120010 ) \n" +
		"    then \n" +
		"\n" +
		"        prop.setFunctionalRuleString(\"P1\"); \n" +
		"\n" +
		"        String uuidP1 = \"P1-1fbc3af5-6b2f-445e-be85-719669e88dc3\"; \n" +
		"        RulePropositionDTO rulePropositionP1 = (RulePropositionDTO) propositionMap.get(\"P1\"); \n" +
		"\n" +
		"        AverageRuleProposition<java.math.BigDecimal> propositionP1 = new AverageRuleProposition<java.math.BigDecimal>( \n" +
		"            uuidP1, \"P1\", rulePropositionP1, factMap ); \n" +
		"        propositionP1.apply(); \n" +
		"        prop.addProposition(propositionP1); \n" +
		"        insert(propositionP1.getProposition()); \n" +
		
		"end \n" +

		"rule \"CPR201\" \n" +
		"    when \n" +
        "          container : FactContainer( anchor == \"CPR201\", state == FactContainer.State.INIT, propositionMap : propositionMap, prop : propositionContainer, factMap : factMap ) \n" +
		
		"        P1 : Proposition( id == \"P1-1fbc3af5-6b2f-445e-be85-719669e88dc3\" ) \n" +
		"\n" +
		"        exists \n" +
		"        ( \n" +
		"            Proposition( id == \"P1-1fbc3af5-6b2f-445e-be85-719669e88dc3\" && result == true ) \n" +
		"        ) \n" +
		"    then \n" +
		"        prop.setRuleResult(true); \n" +
		"        retract(P1); \n" +
		"end \n";

    	ruleSet.setContent(drl);
    	return ruleSet;
	}

	public static RuleSetDTO getIntersectionPropositionRuleSet() {
    	RuleSetDTO ruleSet = new RuleSetDTO("IntersectionProposition", "A description", "DRL");
    	// Rule set name and Drools package name must match
		String drl = 
		"package IntersectionProposition \n" +
		"import java.util.*; \n" +
		"import java.math.BigDecimal; \n" +
		"import org.slf4j.Logger; \n" +
		"import org.slf4j.LoggerFactory; \n" +
		"import org.kuali.student.rules.internal.common.entity.*; \n" +
		"import org.kuali.student.rules.internal.common.statement.propositions.*; \n" +
		"import org.kuali.student.rules.internal.common.statement.propositions.rules.*; \n" +
		"import org.kuali.student.rules.rulemanagement.dto.*; \n" +
		"import org.kuali.student.rules.util.FactContainer; \n" +
		"import org.kuali.student.rules.util.FactContainer.State; \n" +
		"import org.kuali.student.rules.util.CurrentDateTime; \n" +
		"import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil; \n" +
		"\n" +
		"rule \"CPR103_INIT\" \n" +
		"    no-loop true \n" +
		"    lock-on-active true \n" +
		"\n" +
		"    when \n" + 
        "          container : FactContainer( anchor == \"CPR301\", state == FactContainer.State.INIT, propositionMap : propositionMap, prop : propositionContainer, factMap : factMap ) \n" +
		//"          //Only run rule when: currentDate >= effectiveStartTime AND currentDate < effectiveEndTime \n" +
		//"          CurrentDateTime( currentDateAsLong >= 20000101120010 && currentDateAsLong < 20010101120010 ) \n" +
		"    then \n" +
		"\n" +
		"        prop.setFunctionalRuleString(\"P1\"); \n" +
		"\n" +
		"        String uuidP1 = \"P1-a46c7596-8052-4d83-bcc0-da4744436ed3\"; \n" +
		"        RulePropositionDTO rulePropositionP1 = (RulePropositionDTO) propositionMap.get(\"P1\"); \n" +
		"\n" +
		"        IntersectionRuleProposition<java.lang.Integer> propositionP1 = new IntersectionRuleProposition<java.lang.Integer>( \n" +
		"            uuidP1, \"P1\", rulePropositionP1, factMap ); \n" +
		"        propositionP1.apply(); \n" +
		"        prop.addProposition(propositionP1); \n" +
		"        insert(propositionP1.getProposition()); \n" +
		"end \n" +

		"rule \"CPR301\" \n" +
		"    when \n" +
        "          container : FactContainer( anchor == \"CPR301\", state == FactContainer.State.INIT, propositionMap : propositionMap, prop : propositionContainer, factMap : factMap ) \n" +
		
		"        P1 : Proposition( id == \"P1-a46c7596-8052-4d83-bcc0-da4744436ed3\" ) \n" +
		"\n" +
		"        exists \n" +
		"        ( \n" +
		"            Proposition( id == \"P1-a46c7596-8052-4d83-bcc0-da4744436ed3\" && result == true ) \n" +
		"        ) \n" +
		"    then \n" +
		"        prop.setRuleResult(true); \n" +
		"        retract(P1); \n" +
		"end \n";

    	ruleSet.setContent(drl);
    	return ruleSet;
	}
}
