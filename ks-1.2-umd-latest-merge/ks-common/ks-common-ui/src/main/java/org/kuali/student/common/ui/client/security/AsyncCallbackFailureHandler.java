package org.kuali.student.common.ui.client.security;

import org.kuali.student.common.ui.client.service.exceptions.VersionMismatchClientException;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * Default KS implementation of failure handling.
 * <p>
 * Implementing institution should override this class to 
 * custom handle failures.
 */
public class AsyncCallbackFailureHandler {
  
    /**
     * Attempt to provide default session timeout handling.
     */
    protected static final SessionTimeoutHandler sessionTimeoutHandler = GWT.create(SpringSecurityLoginDialogHandler.class);
    
    public void onFailure(Throwable caught) {
        // This code attempts to handle session timeouts
        // it appears to be half finished.  The implementing
        // institution may wish to override the method to
        // to properly detect session timeout
        
        // The idea with the if/else below is to attempt to detect
        // the type of error and handle it appropriately. It was
        // not finished, so only these two errors are detected.  
        // There is also inconsistent handling of errors in the 
        // individual widgets as well. 
        if (sessionTimeoutHandler.isSessionTimeout(caught)){
             handleTimeout(caught);
             sessionTimeoutHandler.handleSessionTimeout();
        } else if (caught instanceof VersionMismatchClientException){
             handleVersionMismatch(caught);
         }else {        
             handleFailure(caught);
         } 
    }
     
    /**
     * Override this method to process any exceptions you wish to handle. The default implementation displays 
     * an error dialog with the message and logs the exception.
     * 
     * @param caught
     */
    public void handleFailure(Throwable caught){
        if (!sessionTimeoutHandler.isSessionTimeout(caught)){
            KSErrorDialog.show(caught);
        }
        GWT.log("Exception:", caught);
    }

    /**
     * By default this defers to handleFailure. Override this handle a rpc failure due to timeout differently
     * from other exceptions.
     * 
     * @param caught
     */
    public void handleTimeout(Throwable caught){
        handleFailure(caught);
    }
        
    public void handleVersionMismatch(Throwable caught){
        String message = null;
        if (caught.getMessage() != null){
            message = "Version Error: " + caught.getMessage() + "\n\n";
        }
        message += "This page has been updated by another user since you loaded it. Please refresh and re-apply changes before saving.";
        Window.alert(message);
    }
    
}
