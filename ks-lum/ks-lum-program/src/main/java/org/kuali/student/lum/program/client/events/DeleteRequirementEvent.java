package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteRequirementEvent extends GwtEvent<DeleteRequirementEventHandler> {

    public static Type<DeleteRequirementEventHandler> TYPE = new Type<DeleteRequirementEventHandler>();

    @Override
    public Type<DeleteRequirementEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteRequirementEventHandler handler) {
        handler.onEvent(this);
    }
}
