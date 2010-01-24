package org.kuali.student.commons.ui.mvc.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

/**
 * Model implementation. Models are stored by ModelObject type. Direct references from view to model layer should be avoided.
 * 
 * @param <T>
 *            the type of ModelObject to be contained
 */
public class Model<T extends ModelObject> {
    // force initialization of the event types we're aware of
    static {
        new ModelChangeEvent();
        new ModelChangeEvent.AddEvent();
        new ModelChangeEvent.RemoveEvent();
        new ModelChangeEvent.UpdateEvent();
    }
    private final Map<String, T> data = new HashMap<String, T>();

    private final Set<ListenerMapping> listeners = new HashSet<ListenerMapping>();

    /**
     * Adds a ModelChangeEvent listener for the specified change event type
     * 
     * @see org.kuali.student.commons.ui.mvc.client.model.ModelChangeEvent
     * @param eventType
     *            the type of operation for which to listen
     * @param listener
     *            the event listener to fire
     */
    public void addListener(final Class<? extends ModelChangeEvent> eventType, final MVCEventListener listener) {
        ListenerMapping lm = new ListenerMapping();
        lm.eventType = eventType;
        lm.listener = listener;
        listeners.add(lm);
    }

    /**
     * Removes the specified listener
     * 
     * @param eventType
     *            the event type to which the listener was bound
     * @param listener
     *            the listener to remove
     */
    public void removeListener(final Class<? extends ModelChangeEvent> eventType, final MVCEventListener listener) {
        List<ListenerMapping> toRemove = new ArrayList<ListenerMapping>();
        for (ListenerMapping lm : listeners) {
            if (eventType.equals(lm.eventType) && listener.equals(lm.listener)) {
                toRemove.add(lm);
            }
        }
        listeners.removeAll(toRemove);
    }

    private void fireChangeEvent(final Class<? extends ModelChangeEvent> event, final T data) {
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
        public Class<? extends ModelChangeEvent> eventType;
        public MVCEventListener listener;
    }

    /**
     * Adds an object to the model and fires ModelChangeEvent.ADD
     * 
     * @param object
     *            the object to add
     */
    public void add(T object) {
        data.put(object.getUniqueId(), object);
        fireChangeEvent(ModelChangeEvent.AddEvent.class, object);
    }

    /**
     * Updates an object in the model, adding it to the model if it does not already exist. Fires ModelChangeEvent.ADD if
     * added, ModelChangeEvent.UPDATE if updated
     * 
     * @param object
     *            the object to update
     */
    public void update(T object) {
        boolean added = false;
        String key = object.getUniqueId();
        if (!data.containsKey(key)) {
            added = true;
        }
        data.put(object.getUniqueId(), object);
        if (added) {
            fireChangeEvent(ModelChangeEvent.AddEvent.class, object);
        } else {
            fireChangeEvent(ModelChangeEvent.UpdateEvent.class, object);
        }
    }

    /**
     * Removes an object from the model and fires ModelChangeEvent.REMOVE
     * 
     * @param object
     *            the object to remove
     */
    public void remove(T object) {
        data.remove(object.getUniqueId());
        fireChangeEvent(ModelChangeEvent.RemoveEvent.class, object);
    }
    
    /**
     * 
     * Returns true if the model contains the object
     * 
     * @param object the object to look for
     * @return true if the model contains the object
     */
    public boolean contains(T object) {
        return object != null && data.containsKey(object.getUniqueId());
    }
    
    /**
     * 
     * Removes all of the objects in the model, change events are fired.
     *
     */
    public void clear() {
        List<T> tmp = items();
        for (T t : tmp) {
            remove(t);
        }
    }

    /**
     * Returns an object from the model based on its unique id
     * 
     * @see org.kuali.student.commons.ui.mvc.client.model.ModelObject.getUniqueId()
     * @param uniqueId
     *            the unique id of the object to retrieve
     * @return the requested object, or null if not found
     */
    public T get(String uniqueId) {
        return data.get(uniqueId);
    }

    /**
     * Returns the underlying model as a List. Clones the values collection into a new collection to prevent direct
     * manipulation of model
     * 
     * @return the underlying model as a List
     */
    public List<T> items() {
        return new ArrayList<T>(data.values());
    }

}
