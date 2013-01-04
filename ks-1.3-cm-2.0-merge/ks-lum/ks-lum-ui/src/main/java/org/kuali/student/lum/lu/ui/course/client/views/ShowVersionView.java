package org.kuali.student.lum.lu.ui.course.client.views;

import java.util.List;

import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsController;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsReqController;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

public class ShowVersionView extends VerticalSectionView{
    
//    private TabMenuController courseInfoTabs = new TabMenuController("");
	private VersionsReqController courseInfoTabs;
    private VersionsController parent;

	public ShowVersionView(Enum<?> viewEnum, String name, String modelId, VersionsController controller, List<StatementTypeInfo> stmtTypes) {
		super(viewEnum, name, modelId);
		this.setLayoutController(controller);
		ViewCourseConfigurer cfg = new ViewCourseConfigurer();
		cfg.setModelDefinition(controller.getDefinition());
        cfg.setStatementTypes(stmtTypes);
        courseInfoTabs = new VersionsReqController(controller);
		cfg.generateLayout(courseInfoTabs, modelId);
		this.addWidget(courseInfoTabs);
		parent = controller;
		final ViewContext context = new ViewContext();
		context.setId(parent.getCurrentVersionId());
		context.setIdType(IdType.OBJECT_ID);		
		this.layout.setMessage("Note: This is not the current version of this course. ", false);
		Anchor link = new Anchor("View current version.");
		link.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				getController().setViewContext(context);
				getController().showView(VersionsController.Views.VERSION_VIEW);
			}
		});
		
		this.layout.getMessageWarnContainer().addWarnWidget(link);
	}
	
	@Override
	public void beforeShow(Callback<Boolean> onReadyCallback) {
		courseInfoTabs.showDefaultView(onReadyCallback);
	}
	
	public void setName(String name){
		super.setName(name);
		this.setSectionTitle(name);
	}
	
	public void showWarningMessage(boolean show){
		this.layout.showMessage(show);
	}
}
