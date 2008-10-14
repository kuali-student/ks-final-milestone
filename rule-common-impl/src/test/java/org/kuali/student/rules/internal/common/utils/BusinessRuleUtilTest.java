package org.kuali.student.rules.internal.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BusinessRuleUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void testValidateRuleComposition() throws Exception {
    }

    public void testCreateFunctionalString() throws Exception {
    }

    public void testCreateAdjustedRuleFunctionString() throws Exception {
    }

    public void testGetRulePropositions() throws Exception {
    }
    
    @Test
    public void testConvertToDataType_String() throws Exception {
    	Class clazz = Class.forName(String.class.getName());
    	String value = (String) BusinessRuleUtil.convertToDataType(clazz, "Hey Kuali");
    	Assert.assertEquals("Hey Kuali", value);
    }

    @Test
    public void testConvertToDataType_Integer() throws Exception {
    	Class clazz = Class.forName(Integer.class.getName());
    	Integer value = (Integer) BusinessRuleUtil.convertToDataType(clazz, "1");
    	Assert.assertEquals(1, value.intValue());
    }

    @Test
    public void testConvertToDataType_Double() throws Exception {
    	Class clazz = Class.forName(Double.class.getName());
    	Double value = (Double) BusinessRuleUtil.convertToDataType(clazz, "1.23");
    	Assert.assertEquals(1.23D, value.doubleValue(), 0);
    }

    @Test
    public void testConvertToDataType_Long() throws Exception {
    	Class clazz = Class.forName(Long.class.getName());
    	Long value = (Long) BusinessRuleUtil.convertToDataType(clazz, "1");
    	Assert.assertEquals(1L, value.longValue());
    }

    @Test
    public void testConvertToDataType_Float() throws Exception {
    	Class clazz = Class.forName(Float.class.getName());
    	Float value = (Float) BusinessRuleUtil.convertToDataType(clazz, "1.1");
    	Assert.assertEquals(1.1F, value.floatValue(), 0);
    }

    @Test
    public void testConvertToDataType_Short() throws Exception {
    	Class clazz = Class.forName(Short.class.getName());
    	Short value = (Short) BusinessRuleUtil.convertToDataType(clazz, "123");
    	Assert.assertEquals(123, value.intValue());
    }

    @Test
    public void testConvertToDataType_BigDecimal() throws Exception {
    	Class clazz = Class.forName(BigDecimal.class.getName());
    	BigDecimal value = (BigDecimal) BusinessRuleUtil.convertToDataType(clazz, "123.456");
    	Assert.assertEquals(new BigDecimal(123.456).doubleValue(), value.doubleValue(), 0);
    }

    @Test
    public void testConvertToDataType_BigInteger() throws Exception {
    	Class clazz = Class.forName(BigInteger.class.getName());
    	BigInteger value = (BigInteger) BusinessRuleUtil.convertToDataType(clazz, "123");
    	Assert.assertEquals(new BigInteger("123"), value);
    }

    @Test
    public void testConvertToDataType_Boolean() throws Exception {
    	Class clazz = Class.forName(Boolean.class.getName());
    	Boolean value = (Boolean) BusinessRuleUtil.convertToDataType(clazz, "true");
    	Assert.assertEquals(new Boolean("true").booleanValue(), true);
    }

    @Test
    public void testConvertToDataType_Date() throws Exception {
    	long time = System.currentTimeMillis();
    	Date date = new Date(time);
    	Class clazz = Class.forName(Date.class.getName());
    	Date value = (Date) BusinessRuleUtil.convertToDataType(clazz, String.valueOf(time));
    	Assert.assertEquals(date, value);
    }

    @Test
    public void testConvertToDataType_Calendar() throws Exception {
    	long time = System.currentTimeMillis();
    	Class clazz = Class.forName(Calendar.class.getName());
    	Calendar value = (Calendar) BusinessRuleUtil.convertToDataType(clazz, String.valueOf(time));
    	Assert.assertEquals(time, value.getTimeInMillis());
    }

}
