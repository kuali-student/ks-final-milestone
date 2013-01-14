/*
 * Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.myplan.course.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CourseSearchForm;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.framework.course.CourseSearchStrategy;
import org.kuali.student.myplan.course.form.CourseSearchFormImpl;
import org.kuali.student.myplan.course.util.CampusSearch;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.plan.util.AtpHelper;
import org.kuali.student.myplan.utils.UserSessionHelper;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/course/**")
public class CourseSearchController extends UifControllerBase {

	private CourseSearchStrategy searcher = KsapFrameworkServiceLocator
			.getCourseSearchStrategy();

	private CampusSearch campusSearch = new CampusSearch();

	// Java to JSON outputter.
	private transient ObjectMapper mapper = new ObjectMapper();

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new CourseSearchFormImpl();
	}

	@RequestMapping(value = "/course/{courseCd}", method = RequestMethod.GET)
	public String get(@PathVariable("courseCd") String courseCd,
			@ModelAttribute("KualiForm") CourseSearchForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String number = "";
		String subject = "";
		String courseId = "";
		courseCd = courseCd.toUpperCase();
		StringBuffer campus = new StringBuffer();
		List<KeyValue> campusKeys = campusSearch.getKeyValues();
		for (KeyValue k : campusKeys) {
			campus.append(k.getKey().toString());
			campus.append(",");
		}
		String[] splitStr = courseCd.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
		if (splitStr.length == 2) {
			number = splitStr[1];
			subject = splitStr[0];
		} else {
			StringBuffer splitBuff = new StringBuffer();
			for (int i = 0; i < splitStr.length; i++) {
				splitBuff.append(splitStr[i]);
			}
			response.sendRedirect("/student/myplan/course?searchQuery="
					+ splitBuff + "&searchTerm=any&campusSelect=" + campus);
			return null;

		}
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		Map<String, String> divisionMap = strategy.fetchCourseDivisions();

		ArrayList<String> divisions = new ArrayList<String>();
		strategy.extractDivisions(divisionMap, subject, divisions, false);
		if (divisions.size() > 0) {
			subject = divisions.get(0);
		}

		SearchRequestInfo searchRequest = new SearchRequestInfo(
				"myplan.course.getcluid");
		SearchResult searchResult = null;
		try {
			searchRequest.addParam("number", number);
			searchRequest.addParam("subject", subject.trim());
			searchRequest.addParam("currentTerm", AtpHelper.getCurrentAtpId());
			searchRequest.addParam("lastScheduledTerm",
					AtpHelper.getLastScheduledAtpId());
			searchResult = KsapFrameworkServiceLocator.getCluService().search(
					searchRequest,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		for (SearchResultRow row : searchResult.getRows()) {
			courseId = searcher.getCellValue(row, "lu.resultColumn.cluId");
		}
		if (courseId.equalsIgnoreCase("")) {
			response.sendRedirect(request.getContextPath()
					+ "/myplan/course?searchQuery=" + courseCd
					+ "&searchTerm=any&campusSelect=" + campus);
			return null;

		}
		response.sendRedirect(request.getContextPath()
				+ "/myplan/inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId="
				+ courseId);
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		super.start(form, result, request, response);
		form.setViewId("CourseSearch-FormView");
		form.setView(super.getViewService()
				.getViewById("CourseSearch-FormView"));
		return getUIFModelAndView(form);
	}

	@RequestMapping(value = "/course/", method = RequestMethod.GET)
	public String doGet(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		return "redirect:/myplan/course";
	}

	@RequestMapping(params = "methodToCall=start")
	public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		if (!Boolean.valueOf(request.getAttribute(
				CourseSearchConstants.IS_ACADEMIC_CALENDER_SERVICE_UP)
				.toString())) {
			AtpHelper.addServiceError("searchTerm");
		}
		super.start(form, result, request, response);
		return getUIFModelAndView(form);
	}

	@RequestMapping(value = "/course/search")
	public void getJsonResponse(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String user = UserSessionHelper.getStudentId();

		// Params from the Url
		String queryText = request.getParameter("queryText");
		String campusParamStr = request.getParameter("campusParam");
		List<String> campusParams = Arrays.asList(campusParamStr
				.split("\\s*,\\s*"));
		String termParam = request.getParameter("termParam");

		// populating the form with the params
		CourseSearchForm form = new CourseSearchFormImpl();
		form.setSearchQuery(queryText);
		form.setCampusSelect(campusParams);
		form.setSearchTerm(termParam);

		// populating the CourseSearchItem list
		List<CourseSearchItem> courses = searcher.courseSearch(form, user);

		// Building the Json String
		// TODO: Use a tool to generate JSON - a template at the very least
		StringBuilder jsonString = new StringBuilder();
		jsonString = jsonString.append("{ \"aaData\":[");
		for (CourseSearchItem item : courses) {
			String scheduledAndOfferedTerms = null;
			String status = "";
			try {
				scheduledAndOfferedTerms = mapper.writeValueAsString(item
						.getScheduledAndOfferedTerms());
			} catch (IOException e) {
				throw new RuntimeException(
						"Could not write the value using mapper", e);
			}
			if (item.getStatus().getLabel().length() > 0) {
				status = "<span id=\\\"" + item.getCourseId()
						+ "_status\\\" class=\\\""
						+ item.getStatus().getLabel().toLowerCase() + "\\\">"
						+ item.getStatus().getLabel() + "</span>";
			} else if (UserSessionHelper.isAdviser()) {
				status = "<span id=\\\"" + item.getCourseId() + "_status\\\">"
						+ CourseSearchItem.EMPTY_RESULT_VALUE_KEY + "</span>";
			} else {
				status = "<span id=\\\""
						+ item.getCourseId()
						+ "_status\\\"><input type=\\\"image\\\" title=\\\"Bookmark or Add to Plan\\\" src=\\\"/student/ks-myplan/images/pixel.gif\\\" alt=\\\"Bookmark or Add to Plan\\\" class=\\\"uif-field uif-imageField myplan-add\\\" data-courseid= \\\""
						+ item.getCourseId()
						+ "\\\"onclick=\\\"openMenu('"
						+ item.getCourseId()
						+ "_add','add_course_items',null,event,null,'myplan-container-75',{tail:{align:'middle'},align:'middle',position:'right'},false);\\\" /></span>";

			}
			String courseName = "";
			if (item.getCourseName() != null) {
				courseName = item.getCourseName().replace("\"", "'");
			}

			jsonString = jsonString
					.append("[\"")
					.append(item.getCode())
					.append("\",\"")
					.append(" <a href=\\")
					.append("\"inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId=")
					.append(item.getCourseId()).append("\\")
					.append("\" target=\\").append("\"_self\\")
					.append("\" title=\\").append("\"").append(courseName)
					.append("\\").append("\"").append(" class=\\")
					.append("\"myplan-text-ellipsis\\").append("\">")
					.append(courseName).append("</a>\"").append(",\"")
					.append(item.getCredit()).append("\",")
					.append(scheduledAndOfferedTerms).append(",\"")
					.append(item.getGenEduReq()).append("\",\"").append(status)
					.append("\",\"").append(item.getTermsFacetKeys())
					.append("\",\"").append(item.getGenEduReqFacetKeys())
					.append("\",\"").append(item.getCreditsFacetKeys())
					.append("\",\"").append(item.getCourseLevelFacetKeys())
					.append("\",\"").append(item.getCurriculumFacetKeys())
					.append("\"]").append(", ");
		}
		String jsonStr = null;
		if (!jsonString.toString().equalsIgnoreCase("{ \"aaData\":[")) {
			jsonStr = jsonString.substring(0, jsonString.lastIndexOf(","));
		} else {
			jsonStr = jsonString.toString();
		}
		jsonStr = jsonStr + "]" + "}";
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		response.getWriter().println(jsonStr);
	}

	@RequestMapping(params = "methodToCall=searchForCourses")
	public ModelAndView searchForCourses(
			@ModelAttribute("KualiForm") CourseSearchFormImpl form,
			BindingResult result, HttpServletRequest httprequest,
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

		request.addParam("lastScheduledTerm", AtpHelper.getLastScheduledAtpId());
		request.addParam("currentTerm", AtpHelper.getCurrentAtpId());
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
		List<String> results = new java.util.ArrayList<String>(searchResult
				.getRows().size());
		for (SearchResultRow row : searchResult.getRows())
			results.add(searcher.getCellValue(row, "courseCode"));
		return results;
	}

}
