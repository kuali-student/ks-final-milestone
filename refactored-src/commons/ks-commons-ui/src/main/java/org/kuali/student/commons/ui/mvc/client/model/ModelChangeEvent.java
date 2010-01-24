package org.kuali.student.commons.ui.mvc.client.model;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.core.client.GWT;

/**
 * MVCEvent representing model change events. Should only be fired from within the Model.
 */
public class ModelChangeEvent extends MVCEvent {
    static {
        EventTypeRegistry.register(ModelChangeEvent.class, new ModelChangeEvent().getHierarchy());
    }
    
 
    
    public EventTypeHierarchy getHierarchy() {
        return super.getHierarchy().add(ModelChangeEvent.class);
    }

    /**
     * Event type fired when an object is added to the model
     */
    public static class AddEvent extends ModelChangeEvent {
        static {
            EventTypeRegistry.register(AddEvent.class, new AddEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(AddEvent.class);
        }
    }

    /**
     * Event type fired when an object is removed from the model
     */
    public static class RemoveEvent extends ModelChangeEvent {
        static {
            EventTypeRegistry.register(RemoveEvent.class, new RemoveEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(RemoveEvent.class);
        }
    }

    /**
     * Event type fired when an object is updated in model
     */
    public static class UpdateEvent extends ModelChangeEvent {
        static {
            EventTypeRegistry.register(UpdateEvent.class, new UpdateEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(UpdateEvent.class);
        }
    }

}
