package org.kuali.student.brms.internal.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupportedXsdDatatypesTest {
	@BeforeClass
    public static void setUpOnce() throws Exception {
	}
	
    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConvertToXsdDataType_String() throws Exception {
    	String s = "Kuali ONE";
    	String c = (String) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.STRING, s);
    	Assert.assertEquals(s, c);
    }

    @Test
    public void testConvertToXsdDataType_Boolean() throws Exception {
    	String s = "true";
    	Boolean c = (Boolean) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.BOOLEAN, s);
    	Assert.assertEquals(s, c.toString());
    }

    @Test
    public void testConvertToXsdDataType_DateTime() throws Exception {
    	GregorianCalendar greg = new GregorianCalendar();
    	DatatypeFactoryImpl factory = new DatatypeFactoryImpl();
    	XMLGregorianCalendar time = factory.newXMLGregorianCalendar(greg);
    	Calendar value = (Calendar) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.DATETIME, time.toXMLFormat());
    	Assert.assertEquals(time.toGregorianCalendar(), value);
    }

    @Test
    public void testConvertToXsdDataType_Decimal() throws Exception {
    	String s = "123.456";
    	BigDecimal r = (BigDecimal) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.DECIMAL, s);
    	Assert.assertEquals(s, r.toString());
    }

    @Test
    public void testConvertToXsdDataType_Double() throws Exception {
    	String s = "123.456";
    	Double r = (Double) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.DOUBLE, s);
    	Assert.assertEquals(s, r.toString());
    }

    @Test
    public void testConvertToXsdDataType_Float() throws Exception {
    	String s = "123.456";
    	Float r = (Float) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.FLOAT, s);
    	Assert.assertEquals(s, r.toString());
    }

    @Test
    public void testConvertToXsdDataType_Integer() throws Exception {
    	String s = "123";
    	BigInteger r = (BigInteger) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.INTEGER, s);
    	Assert.assertEquals(s, r.toString());
    }

    @Test
    public void testConvertToXsdDataType_BigInteger() throws Exception {
    	String s = "123";
    	Integer r = (Integer) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.INT, s);
    	Assert.assertEquals(s, r.toString());
    }

    @Test
    public void testConvertToXsdDataType_Long() throws Exception {
    	String s = "123456789";
    	Long r = (Long) SupportedXsdDatatypes.convertToXsdDataType(SupportedXsdDatatypes.Datatype.LONG, s);
    	Assert.assertEquals(s, r.toString());
    }


    
    @Test
    public void testGetJavaDataType_String() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.STRING);
    	Assert.assertEquals(String.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_Boolean() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.BOOLEAN);
    	Assert.assertEquals(Boolean.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_DateTime() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.DATETIME);
    	Assert.assertEquals(Calendar.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_BigDecimal() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.DECIMAL);
    	Assert.assertEquals(BigDecimal.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_Double() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.DOUBLE);
    	Assert.assertEquals(Double.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_Float() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.FLOAT);
    	Assert.assertEquals(Float.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_Integer() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.INT);
    	Assert.assertEquals(Integer.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_BigInteger() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.INTEGER);
    	Assert.assertEquals(BigInteger.class.getName(), c.getName());
    }

    @Test
    public void testGetJavaDataType_Long() throws Exception {
    	Class<?> c = SupportedXsdDatatypes.getJavaDataType(SupportedXsdDatatypes.Datatype.LONG);
    	Assert.assertEquals(Long.class.getName(), c.getName());
    }


}
