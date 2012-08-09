package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class SpecializationCreatedEvent extends GwtEvent<SpecializationCreatedEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private final String specializationId;

    public SpecializationCreatedEvent(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationId() {
        return specializationId;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onEvent(this);
    }

    public static interface Handler extends EventHandler {
        void onEvent(SpecializationCreatedEvent event);
    }
}
