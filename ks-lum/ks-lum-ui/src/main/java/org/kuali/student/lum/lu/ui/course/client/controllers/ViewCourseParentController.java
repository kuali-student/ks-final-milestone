package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbManager;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;

public class ViewCourseParentController extends BasicLayout{
	
	public static enum Views{VIEW, VERSIONS}
	
	private ViewCourseController view;
	private VersionsController versions;

	public ViewCourseParentController() {
		super(ViewCourseParentController.class.toString());
        this.setDefaultView(Views.VIEW);
        setupDefaultView();
    }

    private void setupDefaultView() {
        view = new ViewCourseController(Views.VIEW);
        view.setParentController(this);
        versions = new VersionsController(Views.VERSIONS);
        view.setParentController(this);
        this.addView(view);
        this.addView(versions);
    }
	
	@Override
	public void setViewContext(ViewContext viewContext) {
		//super.setViewContext(viewContext);
		view.setViewContext(viewContext);
	}
	
	@Override
    public <V extends Enum<?>> void showView(final V viewType, final Callback<Boolean> onReadyCallback) {
		if(viewType == Views.VERSIONS){
			view.setName("");
			this.setName(view.getCourseTitle());
			versions.setVersionIndId(view.getVersionIndId());
			versions.setCurrentVersionId(view.getCurrentId());
			versions.setCurrentTitle(view.getCourseTitle());
		}
		else if(viewType == Views.VIEW){
			view.setName(view.getCourseTitle());
			this.setName("");
		}
		super.showView(viewType, onReadyCallback);
    }

    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return Views.class;
    }

    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return Views.valueOf(enumValue);
    }
    
    @Override
    public void beforeShow(Callback<Boolean> onReadyCallback) {
    	if(this.getName() != null && !this.getName().equals("")){
    		this.setName("");
    	}
    	super.beforeShow(onReadyCallback);
    }
    
}
