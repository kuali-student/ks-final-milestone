package org.kuali.student.lum.lu.ui.main.client.controllers;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.controllers.CurriculumHomeController;
import org.kuali.student.lum.lu.ui.main.client.views.HomeView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class HomeController extends LayoutController{
	
	private final CurriculumHomeController curriculumHomeView;
	private final HomeView defaultView = new HomeView(this, HomeViews.DEFAULT);
	private SpanPanel panel = new SpanPanel();
	
	public enum HomeViews{DEFAULT, CURRICULUM_HOME}

	public HomeController(Controller controller, String name, Enum<?> viewType) {
		super(HomeController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
		this.initWidget(panel);
		
		curriculumHomeView = new CurriculumHomeController(this, "Curriculum Management", HomeViews.CURRICULUM_HOME);
		init();
		setupViews();
	}
	
	private void init(){
		defaultView.addWidget(new KSButton("Curriculum Management", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				HomeController.this.showView(HomeViews.CURRICULUM_HOME);
				curriculumHomeView.showDefaultView(NO_OP_CALLBACK);
			}
		}));
	}
	
	private void setupViews(){
		this.addView(defaultView);
		this.addView(curriculumHomeView);
	}
	
	@Override
	public void showDefaultView(Callback<Boolean> onReadyCallback) {
		HistoryManager.setLogNavigationHistory(false);
		super.showDefaultView(onReadyCallback);
		HistoryManager.setLogNavigationHistory(true);
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

}
