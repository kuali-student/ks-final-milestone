package org.kuali.student.commons.ui.mvc.client.model;

import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.core.client.GWT;

/**
 * MVCEvent representing model change events. Should only be fired from within the Model.
 */
public abstract class ModelChangeEvent extends MVCEvent {
    /**
     * Event type fired when an object is added to the model
     */
    public static abstract class AddEvent extends ModelChangeEvent {}

    /**
     * Event type fired when an object is removed from the model
     */
    public static abstract class RemoveEvent extends ModelChangeEvent {}

    /**
     * Event type fired when an object is updated in model
     */
    public static abstract class UpdateEvent extends ModelChangeEvent {}

    /**
     * Constant instance of ModelChangeEvent
     */
    public static final ModelChangeEvent MODEL_CHANGE_EVENT = GWT.create(ModelChangeEvent.class);
    /**
     * Constant instance of ModelChangeEvent.AddEvent
     */
    public static final ModelChangeEvent ADD = GWT.create(AddEvent.class);
    /**
     * Constant instance of ModelChangeEvent.RemoveEvent
     */
    public static final ModelChangeEvent REMOVE = GWT.create(RemoveEvent.class);
    /**
     * Constant instance of ModelChangeEvent.UpdateEvent
     */
    public static final ModelChangeEvent UPDATE = GWT.create(UpdateEvent.class);

}
