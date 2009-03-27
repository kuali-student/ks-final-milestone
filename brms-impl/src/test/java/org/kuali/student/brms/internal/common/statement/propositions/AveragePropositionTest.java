package org.kuali.student.brms.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.propositions.AverageProposition;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class AveragePropositionTest {
    private List<BigDecimal> gradeList = CommonTestUtil.createList("85.0,75.0,80.0");

    @Test
    public void testAverageTrue() throws Exception {
    	
    	BigDecimal number = new BigDecimal(80.0);
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, gradeList);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }

    @Test
    public void testAverageFalse() throws Exception {
    	BigDecimal number = new BigDecimal(80.0);
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
    			"A-1", "A",
                ComparisonOperator.EQUAL_TO, new BigDecimal(70.0), gradeList);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }
}
