package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.lum.program.client.major.edit.ProgramEditController;

/**
 * @author Igor
 */
public class MetadataLoadedEvent extends GwtEvent<MetadataLoadedEventHandler> {

    public static Type<MetadataLoadedEventHandler> TYPE = new Type<MetadataLoadedEventHandler>();

    private Metadata metadata;

    private ProgramEditController controller;

    public MetadataLoadedEvent(Metadata metadata, ProgramEditController controller) {
        this.metadata = metadata;
        this.controller = controller;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public ProgramEditController getController() {
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
