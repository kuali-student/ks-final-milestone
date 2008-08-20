package org.kuali.student.commons.ui.mvc.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

/**
 * Responsible for brokering events to event listeners.
 */
public class EventDispatcher {
    private final Set<ListenerMapping> listeners = new HashSet<ListenerMapping>();

    /**
     * Adds an event listener for the specified event type, and any of its subclasses
     * 
     * @param eventType
     *            the type of event to listen for
     * @param listener
     *            the listener to invoke when the event is fired
     */
    public void addListener(final Class<? extends MVCEvent> eventType, final MVCEventListener listener) {
        ListenerMapping lm = new ListenerMapping();
        lm.eventType = eventType;
        lm.listener = listener;
        listeners.add(lm);
    }

    /**
     * Removes a listener for the specified event type
     * 
     * @param eventType
     *            the event type being listened for
     * @param listener
     *            the listener to remove
     */
    public void removeListener(final Class<? extends MVCEvent> eventType, final MVCEventListener listener) {
        List<ListenerMapping> toRemove = new ArrayList<ListenerMapping>();
        for (ListenerMapping lm : listeners) {
            if (eventType.equals(lm.eventType) && listener.equals(lm.listener)) {
                toRemove.add(lm);
            }
        }
        listeners.removeAll(toRemove);
    }

    /**
     * Fires an event to any matching listeners
     * 
     * @param event
     *            the event to fire
     */
    public void fireEvent(final Class<? extends MVCEvent> event) {
        fireEvent(event, null);
    }

    /**
     * Fires an event to any matching listeners
     * 
     * @param event
     *            the event to fire
     * @param data
     *            Object associated with the event
     */
    public void fireEvent(final Class<? extends MVCEvent> event, final Object data) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                for (ListenerMapping lm : listeners) {
                    if (EventTypeRegistry.isSubClass(lm.eventType, event)) {
                        lm.listener.onEvent(event, data);
                    }
                }
            }
        });
    }

    private static class ListenerMapping {
        public Class<? extends MVCEvent> eventType;
        public MVCEventListener listener;
    }

}
