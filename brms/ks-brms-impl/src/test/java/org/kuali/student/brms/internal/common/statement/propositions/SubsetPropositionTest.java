package org.kuali.student.brms.internal.common.statement.propositions;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class SubsetPropositionTest {

	@Test
    public void testSubsetTrue() throws Exception {
		FactResultInfo criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"CHEM101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

		SubsetProposition<String> prop = new SubsetProposition<String>(
        		"A-1", "A", criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testSubsetFalse() throws Exception {
		FactResultInfo criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"BIOL101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

        SubsetProposition<String> prop = new SubsetProposition<String>(
        		"A-1", "A", criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertEquals(0, prop.getResultValues().size());
    }
}
