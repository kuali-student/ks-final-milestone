package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.lum.program.client.ProgramStatus;

/**
 * @author Igor
 */
public class StateChangeEvent extends GwtEvent<StateChangeEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private ProgramStatus programStatus;

    public StateChangeEvent(ProgramStatus programStatus) {
        this.programStatus = programStatus;
    }

    public ProgramStatus getProgramStatus() {
        return programStatus;
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
        void onEvent(StateChangeEvent event);
    }
}
