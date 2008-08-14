package org.kuali.student.ui.personidentity.client.view;

import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonNameInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminStudentTabWrapper extends Composite {
    final VerticalPanel panel = new VerticalPanel();
    final HorizontalPanel currentlySelectedPanel = new HorizontalPanel();
	final ViewMetaData viewMetaData = ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME);
	final Messages messages = viewMetaData.getMessages();
    final Label currentlySelectedLabel = new Label(messages.get("noUserSelected"));
    final AdminStudentTab tabs = new AdminStudentTab();
    boolean loaded = false;
    
    public AdminStudentTabWrapper() {
        panel.add(currentlySelectedPanel);
        currentlySelectedLabel.addStyleName("KS-Label");
        currentlySelectedLabel.addStyleName("KS-Current-Person");
        currentlySelectedPanel.add(currentlySelectedLabel);
        panel.add(tabs);
        super.initWidget(panel);
    }
    
    public AdminStudentTab getTabs() {
        return tabs;
    }
    
    protected void onLoad()
    {
    	super.onLoad();
    	if (!loaded) 
    	{
			loaded = true;
	        super.setStyleName("AdminStudentTabWrapper");
	        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
			this.setWidth("100%");
			
			final Controller c = MVC.findParentController(this);
			if (c != null) {
			    MVCEventListener listener = new MVCEventListener() {
                    public void onEvent(Class<? extends MVCEvent> event, Object data) {
                        if(data != null) {
                            List<GwtPersonNameInfo> nameInfoList = ((GwtPersonInfo)data).getName();
                        
                            GwtPersonNameInfo defaultInfo = nameInfoList.get(0);
                            String name = messages.get("currentSelection") + ": " + defaultInfo.getSurname() + ", " + defaultInfo.getGivenName() + " " + defaultInfo.getMiddleName();
                            for(GwtPersonNameInfo info: nameInfoList) {
                                if(info.getPreferredName()) {
                                    name = info.getSurname() + ", " + info.getGivenName() + " " + info.getMiddleName();
                                }
                            }
                            currentlySelectedLabel.setText(name);
                        } else {
                            currentlySelectedLabel.setText(messages.get("noUserSelected"));
                        }
                    }
                };
                
				c.getEventDispatcher().addListener(PersonSearchResultPanel.SelectPersonEvent.class, listener);
				c.getEventDispatcher().addListener(PersonIdentityController.PersonUpdatedEvent.class, listener);
				
			}
		}
    }
}
