/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author Garey
 *
 */
public class PersonSearchPanel extends FlowPanel {

	//PersonSearchResultPanel srPanel = new PersonSearchResultPanel();
	//PersonAdvancedSearchPanel	advPanel = new PersonAdvancedSearchPanel(); 
	PersonAdvancedSearchPanel	advPanel = new PersonAdvancedSearchPanel();
	PersonSearchResultPanel srPanel = new PersonSearchResultPanel();
	
	/**
	 * 
	 */
	public PersonSearchPanel() {		
		/*
		 * I want to use this grid result once the incubator code becomes more stable
		 */
		//GridResult srGrid = new GridResult();
		//advPanel.setWidth("60%");
		//srPanel.setWidth("40%");
		
		this.add(advPanel);
		this.add(srPanel);
	}
	
	public PersonSearchResultPanel getSearchResultPanel()
	{
		return srPanel;
	}
	
	public void onLoad() {
	    //super.setCellWidth(advPanel, "160px");
	    this.addStyleName("PersonSearchPanel");
	    advPanel.addStyleName("PersonAdvancedSearchPanel");
	    srPanel.addStyleName("PersonSearchResultPanel");
	}
}
