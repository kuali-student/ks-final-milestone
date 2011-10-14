package org.kuali.student.enrollment.class2.acal.service.decorators;

import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.enrollment.acal.service.AcademicCalendarServiceDecorator;
import org.kuali.student.r2.common.infc.HoldsPermissionService;

public class AcademicCalendarServiceAuthorizationDecorator extends AcademicCalendarServiceDecorator implements HoldsPermissionService{
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
