package org.kuali.student.ap.coursesearch;

import java.math.BigDecimal;
import java.util.List;

/**
 * Bean interface representing a search row in KSAP course search from the
 * ks-ap-ui module perspective.
 * 
 * @version ks-ap-framework-0.3
 */
public interface CourseSearchItem {

	static final String EMPTY_RESULT_VALUE_KEY = "&mdash;";

	enum CreditType {
		FIXED, RANGE, MULTIPLE, UNKNOWN
	}

	/**
	 * Get the course ID (CLU ID) for the search item.
	 * 
	 * @return The CLU ID for this search item.
	 */
	String getCourseId();

	/**
	 * Get the subject area ORG identifier.
	 * 
	 * @return The subject area ORG identifier.
	 */
	String getSubject();

	/**
	 * Get the course code (catalog number).
	 * 
	 * @return The course code.
	 */
	String getCode();

	/**
	 * Get the course level (100, 200, 300, etc), for use with CourseLevelFacet.
	 * 
	 * @return The course level.
	 */
	String getLevel();

	/**
	 * Get the credits minimum value, for use with CreditsFacet.
	 * 
	 * @return The credits minimum value.
	 */
	float getCreditMin();

	/**
	 * Get the credits maximum value, for use with CreditsFacet.
	 * 
	 * @return The credits maximum value.
	 */
	float getCreditMax();

    /**
     * Get the multiple values of credits, for use with CreditsFacet
     * @return The array of credit values
     */
    List<BigDecimal> getMultipleCredits();

	/**
	 * Get the credit type for use with CreditsFacet.
	 * 
	 * @return The credit type.
	 */
	CreditType getCreditType();

	/**
	 * Get the general education requirements for this course, for use with
	 * GenEduReqFacet.
	 * 
	 * @return The gen ed requirements for this course.
	 */
	List<String> getGenEduReqs();

	/**
	 * Get the HTML data to present in the individual table cells related to
	 * this search item.
	 * 
	 * @return The HTML data representing this course for presentation by
	 *         DataTables. This length of this array must be less than or equal
	 *         to the number of columns configured on the search table in the
	 *         front end.
	 */
	String[] getSearchColumns();

	/**
	 * Get contextual data to use for sorting related to this search item.
	 * 
	 * @return The data to use for sorting search items. This length of this
	 *         array must be less than or equal to the number of columns
	 *         configured on the search table in the front end, and a non-null
	 *         value must be present in the array at the index corresponding to
	 *         each of the columns defined as sortable in DataTables on the
	 *         front end.
	 */
	String[] getSortColumns();

	/**
	 * Get contextual data to use for building search facets related to this
	 * search item.
	 * 
	 * @return The data to use for matching the search items. This length of
	 *         this array must be less than or equal to the number of columns
	 *         configured on the search table in the front end, and a non-null
	 *         value must be present in the array at the index corresponding to
	 *         each of the columns defined as searchable in DataTables on the
	 *         front end. Note that facet columns do not need to line up with
	 *         search columns; since the front end never deals with facets
	 *         directly, there is not need for the cells to match.
	 */
	FacetIndex getFacetColumns();

	/**
	 * Get the list of term ATP type IDs for which this course is projected.
	 * 
	 * @return The list of term ATP type IDs for which this course is projected.
	 */
	List<String> getTermInfoList();

	/**
	 * Get the list of term ATP -IDs for which this course is scheduled.
	 * 
	 * @return The list of term ATP IDs for which this course is scheduled.
	 */
	List<String> getScheduledTermsList();

    /**
     * Get a list of campus where the courses is offered.
     *
     * @return A list of campuses.
     */
    List<String> getCampuses();

    /**
     * Get the version independent id of the course
     *
     * @return A identifier for the course not related to a specific version
     */
    public String getVersionIndependentId();

    /**
     * Flag to indicate if the search was exceeded
     *
     * @return True if more courses were found than the allowed, false otherwize
     */
    public boolean isSearchExceeded();
}
