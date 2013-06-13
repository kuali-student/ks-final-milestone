package org.kuali.student.ap.framework.context.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.DeconstructedCourseCode;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchRequest;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.Course;

// TODO: REVIEW https://svn.kuali.org/repos/student/contrib/myplan/trunk/ks-myplan/myplan-ui/src/main/java/edu/uw/kuali/student/myplan/util/CourseHelperImpl.java
public class DefaultCourseHelper implements CourseHelper, Serializable {

	private static final long serialVersionUID = 8000868050066661992L;

	@Override
	public Map<String, Map<String, Object>> getAllSectionStatus(
			Map<String, Map<String, Object>> status, String courseId,
			String termId) {
		try {
			for (CourseOfferingInfo co : KsapFrameworkServiceLocator
					.getCourseOfferingService()
					.getCourseOfferingsByCourseAndTerm(
							courseId,
							termId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo()))
				for (ActivityOfferingInfo ao : KsapFrameworkServiceLocator
						.getCourseOfferingService()
						.getActivityOfferingsByCourseOffering(
								co.getId(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo())) {
					Map<String, Object> enrl = new java.util.LinkedHashMap<String, Object>();
					enrl.put("enrollMaximum", ao.getMaximumEnrollment());
					// TODO: convert remaining attributes to service lookup
					// bean properties
					enrl.put("enrollCount", ao.getAttributeValue("enrollCount"));
					enrl.put("enrollOpen", ao.getAttributeValue("enrollOpen"));
					enrl.put("enrollEstimate",
							ao.getAttributeValue("enrollEstimate"));
					String key = "enrl_" + termId + "_" + ao.getActivityCode();
					status.put(key, enrl);
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
		return status;
	}

	public List<String> getScheduledTerms(Course course) {
		try {
			List<String> scheduledTerms = new java.util.LinkedList<String>();
			for (Term t : KsapFrameworkServiceLocator.getTermHelper()
					.getPublishedTerms())
				try {
					// TODO: This is inefficient without institutional override
					if (!KsapFrameworkServiceLocator
							.getCourseOfferingService()
							.getCourseOfferingsByCourseAndTerm(
									course.getId(),
									t.getId(),
									KsapFrameworkServiceLocator.getContext()
											.getContextInfo()).isEmpty())
						scheduledTerms.add(t.getId());
				} catch (DoesNotExistException e) {
				}
			return scheduledTerms;
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup failure", e);
		}
	}

	@Override
	public String getLastOfferedTermId(Course course) {
		ContextInfo ctx = KsapFrameworkServiceLocator.getContext()
				.getContextInfo();
		List<CourseOfferingInfo> courseOfferingInfo = null;
		try {

			List<String> courseIds = KsapFrameworkServiceLocator
					.getCourseService()
					.searchForCourseIds(
							QueryByCriteria.Builder.fromPredicates(PredicateFactory
									.equal("officialIdentifier.code",
											course.getCode())), ctx);
			String termId = KsapFrameworkServiceLocator.getTermHelper()
					.getOldestHistoricalTerm().getId();
			QueryByCriteria crit = QueryByCriteria.Builder
					.fromPredicates(PredicateFactory.and(PredicateFactory.in(
							"cluId",
							courseIds.toArray(new String[courseIds.size()])),
							PredicateFactory
									.greaterThanOrEqual("atpId", termId)));
			courseOfferingInfo = KsapFrameworkServiceLocator
					.getCourseOfferingService().searchForCourseOfferings(crit,
							ctx);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup failure", e);
		}
		if (courseOfferingInfo != null && courseOfferingInfo.size() > 0) {
			TermInfo lo;
			try {
				lo = KsapFrameworkServiceLocator.getAcademicCalendarService()
						.getTerm(
								courseOfferingInfo.get(0).getTermId(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
			} catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
				throw new IllegalArgumentException("AC lookup failure", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("AC lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("AC lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("AC lookup failure", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("AC lookup failure", e);
			}
			return lo.getName();
		} else
			return null;
	}

	/**
	 * Used to Split the course code into division and Code. eg: "COM 243" is
	 * returned as CourseCode with division=COM and number=243 and section=null.
	 * eg: "COM 243 A" is returned as CourseCode with division=COM , number=243
	 * and section=A.
	 * 
	 * @param courseCode
	 * @return
	 */
	@Override
	public DeconstructedCourseCode getCourseDivisionAndNumber(String courseCode) {
		// TODO: Evaluate whether or not this code is UW specific.
		String subject = null;
		String number = null;
		String activityCd = null;
		if (courseCode
				.matches(CourseSearchConstants.FORMATTED_COURSE_CODE_REGEX)) {
			String[] splitStr = courseCode.toUpperCase().split(
					CourseSearchConstants.SPLIT_DIGITS_ALPHABETS);
			subject = splitStr[0].trim();
			number = splitStr[1].trim();
		} else if (courseCode
				.matches(CourseSearchConstants.COURSE_CODE_WITH_SECTION_REGEX)) {
			activityCd = courseCode.substring(courseCode.lastIndexOf(" "),
					courseCode.length()).trim();
			courseCode = courseCode.substring(0, courseCode.lastIndexOf(" "))
					.trim();
			String[] splitStr = courseCode.toUpperCase().split(
					CourseSearchConstants.SPLIT_DIGITS_ALPHABETS);
			subject = splitStr[0].trim();
			number = splitStr[1].trim();
		} else if (courseCode
				.matches(CourseSearchConstants.UNFORMATTED_COURSE_CODE_REGEX)) {
			String[] splitStr = courseCode.toUpperCase().split(
					CourseSearchConstants.SPLIT_DIGITS_ALPHABETS);
			subject = splitStr[0].trim();
			number = splitStr[1].trim();
		}
		return new DefaultDeconstructedCourseCode(subject, number, activityCd);
	}

	/**
	 * Used to get the course Id for the given subject area and course number
	 * (CHEM, 120) Uses last scheduled term to calculate the course Id
	 * 
	 * @param subjectArea
	 * @param number
	 * @return
	 */
	@Override
	public String getCourseId(String subjectArea, String number) {
		return getCourseIdForTerm(subjectArea, number,
				KsapFrameworkServiceLocator.getTermHelper()
						.getLastScheduledTerm().getId());
	}

	/**
	 * Used to get the course Id for the given subject area and course number
	 * (CHEM, 120) for a given term
	 * 
	 * @param subjectArea
	 * @param number
	 * @return
	 */
	@Override
	public String getCourseIdForTerm(String subjectArea, String number,
			String termId) {
		List<SearchRequest> requests = new ArrayList<SearchRequest>();
		SearchRequestInfo request = new SearchRequestInfo(
				CourseSearchConstants.COURSE_SEARCH_FOR_COURSE_ID);
		request.addParam(CourseSearchConstants.SEARCH_REQUEST_SUBJECT_PARAM,
				subjectArea.trim());
		request.addParam(CourseSearchConstants.SEARCH_REQUEST_NUMBER_PARAM,
				number.trim());
		request.addParam(
				CourseSearchConstants.SEARCH_REQUEST_LAST_SCHEDULED_PARAM,
				termId);
		requests.add(request);
		SearchResultInfo searchResult = new SearchResultInfo();
		try {
			searchResult = KsapFrameworkServiceLocator.getCluService().search(
					request,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		}
		return searchResult.getRows().size() > 0 ? searchResult.getRows()
				.get(0).getCells().get(0).getValue() : null;
	}

	/**
	 * returns the courseInfo for the given courseId by verifying the courId to
	 * be a verifiedcourseId
	 * 
	 * @param courseId
	 * @return
	 */
	public CourseInfo getCourseInfo(String courseId) {
		try {
			return KsapFrameworkServiceLocator.getCourseService().getCourse(
					getVerifiedCourseId(courseId),
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
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
	}

	/**
	 * @param delimiter
	 * @param list
	 * @return
	 */
	public String joinStringsByDelimiter(char delimiter, String... list) {
		return StringUtils.join(list, delimiter);
	}

	/**
	 * Takes a courseId that can be either a version independent Id or a version
	 * dependent Id and returns a version dependent Id. In case of being passed
	 * in a version depend
	 * 
	 * @param courseId
	 * @return
	 */
	@Override
	public String getVerifiedCourseId(String courseId) {
		SearchRequestInfo req = new SearchRequestInfo(
				"myplan.course.version.id");
		req.addParam("courseId", courseId);
		req.addParam("courseId", courseId);
		req.addParam("lastScheduledTerm", KsapFrameworkServiceLocator
				.getTermHelper().getLastScheduledTerm().getId());
		SearchResult result;
		try {
			result = KsapFrameworkServiceLocator.getCluService().search(req,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		}
		for (SearchResultRow row : result.getRows())
			for (SearchResultCell cell : row.getCells())
				if ("lu.resultColumn.cluId".equals(cell.getKey()))
					return cell.getValue();
		return null;
	}

	/**
	 * retuns a SLN for given params
	 * 
	 * @param year
	 * @param term
	 * @param subject
	 * @param number
	 * @param activityCd
	 * @return
	 */
	public String getSLN(String year, String term, String subject,
			String number, String activityCd) {
		String activityId = joinStringsByDelimiter(':', year, term, subject,
				number, activityCd);
		ActivityOfferingDisplayInfo activityOfferingInfo = null;
		try {
			activityOfferingInfo = KsapFrameworkServiceLocator
					.getCourseOfferingService().getActivityOfferingDisplay(
							activityId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			if (activityOfferingInfo != null)
				for (AttributeInfo attributeInfo : activityOfferingInfo
						.getAttributes())
					if (attributeInfo.getKey().equalsIgnoreCase("SLN"))
						return attributeInfo.getValue();
			return null;
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup error", e);
		}
	}

	/**
	 * builds the refObjId for activity PlanItems (eg: '2013:2:CHEM:152:A')
	 * 
	 * @param atpId
	 * @param subject
	 * @param number
	 * @param activityCd
	 * @return
	 */
	public String buildActivityRefObjId(String atpId, String subject,
			String number, String activityCd) {
		YearTerm yearTerm = KsapFrameworkServiceLocator.getTermHelper()
				.getYearTerm(atpId);
		return joinStringsByDelimiter(':',
				Integer.toString(yearTerm.getYear()), yearTerm.getTermName(),
				subject, number, activityCd);
	}

	/**
	 * returns the course code from given activityId
	 * <p/>
	 * eg: for activityId '2013:2:CHEM:152:A' course code CHEM 152 is returned
	 * 
	 * @param activityId
	 * @return
	 */
	public String getCourseCdFromActivityId(String activityId) {
		ActivityOfferingDisplayInfo activityDisplayInfo;
		try {
			activityDisplayInfo = KsapFrameworkServiceLocator
					.getCourseOfferingService().getActivityOfferingDisplay(
							activityId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup error", e);
		}
		assert activityDisplayInfo != null : "activity id " + activityId
				+ " returned null";

		String courseOfferingId = null;
		for (AttributeInfo attributeInfo : activityDisplayInfo.getAttributes())
			if ("PrimaryActivityOfferingId".equalsIgnoreCase(attributeInfo
					.getKey()))
				courseOfferingId = attributeInfo.getValue();
		assert courseOfferingId != null : "activity id " + activityId
				+ " missing PrimaryActiveOfferingId";

		try {
			CourseOfferingInfo courseOfferingInfo = KsapFrameworkServiceLocator
					.getCourseOfferingService().getCourseOffering(
							courseOfferingId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			return courseOfferingInfo != null ? courseOfferingInfo
					.getCourseCode() : null;
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup error", e);
		}
	}

	/**
	 * returns the section code from given activityId
	 * 
	 * eg: for activityId '2013:2:CHEM:152:A' section code A is returned
	 * 
	 * @param activityId
	 * @return
	 */
	public String getCodeFromActivityId(String activityId) {
		try {
			ActivityOfferingDisplayInfo activityDisplayInfo = KsapFrameworkServiceLocator
					.getCourseOfferingService().getActivityOfferingDisplay(
							activityId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			return activityDisplayInfo != null ? activityDisplayInfo
					.getActivityOfferingCode() : null;
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CO lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CO lookup error", e);
		}
	}

}
