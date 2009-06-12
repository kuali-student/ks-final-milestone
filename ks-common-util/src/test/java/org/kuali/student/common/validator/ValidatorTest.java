package org.kuali.student.common.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

public class ValidatorTest {

    @Test
    public void testStringValidator() {
        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        message.setId("validation.required");
        message.setValue("Validation Required");
        messages.add(message);
        
        Validator validator = new Validator();
        validator.addMessages(messages);
        validator.setDateParser(new ServerDateParser());
        
        Map<String,Object> fieldDesc = new HashMap<String,Object>();
        fieldDesc.put("dataType", "string");
        
        String stringToTest = "abcd123";
        
        ValidationResult validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals("keyValue", validate.getKey());
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test validChars
        fieldDesc.put("validChars", "abcdef");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(3, validate.getMessages().size()); //one for each bad letter (123), errormsg: validation.validCharsFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("validChars", "abcdef123456");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test invalidChars
        fieldDesc.remove("validChars");
        fieldDesc.put("invalidChars", "abcdef");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(4, validate.getMessages().size()); //one for each bad letter (abcd), errormsg: validation.invalidCharsFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("invalidChars", "ghijkl456789");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test validChars and invalidChars together
        fieldDesc.put("validChars", "abcdef");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(3, validate.getMessages().size()); //one for each bad letter (123)
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("validChars", "abcdef123456");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("validChars", "bcdef");
        fieldDesc.put("invalidChars", "abcdef");  //bcdef is both valid and invalid
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(8, validate.getMessages().size()); //one for each invalid char present: abcd + one for each char not in valid char: a123 = 8
        for(String msg : validate.getMessages())
            assertNull(msg);
        
        //test minLength
        fieldDesc.put("validChars", "abcdef123456");
        fieldDesc.put("invalidChars", "ghijkl456789");
        fieldDesc.put("minLength", "3");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minLength", Integer.toString(stringToTest.length()));
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minLength", Integer.toString(stringToTest.length() + 1));
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.minLengthFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        
        //test maxLength
        fieldDesc.remove("minLength");
        fieldDesc.put("maxLength", Integer.toString(stringToTest.length() + 1));
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxLength", Integer.toString(stringToTest.length()));
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxLength", "3");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.maxLengthFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        
        //test minLength and maxLength
        fieldDesc.put("maxLength", Integer.toString(stringToTest.length()));
        fieldDesc.put("minLength", "3");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minLength", Integer.toString(stringToTest.length()));
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minLength", Integer.toString(stringToTest.length() + 1));
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size());
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("maxLength", "3");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //violates both min and max, errormsg: validation.lengthOutOfRange
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("minLength", 3);
        fieldDesc.put("maxLength", 3);
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        
        //test minOccurs
        fieldDesc.put("minLength", "0");
        fieldDesc.put("maxLength", Integer.toString(stringToTest.length()));
        fieldDesc.put("minOccurs", "1");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        validate = validator.validate("keyValue", "", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size());
        assertEquals("Validation Required", validate.getMessages().get(0)); //test message lookup
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "0");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "2");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        fieldDesc.put("minOccurs", "bob");
        try {
            validate = validator.validate("keyValue", null, fieldDesc);
            Assert.fail("Should've got a NumberFormatException");
        } catch(NumberFormatException nfe) {
        }
    }
    
    @Test
    public void testIntegerValidator() {
        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        message.setId("validation.required");
        message.setValue("Validation Required");
        messages.add(message);
        
        Validator validator = new Validator();
        validator.addMessages(messages);
        validator.setDateParser(new ServerDateParser());
        
        Map<String,Object> fieldDesc = new HashMap<String,Object>();
        fieldDesc.put("dataType", "integer");
        
        String stringToTest = "123";
        Integer integerToTest = Integer.valueOf(321);
        Number numberToTest = integerToTest;
        
        ValidationResult validate = validator.validate("keyValue", integerToTest, fieldDesc);
        assertEquals("keyValue", validate.getKey());
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test of other Number types
        numberToTest = Double.valueOf(1.05d);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Double.valueOf(Double.MIN_NORMAL);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Double.valueOf(Double.MAX_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Double.valueOf(Double.NEGATIVE_INFINITY);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Long.valueOf(123456789L);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Long.valueOf(Long.MIN_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Short.valueOf(Short.MAX_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Float.valueOf(Float.MIN_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Byte.valueOf(Byte.MAX_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test string numbers
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "-123", fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "0", fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "1.1", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        validate = validator.validate("keyValue", "123456789123456789", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel()); //but this succeeds as long!
        
        //test minOccurs
        fieldDesc.put("minOccurs", "1");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        validate = validator.validate("keyValue", "", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size());
        assertEquals("Validation Required", validate.getMessages().get(0)); //test message lookup
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "0");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "2");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.remove("minOccurs");
        
        //test minValue
        fieldDesc.put("minValue", "1");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "123");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "124");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.minValueFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        validate = validator.validate("keyValue", integerToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.remove("minValue");
        
        //test maxValue
        fieldDesc.put("maxValue", "999");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxValue", "321");
        validate = validator.validate("keyValue", integerToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxValue", "123");
        validate = validator.validate("keyValue", integerToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.maxValueFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test [min,max]
        fieldDesc.put("minValue", "1");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "123");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "124");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel()); //errormsg: validation.outOfRange
        assertEquals(1, validate.getMessages().size());
        for(String msg : validate.getMessages())
            assertNull(msg);
        validate = validator.validate("keyValue", integerToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        fieldDesc.put("minValue", 123);
        fieldDesc.put("maxValue", 124);
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertTrue(validate.isOk());
        validate = validator.validate("keyValue", integerToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
    }
    
    @Test
    public void testLongValidator() {
        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        message.setId("validation.required");
        message.setValue("Validation Required");
        messages.add(message);
        
        Validator validator = new Validator();
        validator.addMessages(messages);
        validator.setDateParser(new ServerDateParser());
        
        Map<String,Object> fieldDesc = new HashMap<String,Object>();
        fieldDesc.put("dataType", "long");
        
        String stringToTest = "123";
        Long longToTest = Long.valueOf(321);
        Number numberToTest = longToTest;
        
        ValidationResult validate = validator.validate("keyValue", longToTest, fieldDesc);
        assertEquals("keyValue", validate.getKey());
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test of other Number types
        numberToTest = Double.valueOf(1.05d);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Double.valueOf(Double.MIN_NORMAL);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Double.valueOf(Double.MAX_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Double.valueOf(Double.NEGATIVE_INFINITY);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Long.valueOf(123456789L);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Long.valueOf(Long.MIN_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Short.valueOf(Short.MAX_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Float.valueOf(Float.MIN_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        numberToTest = Byte.valueOf(Byte.MAX_VALUE);
        validate = validator.validate("keyValue", numberToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test string numbers
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "-123", fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "0", fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "1.1", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        validate = validator.validate("keyValue", "123456789123456789", fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel()); //but fails as integer!
        
        //test minOccurs
        fieldDesc.put("minOccurs", "1");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        validate = validator.validate("keyValue", "", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size());
        assertEquals("Validation Required", validate.getMessages().get(0)); //test message lookup
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "0");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "2");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.remove("minOccurs");
        
        //test minValue
        fieldDesc.put("minValue", "1");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "123");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "124");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.minValueFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        validate = validator.validate("keyValue", longToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.remove("minValue");
        
        //test maxValue
        fieldDesc.put("maxValue", "999");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxValue", "321");
        validate = validator.validate("keyValue", longToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxValue", "123");
        validate = validator.validate("keyValue", longToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.maxValueFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test [min,max]
        fieldDesc.put("minValue", "1");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "123");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "124");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel()); //errormsg: validation.outOfRange
        assertEquals(1, validate.getMessages().size());
        for(String msg : validate.getMessages())
            assertNull(msg);
        validate = validator.validate("keyValue", longToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
    }
    
    @Test
    public void testDateValidator() {
        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        message.setId("validation.required");
        message.setValue("Validation Required");
        messages.add(message);
        
        Validator validator = new Validator();
        validator.addMessages(messages);
        validator.setDateParser(new ServerDateParser());
        SimpleDateFormat[] formats = ((ServerDateParser)validator.getDateParser()).formats;
        for (SimpleDateFormat format : formats) {
            format.setTimeZone(TimeZone.getTimeZone("GMT-0500"));
        }
        
        Map<String,Object> fieldDesc = new HashMap<String,Object>();
        fieldDesc.put("dataType", "date");
        
        String stringToTest = "2009-01-25";
        Date dateToTest = new Date();
        
        ValidationResult validate = validator.validate("keyValue", dateToTest, fieldDesc);
        assertEquals("keyValue", validate.getKey());
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        
        //test string dates, these are defined by ServerDateParser strings, so check those
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "09-01-25", fieldDesc); //AD 9 was a good year, but did January even exist then?
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "2001-07-04T12:08:56.235-0700", fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        validate = validator.validate("keyValue", "Feb 17 2009", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        
        //test minOccurs
        fieldDesc.put("minOccurs", "1");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        validate = validator.validate("keyValue", "", fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size());
        assertEquals("Validation Required", validate.getMessages().get(0)); //test message lookup
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "0");
        validate = validator.validate("keyValue", null, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minOccurs", "2");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.remove("minOccurs");
        
        //test minValue
        fieldDesc.put("minValue", "1970-01-01");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "2009-01-25");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "2009-01-26");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.minValueFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("minValue", "2009-01-25T00:00:00.000-0500");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "2009-01-25T00:00:00.001-0500");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        fieldDesc.remove("minValue");
        
        //test maxValue
        fieldDesc.put("maxValue", "2099-01-01");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxValue", "2009-01-25");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxValue", "2009-01-24");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.maxValueFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("maxValue", "2009-01-25T00:00:00.000-0500");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("maxValue", "2009-01-24T23:59:59.999-0500");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        
        //test [min,max]
        fieldDesc.put("maxValue", "2009-01-25T00:00:00.000-0500");
        fieldDesc.put("minValue", "1970-01-01");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "2009-01-25");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "2009-01-26");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        assertEquals(1, validate.getMessages().size()); //errormsg: validation.minValueFailed
        for(String msg : validate.getMessages())
            assertNull(msg);
        fieldDesc.put("minValue", "2009-01-25T00:00:00.000-0500");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
        fieldDesc.put("minValue", "2009-01-25T00:00:00.001-0500");
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        fieldDesc.put("maxValue", dateToTest);
        validate = validator.validate("keyValue", stringToTest, fieldDesc);
        assertEquals(ErrorLevel.ERROR, validate.getErrorLevel());
        validate = validator.validate("keyValue", dateToTest, fieldDesc);
        assertEquals(ErrorLevel.OK, validate.getErrorLevel());
    }
}
