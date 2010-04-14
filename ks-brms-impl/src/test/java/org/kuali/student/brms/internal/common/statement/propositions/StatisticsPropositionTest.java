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
import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;

public class StatisticsPropositionTest {
	private RulePropositionInfo ruleProposition = new RulePropositionInfo();

	@Test
    public void testStatisticsProposition_Max() throws Exception {
    	StatisticsProposition<Double> statProp = new StatisticsProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.MAX,
    			new Double(3), Arrays.asList(new Double[]{new Double(1), new Double(2), new Double(3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }
   
    @Test
    public void testStatisticsProposition_Min() throws Exception {
    	StatisticsProposition<Double> statProp = new StatisticsProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.MIN,
    			new Double(1), Arrays.asList(new Double[]{new Double(1), new Double(2), new Double(3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }
   
    @Test
    public void testStatisticsProposition_Mean() throws Exception {
    	StatisticsProposition<Double> statProp = new StatisticsProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.MEAN,
    			new Double(2), Arrays.asList(new Double[]{new Double(1), new Double(2), new Double(3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }
   
    @Test
    public void testStatisticsProposition_StandardDeviation() throws Exception {
    	StatisticsProposition<Double> statProp = new StatisticsProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.STANDARD_DEVIATION,
    			new Double(2), Arrays.asList(new Double[]{new Double(1), new Double(3), new Double(5)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }
   
    @Test
    public void testStatisticsProposition_Sum() throws Exception {
    	StatisticsProposition<Double> statProp = new StatisticsProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6), Arrays.asList(new Double[]{new Double(1), new Double(2), new Double(3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();
    	Assert.assertTrue(result);
    }
   
    @Test
    public void testStatisticsProposition_SumOfSquares() throws Exception {
    	StatisticsProposition<Double> statProp = new StatisticsProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM_OF_SQUARES,
    			new Double(14), Arrays.asList(new Double[]{new Double(1), new Double(2), new Double(3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Variance() throws Exception {
    	StatisticsProposition<Double> statProp = new StatisticsProposition<Double>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.VARIANCE,
    			new Double(4), Arrays.asList(new Double[]{new Double(1), new Double(3), new Double(5)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Sum_Long() throws Exception {
    	StatisticsProposition<Long> statProp = new StatisticsProposition<Long>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6.0), Arrays.asList(new Long[]{Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Sum_Integer() throws Exception {
    	StatisticsProposition<Integer> statProp = new StatisticsProposition<Integer>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6.0), Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Sum_Float() throws Exception {
    	StatisticsProposition<Float> statProp = new StatisticsProposition<Float>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6.6), Arrays.asList(new Float[]{new Float(1.1), new Float(2.2), new Float(3.3)}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Sum_Short() throws Exception {
    	StatisticsProposition<Short> statProp = new StatisticsProposition<Short>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6.0), Arrays.asList(new Short[]{new Short("1"), new Short("2"), new Short("3")}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Sum_Byte() throws Exception {
    	StatisticsProposition<Byte> statProp = new StatisticsProposition<Byte>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6.0), Arrays.asList(new Byte[]{new Byte("1"), new Byte("2"), new Byte("3")}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Sum_BigInteger() throws Exception {
    	StatisticsProposition<BigInteger> statProp = new StatisticsProposition<BigInteger>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6.0), Arrays.asList(new BigInteger[]{new BigInteger("1"), new BigInteger("2"), new BigInteger("3")}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

    @Test
    public void testStatisticsProposition_Sum_BigDecimal() throws Exception {
    	StatisticsProposition<BigDecimal> statProp = new StatisticsProposition<BigDecimal>(
    			"A-1", "A",
    			ComparisonOperator.EQUAL_TO,
    			StatisticsProposition.StatFunction.SUM,
    			new Double(6.0), Arrays.asList(new BigDecimal[]{new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")}),
    			ruleProposition);

    	Boolean result = statProp.apply();

    	Assert.assertTrue(result);
    }

}
