package org.kuali.student.brms.internal.common.statement.propositions.rules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.statement.propositions.rules.SubsetRuleProposition;
import org.kuali.student.brms.internal.common.statement.report.PropositionReport;
import org.kuali.student.brms.internal.common.utils.FactUtil;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;

public class SubsetRulePropositionTest {

	private boolean assertEqualStrings(String s) {
		if(s.equals("1 of [CPR101, MATH101] is still required")) {
			return true;
		} else if(s.equals("1 of [MATH101, CPR101] is still required")) {
			return true;
		}
		return false;
	}

	private Map<String, Object> getFactMap(FactStructureInfo fs1, FactStructureInfo fs2, String column) {
    	return getFactMap(fs1, fs2, column, null);
    }
    
    private Map<String, Object> getFactMap(FactStructureInfo fs1, FactStructureInfo fs2, String column, String[] subset) {
    	String criteriaKey = FactUtil.createCriteriaKey(fs1);
    	String factKey = FactUtil.createFactKey(fs2);

    	FactResultTypeInfo columnMetaData1 = CommonTestUtil.createColumnMetaData(String.class.getName(), column);
        FactResultInfo factResult = CommonTestUtil.createFactResult(new String[] {"CPR101","MATH101"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        FactResultInfo factResultCriteria = null;
    	if(subset == null) {
    		factResultCriteria = CommonTestUtil.createFactResult(new String[] {"CPR101"}, column);
    	} else {
    		factResultCriteria = CommonTestUtil.createFactResult(subset, column);
    	}
    	factResultCriteria.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testSubSetProposition_StaticFact() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.subset.criteria");
		fs1.setStaticFact(true);
		fs1.setStaticValueDataType(String.class.getName());
		fs1.setStaticValue("CPR101");

		FactStructureInfo fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.subset.fact");
		fs2.setStaticFact(true);
		fs2.setStaticValueDataType(String.class.getName());
		fs2.setStaticValue("CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, null, null);

		SubsetRuleProposition<String> proposition = new SubsetRuleProposition<String>(
				"1", "YVFSubsetProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());

		FactResultInfo criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(1, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));

		FactResultInfo factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "MATH101"));

		FactResultInfo propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
	}

	@Test
	public void testSubSetProposition_True() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.subset.criteria");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY, "resultColumn.cluId");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		FactStructureInfo fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.subset.fact");
		fs2.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, null, null);

		Map<String, Object> factMap = getFactMap(fs1, fs2, "resultColumn.cluId");
		
		SubsetRuleProposition<String> proposition = new SubsetRuleProposition<String>(
				"1", "YVFSubsetProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());

		FactResultInfo criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(1, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), "resultColumn.cluId", "CPR101"));

		FactResultInfo factResult = report.getFactResult();
		Assert.assertEquals(2, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "MATH101"));

		FactResultInfo propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.cluId", "CPR101"));
	}

	@Test
	public void testSubSetProposition_DefaultSuccessMessage() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.subset.criteria");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY, "resultColumn.cluId");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		FactStructureInfo fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.subset.fact");
		fs2.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, null, null);

		Map<String, Object> factMap = getFactMap(fs1, fs2, "resultColumn.cluId");
		
		SubsetRuleProposition<String> proposition = new SubsetRuleProposition<String>(
				"1", "YVFSubsetProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertEquals(MessageContextConstants.PROPOSITION_SUBSET_SUCCESS_MESSAGE, report.getMessage());
	}

	@Test
	public void testSubSetProposition_DefaultFailureMessage() throws Exception {
		YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();

		FactStructureInfo fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.subset.criteria");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY, "resultColumn.cluId");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		FactStructureInfo fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.subset.fact");
		fs2.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));
		RulePropositionInfo ruleProposition = CommonTestUtil.createRuleProposition(yvf, null, null);

		Map<String, Object> factMap = getFactMap(fs1, fs2, "resultColumn.cluId", new String[] {"MATH200"});
		
		SubsetRuleProposition<String> proposition = new SubsetRuleProposition<String>(
				"1", "YVFSubsetProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();

		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getMessage());
		Assert.assertTrue(assertEqualStrings(report.getMessage()));
	}
}
