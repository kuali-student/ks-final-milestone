package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.common.ui.client.mvc.DataModel;

/**
 * @author Igor
 */
public class ModelLoadedEvent extends GwtEvent<ModelLoadedEventHandler> {

    public static Type<ModelLoadedEventHandler> TYPE = new Type<ModelLoadedEventHandler>();

    private DataModel model;

    public ModelLoadedEvent(DataModel model) {
        this.model = model;
    }


    @Override
    public Type<ModelLoadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ModelLoadedEventHandler handler) {
        handler.onEvent(this);
    }

    public DataModel getModel() {
        return model;
    }
}
