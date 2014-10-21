package org.kuali.student.ap.plannerreview.infc;

import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

public interface LearningPlanReviewRequest {

	/**
	 * Get the ID of the original learning plan.
	 * 
	 * @return The ID of the original learning plan.
	 */
	String getOriginalLearningPlanId();
	
	/**
	 * Get the advisor
	 * @return
	 */
	ConversationAdvisor getAdvisor();

	/**
	 * Get the first comment to post to a conversation related to the review.
	 * 
	 * @return The text of the first comment
	 */
	RichText getFirstCommentToAdvisor();

	/**
	 * Get the IDs of additional courses to include in the review.
	 * 
	 * <p>
	 * These courses must correspond to wishlist plan items in the original
	 * learning plan.
	 * </p>
	 * 
	 * @return The IDs of additional courses to include in the review.
	 */
	List<String> getUnassignedCourseIds();

	/**
	 * Get the review terms involved in the review.
	 * 
	 * @return The review terms to be involved in the review.
	 */
	List<LearningPlanReviewTerm> getReviewTerms();
	
	/**
	 * Get the topic of the conversation for the review
	 * @return The text of the topic
	 */
	String getTopic();

}
