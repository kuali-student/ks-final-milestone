package org.kuali.student.myplan.course.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
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
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.room.infc.Building;
import org.kuali.student.r2.core.room.infc.Room;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

@SuppressWarnings("deprecation")
public class CourseDetailsInquiryHelperImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = 4933435913745621395L;

	private static final Logger LOG = Logger.getLogger(CourseDetailsInquiryHelperImpl.class);

	public static final String NOT_OFFERED_IN_LAST_TEN_YEARS = "Not offered for more than 10 years.";

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

	@Override
	public CourseDetails retrieveDataObject(@SuppressWarnings("rawtypes") Map fieldValues) {
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
		return retrieveCourseDetails(
				(String) fieldValues.get(PlanConstants.PARAM_COURSE_ID),
				(String) fieldValues.get(PlanConstants.PARAM_TERM_ID),
				studentId,
				fieldValues.get(PlanConstants.PARAM_OFFERINGS_FLAG) != null
						&& Boolean.valueOf(fieldValues.get(PlanConstants.PARAM_OFFERINGS_FLAG).toString()));
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
		CourseInfo course = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
		CourseSummaryDetails courseDetails = retrieveCourseSummary(course);
		return courseDetails;
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

		courseDetails.setVersionIndependentId(course.getVersion().getVersionIndId());
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
			String req = (CourseLinkBuilder.makeLinks(str.substring(str.indexOf("Prerequisite:"), str.length()),
					courseLinkTemplateStyle, KsapFrameworkServiceLocator.getContext().getContextInfo()));
			req = req.substring(req.indexOf("Prerequisite:"), req.length());
			req = req.replace("Prerequisite:", "").trim();
			req = req.substring(0, 1).toUpperCase().concat(req.substring(1, req.length()));
			prerequisites.add(req);

			str = str.substring(0, str.indexOf("Prerequisite:"));
		}
		if (str != null) {
			getCourseLinkBuilder();
			str = CourseLinkBuilder.makeLinks(str, KsapFrameworkServiceLocator.getContext().getContextInfo());
		}
		courseDetails.setRequisites(prerequisites);
		courseDetails.setCourseDescription(str);

		List<String> cto = course.getTermsOffered();
		if (cto != null) {
			List<String> to = new java.util.ArrayList<String>();
			try {
				for (TypeInfo ti : KsapFrameworkServiceLocator.getTypeService().getTypesByKeys(cto,
						KsapFrameworkServiceLocator.getContext().getContextInfo()))
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

		for (AttributeInfo attributeInfo : course.getAttributes()) {
			String key = attributeInfo.getKey();
			String value = attributeInfo.getValue();

			// -- Gen Ed requirements
			if ("Y".equals(value) && key.startsWith(CourseSearchConstants.GEN_EDU_REQUIREMENTS_PREFIX)) {

				// Get only the abbre_val of gen ed requirements
				String abbrev = KsapFrameworkServiceLocator.getEnumerationHelper().getEnumAbbrValForCode(key);
				courseDetails.getAbbrGenEdRequirements().add(abbrev);

				// Get general education requirements.
				EnumeratedValueInfo info = KsapFrameworkServiceLocator.getEnumerationHelper().getGenEdReqEnumInfo(key,
						KsapFrameworkServiceLocator.getContext().getContextInfo());
				String genEdText = String.format("%s (%s)", info.getValue(), info.getAbbrevValue());
				courseDetails.getGenEdRequirements().add(genEdText);
			}

			// -- Campus Locations
			if (key.startsWith(CourseSearchConstants.CAMPUS_LOCATION)) {
				List<EnumeratedValueInfo> enumeratedValueInfoList = KsapFrameworkServiceLocator.getEnumerationHelper()
						.getEnumerationValueInfoList("kuali.lu.campusLocation");
				for (EnumeratedValueInfo campusEnum : enumeratedValueInfoList)
					if (campusEnum.getCode().equals(value))
						courseDetails.getCampusLocations().add(campusEnum.getValue());
			}
		}

		// -- Curriculum Title
		Map<String, String> subjectAreaMap = KsapFrameworkServiceLocator.getOrgHelper().getTrimmedSubjectAreas();
		courseDetails.setCurriculumTitle(subjectAreaMap.get(course.getSubjectArea().trim()));

		// -- Scheduled Terms
		courseDetails.setScheduledTerms(KsapFrameworkServiceLocator.getCourseHelper().getScheduledTerms(course));
		if (courseDetails.getScheduledTerms() == null)
			courseDetails.setScheduledTerms(new ArrayList<String>());
		if (courseDetails.getScheduledTerms().size() == 0)
			courseDetails.setLastOffered(KsapFrameworkServiceLocator.getCourseHelper().getLastOfferedTermId(course));

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
	public CourseDetails retrieveCourseDetails(String courseId, String termId, String studentId,
			boolean loadActivityOffering) {
		CourseDetails courseDetails = new CourseDetails();
		final CourseInfo course = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);

		CourseSummaryDetails courseSummaryDetails = retrieveCourseSummary(course);
		courseDetails.setCourseSummaryDetails(courseSummaryDetails);

		// Course offerings
		final List<String> termIds;
		if (termId == null)
			termIds = courseSummaryDetails.getScheduledTerms();
		else {
			termIds = new java.util.ArrayList<String>(1);
			termIds.add(termId);
		}
		if (loadActivityOffering)
			courseDetails.setCourseOfferingInstitutionList(getCourseOfferingInstitutions(course, termIds));
		else
			courseDetails.setCourseOfferingInstitutionList(new java.util.ArrayList<CourseOfferingInstitution>(0));

		// Course Plan + Academic Records
		courseDetails.setPlannedCourseSummary(getPlannedCourseSummary(course, studentId));

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
	public PlannedCourseSummary getPlannedCourseSummaryById(String courseId, String studentId) {
		CourseInfo course = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
		return getPlannedCourseSummary(course, studentId);
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
	public PlannedCourseSummary getPlannedCourseSummary(CourseInfo course, String studentId) {

		PlannedCourseSummary plannedCourseSummary = new PlannedCourseSummary();

		// Planned, backup and Saved Item
		AcademicPlanService academicPlanService = KsapFrameworkServiceLocator.getAcademicPlanService();

		try {
			LearningPlanInfo plan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();
			if (plan != null) {
				plannedCourseSummary.setLearningPlanId(plan.getId());

				// Fetch the plan items which are associated with the plan.
				List<PlanItemInfo> planItemsInPlan = academicPlanService.getPlanItemsInPlan(plan.getId(),
						KsapFrameworkServiceLocator.getContext().getContextInfo());

				// Iterate through the plan items and set flags to indicate
				// whether the item is a planned/backup or saved course.
				for (PlanItem planItemInPlanTemp : planItemsInPlan) {
					if (planItemInPlanTemp.getRefObjectId().equals(course.getId())) {
						// Assuming type is planned or backup if not wishlist.
						String typeKey = planItemInPlanTemp.getTypeKey();
						if (typeKey.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST)) {
							plannedCourseSummary.setSavedItemId(planItemInPlanTemp.getId());
							String dateStr = planItemInPlanTemp.getMeta().getCreateTime().toString();
							dateStr = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER
									.format(DateFormatters.DEFAULT_DATE_FORMATTER.parse(dateStr.substring(0, 10)));
							plannedCourseSummary.setSavedItemDateCreated(dateStr);
						} else {
							PlanItemDataObject planItem = PlanItemDataObject.build(planItemInPlanTemp);
							if (typeKey.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
								plannedCourseSummary.getPlannedList().add(planItem);
							} else if (typeKey.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
								plannedCourseSummary.getBackupList().add(planItem);
							}
						}
					}
				}
			}
		} catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
			// Ignore and not load any plan data
		} catch (Exception e1) {
			LOG.error(" Error loading plan information for course :" + course.getCode() + " " + e1.getMessage());
		}

		// Get Academic Record Data from the SWS and set that to CourseDetails
		// acadRecordList
		try {
			List<StudentCourseRecordInfo> studentCourseRecordInfos = KsapFrameworkServiceLocator
					.getAcademicRecordService().getCompletedCourseRecords(studentId,
							KsapFrameworkServiceLocator.getContext().getContextInfo());
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

					YearTerm str = KsapFrameworkServiceLocator.getTermHelper().getYearTerm(studentInfo.getTermName());
					plannedCourseSummary.getAcademicTerms().add(str.getTermName());
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
	public List<CourseOfferingInstitution> getCourseOfferingInstitutionsById(String courseId, List<String> terms) {
		CourseInfo course = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
		return getCourseOfferingInstitutions(course, terms);
	}

	/**
	 * Loads all plan items for the logged in user. Returns the plan items
	 * grouped by terms
	 * 
	 * @return A Map of term to a Map of refObjectId to planItemId
	 */

	protected Map<String, Map<String, PlanItem>> loadStudentsPlanItems() {
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
		Map<String, Map<String, PlanItem>> planItemsByTerm = new HashMap<String, Map<String, PlanItem>>();
		if (studentId == null)
			return planItemsByTerm;

		List<LearningPlanInfo> learningPlanList;
		try {
			learningPlanList = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlansForStudentByType(
					studentId, PlanConstants.LEARNING_PLAN_TYPE_PLAN,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			for (LearningPlanInfo learningPlan : learningPlanList) {
				List<PlanItemInfo> planItems = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItemsInPlan(
						learningPlan.getId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
				if (null != planItems) {
					for (PlanItem item : planItems) {
						for (String planPeriod : item.getPlanPeriods()) {
							Map<String, PlanItem> planMap = planItemsByTerm.get(planPeriod);
							if (null == planMap) {
								planMap = new HashMap<String, PlanItem>();
								planItemsByTerm.put(planPeriod, planMap);
							}

							planMap.put(item.getRefObjectId(), item);
						}
					}
				}
			}
		} catch (DoesNotExistException e) {
			LOG.warn("Student " + studentId + " has not plan", e);
			return Collections.emptyMap();
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup failure ", e);
		} catch (MissingParameterException e) {
			throw new IllegalStateException("LP lookup failure ", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup failure ", e);
		}
		return planItemsByTerm;
	}

	/**
	 * Get courseOffering information broken down by institution code across the
	 * terms requested.
	 * 
	 * @param course
	 * @param terms
	 * @return list of course offering institution
	 */
	public List<CourseOfferingInstitution> getCourseOfferingInstitutions(CourseInfo course, List<String> terms) {
		List<CourseOfferingInstitution> instituteList = new ArrayList<CourseOfferingInstitution>();

		Map<String, Map<String, PlanItem>> planItemsByTerm = loadStudentsPlanItems();

		Collections.sort(terms);
		for (String termId : terms) {
			// String termId = atpIdByYearTerm.get(yt);

			// Load course offering comments
			List<CourseOfferingInfo> courseOfferingInfoList;
			try {
				courseOfferingInfoList = KsapFrameworkServiceLocator.getCourseOfferingService()
						.getCourseOfferingsByCourseAndTerm(course.getId(), termId,
								KsapFrameworkServiceLocator.getContext().getContextInfo());
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("CO lookup failure " + e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("CO lookup failure " + e);
			} catch (MissingParameterException e) {
				throw new IllegalStateException("CO lookup failure " + e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("CO lookup failure " + e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("CO lookup failure " + e);
			}

			String courseComments = null;
			String curriculumComments = null;
			for (CourseOfferingInfo courseInfo : courseOfferingInfoList) {

				if (null != courseComments)
					break;

				for (AttributeInfo attributeInfo : courseInfo.getAttributes()) {
					String key = attributeInfo.getKey();
					String value = attributeInfo.getValue();
					if ("CourseComments".equalsIgnoreCase(key) && value.length() > 0) {
						courseComments = value;
						break;
					} else if ("CurriculumComments".equalsIgnoreCase(key) && value.length() > 0) {
						curriculumComments = value;
						if (null != courseComments)
							break;
					}
				}
			}

			List<ActivityOfferingItem> list = getActivityOfferingItems(course, courseOfferingInfoList, termId,
					planItemsByTerm.get(termId));
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

				List<CourseOfferingTerm> courseOfferingTermList = courseOfferingInstitution.getCourseOfferingTermList();
				CourseOfferingTerm courseOfferingTerm = null;
				for (CourseOfferingTerm temp : courseOfferingTermList)
					if (termId.equals(temp.getAtpId()))
						courseOfferingTerm = temp;
				if (courseOfferingTerm == null) {
					courseOfferingTerm = new CourseOfferingTerm();
					courseOfferingTerm.setAtpId(termId);
					courseOfferingTerm.setTerm(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termId)
							.getLongName());
					courseOfferingTerm.setCourseComments(courseComments);
					courseOfferingTerm.setCurriculumComments(curriculumComments);
					courseOfferingTerm.setInstituteCode(courseOfferingInstitution.getCode());
					courseOfferingTermList.add(courseOfferingTerm);
				}

				courseOfferingTerm.getActivityOfferingItemList().add(activityOfferingItem);
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
	public List<ActivityOfferingItem> getActivityOfferingItemsById(String courseId, String termId) {

		List<ActivityOfferingItem> activityOfferingItems = new ArrayList<ActivityOfferingItem>();

		try {
			CourseInfo course = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
			List<CourseOfferingInfo> courseOfferingInfoList = KsapFrameworkServiceLocator.getCourseOfferingService()
					.getCourseOfferingsByCourseAndTerm(courseId, termId,
							KsapFrameworkServiceLocator.getContext().getContextInfo());
			activityOfferingItems = getActivityOfferingItems(course, courseOfferingInfoList, termId,
					loadStudentsPlanItems().get(termId));
		} catch (DoesNotExistException e) {
			throw new RuntimeException(String.format("Course [%s] not found.", courseId), e);
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
	public List<ActivityOfferingItem> getActivityOfferingItems(CourseInfo course,
			List<CourseOfferingInfo> courseOfferingInfoList, String termId, Map<String, PlanItem> planItemMap) {

		List<String> plannedSections = new ArrayList<String>();
		if (planItemMap != null)
			for (PlanItem planItem : planItemMap.values())
				if (PlanConstants.SECTION_TYPE.equals(planItem.getRefObjectType())) {
					String courseCode = KsapFrameworkServiceLocator.getCourseHelper().getCourseCdFromActivityId(
							planItem.getRefObjectId());
					if (course.getCode().equalsIgnoreCase(courseCode))
						plannedSections.add(planItem.getRefObjectId());
				}
		Map<String, List<ActivityOfferingItem>> activityOfferingItemsByPrimary = new java.util.LinkedHashMap<String, List<ActivityOfferingItem>>();
		int c = 0;

		TermHelper th = KsapFrameworkServiceLocator.getTermHelper();
		boolean published = false;
		for (Term t : th.getPublishedTerms())
			published = published || t.getId().equals(termId);
		if (published) {
			boolean openForPlanning = th.isPlanning(termId);
			for (CourseOfferingInfo courseInfo : courseOfferingInfoList) {

				// Activity offerings come back as a list, the first item is
				// primary, the remaining are secondary
				String courseOfferingID = courseInfo.getId();
				List<ActivityOfferingDisplayInfo> aodiList;
				try {
					aodiList = KsapFrameworkServiceLocator.getCourseOfferingService()
							.getActivityOfferingDisplaysForCourseOffering(courseOfferingID,
									KsapFrameworkServiceLocator.getContext().getContextInfo());
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("CO lookup error", e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("CO lookup error", e);
				} catch (MissingParameterException e) {
					throw new IllegalStateException("CO lookup error", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("CO lookup error", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("CO lookup error", e);
				}

				for (ActivityOfferingDisplayInfo aodi : aodiList) {
					String planRefObjId = aodi.getId();
					String planItemId = null;
					if (null != planItemMap) {
						PlanItem planItem = planItemMap.get(planRefObjId);
						if (planItem != null) {
							planItemId = planItem.getId();
						}
					}
					ActivityOfferingItem activityOfferingItem = getActivityItem(aodi, courseInfo, openForPlanning,
							termId, planItemId);
					String paoid = activityOfferingItem.getPrimaryActivityOfferingId();
					List<ActivityOfferingItem> aol = activityOfferingItemsByPrimary.get(paoid);
					if (aol == null)
						activityOfferingItemsByPrimary.put(paoid,
								aol = new java.util.LinkedList<ActivityOfferingItem>());
					aol.add(activityOfferingItem);
					LOG.debug("primary " + paoid + ", ao " + activityOfferingItem.getLuiId());
					c++;
					if (plannedSections.contains(planRefObjId))
						plannedSections.remove(planRefObjId);
				}
			}
		}

		List<ActivityOfferingItem> rv = new java.util.ArrayList<ActivityOfferingItem>(c);
		Collections.sort(rv, new Comparator<ActivityOfferingItem>() {
			@Override
			public int compare(ActivityOfferingItem o1, ActivityOfferingItem o2) {
				if (o1 == null && o2 == null)
					return 0;
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;
				String l1 = o1.getLuiId(), l2 = o2.getLuiId();
				if (l1 == null && l2 == null)
					return 0;
				if (l1 == null)
					return -1;
				if (l2 == null)
					return 1;
				return l1.compareTo(l2);
			}
		});
		for (List<ActivityOfferingItem> aol : activityOfferingItemsByPrimary.values()) {
			for (ActivityOfferingItem ao : aol)
				if (ao.isPrimary())
					rv.add(ao);
			for (ActivityOfferingItem ao : aol)
				if (!ao.isPrimary())
					rv.add(ao);
		}
		return rv;
	}

	/**
	 * Used to retrieve a ActivityOffering using the following params
	 * 
	 * @param displayInfo
	 * @param courseOfferingInfo
	 * @param openForPlanning
	 * @param termId
	 * @param planItemId
	 * @return
	 */
	public ActivityOfferingItem getActivityItem(ActivityOfferingDisplayInfo displayInfo,
			CourseOfferingInfo courseOfferingInfo, boolean openForPlanning, String termId, String planItemId) {
		ActivityOfferingItem activity = new ActivityOfferingItem();
		/* Data from ActivityOfferingDisplayInfo */
		activity.setLuiId(displayInfo.getId());
		activity.setCourseId(courseOfferingInfo.getCourseId());
		activity.setCode(displayInfo.getActivityOfferingCode());
		activity.setStateKey(displayInfo.getStateKey());
		activity.setActivityOfferingType(displayInfo.getTypeName());
		List<MeetingDetails> meetingDetailsList = activity.getMeetingDetailsList();
		{
			DateFormat tdf = new SimpleDateFormat("h:mm a");
			ScheduleDisplayInfo sdi = displayInfo.getScheduleDisplay();
			for (ScheduleComponentDisplay scdi : sdi.getScheduleComponentDisplays()) {
				MeetingDetails meeting = new MeetingDetails();

				if (LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY.equals(activity.getStateKey())) {
					Building building = scdi.getBuilding();
					if (building != null) {
						meeting.setCampus(building.getCampusKey());
						meeting.setBuilding(building.getBuildingCode());
					}
					Room roomInfo = scdi.getRoom();
					if (roomInfo != null) {
						meeting.setRoom(roomInfo.getRoomCode());
					}
				}

				for (TimeSlot timeSlot : scdi.getTimeSlots()) {
					List<Integer> weekdays = timeSlot.getWeekdays();
					if (weekdays != null && !weekdays.isEmpty()) {
						StringBuilder days = new StringBuilder();
						for (int weekday : timeSlot.getWeekdays())
							switch (weekday) {
							case Calendar.MONDAY:
								days.append(SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE);
								break;
							case Calendar.TUESDAY:
								days.append(SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE);
								break;
							case Calendar.WEDNESDAY:
								days.append(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE);
								break;
							case Calendar.THURSDAY:
								days.append(SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE);
								break;
							case Calendar.FRIDAY:
								days.append(SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE);
								break;
							case Calendar.SATURDAY:
								days.append(SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE);
								break;
							case Calendar.SUNDAY:
								days.append(SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE);
								break;
							default:
								throw new IllegalArgumentException("Unexpected day code " + weekday);
							}
						meeting.setDays(days.toString());
					}

					TimeOfDayInfo startInfo = timeSlot.getStartTime();
					TimeOfDayInfo endInfo = timeSlot.getEndTime();

					if (startInfo != null && endInfo != null
                            && startInfo.getMilliSeconds()!=null && endInfo.getMilliSeconds()!=null)
						meeting.setTime(tdf.format(new Date(startInfo.getMilliSeconds())) + " - "
								+ tdf.format(new Date(endInfo.getMilliSeconds())));

					meetingDetailsList.add(meeting);
				}
			}
		}
		String instituteCode = null;
		String instituteName = null;

		String campus = null;
		for (AttributeInfo attrib : displayInfo.getAttributes()) {
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
			if ("InstituteCode".equals(key)) {
				instituteCode = value;
				continue;
			}
			if ("InstituteName".equals(key) && !"".equals(value)) {
				instituteName = value;
				continue;
			}

			if ("SectionComments".equalsIgnoreCase(key)) {
				activity.setSectionComments(value);
				continue;
			}

			if ("OtherInformation".equalsIgnoreCase(key)) {
				activity.setOtherInformation(value);
				continue;
			}

			if ("SummerTerm".equalsIgnoreCase(key) && !"".equals(value)) {
				activity.setSummerTerm(value);
				continue;
			}

			if ("PrimaryActivityOfferingId".equalsIgnoreCase(key)) {
				activity.setPrimaryActivityOfferingId(value);
				continue;
			}

			/*
			 * PrimarySectionCode is for the add button hover text in secondary
			 * sections Which have primary section not planned eg: COM 320
			 * AA:"Add Section AA and A to Plan"
			 */
			if ("PrimaryActivityOfferingCode".equalsIgnoreCase(key)) {
				activity.setPrimaryActivityOfferingCode(value);
				activity.setPrimary(value.equalsIgnoreCase(activity.getCode()));
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
		activity.setInstructor(displayInfo.getInstructorName());
		activity.setHonorsSection(displayInfo.getIsHonorsOffering());

		/* data from CourseOfferingInfo */
		activity.setCredits(courseOfferingInfo.getCreditOptionName());
		activity.setGradingOption(courseOfferingInfo.getGradingOptionName());

		/* Data from other params */
		activity.setNewThisYear(false);
		activity.setDetails("View more details");
		activity.setPlanItemId(planItemId);
		activity.setAtpId(termId);
		activity.setOpenForPlanning(openForPlanning);
		YearTerm yt = KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termId);
		activity.setQtryr(yt.getShortName().replace(' ', '+'));
		if (instituteCode == null) {
			instituteCode = campus;
		}
		if (instituteName == null) {
			instituteName = campus;
		}
		activity.setInstituteName(instituteName);
		activity.setInstituteCode(instituteCode);
		return activity;
	}

	/**
	 * Validates if the courseId/versionIndependentId is valid or not.
	 * 
	 * @param courseId
	 * @return
	 */
	public boolean isCourseIdValid(String courseId) {
		return KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId) != null;
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
			PlanItemInfo planItem = planController.getPlannedOrBackupPlanItem(refObjId, atpId);
			if (planItem != null) {
				planItemId = planItem.getId();
			}

		} catch (Exception e) {
			LOG.error(" Exception loading plan item :" + refObjId + " for atp: " + atpId + " " + e.getMessage());
			return null;
		}
		return planItemId;
	}

}
