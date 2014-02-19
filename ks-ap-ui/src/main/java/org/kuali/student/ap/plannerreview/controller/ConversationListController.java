package org.kuali.student.ap.plannerreview.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.plannerreview.infc.Conversation;
import org.kuali.student.ap.plannerreview.infc.ConversationComment;
import org.kuali.student.ap.plannerreview.dto.ConversationInfo;
import org.kuali.student.ap.plannerreview.dto.ConversationYearInfo;
import org.kuali.student.ap.plannerreview.form.ConversationListForm;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


@Controller
@RequestMapping(value = "/reviewList")
public class ConversationListController  extends ConversationControllerBase {
	
	private static final Logger LOG = Logger.getLogger(ConversationListController.class);
	
	public static final String CONVO_FORM = "Conversation-list-FormView";
	

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return new ConversationListForm();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@ModelAttribute("KualiForm") ConversationListForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		super.start(form, request, response);
		
		try {
			initialize(form);
		} catch (PermissionDeniedException e) {
			LOG.warn(
					"User " + request.getRemoteUser()
							+ " is not permitted to retrieve conversations.",
					e);
			response.sendError(
					HttpServletResponse.SC_FORBIDDEN,
					"User " + request.getRemoteUser()
							+ " is not permitted to retreive conversations.");
		}
		LOG.debug("CONVO_FORM: " + form);
		form.setViewId(CONVO_FORM);
		form.setView(super.getViewService().getViewById(CONVO_FORM));
		return getUIFModelAndView(form);
	}
	
    @RequestMapping(params = "methodToCall=ajaxRefresh")
    public ModelAndView ajaxRefresh(@ModelAttribute("KualiForm") ConversationListForm form,
    		HttpServletRequest request,
    		HttpServletResponse response)
            throws Exception {
    	try {
			initialize(form);
		} catch (PermissionDeniedException e) {
			LOG.warn(
					"User " + request.getRemoteUser()
							+ " is not permitted to retrieve conversations.",
					e);
			response.sendError(
					HttpServletResponse.SC_FORBIDDEN,
					"User " + request.getRemoteUser()
							+ " is not permitted to retreive conversations.");
		}
		form.setViewId(CONVO_FORM);
		form.setView(super.getViewService().getViewById(CONVO_FORM));
		return getUIFModelAndView(form);
    }
	
	private void initialize(ConversationListForm form) throws PermissionDeniedException{
		int unreadCount = 0;
		int openCount = 0;
		List<Conversation> conversations = getLearningPlanReviewStrategy().getConversations();
		List<ConversationYearInfo> conversationsByYear = new ArrayList<ConversationYearInfo>();
		Map<String, List<ConversationInfo>> yearConversationMap = new HashMap<String, List<ConversationInfo>>();
		
		for (Conversation conversation : conversations) {
			ConversationInfo convo = (ConversationInfo) conversation;
			ConversationComment comment = convo.getLatestComment();
			Date create = comment.getMeta().getCreateTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(create);
			String year = String.valueOf(cal.get(Calendar.YEAR));
			
			List<ConversationInfo> yearConvos = yearConversationMap.get(year);
			if (yearConvos == null) {
				yearConvos = new ArrayList<ConversationInfo>();
			}
			yearConvos.add(convo);
			yearConversationMap.put(year, yearConvos);
			
			// figure out how many open and unread messages there are
			if (!convo.isClosed())
				openCount++;
			if (convo.hasUnreadComments())
				unreadCount++;
			
		}
		
		for (Entry<String, List<ConversationInfo>> entry : yearConversationMap.entrySet()) {
			ConversationYearInfo cyi = new ConversationYearInfo();
			String keyYear = entry.getKey();
			List<ConversationInfo> convos = entry.getValue();
			
			Collections.sort(convos, new Comparator<ConversationInfo>() {
				// reverse chronological
				@Override
				public int compare(ConversationInfo o1, ConversationInfo o2) {
					if (o1 == null && o2 == null)
						return 0;
					if (o1 == null)
						return -1;
					if (o2 == null)
						return 1;
					Date t1 = o1.getLatestComment().getMeta() == null ? null : o1.getLatestComment().getMeta().getCreateTime();
					Date t2 = o2.getLatestComment().getMeta() == null ? null : o2.getLatestComment().getMeta().getCreateTime();
					if (t1 == null && t2 == null)
						return 0;
					if (t1 == null)
						return -1;
					if (t2 == null)
						return 1;
					return t2.compareTo(t1);
				}
			});
			
			cyi.setConversations(convos);
			cyi.setHeaderOpenText(keyYear);
			cyi.setHeaderClosedText(convos.size() + " conversations in " + keyYear);
			if (convos.size() == 1)
				cyi.setHeaderClosedText(convos.size() + " conversation in " + keyYear);
			conversationsByYear.add(cyi);
		}
		form.setHasNoAdvisors(!getLearningPlanReviewStrategy().hasAdvisors());
		form.setConversations(conversationsByYear);
		
		String summaryText = "Conversations";
		if (unreadCount > 0)
			summaryText = unreadCount + " unread messages";
		else if (openCount > 0)
			summaryText = openCount + " open conversations";
		
		form.setMessageSummaryText(summaryText);
	}

}
