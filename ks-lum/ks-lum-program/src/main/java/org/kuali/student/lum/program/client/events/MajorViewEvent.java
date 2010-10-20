package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class MajorViewEvent extends GwtEvent<MajorViewEventHandler> {

    public static Type<MajorViewEventHandler> TYPE = new Type<MajorViewEventHandler>();

    @Override
    public Type<MajorViewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MajorViewEventHandler handler) {
        handler.onEvent(this);
    }
}
