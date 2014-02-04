package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingItem;
import org.kuali.student.ap.coursesearch.service.impl.CourseDetailsInquiryHelperImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.infc.Term;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by IntelliJ IDEA. User: hemanthg Date: 1/25/13 Time: 11:14 AM To
 * change this template use File | Settings | File Templates.
 */
public class SingleQuarterInquiryHelperImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = 5553434195657521320L;

	private final Logger logger = Logger
			.getLogger(SingleQuarterInquiryHelperImpl.class);

	private transient AcademicRecordService academicRecordService;

	private transient AcademicPlanService academicPlanService;

	private transient CourseDetailsInquiryHelperImpl courseDetailsInquiryHelper;

	public synchronized CourseDetailsInquiryHelperImpl getCourseDetailsInquiryHelper() {
		if (this.courseDetailsInquiryHelper == null) {
			this.courseDetailsInquiryHelper = new CourseDetailsInquiryHelperImpl();
		}
		return courseDetailsInquiryHelper;
	}

	public void setCourseDetailsInquiryHelper(
			CourseDetailsInquiryHelperImpl courseDetailsInquiryHelper) {
		this.courseDetailsInquiryHelper = courseDetailsInquiryHelper;
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

	public AcademicRecordService getAcademicRecordService() {
		if (this.academicRecordService == null) {
			// TODO KSAP-745: Convert to using constants for namespace.
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

	@Override
	public PlannedTerm retrieveDataObject(Map fieldValues) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String termAtpId = request.getParameter("term_atp_id");
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();
		String[] params = {};

		TermHelper th = KsapFrameworkServiceLocator.getTermHelper();
		List<Term> publishedTerms = th.getOfficialTerms();

		/************* PlannedCourseList **************/
		List<PlannedCourseDataObject> plannedCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			plannedCoursesList = getPlanItemListByTermId(
					AcademicPlanServiceConstants.ItemCategory.PLANNED, studentId,
					termAtpId);
		} catch (Exception e) {
			logger.error("Could not load plannedCourseslist", e);

		}
		for (PlannedCourseDataObject pl : plannedCoursesList) {
			Term pt = th.getTerm(pl.getPlanItemDataObject().getAtp());
			try {
				pl.setShowAlert(!KsapFrameworkServiceLocator.getCourseHelper()
						.isCourseOffered(pt,
								KsapFrameworkServiceLocator.getCourseService().getCourse(
										pl.getCourseDetails().getCourseId(),
										KsapFrameworkServiceLocator.getContext()
												.getContextInfo())));
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Course lookup failure",e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Course lookup failure",e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Course lookup failure",e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Course lookup failure",e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Course lookup failure",e);
			}
			boolean open = false;
			for (Term t : publishedTerms)
				open = open && t.getId().equals(pt.getId());
			pl.setTimeScheduleOpen(open);
		}
		/**** academic record SWS call to get the studentCourseRecordInfo list *****/
		List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
		try {
			studentCourseRecordInfos = getAcademicRecordService()
					.getCompletedCourseRecords(
							studentId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (Exception e) {
			GlobalVariables.getMessageMap().putWarningForSectionId(
					PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID,
					PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
			logger.error(
					"Could not retrieve StudentCourseRecordInfo from the SWS.",
					e);
		}

		/************* BackupCourseList **************/
		List<PlannedCourseDataObject> backupCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			backupCoursesList = getPlanItemListByTermId(
					AcademicPlanServiceConstants.ItemCategory.BACKUP, studentId,
					termAtpId);
		} catch (Exception e) {
			logger.error("Could not load backupCourseList", e);

		}
		for (PlannedCourseDataObject pl : backupCoursesList) {
			Term pt = th.getTerm(pl.getPlanItemDataObject().getAtp());
			try {
				pl.setShowAlert(!KsapFrameworkServiceLocator.getCourseHelper()
						.isCourseOffered(pt,
								KsapFrameworkServiceLocator.getCourseService().getCourse(
										pl.getCourseDetails().getCourseId(),
										KsapFrameworkServiceLocator.getContext()
												.getContextInfo())));
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Course lookup failure",e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Course lookup failure",e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Course lookup failure",e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Course lookup failure",e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Course lookup failure",e);
			}
			boolean open = false;
			for (Term t : publishedTerms)
				open = open && t.getId().equals(pt.getId());
			pl.setTimeScheduleOpen(open);
		}

		PlannedTerm perfectPlannedTerm = SingleQuarterHelperBase
				.populatePlannedTerms(plannedCoursesList, backupCoursesList,
						studentCourseRecordInfos, termAtpId, true);
		return perfectPlannedTerm;
	}

	protected List<PlannedCourseDataObject> getPlanItemListByTermId(
			AcademicPlanServiceConstants.ItemCategory planItemCategory, String studentId, String termId)
			throws InvalidParameterException, MissingParameterException,
			DoesNotExistException, OperationFailedException {

		List<PlannedCourseDataObject> plannedCoursesList = new ArrayList<PlannedCourseDataObject>();

		AcademicPlanService academicPlanService = getAcademicPlanService();
		ContextInfo context = KsapFrameworkServiceLocator.getContext()
				.getContextInfo();

		String planTypeKey = PlanConstants.LEARNING_PLAN_TYPE_PLAN;

		List<LearningPlanInfo> learningPlanList = academicPlanService
				.getLearningPlansForStudentByType(studentId, planTypeKey,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
		for (LearningPlanInfo learningPlan : learningPlanList) {
			String learningPlanID = learningPlan.getId();
			List<PlanItemInfo> planItemList = academicPlanService
					.getPlanItemsInPlan(learningPlanID, context);

			for (PlanItemInfo planItem : planItemList) {
				String planPeriod;
                try{
                    planPeriod = KSCollectionUtils.getRequiredZeroElement(planItem.getPlanPeriods());
                }catch(OperationFailedException e){
                    planPeriod = "NULL";
                }
                if (planPeriod.equalsIgnoreCase(termId)
						&& planItem.getRefObjectType().equalsIgnoreCase(
								PlanConstants.COURSE_TYPE)) {
					PlannedCourseDataObject plannedCourseDO = new PlannedCourseDataObject();
					String courseID = planItem.getRefObjectId();
					// Only create a data object for the specified type.
					if (planItem.getCategory().equals(planItemCategory)) {

						plannedCourseDO
								.setPlanItemDataObject(PlanItemDataObject
										.build(planItem));

						// If the course info lookup fails just log the error
						// and omit the item.
						try {

							plannedCourseDO
									.setCourseDetails(getCourseDetailsInquiryHelper()
											.retrieveCourseSummaryById(courseID));
							plannedCourseDO
									.setPlanActivities(getPlannedSections(
											plannedCourseDO.getCourseDetails()
													.getCourseId(), termId));

						} catch (Exception e) {
							logger.error(
									String.format(
											"Unable to retrieve course info for plan item [%s].",
											planItem.getId()), e);
							continue;
						}

						plannedCoursesList.add(plannedCourseDO);
					}
				}
			}
		}
		return plannedCoursesList;
	}

	/* Used to get the planned sections for a coursId and term */
	private List<ActivityOfferingItem> getPlannedSections(String courseId,
			String term) {
		List<ActivityOfferingItem> sectionsPlanned = new ArrayList<ActivityOfferingItem>();
		List<ActivityOfferingItem> activityOfferingItems = getCourseDetailsInquiryHelper()
				.getActivityOfferingItemsById(courseId, term);
		for (ActivityOfferingItem activityOfferingItem : activityOfferingItems) {
			if (activityOfferingItem.getPlanItemId() != null) {
				sectionsPlanned.add(activityOfferingItem);
			}
		}
		return sectionsPlanned;
	}

}
