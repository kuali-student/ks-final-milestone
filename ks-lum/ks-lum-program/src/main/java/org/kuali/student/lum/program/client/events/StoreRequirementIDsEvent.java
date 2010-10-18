package org.kuali.student.lum.program.client.events;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class StoreRequirementIDsEvent extends GwtEvent<StoreRequirementIdsEventHandler> {

    public static Type<StoreRequirementIdsEventHandler> TYPE = new Type<StoreRequirementIdsEventHandler>();
    private List<String> programRequirementIds;

    public StoreRequirementIDsEvent(List<String> programRequirementIds) {
        this.programRequirementIds = programRequirementIds;
    }

    @Override
    public Type<StoreRequirementIdsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StoreRequirementIdsEventHandler handler) {
        handler.onEvent(this);
    }

    public List<String> getProgramRequirementIds() {
        return programRequirementIds;
    }
}
