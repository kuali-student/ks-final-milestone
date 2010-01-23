package org.kuali.student.commons.ui.mvc.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Base type for events in the MVC framework. All events must derive from MVCEvent or one of its subclasses. Important note:
 * due to reflection limitations, event type information is retrieved using the HasMetadata interface. Type comparisons are
 * performed based on instances rather than classes. Event instances should be treated as singletons.
 */
public class MVCEvent {
    static {
        EventTypeRegistry.register(MVCEvent.class, new MVCEvent().getHierarchy());
    }
    
    public EventTypeHierarchy getHierarchy() {
        EventTypeHierarchy result = new EventTypeHierarchy();
        return result.add(MVCEvent.class);
    }
    
}
