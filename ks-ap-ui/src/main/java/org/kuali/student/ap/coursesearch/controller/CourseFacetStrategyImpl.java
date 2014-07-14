package org.kuali.student.ap.coursesearch.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.ap.coursesearch.CourseFacetStrategy;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.FacetKeyValue;
import org.kuali.student.ap.coursesearch.FacetState;
import org.kuali.student.ap.coursesearch.SearchInfo;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation class for facet strategy
 */
public class CourseFacetStrategyImpl implements CourseFacetStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(CourseFacetStrategyImpl.class);


    /**
     * Keyed mapping of DataTables search column order by facet id. This column
     * order is fully internal to this controller class, and is used to tie
     * faceted searches columns on the search item.
     */
    private Map<String, Integer> facetColumns;

    /**
     * Ordered list of facet column ids by DataTables column order. This column
     * order is fully internal to this controller class, and is used to tie
     * faceted searches columns on the search item.
     */
    private List<String> facetColumnsReverse;

    @Override
    public Map<String, Integer> getFacetColumns() {
        if (facetColumns == null) {
            Set<String> facetKeys = KsapFrameworkServiceLocator
                    .getCourseSearchStrategy().getFacetSort().keySet();
            Map<String, Integer> m = new java.util.HashMap<String, Integer>(
                    facetKeys.size());
            List<String> l = new ArrayList<String>(facetKeys.size());
            for (String fk : facetKeys) {
                m.put(fk, l.size());
                l.add(fk);
            }
            facetColumns = Collections.synchronizedMap(Collections
                    .unmodifiableMap(m));
        }
        return facetColumns;
    }

    @Override
    public List<String> getFacetColumnsReversed() {
        if (facetColumnsReverse == null) {
            Set<String> facetKeys = KsapFrameworkServiceLocator
                    .getCourseSearchStrategy().getFacetSort().keySet();
            Map<String, Integer> m = new java.util.HashMap<String, Integer>(
                    facetKeys.size());
            List<String> l = new ArrayList<String>(facetKeys.size());
            for (String fk : facetKeys) {
                m.put(fk, l.size());
                l.add(fk);
            }
            facetColumnsReverse = Collections.synchronizedList(Collections
                    .unmodifiableList(l));
        }
        return facetColumnsReverse;
    }

    @Override
    public String writeFacetToJson(CourseSearchForm form, Map<String, Map<String, FacetState>> facetStateMap) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Create the oFacets object used by ksap.search.js
        ObjectNode oFacets = mapper.createObjectNode();
        oFacets.put("sQuery", form.getSearchQuery());
        oFacets.put("sTerm", form.getSearchTerm());
        ObjectNode oSearchColumn = oFacets.putObject("oSearchColumn");
        for (Map.Entry<String, Integer> fce : getFacetColumns().entrySet())
            oSearchColumn.put(fce.getKey(), fce.getValue());
        ObjectNode oFacetState = oFacets.putObject("oFacetState");
        for (Map.Entry<String, Map<String, FacetState>> row : facetStateMap.entrySet()) {
            ObjectNode ofm = oFacetState.putObject(row.getKey());
            for (Map.Entry<String, FacetState> fse : row.getValue().entrySet()) {
                ObjectNode ofs = ofm.putObject(fse.getKey());
                ofs.put("key", fse.getValue().getValue().getKey());
                ofs.put("value", fse.getValue().getValue().getValue());
                ofs.put("checked", fse.getValue().isChecked());
                ofs.put("count", fse.getValue().getCount());
                if (fse.getValue().getDescription() != null)
                    ofs.put("description", fse.getValue().getDescription());
            }
        }

        // Write json string
        return mapper.writeValueAsString(oFacets);
    }

    /**
     * Walk through the facet columns in the search results and update
     * counts based on the current facet state. The method assumes that the
     * facet state structure is fully formed but that the click state has
     * change since the last time counts were calculated.
     */
    @Override
    public void updateFacetCounts(List<SearchInfo> searchResults, Map<String, Map<String, FacetState>> facetState, Map<String, List<String>> facetCols) {
        // Reset the count on all facet state leaf nodes to 0
        Map<String, Boolean> all = new java.util.HashMap<String, Boolean>(
                facetState.size());
        for (Map.Entry<String, Map<String, FacetState>> fse : facetState
                .entrySet())
            for (FacetState fs : fse.getValue().values()) {
                fs.resetCount();
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
                        if (!hasOne && getFacetState(fci, fk, facetState).isChecked())
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
                        getFacetState(fci, fce.getKey(), facetState).incrementCount();
        }
    }

    /**
     * Get the facet state associated with a specific facet column value.
     *
     * @param key
     *            The facet column value to use as the facet key.
     * @param facetId
     *            The facet id.
     * @param facetState
     *
     * @return The facet state associated with the indicated column value.
     */
    private FacetState getFacetState(String key, String facetId, Map<String, Map<String, FacetState>> facetState) {
        Map<String, FacetState> fm = facetState.get(facetId);
        FacetState fs = fm.get(key);
        if (fs == null) {
            FacetState pruned = new FacetStateImpl(new FacetKeyValue("", ""));
            pruned.setChecked(false);
            return pruned;
        }
        else
            return fs;
    }

    /**
     * Update checked state on all facets following a click event from the
     * browser.
     *
     * @param key
     *            The facet key clicked. May be 'All'.
     * @param fcol
     *            The facet column the click is related to.
     * @param fsm
     *            The facet state map
     */
    @Override
    public boolean facetClick(String key, String fcol, Map<String, FacetState> fsm, boolean oneClick) {
        LOG.debug("Facet click {} {}", key, fcol);
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
                fs = fsm.get(key);
                assert fs != null : fcol + " " + key;
                // Update checked status of facet
                fs.setChecked(!fs.isChecked());
                if (!fs.isChecked())
                    oneClick = true;
                // unchecking the last box in the group, check all
                boolean none = true;
                for (FacetState ifs : fsm.values())
                    if (!none)
                        continue;
                    else
                        none = !ifs.isChecked();
                if (none)
                    for (FacetState tfs : fsm.values())
                        tfs.setChecked(true);
            }
        }
        return oneClick;
    }

    @Override
    public boolean facetClickAll(boolean oneClick, Map<String, Map<String, FacetState>> facetState) {
        LOG.debug("Facet click all");
        boolean retOneClick = true;
        if (oneClick) {
            for (Map.Entry<String, Map<String, FacetState>> fse : facetState
                    .entrySet())
                for (FacetState fs : fse.getValue().values())
                    fs.setChecked(true);
            retOneClick = false;
        }
        return retOneClick;
    }

    @Override
    public Map<String, Map<String, FacetState>> createInitialFacetStateMap(CourseSearchStrategy searcher, Map<String, List<String>> facetColumns, List<SearchInfo> searchResults) {
        Map<String, Map<String, FacetState>> facetStateMap = new java.util.HashMap<String, Map<String, FacetState>>(
                facetColumns.size());
        for (String fk : facetColumns.keySet())
            facetStateMap.put(fk,
                    new java.util.HashMap<String, FacetState>());
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
                            FacetStateImpl fs = (FacetStateImpl)fm.get(facetKey);
                            if (fs == null) {
                                fm.put(facetKey, fs = new FacetStateImpl(fv));
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
        return facetStateMap;
    }

    @Override
    public Map<String, Map<String, FacetState>> processFacetStateMap(CourseSearchStrategy searcher, Map<String, Map<String, FacetState>> facetStateMap, Map<String, List<String>> facetColumns) {
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
}
