package org.kuali.student.common.ui.client.widgets.headers;

import org.kuali.student.common.ui.client.widgets.StylishDropDown;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class KSHeader extends Composite {

	private static KSHeaderUiBinder uiBinder = GWT
			.create(KSHeaderUiBinder.class);

	interface KSHeaderUiBinder extends UiBinder<Widget, KSHeader> {
	}

	@UiField
	FlowPanel hiPanel;

    @UiField
    FlowPanel userNamePanel;
    
    @UiField
    FlowPanel logoutPanel;
    
    @UiField
    FlowPanel leftPartPanel;

    @UiField
    Label applicationTitleLabel;
    
    @UiField
    FlowPanel bottomContainer;

    
    Label userNameLabel =  new Label("");
    Label hiLabel = new Label("Hi,");
	public KSHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		hiPanel.add(hiLabel);
		userNamePanel.add(userNameLabel);
		applicationTitleLabel.setText("Kuali Student");
		//logoutPanel.add(new Anchor("LL"));
		//leftPartPanel.add(new StylishDropDown("Navigation"));
	}
	public void addLogout(Widget w){
	    logoutPanel.add(w);
	}
	public void addNavigation(StylishDropDown dropDown){
	    leftPartPanel.add(dropDown);
	}
	
	public void setHiLabelText(String hi){
	    hiLabel.setText(hi);
	}
    public void setUserName(String name){
        userNameLabel.setText(name);
    }

    public void addBottomContainerWidget(Widget w){
    	bottomContainer.add(w);
    }
    
    public Panel getBottomContainer(){
    	return bottomContainer;
    }
}
