package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AddRequirementEvent extends GwtEvent<AddRequirementEventHandler> {

    public static Type<AddRequirementEventHandler> TYPE = new Type<AddRequirementEventHandler>();

    @Override
    public Type<AddRequirementEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddRequirementEventHandler handler) {
        handler.onEvent(this);
    }
}
