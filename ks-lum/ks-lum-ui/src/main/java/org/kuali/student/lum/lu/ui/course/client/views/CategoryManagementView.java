package org.kuali.student.lum.lu.ui.course.client.views;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.lum.common.client.lo.CategoryManagement;

import com.google.gwt.user.client.ui.FlowPanel;

public class CategoryManagementView extends ViewComposite{

	private FlowPanel layout = new FlowPanel();
	
	public CategoryManagementView(Controller controller, String name,
			Enum<?> viewType) {
		super(controller, name, viewType);
		this.initWidget(layout);
		layout.addStyleName("standard-content-padding");
		layout.add(SectionTitle.generateH1Title(name));
		
		layout.add(new CategoryManagement(true, false));
	}
	
}
