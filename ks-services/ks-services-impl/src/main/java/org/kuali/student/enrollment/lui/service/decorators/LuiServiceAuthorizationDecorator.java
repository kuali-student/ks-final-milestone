package org.kuali.student.enrollment.lui.service.decorators;

import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.infc.HoldsPermissionService;

public class LuiServiceAuthorizationDecorator extends LuiServiceDecorator implements HoldsPermissionService {
	
	private PermissionService permissionService;
		
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	
}
