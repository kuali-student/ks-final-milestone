package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;

/**
 * @author Igor
 */
public class MetadataLoadedEvent extends GwtEvent<MetadataLoadedEventHandler> {

    public static Type<MetadataLoadedEventHandler> TYPE = new Type<MetadataLoadedEventHandler>();

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
    public Type<MetadataLoadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MetadataLoadedEventHandler handler) {
        handler.onEvent(this);
    }
}
