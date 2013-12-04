package org.kuali.student.ap.plannerreview.infc;

/**
 * A conversation commenter is anyone who can comment on a conversation.  
 * It could be the advisor or student.
 * @author Chris Maurer <chmaurer@iu.edu>
 *
 */
public interface ConversationCommenter {

	/**
	 * Get the id of this user 
	 * @return
	 */
	String getUserId();
	
	/**
	 * Get the user's name
	 * @return
	 */
	String getName();
	
	/**
	 * TODO Decide what other pieces of information about a commenter that we want to capture
	 * @return
	 */
	String getDescription();

}
