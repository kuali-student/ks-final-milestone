package org.kuali.student.lum.lu.ui.main.client.events;

import org.kuali.student.common.ui.client.mvc.ApplicationEventHandler;

public interface ChangeViewStateHandler extends ApplicationEventHandler{
    public void onViewStateChange(ChangeViewStateEvent event);
}
