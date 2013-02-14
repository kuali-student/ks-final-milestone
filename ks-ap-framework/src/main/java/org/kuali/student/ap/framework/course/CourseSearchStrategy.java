package org.kuali.student.ap.framework.course;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.core.search.infc.SearchResultRow;

/**
 * Strategy interface for specifying course search behavior.
 */
public interface CourseSearchStrategy {

	/**
	 * Create a new instance of the course search form.
	 * 
	 * @return A new instance of the course search form.
	 */
	CourseSearchForm createSearchForm();

	/**
	 * Get a specific cell value for a search row.
	 * 
	 * @param row
	 *            The search result row.
	 * @param key
	 *            The column key.
	 *            @
	 */
	String getCellValue(SearchResultRow row, String key);

	Map<String, Credit> getCreditMap();

	Credit getCreditByID(String id);

	boolean isCourseOffered(CourseSearchForm form, CourseSearchItem course);

	void populateFacets(CourseSearchForm form, List<CourseSearchItem> courses);

	List<CourseSearchItem> courseSearch(CourseSearchForm form, String studentId);

	Map<String, String> fetchCourseDivisions();

	String extractDivisions(Map<String, String> divisionMap, String query,
			List<String> divisions, boolean isSpaceAllowed);

	Map<String, Comparator<String>> getFacetSort();

}
