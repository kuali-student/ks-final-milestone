/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.SimpleAttribute;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Garey
 *
 */
public class CourseDetails extends Composite {

    VerticalPanel panel = new VerticalPanel();
    FlexTable table = new FlexTable();
    
   
    
	Label	lTitle= new Label();
	Label	lMeetingDays= new Label();
	Label	lMeetingTimes= new Label();
	Label	lDescription= new Label();
	Label 	lLocation= new Label();
	Label	lInstructor= new Label();
	Label	lNotifyMessage= new Label(" ");
	
	public static final String BASKET_ADD = "basketAdd";
	public static final String IN_BASKET = "inBasket";
	public static final String REGISTER_NOTE = "registerSuccess";
	public static final String REGISTERED = "alreadyRegistered";

	
    ViewMetaData metadata;
    Messages messages;
    boolean loaded = false;

    public void onLoad() {
        if (!loaded) {
            loaded = true;
            metadata = ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME);
            messages = metadata.getMessages();
    
            panel.add(table);
            panel.add(lNotifyMessage);
            
            lNotifyMessage.addStyleName("info-content");
            lNotifyMessage.setVisible(false);
            
        }
    }
	
	
	public CourseDetails() {
		super.initWidget(panel);
	}
	
	
	private void populateBase(boolean popFull) {
	    while (table.getRowCount() > 0) {
	        table.removeRow(0);
	    }
	    table.setStyleName("KS-ModelTable");
	    table.addStyleName("CourseDetailTable");
	    
	    table.setText(0, 0, messages.get("title"));
        table.setWidget(0, 1, lTitle);
        if(popFull)
        {
        	table.setText(1, 0, messages.get("meetingDays"));
        	table.setWidget(1, 1, lMeetingDays);
        
        	table.setText(2, 0, messages.get("meetingTimes"));
        	table.setWidget(2, 1, lMeetingTimes);
        	
        	 table.setText(3, 0, messages.get("description"));
             table.setWidget(3, 1, lDescription);
             
             table.setText(4, 0, messages.get("location"));
             table.setWidget(4, 1, lLocation);
             
             table.setText(5, 0, messages.get("instructor"));
             table.setWidget(5, 1, lInstructor);
        }
        else
        {
        	 table.setText(1, 0, messages.get("description"));
             table.setWidget(1, 1, lDescription);
        }
        
	}
	
	private void populateAttributes(Map<String, String> attributes) {
	    for (String key : attributes.keySet()) {
	        String value = attributes.get(key);
	        int row = table.getRowCount();
	        table.setText(row, 0, key);
	        table.setText(row, 1, value);
	    }
	    applyTableStyles();
	}
	
	private void applyTableStyles() {
	    for (int row=0; row<table.getRowCount(); row++) {
	        table.getCellFormatter().setStyleName(row, 0, "KS-ModelTable-Column-Header");
	        table.getCellFormatter().setStyleName(row, 1, "KS-ModelTable-Row");
	    }
	}
	public void populate(GwtCluInfo in){				
		if(in != null){
		    populateBase(false);
			this.lTitle.setText(in.getCluShortName());
			
		//	this.lMeetingDays.setText(in.getEffectiveStartCycle() + " - " + in.getEffectiveEndCycle());
			this.lDescription.setText(in.getDescription());
			populateAttributes(in.getAttributes());
			
		}
	}
	public void populate(GwtCluDisplay in){				
		if(in != null){
			this.lTitle.setText(in.getCluShortName());
		//	this.lMeetingDays.setText(in.getEffectiveStartCycle() + " - " + in.getEffectiveEndCycle());
			this.lDescription.setText(in.getCluShortName());
		}
	}
	public void populate(GwtLuiInfo in){				
		if(in != null){
		    populateBase(true);
			this.lTitle.setText(in.getCluInfo().getCluShortName());
		//	this.lMeetingDays.setText(in.getEffectiveStartCycle() + " - " + in.getEffectiveEndCycle());
			this.lDescription.setText(in.getCluInfo().getDescription());
	        populateAttributes(in.getCluInfo().getAttributes());

		}
	}
	
	/** Shows the message type specified, if show is true.
	 * @param show
	 * @param messageType
	 */
	public void showNotification(boolean show, String messageType)
	{
		if(messageType != null)
		{
			this.lNotifyMessage.setText(messages.get(messageType));
		}
		this.lNotifyMessage.setVisible(show);
	}

	
	
	
	
}
