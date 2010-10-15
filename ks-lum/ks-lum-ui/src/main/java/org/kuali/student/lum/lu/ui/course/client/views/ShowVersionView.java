package org.kuali.student.lum.lu.ui.course.client.views;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsController;
import org.kuali.student.lum.common.client.widgets.AppLocations;

public class ShowVersionView extends VerticalSectionView{
    
    private TabMenuController courseInfoTabs = new TabMenuController("");
    private VersionsController parent;

	public ShowVersionView(Enum<?> viewEnum, String name, String modelId, VersionsController controller) {
		super(viewEnum, name, modelId);
		this.setLayoutController(controller);
		ViewCourseConfigurer cfg = new ViewCourseConfigurer();
		cfg.setModelDefinition(controller.getDefinition());
		cfg.generateLayout(courseInfoTabs, modelId);
		this.addWidget(courseInfoTabs);
		parent = controller;
	}
	
	@Override
	public void beforeShow(Callback<Boolean> onReadyCallback) {
		courseInfoTabs.showDefaultView(onReadyCallback);
		ViewContext context = new ViewContext();
		context.setId(parent.getCurrentVersionId());
		context.setIdType(IdType.OBJECT_ID);
		String location = HistoryManager.appendContext(AppLocations.Locations.VIEW_COURSE.getLocation(), context);
		this.layout.setMessage("Note: There is a currently active version of this course. " +
				"<a href='#" + location +"'>View current version.</a>", false);
	}
	
	public void setName(String name){
		super.setName(name);
		this.setSectionTitle(name);
	}
	
	public void showWarningMessage(boolean show){
		this.layout.showMessage(show);
	}

}
