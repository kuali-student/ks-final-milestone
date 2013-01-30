package org.kuali.student.ap.framework.course;

import java.util.List;
import java.util.Map;

import org.kuali.student.r2.core.search.infc.SearchResultRow;

public interface CourseSearchStrategy {

	CourseSearchForm createSearchForm();

	String getCellValue(SearchResultRow row, String key);

	Map<String, Credit> getCreditMap();

	Credit getCreditByID(String id);

	boolean isCourseOffered(CourseSearchForm form, CourseSearchItem course);

	void populateFacets(CourseSearchForm form, List<CourseSearchItem> courses);

	List<CourseSearchItem> courseSearch(CourseSearchForm form, String studentId);

	Map<String, String> fetchCourseDivisions();

	String extractDivisions(Map<String, String> divisionMap, String query,
			List<String> divisions, boolean isSpaceAllowed);
}
