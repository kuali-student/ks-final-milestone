package org.kuali.student.poc.common.ws.security.metro;

import org.kuali.student.poc.common.ws.security.AuthenticationService;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;

public class PlainTextPasswordValidator implements
		PasswordValidationCallback.PasswordValidator {
   
    public boolean validate(PasswordValidationCallback.Request request)
			throws PasswordValidationCallback.PasswordValidationException {

		PasswordValidationCallback.PlainTextPasswordRequest 
		plainTextRequest = (PasswordValidationCallback.PlainTextPasswordRequest) request;
			
		return AuthenticationService.validateUsernamePassword(plainTextRequest.getUsername(), plainTextRequest.getPassword());
    }
}