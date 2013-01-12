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

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.myplan.academicplan.infc.LearningPlan;
import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.myplan.course.dataobject.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.FacetItem;
import org.kuali.student.myplan.course.form.CourseSearchForm;
import org.kuali.student.myplan.course.util.CampusSearch;
import org.kuali.student.myplan.course.util.CourseLevelFacet;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.course.util.CreditsFacet;
import org.kuali.student.myplan.course.util.CurriculumFacet;
import org.kuali.student.myplan.course.util.GenEduReqFacet;
import org.kuali.student.myplan.course.util.TermsFacet;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.myplan.plan.util.AtpHelper;
import org.kuali.student.myplan.plan.util.EnumerationHelper;
import org.kuali.student.myplan.utils.UserSessionHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/course/**")
public class CourseSearchController extends UifControllerBase {

	private static final Logger LOG = Logger
			.getLogger(CourseSearchController.class);

	private static final int MAX_HITS = 1000;

	@Autowired
	private CourseSearchStrategy searcher = new CourseSearchStrategy();
	private CampusSearch campusSearch = new CampusSearch();

	// Java to JSON outputter.
	private transient ObjectMapper mapper = new ObjectMapper();

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new CourseSearchForm();
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
		HashMap<String, String> divisionMap = fetchCourseDivisions();

		ArrayList<String> divisions = new ArrayList<String>();
		extractDivisions(divisionMap, subject, divisions, false);
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
			courseId = getCellValue(row, "lu.resultColumn.cluId");

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

	public static class Hit {
		public String courseID;
		public int count = 0;

		public Hit(String courseID) {
			this.courseID = courseID;
			count = 1;
		}

		@Override
		public boolean equals(Object other) {
			return courseID.equals(((Hit) other).courseID);
		}

		@Override
		public int hashCode() {
			return courseID.hashCode();
		}
	}

	public static class HitComparator implements Comparator<Hit> {
		@Override
		public int compare(Hit x, Hit y) {
			if (x == null)
				return -1;
			if (y == null)
				return 1;
			return y.count - x.count;
		}
	}

	public class Credit {
		String id;
		String display;
		float min;
		float max;
		CourseSearchItem.CreditType type;
	}

	public String getCellValue(SearchResultRow row, String key) {
		for (SearchResultCell cell : row.getCells()) {
			if (key.equals(cell.getKey())) {
				return cell.getValue();
			}
		}
		throw new RuntimeException("cell result '" + key + "' not found");
	}

	public Map<String, Credit> getCreditMap() {
		Map<String, Credit> creditMap = new java.util.LinkedHashMap<String, Credit>();
		SearchRequestInfo searchRequest = new SearchRequestInfo(
				"myplan.course.info.credits.details");
		searchRequest.setParams(Collections.<SearchParamInfo> emptyList());
		try {
			for (SearchResultRow row : KsapFrameworkServiceLocator
					.getCluService()
					.search(searchRequest,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo()).getRows()) {
				String id = getCellValue(row, "credit.id");
				String type = getCellValue(row, "credit.type");
				String min = getCellValue(row, "credit.min");
				String max = getCellValue(row, "credit.max");
				Credit credit = new Credit();
				credit.id = id;
				credit.min = Float.valueOf(min);
				credit.max = Float.valueOf(max);
				if ("kuali.result.values.group.type.multiple".equals(type)) {
					credit.display = min + ", " + max;
					credit.type = CourseSearchItem.CreditType.multiple;
				} else if ("kuali.result.values.group.type.range".equals(type)) {
					credit.display = min + "-" + max;
					credit.type = CourseSearchItem.CreditType.range;
				} else if ("kuali.result.values.group.type.fixed".equals(type)) {
					credit.display = min;
					credit.type = CourseSearchItem.CreditType.fixed;
				}
				creditMap.put(id, credit);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		}
		return creditMap;
	}

	public Credit getCreditByID(String id) {
		Credit credit = getCreditMap().get(id);
		return credit == null ? getCreditMap().get("u") : credit;
	}

	public List<CourseSearchItem> courseSearch(CourseSearchForm form,
			String studentId) {
		String maxCountProp = ConfigContext.getCurrentContextConfig()
				.getProperty("myplan.search.results.max");
		int maxCount = (StringUtils.hasText(maxCountProp)) ? Integer
				.valueOf(maxCountProp) : MAX_HITS;
		List<SearchRequestInfo> requests = searcher.queryToRequests(form);
		List<Hit> hits = processSearchRequests(requests);
		List<CourseSearchItem> courseList = new ArrayList<CourseSearchItem>();
		Map<String, CourseSearchItem.PlanState> courseStatusMap = getCourseStatusMap(studentId);
		for (Hit hit : hits) {
			CourseSearchItem course = getCourseInfo(hit.courseID);
			if (isCourseOffered(form, course)) {
				loadScheduledTerms(course);
				loadTermsOffered(course);
				loadGenEduReqs(course);
				String courseId = course.getCourseId();
				if (courseStatusMap.containsKey(courseId)) {
					course.setStatus(courseStatusMap.get(courseId));
				}
				courseList.add(course);
				if (courseList.size() >= maxCount) {
					break;
				}
			}
		}
		populateFacets(form, courseList);
		LOG.error(String.format("SEARCH: %s  : %s CAMPUS : %s : %s",
				form.getSearchQuery(), form.getSearchTerm(),
				form.getCampusSelect(), String.valueOf(hits.size())));
		return courseList;
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
		CourseSearchForm form = new CourseSearchForm();
		form.setSearchQuery(queryText);
		form.setCampusSelect(campusParams);
		form.setSearchTerm(termParam);

		// populating the CourseSearchItem list
		List<CourseSearchItem> courses = courseSearch(form, user);

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
			@ModelAttribute("KualiForm") CourseSearchForm form,
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
			results.add(getCellValue(row, "courseCode"));
		return results;
	}

	public void hitCourseID(Map<String, Hit> courseMap, String id) {
		Hit hit = null;
		if (courseMap.containsKey(id)) {
			hit = courseMap.get(id);
			hit.count++;
		} else {
			hit = new Hit(id);
			courseMap.put(id, hit);
		}
	}

	public List<Hit> processSearchRequests(List<SearchRequestInfo> requests) {
		LOG.info("Start of processSearchRequests of CourseSearchController:"
				+ System.currentTimeMillis());
		List<Hit> hits = new java.util.LinkedList<Hit>();
		Set<String> seen = new java.util.HashSet<String>();
		String id;
		for (SearchRequestInfo request : requests)
			try {
				for (SearchResultRow row : KsapFrameworkServiceLocator
						.getCluService()
						.search(request,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo()).getRows())
					if (seen.add(id = getCellValue(row, "lu.resultColumn.cluId")))
						hits.add(new Hit(id));
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException(
						"Invalid course ID or CLU lookup error", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException(
						"Invalid course ID or CLU lookup error", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("CLU lookup error", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalArgumentException("CLU lookup error", e);
			}
		LOG.info("End of processSearchRequests of CourseSearchController:"
				+ System.currentTimeMillis());
		return hits;
	}

	public boolean isCourseOffered(CourseSearchForm form,
			CourseSearchItem course) {
		/*
		 * If the "any" item was chosen in the terms dop-down then continue
		 * processing. Otherwise, determine if the CourseSearchItem should be
		 * filtered out of the result set.
		 */
		String term = form.getSearchTerm();

		if (CourseSearchForm.SEARCH_TERM_ANY_ITEM.equals(term))
			return true;

		/*
		 * Use the course offering service to see if the course is being offered
		 * in the selected term. Note: In the UW implementation of the Course
		 * Offering service, course id is actually course code.
		 */
		CourseOfferingService service = KsapFrameworkServiceLocator
				.getCourseOfferingService();

		String subject = course.getSubject();
		List<String> codes;
		try {
			codes = service.getCourseOfferingIdsByTermAndSubjectArea(term,
					subject, KsapFrameworkServiceLocator.getContext()
							.getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Course Offering not found", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup error", e);
		}

		// The course code is not in the list, so move on to the next item.
		String code = course.getCode();
		boolean result = codes.contains(code);
		return result;
	}

	// Load scheduled terms.
	// Fetch the available terms from the Academic Calendar Service.
	private void loadScheduledTerms(CourseSearchItem course) {
		LOG.info("Start of method loadScheduledTerms of CourseSearchController:"
				+ System.currentTimeMillis());
		AcademicCalendarService atpService = KsapFrameworkServiceLocator
				.getAcademicCalendarService();

		List<TermInfo> terms;
		try {
			terms = atpService.searchForTerms(QueryByCriteria.Builder
					.fromPredicates(equalIgnoreCase("query",
							PlanConstants.PUBLISHED)),
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("ATP lookup failed", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("ATP lookup failed", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("ATP lookup failed", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("ATP lookup failed", e);
		}

		CourseOfferingService offeringService = KsapFrameworkServiceLocator
				.getCourseOfferingService();

		// If the course is offered in the term then add the term info to
		// the scheduled terms list.
		String code = course.getCode();

		for (TermInfo term : terms) {

			String key = term.getId();
			String subject = course.getSubject();

			try {
				List<String> offerings = offeringService
						.getCourseOfferingIdsByTermAndSubjectArea(key, subject,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
				if (offerings.contains(code))
					course.addScheduledTerm(term.getName());
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("ATP lookup failed", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("ATP lookup failed", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("ATP lookup failed", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("ATP lookup failed", e);
			} catch (DoesNotExistException e) {
				LOG.warn("Missing course offering", e);
			}
		}
		LOG.info("End of method loadScheduledTerms of CourseSearchController:"
				+ System.currentTimeMillis());
	}

	private void loadTermsOffered(CourseSearchItem course) {
		LOG.info("Start of method loadTermsOffered of CourseSearchController:"
				+ System.currentTimeMillis());
		String courseId = course.getCourseId();
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.course.info.atp");
		request.addParam("courseID", courseId);

		SearchResult result;
		try {
			result = KsapFrameworkServiceLocator.getCluService().search(
					request,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException(
					"Invalid course ID or CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException(
					"Invalid course ID or CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		}
		List<String> termsOffered = new java.util.ArrayList<String>(result
				.getRows().size());
		for (SearchResultRow row : result.getRows()) {
			String id = getCellValue(row, "atp.id");

			// Don't add the terms that are not found
			AtpInfo atp;
			try {
				atp = KsapFrameworkServiceLocator.getAtpService().getAtp(
						id,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Invalid ATP ID " + id, e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException(
						"Invalid course ID or CLU lookup error", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException(
						"Invalid ATP ID or ATP lookup error", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("ATP lookup error", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalArgumentException("ATP lookup error", e);
			}
			if (null != atp) {
				termsOffered.add(atp.getTypeKey());
			}
		}

		// Collections.sort(termsOffered, getAtpTypeComparator());
		course.setTermInfoList(termsOffered);
		LOG.info("End of method loadTermsOffered of CourseSearchController:"
				+ System.currentTimeMillis());
	}

	private void loadGenEduReqs(CourseSearchItem course) {
		LOG.info("Start of method loadGenEduReqs of CourseSearchController:"
				+ System.currentTimeMillis());
		String courseId = course.getCourseId();
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.course.info.gened");
		request.addParam("courseID", courseId);
		List<String> reqs = new ArrayList<String>();
		SearchResult result;
		try {
			result = KsapFrameworkServiceLocator.getCluService().search(
					request,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException(
					"Invalid course ID or CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException(
					"Invalid course ID or CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		}
		for (SearchResultRow row : result.getRows()) {
			String genEd = getCellValue(row, "gened.name");
			reqs.add(genEd);
		}
		String formatted = formatGenEduReq(reqs, KsapFrameworkServiceLocator
				.getContext().getContextInfo());
		course.setGenEduReq(formatted);
		LOG.info("End of method loadGenEduReqs of CourseSearchController:"
				+ System.currentTimeMillis());
	}

	private transient AcademicPlanService academicPlanService;

	public AcademicPlanService getAcademicPlanService() {
		if (academicPlanService == null) {
			academicPlanService = (AcademicPlanService) GlobalResourceLoader
					.getService(new QName(
							AcademicPlanServiceConstants.NAMESPACE,
							AcademicPlanServiceConstants.SERVICE_NAME));
		}
		return academicPlanService;
	}

	public void setAcademicPlanService(AcademicPlanService academicPlanService) {
		this.academicPlanService = academicPlanService;
	}

	private Map<String, CourseSearchItem.PlanState> getCourseStatusMap(
			String studentID) {
		LOG.info("Start of method getCourseStatusMap of CourseSearchController:"
				+ System.currentTimeMillis());
		AcademicPlanService academicPlanService = getAcademicPlanService();

		ContextInfo context = new ContextInfo();

		String planTypeKey = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;

		Map<String, CourseSearchItem.PlanState> savedCourseSet = new HashMap<String, CourseSearchItem.PlanState>();

		/*
		 * For each plan item in each plan set the state based on the type.
		 */
		List<LearningPlanInfo> learningPlanList;
		try {
			learningPlanList = academicPlanService
					.getLearningPlansForStudentByType(studentID, planTypeKey,
							context);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Learning plan does not exist",
					e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup error", e);
		}
		for (LearningPlan learningPlan : learningPlanList) {
			String learningPlanID = learningPlan.getId();
			List<PlanItemInfo> planItemList;
			try {
				planItemList = academicPlanService.getPlanItemsInPlan(
						learningPlanID, context);
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException(
						"Learning plan items do not exist", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("LP lookup error", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("LP lookup error", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("LP lookup error", e);
			}
			for (PlanItem planItem : planItemList) {
				String courseID = planItem.getRefObjectId();
				CourseSearchItem.PlanState state;
				if (planItem.getTypeKey().equals(
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST)) {
					state = CourseSearchItem.PlanState.SAVED;
				} else if (planItem.getTypeKey().equals(
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)
						|| planItem.getTypeKey().equals(
								PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
					state = CourseSearchItem.PlanState.IN_PLAN;
				} else {
					throw new RuntimeException("Unknown plan item type.");
				}
				savedCourseSet.put(courseID, state);
			}
		}
		LOG.info("End of method getCourseStatusMap of CourseSearchController:"
				+ System.currentTimeMillis());
		return savedCourseSet;
	}

	private CourseSearchItem getCourseInfo(String courseId) {
		LOG.info("Start of method getCourseInfo of CourseSearchController:"
				+ System.currentTimeMillis());

		SearchRequestInfo request = new SearchRequestInfo("myplan.course.info");
		request.addParam("courseID", courseId);
		SearchResult result;
		try {
			result = KsapFrameworkServiceLocator.getCluService().search(
					request,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException(
					"Invalid course ID or CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException(
					"Invalid course ID or CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		}
		if (result.getRows().isEmpty())
			return new CourseSearchItem();

		SearchResultRow row = result.getRows().get(0);
		CourseSearchItem course = new CourseSearchItem();
		course.setCourseId(courseId);
		course.setSubject(getCellValue(row, "course.subject"));
		course.setNumber(getCellValue(row, "course.number"));
		course.setLevel(getCellValue(row, "course.level"));
		course.setCourseName(getCellValue(row, "course.name"));
		course.setCode(getCellValue(row, "course.code"));

		Credit credit = getCreditByID(getCellValue(row, "course.credits"));
		course.setCreditMin(credit.min);
		course.setCreditMax(credit.max);
		course.setCreditType(credit.type);
		course.setCredit(credit.display);

		LOG.info("End of method getCourseInfo of CourseSearchController:"
				+ System.currentTimeMillis());
		return course;
	}

	public void populateFacets(CourseSearchForm form,
			List<CourseSearchItem> courses) {
		LOG.info("Start of method populateFacets of CourseSearchController:"
				+ System.currentTimeMillis());
		// Initialize facets.
		CurriculumFacet curriculumFacet = new CurriculumFacet();
		CreditsFacet creditsFacet = new CreditsFacet();
		CourseLevelFacet courseLevelFacet = new CourseLevelFacet();
		GenEduReqFacet genEduReqFacet = new GenEduReqFacet();
		TermsFacet termsFacet = new TermsFacet();

		// Update facet info and code the item.
		for (CourseSearchItem course : courses) {
			curriculumFacet.process(course);
			courseLevelFacet.process(course);
			genEduReqFacet.process(course);
			creditsFacet.process(course);
			termsFacet.process(course);
		}
		/* Removing Duplicate entries from genEduReqFacet */
		List<FacetItem> genEduReqFacetItems = new ArrayList<FacetItem>();
		for (FacetItem facetItem : genEduReqFacet.getFacetItems()) {
			boolean itemExists = false;
			for (FacetItem facetItem1 : genEduReqFacetItems) {
				if (facetItem1.getKey().equalsIgnoreCase(facetItem.getKey())) {
					itemExists = true;
				}
			}
			if (!itemExists) {
				genEduReqFacetItems.add(facetItem);
			}
		}

		/*
		 * // Add the facet data to the response. logger.info(
		 * "Start of populating curriculumFacet  of CourseSearchController:" +
		 * System.currentTimeMillis());
		 * form.setCurriculumFacetItems(curriculumFacet.getFacetItems());
		 * logger.
		 * info("End of populating curriculumFacet  of CourseSearchController:"
		 * + System.currentTimeMillis()); logger.info(
		 * "Start of populating creditsFacet  of CourseSearchController:" +
		 * System.currentTimeMillis());
		 * form.setCreditsFacetItems(creditsFacet.getFacetItems());
		 * logger.info("End of populating creditsFacet  of CourseSearchController:"
		 * + System.currentTimeMillis()); logger.info(
		 * "Start of populating genEduReqFacet  of CourseSearchController:" +
		 * System.currentTimeMillis());
		 * form.setGenEduReqFacetItems(genEduReqFacetItems); logger.info(
		 * "End of populating genEduReqFacet  of CourseSearchController:" +
		 * System.currentTimeMillis()); logger.info(
		 * "Start of populating courseLevelFacet  of CourseSearchController:" +
		 * System.currentTimeMillis());
		 * form.setCourseLevelFacetItems(courseLevelFacet.getFacetItems());
		 * logger
		 * .info("End of populating courseLevelFacet  of CourseSearchController:"
		 * + System.currentTimeMillis());
		 * logger.info("Start of populating termsFacet  of CourseSearchController:"
		 * + System.currentTimeMillis());
		 * form.setTermsFacetItems(termsFacet.getFacetItems());
		 * logger.info("End of populating termsFacet  of CourseSearchController:"
		 * + System.currentTimeMillis());
		 * logger.info("End of method populateFacets of CourseSearchController:"
		 * + System.currentTimeMillis());
		 */
	}

	public HashMap<String, String> fetchCourseDivisions() {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			SearchRequestInfo request = new SearchRequestInfo(
					"myplan.distinct.clu.divisions");

			SearchResult result = KsapFrameworkServiceLocator.getCluService()
					.search(request,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());

			for (SearchResultRow row : result.getRows()) {
				for (SearchResultCell cell : row.getCells()) {
					String division = cell.getValue();
					// Store both trimmed and original, because source data
					// is sometimes space padded.
					String key = division.trim().replaceAll("\\s+", "");
					map.put(key, division);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return map;
	}

	public String extractDivisions(HashMap<String, String> divisionMap,
			String query, List<String> divisions, boolean isSpaceAllowed) {
		boolean match = true;
		while (match) {
			match = false;
			// Retokenize after each division found is removed
			// Remove extra spaces to normalize input
			if (!isSpaceAllowed) {
				query = query.trim().replaceAll(
						"[\\s\\\\/:?\\\"<>|`~!@#$%^*()_+-={}\\]\\[;',.]", " ");
			} else {
				query = query.replaceAll(
						"[\\\\/:?\\\"<>|`~!@#$%^*()_+-={}\\]\\[;',.]", " ");
			}
			List<QueryTokenizer.Token> tokens = QueryTokenizer.tokenize(query);
			List<String> list = QueryTokenizer.toStringList(tokens);
			List<String> pairs = TokenPairs.toPairs(list);
			TokenPairs.sortedLongestFirst(pairs);

			Iterator<String> i = pairs.iterator();
			while (match == false && i.hasNext()) {
				String pair = i.next();

				String key = pair.replace(" ", "");
				if (divisionMap.containsKey(key)) {
					String division = divisionMap.get(key);
					divisions.add(division);
					query = query.replace(pair, "");
					match = true;
				}
			}
		}
		return query;
	}

	private String formatGenEduReq(List<String> genEduRequirements,
			ContextInfo context) {

		// Make the order predictable.
		Collections.sort(genEduRequirements);
		StringBuilder genEdsOut = new StringBuilder();
		for (String req : genEduRequirements) {
			if (genEdsOut.length() != 0) {
				genEdsOut.append(", ");
			}
			req = EnumerationHelper.getEnumAbbrValForCode(req, context);
			/* Doing this to fix a bug in IE8 which is trimming off the I&S as I */
			if (req.contains("&")) {
				req = req.replace("&", "&amp;");
			}
			genEdsOut.append(req);
		}
		return genEdsOut.toString();
	}

	public Comparator<String> getAtpTypeComparator() {
		// return atpTypeComparator;
		return null;
	}

	public void setAtpTypeComparator(Comparator<String> atpTypeComparator) {
		// this.atpTypeComparator = atpTypeComparator;
	}

	public CourseSearchStrategy getSearcher() {
		return searcher;
	}

	public void setSearcher(CourseSearchStrategy searcher) {
		this.searcher = searcher;
	}

	public CampusSearch getCampusSearch() {
		return campusSearch;
	}

	public void setCampusSearch(CampusSearch campusSearch) {
		this.campusSearch = campusSearch;
	}
}
