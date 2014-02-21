package org.kuali.student.ap.plannerreview.form;

import org.kuali.student.ap.plannerreview.infc.Conversation;
import org.kuali.student.ap.plannerreview.dto.ConversationDateCommentInfo;

import java.util.List;

public class ConversationViewForm extends ConversationFormBase {

	private static final long serialVersionUID = 7787752346692265341L;

	private String learningPlanId;
	private Conversation conversation;
	private String newComment;
	private List<ConversationDateCommentInfo> commentsByDate;
	
	public String getLearningPlanId() {
		return learningPlanId;
	}
	public void setLearningPlanId(String learningPlanId) {
		this.learningPlanId = learningPlanId;
	}
	public Conversation getConversation() {
		return conversation;
	}
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	public String getNewComment() {
		return newComment;
	}
	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public boolean isCommentTextEmpty() {
		return (newComment == null || "".equals(newComment));
	}
	
	public List<ConversationDateCommentInfo> getCommentsByDate() {
		return commentsByDate;
	}
	public void setCommentsByDate(List<ConversationDateCommentInfo> commentsByDate) {
		this.commentsByDate = commentsByDate;
	}	
}
