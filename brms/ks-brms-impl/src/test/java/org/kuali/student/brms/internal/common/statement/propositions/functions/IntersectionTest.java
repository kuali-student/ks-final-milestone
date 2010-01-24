package org.kuali.student.brms.internal.common.statement.propositions.functions;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.internal.common.statement.propositions.functions.Intersection.Operation;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class IntersectionTest {

	@Test
	public void testIntersection() throws Exception {
		Intersection intersection = new Intersection();
		intersection.setOperation(Operation.INTERSECTION.toString());

    	FactResultTypeInfo columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.cluId");

    	FactResultInfo factCriteria = CommonTestUtil.createFactResult(
    			new String[] {"CPR101", "CHEM101"}, 
    			"resultColumn.cluId");
    	factCriteria.setFactResultTypeInfo(columnMetaData);

        FactResultInfo factResult = CommonTestUtil.createFactResult(
        		new String[] {"CPR101", "3", "CPR101", "3","MATH101", "4","CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});
        factResult.setFactResultTypeInfo(columnMetaData);
		
        intersection.setCriteria(factCriteria, "resultColumn.cluId");
        intersection.setFact(factResult,"resultColumn.cluId");
        FactResultInfo result = intersection.compute();

		Assert.assertEquals(2, result.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(result.getResultList(), "resultColumn.cluId", "CPR101"));
	}
}