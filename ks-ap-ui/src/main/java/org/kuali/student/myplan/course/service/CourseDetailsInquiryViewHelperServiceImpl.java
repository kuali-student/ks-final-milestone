package org.kuali.student.myplan.course.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.myplan.academicplan.infc.LearningPlan;
import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.course.util.CreditsFormatter;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.myplan.plan.util.AtpHelper;
import org.kuali.student.myplan.plan.util.DateFormatHelper;
import org.kuali.student.myplan.plan.util.EnumerationHelper;
import org.kuali.student.myplan.plan.util.OrgHelper;
import org.kuali.student.myplan.utils.CourseLinkBuilder;
import org.kuali.student.myplan.utils.UserSessionHelper;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

@SuppressWarnings("deprecation")
public class CourseDetailsInquiryViewHelperServiceImpl extends
		KualiInquirableImpl {

	private static final long serialVersionUID = 4933435913745621395L;

	private final Logger logger = Logger
			.getLogger(CourseDetailsInquiryViewHelperServiceImpl.class);

	private transient CourseService courseService;

	private transient CourseOfferingService courseOfferingService;

	private transient AcademicCalendarService academicCalendarService;

	private transient AtpService atpService;

	private transient CluService cluService;

	private transient AcademicPlanService academicPlanService;

	private transient AcademicRecordService academicRecordService;

	private transient CourseInfo courseInfo;

	private transient boolean isAcademicCalendarServiceUp = true;

	private transient boolean isAcademicRecordServiceUp = true;

	private transient boolean isCourseOfferingServiceUp = true;

	public boolean isAcademicCalendarServiceUp() {
		return isAcademicCalendarServiceUp;
	}

	public void setAcademicCalendarServiceUp(boolean academicCalendarServiceUp) {
		isAcademicCalendarServiceUp = academicCalendarServiceUp;
	}

	public boolean isAcademicRecordServiceUp() {
		return isAcademicRecordServiceUp;
	}

	public void setAcademicRecordServiceUp(boolean academicRecordServiceUp) {
		isAcademicRecordServiceUp = academicRecordServiceUp;
	}

	public boolean isCourseOfferingServiceUp() {
		return isCourseOfferingServiceUp;
	}

	public void setCourseOfferingServiceUp(boolean courseOfferingServiceUp) {
		isCourseOfferingServiceUp = courseOfferingServiceUp;
	}

	// TODO: These should be changed to an ehCache spring bean
	private Map<String, List<OrgInfo>> campusLocationCache;
	private HashMap<String, Map<String, String>> hashMap;

	public HashMap<String, Map<String, String>> getHashMap() {
		if (this.hashMap == null) {
			this.hashMap = new HashMap<String, Map<String, String>>();
		}
		return this.hashMap;
	}

	public void setHashMap(HashMap<String, Map<String, String>> hashMap) {
		this.hashMap = hashMap;
	}

	public Map<String, List<OrgInfo>> getCampusLocationCache() {
		if (this.campusLocationCache == null) {
			this.campusLocationCache = new HashMap<String, List<OrgInfo>>();
		}
		return this.campusLocationCache;
	}

	public void setCampusLocationCache(
			Map<String, List<OrgInfo>> campusLocationCache) {
		this.campusLocationCache = campusLocationCache;
	}

	protected CluService getCluService() {
		if (this.cluService == null) {
			this.cluService = (CluService) GlobalResourceLoader
					.getService(new QName(CluServiceConstants.CLU_NAMESPACE,
							"CluService"));
		}
		return this.cluService;
	}

	private transient CourseLinkBuilder courseLinkBuilder;

	// default is to create real links
	private CourseLinkBuilder.LINK_TEMPLATE courseLinkTemplateStyle = CourseLinkBuilder.LINK_TEMPLATE.COURSE_DETAILS;

	public CourseLinkBuilder getCourseLinkBuilder() {
		if (courseLinkBuilder == null) {
			this.courseLinkBuilder = new CourseLinkBuilder();
		}
		return courseLinkBuilder;
	}

	public void setCourseLinkBuilder(CourseLinkBuilder courseLinkBuilder) {
		this.courseLinkBuilder = courseLinkBuilder;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	@Override
	public CourseDetails retrieveDataObject(
			@SuppressWarnings("rawtypes") Map fieldValues) {
		String studentId = UserSessionHelper.getStudentId();
		return retrieveCourseDetails(
				(String) fieldValues.get(PlanConstants.PARAM_COURSE_ID),
				studentId);
	}

	/**
	 * Populates course with catalog information (title, id, code, description)
	 * and next offering information. Other properties are left empty and a flag
	 * is set to indicate only summary view
	 * 
	 * @param courseId
	 * @return
	 */
	public CourseDetails retrieveCourseSummary(String courseId, String studentId) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setSummaryOnly(true);

		CourseInfo course = getCourseInfo();
		try {
			course = getCourseService().getCourse(courseId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.",
					courseId), e);
		} catch (Exception e) {
			throw new RuntimeException("Query failed.", e);
		}

		courseDetails.setCourseId(course.getId());
		courseDetails.setCode(course.getCode());
		String str = null;
		if (course.getDescr() != null) {
			str = course.getDescr().getFormatted();
		}
		if (str != null && str.contains("Offered:")) {
			str = str.substring(0, str.indexOf("Offered"));
		}
		List<String> prerequisites = new ArrayList<String>();

		if (str != null && str.contains("Prerequisite:")) {
			String req = (CourseLinkBuilder.makeLinks(str.substring(
					str.indexOf("Prerequisite:"), str.length()),
					courseLinkTemplateStyle, KsapFrameworkServiceLocator
							.getContext().getContextInfo()));
			req = req.substring(req.indexOf("Prerequisite:"), req.length());
			req = req.replace("Prerequisite:", "").trim();
			req = req.substring(0, 1).toUpperCase()
					.concat(req.substring(1, req.length()));
			prerequisites.add(req);

			str = str.substring(0, str.indexOf("Prerequisite:"));
		}
		if (str != null) {
			getCourseLinkBuilder();
			str = CourseLinkBuilder.makeLinks(str, KsapFrameworkServiceLocator
					.getContext().getContextInfo());
		}

		courseDetails.setRequisites(prerequisites);
		courseDetails.setCourseDescription(str);
		courseDetails.setCredit(CreditsFormatter.formatCredits(course));
		courseDetails.setCourseTitle(course.getCourseTitle());

		String[] lastTermYear = AtpHelper.atpIdToTermNameAndYear(course
				.getEndTerm());
		if (!lastTermYear[1].equals("9999")) {
			courseDetails.setLastEffectiveTerm(AtpHelper.atpIdToTermName(course
					.getEndTerm()));
		}

		courseDetails.setTermsOffered(course.getTermsOffered());

		return courseDetails;
	}

	public CourseDetails retrieveCourseDetails(String courseId, String studentId) {
		CourseDetails courseDetails = retrieveCourseSummary(courseId, studentId);
		courseDetails.setSummaryOnly(false);

		CourseInfo course = null;
		try {
			course = getCourseService().getCourse(courseId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.",
					courseId), e);
		} catch (Exception e) {
			throw new RuntimeException("Query failed.", e);
		}

		// Campus Locations
		List<OrgInfo> orgInfoList = OrgHelper.getOrgInfo(
				CourseSearchConstants.CAMPUS_LOCATION,
				CourseSearchConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST,
				CourseSearchConstants.ORG_TYPE_PARAM,
				KsapFrameworkServiceLocator.getContext().getContextInfo());
		getCampusLocationCache().put(CourseSearchConstants.CAMPUS_LOCATION,
				orgInfoList);

		List<String> campusLocations = new ArrayList<String>();

		for (String campus : getCampusLocationsOfferedIn(courseId,
				KsapFrameworkServiceLocator.getContext().getContextInfo())) {

			for (OrgInfo orgInfo : orgInfoList) {
				if (campus.equalsIgnoreCase(orgInfo.getId())) {
					campusLocations.add(orgInfo.getLongName());
					break;
				}
			}
		}

		courseDetails.setCampusLocations(campusLocations);

		// Get only the abbre_val of gen ed requirements
		List<String> abbrGenEdReqs = new ArrayList<String>();
		List<AttributeInfo> abbrAttributes = course.getAttributes();
		for (AttributeInfo entry : abbrAttributes) {
			if ("Y".equals(entry.getValue())
					&& entry.getKey().startsWith(
							CourseSearchConstants.GEN_EDU_REQUIREMENTS_PREFIX)) {
				abbrGenEdReqs.add(EnumerationHelper.getEnumAbbrValForCode(entry
						.getKey()));
			}
		}
		courseDetails.setAbbrGenEdRequirements(abbrGenEdReqs);

		// Get general education requirements.
		List<String> genEdReqs = new ArrayList<String>();
		List<AttributeInfo> attributes = course.getAttributes();
		for (AttributeInfo entry : attributes) {
			if ("Y".equals(entry.getValue())
					&& entry.getKey().startsWith(
							CourseSearchConstants.GEN_EDU_REQUIREMENTS_PREFIX)) {
				EnumeratedValueInfo e = EnumerationHelper.getGenEdReqEnumInfo(
						entry.getKey(), KsapFrameworkServiceLocator
								.getContext().getContextInfo());
				String genEdText = String.format("%s (%s)", e.getValue(),
						e.getAbbrevValue());
				genEdReqs.add(genEdText);
			}
		}
		courseDetails.setGenEdRequirements(genEdReqs);

		/*
		 * Use the course offering service to see if the course is being offered
		 * in the selected term. Note: In the UW implementation of the Course
		 * Offering service, course id is actually course code.
		 */
		try {
			// Fetch the available terms from the Academic Calendar Service.
			if (isAcademicCalendarServiceUp && isCourseOfferingServiceUp()) {
				List<TermInfo> termInfos = null;
				try {
					termInfos = getAcademicCalendarService()
							.searchForTerms(
									QueryByCriteria.Builder.fromPredicates(equalIgnoreCase(
											"query", PlanConstants.PUBLISHED)),
									KsapFrameworkServiceLocator.getContext()
											.getContextInfo());
				} catch (Exception e) {
					logger.error("Web service call failed.", e);
					// Create an empty list to Avoid NPE below allowing the data
					// object to be fully initialized.
					termInfos = new ArrayList<TermInfo>();
				}

				List<String> scheduledTerms = new ArrayList<String>();
				for (TermInfo term : termInfos) {
					String key = term.getId();
					String subject = course.getSubjectArea();

					List<String> offerings = getCourseOfferingService()
							.getCourseOfferingIdsByTermAndSubjectArea(
									key,
									subject,
									KsapFrameworkServiceLocator.getContext()
											.getContextInfo());

					if (offerings.contains(course.getCode())) {
						scheduledTerms.add(term.getName());
					}
				}

				courseDetails.setScheduledTerms(scheduledTerms);
			} else {
				courseDetails.setScheduledTerms(new ArrayList<String>());
			}

			AcademicPlanService academicPlanService = getAcademicPlanService();

			// Get the first learning plan. There should only be one ...
			String planTypeKey = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;
			List<LearningPlanInfo> plans = academicPlanService
					.getLearningPlansForStudentByType(studentId, planTypeKey,
							PlanConstants.CONTEXT_INFO);
			if (plans.size() > 0) {
				LearningPlan plan = plans.get(0);

				// Fetch the plan items which are associated with the plan.
				List<PlanItemInfo> planItemsInPlan = academicPlanService
						.getPlanItemsInPlan(plan.getId(),
								PlanConstants.CONTEXT_INFO);

				List<PlanItemDataObject> plannedList = new ArrayList<PlanItemDataObject>();
				List<PlanItemDataObject> backupList = new ArrayList<PlanItemDataObject>();

				// Iterate through the plan items and set flags to indicate
				// whether the item is a planned/backup or saved course.
				for (PlanItem planItemInPlanTemp : planItemsInPlan) {
					if (planItemInPlanTemp.getRefObjectId().equals(
							courseDetails.getCourseId())) {
						// Assuming type is planned or backup if not wishlist.
						if (planItemInPlanTemp.getTypeKey().equals(
								PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST)) {
							courseDetails.setSavedItemId(planItemInPlanTemp
									.getId());
							String dateStr = planItemInPlanTemp.getMeta()
									.getCreateTime().toString();
							dateStr = DateFormatHelper.getDateFomatted(dateStr);
							courseDetails.setSavedItemDateCreated(dateStr);
						} else if (planItemInPlanTemp.getTypeKey().equals(
								PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
							plannedList.add(PlanItemDataObject
									.build(planItemInPlanTemp));

						} else if (planItemInPlanTemp.getTypeKey().equals(
								PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
							backupList.add(PlanItemDataObject
									.build(planItemInPlanTemp));

						}
					}
				}
				if (plannedList.size() > 0) {
					courseDetails.setPlannedList(plannedList);
				}
				if (backupList.size() > 0) {
					courseDetails.setBackupList(backupList);
				}
			}
		} catch (Exception e) {
			logger.error(
					"Exception loading course offering for:" + course.getCode(),
					e);
		}

		// Curriculum
		String courseCode = courseDetails.getCode();
		String subject = null;
		String number = null;
		if (courseCode != null) {
			String[] splitStr = courseCode
					.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
			subject = splitStr[0];
			number = splitStr[1];
			String temp = getTitle(subject);
			StringBuffer value = new StringBuffer();
			value = value.append(temp);
			value = value.append(" (").append(subject.trim()).append(")");

			courseDetails.setCurriculumTitle(value.toString());
		}
		// If course not scheduled for future terms, Check for the last term
		// when course was offered
		if (isCourseOfferingServiceUp()) {
			if (courseDetails.getScheduledTerms().size() == 0) {
				int year = Calendar.getInstance().get(Calendar.YEAR) - 10;
				List<CourseOfferingInfo> courseOfferingInfo = null;
				try {
					// The right strategy would be using the multiple equal
					// predicates joined using an and
					String values = String.format("%s, %s, %s", year, subject,
							number);
					courseOfferingInfo = getCourseOfferingService()
							.searchForCourseOfferings(
									QueryByCriteria.Builder.fromPredicates(equalIgnoreCase(
											"values", values)),
									KsapFrameworkServiceLocator.getContext()
											.getContextInfo());
				} catch (Exception e) {
					String[] params = {};
					GlobalVariables.getMessageMap().putWarningForSectionId(
							CourseSearchConstants.COURSE_SEARCH_PAGE,
							PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
					logger.error("Could not load courseOfferingInfo list.", e);
				}
				if (courseOfferingInfo != null && courseOfferingInfo.size() > 0) {
					String lastOffered = courseOfferingInfo.get(0).getTermId();
					lastOffered = lastOffered
							.substring(0, 1)
							.toUpperCase()
							.concat(lastOffered.substring(1,
									lastOffered.length()));
					courseDetails.setLastOffered(lastOffered);
				}
			}
		}
		/*********
		 * Implementation to get the Academic Record Data from the SWS and set
		 * that to CourseDetails acedRecordList
		 ***************/
		if (isAcademicRecordServiceUp) {
			List<AcademicRecordDataObject> academicRecordDataObjectList = new ArrayList<AcademicRecordDataObject>();
			List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
			try {
				studentCourseRecordInfos = getAcademicRecordService()
						.getCompletedCourseRecords(studentId,
								PlanConstants.CONTEXT_INFO);
			} catch (Exception e) {
				logger.error("Could not retrieve StudentCourseRecordInfo from the SWS");
			}

			if (studentCourseRecordInfos.size() > 0) {

				for (StudentCourseRecordInfo studentInfo : studentCourseRecordInfos) {
					AcademicRecordDataObject academicRecordDataObject = new AcademicRecordDataObject();
					academicRecordDataObject
							.setAtpId(studentInfo.getTermName());
					academicRecordDataObject.setPersonId(studentInfo
							.getPersonId());
					academicRecordDataObject.setCourseCode(studentInfo
							.getCourseCode());
					academicRecordDataObject.setCourseTitle(studentInfo
							.getCourseTitle());
					academicRecordDataObject.setCourseId(studentInfo.getId());
					academicRecordDataObject.setCredit(studentInfo
							.getCreditsEarned());
					academicRecordDataObject.setGrade(studentInfo
							.getCalculatedGradeValue());
					academicRecordDataObject.setRepeated(studentInfo
							.getIsRepeated());
					academicRecordDataObjectList.add(academicRecordDataObject);
					if (courseDetails.getCourseId().equalsIgnoreCase(
							studentInfo.getId())) {
						String[] str = AtpHelper
								.atpIdToTermNameAndYear(studentInfo
										.getTermName());
						courseDetails.getAcademicTerms().add(
								str[0] + " " + str[1]);
					}
				}
				if (academicRecordDataObjectList.size() > 0) {
					courseDetails.setAcadRecList(academicRecordDataObjectList);

				}
			}
		}

		return courseDetails;
	}

	/**
	 * To get the title for the respective display name
	 * 
	 * @param display
	 * @return
	 */
	protected String getTitle(String display) {
		String titleValue = null;
		Map<String, String> subjects = new HashMap<String, String>();
		if (!this.getHashMap().containsKey(CourseSearchConstants.SUBJECT_AREA)) {
			subjects = OrgHelper.getTrimmedSubjectAreas();
			getHashMap().put(CourseSearchConstants.SUBJECT_AREA, subjects);

		} else {
			subjects = getHashMap().get(CourseSearchConstants.SUBJECT_AREA);
		}

		if (subjects != null && subjects.size() > 0) {
			titleValue = subjects.get(display.trim());
		}

		return titleValue;
	}

	private List<String> getCampusLocationsOfferedIn(String courseId,
			ContextInfo context) {
		List<String> campusLocations = new ArrayList<String>();
		SearchRequestInfo searchRequest = new SearchRequestInfo(
				"myplan.course.getCampusLocations");
		searchRequest.addParam("cluId", courseId);
		// TODO: Fix when version issue for course is addressed
		// searchRequest.addParam("currentTerm", AtpHelper.getCurrentAtpId());
		searchRequest.addParam("lastScheduledTerm",
				AtpHelper.getLastScheduledAtpId());
		SearchResult searchResult = null;
		try {
			searchResult = getCluService().search(searchRequest, context);
		} catch (MissingParameterException e) {
			logger.error("Error in CLU search", e);
		} catch (InvalidParameterException e) {
			logger.error("Error in CLU search", e);
		} catch (OperationFailedException e) {
			logger.error("Error in CLU search", e);
		} catch (PermissionDeniedException e) {
			logger.error("Error in CLU search", e);
		}

		if (searchResult != null) {
			for (SearchResultRow row : searchResult.getRows()) {
				campusLocations.add(OrgHelper.getCellValue(row,
						"lu.resultColumn.campusVal"));
			}
		}
		return campusLocations;
	}

	public AcademicRecordService getAcademicRecordService() {
		if (this.academicRecordService == null) {
			// TODO: Use constants for namespace.
			this.academicRecordService = (AcademicRecordService) GlobalResourceLoader
					.getService(new QName(
							"http://student.kuali.org/wsdl/academicrecord",
							"arService"));
		}
		return this.academicRecordService;
	}

	public void setAcademicRecordService(
			AcademicRecordService academicRecordService) {
		this.academicRecordService = academicRecordService;
	}

	protected synchronized CourseService getCourseService() {
		if (this.courseService == null) {
			this.courseService = (CourseService) GlobalResourceLoader
					.getService(new QName(
							CourseServiceConstants.COURSE_NAMESPACE,
							"CourseService"));
		}
		return this.courseService;
	}

	public synchronized void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * Provides an instance of the AtpService client.
	 */
	protected AtpService getAtpService() {
		if (atpService == null) {
			// TODO: Namespace should not be hard-coded.
			atpService = (AtpService) GlobalResourceLoader
					.getService(new QName("http://student.kuali.org/wsdl/atp",
							"AtpService"));
		}
		return this.atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	protected CourseOfferingService getCourseOfferingService() {
		if (this.courseOfferingService == null) {
			// TODO: Use constants for namespace.
			this.courseOfferingService = (CourseOfferingService) GlobalResourceLoader
					.getService(new QName(
							"http://student.kuali.org/wsdl/courseOffering",
							"coService"));
		}
		return this.courseOfferingService;
	}

	public void setCourseOfferingService(
			CourseOfferingService courseOfferingService) {
		this.courseOfferingService = courseOfferingService;
	}

	protected AcademicCalendarService getAcademicCalendarService() {
		if (this.academicCalendarService == null) {
			this.academicCalendarService = (AcademicCalendarService) GlobalResourceLoader
					.getService(new QName(
							AcademicCalendarServiceConstants.NAMESPACE,
							AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
		}
		return this.academicCalendarService;
	}

	public void setAcademicCalendarService(
			AcademicCalendarService academicCalendarService) {
		this.academicCalendarService = academicCalendarService;
	}

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

	// TODO: determine ATP type functionality expectations - this is in ks-1.3,
	// not in CM-2.0.
	// Does it need to move from 1.3 to 2.0 within KS, or has this been
	// explicitly removed?
	// /mwfyffe
	/**
	 * Initializes ATP term cache. AtpSeasonalTypes rarely change, so fetch them
	 * all and store them in a Map. private synchronized void
	 * initializeAtpTypesCache() {
	 * 
	 * if (null == atpCache || atpCache.isEmpty()) { atpCache = new
	 * HashMap<String, String>(); List<String> atpTypeInfos; try { } catch
	 * (OperationFailedException e) { logger.error("ATP types lookup failed.",
	 * e); return; } for (AtpTypeInfo ti : atpTypeInfos) {
	 * atpCache.put(ti.getId(), ti.getName().substring(0, 1).toUpperCase() +
	 * ti.getName().substring(1)); } } }
	 */
}