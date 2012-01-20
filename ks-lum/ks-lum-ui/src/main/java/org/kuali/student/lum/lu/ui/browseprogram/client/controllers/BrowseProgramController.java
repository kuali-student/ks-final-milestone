package org.kuali.student.lum.lu.ui.browseprogram.client.controllers;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.ui.browseprogram.client.views.BrowseProgramView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class BrowseProgramController extends BasicLayout implements RequiresAuthorization{

	public enum BrowseProgramViews {
		MAIN
	}
	
	public BrowseProgramController(String controllerId) {
		
		super(controllerId);
		addView(new BrowseProgramView(this, "Browse Majors and Specializations", BrowseProgramViews.MAIN));
		setDefaultView(BrowseProgramViews.MAIN);
		//super.permissionType=
	}
	
	@Override
	public boolean isAuthorizationRequired() {
		return true;
	}

	@Override
	public void setAuthorizationRequired(boolean required) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void checkAuthorization(final AuthorizationCallback authCallback) {
		Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissions.USE_BROWSE_PROGRAM_SCREEN, new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {

				final boolean isAuthorized = result;
	        
				if(isAuthorized){
					authCallback.isAuthorized();
				}
				else
					authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissions.USE_BROWSE_PROGRAM_SCREEN);
			}	
		});
	}
}