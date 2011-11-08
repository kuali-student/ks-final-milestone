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

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityManagementService;
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
    
	private IdentityManagementService permissionService;
       
	@Override
    public String getPrincipalUsername(){
    	return SecurityUtils.getCurrentPrincipalName();
    }

	@Override
	public HashMap<String, Boolean> getScreenPermissions(
			ArrayList<String> screens) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasScreenPermission(String screenName) throws OperationFailedException {
		String principalId = SecurityUtils.getCurrentPrincipalId();
		
		LOG.debug("SecurityRpcGwtServlet.hasCreenPermission: " + principalId + " component " + screenName);
		
			
        AttributeSet permDetails = new AttributeSet();
        permDetails.put(StudentIdentityConstants.SCREEN_COMPONENT, screenName);
        boolean hasAccess = false;
        hasAccess = getPermissionService().isAuthorizedByTemplateName(principalId, 
					PermissionType.KS_ADMIN_SCREEN.getPermissionNamespace(), 
					PermissionType.KS_ADMIN_SCREEN.getPermissionTemplateName(), permDetails, 
					permDetails);

        LOG.debug(principalId + " has access : " + hasAccess);
        
		return hasAccess;
	}
	
	
	
	public void setPermissionService(IdentityManagementService permissionService) {
		this.permissionService = permissionService;
	}

	public IdentityManagementService getPermissionService()throws OperationFailedException{
		if(permissionService==null){
        	throw new OperationFailedException("Permission Service is unavailable");
        }

		return permissionService;
	}
	
}
