package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.SearchInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Simple object representing pre-processed search data.
 *
 * @see SessionSearchInfo
 * @see org.kuali.student.ap.coursesearch.CourseSearchItem#getSearchColumns()
 * @see org.kuali.student.ap.coursesearch.CourseSearchItem#getFacetColumns()
 */
public class SearchInfoImpl implements Serializable, SearchInfo {
    private static final long serialVersionUID = 8697147011424347285L;

    private final CourseSearchItem item;
    private final String[] sortColumns;
    private final Map<String, List<String>> facetColumns;

    /**
     * Constructor which processes an item returned in the search and stores information for quick reference.
     *
     * @param item - Item for the search to process.
     */
    public SearchInfoImpl(CourseSearchItem item) {
        this.item = item;
        sortColumns = item.getSortColumns();
        facetColumns = new java.util.LinkedHashMap<String, List<String>>();
        for (Map.Entry<Object, List<KeyValue>> fe : item
                .getFacetColumns().entrySet()) {
            List<String> fl = facetColumns.get(fe.getKey());
            if (fl == null)
                facetColumns.put((String)fe.getKey(), fl = new ArrayList<String>());
            for(KeyValue keyValue : fe.getValue()){
                fl.add(keyValue.getKey());
            }
        }

    }

    @Override
    public String toString() {
        return "SearchInfoImpl [searchColumns=" + item.getCourseId()
                + ", sortColumns=" + Arrays.toString(sortColumns)
                + ", facetColumns=" + facetColumns + "]";
    }

    /**
     * @see org.kuali.student.ap.coursesearch.SearchInfo#getItem()
     */
    @Override
    public CourseSearchItem getItem() {
        return item;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.SearchInfo#getSortColumns()
     */
    @Override
    public String[] getSortColumns() {
        return sortColumns;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.SearchInfo#getFacetColumns()
     */
    @Override
    public Map<String, List<String>> getFacetColumns() {
        return facetColumns;
    }
}
