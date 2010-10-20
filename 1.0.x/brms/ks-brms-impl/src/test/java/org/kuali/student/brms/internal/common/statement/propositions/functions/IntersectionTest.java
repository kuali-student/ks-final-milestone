/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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