package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.coursesearch.CourseFacetStrategy;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.FacetState;
import org.kuali.student.ap.coursesearch.SearchInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/4/14
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseFacetStrategyMockTest implements CourseFacetStrategy {
    @Override
    public String writeFacetToJson(CourseSearchForm form, Map<String, Map<String, FacetState>> facetStateMap) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Integer> getFacetColumns() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getFacetColumnsReversed() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateFacetCounts(List<SearchInfo> searchResults, Map<String, Map<String, FacetState>> facetState, Map<String, List<String>> facetCols) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean facetClick(String fclick, String fcol, Map<String, FacetState> facetStateMap) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean facetClickAll(boolean oneClick, Map<String, Map<String, FacetState>> facetStateMap) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Map<String, FacetState>> createInitialFacetStateMap(CourseSearchStrategy searcher, Map<String, List<String>> facetColumns, List<SearchInfo> searchResults) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Map<String, FacetState>> processFacetStateMap(CourseSearchStrategy searcher, Map<String, Map<String, FacetState>> facetStateMap, Map<String, List<String>> facetColumns) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
