package org.kuali.student.lum.lu.ui.course.client.views;

import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.lum.common.client.lo.CategoryManagement;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;

import com.google.gwt.user.client.ui.FlowPanel;

public class CategoryManagementView extends ViewComposite implements RequiresAuthorization{

	private FlowPanel layout = new FlowPanel();
	
	public CategoryManagementView(Controller controller, String name,
			Enum<?> viewType) {
		super(controller, name, viewType);
		this.initWidget(layout);
		layout.addStyleName("standard-content-padding");
		layout.add(SectionTitle.generateH1Title(name));
		
		layout.add(new CategoryManagement(true, false));
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
		Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissions.USE_LO_CATEGORY_SCREEN, new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {

				final boolean isAuthorized = result;
	        
				if(isAuthorized){
					authCallback.isAuthorized();
				}
				else
					authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissions.USE_LO_CATEGORY_SCREEN);
			}	
		});
	}
}
