package org.kuali.student.common.ui.client.mvc;

import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for ModelChangeEvent
 * 
 * @author Kuali Student Team
 * @param <T>
 */
public interface ModelChangeHandler<T> extends EventHandler {
    /**
     * Invoked when a ModelChangeEvent is dispatched to this handler.
     * 
     * @param event
     *            the event that was fired
     */
    public void onModelChange(ModelChangeEvent<T> event);
}
