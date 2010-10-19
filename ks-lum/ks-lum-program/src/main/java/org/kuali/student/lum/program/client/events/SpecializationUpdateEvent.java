package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class SpecializationUpdateEvent extends GwtEvent<SpecializationUpdateEventHandler> {

    public static Type<SpecializationUpdateEventHandler> TYPE = new Type<SpecializationUpdateEventHandler>();

    @Override
    public Type<SpecializationUpdateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SpecializationUpdateEventHandler handler) {
        handler.onEvent(this);
    }
}
