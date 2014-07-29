package org.kuali.student.ap.plannerreview.dto;

import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.infc.LearningPlanReviewRequest;
import org.kuali.student.ap.plannerreview.infc.LearningPlanReviewTerm;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

public class LearningPlanReviewRequestInfo implements LearningPlanReviewRequest {

	private String originalLearningPlanId;
	private ConversationAdvisor advisor;
	private RichText firstCommentToAdvisor;
	private List<String> unassignedCourseIds;
	private List<LearningPlanReviewTerm> reviewTerms;
	private String topic;
	
	
	@Override
	public String getOriginalLearningPlanId() {
		return originalLearningPlanId;
	}

	@Override
	public RichText getFirstCommentToAdvisor() {
		return firstCommentToAdvisor;
	}

	@Override
	public List<String> getUnassignedCourseIds() {
		return unassignedCourseIds;
	}

	@Override
	public List<LearningPlanReviewTerm> getReviewTerms() {
		return reviewTerms;
	}

	public void setOriginalLearningPlanId(String originalLearningPlanId) {
		this.originalLearningPlanId = originalLearningPlanId;
	}

	public void setFirstCommentToAdvisor(RichText firstCommentToAdvisor) {
		this.firstCommentToAdvisor = firstCommentToAdvisor;
	}

	public void setUnassignedCourseIds(List<String> unassignedCourseIds) {
		this.unassignedCourseIds = unassignedCourseIds;
	}

	public void setReviewTerms(List<LearningPlanReviewTerm> reviewTerms) {
		this.reviewTerms = reviewTerms;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public ConversationAdvisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(ConversationAdvisor advisor) {
		this.advisor = advisor;
	}

}
