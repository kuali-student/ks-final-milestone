/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.myplan.quickAdd.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.EnrollmentStatusHelper;
import org.kuali.student.ap.framework.course.CourseSearchStrategy;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.ap.coursesearch.controller.CourseSearchController;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.springframework.util.StringUtils;

//unused
public class QuickAddSuggestHelperService {

	public static final Logger logger = Logger
			.getLogger(QuickAddSuggestHelperService.class);

	public static CourseSearchController searchController = new CourseSearchController();

    //unused
	public static List<String> getSuggestions(String courseCd,
			ContextInfo context) {
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		List<String> results = new ArrayList<String>();
		if (courseCd.length() >= 2) {
			if (StringUtils.hasText(courseCd)) {
				SearchRequestInfo searchRequest = null;
				Map<String, String> divisionMap = strategy
						.fetchCourseDivisions();
				String searchText = courseCd.toUpperCase();
				String number = null;
				String subject = null;
				String[] splitStr = searchText
						.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
				if (splitStr.length == 2) {
					number = splitStr[1];
					ArrayList<String> divisions = new ArrayList<String>();
					subject = strategy.extractDivisions(divisionMap,
							splitStr[0], divisions, true);
					if (divisions.size() > 0) {
						subject = divisions.get(0);
						searchRequest = new SearchRequestInfo(
								"ksap.clu.divisionAndCode");
						results = searchController.getResults(searchRequest,
								subject, number);
					}
				} else if (splitStr.length == 1
						&& !org.apache.commons.lang.StringUtils
								.isNumeric(splitStr[0])) {
					ArrayList<String> divisions = new ArrayList<String>();
					subject = strategy.extractDivisions(divisionMap,
							splitStr[0], divisions, true);
					if (divisions.size() > 0) {
						subject = divisions.get(0);
						searchRequest = new SearchRequestInfo(
								"ksap.clu.division");
						results = searchController.getResults(searchRequest,
								subject, number);
					} else {
						searchRequest = new SearchRequestInfo(
								"ksap.clu.division");
						results = searchController.getResults(searchRequest,
								subject, number);
					}
				}
				if (results == null || results.size() == 0) {
					results.add("No courses found");
				}

				/*
				 * TODO: uncomment this once a way to pass in atpid is known
				 * from the krad
				 */
				/*
				 * if (results.size() > 0) { results =
				 * additionalFiltering(results, atpId); }
				 */

			} else {
				results.add("No courses found");
			}
		} else {
			results.add("Search Term Should be at least 2 characters");
		}
		return results;
	}

    //unused
	public static List<String> additionalFiltering(List<String> results,
			String atpId) {
		int year = Calendar.getInstance().get(Calendar.YEAR) - 10;
		int resultsSize = results.size();
		for (int i = 0; i < resultsSize; i++) {
                EnrollmentStatusHelper.CourseCode courseCode = KsapFrameworkServiceLocator.getEnrollmentStatusHelper().getCourseDivisionAndNumber(results.get(i));
			List<CourseOfferingInfo> courseOfferingInfo = null;
			boolean removed = false;
			/* Filtering courses that are not offered in the given term */
			List<String> offerings;
			try {
				offerings = KsapFrameworkServiceLocator
						.getCourseOfferingService()
						.getCourseOfferingIdsByTermAndSubjectArea(
								atpId,
                                courseCode.getSubject(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("CO lookup error", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("CO lookup error", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("CO lookup error", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("CO lookup error", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("CO lookup error", e);
			}
			if (!offerings.contains(results.get(i))) {
				results.remove(results.get(i));
				resultsSize--;
				removed = true;
			}
			/* Filtering courses that are not offered for more than 10 years */
			if (!removed) {
                String values = String.format("%s, %s, %s", year, courseCode.getSubject(), courseCode.getNumber());
				try {
					courseOfferingInfo = KsapFrameworkServiceLocator
							.getCourseOfferingService()
							.searchForCourseOfferings(
									QueryByCriteria.Builder.fromPredicates(equalIgnoreCase(
											"values", values)),
									KsapFrameworkServiceLocator.getContext()
											.getContextInfo());
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("CO lookup error", e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("CO lookup error", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("CO lookup error", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("CO lookup error", e);
				}
				if (courseOfferingInfo == null) {
					results.remove(results.get(i));
					resultsSize--;
				}
			}
		}

		if (results.size() > 9) {
			List<String> trimmedList = new ArrayList<String>();
			for (String result : results) {
				trimmedList.add(result);
				if (trimmedList.size() == 10) {
					break;
				}

			}
			results = new ArrayList<String>();
			results.addAll(trimmedList);
		}
		return results;
	}
}
