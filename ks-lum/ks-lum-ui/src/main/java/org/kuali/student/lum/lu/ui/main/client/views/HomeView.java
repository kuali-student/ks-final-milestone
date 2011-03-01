package org.kuali.student.lum.lu.ui.main.client.views;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.controllers.CurriculumHomeController.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.widgets.ActionList;

import com.google.gwt.user.client.ui.Widget;

/**
 * The default view for the HomeController contains the rice action list in an iFrame.
 * 
 * @author Kuali Student Team
 *
 */
public class HomeView extends ViewComposite{
	
	private SpanPanel container = new SpanPanel();

	private ActionList actionListView = new ActionList();
	
	public HomeView(Controller controller, Enum<?> viewType) {
		super(controller, "", viewType);
		container.add(actionListView);

		this.initWidget(container);

	}
	
	public void addWidget(Widget w){
		container.insert(w, container.getWidgetIndex(actionListView));
	}
	
}
