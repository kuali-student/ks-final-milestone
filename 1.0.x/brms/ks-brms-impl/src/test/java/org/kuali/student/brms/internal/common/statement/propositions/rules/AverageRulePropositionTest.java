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

import java.math.BigDecimal;
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
import org.kuali.student.brms.internal.common.statement.exceptions.IllegalFunctionStateException;
import org.kuali.student.brms.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.brms.internal.common.statement.propositions.rules.AverageRuleProposition;
import org.kuali.student.brms.internal.common.statement.report.PropositionReport;
import org.kuali.student.brms.internal.common.utils.FactUtil;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;

public class AverageRulePropositionTest {

    public Map<String, Object> getFactMap(FactStructureInfo fs1, String column) {
    	String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfo columnMetaData1 = CommonTestUtil.createColumnMetaData(BigDecimal.class.getName(), column);
        FactResultInfo factResult = CommonTestUtil.createFactResult(new String[] {"80","95","80"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testAverageProposition_StaticFact() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
		FactStructureInfo fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");
		fs.setStaticFact(true);
		fs.setStaticValueDataType(BigDecimal.class.getName());
		fs.setStaticValue("80,95,80");
		
		BigDecimal expectedValue = new BigDecimal(85);
		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, expectedValue.toString(), ComparisonOperator.EQUAL_TO.toString());

		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultInfo fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "95"));

		FactResultInfo propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "85.0"));
	}

	@Test
	public void testAverageProposition_True() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
		FactStructureInfo fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(85).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertNotNull(report.getFactResult());

		FactResultInfo fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));

		FactResultInfo propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "85.0"));
	}

	@Test
	public void testAverageProposition_False() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
		FactStructureInfo fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(90).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");

		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals("Average of 85.0 is short by 5.0. Expected an average of 90.", report.getMessage());

		FactResultInfo fact = report.getFactResult();
		Assert.assertEquals(3, fact.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "80"));
		Assert.assertTrue(CommonTestUtil.containsResult(fact.getResultList(), "resultColumn.credit", "95"));

		FactResultInfo propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.credit", "85.0"));
	}

	@Test
	public void testAverageProposition_InvalidColumn() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
		FactStructureInfo fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.xxx");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(888).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
					"1", "AverageRuleProposition", ruleProposition, factMap);
			proposition.apply();
			Assert.fail("AverageRuleProposition should have thrown a PropositionException for resultColumn.xxx");
		} catch(IllegalFunctionStateException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testAverageProposition_NullColumn() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
		FactStructureInfo fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, null);
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(888).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		try {
			AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
					"1", "AverageRuleProposition", ruleProposition, factMap);
			Assert.fail("AverageRuleProposition should have thrown a PropositionException for a null column");
		} catch(PropositionException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testAverageProposition_DefaultSuccessMessage() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
		FactStructureInfo fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal(85).toString(), ComparisonOperator.EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals(MessageContextConstants.PROPOSITION_AVERAGE_SUCCESS_MESSAGE, report.getMessage());
	}

	@Test
	public void testAverageProposition_DefaultFailureMessage() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
		FactStructureInfo fs = CommonTestUtil.createFactStructure("fact.id.1", "course.average.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "resultColumn.credit");
		fs.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, new BigDecimal("90.0").toString(), ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

		Map<String, Object> factMap = getFactMap(fs, "resultColumn.credit");
		
		AverageRuleProposition<BigDecimal> proposition = new AverageRuleProposition<BigDecimal>(
				"1", "AverageRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals("Average of 85.0 is short by 5.0. Expected an average of 90.0.", report.getMessage());
	}
}
