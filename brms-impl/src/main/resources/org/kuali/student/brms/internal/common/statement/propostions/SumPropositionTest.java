package org.kuali.student.brms.internal.common.statement.propostions;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class SumPropositionTest {
    private List<BigDecimal> creditList = CommonTestUtil.createList("3.0,6.0,3.0");

	@Test
    public void testSumTrue() throws Exception {
    	BigDecimal number = new BigDecimal(12.0);
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, creditList);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }

    @Test
    public void testSumFalse() throws Exception {
    	BigDecimal number = new BigDecimal(12.0);
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.LESS_THAN, number, creditList);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }
}
