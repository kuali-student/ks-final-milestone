package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.FacetKeyValue;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final FacetState pruned;

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
     * @see CourseSearchController#getJsonResponse(org.kuali.student.ap.coursesearch.CourseSearchForm,
     *      javax.servlet.http.HttpServletResponse, javax.servlet.http.HttpServletRequest)
     * @see CourseSearchController#getFacetValues(javax.servlet.http.HttpServletResponse,
     *      javax.servlet.http.HttpServletRequest)
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
            resultList.add(new SearchInfo(course));
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
        pruned = new FacetState(new FacetKeyValue("", ""));
        pruned.setChecked(false);
    }

    private Map<String, Map<String, FacetState>> createFacetStateMap(CourseSearchStrategy searcher) {
        Map<String, List<String>> facetColumns = searchResults.get(0).getFacetColumns();
        assert facetColumns.size() == searcher.getFacetSort().size() : facetColumns
                .size()
                + " != "
                + searcher.getFacetSort().size()
                + " ... " + searchResults.get(0);
        Map<String, Map<String, FacetState>> facetStateMap = new java.util.HashMap<String, Map<String, FacetState>>(
                facetColumns.size());
        for (String fk : facetColumns.keySet())
            facetStateMap.put(fk,
                    new java.util.HashMap<String, FacetState>());
        final Map<String, Integer> kwc = new java.util.HashMap<String, Integer>();
        // Generate initial counts, unabridged and unordered
        for (SearchInfo row : searchResults) {
            // Validate that the number of facet columns is uniform
            // across all search rows
            assert row.getFacetColumns() != null
                    || row.getFacetColumns().size() == facetColumns.size() : row.getFacetColumns() == null ? "null"
                    : row.getFacetColumns().size() + " != "
                            + facetColumns.size();
            // Update facet counts for all columns, creating pre-checked
            // state nodes as needed
            for (Map.Entry<String, Map<String, FacetState>> fce : facetStateMap
                    .entrySet()) {
                Map<String, FacetState> fm = fce.getValue();
                Map<String, Map<String, KeyValue>> fcr;
                if (row.getItem() != null
                        && (fcr = row.getItem().getFacetColumns().get(
                                fce.getKey())) != null)
                    for (Map.Entry<String, Map<String, KeyValue>> group : fcr
                            .entrySet())
                        for (Map.Entry<String, KeyValue> fe : group
                                .getValue().entrySet()) {
                            KeyValue fv = fe.getValue();
                            assert fv.getKey().length() >= 1 : fv
                                    .getKey();
                            String facetKey = fe.getKey();
                            FacetState fs = fm.get(facetKey);
                            if (fs == null) {
                                fm.put(facetKey, fs = new FacetState(fv));
                                if (fce.getKey().equals("facet_genedureq")) {
                                    fs.setDescription(searcher.getGenEdMap().get(fv.getKey()));
                                } else if (fce.getKey().equals("facet_curriculum")) {
                                    fs.setDescription(searcher.getCurriculumMap(null).get(fv.getKey()));
                                }
                            }
                            fs.incrementCount();
                        }
            }

        }
        // Post-process based on calculated totals and per-column rules
        for (String fk : facetColumns.keySet()) {
            final Map<String, FacetState> fm = facetStateMap.get(fk);
            Map<String, FacetState> nfm;
            if (fm.isEmpty())
                // Discard superfluous empty map references
                facetStateMap.put(fk, nfm = Collections.emptyMap());
            else {
                // Establish facet key order
                String[] sk = fm.keySet()
                        .toArray(new String[fm.size()]);
                // Sort by relevance for trending
                Arrays.sort(sk, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        FacetState fs1 = fm.get(o1);
                        FacetState fs2 = fm.get(o2);
                        return fs1.getCount() == fs2.getCount() ? 0
                                : fs1.getCount() < fs2.getCount() ? 1 : -1;
                    }
                });
                // Truncate results to show only the 50 most relevant
                // words in the facet
                sk = Arrays.copyOf(sk, Math.min(sk.length, 50));
                Map<String, List<String>> vtk = new java.util.HashMap<String, List<String>>();
                for (String k : sk) {
                    assert k != null : fk;
                    String fvk = fm.get(k).getValue().getKey();
                    assert fvk.length() >= 1 : fk + " " + fvk;
                    List<String> l = vtk.get(fvk);
                    if (l == null)
                        vtk.put(fvk,
                                l = new java.util.LinkedList<String>());
                    l.add(k);
                }
                String[] sv = vtk.keySet().toArray(
                        new String[vtk.size()]);
                // Sort according to strategy definitions
                Arrays.sort(sv, searcher.getFacetSort().get(fk));
                nfm = new java.util.LinkedHashMap<String, FacetState>(
                        sk.length);
                for (String v : sv)
                    for (String k : vtk.get(v))
                        // Insert truncated facet keys in order
                        nfm.put(k, fm.get(k));

                // Seal the map for synchronized use
                facetStateMap.put(fk, Collections
                        .synchronizedMap(Collections
                                .unmodifiableMap(nfm)));
            }
        }
        return facetStateMap;
    }

    /**
     * Get the facet state associated with a specific facet column value.
     *
     * @param key
     *            The facet column value to use as the facet key.
     * @param facetId
     *            The facet id.
     * @return The facet state associated with the indicated column value.
     */
    private FacetState getFacetState(String key, String facetId) {
        Map<String, FacetState> fm = facetState.get(facetId);
        FacetState fs = fm.get(key);
        if (fs == null)
            return pruned;
        else
            return fs;
    }

    /**
     * Walk through the facet columns in the search results and update
     * counts based on the current facet state. The method assumes that the
     * facet state structure is fully formed but that the click state has
     * change since the last time counts were calculated.
     */
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

        // Reset the count on all facet state leaf nodes to 0
        pruned.setCount(0);
        Map<String, Boolean> all = new java.util.HashMap<String, Boolean>(
                facetState.size());
        for (Map.Entry<String, Map<String, FacetState>> fse : facetState
                .entrySet())
            for (FacetState fs : fse.getValue().values()) {
                fs.setCount(0);
                if (!Boolean.FALSE.equals(all.get(fse.getKey())))
                    all.put(fse.getKey(), fs.isChecked());
            }

        // Iterate search results
        for (SearchInfo row : searchResults) {

            // Validate facet column count matches facet table size
            assert row.getFacetColumns() != null
                    || row.getFacetColumns().size() == facetCols.size() : row.getFacetColumns() == null ? "null"
                    : row.getFacetColumns().size() + " != " + facetCols.size();

            // identify filtered rows before counting
            boolean filtered = false;
            for (Map.Entry<String, List<String>> fce : facetCols.entrySet()) {
                if (filtered)
                    continue;
                String fk = fce.getKey();
                if (row.getFacetColumns().get(fk).size() == 0) {
                    // When there are no values on this facet column, filter
                    // unless all is checked on the column
                    filtered = Boolean.FALSE.equals(all.get(fk));
                } else {
                    // Filter unless there is at least one match for one
                    // checked facet on this row
                    boolean hasOne = false;
                    for (String fci : row.getFacetColumns().get(fk))
                        if (!hasOne && getFacetState(fci, fk).isChecked())
                            hasOne = true;
                    assert !filtered : "filtered state changed";
                    filtered = !hasOne;
                }
            }
            if (!filtered)
                // count all cells in all non-filtered rows
                for (Map.Entry<String, List<String>> fce : row.getFacetColumns()
                        .entrySet())
                    for (String fci : fce.getValue())
                        getFacetState(fci, fce.getKey()).incrementCount();
        }
        if (LOG.isDebugEnabled())
            LOG.debug("Pruned facet entry {}", pruned.getCount());
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
        if ("All".equals(key))
            for (FacetState fs : fsm.values())
                fs.setChecked(true);
        else {
            FacetState fs = null;

            // Determine if all facets are checked prior to recording
            // This state corresponds to the "All" checkbox being
            // checked on the browser, in which case no other boxes
            // in the group appear to be checked.
            boolean all = true;
            for (FacetState ifs : fsm.values())
                if (!all)
                    continue;
                else
                    all = all && ifs.isChecked();

            // when all checked, clear all but the clicked box
            if (all) {
                for (Map.Entry<String, FacetState> fe : fsm.entrySet()) {
                    fe.getValue().setChecked(key.equals(fe.getKey()));
                    if (fe.getValue().isChecked()) {
                        assert fs == null : fs + " " + fe;
                        fs = fe.getValue();
                    }
                }
                assert fs != null : fcol + " " + key;
                oneClick = true;
            } else {
                // when all are not checked, toggle the clicked box
                fs = facetState.get(fcol).get(key);
                assert fs != null : fcol + " " + key;
                // Update checked status of facet
                fs.setChecked(!fs.isChecked());
                if (!fs.isChecked())
                    oneClick = true;
                // unchecking the last box in the group, check all
                boolean none = true;
                for (FacetState ifs : facetState.get(fcol).values())
                    if (!none)
                        continue;
                    else
                        none = !ifs.isChecked();
                if (none)
                    for (FacetState tfs : fsm.values())
                        tfs.setChecked(true);
            }
        }
        if (oneClick)
            updateFacetCounts();
    }

    private void facetClickAll() {
        LOG.debug("Facet click all");
        if (oneClick) {
            for (Map.Entry<String, Map<String, FacetState>> fse : facetState
                    .entrySet())
                for (FacetState fs : fse.getValue().values())
                    fs.setChecked(true);
            updateFacetCounts();
            oneClick = false;
        }
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
                    return current.getFacetColumns().get(CourseSearchController.FACET_COLUMNS_REVERSE
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
                        + current.getFacetColumns().get(CourseSearchController.FACET_COLUMNS_REVERSE
                                .get(j)) + ", removed=" + removed
                        + ", searchString=" + searchString
                        + ", searchPattern=" + searchPattern + ", j=" + j
                        + " (" + CourseSearchController.FACET_COLUMNS_REVERSE.get(j) + ")]";
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
