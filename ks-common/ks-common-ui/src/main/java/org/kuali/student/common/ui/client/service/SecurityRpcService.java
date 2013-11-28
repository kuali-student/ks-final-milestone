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

import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.r1.common.rice.authorization.PermissionType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Kuali Student Team
 * 
 */
@RemoteServiceRelativePath("rpcservices/SecurityRpcService")
public interface SecurityRpcService extends RemoteService {

	public String getPrincipalUsername();

	/**
	 * This is used to check if the user has screen access based on a screen permission. 
	 * 
	 * @param screenName
	 * @return true if user has permission
	 * @throws OperationFailedException
	 */
	public Boolean hasScreenPermission(String screenName) throws OperationFailedException;
	
	
	/**
	 * Given a list of screen components, returns if user has permission for that screen element.
	 * 
	 * @param screens
	 * @return
	 * @throws OperationFailedException
	 */
	public HashMap<String,Boolean> getScreenPermissions(ArrayList<String> screens) throws OperationFailedException; 
	
	/**
	 * Given a list of permission names returns the list of permissions and if the 
	 * @param permissionNames
	 * @return
	 * @throws OperationFailedException
	 */
	public HashMap<String,Boolean> getPermissions(ArrayList<String> permissionNames) throws OperationFailedException;
	
	/**
	 * This is used to check if the user has a specific permission by the permission name
	 * 
	 * @param permissionName
	 * @return true if user has the permission.
	 */
	public Boolean hasPermissionByPermissionName(String permissionName) throws OperationFailedException;
	
	/**
	 * This is used to get all permissions assigned to the user based on a permission template.
	 * 
	 * @param templateName
	 * @return list of permission names
	 */
	public ArrayList<String> getPermissionsByType(PermissionType permissionType) throws OperationFailedException;
	
	/**
     * This is used to get all permissions assigned to the user based on a permission template.
     * 
     * @param permissionType
     * @param attributes
     * @return list of permission names
     * @throws OperationFailedException
     */
    public ArrayList<String> getPermissionsByType(PermissionType permissionType, HashMap<String, String> attributes) throws OperationFailedException;
}
