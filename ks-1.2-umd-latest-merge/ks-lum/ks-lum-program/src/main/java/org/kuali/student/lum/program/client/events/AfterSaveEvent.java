package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;

/**
 * @author Igor
 */
public class AfterSaveEvent extends GwtEvent<AfterSaveEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private DataModel model;

    private ProgramController controller;

    public AfterSaveEvent(DataModel model, ProgramController controller) {
        this.model = model;
        this.controller = controller;
    }

    public DataModel getModel() {
        return model;
    }

    public ProgramController getController() {
        return controller;
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
        void onEvent(AfterSaveEvent event);
    }
}
