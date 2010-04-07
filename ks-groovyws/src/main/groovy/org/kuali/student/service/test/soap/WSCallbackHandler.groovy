/**
 * 
 */
package org.kuali.student.service.test.soap

import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler

import org.apache.ws.security.WSPasswordCallback
/**
 * Simple implementation of WSPasswordCallback
 * kind of ugly that the pw needs to be hard-coded into the class impl
 * @author randy
 *
 */
public class WSCallbackHandler implements CallbackHandler{


	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	public void handle(Callback[] callbacks){
		println 'handle pw callback'
        
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0]
		def pw = MerlinCryptoHelper.keystorePassword
		println 'keystorePassword is: ' + pw
        pc.setPassword(pw)
	}
	
}
