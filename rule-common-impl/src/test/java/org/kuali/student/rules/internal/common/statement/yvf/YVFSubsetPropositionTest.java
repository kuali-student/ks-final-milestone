package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFSubsetPropositionTest {

    public Map<String, Object> getFactMap(FactStructureDTO fs1, FactStructureDTO fs2, String column) {
    	String criteriaKey = FactUtil.createCriteriaKey(fs1);
    	String factKey = FactUtil.createFactKey(fs2);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(String.class.getName(), column);
        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

    	FactResultDTO factResultCriteria = CommonTestUtil.createFactResult(new String[] {"CPR101"}, column);
    	factResultCriteria.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testSubSetProposition_StaticFact() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();

		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.subset.criteria");
		fs1.setStaticFact(true);
		fs1.setStaticValueDataType(String.class.getName());
		fs1.setStaticValue("CPR101");

		FactStructureDTO fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.subset.fact");
		fs2.setStaticFact(true);
		fs2.setStaticValueDataType(String.class.getName());
		fs2.setStaticValue("CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));

		YVFSubsetProposition<String> proposition = new YVFSubsetProposition<String>(
				"1", "YVFSubsetProposition", yvf, null);

		PropositionReport report = proposition.getReport();

		Assert.assertTrue(proposition.apply());
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(1, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), YVFSubsetProposition.STATIC_FACT_COLUMN, "CPR101"));

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), YVFSubsetProposition.STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), YVFSubsetProposition.STATIC_FACT_COLUMN, "MATH101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), YVFSubsetProposition.STATIC_FACT_COLUMN, "CHEM101"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), YVFSubsetProposition.STATIC_FACT_COLUMN, "CPR101"));
	}

	@Test
	public void testSubSetProposition_True() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();

		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.subset.criteria");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFSubsetProposition.SUBSET_COLUMN_KEY, "resultColumn.cluId");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		FactStructureDTO fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.subset.fact");
		fs2.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));

		Map<String, Object> factMap = getFactMap(fs1, fs2, "resultColumn.cluId");
		
		YVFSubsetProposition<String> proposition = new YVFSubsetProposition<String>(
				"1", "YVFSubsetProposition", yvf, factMap);

		PropositionReport report = proposition.getReport();

		Assert.assertTrue(proposition.apply());
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(1, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), "resultColumn.cluId", "CPR101"));

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "MATH101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "CHEM101"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.cluId", "CPR101"));
	}
}
