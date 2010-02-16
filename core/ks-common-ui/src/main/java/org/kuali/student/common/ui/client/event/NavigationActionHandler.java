package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.ApplicationEventHandler;

public interface NavigationActionHandler extends ApplicationEventHandler{
    public void processNavigation(NavigationActionEvent navAction);
}
