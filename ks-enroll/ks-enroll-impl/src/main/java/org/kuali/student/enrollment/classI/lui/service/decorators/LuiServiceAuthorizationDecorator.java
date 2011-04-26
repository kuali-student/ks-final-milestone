package org.kuali.student.enrollment.classI.lui.service.decorators;

import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.enrollment.classI.lui.service.LuiServiceDecorator;
import org.kuali.student.r2.common.infc.HoldsPermissionService;

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
