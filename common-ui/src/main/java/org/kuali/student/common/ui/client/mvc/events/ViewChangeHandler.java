package org.kuali.student.common.ui.client.mvc.events;

import org.kuali.student.common.ui.client.mvc.ApplicationEventHandler;

/**
 * Handler for ViewChangeEvents
 * 
 * @author Kuali Student Team
 */
public interface ViewChangeHandler extends ApplicationEventHandler {
    public void onViewChange(ViewChangeEvent event);
}
