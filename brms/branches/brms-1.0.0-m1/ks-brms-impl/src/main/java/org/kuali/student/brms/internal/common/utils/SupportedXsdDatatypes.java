package org.kuali.student.brms.internal.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;

/**
 * This class converts an XSD data type into a Java datatype.
 * 
 * <p>XML Schema Part 2: Datatypes Second Edition<br/>
 * W3C Recommendation 28 October 2004</p>
 * 
 * <p><a href="http://www.w3.org/TR/2004/REC-xmlschema-2-20041028/" />
 * http://www.w3.org/TR/2004/REC-xmlschema-2-20041028/</a></p>
 * 
 * <p><b>Supported Datatype</b></p>
 * 3.2 Primitive datatypes:<br/>
 * 3.2.1 string<br/>
 * 3.2.2 boolean<br/>
 * 3.2.3 decimal<br/>
 * 3.2.4 float<br/>
 * 3.2.5 double<br/>
 * 3.2.7 dateTime<br/>
 * 3.2.8 time - Not supported, use dateTime<br/>
 * 3.2.9 date - Not supported, use dateTime<br/>
 * <br/>
 * 3.3 Derived datatypes<br/>
 * 3.3.13 integer<br/>
 * 3.3.16 long<br/>
 * 3.3.17 int<br/>
 * 3.3.18 short - Not supported<br/>
 * 3.3.19 byte - Not supported<br/>
 */ 
public class SupportedXsdDatatypes {
	/**
	 * Supported XSD datatype.
	 */
	public enum Datatype {STRING, BOOLEAN, DECIMAL, INT, INTEGER, FLOAT, DOUBLE, LONG, DATETIME};

    /**
     * Converts an XSD datatype into a Java datatype using the 
     * JAXB data type converter.
     * 
     * @param xsdDataType XSD Data type
     * @param value String value
     * @return Value in its proper Java datatype
     */
    public static Object convertToXsdDataType(final Datatype xsdDataType, final String xsdStringValue) {
    	if (xsdStringValue == null) {
    		return null;
    	}
    	
    	switch(xsdDataType) {
    		case STRING:
    			return DatatypeConverter.parseString(xsdStringValue);
    		case BOOLEAN:
    			return new Boolean(DatatypeConverter.parseBoolean(xsdStringValue));
    		case DECIMAL:
    			return DatatypeConverter.parseDecimal(xsdStringValue);
    		case FLOAT:
    			return new Float(DatatypeConverter.parseFloat(xsdStringValue));
    		case DOUBLE:
    			return new Double(DatatypeConverter.parseDouble(xsdStringValue));
    		case DATETIME:
    			return DatatypeConverter.parseDateTime(xsdStringValue);
    		case INT:
    			return new Integer(DatatypeConverter.parseInt(xsdStringValue));
    		case INTEGER:
    			return DatatypeConverter.parseInteger(xsdStringValue);
    		case LONG:
    			return new Long(DatatypeConverter.parseLong(xsdStringValue));
    	}
		throw new AssertionError("Data type conversion error. Unknown XSD datatype: " + xsdDataType);
    }
    
    /**
     * Gets the Java class (datatype) for the XSD datatype.
     * 
     * @param xsdDataType XSD datatype
     * @return Java class
     */
    public static Class<?> getJavaDataType(final Datatype xsdDataType) {
    	switch(xsdDataType) {
			case STRING:
				return String.class;
			case BOOLEAN:
				return Boolean.class;
			case DECIMAL:
				return BigDecimal.class;
			case FLOAT:
				return Float.class;
			case DOUBLE:
				return Double.class;
			case DATETIME:
				return Calendar.class;
			case INT:
				return Integer.class;
			case INTEGER:
				return BigInteger.class;
			case LONG:
				return Long.class;
		}
		throw new AssertionError("Data type conversion error. Unknown XSD datatype: " + xsdDataType);
    }
}
