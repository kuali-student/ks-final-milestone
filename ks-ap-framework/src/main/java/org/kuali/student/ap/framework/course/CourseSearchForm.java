package org.kuali.student.ap.framework.course;

import java.util.List;

/**
 * Common interface for controlling course search inputs.
 */
public interface CourseSearchForm {

	/**
	 * String value representing a request to search for all courses regardless
	 * of offering data.
	 */
	static final String SEARCH_TERM_ANY_ITEM = "any";

	/**
	 * String value representing a request to search only for courses projected
	 * to be offered in at least one current or future term.
	 */
	static final String SEARCH_TERM_PROJECTED = "projected";

	/**
	 * String value representing a request to search only for courses currently
	 * published on the time schedule.
	 */
	static final String SEARCH_TERM_SCHEDULED = "scheduled";

	/**
	 * Get the search query, as provided by the user.
	 * 
	 * @return The search query.
	 */
	String getSearchQuery();

	/**
	 * Get the list of selected campus IDs.
	 * 
	 * @return The list of selected campus IDs, not null.
	 */
	List<String> getCampusSelect();

	/**
	 * Get the search term value.
	 * 
	 * <p>
	 * May be:
	 * </p>
	 * <ol>
	 * <li>{@link #SEARCH_TERM_ANY_ITEM}</li>
	 * <li>{@link #SEARCH_TERM_PROJECTED}</li>
	 * <li>{@link #SEARCH_TERM_SCHEDULED}</li>
	 * <li>An ATP ID referring to a specific scheduled term</li>
	 * <li>An ATP Type Key referring to a specific projected term</li>
	 * </ol>
	 * 
	 * @return The search term value.
	 */
	String getSearchTerm();
	
	/**
	 * Get the saved courses flag.
	 * @return True if search results should include only bookmarked and planned courses, false to include all courses. 
	 */
	boolean isSavedCourses();

	/**
	 * Return the values of all criteria items added by institution-specific
	 * overrides. This list of values will be used to uniquely identify a search
	 * result list in a session-bound result cache, so any value that modifies
	 * the search should be returned to facilitate appropriate caching of
	 * results.
	 * 
	 * @return The values of all criteria items added by institution specific
	 *         overrides.
	 */
	List<String> getAdditionalCriteria();

}
