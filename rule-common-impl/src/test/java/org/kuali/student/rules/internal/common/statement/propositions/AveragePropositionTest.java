package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class AveragePropositionTest {
    private List<BigDecimal> gradeList = CommonTestUtil.createList("85.0,75.0,80.0");

	private RulePropositionDTO ruleProposition = new RulePropositionDTO();

    @Before
    public void setUp() throws Exception {
    	ruleProposition.setSuccessMessage("Average successful: #average#%");
    	ruleProposition.setFailureMessage("Average failure. Average is short by #needed#%");
    }
    
    @Test
    public void testAverageTrue() throws Exception {
    	
    	BigDecimal number = new BigDecimal(80.0);
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, gradeList, 
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }

    @Test
    public void testAverageFalse() throws Exception {
    	BigDecimal number = new BigDecimal(80.0);
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
    			"A-1", "A",
                ComparisonOperator.EQUAL_TO, new BigDecimal(70.0), gradeList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }

    @Test
    public void testAverage_SuccessMessage() throws Exception {
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(80.0), gradeList, 
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertEquals("Average successful: 80.0%", prop.getReport().getSuccessMessage());
    }

    @Test
    public void testAverage_DefaultSuccessMessage() throws Exception {
    	ruleProposition.setSuccessMessage(null);
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(80.0), gradeList, 
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertEquals(AverageProposition.DEFAULT_SUCCESS_MESSAGE, prop.getReport().getSuccessMessage());
    }

    @Test
    public void testAverageFalse_FailureMessage() throws Exception {
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
    			"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(85.0), gradeList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertEquals("Average failure. Average is short by 5.0%", prop.getReport().getFailureMessage());
    }

    @Test
    public void testAverageFalse_DefaultFailureMessage() throws Exception {
    	ruleProposition.setFailureMessage(null);
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
    			"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(85.0), gradeList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertEquals("Average of 80.0 is short by 5.0", prop.getReport().getFailureMessage());
    }
}
