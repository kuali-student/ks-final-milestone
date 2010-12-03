package org.kuali.student.lum.program.client.events;

import org.kuali.student.core.assembly.data.Data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Igor
 */
public class SpecializationUpdateEvent extends GwtEvent<SpecializationUpdateEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private final Data specializations;

    public SpecializationUpdateEvent(Data specs) {
        this.specializations = specs;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    public Data getSpecializations() {
        return specializations;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onEvent(this);
    }

    public static interface Handler extends EventHandler {
        void onEvent(SpecializationUpdateEvent event);
    }
}
