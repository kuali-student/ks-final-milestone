/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.view.AdminStudentTab.ShowAdminStudentTab;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Garey
 *
 */
public class PersonSearchResultPanel extends Composite {
    final VerticalPanel panel = new VerticalPanel();
    final PersonSearchResultTable table = new PersonSearchResultTable();
    final PersonSearchResultPanel me = this;
    public static abstract class SelectPersonEvent extends MVCEvent {}
    public static final SelectPersonEvent PERSON_SELECTED = GWT.create(SelectPersonEvent.class);
    ModelBinding<GwtPersonInfo> binding = null;
    boolean loaded = false;
    Label	lSearchText = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("searchInstructions"));
    
  /*  
	private PropertyChangeListener listener  
		= new PropertyChangeListenerProxy(
	            "searchResult",
	            new PropertyChangeListener() {
	                public void propertyChange(
	                    PropertyChangeEvent propertyChangeEvent) {
	                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
	                	updateView( ModelState.getInstance().getSearchResult());
	                }
	            });
	
	*/
	
	/**
	 * 
	 */
	public PersonSearchResultPanel() {
		lSearchText.addStyleName("KS-Label");
		lSearchText.addStyleName("KS-Search-Message");
		
		//panel.add(lSearchText);
		panel.add(table);
		table.setVisible(false);
		
		super.initWidget(panel);
		//init();
		
	}
	public void onLoad() {
	    if (!loaded) {
	        loaded = true;
	        Model<GwtPersonInfo> model = (Model<GwtPersonInfo>) MVC.findParentController(this).getModel(GwtPersonInfo.class);
	        binding = new ModelBinding<GwtPersonInfo>(model, table);
	        
	        table.addSelectionListener(new ModelTableSelectionListener<GwtPersonInfo>() {
                public void onSelect(GwtPersonInfo modelObject) {
                    ModelState.getInstance().setCurrPerson(modelObject);
                    PersonIdentityController.viewPersonDetailsScreen();
                    Controller c = MVC.findParentController(me);
                    c.getEventDispatcher().fireEvent(PERSON_SELECTED, modelObject);
                    //Show the registration panel, can someone find a more elegant way to do this?
                    //((AdminStudentTab)(panel.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent())).addRegistrationTab();
                }
	        });
	    }
	}
	
	public PersonSearchResultPanel(List<GwtPersonInfo> pList) {
		super();
		init();
		//this.updateView(pList);
	}
	
	protected void init(){
		//ModelState.getInstance().addPropertyChangeListener(listener);
	}
	
	public void setSearchLabel(String text)
	{
		lSearchText.setText(text);
	}
	
	public void setTableVisibility(boolean visible)
	{
		table.setVisible(visible);
	}
	/*
	public void updateView(List<GwtPersonInfo> pList){
		this.clear();				
		for(GwtPersonInfo pInfo: pList){
			PersonSearchDetailWidget pDetails = new PersonSearchDetailWidget(pInfo);
			this.add(new SearchResultElementWidget(pDetails, false));
		}
	}
*/
}
