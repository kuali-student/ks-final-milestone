package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class RemoveSpecializationEvent extends GwtEvent<RemoveSpecializationEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private String id;

    public RemoveSpecializationEvent(String id) {
        this.id = id;
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
        void onEvent(RemoveSpecializationEvent event);
    }
}
