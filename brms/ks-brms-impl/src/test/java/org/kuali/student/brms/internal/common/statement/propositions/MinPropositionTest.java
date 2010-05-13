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
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.brms.internal.common.utils.CommonTestUtil;

public class MinPropositionTest {
	@Test
    public void testMinProposition_String() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), String.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

    	MinProposition<String> minProp = new MinProposition<String>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, "3", 
    			fact, "resultColumn.credits");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains("3"));
    }

    @Test
    public void testMinProposition_BigDecimal() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), BigDecimal.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

    	BigDecimal number = new BigDecimal("3");
    	MinProposition<BigDecimal> minProp = new MinProposition<BigDecimal>(
    			"A-1", "A", ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
        Assert.assertTrue(number.compareTo((BigDecimal) minProp.getResultValues().iterator().next())==0);
    }

    @Test
    public void testMinProposition_Double() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Double.class.getName()},
				new String[] {"MATH101", "3.1", "MATH103", "4.2", "CHEM101", "5.1"}, 
    			new String[] {"resultColumn.cluId","resultColumn.credits"});

    	Double number = new Double("3.1");
    	MinProposition<Double> minProp = new MinProposition<Double>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains(number));
    }

    @Test
    public void testMinProposition_Integer() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Integer.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

    	Integer number = Integer.valueOf(3);
    	MinProposition<Integer> minProp = new MinProposition<Integer>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains(number));
    }

    @Test
    public void testMinProposition_Long() throws Exception {
		FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"MATH101", "3", "MATH103", "4", "CHEM101", "5"}, 
    			new String[] {"resultColumn.cluId", "resultColumn.credits"});

    	Long number = Long.valueOf(3);
    	MinProposition<Long> minProp = new MinProposition<Long>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, number, 
    			fact, "resultColumn.credits");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains(number));
    }

    @Test
    public void testMinProposition_Calendar() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0);

		String date1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		String date2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
		String date3 = BusinessRuleUtil.formatIsoDate(cal3.getTime());
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Calendar.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MinProposition<Calendar> minProp = new MinProposition<Calendar>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains(cal1));
    }

    @Test
    public void testMinProposition_Calendar_LessThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0);

		String date1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		String date2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
		String date3 = BusinessRuleUtil.formatIsoDate(cal3.getTime());
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Calendar.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MinProposition<Calendar> minProp = new MinProposition<Calendar>(
    			"A-1", "A", ComparisonOperator.LESS_THAN, cal3, 
    			fact, "resultColumn.date");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains(cal1));
    }

    @Test
    public void testMinProposition_Calendar_GreaterThan() throws Exception {
		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0);
		Calendar cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0);

		String date1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
		String date2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
		String date3 = BusinessRuleUtil.formatIsoDate(cal3.getTime());
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Calendar.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MinProposition<Calendar> minProp = new MinProposition<Calendar>(
    			"A-1", "A", ComparisonOperator.GREATER_THAN, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = minProp.apply();

    	Assert.assertFalse(result);
    	Assert.assertTrue(minProp.getResultValues().contains(cal1));
    }

    @Test
    public void testMinProposition_Date_EqualTo() throws Exception {
		Date cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0).getTime();
		Date cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0).getTime();
		Date cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0).getTime();

		String date1 = BusinessRuleUtil.formatIsoDate(cal1);
		String date2 = BusinessRuleUtil.formatIsoDate(cal2);
		String date3 = BusinessRuleUtil.formatIsoDate(cal3);
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Date.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MinProposition<Date> minProp = new MinProposition<Date>(
    			"A-1", "A", ComparisonOperator.EQUAL_TO, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains(cal1));
    }

    @Test
    public void testMinProposition_Date_LessThan() throws Exception {
		Date cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0).getTime();
		Date cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0).getTime();
		Date cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0).getTime();

		String date1 = BusinessRuleUtil.formatIsoDate(cal1);
		String date2 = BusinessRuleUtil.formatIsoDate(cal2);
		String date3 = BusinessRuleUtil.formatIsoDate(cal3);
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Date.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MinProposition<Date> minProp = new MinProposition<Date>(
    			"A-1", "A", ComparisonOperator.LESS_THAN, cal2, 
    			fact, "resultColumn.date");

    	Boolean result = minProp.apply();

    	Assert.assertTrue(result);
    	Assert.assertTrue(minProp.getResultValues().contains(cal1));
    }

    @Test
    public void testMinProposition_Date_GreaterThan() throws Exception {
		Date cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0).getTime();
		Date cal2 = CommonTestUtil.createDate(2010, 1, 1, 1, 0).getTime();
		Date cal3 = CommonTestUtil.createDate(2020, 1, 1, 1, 0).getTime();

		String date1 = BusinessRuleUtil.formatIsoDate(cal1);
		String date2 = BusinessRuleUtil.formatIsoDate(cal2);
		String date3 = BusinessRuleUtil.formatIsoDate(cal3);
    	
    	FactResultInfo fact = CommonTestUtil.createFact(
				new String[] {String.class.getName(), Date.class.getName()},
				new String[] {"MATH101", date1, "MATH103", date2, "CHEM101", date3}, 
    			new String[] {"resultColumn.cluId", "resultColumn.date"});

    	MinProposition<Date> minProp = new MinProposition<Date>(
    			"A-1", "A", ComparisonOperator.GREATER_THAN, cal1, 
    			fact, "resultColumn.date");

    	Boolean result = minProp.apply();

    	Assert.assertFalse(result);
    	Assert.assertTrue(minProp.getResultValues().contains(cal1));
    }
}
