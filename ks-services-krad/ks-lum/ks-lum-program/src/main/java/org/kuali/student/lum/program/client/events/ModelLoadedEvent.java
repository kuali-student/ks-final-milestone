package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.common.ui.client.mvc.DataModel;

/**
 * @author Igor
 */
public class ModelLoadedEvent extends GwtEvent<ModelLoadedEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private DataModel model;

    public ModelLoadedEvent(DataModel model) {
        this.model = model;
    }


    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onEvent(this);
    }

    public DataModel getModel() {
        return model;
    }

    public static interface Handler extends EventHandler {
        void onEvent(ModelLoadedEvent event);
    }
}
