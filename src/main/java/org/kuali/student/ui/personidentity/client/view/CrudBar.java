/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Garey
 *
 */
public class CrudBar extends HorizontalPanel {

	protected		FlowPanel	fPanel = new FlowPanel();
	
	public final	Button	bEdit = new Button();
	public final	Button	bDelete = new Button();
	public final	Button	bCancel = new Button();
	
	/**
	 * 
	 */
	public CrudBar() {
		
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		this.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		this.setWidth("100%");
		
		//bEdit.setText("Edit");
		//bEdit.setText(I18N.i18nConstant.edit());
		bEdit.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("edit"));
		
		bEdit.addStyleName("KS-Button");
		bEdit.addStyleName("KS-Button-Padding");
		//bDelete.setText("Delete");
		//bDelete.setText(I18N.i18nConstant.delete());
		bDelete.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("delete"));
		
		bDelete.addStyleName("KS-Button");
		bDelete.addStyleName("KS-Button-Padding");
		//bCancel.setText("Cancel");
		//bCancel.setText(I18N.i18nConstant.cancel());
		bCancel.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("cancel"));
		
		bCancel.addStyleName("KS-Button");
		bCancel.addStyleName("KS-Button-Padding");
		
		fPanel.add(bCancel);
		fPanel.add(bEdit);		
		fPanel.add(bDelete);
		
		
		this.add(fPanel);
		this.addStyleName("KS-CRUD-Panel");
	}

}
