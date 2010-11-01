package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;

/**
 * @author Igor
 */
public class MetadataLoadedEvent extends GwtEvent<MetadataLoadedEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();

    private ModelDefinition modelDefinition;

    private MajorEditController controller;

    public MetadataLoadedEvent(ModelDefinition modelDefinition, MajorEditController controller) {
        this.modelDefinition = modelDefinition;
        this.controller = controller;
    }

    public ModelDefinition getModelDefinition() {
        return modelDefinition;
    }

    public MajorEditController getController() {
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
        void onEvent(MetadataLoadedEvent event);
    }
}
