package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class ModelLoadedEvent extends GwtEvent<ModelLoadedEventHandler> {

    public static Type<ModelLoadedEventHandler> TYPE = new Type<ModelLoadedEventHandler>();

    @Override
    public Type<ModelLoadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ModelLoadedEventHandler handler) {
        handler.onEvent(this);
    }
}
