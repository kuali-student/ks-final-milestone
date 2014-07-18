package org.kuali.student.ap.coursesearch;

import java.util.List;
import java.util.Map;

/**
 * Simple object representing pre-processed search data.
 *
 * @see org.kuali.student.ap.coursesearch.CourseSearchItem#getSearchColumns()
 * @see org.kuali.student.ap.coursesearch.CourseSearchItem#getFacetColumns()
 */
public interface SearchInfo {

    /**
     * Get the course search item
     * @return The course item stored.
     */
    CourseSearchItem getItem();

    /**
     * Get the sort columns
     * @return A list of columns for sorting the stored item.
     */
    String[] getSortColumns();

    /**
     * Get the facet columns
     * @return - The facet information for the stored item
     */
    Map<String, List<String>> getFacetColumns();
}
