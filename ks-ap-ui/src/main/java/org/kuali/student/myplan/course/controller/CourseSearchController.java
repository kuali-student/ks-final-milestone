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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CourseSearchForm;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.framework.course.CourseSearchStrategy;
import org.kuali.student.myplan.course.form.CourseSearchFormImpl;
import org.kuali.student.myplan.course.util.CampusSearch;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
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
		return (UifFormBase) searcher.createSearchForm();
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
			response.sendRedirect("../course?searchQuery=" + splitBuff
					+ "&searchTerm=any&campusSelect=" + campus);
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
			searchRequest.addParam("currentTerm", KsapFrameworkServiceLocator
					.getAtpHelper().getCurrentAtpId());
			searchRequest.addParam("lastScheduledTerm",
					KsapFrameworkServiceLocator.getAtpHelper()
							.getLastScheduledAtpId());
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
			response.sendRedirect("../course?searchQuery=" + courseCd
					+ "&searchTerm=any&campusSelect=" + campus);
			return null;

		}
		response.sendRedirect("../inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId="
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
			KsapFrameworkServiceLocator.getAtpHelper().addServiceError(
					"searchTerm");
		}
		super.start(form, result, request, response);
		return getUIFModelAndView(form);
	}

	@RequestMapping(value = "/course/search")
	public void getJsonResponse(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String user = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();

		// Params from the Url
		String queryText = request.getParameter("queryText");
		String campusParamStr = request.getParameter("campusParam");
		List<String> campusParams = Arrays.asList(campusParamStr
				.split("\\s*,\\s*"));
		String termParam = request.getParameter("termParam");

		// populating the form with the params
		CourseSearchForm form = searcher.createSearchForm();
		form.setSearchQuery(queryText);
		form.setCampusSelect(campusParams);
		form.setSearchTerm(termParam);

		// populating the CourseSearchItem list
		List<CourseSearchItem> courses = searcher.courseSearch(form, user);

		// Building the Json String
		StringBuilder jsonString = new StringBuilder();
		jsonString = jsonString.append("{ \"aaData\":[");
		for (CourseSearchItem item : courses) {
			String status = "";
			String scheduledAndOfferedTerms = mapper.writeValueAsString(item
					.getScheduledAndOfferedTerms());
			String cid = item.getCourseId().replace('.', '_');
			if (item.getStatus().getLabel().length() > 0) {
				status = "<span id=\\\"" + cid + "_status\\\" class=\\\""
						+ item.getStatus().getLabel().toLowerCase() + "\\\">"
						+ item.getStatus().getLabel() + "</span>";
			} else if (KsapFrameworkServiceLocator.getUserSessionHelper()
					.isAdviser()) {
				status = "<span id=\\\"" + cid + "_status\\\">"
						+ CourseSearchItem.EMPTY_RESULT_VALUE_KEY + "</span>";
			} else {
				status = "<span id=\\\""
						+ cid
						+ "_status\\\"><input id=\\\""
						+ cid
						+ "_add_anchor\\\" type=\\\"image\\\" title=\\\"Bookmark or Add to Plan\\\" src=\\\""
						+ ConfigContext.getCurrentContextConfig().getProperty(
								"ks.myplan.externalizable.images.url")
						+ "pixel.gif\\\" alt=\\\"Bookmark or Add to Plan\\\" class=\\\"uif-field uif-imageField myplan-add uif-tooltip\\\" data-courseid=\\\""
						+ item.getCourseId()
						+ "\\\" data-coursexid=\\\""
						+ cid
						+ "\\\" onclick=\\\"openMenu('"
						+ cid
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
					.append(item.getCourseId());
			Map<String, String> ap = form.getAdditionalParams();
			if (ap != null)
				for (Entry<String, String> e : ap.entrySet())
					jsonString.append('&')
							.append(URLEncoder.encode(e.getKey(), "UTF-8"))
							.append('=')
							.append(URLEncoder.encode(e.getValue(), "UTF-8"));
			String jcn = StringEscapeUtils.escapeJavaScript(StringEscapeUtils
					.escapeHtml(courseName));
			jsonString.append("\\").append("\" target=\\").append("\"_self\\")
					.append("\" title=\\").append("\"").append(jcn)
					.append("\\").append("\"").append(" class=\\")
					.append("\"myplan-text-ellipsis\\").append("\">")
					.append(jcn).append("</a>\"").append(",\"")
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

		request.addParam("lastScheduledTerm", KsapFrameworkServiceLocator
				.getAtpHelper().getLastScheduledAtpId());
		request.addParam("currentTerm", KsapFrameworkServiceLocator
				.getAtpHelper().getCurrentAtpId());
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
