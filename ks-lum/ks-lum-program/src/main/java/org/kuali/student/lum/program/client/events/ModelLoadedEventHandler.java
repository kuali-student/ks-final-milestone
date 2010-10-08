package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Igor
 */
public interface ModelLoadedEventHandler extends EventHandler {
    void onEvent(ModelLoadedEvent event);
}
