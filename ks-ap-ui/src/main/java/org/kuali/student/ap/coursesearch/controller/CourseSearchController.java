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

import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.CourseFacetStrategy;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.form.CourseSearchFormImpl;
import org.kuali.student.ap.coursesearch.service.CourseSearchViewHelperService;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/course/**")
public class CourseSearchController extends UifControllerBase {

	private static final Logger LOG = LoggerFactory.getLogger(CourseSearchController.class);

    private static final String COURSE_SEARCH_VIEW_FORM = "CourseSearch-FormView";

	/**
	 * HTTP session attribute key for holding recent search results.
	 */
	private static final String RESULTS_ATTR = CourseSearchController.class
			.getName() + ".results";

    private CourseSearchStrategy searcher = KsapFrameworkServiceLocator
            .getCourseSearchStrategy();

    private CourseFacetStrategy facetStrategy = KsapFrameworkServiceLocator
            .getCourseFacetStrategy();

	private ObjectMapper mapper = new ObjectMapper();

    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase
     */
	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
        return (UifFormBase) searcher.createInitialSearchForm();
	}

    /**
     * Sets up basic empty Course search page
     */
	@RequestMapping(params = "methodToCall=start")
	public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
			HttpServletRequest request,
			HttpServletResponse response) {
		super.start(form, request, response);
		return getUIFModelAndView(form);
	}

    /**
     * Execute a search on information supplied by the user
     */
	@RequestMapping(value = "/course/search")
	public void getJsonResponse(
			@ModelAttribute("KualiForm") final CourseSearchFormImpl form,
			HttpServletResponse response, final HttpServletRequest request)
			throws IOException {

        if (form.getView() == null) {
            form.setViewId(COURSE_SEARCH_VIEW_FORM);
            form.setView(super.getViewService().getViewById(COURSE_SEARCH_VIEW_FORM));
        }


            // Run search and retrieve results in the table
		SessionSearchInfo table = getViewHelperService(form).getSearchResults(form, request);
		if (table == null) {
			return;
		}

        // Translate the search results into a json
        JsonObjectBuilder json = getViewHelperService(form).getSearchResultsJson(form,table,request);

        // Write Json string
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "No-cache");
        response.setHeader("Cache-Control", "No-store");
        response.setHeader("Cache-Control", "max-age=0");
        JsonWriter jwriter = Json.createWriter(response.getWriter());
        jwriter.writeObject(json.build());
        jwriter.close();
	}

    /**
     * Load the facets values of the search
     * This also executes the search but only returns facet information.
     */
	@RequestMapping(value = "/course/facetValues")
	public void getFacetValues(HttpServletResponse response,
			@ModelAttribute("KualiForm") final CourseSearchFormImpl form,
			final HttpServletRequest request) throws IOException {

        if (form.getView() == null) {
            form.setViewId(COURSE_SEARCH_VIEW_FORM);
            form.setView(super.getViewService().getViewById(COURSE_SEARCH_VIEW_FORM));
        }

        // Run search and retrieve results in the table
        SessionSearchInfo table = getViewHelperService((UifFormBase) form).getSearchResults(form, request);

        assert table.getFacetState() != null;

        String fclick = request.getParameter("fclick");
        String fcol = request.getParameter("fcol");

        if (fclick != null && fcol != null)
            table.facetClick(fclick, fcol);

        JsonObjectBuilder json = getViewHelperService(form).getFacetsJson(form, table.getFacetState());

        // Write Json string
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "No-cache");
        response.setHeader("Cache-Control", "No-store");
        response.setHeader("Cache-Control", "max-age=0");
        JsonWriter jwriter = Json.createWriter(response.getWriter());
        jwriter.writeObject(json.build());
        jwriter.close();
	}

    /**
     * Redirects to the course search results page.
     */
	@RequestMapping(params = "methodToCall=searchForCourses")
	public ModelAndView searchForCourses(
			@ModelAttribute("KualiForm") CourseSearchFormImpl form,
			HttpServletRequest httprequest,
			HttpServletResponse httpresponse) {
		return getUIFModelAndView(form,
				CourseSearchConstants.COURSE_SEARCH_RESULT_PAGE);
	}

    /**
     * Retrieve the view helper from a form.
     *
     * @param form - Form helper is being retrieved for
     * @return Form's view helper
     */
    private CourseSearchViewHelperService getViewHelperService(UifFormBase form) {
        CourseSearchViewHelperService viewHelperService = (CourseSearchViewHelperService) form.getViewHelperService();
        return viewHelperService;
    }

}
