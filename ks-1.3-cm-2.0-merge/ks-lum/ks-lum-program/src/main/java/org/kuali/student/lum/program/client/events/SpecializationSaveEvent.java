package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import org.kuali.student.r1.common.assembly.data.Data;

/**
 * @author Igor
 */
public class SpecializationSaveEvent extends GwtEvent<SpecializationSaveEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private Data data;

    public SpecializationSaveEvent(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
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
        void onEvent(SpecializationSaveEvent event);
    }
}
