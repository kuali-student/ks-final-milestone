package org.kuali.student.commons.ui.mvc.client;

import com.google.gwt.core.client.GWT;

/**
 * Base type for events in the MVC framework. All events must derive from MVCEvent or one of its subclasses. Important note:
 * due to reflection limitations, event type information is retrieved using the HasMetadata interface. Type comparisons are
 * performed based on instances rather than classes. Event instances should be treated as singletons.
 */
public abstract class MVCEvent implements HasMetadata {
    /**
     * Constant instance of MVCEvent
     */
    public static final MVCEvent MVCEVENT = GWT.create(MVCEvent.class);
}
