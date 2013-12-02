package org.kuali.student.ap.plannerreview.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.plannerreview.infc.Conversation;
import org.kuali.student.ap.plannerreview.infc.ConversationComment;
import org.kuali.student.ap.plannerreview.util.ConversationConstants;
import org.kuali.student.ap.plannerreview.dto.ConversationAdvisorInfo;
import org.kuali.student.ap.plannerreview.dto.ConversationCommentInfo;
import org.kuali.student.ap.plannerreview.dto.ConversationDateCommentInfo;
import org.kuali.student.ap.plannerreview.form.ConversationViewForm;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/reviewView")
public class ConversationViewController  extends ConversationControllerBase {
	
	private static final Logger LOG = Logger.getLogger(ConversationViewController.class);
	
	public static final String CONVO_FORM = "Conversation-FormView";
	
	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new ConversationViewForm();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@ModelAttribute("KualiForm") ConversationViewForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		super.start(form, result, request, response);
		try {
			initialize(form);
		} catch (PermissionDeniedException e) {
			LOG.warn(
					"User " + request.getRemoteUser()
							+ " is not permitted to view this conversation.",
					e);
			response.sendError(
					HttpServletResponse.SC_FORBIDDEN,
					"User " + request.getRemoteUser()
							+ " is not permitted to view this conversation.");
		}
		LOG.debug("CONVO_FORM: " + form);
		form.setViewId(CONVO_FORM);
		form.setView(super.getViewService().getViewById(CONVO_FORM));
		return getUIFModelAndView(form);
	}
	
	@RequestMapping(params = "methodToCall=send")
	public ModelAndView submit(
			@ModelAttribute("KualiForm") ConversationViewForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		if (!result.hasErrors()) {
			RichText richMessage = new RichTextInfo(form.getNewComment(), form.getNewComment());
			try {
				ConversationComment newComment = getLearningPlanReviewStrategy().addCommentToConversation(form.getLearningPlanId(), false, richMessage);
				addNewCommentToForm(newComment, form);
				form.setNewComment("");
			} catch (PermissionDeniedException e) {
				throw new ServletException("Unexpected authorization failure", e);
			}
		}
		return getUIFModelAndView(form);
	}
	
	/**
	 * Initialize the form with necessary data
	 * @param form
	 */
	private void initialize(ConversationViewForm form) throws PermissionDeniedException {
		//Mark all comments as read.
		getLearningPlanReviewStrategy().markAllConversationCommentsAsRead(form.getLearningPlanId());
		
		Conversation conversation = getLearningPlanReviewStrategy().getConversation(form.getLearningPlanId());
		
		List<ConversationComment> comments = conversation.getComments();
		//sort in reverse order
		Collections.sort(comments, Collections.reverseOrder());
		
		form.setConversation(conversation);
		List<ConversationDateCommentInfo> commentsByDate = new ArrayList<ConversationDateCommentInfo>();
		Map<String, ConversationAdvisorInfo> advisorMap = new HashMap<String, ConversationAdvisorInfo>();
		for (ConversationComment comment : conversation.getComments()) {
			ConversationCommentInfo commentInfo = (ConversationCommentInfo) comment;
			int day = commentInfo.getCommentCreationDay();
			ConversationDateCommentInfo cdci = getCommentInfoListByDay(day, commentsByDate);
			if (cdci == null) {
				cdci = new ConversationDateCommentInfo();
				cdci.setDayOfYear(day);
				cdci.setDateDisplay(commentInfo.getCommentCreationDayDisplay());
				cdci.addComment(commentInfo, false);
				commentsByDate.add(cdci);
			}
			else {
				cdci.addComment(commentInfo, false);
			}
			
			// Add in the user making the comment.  If it is the advisor, use their role, otherwise just use STUDENT.
			// If the comment is by the advisor, set the type, otherwise always use the student type since it'll just look them up as a person
			String role = comment.isByAdvisor() ? conversation.getAdvisor().getAdvisorRoleId() : "STUDENT";
			String type = comment.isByAdvisor() ? conversation.getAdvisor().getAdvisorType() : ConversationConstants.CONV_COMMENTER_TYPE_STUDENT;
			addAdvisorToMap(advisorMap, comment.getMeta().getCreateId(), role, type);
		}
		
		//Add current user to map since they may not have made a comment to be added above
		addAdvisorToMap(advisorMap, getUserId(), "STUDENT", ConversationConstants.CONV_COMMENTER_TYPE_STUDENT);
		form.setAdvisorMap(advisorMap);
		
		//sort in reverse order
		Collections.sort(commentsByDate, Collections.reverseOrder());
		form.setCommentsByDate(commentsByDate);
	}
	
	/**
	 * Add a newly created comment to the form data 
	 * @param comment New comment to add
	 * @param form Form containing the data to add the comment to
	 */
	private void addNewCommentToForm(ConversationComment comment, ConversationViewForm form) {
		List<ConversationDateCommentInfo> commentsByDate = form.getCommentsByDate();
		ConversationCommentInfo commentInfo = (ConversationCommentInfo) comment;
		int day = commentInfo.getCommentCreationDay();
		ConversationDateCommentInfo cdci = getCommentInfoListByDay(day, commentsByDate);
		if (cdci == null) {
			cdci = new ConversationDateCommentInfo();
			cdci.setDayOfYear(day);
			cdci.setDateDisplay(commentInfo.getCommentCreationDayDisplay());
			cdci.addComment(commentInfo, true);
			commentsByDate.add(0, cdci);
		}
		else {
			cdci.addComment(commentInfo, true);
		}
		//sort in reverse order
		//Collections.sort(commentsByDate, Collections.reverseOrder());
	}
	
	/**
	 * Get a list of all comments for a particular day
	 * @param day Day of the year on which to get comments
	 * @param commentsByDaten Base list to search through
	 * @return
	 */
	private ConversationDateCommentInfo getCommentInfoListByDay(Integer day, List<ConversationDateCommentInfo> commentsByDate) {
		for (ConversationDateCommentInfo cdci : commentsByDate) {
			if (day.equals(cdci.getDayOfYear()))
				return cdci;
		}
		return null;
	}

}
