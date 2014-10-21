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
     * Key used to look up the config values for the resource bundle names
     */
    public static final String CONFIG_RESOURCE_BUNDLE_NAMES = "ks.ap.TextHelper.resourceBundleNames";

	/**
	 * Get the text for a message code within the current transactional context.
	 * 
	 * @param messageCode
	 *            The message code.
	 * @return The text corresponding to the message code.
	 */
	String getText(String messageCode);

    /**
     * Get the text for a message code within the current transactional context.
     *
     * @param messageCode
     *            The message code.
     * @param defaultValue
     *            The value to use as a default if nothing is found
     * @return The text corresponding to the message code.
     * @since KSAP-0.8.0
     */
    String getText(String messageCode, String defaultValue);

    /**
     * Get the text for a message code within the current transactional context.
     * The pattern string would look like this:
     * "My favorite color is {0}."
     * So your code call would look like this:
     * {@code}getFormattedMessage("msg", "Blue"){/@code}
     * And the result would be "My favorite color is Blue"
     * @param messageCode
     *              The message code
     * @param args
     *              Values to use as replacements in the returned string.
     * @return The text corresponding to the message code, filling in the placeholders with values supplied.
     * @since KSAP-0.8.0
     */
    String getFormattedMessage(String messageCode, Object... args);

}
