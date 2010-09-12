package org.kuali.student.lum.lu.ui.main.client.controllers;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbManager;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationController extends LayoutController{

	public static enum AppViews{HOME}
	
	public static FlowPanel contentPanel = new FlowPanel();
	public FlowPanel container = new FlowPanel();
	
	public ApplicationController(String controllerId, Widget header) {
		super(controllerId);
		this.init(header);
		this.setupViews();
		this.addHandlers();
		//add url parsing here
		this.showDefaultView(NO_OP_CALLBACK);
	}
	
	private void init(Widget header){
		container.add(header);
		container.add(contentPanel);
		this.initWidget(container);
	}
	
	private void addHandlers(){
		addApplicationEventHandler(LogoutEvent.TYPE, new LogoutHandler() {
			public void onLogout(LogoutEvent event) {
				Window.Location.assign("/j_spring_security_logout");
			}
		});
	}
	
	private void setupViews(){
		//if a configurer pattern makes sense here this is where it would be called
		HomeController home = new HomeController(this, "Home", 
				AppViews.HOME);
		this.addView(home);
		this.setDefaultView(AppViews.HOME);
	}

	@Override
	public void updateModel() {
		//Does nothing, no model needed here
	}

	@Override
	protected void hideView(View view) {
		contentPanel.clear();
	}

	@Override
	protected void renderView(View view) {
		contentPanel.add(view.asWidget());
	}
	
	public static ComplexPanel getApplicationViewContainer(){
		return contentPanel;
	}
	
	@Override
	public void showDefaultView(Callback<Boolean> onReadyCallback) {
		super.showView(AppViews.HOME);
		if(this.getCurrentView() instanceof Controller){
			((Controller) this.getCurrentView()).showDefaultView(onReadyCallback);
		}
		else{
			onReadyCallback.exec(true);
		}
	}
}
