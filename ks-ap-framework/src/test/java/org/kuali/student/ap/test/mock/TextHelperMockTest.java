package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.framework.context.TextHelper;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextHelperMockTest implements TextHelper {
    /**
     * Get the text for a message code within the current transactional context.
     *
     * @param messageCode The message code.
     * @return The text corresponding to the message code.
     */
    @Override
    public String getText(String messageCode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getText(String messageCode, String defaultValue) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getFormattedMessage(String messageCode, Object... args) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
