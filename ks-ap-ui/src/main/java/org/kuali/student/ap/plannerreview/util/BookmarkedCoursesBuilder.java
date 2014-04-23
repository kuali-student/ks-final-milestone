package org.kuali.student.ap.plannerreview.util;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.plannerreview.dto.CourseInfo;
import org.kuali.student.ap.plannerreview.form.ConversationCreateForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Build the list of bookmarked courses that are available to select for the planner snapshot
 * @see ConversationCreateForm#getBookmarkedCourses()
 * @author Chris Maurer <chmaurer@iupui.edu>
 *
 */
public class BookmarkedCoursesBuilder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 6304950866647571324L;
	
	private boolean useSpanInsteadOfTooltip = false;
	
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> courseList = new ArrayList<KeyValue>();
		if (model instanceof ConversationCreateForm) {
			ConversationCreateForm form = (ConversationCreateForm) model;
			
			if (form.getBookmarkedCourses() != null) {
				for (Entry<String, CourseInfo> entry : form.getBookmarkedCourses().entrySet()) {
					courseList.add(new ConcreteKeyValue(entry.getKey(), toolTipifyText(entry.getValue().getCourseCode(), entry.getValue().getCourseName())));
				}
			}
			// Add some mock data if the form asks for it
			if (form.isMockData()) {
				courseList.add(new ConcreteKeyValue("bm1", "MATH-A 100"));
				courseList.add(new ConcreteKeyValue("bm2", "PSYC-B 200"));
				courseList.add(new ConcreteKeyValue("bm3", "CSCI-C 300"));
				courseList.add(new ConcreteKeyValue("bm4", "ART-D 400"));
				courseList.add(new ConcreteKeyValue("bm5", "MUSI-E 500"));
			}
		}
		return courseList;
	}
	
	/**
	 * Make rich message text for the checkbox value to contain information to build a tooltip
	 * @param courseCode
	 * @param courseName
	 * @return
	 */
	private String toolTipifyText(String courseCode, String courseName) {
		String value="[id='course_checkbox_tooltip' messageText='" + courseCode + "' toolTip.tooltipContent='" + courseName + "']";
		if (useSpanInsteadOfTooltip)
			value="[span title='" + courseName + "']" + courseCode + "[/span]";
		return value;
	}

	public boolean isUseSpanInsteadOfTooltip() {
		return useSpanInsteadOfTooltip;
	}

	public void setUseSpanInsteadOfTooltip(boolean useSpanInsteadOfTooltip) {
		this.useSpanInsteadOfTooltip = useSpanInsteadOfTooltip;
	}
}
