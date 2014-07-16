/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.ap.coursesearch.controller;

import org.kuali.student.ap.coursesearch.CourseFacetStrategy;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.FacetKeyValue;
import org.kuali.student.ap.coursesearch.FacetState;
import org.kuali.student.ap.coursesearch.SearchInfo;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Session-bound search results cache. This object backs the facet and data
 * table result views on the KSAP course search front-end. Up to three
 * searches are stored in the HTTP session via these objects.
 *
 */

public class SessionSearchInfo {

    private static final Logger LOG = LoggerFactory.getLogger(SessionSearchInfo.class);

    /**
     * The search result column data.
     */
    private final List<SearchInfo> searchResults;

    /**
     * The course facet strategy
     */
    private CourseFacetStrategy facetStrategy = KsapFrameworkServiceLocator
            .getCourseFacetStrategy();

    /**
     * The calculated facet state.
     */
    private final Map<String, Map<String, FacetState>> facetState;

    /**
     * Pruned facet state - this shared state keeps a count of all facets
     * values that were pruned from display due to size limits and relevance
     * scoring.
     *
     * <p>
     * Note that pruned is not reliable - it is only a placeholder to
     * facilitate the counting algorithm and is used for informational
     * logging. The first facet build leaves pruned rows uncounted - on
     * updated builds they are recorded in the log for tuning purposes.
     * </p>
     */
    private final FacetStateImpl pruned;

    /**
     * The oneClick flag records whether or not any facet state leaf nodes
     * have been switched to false. Until oneClick has been set, count
     * updates and clickAll requests will be ignored.
     *
     * @see #facetClick(String, String)
     * @see #facetClickAll()
     * @see #updateFacetCounts()
     */
    private boolean oneClick = false;

    /**
     * Compose search information based on materialized inputs.
     *
     * <p>
     * This constructor is potentially expensive - it is where the actual
     * search is performed on the back end when pulling data or facet table
     * results.
     * </p>
     *
     * @param request
     *            The active HTTP servlet request.
     * @param form
     *            The search form.
     */
    public SessionSearchInfo(HttpServletRequest request,
                             CourseSearchForm form) {
        this(request, new FormKey(form), KsapFrameworkServiceLocator
                .getCourseSearchStrategy().courseSearch(
                        form,
                        KsapFrameworkServiceLocator.getUserSessionHelper()
                                .getStudentId()));
    }

    SessionSearchInfo(HttpServletRequest request, FormKey formKey,
                      List<CourseSearchItem> courses) {
        CourseSearchStrategy searcher = KsapFrameworkServiceLocator
                .getCourseSearchStrategy();
        List<SearchInfo> resultList = new ArrayList<SearchInfo>(
                courses.size());
        for (CourseSearchItem course : courses)
            resultList.add(new SearchInfoImpl(course));
        this.searchResults = Collections.unmodifiableList(Collections
                .synchronizedList(resultList));

        // Calculate initial facet state when search results are non-empty
        if (searchResults.isEmpty())
            facetState = Collections.emptyMap();
        else {
            facetState = Collections.synchronizedMap(Collections
                    .unmodifiableMap(createFacetStateMap(searcher)));
        }
        // Tread pruned facets as not checked unless all
        // visible facet values in the same group are checked
        pruned = new FacetStateImpl(new FacetKeyValue("", ""));
        pruned.setChecked(false);
    }

    private Map<String, Map<String, FacetState>> createFacetStateMap(CourseSearchStrategy searcher) {
        Map<String, List<String>> facetColumns = searchResults.get(0).getFacetColumns();
        assert facetColumns.size() == KsapFrameworkServiceLocator.getCourseFacetStrategy().getFacetSort().size() : facetColumns
                .size()
                + " != "
                + KsapFrameworkServiceLocator.getCourseFacetStrategy().getFacetSort().size()
                + " ... " + searchResults.get(0);

        Map<String, Map<String, FacetState>> facetStateMap = facetStrategy.createInitialFacetStateMap(facetColumns, searchResults);
        return facetStrategy.processFacetStateMap(facetStateMap, facetColumns);
    }

    /**
     * Update checked state on all facets following a click event from the
     * browser.
     *
     * @param key
     *            The facet key clicked. May be 'All'.
     * @param fcol
     *            The facet column the click is related to.
     */
    public void facetClick(String key, String fcol) {
        LOG.debug("Facet click {} {}", key, fcol);
        Map<String, FacetState> fsm = facetState.get(fcol);
        if (fsm == null)
            return;
        oneClick = facetStrategy.facetClick(key, fcol, fsm,oneClick);
        if (oneClick)
            updateFacetCounts();
    }

    private void facetClickAll() {
        LOG.debug("Facet click all");
        boolean tmpOneClick = facetStrategy.facetClickAll(oneClick, facetState);
        if (tmpOneClick != oneClick) {
            updateFacetCounts();
            oneClick = tmpOneClick;
        }
    }

    private void updateFacetCounts() {
        if (searchResults.isEmpty())
            return;

        // Determine the number of facet columns - this should be uniform
        // across the facet state table and the facet columns in each row
        Map<String, List<String>> facetCols;
        if(!(searchResults==null) && !searchResults.isEmpty()){
            facetCols = searchResults.get(0).getFacetColumns();
        }else{
            return;
        }

        assert facetState.size() == facetCols.size() : facetState.size()
                + " != " + facetCols.size();

        facetStrategy.updateFacetCounts(searchResults, facetState, facetCols);
        if (LOG.isDebugEnabled())
            LOG.debug("Pruned facet entry {}", pruned.getCount());
    }


    public List<SearchInfo> getFilteredResults(
            final DataTablesInputs dataTablesInputs) {
        final List<SearchInfo> filteredResults = new ArrayList<SearchInfo>(
                searchResults);

        /**
         * Tracks loop state for search filtering.
         */
        class Iter implements Iterator<SearchInfo> {

            /**
             * Backing iterator.
             */
            Iterator<SearchInfo> i = filteredResults.iterator();

            /**
             * The row data last returned by {@link #next()}.
             */
            SearchInfo current;

            /**
             * The removed state of the current row
             */
            boolean removed = false;

            /**
             * The search string on the current column.
             */
            String searchString = dataTablesInputs.getsSearch();

            /**
             * The search patter on the current column, null if non-regex.
             */
            Pattern searchPattern = dataTablesInputs.getPatSearch();

            /**
             * Current column pointer.
             */
            int j = -1;

            /**
             * Column iterator.
             */
            Iterator<List<String>> fi = new Iterator<List<String>>() {
                @Override
                public boolean hasNext() {
                    // break column loop once row has been removed,
                    // or when all columns have been seen
                    return !removed && j < current.getFacetColumns().size() - 1;
                }

                @Override
                public List<String> next() {
                    // break column loop once row has been removed
                    if (removed)
                        throw new IllegalStateException(
                                "Row has been removed");
                    // increase column pointer, update search fields
                    j++;
                    if (dataTablesInputs.getsSearch_()[j] != null) {
                        searchString = dataTablesInputs.getsSearch_()[j];
                        searchPattern = dataTablesInputs.getPatSearch_()[j];
                    } else {
                        searchString = dataTablesInputs.getsSearch();
                        searchPattern = dataTablesInputs.getPatSearch();
                    }
                    // Here is where data tables column # is tied to
                    // internal
                    // facet column order.
                    return current.getFacetColumns().get(facetStrategy.getFacetColumnsReversed()
                            .get(j));
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };

            /**
             * Determine whether or not DataTables defines the current
             * column as searchable.
             *
             * @return True if DataTables defines the current column as
             *         searchable.
             */
            private boolean isSearchable() {
                return dataTablesInputs.getbSearchable_()[j];
            }

            /**
             * Get the column iterator, after resetting to the start of the
             * row.
             *
             * @return The column iterator, reset to the start of the row.
             */
            private Iterable<List<String>> facets() {
                return new Iterable<List<String>>() {
                    @Override
                    public Iterator<List<String>> iterator() {
                        j = -1;
                        return fi;
                    }
                };
            }

            @Override
            public boolean hasNext() {
                return i.hasNext();
            }

            @Override
            public SearchInfo next() {
                removed = false;
                return current = i.next();
            }

            @Override
            public void remove() {
                removed = true;
                // if (LOG.isDebugEnabled())
                // LOG.debug("Removed " + this);
                i.remove();
            }

            @Override
            public String toString() {
                return "Iter [current="
                        + current.getFacetColumns().get(facetStrategy.getFacetColumnsReversed()
                                .get(j)) + ", removed=" + removed
                        + ", searchString=" + searchString
                        + ", searchPattern=" + searchPattern + ", j=" + j
                        + " (" + facetStrategy.getFacetColumnsReversed().get(j) + ")]";
            }
        }
        Iter li = new Iter();
        while (li.hasNext()) { // filter search results
            SearchInfo ln = li.next();
            // li maintains its own handle to the row
            assert ln == li.current; // ln is otherwise unused
            for (List<String> cell : li.facets()) {
                if (li.isSearchable()) {
                    if (li.searchString == null
                            || li.searchString.trim().equals(""))
                        continue;
                    if (cell == null || cell.size() == 0)
                        li.remove();
                    else {
                        boolean match = false;
                        for (String c : cell) {
                            if (match)
                                continue;
                            if (li.searchPattern == null) {
                                if (c.toUpperCase().equals(
                                        li.searchString.toUpperCase()))
                                    match = true;
                            } else if (li.searchPattern.matcher(c)
                                    .matches())
                                match = true;
                        }
                        if (!match)
                            li.remove();
                    }
                }
            }
        }

        // Perform sorting if requested
        if (dataTablesInputs.getiSortingCols() > 0)
            Collections.sort(filteredResults, new Comparator<SearchInfo>() {
                @Override
                public int compare(SearchInfo o1, SearchInfo o2) {
                    for (int i = 0; i < dataTablesInputs.getiSortingCols(); i++) {
                        String s1 = o1.getSortColumns()[dataTablesInputs.getiSortCol_()[i]];
                        String s2 = o2.getSortColumns()[dataTablesInputs.getiSortCol_()[i]];
                        if (s1 == null && s2 == null)
                            continue;
                        if (s1 == null)
                            return "desc"
                                    .equals(dataTablesInputs.getsSortDir_()[i]) ? 1
                                    : -1;
                        if (s2 == null)
                            return "desc"
                                    .equals(dataTablesInputs.getsSortDir_()[i]) ? -1
                                    : 1;
                        int rv = 0;
                        if("desc".equals(dataTablesInputs.getsSortDir_()[i])){
                            rv = s2.compareTo(s1);
                        }else{
                            rv = s1.compareTo(s2);
                        }
                        if (rv != 0)
                            return rv;
                    }
                    return 0;
                }
            });

        return filteredResults;
    }

    public List<SearchInfo> getSearchResults() {
        return searchResults;
    }

    public Map<String, Map<String, FacetState>> getFacetState() {
        return facetState;
    }

    public FacetState getPruned() {
        return pruned;
    }

    public boolean isOneClick() {
        return oneClick;
    }

    public void setOneClick(boolean oneClick) {
        this.oneClick = oneClick;
    }
}
