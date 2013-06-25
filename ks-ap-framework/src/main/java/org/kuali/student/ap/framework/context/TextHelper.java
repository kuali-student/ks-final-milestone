package org.kuali.student.ap.framework.context;

import org.kuali.student.r2.common.messages.service.MessageService;

/**
 * Simplified interface acquire text for presentation to the user based on
 * transactional context.
 * 
 * <p>
 * The default implementation of this helper class will determine the user's
 * locale and the correct message group based on the active request, then supply
 * text from {@link MessageService} based on the message code.
 * </p>
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version KSAP-0.6.0
 */
public interface TextHelper {

	/**
	 * Get the text for a message code within the current transactional context.
	 * 
	 * @param messageCode
	 *            The message code.
	 * @return The text corresponding to the message code.
	 */
	String getText(String messageCode);

}
