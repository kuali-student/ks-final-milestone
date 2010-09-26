package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class CancelEvent extends GwtEvent<CancelEventHandler> {

    public static Type<CancelEventHandler> TYPE = new Type<CancelEventHandler>();

    @Override
    public Type<CancelEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CancelEventHandler handler) {
        handler.onEvent(this);
    }
}
