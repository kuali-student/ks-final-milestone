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

package org.kuali.student.brms.internal.common.statement.propositions;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class AveragePropositionTest {

	@Test
    public void testAverageTrue() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
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
		FactResultInfo fact = CommonTestUtil.createFact(
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
