/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 */
public class PersonDetailsWidget extends Composite {

	VerticalPanel					rootPanel	= new VerticalPanel();
	AdminEditPanel					aPanel		= new AdminEditPanel();		
	final PersonInfoWidget			pWidget		= new PersonInfoWidget(ModelState.getInstance().getCurrPerson());
	protected boolean				initialized = false;
	
	//Label							noneSelected = new Label("No User Selected");
	//Label							noneSelected = new Label(I18N.i18nConstant.noUserSelected());
	Label							noneSelected = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("noUserSelected"));
	
	private PropertyChangeListener[] listeners = new PropertyChangeListener[5];	
	
	ClickListener			clickEdit = 
		new ClickListener(){
		public void onClick(Widget sender){
			setEditMode(true);
		}
	};
	
	ClickListener			clickSave = 
		new ClickListener(){
		public void onClick(Widget sender){
			PersonIdentityController.updatePerson(pWidget.getPersonObj());
			setEditMode(false);
		}
	};
	
	ClickListener          clickDelete = 
        new ClickListener(){
        public void onClick(Widget sender){
            PersonIdentityController.deletePerson(pWidget.getPersonObj().getPersonId());
            pWidget.setEditable(false);         
            Info.displayInfo(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("info"),ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("personDeleted"));
        }
    };
	
	ClickListener			clickCancel = 
		new ClickListener(){
		public void onClick(Widget sender){			
			
			// replace the altered state with the initial state.
			pWidget.updateView(ModelState.getInstance().getCurrPerson());			
			setEditMode(false);
		}
	};
	
	public void setEditMode(boolean edit)
	{
		if(edit)
		{
			pWidget.setEditable(true);
			aPanel.cBar.bCancel.setVisible(true);
			aPanel.cBar.bEdit.setVisible(false);
			aPanel.bSave.setVisible(true);
		}
		else
		{
			pWidget.setEditable(false);
			aPanel.cBar.bCancel.setVisible(false);
			aPanel.bSave.setVisible(false);
			aPanel.cBar.bEdit.setVisible(true);
		}
	}
	
	/**
	 * 
	 */
	public PersonDetailsWidget() {
		
		
				
		aPanel.innerPanel.add(pWidget);
		this.setEditMode(false);
		
		aPanel.cBar.bEdit.addClickListener(clickEdit);
		aPanel.cBar.bCancel.addClickListener(clickCancel);		
		aPanel.cBar.bDelete.addClickListener(clickDelete);
		aPanel.bSave.addClickListener(clickSave);
	
		noneSelected.setVisible(false);
		
		listeners[0] = new PropertyChangeListenerProxy(
                "currPerson",
                new PropertyChangeListener() {
                    public void propertyChange(
                        PropertyChangeEvent propertyChangeEvent) {
                    	
                    	GwtPersonInfo pInfo = (GwtPersonInfo)propertyChangeEvent.getNewValue();
                    	
                        pWidget.updateView( pInfo);
                        
                        if(initialized){
	                        if(pInfo != null){
	                        	aPanel.setVisible(true);
	                			noneSelected.setVisible(false);
	                        }else {
	                        	aPanel.setVisible(false);
	                			noneSelected.setVisible(true);
	                        }
                        }
                        
                    }
                });
		ModelState.getInstance().addPropertyChangeListener(listeners[0]);
		
		rootPanel.setWidth("100%");
		this.noneSelected.setHorizontalAlignment(Label.ALIGN_CENTER);
		
		rootPanel.add(aPanel);
		rootPanel.add(noneSelected);
		
		initWidget(rootPanel);
		initialized = true;
		
		if(ModelState.getInstance().getCurrPerson() == null){
			aPanel.setVisible(false);
			noneSelected.setVisible(true);
		}
	}
	
	

}
