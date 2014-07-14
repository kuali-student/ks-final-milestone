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
package org.kuali.student.ap.coursesearch.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.FacetState;
import org.kuali.student.ap.coursesearch.controller.SessionSearchInfo;

import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  ViewHelper class used to populate the data on the Course Search page (CourseSearchUI.xml)
 */
public interface CourseSearchViewHelperService extends ViewHelperService {

    /**
     * Synchronously retrieve session bound search results for an incoming
     * request.
     *
     * <p>
     * This method ensures that only one back-end search per HTTP session is
     * running at the same time for the same set of criteria. This is important
     * since the browser fires requests for the facet table and the search table
     * independently, so this consideration constrains those two requests to
     * operating synchronously on the same set of results.
     * </p>
     *
     * @param request - The incoming request.
     * @return Session-bound search results for the request.
     */
    public SessionSearchInfo getSearchResults(final CourseSearchForm form, final HttpServletRequest request);

    /**
     * Creates the Json Object summarizing the search results needed for displaying to the datatable
     *
     * @param form - Form containing the search information
     * @param table - Results of the search
     * @param request - The incoming request.
     * @return A json object for the search results
     */
    public JsonObjectBuilder getSearchResultsJson(CourseSearchForm form, SessionSearchInfo table, HttpServletRequest request);

    /**
     * Creates the json object summarizing the facets needed for displaying to the facet column
     *
     * @param form - Form containing the search information
     * @param facetState - Current state of the facets
     * @return A json object for the search facets
     */
    public JsonObjectBuilder getFacetsJson(CourseSearchForm form, Map<String, Map<String, FacetState>> facetState);

}
