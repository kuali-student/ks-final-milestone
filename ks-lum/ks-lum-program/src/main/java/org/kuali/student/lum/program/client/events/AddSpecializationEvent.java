package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class AddSpecializationEvent extends GwtEvent<AddSpecializationEventHandler> {

    public static Type<AddSpecializationEventHandler> TYPE = new Type<AddSpecializationEventHandler>();

    @Override
    public Type<AddSpecializationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddSpecializationEventHandler handler) {
        handler.onEvent(this);
    }
}
