package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class SumPropositionTest {

	@Test
    public void testSumTrue() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

    	BigDecimal expectedValue = new BigDecimal("10.0");
        SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, expectedValue, fact, "resultColumn.credits");

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(expectedValue.compareTo((BigDecimal) prop.getResultValues().iterator().next()) == 0);
    }

    @Test
    public void testSumFalse() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

    	BigDecimal expectedValue = new BigDecimal("9.0");

    	SumProposition<BigDecimal> prop = new SumProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.LESS_THAN, expectedValue, fact, "resultColumn.credits");

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertTrue(expectedValue.compareTo((BigDecimal) prop.getResultValues().iterator().next()) == -1);
    }

}
