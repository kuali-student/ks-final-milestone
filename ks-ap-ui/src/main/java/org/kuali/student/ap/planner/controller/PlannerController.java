package org.kuali.student.ap.planner.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.uif.view.ViewAuthorizerBase;
import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.util.KsapStringUtil;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.form.PlannerFormImpl;
import org.kuali.student.ap.planner.support.PlanItemControllerHelper;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.kuali.student.common.collection.KSCollectionUtils;
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
import org.kuali.student.r2.core.acal.infc.Term;
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
 *
 * Controller for the Planner screen and its associated dialogs.
 */
@Controller
@RequestMapping(value = "/planner/**")
public class PlannerController extends KsapControllerBase {

	private static final Logger LOG = Logger.getLogger(PlannerController.class);

	private static final String PLANNER_FORM = "Planner-FormView";
	private static final String DIALOG_FORM = "PlannerDialog-FormView";

	private static final String QUICKADD_COURSE_PAGE = "planner_add_course_page";
	private static final String EDIT_TERM_NOTE_PAGE = "planner_edit_term_note_page";
	private static final String COURSE_SUMMARY_PAGE = "planner_course_summary_page";
	private static final String COPY_COURSE_PAGE = "planner_copy_course_page";
	private static final String EDIT_PLAN_ITEM_PAGE = "planner_edit_plan_item_page";
	private static final String COPY_PLAN_ITEM_PAGE = "planner_copy_plan_item_page";
	private static final String MOVE_PLAN_ITEM_PAGE = "planner_move_plan_item_page";
	private static final String DELETE_PLAN_ITEM_PAGE = "planner_delete_plan_item_page";
    private static final String ADD_BOOKMARK_PAGE = "bookmark_add_course_page";
    private static final String DELETE_BOOKMARK_PAGE = "bookmark_delete_course_page";
    private static final String ADD_COURSE_PAGE = "course_add_course_page";

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new PlannerFormImpl();
	}

    /**
     * Entry point for the screen that handles setting up the form for the first display of the screen.
     *
     * Does not appear to be hit at any time.
     */
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

    /**
     * Loads the Term information for the planner.
     * Planner is loaded in two stages the first is the loading of the whole page with the calendar information blank.
     * The second stage refreshes the calendar with js retreiveComponent(componentId, methodToCall) which hits here
     * to load the calendar term data.
     */
	@RequestMapping(params = "methodToCall=load")
	public ModelAndView loadPlanner(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response) == null)
			return null;
        // Set form to load terms
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

    /**
     * Loads the initial information for any dialog screen opened in the planner.
     */
	@RequestMapping(params = "methodToCall=startDialog")
	public ModelAndView startDialog(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

		UifFormBase uifForm = (UifFormBase) form;
		super.start(uifForm, result, request, response);

		String pageId = uifForm.getPageId();

        // If screen is add course or edit term note valid term information is needed
		boolean termRequired = QUICKADD_COURSE_PAGE.equals(pageId) || EDIT_TERM_NOTE_PAGE.equals(pageId);
		if (termRequired) {
			String termId = form.getTermId();
			Term term = form.getTerm();
			if (term == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid term ID " + termId);
				return null;
			}
		}

        // If screen is course summary or copy course valid course information is needed
		boolean courseRequired = COURSE_SUMMARY_PAGE.equals(pageId) || COPY_COURSE_PAGE.equals(pageId) || ADD_COURSE_PAGE.equals(pageId);

        // Retrieve plan item information if an id is returned
		boolean hasPlanItem = form.getPlanItemId() != null;
		if (hasPlanItem) {
			PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
			if (planItem == null)
				return null;

			form.populateFromPlanItem();

		} else if (!termRequired && !courseRequired) {
            // If term or course information are not required then a plan item should be found.
			LOG.warn("Missing plan item for loading page " + pageId);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing plan item for loading page " + pageId);
			return null;
		}

        // Retrieve course information if possible
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

    /**
     * Handles submissions from the term note dialog.
     * Formats and updates the term's note in the database.
     */
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

        // Format the text submitted by the user.
		String termId = form.getTermId();
		String termNote = form.getTermNote();
		if (termNote != null)
			termNote = KsapStringUtil.replaceSmartCharacters(termNote);

        // Retrieve the list of term notes for this plan.
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


        // Create replacement rich text with new term note
		RichTextInfo newNote = new RichTextInfo();
		newNote.setFormatted(termNote);
		newNote.setPlain(termNote);

        // Search for existing term note for that term.
        boolean found = false;
		for (CommentInfo comment : commentInfos) {
			String commentAtpId = comment.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
			if (termId.equals(commentAtpId)) {
				found = true;
				comment.setCommentText(newNote);
				try {
                    if(StringUtils.isEmpty(termNote)){
                        commentService.deleteComment(comment.getId(),KsapFrameworkServiceLocator.getContext().getContextInfo());
                    }else{
                        // If existing note is found replace the rich text and update it in the database.
                        commentService.updateComment(comment.getId(), comment, KsapFrameworkServiceLocator.getContext()
                                .getContextInfo());
                    }
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

        // If no existing note is found create new term note and save it to the database
		if (!found && !StringUtils.isEmpty(termNote)) {
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

        // Create Json strings for displaying action's response and updating the planner screen.
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.updateTermNoteEvent(uniqueId, termNote, eventList);
		PlanEventUtils.sendJsonEvents(true, null, response, eventList);
		return null;
	}

    /**
     * Handles submissions from the quick add course dialog.
     * Validates the course and addes it to the students plan.
     */
	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + QUICKADD_COURSE_PAGE)
	public ModelAndView addPlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Retrieve student's plan
		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
        JsonObjectBuilder eventList = Json.createObjectBuilder();
		if (plan == null)
			return null;

		String termId = form.getTermId();

		String courseCd = form.getCourseCd();
		if (!StringUtils.hasText(courseCd)) {
			PlanEventUtils.sendJsonEvents(false, "Course code required", response, eventList);
			return null;
		}

        // Retrieve course information using the course code entered by the user
		Course course=null;
		try {
			List<Course> courses = KsapFrameworkServiceLocator.getCourseHelper().getCoursesByCode(courseCd);
			if (courses == null || courses.isEmpty()) {
				PlanEventUtils.sendJsonEvents(false, "Course " + courseCd + " not found", response, eventList);
				return null;
			}
            for(Course courseTemp : courses){
                if(courseTemp.getStateKey().equals("Active")){
                    course=courseTemp;
                    break;
                }
            }
            if (course == null) {
                PlanEventUtils.sendJsonEvents(false, "Course " + courseCd + " not active", response, eventList);
                return null;
            }
		} catch (IllegalArgumentException e) {
			LOG.error("Invalid course code " + courseCd, e);
			PlanEventUtils.sendJsonEvents(false, "Course " + courseCd + " not found", response, eventList);
			return null;
		}

        // Add the course to the plan
		finishAddCourse(plan, form, course, termId, response);
		return null;
	}

    /**
     * Handles the submissions from the copy course dialog (when copying from a completed plan item).
     * Copies the course for a plan item into a new item under a new term.
     */
	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + COPY_COURSE_PAGE)
	public ModelAndView copyCourse(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Retrieve the student's plan
		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

		String termId = form.getTargetTermId();

        // Retrieve Coure information
		Course course = form.getCourse();
		if (course == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course " + form.getCourseId() + " not found");
			return null;
		}

        // Add course to plan
		finishAddCourse(plan, form, course, termId, response);
		return null;
	}

    /**
     * Handles the submissions from the edit plan item dialog.
     * Changes the information for a single plan item.
     */
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

            // Copy new course note into the plan item
			RichTextInfo descr = new RichTextInfo();
			descr.setPlain(form.getCourseNote());
			descr.setFormatted(form.getCourseNote());
			planItemInfo.setDescr(descr);

            // Determine if course note has been edited
            String oldFormatted;
            if(previousDescr == null)oldFormatted=null;
			else oldFormatted = previousDescr.getFormatted();
			String newFormatted = descr.getFormatted();
			if(!newFormatted.equals(oldFormatted)){
				if(previousDescr==null || previousDescr.getFormatted() == null){
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

        // Determine if Credit has be changed
		if (oldCredit == null) {
			if (newCredit != null)
				creditEdited = true;
		} else {
			if (newCredit == null || oldCredit.compareTo(newCredit) != 0) {
				creditEdited = true;
			}
		}

        // Update the plan item in the database
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

        // Construct json events for updating the planner screen
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.updatePlanItemEvent(form.getUniqueId(), planItemInfo, eventList);
        try{
            eventList = PlanEventUtils.updateTotalCreditsEvent(true, KSCollectionUtils.getRequiredZeroElement(planItemInfo.getPlanPeriods()), eventList);
        }catch(OperationFailedException e){
            LOG.warn("Unable to update total credits", e);
        }

        // Create json strings for displaying action's response and send those updating the planner screen.
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

    /**
     * Handles the submissions from the copy plan item dialog.
     * Copies the course for a plan item into a new item under a new term.
     */
	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + COPY_PLAN_ITEM_PAGE)
	public ModelAndView copyPlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Retrieve student's plan.
		LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

        // Retrieve plan item to be copied
		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

		String termId = form.getTargetTermId();

        // Retrieve course information
		Course course = form.getCourse();
		if (course == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course " + form.getCourseId() + " not found");
			return null;
		}
		assert course.getId().equals(planItem.getRefObjectId());

        // Populate addition information need to add a plan item from the one to copy.
		form.populateFromPlanItem();

        // Add the course to the plan
		finishAddCourse(plan, form, course, termId, response);
		return null;
	}

    /**
     * Handles the submissions from the move plan item dialog.
     * Changes the term that an existing plan item is in to another one.
     */
	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + MOVE_PLAN_ITEM_PAGE)
	public ModelAndView movePlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String expectedTermId = form.getTermId();
		if (expectedTermId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing term ID");
			return null;
		}

		String termId = form.getTargetTermId();

        // Retrieve term information
		Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);

        // Retrieve plan item information
		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

        // Replaces the existing term information with the new term
		PlanItemInfo planItemInfo = new PlanItemInfo(planItem);
		List<String> planPeriods = new ArrayList<String>(1);
		planPeriods.add(termId);
		planItemInfo.setPlanPeriods(planPeriods);

        // Save updated plan item
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

        // Create json strings for displaying action's response and updating the planner screen.
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), planItem, eventList);
        eventList = PlanEventUtils.makeAddEvent(planItemInfo, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(false, expectedTermId, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, termId, eventList);
        PlanEventUtils.sendJsonEvents(true, "Course " + form.getCourse().getCode() + " moved to " + term.getName(),
				response, eventList);
		return null;
	}

    /**
     * Handles the submissions from the delete plan dialog.
     * Removes a plan item from a students plan and deletes it from the database.
     */
	@RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + DELETE_PLAN_ITEM_PAGE)
	public ModelAndView deletePlanItem(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String expectedTermId = form.getTermId();
		if (expectedTermId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing term ID");
			return null;
		}
		Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(expectedTermId);

        // Retrieve valid plan item
		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

        // Delete plan item from the database
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

        // Create json strings for displaying action's response and updating the planner screen.
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), planItem, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, term.getId(), eventList);
		PlanEventUtils.sendJsonEvents(true, "Course " + form.getCourse().getCode() + " removed from " + term.getName(),
				response, eventList);
		return null;
	}

    /**
     * Handles changing a plan item's type.
     * Changes a plan item from its current type (planned, backup) to a new type (planned, backup)
     */
	@RequestMapping(method = RequestMethod.POST, params = "methodToCall=updatePlanItemCategory")
	public ModelAndView updatePlanItemType(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Retrieve a valid plan item
		PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
		if (planItem == null)
			return null;

		PlanItemInfo planItemInfo = new PlanItemInfo(planItem);


        // Set the new category for the item
        planItemInfo.setCategory(form.isBackup() ? AcademicPlanServiceConstants.ItemCategory.BACKUP
                : AcademicPlanServiceConstants.ItemCategory.PLANNED);

        // Update the plan item in the database.
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

        // Create json strings for displaying action's response and updating the planner screen.
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), planItem, eventList);
        eventList = PlanEventUtils.makeAddEvent(planItemInfo, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, form.getTermId(), eventList);
		PlanEventUtils.sendJsonEvents(true, "Course " + form.getCourse().getCode() + " updated", response, eventList);
		return null;
	}

    /**
     * Helps with adding courses to the student's plan.
     * Creates a new or retrieves an existing learning plan item and fills in the proper information before
     * saving it to the database.
     * @param plan - The student's learning plan
     * @param form - Form containing all information entered for the new plan item
     * @param course - Course plan item is being created for
     * @param termId - Id of the term course is being added to
     * @param response - Service response object
     * @throws IOException -
     * @throws ServletException
     */
    private void finishAddCourse(LearningPlan plan, PlannerForm form, Course course, String termId,
                                 HttpServletResponse response) throws IOException, ServletException {
        AcademicPlanServiceConstants.ItemCategory category = form.isBackup() ? AcademicPlanServiceConstants.ItemCategory.BACKUP
                : AcademicPlanServiceConstants.ItemCategory.PLANNED;
        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        PlanItem wishlistPlanItem = null;

        // Get list of existing plan items
        List<PlanItem> existingPlanItems = form.getExistingPlanItems();
        if (existingPlanItems != null)
            for (PlanItem existingPlanItem : existingPlanItems) {
                // If item has no term then record it
                if (AcademicPlanServiceConstants.ItemCategory.WISHLIST.equals(existingPlanItem.getCategory())) {
                    wishlistPlanItem = existingPlanItem;
                    continue;
                }

                // Check if course is already offered in that term
                List<String> planPeriods = existingPlanItem.getPlanPeriods();
                if (planPeriods != null && planPeriods.contains(termId)) {
                    PlanEventUtils.sendJsonEvents(false, "Course " + course.getCode() + " is already planned for "
                            + form.getTerm().getName(), response, eventList);
                    return;
                }
            }

        // If item is in wishlist use existing entry instead of creating new.
        boolean create = wishlistPlanItem == null;
        PlanItemInfo planItemInfo;
        if (create) {
            planItemInfo = new PlanItemInfo();
            planItemInfo.setCategory(category);
            planItemInfo.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
            planItemInfo.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
            planItemInfo.setLearningPlanId(plan.getId());
        } else {
            assert plan.getId().equals(wishlistPlanItem.getLearningPlanId()) : plan.getId() + " "
                    + wishlistPlanItem.getLearningPlanId();
            eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), wishlistPlanItem, eventList);
            planItemInfo = new PlanItemInfo(wishlistPlanItem);
            planItemInfo.setCategory(category);
        }

        // Fill in course information
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
                // If creating new add it to the database
                planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(planItemInfo,
                        KsapFrameworkServiceLocator.getContext().getContextInfo());
            } else {
                // If using wish list item update it
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

        // Create json strings for displaying action's response and updating the planner screen.
        eventList = PlanEventUtils.makeAddEvent(planItemInfo, eventList);
        eventList = PlanEventUtils.updateTotalCreditsEvent(true, termId, eventList);
        PlanEventUtils.sendJsonEvents(true, course.getCode() + " was successfully added to your plan.",
                response, eventList);
    }

    /**
     * Handles submissions from the bookmark add dialog.
     * Validates the course and adds it to the students plan.
     */
    @RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + ADD_BOOKMARK_PAGE)
    public ModelAndView addBookmarkedCourse(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Retrieve student's plan
        LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        if (plan == null)
            return null;

        String termId = form.getTermId();

        String courseId = form.getCourseId();
        if (!StringUtils.hasText(courseId)) {
            PlanEventUtils.sendJsonEvents(false, "Course id required", response, eventList);
            return null;
        }

        // Retrieve course information using the course code entered by the user
        Course course;
        try {
            course = KsapFrameworkServiceLocator.getCourseService().getCourse(courseId, KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Course service failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Course service failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Course service failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Course service failure", e);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Course service failure", e);
        }

        // Add the course to the plan
        finishAddCourse(plan, form, course, termId, response);
        return null;
    }

    /**
     * Handles the submissions from the bookmark delete dialog.
     * Removes a plan item from a students bookmarks and deletes it from the database.
     */
    @RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + DELETE_BOOKMARK_PAGE)
    public ModelAndView deleteBookmark(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Retrieve valid plan item
        PlanItem planItem = PlanItemControllerHelper.getValidatedPlanItem(form, request, response);
        if (planItem == null)
            return null;

        // Delete plan item from the database
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

        // Create json strings for displaying action's response and updating the planner screen.
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        eventList = PlanEventUtils.makeRemoveEvent(form.getUniqueId(), planItem, eventList);
        PlanEventUtils.sendJsonEvents(true, "Course " + form.getCourse().getCode() + " removed from Bookmarks",
                response, eventList);
        return null;
    }

    /**
     * Handles the additions of items to the bookmark list.
     * Creates a plan item for a course and adds it as a bookmarrk.
     */
    @RequestMapping(params = "methodToCall=addBookmark")
    public ModelAndView addBookmark(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        JsonObjectBuilder eventList = Json.createObjectBuilder();
        LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);

        Course course = form.getCourse();
        if (course == null) {
            LOG.warn("Missing course for summary ");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course for summary ");
            return null;
        }

        // Add the course to the plan
        PlanItemInfo newBookmark = new PlanItemInfo();
        newBookmark.setRefObjectId(course.getId());
        newBookmark.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        newBookmark.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
        newBookmark.setRefObjectType(PlanConstants.COURSE_TYPE);
        newBookmark.setCategory(AcademicPlanServiceConstants.ItemCategory.WISHLIST);
        newBookmark.setLearningPlanId(plan.getId());

        try {
                // If creating new add it to the database
            newBookmark = KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(newBookmark,
                        KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (AlreadyExistsException e) {
            LOG.warn("Course " + course.getCode() + " is already bookmarked", e);
            PlanEventUtils.sendJsonEvents(false,
                    "Course " + course.getCode() + " is already bookmarked", response, eventList);
            return null;
        } catch (DataValidationErrorException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP service failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP service failure", e);
        }
        eventList = PlanEventUtils.makeAddBookmarkEvent(newBookmark, eventList);
        eventList = PlanEventUtils.makeUpdateBookmarkTotalEvent(newBookmark, eventList);
        PlanEventUtils.sendJsonEvents(true, "Course " + course.getCode() + " added to bookmarks",
                response, eventList);
        return null;
    }

    /**
     * Handles submissions from the course add dialog.
     * Validates the course and adds it to the students plan.
     */
    @RequestMapping(method = RequestMethod.POST, params = "view.currentPageId=" + ADD_COURSE_PAGE)
    public ModelAndView addCourse(@ModelAttribute("KualiForm") PlannerForm form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Retrieve student's plan
        LearningPlan plan = PlanItemControllerHelper.getAuthorizedLearningPlan(form, request, response);
        JsonObjectBuilder eventList = Json.createObjectBuilder();
        if (plan == null)
            return null;

        String termId = form.getTermId();

        String courseId = form.getCourseId();
        if (!StringUtils.hasText(courseId)) {
            PlanEventUtils.sendJsonEvents(false, "Course id required", response, eventList);
            return null;
        }

        // Retrieve course information using the course code entered by the user
        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);
        if(course != null && !course.getStateKey().equals("Active")){
            PlanEventUtils.sendJsonEvents(false, "Course " + course.getCode() + " not active", response, eventList);
            return null;
        }

        // Add the course to the plan
        finishAddCourse(plan, form, course, termId, response);
        return null;
    }
}
