package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class BaseFooterPanel extends Composite {
	
	HorizontalPanel root = new HorizontalPanel();
//	BusyIndicator busyIndicator = new BusyIndicator();
	//Label footer = new Label(I18N.i18nConstant.kualiStudentFoundation());
    Label footer = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("kualiStudentFoundation"));
	
	boolean loaded = false;
	
	public BaseFooterPanel(){
		//HTML footer=new HTML("Kuali Student Foundation");
		initWidget(root);
		
	}
	public void onLoad() {
		if (!loaded) {
			loaded = true;
			super.addStyleName("KS-Footer");
//			busyIndicator.setWidth("225px");
//			busyIndicator.setHeight("30px");
//			
			footer.setWidth("100%");
			footer.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
//			root.add(busyIndicator);
			root.add(footer);
			
			root.setWidth("100%");
		}
	}
	
}
