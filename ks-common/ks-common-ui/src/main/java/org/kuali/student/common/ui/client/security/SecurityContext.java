/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.security;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcServiceAsync;

import com.google.gwt.core.client.GWT;

/**
 * An interface to access the Kuali Student security context 
 * 
 * @author Kuali Student Team
 *
 */
public class SecurityContext {

    protected SecurityRpcServiceAsync securityRpcService = GWT.create(SecurityRpcService.class);
    
	private String principalName = "";		
     
	public void initializeSecurityContext(final Callback<Boolean> contextIntializedCallback){
	    securityRpcService.getPrincipalUsername(new KSAsyncCallback<String>(){
	        public void handleFailure(Throwable caught) {
				throw new RuntimeException("Fatal - Unable to initialze security context");
			}
	
	        @Override
	        public void onSuccess(String userId) {
	            setPrincipalName(userId);
	            contextIntializedCallback.exec(true);
	        }            
	    });
	}

	/**
	 * Set principalName of the user logged in (eg. jDoe)   
	 *
	 * @param principalId
	 */
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	/**
	 * @return the userId of user logged into the system. (NOTE: When using KIM userId=principalName)
	 */
	public String getUserId() {
		return principalName;
	}
	
	/**
	 * Used to check of user has access to the view
	 * 
	 * @param viewName
	 * @return
	 */
	public void checkPermission(String viewName, final Callback<Boolean> checkPermissionCallback){
        securityRpcService.hasScreenPermission(viewName, new KSAsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
            	checkPermissionCallback.exec(result);
            }
        });		
	}
}
