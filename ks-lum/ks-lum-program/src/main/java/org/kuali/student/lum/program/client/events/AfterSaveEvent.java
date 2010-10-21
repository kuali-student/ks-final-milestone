package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.common.ui.client.mvc.DataModel;

/**
 * @author Igor
 */
public class AfterSaveEvent extends GwtEvent<AfterSaveEventHandler> {

    public static Type<AfterSaveEventHandler> TYPE = new Type<AfterSaveEventHandler>();

    private DataModel model;

    public AfterSaveEvent(DataModel model) {
        this.model = model;
    }

    public DataModel getModel() {
        return model;
    }

    @Override
    public Type<AfterSaveEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AfterSaveEventHandler handler) {
        handler.onEvent(this);
    }
}
