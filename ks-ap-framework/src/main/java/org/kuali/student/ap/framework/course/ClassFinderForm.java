package org.kuali.student.ap.framework.course;

import java.util.List;

/**
 * Interface for the driving form properties for next generation class finder
 * JSON interface.
 */
public interface ClassFinderForm {

	/**
	 * Enumerates course level criteria for class finder.
	 */
	public enum CourseLevel {
		UNDERGRADUATE("Undergraduate"), GRADUATE("Graduate"), PROFESSIONAL(
				"Professional");
		private final String descr;

		private CourseLevel(String descr) {
			this.descr = descr;
		}

		@Override
		public String toString() {
			return descr;
		}
	}

	/**
	 * Get the search query.
	 * 
	 * @return The search query.
	 */
	String getQuery();

	/**
	 * Set the search query.
	 * 
	 * @param query
	 *            The search query.
	 */
	void setQuery(String query);

	/**
	 * Determine if a facet is to be considered part of the search criteria, or
	 * as post-filtering item.
	 * 
	 * @param facet
	 *            The facet key, should be a valid value for getFacet().
	 * @return True if the facet key is part of the search criteria, false if
	 *         not.
	 */
	boolean isCriterion(String facet);

	/**
	 * Get all clicked search facets.
	 * 
	 * @return All clicked search facets.
	 */
	List<String> getFacet();

	/**
	 * Set all clicked search facets.
	 * 
	 * @param facet
	 *            All clicked search facets.
	 */
	void setFacet(List<String> facet);

	/**
	 * Get the index of the first result to return.
	 * 
	 * @return The index of the first result to return.
	 */
	int getStart();

	/**
	 * Set the index of the first result to return.
	 * 
	 * @param start
	 *            The index of the first result to return.
	 */
	void setStart(int start);

	/**
	 * Get the number of results to return.
	 * 
	 * @return The number results to return.
	 */
	int getCount();

	/**
	 * Set the number of results to return.
	 * 
	 * @param count
	 *            The number results to return.
	 */
	void setCount(int count);

	/**
	 * Get the name of the property to sort results by.
	 * 
	 * @return The name of the property to sort results by.
	 */
	String getSort();

	/**
	 * Set the name of the property to sort results by.
	 * 
	 * @param sort
	 *            The name of the property to sort results by.
	 */
	void setSort(String sort);

	/**
	 * Determine if results should be returned in reverse order.
	 * 
	 * @return True if results should be returned in reverse (descending) order,
	 *         false for ascending order.
	 */
	boolean isReverse();

	/**
	 * Set whether results should be returned in reverse order.
	 * 
	 * @param reverse
	 *            True if results should be returned in reverse (descending)
	 *            order, false for ascending order.
	 */
	void setReverse(boolean reverse);

}
