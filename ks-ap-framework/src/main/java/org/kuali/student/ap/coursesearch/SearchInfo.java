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
     * @return
     */
    CourseSearchItem getItem();

    /**
     * Get the sort columns
     * @return
     */
    String[] getSortColumns();

    /**
     * Get the facet columns
     * @return
     */
    Map<String, List<String>> getFacetColumns();
}
