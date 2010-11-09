package org.kuali.student.lum.lu.ui.course.client.views;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.lum.common.client.lo.CategoryManagement;

import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.user.client.ui.FlowPanel;

public class CategoryManagementView extends ViewComposite{

	private FlowPanel layout = new FlowPanel();
	private KSDocumentHeader header = new KSDocumentHeader();
	
	public CategoryManagementView(Controller controller, String name,
			Enum<?> viewType) {
		super(controller, name, viewType);
		this.initWidget(layout);
		layout.add(header);
		header.addStyleName("Lum-DocumentHeader-Spacing");
		header.setTitle(name);
		layout.add(new CategoryManagement(true,SelectionPolicy.MULTI_ROW));
	}
	
}
