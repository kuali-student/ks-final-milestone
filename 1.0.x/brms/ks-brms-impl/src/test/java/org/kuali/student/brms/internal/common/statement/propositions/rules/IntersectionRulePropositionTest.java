/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.internal.common.statement.propositions.rules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.statement.propositions.rules.IntersectionRuleProposition;
import org.kuali.student.brms.internal.common.statement.report.PropositionReport;
import org.kuali.student.brms.internal.common.utils.FactUtil;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;

public class IntersectionRulePropositionTest {

	private final static String velocityTemplate = 
		"#if( $prop_intersection_diff_set == 0 && $prop_comparison_operator_str == 'NOT_EQUAL_TO' )" +
			"Found $prop_intersection_met_set.size() course(s) $prop_intersection_met_set but expected not $prop_expected_value_str" +
		"#elseif( $prop_intersection_diff_set < 0 )" +
			"#if( $prop_comparison_operator_str == 'EQUAL_TO' )" +
				"Found $prop_intersection_met_set.size() course(s) $prop_intersection_met_set but expected only $prop_expected_value_str" +
			"#elseif( $prop_comparison_operator_str == 'LESS_THAN_OR_EQUAL_TO' )" +
				"Found $prop_intersection_met_set.size() course(s) $prop_intersection_met_set but expected only $prop_expected_value_str or less" +
			"#elseif( $prop_comparison_operator_str == 'LESS_THAN' )" +
				"Found $prop_intersection_met_set.size() course(s) $prop_intersection_met_set but expected less than $prop_expected_value_str" +
			"#end" +
		"#elseif( $mathTool.toNumber($prop_expected_value_str) > $prop_intersection_met_set.size() )" +
			"#if( $prop_comparison_operator_str == 'GREATER_THAN_OR_EQUAL_TO' )" +
				"Found $prop_intersection_met_set.size() course(s) $prop_intersection_met_set but expected $prop_expected_value_str or more" +
			"#elseif( $prop_comparison_operator_str == 'GREATER_THAN' )" +
				"Found $prop_intersection_met_set.size() course(s) $prop_intersection_met_set but expected more than $prop_expected_value_str" +
			"#end" +
		"#else" +
			"$prop_intersection_diff_set of $prop_intersection_unmet_set is still required" +
		"#end";
		
    public Map<String, Object> getFactMap(FactStructureInfo fs1, FactStructureInfo fs2, String column) {
    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(fs1);
    	String factKeyIntersection = FactUtil.createFactKey(fs2);

    	FactResultTypeInfo columnMetaData1 = CommonTestUtil.createColumnMetaData(String.class.getName(), column);

    	FactResultInfo factResultCriteria = CommonTestUtil.createFactResult(new String[] {"CPR101","CHEM101"}, column);
    	factResultCriteria.setFactResultTypeInfo(columnMetaData1);

        FactResultInfo factResult = CommonTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKeyIntersection, factResultCriteria);
        factMap.put(factKeyIntersection, factResult);
        
        return factMap;
    }
    
    private FactStructureInfo createStaticFactStructure(String factStructureId, String criteriaTypeName, String facts) {
		FactStructureInfo fs = CommonTestUtil.createFactStructure(factStructureId, criteriaTypeName);
		fs.setStaticFact(true);
		fs.setStaticValueDataType(String.class.getName());
		fs.setStaticValue(facts);
		return fs;
    }

	@Test
	public void testIntersectionProposition_StaticFact_Success() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = createStaticFactStructure("fact.id.1", "course.intersection.criteria", "CPR101,CHEM101");
		FactStructureInfo fs2 = createStaticFactStructure("fact.id.2", "course.intersection.fact", "CPR101,MATH101,CHEM101");
			
		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "2", ComparisonOperator.EQUAL_TO.toString());

		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals("Intersection constraint fulfilled", report.getMessage());

		FactResultInfo criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(2, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CHEM101"));

		FactResultInfo factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "MATH101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CHEM101"));

		FactResultInfo propositionResult = report.getPropositionResult();
        Assert.assertEquals(2, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CHEM101"));
	}

	@Test
	public void testIntersectionProposition_StaticFact_Failure_Equals() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = createStaticFactStructure("fact.id.1", "course.intersection.criteria", "CPR101,MATH101,CHEM101");
		FactStructureInfo fs2 = createStaticFactStructure("fact.id.2", "course.intersection.fact", "CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "2", ComparisonOperator.EQUAL_TO.toString());
		ruleProposition.setFailureMessage(velocityTemplate);

		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertEquals("Found 3 course(s) [CHEM101, CPR101, MATH101] but expected only 2", report.getMessage());
	}

	@Test
	public void testIntersectionProposition_StaticFact_Failure_LessThanOrEquals() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = createStaticFactStructure("fact.id.1", "course.intersection.criteria", "CPR101,MATH101,CHEM101");
		FactStructureInfo fs2 = createStaticFactStructure("fact.id.2", "course.intersection.fact", "CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "2", ComparisonOperator.LESS_THAN_OR_EQUAL_TO.toString());
		ruleProposition.setFailureMessage(velocityTemplate);

		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertEquals("Found 3 course(s) [CHEM101, CPR101, MATH101] but expected only 2 or less", report.getMessage());
	}

	@Test
	public void testIntersectionProposition_StaticFact_Failure_LessThan() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = createStaticFactStructure("fact.id.1", "course.intersection.criteria", "CPR101,MATH101,CHEM101");
		FactStructureInfo fs2 = createStaticFactStructure("fact.id.2", "course.intersection.fact", "CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "2", ComparisonOperator.LESS_THAN.toString());
		ruleProposition.setFailureMessage(velocityTemplate);

		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertEquals("Found 3 course(s) [CHEM101, CPR101, MATH101] but expected less than 2", report.getMessage());
	}

	@Test
	public void testIntersectionProposition_StaticFact_Failure_NotEqual() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = createStaticFactStructure("fact.id.1", "course.intersection.criteria", "CPR101,MATH101,CHEM101");
		FactStructureInfo fs2 = createStaticFactStructure("fact.id.2", "course.intersection.fact", "CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "3", ComparisonOperator.NOT_EQUAL_TO.toString());
		ruleProposition.setFailureMessage(velocityTemplate);

		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertEquals("Found 3 course(s) [CHEM101, CPR101, MATH101] but expected not 3", report.getMessage());
	}

	@Test
	public void testIntersectionProposition_StaticFact_Failure_GreaterThan() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = createStaticFactStructure("fact.id.1", "course.intersection.criteria", "CPR101,MATH101");
		FactStructureInfo fs2 = createStaticFactStructure("fact.id.2", "course.intersection.fact", "CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "3", ComparisonOperator.GREATER_THAN.toString());
		ruleProposition.setFailureMessage(velocityTemplate);

		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertEquals("Found 2 course(s) [CPR101, MATH101] but expected more than 3", report.getMessage());
	}

	@Test
	public void testIntersectionProposition_StaticFact_Failure_GreaterThanOrEqual() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = createStaticFactStructure("fact.id.1", "course.intersection.criteria", "CPR101,MATH101");
		FactStructureInfo fs2 = createStaticFactStructure("fact.id.2", "course.intersection.fact", "CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "3", ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());
		ruleProposition.setFailureMessage(velocityTemplate);

		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertEquals("Found 2 course(s) [CPR101, MATH101] but expected 3 or more", report.getMessage());
	}

	@Test
	public void testIntersectionProposition_DynamicFact() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.intersection.criteria");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "resultColumn.cluId");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		FactStructureInfo fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.intersection.fact");
		fs2.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, "2", ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs1, fs2, "resultColumn.cluId");
		
		IntersectionRuleProposition<String> proposition = new IntersectionRuleProposition<String>(
				"1", "IntersectionRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());

		FactResultInfo criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(2, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), "resultColumn.cluId", "CHEM101"));

		FactResultInfo factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "MATH101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "CHEM101"));

		FactResultInfo propositionResult = report.getPropositionResult();
        Assert.assertEquals(2, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.cluId", "CHEM101"));
	}
}
