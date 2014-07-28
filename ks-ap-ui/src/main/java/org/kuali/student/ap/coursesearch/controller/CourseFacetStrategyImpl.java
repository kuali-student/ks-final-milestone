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
    private List<String> sortedFacetColumnsNames;

    /**
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#getFacetColumns()
     */
    @Override
    public Map<String, Integer> getFacetColumns() {
        if (facetColumns == null) {
            Set<String> facetKeys = getFacetSort().keySet();
            Map<String, Integer> newMap = new java.util.HashMap<String, Integer>(
                    facetKeys.size());
            int index = 0;
            for (String fk : facetKeys) {
                newMap.put(fk, index);
                index++;
            }
            facetColumns = Collections.synchronizedMap(Collections
                    .unmodifiableMap(newMap));
        }
        return facetColumns;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#getSortedFacetColumnNames()
     */
    @Override
    public List<String> getSortedFacetColumnNames() {
        if (sortedFacetColumnsNames == null) {
            Set<String> facetKeys = getFacetSort().keySet();
            List<String> l = new ArrayList<String>(facetKeys.size());
            for (String fk : facetKeys) {
                l.add(fk);
            }
            sortedFacetColumnsNames = Collections.synchronizedList(Collections
                    .unmodifiableList(l));
        }
        return sortedFacetColumnsNames;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#getFacetSort()
     */
    @Override
    public Map<String, Comparator<String>> getFacetSort() {
        if(facet_sort == null){
            // Related to CourseSearchUI.xml definitions
            Map<String, Comparator<String>> newMap = new java.util.LinkedHashMap<String, Comparator<String>>(
                    5);
            newMap.put("facet_quarter", KsapHelperUtil.TERMS);
            newMap.put("facet_genedureq", KsapHelperUtil.ALPHA);
            newMap.put("facet_credits", CreditsFormatter.CREDIT);
            newMap.put("facet_level", KsapHelperUtil.NUMERIC);
            newMap.put("facet_curriculum", KsapHelperUtil.ALPHA);
            facet_sort = Collections
                    .unmodifiableMap(Collections.synchronizedMap(newMap));
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
        for (Map.Entry<String, Map<String, FacetState>> FacetStateEntries : facetState
                .entrySet())
            for (FacetState facetStateEntry : FacetStateEntries.getValue().values()) {
                facetStateEntry.resetCount();
                if (!Boolean.FALSE.equals(all.get(FacetStateEntries.getKey())))
                    all.put(FacetStateEntries.getKey(), facetStateEntry.isChecked());
            }

        // Iterate search results
        for (SearchInfo row : searchResults) {

            // Validate facet column count matches facet table size
            assert row.getFacetColumns() != null|| row.getFacetColumns().size() == facetCols.size() :
                    row.getFacetColumns() == null ? "null" : row.getFacetColumns().size() + " != " + facetCols.size();

            // identify filtered rows before counting
            boolean filtered = false;
            for (Map.Entry<String, List<String>> facetColumnEntry : facetCols.entrySet()) {
                if (filtered){
                    continue;
                }

                String facetKey = facetColumnEntry.getKey();
                if (row.getFacetColumns().get(facetKey).size() == 0) {
                    // When there are no values on this facet column, filter unless all is checked on the column
                    filtered = Boolean.FALSE.equals(all.get(facetKey));
                } else {
                    // Filter unless there is at least one match for one checked facet on this row
                    boolean hasOne = false;
                    for (String facetValue : row.getFacetColumns().get(facetKey)){
                        if (!hasOne && getFacetState(facetValue, facetKey, facetState).isChecked()) {
                            hasOne = true;
                        }
                    }
                    assert !filtered : "filtered state changed";
                    filtered = !hasOne;
                }
            }

            if (!filtered){
                // count all cells in all non-filtered rows
                for (Map.Entry<String, List<String>> facetColumnEntry : row.getFacetColumns().entrySet()){
                    for (String facetValue : facetColumnEntry.getValue()){
                        getFacetState(facetValue, facetColumnEntry.getKey(), facetState).incrementCount();
                    }
                }
            }
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
        Map<String, FacetState> facetStateMap = facetState.get(facetId);
        FacetState facetStateEntry = facetStateMap.get(key);
        if (facetStateEntry == null) {
            FacetState pruned = new FacetStateImpl(new FacetKeyValue("", ""));
            pruned.setChecked(false);
            return pruned;
        }
        else{
            return facetStateEntry;
        }
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#facetClick(String, String, java.util.Map, boolean)
     */
    @Override
    public boolean facetClick(String fclick, String fcol, Map<String, FacetState> facetStateMap, boolean oneClick) {
        LOG.debug("Facet click {} {}", fclick, fcol);
        if ("All".equals(fclick)){
            for (FacetState facetStateEntry : facetStateMap.values()){
                facetStateEntry.setChecked(true);
            }
        } else {
            FacetState facetStateEntry = null;

            // Determine if all facets are checked prior to recording
            // This state corresponds to the "All" checkbox being checked on the browser, in which case no other boxes
            // in the group appear to be checked.
            boolean all = true;
            for (FacetState facetState : facetStateMap.values()){
                if (!all){
                    continue;
                } else{
                    all = all && facetState.isChecked();
                }
            }

            // when all checked, clear all but the clicked box
            if (all) {
                for (Map.Entry<String, FacetState> facetEntry : facetStateMap.entrySet()) {
                    facetEntry.getValue().setChecked(fclick.equals(facetEntry.getKey()));
                    if (facetEntry.getValue().isChecked()) {
                        assert facetStateEntry == null : facetStateEntry + " " + facetEntry;
                        facetStateEntry = facetEntry.getValue();
                    }
                }
                assert facetStateEntry != null : fcol + " " + fclick;
                oneClick = true;
            } else {

                // when all are not checked, toggle the clicked box
                facetStateEntry = facetStateMap.get(fclick);
                assert facetStateEntry != null : fcol + " " + fclick;

                // Update checked status of facet
                facetStateEntry.setChecked(!facetStateEntry.isChecked());
                if (!facetStateEntry.isChecked()){
                    oneClick = true;
                }

                // unchecking the last box in the group, check all
                boolean none = true;
                for (FacetState facetState : facetStateMap.values()){
                    if (!none){
                        continue;
                    }else{
                        none = !facetState.isChecked();
                    }
                }
                if (none){
                    for (FacetState facetState : facetStateMap.values()){
                        facetState.setChecked(true);
                    }
                }
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
            for (Map.Entry<String, Map<String, FacetState>> facetStateEntries : facetState
                    .entrySet())
                for (FacetState facetStateEntry : facetStateEntries.getValue().values())
                    facetStateEntry.setChecked(true);
            retOneClick = false;
        }
        return retOneClick;
    }

    /**
     * Create Map of structure (Facet Column, (Facet Value, Facet State))
     * Facet columns are provided
     * Facet values are discovered in the search results
     * Facet states are calculated from search results and default settings
     *
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#createInitialFacetStateMap(java.util.Map, java.util.List)
     */
    @Override
    public Map<String, Map<String, FacetState>> createInitialFacetStateMap(Map<String, List<String>> facetColumns,
                                                                           List<SearchInfo> searchResults) {
        Map<String, Map<String, FacetState>> facetStateMap = new java.util.HashMap<String, Map<String, FacetState>>(facetColumns.size());

        // Set first layer as the facet columns
        for (String facetKey : facetColumns.keySet()){
            facetStateMap.put(facetKey, new java.util.HashMap<String, FacetState>());
        }

        // Generate initial counts, unabridged and unordered
        for (SearchInfo row : searchResults) {

            // Validate that the number of facet columns is uniform across all search rows
            assert row.getFacetColumns() != null || row.getFacetColumns().size() == facetColumns.size() :
                    row.getFacetColumns() == null ? "null" : row.getFacetColumns().size() + " != "
                            + facetColumns.size();

            // Update facet counts for all columns, creating pre-checked state nodes as needed
            for (Map.Entry<String, Map<String, FacetState>> facetStateEntry : facetStateMap
                    .entrySet()) {
                Map<String, FacetState> facetMap = facetStateEntry.getValue();
                if(row.getItem() != null){
                    List<KeyValue> facetStateRow = row.getItem().getFacetColumns().get( facetStateEntry.getKey());
                    if ( facetStateRow != null){
                        for (KeyValue keyValue : facetStateRow){
                            String facetKey = keyValue.getKey();
                            FacetStateImpl facetState = (FacetStateImpl)facetMap.get(facetKey);
                            if (facetState == null) {
                                facetMap.put(facetKey, facetState = new FacetStateImpl(keyValue));

                                // Set descriptions for selected facets
                                if (facetStateEntry.getKey().equals("facet_genedureq")) {
                                    facetState.setDescription(KsapFrameworkServiceLocator.getCourseSearchStrategy()
                                            .getGenEdMap().get(keyValue.getKey()));
                                } else if (facetStateEntry.getKey().equals("facet_curriculum")) {
                                    facetState.setDescription(KsapFrameworkServiceLocator.getCourseSearchStrategy()
                                            .getCurriculumMap(null).get(keyValue.getKey()));
                                }
                            }
                            facetState.incrementCount();
                        }
                    }
                }
            }
        }
        return facetStateMap;
    }

    /**
     * Truncate facets to max 50 entries
     * Sort facets
     * Seal and syncronize map
     *
     * @see org.kuali.student.ap.coursesearch.CourseFacetStrategy#processFacetStateMap(java.util.Map, java.util.Map)
     */
    @Override
    public Map<String, Map<String, FacetState>> processFacetStateMap(Map<String, Map<String, FacetState>> facetStateMap, Map<String, List<String>> facetColumns) {
        // Post-process based on calculated totals and per-column rules
        for (String facetKey : facetColumns.keySet()) {
            final Map<String, FacetState> facetStateMapEntry = facetStateMap.get(facetKey);
            Map<String, FacetState> newfacetStateMapEntry;
            if (facetStateMapEntry.isEmpty()){

                // Discard superfluous empty map references
                facetStateMap.put(facetKey, newfacetStateMapEntry = Collections.emptyMap());
            }
            else {
                // Establish facet key order
                String[] facetStateKeys = facetStateMapEntry.keySet().toArray(new String[facetStateMapEntry.size()]);

                // Truncate results to show only the 50 most relevant words in the facet
                facetStateKeys = Arrays.copyOf(facetStateKeys, Math.min(facetStateKeys.length, 50));

                // Sort according to strategy definitions
                Arrays.sort(facetStateKeys, getFacetSort().get(facetKey));
                newfacetStateMapEntry = new java.util.LinkedHashMap<String, FacetState>(
                        facetStateKeys.length);
                for (String key : facetStateKeys){
                    // Insert truncated facet keys in order
                    newfacetStateMapEntry.put(key, facetStateMapEntry.get(key));
                }

                // Seal the map for synchronized use
                facetStateMap.put(facetKey, Collections.synchronizedMap(Collections.unmodifiableMap(newfacetStateMapEntry)));
            }
        }
        return facetStateMap;
    }

    public void setFacetSort(Map<String,Comparator<String>> facetSort){
        facet_sort=facetSort;
    }
}
