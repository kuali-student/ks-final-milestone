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

package org.kuali.student.common.ui.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.kuali.student.r1.common.rice.authorization.PermissionType;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Async interface for SecurityRpcService 
 * 
 * @author Kuali Student Team
 *
 */
public interface SecurityRpcServiceAsync {
    
    public void getPrincipalUsername(AsyncCallback<String> callback);
	public void hasScreenPermission(String screenName, AsyncCallback<Boolean> callback);	
	public void getScreenPermissions(ArrayList<String> screens, AsyncCallback<HashMap<String,Boolean>> callback);     
	public void getPermissions(ArrayList<String> permissionNames, AsyncCallback<HashMap<String,Boolean>> callback);
	public void hasPermissionByPermissionName(String permissionName, AsyncCallback<Boolean> callback);
	public void getPermissionsByType(PermissionType type, AsyncCallback<ArrayList<String>> callback);
	public void getPermissionsByType(PermissionType permissionType, HashMap<String,String> attributes, AsyncCallback<ArrayList<String>> callback);
	
}
