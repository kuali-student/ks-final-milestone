package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class ChangeViewEvent extends GwtEvent<ChangeViewEventHandler> {

    public static Type<ChangeViewEventHandler> TYPE = new Type<ChangeViewEventHandler>();

    private Enum<?> viewToken;

    public ChangeViewEvent(Enum<?> viewToken) {
        this.viewToken = viewToken;
    }

    public Enum<?> getViewToken() {
        return viewToken;
    }

    @Override
    public Type<ChangeViewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChangeViewEventHandler handler) {
        handler.onEvent(this);
    }
}
