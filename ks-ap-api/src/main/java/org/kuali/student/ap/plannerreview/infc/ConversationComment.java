package org.kuali.student.ap.plannerreview.infc;

import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.HasMeta;
import org.kuali.student.r2.common.infc.RichText;

/**
 * 
 * @author Chris Maurer <chmaurer@iu.edu>
 *
 */
public interface ConversationComment extends HasId, HasMeta {

	/**
	 * Determine if the comment was made by the advisor or by the student.
	 * 
	 * @return True if the comment was made by the advisor, false if by the
	 *         student.
	 */
	boolean isByAdvisor();

	/**
	 * Determine if the comment has been read at least once by the other party.
	 * 
	 * @return True if the comment has been read at least once by the other
	 *         party, false if not.
	 */
	boolean isReadOnce();

	/**
	 * Get the text of the comment.
	 * 
	 * @return The text of the comment.
	 */
	RichText getCommentText();

}
