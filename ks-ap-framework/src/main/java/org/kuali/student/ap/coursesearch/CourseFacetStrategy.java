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

package org.kuali.student.ap.coursesearch;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Strategy interface for specifying course search facet behavior.
 */
public interface CourseFacetStrategy {

    /**
     * Creates a mapping of the facet group names and the index they are in when ordered.
     *
     * @return A map relating facet group names and ordering position.
     */
    Map<String, Integer> getFacetColumns();

    /**
     * Returns a list of the facet group names in order
     *
     * @return A ordered list of facet group names.
     */
    List<String> getFacetColumnsReversed();

    /**
     * Retrieves a static map of facets to their related sort algorithm.
     *
     * @return  Static map of facet id to comparator
     */
    Map<String, Comparator<String>> getFacetSort();

    /**
     * Updates the number of results shown for each facet value based on the current facet filtering
     *
     * @param searchResults - List of the search results
     * @param facetState - Map of the current facet information
     * @param facetCols  - Map of the facet available for each search item.
     */
    void updateFacetCounts(List<SearchInfo> searchResults, Map<String, Map<String, FacetState>> facetState, Map<String, List<String>> facetCols);

    /**
     * Update checked state on all facets following a click event from the
     * browser.
     *
     * @param fclick - The facet key clicked. May be 'All'.
     * @param fcol - The facet column the click is related to.
     * @param facetStateMap - The facet state map
     * @param oneclick - Current state if a facet value has been set to false
     */
    boolean facetClick(String fclick, String fcol, Map<String, FacetState> facetStateMap, boolean oneclick);

    /**
     * If a facet has already be clicked reset all facet values to true
     *
     * @param oneClick - Current state if a facet value has been set to false
     * @param facetStateMap - The facet state map
     * @return True if no changes were made
     */
    boolean facetClickAll(boolean oneClick, Map<String, Map<String, FacetState>> facetStateMap);

    /**
     * Creates the map of initial facet states values for the course search results.
     *
     * @param facetColumns - Map of the facet available for each search item.
     * @param searchResults - List of results returned in the search
     * @return A map to each possible facet value and its initial state in the search results
     */
    Map<String, Map<String, FacetState>> createInitialFacetStateMap(Map<String, List<String>> facetColumns, List<SearchInfo> searchResults);

    /**
     * Refines and updates the facet states
     *
     * @param facetStateMap - Map of the current states of all facet value.
     * @param facetColumns - Map of the facet available for each search item.
     * @return - Refined map of the facet states of all facet values
     */
    Map<String, Map<String, FacetState>> processFacetStateMap(Map<String, Map<String, FacetState>> facetStateMap, Map<String, List<String>> facetColumns);
}
