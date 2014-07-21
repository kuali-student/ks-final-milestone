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

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.ap.coursesearch.CourseFacetStrategy;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.FacetKeyValue;
import org.kuali.student.ap.coursesearch.FacetState;
import org.kuali.student.ap.coursesearch.SearchInfo;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Map<String, Comparator<String>> facet_sort;
    private Map<String, Integer> facetColumns;
    private List<String> facetColumnsReverse;

    /**
     * Keyed mapping of DataTables search column order by facet id. This column
     * order is fully internal to this controller class, and is used to tie
     * faceted searches columns on the search item.
     *
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#getFacetColumns()
     */
    @Override
    public Map<String, Integer> getFacetColumns() {
        if (facetColumns == null) {
            Set<String> facetKeys = getFacetSort().keySet();
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

    /**
     * Ordered list of facet column ids by DataTables column order. This column
     * order is fully internal to this controller class, and is used to tie
     * faceted searches columns on the search item.
     *
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#getFacetColumnsReversed()
     */
    @Override
    public List<String> getFacetColumnsReversed() {
        if (facetColumnsReverse == null) {
            Set<String> facetKeys = getFacetSort().keySet();
            List<String> l = new ArrayList<String>(facetKeys.size());
            for (String fk : facetKeys) {
                l.add(fk);
            }
            facetColumnsReverse = Collections.synchronizedList(Collections
                    .unmodifiableList(l));
        }
        return facetColumnsReverse;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#getFacetSort()
     */
    @Override
    public Map<String, Comparator<String>> getFacetSort() {
        if(facet_sort == null){
            // Related to CourseSearchUI.xml definitions
            Map<String, Comparator<String>> l = new java.util.LinkedHashMap<String, Comparator<String>>(
                    5);
            l.put("facet_quarter", KsapHelperUtil.TERMS);
            l.put("facet_genedureq", KsapHelperUtil.ALPHA);
            l.put("facet_credits", CreditsFormatter.CREDIT);
            l.put("facet_level", KsapHelperUtil.NUMERIC);
            l.put("facet_curriculum", KsapHelperUtil.ALPHA);
            facet_sort = Collections
                    .unmodifiableMap(Collections.synchronizedMap(l));
        }
        return facet_sort;
    }

    /**
     * Walk through the facet columns in the search results and update
     * counts based on the current facet state. The method assumes that the
     * facet state structure is fully formed but that the click state has
     * change since the last time counts were calculated.
     *
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#updateFacetCounts(java.util.List, java.util.Map, java.util.Map)
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
     * @param key - The facet column value to use as the facet key.
     * @param facetId - The facet id.
     * @param facetState - Map of the current facet information
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
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#facetClick(String, String, java.util.Map, boolean)
     */
    @Override
    public boolean facetClick(String fclick, String fcol, Map<String, FacetState> facetStateMap, boolean oneClick) {
        LOG.debug("Facet click {} {}", fclick, fcol);
        if ("All".equals(fclick))
            for (FacetState fs : facetStateMap.values())
                fs.setChecked(true);
        else {
            FacetState fs = null;

            // Determine if all facets are checked prior to recording
            // This state corresponds to the "All" checkbox being
            // checked on the browser, in which case no other boxes
            // in the group appear to be checked.
            boolean all = true;
            for (FacetState ifs : facetStateMap.values())
                if (!all)
                    continue;
                else
                    all = all && ifs.isChecked();

            // when all checked, clear all but the clicked box
            if (all) {
                for (Map.Entry<String, FacetState> fe : facetStateMap.entrySet()) {
                    fe.getValue().setChecked(fclick.equals(fe.getKey()));
                    if (fe.getValue().isChecked()) {
                        assert fs == null : fs + " " + fe;
                        fs = fe.getValue();
                    }
                }
                assert fs != null : fcol + " " + fclick;
                oneClick = true;
            } else {
                // when all are not checked, toggle the clicked box
                fs = facetStateMap.get(fclick);
                assert fs != null : fcol + " " + fclick;
                // Update checked status of facet
                fs.setChecked(!fs.isChecked());
                if (!fs.isChecked())
                    oneClick = true;
                // unchecking the last box in the group, check all
                boolean none = true;
                for (FacetState ifs : facetStateMap.values())
                    if (!none)
                        continue;
                    else
                        none = !ifs.isChecked();
                if (none)
                    for (FacetState tfs : facetStateMap.values())
                        tfs.setChecked(true);
            }
        }
        return oneClick;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#facetClickAll(boolean, java.util.Map)
     */
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

    /**
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#createInitialFacetStateMap(java.util.Map, java.util.List)
     */
    @Override
    public Map<String, Map<String, FacetState>> createInitialFacetStateMap(Map<String, List<String>> facetColumns, List<SearchInfo> searchResults) {
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
                List<KeyValue> fcr;
                if (row.getItem() != null
                        && (fcr = row.getItem().getFacetColumns().get(
                        fce.getKey())) != null)
                    for (KeyValue keyValue : fcr){
                            String facetKey = keyValue.getKey();
                            FacetStateImpl fs = (FacetStateImpl)fm.get(facetKey);
                            if (fs == null) {
                                fm.put(facetKey, fs = new FacetStateImpl(keyValue));
                                if (fce.getKey().equals("facet_genedureq")) {
                                    fs.setDescription(KsapFrameworkServiceLocator.getCourseSearchStrategy().getGenEdMap().get(keyValue.getKey()));
                                } else if (fce.getKey().equals("facet_curriculum")) {
                                    fs.setDescription(KsapFrameworkServiceLocator.getCourseSearchStrategy().getCurriculumMap(null).get(keyValue.getKey()));
                                }
                            }
                            fs.incrementCount();
                        }
            }

        }
        return facetStateMap;
    }

    /**
     * Sets up and sorts for trending
     * Truncate facets to max 50 entries
     * Sort facets
     * Seal map for sync
     *
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#processFacetStateMap(java.util.Map, java.util.Map)
     */
    @Override
    public Map<String, Map<String, FacetState>> processFacetStateMap(Map<String, Map<String, FacetState>> facetStateMap, Map<String, List<String>> facetColumns) {
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
                Arrays.sort(sv, getFacetSort().get(fk));
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
