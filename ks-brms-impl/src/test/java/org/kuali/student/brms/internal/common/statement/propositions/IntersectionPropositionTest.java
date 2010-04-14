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

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class IntersectionPropositionTest {

	@Test
    public void testIntersectionTrue() throws Exception {
		FactResultInfo criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"CHEM101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

		IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, Integer.valueOf(1), 
                criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testIntersectionFalse() throws Exception {
		FactResultInfo criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"CHEM101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

        IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, Integer.valueOf(2), 
                criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertFalse(result);
		Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testIntersectionFalse2() throws Exception {
		FactResultInfo criteria = CommonTestUtil.createFact(
				new String[] {String.class.getName()},
				new String[] {"ENGL101"}, 
				new String[] {"resultColumn.cluId"});
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "3"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

        IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, Integer.valueOf(1), 
                criteria, "resultColumn.cluId", fact, "resultColumn.cluId");

        Boolean result = prop.apply();

        Assert.assertFalse(result);
		Assert.assertTrue(prop.getResultValues().isEmpty());
    }
}
