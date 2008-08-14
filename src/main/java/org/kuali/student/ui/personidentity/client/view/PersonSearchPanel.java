/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Garey
 *
 */
public class PersonSearchPanel extends HorizontalPanel {

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
	    super.setCellWidth(advPanel, "160px");
	    srPanel.addStyleName("PersonAdvancedSearchPanel");
	}
}
