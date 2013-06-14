package org.kuali.student.ap.framework.course;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Bean interface representing a search row in KSAP course search from the
 * ks-ap-ui module perspective.
 * 
 * @version ks-ap-framework-0.3
 */
public interface CourseSearchItem {

	static final String EMPTY_RESULT_VALUE_KEY = "&mdash;";

	static final Set<String> NOISE_WORDS = new java.util.HashSet<String>(
			Arrays.asList("THE", "AND", "BUT", "PUT", "FOR", "ALL", "ALSO",
					"ARE", "BETWEEN", "BOTH", "DAY", "EACH", "HALL", "WHO",
					"WHAT", "WHERE", "WHICH", "WHY", "HOW", "HIS", "HERS",
					"THEN", "THAN", "THAT", "THEREFORE", "THEIR", "WHICHEVER",
					"WHOM", "IN", "OUT", "NORTH", "SOUTH", "EAST", "WEST",
					"TOPICS", "EMPHASIS", "COURSE", "EMPHASIS", "INTRO",
					"INTRODUCTION", "ISSUES", "SKILLS", "STUDENTS", "INCLUDE",
					"STUDY"));

	enum CreditType {
		fixed, range, multiple, unknown
	}

	enum PlanState {
		UNPLANNED(""), SAVED("Bookmarked"), IN_PLAN("Planned");

		private final String label;

		PlanState(String label) {
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}
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
	String getGenEduReq();

	/**
	 * Get the HTML data to present in the individual table cells related to
	 * this search item.
	 * 
	 * @return The HTML data representing this course for presentation by
	 *         DataTables. This length of this array must be less than or equal
	 *         to the number of columns configured on the search table in the
	 *         front end.
	 * 
	 * @see CourseSearchUI.xml
	 * @see myplan.search.js
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
	 * 
	 * @see CourseSearchUI.xml
	 * @see myplan.search.js
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
	 * 
	 * @see CourseSearchUI.xml
	 * @see myplan.search.js
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
	 * Get a list of keywords for use with the trending searches feature.
	 * 
	 * @return A list of keywords for use with the trending searches feature.
	 */
	List<String> getKeywords();

}
