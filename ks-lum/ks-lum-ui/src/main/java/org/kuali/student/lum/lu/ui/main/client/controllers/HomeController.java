package org.kuali.student.lum.lu.ui.main.client.controllers;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.controllers.CurriculumHomeController;
import org.kuali.student.lum.lu.ui.main.client.configuration.AcknowledgeView;
import org.kuali.student.lum.lu.ui.main.client.views.HomeView;

public class HomeController extends LayoutController{
	
	private final CurriculumHomeController curriculumHomeView;
	private final HomeView defaultView = new HomeView(this, HomeViews.DEFAULT);
	private final AcknowledgeView ackView = new AcknowledgeView(this, HomeViews.ACKNOWLEDGEMENTS);
	private SpanPanel panel = new SpanPanel();
	
	public enum HomeViews{DEFAULT, CURRICULUM_HOME, ACKNOWLEDGEMENTS}

	public HomeController(Controller controller, String name, Enum<?> viewType) {
		super(HomeController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
		this.initWidget(panel);
		
		curriculumHomeView = new CurriculumHomeController(this, "Curriculum Management", HomeViews.CURRICULUM_HOME);
		setupViews();
	}
	
	private void setupViews(){
		this.addView(defaultView);
		this.addView(curriculumHomeView);
		this.addView(ackView);
	}

	@Override
	public void updateModel() {
		//Does nothing, no model needed here
	}

	@Override
	protected void hideView(View view) {
		ApplicationController.getApplicationViewContainer().clear();
	}

	@Override
	protected void renderView(View view) {
		ApplicationController.getApplicationViewContainer().add(view.asWidget());
	}
	
    public <V extends Enum<?>> void showView(V viewType, Callback<Boolean> onReadyCallback) {
    	if(viewType == HomeViews.DEFAULT){
    		WindowTitleUtils.setContextTitle(name);
    	}
    	super.showView(viewType, onReadyCallback);
    };
}
