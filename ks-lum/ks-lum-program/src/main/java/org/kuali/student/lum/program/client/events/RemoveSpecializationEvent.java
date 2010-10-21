package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class RemoveSpecializationEvent extends GwtEvent<RemoveSpecializationEventHandler> {

    public static Type<RemoveSpecializationEventHandler> TYPE = new Type<RemoveSpecializationEventHandler>();

    private String id;

    public RemoveSpecializationEvent(String id) {
        this.id = id;
    }

    public static Type<RemoveSpecializationEventHandler> getTYPE() {
        return TYPE;
    }

    @Override
    public Type<RemoveSpecializationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RemoveSpecializationEventHandler handler) {
        handler.onEvent(this);
    }
}
