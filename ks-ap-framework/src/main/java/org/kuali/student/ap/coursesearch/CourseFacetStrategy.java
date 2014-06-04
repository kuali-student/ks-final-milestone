package org.kuali.student.ap.coursesearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Strategy interface for specifying course search facet behavior.
 */
public interface CourseFacetStrategy {

    String writeFacetToJson(CourseSearchForm form, Map<String, Map<String, FacetState>> facetStateMap) throws IOException;
    Map<String, Integer> getFacetColumns();
    List<String> getFacetColumnsReversed();
    void updateFacetCounts(List<SearchInfo> searchResults, Map<String, Map<String, FacetState>> facetState, Map<String, List<String>> facetCols);
    boolean facetClick(String fclick, String fcol, Map<String, FacetState> facetStateMap);
    boolean facetClickAll(boolean oneClick, Map<String, Map<String, FacetState>> facetStateMap);
    Map<String, Map<String, FacetState>> createInitialFacetStateMap(CourseSearchStrategy searcher, Map<String, List<String>> facetColumns, List<SearchInfo> searchResults);
    Map<String, Map<String, FacetState>> processFacetStateMap(CourseSearchStrategy searcher, Map<String, Map<String, FacetState>> facetStateMap, Map<String, List<String>> facetColumns);
}
