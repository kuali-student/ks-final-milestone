package org.kuali.student.myplan.course.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.EnrollmentStatusHelper;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.myplan.academicplan.infc.LearningPlan;
import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.myplan.course.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.dataobject.CourseOfferingInstitution;
import org.kuali.student.myplan.course.dataobject.CourseOfferingTerm;
import org.kuali.student.myplan.course.dataobject.CourseSummaryDetails;
import org.kuali.student.myplan.course.dataobject.MeetingDetails;
import org.kuali.student.myplan.course.util.CreditsFormatter;
import org.kuali.student.myplan.plan.controller.PlanController;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseSummary;
import org.kuali.student.myplan.utils.CourseLinkBuilder;
import org.kuali.student.myplan.utils.TimeStringMillisConverter;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.room.infc.Building;
import org.kuali.student.r2.core.room.infc.Room;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
public class CourseDetailsInquiryHelperImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = 4933435913745621395L;

	private static final Logger LOG = Logger
			.getLogger(CourseDetailsInquiryHelperImpl.class);

	private final static String[] WEEKDAYS_FIRST_LETTER = { "M", "T", "W",
			"Th", "F", "Sa", "Su" };

	public static final String NOT_OFFERED_IN_LAST_TEN_YEARS = "Not offered for more than 10 years.";

	private transient CourseService courseService;

	private transient CourseOfferingService courseOfferingService;

	private transient AcademicCalendarService academicCalendarService;

	private transient AtpService atpService;

	private transient CluService cluService;

	private transient AcademicPlanService academicPlanService;

	private transient AcademicRecordService academicRecordService;

	// TODO: These should be changed to an ehCache spring bean
	private Map<String, List<OrgInfo>> campusLocationCache;
	// private Map<String, String> atpCache;
	private HashMap<String, Map<String, String>> hashMap;
	private transient CourseInfo courseInfo;

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
			this.cluService = KsapFrameworkServiceLocator.getCluService();
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
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();
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
	public CourseSummaryDetails retrieveCourseSummaryById(String courseId) {
		/**
		 * If version identpendent Id provided, retrieve the right course
		 * version Id based on current term/date else get the same id as the
		 * provided course version specific Id
		 */
		String verifiedCourseId = getVerifiedCourseId(courseId);
		try {
			CourseInfo course = getCourseService().getCourse(verifiedCourseId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			CourseSummaryDetails courseDetails = retrieveCourseSummary(course);

			return courseDetails;
		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.",
					courseId), e);
		} catch (Exception e) {
			throw new RuntimeException("Query failed.", e);
		}
	}

	/**
	 * Populates course with catalog information (title, id, code, description)
	 * and next offering information. Other properties are left empty and a flag
	 * is set to indicate only summary view
	 * 
	 * @param course
	 * @return
	 */
	protected CourseSummaryDetails retrieveCourseSummary(CourseInfo course) {

		if (null == course) {
			return null;
		}

		CourseSummaryDetails courseDetails = new CourseSummaryDetails();

		courseDetails.setVersionIndependentId(course.getVersion()
				.getVersionIndId());
		courseDetails.setCourseId(course.getId());
		courseDetails.setCode(course.getCode());
		courseDetails.setCredit(CreditsFormatter.formatCredits(course));
		courseDetails.setCourseTitle(course.getCourseTitle());
		courseDetails.setSubjectArea(course.getSubjectArea().trim());
		courseDetails.setCourseNumber(course.getCourseNumberSuffix());

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

		List<String> cto = course.getTermsOffered();
		if (cto != null) {
			List<String> to = new java.util.ArrayList<String>();
			try {
				for (TypeInfo ti : KsapFrameworkServiceLocator.getTypeService()
						.getTypesByKeys(
								cto,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo()))
					to.add(ti.getName());
			} catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Type lookup error", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Type lookup error", e);
			}
			courseDetails.setTermsOffered(to);
		}

		// Load campus location map
		List<org.kuali.student.r2.core.organization.dto.OrgInfo> orgInfoList = KsapFrameworkServiceLocator
				.getOrgHelper().getOrgInfo(
						CourseSearchConstants.CAMPUS_LOCATION,
						CourseSearchConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST,
						CourseSearchConstants.ORG_TYPE_PARAM,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
		getCampusLocationCache().put(CourseSearchConstants.CAMPUS_LOCATION,
				orgInfoList);

		for (AttributeInfo attributeInfo : course.getAttributes()) {
			String key = attributeInfo.getKey();
			String value = attributeInfo.getValue();

			// -- Gen Ed requirements
			if ("Y".equals(value)
					&& key.startsWith(CourseSearchConstants.GEN_EDU_REQUIREMENTS_PREFIX)) {

				// Get only the abbre_val of gen ed requirements
				String abbrev = KsapFrameworkServiceLocator
						.getEnumerationHelper().getEnumAbbrValForCode(key);
				courseDetails.getAbbrGenEdRequirements().add(abbrev);

				// Get general education requirements.
				EnumeratedValueInfo info = KsapFrameworkServiceLocator
						.getEnumerationHelper().getGenEdReqEnumInfo(
								key,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
				String genEdText = String.format("%s (%s)", info.getValue(),
						info.getAbbrevValue());
				courseDetails.getGenEdRequirements().add(genEdText);
			}

			// -- Campus Locations
			if (key.startsWith(CourseSearchConstants.CAMPUS_LOCATION)) {
				for (OrgInfo campusOrg : orgInfoList) {
					if (campusOrg.getId().equals(value)) {
						courseDetails.getCampusLocations().add(
								campusOrg.getLongName());
					}
				}
			}
		}

		// -- Curriculum Title
		Map<String, String> subjectAreaMap = KsapFrameworkServiceLocator
				.getOrgHelper().getTrimmedSubjectAreas();
		courseDetails.setCurriculumTitle(subjectAreaMap.get(course
				.getSubjectArea().trim()));

		// -- Scheduled Terms
		try {
			// Fetch the available terms from the Academic Calendar Service.
			CourseOfferingService cos = getCourseOfferingService();
			Set<String> scheduledTermIds = new java.util.HashSet<String>();
			for (CourseOffering co : cos.getCourseOfferingsByCourse(course
					.getId(), KsapFrameworkServiceLocator.getContext()
					.getContextInfo()))
				if (scheduledTermIds.contains(co.getTermId()))
					continue;
				else
					scheduledTermIds.add(co.getTermId());

			List<String> scheduledTerms = new java.util.LinkedList<String>();
			for (TermInfo ti : getAcademicCalendarService().searchForTerms(
					QueryByCriteria.Builder.fromPredicates(like(
                            "atpStatus", PlanConstants.PUBLISHED)),
					KsapFrameworkServiceLocator.getContext().getContextInfo()))
				if (scheduledTermIds.contains(ti.getId()))
					scheduledTerms.add(ti.getId());
			courseDetails.setScheduledTerms(scheduledTerms);
		} catch (Exception e) {
			LOG.error("Error Retrieving ScheduleTerms", e);
		}

		if (courseDetails.getScheduledTerms().size() == 0) {
			List<CourseOfferingInfo> courseOfferingInfo = null;
			try {
				courseOfferingInfo = getCourseOfferingService()
						.getCourseOfferingsByCourse(
								course.getId(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
			} catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
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
			if (courseOfferingInfo != null && courseOfferingInfo.size() > 0) {
				TermInfo lo;
				try {
					lo = getAcademicCalendarService().getTerm(
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
				courseDetails.setLastOffered(lo.getName());
			}
		}

		return courseDetails;

	}

	/**
	 * Method returns a courseDetails object with course summary, academic
	 * record, course offering and planned information for the course
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	public CourseDetails retrieveCourseDetails(String courseId, String studentId) {
		CourseDetails courseDetails = new CourseDetails();

		ContextInfo context = KsapFrameworkServiceLocator.getContext()
				.getContextInfo();

		CourseInfo course;
		try {
			/* Get version verified course */
			course = getCourseService().getCourse(
					getVerifiedCourseId(courseId), context);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Course lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Course lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Course lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Course lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Course lookup failure", e);
		}

		CourseSummaryDetails courseSummaryDetails = retrieveCourseSummary(course);
		courseDetails.setCourseSummaryDetails(courseSummaryDetails);

		// Course Plan + Academic Records
		courseDetails.setPlannedCourseSummary(getPlannedCourseSummary(course,
				studentId));

		// Course offerings
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if (request.getParameter("section_term") != null) {
			String termId = KsapFrameworkServiceLocator.getTermHelper()
					.getTerm(request.getParameter("section_term")).getName();
			List<String> termList = new ArrayList<String>();
			termList.add(termId);
			courseDetails
					.setCourseOfferingInstitutionList(getCourseOfferingInstitutions(
							course, termList));
		} else {
			courseDetails
					.setCourseOfferingInstitutionList(getCourseOfferingInstitutions(
							course, courseDetails.getCourseSummaryDetails()
									.getScheduledTerms()));
		}

		EnrollmentStatusHelper enrollmentStatusHelper = KsapFrameworkServiceLocator
				.getEnrollmentStatusHelper();
		for (CourseOfferingInstitution institution : courseDetails
				.getCourseOfferingInstitutionList()) {
			for (CourseOfferingTerm term : institution
					.getCourseOfferingTermList()) {
				for (ActivityOfferingItem activity : term
						.getActivityOfferingItemList()) {
					YearTerm yt = term.getYearTerm();
					String year = Integer.toString(yt.getYear());
					String quarter = yt.getTermName();
					String curric = courseSummaryDetails.getSubjectArea();
					String num = courseSummaryDetails.getCourseNumber();
					String sectionID = activity.getCode();
					try {
						String[] enrollmentFields = enrollmentStatusHelper
								.populateEnrollmentFields(year, quarter,
										curric, num, sectionID);
						activity.setEnrollCount(enrollmentFields[0]);
						activity.setEnrollMaximum(enrollmentFields[1]);
						activity.setEnrollEstimate(enrollmentFields[2]);
					} catch (Exception e) {
						LOG.warn("cannot populate enrollment fields", e);
					}
				}
			}
		}

		return courseDetails;
	}

	/**
	 * Retrieves plan summary for the course. Finds all the plan, backup and
	 * academic record information spread across multiple terms for the provided
	 * course Id and Student Id
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	public PlannedCourseSummary getPlannedCourseSummaryById(String courseId,
			String studentId) {

		/**
		 * If version identpendent Id provided, retrieve the right course
		 * version Id based on current term/date else get the same id as the
		 * provided course version specific Id
		 */
		String verifiedCourseId = getVerifiedCourseId(courseId);
		try {
			CourseInfo course = getCourseService().getCourse(verifiedCourseId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			return getPlannedCourseSummary(course, studentId);
		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.",
					courseId), e);
		} catch (Exception e) {
			throw new RuntimeException("Query failed.", e);
		}

	}

	/**
	 * Retrieves plan summary for the course. Finds all the plan, backup and
	 * academic record information spread across multiple terms for the provided
	 * course and student Id
	 * 
	 * @param course
	 * @param studentId
	 * @return
	 */
	public PlannedCourseSummary getPlannedCourseSummary(CourseInfo course,
			String studentId) {

		PlannedCourseSummary plannedCourseSummary = new PlannedCourseSummary();

		// Planned, backup and Saved Item
		AcademicPlanService academicPlanService = getAcademicPlanService();

		// Get the first learning plan. There should only be one ...
		String planTypeKey = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;
		try {
			List<LearningPlanInfo> plans = academicPlanService
					.getLearningPlansForStudentByType(studentId, planTypeKey,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			if (plans.size() > 0) {
				LearningPlan plan = plans.get(0);

				// Fetch the plan items which are associated with the plan.
				List<PlanItemInfo> planItemsInPlan = academicPlanService
						.getPlanItemsInPlan(plan.getId(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());

				// Iterate through the plan items and set flags to indicate
				// whether the item is a planned/backup or saved course.
				for (PlanItem planItemInPlanTemp : planItemsInPlan) {
					if (planItemInPlanTemp.getRefObjectId().equals(
							course.getId())) {
						// Assuming type is planned or backup if not wishlist.
						String typeKey = planItemInPlanTemp.getTypeKey();
						if (typeKey
								.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST)) {
							plannedCourseSummary
									.setSavedItemId(planItemInPlanTemp.getId());
							String dateStr = planItemInPlanTemp.getMeta()
									.getCreateTime().toString();
							dateStr = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER
									.format(DateFormatters.DEFAULT_DATE_FORMATTER
											.parse(dateStr.substring(0, 10)));
							plannedCourseSummary
									.setSavedItemDateCreated(dateStr);
						} else {
							PlanItemDataObject planItem = PlanItemDataObject
									.build(planItemInPlanTemp);
							if (typeKey
									.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
								plannedCourseSummary.getPlannedList().add(
										planItem);
							} else if (typeKey
									.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
								plannedCourseSummary.getBackupList().add(
										planItem);
							}
						}
					}
				}
			}
		} catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
			// Ignore and not load any plan data
		} catch (Exception e1) {
			LOG.error(" Error loading plan information for course :"
					+ course.getCode() + " " + e1.getMessage());
		}

		// Get Academic Record Data from the SWS and set that to CourseDetails
		// acadRecordList
		try {
			List<StudentCourseRecordInfo> studentCourseRecordInfos = getAcademicRecordService()
					.getCompletedCourseRecords(
							studentId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			for (StudentCourseRecordInfo studentInfo : studentCourseRecordInfos) {
				AcademicRecordDataObject acadrec = new AcademicRecordDataObject();
				acadrec.setAtpId(studentInfo.getTermName());
				acadrec.setPersonId(studentInfo.getPersonId());
				acadrec.setCourseCode(studentInfo.getCourseCode());
				acadrec.setCourseTitle(studentInfo.getCourseTitle());
				acadrec.setCourseId(studentInfo.getId());
				acadrec.setCredit(studentInfo.getCreditsEarned());
				acadrec.setGrade(studentInfo.getCalculatedGradeValue());
				acadrec.setRepeated(studentInfo.getIsRepeated());
				acadrec.setActivityCode(studentInfo.getActivityCode());

				if (course.getId().equalsIgnoreCase(studentInfo.getId())) {
					plannedCourseSummary.getAcadRecList().add(acadrec);

					YearTerm str = KsapFrameworkServiceLocator.getTermHelper()
							.getYearTerm(studentInfo.getTermName());
					plannedCourseSummary.getAcademicTerms().add(
							str.getTermName());
				}
			}
		} catch (Exception e) {
			LOG.error("Could not retrieve StudentCourseRecordInfo from the SWS");
		}

		return plannedCourseSummary;

	}

	/**
	 * Get courseOffering information broken down by institution code across the
	 * terms requested
	 * 
	 * @param courseId
	 * @param terms
	 * @return
	 */
	public List<CourseOfferingInstitution> getCourseOfferingInstitutionsById(
			String courseId, List<String> terms) {

		/**
		 * If version identpendent Id provided, retrieve the right course
		 * version Id based on current term/date else get the same id as the
		 * provided course version specific Id
		 */
		String verifiedCourseId = getVerifiedCourseId(courseId);
		try {
			CourseInfo course = getCourseService().getCourse(verifiedCourseId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			return getCourseOfferingInstitutions(course, terms);
		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.",
					courseId), e);
		} catch (Exception e) {
			throw new RuntimeException("Query failed.", e);
		}
	}

	/**
	 * Get courseOffering information broken down by institution code across the
	 * terms requested.
	 * 
	 * @param course
	 * @param terms
	 * @return list of course offering institution
	 */
	public List<CourseOfferingInstitution> getCourseOfferingInstitutions(
			CourseInfo course, List<String> terms) {
		List<CourseOfferingInstitution> instituteList = new ArrayList<CourseOfferingInstitution>();

		Map<YearTerm, String> atpIdByYearTerm = new java.util.HashMap<YearTerm, String>();
		List<YearTerm> ytList = new ArrayList<YearTerm>();
		for (String term : terms) {
			YearTerm yt = KsapFrameworkServiceLocator.getTermHelper()
					.getYearTerm(term);
			ytList.add(yt);
			atpIdByYearTerm.put(yt, term);
		}
		Collections.sort(ytList, Collections.reverseOrder());
		for (YearTerm yt : ytList) {
			String atp = atpIdByYearTerm.get(yt);

			// Load course offering comments
			List<CourseOfferingInfo> courseOfferingInfoList = null;
			try {
				courseOfferingInfoList = getCourseOfferingService()
						.getCourseOfferingsByCourseAndTerm(
								course.getId(),
								atp,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
			} catch (Exception e) {
				LOG.error(" Could not load course offerings for : "
						+ course.getCode() + " atp : " + atp);
				return instituteList;
			}

			String courseComments = null;
			for (CourseOfferingInfo courseInfo : courseOfferingInfoList) {

				if (null != courseComments)
					break;

				for (AttributeInfo attributeInfo : courseInfo.getAttributes()) {
					String key = attributeInfo.getKey();
					String value = attributeInfo.getValue();
					if ("CourseComments".equalsIgnoreCase(key)
							&& value.length() > 0) {
						courseComments = value;
						break;
					}
				}
			}

			List<ActivityOfferingItem> list = getActivityOfferingItems(course,
					courseOfferingInfoList, atp);
			for (ActivityOfferingItem activityOfferingItem : list) {
				String instituteCode = activityOfferingItem.getInstituteCode();
				String instituteName = activityOfferingItem.getInstituteName();

				CourseOfferingInstitution courseOfferingInstitution = null;
				for (CourseOfferingInstitution temp : instituteList) {
					if (instituteCode.equals(temp.getCode())) {
						courseOfferingInstitution = temp;
						break;
					}
				}
				if (courseOfferingInstitution == null) {
					courseOfferingInstitution = new CourseOfferingInstitution();
					courseOfferingInstitution.setCode(instituteCode);
					courseOfferingInstitution.setName(instituteName);
					instituteList.add(courseOfferingInstitution);
				}

				List<CourseOfferingTerm> courseOfferingTermList = courseOfferingInstitution
						.getCourseOfferingTermList();
				CourseOfferingTerm courseOfferingTerm = null;
				for (CourseOfferingTerm temp : courseOfferingTermList) {
					if (yt.equals(temp.getYearTerm())) {
						courseOfferingTerm = temp;
					}
				}
				if (courseOfferingTerm == null) {
					courseOfferingTerm = new CourseOfferingTerm();
					courseOfferingTerm.setYearTerm(yt);
					courseOfferingTerm.setTerm(yt.getLongName());
					courseOfferingTerm.setCourseComments(courseComments);
					courseOfferingTerm
							.setInstituteCode(courseOfferingInstitution
									.getCode());
					courseOfferingTermList.add(courseOfferingTerm);
				}

				courseOfferingTerm.getActivityOfferingItemList().add(
						activityOfferingItem);
			}
		}
		Collections.sort(instituteList, Collections.reverseOrder());

		return instituteList;

	}

	/**
	 * @param courseId
	 * @param termId
	 * @return
	 */
	public List<ActivityOfferingItem> getActivityOfferingItemsById(
			String courseId, String termId) {

		List<ActivityOfferingItem> activityOfferingItems = new ArrayList<ActivityOfferingItem>();

		// Get version verified course
		courseId = getVerifiedCourseId(courseId);
		try {
			CourseInfo course = getCourseService().getCourse(courseId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());

			List<CourseOfferingInfo> courseOfferingInfoList = getCourseOfferingService()
					.getCourseOfferingsByCourseAndTerm(
							courseId,
							termId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			activityOfferingItems = getActivityOfferingItems(course,
					courseOfferingInfoList, termId);

		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.",
					courseId), e);
		} catch (Exception e) {
			throw new RuntimeException("Query failed.", e);
		}

		return activityOfferingItems;
	}

	/**
	 * Returns activity Offerings for given courseId and term
	 * 
	 * @param course
	 * @param courseOfferingInfoList
	 * @return
	 */
	public List<ActivityOfferingItem> getActivityOfferingItems(
			CourseInfo course, List<CourseOfferingInfo> courseOfferingInfoList,
			String termId) {

		List<ActivityOfferingItem> activityOfferingItemList = new ArrayList<ActivityOfferingItem>();

		for (CourseOfferingInfo courseInfo : courseOfferingInfoList) {

			// Activity offerings come back as a list, the first item is
			// primary, the remaining are secondary
			String courseOfferingID = courseInfo.getId();
			List<ActivityOfferingDisplayInfo> aodiList = null;

			try {
				aodiList = getCourseOfferingService()
						.getActivityOfferingDisplaysForCourseOffering(
								courseOfferingID,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
			} catch (Exception e) {
				LOG.error(" Could not load activity offering for course offering: "
						+ courseOfferingID);
				return activityOfferingItemList;
			}

			boolean primary = true;

			for (ActivityOfferingDisplayInfo aodi : aodiList) {
				ActivityOfferingItem activity = new ActivityOfferingItem();
				String sectionId = aodi.getActivityOfferingCode();
				activity.setCode(sectionId);

				String typeName = aodi.getTypeName();
				activity.setActivityOfferingType(typeName);

				activity.setCredits(courseInfo.getCreditOptionName());
				activity.setGradingOption(courseInfo.getGradingOptionName());
				List<MeetingDetails> meetingDetailsList = activity
						.getMeetingDetailsList();
				{
					ScheduleDisplayInfo sdi = aodi.getScheduleDisplay();
					for (ScheduleComponentDisplay scdi : sdi
							.getScheduleComponentDisplays()) {
						MeetingDetails meeting = new MeetingDetails();

						Building building = scdi.getBuilding();
						if (building != null) {
							meeting.setCampus(building.getCampusKey());
							meeting.setBuilding(building.getBuildingCode());
						}

						Room roomInfo = scdi.getRoom();
						if (roomInfo != null) {
							meeting.setRoom(roomInfo.getRoomCode());
						}

						for (TimeSlot timeSlot : scdi.getTimeSlots()) {

							String days = "";
							for (int weekday : timeSlot.getWeekdays()) {
								if (weekday > -1 && weekday < 7) {
									String letter = WEEKDAYS_FIRST_LETTER[weekday];
									days += letter;
								}
							}
							if (!"".equals(days)) {
								meeting.setDays(days);
							}

							TimeOfDayInfo startInfo = timeSlot.getStartTime();
							TimeOfDayInfo endInfo = timeSlot.getEndTime();
							if (startInfo != null && endInfo != null
									&& startInfo.getMilliSeconds() != null
									&& endInfo.getMilliSeconds() != null) {
								long startTimeMillis = startInfo
										.getMilliSeconds();
								String startTime = TimeStringMillisConverter
										.millisToStandardTime(startTimeMillis);

								long endTimeMillis = endInfo.getMilliSeconds();
								String endTime = TimeStringMillisConverter
										.millisToStandardTime(endTimeMillis);

								String time = startTime + " - " + endTime;

								meeting.setTime(time);
							}
							meetingDetailsList.add(meeting);
						}
					}
				}

				String instituteCode = null;
				String instituteName = null;

				String campus = null;
				// String enrollCount = null;
				// String enrollMaximum = null;
				// String enrollEstimate = null;
				for (AttributeInfo attrib : aodi.getAttributes()) {
					String key = attrib.getKey();
					String value = attrib.getValue();
					if ("Campus".equalsIgnoreCase(key) && !"".equals(value)) {
						campus = value;
						continue;
					}
					if ("FeeAmount".equalsIgnoreCase(key) && !"".equals(value)) {
						activity.setFeeAmount(value);
						continue;
					}
					if ("SLN".equalsIgnoreCase(key)) {
						activity.setRegistrationCode(value);
						continue;
					}
					if ("instituteCode".equals(key)) {
						instituteCode = value;
						continue;
					}
					if ("instituteName".equals(key) && !"".equals(value)) {
						instituteName = value;
						continue;
					}

					// if ("currentEnrollment".equals(key) && !"".equals(value))
					// {
					// enrollCount = value;
					// continue;
					// }
					//
					// if ("enrollmentLimit".equals(key) && !"".equals(value)) {
					// enrollMaximum = value;
					// continue;
					// }
					//
					// if ("limitEstimate".equals(key) && "E".equals(value)) {
					// enrollEstimate = value;
					// continue;
					// }

					if ("SectionComments".equalsIgnoreCase(key)) {
						activity.setSectionComments(value);
						continue;
					}

					Boolean flag = Boolean.valueOf(value);
					if ("ServiceLearning".equalsIgnoreCase(key)) {
						activity.setServiceLearning(flag);
					} else if ("ResearchCredit".equalsIgnoreCase(key)) {
						activity.setResearch(flag);
					} else if ("DistanceLearning".equalsIgnoreCase(key)) {
						activity.setDistanceLearning(flag);
					} else if ("JointSections".equalsIgnoreCase(key)) {
						activity.setJointOffering(flag);
					} else if ("Writing".equalsIgnoreCase(key)) {
						activity.setWritingSection(flag);
					} else if ("FinancialAidEligible".equalsIgnoreCase(key)) {
						activity.setIneligibleForFinancialAid(flag);
					} else if ("AddCodeRequired".equalsIgnoreCase(key)) {
						activity.setAddCodeRequired(flag);
					} else if ("IndependentStudy".equalsIgnoreCase(key)) {
						activity.setIndependentStudy(flag);
					} else if ("EnrollmentRestrictions".equalsIgnoreCase(key)) {
						activity.setEnrollRestriction(flag);
					}

				}
				// activity.setEnrollOpen(true);
				// activity.setEnrollCount(enrollCount);
				// activity.setEnrollMaximum(enrollMaximum);
				// activity.setEnrollEstimate(enrollEstimate);
				activity.setInstructor(aodi.getInstructorName());

				activity.setHonorsSection(aodi.getIsHonorsOffering());
				activity.setNewThisYear(false);

				activity.setDetails("View more details");

				// Added this flag to know if the activityoffering is
				// planned/backup
				activity.setPlanItemId(getPlanItemId(course.getCode() + " "
						+ sectionId, termId));
				activity.setAtpId(termId);
				YearTerm yt = KsapFrameworkServiceLocator.getTermHelper()
						.getYearTerm(termId);
				activity.setQtryr(yt.getTermName().substring(0, 3)
						.toUpperCase()
						+ "+" + yt.getYear());

				if (instituteCode == null) {
					instituteCode = campus;
				}
				if (instituteName == null) {
					instituteName = campus;
				}

				activity.setInstituteName(instituteName);
				activity.setInstituteCode(instituteCode);

				activity.setPrimary(primary);
				primary = false;
				activityOfferingItemList.add(activity);
			}
		}

		return activityOfferingItemList;
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
			subjects = KsapFrameworkServiceLocator.getOrgHelper()
					.getTrimmedSubjectAreas();
			getHashMap().put(CourseSearchConstants.SUBJECT_AREA, subjects);

		} else {
			subjects = getHashMap().get(CourseSearchConstants.SUBJECT_AREA);
		}

		if (subjects != null && subjects.size() > 0) {
			titleValue = subjects.get(display.trim());
		}

		return titleValue;
	}

	/**
	 * Validates if the courseId/versionIndependentId is valid or not.
	 * 
	 * @param courseId
	 * @return
	 */
	public boolean isCourseIdValid(String courseId) {
		boolean isCourseIdValid = false;

		CourseInfo course = null;
		try {
			/* Get version verified course */
			course = getCourseService().getCourse(
					getVerifiedCourseId(courseId),
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.",
					courseId), e);
		} catch (Exception e) {
			throw new RuntimeException("Query failed.", e);
		}
		if (course != null) {
			isCourseIdValid = true;
		}
		return isCourseIdValid;
	}

	/**
	 * Checks if the Given refObjId for a section (eg: com 453 A or com 453 AA
	 * or can use a versionIndependentId) for the given atpId exists in
	 * Plan/backup returns planItemId if exists otherwise returns null.
	 * 
	 * @param refObjId
	 * @param atpId
	 * @return
	 */
	public String getPlanItemId(String refObjId, String atpId) {
		String planItemId = null;
		try {
			PlanController planController = new PlanController();
			PlanItemInfo planItem = planController.getPlannedOrBackupPlanItem(
					refObjId, atpId);
			if (planItem != null) {
				planItemId = planItem.getId();
			}

		} catch (Exception e) {
			LOG.error(" Exception loading plan item :" + refObjId
					+ " for atp: " + atpId + " " + e.getMessage());
			return null;
		}
		return planItemId;
	}

	public AcademicRecordService getAcademicRecordService() {
		if (this.academicRecordService == null) {
			// TODO: Use constants for namespace.
			this.academicRecordService = KsapFrameworkServiceLocator
					.getAcademicRecordService();
		}
		return this.academicRecordService;
	}

	public void setAcademicRecordService(
			AcademicRecordService academicRecordService) {
		this.academicRecordService = academicRecordService;
	}

	protected synchronized CourseService getCourseService() {
		if (this.courseService == null) {
			this.courseService = KsapFrameworkServiceLocator.getCourseService();
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
			atpService = KsapFrameworkServiceLocator.getAtpService();
		}
		return this.atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	protected CourseOfferingService getCourseOfferingService() {
		if (this.courseOfferingService == null) {
			this.courseOfferingService = KsapFrameworkServiceLocator
					.getCourseOfferingService();
		}
		return this.courseOfferingService;
	}

	public void setCourseOfferingService(
			CourseOfferingService courseOfferingService) {
		this.courseOfferingService = courseOfferingService;
	}

	protected AcademicCalendarService getAcademicCalendarService() {
		if (this.academicCalendarService == null) {
			this.academicCalendarService = KsapFrameworkServiceLocator
					.getAcademicCalendarService();
		}
		return this.academicCalendarService;
	}

	public void setAcademicCalendarService(
			AcademicCalendarService academicCalendarService) {
		this.academicCalendarService = academicCalendarService;
	}

	public AcademicPlanService getAcademicPlanService() {
		if (academicPlanService == null) {
			academicPlanService = KsapFrameworkServiceLocator
					.getAcademicPlanService();
		}
		return academicPlanService;
	}

	public void setAcademicPlanService(AcademicPlanService academicPlanService) {
		this.academicPlanService = academicPlanService;
	}

	/**
	 * Takes a courseId that can be either a version independent Id or a version
	 * dependent Id and returns a version dependent Id. In case of being passed
	 * in a version depend
	 * 
	 * @param courseId
	 * @return
	 */
	private String getVerifiedCourseId(String courseId) {
		/*
		 * String verifiedCourseId = null; try { SearchRequestInfo req = new
		 * SearchRequestInfo("myplan.course.version.id");
		 * req.addParam("courseId", courseId); req.addParam("courseId",
		 * courseId); req.addParam("lastScheduledTerm",
		 * KsapFrameworkServiceLocator.getAtpHelper().getLastScheduledAtpId());
		 * SearchResult result = getCluService().search(req,
		 * KsapFrameworkServiceLocator.getContext().getContextInfo()); for
		 * (SearchResultRow row : result.getRows()) { for (SearchResultCell cell
		 * : row.getCells()) { if
		 * ("lu.resultColumn.cluId".equals(cell.getKey())) { verifiedCourseId =
		 * cell.getValue(); } } } } catch (Exception e) {
		 * LOG.error("version verified Id retrieval failed", e); } return
		 * verifiedCourseId;
		 */
		return courseId;
	}

}