package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class SpecializationCreatedEvent extends GwtEvent<SpecializationCreatedEventHandler> {

    public static Type<SpecializationCreatedEventHandler> TYPE = new Type<SpecializationCreatedEventHandler>();

    private final String specializationId;

    public SpecializationCreatedEvent(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationId() {
        return specializationId;
    }

    @Override
    public Type<SpecializationCreatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SpecializationCreatedEventHandler handler) {
        handler.onEvent(this);
    }
}
