package org.kuali.student.rules.internal.common.statement.propositions.functions;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Intersection.Operation;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;

public class IntersectionTest {

	@Test
	public void testIntersection() throws Exception {
		Intersection intersection = new Intersection();
		intersection.setOperation(Operation.INTERSECTION.toString());

    	FactResultTypeInfoDTO columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.cluId");

    	FactResultDTO factCriteria = CommonTestUtil.createFactResult(
    			new String[] {"CPR101", "CHEM101"}, 
    			"resultColumn.cluId");
    	factCriteria.setFactResultTypeInfo(columnMetaData);

        FactResultDTO factResult = CommonTestUtil.createFactResult(
        		new String[] {"CPR101", "3", "CPR101", "3","MATH101", "4","CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});
        factResult.setFactResultTypeInfo(columnMetaData);
		
        intersection.setCriteria(factCriteria, "resultColumn.cluId");
        intersection.setFact(factResult,"resultColumn.cluId");
        FactResultDTO result = intersection.compute();

		Assert.assertEquals(2, result.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(result.getResultList(), "resultColumn.cluId", "CPR101"));
	}
}