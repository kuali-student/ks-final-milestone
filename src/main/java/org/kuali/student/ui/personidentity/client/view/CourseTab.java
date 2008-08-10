package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.ui.personidentity.client.view.lu.CourseDetailsPanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class CourseTab extends TabPanel {

    final CourseSearchPanel courseSearchPanel = new CourseSearchPanel();
    final CourseDetailsPanel cDetails = new CourseDetailsPanel();

    public CourseTab() {
        super.setStyleName("gwt-TabBar-Nested");
        // add(courseSearchPanel, "Course Search");
        // add(courseSearchPanel, I18N.i18nConstant.courseSearch());
        addTab(courseSearchPanel, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("courseSearch"));

        addTab(cDetails, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("courseDetail"));
    }

    public CourseSearchPanel getCourseSearchPanel() {
		return courseSearchPanel;
	}

	public void displaySearchResultsTab() {
        this.selectTab(this.getWidgetIndex(courseSearchPanel));
    }

    public void displayCourseDetailsTab() {
        this.selectTab(this.getWidgetIndex(cDetails));
    }
    
    private void addTab(Widget tabWidget, String text) {
        Label label = new Label(text);
        label.addStyleName("gwt-TabBarItem");
        add(tabWidget, label);
    }
}
