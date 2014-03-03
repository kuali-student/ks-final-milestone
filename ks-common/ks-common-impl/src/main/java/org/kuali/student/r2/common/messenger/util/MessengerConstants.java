package org.kuali.student.r2.common.messenger.util;

/**
 * This class represents constants for the jms user message messenger implementation.
 *
 * @author Kuali Student Team
 */
public class MessengerConstants {

    //Destination
    public static final String USER_MESSAGE_DESTINATION = "org.kuali.student.user.message";

    //Message themes
    public static final String WARNING_MESSAGE = "WARNING";
    public static final String INFO_MESSAGE = "INFORMATION";
    public static final String ERROR_MESSAGE = "ERROR";
    public static final String SUCCESS_MESSAGE = "SUCCESS";

    //User Message constants
    public static final String USER_MESSAGE_USER = "org.kuali.student.user.message.user";
    public static final String USER_MESSAGE_KEY = "org.kuali.student.user.message.key";
    public static final String USER_MESSAGE_THEME = "org.kuali.student.user.message.theme";
    public static final String USER_MESSAGE_PARAMETERS = "org.kuali.student.user.message.parameters";

}
