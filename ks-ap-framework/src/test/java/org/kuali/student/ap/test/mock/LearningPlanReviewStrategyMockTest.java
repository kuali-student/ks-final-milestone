package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.plannerreview.LearningPlanReviewStrategy;
import org.kuali.student.ap.plannerreview.infc.Conversation;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.infc.ConversationComment;
import org.kuali.student.ap.plannerreview.infc.LearningPlanReviewRequest;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class LearningPlanReviewStrategyMockTest implements LearningPlanReviewStrategy {
    /**
     * Get all available advisors for the current student
     *
     * @return
     */
    @Override
    public List<ConversationAdvisor> getAdvisors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Calls {@link #getAdvisors()} and returns true if the list is not empty.
     *
     * @return
     */
    @Override
    public boolean hasAdvisors() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create a new learning plan review, based on an existing learning plan,
     * and return the learning plan ID for the review.
     * <p/>
     * <p>
     * The resulting learning plan will have a type of
     * {@link org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN_REVIEW}
     * </p>
     *
     * @param request The request for review.
     * @return The ID for the newly created learning plan review.
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          If the current user does not have access to the create a
     *          learning plan review.
     */
    @Override
    public String createLearningPlanReview(LearningPlanReviewRequest request) throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create a new conversation based on a learning plan review.
     *
     * @param learningPlanId The ID of the learning plan review.
     * @return The newly created conversation.
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          If the current user does not have access to the requested
     *          learning plan.
     */
    @Override
    public Conversation getConversation(String learningPlanId) throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a comment to the conversation on a learning plan review.
     *
     * @param learningPlanId The ID of the learning plan review.
     * @param byAdvisor      Flag indicating if the comment is from the advisor.  False means it is from the student.
     * @param comment        The comment to add
     * @return The newly added comment
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          If the current user does not have access to the requested
     *          learning plan.
     */
    @Override
    public ConversationComment addCommentToConversation(String learningPlanId, boolean byAdvisor, RichText comment) throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get all conversations for a student
     *
     * @return A list of Conversations
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          If the current user does not have access to view conversations.
     */
    @Override
    public List<Conversation> getConversations() throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Mark a single comment as read
     *
     * @param conversationCommentId The ID of the comment to mark as read
     * @return A success or failure to do the update
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          If the current user does not have access to the requested
     *          conversation comment.
     */
    @Override
    public boolean markCommentAsRead(String conversationCommentId) throws PermissionDeniedException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Mark all comments inside a conversation as read
     *
     * @param learningPlanId The ID of the learning plan review
     * @return A success or failure to do the update
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          If the current user does not have access to the requested
     *          learning plan.
     */
    @Override
    public boolean markAllConversationCommentsAsRead(String learningPlanId) throws PermissionDeniedException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
