/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Garey
 *
 */
public class AdminEditPanel extends VerticalPanel {
	public static final String VIEW_NAME = "org.kuali.student.personIdentity";
	public final AbsolutePanel innerPanel = new AbsolutePanel();
	
	public final CrudBar	cBar	= new CrudBar();
	public final Button		bSave	= new Button();
	
	HorizontalPanel			hPanel	= new HorizontalPanel();
	
	public AdminEditPanel(){
		//bSave.setText("Save");
		//bSave.setText( I18N.i18nConstant.save());
		bSave.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("save"));
		//bSave.addStyleName("KS-Save-Button");
		bSave.addStyleName("KS-Button");
		bSave.addStyleName("KS-Button-Padding");
		
		hPanel.setWidth("100%");
		hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		hPanel.add(bSave);
		
		this.setWidth("100%");
		
		this.add(cBar);
		this.add(innerPanel);
		this.add(hPanel);
	}
}
