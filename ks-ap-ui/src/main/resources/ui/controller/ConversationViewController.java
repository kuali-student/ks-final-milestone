package ui.controller;

import org.apache.log4j.Logger;
import org.kuali.student.ap.plan.review.Conversation;
import org.kuali.student.ap.plan.review.ConversationComment;
import org.kuali.student.ap.plan.review.ConversationConstants;
import org.kuali.student.ap.plan.review.dto.AdvisorInfo;
import org.kuali.student.ap.plan.review.dto.ConversationCommentInfo;
import org.kuali.student.ap.plan.review.dto.ConversationDateCommentInfo;
import org.kuali.student.ap.plan.review.form.ConversationViewForm;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/review/view/**")
public class ConversationViewController  extends ConversationControllerBase {
	
	private static final Logger LOG = Logger.getLogger(ConversationViewController.class);
	
	@Override
	protected ConversationViewForm createInitialForm(HttpServletRequest request) {
		return new ConversationViewForm();
	}

    @RequestMapping(params = "methodToCall=load")
	public ModelAndView get(@ModelAttribute("KualiForm") ConversationViewForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		super.start(form, result, request, response);
		//if (((LearningPlanReviewForm) form).getLearningPlanId() == null) {
		//	response.sendError(HttpServletResponse.SC_BAD_REQUEST,
		//			"LearningPlan ID is not available for planning");
		//}
		initialize(form);
		return getUIFModelAndView(form);
	}
	
	@RequestMapping(params = "methodToCall=send")
	public ModelAndView submit(
			@ModelAttribute("KualiForm") ConversationViewForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
        super.start(form, result, request, response);
        initialize(form);
		if (!result.hasErrors()) {
			RichText richMessage = new RichTextInfo(form.getNewComment(), form.getNewComment());
			ConversationComment newComment = getLearningPlanReviewStrategy().addCommentToConversation(form.getLearningPlanId(), false, richMessage);
			addNewCommentToForm(newComment, form);

		}
		form.setNewComment("");
		return getUIFModelAndView(form);
	}

	/**
	 * Initialize the form with necessary data
	 * @param form
	 */
	private void initialize(ConversationViewForm form) {
		//Mark all comments as read.
		getLearningPlanReviewStrategy().markAllConversationCommentsAsRead(form.getLearningPlanId());
		
		Conversation conversation = getLearningPlanReviewStrategy().getConversation(form.getLearningPlanId());
		
		form.setConversation(conversation);
		List<ConversationDateCommentInfo> commentsByDate = new ArrayList<ConversationDateCommentInfo>();
		Map<String, AdvisorInfo> advisorMap = new HashMap<String, AdvisorInfo>();
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
	
	private ConversationDateCommentInfo getCommentInfoListByDay(Integer day, List<ConversationDateCommentInfo> commentsByDate) {
		for (ConversationDateCommentInfo cdci : commentsByDate) {
			if (day.equals(cdci.getDayOfYear()))
				return cdci;
		}
		return null;
	}

}
