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

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.dataobject.CourseSummaryDetails;
import org.kuali.student.ap.coursesearch.form.CourseSearchFormImpl;
import org.kuali.student.ap.coursesearch.service.impl.CourseDetailsInquiryHelperImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

@Controller
@RequestMapping(value = "/course/**")
public class CourseSearchController extends UifControllerBase {

	private static final Logger LOG = LoggerFactory.getLogger(CourseSearchController.class);

	/**
	 * HTTP session attribute key for holding recent search results.
	 */
	private static final String RESULTS_ATTR = CourseSearchController.class
			.getName() + ".results";

	/**
	 * Keyed mapping of DataTables search column order by facet id. This column
	 * order is fully internal to this controller class, and is used to tie
	 * faceted searches columns on the search item.
	 * 
	 * @see #FACET_COLUMNS_REVERSE
	 * @see #getFacetValues(javax.servlet.http.HttpServletResponse,
     *      org.kuali.student.ap.coursesearch.CourseSearchForm, javax.servlet.http.HttpServletRequest)
	 */
	private static final Map<String, Integer> FACET_COLUMNS;

	/**
	 * Ordered list of facet column ids by DataTables column order. This column
	 * order is fully internal to this controller class, and is used to tie
	 * faceted searches columns on the search item.
	 *
	 * @see #FACET_COLUMNS
     * @see #getFacetValues(javax.servlet.http.HttpServletResponse,
     *      org.kuali.student.ap.coursesearch.CourseSearchForm, javax.servlet.http.HttpServletRequest)
     */
	public static final List<String> FACET_COLUMNS_REVERSE;

	/**
	 * Initialize internal facet column order.
	 */
	static {
		Set<String> facetKeys = KsapFrameworkServiceLocator
				.getCourseSearchStrategy().getFacetSort().keySet();
		Map<String, Integer> m = new java.util.HashMap<String, Integer>(
				facetKeys.size());
		List<String> l = new ArrayList<String>(facetKeys.size());
		for (String fk : facetKeys) {
			m.put(fk, l.size());
			l.add(fk);
		}
		FACET_COLUMNS = Collections.synchronizedMap(Collections
				.unmodifiableMap(m));
		FACET_COLUMNS_REVERSE = Collections.synchronizedList(Collections
				.unmodifiableList(l));
	}

    private CourseSearchStrategy searcher = KsapFrameworkServiceLocator
			.getCourseSearchStrategy();

	private ObjectMapper mapper = new ObjectMapper();

	private CourseDetailsInquiryHelperImpl courseDetailsInquiryService;

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
	 * @param request
	 *            The incoming request.
	 * @return Session-bound search results for the request.
	 */
	private SessionSearchInfo getSearchResults(FormKey k,
			Callable<SessionSearchInfo> search, HttpServletRequest request) {
		if (k.isSavedCourses()) // don't cache saved course searches
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
						k, // The back-end search happens here --------V
						(table = results.remove(k)) == null ? table = search
								.call() : table);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalStateException("search failed", e);
			}
		}
		return table;
	}

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
        return (UifFormBase) searcher.createInitialSearchForm();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@ModelAttribute("KualiForm") UifFormBase form,
			HttpServletRequest request,
			HttpServletResponse response) {
		super.start(form, request, response);
		form.setViewId("CourseSearch-FormView");
		form.setView(super.getViewService()
				.getViewById("CourseSearch-FormView"));
		return getUIFModelAndView(form);
	}

	@RequestMapping(value = "/course/", method = RequestMethod.GET)
	public String doGet(@ModelAttribute("KualiForm") UifFormBase form,
			HttpServletRequest request,
			HttpServletResponse response) {
		return "redirect:/kr-krad/course";
	}

	@RequestMapping(params = "methodToCall=start")
	public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
			HttpServletRequest request,
			HttpServletResponse response) {
		super.start(form, request, response);
		return getUIFModelAndView(form);
	}

    /**
     * Execute a search on information supplied by the user
     *
     * @param form
     * @param response
     * @param request
     * @throws IOException
     */
	@RequestMapping(value = "/course/search")
	public void getJsonResponse(
			@ModelAttribute("KualiForm") final CourseSearchForm form,
			HttpServletResponse response, final HttpServletRequest request)
			throws IOException {

		// Parse incoming jQuery datatables inputs
		final DataTablesInputs dataTablesInputs = new DataTablesInputs(request);
		if (LOG.isDebugEnabled())
			LOG.debug(dataTablesInputs.toString());

		if (LOG.isDebugEnabled())
			LOG.debug("Search form: {}", form);

        // Run search and retrieve results in the table
		SessionSearchInfo table = getSearchResults(new FormKey(form),
				new Callable<SessionSearchInfo>() {
					@Override
					public SessionSearchInfo call() throws Exception {
						return new SessionSearchInfo(request, form);
					}
				}, request);
		if (table == null) {
			return;
		}

        ObjectNode json = mapper.createObjectNode();

        // Validate search results
		if (table.getSearchResults() != null && !table.getSearchResults().isEmpty()) {
            String maxCountProp = ConfigContext.getCurrentContextConfig()
                    .getProperty("ksap.search.results.max");
            int maxCount = maxCountProp != null && !"".equals(maxCountProp.trim()) ? Integer
                    .valueOf(maxCountProp) : CourseSearchStrategy.MAX_HITS;
            if(this.searcher.isLimitExceeded()){
                json.put("LimitExceeded", maxCount);
            }else{
                json.put("LimitExceeded", 0);
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
		json.put("iTotalRecords", table.getSearchResults().size());
		json.put("iTotalDisplayRecords", filteredResults.size());
		json.put("sEcho", Integer.toString(dataTablesInputs.getsEcho()));
		ArrayNode aaData = mapper.createArrayNode();
		int rsize = Math.min(filteredResults.size(),
				dataTablesInputs.getiDisplayLength());
		for (int i = 0; i < rsize; i++) {
			int resultsIndex = dataTablesInputs.getiDisplayStart() + i;
			if (resultsIndex >= filteredResults.size())
				break;
			ArrayNode cs = mapper.createArrayNode();
			CourseSearchItem item = filteredResults.get(resultsIndex).getItem();
			String[] scol = item.getSearchColumns();
			for (String col : scol)
				cs.add(col);
			for (int j = scol.length; j < dataTablesInputs.getiColumns(); j++)
				cs.add((String) null);
			aaData.add(cs);
		}
		json.put("aaData", aaData);

        // Write Json string
        String jsonString = mapper.writeValueAsString(json);
		if (LOG.isDebugEnabled())
			LOG.debug("JSON output: {}", jsonString.length() < 8192
                    ? jsonString
                    : jsonString.substring(0, 8192));

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		response.getWriter().println(jsonString);
	}

    /**
     * Load the facets values of the search
     * This also executes the search but only returns facet information.
     * @param response
     * @param form
     * @param request
     * @throws IOException
     */
	@RequestMapping(value = "/course/facetValues")
	public void getFacetValues(HttpServletResponse response,
			@ModelAttribute("KualiForm") final CourseSearchForm form,
			final HttpServletRequest request) throws IOException {

		if (LOG.isDebugEnabled())
			LOG.debug("Search form: {}", form);

        // Run search and retrieve results in the table
		SessionSearchInfo table = getSearchResults(new FormKey(form),
				new Callable<SessionSearchInfo>() {
					@Override
					public SessionSearchInfo call() throws Exception {
						return new SessionSearchInfo(request, form);
					}
				}, request);

		assert table.getFacetState() != null;

		// Update click state based on inputs - see ksap.search.js
		String fclick = request.getParameter("fclick");
		String fcol = request.getParameter("fcol");
		if (fclick != null && fcol != null)
			table.facetClick(fclick, fcol);

		// Create the oFacets object used by ksap.search.js
		ObjectNode oFacets = mapper.createObjectNode();
		oFacets.put("sQuery", form.getSearchQuery());
		oFacets.put("sTerm", form.getSearchTerm());
		ObjectNode oSearchColumn = oFacets.putObject("oSearchColumn");
		for (Entry<String, Integer> fce : FACET_COLUMNS.entrySet())
			oSearchColumn.put(fce.getKey(), fce.getValue());
		ObjectNode oFacetState = oFacets.putObject("oFacetState");
		for (Entry<String, Map<String, FacetState>> row : table.getFacetState()
				.entrySet()) {
			ObjectNode ofm = oFacetState.putObject(row.getKey());
			for (Entry<String, FacetState> fse : row.getValue().entrySet()) {
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
		String jsonString = mapper.writeValueAsString(oFacets);
		if (LOG.isDebugEnabled())
			LOG.debug("JSON output: {}", jsonString.length() < 8192
                    ? jsonString
                    : jsonString.substring(0, 8192));

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		response.getWriter().println(jsonString);
	}

    /**
     * Redirects to the course search results page.
     * @param form
     * @param httprequest
     * @param httpresponse
     * @return
     */
	@RequestMapping(params = "methodToCall=searchForCourses")
	public ModelAndView searchForCourses(
			@ModelAttribute("KualiForm") CourseSearchFormImpl form,
			HttpServletRequest httprequest,
			HttpServletResponse httpresponse) {
		return getUIFModelAndView(form,
				CourseSearchConstants.COURSE_SEARCH_RESULT_PAGE);
	}

	public List<String> getResults(SearchRequestInfo request, String division,
			String code) {

		if (division != null && code != null) {
			request.addParam("division", division);
			request.addParam("code", code);
		} else if (division != null && code == null)
			request.addParam("division", division);
		else
			return Collections.emptyList();

		request.addParam("lastScheduledTerm", KsapFrameworkServiceLocator
				.getTermHelper().getLastScheduledTerm().getId());
		request.addParam("currentTerm", KsapFrameworkServiceLocator
				.getTermHelper().getCurrentTerm().getId());
		SearchResult searchResult;
		try {
			if ((searchResult = KsapFrameworkServiceLocator.getCluService()
					.search(request,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo())) == null)
				return Collections.emptyList();
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		}
		List<String> results = new ArrayList<String>(searchResult.getRows()
				.size());
		for (SearchResultRow row : searchResult.getRows())
			results.add(KsapHelperUtil.getCellValue(row, "courseCode"));
		return results;
	}

	public synchronized CourseDetailsInquiryHelperImpl getCourseDetailsInquiryService() {
		if (this.courseDetailsInquiryService == null) {
			this.courseDetailsInquiryService = new CourseDetailsInquiryHelperImpl();
		}
		return courseDetailsInquiryService;
	}

	// Course ID GUID, atp key id eg "uw.kuali.atp.2001.1"
	@RequestMapping(value = "/course/enroll")
	public void getCourseSectionStatusAsJson(HttpServletResponse response,
			HttpServletRequest request) throws IOException, ServletException {
		String courseId = request.getParameter("courseId");
		CourseSummaryDetails courseDetails = getCourseDetailsInquiryService()
				.retrieveCourseSummaryById(courseId);

		String termIdInput = request.getParameter("termId");
		List<String> termIds;
		if (StringUtils.isBlank(termIdInput))
			termIds = new java.util.ArrayList<String>(
					courseDetails.getScheduledTerms());
		else
			termIds = Collections.singletonList(termIdInput);

		CourseHelper ch = KsapFrameworkServiceLocator.getCourseHelper();
		Map<String, Map<String, Object>> payload = new java.util.LinkedHashMap<String, Map<String, Object>>();

        try {
            for (String termId : termIds){
                for (CourseOfferingInfo co : KsapFrameworkServiceLocator.getCourseOfferingService()
                        .getCourseOfferingsByCourseAndTerm(courseId, termId,
                                KsapFrameworkServiceLocator.getContext().getContextInfo())) {
                    for (ActivityOfferingInfo ao : KsapFrameworkServiceLocator.getCourseOfferingService()
                            .getActivityOfferingsByCourseOffering(co.getId(),
                                    KsapFrameworkServiceLocator.getContext().getContextInfo())) {
                        Map<String, Object> enrl = new java.util.LinkedHashMap<String, Object>();
                        enrl.put("enrollMaximum", KsapFrameworkServiceLocator.getEnrollmentStatusHelper().populateMaxEnrollmentField(ao));
                        enrl.put("enrollCount", KsapFrameworkServiceLocator.getEnrollmentStatusHelper().populateCurrentEnrollmentField(ao));
                        enrl.put("enrollOpen", KsapFrameworkServiceLocator.getEnrollmentStatusHelper().populateCurrentEnrollmentField(ao));
                        enrl.put("enrollEstimate", KsapFrameworkServiceLocator.getEnrollmentStatusHelper().populateEstimatedEnrollmentField(ao));
                        String key = "enrl_" + termId.replace('.', '-').intern() + "_" + ao.getActivityCode();
                        payload.put(key, enrl);
                    }
                }
            }
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("CO lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("CO lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("CO lookup failure", e);
        }

		String json;
		try {
			json = mapper.writeValueAsString(payload);
			response.setHeader("content-type", "application/json");
			response.setHeader("Cache-Control", "No-cache");
			response.setHeader("Cache-Control", "No-store");
			response.setHeader("Cache-Control", "max-age=0");
			response.getWriter().println(json);
		} catch (JsonGenerationException e) {
			throw new ServletException("JSON generation failed", e);
		} catch (JsonMappingException e) {
			throw new ServletException("JSON generation failed", e);
		}
	}

}
