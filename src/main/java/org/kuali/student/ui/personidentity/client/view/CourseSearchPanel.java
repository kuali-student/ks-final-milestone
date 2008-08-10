package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.ui.personidentity.client.view.lu.CourseAdvancedSearchPanel;
import org.kuali.student.ui.personidentity.client.view.lu.CourseSearchResultPanel;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CourseSearchPanel extends HorizontalPanel {
	
	CourseSearchResultPanel srPanel = new CourseSearchResultPanel();
	CourseAdvancedSearchPanel	advPanel = new CourseAdvancedSearchPanel(); 


	public CourseSearchPanel(){
		this.add(advPanel);
		this.add(srPanel);
	}


	public CourseSearchResultPanel getSrPanel() {
		return srPanel;
	}
	
	public void onLoad() {
	    super.setCellWidth(advPanel, "160px");
	    srPanel.addStyleName("CourseAdvancedSearchPanel");
	}
    
}
