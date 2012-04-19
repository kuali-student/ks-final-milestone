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

package org.kuali.student.common.ui.server.gwt;

import java.util.*;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.util.security.SecurityUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This provides security RPC services to the GWT Application.  It should be noted that this
 * does not provide true client authorization as these calls can be easily manipulated by the
 * end user.  These calls are to be used to solely hide application components for
 * users which are not privileged to view them and the check is merely for visual display.
 * 
 * The real security checks are performed via security checks on the data RPC get/save
 * operations as well as masking/hiding of data returned to the browser.
 * 
 * @author Kuali Student Team
 *
 */
public class SecurityRpcGwtServlet extends RemoteServiceServlet implements SecurityRpcService{

	final Logger LOG = Logger.getLogger(SecurityRpcGwtServlet.class);
	
	private static final long serialVersionUID = 1L;
    
	private PermissionService permissionService;
       
	@Override
    public String getPrincipalUsername(){
    	return SecurityUtils.getCurrentPrincipalName();
    }

	@Override
	public HashMap<String, Boolean> getScreenPermissions(ArrayList<String> screens) {
		// TODO Auto-generated method stub
		return null;
	}
	 
	@Override
	public HashMap<String, Boolean> getPermissions(ArrayList<String> permissionNames) throws OperationFailedException{
		String principalId = SecurityUtils.getCurrentPrincipalId();
		
		LOG.debug("Retreiving permissions for permission name: " + permissionNames + " for " + principalId);
		
		//FIXME: Is there a way to retrieve multiple permissions at once instead of calling isAuthorized multiple times?
		Map<String, String> permDetails = new LinkedHashMap<String, String>();
		HashMap<String,Boolean> permissions = new HashMap<String,Boolean>();
		for (String permissionName:permissionNames){
			boolean hasAccess = getPermissionService().isAuthorized(principalId, "KS-SYS", permissionName, permDetails);
			permissions.put(permissionName, hasAccess);
		}
				
		return permissions;
	}
	
	@Override
	public Boolean hasScreenPermission(String screenName) throws OperationFailedException {
		String principalId = SecurityUtils.getCurrentPrincipalId();
		
		LOG.debug("Retreiving screen permission " + screenName + " for " + principalId);		
			
        Map<String, String> permDetails = new LinkedHashMap<String, String>();
        permDetails.put(StudentIdentityConstants.SCREEN_COMPONENT, screenName);
        boolean hasAccess = false;
        hasAccess = getPermissionService().isAuthorizedByTemplate(principalId, 
					PermissionType.USE_SCREEN.getPermissionNamespace(), 
					PermissionType.USE_SCREEN.getPermissionTemplateName(), permDetails, 
					permDetails);

        LOG.debug(principalId + " access : " + hasAccess);
        
		return hasAccess;
	}
	
	
	@Override
	public Boolean hasPermissionByPermissionName(String permissionName)	throws OperationFailedException {		
		String principalId = SecurityUtils.getCurrentPrincipalId();
		
		LOG.debug("Retreiving permissions for permission name: " + permissionName + " for " + principalId);
		
		//TODO: Do we need to worry about permission details when checking by permission name
		Map<String, String> permDetails = new LinkedHashMap<String, String>();
		boolean hasAccess = false;
		hasAccess = getPermissionService().isAuthorized(principalId, "KS-SYS", permissionName, permDetails);
		
		LOG.debug(principalId + " access : " + hasAccess);
		
		return hasAccess;
	}

	/**
	 * This will return all permissions assigned to this user.
	 * 
	 * TODO: Need to determine if permission details are required.   
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getPermissionsByType(PermissionType permissionType) throws OperationFailedException {
		ArrayList<String> matchingPermissions = new ArrayList<String>();
	
		String principalId = SecurityUtils.getCurrentPrincipalId();
		
		LOG.debug("Retreiving permissions for template: " + permissionType.getPermissionTemplateName() + " for " + principalId);
 
		Map<String, String> permDetails = new LinkedHashMap<String, String>();
		List<Permission> permissions = permissionService.getAuthorizedPermissionsByTemplate(
				principalId, permissionType.getPermissionNamespace(), permissionType.getPermissionTemplateName(), permDetails, permDetails);

		for (Permission permissionInfo:permissions){
			matchingPermissions.add(permissionInfo.getName());
		}
		
		return matchingPermissions;
	}
	
	
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public PermissionService getPermissionService()throws OperationFailedException{
		if(permissionService==null){
        	throw new OperationFailedException("Permission Service is unavailable");
        }

		return permissionService;
	}

    protected Map<String, String> getQualification(String idType, String id, String docType) {
        Map<String, String> qualification = new LinkedHashMap<String, String>();
        qualification.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, docType);
        qualification.put(idType, id);
        //Put in a random number to avoid this request from being cached. Might want to do this only for specific templates to take advantage of caching
        qualification.put("RAND_NO_CACHE", UUID.randomUUID().toString());
        return qualification;
    }

}
