package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;

/**
 * @author Igor
 */
public class UpdateEvent extends GwtEvent<UpdateEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private Callback<Boolean> okCallback;

    private Enum<?> currentView;

    public UpdateEvent() {
        this(Controller.NO_OP_CALLBACK);
    }

    public UpdateEvent(Enum<?> currentView) {
        this();
        this.currentView = currentView;
    }

    public UpdateEvent(Callback<Boolean> okCallback) {
        this.okCallback = okCallback;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onEvent(this);
    }

    public Callback<Boolean> getOkCallback() {
        return okCallback;
    }

    public Enum<?> getCurrentView() {
        return currentView;
    }

    public static interface Handler extends EventHandler {
        void onEvent(UpdateEvent event);
    }
}
