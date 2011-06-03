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

package org.kuali.student.core.authorization.ui.server.gwt;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.util.AttributeSet;
import org.kuali.rice.kim.api.services.IdentityManagementService;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.authorization.ui.client.service.AuthorizationRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AuthorizationRpcGwtServlet extends RemoteServiceServlet implements AuthorizationRpcService{

	private static final long serialVersionUID = 8568346881191827247L;
	private IdentityManagementService permissionService;

	@Override
	public Boolean isAuthorizedForPermission(String namespace, String permissionTemplateName) {
		return isAuthorizedForPermissionWithDetailsAndQualifications(namespace, permissionTemplateName, null, null);
	}

	public Boolean isAuthorizedForPermissionWithQualifications(String namespace, String permissionTemplateName, Map<String,String> roleQualifications) {
		return isAuthorizedForPermissionWithDetailsAndQualifications(namespace, permissionTemplateName, roleQualifications, null);
	}

	public Boolean isAuthorizedForPermissionWithDetailsAndQualifications(String namespace, String permissionTemplateName, Map<String,String> roleQualifications, Map<String,String> permissionDetails) {
		String currentUser = getCurrentUser();
		if (StringUtils.isBlank(currentUser)) {
			throw new RuntimeException("Unable to find current user or backdoor user.");
		}
		AttributeSet roleQuals = null;
		if (roleQualifications != null) {
			roleQuals = new AttributeSet(roleQualifications);
		}
		AttributeSet permDetails = null;
		if (permissionDetails != null) {
			permDetails = new AttributeSet(permissionDetails);
		}
		return  Boolean.valueOf(permissionService.isAuthorizedByTemplateName(currentUser, namespace, permissionTemplateName, permDetails, roleQuals));
	}

	protected String getCurrentUser() {
		String username = SecurityUtils.getCurrentUserId();
		//backdoorId is only for convenience
		if(username==null&&this.getThreadLocalRequest().getSession().getAttribute("backdoorId")!=null){
			username=(String)this.getThreadLocalRequest().getSession().getAttribute("backdoorId");
        }
		return username;
	}

	public void setPermissionService(IdentityManagementService permissionService) {
		this.permissionService = permissionService;
	}

}
