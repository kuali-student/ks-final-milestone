package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class SumPropositionTest {
    private List<BigDecimal> creditList = CommonTestUtil.createList("3.0,6.0,3.0");

    @Test
    public void testSumTrue() throws Exception {

        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new BigDecimal(12.0), creditList);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
    }

    @Test
    public void testSumFalse() throws Exception {
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.LESS_THAN, new BigDecimal(12.0), creditList);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
    }
}
