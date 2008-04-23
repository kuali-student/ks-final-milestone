package org.kuali.student.poc.common.metro.security;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;

public class PlainTextPasswordValidator implements
		PasswordValidationCallback.PasswordValidator {

	public boolean validate(PasswordValidationCallback.Request request)
			throws PasswordValidationCallback.PasswordValidationException {

		PasswordValidationCallback.PlainTextPasswordRequest 
		plainTextRequest = (PasswordValidationCallback.PlainTextPasswordRequest) request;
			
		//Really simple, allows you trhough if username=password
		if (plainTextRequest.getUsername().equals(plainTextRequest.getPassword())){ 
			return true;
		} else {
			return false;
		}
	}
}