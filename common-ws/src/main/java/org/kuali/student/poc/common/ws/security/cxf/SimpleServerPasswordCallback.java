package org.kuali.student.poc.common.ws.security.cxf;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.kuali.student.poc.common.ws.security.AuthenticationService;

public class SimpleServerPasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		    
		//Set the expected password for this user
		pc.setPassword(AuthenticationService.getPasswordForUsername(pc.getIdentifer()));
	}
}
