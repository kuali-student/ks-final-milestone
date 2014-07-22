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
package org.kuali.student.ap.coursesearch.service.impl;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.FacetState;
import org.kuali.student.ap.coursesearch.SearchInfo;
import org.kuali.student.ap.coursesearch.controller.DataTablesInputs;
import org.kuali.student.ap.coursesearch.controller.FormKey;
import org.kuali.student.ap.coursesearch.controller.SessionSearchInfo;
import org.kuali.student.ap.coursesearch.service.CourseSearchViewHelperService;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *  ViewHelper class used to populate the data on the Course Search page (CourseSearchUI.xml)
 *
 *  Default implementation of the view helper for KSAP
 */
public class CourseSearchViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseSearchViewHelperService{
    private static final Logger LOG = LoggerFactory.getLogger(CourseSearchViewHelperServiceImpl.class);

    /**
     * HTTP session attribute key for holding recent search results.
     */
    private static final String RESULTS_ATTR = CourseSearchViewHelperServiceImpl.class
            .getName() + ".results";

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseSearchViewHelperService#getSearchResults(org.kuali.student.ap.coursesearch.CourseSearchForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public SessionSearchInfo getSearchResults(final CourseSearchForm form, final HttpServletRequest request) {
        SessionSearchInfo table = getSearchResults(new FormKey(form),
                new Callable<SessionSearchInfo>() {
                    @Override
                    public SessionSearchInfo call() throws Exception {
                        return new SessionSearchInfo(request, form);
                    }
                }, request);

        //Loading status information here to put it outside the cache since status can change often
        List<SearchInfo> searchResults = table.getSearchResults();
        List<CourseSearchItem> courseSearchItems = new ArrayList<CourseSearchItem>();
        for(SearchInfo searchInfo : searchResults){
            courseSearchItems.add(searchInfo.getItem());
        }
        KsapFrameworkServiceLocator.getCourseSearchStrategy().loadPlanStatus(form.getSessionId(),KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId(),courseSearchItems);

        return table;
    }

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
     * @param key - Form key storing identification data on the search
     * @param search -  Callable object for executing the search
     * @param request - The incoming request.
     * @return Session-bound search results for the request.
     */
    private SessionSearchInfo getSearchResults(FormKey key, Callable<SessionSearchInfo> search, HttpServletRequest request) {
        if (key.isSavedCourses()) // don't cache saved course searches
            try {
                return search.call();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalStateException("search failed", e);
            }

        // Check HTTP session for cached search results
        @SuppressWarnings("unchecked")
        Map<FormKey, SessionSearchInfo> results = (Map<FormKey, SessionSearchInfo>) request
                .getSession().getAttribute(RESULTS_ATTR);
        if (results == null)
            request.getSession()
                    .setAttribute(
                            RESULTS_ATTR,
                            results = Collections
                                    .synchronizedMap(new java.util.LinkedHashMap<FormKey, SessionSearchInfo>()));
        SessionSearchInfo table = null;

        // Synchronize on the result table to constrain sessions to one back-end search at a time
        synchronized (results) {

            // dump search results in excess of 1
            while (results.size() > 1) {
                Iterator<?> ei = results.entrySet().iterator();
                ei.next();
                ei.remove();
            }
            try {
                results.put(
                        key, // The back-end search happens here --------V
                        (table = results.remove(key)) == null ? table = search
                                .call() : table);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalStateException("search failed", e);
            }
        }
        return table;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseSearchViewHelperService#getSearchResultsJson(org.kuali.student.ap.coursesearch.CourseSearchForm, org.kuali.student.ap.coursesearch.controller.SessionSearchInfo, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public JsonObjectBuilder getSearchResultsJson(CourseSearchForm form, SessionSearchInfo table, HttpServletRequest request) {

        // Parse incoming jQuery datatables inputs
        final DataTablesInputs dataTablesInputs = new DataTablesInputs(request);

        JsonObjectBuilder json = Json.createObjectBuilder();

        // Validate search results
        if (table.getSearchResults() != null && !table.getSearchResults().isEmpty()) {

            // Set up Limited Exceeded message
            String maxCountProp = ConfigContext.getCurrentContextConfig()
                    .getProperty("ksap.search.results.max");
            int maxCount = maxCountProp != null && !"".equals(maxCountProp.trim()) ? Integer
                    .valueOf(maxCountProp) : CourseSearchStrategy.MAX_HITS;
            if(form.isLimitExceeded()){
                json.add("LimitExceeded", maxCount);
            }else{
                json.add("LimitExceeded", 0);
            }

            SearchInfo firstRow = table.getSearchResults().iterator().next();
            // Validate incoming jQuery datatables inputs
            assert table != null;
            assert table.getSearchResults().isEmpty()
                    || dataTablesInputs.getiColumns() >= firstRow.getItem()
                    .getSearchColumns().length : firstRow.getItem()
                    .getSearchColumns().length
                    + " > "
                    + dataTablesInputs.getiColumns();
            assert table.getSearchResults().isEmpty()
                    || dataTablesInputs.getiColumns() >= firstRow.getSortColumns().length : firstRow.getSortColumns().length
                    + " > " + dataTablesInputs.getiColumns();
            assert table.getSearchResults().isEmpty()
                    || dataTablesInputs.getiColumns() >= firstRow.getFacetColumns()
                    .size() : firstRow.getFacetColumns().size() + " > "
                    + dataTablesInputs.getiColumns();
            assert table.getSearchResults().isEmpty()
                    || dataTablesInputs.getiColumns() == firstRow.getFacetColumns()
                    .size()
                    || dataTablesInputs.getiColumns() == firstRow.getSortColumns().length
                    || dataTablesInputs.getiColumns() == firstRow.getItem()
                    .getSearchColumns().length : "Max("
                    + firstRow.getFacetColumns().size() + ","
                    + firstRow.getSortColumns().length + ","
                    + firstRow.getItem().getSearchColumns().length + ") != "
                    + dataTablesInputs.getiColumns();
        }

		/*DataTables search filter is tied to facet click state on the front end,
		 but is only loosely coupled on the server side.*/
        List<SearchInfo> filteredResults = table
                .getFilteredResults(dataTablesInputs);

        // Render JSON response for DataTables
        json.add("iTotalRecords", table.getSearchResults().size());
        json.add("iTotalDisplayRecords", filteredResults.size());
        json.add("sEcho", Integer.toString(dataTablesInputs.getsEcho()));
        JsonArrayBuilder aaData = Json.createArrayBuilder();
        int rsize = Math.min(filteredResults.size(),
                dataTablesInputs.getiDisplayLength());
        for (int i = 0; i < rsize; i++) {
            int resultsIndex = dataTablesInputs.getiDisplayStart() + i;
            if (resultsIndex >= filteredResults.size())
                break;
            JsonArrayBuilder cs = Json.createArrayBuilder();
            CourseSearchItem item = filteredResults.get(resultsIndex).getItem();
            String[] scol = item.getSearchColumns();
            for (String col : scol)
                cs.add(col);
            for (int j = scol.length; j < dataTablesInputs.getiColumns(); j++)
                cs.add((String) null);
            aaData.add(cs);
        }
        json.add("aaData", aaData);

        return json;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseSearchViewHelperService#getFacetsJson(org.kuali.student.ap.coursesearch.CourseSearchForm, java.util.Map)
     */
    @Override
    public JsonObjectBuilder getFacetsJson(CourseSearchForm form, Map<String, Map<String, FacetState>> facetState) {

        // Create the oFacets object used by ksap.search.js
        JsonObjectBuilder oFacets = Json.createObjectBuilder();
        oFacets.add("sQuery", form.getSearchQuery());
        oFacets.add("sTerm", form.getSearchTerm());

        JsonObjectBuilder oSearchColumn = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> fce : KsapFrameworkServiceLocator.getCourseFacetStrategy().getFacetColumns().entrySet())
            oSearchColumn.add(fce.getKey(), fce.getValue());
        oFacets.add("oSearchColumn",oSearchColumn);


        JsonObjectBuilder oFacetState = Json.createObjectBuilder();
        for (Map.Entry<String, Map<String, FacetState>> row : facetState.entrySet()) {
            JsonObjectBuilder ofm = Json.createObjectBuilder();
            for (Map.Entry<String, FacetState> fse : row.getValue().entrySet()) {
                JsonObjectBuilder ofs = Json.createObjectBuilder();
                ofs.add("key", fse.getValue().getValue().getKey());
                ofs.add("value", fse.getValue().getValue().getValue());
                ofs.add("checked", fse.getValue().isChecked());
                ofs.add("count", fse.getValue().getCount());
                if (fse.getValue().getDescription() != null)
                    ofs.add("description", fse.getValue().getDescription());
                ofm.add(fse.getKey(),ofs);
            }
            oFacetState.add(row.getKey(),ofm);
        }
        oFacets.add("oFacetState",oFacetState);

        // Write json string
        return oFacets;
    }
}
