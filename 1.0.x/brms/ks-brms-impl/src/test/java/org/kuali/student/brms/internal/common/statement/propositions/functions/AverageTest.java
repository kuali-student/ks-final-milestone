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

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class AverageTest {

	@Test
	public void testX() throws Exception {
	}
	
	@Test
	public void testAverage1() throws Exception {
    	FactResultTypeInfo columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.grade");

    	FactResultInfo factResult = CommonTestUtil.createFactResult(
    			new String[] {"80.0", "85.0", "90.0"}, 
    			"resultColumn.grade");
    	factResult.setFactResultTypeInfo(columnMetaData);

    	Average<Number> avg = new Average<Number>();
		avg.setFact(factResult, "resultColumn.grade");
		Number output = avg.compute();
		
		Assert.assertEquals(new BigDecimal("85.0"), output);
	}

	@Test
	public void testAverage2() throws Exception {
    	FactResultTypeInfo columnMetaData = CommonTestUtil.createColumnMetaData(String.class.getName(), "resultColumn.grade");

    	FactResultInfo factResult = CommonTestUtil.createFactResult(
    			new String[] {"80.0", "85.0", "95.0"}, 
    			"resultColumn.grade");
    	factResult.setFactResultTypeInfo(columnMetaData);

    	Average<Number> avg = new Average<Number>();
		avg.setFact(factResult, "resultColumn.grade");
		Number output = avg.compute();

		BigDecimal result = new BigDecimal(output.toString());
		
		Assert.assertEquals(new BigDecimal("86.67"), result.setScale(2, RoundingMode.HALF_UP));
	}
}