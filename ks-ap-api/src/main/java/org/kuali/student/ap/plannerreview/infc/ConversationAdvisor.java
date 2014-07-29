package org.kuali.student.ap.plannerreview.infc;

/**
 * 
 * @author Chris Maurer <chmaurer@iu.edu>
 *
 */
public interface ConversationAdvisor extends ConversationCommenter {

	/**
	 * Get the unique identifier that represents this advisor.  The reason that {@link org.kuali.student.ap.plannerreview.infc.ConversationCommenter#getUserId()})
	 * might not be unique is that an advisor might have more than one role
	 * @return
	 */
	String getUniqueId();
	
	/**
	 * Get the name of the role
	 * @return
	 */
	String getAdvisorRoleName();

	/**
	 * Get the id of the role
	 * @return
	 */
	String getAdvisorRoleId();

	/**
	 * Get the advisor type
	 * @return
	 */
	String getAdvisorType();

}
