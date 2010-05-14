package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class AveragePropositionTest {

	@Test
    public void testAverageTrue() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "85.0", "MATH103", "75.0", "CHEM101", "80.0"}, 
    			new String[] {"resultColumn.cluId","resultColumn.grade"});
    	
    	BigDecimal number = new BigDecimal("80.0");
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, fact, "resultColumn.grade");

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }

    @Test
    public void testAverageFalse() throws Exception {
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "85.0", "MATH103", "75.0", "CHEM101", "80.0"}, 
    			new String[] {"resultColumn.cluId","resultColumn.grade"});
    	
    	BigDecimal number = new BigDecimal("80.0");
    	AverageProposition<BigDecimal> prop = new AverageProposition<BigDecimal>(
    			"A-1", "A",
                ComparisonOperator.EQUAL_TO, new BigDecimal(70.0), fact, "resultColumn.grade");

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertTrue(number.compareTo((BigDecimal) prop.getResultValues().iterator().next())==0);
    }
}
