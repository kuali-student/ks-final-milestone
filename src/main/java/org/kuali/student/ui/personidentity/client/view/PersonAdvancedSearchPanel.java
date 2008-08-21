package org.kuali.student.ui.personidentity.client.view;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.widgets.BusyWidgetShade;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PersonAdvancedSearchPanel extends HidablePanel{
	boolean loaded = false;
	final TextBox 	surName;
	final TextBox 	givenName;
	
	public PersonAdvancedSearchPanel() {
		


		setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("advancedPersonSearch"));
		
		
		
		FlexTable advanced = new FlexTable();
		Label		lGivenName		= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("firstName"));
		
		lGivenName.addStyleName("KS-Label");
		givenName= new TextBox();
		givenName.addStyleName("KS-TextBox");
	
		Label		lSurName		= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("lastName"));
		lSurName.addStyleName("KS-Label");
		surName= new TextBox();
		surName.addStyleName("KS-TextBox");

		advanced.setWidget(0, 0, lGivenName);
		advanced.setWidget(1, 0, givenName);

		advanced.setWidget(2, 0, lSurName);
		advanced.setWidget(3, 0, surName);
	
		VerticalPanel advancedSearchPanel = new VerticalPanel();
		
		
		final Button button = new Button(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("search"));
		
		
		button.setStyleName("KS-Button");
		//button.addStyleName("KS-Advanced-Person-Search-Button");
		
		button.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
			    BusyWidgetShade.shade(button);
				GwtPersonCriteria pCriteria = new GwtPersonCriteria();
				if(!"".equals(givenName.getText()))
					pCriteria.setFirstName(givenName.getText() + "%");
				if(!"".equals(surName.getText()))
					pCriteria.setLastName(surName.getText() + "%");
					
				PersonIdentityController.serachForPeople(pCriteria, new AsyncCallback(){

                    
                    public void onFailure(Throwable caught) {
                        BusyWidgetShade.unshade(button);
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(Object result) {
                        String filler = "";
                        if(!surName.getText().equals("") && !givenName.getText().equals(""))
                        {
                        	filler = ", ";
                        }
                    	PersonIdentityController.updateSearchResults(surName.getText() + filler + givenName.getText() , (List<GwtPersonInfo>)result);
                        BusyWidgetShade.unshade(button);
                    }} );
			}
		});
		
		advancedSearchPanel.add(advanced);
		advancedSearchPanel.add(button);
		
		setCenterWidget(advancedSearchPanel);
	      //this.setWidth("100%");
        this.setWidth("100px");
	}
	
	protected void onLoad() 
	{
		super.onLoad();
		if (!loaded) {
			loaded = true;
			final Controller c = MVC.findParentController(this);
			if (c != null) {
				c.getEventDispatcher().addListener(SearchWidget.PersonSearchEvent.class, new MVCEventListener() {
					public void onEvent(Class<? extends MVCEvent> event, Object data) {
						givenName.setText("");
						surName.setText("");
					}
				});
			}
		}
	}
}

