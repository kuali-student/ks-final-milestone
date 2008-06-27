package org.kuali.student.commons.ui.validators.client.lib;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.commons.ui.validators.client.Validator;
import org.kuali.student.commons.ui.validators.client.ValidationResult.ValidationState;


public class BasicNumericValidator extends Validator {

	public ValidationResult validate(String value, String label) {
		ValidationResult result = new ValidationResult();
		Messages messages = super.getMessages();
		
		value = (value == null) ? "" : value.trim();
		
		
		if (super.getAttributeAsBoolean("required", false) && value.equals("")) {
			result.addMessage(ValidationState.FAILED, messages.get("isRequired", label));
		} else {
			try {
				double dbl = Double.parseDouble(value);
				double min = super.getAttributeAsDouble("min", Double.MIN_VALUE);
				double max = super.getAttributeAsDouble("max", Double.MAX_VALUE);
				
				if (dbl < min) {
					result.addMessage(ValidationState.FAILED, messages.get("failedMin", super.getAttributes(), label));
				}
				if (dbl > max) {
					result.addMessage(ValidationState.FAILED,  messages.get("failedMax", super.getAttributes(), label));
				}
				
			} catch (NumberFormatException nfe) {
				result.addMessage(ValidationState.FAILED, messages.get("mustBeNumeric", label));
			}
		}
		return result;
	}
	
	

}
