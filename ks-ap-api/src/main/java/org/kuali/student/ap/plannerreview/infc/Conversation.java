package org.kuali.student.ap.plannerreview.infc;

import java.util.List;

/**
 * Represents a conversation between a student and advisor.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.7.1
 */
public interface Conversation { 

	/**
	 * Get the ID learning plan this conversation is about.
	 * 
	 * @return The ID of the learning plan.
	 * @see org.kuali.student.ap.academicplan.service.AcademicPlanService
	 */
	String getLearningPlanId();

	/**
	 * Get the Advisor that is associated with this conversation
	 * @return
	 */
	ConversationAdvisor getAdvisor();

	/**
	 * Determine whether or not the conversation is closed for comments.
	 * 
	 * @return True if the conversation is closed, false if comments may still
	 *         be added.
	 */
	boolean isClosed();

	/**
	 * Get the comments on the conversation.
	 * 
	 * @return The comments for this conversation.
	 */
	List<ConversationComment> getComments();
	
	/**
	 * Get the topic for the conversation
	 * @return
	 */
	String getTopic();

}
