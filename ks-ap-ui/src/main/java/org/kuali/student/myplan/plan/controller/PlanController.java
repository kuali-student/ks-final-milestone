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
package org.kuali.student.myplan.plan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.datadictionary.exception.DuplicateEntryException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.EnrollmentStatusHelper;
import org.kuali.student.ap.framework.context.EnrollmentStatusHelper.CourseCode;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.myplan.academicplan.infc.LearningPlan;
import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.myplan.audit.dto.AuditReportInfo;
import org.kuali.student.myplan.audit.service.DegreeAuditService;
import org.kuali.student.myplan.audit.service.DegreeAuditServiceConstants;
import org.kuali.student.myplan.comment.dataobject.MessageDataObject;
import org.kuali.student.myplan.comment.service.CommentQueryHelper;
import org.kuali.student.myplan.course.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.course.dataobject.CourseOfferingInstitution;
import org.kuali.student.myplan.course.dataobject.CourseOfferingTerm;
import org.kuali.student.myplan.course.dataobject.CourseSummaryDetails;
import org.kuali.student.myplan.course.service.CourseDetailsInquiryHelperImpl;
import org.kuali.student.myplan.plan.form.PlanForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.course.infc.Course;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/plan")
public class PlanController extends UifControllerBase {

	private static final Logger LOG = Logger.getLogger(PlanController.class);

	private transient AcademicPlanService academicPlanService;

	private transient DegreeAuditService degreeAuditService;

	private transient CourseDetailsInquiryHelperImpl courseDetailsInquiryService;

	// Java to JSON outputter.
	private transient ObjectMapper mapper = new ObjectMapper();

	// Used for getting the term and year from Atp
	private transient AcademicRecordService academicRecordService;

	public AcademicRecordService getAcademicRecordService() {
		if (this.academicRecordService == null) {
			// TODO: Use constants for namespace.
			this.academicRecordService = KsapFrameworkServiceLocator.getAcademicRecordService();
		}
		return this.academicRecordService;
	}

	public void setAcademicRecordService(
			AcademicRecordService academicRecordService) {
		this.academicRecordService = academicRecordService;
	}

	@Override
	protected PlanForm createInitialForm(HttpServletRequest request) {
		return new PlanForm();
	}

	@RequestMapping(params = "methodToCall=startAcademicPlannerForm")
	public ModelAndView startAcademicPlannerForm(
			@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {

		super.start(form, result, request, response);
		PlanForm planForm = (PlanForm) form;
		List<LearningPlanInfo> plan = null;
		try {
			// Throws RuntimeException is there is a problem. Otherwise, returns
			// a plan or null.
			plan = getAcademicPlanService().getLearningPlansForStudentByType(
					getUserId(), PlanConstants.LEARNING_PLAN_TYPE_PLAN,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(planForm,
					"Query for learning plan failed.", e);
		}
		List<MessageDataObject> messages = null;
		try {
			CommentQueryHelper commentQueryHelper = new CommentQueryHelper();
			messages = commentQueryHelper.getMessages(getUserId(),
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Could not retrieve messages.", e);
		}
		if (messages != null && messages.size() > 0) {
			planForm.setMessagesCount(messages.size());
		}
		if (plan != null && plan.size() > 0) {
			if (plan.get(0).getShared()) {
				planForm.setEnableAdviserView(plan.get(0).getShared()
						.toString());
			} else {
				planForm.setEnableAdviserView(plan.get(0).getShared()
						.toString());

			}
			List<PlanItemInfo> planItems = null;
			PlanItem item = null;
			try {
				planItems = getAcademicPlanService().getPlanItemsInPlanByType(
						plan.get(0).getId(),
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
			} catch (Exception e) {
				throw new RuntimeException("Could not retrieve plan items.", e);
			}
			if (planItems != null && planItems.size() > 0) {
				planForm.setBookmarkedCount(planItems.size());
			}

		}
		return getUIFModelAndView(planForm);
	}

	@RequestMapping(params = "methodToCall=start")
	public ModelAndView get(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		super.start(form, result, request, response);

		PlanForm planForm = (PlanForm) form;

		planForm.setNewUser(isNewUser());
		return getUIFModelAndView(planForm);
	}

	@RequestMapping(params = "methodToCall=startAddPlannedCourseForm")
	public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		super.start(form, result, request, response);

		PlanForm planForm = (PlanForm) form;
		// First load the plan item and retrieve the courseId
		PlanItemInfo planItem;

		String courseId = null;
		if (planForm.getPlanItemId() != null) {
			try {
				planItem = getAcademicPlanService().getPlanItem(
						planForm.getPlanItemId(),
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
				courseId = planItem.getRefObjectId();
				// Following data used for the Dialog boxes
				if (planItem.getTypeKey().equalsIgnoreCase(
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
					planForm.setBackup(true);
				}
				/*
				 * if a plan item exists the this is used for populating the
				 * menu items
				 */
				if (planItem.getPlanPeriods().size() > 0) {
					if (planForm.getAtpId() == null
							|| planItem.getPlanPeriods().get(0)
									.equalsIgnoreCase(planForm.getAtpId())) {
						/*
						 * Condition to check if the atp is greater than or
						 * equal to planning term
						 */
						if (!planForm.isSetToPlanning()) {
							planForm.setSetToPlanning(KsapFrameworkServiceLocator
									.getTermHelper().isPlanning(
											planItem.getPlanPeriods().get(0)));
						}
						planForm.setAtpId(planItem.getPlanPeriods().get(0));
					} else {
						return doPageRefreshError(planForm,
								"Plan item not found.", null);
					}
				}
			} catch (Exception e) {
				return doPageRefreshError(planForm, "Plan item not found.", e);
			}
		} else {
			planItem = new PlanItemInfo();
			courseId = planForm.getCourseId();
		}

		// TODO: Clean up with courseId removal
		if (StringUtils.isEmpty(courseId)) {
			return doOperationFailedError(planForm,
					"Could not initialize form because Course ID was missing.",
					null);
		}
		// Initialize the form with a course Id.
		planForm.setCourseId(courseId);

		// Also, add a full CourseDetails object so that course details
		// properties are available to be displayed on the form.
		try {
			planForm.setCourseSummaryDetails(getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(planForm.getCourseId()));
		} catch (RuntimeException e) {
			CourseSummaryDetails courseSummaryDetails = new CourseSummaryDetails();
			planForm.setCourseSummaryDetails(courseSummaryDetails);
			GlobalVariables.getMessageMap().clearErrorMessages();
			GlobalVariables.getMessageMap().putErrorForSectionId(
					"add_planned_course",
					PlanConstants.ERROR_KEY_UNKNOWN_COURSE);
			return doPageRefreshError(planForm, "Plan item not found.", e);

		}

		/*
		 * Checks if the course is added as plan/backup to that term in case of
		 * sections to add only to that particular type
		 */
		if (planForm.getCourseSummaryDetails() != null
				&& planForm.getTermYear() != null) {
			PlanItemInfo existingPlanItem = null;
			try {
				existingPlanItem = getPlannedOrBackupPlanItem(planForm
						.getCourseSummaryDetails().getVersionIndependentId(),
						planForm.getTermYear());
			} catch (RuntimeException e) {
				return doOperationFailedError(planForm,
						"Query for existing plan item failed.", null);
			}
			if (existingPlanItem != null) {
				if (existingPlanItem.getTypeKey().equalsIgnoreCase(
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
					planForm.setCourseInPlan(true);

				} else if (existingPlanItem.getTypeKey().equalsIgnoreCase(
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
					planForm.setCourseInBackup(true);
					planForm.setBackup(true);
				}
			}
		}

		planForm.setPlannedCourseSummary(getCourseDetailsInquiryService()
				.getPlannedCourseSummaryById(
						courseId,
						KsapFrameworkServiceLocator.getUserSessionHelper()
								.getStudentId()));

		/*
		 * plan item does not exists for academic Record course In that case
		 * acadRecAtpId is passed in through the UI which is used for populating
		 * the right flag
		 */
		if (planForm.getAcadRecAtpId() != null) {
			/*
			 * Condition to check if the atp is greater than or equal to
			 * planning term
			 */
			if (!planForm.isSetToPlanning()) {
				planForm.setSetToPlanning(KsapFrameworkServiceLocator
						.getTermHelper().isPlanning(planForm.getAcadRecAtpId()));
			}

		}
		if (planForm.getTermYear() != null) {
			planForm.setTermName(KsapFrameworkServiceLocator.getTermHelper()
					.getYearTerm(planForm.getTermYear()).getTermName());
		}
		return getUIFModelAndView(planForm);
	}

	@RequestMapping(params = "methodToCall=plannedToBackup")
	public ModelAndView plannedToBackup(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			return doAdviserAccessError(form, "Adviser Access Denied", null);
		}

		String planItemId = form.getPlanItemId();
		if (StringUtils.isEmpty(planItemId)) {
			return doOperationFailedError(form, "Plan Item ID was missing.",
					null);
		}

		// Verify the type is planned, change to backup, update, make events
		// (delete, add, update credits).
		PlanItemInfo planItem = null;
		try {
			// First load the plan item and retrieve the courseId
			planItem = getAcademicPlanService().getPlanItem(planItemId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not fetch plan item.", e);
		}

		// Verify that the plan item type is "planned".
		if (!planItem.getTypeKey().equals(
				PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
			return doOperationFailedError(form,
					"Move planned item was not type planned.", null);
		}

		// Validate: Capacity.
		boolean hasCapacity = false;
		try {
			hasCapacity = isAtpHasCapacity(getLearningPlan(getUserId()),
					planItem.getPlanPeriods().get(0),
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP);
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Could not validate capacity for new plan item.", e);
		}
		if (!hasCapacity) {
			return doPlanCapacityExceededError(form,
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP);
		}

		// Lookup course details.
		CourseSummaryDetails courseDetails = null;
		try {
			courseDetails = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(planItem.getRefObjectId());
		} catch (Exception e) {
			return doOperationFailedError(form,
					"Unable to retrieve Course Details.", e);
		}

		// Make removed event before updating the plan item.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> removeEvent = makeRemoveEvent(
				planItem, courseDetails, planItem.getRefObjectId(), form, null);

		// Update
		planItem.setTypeKey(PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP);
		try {
			getAcademicPlanService().updatePlanItem(planItemId, planItem,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not update plan item.",
					e);
		}

		// Make events (delete, add, update credits).
		// Set the javascript event(s) that should be thrown in the UI.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();

		events.putAll(removeEvent);
		events.putAll(makeAddEvent(planItem, courseDetails, form));
		String atpId = planItem.getPlanPeriods().get(0);
		events.putAll(makeUpdateTotalCreditsEvent(atpId,
				PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS));

		form.setJavascriptEvents(events);

		// Pass the ATP name in the params.
		String[] params = { KsapFrameworkServiceLocator.getTermHelper()
				.getYearTerm(planItem.getPlanPeriods().get(0)).getTermName() };
		return doPlanActionSuccess(form,
				PlanConstants.SUCCESS_KEY_PLANNED_ITEM_MARKED_BACKUP, params);
	}

	@RequestMapping(params = "methodToCall=backupToPlanned")
	public ModelAndView backupToPlanned(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			return doAdviserAccessError(form, "Adviser Access Denied", null);
		}

		String planItemId = form.getPlanItemId();
		if (StringUtils.isEmpty(planItemId)) {
			return doOperationFailedError(form, "Plan Item ID was missing.",
					null);
		}

		// Verify type backup, change to planned, update, make events (delete,
		// add, update credits).
		PlanItemInfo planItem = null;
		try {
			planItem = getAcademicPlanService().getPlanItem(planItemId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not fetch plan item.", e);
		}

		// Verify that the plan item type is "backup".
		if (!planItem.getTypeKey().equals(
				PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
			return doOperationFailedError(form,
					"Move planned item was not type backup.", null);
		}

		// Validate: Capacity.
		boolean hasCapacity = false;
		try {
			hasCapacity = isAtpHasCapacity(getLearningPlan(getUserId()),
					planItem.getPlanPeriods().get(0),
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED);
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Could not validate capacity for new plan item.", e);
		}
		if (!hasCapacity) {
			return doPlanCapacityExceededError(form,
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED);
		}

		// Lookup course details.
		CourseSummaryDetails courseDetails = null;
		try {
			courseDetails = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(planItem.getRefObjectId());
		} catch (Exception e) {
			return doOperationFailedError(form,
					"Unable to retrieve Course Details.", e);
		}

		// Make removed event before updating the plan item.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> removeEvent = makeRemoveEvent(
				planItem, courseDetails, planItem.getRefObjectId(), form, null);

		// Set type to "planned".
		planItem.setTypeKey(PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED);

		// Update
		try {
			getAcademicPlanService().updatePlanItem(planItemId, planItem,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not udpate plan item.",
					e);
		}

		// Make events (delete, add, update credits).
		// Set the javascript event(s) that should be thrown in the UI.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new HashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();

		events.putAll(removeEvent);
		events.putAll(makeAddEvent(planItem, courseDetails, form));
		String atpId = planItem.getPlanPeriods().get(0);
		events.putAll(makeUpdateTotalCreditsEvent(atpId,
				PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS));

		form.setJavascriptEvents(events);

		String[] params = { KsapFrameworkServiceLocator.getTermHelper()
				.getYearTerm(planItem.getPlanPeriods().get(0)).getTermName() };
		return doPlanActionSuccess(form,
				PlanConstants.SUCCESS_KEY_PLANNED_ITEM_MARKED_PLANNED, params);
	}

	@RequestMapping(params = "methodToCall=movePlanCourse")
	public ModelAndView movePlannedCourse(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			return doAdviserAccessError(form, "Adviser Access Denied", null);
		}
		/**
		 * This method needs a Plan Item ID and an ATP ID.
		 */
		String planItemId = form.getPlanItemId();
		if (StringUtils.isEmpty(planItemId)) {
			return doOperationFailedError(form, "Plan Item ID was missing.",
					null);
		}
		// Further validation of ATP IDs will happen in the service validation
		// methods.
		if (StringUtils.isEmpty(form.getAtpId())) {
			return doOperationFailedError(form, "Term Year value missing", null);
		}

		/*
		 * Move doesn't currently support changing plan type in the same
		 * operation (aka diagonal moves).
		 * 
		 * Should the course be type 'planned' or 'backup'. Default to planned.
		 * boolean backup = form.isBackup();
		 * 
		 * String newType = PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED; if
		 * (backup) { newType = PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP; }
		 */

		// This list can only contain one item, otherwise the backend validation
		// will fail.
		// Use LinkedList here so that the remove method works during "other"
		// option processing.
		List<String> newAtpIds = null;
		try {
			newAtpIds = getNewTermIds(form);
		} catch (RuntimeException e) {
			return doOperationFailedError(form, "Unable to process request.", e);
		}

		try {
			KsapFrameworkServiceLocator.getTermHelper().getTerm(
					newAtpIds.get(0));
		} catch (IllegalArgumentException e) {
			LOG.warn("Invalid ATP ID " + newAtpIds.get(0), e);
			return doOperationFailedError(form, String.format(
					"ATP ID [%s] does not refer to a valid term.",
					newAtpIds.get(0)), null);
		}

		PlanItemInfo planItem = null;
		try {
			// First load the plan item and retrieve the courseId
			planItem = getAcademicPlanService().getPlanItem(planItemId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not fetch plan item.", e);
		}

		if (planItem == null) {
			return doOperationFailedError(form,
					String.format("Could not fetch plan item."), null);
		}

		// Lookup course details as they will be needed for errors.
		CourseSummaryDetails courseDetails = null;
		try {
			courseDetails = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(planItem.getRefObjectId());
		} catch (Exception e) {
			return doOperationFailedError(form,
					"Unable to retrieve Course Details.", null);
		}

		// Make sure there isn't a plan item for the same course id in the
		// destination ATP.
		PlanItemInfo existingPlanItem = null;
		try {
			existingPlanItem = getPlannedOrBackupPlanItem(
					planItem.getRefObjectId(), newAtpIds.get(0));
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Query for existing plan item failed.", null);
		}

		if (existingPlanItem != null) {
			String[] params = {
					courseDetails.getCode(),
					KsapFrameworkServiceLocator.getTermHelper()
							.getYearTerm(newAtpIds.get(0)).getTermName() };
			return doErrorPage(form,
					PlanConstants.ERROR_KEY_PLANNED_ITEM_ALREADY_EXISTS, params);
		}

		// Create events before updating the plan item.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> originalRemoveEvents = makeRemoveEvent(
				planItem, courseDetails, planItem.getRefObjectId(), form, null);
		// Save the source ATP ID to create credit total updates later.
		String originalAtpId = planItem.getPlanPeriods().get(0);

		// Do validations.
		// Validate: Plan Size exceeded.
		boolean hasCapacity = false;
		try {
			hasCapacity = isAtpHasCapacity(getLearningPlan(getUserId()),
					newAtpIds.get(0), planItem.getTypeKey());
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Could not validate capacity for new plan item.", e);
		}
		if (!hasCapacity) {
			return doPlanCapacityExceededError(form, planItem.getTypeKey());
		}

		// Validate: Adding to historical term.
		if (!KsapFrameworkServiceLocator.getTermHelper().isPlanning(
				newAtpIds.get(0))) {
			return doCannotChangeHistoryError(form);
		}

		// Update the plan item.
		planItem.setPlanPeriods(newAtpIds);
		// Changing types not current supported in this operation.
		// planItem.setTypeKey(newType);

		try {
			getAcademicPlanService().updatePlanItem(planItem.getId(), planItem,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not udpate plan item.",
					e);
		}

		// Set the status of the request for the UI.
		form.setRequestStatus(PlanForm.REQUEST_STATUS.SUCCESS);

		// Make Javascript UI events (delete, add, update credits).
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();
		// Add events generated for the plan item before it was updated.
		events.putAll(originalRemoveEvents);
		// Create update total credits on source ATP.
		events.putAll(makeUpdateTotalCreditsEvent(originalAtpId,
				PlanConstants.JS_EVENT_NAME.UPDATE_OLD_TERM_TOTAL_CREDITS));

		try {
			events.putAll(makeAddEvent(planItem, courseDetails, form));
		} catch (RuntimeException e) {
			return doOperationFailedError(form, "Unable to create add event.",
					e);
		}
		events.putAll(makeUpdateTotalCreditsEvent(newAtpIds.get(0),
				PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS));

		form.setJavascriptEvents(events);

		String atpId = planItem.getPlanPeriods().get(0);

        //Not used or implemented
		/*String link = makeLinkToAtp(atpId, KsapFrameworkServiceLocator
				.getTermHelper().getYearTerm(atpId).getTermName());*/
		String[] params = { KsapFrameworkServiceLocator.getTermHelper()
				.getYearTerm(planItem.getPlanPeriods().get(0)).getTermName() };
		return doPlanActionSuccess(form,
				PlanConstants.SUCCESS_KEY_PLANNED_ITEM_MOVED, params);
	}

	/**
	 * Build an HTML link to a specific ATP in the quarter view.
	 * 
	 * @param atpId
	 * @param text
	 * @return
	 */
	private String makeLinkToAtp(String atpId, String text) {
		return ConfigContext.getCurrentContextConfig()
				.getProperty("ksap.plan.quarterUrl").replace("{atpId}", atpId)
				.replace("{label}", text);
	}

	@RequestMapping(params = "methodToCall=copyPlanCourse")
	public ModelAndView copyPlannedCourse(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			return doAdviserAccessError(form, "Adviser Access Denied", null);
		}
		/**
		 * This method needs a Plan Item ID and an ATP ID.
		 */
		String planItemId = form.getPlanItemId();
		if (StringUtils.isEmpty(planItemId)) {
			return doOperationFailedError(form, "Plan Item ID was missing.",
					null);
		}

		// validation of Year and Term will happen in the service validation
		// methods.
		if (StringUtils.isEmpty(form.getAtpId())) {
			return doOperationFailedError(form, "Term Year value missing", null);
		}

		// Should the course be type 'planned' or 'backup'. Default to planned.
		// boolean backup = form.isBackup();

		// String newType = PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED;
		// if (backup) {
		// newType = PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP;
		// }

		// This list can only contain one item, otherwise the backend validation
		// will fail.
		// Use LinkedList here so that the remove method works during "other"
		// option processing.
		List<String> newAtpIds = null;
		try {
			newAtpIds = getNewTermIds(form);
		} catch (RuntimeException e) {
			return doOperationFailedError(form, "Unable to process request.", e);
		}

		try {
			KsapFrameworkServiceLocator.getTermHelper().getTerm(
					newAtpIds.get(0));
		} catch (IllegalArgumentException e) {
			LOG.warn("Invalid ATP ID " + newAtpIds.get(0), e);
			return doOperationFailedError(form, String.format(
					"ATP ID [%s] does not refer to a valid term.",
					newAtpIds.get(0)), null);
		}

		PlanItemInfo planItem = null;
		try {
			// First load the plan item and retrieve the courseId
			planItem = getAcademicPlanService().getPlanItem(planItemId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not fetch plan item.", e);
		}

		if (planItem == null) {
			return doOperationFailedError(form,
					String.format("Could not fetch plan item."), null);
		}

		// Lookup course details as they will be needed for errors.
		CourseSummaryDetails courseDetails = null;
		try {
			courseDetails = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(planItem.getRefObjectId());
		} catch (Exception e) {
			return doOperationFailedError(form,
					"Unable to retrieve Course Details.", e);
		}

		// Make sure there isn't a plan item for the same course id in the
		// destination ATP.
		PlanItemInfo existingPlanItem = null;
		try {
			existingPlanItem = getPlannedOrBackupPlanItem(
					planItem.getRefObjectId(), newAtpIds.get(0));
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Query for existing plan item failed.", e);
		}

		if (existingPlanItem != null) {
			String[] params = {
					courseDetails.getCode(),
					KsapFrameworkServiceLocator.getTermHelper()
							.getYearTerm(newAtpIds.get(0)).getTermName() };
			return doErrorPage(form,
					PlanConstants.ERROR_KEY_PLANNED_ITEM_ALREADY_EXISTS, params);
		}

		// Do validations.
		// Validate: Plan Size exceeded.
		boolean hasCapacity = false;
		LearningPlan learningPlan = null;
		try {
			learningPlan = getLearningPlan(getUserId());
			hasCapacity = isAtpHasCapacity(learningPlan, newAtpIds.get(0),
					planItem.getTypeKey());
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Could not validate capacity for new plan item.", e);
		}
		if (!hasCapacity) {
			return doPlanCapacityExceededError(form, planItem.getTypeKey());
		}

		// Validate: Adding to historical term.
		if (!KsapFrameworkServiceLocator.getTermHelper().isPlanning(
				newAtpIds.get(0))) {
			return doCannotChangeHistoryError(form);
		}

		// Update the plan item.
		planItem.setPlanPeriods(newAtpIds);
		// Do not allow diagonal moves .
		// planItem.setTypeKey(newType);

		PlanItemInfo planItemCopy = null;
		try {
			String courseId = planItem.getRefObjectId();
			planItemCopy = addPlanItem(learningPlan, courseId, newAtpIds,
					planItem.getTypeKey());
		} catch (DuplicateEntryException e) {
			return doDuplicatePlanItem(form,
					formatAtpIdForUI(newAtpIds.get(0)), courseDetails);
		} catch (Exception e) {
			return doOperationFailedError(form, "Unable to add plan item.", e);
		}

		// Set the status of the request for the UI.
		form.setRequestStatus(PlanForm.REQUEST_STATUS.SUCCESS);

		// Create the map of javascript event(s) that should be thrown in the
		// UI.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();

		try {
			events.putAll(makeAddEvent(planItemCopy, courseDetails, form));
		} catch (RuntimeException e) {
			return doOperationFailedError(form, "Unable to create add event.",
					e);
		}

		events.putAll(makeUpdateTotalCreditsEvent(planItemCopy.getPlanPeriods()
				.get(0),
				PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS));

		// Populate the form.
		form.setJavascriptEvents(events);

		String atpId = planItem.getPlanPeriods().get(0);
        //Not used or implemented
		/*String link = makeLinkToAtp(atpId, KsapFrameworkServiceLocator
				.getTermHelper().getYearTerm(atpId).getTermName());*/
		String[] params = { KsapFrameworkServiceLocator.getTermHelper()
				.getYearTerm(atpId).getTermName() };
		return doPlanActionSuccess(form,
				PlanConstants.SUCCESS_KEY_PLANNED_ITEM_COPIED, params);
	}

	@RequestMapping(params = "methodToCall=addPlanCourse")
	public ModelAndView addPlannedCourse(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			return doAdviserAccessError(form, "Adviser Access Denied", null);
		}
		/**
		 * This method needs a Course ID and an ATP ID.
		 */
		String courseId = form.getCourseId();
		if (StringUtils.isEmpty(courseId)) {
			return doOperationFailedError(form, "Course ID was missing.", null);
		}

		// Retrieve courseDetails based on the passed in CourseId and then
		// update courseDetails based on the version independent Id
		CourseSummaryDetails courseDetails = null;
		// Now switch to the details based on the version independent Id
		// Lookup course details as well need them in case there is an error
		// below.
		List<ActivityOfferingItem> activityOfferings = new ArrayList<ActivityOfferingItem>();
		try {
			courseDetails = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(courseId);

			// Now switch the courseDetails based on the versionIndependent Id
			/*
			 * if (!courseId.equals(courseDetails.getVersionIndependentId())) {
			 * courseDetails =
			 * getCourseDetailsInquiryService().retrieveCourseSummaryById
			 * (courseDetails.getVersionIndependentId()); }
			 */

			activityOfferings = getCourseDetailsInquiryService()
					.getActivityOfferingItemsById(courseId, form.getAtpId());
		} catch (Exception e) {
			return doOperationFailedError(form,
					"Unable to retrieve Course Details.", null);
		}

		// Further validation of ATP IDs will happen in the service validation
		// methods.
		if (StringUtils.isEmpty(form.getAtpId())) {
			return doOperationFailedError(form, "Term Year value missing", null);
		}

		// Should the course be type 'planned' or 'backup'. Default to planned.
		boolean backup = form.isBackup();
		String newType = PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED;
		if (backup) {
			newType = PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP;
		}

		// This list can only contain one item, otherwise the backend validation
		// will fail.
		List<String> newAtpIds = null;
		try {
			newAtpIds = getNewTermIds(form);
		} catch (RuntimeException e) {
			return doOperationFailedError(form, "Unable to process request.", e);
		}

		try {
			KsapFrameworkServiceLocator.getTermHelper().getTerm(
					newAtpIds.get(0));
		} catch (IllegalArgumentException e) {
			LOG.warn("Invalid ATP ID " + newAtpIds.get(0), e);
			return doOperationFailedError(form, String.format(
					"ATP ID [%s] does not refer to a valid term.",
					newAtpIds.get(0)), null);
		}

		String studentId = getUserId();

		LearningPlan plan = null;
		try {
			// If something goes wrong with the query then a RuntimeException
			// will be thrown. Otherwise, the method
			// will return the default plan or null. Having multiple plans will
			// also produce a RuntimeException.
			plan = getLearningPlan(studentId);
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Query for default learning plan failed.", e);
		}

		/*
		 * Create a default learning plan if there isn't one already and skip
		 * querying for plan items.
		 */
		// TODO: There is a potential (small) for multiple plan's created in
		// this model coz of multi threading. There should be a check
		// at the db level to restrict a single plan of a given type to a
		// student
		if (plan == null) {
			try {
				plan = createDefaultLearningPlan(studentId);
			} catch (Exception e) {
				return doOperationFailedError(form,
						"Unable to create learning plan.", e);
			}
		}

		/* Retrieve course activity offerings for the term to be planned */
		/* Adding Section related courses logic */
		boolean addCourse = true;
		boolean addPrimaryCourse = false;
		boolean addSecondaryCourse = false;
		String primarySectionCode = null;
		String secondarySectionCode = null;
		if (form.getSectionCode() != null) {
			/* Populate the primary and secondary flags */
			for (ActivityOfferingItem activityOfferingItem : activityOfferings) {
				if (activityOfferingItem.getCode().equalsIgnoreCase(
						form.getSectionCode())) {
					if (activityOfferingItem.isPrimary()) {
						PlanItemInfo coursePlanItem = getPlannedOrBackupPlanItem(
								courseDetails.getVersionIndependentId(),
								form.getAtpId());
						if (coursePlanItem != null) {
							addCourse = false;
						}
						addPrimaryCourse = true;
						primarySectionCode = activityOfferingItem.getCode();
						break;
					} else {
						PlanItemInfo primaryPlanItem = getPlannedOrBackupPlanItem(
								courseDetails.getCode()
										+ " "
										+ activityOfferingItem.getCode()
												.substring(0, 1),
								form.getAtpId());
						if (primaryPlanItem != null) {
							addCourse = false;
						} else {
							addPrimaryCourse = true;
							primarySectionCode = activityOfferingItem.getCode()
									.substring(0, 1);
							form.setPrimarySectionCode(primarySectionCode);
							PlanItemInfo coursePlanItem = getPlannedOrBackupPlanItem(
									courseDetails.getVersionIndependentId(),
									form.getAtpId());
							if (coursePlanItem != null) {
								addCourse = false;
							}
						}
						addSecondaryCourse = true;
						secondarySectionCode = activityOfferingItem.getCode();
						break;
					}
				}
			}

		}

		/* Do validations. */
		// Plan Size exceeded.
		boolean hasCapacity = false;
		try {
			hasCapacity = isAtpHasCapacity(plan, newAtpIds.get(0), newType);
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Could not validate capacity for new plan item.", e);
		}

		if (!hasCapacity) {
			return doPlanCapacityExceededError(form, newType);
		}

		// Validate: Adding to historical term.
		if (!KsapFrameworkServiceLocator.getTermHelper().isPlanning(
				newAtpIds.get(0))) {
			return doCannotChangeHistoryError(form);
		}

		// See if a wishlist item exists for the course. If so, then update it.
		// Otherwise create a new plan item.
		PlanItemInfo planItem = getWishlistPlanItem(courseDetails
				.getVersionIndependentId());

		/* PlanItems for sections */
		PlanItemInfo primaryPlanItem = null;
		PlanItemInfo secondaryPlanItem = null;
		// Storage for wishlist events.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> wishlistEvents = null;
		// Create a new plan item if no wishlist exists. Otherwise, update the
		// wishlist item.
		if (planItem == null) {
			try {
				if (addCourse) {
					planItem = addPlanItem(plan, courseDetails.getCourseId(),
							newAtpIds, newType);
				}
				if (addPrimaryCourse) {
					if (primarySectionCode != null) {
						// primaryPlanItem = addActivityOfferingPlanItem(plan,
						// courseDetails.getCode() + " " + primarySectionCode,
						// newAtpIds, newType);
						// form.setPrimaryPlanItemId(primaryPlanItem.getId());

					} else {
						return doOperationFailedError(form,
								"Could not add section to new plan item.", null);
					}
				}
				if (addSecondaryCourse) {
					if (secondarySectionCode != null) {
						// secondaryPlanItem = addActivityOfferingPlanItem(plan,
						// courseDetails.getCode() + " " + secondarySectionCode,
						// newAtpIds, newType);

					} else {
						return doOperationFailedError(form,
								"Could not add section to new plan item.", null);
					}
				}
			} catch (DuplicateEntryException e) {
				return doDuplicatePlanItem(form, newAtpIds.get(0),
						courseDetails);
			} catch (Exception e) {
				return doOperationFailedError(form, "Unable to add plan item.",
						e);
			}
		} else {
			// Check for duplicates since addPlanItem isn't being called.
			if (addCourse
					&& isDuplicate(plan, newAtpIds.get(0),
							courseDetails.getVersionIndependentId(), newType)) {
				return doDuplicatePlanItem(form, newAtpIds.get(0),
						courseDetails);
			}
			// Create wishlist events before updating the plan item.
			wishlistEvents = makeRemoveEvent(planItem, courseDetails,
					planItem.getRefObjectId(), form, null);
			planItem.setTypeKey(newType);
			planItem.setPlanPeriods(newAtpIds);

			try {
				if (addCourse) {
					planItem = getAcademicPlanService().updatePlanItem(
							planItem.getId(),
							planItem,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
				}
				if (addPrimaryCourse) {
					if (primarySectionCode != null) {
						// primaryPlanItem = addActivityOfferingPlanItem(plan,
						// courseDetails.getCode() + " " + primarySectionCode,
						// newAtpIds, newType);
						// form.setPrimaryPlanItemId(primaryPlanItem.getId());

					} else {
						return doOperationFailedError(form,
								"Could not add section to new plan item.", null);
					}
				}
				if (addSecondaryCourse) {
					if (secondarySectionCode != null) {
						// secondaryPlanItem = addActivityOfferingPlanItem(plan,
						// courseDetails.getCode() + " " + secondarySectionCode,
						// newAtpIds, newType);

					} else {
						return doOperationFailedError(form,
								"Could not add section to new plan item.", null);
					}
				}
			} catch (Exception e) {
				return doOperationFailedError(form,
						"Unable MetaENtito update wishlist plan item.", e);
			}
		}

		// Create the map of javascript event(s) that should be thrown in the
		// UI.
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();

		// If a wishlist item was clobbered then generate Javascript events.
		if (wishlistEvents != null) {
			events.putAll(wishlistEvents);
		}

		try {
			if (planItem != null) {
				events.putAll(makeAddEvent(planItem, courseDetails, form));
			}
			if (primaryPlanItem != null) {
				events.putAll(makeAddEvent(primaryPlanItem, courseDetails, form));
			}
			if (secondaryPlanItem != null) {
				events.putAll(makeAddEvent(secondaryPlanItem, courseDetails,
						form));
			}
		} catch (RuntimeException e) {
			return doOperationFailedError(form, "Unable to create add event.",
					e);
		}

		if (planItem != null) {
			events.putAll(makeUpdateTotalCreditsEvent(planItem.getPlanPeriods()
					.get(0),
					PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS));
		}

		// Populate the form.
		form.setJavascriptEvents(events);

		String[] params = {};
		if (planItem != null) {
			params = new String[] { KsapFrameworkServiceLocator.getTermHelper()
					.getYearTerm(planItem.getPlanPeriods().get(0))
					.getTermName() };
		} else if (primaryPlanItem != null) {
			params = new String[] { KsapFrameworkServiceLocator.getTermHelper()
					.getYearTerm(primaryPlanItem.getPlanPeriods().get(0))
					.getTermName() };

		} else if (secondaryPlanItem != null) {
			params = new String[] { KsapFrameworkServiceLocator.getTermHelper()
					.getYearTerm(secondaryPlanItem.getPlanPeriods().get(0))
					.getTermName() };
		}

		return doPlanActionSuccess(form,
				PlanConstants.SUCCESS_KEY_PLANNED_ITEM_ADDED, params);
	}

	/* Academic Planner */

	@RequestMapping(params = "methodToCall=academicPlanner")
	public ModelAndView academicPlanner(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			String[] params = {};
			return doErrorPage(form, PlanConstants.ERROR_KEY_ADVISER_ACCESS,
					params);
		}
		List<LearningPlanInfo> plan = new ArrayList<LearningPlanInfo>();
		try {
			String studentId = getUserId();
			plan = getAcademicPlanService().getLearningPlansForStudentByType(
					studentId, PlanConstants.LEARNING_PLAN_TYPE_PLAN,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
			if (plan.size() > 0) {
				if (!plan.get(0).getShared().toString()
						.equalsIgnoreCase(form.getEnableAdviserView())) {
					if (form.getEnableAdviserView().equalsIgnoreCase(
							PlanConstants.LEARNING_PLAN_ITEM_SHARED_TRUE_KEY)) {
						plan.get(0).setShared(true);
					} else {
						plan.get(0).setShared(false);
					}
					plan.get(0).setStateKey(
							PlanConstants.LEARNING_PLAN_ACTIVE_STATE_KEY);
					getAcademicPlanService().updateLearningPlan(
							plan.get(0).getId(),
							plan.get(0),
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
				}
			} else {
				LearningPlanInfo planInfo = new LearningPlanInfo();
				planInfo.setTypeKey(PlanConstants.LEARNING_PLAN_TYPE_PLAN);
				RichTextInfo rti = new RichTextInfo();
				rti.setFormatted("");
				rti.setPlain("");
				if (form.getEnableAdviserView().equalsIgnoreCase(
						PlanConstants.LEARNING_PLAN_ITEM_SHARED_TRUE_KEY)) {
					planInfo.setShared(true);
				} else {
					planInfo.setShared(false);
				}
				planInfo.setDescr(rti);
				planInfo.setStudentId(studentId);
				planInfo.setStateKey(PlanConstants.LEARNING_PLAN_ACTIVE_STATE_KEY);
				planInfo.setMeta(new MetaInfo());

				ContextInfo context = new ContextInfo();
				context.setPrincipalId(studentId);
				getAcademicPlanService().createLearningPlan(planInfo, context);
			}
		} catch (Exception e) {
			return doOperationFailedError(form,
					"Query for default learning plan failed.", e);
		}

		return getUIFModelAndView(form);

	}

	/**
	 * AtpId generated from the year and the term in the form .
	 * 
	 * @param form
	 * @return
	 */
	private List<String> getNewTermIds(PlanForm form) {
		List<String> newTermIds = new LinkedList<String>();
		// Create an ATP id from the values in the year and term fields.
		if (StringUtils.isEmpty(form.getAtpId())) {
			throw new RuntimeException(
					"Could not construct ATP id for Given TermYear option because year was blank.");
		}

		newTermIds.add(form.getAtpId());
		return newTermIds;
	}

	/**
	 * Check for duplicate plan items by type.
	 * 
	 * @param plan
	 * @param atpId
	 * @param courseId
	 * @param planItemType
	 * @return
	 */
	private boolean isDuplicate(LearningPlan plan, String atpId,
			String courseId, String planItemType) {
		/*
		 * Make sure no dups exist. The rules are different for wishlist vs
		 * planned or backup courses.
		 */
		boolean isDuplicate = false;
		if (planItemType.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST)) {
			if (getWishlistPlanItem(courseId) != null) {
				isDuplicate = true;
			}
		} else {
			if (getPlannedOrBackupPlanItem(courseId, atpId) != null) {
				isDuplicate = true;
			}
		}
		return isDuplicate;
	}

	/**
	 * Determines if a plan has capacity in within a particular ATP for adding a
	 * new plan item of a specific type.
	 * 
	 * @param plan
	 * @param atpId
	 * @param typeKey
	 * @return True if the item can be added or false if not.
	 * @throws RuntimeException
	 *             if things go wrong.
	 */
	private boolean isAtpHasCapacity(LearningPlan plan, String atpId,
			String typeKey) {
		if (plan == null) {
			throw new RuntimeException("Plan was NULL.");
		}

		if (StringUtils.isEmpty(atpId)) {
			throw new RuntimeException("Course Id was empty.");
		}

		List<PlanItemInfo> planItems = null;
		PlanItem item = null;
		try {
			planItems = getAcademicPlanService().getPlanItemsInPlanByType(
					plan.getId(), typeKey,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Could not retrieve plan items.", e);
		}

		int counter = 0;
		if (planItems == null) {
			throw new RuntimeException("Could not retrieve plan items.");
		} else {
			for (PlanItem p : planItems) {
				if (p.getPlanPeriods().get(0).equals(atpId)
						&& p.getRefObjectType().equalsIgnoreCase(
								PlanConstants.COURSE_TYPE)) {
					counter++;
				}
			}
		}

		if (typeKey.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
			return (counter >= PlanConstants.BACKUP_PLAN_ITEM_CAPACITY) ? false
					: true;
		} else if (typeKey
				.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
			return (counter >= PlanConstants.PLANNED_PLAN_ITEM_CAPACITY) ? false
					: true;
		}

		throw new RuntimeException(String.format(
				"Unknown plan item type [%s].", typeKey));
	}

	@RequestMapping(params = "methodToCall=addSavedCourse")
	public ModelAndView addSavedCourse(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			return doAdviserAccessError(form, "Adviser Access Denied", null);
		}

		String courseId = form.getCourseId();
		if (StringUtils.isEmpty(courseId)) {
			return doOperationFailedError(form, "Course ID was missing.", null);
		}

		String studentId = getUserId();
		LearningPlan plan = null;
		try {
			// Throws RuntimeException is there is a problem. Otherwise, returns
			// a plan or null.
			plan = getLearningPlan(studentId);
		} catch (RuntimeException e) {
			return doOperationFailedError(form,
					"Query for default learning plan failed.", e);
		}

		/*
		 * Create a default learning plan if there isn't one already and skip
		 * querying for plan items.
		 */
		if (plan == null) {
			try {
				plan = createDefaultLearningPlan(studentId);
			} catch (Exception e) {
				return doOperationFailedError(form,
						"Unable to create learning plan.", e);
			}
		}

		// Retrieve courseDetails based on the passed in CourseId and then
		// update courseDetails based on the version independent Id
		CourseSummaryDetails courseDetails = null;
		// Now switch to the details based on the version independent Id
		// Lookup course details as well need them in case there is an error
		// below.
		try {
			courseDetails = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(courseId);

			// Now switch the courseDetails based on the versionIndependent Id
			/*
			 * if (!courseId.equals(courseDetails.getVersionIndependentId())) {
			 * courseDetails =
			 * getCourseDetailsInquiryService().retrieveCourseSummaryById
			 * (courseDetails.getVersionIndependentId()); }
			 */
		} catch (Exception e) {
			return doOperationFailedError(form,
					"Unable to retrieve Course Details.", null);
		}

		PlanItemInfo planItem = null;
		try {
			planItem = addPlanItem(plan, courseDetails.getCourseId(), null,
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST);
		} catch (DuplicateEntryException e) {
			return doDuplicatePlanItem(form, null, courseDetails);
		} catch (Exception e) {
			return doOperationFailedError(form, "Unable to add plan item.", e);
		}

		// Create events
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();
		events.putAll(makeAddEvent(planItem, courseDetails, form));

		form.setRequestStatus(PlanForm.REQUEST_STATUS.SUCCESS);
		form.setJavascriptEvents(events);

		return doPlanActionSuccess(form,
				PlanConstants.SUCCESS_KEY_SAVED_ITEM_ADDED, new String[0]);
	}

	@RequestMapping(params = "methodToCall=removeItem")
	public ModelAndView removePlanItem(
			@ModelAttribute("KualiForm") PlanForm form, BindingResult result,
			HttpServletRequest httprequest, HttpServletResponse httpresponse) {
		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			return doAdviserAccessError(form, "Adviser Access Denied", null);
		}

		String planItemId = form.getPlanItemId();
		String courseId = form.getCourseId();
		if (StringUtils.isEmpty(planItemId) && StringUtils.isEmpty(courseId)) {
			return doOperationFailedError(form,
					"Plan item id and courseId are missing.", null);
		}

		if (StringUtils.isEmpty(planItemId)) {
			planItemId = getPlanIdFromCourseId(courseId,
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST);
		}

		String sectionCode = null;
		// See if the plan item exists.
		PlanItemInfo planItem = null;
		try {
			planItem = getAcademicPlanService().getPlanItem(planItemId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			return doPageRefreshError(form, String.format(
					"No plan item with id [%s] exists.", planItemId), e);
		} catch (Exception e) {
			return doOperationFailedError(form, "Query for plan item failed.",
					e);
		}
		/*List<String> terms = new ArrayList();
		for (String term : planItem.getPlanPeriods()) {
			terms.add(term);
		}*/
		CourseSummaryDetails courseDetail = null;
		List<CourseOfferingInstitution> courseOfferingInstitutions = new ArrayList<CourseOfferingInstitution>();
		if (planItem.getRefObjectType().equalsIgnoreCase(
				PlanConstants.COURSE_TYPE)) {
			courseDetail = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(planItem.getRefObjectId());
			courseId = courseDetail.getCourseId();
		} else {
            /*Needs to be changed to use service calls to get the course information instead of
            string manipulations*/

			EnrollmentStatusHelper enrollmentStatusHelper = KsapFrameworkServiceLocator
					.getEnrollmentStatusHelper();
			CourseCode courseCodeAndSection = enrollmentStatusHelper
					.getCourseDivisionAndNumber(planItem.getRefObjectId());
			courseId = enrollmentStatusHelper.getCourseId(
					courseCodeAndSection.getSubject(),
					courseCodeAndSection.getNumber());
			courseDetail = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(courseId);
			sectionCode = courseCodeAndSection.getSection();
		}
		courseOfferingInstitutions = getCourseDetailsInquiryService()
				.getCourseOfferingInstitutionsById(courseId, planItem.getPlanPeriods());
		Map<String, String> planItemsToRemove = new HashMap<String, String>();
		if (!AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST
				.equals(planItem.getTypeKey())) {
			if (planItem.getRefObjectType().equalsIgnoreCase(
					PlanConstants.COURSE_TYPE)) {
				planItemsToRemove = getPlannedSections(courseDetail,
						courseOfferingInstitutions, planItem,
						form.getInstituteCode(), false, null);
			} else if (form.isPrimary()) {
				planItemsToRemove = getPlannedSections(courseDetail,
						courseOfferingInstitutions, planItem,
						form.getInstituteCode(), true, sectionCode);
			}
		}

		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();

		// Make events ...

		events.putAll(makeRemoveEvent(planItem, null, courseId, form,
				planItemsToRemove));
		planItemsToRemove.put(planItemId, null);
		try {
			if (planItemsToRemove.size() > 0) {
				for (String planItemIdToRemove : planItemsToRemove.keySet()) {
					getAcademicPlanService().deletePlanItem(
							planItemIdToRemove,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
				}
			}
		} catch (Exception e) {
			return doOperationFailedError(form, "Could not delete plan item", e);
		}

		if (planItem.getTypeKey().equals(
				PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
			events.putAll(makeUpdateTotalCreditsEvent(planItem.getPlanPeriods()
					.get(0),
					PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS));
		}

		form.setJavascriptEvents(events);
		return doPlanActionSuccess(form,
				PlanConstants.SUCCESS_KEY_ITEM_DELETED, new String[0]);
	}

	/**
	 * Used to get the list of all the section that are planned for the
	 * institute code or for the term
	 * 
	 * @param courseDetails
	 * @param planItem
	 * @return
	 */
	private Map<String, String> getPlannedSections(
			CourseSummaryDetails courseDetails,
			List<CourseOfferingInstitution> courseOfferingInstitutions,
			PlanItem planItem, String instituteCode, boolean isPrimary,
			String sectionCode) {
		Map<String, String> plannedSections = new HashMap<String, String>();
		String planItemId = planItem.getId();
		List<ActivityOfferingItem> sectionsPlanned = new ArrayList<ActivityOfferingItem>();
		for (CourseOfferingInstitution courseOfferingInstitution : courseOfferingInstitutions) {
			for (CourseOfferingTerm courseOfferingTerm : courseOfferingInstitution
					.getCourseOfferingTermList()) {
				for (ActivityOfferingItem activityOfferingItem : courseOfferingTerm
						.getActivityOfferingItemList()) {
					if (null != activityOfferingItem.getPlanItemId()) {
						sectionsPlanned.add(activityOfferingItem);
					}
				}
			}
		}
		List<ActivityOfferingItem> activityOfferingItems = new ArrayList<ActivityOfferingItem>();
		if (instituteCode == null) {
			activityOfferingItems = sectionsPlanned;
		} else {
			for (ActivityOfferingItem activityOfferingItem : sectionsPlanned) {
				if (activityOfferingItem.getInstituteCode().equalsIgnoreCase(
						instituteCode)) {
					activityOfferingItems.add(activityOfferingItem);
				}
			}
		}

		for (ActivityOfferingItem activityOfferingItem : activityOfferingItems) {
			if (!activityOfferingItem.getPlanItemId().equalsIgnoreCase(
					planItemId)) {
				if (isPrimary
						&& sectionCode != null
						&& activityOfferingItem.getCode().startsWith(
								sectionCode)
						&& !activityOfferingItem.isPrimary()) {
					plannedSections.put(activityOfferingItem.getPlanItemId(),
							activityOfferingItem.getCode());
				} else if (!isPrimary) {
					plannedSections.put(activityOfferingItem.getPlanItemId(),
							activityOfferingItem.getCode());
				}
			}
		}

		return plannedSections;
	}

	/**
	 * Blow-up response for all plan item actions for the Adviser.
	 */
	private ModelAndView doAdviserAccessError(PlanForm form,
			String errorMessage, Exception e) {
		String[] params = {};
		return doErrorPage(form, errorMessage,
				PlanConstants.ERROR_KEY_ADVISER_ACCESS, params, e);
	}

	/**
	 * Blow up response of the plan capacity validation fails.
	 * 
	 * @param form
	 * @return
	 */
	private ModelAndView doPlanCapacityExceededError(PlanForm form, String type) {
		String errorId = PlanConstants.ERROR_KEY_PLANNED_ITEM_CAPACITY_EXCEEDED;
		if (type.equals(PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
			errorId = PlanConstants.ERROR_KEY_BACKUP_ITEM_CAPACITY_EXCEEDED;
		}
		return doErrorPage(form, errorId, new String[0]);
	}

	/**
	 * Blow up response if the user tries to update plan items in past terms.
	 * 
	 * @param form
	 * @return
	 */
	private ModelAndView doCannotChangeHistoryError(PlanForm form) {
		return doErrorPage(form, PlanConstants.ERROR_KEY_HISTORICAL_ATP,
				new String[0]);
	}

	/**
	 * Blow-up response for all plan item actions.
	 */
	private ModelAndView doPageRefreshError(PlanForm form, String errorMessage,
			Exception e) {
		// <a
		// href="/student/myplan/plan?methodToCall=start&viewId=PlannedCourses-FormView">Reset
		// your academic plan</a>
		// Removed link because html string is being encoded in the view
		String[] params = {};
		if (e != null) {
			LOG.error(errorMessage, e);
		} else {
			LOG.error(errorMessage);
		}
		return doErrorPage(form, errorMessage,
				PlanConstants.ERROR_KEY_PAGE_RESET_REQUIRED, params, e);
	}

	/**
	 * Blow-up response for all plan item actions.
	 */
	private ModelAndView doOperationFailedError(PlanForm form,
			String errorMessage, Exception e) {
		String[] params = {};
		if (e != null) {
			LOG.error(errorMessage, e);
		} else {
			LOG.error(errorMessage);
		}
		return doErrorPage(form, errorMessage,
				PlanConstants.ERROR_KEY_OPERATION_FAILED, params, e);
	}

	/**
	 * Logs errors and passes the request on to the error page.
	 */
	private ModelAndView doErrorPage(PlanForm form, String errorMessage,
			String errorKey, String[] params, Exception e) {
		if (e != null) {
			LOG.error(errorMessage, e);
		} else {
			LOG.error(errorMessage);
		}
		return doErrorPage(form, errorKey, params);
	}

	/**
	 * Initializes the error page.
	 */
	private ModelAndView doErrorPage(PlanForm form, String errorKey,
			String[] params) {
		form.setRequestStatus(PlanForm.REQUEST_STATUS.ERROR);
		GlobalVariables.getMessageMap().clearErrorMessages();
		GlobalVariables.getMessageMap().putErrorForSectionId(
				PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID, errorKey, params);
		return getUIFModelAndView(form,
				PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID);
	}

	/**
	 * Blow-up response for all plan item actions.
	 */
	private ModelAndView doDuplicatePlanItem(PlanForm form, String atpId,
			CourseSummaryDetails courseDetails) {
		/*
		 * String[] t = {"?", "?"}; try { t =
		 * AtpHelper.atpIdToTermNameAndYear(atpId); } catch (RuntimeException e)
		 * { logger.error("Could not convert ATP ID to a term and year.", e); }
		 * String term = t[0] + " " + t[1];
		 */
		if (atpId != null) {
			String[] params = {
					courseDetails.getCode(),
					KsapFrameworkServiceLocator.getTermHelper()
							.getYearTerm(atpId).getTermName() };
			return doErrorPage(form,
					PlanConstants.ERROR_KEY_PLANNED_ITEM_ALREADY_EXISTS, params);
		} else {
			String[] params = {};
			return doErrorPage(form,
					PlanConstants.ERROR_KEY_PLANNED_ITEM_ALREADY_EXISTS, params);
		}

	}

	/**
	 * Success response for all plan item actions.
	 */
	private ModelAndView doPlanActionSuccess(PlanForm form, String key,
			String[] params) {
		form.setRequestStatus(PlanForm.REQUEST_STATUS.SUCCESS);
		GlobalVariables.getMessageMap().putInfoForSectionId(
				PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID, key, params);
		return getUIFModelAndView(form,
				PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID);
	}

	private String getCourseDetailsAsJson(String courseId) {
		// Also, add a full CourseDetails object so that course details
		// properties are available to be displayed on the form.
		CourseSummaryDetails courseDetails = null;
		try {
			courseDetails = getCourseDetailsInquiryService()
					.retrieveCourseSummaryById(courseId);
		} catch (Exception e) {
			throw new RuntimeException("Unable to retrieve Course Details.", e);
		}

		String courseDetailsAsJson;
		try {
			// Turn the list of javascript events into a string of JSON.
			courseDetailsAsJson = mapper.writeValueAsString(courseDetails);
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not convert javascript events to JSON.", e);
		}

		return courseDetailsAsJson;
	}

	/**
	 * Adds a plan item for the given course id and ATPs.
	 * 
	 * @param plan
	 *            The learning plan to add the item to.
	 * @param courseId
	 *            The id of the course.
	 * @param termIds
	 *            A list of ATP/term ids if the plan item is a planned course.
	 * @param planItemType
	 *            Saved couse or planned course.
	 * @return The newly created plan item or the existing plan item where a
	 *         plan item already exists for the given course.
	 * @throws RuntimeException
	 *             on errors.
	 */
	protected PlanItemInfo addPlanItem(LearningPlan plan, String courseId,
			List<String> termIds, String planItemType)
			throws DuplicateEntryException {

		if (StringUtils.isEmpty(courseId)) {
			throw new RuntimeException("Empty Course ID");
		}

		PlanItemInfo newPlanItem = null;

		PlanItemInfo pii = new PlanItemInfo();
		pii.setLearningPlanId(plan.getId());
		pii.setTypeKey(planItemType);
		pii.setRefObjectType(PlanConstants.COURSE_TYPE);
		pii.setRefObjectId(courseId);

		pii.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

		RichTextInfo rti = new RichTextInfo();
		rti.setFormatted("");
		rti.setPlain("");
		pii.setDescr(rti);

		String atpId = null;
		if (null != termIds) {
			pii.setPlanPeriods(termIds);
			atpId = termIds.get(0);
		}

		// Don't allow duplicates.
		if (isDuplicate(plan, atpId, courseId, planItemType)) {
			throw new DuplicateEntryException("Duplicate plan item exists.");
		}

		try {
			newPlanItem = getAcademicPlanService().createPlanItem(pii,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			LOG.error("Could not create plan item.", e);
			throw new RuntimeException("Could not create plan item.", e);
		}

		return newPlanItem;
	}

	/**
	 * @param plan
	 * @param refObjId
	 * @param termIds
	 * @param planItemType
	 * @return
	 * @throws DuplicateEntryException
	 */
	protected PlanItemInfo addActivityOfferingPlanItem(LearningPlan plan,
			String refObjId, List<String> termIds, String planItemType)
			throws DuplicateEntryException {

		if (StringUtils.isEmpty(refObjId)) {
			throw new RuntimeException("Empty RefObjId");
		}

		PlanItemInfo newPlanItem = null;

		PlanItemInfo pii = new PlanItemInfo();
		pii.setLearningPlanId(plan.getId());
		pii.setTypeKey(planItemType);
		pii.setRefObjectType(PlanConstants.SECTION_TYPE);
		pii.setRefObjectId(refObjId);

		pii.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

		RichTextInfo rti = new RichTextInfo();
		rti.setFormatted("");
		rti.setPlain("");
		pii.setDescr(rti);

		String atpId = null;
		if (null != termIds) {
			pii.setPlanPeriods(termIds);
			atpId = termIds.get(0);
		}

		// Don't allow duplicates.
		if (isDuplicate(plan, atpId, refObjId, planItemType)) {
			throw new DuplicateEntryException("Duplicate plan item exists.");
		}

		try {
			newPlanItem = getAcademicPlanService().createPlanItem(pii,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			LOG.error("Could not create plan item.", e);
			throw new RuntimeException("Could not create plan item.", e);
		}

		return newPlanItem;
	}

	/**
	 * Gets a plan item of a particular type for a particular ATP.
	 * 
	 * @param planId
	 *            The id of the learning plan
	 * @param courseId
	 *            The id of the course
	 * @param atpId
	 *            The ATP id
	 * @param planItemType
	 *            The plan item type key.
	 * @return A "planned" or "backup" plan item. Or 'null' if none exists.
	 * @throws RuntimeException
	 *             on errors.
	 */
	private PlanItemInfo getPlanItemByAtpAndType(String planId,
			String courseId, String atpId, String planItemType) {
		if (StringUtils.isEmpty(planId)) {
			throw new RuntimeException("Plan Id was empty.");
		}

		if (StringUtils.isEmpty(courseId)) {
			throw new RuntimeException("Course Id was empty.");
		}

		if (StringUtils.isEmpty(atpId)) {
			throw new RuntimeException("ATP Id was empty.");
		}

		List<PlanItemInfo> planItems = null;
		PlanItemInfo item = null;

		try {
			planItems = getAcademicPlanService().getPlanItemsInPlanByAtp(
					planId, atpId, planItemType,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Could not retrieve plan items.", e);
		}

		for (PlanItemInfo p : planItems) {
			if (p.getRefObjectId().equals(courseId)
					&& p.getTypeKey().equals(planItemType)) {
				item = p;
				break;
			}
		}

		// A null here means that no plan item exists for the given course and
		// ATP IDs.
		return item;
	}

	/**
	 * Gets a Plan Item of type "wishlist" for a particular course. There should
	 * only ever be one.
	 * 
	 * @param courseId
	 *            The id of the course.
	 * @return A PlanItem of type wishlist.
	 */
	protected PlanItemInfo getWishlistPlanItem(String courseId) {

		if (StringUtils.isEmpty(courseId)) {
			throw new RuntimeException("Course Id was empty.");
		}

		String studentId = getUserId();
		LearningPlan learningPlan = getLearningPlan(studentId);
		if (learningPlan == null) {
			throw new RuntimeException(String.format(
					"Could not find the default plan for [%s].", studentId));
		}

		List<PlanItemInfo> planItems = null;
		PlanItemInfo item = null;

		try {
			planItems = getAcademicPlanService().getPlanItemsInPlanByType(
					learningPlan.getId(),
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Could not retrieve plan items.", e);
		}

		for (PlanItemInfo p : planItems) {
			if (p.getRefObjectId().equals(courseId)) {
				item = p;
				break;
			}
		}

		// A null here means that no plan item exists for the given course ID.
		return item;
	}

	/**
	 * Gets a Plan Item of type "planned" or "backup" for a particular course
	 * and ATP ID. Since we are enforcing a data constraint of one "planned" or
	 * "backup" plan item per ATP ID this method only returns a single plan
	 * item.
	 * 
	 * @param courseId
	 * @return A "planned" or "backup" plan item. Or 'null' if none exists.
	 * @throws RuntimeException
	 *             on errors.
	 */
	public PlanItemInfo getPlannedOrBackupPlanItem(String courseId, String atpId) {
		String studentId = getUserId();
		LearningPlan learningPlan = getLearningPlan(studentId);
		if (learningPlan == null) {
			return null;
		}

		PlanItemInfo planItem = null;

		try {
			planItem = getPlanItemByAtpAndType(learningPlan.getId(), courseId,
					atpId, PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED);
		} catch (Exception e) {
			LOG.error("Could not retrieve plan items.", e);
			throw new RuntimeException("Could not retrieve plan items.", e);
		}

		if (planItem == null) {
			try {
				planItem = getPlanItemByAtpAndType(learningPlan.getId(),
						courseId, atpId,
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP);
			} catch (Exception e) {
				LOG.error("Could not retrieve plan items.", e);
				throw new RuntimeException("Could not retrieve plan items.", e);
			}
		}

		// A null here means that no plan item exists for the given course and
		// ATP IDs.
		return planItem;
	}

	/**
	 * Retrieve a student's LearningPlan.
	 * 
	 * @param studentId
	 * @return A LearningPlan or null on errors.
	 * @throws RuntimeException
	 *             if the query fails.
	 */
	private LearningPlan getLearningPlan(String studentId) {
		/*
		 * First fetch the student's learning plan.
		 */
		List<LearningPlanInfo> learningPlans = null;
		try {
			learningPlans = getAcademicPlanService()
					.getLearningPlansForStudentByType(
							studentId,
							PlanConstants.LEARNING_PLAN_TYPE_PLAN,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"Could not fetch plan for user [%s].", studentId), e);
		}

		if (learningPlans == null) {
			throw new RuntimeException(
					String.format(
							"Could not fetch plan for user [%s]. The query returned null.",
							studentId));
		}

		// There should currently only be a single learning plan. This may
		// change in the future.
		if (learningPlans.size() > 1) {
			throw new RuntimeException(String.format(
					"User [%s] has more than one plan.", studentId));
		}

		LearningPlan learningPlan = null;
		if (learningPlans.size() != 0) {
			learningPlan = learningPlans.get(0);
		}

		return learningPlan;
	}

	/**
	 * Create a new learning plan for the given student.
	 * 
	 * @param studentId
	 * @return The plan.
	 */
	private LearningPlan createDefaultLearningPlan(String studentId)
			throws InvalidParameterException, DataValidationErrorException,
			MissingParameterException, AlreadyExistsException,
			PermissionDeniedException, OperationFailedException {

		LearningPlanInfo plan = new LearningPlanInfo();
		plan.setTypeKey(PlanConstants.LEARNING_PLAN_TYPE_PLAN);
		RichTextInfo rti = new RichTextInfo();
		rti.setFormatted("");
		rti.setPlain("");
		plan.setShared(true);
		plan.setDescr(rti);
		plan.setStudentId(studentId);
		plan.setStateKey(PlanConstants.LEARNING_PLAN_ACTIVE_STATE_KEY);
		plan.setMeta(new MetaInfo());

		// Set the user id in the context used in the web service call.
		ContextInfo context = new ContextInfo();
		context.setPrincipalId(getUserId());

		return getAcademicPlanService().createLearningPlan(plan, context);
	}

	/**
	 * Creates events map for a remove.
	 * 
	 * @param planItem
	 * @return
	 */
	private Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> makeRemoveEvent(
			PlanItemInfo planItem, CourseSummaryDetails courseDetails,
			String courseId, PlanForm planForm,
			Map<String, String> removedPlanItemIds) {
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();
		Map<String, String> params = new HashMap<String, String>();

		// Only planned or backup items get an atpId attribute.
		if (planItem.getTypeKey().equals(
				PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)
				|| planItem.getTypeKey().equals(
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
			params.put("atpId",
					formatAtpIdForUI(planItem.getPlanPeriods().get(0)));
		}
		params.put("planItemType", formatTypeKey(planItem.getTypeKey()));
		params.put("planItemId", planItem.getId());
		// Create Javascript events.
		String courseDetailsAsJson;
		String removedPlanItemsJson = null;
		try {
			if (courseDetails == null) {
				courseDetails = getCourseDetailsInquiryService()
						.retrieveCourseSummaryById(courseId);
			}
			// Serialize course details into a string of JSON.
			courseDetailsAsJson = mapper.writeValueAsString(courseDetails);
			if (removedPlanItemIds != null && removedPlanItemIds.size() > 0) {
				removedPlanItemsJson = mapper
						.writeValueAsString(removedPlanItemIds);
			}
		} catch (Exception e) {
			LOG.error("Could not convert javascript events to JSON.", e);
			throw new RuntimeException(
					"Could not convert javascript events to JSON.", e);
		}
		params.put("courseDetails", courseDetailsAsJson);
		if (planItem.getRefObjectType().equalsIgnoreCase(
				PlanConstants.COURSE_TYPE)) {
			events.put(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_DELETED, params);
		} else {
			params.put("SectionCode", planForm.getSectionCode());
			params.put("InstituteCode", planForm.getInstituteCode());
			params.put(
					"shortTermName",
					KsapFrameworkServiceLocator.getTermHelper()
							.getYearTerm(planItem.getPlanPeriods().get(0))
							.getTermName());
			params.put("PlanItemsDeleted", removedPlanItemsJson);
			events.put(PlanConstants.JS_EVENT_NAME.SECTION_ITEM_DELETED, params);
		}
		return events;
	}

	/**
	 * Creates an update credits event.
	 * 
	 * @param atpId
	 *            The id of the term.
	 * @return
	 */
	private Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> makeUpdateTotalCreditsEvent(
			String atpId, PlanConstants.JS_EVENT_NAME eventName) {
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();

		Map<String, String> params = new HashMap<String, String>();

		params.put("atpId", formatAtpIdForUI(atpId));
		String totalCredits = this.getTotalCredits(atpId);
		params.put("totalCredits", totalCredits);

		events.put(eventName, params);
		return events;
	}

	/**
	 * Creates an add plan item event.
	 * 
	 * @param planItem
	 * @param courseDetails
	 * @return
	 * @throws RuntimeException
	 *             if anything goes wrong.
	 */
	private Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> makeAddEvent(
			PlanItemInfo planItem, CourseSummaryDetails courseDetails,
			PlanForm planForm) {
		Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> events = new LinkedHashMap<PlanConstants.JS_EVENT_NAME, Map<String, String>>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("planItemId", planItem.getId());
		params.put("planItemType", formatTypeKey(planItem.getTypeKey()));
		// Only planned or backup items get an atpId attribute.
		if (planItem.getTypeKey().equals(
				PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)
				|| planItem.getTypeKey().equals(
						PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
			params.put("atpId",
					formatAtpIdForUI(planItem.getPlanPeriods().get(0)));
			// event for aler Icon
			TermHelper th = KsapFrameworkServiceLocator.getTermHelper();
			String pp = planItem.getPlanPeriods().get(0);
			boolean timeScheduleOpen = false;
			for (Term t : th.getPublishedTerms())
				timeScheduleOpen = timeScheduleOpen || t.getId().equals(pp);
			Course c;
			try {
				c = KsapFrameworkServiceLocator.getCourseService().getCourse(
						courseDetails.getCourseId(),
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
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
			params.put("showAlert", String.valueOf(!KsapFrameworkServiceLocator
					.getTermHelper().isCourseOffered(th.getTerm(pp), c)));
			params.put("termName", KsapFrameworkServiceLocator.getTermHelper()
					.getYearTerm(planItem.getPlanPeriods().get(0))
					.getTermName());
			params.put("timeScheduleOpen", String.valueOf(timeScheduleOpen));
			if (planItem.getRefObjectType().equalsIgnoreCase(
					PlanConstants.SECTION_TYPE)) {
				params.put("SectionCode", planForm.getSectionCode());
				params.put("PrimarySectionCode",
						planForm.getPrimarySectionCode());
				params.put("InstituteCode", planForm.getInstituteCode());
				params.put("Primary", String.valueOf(planForm.isPrimary()));
				params.put("PrimaryPlanItemId", planForm.getPrimaryPlanItemId());
			}
		}

		// Create Javascript events.
		String courseDetailsAsJson;
		try {
			// Serialize course details into a string of JSON.
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
					true);
			courseDetailsAsJson = mapper.writeValueAsString(courseDetails);
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not convert javascript events to JSON.", e);
		}
		params.put("courseDetails", courseDetailsAsJson);
		if (planItem.getRefObjectType().equalsIgnoreCase(
				PlanConstants.COURSE_TYPE)) {
			events.put(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_ADDED, params);
		} else if (planItem.getRefObjectType().equalsIgnoreCase(
				PlanConstants.SECTION_TYPE)) {
			events.put(PlanConstants.JS_EVENT_NAME.SECTION_ITEM_ADDED, params);
		}
		return events;
	}

	private String getUserId() {
		Person user = GlobalVariables.getUserSession().getPerson();
		return user.getPrincipalId();
	}

	private String formatAtpIdForUI(String atpId) {
		return atpId.replaceAll("\\.", "-");
	}

	private String formatTypeKey(String typeKey) {
		return typeKey.substring(typeKey.lastIndexOf(".") + 1);
	}

	private String getTotalCredits(String termId) {
		double plannedTotalMin = 0;
		double plannedTotalMax = 0;
		String totalCredits = null;
		Person user = GlobalVariables.getUserSession().getPerson();
		String studentID = user.getPrincipalId();

		String planTypeKey = PlanConstants.LEARNING_PLAN_TYPE_PLAN;
		List<LearningPlanInfo> learningPlanList = null;
		List<StudentCourseRecordInfo> studentCourseRecordInfos = getAcadRecs(studentID);

		List<PlanItemInfo> planItemList = null;
		try {
			learningPlanList = getAcademicPlanService()
					.getLearningPlansForStudentByType(
							studentID,
							planTypeKey,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			for (LearningPlanInfo learningPlan : learningPlanList) {
				String learningPlanID = learningPlan.getId();

				planItemList = getAcademicPlanService()
						.getPlanItemsInPlanByType(
								learningPlanID,
								PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());

				for (PlanItemInfo planItem : planItemList) {
					String courseID = planItem.getRefObjectId();
					if (planItem.getRefObjectType().equalsIgnoreCase(
							PlanConstants.COURSE_TYPE)) {
						if (getCourseDetailsInquiryService().isCourseIdValid(
								courseID)) {
							for (String atp : planItem.getPlanPeriods()) {
								if (atp.equalsIgnoreCase(termId)) {
									CourseSummaryDetails courseDetails = getCourseDetailsInquiryService()
											.retrieveCourseSummaryById(courseID);
									if (courseDetails != null
											&& !courseDetails.getCredit()
													.contains(".")) {
										String[] str = courseDetails
												.getCredit().split("\\D");
										double min = Double.parseDouble(str[0]);
										plannedTotalMin += min;
										double max = Double
												.parseDouble(str[str.length - 1]);
										plannedTotalMax += max;

									} else if (courseDetails != null
											&& courseDetails.getCredit()
													.contains(".")) {
										if (courseDetails.getCredit().contains(
												PlanConstants.MULTIPLE)) {
											String[] str = courseDetails
													.getCredit()
													.split(PlanConstants.MULTIPLE);
											plannedTotalMin += Double
													.parseDouble(str[0]);
											plannedTotalMax += Double
													.parseDouble(str[1]);
										} else if (courseDetails.getCredit()
												.contains(PlanConstants.RANGE)) {
											String[] str = courseDetails
													.getCredit()
													.split(PlanConstants.RANGE);
											plannedTotalMin += Double
													.parseDouble(str[0]);
											plannedTotalMax += Double
													.parseDouble(str[1]);
										} else {
											plannedTotalMin += Double
													.parseDouble(courseDetails
															.getCredit());
											plannedTotalMax += Double
													.parseDouble(courseDetails
															.getCredit());
										}
									}
								}
								totalCredits = Double.toString(plannedTotalMin);
								if (plannedTotalMin != plannedTotalMax) {
									totalCredits = totalCredits + "-"
											+ Double.toString(plannedTotalMax);
								}
							}
						}
					}
				}

				double academicTotalMin = 0;
				double academicTotalMax = 0;
				if (studentCourseRecordInfos.size() > 0) {
					for (StudentCourseRecordInfo ar : studentCourseRecordInfos) {
						if (ar.getTermName().equalsIgnoreCase(termId)) {
							if (ar.getCreditsEarned() != null
									|| !ar.getCreditsEarned().isEmpty()
									&& !ar.getCreditsEarned().contains(".")) {
								String[] str = ar.getCreditsEarned().split(
										"\\D");
								double min = Double.parseDouble(str[0]);
								academicTotalMin += min;
								double max = Double
										.parseDouble(str[str.length - 1]);
								academicTotalMax += max;
							} else if (ar.getCreditsEarned() != null
									|| !ar.getCreditsEarned().isEmpty()
									&& ar.getCreditsEarned().contains(".")) {
								academicTotalMin += Double.parseDouble(ar
										.getCreditsEarned());
								academicTotalMax += Double.parseDouble(ar
										.getCreditsEarned());
							}
						}
					}
					totalCredits = Double.toString(academicTotalMin);

					if (academicTotalMin != academicTotalMax) {
						totalCredits = totalCredits + "-"
								+ Double.toString(academicTotalMax);
					}
				}

				if (planItemList.size() > 0
						&& studentCourseRecordInfos.size() > 0) {
					if (plannedTotalMin != plannedTotalMax
							&& academicTotalMin != academicTotalMax) {
						double minVal = 0;
						double maxVal = 0;
						minVal = plannedTotalMin + academicTotalMin;
						maxVal = plannedTotalMax + academicTotalMax;
						totalCredits = minVal + "-" + maxVal;
					}
					if (plannedTotalMin == plannedTotalMax
							&& academicTotalMin == academicTotalMax) {
						totalCredits = String.valueOf(plannedTotalMin
								+ academicTotalMin);
					}
					if (plannedTotalMin != plannedTotalMax
							&& academicTotalMin == academicTotalMax) {
						double minVal = 0;
						double maxVal = 0;
						minVal = plannedTotalMin + academicTotalMin;
						maxVal = plannedTotalMax + academicTotalMax;
						totalCredits = minVal + "-" + maxVal;

					}
					if (plannedTotalMin == plannedTotalMax
							&& academicTotalMin != academicTotalMax) {
						double minVal = 0;
						double maxVal = 0;
						minVal = academicTotalMin;
						maxVal = plannedTotalMax + academicTotalMax;
						totalCredits = minVal + "-" + maxVal;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("could not load total credits");
		}

		if (totalCredits != null) {
			if (totalCredits.contains(".0"))
				totalCredits = totalCredits.replace(".0", "");
		} else {
			totalCredits = "0";
		}
		return totalCredits;
	}

	private String getPlanIdFromCourseId(String courseId, String planItemType) {
		String planItemId = null;
		Person user = GlobalVariables.getUserSession().getPerson();
		String studentID = user.getPrincipalId();

		String planTypeKey = PlanConstants.LEARNING_PLAN_TYPE_PLAN;
		List<LearningPlanInfo> learningPlanList = null;
		List<StudentCourseRecordInfo> studentCourseRecordInfos = getAcadRecs(studentID);

		List<PlanItemInfo> planItemList = null;
		try {
			learningPlanList = getAcademicPlanService()
					.getLearningPlansForStudentByType(
							studentID,
							planTypeKey,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
			for (LearningPlanInfo learningPlan : learningPlanList) {
				String learningPlanID = learningPlan.getId();

				planItemList = getAcademicPlanService()
						.getPlanItemsInPlanByType(
								learningPlanID,
								planItemType,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());

				for (PlanItemInfo planItem : planItemList) {
					if (planItem.getRefObjectId().equalsIgnoreCase(courseId)) {
						planItemId = planItem.getId();
						break;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("could not get the planItemId");
		}
		return planItemId;
	}

	private List<StudentCourseRecordInfo> getAcadRecs(String studentID) {
		List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
		try {
			studentCourseRecordInfos = KsapFrameworkServiceLocator
					.getAcademicRecordService().getCompletedCourseRecords(
							studentID,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());

		} catch (Exception e) {
			LOG.error("Query to fetch Academic records failed with SWS");
			return studentCourseRecordInfos;
		}
		return studentCourseRecordInfos;
	}

	private boolean isNewUser() {
		boolean isNewUser = false;
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();
		List<LearningPlanInfo> learningPlanList = new ArrayList<LearningPlanInfo>();
		List<AuditReportInfo> auditReportInfoList = new ArrayList<AuditReportInfo>();
		try {
			learningPlanList = getAcademicPlanService()
					.getLearningPlansForStudentByType(
							studentId,
							PlanConstants.LEARNING_PLAN_TYPE_PLAN,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException("Could not retrieve plan items.", e);
		}
		/* check if any audits are ran ! if no plans found */
		// if (learningPlanList.size() == 0) {
		// String systemKey =
		// KsapFrameworkServiceLocator.getUserSessionHelper().getAuditSystemKey();
		//
		//
		// Date startDate = new Date();
		// Date endDate = new Date();
		// ContextInfo contextInfo = new ContextInfo();
		// try {
		// auditReportInfoList = getDegreeAuditService()

		// .getAuditsForStudentInDateRange(systemKey, startDate,
		// endDate, contextInfo);
		// } catch (Exception e) {
		// throw new RuntimeException(
		// "Could not retrieve degreeaudit details", e);
		// }
		// if (auditReportInfoList.size() == 0) {
		// isNewUser = true;
		// }
		// }
		return isNewUser;
	}

	public DegreeAuditService getDegreeAuditService() {
		if (degreeAuditService == null) {
			degreeAuditService = (DegreeAuditService) GlobalResourceLoader
					.getService(new QName(
							DegreeAuditServiceConstants.NAMESPACE,
							DegreeAuditServiceConstants.SERVICE_NAME));
		}
		return degreeAuditService;
	}

	public AcademicPlanService getAcademicPlanService() {
		if (academicPlanService == null) {
			academicPlanService = (AcademicPlanService) GlobalResourceLoader
					.getService(new QName(PlanConstants.NAMESPACE,
							PlanConstants.SERVICE_NAME));
		}
		return academicPlanService;
	}

	public void setAcademicPlanService(AcademicPlanService academicPlanService) {
		this.academicPlanService = academicPlanService;
	}

	public synchronized CourseDetailsInquiryHelperImpl getCourseDetailsInquiryService() {
		if (this.courseDetailsInquiryService == null) {
			this.courseDetailsInquiryService = new CourseDetailsInquiryHelperImpl();
		}
		return courseDetailsInquiryService;
	}

	public void setCourseDetailsInquiryService(
			CourseDetailsInquiryHelperImpl courseDetailsInquiryService) {
		this.courseDetailsInquiryService = courseDetailsInquiryService;
	}
}