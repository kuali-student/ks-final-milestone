package org.kuali.student.rules.internal.common.statement.propositions;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class IntersectionPropositionTest {

	@Test
    public void testIntersectionTrue() throws Exception {
		FactResultDTO criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"CHEM101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

		IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(1), 
                criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testIntersectionFalse() throws Exception {
		FactResultDTO criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"CHEM101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

        IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(2), 
                criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertFalse(result);
		Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testIntersectionFalse2() throws Exception {
		FactResultDTO criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"ENGL101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultDTO fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

        IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(1), 
                criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertFalse(result);
		Assert.assertTrue(prop.getResultValues().isEmpty());
    }
}
