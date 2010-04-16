package org.kuali.student.common.ui.client.mvc.history;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEventHandler;

public interface NavigationEventHandler extends UncheckedApplicationEventHandler {
    public void onNavigationEvent(NavigationEvent event);
}
