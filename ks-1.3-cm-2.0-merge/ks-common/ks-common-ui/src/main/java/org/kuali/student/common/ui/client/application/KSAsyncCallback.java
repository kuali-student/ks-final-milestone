/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.application;

import org.kuali.student.common.ui.client.security.SessionTimeoutHandler;
import org.kuali.student.common.ui.client.security.SpringSecurityLoginDialogHandler;
import org.kuali.student.common.ui.client.service.exceptions.VersionMismatchClientException;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This extends the AysncCallback to handle uncaught RPC exceptions and
 * handle authentication.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class KSAsyncCallback<T> implements AsyncCallback<T>{
       
	private static final SessionTimeoutHandler sessionTimeoutHandler = GWT.create(SpringSecurityLoginDialogHandler.class);
	
	/**
	 * It is recommended that this method not be overrided, as it provides session timeout handling. 
	 * Instead the handleFailure method should be overriden to handle rpc call error.
	 *  
	 */
	public void onFailure(Throwable caught) {  
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
