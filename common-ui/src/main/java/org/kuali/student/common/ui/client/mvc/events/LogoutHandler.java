package org.kuali.student.common.ui.client.mvc.events;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEventHandler;

/**
 * Example UncheckedApplicationEventHandler associated with LogoutEvent
 * 
 * @author Kuali Student Team
 */
public interface LogoutHandler extends UncheckedApplicationEventHandler {
    public void onLogout(LogoutEvent event);
}
