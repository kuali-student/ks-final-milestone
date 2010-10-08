package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.core.assembly.data.Data;

/**
 * @author Igor
 */
public class SpecializationCreatedEvent extends GwtEvent<SpecializationCreatedEventHandler> {

    public static Type<SpecializationCreatedEventHandler> TYPE = new Type<SpecializationCreatedEventHandler>();

    private Data data;

    public SpecializationCreatedEvent(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public Type<SpecializationCreatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SpecializationCreatedEventHandler handler) {
        handler.onEvent(this);
    }
}
