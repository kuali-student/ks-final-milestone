package org.kuali.student.ap.plannerreview.support;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.plannerreview.dto.ConversationAdvisorInfo;
import org.kuali.student.ap.plannerreview.dto.ConversationCommentInfo;
import org.kuali.student.ap.plannerreview.dto.ConversationInfo;
import org.kuali.student.ap.plannerreview.infc.Conversation;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.infc.ConversationComment;
import org.kuali.student.ap.plannerreview.infc.LearningPlanReviewRequest;
import org.kuali.student.ap.plannerreview.LearningPlanReviewStrategy;
import org.kuali.student.ap.plannerreview.util.ConversationConstants;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MockLearningPlanReviewStrategy implements
		LearningPlanReviewStrategy {
	
	private List<Conversation> conversations = new ArrayList<Conversation>();
	private ConversationAdvisor mockAdvisor1;
	private ConversationAdvisor mockAdvisor2;
	private ConversationAdvisor mockAdvisor3;
	private ConversationAdvisor mockAdvisor4;
	
	private int commentSequence = 0;
	
	@Override
	public List<ConversationAdvisor> getAdvisors() {
		List<ConversationAdvisor> advisors = new ArrayList<ConversationAdvisor>();
		advisors.add(mockAdvisor1);
		advisors.add(mockAdvisor2);
		advisors.add(mockAdvisor3);
		advisors.add(mockAdvisor4);
		
		return advisors;
	}
	
	@Override
	public boolean hasAdvisors() {
		boolean hasAdvisors = false;
		
		List<ConversationAdvisor> advisors = getAdvisors();
		if (advisors != null && advisors.size() > 0) {
			hasAdvisors = true;
		}
		
		return hasAdvisors;
	}

	@Override
	public String createLearningPlanReview(LearningPlanReviewRequest request) throws PermissionDeniedException {
		ConversationInfo conversation = new ConversationInfo();
		conversation.setLearningPlanId(request.getOriginalLearningPlanId());
		conversation.setAdvisor(request.getAdvisor());
		List<ConversationComment> comments = new ArrayList<ConversationComment>();
		ConversationCommentInfo comment = new ConversationCommentInfo();
		comment.setId(String.valueOf(commentSequence++));
		String userId = getUserId();
		Date now = new Date();
		MetaInfo meta = new MetaInfo();
		meta.setCreateId(userId);
		meta.setCreateTime(now);
		meta.setUpdateId(userId);
		meta.setUpdateTime(now);
		
		comment.setByAdvisor(false);
		comment.setReadOnce(false);
		comment.setCommentText(request.getFirstCommentToAdvisor());
		comment.setMeta(meta);
		comments.add(comment);
		conversation.setComments(comments);
		conversation.setTopic(request.getTopic());
		conversations.add(conversation);
		int size = conversations.size();
		return String.valueOf(size);
	}

	@Override
	public Conversation getConversation(String learningPlanId) throws PermissionDeniedException {
		for (Conversation conversation : conversations) {
			if (learningPlanId.equals(conversation.getLearningPlanId()))
				return conversation;
		}
		return null;
	}

	@Override
	public ConversationComment addCommentToConversation(String learningPlanId, boolean byAdvisor,
			RichText comment) throws PermissionDeniedException {
		ConversationCommentInfo cci = new ConversationCommentInfo();
		cci.setId(String.valueOf(commentSequence++));
		cci.setByAdvisor(byAdvisor);
		cci.setCommentText(comment);
		
		String userId = getUserId();
		
		Date now = new Date();
		MetaInfo meta = new MetaInfo();
		meta.setCreateId(userId);
		meta.setCreateTime(now);
		meta.setUpdateId(userId);
		meta.setUpdateTime(now);
		//meta.setVersionInd("1");
		
		cci.setMeta(meta);
		
		//If the student made the comment, go ahead and mark it as read
		cci.setReadOnce(!byAdvisor);
		
		Conversation conversation = getConversation(learningPlanId);
		conversation.getComments().add(cci);
		
		return cci;
	}
	
	@Override
	public List<Conversation> getConversations() throws PermissionDeniedException {
		if (conversations.isEmpty()) {
			initConversations();
		}
		return conversations;
	}

	/**
	 * Method to get the current user's id
	 * @return
	 */
	private String getUserId() {
		Person user = GlobalVariables.getUserSession().getPerson();
		return user.getPrincipalId();
	}
	
	/**
	 * Initialize mock advisor data
	 */
	private void initMockAdvisors() {
		mockAdvisor1 = new ConversationAdvisorInfo("0002126794:ADVR", "0002126794", ConversationConstants.CONV_ADVISOR_ID_TYPE_ADVISOR,
				"ADVR", "Advisor", "", "Schlemmer, Cheryl Ann");
		
		mockAdvisor2 = new ConversationAdvisorInfo("0003092787:ADVR", "0003092787", ConversationConstants.CONV_ADVISOR_ID_TYPE_ADVISOR, 
				"ADVR", "Advisor", "", "Evans, Kim Samarian");
		
		mockAdvisor3 = new ConversationAdvisorInfo("BLEDUC:ADOF", "BLEDUC", ConversationConstants.CONV_ADVISOR_ID_TYPE_ADVISING_OFFICE, 
				"ADOF", "Advising Office", "", "BLEDUC");
		
		mockAdvisor4 = new ConversationAdvisorInfo("BLBUS1:ADOF", "BLBUS1", ConversationConstants.CONV_ADVISOR_ID_TYPE_ADVISING_OFFICE, 
				"ADOF", "Advising Office", "", "BLBUS1");
	}
	
	/**
	 * Initialize the mock data
	 */
	private void initConversations() {
		String userId = getUserId();
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();
		
		cal.setTime(now);
		cal.add(Calendar.DATE, -7);
		Date lastWeek = cal.getTime();
		
		cal.setTime(now);
		cal.add(Calendar.DATE, -365);
		Date lastYear = cal.getTime();
		
		//Init the mock advisors
		initMockAdvisors();
		
		ConversationInfo conversation = new ConversationInfo();
		MetaInfo meta = new MetaInfo();
		meta.setCreateId(userId);
		meta.setCreateTime(now);
		meta.setUpdateId(userId);
		meta.setUpdateTime(now);
		meta.setVersionInd("1");
		
		conversation.setLearningPlanId("1");
		conversation.setAdvisor(mockAdvisor1);
		conversation.setTopic("This is a topic (open, unread).  Adding some more text here in hopes of making it long enough to figure out how to wrap it and handle some overflow, etc.");
		conversation.setClosed(false);
		List<ConversationComment> comments = new ArrayList<ConversationComment>();
		ConversationCommentInfo comment = new ConversationCommentInfo();
		comment.setId(String.valueOf(commentSequence++));
		comment.setByAdvisor(false);
		comment.setReadOnce(false);
		String commentText = "Here is some sample text.  How long do you think it'll have to be so that I get enough in the UI to make it wrap and be long enough to get some overflow so I can add an ellipse?  Hopefully this is enough!";
		comment.setCommentText(new RichTextInfo(commentText, commentText));
		comment.setMeta(meta);
		comments.add(comment);
		conversation.setComments(comments);
		conversations.add(conversation);
		
		conversation = new ConversationInfo();
		meta = new MetaInfo();
		meta.setCreateId(userId);
		meta.setCreateTime(yesterday);
		meta.setUpdateId(userId);
		meta.setUpdateTime(yesterday);
		meta.setVersionInd("1");
		
		conversation.setLearningPlanId("2");
		conversation.setAdvisor(mockAdvisor3);
		conversation.setTopic("This is another topic (open, read)");
		conversation.setClosed(false);
		comments = new ArrayList<ConversationComment>();
		comment = new ConversationCommentInfo();
		comment.setId(String.valueOf(commentSequence++));
		comment.setByAdvisor(false);
		comment.setReadOnce(true);
		commentText = "Here is some more sample text, not to be confused with the previous text.  How long do you think it'll have to be so that I get enough in the UI to make it wrap and be long enough to get some overflow so I can add an ellipse?  Hopefully this is enough!";
		comment.setCommentText(new RichTextInfo(commentText, commentText));
		comment.setMeta(meta);
		comments.add(comment);
		conversation.setComments(comments);
		conversations.add(conversation);
		
		conversation = new ConversationInfo();
		meta = new MetaInfo();
		meta.setCreateId(userId);
		meta.setCreateTime(lastWeek);
		meta.setUpdateId(userId);
		meta.setUpdateTime(lastWeek);
		meta.setVersionInd("1");
		
		conversation.setLearningPlanId("3");
		conversation.setAdvisor(mockAdvisor3);
		//conversation.setTopic("This is yet again a new topic (closed, read)");
		conversation.setClosed(true);
		comments = new ArrayList<ConversationComment>();
		comment = new ConversationCommentInfo();
		comment.setId(String.valueOf(commentSequence++));
		comment.setByAdvisor(false);
		comment.setReadOnce(true);
		commentText = "Here is even more sample text, not to be confused with the previous two sets of text.  How long do you think it'll have to be so that I get enough in the UI to make it wrap and be long enough to get some overflow so I can add an ellipse?  Hopefully this is enough!";
		comment.setCommentText(new RichTextInfo(commentText, commentText));
		comment.setMeta(meta);
		comments.add(comment);
		
		
		meta = new MetaInfo();
		meta.setCreateId(conversation.getAdvisor().getUserId());
		meta.setCreateTime(yesterday);
		meta.setUpdateId(userId);
		meta.setUpdateTime(yesterday);
		meta.setVersionInd("1");
		comment = new ConversationCommentInfo();
		comment.setId(String.valueOf(commentSequence++));
		comment.setByAdvisor(true);
		comment.setReadOnce(true);
		comment.setCommentText(new RichTextInfo("great", "great"));
		comment.setMeta(meta);
		comments.add(comment);
		conversation.setComments(comments);
		conversations.add(conversation);
		
		conversation = new ConversationInfo();
		conversation.setLearningPlanId("4");
		conversation.setAdvisor(mockAdvisor2);
		conversation.setTopic("4th topic (open, unread)");
		conversation.setClosed(false);
		
		meta = new MetaInfo();
		meta.setCreateId(conversation.getAdvisor().getUserId());
		meta.setCreateTime(lastYear);
		meta.setUpdateId(conversation.getAdvisor().getUserId());
		meta.setUpdateTime(lastYear);
		
		comments = new ArrayList<ConversationComment>();
		comment = new ConversationCommentInfo();
		comment.setId(String.valueOf(commentSequence++));
		comment.setByAdvisor(true);
		comment.setReadOnce(false);
		comment.setCommentText(new RichTextInfo("This is fun!", "This is fun!"));
		comment.setMeta(meta);
		comments.add(comment);
		conversation.setComments(comments);
		conversations.add(conversation);
		
	}

	@Override
	public boolean markCommentAsRead(String conversationCommentId) throws PermissionDeniedException {
		for (Conversation conversation : conversations) {
			for (ConversationComment comment : conversation.getComments()) {
				ConversationCommentInfo cci = (ConversationCommentInfo) comment;
				if (conversationCommentId.equals(comment.getId())) {
					cci.setReadOnce(true);
					MetaInfo meta = (MetaInfo)cci.getMeta();
					meta.setUpdateTime(new Date());
					meta.setUpdateId(getUserId());
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean markAllConversationCommentsAsRead(String learningPlanId) throws PermissionDeniedException {
		Conversation conversation = getConversation(learningPlanId);
		for (ConversationComment comment : conversation.getComments()) {
			ConversationCommentInfo cci = (ConversationCommentInfo) comment;
			cci.setReadOnce(true);
			MetaInfo meta = (MetaInfo)cci.getMeta();
			meta.setUpdateTime(new Date());
			meta.setUpdateId(getUserId());
		}
		
		return true;
	}
}
