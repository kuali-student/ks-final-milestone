package org.kuali.student.common.ui.client.security;

import org.kuali.student.common.ui.client.util.BrowserUtils;

import com.google.gwt.user.client.Window;

/**
 * This implements the SessionTimeoutHandler. The timeout is handled by redirecting the
 * user to the application root, so the spring login form will be displayed.  
 * 
 * @author Kuali Student Team
 *
 */
@Deprecated
public class SpringSecurityLoginRedirectHandler implements SessionTimeoutHandler {

	/**
	 * This assumes the login mechanism is the standard spring security.
	 * 
	 */
	@Override
	public boolean isSessionTimeout(Throwable error) {
    	return error.toString().contains("Login");
	}

	@Override
	public void handleSessionTimeout() {
		//FIXME: Need a way to get the proper redirect url, for now just reloading the page
		//BrowserUtils.redirect(GWT.getHostPageBaseURL());
		
		Window.alert("Your session has timed out and are being redirected to the login page.");
		BrowserUtils.reload();
	}
 
}
