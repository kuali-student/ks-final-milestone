package edu.umd.dan.testricepersonlookup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestRicePersonLookup implements EntryPoint  {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		VerticalPanel navPanel = new VerticalPanel();
		Hyperlink getPersonLookupLink = new Hyperlink("PersonLookup","PersonLookup");
		final Label selectedUserLabel = new Label("<Selected User Id>");

		
		getPersonLookupLink.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				RootPanel.get("iFrameContainer").clear();
				ExtFrame iframe = new ExtFrame("http://localhost:8081/ks-rice-dev/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.bo.impl.PersonImpl&docFormKey=88888888&returnLocation="+GWT.getHostPageBaseURL()+"sendResponse.html&hideReturnLink=false");
				iframe.setSelectedUserLabel(selectedUserLabel);
				RootPanel.get("iFrameContainer").add(iframe);				
			}
		});
		navPanel.add(getPersonLookupLink);
		navPanel.add(selectedUserLabel);
		RootPanel.get("leftNavContainer").add(navPanel);

	}
}
