package org.kuali.student.ap.planner.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.uif.view.ViewAuthorizerBase;
import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.util.KsapStringUtil;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.support.PlanItemControllerHelper;
import org.kuali.student.ap.planner.form.PlannerFormImpl;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.lum.course.infc.Course;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Fix under KSAP-265
 * UifControllerBase replaced by KsapControllerBase
 */
@Controller
@RequestMapping(value = "/planner/**")
public class PlannerController extends KsapControllerBase {

	private static final Logger LOG = Logger.getLogger(PlannerController.class);

	private static final String PLANNER_FORM = "Planner-FormView";
	private static final String PLANNER_LOAD_FORM = "PlannerLoad-FormView";
	private static final String DIALOG_FORM = "PlannerDialog-FormView";

	private static final String ADD_COURSE_PAGE = "planner_add_course_page";
	private static final String EDIT_TERM_NOTE_PAGE = "planner_edit_term_note_page";
	private static final String COURSE_SUMMARY_PAGE = "planner_course_summary_page";
	private static final String COPY_COURSE_PAGE = "planner_copy_course_page";
	private static final String EDIT_PLAN_ITEM_PAGE = "planner_edit_plan_item_page";
	private static final String COPY_PLAN_ITEM_PAGE = "planner_copy_plan_item_page";
	private static final String MOVE_PLAN_ITEM_PAGE = "planner_move_plan_item_page";
	private static final String DELETE_PLAN_ITEM_PAGE = "planner_delete_plan_item_page";

	private void finishAddCourse(LearningPlan plan, PlannerForm form, Course course, String termId,
			HttpServletResponse response) throws IOException, ServletException {
		String newType = form.isBackup() ? PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP
				: PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED;
		Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
        JsonObjectBuilder eventList = Json.createObjectBuilder();
		PlanItem wishlistPlanItem = null;
		List<PlanItem> existingPlanItems = form.getExistingPlanItems();
		if (existingPlanItems != null)
			for (PlanItem existingPlanItem : existingPlanItems) {
				if (PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST.equals(existingPlanItem.getTypeKey())) {
					wishlistPlanItem = existingPlanItem;
					continue;
				}

				List<String> planPeriods = existingPlanItem.getPlanPeriods();
				if (planPeriods != null && planPeriods.contains(termId)) {
					PlanEventUtils.sendJsonEvents(false, "Course " + course.getCode() + " is already planned for "
							+ form.getTerm().getName(), response, eventList);
					return;
				}
			}

		boolean create = wishlistPlanItem == null;
		PlanItemInfo planItemInfo;
		if (create) {
			planItemInfo = new PlanItemInfo();
			planItemInfo.setTypeKey(newType);
			planItemInfo.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
			planItemInfo.setLearningPlanId(plan.getId());
		} else {
			assert plan.getId().equals(wishlistPlanItem.getLearningPlanId()) : plan.getId() + " "
					+ wishlistPlanItem.getLearningPlanId();
            eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), wishlistPlanItem, eventList);
			planItemInfo = new PlanItemInfo(wishlistPlanItem);
		}

		planItemInfo.setRefObjectId(course.getId());
		planItemInfo.setRefObjectType(PlanConstants.COURSE_TYPE);
		List<String> planPeriods = new ArrayList<String>(1);
		planPeriods.add(termId);
		planItemInfo.setPlanPeriods(planPeriods);

		if (StringUtils.hasText(form.getCourseNote())) {
			RichTextInfo descr = new RichTextInfo();
			descr.setPlain(form.getCourseNote());
			descr.setFormatted(form.getCourseNote());
			planItemInfo.setDescr(descr);
		} else
			planItemInfo.setDescr(null);
		planItemInfo.setCredit(form.getCreditsForPlanItem(course));

		try {
			if (create) {
				planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(planItemInfo,
						KsapFrameworkServiceLocator.getContext().getContextInfo());
			} else {
				planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(
						planItemInfo.getId(), planItemInfo, KsapFrameworkServiceLocator.getContext().getContextInfo());
			}
		} catch (AlreadyExistsException e) {
			LOG.warn("Course " + course.getCode() + " is already planned for " + term.getName(), e);
			PlanEventUtils.sendJsonEvents(false,
					"Course " + course.getCode() + " is already planned for " + term.getName(), response, eventList);
			return;
		} catch (DataValidationErrorException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP service failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("LP service failure", e);
		}

        eventList = PlanEventUtils.makeAddEvent(planItemInfo, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, termId, eventList);

		PlanEventUtils.sendJsonEvents(true, "Course " + course.getCode() + " added to plan for " + term.getName(),
				response, eventList);
	}

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new PlannerFormImpl();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView startPlanner(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response) == null)
			return null;

		UifFormBase uifForm = (UifFormBase) form;
		super.start(uifForm, result, request, response);

		uifForm.setViewId(PLANNER_FORM);
		uifForm.setView(super.getViewService().getViewById(PLANNER_FORM));

		return getUIFModelAndView(uifForm);
	}

	@RequestMapping(params = "methodToCall=load")
	public ModelAndView loadPlanner(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response) == null)
			return null;
        PlannerFormImpl newForm = (PlannerFormImpl) form;
        newForm.setLoadCalendar(true);
		// Force loading of terms prior to rendering.
        newForm.getTerms();

		UifFormBase uifForm = (UifFormBase) newForm;
        uifForm.getView().setAuthorizer(new ViewAuthorizerBase());
		super.start(uifForm, result, request, response);

		uifForm.setViewId(PLANNER_FORM);
		uifForm.setView(super.getViewService().getViewById(PLANNER_FORM));



		return getUIFModelAndView(uifForm);
	}

	@RequestMapping(params = "methodToCall=startDialog")
	public ModelAndView startDialog(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

		UifFormBase uifForm = (UifFormBase) form;
		super.start(uifForm, result, request, response);

		String pageId = uifForm.getPageId();
		boolean quickAdd = ADD_COURSE_PAGE.equals(pageId) || EDIT_TERM_NOTE_PAGE.equals(pageId);
		if (quickAdd) {
			String termId = form.getTermId();
			Term term = form.getTerm();
			if (term == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid term ID " + termId);
				return null;
			}
		}

		boolean courseRequired = COURSE_SUMMARY_PAGE.equals(pageId) || COPY_COURSE_PAGE.equals(pageId);

		boolean hasPlanItem = form.getPlanItemId() != null;
		if (hasPlanItem) {
			PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
			if (planItem == null)
				return null;

			form.populateFromPlanItem();

		} else if (!quickAdd && !courseRequired) {
			LOG.warn("Missing plan item for loading page " + pageId);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing plan item for loading page " + pageId);
			return null;
		}

		Course course = form.getCourse();
		if (course == null && courseRequired) {
			LOG.warn("Missing course for summary " + pageId);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course for summary " + pageId);
			return null;
		}

		uifForm.setViewId(DIALOG_FORM);
		uifForm.setView(super.getViewService().getViewById(DIALOG_FORM));

		return getUIFModelAndView(uifForm);
	}

	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + EDIT_TERM_NOTE_PAGE)
	public ModelAndView editTermNote(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

		String uniqueId = form.getUniqueId();
		if (uniqueId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unique ID note posted");
			return null;
		}

		String termId = form.getTermId();
		// SCT-6247 - allow planning for prior terms for IU Roadmap
		// If this restriction needs to be restored for KSAP, than it should be done by configuration
		// and not as a forced default.
		//		if (!form.isPlanning()) {
		//			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Term " + termId + " is not open for planning");
		//			return null;
		//		}

		String termNote = form.getTermNote();
		if (termNote != null)
			termNote = KsapStringUtil.replaceSmartCharacters(termNote);

		CommentService commentService = KsapFrameworkServiceLocator.getCommentService();
		List<CommentInfo> commentInfos;
		try {
			commentInfos = commentService.getCommentsByReferenceAndType(plan.getId(),
					PlanConstants.TERM_NOTE_COMMENT_TYPE, KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
		}

		boolean found = false;
		RichTextInfo newNote = new RichTextInfo();
		newNote.setFormatted(termNote);
		newNote.setPlain(termNote);
		for (CommentInfo comment : commentInfos) {
			String commentAtpId = comment.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
			if (termId.equals(commentAtpId)) {
				found = true;
				comment.setCommentText(newNote);
				try {
					commentService.updateComment(comment.getId(), comment, KsapFrameworkServiceLocator.getContext()
							.getContextInfo());
				} catch (DataValidationErrorException e) {
					throw new IllegalArgumentException("Comment lookup failure", e);
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("Comment lookup failure", e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("Comment lookup failure", e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("Comment lookup failure", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("Comment lookup failure", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("Comment lookup failure", e);
				} catch (ReadOnlyException e) {
					throw new IllegalStateException("Comment lookup failure", e);
				} catch (VersionMismatchException e) {
					throw new IllegalStateException("Comment lookup failure", e);
				}
				break;
			}
		}
		if (!found) {
			CommentInfo newComment = new CommentInfo();
			newComment.setCommentText(newNote);
			newComment.setEffectiveDate(new Date());
			newComment.setReferenceId(plan.getId());
			newComment.setReferenceTypeKey(PlanConstants.TERM_NOTE_COMMENT_TYPE);
			newComment.setTypeKey(PlanConstants.TERM_NOTE_COMMENT_TYPE);
			newComment.setStateKey("ACTIVE");
			AttributeInfo atpIdAttr = new AttributeInfo();
			atpIdAttr.setKey(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
			atpIdAttr.setValue(termId);
			newComment.getAttributes().add(atpIdAttr);
			try {
				commentService.createComment(newComment.getReferenceId(), newComment.getReferenceTypeKey(),
						PlanConstants.TERM_NOTE_COMMENT_TYPE, newComment, KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
			} catch (DataValidationErrorException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Comment lookup failure", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Comment lookup failure", e);
			} catch (ReadOnlyException e) {
				throw new IllegalStateException("Comment lookup failure", e);
			}
		}
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.updateTermNoteEvent(uniqueId, termNote, eventList);
		PlanEventUtils.sendJsonEvents(true, null, response, eventList);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + ADD_COURSE_PAGE)
	public ModelAndView addPlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
        JsonObjectBuilder eventList = Json.createObjectBuilder();
		if (plan == null)
			return null;

		String termId = form.getTermId();
		// SCT-6247 - allow planning for prior terms for IU Roadmap
		// If this restriction needs to be restored for KSAP, than it should be done by configuration
		// and not as a forced default.
		//		if (!form.isPlanning()) {
		//			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Term " + termId + " is not open for planning");
		//			return null;
		//		}

		String courseCd = form.getCourseCd();
		if (!StringUtils.hasText(courseCd)) {
			PlanEventUtils.sendJsonEvents(false, "Course code required", response, eventList);
			return null;
		}

		Course course;
		try {
			List<Course> courses = KsapFrameworkServiceLocator.getCourseHelper().getCoursesByCode(courseCd);
			if (courses == null || courses.isEmpty()) {
				PlanEventUtils.sendJsonEvents(false, "Course " + courseCd + " not found", response, eventList);
				return null;
			}
			course = courses.get(0);
		} catch (IllegalArgumentException e) {
			LOG.error("Invalid course code " + courseCd, e);
			PlanEventUtils.sendJsonEvents(false, "Course " + courseCd + " not found", response, eventList);
			return null;
		}

		finishAddCourse(plan, form, course, termId, response);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + COPY_COURSE_PAGE)
	public ModelAndView copyCourse(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

		String termId = form.getTargetTermId();
		// SCT-6247 - allow planning for prior terms for IU Roadmap
		// If this restriction needs to be restored for KSAP, than it should be done by configuration
		// and not as a forced default.
		//		if (!KsapFrameworkServiceLocator.getTermHelper().isPlanning(termId)) {
		//			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Term " + termId + " is not open for planning");
		//			return null;
		//		}

		Course course = form.getCourse();
		if (course == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course " + form.getCourseId() + " not found");
			return null;
		}

		finishAddCourse(plan, form, course, termId, response);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + EDIT_PLAN_ITEM_PAGE)
	public ModelAndView editPlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String expectedTermId = form.getTermId();
		boolean creditEdited = false;
		boolean notesEdited = false;
		boolean newNoteFlag = false;
		
		if (expectedTermId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing term ID");
			return null;
		}

		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

		
		
		PlanItemInfo planItemInfo = new PlanItemInfo(planItem);
		RichTextInfo previousDescr = planItemInfo.getDescr();
		if (StringUtils.hasText(form.getCourseNote())) {
			RichTextInfo descr = new RichTextInfo();
			descr.setPlain(form.getCourseNote());
			descr.setFormatted(form.getCourseNote());
			planItemInfo.setDescr(descr);
			String oldFormatted = previousDescr.getFormatted();
			String newFormatted = descr.getFormatted();
			if(!newFormatted.equals(oldFormatted)){
				if(previousDescr.getFormatted() == null){
					newNoteFlag = true;
				}
				notesEdited = true;
			}
		} else
			planItemInfo.setDescr(null);
		
		BigDecimal oldCredit = planItemInfo.getCredit();
		
		LOG.debug("In PlannerController: oldCredit is " + oldCredit);
		LOG.debug("form.getCreditsForPlanItem() is " + form.getCreditsForPlanItem());
		
		planItemInfo.setCredit(form.getCreditsForPlanItem());
		BigDecimal newCredit = planItemInfo.getCredit();
		
		LOG.debug("In PlannerController: newCredit is " + newCredit);
		
		if (oldCredit == null) {
			if (newCredit != null)
				creditEdited = true;
		} else {
			if (newCredit == null || oldCredit.compareTo(newCredit) != 0) {
				creditEdited = true;
			}
		}

		try {
			planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(planItemInfo.getId(),
					planItemInfo, KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (AlreadyExistsException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DataValidationErrorException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP service failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("LP service failure", e);
		}
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.updatePlanItemCreditsEvent(form.getUniqueId(), planItemInfo, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, planItemInfo.getPlanPeriods().get(0), eventList);
		if(notesEdited && creditEdited){
			PlanEventUtils.sendJsonEvents(true, "Changes to the notes and credits for " + form.getTerm().getName() +" "+ form.getCourse().getCode() +" is saved", response, eventList);
		}else if (notesEdited){
			if(newNoteFlag){
				PlanEventUtils.sendJsonEvents(true, "Note added to " + form.getTerm().getName() +" "+ form.getCourse().getCode(), response, eventList);
			}else{
				PlanEventUtils.sendJsonEvents(true, "Note edited for " + form.getTerm().getName() +" "+ form.getCourse().getCode() , response, eventList);
			}
		}else if(creditEdited){
			PlanEventUtils.sendJsonEvents(true, "Changes to the credits for " + form.getTerm().getName() +" "+ form.getCourse().getCode() +" is saved", response, eventList);
		}else{
			PlanEventUtils.sendJsonEvents(true, null, response, eventList);
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + COPY_PLAN_ITEM_PAGE)
	public ModelAndView copyPlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

		String termId = form.getTargetTermId();
		// SCT-6247 allow planning for prior terms
		//		if (!KsapFrameworkServiceLocator.getTermHelper().isPlanning(termId)) {
		//			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Term " + termId + " is not open for planning");
		//			return null;
		//		}

		Course course = form.getCourse();
		if (course == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course " + form.getCourseId() + " not found");
			return null;
		}
		assert course.getId().equals(planItem.getRefObjectId());

		form.populateFromPlanItem();

		finishAddCourse(plan, form, course, termId, response);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + MOVE_PLAN_ITEM_PAGE)
	public ModelAndView movePlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String expectedTermId = form.getTermId();
		if (expectedTermId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing term ID");
			return null;
		}

		String termId = form.getTargetTermId();
		// SCT-6247 - allow planning for prior terms
		// If this is needed in KSAP, then a
		//		if (!KsapFrameworkServiceLocator.getTermHelper().isPlanning(termId)) {
		//			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Term " + termId + " is not open for planning");
		//			return null;
		//		}
		Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);

		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

		PlanItemInfo planItemInfo = new PlanItemInfo(planItem);
		List<String> planPeriods = new ArrayList<String>(1);
		planPeriods.add(termId);
		planItemInfo.setPlanPeriods(planPeriods);

		try {
			planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(planItemInfo.getId(),
					planItemInfo, KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (AlreadyExistsException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DataValidationErrorException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP service failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("LP service failure", e);
		}
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), planItem, eventList);
        eventList = PlanEventUtils.makeAddEvent(planItemInfo, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(false, expectedTermId, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, termId, eventList);
        PlanEventUtils.sendJsonEvents(true, "Course " + form.getCourse().getCode() + " moved to " + term.getName(),
				response, eventList);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + DELETE_PLAN_ITEM_PAGE)
	public ModelAndView deletePlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String expectedTermId = form.getTermId();
		if (expectedTermId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing term ID");
			return null;
		}
		Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(expectedTermId);

		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

		try {
			KsapFrameworkServiceLocator.getAcademicPlanService().deletePlanItem(planItem.getId(),
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP service failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("LP service failure", e);
		}
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), planItem, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, term.getId(), eventList);
		PlanEventUtils.sendJsonEvents(true, "Course " + form.getCourse().getCode() + " removed from " + term.getName(),
				response, eventList);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "methodToCall=updatePlanItemType")
	public ModelAndView updatePlanItemType(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

		PlanItemInfo planItemInfo = new PlanItemInfo(planItem);
		planItemInfo.setTypeKey(form.isBackup() ? PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP
				: PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED);

		try {
			planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(planItemInfo.getId(),
					planItemInfo, KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (AlreadyExistsException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DataValidationErrorException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP service failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP service failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("LP service failure", e);
		}
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), planItem, eventList);
        eventList = PlanEventUtils.makeAddEvent(planItemInfo, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, form.getTermId(), eventList);
		PlanEventUtils.sendJsonEvents(true, "Course " + form.getCourse().getCode() + " updated", response, eventList);
		return null;
	}

}
