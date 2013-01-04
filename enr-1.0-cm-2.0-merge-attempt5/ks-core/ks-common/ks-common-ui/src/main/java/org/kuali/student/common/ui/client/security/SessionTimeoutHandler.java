package org.kuali.student.common.ui.client.security;

/**
 * Implement this interface to provide a timeout handler for session timeout.
 * 
 * @author Kuali Student Team
 * 
 */
public interface SessionTimeoutHandler {
	
	/**
	 * Detects if session has timed out
	 */
	public boolean isSessionTimeout(Throwable error);
	
	/**
	 * Performs operations needed to handle a session timeout.
	 */
	public void handleSessionTimeout();
}
