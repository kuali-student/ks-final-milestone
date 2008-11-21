package org.kuali.student.rules.internal.common.utils;

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
 * 3.2.8 time<br/>
 * 3.2.9 date<br/>
 * <br/>
 * 3.3 Derived datatypes<br/>
 * 3.3.13 integer<br/>
 * 3.3.16 long<br/>
 * 3.3.17 int<br/>
 * 3.3.18 short<br/>
 * 3.3.19 byte<br/>
 */ 
public class SupportedXsdDatatypes {

	public enum Datatype {STRING, BOOLEAN, DECIMAL, FLOAT, DOUBLE, 
		DATETIME, DATE, TIME, INTEGER, LONG, INT, SHORT, BYTE};


    /**
     * Converts an XSD datatype into a Java datatype using the 
     * JAXB data type converter.
     * 
     * @param xsdDataType XSD Data type
     * @param value String value
     * @return Value in its proper Java datatype
     */
    public static Object convertToXsdDataType(
    		final Datatype xsdDataType, final String xsdStringValue) {
    	if (xsdStringValue == null) {
    		return null;
    	}
    	
    	switch(xsdDataType) {
    		case STRING:
    			return DatatypeConverter.parseString(xsdStringValue);
    		case BOOLEAN:
    			return DatatypeConverter.parseBoolean(xsdStringValue);
    		case DECIMAL:
    			return DatatypeConverter.parseDecimal(xsdStringValue);
    		case FLOAT:
    			return DatatypeConverter.parseFloat(xsdStringValue);
    		case DOUBLE:
    			return DatatypeConverter.parseDouble(xsdStringValue);
    		case DATE:
    			return DatatypeConverter.parseDate(xsdStringValue);
    		case TIME:
    			return DatatypeConverter.parseTime(xsdStringValue);
    		case DATETIME:
    			return DatatypeConverter.parseDateTime(xsdStringValue);
    		case INT:
    			return DatatypeConverter.parseInt(xsdStringValue);
    		case INTEGER:
    			return DatatypeConverter.parseInteger(xsdStringValue);
    		case LONG:
    			return DatatypeConverter.parseLong(xsdStringValue);
    		case SHORT:
    			return DatatypeConverter.parseShort(xsdStringValue);
    		case BYTE:
    			return DatatypeConverter.parseByte(xsdStringValue);
    	}
		throw new AssertionError("Data type conversion error. Unknown XSD datatype: " + xsdDataType);
    }
}
