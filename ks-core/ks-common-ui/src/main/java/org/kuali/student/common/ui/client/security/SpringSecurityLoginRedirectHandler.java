package org.kuali.student.common.ui.client.security;

import com.google.gwt.user.client.Window;

/**
 * This implements the SessionTimeoutHandler. The timeout is handled by redirecting the
 * user to the application root, so the spring login form will be displayed.  
 * 
 * @author Kuali Student Team
 *
 */
public class SpringSecurityLoginRedirectHandler implements
		SessionTimeoutHandler {

	@Override
	public void handleSessionTimeout() {
		//FIXME: Need a way to get the proper redirect url, for now just reloading the page
		//redirect(GWT.getHostPageBaseURL());
		
		Window.alert("Your session has timed out and are being redirected to the login page.");
		reload();
	}

	public static native void redirect(String url)/*-{
	      $wnd.location = url;
	}-*/;

	public static native void reload()/*-{
		$wnd.location.reload();
	}-*/;
 
}
