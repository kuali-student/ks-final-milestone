package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class UpdateEvent extends GwtEvent<UpdateEventHandler> {

    public static Type<UpdateEventHandler> TYPE = new Type<UpdateEventHandler>();

    @Override
    public Type<UpdateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UpdateEventHandler handler) {
        handler.onEvent(this);
    }
}
