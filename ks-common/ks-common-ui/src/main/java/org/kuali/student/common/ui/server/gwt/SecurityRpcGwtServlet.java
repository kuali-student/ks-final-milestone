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

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityManagementService;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.util.security.SecurityUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class SecurityRpcGwtServlet extends RemoteServiceServlet implements SecurityRpcService{

    private static final long serialVersionUID = 1L;
    private IdentityManagementService permissionService;
       
    public String getPrincipalUsername(){
    	return SecurityUtils.getPrincipalUserName();
    }

	@Override
	public Boolean checkAdminPermission(String principalId, String screenComponent)  throws OperationFailedException {
		System.out.println("SecurityRpcGwtServlet.checkAdminPermission for : " + principalId + " component " + screenComponent);
        AttributeSet permDetails = new AttributeSet();
        permDetails.put(StudentIdentityConstants.SCREEN_COMPONENT,screenComponent);
        boolean hasAccess = false;
        hasAccess = getPermissionService().isAuthorizedByTemplateName(principalId, 
					PermissionType.KS_ADMIN_SCREEN.getPermissionNamespace(), 
					PermissionType.KS_ADMIN_SCREEN.getPermissionTemplateName(), permDetails, 
					permDetails);
        System.out.println(principalId + " has access : " + hasAccess);
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
