package org.kuali.student.common.ui.client.widgets.headers;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class KSHeader extends Composite {

	private static KSHeaderUiBinder uiBinder = GWT
			.create(KSHeaderUiBinder.class);

	interface KSHeaderUiBinder extends UiBinder<Widget, KSHeader> {
	}

	@UiField
    Label hiLabel;

    @UiField
    Label userNameLabel;
    
    @UiField
    Label searchLabel;
    
	public KSHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		hiLabel.setText("Hi,");
     //   searchButton.setText("Search");
       // logoutButton.setText("Logout");
	}
    
    //TODO University widget
	public void setUniversityWidget(Widget w){
	    
	}
	public void setApplicationTitle(String title){
	  // applicationTitle.setText(title);
	}
	public void setNavigationData(List<KSMenuItemData> naviData){
	    
	}
	public void setSearchTitle(String s){
	   searchLabel.setText(s);
	}
	public void setUserName(String u){
	    userNameLabel.setText(u);
	}
	public void addButton(KSButton ksButton){
	  //ksButtonPanel.add(ksButton);
	}
	public void setBreadcrum(List<String> items){
	    //naviPanel.add();
	}
	//TODO Breadcrum
}
