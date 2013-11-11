package org.kuali.student.ap.plannerreview;

import org.kuali.student.ap.plannerreview.infc.Conversation;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.infc.ConversationComment;
import org.kuali.student.ap.plannerreview.infc.LearningPlanReviewRequest;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

/**
 * Defines methods for interacting with Learning Plan Reviews
 * @author Chris Maurer <chmaurer@iu.edu>
 *
 */
public interface LearningPlanReviewStrategy {

    /**
	 * Get all available advisors for the current student
	 * @return
	 */
	List<ConversationAdvisor> getAdvisors();
	
	/**
	 * Calls {@link #getAdvisors()} and returns true if the list is not empty.
	 * @return
	 */
	boolean hasAdvisors();
	
	/**
	 * Create a new learning plan review, based on an existing learning plan,
	 * and return the learning plan ID for the review.
	 * 
	 * <p>
	 * The resulting learning plan will have a type of
	 * {@link org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN_REVIEW}
	 * </p>
	 * 
	 * @param request
	 *            The request for review.
	 * @return The ID for the newly created learning plan review.
	 * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
	 *             If the current user does not have access to the create a
	 *             learning plan review.
	 */
	String createLearningPlanReview(LearningPlanReviewRequest request)  throws PermissionDeniedException;

	/**
	 * Create a new conversation based on a learning plan review.
	 *
	 * @param learningPlanId
	 *            The ID of the learning plan review.
	 * @return The newly created conversation.
	 * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
	 *             If the current user does not have access to the requested
	 *             learning plan.
	 */
	Conversation getConversation(String learningPlanId) throws PermissionDeniedException;

	/**
	 * Add a comment to the conversation on a learning plan review.
	 * @param learningPlanId The ID of the learning plan review.
	 * @param byAdvisor	Flag indicating if the comment is from the advisor.  False means it is from the student.
	 * @param comment The comment to add
	 * @return The newly added comment
	 * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
	 *             If the current user does not have access to the requested
	 *             learning plan.
	 */
	ConversationComment addCommentToConversation(String learningPlanId, boolean byAdvisor,
                                                 RichText comment) throws PermissionDeniedException;

	/**
	 * Get all conversations for a student
	 * @return A list of Conversations
	 * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
	 *             If the current user does not have access to view conversations.
	 */
	List<Conversation> getConversations() throws PermissionDeniedException;

	/**
	 * Mark a single comment as read
	 * @param conversationCommentId
	 * 				The ID of the comment to mark as read
	 * @return A success or failure to do the update
	 * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
	 *             If the current user does not have access to the requested
	 *             conversation comment.
	 */
	boolean markCommentAsRead(String conversationCommentId) throws PermissionDeniedException;

	/**
	 * Mark all comments inside a conversation as read
	 * @param learningPlanId The ID of the learning plan review
	 * @return A success or failure to do the update
	 * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
	 *             If the current user does not have access to the requested
	 *             learning plan.
	 */
	boolean markAllConversationCommentsAsRead(String learningPlanId) throws PermissionDeniedException;

}
