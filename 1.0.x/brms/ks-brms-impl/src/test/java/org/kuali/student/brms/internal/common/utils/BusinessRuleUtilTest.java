package org.kuali.student.brms.internal.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
    public void testConvertToDataType_String1() throws Exception {
    	String s = "Hey Kuali";
    	String value = (String) BusinessRuleUtil.convertToDataType(String.class, s);
    	Assert.assertEquals(s, value);
    }

    @Test
    public void testConvertToDataType_String2() throws Exception {
    	String s = "Hey Kuali";
    	String value = (String) BusinessRuleUtil.convertToDataType(s);
    	Assert.assertEquals(s, value);
    }

    @Test
    public void testConvertToDataType_Integer() throws Exception {
    	Integer num = new Integer(1);
    	Integer value = (Integer) BusinessRuleUtil.convertToDataType(Integer.class, num);
    	Assert.assertEquals(num, value);
    }

    @Test
    public void testConvertToDataType_Double() throws Exception {
    	Double num = new Double(1.23D);
    	Double value = (Double) BusinessRuleUtil.convertToDataType(Double.class, num);
    	Assert.assertEquals(num, value);
    }

    @Test
    public void testConvertToDataType_Long() throws Exception {
    	Long num = new Long(1L);
    	Long value = (Long) BusinessRuleUtil.convertToDataType(Long.class,num);
    	Assert.assertEquals(num, value);
    }

    @Test
    public void testConvertToDataType_Float() throws Exception {
    	Float num = new Float(1.1F);
    	Float value = (Float) BusinessRuleUtil.convertToDataType(Float.class, num);
    	Assert.assertEquals(num, value);
    }

    @Test
    public void testConvertToDataType_Short() throws Exception {
    	Short num = new Short("123");
    	Short value = (Short) BusinessRuleUtil.convertToDataType(Short.class, num);
    	Assert.assertEquals(num, value);
    }

    @Test
    public void testConvertToDataType_BigDecimal1() throws Exception {
    	BigDecimal num = new BigDecimal(123.456);
    	BigDecimal value = (BigDecimal) BusinessRuleUtil.convertToDataType(num);
    	Assert.assertEquals(num.doubleValue(), value.doubleValue(), 0);
    }

    @Test
    public void testConvertToDataType_BigDecimal2() throws Exception {
    	BigDecimal num = new BigDecimal(123.456);
    	BigDecimal value = (BigDecimal) BusinessRuleUtil.convertToDataType(BigDecimal.class, num);
    	Assert.assertEquals(num.doubleValue(), value.doubleValue(), 0);
    }

    @Test
    public void testConvertToDataType_BigInteger1() throws Exception {
    	BigInteger num = new BigInteger("123");
    	BigInteger value = (BigInteger) BusinessRuleUtil.convertToDataType(num);
    	Assert.assertEquals(num, value);
    }

    @Test
    public void testConvertToDataType_BigInteger2() throws Exception {
    	BigInteger num = new BigInteger("123");
    	BigInteger value = (BigInteger) BusinessRuleUtil.convertToDataType(BigInteger.class, num);
    	Assert.assertEquals(num, value);
    }

    @Test
    public void testConvertToDataType_Boolean() throws Exception {
    	Boolean b = new Boolean(true);
    	Boolean value = (Boolean) BusinessRuleUtil.convertToDataType(Boolean.class, b);
    	Assert.assertEquals(b, value);
    }

    @Test
    public void testConvertToDataType_Date1() throws Exception {
    	Date time = new Date();
    	Date value = (Date) BusinessRuleUtil.convertToDataType(time);
    	Assert.assertEquals(time, value);
    }

    @Test
    public void testConvertToDataType_Date2() throws Exception {
    	Date time = new Date();
    	Date value = (Date) BusinessRuleUtil.convertToDataType(Date.class, time);
    	Assert.assertEquals(time, value);
    }

    @Test
    public void testConvertToDataType_Calendar1() throws Exception {
    	Calendar time = Calendar.getInstance();
    	Calendar value = (Calendar) BusinessRuleUtil.convertToDataType(Calendar.class, time);
    	Assert.assertEquals(time, value);
    }

    @Test
    public void testConvertToDataType_Calendar2() throws Exception {
    	Calendar time = Calendar.getInstance();
    	Calendar value = (Calendar) BusinessRuleUtil.convertToDataType(time);
    	Assert.assertEquals(time, value);
    }

    @Test
    public void testConvertToDataType_GregorianCalendar() throws Exception {
    	GregorianCalendar greg = new GregorianCalendar();
		GregorianCalendar value = (GregorianCalendar) BusinessRuleUtil.convertToDataType(greg);
    	Assert.assertEquals(greg.getTime(), value.getTime());
    }

    @Test
    public void testConvertToDataType_XMLGregorianCalendar() throws Exception {
    	GregorianCalendar greg = new GregorianCalendar();
    	DatatypeFactoryImpl factory = new DatatypeFactoryImpl();
    	XMLGregorianCalendar time = factory.newXMLGregorianCalendar(greg);
		GregorianCalendar value = (GregorianCalendar) BusinessRuleUtil.convertToDataType(time);
    	Assert.assertEquals(greg.getTime(), value.getTime());
    }

    @Test
    public void testConvertToDataType_Calendar_XMLGregorianCalendar() throws Exception {
    	Calendar cal = Calendar.getInstance();
		GregorianCalendar value = (GregorianCalendar) BusinessRuleUtil.convertToDataType(GregorianCalendar.class, cal);
    	Assert.assertEquals(cal.getTime(), value.getTime());
    }

    @Test
    public void testConvertToDataType_StringToString() throws Exception {
    	String s = "Hey Kuali";
    	String type = java.lang.String.class.getName();
    	String value = (String) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value);
    }

    @Test
    public void testConvertToDataType_StringToInteger() throws Exception {
    	String s = "123";
    	String type = java.lang.Integer.class.getName();
    	Integer value = (Integer) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToDouble() throws Exception {
    	String s = "123.123";
    	String type = java.lang.Double.class.getName();
    	Double value = (Double) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToLong() throws Exception {
    	String s = "123";
    	String type = java.lang.Long.class.getName();
    	Long value = (Long) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToFloat() throws Exception {
    	String s = "123.456";
    	String type = java.lang.Float.class.getName();
    	Float value = (Float) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToShort() throws Exception {
    	String s = "123";
    	String type = java.lang.Short.class.getName();
    	Short value = (Short) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToBigDecimal() throws Exception {
    	String s = "12345678901234567890.123";
    	String type = java.math.BigDecimal.class.getName();
    	BigDecimal value = (BigDecimal) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToBigInteger() throws Exception {
    	String s = "12345678901234567890";
    	String type = java.math.BigInteger.class.getName();
    	BigInteger value = (BigInteger) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToBoolean() throws Exception {
    	String s = "true";
    	String type = java.lang.Boolean.class.getName();
    	Boolean value = (Boolean) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(s, value.toString());
    }

    @Test
    public void testConvertToDataType_StringToDate() throws Exception {
    	Date date = new Date(System.currentTimeMillis());
    	String s = BusinessRuleUtil.formatIsoDate(date);
    	String type = java.util.Date.class.getName();
    	Date value = (Date) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(date.toString(), value.toString());
    }

    @Test
    public void testConvertToDataType_StringToCalendar() throws Exception {
    	Calendar time = Calendar.getInstance();
    	String s = BusinessRuleUtil.formatIsoDate(time.getTime());
    	String type = java.util.Calendar.class.getName();
    	Calendar value = (Calendar) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(time.toString(), value.toString());
    }

    @Test
    public void testConvertToDataType_StringToGregorianCalendar() throws Exception {
    	Calendar time = GregorianCalendar.getInstance();
    	String s = BusinessRuleUtil.formatIsoDate(time.getTime());
    	String type = java.util.GregorianCalendar.class.getName();
    	GregorianCalendar value = (GregorianCalendar) BusinessRuleUtil.convertToDataType(type,s);
    	Assert.assertEquals(time.toString(), value.toString());
    }

    @Test
    public void testConvertToDataType_StringToXMLGregorianCalendar() throws Exception {
    	GregorianCalendar greg = new GregorianCalendar();
    	DatatypeFactoryImpl factory = new DatatypeFactoryImpl();
    	XMLGregorianCalendar time = factory.newXMLGregorianCalendar(greg);

    	String xsdDate = time.toString();
    	String type = javax.xml.datatype.XMLGregorianCalendar.class.getName();
    	XMLGregorianCalendar value = (XMLGregorianCalendar) BusinessRuleUtil.convertToDataType(type, xsdDate);
    	Assert.assertEquals(time.toString(), value.toString());
    }
}
