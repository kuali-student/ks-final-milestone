package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.core.assembly.data.Data;

/**
 * @author Igor
 */
public class SpecializationSaveEvent extends GwtEvent<SpecializationSaveEventHandler> {

    public static Type<SpecializationSaveEventHandler> TYPE = new Type<SpecializationSaveEventHandler>();

    private Data data;

    public SpecializationSaveEvent(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public Type<SpecializationSaveEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SpecializationSaveEventHandler handler) {
        handler.onEvent(this);
    }
}
