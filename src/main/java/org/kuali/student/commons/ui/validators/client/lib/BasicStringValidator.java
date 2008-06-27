package org.kuali.student.commons.ui.validators.client.lib;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.commons.ui.validators.client.Validator;
import org.kuali.student.commons.ui.validators.client.ValidationResult.ValidationState;


public class BasicStringValidator extends Validator {

	public ValidationResult validate(String value, String label) {
		ValidationResult result = new ValidationResult();
		Messages messages = super.getMessages();
		
		value = (value == null) ? "" : value.trim();
		
		
		boolean failedMinLengthCheck = false;
		int minLength = super.getAttributeAsInt("minLength", 0);
		int maxLength = super.getAttributeAsInt("maxLength", Integer.MAX_VALUE);
		
		if (value.length() < minLength) {
			result.addMessage(ValidationState.FAILED, messages.get("failedMinLength", super.getAttributes(), label));
			failedMinLengthCheck = true;
		}
		
		if (value.length() > maxLength) {
			result.addMessage(ValidationState.FAILED, messages.get("failedMaxLength", super.getAttributes(), label));
		}
		
		if (failedMinLengthCheck == false && super.getAttributeAsBoolean("required", false) && value.equals("")) {
			result.addMessage(ValidationState.FAILED, messages.get("isRequired", label));
		}
		
		if (result.getState() != ValidationState.FAILED) {
			String regex = super.getAttribute("regex");
			regex = (regex==null) ? "" : regex.trim();
			if (!regex.equals("")) {
				// Note the hack to work in case-insensitive matching,
				// make sure all regexes define characters as uppercase
				if (!value.toUpperCase().matches(regex)) {
					result.addMessage(ValidationState.FAILED, messages.get("failedRegex", label));
				}
			}
		}
		
		return result;
	}
	
	

}
