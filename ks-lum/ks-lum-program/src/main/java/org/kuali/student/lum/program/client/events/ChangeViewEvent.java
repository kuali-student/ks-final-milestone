package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class ChangeViewEvent extends GwtEvent<ChangeViewEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private Enum<?> viewToken;

    public ChangeViewEvent(Enum<?> viewToken) {
        this.viewToken = viewToken;
    }

    public Enum<?> getViewToken() {
        return viewToken;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onEvent(this);
    }

    public static interface Handler extends EventHandler {
        void onEvent(ChangeViewEvent event);
    }
}
