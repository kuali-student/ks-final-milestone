package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class SumPropositionTest {
    private List<BigDecimal> creditList = CommonTestUtil.createList("3.0,6.0,3.0");

	private RulePropositionDTO ruleProposition = new RulePropositionDTO();

    @Before
    public void setUp() throws Exception {
    	ruleProposition.setSuccessMessage("Sum successful: #sum# credits");
    	ruleProposition.setFailureMessage("Sum failure. " +
    			"Sum is short by #needed# credits. " +
    			"Must have minimum of #expectedValue#.0 credits.");
    }

	@Test
    public void testSumTrue() throws Exception {
    	BigDecimal number = new BigDecimal(12.0);
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, creditList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }

    @Test
    public void testSumFalse() throws Exception {
    	BigDecimal number = new BigDecimal(12.0);
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.LESS_THAN, number, creditList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }

	@Test
    public void testSumTrue_SuccessMessage() throws Exception {
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(12.0), creditList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertEquals("Sum successful: 12.0 credits", prop.getReport().getSuccessMessage());
    }

	@Test
    public void testSumTrue_DefaultSuccessMessage() throws Exception {
    	ruleProposition.setSuccessMessage(null);
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(12.0), creditList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertEquals(SumProposition.DEFAULT_SUCCESS_MESSAGE, prop.getReport().getSuccessMessage());
    }

    @Test
    public void testSumFalse_FailureMessage() throws Exception {
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN, new BigDecimal(13.0), creditList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertEquals("Sum failure. Sum is short by 1.0 credits. " +
        		"Must have minimum of 13.0 credits.", prop.getReport().getFailureMessage());
    }

    @Test
    public void testSumFalse_DefaultFailureMessage() throws Exception {
    	ruleProposition.setFailureMessage(null);
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.LESS_THAN, new BigDecimal(12.0), creditList,
                ruleProposition);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertEquals("Sum is short by 0.0", prop.getReport().getFailureMessage());
    }

}
