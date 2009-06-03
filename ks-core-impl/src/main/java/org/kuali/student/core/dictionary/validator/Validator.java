package org.kuali.student.core.dictionary.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.kuali.student.core.validation.dto.ValidationResult;

/**
 * @deprecated use org.kuali.student.common.validator.Validator instead
 */
@Deprecated
public class Validator {
	
	final static Logger logger = LoggerFactory.getLogger(Validator.class);
	
	/**
	 * @deprecated use org.kuali.student.common.validator.Validator instead
	 */
	@Deprecated
	public static ValidationResult validate(Object value, Map<String, Object> fieldDesc){
		ValidationResult result = new ValidationResult();
		
		String dataType = ((String)fieldDesc.get("dataType")).trim();
		if(dataType.equalsIgnoreCase("integer")){
			if(value instanceof Integer){
				result = validateInteger((Integer)value, fieldDesc);
			}
			else{
				result.addError("The value's type and required dataType do not match.");
			}
    	}
		else if(dataType.equalsIgnoreCase("string")){
			if(value instanceof String){
				result = validateString((String)value, fieldDesc);
			}
			else{
				result.addError("The value's type and required dataType do not match.");
			}
    	}
		else if(dataType.equalsIgnoreCase("Date")){
			if(value instanceof String){
	    		DateFormat format = new SimpleDateFormat();
	    		String stringValue = ((String)value).trim();
	    		if(stringValue.length() == 10){
	    			format = new SimpleDateFormat("yyyy-MM-dd");
	    		}
	    		else if(stringValue.length() == 23){
	    			format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss,SSS");
	    		}
	    		
	    		try{
	                Date convertedValue = format.parse(stringValue);
	                result = validateDate(convertedValue, fieldDesc);
	    		}
	    		catch(ParseException e){
	    			logger.error("Could not parse string to a Date value - are you sure this field is supposed to be a Date? : " + stringValue, e);
	    			throw new IllegalArgumentException("Could not parse string to a Date value - are you sure this field is a Date? : " + stringValue, e);
	    		}	
			}
			else if(value instanceof Date){
				result = validateDate((Date)value, fieldDesc);
			}
			else{
				result.addError("The value's type and required dataType do not match.");
			}
    	}
		else{
			logger.error("dataType field did not match any of the expected dataTypes : " + dataType);
		}

		return result;
	}
	
	private static ValidationResult validateInteger(Integer value, Map<String, Object> fieldDesc){
		ValidationResult result = new ValidationResult();
		
		
		String maxValue = ((String)fieldDesc.get("maxValue"));
    	if(maxValue != null && !maxValue.equals("")){
    		int max;
    		try{
    			max = Integer.parseInt(maxValue);
    		}
    		catch(NumberFormatException e){
    			throw new IllegalArgumentException("Invalid maxValue for data type Integer: " + maxValue, e);
    		}
    		
    		if(value > max){
    			result.addError("The value is greater than the allowed max value");
    		}
    		
    	}
    	
		String minValue = ((String)fieldDesc.get("minValue"));
    	if(minValue != null && !minValue.equals("")){
    		int min;
    		try{
    			min = Integer.parseInt(minValue);
    		}
    		catch(NumberFormatException e){
    			throw new IllegalArgumentException("Invalid minValue for data type Integer: " + minValue, e);
    		}
    		
    		if(value < min){
    			result.addError("The value is less than the required minimum value");
    		}
    		
    	}
    	
    	return result;
	}
	
	private static ValidationResult validateDate(Date value, Map<String, Object> fieldDesc){
		ValidationResult result = new ValidationResult();
		
		
		String maxValue = ((String)fieldDesc.get("maxValue"));
    	if(maxValue != null && !maxValue.equals("")){
    		DateFormat format = new SimpleDateFormat();
    		if(maxValue.length() == 10){
    			format = new SimpleDateFormat("yyyy-MM-dd");
    		}
    		else if(maxValue.length() == 23){
    			format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss,SSS");
    		}
    		
    		try{
                Date max = format.parse(maxValue);
                if(value.after(max)){
                	result.addError("The date is after the latest date allowed.");
                }
    		}
    		catch(ParseException e){
    			
    		}
    		
    	}
    	
		String minValue = ((String)fieldDesc.get("minValue"));
    	if(minValue != null && !minValue.equals("")){
    		DateFormat format = new SimpleDateFormat();
    		if(minValue.length() == 10){
    			format = new SimpleDateFormat("yyyy-MM-dd");
    		}
    		else if(minValue.length() == 23){
    			format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss,SSS");
    		}
    		
    		try{
                Date min = format.parse(minValue);
                if(value.before(min)){
                	result.addError("The date is before the earliest date allowed.");
                }
    		}
    		catch(ParseException e){
    			
    		}
    		
    	}
		
    	return result;
	}
	
	private static ValidationResult validateString(String value, Map<String, Object> fieldDesc){
		ValidationResult result = new ValidationResult();
		
    	
    	//maxLength, non-set max length is null
    	if(((Integer)fieldDesc.get("maxLength")) != null && ((Integer)fieldDesc.get("maxLength")) < value.length()){
    		result.addError("The value's length is greater than allowed max length.");
    	}
    	
    	//minLength, non-set min length is null
    	if(((Integer)fieldDesc.get("minLength")) != null && ((Integer)fieldDesc.get("minLength")) > value.length()){
    		result.addError("The value's length is less than than the required min length.");
    	}
    	
    	
    	//validChars, a non-set valid chars field means all characters valid (other than those in invalid)
    	String validChars = ((String)fieldDesc.get("validChars")).trim();
    	if(validChars != null && !validChars.equals("")){
    		char[] valueChars = value.toCharArray();
    		for(char c: valueChars){
    			if(validChars.indexOf(c) == -1){
    				result.addError("The value contains a character that is not in the valid set.");
    			}
    		}
    	
    	}
    	//invalidChars, a non-set invalid chars field means no chars are specified as invalid
    	String invalidChars = ((String)fieldDesc.get("invalidChars")).trim();
    	if(invalidChars != null && !invalidChars.equals("")){
    		char[] valueChars = value.toCharArray();
    		for(char c: valueChars){
    			if(invalidChars.indexOf(c) >= 0){
    				result.addError("The value contains a character that defined as invalid.");
    			}
    		}
    	}
    	
    	if(((String)fieldDesc.get("maxValue")) != null && !((String)fieldDesc.get("maxValue")).equals("")){
    		logger.warn("maxValue is not used for Strings");
    	}
    	
    	if(((String)fieldDesc.get("minValue")) != null && !((String)fieldDesc.get("minValue")).equals("")){
    		logger.warn("minValue is not used for Strings");
    	}
    	
		return result;
	}
}
